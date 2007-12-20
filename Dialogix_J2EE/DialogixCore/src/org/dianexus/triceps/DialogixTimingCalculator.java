package org.dianexus.triceps;

import java.math.BigInteger;
import java.sql.Timestamp; // FIXME - shouldn't we be moving away from sql Timestamps?
import java.util.*;

import org.dialogix.entities.*;
import org.dialogix.session.DialogixEntitiesFacadeLocal;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.logging.*;

/**
This class consolidates all of the timing functionality, including processing events, and determining response times
 */
public class DialogixTimingCalculator {

    static Logger logger = Logger.getLogger("org.dianexus.triceps.DialogixTimingCalculator");
    private boolean initialized = false;
    private long priorTimeEndServerProcessing;
    private long timeBeginServerProcessing;
    private long timeEndServerProcessing;
    private int networkDuration;
    private int serverDuration;
    private int loadDuration;
    private int pageDuration;
    private int totalDuration;
    private InstrumentSession instrumentSession = null;
    private PageUsage pageUsage = null;
    private ArrayList<ItemUsage> itemUsages = null;
    private ArrayList<PageUsage> pageUsages = null;
    private ArrayList<PageUsageEvent> pageUsageEvents = null;
    private ArrayList<DataElement> dataElements = null;
    private HashMap<String, DataElement> dataElementHash = null;
    private HashMap<Integer, Integer> groupNumVisits = null;
    private HashMap<String, ActionType> actionTypeHash = null;
    private HashMap<String, NullFlavor> nullFlavorHash = null;
    private int pageUsageCounter = 0;
    private int pageUsageEventCounter = 0;
    private int itemUsageCounter = 0;
    private boolean finished = false;
    private DialogixEntitiesFacadeLocal dialogixEntitiesFacade = null;

    /**
    Empty constructor to avoid NullPointerException
     */
    public DialogixTimingCalculator() {
        lookupDialogixEntitiesFacadeLocal();
        initialized = false;
    }

    /**
     * Restore Data_Elements from a specific file.
     */
    public DialogixTimingCalculator(String restoreFile) {
        try {
            beginServerProcessing(System.currentTimeMillis());
            setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());

            lookupDialogixEntitiesFacadeLocal();
            InstrumentSession restoredSession = dialogixEntitiesFacade.findInstrumentSessionByName(restoreFile);
            if (restoredSession == null) {
                logger.log(Level.SEVERE,"Unable to restore session: " + restoreFile);
                initialized = false;
            }
            instrumentSession = restoredSession;

            pageUsages = new ArrayList<PageUsage>();
            groupNumVisits = new HashMap<Integer, Integer>();

            Iterator<DataElement> dataElementIterator = instrumentSession.getDataElementCollection().iterator();
            dataElementHash = new HashMap<String, DataElement>();
            while (dataElementIterator.hasNext()) {
                DataElement dataElement = dataElementIterator.next();
                VarName varName = dataElement.getInstrumentContentID().getVarNameID();
                dataElementHash.put(varName.getVarName(), dataElement);
                groupNumVisits.put(dataElement.getGroupNum(), dataElement.getItemVisits()); // this will set groupNumVisits with the final counts
            }
            pageUsage.setPageVisits(groupNumVisits.get(instrumentSession.getCurrentGroup()));
            pageUsage.setFromGroupNum(instrumentSession.getCurrentGroup());
            
            initialized = true;
        } catch (Throwable e) {
            logger.log(Level.SEVERE,"", e);
            initialized = false;
        }
    }

    /**
    Constructor.  This loads the proper instrument from the database (based upon title, and version).
    Initializes the session.
    @param instrumentTitle	The title of the instrument
    @param major_version Major version
    @param minor_version Minor version
    @param dialogixUserID DialogixUser ID - not currently used
    @param startingStep	The starting step (first group)
     */
    public DialogixTimingCalculator(String instrumentTitle, String major_version, String minor_version, int dialogixUserID, int startingStep, String filename) {
        try {
            beginServerProcessing(System.currentTimeMillis());
            lookupDialogixEntitiesFacadeLocal();

            setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());

            //	handle error if versions not found
            if (major_version == null) {
                major_version = "0";
            }
            if (minor_version == null) {
                minor_version = "0";
            }

            InstrumentVersion instrumentVersion = dialogixEntitiesFacade.getInstrumentVersion(instrumentTitle, major_version, minor_version);
            if (instrumentVersion == null) {
                throw new Exception("Unable to find Instrument " + instrumentTitle + "(" + major_version + "." + minor_version + ")");
            }
            Instrument instrument = instrumentVersion.getInstrumentID();

            pageUsages = new ArrayList<PageUsage>();            
            groupNumVisits = new HashMap<Integer, Integer>();

            // Create InstrumentSession Bean - as side effect, sets all startup values (which may be inappropriate)
            instrumentSession = new InstrumentSession();
            instrumentSession.setInstrumentVersionID(instrumentVersion);
            instrumentSession.setDisplayNum(-1);
            instrumentSession.setInstrumentStartingGroup(startingStep);
            instrumentSession.setLanguageCode("en");
            instrumentSession.setStatusMsg("init");
            instrumentSession.setStartTime(new Timestamp(System.currentTimeMillis()));
            instrumentSession.setLastAccessTime(instrumentSession.getStartTime());
            instrumentSession.setActionTypeID(parseActionType("START"));     
            instrumentSession.setDialogixUserID(null);  // FIXME
            instrumentSession.setInstrumentID(instrument);
            instrumentSession.setInstrumentVersionID(instrumentVersion);
            instrumentSession.setInstrumentSessionFileName(filename.replace('\\', '/'));
            instrumentSession.setMaxVarNum(startingStep);
            instrumentSession.setNumGroups(instrumentVersion.getInstrumentHashID().getNumGroups());
            instrumentSession.setNumVars(instrumentVersion.getInstrumentHashID().getNumVars());

            instrumentSession.setItemUsageCollection(itemUsages);
            instrumentSession.setCurrentVarNum(startingStep);

            // Create the collection of DataElements
            dataElementHash = new HashMap<String, DataElement>();
            Iterator<InstrumentContent> iterator = instrumentVersion.getInstrumentContentCollection().iterator();
            int dataElementSequence = -1;
            int lastVarNumVisited = -1;
            while (iterator.hasNext()) {
                ++dataElementSequence;
                InstrumentContent instrumentContent = iterator.next();
                DataElement dataElement = new DataElement();
                dataElement.setInstrumentContentID(instrumentContent);
                dataElement.setDataElementSequence(instrumentContent.getItemSequence());
                dataElement.setInstrumentSessionID(instrumentSession);
                dataElement.setItemVisits(-1); // will be incremented again (setting it to 0) with fist call to writeNode()
                dataElement.setLanguageCode("en");
                dataElement.setNullFlavorID(parseNullFlavor("*UNASKED*"));   
                dataElement.setDisplayNum(0);
                dataElement.setVarNameID(instrumentContent.getVarNameID());
                dataElement.setGroupNum(instrumentContent.getGroupNum());
                dataElements.add(dataElement);
                dataElementHash.put(instrumentContent.getVarNameID().getVarName(), dataElement);
                if (dataElementSequence == startingStep) {
                    instrumentSession.setCurrentGroup(instrumentContent.getGroupNum());
                    instrumentSession.setMaxGroup(instrumentContent.getGroupNum()); // may be called several times
                    lastVarNumVisited = dataElementSequence;
                }
                groupNumVisits.put(dataElement.getGroupNum(), 0);
            }
            instrumentSession.setDataElementCollection(dataElements);

            instrumentSession.setMaxVarNum(lastVarNumVisited);  // last VarNum on the screen of maxGroup
            instrumentSession.setFinished(isFinished() ? 1 : 0);

            dialogixEntitiesFacade.persist(instrumentSession);

            initialized = true;
        } catch (Throwable e) {
            logger.log(Level.SEVERE,"", e);
        }
    }

    /**
    This is called when the server receives a request.  It starts the the clock for server processing time.
    Side effects include:
    (1) Increasing displayCount
    (2) Setting displayCount, groupNum, and storing that information to pageHits
    @param timestamp	System time in milliseconds
     */
    public void beginServerProcessing(Long timestamp) {
        setTimeBeginServerProcessing(System.currentTimeMillis());
        setPerPageParams();
    }

    private void setPerPageParams() {
        try {
            pageUsage = new PageUsage();
            pageUsageEvents = new ArrayList<PageUsageEvent>();
            pageUsage.setPageUsageEventCollection(pageUsageEvents);
            pageUsage.setPageUsageSequence(++pageUsageCounter);
            if (initialized == true) {
                pageUsage.setFromGroupNum(instrumentSession.getCurrentGroup());
                pageUsage.setPageVisits(groupNumVisits.get(instrumentSession.getCurrentGroup()));
            } else {
                pageUsage.setFromGroupNum(-1);  // so know hasn't yet been set
                pageUsage.setPageVisits(0);                
            }
            itemUsages = new ArrayList<ItemUsage>();
            dataElements = new ArrayList<DataElement>();
        } catch (Throwable e) {
            logger.log(Level.SEVERE,"beginServerProcessing", e);
        }
    }

    /**
    This is called when the server is ready to return a response to the dialogixUser.
    ServerProcessingTime = (finishServerProcessing.timestamp - beginServerProcessing.timestamp)
    @param timestamp	Current system time
     */
    public void finishServerProcessing(Long timestamp) {
        try {
            if (initialized == false) {
                logger.log(Level.FINE,"DialogixTimingCalculator not yet initialized");
                return;
            }
            int displayNum = instrumentSession.getDisplayNum() + 1;
            instrumentSession.setDisplayNum(displayNum);

            // Update Session State
            instrumentSession.setLastAccessTime(new Timestamp(timestamp.longValue()));
            
            // Need to determine GroupNum from VarNum
            Iterator<DataElement> iterator = instrumentSession.getDataElementCollection().iterator();
            while (iterator.hasNext()) {
                DataElement dataElement = iterator.next();
                if (dataElement.getDataElementSequence() == instrumentSession.getCurrentVarNum()) {
                    instrumentSession.setCurrentGroup(dataElement.getGroupNum());
                }
            }
            instrumentSession.setLanguageCode(getLangCode());

            // Add information about this page-worth of usage
            pageUsage.setDisplayNum(instrumentSession.getDisplayNum());
            pageUsage.setLanguageCode(instrumentSession.getLanguageCode());
            pageUsage.setToGroupNum(instrumentSession.getCurrentGroup());
            pageUsage.setActionTypeID(instrumentSession.getActionTypeID());
            pageUsage.setStatusMsg(instrumentSession.getStatusMsg());
            pageUsage.setInstrumentSessionID(instrumentSession);
            pageUsage.setPageVisits(groupNumVisits.get(pageUsage.getFromGroupNum()));   // TODO - CHECK
            pageUsage.setTimeStamp(instrumentSession.getLastAccessTime());

            setTimeEndServerProcessing(System.currentTimeMillis());
            setServerDuration((int) (getTimeEndServerProcessing() - getTimeBeginServerProcessing()));
            setTotalDuration((int) (getTimeBeginServerProcessing() - getPriorTimeEndServerProcessing()));
            setNetworkDuration(getTotalDuration() - getLoadDuration() - getPageDuration() - getServerDuration());

            pageUsage.setLoadDuration(getLoadDuration());
            pageUsage.setNetworkDuration(getNetworkDuration());
            pageUsage.setPageDuration(getPageDuration());
            pageUsage.setServerDuration(getServerDuration());
            pageUsage.setTotalDuration(getTotalDuration());

            pageUsages.add(pageUsage);

            setPriorTimeEndServerProcessing(getTimeEndServerProcessing());
            instrumentSession.setPageUsageCollection(pageUsages);
            instrumentSession.setItemUsageCollection(itemUsages);
            instrumentSession.setFinished(isFinished() ? 1 : 0);

            dialogixEntitiesFacade.merge(instrumentSession);
        } catch (Throwable e) {
            logger.log(Level.SEVERE,"", e);
        }
    }

    private BigInteger findAnswerID(DataElement dataElement, String encodedAnswer) {
        try {
            AnswerList answerList = dataElement.getInstrumentContentID().getItemID().getAnswerListID();
            if (answerList == null) {
                return null;
            }
            Iterator<AnswerListContent> iterator = answerList.getAnswerListContentCollection().iterator();
            while (iterator.hasNext()) {
                AnswerListContent answerListContent = iterator.next();
                if (answerListContent.getAnswerCode().equals(encodedAnswer)) {
                    return answerListContent.getAnswerID().getAnswerID();
                }
            }
        } catch (Throwable e) {
            logger.log(Level.SEVERE,"Unable to find AnswerID for " + encodedAnswer, e);
        }
        return null;
    }

    private Integer findNullFlavor(Datum ans) {
        if (ans.isSpecial()) {
            return new Integer(ans.type());
        } else {
            return new Integer(0);
        }
    }

    /**
    This assigns a value (Datum) to an item (Node).
    @param ques	the Item
    @param ans	the Value
     */
    public void writeNode(Node ques, Datum ans) {
        if (!initialized) {
            return;
        }
        try {
            if (ques != null && ans != null) {
                // Update in-memory (and persisted) data store
                String answerCode = InputEncoder.encode(ans.stringVal(true));   // TODO - CHECK - what is difference between these?  Which should be used?
                String answerString = null;
                if (!ans.isSpecial()) {
                    answerString = InputEncoder.encode(ques.getLocalizedAnswer(ans));
                }
                String questionAsAsked = InputEncoder.encode(ques.getQuestionAsAsked());
                String varNameString = ques.getLocalName();
                Timestamp timestamp = new Timestamp(ques.getTimeStamp().getTime());

                DataElement dataElement = dataElementHash.get(varNameString);
                if (dataElement == null) {
                    logger.log(Level.SEVERE,"Attempt to write to unitialized DataElement " + varNameString);
                    return;
                }

                dataElement.setItemVisits(dataElement.getItemVisits() + 1);
                if (dataElement.getItemVisits() == 0) {
                    return; // don't write initial *UNASKED* values 
                }

                dataElement.setAnswerID(findAnswerID(dataElement, answerCode));    // FIXME - must be a  better way to do this!
                dataElement.setAnswerCode(answerCode);
                dataElement.setAnswerString(answerString);
                dataElement.setComments(ques.getComment());
                dataElement.setDisplayNum(instrumentSession.getDisplayNum());
                dataElement.setLanguageCode(instrumentSession.getLanguageCode());
                dataElement.setQuestionAsAsked(questionAsAsked);
                dataElement.setWhenAsMS(ques.getTimeStamp().getTime());
                dataElement.setTimeStamp(timestamp);
                dataElement.setNullFlavorID(findNullFlavor(ans));

                if (!dataElements.contains(dataElement)) {
                    dataElements.add(dataElement);
                }

                ItemUsage itemUsage = cloneDataElement(dataElement);

                itemUsages.add(itemUsage);
                instrumentSession.getItemUsageCollection().add(itemUsage);

                if (dataElement.getGroupNum() > instrumentSession.getMaxGroup()) {
                    instrumentSession.setMaxGroup(dataElement.getGroupNum());
                }
                if (dataElement.getDataElementSequence() > instrumentSession.getMaxVarNum()) {
                    instrumentSession.setMaxVarNum(dataElement.getDataElementSequence());
                }
                
                groupNumVisits.put(dataElement.getGroupNum(), dataElement.getItemVisits()); // to update visit counts               

            }
        } catch (Throwable e) {
            logger.log(Level.SEVERE,"WriteNode Error", e);
        }
    }

    /**
     *  Copy all content of DataElement into ItemUsage
     */
    private ItemUsage cloneDataElement(DataElement dataElement) {
        ItemUsage itemUsage = new ItemUsage();

        itemUsage.setAnswerCode(dataElement.getAnswerCode());
        itemUsage.setAnswerString(dataElement.getAnswerString());
        itemUsage.setComments(dataElement.getComments());
        itemUsage.setDataElementSequence(dataElement.getDataElementSequence());
        itemUsage.setDisplayNum(dataElement.getDisplayNum());
        itemUsage.setGroupNum(dataElement.getGroupNum());
        itemUsage.setItemUsageSequence(++itemUsageCounter);
        itemUsage.setItemVisits(dataElement.getItemVisits());
        itemUsage.setLanguageCode(dataElement.getLanguageCode());
        itemUsage.setQuestionAsAsked(dataElement.getQuestionAsAsked());
        itemUsage.setTimeStamp(dataElement.getTimeStamp());
        itemUsage.setInstrumentSessionID(dataElement.getInstrumentSessionID());
        itemUsage.setVarNameID(dataElement.getVarNameID()); // TODO - CHECK - needed?  get from InstrumentContents?
        itemUsage.setWhenAsMS(dataElement.getWhenAsMS());
        itemUsage.setNullFlavorID(dataElement.getNullFlavorID());
        itemUsage.setInstrumentContentID(dataElement.getInstrumentContentID());
        itemUsage.setAnswerID(dataElement.getAnswerID());

        return itemUsage;
    }

    public void writeReserved(String reservedName, String value) {
    // FIXME - unimplemented
    }

    /**
     * Takes parameter src which contains the event timing data from the
     * http request parameter EVENT_TIMINGS and creates an event timing bean for each
     * event.
     *
     * @param eventStrng
     * @return
     */
    public void processEvents(String eventString) {
        if (eventString == null || eventString.trim().length() == 0) {
            return;
        }
        if (initialized == false) {
            return;
        }

        StringTokenizer st = new StringTokenizer(eventString, "\t", false);
        int tokenCount = st.countTokens();
        for (int count = 1; st.hasMoreTokens(); ++count) {
            PageUsageEvent pageUsageEvent = tokenizeEventString(st.nextToken());
            pageUsageEvents.add(pageUsageEvent);
            if (count == 1) {
                setLoadDuration(pageUsageEvent.getDuration());
            }
            if (count == tokenCount) {
                setPageDuration(pageUsageEvent.getDuration() - getLoadDuration());
            }
        }
    }

    /**
    Parses a single line from Event Timings, storing them within an PageUsageEvent
     * @param src
     * @return
     */
    private PageUsageEvent tokenizeEventString(String src) {
        PageUsageEvent pageUsageEvent = new PageUsageEvent();

        StringTokenizer str = new StringTokenizer(src, ",", false);
        int tokenCount = 0;
        while (str.hasMoreTokens()) {
            String token = str.nextToken();
            switch (tokenCount) {
                case 0: {
                    pageUsageEvent.setVarName(token);
                    break;
                }
                case 1: {
                    pageUsageEvent.setGuiActionType(token);
                    break;
                }
                case 2: {
                    pageUsageEvent.setEventType(token);
                    break;
                }
                case 3: {
                    long ts = new Long(token).longValue();
                    Date d = new Date(ts);
                    Timestamp tms = new Timestamp(d.getTime());
                    pageUsageEvent.setTimeStamp(tms);
                    break;
                }
                case 4: {
                    pageUsageEvent.setDuration(new Integer(token).intValue());
                    break;
                }
                case 5: {
                    pageUsageEvent.setValue1(InputEncoder.encode(token));
                    break;
                }
                case 6: {
                    StringBuffer sb2 = new StringBuffer(token);
                    // remaining contents may contain commas, and thus be incorrectly treated as tokens
                    // so, merge remaining contents into a single value
                    while (str.hasMoreTokens()) {
                        sb2.append(",").append((String) str.nextToken());
                    }
                    token = sb2.toString();

                    pageUsageEvent.setValue2(InputEncoder.encode(token));
                    break;
                }
                default: {
                    logger.log(Level.SEVERE,"Should never get here, but got '" + token + "'");
                    break;
                }
            }

            tokenCount++;
        }
        if (pageUsageEvent.getValue1() == null) {
            pageUsageEvent.setValue1("");
        }
        if (pageUsageEvent.getValue2() == null) {
            pageUsageEvent.setValue2("");
        }
        if (pageUsageEvent.getGuiActionType() == null) {
            pageUsageEvent.setGuiActionType("");
        }
        if (pageUsageEvent.getEventType() == null) {
            pageUsageEvent.setEventType("");
        }
        pageUsageEvent.setPageUsageID(pageUsage);
        pageUsageEvent.setPageUsageEventSequence(++pageUsageEventCounter);

        
        return pageUsageEvent;
    }

    public void setLastAction(String lastAction) {
        if (!initialized) {
            return;
        }
        instrumentSession.setActionTypeID(parseActionType(lastAction));  
    }

    public void setStatusMsg(String statusMsg) {
        if (!initialized) {
            return;
        }
        instrumentSession.setStatusMsg(statusMsg);
        if ("finished".equals(statusMsg)) {
            setFinished(true);
        }
    }

    private boolean isFinished() {
        // TODO - CHECK - should viewing the page without necessarily submitting the final page count as finished?
        if (finished == true ||
                instrumentSession.getMaxGroup() == instrumentSession.getNumGroups() ||
                instrumentSession.getMaxVarNum() == instrumentSession.getNumVars()) {
            return true;
        } else {
            return false;
        }
    }

    private void setFinished(boolean finished) {
        this.finished = finished;   // overrides MaxGroup & VarNum calculations -  for explicitly setting finished status
    }

    public void setToVarNum(int varNum) {
        if (!initialized) {
            return;
        }
        instrumentSession.setCurrentVarNum(varNum);
    }

    private void setTimeBeginServerProcessing(long time) {
        timeBeginServerProcessing = time;
    }

    private long getTimeBeginServerProcessing() {
        return timeBeginServerProcessing;
    }

    private void setPriorTimeEndServerProcessing(long time) {
        priorTimeEndServerProcessing = time;
    }

    private long getPriorTimeEndServerProcessing() {
        return priorTimeEndServerProcessing;
    }

    private void setTimeEndServerProcessing(long time) {
        timeEndServerProcessing = time;
    }

    private long getTimeEndServerProcessing() {
        return timeEndServerProcessing;
    }

    private void setNetworkDuration(int time) {
        networkDuration = time;
    }

    private int getNetworkDuration() {
        return networkDuration;
    }

    private int getTotalDuration() {
        return totalDuration;
    }

    private void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    private int getLoadDuration() {
        return loadDuration;
    }

    private void setLoadDuration(int loadDuration) {
        this.loadDuration = loadDuration;
    }

    private void setServerDuration(int serverDuration) {
        this.serverDuration = serverDuration;
    }

    private int getServerDuration() {
        return serverDuration;
    }

    private int getPageDuration() {
        return pageDuration;
    }

    private void setPageDuration(int pageDuration) {
        this.pageDuration = pageDuration;
    }

    public void setLangCode(String langCode) {
        if (!initialized) {
            return;
        }
        if (langCode == null) {
            langCode = "en";
        } else if (langCode.length() > 2) {
            langCode = langCode.substring(0, 2);
        }
        instrumentSession.setLanguageCode(langCode);
    }

    public String getLangCode() {
        if (!initialized) {
            return "en";
        } else {
            return instrumentSession.getLanguageCode();
        }
    }

    private void lookupDialogixEntitiesFacadeLocal() {
        try {
            Context c = new InitialContext();
            dialogixEntitiesFacade = (DialogixEntitiesFacadeLocal) c.lookup("java:comp/env/DialogixEntitiesFacade_ejbref");
            init();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"", e);
        }
    }
    
    private void init() {
        if (initialized) {
            return;
        }
        Iterator<ActionType> actionTypeIterator = dialogixEntitiesFacade.getActionTypes().iterator();
        actionTypeHash = new HashMap<String,ActionType>();
        while (actionTypeIterator.hasNext()) {
            ActionType actionType = actionTypeIterator.next();
            actionTypeHash.put(actionType.getActionName(), actionType);
        }
        
        Iterator<NullFlavor> nullFlavorIterator = dialogixEntitiesFacade.getNullFlavors().iterator();
        nullFlavorHash = new HashMap<String,NullFlavor>();
        while (nullFlavorIterator.hasNext()) {
            NullFlavor nullFlavor = nullFlavorIterator.next();
            nullFlavorHash.put(nullFlavor.getNullFlavor(), nullFlavor);
        }        
    }
    
    private ActionType parseActionType(String token) {
        if (actionTypeHash.containsKey(token)) {
            return actionTypeHash.get(token);
        }
        else {
            return null;
        }
    }
    
    private Integer parseNullFlavor(String token) {
        if (nullFlavorHash.containsKey(token)) {
            return nullFlavorHash.get(token).getNullFlavorID();
        }
        else {
            return null;
        }
    }
}
