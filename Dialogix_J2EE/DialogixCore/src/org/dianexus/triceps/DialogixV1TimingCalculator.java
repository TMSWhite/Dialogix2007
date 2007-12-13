package org.dianexus.triceps;

import java.sql.Timestamp; // FIXME - shouldn't we be moving away from sql Timestamps?
import java.util.*;
import java.security.*;


import javax.naming.Context;
import javax.naming.InitialContext;
import org.dialogix.entities.*;
import org.apache.log4j.Logger;
import org.dialogix.session.V1InstrumentSessionFacadeLocal;

/**
This class consolidates all of the timing functionality, including processing events, and determining response times
 */
public class DialogixV1TimingCalculator {
   
    private V1InstrumentSessionFacadeLocal v1InstrumentSessionFacade;    
    static Logger logger = Logger.getLogger(DialogixV1TimingCalculator.class);
    private boolean initialized = false;
    private long priorTimeEndServerProcessing;
    private long timeBeginServerProcessing;
    private long timeEndServerProcessing;
    private int networkDuration;
    private int serverDuration;
    private int loadDuration;
    private int pageDuration;
    private int totalDuration;
    private boolean finished = false;
    private V1InstrumentSession v1InstrumentSession = null;
    private ArrayList<V1ItemUsage> v1ItemUsages = null; // exclusively to update certain values just before merge
    private ArrayList<V1DataElement> v1DataElements = null; // exclusively to update certain values just before merge
    private HashMap<String, V1DataElement> v1DataElementHash = null;
    private int v1ItemUsageCounter = 0;
    // groupNum and withinBlock are only used to compute GroupNum
    private int groupNum = 0;
    private boolean withinBlock = false;

    /**
    Empty constructor to avoid NullPointerException
     */
    public DialogixV1TimingCalculator() {
        initialized = false;
    }
    /**
     * Restore Data_Elements from a specific file.
     */
    public DialogixV1TimingCalculator(String restoreFile) {
        try {
            beginServerProcessing(System.currentTimeMillis());
            setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());
            
            v1InstrumentSessionFacade = lookupV1InstrumentSessionFacade();            
            V1InstrumentSession restoredSession = v1InstrumentSessionFacade.findByName(restoreFile);   
            if (restoredSession == null) {
                logger.error("Unable to restore session: " + restoreFile);
                initialized = false;
            }
            v1InstrumentSession = restoredSession;

            // Re-set the HashMap
            Iterator<V1DataElement> v1DataElementIterator = v1InstrumentSession.getV1DataElementCollection().iterator();
            v1DataElementHash = new HashMap<String,V1DataElement>();
            while(v1DataElementIterator.hasNext()) {
                V1DataElement v1DataElement = v1DataElementIterator.next();
                v1DataElementHash.put(v1DataElement.getVarName(), v1DataElement);
            }

            initialized = true;
        } catch (Throwable e) {
            logger.error("", e);
            initialized = false;
        }
    }

    /**
    Constructor.  This loads the proper instrument from the database (based upon title, and version).
    Initializes the session.
    @param instrumentTitle	The title of the instrument
    @param major_version Major version
    @param minor_version Minor version
    @param filename name of file to which data is written
    @param startingStep	The starting step (first group)
    @param varNames list of  variable names
    @param actionTypes list of actionTypes - needed to determine the GroupNum
     */
    public DialogixV1TimingCalculator(String instrumentFilename, String instrumentTitle, String major_version, String minor_version, int startingStep, String filename, ArrayList<String> varNames, ArrayList<String> actionTypes) {
        try {
            StringBuffer varNameMD5source = new StringBuffer();

            beginServerProcessing(System.currentTimeMillis());
            setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());

            if (major_version == null) {
                major_version = "0";
            }
            if (minor_version == null) {
                minor_version = "0";
            }
//            // JNDI Lookup 
            v1InstrumentSessionFacade = lookupV1InstrumentSessionFacade();
            
            v1InstrumentSession = new V1InstrumentSession();
            v1InstrumentSession.setInstrumentVersionName(instrumentTitle + "(" + major_version + "." + minor_version + ")");
            v1InstrumentSession.setStartTime(new Timestamp(System.currentTimeMillis()));
            v1InstrumentSession.setLastAccessTime(v1InstrumentSession.getStartTime());
            v1InstrumentSession.setInstrumentStartingGroup(startingStep);  // FIXME - shouldn't this be based on true GroupNum, not VarNum position?
            v1InstrumentSession.setMaxVarNum(startingStep);
            v1InstrumentSession.setCurrentGroup(startingStep);
            v1InstrumentSession.setDisplayNum(-1);
            v1InstrumentSession.setLanguageCode("en");
            v1InstrumentSession.setActionType("START");
            v1InstrumentSession.setStatusMsg("init");
            v1InstrumentSession.setInstrumentVersionFileName(instrumentFilename);
            v1InstrumentSession.setInstrumentSessionFileName(filename.replace('\\', '/'));
            v1InstrumentSession.setMaxGroup(0);


            v1DataElementHash = new HashMap<String, V1DataElement>();
            ArrayList<V1DataElement> v1DataElements = new ArrayList<V1DataElement>();

            Iterator<String> iterator = varNames.iterator();
            Iterator<String> actionTypeIterator = actionTypes.iterator();
            int dataElementSequence = 0;
            boolean maxVarNumVisitedSet = false;
            V1DataElement v1DataElement = null;
            while (iterator.hasNext()) {
                String varName = iterator.next();
                v1DataElement = new V1DataElement();

                v1DataElement.setAnswerCode("*UNASKED*");
                v1DataElement.setAnswerString("*UNASKED*");
                v1DataElement.setDisplayNum(0);
                v1DataElement.setDataElementSequence(++dataElementSequence);

                if (actionTypeIterator.hasNext()) {
                    String actionType = actionTypeIterator.next();
                    v1DataElement.setGroupNum(parseGroupNum(actionType));
                } else {
                    v1DataElement.setGroupNum(-1);
                }
                v1DataElement.setItemVisits(-1);
                v1DataElement.setLanguageCode(v1InstrumentSession.getLanguageCode());
                v1DataElement.setV1InstrumentSessionID(v1InstrumentSession);
                v1DataElement.setVarName(varName);

                v1DataElement.setLoadDuration(0);
                v1DataElement.setNetworkDuration(0);
                v1DataElement.setPageDuration(0);
                v1DataElement.setServerDuration(0);
                v1DataElement.setTotalDuration(0);

                v1DataElements.add(v1DataElement);
                v1DataElementHash.put(varName, v1DataElement);

                varNameMD5source.append(varName); // for MD5 hash

                if (startingStep == (dataElementSequence - 1)) {
                    v1InstrumentSession.setMaxGroup(getGroupNum());
                }
                if (getGroupNum() == (v1InstrumentSession.getMaxGroup() + 1)) {
                    if (maxVarNumVisitedSet == false) {
                        v1InstrumentSession.setMaxVarNum(dataElementSequence - 2);   // hack to get last VarNum seen on the first page
                        maxVarNumVisitedSet = true;
                    }
                }
            }

            v1InstrumentSession.setFinished(isFinished() ? 1 : 0);
            v1InstrumentSession.setNumVars(varNames.size());
            v1InstrumentSession.setNumGroups(getGroupNum());

            try {
                MessageDigest md5 = MessageDigest.getInstance("SHA-256");   // if convert this to HexString, it is 125 characters long, so keep as Unicode?
                byte[] digest = md5.digest(varNameMD5source.toString().getBytes());
//                String message = new String(digest);
                v1InstrumentSession.setVarListMD5(convertByteArrayToHexString(digest));
            } catch (Throwable e) {
                logger.error("", e);
            }

            v1InstrumentSession.setV1ItemUsageCollection(new ArrayList<V1ItemUsage>());
            v1InstrumentSession.setV1DataElementCollection(v1DataElements);

            // George - Persist 
            v1InstrumentSessionFacade.create(v1InstrumentSession);

            initialized = true;
        } catch (Throwable e) {
            logger.error("", e);
        }
    }
    
    private String convertByteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<bytes.length;++i) {
            int val = Byte.valueOf(bytes[i]).intValue();
            if (val < 0) {
                val = Byte.MAX_VALUE * 2 + 2 + val;
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }    

    /**
    This is called when the server receives a request.  It starts the the clock for server processing time.
    Side effects include:
    (1) Increasing displayCount
    (2) Setting displayCount, groupNum, and storing that information to pageHits
    @param timestamp	System time in milliseconds
     */
    public void beginServerProcessing(Long timestamp) {
        try {
            setTimeBeginServerProcessing(System.currentTimeMillis());
            v1ItemUsages = new ArrayList<V1ItemUsage>();
            v1DataElements = new ArrayList<V1DataElement>();
        } catch (Throwable e) {
            logger.error("beginServerProcessing", e);
        }
    }

    /**
    This is called when the server is ready to return a response to the user.
    ServerProcessingTime = (finishServerProcessing.timestamp - beginServerProcessing.timestamp)
    @param timestamp	Current system time
     */
    public void finishServerProcessing(Long timestamp) {
        if (!initialized) {
            return;
        }
        try {
            int displayNum = v1InstrumentSession.getDisplayNum() + 1;
            v1InstrumentSession.setDisplayNum(displayNum);

            setTimeEndServerProcessing(System.currentTimeMillis());
            setServerDuration((int) (getTimeEndServerProcessing() - getTimeBeginServerProcessing()));
            setTotalDuration((int) (getTimeBeginServerProcessing() - getPriorTimeEndServerProcessing()));
            setNetworkDuration(getTotalDuration() - getLoadDuration() - getPageDuration() - getServerDuration());

            // Set timing infomation for all new ItemUsage
            V1ItemUsage v1ItemUsage = null;
            for (int i = 0; i < v1ItemUsages.size(); ++i) {
                v1ItemUsage = v1ItemUsages.get(i);
                v1ItemUsage.setLoadDuration(getLoadDuration());
                v1ItemUsage.setNetworkDuration(getNetworkDuration());
                v1ItemUsage.setPageDuration(getPageDuration());
                v1ItemUsage.setServerDuration(getServerDuration());
                v1ItemUsage.setTotalDuration(getTotalDuration());
                v1ItemUsage.setDisplayNum(displayNum);
            }

            // Set timing infomation for all modified DataElements - use running totals?
            V1DataElement v1DataElement = null;
            for (int i = 0; i < v1DataElements.size(); ++i) {
                v1DataElement = v1DataElements.get(i);
                v1DataElement.setLoadDuration(v1DataElement.getLoadDuration() + getLoadDuration());
                v1DataElement.setNetworkDuration(v1DataElement.getNetworkDuration() + getNetworkDuration());
                v1DataElement.setPageDuration(v1DataElement.getPageDuration() + getPageDuration());
                v1DataElement.setServerDuration(v1DataElement.getServerDuration() + getServerDuration());
                v1DataElement.setTotalDuration(v1DataElement.getTotalDuration() + getTotalDuration());
                v1DataElement.setDisplayNum(displayNum);
            }

            // Update Session State
            v1InstrumentSession.setLastAccessTime(new Timestamp(timestamp.longValue()));

            v1InstrumentSession.setFinished(isFinished() ? 1 : 0);

            // Finally, update GroupNum to reflect where should land
            setPriorTimeEndServerProcessing(getTimeEndServerProcessing());

            // George - Merge
            v1InstrumentSessionFacade.edit(v1InstrumentSession);

        } catch (Throwable e) {
            logger.error("", e);
        }
    }

    /**
    This assigns a value (Datum) to an item (Node).
    @param ques	the Item
    @param ans	the Value
     */
    public void writeNode(Node ques, Datum ans) {
        try {
            if (ques != null && ans != null) {
                String answerCode = InputEncoder.encode(ans.stringVal(true));
                String answerString = InputEncoder.encode(ques.getLocalizedAnswer(ans));
                String questionAsAsked = InputEncoder.encode(ques.getQuestionAsAsked());
                String v1VarNameString = ques.getLocalName();
                Timestamp timestamp = new Timestamp(ques.getTimeStamp().getTime());

                V1DataElement v1DataElement = v1DataElementHash.get(v1VarNameString);
                if (v1DataElement == null) {
                    logger.error("Attempt to write to unitialized V1DataElement " + v1VarNameString);
                    return;
                }

                v1DataElement.setItemVisits(v1DataElement.getItemVisits() + 1);

                if (v1DataElement.getItemVisits() == 0) {
                    return; // don't write initial *UNASKED* values 
                }

                v1DataElement.setAnswerCode(answerCode);
                v1DataElement.setAnswerString(answerString);
                v1DataElement.setComments(ques.getComment());
                v1DataElement.setDisplayNum(v1InstrumentSession.getDisplayNum());
                v1DataElement.setLanguageCode(v1InstrumentSession.getLanguageCode());
                v1DataElement.setQuestionAsAsked(questionAsAsked);
                v1DataElement.setWhenAsMS(ques.getTimeStamp().getTime());
                v1DataElement.setTimeStamp(timestamp);

                if (!v1DataElements.contains(v1DataElement)) {
                    v1DataElements.add(v1DataElement);
                }

                V1ItemUsage v1ItemUsage = cloneV1DataElement(v1DataElement);

                v1ItemUsages.add(v1ItemUsage);
                v1InstrumentSession.getV1ItemUsageCollection().add(v1ItemUsage);

                // Compute position relative to end
                if (v1DataElement.getGroupNum() > v1InstrumentSession.getMaxGroup()) {
                    v1InstrumentSession.setMaxGroup(v1DataElement.getGroupNum());
                }
                if (v1DataElement.getDataElementSequence() > v1InstrumentSession.getMaxVarNum()) {
                    v1InstrumentSession.setMaxVarNum(v1DataElement.getDataElementSequence());
                }
            }
        } catch (Throwable e) {
            logger.error("WriteNode Error", e);
        }
    }

    /**
     * Write reseved value to DataElements and ItemUsage
     * @param name the Reserved Word name
     * @param value the current value
     */
    public void writeReserved(String reservedName, String value) {
         try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (reservedName == null || reservedName.trim().length() == 0) {
                logger.error("Trying to write blank Reserved name with value " + value);
                return;
            }
            if (v1DataElementHash == null) {
                logger.error("v1DataElementHash is null");  //  FIXME - this is happening on restore - why?
                return;
            }

            V1DataElement v1DataElement = v1DataElementHash.get(reservedName);  // NullPointer on restore
            if (v1DataElement == null) {
                // add it
                v1DataElement = new V1DataElement();
                
                v1DataElement.setDisplayNum(0);
                v1DataElement.setDataElementSequence(0);   
                v1DataElement.setGroupNum(0); 
                v1DataElement.setItemVisits(-1);                
                v1DataElement.setV1InstrumentSessionID(v1InstrumentSession);
                v1DataElement.setVarName(reservedName);
                v1DataElement.setLoadDuration(0);
                v1DataElement.setNetworkDuration(0);
                v1DataElement.setPageDuration(0);
                v1DataElement.setServerDuration(0);
                v1DataElement.setTotalDuration(0);                
                
                v1InstrumentSession.getV1DataElementCollection().add(v1DataElement);    // add it to the v1InstrumentSession so updated each time
                v1DataElementHash.put(reservedName, v1DataElement);                
            }

            v1DataElement.setItemVisits(v1DataElement.getItemVisits() + 1);

            v1DataElement.setAnswerCode(null);
            v1DataElement.setAnswerString(InputEncoder.encode(value));
            v1DataElement.setComments(null);
            v1DataElement.setDisplayNum(v1InstrumentSession.getDisplayNum());
            v1DataElement.setLanguageCode(v1InstrumentSession.getLanguageCode());
            v1DataElement.setQuestionAsAsked(null);
            v1DataElement.setWhenAsMS(timestamp.getTime());
            v1DataElement.setTimeStamp(timestamp);

            if (!v1DataElements.contains(v1DataElement)) {
                v1DataElements.add(v1DataElement);  // if this is the first pass, then this RESERVED word was added to v1DataElements by adding it to the DataElementCollection above.
            }
            
            if (reservedName.equals(Schedule.RESERVED_WORDS[Schedule.STARTING_STEP]) || 
                    reservedName.equals(Schedule.RESERVED_WORDS[Schedule.DISPLAY_COUNT])) {
//                return; // we do not need to set these every pageUsage
            }         
            
            if (v1DataElement.getItemVisits() == 0) {
//                return; // don't write initial *UNASKED* values 
            }            

            V1ItemUsage v1ItemUsage = cloneV1DataElement(v1DataElement);

            v1ItemUsages.add(v1ItemUsage);
            v1InstrumentSession.getV1ItemUsageCollection().add(v1ItemUsage);

        } catch (Throwable e) {
            logger.error("WriteReserved Error", e);
        }       
    }
        
    /**
     *  Copy all content of DataElement into ItemUsage
     */
    private V1ItemUsage cloneV1DataElement(V1DataElement v1DataElement) {
        V1ItemUsage v1ItemUsage = new V1ItemUsage();

        v1ItemUsage.setAnswerCode(v1DataElement.getAnswerCode());
        v1ItemUsage.setAnswerString(v1DataElement.getAnswerString());
        v1ItemUsage.setComments(v1DataElement.getComments());
        v1ItemUsage.setDataElementSequence(v1DataElement.getDataElementSequence());
        v1ItemUsage.setDisplayNum(v1DataElement.getDisplayNum());
        v1ItemUsage.setGroupNum(v1DataElement.getGroupNum());
        v1ItemUsage.setItemUsageSequence(++v1ItemUsageCounter);
        v1ItemUsage.setItemVisits(v1DataElement.getItemVisits());
        v1ItemUsage.setLanguageCode(v1DataElement.getLanguageCode());
        v1ItemUsage.setQuestionAsAsked(v1DataElement.getQuestionAsAsked());
        v1ItemUsage.setTimeStamp(v1DataElement.getTimeStamp());
        v1ItemUsage.setV1InstrumentSessionID(v1DataElement.getV1InstrumentSessionID());
        v1ItemUsage.setVarName(v1DataElement.getVarName());
        v1ItemUsage.setWhenAsMS(v1DataElement.getWhenAsMS());

        return v1ItemUsage;
    }

    public void setLastAction(String lastAction) {
        if (!initialized) {
            return;
        }
        v1InstrumentSession.setActionType(lastAction);
    }

    public void setStatusMsg(String statusMsg) {
        if (!initialized) {
            return;
        }
        v1InstrumentSession.setStatusMsg(statusMsg);
        if ("finished".equals(statusMsg)) {
            setFinished(true);
        }
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

    private void setServerDuration(int serverDuration) {
        this.serverDuration = serverDuration;
    }

    private int getServerDuration() {
        return serverDuration;
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
        v1InstrumentSession.setLanguageCode(langCode);
    }

    public void setToGroupNum(int groupNum) {
        if (!initialized) {
            return;
        }
        v1InstrumentSession.setCurrentGroup(groupNum);
    }

    private int getLoadDuration() {
        return loadDuration;
    }

    private void setLoadDuration(int loadDuration) {
        this.loadDuration = loadDuration;
    }

    private int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    private int getPageDuration() {
        return pageDuration;
    }

    private void setPageDuration(int pageDuration) {
        this.pageDuration = pageDuration;
    }

    private boolean isFinished() {
        // TODO - CHECK - should viewing the page without necessarily submitting the final page count as finished?
        if (finished == true ||
                v1InstrumentSession.getMaxGroup() == v1InstrumentSession.getNumGroups() ||
                v1InstrumentSession.getMaxVarNum() == v1InstrumentSession.getNumVars()) {
            return true;
        } else {
            return false;
        }
    }

    private void setFinished(boolean finished) {
        this.finished = finished;   // overrides MaxGroup & VarNum calculations -  for explicitly setting finished status
    }

    private int getGroupNum() {
        return groupNum;
    }

    private void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    /**
    Determine the GroupNum based upon item grouping paremeters (actionType)
    @return groupNum
     */
    private int parseGroupNum(String actionType) {
        if (actionType == null) {
            logger.error("No actionType specified");
        }
        if (actionType.length() > 1) {
            actionType = actionType.substring(0, 1);
        }

        if (actionType.equals("[")) {
            if (withinBlock == true) {
                logger.error("Trying to create a nested group of items");
            } else {
                withinBlock = true;
                ++groupNum;
            }
        } else if (actionType.equals("]")) {
            if (withinBlock == false) {
                logger.error("Trying to close a group of items with no matching '['");
            } else {
                withinBlock = false;
            }
        } else if (actionType.equals("q")) {
            if (withinBlock == false) {
                ++groupNum;
            }
        } else if (actionType.equals("e")) {
            if (withinBlock == true) {
                logger.error("Trying to process an equation within a group of items");
            }
        }
        return groupNum;
    }

    /**
     * Takes parameter src which contains the event timing data from the
     * http request parameter EVENT_TIMINGS and creates an event timing bean for each
     * event.
     *
     * @param eventStrng
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
            String token = st.nextToken();
            if (count == 1 || count == tokenCount) {
                StringTokenizer str = new StringTokenizer(token, ",", false);
                int subtCount = 0;
                while (str.hasMoreTokens()) {
                    String subt = str.nextToken();
                    switch (subtCount) {
                        case 4:
                            if (count == 1) {
                                setLoadDuration(new Integer(subt).intValue());
                            }
                            if (count == tokenCount) {
                                setPageDuration(new Integer(subt).intValue() - getLoadDuration());
                            }
                    }
                    ++subtCount;
                }
            }
        }
    }

    private V1InstrumentSessionFacadeLocal lookupV1InstrumentSessionFacade() {
        try {
            Context c = new InitialContext();
            return (V1InstrumentSessionFacadeLocal) c.lookup("java:comp/env/V1InstrumentSession_ejbref");
            
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    }
