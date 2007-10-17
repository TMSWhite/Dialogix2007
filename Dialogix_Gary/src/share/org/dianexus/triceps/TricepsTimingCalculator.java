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
	private int groupNum=0;
	private int accessCount=0;
	int userId=0;
	int startingStep;
	String instrumentTitle="";
	String major_version="";
	String minor_version="";
	InstrumentSessionDAO is=null;
	InstrumentSessionDataDAO isd= null;
	RawDataDAO rd = null;
	InstrumentSessionBean isb =null;
	InstrumentVersionDAO ivDAO =null;
	// FIXME - Should I add a stateful record of last server receipt time to avoid problem with calculating 
	// ServerProcessingTime and NetworkProcessingTime?
	
	/**
		Empty constructor to avoid NullPointerException
	*/
	public TricepsTimingCalculator(){
		
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
		this.major_version = major_version;
		this.minor_version = minor_version;
		this.instrumentTitle = instrumentTitle;
		this.userId = userId;
		this.startingStep = startingStep;
		isb = new InstrumentSessionBean();
		DialogixDAOFactory df = DialogixDAOFactory.getDAOFactory(1);
		// get the instrument id based on the instrument title
		InstrumentDAO instrumentDAO = df.getInstrumentDAO();
		instrumentDAO.getInstrument(instrumentTitle);
		int instrumentId = instrumentDAO.getInstrumentId();
		
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
		isd = df.getInstrumentSessionDataDAO();
		isd.setFirstGroup(startingStep);
		isd.setSessionStartTime(new Timestamp(System.currentTimeMillis()));
		isd.setSessionEndTime(new Timestamp(System.currentTimeMillis()));
		isd.setLastAccess("init");
		isd.setInstrumentName(instrumentTitle);
		isd.setInstanceName(instrumentTableName);
		isd.setLastAction("init");
		isd.setLastGroup(0);
		isd.setStatusMsg("init");
		
		// create instrument session bean -- XXX this is wrong: update this from Evidence:435
		
		InstrumentSessionBean instrumentSessionBean = new InstrumentSessionBean();
		instrumentSessionBean.setStart_time(new Timestamp(System.currentTimeMillis()));
		instrumentSessionBean.setEnd_time(new Timestamp(System.currentTimeMillis()));
		instrumentSessionBean.setInstrumentVersionId(this.ivDAO.getInstrumentVersionId());
		instrumentSessionBean.setInstrumentId(instrumentId);
		instrumentSessionBean.setUserId(userId);    // this is wrong - has UserID been set?
		instrumentSessionBean.setFirst_group(startingStep);
		instrumentSessionBean.setLast_group(startingStep);
		instrumentSessionBean.setDisplayNum(this.getDisplayCount());
		instrumentSessionBean.setLast_action("init");
		instrumentSessionBean.setLast_access("init");
		instrumentSessionBean.setStatusMessage("initialized");
		instrumentSessionBean.store();
		this.setIsb(instrumentSessionBean);
		// need to do this here because sessionId now exists
		isd.setSessionId(instrumentSessionBean.getInstrumentSessionId());
		isd.setInstrumentSessionDataDAO(instrumentTableName);
		
		
		 rd = df.getRawDataDAO();
	} catch (Exception e) {
		logger.error("", e);
	}
	}
	
	/**
		This is called when the server receives a request.  It starts the the clock for server processing time.
		Side effects include:
		(1) Increasing displayCount
		(2) Setting accessCount, displayCount, groupNum, and storing that information to pageHits
		@param timestamp	System time in milliseconds
	*/
	
	public void gotRequest(Long timestamp){
	try {
		this.incrementDisplayCount();
		logger.debug("In TTC gotRequest: time is"+timestamp.toString());

		this.getPhb().setReceivedRequest(timestamp.longValue());
		this.getPhb().processPageEvents();	// does this deal with cross-page information?
		this.getPhb().setAccessCount(accessCount);
		this.getPhb().setDisplayNum(this.getDisplayCount());
		this.getPhb().setGroupNum(groupNum);
		if (this.isb != null) {	// will be null if there is no session yet
			this.getPhb().setInstrumentSessionId(this.isb.getInstrumentSessionId());
			this.isb.setDisplayNum(this.getDisplayCount());
		}
		// totalDuration?
		// serverDuration?
		// loadDuration?
		// networkDuratin?
		// pageVacillation?
		this.getPhb().store();
	} catch (Exception e) {
		logger.error("", e);
	}
	}
	
	/**
		This is called when the server is ready to return a response to the user.
		ServerProcessingTime = (sentResponse.timestamp - gotRequest.timestamp)
		
		@param timestamp	Current system time
	*/
	public void sentResponse(Long timestamp){
		logger.debug("In TTC sentResponse: time is "+timestamp.toString());
		this.getPhb().setSentResponse(timestamp.longValue());
		// CHECK - should database writing occur here to ensure all parameters are set?
	}
	
	/**
		This assigns a value (Datum) to an item (Node).
		@param ques	the Item
		@param asn	the Value
	*/
	public void writeNode(Node ques, Datum ans){
	try {
		logger.debug("In TTC write node : writing question");
		if (ques != null && ans != null) {
      // If instrument does not yet exist, but there are page events (clickstream), ...
			if (this.isb == null && this.phb != null) {
				logger.debug("in ttc: isb is null");
				isb = new InstrumentSessionBean();
				isb.setStart_time(new Timestamp(System.currentTimeMillis()));
				isb.setEnd_time(new Timestamp(System.currentTimeMillis()));
				isb.setInstrumentVersionId(this.ivDAO.getInstrumentVersionId());	// FIXME - throwing NullPointerException - InstrumentID and InstrumentSessionID both wrongly 0
				isb.setUserId(this.userId);
				isb.setFirst_group(this.startingStep);
				isb.setLast_group(this.groupNum);
				isb.setLast_action(this.phb.getLastAction());
				isb.setDisplayNum(this.getDisplayCount());
				// TODO need real last access here
				isb.setLast_access("");
				isb.setStatusMessage(this.phb.getStatusMsg());
				isb.store();

			} else if (this.phb != null) {
				logger.debug("in ttc: isb is NOT null");
				this.isb.setEnd_time(new Timestamp(System.currentTimeMillis()));
				this.isb.setLast_group(this.groupNum);
				this.isb.setLast_action(phb.getLastAction());//wrong
				// TODO need real last access here
				this.isb.setLast_access("");
				this.isb.setStatusMessage(phb.getStatusMsg());//wrong
				this.isb.setDisplayNum(this.getDisplayCount());
				this.isb.update();
			}

			// update session data table
			isd.setLastAccess("");//this.displayCount);	// NullPointerException
			isd.setLastGroup(this.groupNum);
			isd.setSessionEndTime(new Timestamp(System.currentTimeMillis()));
			isd.setDisplayNum(this.getDisplayCount());
			
			if (phb != null) {
				isd.setLastAction(phb.getLastAction());
				isd.setStatusMsg(phb.getStatusMsg());
			}
			logger.debug("In ttc preparing to save data to horiz table");
			
			isd.updateInstrumentSessionDataDAO(ques.getLocalName(), ans.stringVal(true));	// CHECK - does this need to be encoded?
			logger.debug("In ttc after saving data to horiz table");
//			sdao.updateInstrumentSessionColumn(q.getLocalName(),
//			InputEncoder.encode(ans));
			this.rd.clearRawDataStructure();
			this.rd.setAnswer(InputEncoder.encode(ans.stringVal(true)));
			this.rd.setAnswerType(ques.getAnswerType());
			this.rd.setComment(ques.getComment());
			if (isb != null) {  // XXX Will this ever be null?  If so, do something
				this.rd.setInstrumentSessionId(this.isb.getInstrumentSessionId());
			}
			this.rd.setDisplayNum(this.getDisplayCount());
			this.rd.setGroupNum(this.groupNum);	// FIXME - should be current group
			this.rd.setInstanceName(ivDAO.getInstanceTableName());
			// TODO get reserved index id
			this.rd.setInstrumentName(this.instrumentTitle);
			this.rd.setLangNum(ques.getAnswerLanguageNum());	// FIXME - should be answer language CODE (5 char), not #
			this.rd.setQuestionAsAsked(InputEncoder.encode(ques.getQuestionAsAsked()));	// CHECK - should this be InputEncoded?
			this.rd.setTimeStamp(new Timestamp(ques.getTimeStamp().getTime()));
			this.rd.setVarName(ques.getLocalName());
			this.rd.setVarNum(-1) ; // XXX This should be Evidence.getNodeIndex(q) -- Easiest fix is to set this in the table when instrument is loaded, not compute dynamically
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
					this.getPhb().setCurrentQuestionIndex(qi);
					this.getPhb().setAccessCount(this.accessCount);
					this.getPhb().setGroupNum(this.groupNum);
					this.getPhb().setDisplayNum(this.getDisplayCount());
					this.getPhb().setInstrumentSessionId(this.isb.getInstrumentSessionId());
					
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
	
	/**
		@param accessCount	Number of times this Java class has been used - not really needed.  FIXME - it is actually sent DisplayCount
		@param groupNum	The # of nodes
	*/
	public void setItemMetadata(int accessCount, int groupNum){
		this.accessCount = accessCount;
		this.groupNum = groupNum;
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
	public int getAccessCount() {
		return accessCount;
	}
	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}
	public int getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
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
	public InstrumentSessionBean getIsb() {
		// CHECK - is there risk of creating empty ISB?
		if(this.isb==null){
			this.setIsb(new InstrumentSessionBean());
		}		
		return this.isb;
	}
	public void setIsb(InstrumentSessionBean isb) {
		this.isb = isb;
	}
	
	public void incrementDisplayCount() {
		this.setDisplayCount(this.getDisplayCount() + 1);
	}
}
