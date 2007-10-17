package org.dianexus.triceps;

import java.sql.Timestamp;

import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.InstrumentDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDataDAO;
import org.dianexus.triceps.modules.data.InstrumentVersionDAO;
import org.dianexus.triceps.modules.data.RawDataDAO;
import org.apache.log4j.Logger;

/**
	This class consolidates all of the timing functionality, including processing events, and determining response times
*/
public class TricepsTimingCalculator {
  static Logger logger = Logger.getLogger(TricepsTimingCalculator.class);
	private PageHitBean phb = null;
	private int displayCount=0;
	private int startingGroupNum=0;
	private int endingGroupNum=0;
	private String lastAction = "";
	private String statusMsg = "";
	private int userId=0;
	private int startingStep;
	private int instrumentId = 0;
	private int instrumentSessionId = 0;
	private String instrumentTitle="";
	private String major_version="";
	private String minor_version="";
	private InstrumentSessionDAO is=null;
	private InstrumentSessionDataDAO isd= null;
	private RawDataDAO rd = null;
	private InstrumentSessionBean isb =null;
	private InstrumentVersionDAO ivDAO =null;
	private boolean initialized = false;
	// FIXME - Should I add a stateful record of last server receipt time to avoid problem with calculating 
	// ServerProcessingTime and NetworkProcessingTime?
	
	/**
		Empty constructor to avoid NullPointerException
	*/
	public TricepsTimingCalculator(){
		initialized = false;
	}
	
	/**
		Constructor.  This loads the proper instrument from the database (based upon title, and version).
		Initializes the session.
		
		@param instrumentTitle	The title of the instrument
		@param major_version Major version
		@param minor_version Minor version
		@param userId User ID - not currently used
		@param startingStep	The starting step (first group)
	*/
	public TricepsTimingCalculator(String instrumentTitle,String major_version, String minor_version, int userId, int startingStep) {
	try {
		this.setStatusMsg("init");
		this.setLastAction("init");
		this.setStartingGroupNum(startingStep);
		this.setEndingGroupNum(startingStep);	
		
		this.major_version = major_version;
		this.minor_version = minor_version;
		this.instrumentTitle = instrumentTitle;
		this.userId = userId;
		this.startingStep = startingStep;
		DialogixDAOFactory df = DialogixDAOFactory.getDAOFactory(1);
		// get the instrument id based on the instrument title
		InstrumentDAO instrumentDAO = df.getInstrumentDAO();
		instrumentDAO.getInstrument(instrumentTitle);
		this.setInstrumentId(instrumentDAO.getInstrumentId());
		
//		 handle error if versions not found
		if (major_version == null) {
			major_version = "1";
		}
		if (minor_version == null) {
			minor_version = "1";
		}
		// get the instrument version id
		ivDAO = df.getInstrumentVersionDAO();
		ivDAO.getInstrumentVersion(instrumentId, new Integer(major_version).intValue(), new Integer( minor_version).intValue());
		String instrumentTableName = ivDAO.getInstanceTableName();
		logger.info("table name is: " + instrumentTableName);
		
		// Create InstrumentSession Bean - as side effect, sets all startup values (which may be inappropriate)
		this.getIsb().setInstrumentVersionId(this.ivDAO.getInstrumentVersionId());
		
		isd = df.getInstrumentSessionDataDAO();
		isd.setFirstGroup(startingStep);
		isd.setSessionStartTime(new Timestamp(System.currentTimeMillis()));
		isd.setSessionEndTime(new Timestamp(System.currentTimeMillis()));
		isd.setInstrumentName(instrumentTitle);
		isd.setInstanceName(instrumentTableName);
		isd.setLastAction(this.getLastAction());
		isd.setLastGroup(this.getEndingGroupNum());
		isd.setStatusMsg(this.getStatusMsg());
		isd.setSessionId(this.getInstrumentSessionId());
		isd.setInstrumentSessionDataDAO(instrumentTableName);
		
		
		 rd = df.getRawDataDAO();
		 
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
	
	public void beginServerProcessing(Long timestamp){
	try {
		this.incrementDisplayCount();	// CHECK - should this happen if it is the last page?

		this.getPhb().setReceivedRequest(timestamp.longValue());
		this.getPhb().setStartingGroupNum(this.getStartingGroupNum());
		this.getPhb().processPageEvents();	// does this deal with cross-page information?
	} catch (Exception e) {
		logger.error("", e);
	}
	}
	
	/**
		This is called when the server is ready to return a response to the user.
		ServerProcessingTime = (finishServerProcessing.timestamp - beginServerProcessing.timestamp)
		
		@param timestamp	Current system time
	*/
	public void finishServerProcessing(Long timestamp){
		try {
			if (initialized == false) {
				logger.info("ttc not yet initialized");
				return;
			}
			
			// Update Horizontal Table
			isd.setLastGroup(this.getEndingGroupNum());
			isd.setSessionEndTime(new Timestamp(timestamp.longValue()));
			isd.setDisplayNum(this.getDisplayCount());
			isd.setLastAction(this.getLastAction());
			isd.setStatusMsg(this.getStatusMsg());
			isd.update();
			
			// Update Session State
			this.getIsb().setEnd_time(new Timestamp(timestamp.longValue()));
			this.getIsb().setLast_group(this.getEndingGroupNum());
			this.getIsb().setLastAction(this.getLastAction());
			this.getIsb().setDisplayNum(this.getDisplayCount());
			this.getIsb().setStatusMessage(this.getStatusMsg());
			this.getIsb().update();	

			// Add information about this page-worth of usage
			this.getPhb().setSentResponse(timestamp.longValue());
			this.getPhb().setDisplayNum(this.getDisplayCount());
			this.getPhb().setEndingGroupNum(this.getEndingGroupNum());
			this.getPhb().setLastAction(this.getLastAction());	
			this.getPhb().setStatusMsg(this.getStatusMsg());		
			this.getPhb().setInstrumentSessionId(this.getInstrumentSessionId());
			// totalDuration?
			// serverDuration?
			// loadDuration?
			// networkDuratin?
			// pageVacillation?
			this.getPhb().store();	
			
			// Finally, update GroupNum to reflect where should land
			// Put information about server processing time here too?
			this.setStartingGroupNum(this.getEndingGroupNum());
			
		}	catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
		This assigns a value (Datum) to an item (Node).
		@param ques	the Item
		@param asn	the Value
	*/
	public void writeNode(Node ques, Datum ans){
	try {
		if (ques != null && ans != null) {
			// Queue data to be saved to horizontal table
			isd.updateInstrumentSessionDataDAO(ques.getLocalName(), InputEncoder.encode(ans.stringVal(true)));
			
			// Save data to Raw Data table
			this.rd.clearRawDataStructure();
			this.rd.setAnswer(InputEncoder.encode(ans.stringVal(true)));
			this.rd.setAnswerType(ques.getAnswerType());
			this.rd.setComment(ques.getComment());
			this.rd.setInstrumentSessionId(this.getInstrumentSessionId());
			this.rd.setDisplayNum(this.getDisplayCount());
			this.rd.setGroupNum(this.getStartingGroupNum());	
			this.rd.setInstanceName(ivDAO.getInstanceTableName());
			this.rd.setInstrumentName(this.instrumentTitle);
			this.rd.setLangNum(ques.getAnswerLanguageNum());	// FIXME - should be answer language CODE (5 char), not #
			this.rd.setQuestionAsAsked(InputEncoder.encode(ques.getQuestionAsAsked()));
			this.rd.setTimeStamp(new Timestamp(ques.getTimeStamp().getTime()));
			this.rd.setVarName(ques.getLocalName());
			this.rd.setWhenAsMS(ques.getTimeStamp().getTime());	// This duplicates timestamp - which will be easier to use?
			// get event data from triceps

			if (this.phb != null) { // Will this only exist if there are events associated with the question?  When Unasked, it will be null?
				logger.debug("### in ttc. page hit bean is not null");
				int qi = this.getPhb().getCurrentQuestonIndex();

				logger.debug("### in tc. qi is "+qi);
				QuestionTimingBean qtb = null;
				try {
					qtb = this.getPhb().getQuestionTimingBean(ques.getLocalName());
				} catch (IndexOutOfBoundsException iob) {
					logger.error("FIXME", iob);
					qtb = null;
				}
				if (qtb != null) {
					logger.debug("### in Evidence qtb is not null");
					this.rd.setResponseDuration(qtb.getResponseDuration());
					logger.debug("### in Evidence responseDuration is : "+qtb.getResponseDuration());
					this.rd.setResponseLatency(qtb.getResponseLatency());
					logger.debug("### in Evidence responseLatency is : "+qtb.getResponseLatency());
					this.rd.setItemVacillation(qtb.getItemVacillation());
					logger.debug("### in Evidence  item vacilation is "+qtb.getItemVacillation());
					qi++;
				}
				else {
					logger.info("qtb is null");	// this is always the case, which may explain why durations aren't set
				}
			}
			this.rd.setRawData();
			logger.debug("### in Evidence raw data has been writen");
		}
	} catch (Exception e) {
		logger.error("", e);
	}
	}

	/**
		This calculates item-specific timing information within a page, including responseLatency, responseDuration, and itemVacillation
		@param eventString	The full list of events.  PageHitBean processes through them all
	*/
	public void processEvents(String eventString){
	try {
		logger.debug("In TTC processEvents string is "+eventString);
		if(eventString!= null){
		this.setPhb(new PageHitBean());
		logger.debug("got new phb");
		// parse the raw timing data string
			this.getPhb().parseSource(eventString);
			logger.debug("In TTC processEvents parsing source");
		// extract the events and write to pageHitEvents table
		// set variables for page hit level timing
		
		}
	} catch (Exception e) {
		logger.error("", e);
	}
	}
	
	public PageHitBean getPhb() {
		if(this.phb==null){
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

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStartingStep() {
		return startingStep;
	}
	public void setStartingStep(int startingStep) {
		this.startingStep = startingStep;
	}
	
	private InstrumentSessionBean initializeIsb() {
		try {
			InstrumentSessionBean _isb = new InstrumentSessionBean();
			
			_isb.setStart_time(new Timestamp(System.currentTimeMillis()));
			_isb.setEnd_time(new Timestamp(System.currentTimeMillis()));
			if (this.ivDAO != null) {	// If there is no instrument version at outset, keep it blank.
				_isb.setInstrumentVersionId(this.ivDAO.getInstrumentVersionId());	// FIXME - throwing NullPointerException - InstrumentID and InstrumentSessionID both wrongly 0
			}
			_isb.setInstrumentId(this.instrumentId);	// CHECK THIS
			_isb.setUserId(this.userId);
			_isb.setFirst_group(this.startingStep);		
			_isb.setLast_group(this.startingStep);	
			_isb.setDisplayNum(this.getDisplayCount());
			_isb.setLastAction("init");
			_isb.setStatusMessage("init");
			
			_isb.store();	// so that new row inserted into database - rest of interactions will be updates
			this.setInstrumentSessionId(_isb.getInstrumentSessionId());
			return _isb;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	public InstrumentSessionBean getIsb() {
		// CHECK - is there risk of creating empty ISB?
		if(this.isb==null){
			this.setIsb(initializeIsb());
		}
		return this.isb;
	}
	public void setIsb(InstrumentSessionBean isb) {
		this.isb = isb;
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
	
	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}
	
	public int getInstrumentId() {
		return this.instrumentId;
	}
	
	public void setInstrumentSessionId(int instrumentSessionId) {
		this.instrumentSessionId = instrumentSessionId;
	}
	
	public int getInstrumentSessionId() {
		return this.instrumentSessionId;
	}	
	
	public int getStartingGroupNum() {
		return startingGroupNum;
	}

	public void setStartingGroupNum(int groupNum) {
		this.startingGroupNum = groupNum;
	}
	
	public int getEndingGroupNum() {
		return endingGroupNum;
	}

	public void setEndingGroupNum(int groupNum) {
		this.endingGroupNum = groupNum;
	}		
}
