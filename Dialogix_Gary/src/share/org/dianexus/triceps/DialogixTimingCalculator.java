package org.dianexus.triceps;

import java.sql.Timestamp; // FIXME - shouldn't we be moving away from sql Timestamps?
import java.util.*;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.*;

import javax.persistence.Query;
import org.dialogix.entities.*;
import org.dianexus.triceps.modules.data.InstrumentSessionDataDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDataJPA;
import org.apache.log4j.Logger;

/**
This class consolidates all of the timing functionality, including processing events, and determining response times
// FIXME - it should also encapsulate all functionality within EventTimingBean, UsageHitBean
// CHECK - QuestionTimingBean and EventAggregate seem unused at present
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
    private ItemUsage itemUsage = null;
    private DataElement dataElement = null;
    private User user = null;
    private Collection<ItemUsage> itemUsages = new ArrayList<ItemUsage>();
    private UsageHitBean phb = null; // FIXME seems to be needed, other than embedded persistence methods
    private InstrumentSessionDataDAO isd = null; // FIXME - interface needed for horizontal tables, but can be more pared down than this
    private ArrayList<DataElement> dataElements = null;
    private HashMap<String,DataElement> dataElementHash = null;

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
    public DialogixTimingCalculator(String instrumentTitle, String major_version, String minor_version, int userID, int startingStep) {
        try {
            setStatusMsg("init");
            setLastAction("START");
            setFromGroupNum(startingStep);
            setToGroupNum(startingStep);
            setTimeBeginServerProcessing(System.currentTimeMillis());
            setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());

            this.major_version = major_version;
            this.minor_version = minor_version;
            this.instrumentTitle = instrumentTitle;
            this.user = DialogixConstants.getDefaultUserID();    //userID;
            this.startingStep = startingStep;

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

            // Create InstrumentSession Bean - as side effect, sets all startup values (which may be inappropriate)
            instrumentSession = initializeInstrumentSession();
            if (instrumentSession == null) {
                // George - todo cannot continue if true
               throw new Exception("Unable to create new session for " + instrumentTitle + "(" + major_version + "." + minor_version + ")");                
            }

            isd = getInstrumentSessionDataDAO();
            isd.setInstrumentStartingGroup(startingStep);
            isd.setSessionStartTime(new Timestamp(System.currentTimeMillis()));
            isd.setSessionLastAccessTime(new Timestamp(System.currentTimeMillis()));
            isd.setLastAction(getLastAction());
            isd.setCurrentGroup(getToGroupNum());
            isd.setStatusMsg(getStatusMsg());
            isd.setSessionId(instrumentSession.getInstrumentSessionID());
            isd.setInstrumentSessionDataDAO(instrumentTableName); // create new row at this point
            
            dataElements = new ArrayList<DataElement>();
            dataElementHash = new HashMap<String,DataElement>();
            
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

            // FIXME - should processPageEvents() be encapsulated within DialogixTimingCalculator, rather than UsageHitBean?
            // FIXME - PHB doesn't do much other than processing Events and storing one row per new event (which should be here)
            getPhb().processPageEvents();	// does this deal with cross-page information?
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
            isd.setCurrentGroup(getToGroupNum());
            isd.setSessionLastAccessTime(new Timestamp(timestamp.longValue()));
            isd.setDisplayNum(getDisplayCount());
            isd.setLangCode(getLangCode());
            isd.setLastAction(getLastAction());
            isd.setStatusMsg(getStatusMsg());
            isd.update();
            
             // Update Session State
            instrumentSession.setLastAccessTime(new Timestamp(timestamp.longValue()));
            instrumentSession.setCurrentGroup(getToGroupNum());
            instrumentSession.setActionTypeID(getLastActionTypeID());   
            instrumentSession.setDisplayNum(getDisplayCount());
            instrumentSession.setLanguageCode(getLangCode());
            instrumentSession.setStatusMsg(getStatusMsg());

            // Add information about this page-worth of usage
            // FIXME - extract all UsageHitBean functionality to this class?
            UsageHitBean phb = getPhb();
            phb.setDisplayNum(getDisplayCount());
            phb.setLangCode(getLangCode());
            phb.setToGroupNum(getToGroupNum());
            phb.setLastAction(getLastAction());
            phb.setStatusMsg(getStatusMsg());
            phb.setInstrumentSessionId(instrumentSession.getInstrumentSessionID()); /// FIXME - this may empty initially
            // totalDuration?
            // serverDuration?
            // loadDuration?
            // networkDuratin?
            // pageVacillation?
            setTimeEndServerProcessing(System.currentTimeMillis());
            setServerDuration((int) (getTimeEndServerProcessing() - getTimeBeginServerProcessing()));
            setNetworkDuration((int) (getTimeBeginServerProcessing() - getPriorTimeEndServerProcessing()) -
                phb.getLoadDuration() -
                phb.getTotalDuration());
            phb.setServerDuration(getServerDuration());
            phb.setNetworkDuration(getNetworkDuration());
            phb.store();    // CHECK this sets the PageUsage variables
            // FIXME - Persist whole stucture here
            // Finally, update GroupNum to reflect where should land
            // Put information about server processing time here too?
            setFromGroupNum(getToGroupNum());
            setPriorTimeEndServerProcessing(getTimeEndServerProcessing());

            // Persist Everthing -------------------------------
            instrumentSession.setItemUsageCollection(itemUsages);
            instrumentSession.setDataElementCollection(dataElements);
            
            DialogixConstants.merge(instrumentSession);
            // Persist Everything Done! ------------------------
            
            setFromGroupNum(getToGroupNum());
            setPriorTimeEndServerProcessing(getTimeEndServerProcessing());
        } catch (Exception e) {
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
                // Queue data to be saved to horizontal table
                dataElement = dataElementHash.get(ques.getLocalName());
                if (dataElement == null) {
                    dataElement = new DataElement();
                    dataElement.setInstrumentContentID(DialogixConstants.getDefaultInstrumentContent());
                    dataElement.setInstrumentSessionID(instrumentSession);
                    dataElements.add(dataElement);
                    dataElementHash.put(ques.getLocalName(),dataElement);
                }
                dataElement.setAnswerID(null);
                dataElement.setAnswerString(InputEncoder.encode(ans.stringVal(true)));
                dataElement.setComments(ques.getComment());
                dataElement.setItemVacillation(1);  // FIXME
                dataElement.setLanguageCode(getLangCode());
                dataElement.setNullFlavorID(0); // FIXME
                dataElement.setQuestionAsAsked(InputEncoder.encode(ques.getQuestionAsAsked()));
                dataElement.setResponseDuration(null);
                dataElement.setResponseLatency(null);
                dataElement.setTimeStamp(new Timestamp(ques.getTimeStamp().getTime()));

                
                itemUsage = new ItemUsage();
                itemUsage.setAnswerString(InputEncoder.encode(ans.stringVal(true)));
                itemUsage.setAnswerID(null);    // FIXME - will only be true if there is an AnswerID from an enumerated list
                itemUsage.setComments(ques.getComment());
                itemUsage.setDisplayNum(getDisplayCount());
                itemUsage.setGroupNum(getFromGroupNum());
                itemUsage.setLanguageCode(getLangCode());
                itemUsage.setQuestionAsAsked(InputEncoder.encode(ques.getQuestionAsAsked()));
                itemUsage.setTimeStamp(new Timestamp(ques.getTimeStamp().getTime()));
                itemUsage.setVarNameID(DialogixConstants.parseVarName(ques.getLocalName()));    // FIXME, this should be loaded from InstrumentVersion
                itemUsage.setInstrumentContentID(DialogixConstants.getDefaultInstrumentContent());    // FIXME 
                itemUsage.setWhenAsMS(ques.getTimeStamp().getTime()); // This duplicates timestamp - which will be easier to use?
                itemUsage.setInstrumentSessionID(instrumentSession);
                itemUsages.add(itemUsage);  // CHECK - should we add additional values to this, or clear it and re-set it each time?
            }
        } catch (Exception e) {
            logger.error("WriteNode Error", e);
        }
    }

    /**
    This calculates item-specific timing information within a page, including responseLatency, responseDuration, and itemVacillation
    @param eventString	The full list of events.  UsageHitBean processes through them all
     */
    public void processEvents(String eventString) {
        /*
        try {
        logger.debug("In TTC processEvents string is " + eventString);
        if (eventString != null) {
        setPhb(new PageHitBean());
        logger.debug("got new phb");
        // parse the raw timing data string
        // FIXME - move processEvents() functionality into this class so can remove PageHitBean?
        getPhb().parseSource(eventString);
        logger.debug("In TTC processEvents parsing source");
        // extract the events and write to pageHitEvents table
        // set variables for page hit level timing
        }
        } catch (Exception e) {
        logger.error("", e);
        }
         */
    }

    // FIXME -
    // (1) Create new InstrumentSession if one doesn't exist, populating it with defaults

    private InstrumentSession initializeInstrumentSession() {
        try {
            instrumentSession = new InstrumentSession();
            instrumentSession.setInstrumentVersionID(instrumentVersion);
            instrumentSession.setCurrentGroup(startingStep);
            instrumentSession.setDisplayNum(getDisplayCount());
            instrumentSession.setInstrumentStartingGroup(startingStep);
            instrumentSession.setLanguageCode("en");
            instrumentSession.setStatusMsg("init");
            instrumentSession.setStartTime(new Timestamp(System.currentTimeMillis()));
            instrumentSession.setLastAccessTime(new Timestamp(System.currentTimeMillis()));
            
            instrumentSession.setActionTypeID(DialogixConstants.parseActionType("START"));
            instrumentSession.setUserID(DialogixConstants.getDefaultUserID());
            instrumentSession.setInstrumentID(instrument);
            instrumentSession.setInstrumentVersionID(instrumentVersion);

            DialogixConstants.persist(instrumentSession);
            return instrumentSession;
        } catch (Exception e) {
            logger.error("initializeInstrumentSession", e);
            return null;
        }
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
            em.close();
        }
        return _instrumentVersion;
    }

    public InstrumentSessionDataDAO getInstrumentSessionDataDAO() {
        return new InstrumentSessionDataJPA();
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
    
    public UsageHitBean getPhb() {
        if (phb == null) {
            setPhb(new UsageHitBean());
        }
        return phb;
    }

    public void setPhb(UsageHitBean phb) {
        this.phb = phb;
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

}