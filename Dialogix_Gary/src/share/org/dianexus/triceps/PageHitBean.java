	
package org.dianexus.triceps;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.*;

import java.util.StringTokenizer;

import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.PageHitsDAO;
import org.apache.log4j.Logger;
 
public class PageHitBean implements VersionIF {
  static Logger logger = Logger.getLogger(PageHitBean.class);
	
	// variables for software state machine
	private static final int LATENCY_EMPTY = 0;
	private static final int LATENCY_START= 1;
	private static final int LATENCY_FINISH = 2;
	private static final int DURATION_EMPTY = 0;
	private static final int DURATION_START = 1;
	private static final int DURATION_FINISH = 2;
	
	// init variables
	private int latencyState = LATENCY_EMPTY;
	private int durationState = DURATION_EMPTY;
	private int pageHitId=0;
	private int instrumentSessionId=0;
	private Timestamp timestamp = new Timestamp (System.currentTimeMillis());
	private int accessCount=0;
	private int groupNum=0;
	private int displayNum=0;
	private String lastAction="";
	private String statusMsg="";
	private int totalDuration=0;
	private int serverDuration=0;
	private int loadDuration=0;
	private int networkDuration=0;
	private int pageVacillation=0;
	private int currentQuestionIndex=0;
	private int numQuestions = 0;
	private long receivedRequest;
	private long lastRecievedRequest;
	private long previousReceivedRequest;
	private long sentResponse;
	private long lastSentResponse;
	private List eventTimingBeans = new ArrayList();
	private List eventAggregates = new ArrayList();
	private Hashtable questionTimingBeans = new Hashtable(); 
	int displayTime=0;
	PageHitsDAO phdao;
	private int responseLatency=0;
	private int responseDuration=0;
	private int itemVacillation=0;
	private int itemVisit=0;
	

	// TODO change this to declarative
	int DBID = 1;
	public PageHitBean() {
	}

	/**
	 * Takes parameter src which contains the event timing data from the 
	 * http request parameter EVENT_TIMINGS and creates an event timing bean for each
	 * event. Event timing beans are stored in the ArrayList eventTimingBeans
	 * 
	 * @param src
	 * @return
	 */
	public boolean parseSource(String src) {
		//logger.debug("## in page hit bean parse source");
		
		int displayCount = 0;
		boolean rtn = false;
		eventTimingBeans.clear();
		String line;
		// break src string up into lines using "\t" as eol character
		StringTokenizer st = new StringTokenizer(src, "\t", false);
		int i = 0;
		while (st.hasMoreTokens()) {
			line = st.nextToken();
			// send each line to a new bean
			EventTimingBean etb = new EventTimingBean().tokenizeEventString(line, displayCount);
			// add bean to array
			
			eventTimingBeans.add(i, etb);
			i++;
			// at least one line processed so return true
			//logger.debug("PageHitBean.parseSource() :: var line is "+line+" var i is "+i);
			rtn = true;
		}
		return rtn;
	}
	/**
	 * processEvents(varName) loops through the array of eventTimingBeans selecting only those with varName
	 * and generates timing values for each item, stored in a rawDataDAO bean. Page level timing is stored 
	 * in pageHitBean with individual event data stored in PageHitEventsBeans. Data is also provided for the
	 * InsturmentSessionDAO and the InstrumentSessionDataDAO. State is maintained by getting 
	 * and setting the  PageHitBean to and from the current Triceps object. 
	 * TODO add docs about interaction with Evidence and TricepsEngine
	 * @return
	 */
	public boolean processEvents(String varName){
		int startTime=0;
		int vacillation =0;
		int	visit=0;
		int responseDuration = 0;
		int	responseLatency = 0;
		int withinViewEventCount = 0;
		
		for (int i = 0; i < eventTimingBeans.size(); i++) {
			EventTimingBean etb = (EventTimingBean) eventTimingBeans.get(i);

			if(etb.getVarName().equals(varName) ){
				++withinViewEventCount;

				if(etb.getEventType().equals("focus")){
					startTime=etb.getDuration();
					withinViewEventCount = 1;	// reset it so know how many events between focus and blur
				}
				else if(etb.getEventType().equals("blur")) {
					if (withinViewEventCount > 2) {
						++visit;
						responseDuration += (etb.getDuration() - startTime);
					}
				} 
				else {
					if (withinViewEventCount == 2) {
						responseLatency += (etb.getDuration() - startTime);
					}
					if(etb.getEventType().equals("change")){	
						++vacillation;
					}
				}
			}
		}
		this.setResponseLatency(responseLatency);
		this.setResponseDuration(responseDuration);
		this.setItemVacillation(vacillation);
		this.setItemVisit(visit);
		
		return true;
	}
	/**
	 * processEvents loops through the array of eventTimingBeans and generates timing values
	 * for each item, stored in a rawDataDAO bean. Page level timing is stored in pageHitBean 
	 * with individual event data stored in PageHitEventsBeans. Data is also provided for the
	 * InsturmentSessionDAO and the InstrumentSessionDataDAO. State is maintained by getting 
	 * and setting the  PageHitBean to and from the current Triceps object. 
	 * TODO add docs about interaction with Evidence and TricepsEngine
	 * @return */
	 	public boolean processEvents(){
		int latencyStart=0;
		int latencyFinish=0;
		int durationStart=0;
		int durationFinish=0;
		int responseLatency =0;
		int responseDuration =0;
		int vacillation = 0;
		String currentVarName="";

		for (int i = 0; i < eventTimingBeans.size(); i++) {
			EventTimingBean etb = (EventTimingBean) eventTimingBeans.get(i);
			//logger.debug("in pageHitBean.processEvents for loop iteration "+i);
			
//			check to see if question is changed	-- FIXME - is this needed?
			if(!etb.getVarName().equals(currentVarName) && !etb.getVarName().equals("") && !etb.equals(null)){
			// This will accumulate and allways be the duration time of the current event
			// resulting in the last duration value for the total duration.
				if(vacillation == 0){
					vacillation =1;
				}
				responseLatency = latencyFinish - latencyStart;
				responseDuration = durationFinish - durationStart;
//				add to aggregate array here
				EventAggregate eg = new EventAggregate();
				eg.setVarName(currentVarName);
				eg.setItemVacillation(vacillation);
				eg.setResponseDuration(responseDuration);
				eg.setResponseLatency(responseLatency);
				this.setEventAggregate(eg);
				responseLatency =0;
				responseDuration =0;
				vacillation = 0;
				currentVarName = etb.getVarName();
			}
			displayTime = etb.getDuration();
			this.setLastAction(etb.getActionType());
			
			// test for the load event (1 per page)
			if (etb.getEventType().equals("load")) {
				// total page rendering time
				// (from time received HTML to time it was displayed to user)
				loadDuration =  etb.getDuration();
				latencyState = PageHitBean.LATENCY_START;
				latencyStart = etb.getDuration();
			} 
			if (etb.getActionType().equals("submit")
					&& etb.getEventType().equals("click")) {
				// page is submitted set all states to empty
				
				latencyState = PageHitBean.LATENCY_EMPTY;
				durationState = PageHitBean.LATENCY_EMPTY;

			}
			if(!etb.getActionType().equals("submit")
					&& etb.getEventType().equals("focus")){
				
				if(latencyState==PageHitBean.LATENCY_FINISH){
				latencyState = PageHitBean.LATENCY_START;
				latencyStart = etb.getDuration();
				
				}

			}
			if(etb.getEventType().equals("blur")){
				if(durationState == PageHitBean.DURATION_START){
					durationFinish = etb.getDuration();
					durationState = PageHitBean.DURATION_FINISH;
				}
				if(latencyState == PageHitBean.LATENCY_START){
					latencyFinish = etb.getDuration();
					latencyState = PageHitBean.LATENCY_FINISH;
					
				}

			}
			if(etb.getEventType().equals("change")){
				
				//	increment item vacillation 
				vacillation++;

				if(latencyState == PageHitBean.LATENCY_START){
					latencyFinish = etb.getDuration();
					latencyState = PageHitBean.LATENCY_FINISH;
					
				}
			}
			if(! etb.getActionType().equals("submit")
					&& etb.getEventType().equals("click")){
				
				if(latencyState==PageHitBean.LATENCY_START){
					latencyState = PageHitBean.LATENCY_FINISH;
					latencyFinish  = etb.getDuration();
					
					}
				if(durationState != PageHitBean.DURATION_EMPTY ||
						durationState != PageHitBean.DURATION_FINISH){
					durationStart = etb.getDuration();
					durationState = PageHitBean.DURATION_START;
				}
			}
			if(etb.getEventType().equals("keypress")){

				if(latencyState==PageHitBean.LATENCY_START){
					latencyState = PageHitBean.LATENCY_FINISH;
					latencyFinish = etb.getDuration();
					}
				if(durationState != PageHitBean.DURATION_EMPTY ||
						durationState != PageHitBean.DURATION_FINISH){
					durationStart = etb.getDuration();
					durationState = PageHitBean.DURATION_START;
				}
			}
			if(etb.getEventType().equals("mouseup")){
				
				if(latencyState==PageHitBean.LATENCY_START){
					latencyState = PageHitBean.LATENCY_FINISH;
					latencyFinish = etb.getDuration();
					}
				if(durationState != PageHitBean.DURATION_EMPTY ||
						durationState != PageHitBean.DURATION_FINISH){
					durationStart = etb.getDuration();
					durationState = PageHitBean.DURATION_START;
				}
			}
			
			//this.setServerDuration(new Long (receivedRequest - lastSentResponse).intValue());
			this.setTotalDuration(new Long(this.receivedRequest - this.lastRecievedRequest  ).intValue() );
			
			timestamp = new Timestamp (System.currentTimeMillis());
			
			this.setNetworkDuration(this.totalDuration - (this.getServerDuration()+ displayTime));
			
			this.setPageVacillation(1);
		}
		
		return true;
	}
	
	 	/**
		 * processPageEvents loops through the array of eventTimingBeans and generates timing values
		 * for at the page hit scope
		 * @return */
	 	
	 	
		 	public boolean processPageEvents(){
			
			
			for (int i = 0; i < eventTimingBeans.size(); i++) {
				EventTimingBean etb = (EventTimingBean) eventTimingBeans.get(i);
				//logger.debug("in pageHitBean.processEvents for loop iteration "+i);
				
				
				logger.debug("display time is"+displayTime);
				// test for the load event (1 per page)
				if (etb.getEventType().equals("load")) {
					// total page rendering time
					// (from time received HTML to time it was displayed to user)
					loadDuration =  etb.getDuration();
					
				} 
				if( etb.getActionType().toLowerCase().trim().equals("submit")
						&& etb.getEventType().equals("click")){
					this.displayTime = etb.getDuration();
					logger.debug("got submit action, display time is "+this.displayTime);
				}
				this.lastAction=etb.getActionType();
				
			}
			
			
			
			logger.debug("display time is "+displayTime);
			return true;
		}
	public boolean update(){
		
		this.setServerDuration(new Long (receivedRequest - sentResponse).intValue());
		this.setTotalDuration(new Long(this.sentResponse - this.lastSentResponse  ).intValue() );
		timestamp = new Timestamp (System.currentTimeMillis());
		this.setNetworkDuration(this.totalDuration - (this.getServerDuration()+ this.displayTime));
		
		this.setPageVacillation(1);

;
		phdao.setAccessCount(this.getAccessCount());
		phdao.setDisplayNum(this.getDisplayNum());
		phdao.setGroupNum(this.getGroupNum());
		phdao.setInstrumentSessionId(this.getInstrumentSessionId());
		phdao.setServerDuration(this.getServerDuration());
		phdao.setTotalDuration(this.getTotalDuration());
		phdao.setLastAction(this.getLastAction());
		phdao.setLoadDuration(this.getLoadDuration());
		phdao.setNetworkDuration(0);//this.getNetworkDuration());
		phdao.setPageHitId(this.getPageHitId());
		phdao.setPageVacillation(this.getPageVacillation());
		phdao.setStatusMessage(this.getStatusMsg());
		return phdao.updatePageHit();
	}
	public boolean store() {
		//logger.debug("## in page hit bean  store");

		DialogixDAOFactory ddf = DialogixDAOFactory.getDAOFactory(DBID);
		phdao = ddf.getPageHitsDAO();
		//TODO find out where to get real value
		
		this.setStatusMsg("OK");
		phdao.setAccessCount(this.getAccessCount());
		phdao.setDisplayNum(this.getDisplayNum());
		phdao.setGroupNum(this.getGroupNum());
		phdao.setInstrumentSessionId(this.getInstrumentSessionId());
		phdao.setServerDuration(this.getServerDuration());
		phdao.setTotalDuration(this.getTotalDuration());
		phdao.setLastAction(this.getLastAction());
		phdao.setLoadDuration(this.getLoadDuration());
		phdao.setNetworkDuration(0);//this.getNetworkDuration());
		phdao.setPageHitId(this.getPageHitId());
		phdao.setPageVacillation(1);
		phdao.setStatusMessage(this.getStatusMsg());
		boolean res = phdao.setPageHit();
		this.pageHitId = phdao.getPageHitId();
		
		// now that we have the pageHitId we can save the individual evenTimingBeans
		for(int i =0; i < eventTimingBeans.size() ;i++){
			//logger.debug("In pageHitBean.store for loop iteration "+i);
			// grab a bean off the array
			EventTimingBean evbean = (EventTimingBean)eventTimingBeans.get(i);
			//set page hit id 
			evbean.setPageHitId(this.pageHitId);
			//write to the database
			evbean.store();
		}

		return res;
	}
	
	public void setNumQuestions(int num){
		this.numQuestions=num;
	}
	
	public int getNumQuestions(){
		return this.numQuestions;
	}
	
	public void setCurrentQuestionIndex(int index){
		this.currentQuestionIndex = index;
	}
	
	public int getCurrentQuestonIndex(){
		return this.currentQuestionIndex;
	}
	 
	public void setReceivedRequest(long _receivedRequest){
		
		this.lastRecievedRequest = this.receivedRequest;
		//logger.debug("## page hit bean setter for recievedRequest last value =:"+this.receivedRequest);
		this.receivedRequest = _receivedRequest;
		//logger.debug("## page hit bean setter for recievedRequest current value =:"+this.receivedRequest);
	}
	
	public long getReceivedRequest(){
		//logger.debug("## page hit bean getter for recievedRequest value =:"+this.receivedRequest);
		return this.receivedRequest;
	}
	
	public void setSentResponse(long response){
		lastSentResponse = sentResponse;
		sentResponse = response;
		//logger.debug("## page hit bean setter for SentResponse value =:"+this.sentResponse);
	}
	
	public long getSentResponse(){
		//logger.debug("## page hit bean getter for SentResponse value =:"+this.sentResponse);
		return this.sentResponse;
	}

	public List getEventTimingBeans() {
		return eventTimingBeans;
	}

	public EventTimingBean getEventTimingBean(int i) {
		return (EventTimingBean) this.eventTimingBeans.get(i);
	}

	public void setEventTimingBeans(List eventTimingBeans) {
		this.eventTimingBeans = eventTimingBeans;
	}
	
	public  Hashtable getQuestionTimingBeans(){
		return questionTimingBeans;
	}
	
	public QuestionTimingBean getQuestionTimingBean(String varName){
		// TODO we need more error checking here. When instruments are mal formed we throw index out of bounds errors on this line
		// FIXME - What is there is no bean for a particular varName?  Try/Catch?
		if(this.questionTimingBeans.size()>0){
		return (QuestionTimingBean) this.questionTimingBeans.get(varName);
		}else{
			return null;
		}
	}
	
	public void setQuestionTimingBeans(Hashtable questionTimingBeans){
		this.questionTimingBeans = questionTimingBeans;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public int getDisplayNum() {
		return displayNum;
	}

	public void setDisplayNum(int displayNum) {
		this.displayNum = displayNum;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public int getInstrumentSessionId() {
		return instrumentSessionId;
	}

	public void setInstrumentSessionId(int instrumentSessionId) {
		this.instrumentSessionId = instrumentSessionId;
	}

	public String getLastAction() {
		return lastAction;
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	public int getLoadDuration() {
		return loadDuration;
	}

	public void setLoadDuration(int loadDuration) {
		this.loadDuration = loadDuration;
		//logger.debug("## page hit bean setter for loadDuration value =:"+loadDuration);
	}

	public int getNetworkDuration() {
		logger.debug("setting network duration as rr ="+this.receivedRequest+" sr ="+this.sentResponse+" dt = "+this.displayTime+" sd = "+this.serverDuration);
		return new Long( this.receivedRequest - this.sentResponse).intValue()- this.displayTime;
	}

	public void setNetworkDuration(int networkDuration) {
		this.networkDuration = networkDuration;
		//logger.debug("##page hit bean setter for networkDuration value =:"+networkDuration);
	}

	public int getPageHitId() {
		return pageHitId;
	}

	public void setPageHitId(int pageHitId) {
		this.pageHitId = pageHitId;
	}

	public int getPageVacillation() {
		return pageVacillation;
	}

	public void setPageVacillation(int pageVacillation) {
		this.pageVacillation = pageVacillation;
	}

	public int getServerDuration() {
		logger.debug("setting server duration as"+new Long(this.sentResponse - this.lastRecievedRequest).intValue());
		return new Long(this.sentResponse - this.lastRecievedRequest).intValue();
		
	}

	public void setServerDuration(int serverDuration) {
		this.serverDuration = serverDuration;
		//logger.debug("##page hit bean setter for serverDuration value =:"+serverDuration);
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
		//logger.debug("## page hit bean setter for statusMsg value =:"+statusMsg);
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
		//logger.debug("##page hit bean setter for timestamp value =:"+timestamp);
	}

	public int getTotalDuration() {
		return new Long(this.receivedRequest - this.lastRecievedRequest).intValue();
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
		//logger.debug("##page hit bean setter for total duration value = :"+totalDuration);
	}
	
	/**
		CHECKME: Updates variable-specific event information into arraylist.
		?? Why not use a Hashtable?
		@param e The variable-specific events
	*/
	public void setEventAggregate(EventAggregate e){
		boolean found = false;
		for(int i = 0 ;i <eventAggregates.size(); i++){
			EventAggregate e1 = (EventAggregate)this.eventAggregates.get(i);
			if (e1.getVarName() != null && e.getVarName().equals(e1.getVarName())){
				e1.setItemVacillation(e1.getItemVacillation()+ e.getItemVacillation());
				e1.setResponseDuration(e1.getResponseDuration() + e.getResponseDuration());
				e1.setResponseLatency(e1.getResponseLatency() + e.getResponseLatency());
				found = true;
			}
		}
		if(!found){
			this.eventAggregates.add(e);
		}
		
		
	}
	public EventAggregate getEventAggregate(EventAggregate d){
		EventAggregate e = null;
	
		for(int i = 0 ;i <eventAggregates.size(); i++){
			if (d.getVarName().equals(((EventAggregate)this.eventAggregates.get(i)).getVarName())){
			
				e =  (EventAggregate)this.eventAggregates.get(i);
				
			}
		}
		return e;
	}
	public EventAggregate getEventAggregateByVarName(String varName){
		EventAggregate e = null;
		
		for(int i = 0 ;i <eventAggregates.size(); i++){
			if (varName.equals(((EventAggregate)this.eventAggregates.get(i)).getVarName())){
			
				e =  (EventAggregate)this.eventAggregates.get(i);
				
			}
		}
		return e;
		
	}
	public void clear(){
		//logger.debug("## page hit bean in clear");
		latencyState = LATENCY_EMPTY;
		durationState = DURATION_EMPTY;
		pageHitId=0;
		instrumentSessionId=0;
		accessCount=0;
		groupNum=0;
		displayNum=0;
		lastAction="";
		statusMsg="";
		totalDuration=0;
		serverDuration=0;
		loadDuration=0;
		networkDuration=0;
		pageVacillation=0;
		currentQuestionIndex=0;
		numQuestions = 0;
		receivedRequest=0;
		sentResponse=0;
		eventTimingBeans = new ArrayList();
		questionTimingBeans = new Hashtable();
	}

	public int getResponseDuration() {
		return responseDuration;
	}

	public void setResponseDuration(int responseDuration) {
		this.responseDuration = responseDuration;
	}

	public int getResponseLatency() {
		return responseLatency;
	}

	public void setResponseLatency(int responseLatency) {
		this.responseLatency = responseLatency;
	}

	public int getItemVacillation() {
		return itemVacillation;
	}

	public void setItemVacillation(int itemVacillation) {
		this.itemVacillation = itemVacillation;
	}
	
	public int getItemVisit() {
		return itemVisit;
	}

	public void setItemVisit(int itemVisit) {
		this.itemVisit = itemVisit;
	}	
}