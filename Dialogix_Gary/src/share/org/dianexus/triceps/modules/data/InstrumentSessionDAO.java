/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** InstrumentSessionDAO.java ,v 3.0.0 Created on March 20, 2006, 1:14 PM
 ** @author Gary Lyons
 ******************************************************** 
 */
package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;

/** 
 * @author ISTCGXL
 *
 */
public interface InstrumentSessionDAO {
	
	public boolean setInstrumentSession();
	/**
	 * @param pageHitId
	 * @return
	 */
	public boolean getInstrumentSession(int pageHitId);
	/**
	 * @param column
	 * @param value
	 * @return
	 */
	public boolean updateInstrumentSessionColumn(String column);
	/**
	 * @return
	 */
	public boolean updateInstrumentSession();
	/**
	 * @return
	 */
	public void setInstrumentSessionId(int instrumentSessionId);
	public int getInstrumentSessionId();
	public void setStartTime (Timestamp startTime);
	public Timestamp getStartTime();
	public void setLastAccessTime(Timestamp LastAccessTime);
	public Timestamp getLastAccessTime();
	public void setInstrumentVersionId(int instrumentVersionId);
	public int getInstrumentVersionId();
	public void setUserId(int userId);
	public int getUserId();
	public void setInstrumentStartingGroup(int InstrumentStartingGroup);
	public int getInstrumentStartingGroup();
	public void setCurrentGroup(int CurrentGroup);
	public int getCurrentGroup();
	public void setLastAction(String lastAction);
	public String getLastAction();
	public void setStatusMessage(String statusMessage);
	public String getStatusMessage();
	public void setInstrumentId(int id);
	public int getInstrumentId();
	public void setDisplayNum(int displayNum);
	public int getDisplayNum();	
}
