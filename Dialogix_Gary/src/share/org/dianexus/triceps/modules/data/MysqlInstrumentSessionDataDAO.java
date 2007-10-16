package org.dianexus.triceps.modules.data;

import java.sql.*;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class MysqlInstrumentSessionDataDAO implements InstrumentSessionDataDAO {
  static Logger logger = Logger.getLogger(MysqlInstrumentSessionDataDAO.class);
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	
	private int firstGroup; 
	private String instrumentName;
	private int instrumentSessionDataId;
	private String lastAccess;
	private String lastAction;
	private int lastGroup;
	private Timestamp sessionEndTime;
	private Timestamp sessionStartTime;
	private int sessionId;
	private String statusMsg;
	private String tableName;
	private ArrayList dataColumns = new ArrayList();
	private ArrayList dataValues =  new ArrayList();
	private String instanceName;
	
	public boolean setInstrumentSessionDataDAO(String tablename) {
		// store a skeletal record that can be updated as the session progresses
		setTableName(tablename);
		String query = "INSERT INTO "+tablename+" (InstrumentName, InstanceName, StartTime, "
		+ "end_time, first_group, last_group, last_action, last_access, status_message,instrument_session_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setString(1, getInstrmentName());
			ps.setString(2, getInstanceName());
			ps.setTimestamp(3, getSessionStartTime());
			ps.setTimestamp(4, getSessionEndTime());
			ps.setInt(5, getFirstGroup());
			ps.setInt(6, getLastGroup());
			ps.setString(7, getLastAction());
			ps.setString(8, getLastAccess());
			ps.setString(9, getStatusMsg());
			ps.setInt(10, getSessionId());

			ps.execute();
			// get the raw data id as last insert id 
			logger.info(ps.toString());
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if(rs.next()){
				this.setInstrumentSessionDataId(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception fe) {
				logger.error(ps.toString(), fe);
			}
		}
		return true;
	}
	
	public boolean deleteInstrumentSessionDataDAO(String tableName, int id) {
		// delete a row from the session data table using session id
		String query = "DELETE FROM "+tableName+" WHERE instrument_session_id = ?";
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setInt(1, id);
			
			ps.execute();
			logger.info(ps.toString());
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception fe) {
				logger.error(ps.toString(), fe);
			}
		}
		return true;
	}
	
	public boolean getInstrumentSessionDataDAO(String table, int id) {
		// get a row from the session data table
		
		boolean rtn = false;
		String query = "SELECT * FROM "+table+" WHERE instrument_session_id = ?";
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsm = null;
		try {
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			rsm = rs.getMetaData();
			int numCols = rsm.getColumnCount();
			if(rs.next()){
				//TODO complete function
				for(int i=1;i<numCols;i++){
					dataValues.add(rs.getString(i));
					dataColumns.add(rsm.getColumnLabel(i));
				}
				rtn=true;
			}
			logger.info(ps.toString());
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception fe) {
				logger.error(ps.toString(), fe);
			}
		}
		return rtn;
	}
	
	public boolean updateInstrumentSessionDataDAO(String column, String value) {
		// update a column value and latest status
		String query ="UPDATE "+this.getTableName()+" SET "+column+" = ?,last_group = ?, last_action = ?, " +
				" last_access = ?, status_message = ?, end_time = ? WHERE ID = ?";
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setString(1, value);
			ps.setInt(2, getLastGroup());
			ps.setString(3, getLastAction());
			ps.setString(4,getLastAccess());
			ps.setString(5,getStatusMsg());
			ps.setTimestamp(6, getSessionEndTime());	// end time wasn't being recorded
			ps.setInt(7,getInstrumentSessionDataId());
			ps.execute();
			logger.info(ps.toString());
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception fe) {
				logger.error(ps.toString(), fe);
			}
		}
		return true;
	}

	public int getFirstGroup() {
		return firstGroup;
	}

	public String getInstrmentName() {
		return instrumentName;
	}

	public int getInstrumentSessionDataId() {
		return instrumentSessionDataId;
	}

	public String getLastAccess() {
		return lastAccess;
	}

	public String getLastAction() {
		return lastAction;
	}

	public int getLastGroup() {
		return lastGroup;
	}

	public Timestamp getSessionEndTime() {
		return sessionEndTime;
	}

	public int getSessionId() {
		return sessionId;
	}

	public Timestamp getSessionStartTime() {
		return sessionStartTime;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setFirstGroup(int group) {
		firstGroup = group;
	}

	public void setInstrumentName(String name) {
		instrumentName = name;
	}

	public void setInstrumentSessionDataId(int id) {
		instrumentSessionDataId = id;
	}

	public void setLastAccess(String access) {
		lastAccess = access;
	}

	public void setLastAction(String action) {
		lastAction = action;
	}

	public void setLastGroup(int group) {
		lastGroup = group;
	}

	public void setSessionStartTime(Timestamp time) {
		sessionStartTime = time;
	}

	public void setSessionEndTime(Timestamp time) {
		sessionEndTime = time;
	}

	public void setSessionId(int id) {
		sessionId = id;
	}

	public void setStatusMsg(String msg) {
		statusMsg = msg;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String table) {
		tableName = table;
	}
	
	public String getInstanceName() {
		return instanceName;
	}
	
	public void setInstanceName(String name) {
		instanceName = name;
	}
	
	public ArrayList getColumnArray() {
		return dataColumns;
	}
	
	public ArrayList getDataArray() {
		return dataValues;
	}
}
