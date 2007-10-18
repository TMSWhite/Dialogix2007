package org.dianexus.triceps;

import java.sql.Timestamp;

import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.InstrumentSessionDAO;
import org.apache.log4j.Logger;

public class InstrumentSessionBean implements VersionIF {
  static Logger logger = Logger.getLogger(InstrumentSessionBean.class);
 	int instrumentSessionId;
	Timestamp start_time;
	Timestamp LastAccessTime;
	int instrumentVersionId; 
	int userId;
	int instrumentId;
	int InstrumentStartingGroup=0;
	int CurrentGroup;
	String lastAction;
	String statusMessage;
	int displayNum;
	//TODO make declarative
	private int DBID=1;
        
	boolean store(){
		DialogixDAOFactory ddf = DialogixDAOFactory.getDAOFactory(DBID);
		InstrumentSessionDAO isdao = ddf.getInstrumentSessionDAO();
		isdao.setStartTime(this.getStart_time());
		isdao.setLastAccessTime(this.getLastAccessTime());
		isdao.setInstrumentId(this.getInstrumentId());  // XXX This seems wrong
		isdao.setInstrumentVersionId(this.getInstrumentVersionId());
		isdao.setUserId(this.getUserId());
		isdao.setInstrumentStartingGroup(this.getInstrumentStartingGroup());
		isdao.setCurrentGroup(this.getCurrentGroup());
		isdao.setLastAction(this.getLastAction());
		isdao.setStatusMessage(this.getStatusMessage());
		isdao.setDisplayNum(this.getDisplayNum());
		boolean rtn = isdao.setInstrumentSession();
		this.setInstrumentSessionId(isdao.getInstrumentSessionId());
		return rtn;
	}
	boolean load(){
		return false;
	}
	boolean update(){   // XXX Values don't seem to be getting set - check setters
		DialogixDAOFactory ddf = DialogixDAOFactory.getDAOFactory(DBID);
		InstrumentSessionDAO isdao = ddf.getInstrumentSessionDAO();
		isdao.setStartTime(this.getStart_time());
		isdao.setLastAccessTime(this.getLastAccessTime());
		isdao.setInstrumentId(this.getInstrumentId());
		isdao.setInstrumentVersionId(this.getInstrumentVersionId());
		isdao.setUserId(this.getUserId());
		isdao.setInstrumentStartingGroup(this.getInstrumentStartingGroup());
		isdao.setCurrentGroup(this.getCurrentGroup());
		isdao.setLastAction(this.getLastAction());
		isdao.setStatusMessage(this.getStatusMessage());
		isdao.setInstrumentSessionId(this.getInstrumentSessionId());
		isdao.setDisplayNum(this.getDisplayNum());
		boolean rtn = isdao.updateInstrumentSession();
		
		return rtn;
	}

	public void setGroup(int group){
		
		if( this.getInstrumentStartingGroup()==0){
			this.setInstrumentStartingGroup(group);
		}
		this.setCurrentGroup(group);
	}
	
	
	
	public Timestamp getLastAccessTime() {
		return LastAccessTime;
	}
	public void setLastAccessTime(Timestamp LastAccessTime) {
		this.LastAccessTime = LastAccessTime;
	}
	public int getInstrumentStartingGroup() {
		return InstrumentStartingGroup;
	}
	public void setInstrumentStartingGroup(int InstrumentStartingGroup) {
		this.InstrumentStartingGroup = InstrumentStartingGroup;
	}
	public int getInstrumentSessionId() {
		return instrumentSessionId;
	}
	public void setInstrumentSessionId(int instrumentSessionId) {
		this.instrumentSessionId = instrumentSessionId;
	}
	public int getInstrumentVersionId() {
		return instrumentVersionId;
	}
	public void setInstrumentVersionId(int instrumentVersionId) {
		this.instrumentVersionId = instrumentVersionId;
	}
	public String getLastAction() {
		return lastAction;
	}
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}
	public int getCurrentGroup() {
		return CurrentGroup;
	}
	public void setCurrentGroup(int CurrentGroup) {
		this.CurrentGroup = CurrentGroup;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;   // XXX This is never being called - should be instrument_version.instrument_id
	}
	
	public void setDisplayNum(int displayNum) {
		this.displayNum  = displayNum ;
	}
	
	public int getDisplayNum() {
		return this.displayNum;
	}
	
	
	
	
	

}
