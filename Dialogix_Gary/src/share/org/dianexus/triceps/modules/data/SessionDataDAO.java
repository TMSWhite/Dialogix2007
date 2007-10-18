package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;

/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** SessionDataDAO.java ,v 3.0.0 Created on February 23, 2006, 11:43 AM
 ** @author Gary Lyons
 ******************************************************** 
 */
 
 
 public interface SessionDataDAO {
     /**
     * create new row in table for update. Needs starting values present??
     */
    public boolean setSessionData(String tableName);
    
    public boolean getSessionData(int sessionDataId);

    /**
     * set the start time for this row
     */
    public void setStartTime(java.sql.Timestamp startTime);
    /**
     * update columns of current row with new data 
     */
    public int updateSessionData(String columnName, String dataValue);

    /**
     * set the starting values for this row
     */
    public boolean setStartingValues(String defaultText, String tableName, String[] colNames); 

    /**
     * set the items for this item TODO is data string OK ??
     */
   
	public Timestamp getStartTime(); 
    public boolean setSessionValue(String col, String data); 
    public void setLastAccessTime(Timestamp LastAccessTime);
	public Timestamp getLastAccessTime(); 
	public void setInstrumentStartingGroup(int InstrumentStartingGroup); 
	public int getInstrumentStartingGroup();
	public void setCurrentGroup(int CurrentGroup); 
	public int getCurrentGroup(); 
	public void setLastAction(int lastAction); 
	public int getLastAction(); 
	public void setStatusMsg(String statusMsg); 
	public String getStatusMsg();
    /**
     * using introspection get all data col values and write default values for each
     */
    public void writeStartingValues(); 
    public String[] getDataColumnNames();
    public String[] getColumnData();
    public int getRowId();
    public void getDaoFactory(); 
 }

