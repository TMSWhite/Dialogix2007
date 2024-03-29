package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MysqlInstrumentDAO implements InstrumentDAO{
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

	private static final String SQL_INSTRUMENT_VERSION_NEW = "INSERT INTO instrument SET  instrument_name= ? ,instrument_description = ? ";


	private static final String SQL_INSTRUMENT_VERSION_DELETE = "DELETE FROM instrument WHERE instrument_id = ?";

	private static final String SQL_INSTRUMENT_VERSION_UPDATE = "UPDATE instrument SET instrument_name= ? , instrument_description = ? where instrument_id = ?";

	private static final String SQL_INSTRUMENT_ID_GET = "SELECT * FROM instrument WHERE instrument_id = ?";
	private static final String SQL_INSTRUMENT_NAME_GET = "SELECT * FROM instrument WHERE  instrument_name = ?";
	
	private String instrumentDescription;
	private int instrumentId;
	private String instrumentName;
	private int lastInsertId;
	
	
	public boolean deleteInstrument(int _id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_DELETE);
			ps.clearParameters();
			ps.setInt(1, this.getInstrumentId());
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


	public boolean updateInstrument(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_UPDATE);
			ps.clearParameters();
			ps.setString(1, instrumentName);
			ps.setString(2, instrumentDescription);
			ps.setInt(3, id);
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

	public boolean getInstrument(int _id) {


		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_ID_GET);
			ps.clearParameters();
			ps.setInt(1, _id);
			
			rs = ps.executeQuery();
			
			
			if(rs.next()){
				rtn=true;
				this.instrumentId = _id;
				this.instrumentName =  rs.getString(2);
				this.instrumentDescription = rs.getString(3);
				
			}
			

		} catch (Exception e) {

			e.printStackTrace();
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
				fe.printStackTrace();
			}
		}
		return rtn;
	}

	public boolean getInstrument(String _name) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_NAME_GET);
			ps.clearParameters();
			ps.setString(1, _name);
			
			rs = ps.executeQuery();
			
			
			if(rs.next()){
				this.instrumentId =  rs.getInt(1);
				this.instrumentName = _name;
				this.instrumentDescription = rs.getString(3);
				rtn=true;
			}
			

		} catch (Exception e) {

			e.printStackTrace();
			rtn=  false;

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
				fe.printStackTrace();
			}
		}
		return rtn;
	}
	public boolean setInstrument() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_NEW);
			ps.clearParameters();
			ps.setString(1, this.getInstrumentName());
			ps.setString(2,this.getInstrumentDescription());
			
			ps.execute();
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				setInstrumentId(rs.getInt(1));
			}
		} catch (Exception e) {

			e.printStackTrace();
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
				fe.printStackTrace();
			}
		}
		return true;
	}

	public String getInstrumentDescription() {
		
		return this.instrumentDescription;
	}

	public int getInstrumentId() {
		
		return this.instrumentId;
	}

	public int getInstrumentLastInsertId() {
		
		return this.lastInsertId;
	}
	public void setInstrumentLastInsertId(int last) {

		this.lastInsertId = last;
		
	}

	public String getInstrumentName() {
		
		return this.instrumentName;
	}



	public void setInstrumentDescription(String instrument_description) {
		this.instrumentDescription = instrument_description;
		
	}

	public void setInstrumentId(int _id) {
		this.instrumentId=_id;
		
	}

	public void setInstrumentName(String _instrumentName) {
		this.instrumentName=_instrumentName;
		
	}

	
	
	

}
