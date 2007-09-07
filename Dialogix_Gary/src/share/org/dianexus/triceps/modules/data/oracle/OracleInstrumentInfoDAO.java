package org.dianexus.triceps.modules.data.oracle;

import org.dianexus.triceps.modules.data.oracle.DialogixOracleDAOFactory;
import org.dianexus.triceps.modules.data.InstrumentInfoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OracleInstrumentInfoDAO implements InstrumentInfoDAO  {
	
	private static final String ORACLE_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

	private static final String ORACLE_INSTRUMENT_INFO_NEW = "INSERT INTO instrument_info SET instrument_version_info_id = ? , "
			+ " instrument_version_id= ? , instrument_info_name = ?, instrument_info_value=?, instrument_info_memo = ? ";

	private static final String ORACLE_INSTRUMENT_INFO_DELETE = "DELETE FROM instrument_info WHERE instrument_version_id = ?";

	private static final String ORACLE_INSTRUMENT_INFO_UPDATE = "UPDATE instrument_info SET instrument_version_info_id = ? , "
			+ " instrument_info_name= ? , instrument_info_value = ?, instrument_info_memo = ? WHERE instrument_version_id = ?";
	private static final String ORACLE_INSTRUMENT_INFO_GET_1 = "SELECT * FROM instrument_info WHERE instrument_version_id = ? ";
	
	
	private int instrumentVersionInfoId;
	
	private int instrumentVersionId;
	
	private String instrumentInfoName;

	private String instrumentInfoValue;

	private String instrumentInfoMemo;

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentVersion()
	 */
	public boolean setInstrumentInfo() {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(ORACLE_INSTRUMENT_INFO_NEW);
			ps.clearParameters();
			ps.setInt(1, this.getInstrumentVersionInfoId());
			ps.setInt(2, this.getInstrumentVersionId());
			ps.setString(3, this.getInstrumentInfoName());
			ps.setString(4, this.getInstrumentInfoValue());
			ps.setString(5, this.getInstrumentInfoMemo());
			ps.execute();
			// get the raw data id as last insert id
			ps = con.prepareStatement(ORACLE_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setInstrumentVersionId(rs.getInt(1));
				rtn = true;
			}

		} catch (Exception e) {

			e.printStackTrace();
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
				fe.printStackTrace();
			}
		}
		return rtn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentVersion(int,
	 *      int, int)
	 */
	public boolean getInstrumentInfo(int versionid ) {
		boolean rtn = false;
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(ORACLE_INSTRUMENT_INFO_GET_1);
			ps.clearParameters();
			ps.setInt(1, versionid);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setInstrumentVersionInfoId(rs.getInt(1));
				this.setInstrumentVersionId(rs.getInt(2));
				this.setInstrumentInfoName(rs.getString(3));
				this.setInstrumentInfoValue(rs.getString(4));
				this.setInstrumentInfoMemo(rs.getString(5));
				rtn = true;

			}

		} catch (Exception e) {

			e.printStackTrace();
			rtn=false;

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
		return rtn;
	}
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#deleteInstrumentVersion(int)
	 */
	public boolean deleteInstrumentInfo(int versionid) {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(ORACLE_INSTRUMENT_INFO_DELETE);
			ps.clearParameters();
			ps.setInt(1, versionid);

			rtn = ps.execute();
			rtn = true;
		} catch (Exception e) {

			e.printStackTrace();
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
				fe.printStackTrace();
			}
		}
		return rtn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#updateInstrumentversion(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean updateInstrumentInfo() {
		Connection con = DialogixOracleDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(ORACLE_INSTRUMENT_INFO_UPDATE);
			ps.clearParameters();
			ps.setInt(1, instrumentVersionInfoId);
			ps.setString(2, instrumentInfoName);
			ps.setString(3, instrumentInfoValue);
			ps.setString(4, instrumentInfoMemo);
			ps.setInt(5, this.getInstrumentVersionId());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentNotes()
	 */
	

	public int getInstrumentVersionInfoId(){
		
		return this.instrumentVersionInfoId;
		
	}
		
	public int getInstrumentVersionId(){
		
		return this.instrumentVersionId;
		
	}
	
	public String getInstrumentInfoName(){
		
		return this.instrumentInfoName;
		
	}
	public String getInstrumentInfoValue(){
		
		return this.instrumentInfoValue;
	}
	public String getInstrumentInfoMemo(){
		
		return this.instrumentInfoMemo;
	}
	
	
	
	public void setInstrumentVersionInfoId(int infoid) {
		
		this.instrumentVersionInfoId = infoid;

	}
		
	public void setInstrumentVersionId(int versionid){
	
		this.instrumentVersionId=versionid;
	}

	public void setInstrumentInfoName(String name){
		
		this.instrumentInfoName = name;
	}
	
	public void setInstrumentInfoValue(String value){
		
		this.instrumentInfoValue = value;
	}
	
	public void setInstrumentInfoMemo(String memo){
		
		this.instrumentInfoMemo=memo;
		
	}
	
}











