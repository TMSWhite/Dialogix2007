package org.dianexus.triceps;

import java.sql.Timestamp;	// FIXME - shouldn't we be moving away from sql Timestamps?
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import org.dialogix.entities.*;
import javax.persistence.Query;
import org.dialogix.entities.Instrument;
import org.dialogix.entities.InstrumentSession;
import org.dialogix.entities.InstrumentVersion;
import org.dialogix.entities.ItemUsage;

import org.dianexus.triceps.modules.data.InstrumentSessionDataDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDataJPA;

import org.apache.log4j.Logger;
import org.dialogix.entities.ActionType;

/**
	This class consolidates all of the timing functionality, including processing events, and determining response times
	// FIXME - it should also encapsulate all functionality within EventTimingBean, PageHitBean
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
    private PageHitBean phb = null;	// FIXME seems to be needed, other than embedded persistence methods

    private InstrumentSessionDataDAO isd = null;	// FIXME - interface needed for horizontal tables, but can be more pared down than this

    /**
		Empty constructor to avoid NullPointerException
	*/

    public DialogixTimingCalculator() {
        initialized = false;
    }
    private EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DialogixEntitiesPU");
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

            // get the instrument id based on the instrument title
            // tweak
            this.instrument = getInstrument(this.instrumentTitle);
            this.setInstrumentID(instrument.getInstrumentID());

            //	handle error if versions not found
            if (major_version == null) {
                major_version = "1";
            }
            if (minor_version == null) {
                minor_version = "1";
            }

            /* How do we query into Instrument Versions?  Should major and minor be combined to facilitate this? */
            //	Collection<InstrumentVersion> instrumentVersions = instrument.getInstrumentVersionCollection();	// FIXME - check whether version exists within collection of instruments
//		instrumentVersion = instrumentVersions.findByMajorVersion(new Integer(major_version).intValue());	// FIXME - check whether version exists within collection of instruments

            // George - just to get by, will clean up types later
            int major_version_int = Integer.parseInt(major_version);
            int minor_version_int = Integer.parseInt(minor_version);
            this.instrumentVersion = getInstrumentVersion(instrumentID, major_version_int, minor_version_int);
            if (this.instrumentVersion == null) {
            // George - todo cannot continue if true
            }

            String instrumentTableName = "InstVer_" + instrumentVersion.getInstrumentVersionID();

            logger.info("table name is: " + instrumentTableName);

            // Create InstrumentSession Bean - as side effect, sets all startup values (which may be inappropriate)
            // FIXME - how should initializatin of InstrumentSession happen?
            // this.getInstrumentSession().setInstrumentVersionID(this.instrumentVersion.getInstrumentVersionID().intValue());	// FIXME - how are IDs set?
            instrumentSession = initializeInstrumentSession();
            if (this.instrumentSession == null) {
            // George - todo cannot continue if true
            }

            // FIXME - replace isd with pared down Interface class?
	    // (1) Create new row within InstrumentSession Data -- table name will be instrumentTableName
            // isd = df.getInstrumentSessionDataDAO();	// FIXME - create new horizontal table

            // George change to getter factory
            //isd = new InstrumentSessionDataDAO();	
            // FIXME - should be interface to data
            isd = getInstrumentSessionDataDAO();
            isd.setInstrumentStartingGroup(startingStep);
            isd.setSessionStartTime(new Timestamp(System.currentTimeMillis()));
            isd.setSessionLastAccessTime(new Timestamp(System.currentTimeMillis()));
            isd.setLastAction(this.getLastAction());
            isd.setCurrentGroup(this.getToGroupNum());
            isd.setStatusMsg(this.getStatusMsg());
            // FIXME - how are IDs set            
            isd.setSessionId(instrumentSession.getInstrumentSessionID());
            isd.setInstrumentSessionDataDAO(instrumentTableName);	// FIXME - create new row at this point

            // FIXME - replace rd with ItemUsage - I don't think we need a DAO for this - just add new ItemUsage entry each time, rather than as Bean
            // rd = df.getRawDataDAO();	// FIXME - not needed?

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

            // FIXME - should processPageEvents() be encapsulated within DialogixTimingCalculator, rather than PageHitBean?
	    // FIXME - PHB doesn't do much other than processing Events and storing one row per new event (which should be here)
//            this.getPhb().processPageEvents();	// does this deal with cross-page information?
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
                logger.info("ttc not yet initialized");
                return;
            }

            // Update Horizontal Table
            isd.setCurrentGroup(this.getToGroupNum());
            isd.setSessionLastAccessTime(new Timestamp(timestamp.longValue()));
            isd.setDisplayNum(this.getDisplayCount());
            isd.setLangCode(this.getLangCode());
            isd.setLastAction(this.getLastAction());
            isd.setStatusMsg(this.getStatusMsg());
            //george
            // FIXME - persist everything at one time
            isd.updateInstrumentSessionDataDAO(statusMsg, langCode);	

            // Update Session State
            instrumentSession.setLastAccessTime(new Timestamp(timestamp.longValue()));
            instrumentSession.setCurrentGroup(this.getToGroupNum());
            //this.getInstrumentSession().setLastAction(this.getLastAction());	// FIXME - uses enumerated list?
            instrumentSession.setDisplayNum(this.getDisplayCount());
            instrumentSession.setLanguageCode(this.getLangCode());
            instrumentSession.setStatusMsg(this.getStatusMsg());
            // FIXME - persist everything at one time
            //this.getInstrumentSession().update();

            // Add information about this page-worth of usage
	    // FIXME - extract all PageHitBean functionality to this class?
            /*
            this.getPhb().setDisplayNum(this.getDisplayCount());
            this.getPhb().setLangCode(this.getLangCode());
            this.getPhb().setToGroupNum(this.getToGroupNum());
            this.getPhb().setLastAction(this.getLastAction());
            this.getPhb().setStatusMsg(this.getStatusMsg());
            //	this.getPhb().setInstrumentSessionID(this.getInstrumentSessionID());	// FIXME - how are related IDs set?
            // totalDuration?
	    // serverDuration?
	    // loadDuration?
	    // networkDuratin?
	    // pageVacillation?

            this.setTimeEndServerProcessing(System.currentTimeMillis());
            this.setServerDuration((int) (getTimeEndServerProcessing() - getTimeBeginServerProcessing()));

            this.setNetworkDuration((int) (getTimeBeginServerProcessing() - getPriorTimeEndServerProcessing()) -
                    getPhb().getLoadDuration() -
                    getPhb().getTotalDuration());

            this.getPhb().setServerDuration(this.getServerDuration());
            this.getPhb().setNetworkDuration(this.getNetworkDuration());
            this.getPhb().store();

            // FIXME - Persist whole stucture here

            // Finally, update GroupNum to reflect where should land
	    // Put information about server processing time here too?
            this.setFromGroupNum(this.getToGroupNum());
            this.setPriorTimeEndServerProcessing(getTimeEndServerProcessing());  
            */
            // George - here is where to merge instrumentSession 
            // instrumentSession contains collection of
            // Datum, PageUsage, PageUsageEvents

            // Persist Everthing -------------------------------
            merge(instrumentSession); 
            // Persist Everything Done! ------------------------

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
                isd.updateInstrumentSessionDataDAO(ques.getLocalName(), InputEncoder.encode(ans.stringVal(true)));

                // FIXME - rather than clearing a RawDataBean/DAO, just create new one and populate it.
			// FIXME - Persist whole RawData structure at end, rather than one insert per variable
			// Save data to Raw Data (ItemUsage) table
                itemUsage = new ItemUsage();
                itemUsage.setAnswerString(InputEncoder.encode(ans.stringVal(true)));
                itemUsage.setComments(ques.getComment());
                //			itemUsage.setInstrumentSessionID(this.getInstrumentSessionID());	// FIXME - how are IDs set?
                itemUsage.setDisplayNum(this.getDisplayCount());
                itemUsage.setGroupNum(this.getFromGroupNum());
                itemUsage.setLanguageCode(this.getLangCode());
                itemUsage.setQuestionAsAsked(InputEncoder.encode(ques.getQuestionAsAsked()));
                itemUsage.setTimeStamp(new Timestamp(ques.getTimeStamp().getTime()));
                
                //itemUsage.setVarNameID(new VarName()); // (ques.getLocalName()) FIXME - should look-up whether VarName exists already; and/or pull VarName_ID from loaded instrument

                itemUsage.setWhenAsMS(ques.getTimeStamp().getTime());	// This duplicates timestamp - which will be easier to use?
//			this.rd.setRawData();	// FIXME - persist all ItemUsage objects at end?  So add to collection of objects to persist?
            }
        } catch (Exception e) {
            logger.error("WriteNode Error", e);
        }
    }

    /**
		This calculates item-specific timing information within a page, including responseLatency, responseDuration, and itemVacillation
		@param eventString	The full list of events.  PageHitBean processes through them all
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

    public PageHitBean getPhb() {
        if (this.phb == null) {
            this.setPhb(new PageHitBean());
        }
        return this.phb;
    }

    public void setPhb(PageHitBean phb) {
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

    // FIXME - 
	// (1) Create new InstrumentSession if one doesn't exist, populating it with defaults

    private InstrumentSession initializeInstrumentSession() {
        // george
        try {

            EntityManager em = getEntityManager();
            instrumentSession = new InstrumentSession();
            instrumentSession.setInstrumentVersionID(instrumentVersion);
            instrumentSession.setCurrentGroup(1);
            instrumentSession.setDisplayNum(1);
            instrumentSession.setInstrumentStartingGroup(1);
            instrumentSession.setLanguageCode("en");
            instrumentSession.setLastAccessTime(new Date());
            instrumentSession.setStartTime(new Date());
            instrumentSession.setStatusMsg("statusMsg");
            // Lookups begin-------------
            Integer Id = 1; // hack
            try {
                System.out.println("Lookup and set ActionType");
                ActionType actionType = em.find(ActionType.class, Id);
                instrumentSession.setActionTypeID(actionType);
                System.out.println("Lookup and set Instrument");
                // Already in memory?
                instrument = em.find(Instrument.class, Id);
                instrumentSession.setInstrumentID(instrument);
            } finally {
                em.close();
            }
            instrumentSession.setStartTime(new Timestamp(System.currentTimeMillis()));
            instrumentSession.setLastAccessTime(new Timestamp(System.currentTimeMillis()));
            if (this.instrumentVersion != null) { // If there is no instrument version at outset, keep it blank.
            //	_is.setInstrumentVersionID(this.instrumentVersion.getInstrumentVersionID());	// FIXME - throwing NullPointerException - InstrumentID and InstrumentSessionID both wrongly 0
            }
            //	_is.setInstrumentID(this.instrumentID);	// FIXME - how are IDs set?
            //	_is.setUserID(this.userID);	// FIXME - how are IDs set?
            instrumentSession.setInstrumentStartingGroup(this.startingStep);
            instrumentSession.setCurrentGroup(this.startingStep);
            instrumentSession.setDisplayNum(this.getDisplayCount());
            // 	_is.setLastAction("init");	// FIXME - refers to enumerated list
            instrumentSession.setStatusMsg("init");

            //	_is.store(); //  FIXME - so that new row inserted into database - rest of interactions will be updates
	    // FIXME - create new row here
            this.setInstrumentSessionID(instrumentSession.getInstrumentSessionID());
            return instrumentSession;
        } catch (Exception e) {
            logger.error("initializeInstrumentSession", e);
            return null;
        }
    }

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

    public InstrumentVersion getInstrumentVersion(int id, int major, int minor) {
        EntityManager em = getEntityManager();
        instrumentVersion = null;
        try {
            String versionQuery = "SELECT i FROM InstrumentVersion i WHERE i.instrumentId = :instrumentId AND i.majorVersion = :majorVersion AND i.minorVersion = :minorVersion";
            Query query = em.createQuery(versionQuery);
            query.setParameter("instrumentId", id);
            query.setParameter("majorVersion", major);
            query.setParameter("minorVersion", minor);
            instrumentVersion = (InstrumentVersion) query.getSingleResult();
            if (instrumentVersion == null) {
                return null;
            }
        } catch (Exception e) {
            logger.error("getInstrumentVersion", e);
            return null;
        } finally {
            em.close();
        }
        return instrumentVersion;
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
}
