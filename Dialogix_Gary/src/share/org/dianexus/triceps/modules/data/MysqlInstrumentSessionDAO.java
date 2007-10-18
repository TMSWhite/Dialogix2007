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
	private Timestamp LastAccessTime;
	private int instrumentId;
	private int instrumentVersionId;
	private int userId;
	private int InstrumentStartingGroup;
	private int CurrentGroup;
	private String lastAction;
	private String statusMessage;
	private int displayNum;
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String SQL_INSTRUMENT_SESSION_NEW = "INSERT INTO instrument_session SET start_time = ? , "
			+ " LastAccessTime = ? , instrument_id = ?, instrument_version_id=?, user_id = ? ,"
			+ " InstrumentStartingGroup = ? , CurrentGroup = ? , LastAction = ? ,"
			+ " statusMsg = ? , displayNum = ?";

	private static final String SQL_INSTRUMENT_SESSION_UPDATE = "UPDATE instrument_session SET start_time = ? , "
			+ " LastAccessTime = ? , instrument_id = ? , instrument_version_id=?, user_id = ? ,"
			+ " InstrumentStartingGroup = ? , CurrentGroup = ? , LastAction = ? ,"
			+ " statusMsg = ?, displayNum = ? WHERE instrument_session_id = ?"; 

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
			ps.setTimestamp(2, this.LastAccessTime);
			ps.setInt(3,this.instrumentSessionId);
			ps.setInt(4, this.instrumentVersionId);
			ps.setInt(5, this.userId);
			ps.setInt(6, this.InstrumentStartingGroup);
			ps.setInt(7, this.CurrentGroup);
			ps.setString(8, this.lastAction);
			ps.setString(9,this.statusMessage);
			ps.setInt(10,this.displayNum);
			
			logger.info(ps.toString());
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
			logger.info(ps.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				this.setStartTime(rs.getTimestamp(2));
				this.setLastAccessTime(rs.getTimestamp(3));
				this.setInstrumentId(rs.getInt(4));
				this.setInstrumentVersionId(rs.getInt(5));
				this.setUserId(rs.getInt(6));
				this.setInstrumentStartingGroup(rs.getInt(7));
				this.setCurrentGroup(rs.getInt(8));
				this.setLastAction(rs.getString(9));
				this.setStatusMessage(rs.getString(10));
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
			else if(column.equals("LastAccessTime")){
				ps.setTimestamp(1,this.getLastAccessTime());
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
			else if (column.equals("InstrumentStartingGroup")){
				ps.setInt(1,this.getInstrumentStartingGroup());
			}
			else if (column.equals("CurrentGroup")){
				ps.setInt(1,this.getCurrentGroup());
			}
			else if (column.equals("LastAction")){
				ps.setString(1,this.getLastAction());
			}
			else if (column.equals("statusMsg")){
				ps.setString(1,this.getStatusMessage());
			}
			else if (column.equals("displayNum")){
				ps.setInt(1,this.getDisplayNum());
			}			
			else {
				return false;
			}
			ps.setInt(2,this.getInstrumentSessionId());
			ps.execute();
			logger.info(ps.toString());
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
			ps.setTimestamp(2, this.LastAccessTime);
			ps.setInt(3, this.instrumentId);
			ps.setInt(4, this.instrumentVersionId);
			ps.setInt(5, this.userId);
			ps.setInt(6, this.InstrumentStartingGroup);
			ps.setInt(7, this.CurrentGroup);
			ps.setString(8, this.lastAction);
			ps.setString(9,this.statusMessage);
			ps.setInt(10,this.displayNum);
			ps.setInt(11,this.getInstrumentSessionId());
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
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setLastAccessTime(java.sql.Timestamp)
	 */
	public void setLastAccessTime(Timestamp LastAccessTime) {
		this.LastAccessTime = LastAccessTime;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getLastAccessTime()
	 */
	public Timestamp getLastAccessTime() {
		return this.LastAccessTime;
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
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setInstrumentStartingGroup(int)
	 */
	public void setInstrumentStartingGroup(int InstrumentStartingGroup) {
		this.InstrumentStartingGroup = InstrumentStartingGroup;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getInstrumentStartingGroup()
	 */
	public int getInstrumentStartingGroup() {
		return this.InstrumentStartingGroup;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#setCurrentGroup(int)
	 */
	public void setCurrentGroup(int CurrentGroup) {
		this.CurrentGroup = CurrentGroup;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentSessionDAO#getCurrentGroup()
	 */
	public int getCurrentGroup() {
		return this.CurrentGroup;
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
	
	public void setDisplayNum(int displayNum) {
		this.displayNum = displayNum;
	}
	public int getDisplayNum() {
		return displayNum;
	}
}
