package org.dianexus.triceps;

import java.sql.Timestamp;

import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.InstrumentDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDataDAO;
import org.dianexus.triceps.modules.data.InstrumentVersionDAO;
import org.dianexus.triceps.modules.data.RawDataDAO;

public class TricepsTimingCalculator {
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
	
	public TricepsTimingCalculator(){
		
	}
	public TricepsTimingCalculator(String instrumentTitle,String major_version, String minor_version, int userId, int startingStep) {
		super();
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
		if (major_version == null || minor_version == null) {
			// throw an error here
		}
		// get the instrument version id
		ivDAO = df.getInstrumentVersionDAO();
		ivDAO.getInstrumentVersion(instrumentId, new Integer(major_version).intValue(), new Integer( minor_version).intValue());
		String instrumentTableName = ivDAO.getInstanceTableName();
		System.out.println("table name is: " + instrumentTableName);
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
		
		// create instrument session bean
		
		InstrumentSessionBean instrumentSessionBean = new InstrumentSessionBean();
		instrumentSessionBean.setStart_time(new Timestamp(System.currentTimeMillis()));
		instrumentSessionBean.setEnd_time(new Timestamp(System.currentTimeMillis()));
		instrumentSessionBean.setInstrumentVersionId(this.ivDAO.getInstrumentVersionId());
		instrumentSessionBean.setInstrumentId(instrumentId);
		instrumentSessionBean.setUserId(userId);
		instrumentSessionBean.setFirst_group(startingStep);
		instrumentSessionBean.setLast_group(startingStep);
		instrumentSessionBean.setLast_action("init");
		instrumentSessionBean.setLast_access("init");
		instrumentSessionBean.setStatusMessage("initialized");
		instrumentSessionBean.store();
		this.setIsb(instrumentSessionBean);
		// need to do this here because sessionId now exists
		isd.setSessionId(instrumentSessionBean.getInstrumentSessionId());
		isd.setInstrumentSessionDataDAO(instrumentTableName);
		
		
		 rd = df.getRawDataDAO();
	}
	public void gotRequest(Long timestamp){
		this.displayCount++;
		System.out.println("In TTC gotRequest: time is"+timestamp.toString());
		if(this.getPhb()==null){
			this.setPhb(new PageHitBean());
			this.getPhb().setReceivedRequest(timestamp.longValue());
		}
		else{
		this.getPhb().setReceivedRequest(timestamp.longValue());
		
		this.getPhb().processPageEvents();
		this.getPhb().setAccessCount(accessCount);
		this.getPhb().setDisplayNum(displayCount);
		this.getPhb().setGroupNum(groupNum);
		this.getPhb().store();
		}
	}
	public void sentResponse(Long timestamp){
		System.out.println("In TTC sentResponse: time is"+timestamp.toString());
		this.getPhb().setSentResponse(timestamp.longValue());
	}
	
	public void writeNode(Node ques, Datum ans){
		System.out.println("In TTC write node : writing question");
		// ##GFL Code added by Gary Lyons 2-24-06 to add direct db access
		// to update instrument session instance table
		
		if (ques != null && ans != null) {



			if (this.isb == null && this.phb != null) {
				System.out.println("in ttc: isb is null");
				isb = new InstrumentSessionBean();
				isb.setStart_time(new Timestamp(System.currentTimeMillis()));
				isb.setEnd_time(new Timestamp(System.currentTimeMillis()));
				isb.setInstrumentVersionId(this.ivDAO.getInstrumentVersionId());
				isb.setUserId(this.userId);
				isb.setFirst_group(this.groupNum);
				isb.setLast_group(this.groupNum);
				isb.setLast_action(this.phb.getLastAction());
				// TODO need real last access here
				isb.setLast_access("");
				isb.setStatusMessage(this.phb.getStatusMsg());
				isb.store();
			

			} else if (this.phb != null) {
				System.out.println("in ttc: isb is NOT null");
				this.isb.setEnd_time(new Timestamp(System.currentTimeMillis()));
				this.isb.setLast_group(this.groupNum);
				this.isb.setLast_action(phb.getLastAction());//wrong
				// TODO need real last access here
				this.isb.setLast_access("");
				this.isb.setStatusMessage(phb.getStatusMsg());//wrong
				this.isb.update();
			}

			// update session data table
			isd.setLastAccess("");//this.displayCount);
			isd.setLastGroup(this.groupNum);
			if (phb != null) {
				isd.setLastAction(phb.getLastAction());
				isd.setStatusMsg(phb.getStatusMsg());
			}
			System.out.println("In ttc preparing to save data to horiz table");
			
			isd.updateInstrumentSessionDataDAO(ques.getLocalName(), ans.toString());
			System.out.println("In ttcafter saving data to  horiz table");
			// sdao.updateInstrumentSessionColumn(q.getLocalName(),
			// InputEncoder.encode(ans));
			this.rd.clearRawDataStructure();
			this.rd.setAnswer(InputEncoder.encode(ans.toString()));
			this.rd.setAnswerType(ques.getAnswerType());
			this.rd.setComment("");
			if (isb != null) {
				this.rd.setInstrumentSessionId(this.isb.getInstrumentSessionId());
			}
			if (this.displayCount !=0) {
				this.rd.setDisplayNum(this.displayCount);
			}
			this.rd.setGroupNum(this.displayCount);
			this.rd.setInstanceName(ivDAO.getInstanceTableName());
			// TODO get reserved index id
			this.rd.setInstrumentName(this.instrumentTitle);
			this.rd.setLangNum(ques.getAnswerLanguageNum());
			this.rd.setQuestionAsAsked(ques.getQuestionAsAsked());
			this.rd.setTimeStamp(new Timestamp(ques.getTimeStamp().getTime()));
			this.rd.setVarName(ques.getLocalName());
			this.rd.setVarNum(this.displayCount) ;// triceps.getCurrentStep());
			this.rd.setWhenAsMS(ques.getTimeStamp().getTime());
			// get event data from triceps

			if (this.phb != null) {
				System.out.println("### in ttc. page hit bean is not null");
				int qi = this.phb.getCurrentQuestonIndex();

				System.out.println("### in tc. qi is "+qi);
				QuestionTimingBean qtb = null;
				try {
					qtb = this.phb.getQuestionTimingBean(ques.getLocalName());
				} catch (IndexOutOfBoundsException iob) {
					qtb = null;
				}
				if (qtb != null) {
					//System.out.println("### in Evidence qtb is not null");
					this.rd.setResponseDuration(qtb.getResponseDuration());
					//System.out.println("### in Evidence responseDuration is :"+qtb.getResponseDuration());
					this.rd.setResponseLatency(qtb.getResponseLatency());
					//System.out.println("### in Evidence responseLatence is :"+qtb.getResponseLatency());
					this.rd.setItemVacillation(qtb.getItemVacillation());
					//System.out.println("### in Evidence  item vacilation is "+qtb.getItemVacillation());
					qi++;
					this.phb.setCurrentQuestionIndex(qi);
					this.phb.setAccessCount(this.displayCount);
					this.phb.setGroupNum(this.groupNum);
					this.phb.setDisplayNum(this.displayCount);
					this.phb.setInstrumentSessionId(this.isb.getInstrumentSessionId());
					
				}
			}
			this.rd.setRawData();
			//System.out.println("### in Evidence raw data has been writen");
		}
	}

	public void processEvents(String eventString){
		System.out.println("In TTC processEvents string is"+eventString);
		if(eventString!= null){
		//this.setPhb(new PageHitBean());
		//System.out.println("got new phb");
		// parse the raw timing data string
			this.getPhb().parseSource(eventString);
			System.out.println("In TTC processEvents parsing source");
		// extract the events and write to pageHitEvents table
		// set variables for page hit level timing
		
		}
	}
	public void setItemMetadata(int accessCount, int groupNum){
		this.accessCount = accessCount;
		this.groupNum = groupNum;
	}
	public PageHitBean getPhb() {
		return phb;
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
		return isb;
	}
	public void setIsb(InstrumentSessionBean isb) {
		this.isb = isb;
	}
	
}