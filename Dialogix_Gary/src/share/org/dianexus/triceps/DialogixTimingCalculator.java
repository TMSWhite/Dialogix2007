package org.dianexus.triceps;

import java.sql.Timestamp; // FIXME - shouldn't we be moving away from sql Timestamps?
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.*;
//import org.dialogix.entities.*;
import javax.persistence.Query;
import org.dialogix.entities.Instrument;
import org.dialogix.entities.InstrumentSession;
import org.dialogix.entities.InstrumentVersion;
import org.dialogix.entities.InstrumentContent;
import org.dialogix.entities.PageUsage;
import org.dialogix.entities.DataElement;
import org.dialogix.entities.ItemUsage;
import org.dialogix.entities.ActionType;
import org.dialogix.entities.User;
import org.dialogix.entities.VarName;
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
    private int userID = 0;
    private int startingStep;
    private int instrumentID = 0;
    private int instrumentSessionID = 0;
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
    private Collection<ItemUsage> itemUsages = new ArrayList<ItemUsage>();
    private UsageHitBean phb = null; // FIXME seems to be needed, other than embedded persistence methods
    private InstrumentSessionDataDAO isd = null; // FIXME - interface needed for horizontal tables, but can be more pared down than this

    /**
    Empty constructor to avoid NullPointerException
     */
    public DialogixTimingCalculator() {
        initialized = false;
    }
    private EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DialogixDomainPU");
        }
        return emf.createEntityManager();
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
            this.setStatusMsg("init");
            this.setLastAction("init");
            this.setFromGroupNum(startingStep);
            this.setToGroupNum(startingStep);
            this.setTimeBeginServerProcessing(System.currentTimeMillis());
            this.setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());

            this.major_version = major_version;
            this.minor_version = minor_version;
            this.instrumentTitle = instrumentTitle;
            this.userID = userID;
            this.startingStep = startingStep;

            //	handle error if versions not found
            if (major_version == null) {
                major_version = "1";
            }
            if (minor_version == null) {
                minor_version = "1";
            }

           this.instrumentVersion = getInstrumentVersion(this.instrumentTitle, major_version, minor_version);
           if (this.instrumentVersion == null) {
                // George - todo cannot continue if true
               throw new Exception("Unable to find Instrument " + this.instrumentTitle + "(" + major_version + "." + minor_version + ")");
            }
           this.instrument = instrumentVersion.getInstrumentID();
            String instrumentTableName = "Inst_ver_" + instrumentVersion.getInstrumentVersionID();
            logger.info("table name is: " + instrumentTableName);

            // Create InstrumentSession Bean - as side effect, sets all startup values (which may be inappropriate)
            instrumentSession = initializeInstrumentSession();
            if (this.instrumentSession == null) {
                // George - todo cannot continue if true
               throw new Exception("Unable to create new session for " + this.instrumentTitle + "(" + major_version + "." + minor_version + ")");                
            }

            isd = getInstrumentSessionDataDAO();
            isd.setInstrumentStartingGroup(startingStep);
            isd.setSessionStartTime(new Timestamp(System.currentTimeMillis()));
            isd.setSessionLastAccessTime(new Timestamp(System.currentTimeMillis()));
            isd.setLastAction(this.getLastAction());
            isd.setCurrentGroup(this.getToGroupNum());
            isd.setStatusMsg(this.getStatusMsg());
            isd.setSessionId(instrumentSession.getInstrumentSessionID());
            isd.setInstrumentSessionDataDAO(instrumentTableName); // FIXME - create new row at this point -- should this be a merge or persist?
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
            this.setTimeBeginServerProcessing(System.currentTimeMillis());
            this.incrementDisplayCount();

            // FIXME - should processPageEvents() be encapsulated within DialogixTimingCalculator, rather than UsageHitBean?
            // FIXME - PHB doesn't do much other than processing Events and storing one row per new event (which should be here)
            this.getPhb().processPageEvents();	// does this deal with cross-page information?
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
            isd.setCurrentGroup(this.getToGroupNum());
            isd.setSessionLastAccessTime(new Timestamp(timestamp.longValue()));
            isd.setDisplayNum(this.getDisplayCount());
            isd.setLangCode(this.getLangCode());
            isd.setLastAction(this.getLastAction());
            isd.setStatusMsg(this.getStatusMsg());
            isd.update();
            
             // Update Session State
            instrumentSession.setLastAccessTime(new Timestamp(timestamp.longValue()));
            instrumentSession.setCurrentGroup(this.getToGroupNum());
            instrumentSession.setActionTypeID(null);   // FIXME setLastAction(this.getLastAction());	// FIXME - uses enumerated list?
            instrumentSession.setDisplayNum(this.getDisplayCount());
            instrumentSession.setLanguageCode(this.getLangCode());
            instrumentSession.setStatusMsg(this.getStatusMsg());

            // Add information about this page-worth of usage
            // FIXME - extract all UsageHitBean functionality to this class?
            UsageHitBean phb = this.getPhb();
            phb.setDisplayNum(this.getDisplayCount());
            phb.setLangCode(this.getLangCode());
            phb.setToGroupNum(this.getToGroupNum());
            phb.setLastAction(this.getLastAction());
            phb.setStatusMsg(this.getStatusMsg());
            phb.setInstrumentSessionId(instrumentSession.getInstrumentSessionID()); /// FIXME - this may empty initially
            // totalDuration?
            // serverDuration?
            // loadDuration?
            // networkDuratin?
            // pageVacillation?
            this.setTimeEndServerProcessing(System.currentTimeMillis());
            this.setServerDuration((int) (getTimeEndServerProcessing() - getTimeBeginServerProcessing()));
            this.setNetworkDuration((int) (getTimeBeginServerProcessing() - getPriorTimeEndServerProcessing()) -
                phb.getLoadDuration() -
                phb.getTotalDuration());
            phb.setServerDuration(this.getServerDuration());
            phb.setNetworkDuration(this.getNetworkDuration());
            phb.store();    // CHECK this sets the PageUsage variables
            // FIXME - Persist whole stucture here
            // Finally, update GroupNum to reflect where should land
            // Put information about server processing time here too?
            this.setFromGroupNum(this.getToGroupNum());
            this.setPriorTimeEndServerProcessing(getTimeEndServerProcessing());

            // Persist Everthing -------------------------------
            instrumentSession.setItemUsageCollection(itemUsages);
            merge(instrumentSession);
            // Persist Everything Done! ------------------------
            
            this.setFromGroupNum(this.getToGroupNum());
            this.setPriorTimeEndServerProcessing(getTimeEndServerProcessing());
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
    This assigns a value (Datum) to an item (Node).
    @param ques	the Item
    @param asn	the Value
     */
    public void writeNode(Node ques, Datum ans) {
        try {
            if (ques != null && ans != null) {
                // Queue data to be saved to horizontal table
                // FIXME - move this function into this class? (so that this class is facade for everything?)
                // George - comment out to test
                //isd.updateInstrumentSessionDataDAO(ques.getLocalName(), InputEncoder.encode(ans.stringVal(true)));
                // FIXME - rather than clearing a RawDataBean/DAO, just create new one and populate it.
                // FIXME - Persist whole RawData structure at end, rather than one insert per variable
                // Save data to Raw Data (ItemUsage) table
                /* George - dicuss table changes
                dataElement = new DataElement();
                dataElement.setAnswerID(userID);
                dataElement.setAnswerString(langCode);
                dataElement.setComments(langCode);
                dataElement.setItemVacillation(userID);
                dataElement.setLanguageCode(langCode);
                dataElement.setNullFlavorID(userID);
                dataElement.setQuestionAsAsked(langCode);
                dataElement.setResponseDuration(userID);
                dataElement.setResponseLatency(userID);
                dataElement.setTimeStamp(arg0);
                dataElement.setInstrumentContentID(arg0);
                dataElement.setInstrumentSessionID(instrumentSession);
                */
                itemUsage = new ItemUsage();
//                itemUsage.getInstrumentContentID();
                itemUsage.setAnswerString(InputEncoder.encode(ans.stringVal(true)));
                itemUsage.setAnswerID(null);    // FIXME - will only be true if there is an AnswerID from an enumerated list
                itemUsage.setComments(ques.getComment());
                itemUsage.setDisplayNum(this.getDisplayCount());
                itemUsage.setGroupNum(this.getFromGroupNum());
                itemUsage.setLanguageCode(this.getLangCode());
                itemUsage.setQuestionAsAsked(InputEncoder.encode(ques.getQuestionAsAsked()));
                itemUsage.setTimeStamp(new Timestamp(ques.getTimeStamp().getTime()));
                EntityManager em = getEntityManager();
                Integer Id = 1; // hack for test
                try {
                    logger.info("Lookup and set VarName");
                    VarName varName = em.find(VarName.class, Id);
                    itemUsage.setVarNameID(varName);
                    logger.info("Lookup and set InstrumentContent");
                    InstrumentContent instrumentContent = em.find(InstrumentContent.class, Id);
                    itemUsage.setInstrumentContentID(instrumentContent);
                } finally {
                    em.close();
                }
                // (ques.getLocalName()) FIXME - should look-up whether VarName exists already; and/or pull VarName_ID from loaded instrument
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
        this.setPhb(new PageHitBean());
        logger.debug("got new phb");
        // parse the raw timing data string
        // FIXME - move processEvents() functionality into this class so can remove PageHitBean?
        this.getPhb().parseSource(eventString);
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
            instrumentSession.setCurrentGroup(this.startingStep);
            instrumentSession.setDisplayNum(this.getDisplayCount());
            instrumentSession.setInstrumentStartingGroup(this.startingStep);
            instrumentSession.setLanguageCode("en");
            instrumentSession.setStatusMsg("init");
            instrumentSession.setInstrumentID(instrument);
            instrumentSession.setStartTime(new Timestamp(System.currentTimeMillis()));
            instrumentSession.setLastAccessTime(new Timestamp(System.currentTimeMillis()));
            // Lookups begin-------------
            EntityManager em = getEntityManager();
            Integer Id = 1; // hack for test
            try {
                logger.info("Lookup and set UserID");
                User user = em.find(User.class, Id);
                instrumentSession.setUserID(user);
                logger.info("Lookup and set ActionType");
                ActionType actionType = em.find(ActionType.class, Id);
                instrumentSession.setActionTypeID(actionType);
            } finally {
                em.close();
            }

            persist(instrumentSession);
            return instrumentSession;
        } catch (Exception e) {
            logger.error("initializeInstrumentSession", e);
            return null;
        }
    }

    /*
    public Instrument getInstrument(String _name) {
        EntityManager em = getEntityManager();
        //Instrument instrument = null;
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Instrument.findByInstrumentName");
            query.setParameter("instrumentName", _name);
            query.setMaxResults(1);
            instrument = (Instrument) query.getSingleResult();
            if (instrument == null) {
                return null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("getInstrument from Name", e);
            return null;
        } finally {
            em.close();
        }
        return instrument;
    }
    */

    public InstrumentVersion getInstrumentVersion(String name, String major, String minor) {
        EntityManager em = getEntityManager();
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

    private void persist(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            System.out.println("Before Persist");
            em.persist(object);
            System.out.println("After Persist");
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Persist Error - Rollback", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    private void merge(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            System.out.println("Before Merge");
            //em.persist(object);
            em.merge(object);
            System.out.println("After Merge");
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Merge Error - Rollback", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public InstrumentSession getInstrumentSession() {
        return this.instrumentSession;
    }

    public void setInstrumentSession(InstrumentSession instrumentSession) {
        this.instrumentSession = instrumentSession;
    }

    public void incrementDisplayCount() {
        this.setDisplayCount(this.getDisplayCount() + 1);
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getLastAction() {
        return this.lastAction;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getStatusMsg() {
        return this.statusMsg;
    }

    public void setInstrumentID(int instrumentID) {
        this.instrumentID = instrumentID;
    }

    public int getInstrumentID() {
        return this.instrumentID;
    }

    public void setInstrumentSessionID(int instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public int getInstrumentSessionID() {
        return this.instrumentSessionID;
    }

    public int getFromGroupNum() {
        return FromGroupNum;
    }

    public void setFromGroupNum(int groupNum) {
        this.FromGroupNum = groupNum;
    }

    public int getToGroupNum() {
        return ToGroupNum;
    }

    public void setToGroupNum(int groupNum) {
        this.ToGroupNum = groupNum;
    }

    public void setTimeBeginServerProcessing(long time) {
        this.timeBeginServerProcessing = time;
    }

    public long getTimeBeginServerProcessing() {
        return this.timeBeginServerProcessing;
    }

    public void setPriorTimeEndServerProcessing(long time) {
        this.priorTimeEndServerProcessing = time;
    }

    public long getPriorTimeEndServerProcessing() {
        return this.priorTimeEndServerProcessing;
    }

    public void setTimeEndServerProcessing(long time) {
        this.timeEndServerProcessing = time;
    }

    public long getTimeEndServerProcessing() {
        return this.timeEndServerProcessing;
    }

    public void setNetworkDuration(int time) {
        this.networkDuration = time;
    }

    public int getNetworkDuration() {
        return this.networkDuration;
    }

    public void setServerDuration(int serverDuration) {
        this.serverDuration = serverDuration;
    }

    public int getServerDuration() {
        return this.serverDuration;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangCode() {
        if (this.langCode == null) {
            return "";
        } else {
            return this.langCode;
        }
    }
    public UsageHitBean getPhb() {
        if (this.phb == null) {
            this.setPhb(new UsageHitBean());
        }
        return this.phb;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStartingStep() {
        return startingStep;
    }

    public void setStartingStep(int startingStep) {
        this.startingStep = startingStep;
    }

}