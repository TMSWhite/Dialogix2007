package org.dianexus.triceps;

import java.sql.Timestamp; // FIXME - shouldn't we be moving away from sql Timestamps?
import java.util.*;
import javax.persistence.*;

import javax.persistence.Query;
import org.dialogix.entities.*;
import org.dianexus.triceps.modules.data.InstrumentSessionDataJPA;
import org.apache.log4j.Logger;

/**
This class consolidates all of the timing functionality, including processing events, and determining response times
 */
public class DialogixTimingCalculator {

    static Logger logger = Logger.getLogger(DialogixTimingCalculator.class);
    private int displayCount = 0;
    private int FromGroupNum = 0;
    private int ToGroupNum = 0;
    private String lastAction = "";
    private String statusMsg = "";
    private int startingStep;
    private String instrumentTitle = "";
    private String major_version = "";
    private String minor_version = "";
    private boolean initialized = false;
    private long priorTimeEndServerProcessing;
    private long timeBeginServerProcessing;
    private long timeEndServerProcessing;
    private int networkDuration;
    private int serverDuration;
    private String langCode;
    private Instrument instrument = null;
    private InstrumentVersion instrumentVersion = null;
    private InstrumentSession instrumentSession = null;
    private PageUsage pageUsage = null;
    private ItemUsage itemUsage = null;
    private DataElement dataElement = null;
    private User user = null;
    private ArrayList<ItemUsage> itemUsages = null;
    private ArrayList<PageUsage> pageUsages = null;    
    private ArrayList<PageUsageEvent> pageUsageEvents = null;
    private InstrumentSessionDataJPA instrumentSessionData = null; 
    private ArrayList<DataElement> dataElements = null;
    private HashMap<String,DataElement> dataElementHash = null;
    private String instrumentSessionFileName = null;

    /**
    Empty constructor to avoid NullPointerException
     */
    public DialogixTimingCalculator() {
        initialized = false;
    }

    /**
    Constructor.  This loads the proper instrument from the database (based upon title, and version).
    Initializes the session.
    @param instrumentTitle	The title of the instrument
    @param major_version Major version
    @param minor_version Minor version
    @param userID User ID - not currently used
    @param startingStep	The starting step (first group)
     */
    public DialogixTimingCalculator(String instrumentTitle, String major_version, String minor_version, int userID, int startingStep, String filename) {
        try {
            beginServerProcessing(System.currentTimeMillis());
            
            setStatusMsg("init");
            setLastAction("START");
            setFromGroupNum(startingStep);
            setToGroupNum(startingStep);
            setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());

            this.major_version = major_version;
            this.minor_version = minor_version;
            this.instrumentTitle = instrumentTitle;
            this.user = DialogixConstants.getDefaultUserID();    //FIXME;
            this.startingStep = startingStep;
            this.instrumentSessionFileName = filename;

            //	handle error if versions not found
            if (major_version == null) {
                major_version = "1";
            }
            if (minor_version == null) {
                minor_version = "1";
            }

           instrumentVersion = getInstrumentVersion(instrumentTitle, major_version, minor_version);
           if (instrumentVersion == null) {
                // George - todo cannot continue if true
               throw new Exception("Unable to find Instrument " + instrumentTitle + "(" + major_version + "." + minor_version + ")");
            }
            instrument = instrumentVersion.getInstrumentID();
            String instrumentTableName = "Inst_ver_" + instrumentVersion.getInstrumentVersionID();
            logger.info("table name is: " + instrumentTableName);

            // Create placeholders for PageUsages
            pageUsages = new ArrayList<PageUsage>();
            
            // Create InstrumentSession Bean - as side effect, sets all startup values (which may be inappropriate)
            instrumentSession = new InstrumentSession();
            instrumentSession.setInstrumentVersionID(instrumentVersion);
            instrumentSession.setCurrentGroup(startingStep);
            instrumentSession.setDisplayNum(getDisplayCount());
            instrumentSession.setInstrumentStartingGroup(startingStep);
            instrumentSession.setLanguageCode("en");
            instrumentSession.setStatusMsg("init");
            instrumentSession.setStartTime(new Timestamp(System.currentTimeMillis()));
            instrumentSession.setLastAccessTime(instrumentSession.getStartTime());
            instrumentSession.setActionTypeID(DialogixConstants.parseActionType("START"));
            instrumentSession.setUserID(DialogixConstants.getDefaultUserID());  // FIXME
            instrumentSession.setInstrumentID(instrument);
            instrumentSession.setInstrumentVersionID(instrumentVersion);
            instrumentSession.setInstrumentSessionFileName(getInstrumentSessionFileName());
            instrumentSession.setPageUsageCollection(pageUsages);
            
            itemUsages = new ArrayList<ItemUsage>();
            instrumentSession.setItemUsageCollection(itemUsages);
            
            // Create the collection of DataElements
            dataElements = new ArrayList<DataElement>();
            dataElementHash = new HashMap<String,DataElement>();            
            Iterator<InstrumentContent> iterator = instrumentVersion.getInstrumentContentCollection().iterator();
            while (iterator.hasNext()) {
                InstrumentContent instrumentContent = iterator.next();
                dataElement = new DataElement();
                dataElement.setInstrumentContentID(instrumentContent);
                dataElement.setInstrumentSessionID(instrumentSession);
                dataElement.setItemVacillation(-1); // will be incremented again (setting it to 0) with fist call to writeNode()
                dataElement.setLanguageCode(getLangCode());
                dataElement.setNullFlavorID(DialogixConstants.parseNullFlavor("*UNASKED*"));
                dataElement.setDisplayNum(0);
                dataElements.add(dataElement);
                dataElementHash.put(instrumentContent.getVarNameID().getVarName(), dataElement);
            }
            instrumentSession.setDataElementCollection(dataElements);

            DialogixConstants.persist(instrumentSession);   // create new row for InstrumentSession, and one new row for each new DataElement
            
            // Initialize the horizontal table
            instrumentSessionData = new InstrumentSessionDataJPA();
            instrumentSessionData.setInstrumentStartingGroup(startingStep);
            instrumentSessionData.setSessionStartTime(new Timestamp(System.currentTimeMillis()));
            instrumentSessionData.setSessionLastAccessTime(new Timestamp(System.currentTimeMillis()));
            instrumentSessionData.setLastAction(getLastAction());
            instrumentSessionData.setCurrentGroup(getToGroupNum());
            instrumentSessionData.setStatusMsg(getStatusMsg());
            instrumentSessionData.setSessionId(instrumentSession.getInstrumentSessionID());
            instrumentSessionData.setTableName(instrumentTableName);
            instrumentSessionData.persist(); // create new row at this point
            
            initialized = true;
        } catch (Exception e) {
            logger.error("", e);
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
        try {
            setTimeBeginServerProcessing(System.currentTimeMillis());
            incrementDisplayCount();
            pageUsage = new PageUsage();
            pageUsageEvents = new ArrayList<PageUsageEvent>();
            pageUsage.setPageUsageEventCollection(pageUsageEvents);
        } catch (Exception e) {
            logger.error("beginServerProcessing", e);
        }
    }

    /**
    This is called when the server is ready to return a response to the user.
    ServerProcessingTime = (finishServerProcessing.timestamp - beginServerProcessing.timestamp)
    @param timestamp	Current system time
     */
    public void finishServerProcessing(Long timestamp) {
        try {
            if (initialized == false) {
                logger.info("DialogixTimingCalculator not yet initialized");
                return;
            }

            // Update Horizontal Table
            instrumentSessionData.setCurrentGroup(getToGroupNum());
            instrumentSessionData.setSessionLastAccessTime(new Timestamp(timestamp.longValue()));
            instrumentSessionData.setDisplayNum(getDisplayCount());
            instrumentSessionData.setLangCode(getLangCode());
            instrumentSessionData.setLastAction(getLastAction());
            instrumentSessionData.setStatusMsg(getStatusMsg());
            instrumentSessionData.update();
            
             // Update Session State
            instrumentSession.setLastAccessTime(new Timestamp(timestamp.longValue()));
            instrumentSession.setCurrentGroup(getToGroupNum());
            instrumentSession.setActionTypeID(getLastActionTypeID());   
            instrumentSession.setDisplayNum(getDisplayCount());
            instrumentSession.setLanguageCode(getLangCode());
            instrumentSession.setStatusMsg(getStatusMsg());

            // Add information about this page-worth of usage
            pageUsage.setDisplayNum(getDisplayCount());
            pageUsage.setLanguageCode(getLangCode());
            pageUsage.setToGroupNum(getToGroupNum());
            pageUsage.setActionTypeID(DialogixConstants.parseActionType(getLastAction()));  // CHECK - is this right, or does it come from definition?
            pageUsage.setStatusMsg(getStatusMsg());
            pageUsage.setInstrumentSessionID(instrumentSession);
            pageUsage.setPageVacillation(null); // FIXME - do I need to create a GroupNum hash reflecting each page, and increment hit counters within it?
            pageUsage.setTimeStamp(instrumentSessionData.getSessionLastAccessTime());
//            pageUsage.setPageUsageEventCollection(pageUsageEvents);

            if (pageUsageEvents.size() > 0) {
                pageUsage.setLoadDuration(pageUsageEvents.get(0).getDuration());
                pageUsage.setTotalDuration(pageUsageEvents.get(pageUsageEvents.size()-1).getDuration());
            } else {
                pageUsage.setLoadDuration(new Integer(-1));
                pageUsage.setTotalDuration(new Integer(-1));
            }

            setTimeEndServerProcessing(System.currentTimeMillis()); // CHECK - do we use this, or timestamp?
            setServerDuration((int) (getTimeEndServerProcessing() - getTimeBeginServerProcessing()));
            setNetworkDuration((int) (getTimeBeginServerProcessing() - getPriorTimeEndServerProcessing()) -
                pageUsage.getLoadDuration() -
                pageUsage.getTotalDuration());

            pageUsage.setServerDuration(getServerDuration());
            pageUsage.setNetworkDuration(getNetworkDuration());

            pageUsages.add(pageUsage);  // CHECK - should this be cleared after each merge()?

            // Finally, update GroupNum to reflect where should land
            setFromGroupNum(getToGroupNum());
            setPriorTimeEndServerProcessing(getTimeEndServerProcessing());

            instrumentSession.setItemUsageCollection(itemUsages);
            
            DialogixConstants.merge(instrumentSession);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    private InstrumentContent findInstrumentContentID(String token) {
        try {
            VarName varName = DialogixConstants.parseVarName(token);
            Iterator<InstrumentContent> iterator = instrumentVersion.getInstrumentContentCollection().iterator();
            while (iterator.hasNext()) {
                InstrumentContent instrumentContent = iterator.next();
                if (instrumentContent.getVarNameID().equals(varName)) {
                    return instrumentContent;
                }
            }
        } catch (Exception e) {
            logger.error("Unable to find InstrumentContent for " + token, e);
        }
        return DialogixConstants.getDefaultInstrumentContent();        
    }
    
    private Integer findAnswerID(DataElement dataElement, String encodedAnswer) {
        try {
            AnswerList answerList = dataElement.getInstrumentContentID().getItemID().getAnswerListID();
            if (answerList == null) {
                return null;
            }
            Iterator<AnswerListContent> iterator = answerList.getAnswerListContentCollection().iterator();
            while (iterator.hasNext()) {
                AnswerListContent answerListContent = iterator.next();
                if (answerListContent.getValue().equals(encodedAnswer)) {
                    return answerListContent.getAnswerID().getAnswerID();
                }
            }
        } catch (Exception e) {
            logger.error("Unable to find AnswerID for " + encodedAnswer, e);
        }
        return null;
    }
    
    private Integer findNullFlavor(Datum ans) {
        if (ans.isSpecial()) {
            return new Integer(ans.type());
        }
        else {
            return new Integer(0);
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
                // Update in-memory (and persisted) data store
                String encodedAnswer = InputEncoder.encode(ans.stringVal(true));
                String encodedQuestion = InputEncoder.encode(ques.getQuestionAsAsked());
                String varNameString = ques.getLocalName();
                Timestamp timestamp = new Timestamp(ques.getTimeStamp().getTime());
                
                dataElement = dataElementHash.get(varNameString);
                if (dataElement == null) {
                    logger.error("Attempt to write to unitialized DataElement " + varNameString);
                    return;
                }
                dataElement.setAnswerID(findAnswerID(dataElement, encodedAnswer));
                dataElement.setAnswerString(encodedAnswer);
                dataElement.setComments(ques.getComment());
                dataElement.setItemVacillation(dataElement.getItemVacillation() + 1);
                dataElement.setLanguageCode(getLangCode());
                dataElement.setNullFlavorID(findNullFlavor(ans));
                dataElement.setQuestionAsAsked(encodedQuestion);
                dataElement.setResponseDuration(null);  //FIXME
                dataElement.setResponseLatency(null);   //FIXME
                dataElement.setTimeStamp(timestamp);
                dataElement.setDisplayNum(getDisplayCount());
                
                // Update Horizontal Table
                instrumentSessionData.updateColumnValue(varNameString,encodedAnswer);                

                // Update log-file of changed values
                itemUsage = new ItemUsage();
                itemUsage.setAnswerString(encodedAnswer);
                itemUsage.setAnswerID(dataElement.getAnswerID());    
                itemUsage.setComments(ques.getComment());
                itemUsage.setDisplayNum(getDisplayCount());
                itemUsage.setGroupNum(getFromGroupNum());
                itemUsage.setLanguageCode(getLangCode());
                itemUsage.setQuestionAsAsked(encodedQuestion);
                itemUsage.setTimeStamp(timestamp);
                itemUsage.setVarNameID(dataElement.getInstrumentContentID().getVarNameID());
                itemUsage.setInstrumentContentID(dataElement.getInstrumentContentID());
                itemUsage.setWhenAsMS(ques.getTimeStamp().getTime()); // This duplicates timestamp - which will be easier to use?
                itemUsage.setInstrumentSessionID(instrumentSession);
                itemUsages.add(itemUsage);  // CHECK - should we add additional values to this, or clear it and re-set it each time?

            }
        } catch (Exception e) {
            logger.error("WriteNode Error", e);
        }
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
        if (eventString == null || eventString.trim().length() == 0)
            return;
        if (initialized == false)
            return;

        StringTokenizer st = new StringTokenizer(eventString, "\t", false);
        while (st.hasMoreTokens()) {
            pageUsageEvents.add(tokenizeEventString(st.nextToken()));
        }
    }

	/**
		Parses a single line from Event Timings, storing them within an PageUsageEvent
	 * @param src
	 * @return
	 */
	private PageUsageEvent tokenizeEventString(String src){
        PageUsageEvent pageUsageEvent = new PageUsageEvent();

        pageUsageEvent.setPageUsageID(pageUsage);

		StringTokenizer str = new StringTokenizer(src,",",false);
		int tokenCount=0;
		while (str.hasMoreTokens()){
			String token = str.nextToken();
			switch (tokenCount){
			case 0:{
				pageUsageEvent.setVarNameID(DialogixConstants.parseVarName(token));
				break;
			}
			case 1:{
				pageUsageEvent.setGuiActionType(token);
				break;
			}
			case 2:{
				pageUsageEvent.setEventType(token);
				break;
			}
			case 3:{
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
			case 5:{
				pageUsageEvent.setValue1(InputEncoder.encode(token));
				break;
			}
			case 6:{
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
			default:{
				logger.error("Should never get here, but got '" + token + "'");
				break;
			}
			}
			
			tokenCount++;
		}
        if (pageUsageEvent.getValue1()== null) {
            pageUsageEvent.setValue1("");
        }
        if (pageUsageEvent.getValue2()== null) {
            pageUsageEvent.setValue2("");
        }
		return pageUsageEvent;
	}

    public InstrumentVersion getInstrumentVersion(String name, String major, String minor) {
        EntityManager em = DialogixConstants.getEntityManager();
        InstrumentVersion _instrumentVersion = null;
        try {
            Query query = em.createQuery("SELECT iv FROM InstrumentVersion AS iv JOIN iv.instrumentID as i WHERE i.instrumentName = :title AND iv.versionString = :versionString");
            String version = major.concat(".").concat(minor);
            query.setParameter("versionString", version);
            query.setParameter("title", name);
            try {
                _instrumentVersion = (InstrumentVersion) query.getSingleResult();
            } catch (NoResultException e) {
                logger.error("Unable to find instrument " + name + "(" + major + "." + minor + ")");
            }
            if (_instrumentVersion == null) {
                return null;
            }
        } catch (Exception e) {
            logger.error("Unable to find instrument " + name + "(" + major + "." + minor + ")", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
        return _instrumentVersion;
    }

    public InstrumentSession getInstrumentSession() {
        return instrumentSession;
    }

    public void setInstrumentSession(InstrumentSession instrumentSession) {
        this.instrumentSession = instrumentSession;
    }

    public void incrementDisplayCount() {
        setDisplayCount(getDisplayCount() + 1);
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getLastAction() {
        return lastAction;
    }
    
    public ActionType getLastActionTypeID() {
        if (lastAction == null) {
            return DialogixConstants.parseActionType("START");
        }
        else {
            return DialogixConstants.parseActionType(lastAction);
        }
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public int getFromGroupNum() {
        return FromGroupNum;
    }

    public void setFromGroupNum(int groupNum) {
        FromGroupNum = groupNum;
    }

    public int getToGroupNum() {
        return ToGroupNum;
    }

    public void setToGroupNum(int groupNum) {
        ToGroupNum = groupNum;
    }

    public void setTimeBeginServerProcessing(long time) {
        timeBeginServerProcessing = time;
    }

    public long getTimeBeginServerProcessing() {
        return timeBeginServerProcessing;
    }

    public void setPriorTimeEndServerProcessing(long time) {
        priorTimeEndServerProcessing = time;
    }

    public long getPriorTimeEndServerProcessing() {
        return priorTimeEndServerProcessing;
    }

    public void setTimeEndServerProcessing(long time) {
        timeEndServerProcessing = time;
    }

    public long getTimeEndServerProcessing() {
        return timeEndServerProcessing;
    }

    public void setNetworkDuration(int time) {
        networkDuration = time;
    }

    public int getNetworkDuration() {
        return networkDuration;
    }

    public void setServerDuration(int serverDuration) {
        this.serverDuration = serverDuration;
    }

    public int getServerDuration() {
        return serverDuration;
    }

    public void setLangCode(String langCode) {
        if (langCode.length() > 2) {
            this.langCode = langCode.substring(0,2);
        }
        else {
            this.langCode = langCode;
        }
    }

    public String getLangCode() {
        if (langCode == null) {
            return "";
        } else {
            return langCode;
        }
    }
    
    public int getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(int displayCount) {
        this.displayCount = displayCount;
    }

    public int getStartingStep() {
        return startingStep;
    }

    public void setStartingStep(int startingStep) {
        this.startingStep = startingStep;
    }
    
    public void setInstrumentSessionFileName(String filename) {
        this.instrumentSessionFileName = filename;
    }
    
    public String getInstrumentSessionFileName() {
        return this.instrumentSessionFileName;
    }
}