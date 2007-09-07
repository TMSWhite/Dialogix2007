package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlInstrumentInfoDAO implements InstrumentInfoDAO  {
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() from instrument_info";
	private static final String SQL_INSTRUMENT_INFO_NEW = "INSERT INTO instrument_info SET instrument_version_id= ? , instrument_info_name = ?, instrument_info_value=?, instrument_info_memo = ? ";
	private static final String SQL_INSTRUMENT_INFO_DELETE = "DELETE FROM instrument_info WHERE instrument_version_id = ?";
	private static final String SQL_INSTRUMENT_INFO_UPDATE = "UPDATE instrument_info SET instrument_info_name= ? , instrument_info_value = ?, instrument_info_memo = ? WHERE instrument_version_id = ?";
	private static final String SQL_INSTRUMENT_INFO_GET_BYVERSION = "SELECT * FROM instrument_info WHERE instrument_version_id = ? ";
	
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
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_INFO_NEW);
			ps.clearParameters();
			ps.setInt(1, getInstrumentVersionId());
			ps.setString(2, getInstrumentInfoName());
			ps.setString(3, getInstrumentInfoValue());
			ps.setString(4, getInstrumentInfoMemo());
			ps.execute();
			// get the raw data id as last insert id
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				instrumentVersionInfoId=rs.getInt(1);
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
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_INFO_GET_BYVERSION);
			ps.clearParameters();
			ps.setInt(1, versionid);
			rs = ps.executeQuery();
			if (rs.next()) {
				setInstrumentVersionInfoId(rs.getInt(1));
				setInstrumentVersionId(rs.getInt(2));
				setInstrumentInfoName(rs.getString(3));
				setInstrumentInfoValue(rs.getString(4));
				setInstrumentInfoMemo(rs.getString(5));
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
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_INFO_DELETE);
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
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_INFO_UPDATE);
			ps.clearParameters();
			ps.setString(1, getInstrumentInfoName());
			ps.setString(2, getInstrumentInfoValue());
			ps.setString(3, getInstrumentInfoMemo());
			ps.setInt(4, getInstrumentVersionId());
			if (ps.executeUpdate() < 1) {
				return false;
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
		return instrumentVersionInfoId;
	}
		
	public int getInstrumentVersionId(){
		return instrumentVersionId;
	}
	
	public String getInstrumentInfoName(){
		return instrumentInfoName;
	}
	
	public String getInstrumentInfoValue(){
		return instrumentInfoValue;
	}
	
	public String getInstrumentInfoMemo(){
		return instrumentInfoMemo;
	}
		
	public void setInstrumentVersionInfoId(int infoID){
		instrumentVersionInfoId=infoID;
	}
	
	public void setInstrumentVersionId(int versionid){
		instrumentVersionId=versionid;
	}

	public void setInstrumentInfoName(String name){
		instrumentInfoName = name;
	}
	
	public void setInstrumentInfoValue(String value){
		instrumentInfoValue = value;
	}
	
	public void setInstrumentInfoMemo(String memo){
		instrumentInfoMemo=memo;
	}
}
