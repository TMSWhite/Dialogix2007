package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class MysqlLanguagesDAO implements LanguagesDAO {
  static Logger logger = Logger.getLogger(MysqlLanguagesDAO.class);

	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() from languages";
	private static final String SQL_LANGUAGES_SET = "insert into languages SET  language_name= ?, dialogix_abbrev =?, code = ?, description=?";
	private static final String SQL_LANGUAGES_DELETE = "DELETE FROM languages WHERE id = ?";
	private static final String SQL_LANGUAGES_UPDATE = "UPDATE languages SET  language_name= ? ,dialogix_abbrev = ?, code = ?, description = ? WHERE id =?";
	private static final String SQL_LANGUAGES_CODE_GET = "SELECT * FROM languages WHERE  code = ?";
	
	private int id;
	private String languageName;
	private String dialogixAbbrev;
	private String code;
	private String description;
	
	public boolean setLanguagesDAO() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_LANGUAGES_SET);
			ps.clearParameters();
			ps.setString(1,getLanguageName());
			ps.setString(2,getDialogixAbbrev());
			ps.setString(3,getCode());
			ps.setString(4,getDesc());
			ps.execute();
			// get the raw data id as last insert id
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				setId(rs.getInt(1));
				rtn = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			rtn = false;
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
		return rtn;
	}
	
	public boolean getLanguagesDAO(String code) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_LANGUAGES_CODE_GET);
			ps.clearParameters();
			ps.setString(1, languageName);
			rs = ps.executeQuery();
			while (rs.next()) {
				id=rs.getInt("id");
				languageName=rs.getString("language_name");
				dialogixAbbrev=rs.getString("dialogix_abrev");
				code=rs.getString("code");
				description=rs.getString("desc");
			}
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
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return true;
	}
	
	public boolean updateLanguagesDAO() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(SQL_LANGUAGES_UPDATE);
			ps.clearParameters();
			ps.setString(1, languageName);
			ps.setString(2, dialogixAbbrev);
			ps.setString(3, code);
			ps.setString(4, description);
			ps.setInt(5, getId());
			if (ps.executeUpdate() < 1) {
				return false;
			}
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
	
	public boolean deleteLanguagesDAO() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_LANGUAGES_DELETE);
			ps.clearParameters();
			ps.setInt(1, getId());
			if (ps.executeUpdate() < 1) {
				return false;
			}
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

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return description;
	}

	public String getDialogixAbbrev() {
		return dialogixAbbrev;
	}

	public int getId() {
		return id;
	}

	public String getLanguageName() {
		return languageName;
	}
	
	public void setCode(String code) {
		this.code = code;
		
	}

	public void setDesc(String desc) {
		description = desc;
	}

	public void setDialogixAbbrev(String abbrev) {
		dialogixAbbrev = abbrev;
	}

	public void setId(int id) {
		this.id =  id;
	}

	public void setLanguageName(String name) {
		languageName = name;
	}
}
