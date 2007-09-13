package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import org.dianexus.triceps.UserPermission;

public class MysqlUserPermissionDAO implements UserPermissionDAO{
  static Logger logger = Logger.getLogger(MysqlUserPermissionDAO.class);
	private String comment;
	private int instrumentId;
	private int id;
	private String role;
	private int userId;
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() from user_permission";
	private static final String SQL_GET_USER_PERMISSIONS_INSTRUMENT ="SELECT * FROM user_permission WHERE user_id = ? AND instrument_id = ?";
	private static final String	SQL_GET_USER_PERMISSION="SELECT * FROM user_permission WHERE id = ?";
	private static final String SQL_SET_USER_PERMISSION="INSERT INTO user_permission SET user_id = ? , instrument_id = ?, role = ?, comment = ?";
	private static final String SQL_UPDATE_USER_PERMISSION = "UPDATE user_permission SET user_id = ? , instrument_id = ?, role = ?, comment = ? WHERE id=?";
	private static final String SQL_DELETE_USER_PERMISSION = "DELETE FROM user_permission WHERE id = ?";

	public boolean getUserPermission(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_GET_USER_PERMISSION);
			ps.clearParameters();
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.id = rs.getInt(1);
				userId = rs.getInt(2);
				instrumentId = rs.getInt(3);
				role = rs.getString(4);
				comment = rs.getString(5);
				ret = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret= false;
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
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return ret;
	}

	public ArrayList getUserPermissions(int user_id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList permissions = new ArrayList();
		try {
			ps = con.prepareStatement(SQL_GET_USER_PERMISSION);
			ps.clearParameters();

			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserPermission userPermission = new UserPermission();
				userPermission.setPermissionId(rs.getInt(1));
				userPermission.setUserId(rs.getInt(2));
				userPermission.setInstrumentId(rs.getInt(3));
				userPermission.setRole(rs.getString(4));
				userPermission.setComment(rs.getString(5));
				permissions.add(userPermission);
			}
		}catch (Exception e) {
			logger.error(ps.toString(), e);
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

			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return permissions;
	}
	
	public boolean getUserPermission(int user_id, int instrument_id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn=false;
		try {
			ps = con.prepareStatement(SQL_GET_USER_PERMISSIONS_INSTRUMENT);
			ps.clearParameters();
			ps.setInt(1, user_id);
			ps.setInt(2,instrument_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				setPermissionId(rs.getInt(1));
				setUserId(rs.getInt(2));
				setInstrumentId(rs.getInt(3));
				setRole(rs.getString(4));
				setComment(rs.getString(5));
                rtn=true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			rtn=false;
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
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}	
		return rtn;
	}

	public boolean deleteUserPermission(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_DELETE_USER_PERMISSION);
			ps.clearParameters();
			ps.setInt(1, id);
			ps.execute();
			ret = true;
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret= false;
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
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return ret;
	}

	public boolean setUserPermission() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		boolean ret = false;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_SET_USER_PERMISSION);
			ps.clearParameters();
			ps.setInt(1, getUserId());
			ps.setInt(2,getInstrumentId());
			ps.setString(3,getRole());
			ps.setString(4, getComment());
			ps.executeUpdate();
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
			    setPermissionId(rs.getInt(1));
				ret = true;
			} else {
				ret = false;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret= false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}	
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return ret;
	}

	public boolean updateUserPermission(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_UPDATE_USER_PERMISSION);
			ps.clearParameters();
			ps.setInt(1, getUserId());
			ps.setInt(2,getInstrumentId());
			ps.setString(3,getRole());
			ps.setString(4, getComment());
			ps.setInt(5, id);
			ps.executeUpdate();
			ret=true;
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret= false;
		} finally {
			try {		
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}	
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return ret;
	}

	public String getComment() {
		return comment;
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	public int getPermissionId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public int getUserId() {
		return userId;
	}

	public void setComment(String comment) {
		this.comment = comment;	
	}

	public void setInstrumentId(int instrument_id) {
		this.instrumentId = instrument_id;

	}

	public void setPermissionId(int id) {
		this.id = id;	
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUserId(int user_id) {
		this.userId = user_id;
	}
}
