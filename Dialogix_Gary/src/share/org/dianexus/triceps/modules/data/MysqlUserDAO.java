package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
 
public class MysqlUserDAO implements UserDAO {
  static Logger logger = Logger.getLogger(MysqlUserDAO.class);

	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() from users";
	private static final String SQL_USERS_INSERT = "INSERT INTO users SET user_name = ?, password = ?, first_name = ?, last_name = ?, email = ?, phone =?";
	private static final String SQL_USERS_DELETE = "DELETE FROM users where id = ?";
	private static final String SQL_USERS_UPDATE = "UPDATE users SET user_name = ?, password = ?, first_name = ?, last_name = ?, email = ?, phone =? where id =?";
	private static final String SQL_USERS_GET = "SELECT * FROM users WHERE id = ?";
	private static final String SQL_USERS_LOGIN_GET = "SELECT * FROM users WHERE user_name = ? AND password = ?";

	public boolean deleteUser(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;

		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_USERS_DELETE);
			ps.clearParameters();
			ps.setInt(1, id);
			ps.execute();
			ret = true;
		} catch (Exception e) {
			logger.error(ps.toString(), e);
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
		return ret;
	}

	public boolean getUser(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_USERS_GET);
			ps.clearParameters();
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if (rs.next()) {
				setUserName(rs.getString(2));
				setPassword(rs.getString(3));
				setFirstName(rs.getString(4));
				setLastName(rs.getString(5));
				setEmail(rs.getString(6));
				setPhone(rs.getString(7));
				ret = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret = false;
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
		return ret;
	}

	public boolean getUser(String userName, String password) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_USERS_LOGIN_GET);
			ps.clearParameters();
			ps.setString(1, userName);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next()) {
				setId(rs.getInt(1));
				setUserName(rs.getString(2));
				setPassword(rs.getString(3));
				setFirstName(rs.getString(4));
				setLastName(rs.getString(5));
				setPhone(rs.getString(6));
				setEmail(rs.getString(7));
				ret = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret = false;
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
		return ret;
	}

	public boolean setUser() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_USERS_INSERT);
			ps.clearParameters();

			ps.setString(1, getUserName());
			ps.setString(2, getPassword());
			ps.setString(3, getFirstName());
			ps.setString(4, getLastName());
			ps.setString(5, getEmail());
			ps.setString(6, getPhone());
			ps.execute();
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				setId(rs.getInt(1));
				ret = true;
			} else {
				ret = false;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret = false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception fe) {
				logger.error(ps.toString(), fe);
			}
		}
		return ret;
	}

	public boolean updateUser(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		boolean ret = false;
		try {
			ps = con.prepareStatement(SQL_USERS_UPDATE);
			ps.clearParameters();

			ps.setString(1, getUserName());
			ps.setString(2, getPassword());
			ps.setString(3, getFirstName());
			ps.setString(4, getLastName());
			ps.setString(5, getEmail());
			ps.setString(6, getPhone());
			ps.setInt(7, getId());
			if (ps.executeUpdate() > 0) {
				ret = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			ret = false;
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
		return ret;
	}

	public String getEmail() {      
		return email;      
	}

	public String getFirstName() {
		return firstName;
	}

	public int getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
