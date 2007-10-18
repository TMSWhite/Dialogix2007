package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface InstrumentSessionDataDAO {
	
	public boolean setInstrumentSessionDataDAO(String tablename);
	public boolean getInstrumentSessionDataDAO(String table, int id);
	public boolean updateInstrumentSessionDataDAO(String column, String value);

	public void setSessionId(int id);
	public int getSessionId();
	public void setInstrumentSessionDataId(int id);
	public int getInstrumentSessionDataId();
	public void setSessionStartTime(Timestamp time);
	public Timestamp getSessionStartTime();
	public void setSessionLastAccessTime(Timestamp time);
	public Timestamp getSessionLastAccessTime();
	public void setInstrumentStartingGroup (int group);
	public int getInstrumentStartingGroup();
	public void setCurrentGroup (int group);
	public int getCurrentGroup();
	public void setLastAction(String action);
	public String getLastAction();
	public void setStatusMsg(String msg);
	public String getStatusMsg();
	public void setTableName(String table);
	public String getTableName();
	public ArrayList getDataArray();
	public ArrayList getColumnArray(); 
	public void setDisplayNum(int displayNum);
	public int getDisplayNum();
	public boolean update();
	public void setLangCode(String langCode);
	public String getLangCode();
}
