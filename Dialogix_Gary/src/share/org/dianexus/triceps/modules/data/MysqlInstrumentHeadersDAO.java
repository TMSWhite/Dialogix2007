/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** MysqlInstrumentHeadersDAO.java ,v 3.0.0 Created on March 10, 2006, 9:11 AM
 ** @author Gary Lyons
 ******************************************************** */

package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlInstrumentHeadersDAO implements InstrumentHeadersDAO {

	public static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() FROM InstrumentHeaders";
	public static final String SQL_GET_INSTRUMENT_HEADERS = "SELECT * FROM instrumentheaders WHERE ID = ?";
	public static final String SQL_INSERT_INSTRUMENT_HEADERS = "INSERT INTO instrumentheaders VALUES(null,?,?,?)";
	public static final String SQL_DELETE_INSTRUMENT_HEADERS = "DELETE FROM instrumentheaders WHERE ReservedVarName = ?";
	public static final String SQL_DELETE_INSTRUMENT_VERSION_ID_HEADERS = "DELETE FROM instrumentheaders WHERE instrument_version_id = ?";
	public static final String SQL_UPDATE_INSTRUMENT_HEADERS = "UPDATE instrumentheaders SET Value = ? where ReservedVarName = ?";

	private int lastInstrumentHeaderId;
	private int instrumentVersionId;
	private String reservedVarName;
	private String reservedVarValue;

	/** Creates a new instance of MysqlInstrumentHeadersDAO */
	public MysqlInstrumentHeadersDAO() {
	}

	public boolean getInstrumentHeaders(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_GET_INSTRUMENT_HEADERS);
			ps.clearParameters();
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				reservedVarName=rs.getString("ReservedVarName");
				reservedVarValue=rs.getString("Value");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}
		}
		return true;
	}

	public boolean updateInstrumentHeader(String newValue,String oldName) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_UPDATE_INSTRUMENT_HEADERS);
			ps.clearParameters();
			ps.setString(1, newValue);
			ps.setString(2, oldName);
			if (ps.executeUpdate() < 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				fe.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean deleteInstrumentHeader(String varName) {
		
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_DELETE_INSTRUMENT_HEADERS);
			ps.clearParameters();
			ps.setString(1, varName);
			if (ps.executeUpdate() < 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				fe.printStackTrace();
			}
		}
		return true;
	}

	public boolean deleteInstrumentHeader(int instrument_version_id) {

		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_DELETE_INSTRUMENT_VERSION_ID_HEADERS);
			ps.clearParameters();
			ps.setInt(1, instrument_version_id);
			if (ps.executeUpdate() < 1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				fe.printStackTrace();
			}
		}
		return true;
	}

	public boolean setInstrumentHeaders() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_INSERT_INSTRUMENT_HEADERS);
			ps.clearParameters();

			ps.setInt(1, instrumentVersionId);
			ps.setString(2, reservedVarName);
			ps.setString(3, reservedVarValue);
			ps.execute();
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if(rs.next()){
				lastInstrumentHeaderId = rs.getInt(1);
				rtn = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				ee.printStackTrace();
			}
		}
		return rtn;
	}

	public int getInstrumentHeadersLastInsertId() {
		return lastInstrumentHeaderId ;
	}

	public int getInstrumentVersionId() {
		return instrumentVersionId;
	}
	
	public void setInstrumentVersionId(int id) {
		instrumentVersionId = id;
	}

	public String getReservedVarValue() {
		return reservedVarValue;
	}
	
	public void setReserved(String varName, String value) {
		reservedVarName = varName;
		reservedVarValue = value;
	}

	public String getReservedVarName() {
		return reservedVarName;
	}
}
