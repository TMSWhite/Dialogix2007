/**
 */
package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.log4j.Logger;

/**
 * This CLASS provides DAO support for the user_session table. It implements
 * the UserSessionDAO interface.  This  * interface is implemented in this class
 *  to support mysql.
 *
 */
public class MysqlUserSessionDAO implements UserSessionDAO {
  static Logger logger = Logger.getLogger(MysqlUserSessionDAO.class);
	
	private String status;
	private String comments;
	private Timestamp timestamp;
	private int userId;
	private int instrumentSessionId;
	private int userSessionId;
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String SQL_USER_SESSION_NEW = "INSERT INTO user_session SET instrument_session_id = ? , "
			+ " user_id = ?, timestamp = ?, comments = ?, status = ? ";
	private static final String SQL_USER_SESSION_DELETE = "DELETE FROM user_session  WHERE user_session_id = ?";
	private static final String SQL_USER_SESSION_UPDATE = "UPDATE user_session SET instrument_session_id = ? , "
			+ " user_id = ?, timestamp = ?, comments = ?, status = ? WHERE user_session_id = ?";
	private static final String SQL_USER_SESSION_GET = "SELECT * FROM user_session WHERE user_session_id = ?";

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#setUserSession()
	 */
	public boolean setUserSession() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_USER_SESSION_NEW);
			ps.clearParameters();
			ps.setInt(1, instrumentSessionId);
			ps.setInt(2, userId);
			ps.setTimestamp(3, timestamp);
			ps.setString(4, comments);
			ps.setString(5, status);
			
			ps.execute();
			// get the raw data id as last insert id 
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if(rs.next()){
				setUserSessionId(rs.getInt(1));
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
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#getUserSession(int)
	 */
	public boolean getUserSession(int userSessionId) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_USER_SESSION_GET);
			ps.clearParameters();
			ps.setInt(1,userSessionId);
			rs = ps.executeQuery();
			if(rs.next()){
				setInstrumentSessionId(rs.getInt(2));
				setUserId(rs.getInt(3));
				setTimestamp(rs.getTimestamp(4));
				setComments(rs.getString(5));
				setStatus(rs.getString(6));
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
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#updateUserSession()
	 */
	public boolean updateUserSession() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_USER_SESSION_UPDATE);
			ps.clearParameters();
			ps.setInt(1, getInstrumentSessionId());
			ps.setInt(2, getUserId());
			ps.setTimestamp(3, getTimestamp());
			ps.setString(4, getComments());
			ps.setString(5, getStatus());
			ps.setInt(6,getUserSessionId());
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
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#deleteUserSession()
	 */
	public boolean deleteUserSession(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_USER_SESSION_DELETE);
			ps.clearParameters();
			ps.setInt(1,id);
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
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#setUserSessionId(int)
	 */
	public void setUserSessionId(int userSessionId) {
		this.userSessionId = userSessionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#getUserSessionId()
	 */
	public int getUserSessionId() {
		return userSessionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#setInstrumentSessionId(int)
	 */
	public void setInstrumentSessionId(int instrumentSessionId) {
		this.instrumentSessionId = instrumentSessionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#getInstrumentSessionId()
	 */
	public int getInstrumentSessionId() {
		return instrumentSessionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#setUserId(int)
	 */
	public void setUserId(int id) {
		userId = id;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#getUserId()
	 */
	public int getUserId() {
		return userId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#setTimestamp(java.sql.Timestamp)
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#getTimestamp()
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#setComments(java.lang.String)
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#getComments()
	 */
	public String getComments() {
		return this.comments;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#setStatus(java.lang.String)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.UserSessionDAO#getStatus()
	 */
	public String getStatus() {
		return status;
	}
}
