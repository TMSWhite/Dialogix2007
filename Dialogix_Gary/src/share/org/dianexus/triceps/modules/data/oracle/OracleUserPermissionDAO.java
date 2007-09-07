package org.dianexus.triceps.modules.data.oracle;

import org.dianexus.triceps.modules.data.oracle.DialogixOracleDAOFactory;
import org.dianexus.triceps.modules.data.UserPermissionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.dianexus.triceps.UserPermission;

public class OracleUserPermissionDAO implements UserPermissionDAO{
	private ArrayList userPermissions;
	private String comment = "";
	private int instrumentId;
	private int permissionId;
	private String role ="";
	private int userId;

	private static final String ORACLE_GET_USER_PERMISSIONS_INSTRUMENT ="SELECT * FROM user_permission WHERE user_id = ? AND instrument_id = ?";
	private static final String	ORACLE_GET_USER_PERMISSION="SELECT * FROM user_permission WHERE user_id = ?";
	private static final String ORACLE_SET_USER_PERMISSION="INSERT INTO user_permission SET user_id = ? , instrument_id = ?, role = ?, comment = ?";
	private static final String ORACLE_UPDATE_USER_PERMISSION = "UPDATE user_permission SET user_id = ? , instrument_id = ?, role = ?, comment = ?";
	private static final String ORACLE_DELETE_USER_PERMISSION = "DELETE FROM user_permission WHERE id = ?";

	public boolean getUserPermission(int permission_id) {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(ORACLE_GET_USER_PERMISSION);
			ps.clearParameters();

			ps.setInt(1, permission_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.permissionId = rs.getInt(1);
				this.userId = rs.getInt(2);
				this.instrumentId = rs.getInt(3);
				this.role = rs.getString(4);
				this.comment = rs.getString(5);
				this.userPermissions.clear();
				this.userPermissions.add(this.role);
				ret = true;



			}

		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}

		}


		return ret;
	}
	public ArrayList getUserPermissions(int user_id) {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList permissions = new ArrayList();
		try {
			ps = con.prepareStatement(ORACLE_GET_USER_PERMISSION);
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
		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}
		}
		return permissions;
	}

	public boolean getUserPermission(int user_id, int instrument_id) {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn=false;
		try {
			ps = con.prepareStatement(ORACLE_GET_USER_PERMISSIONS_INSTRUMENT);
			ps.clearParameters();

			ps.setInt(1, user_id);
			ps.setInt(2,instrument_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				setPermissionId(rs.getInt(1));
				setUserId(rs.getInt(2));
				setInstrumentId(rs.getInt(3));
				setRole(rs.getString(4));
				setComment(rs.getString(5));
				rtn=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}
		}
		return rtn;
	}

	public boolean deleteUserPermission(int id) {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(ORACLE_DELETE_USER_PERMISSION);
			ps.clearParameters();
			ps.setInt(1, id);
			ps.execute();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}
		}
		return ret;
	}

	public boolean setUserPermission() {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;

		boolean ret = false;
		try {
			ps = con.prepareStatement(ORACLE_SET_USER_PERMISSION);
			ps.clearParameters();
			ps.setInt(1, this.getUserId());
			ps.setInt(2,this.getInstrumentId());
			ps.setString(3,this.getRole());
			ps.setString(4, this.getComment());

			ps.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}
		}
		return ret;
	}

	public boolean updateUserPermission(int id) {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;

		boolean ret = false;
		try {
			ps = con.prepareStatement(ORACLE_UPDATE_USER_PERMISSION);

			ps.clearParameters();
			ps.setInt(1, this.getUserId());
			ps.setInt(2,this.getInstrumentId());
			ps.setString(3,this.getRole());
			ps.setString(4, this.getComment());

			ps.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}

		}


		return ret;
	}
	public String getComment() {
		return this.comment;
	}

	public int getInstrumentId() {
		return this.instrumentId;
	}

	public int getPermissionId() {
		return this.permissionId;
	}

	public String getRole() {
		return this.role;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setInstrumentId(int instrument_id) {
		this.instrumentId = instrument_id;
	}

	public void setPermissionId(int id) {
		this.permissionId = id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUserId(int user_id) {
		this.userId = user_id;
	}
}

