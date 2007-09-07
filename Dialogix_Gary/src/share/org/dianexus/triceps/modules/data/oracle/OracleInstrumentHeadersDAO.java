package org.dianexus.triceps.modules.data.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import org.dianexus.triceps.modules.data.oracle.DialogixOracleDAOFactory;
import org.dianexus.triceps.modules.data.InstrumentHeadersDAO;

public class OracleInstrumentHeadersDAO implements InstrumentHeadersDAO {

	public static final String ORACLE_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID FROM InstrumentHeaders";
	public static final String ORACLE_GET_INSTRUMENT_HEADERS = "SELECT * FROM instrumentheaders WHERE instrument_version_id = ?";
	public static final String ORACLE_INSERT_INSTRUMENT_HEADERS = "INSERT INTO instrumentheaders VALUES(null,?,?,?)";
	public static final String ORACLE_DELETE_INSTRUMENT_HEADERS = "DELETE FROM instrumentheaders WHERE ReservedVarName = ?";
	public static final String ORACLE_DELETE_INSTRUMENT_VERSION_ID_HEADERS = "DELETE FROM instrumentheaders WHERE instrument_version_id = ?";

	public static final String ORACLE_UPDATE_INSTRUMENT_HEADERS = "UPDATE instrumentheaders SET instrument_version_id= ? , ReservedVarName = ? , Value = ? where ID = ?";
	private int instrumentHeaderId;
	private int instrumentVersionId;
	private String reservedVarName;
	private String reservedVarValue;
	private Hashtable headerValues;

	/** Creates a new instance of MysqlInstrumentHeadersDAO */
	public OracleInstrumentHeadersDAO() {
	}

	public boolean getInstrumentHeaders(int id) {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(ORACLE_GET_INSTRUMENT_HEADERS);
			ps.clearParameters();
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				headerValues.put(rs.getString("reservedVarName"), rs
						.getString("Value"));
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

	public boolean updateInstrumentHeader(String varname, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateInstrumentHeaders() {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(ORACLE_UPDATE_INSTRUMENT_HEADERS);
			ps.clearParameters();
			ps.setInt(1, instrumentVersionId);
			ps.setString(2, reservedVarName);
			ps.setString(3, reservedVarValue);
			ps.setInt(4, this.getInstrumentHeadersLastInsertId());
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

		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(ORACLE_DELETE_INSTRUMENT_HEADERS);
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
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(ORACLE_DELETE_INSTRUMENT_VERSION_ID_HEADERS);
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
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(ORACLE_INSERT_INSTRUMENT_HEADERS);
			ps.clearParameters();

			ps.setInt(1, this.instrumentVersionId);
			ps.setString(2, this.reservedVarName);
			ps.setString(3, this.reservedVarValue);
			ps.execute();
			ps = con.prepareStatement(ORACLE_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if(rs.next()){
				this.instrumentHeaderId = rs.getInt(1);
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
		return this.instrumentHeaderId ;
	}

	public void addReserved(String varName, String value) {
	}

	public int getInstrumentVersionId() {
		return instrumentVersionId;
	}
	
	public void setInstrumentVersionId(int id) {
		instrumentVersionId = id;
	}
	
	public void setReserved(String varName, String value) {
		reservedVarName = varName;
		reservedVarValue = value;
	}

	public String getReservedVarName() {
		return reservedVarName;
	}
	
	public String getReservedVarValue() {
		return reservedVarValue;
	}
}
