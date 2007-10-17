/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** PageHitsDAO.java ,v 3.0.0 Created on March 20, 2006, 1:14 PM
 ** @author Gary Lyons
 ******************************************************** 
 */
package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;


public interface PageHitsDAO {
	
	public boolean setPageHit();
	/**
	 * @param pageHitId
	 * @return
	 */
	public boolean getPageHit(int pageHitId);
	/**
	 * @param column
	 * @param value
	 * @return
	 */
	public boolean updatePageHitColumn(String column);
	/**
	 * @return
	 */
	public boolean updatePageHit();
	/**
	 * @return
	 */
	public boolean deletePageHit();
	
	public void setPageHitId(int pageHitId);
	public void setInstrumentSessionId(int instrumentSessionId);
	public void setStartingGroupNum(int groupNum);
	public void setEndingGroupNum(int groupNum);
	public void setDisplayNum(int displayNum);
	public void setLastAction(String lastAction);
	public void setStatusMessage(String statusMessage);
	public void setTotalDuration(int totalDuration);
	public void setServerDuration(int serverDuration);
	public void setLoadDuration(int loadDuration);
	public void setNetworkDuration( int networkDuration);
	public void setPageVacillation(int pageVacillation);
	public void setTimeStamp(Timestamp timestamp);
	
	public int getPageHitId();
	public int getInstrumentSessionId();
	public int getStartingGroupNum();
	public int getEndingGroupNum();
	public int getDisplayNum();
	public String getLastAction();
	public String getStatusMessage();
	public int getTotalDuration();
	public int getServerDuration();
	public int getLoadDuration();
	public int getNetworkDuration();
	public int getPageVacillation();
	public Timestamp getTimeStamp();
}
