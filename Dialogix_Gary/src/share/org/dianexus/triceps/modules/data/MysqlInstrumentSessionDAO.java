package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
 import org.apache.log4j.Logger;

/**
 * @author ISTCGXL
 *
 */
public class MysqlInstrumentSessionDAO implements InstrumentSessionDAO {
  static Logger logger = Logger.getLogger(MysqlInstrumentSessionDAO.class);
	
	private int instrumentSessionId;
	private Timestamp startTime;
	private Timestamp endTime;
	private int instrumentId;
	private int instrumentVersionId;
	private int userId;
	private int firstGroup;
	private int lastGroup;
	private String lastAction;
	private String lastAccess;
	private String statusMessage;
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String SQL_INSTRUMENT_SESSION_NEW = "INSERT INTO instrument_session SET start_time = ? , "
			+ " end_time = ? , instrument_id = ?, instrument_version_id=?, user_id = ? ,"
			+ " first_group = ? , last_group = ? , last_action = ? ,"
			+ " last_access = ? , statusMsg = ? ";

	private static final String SQL_INSTRUMENT_SESSION_DELETE = "DELETE FROM instrument_session WHERE instrument_session_id = ?";
	private static final String SQL_INSTRUMENT_SESSION_UPDATE = "UPDATE instrument_session SET start_time = ? , "
			+ " end_time = ? , instrument_id = ? , instrument_version_id=?, user_id = ? ,"
			+ " first_group = ? , last_group = ? , last_action = ? ,"
			+ " last_access = ? , statusMsg = ? WHERE instrument_session_id = ?"; 

	private static final String SQL_INSTRUMENT_SESSION_GET = "SELECT * FROM instrument_session WHERE instrument_session_id = ?";
	
	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setInstrumentSession()
	 */
	public boolean setInstrumentSession() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_SESSION_NEW);
			ps.clearParameters();
			ps.setTimestamp(1, this.startTime);
			ps.setTimestamp(2, this.endTime);
			ps.setInt(3,this.instrumentSessionId);
			ps.setInt(4, this.instrumentVersionId);
			ps.setInt(5, this.userId);
			ps.setInt(6, this.firstGroup);
			ps.setInt(7, this.lastGroup);
			ps.setString(8, this.lastAction);
			ps.setString(9, this.lastAccess);
			ps.setString(10,this.statusMessage);
			
			ps.execute();
			// get the raw data id as last insert id 
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if(rs.next()){
				this.setInstrumentSessionId(rs.getInt(1));
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

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getInstrumentSession(int)
	 */
	public boolean getInstrumentSession(int instrumentSessionId) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_SESSION_GET);
			ps.clearParameters();
			ps.setInt(1,instrumentSessionId);
			rs = ps.executeQuery();
			if(rs.next()){
				this.setStartTime(rs.getTimestamp(2));
				this.setEndTime(rs.getTimestamp(3));
				this.setInstrumentId(rs.getInt(4));
				this.setInstrumentVersionId(rs.getInt(5));
				this.setUserId(rs.getInt(6));
				this.setFirstGroup(rs.getInt(7));
				this.setLastGroup(rs.getInt(8));
				this.setLastAction(rs.getString(9));
				this.setLastAccess(rs.getString(10));
				this.setStatusMessage(rs.getString(11));
			}
			else{
				return false;
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

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#updateInstrumentSessionColumn(java.lang.String)
	 */
	public boolean updateInstrumentSessionColumn(String column) {
		String SQL_INSTRUMENT_SESSION_COLUMN_UPDATE = "UPDATE instrument_session SET "+column+" = ?  WHERE instrument_session_id = ?";
//		 get the connection
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_SESSION_COLUMN_UPDATE);
			ps.clearParameters();
			if(column.equals("start_time")){
				ps.setTimestamp(1,this.getStartTime());
			}
			else if(column.equals("end_time")){
				ps.setTimestamp(1,this.getEndTime());
			}
			else if (column.equals("instrument_version_id")){
				ps.setInt(1,this.getInstrumentVersionId());
			}
			else if (column.equals("instrument_id")){
				ps.setInt(1,this.getInstrumentId());
			}
			else if (column.equals("user_id")){
				ps.setInt(1,this.getUserId());
			}
			else if (column.equals("first_group")){
				ps.setInt(1,this.getFirstGroup());
			}
			else if (column.equals("last_group")){
				ps.setInt(1,this.getLastGroup());
			}
			else if (column.equals("last_action")){
				ps.setString(1,this.getLastAction());
			}
			else if (column.equals("last_access")){
				ps.setString(1,this.getLastAccess());
			}
			else if (column.equals("statusMsg")){
				ps.setString(1,this.getStatusMessage());
			}
			else {
				return false;
			}
			ps.setInt(2,this.getInstrumentSessionId());
			ps.execute();
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if (rs != null) {
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

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#updateInstrumentSession()
	 */
	public boolean updateInstrumentSession() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_SESSION_UPDATE);
			ps.clearParameters();
			ps.setTimestamp(1, this.startTime);
			ps.setTimestamp(2, this.endTime);
			ps.setInt(3, this.instrumentId);
			ps.setInt(4, this.instrumentVersionId);
			ps.setInt(5, this.userId);
			ps.setInt(6, this.firstGroup);
			ps.setInt(7, this.lastGroup);
			ps.setString(8, this.lastAction);
			ps.setString(9, this.lastAccess);
			ps.setString(10,this.statusMessage);
			ps.setInt(11,this.getInstrumentSessionId());
			ps.execute();
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

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#deleteInstrumentSession()
	 */
	public boolean deleteInstrumentSession() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_SESSION_DELETE);
			ps.clearParameters();
			ps.setInt(1,this.instrumentSessionId);
			ps.execute();
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

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setInstrumentSessionId(int)
	 */
	public void setInstrumentSessionId(int instrumentSessionId) {
		this.instrumentSessionId = instrumentSessionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getInstrumentSessionId()
	 */
	public int getInstrumentSessionId() {
		return this.instrumentSessionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setStartTime(java.sql.Timestamp)
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getStartTime()
	 */
	public Timestamp getStartTime() {
		return this.startTime;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setEndTime(java.sql.Timestamp)
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getEndTime()
	 */
	public Timestamp getEndTime() {
		return this.endTime;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setInstrumentVersionId(int)
	 */
	public void setInstrumentVersionId(int instrumentVersionId) {
		this.instrumentVersionId = instrumentVersionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getInstrumentVersionId()
	 */
	public int getInstrumentVersionId() {
		return this.instrumentVersionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setUserId(int)
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getUserId()
	 */
	public int getUserId() {
		return this.userId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setFirstGroup(int)
	 */
	public void setFirstGroup(int firstGroup) {
		this.firstGroup = firstGroup;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getFirstGroup()
	 */
	public int getFirstGroup() {
		return this.firstGroup;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setLastGroup(int)
	 */
	public void setLastGroup(int lastGroup) {
		this.lastGroup = lastGroup;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getLastGroup()
	 */
	public int getLastGroup() {
		return this.lastGroup;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setLastAction(java.lang.String)
	 */
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getLastAction()
	 */
	public String getLastAction() {
		return this.lastAction;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setLastAccess(java.lang.String)
	 */
	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getLastAccess()
	 */
	public String getLastAccess() {
		return this.lastAccess;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setStatusMessage(java.lang.String)
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getStatusMessage()
	 */
	public String getStatusMessage() {
			return this.statusMessage;
	}
	
	public int getInstrumentId(){
		return instrumentId;
	}
	
	public void setInstrumentId(int id){
		instrumentId=id;
	}
}
