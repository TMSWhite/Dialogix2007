package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
 
/**
 * @author ISTCGXL
 * 
 */
public class MysqlInstrumentVersionDAO implements InstrumentVersionDAO {
  static Logger logger = Logger.getLogger(MysqlInstrumentVersionDAO.class);

	private String instrumentNotes;
	private String instanceTableName;
	private int instrumentId;
	private int instrumentStatus;
	private int instrumentVersionId;
	private int instrumentVersionMajor;
	private int instrumentVersionMinor;
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String SQL_INSTRUMENT_VERSION_NEW = "INSERT INTO instrument_version SET instrument_id = ? , "
			+ " instance_table_name= ? , major_version = ?, minor_version=?, instrument_notes = ? , instrument_status = ? ";
	private static final String SQL_INSTRUMENT_VERSION_DELETE = "DELETE FROM instrument_version WHERE instrument_version_id = ?";
	private static final String SQL_INSTRUMENT_VERSION_UPDATE = "UPDATE instrument_version SET instrument_id = ? , "
			+ " instance_table_name= ? , major_version = ?, minor_version = ?, instrument_notes = ? , instrument_status = ? WHERE instrument_version_id = ?";
	private static final String SQL_INSTRUMENT_VERSION_GET_INDEX = "SELECT * FROM instrument_version WHERE instrument_version_id = ? ";
	private static final String SQL_INSTRUMENT_VERSION_GET_VERSION = "SELECT * FROM instrument_version WHERE instrument_id = ? AND major_version = ? AND minor_version =?";
	private static final String SQL_INSTRUMENT_VERSION_MAJOR_EXISTS = "SELECT * FROM instrument_version WHERE instrument_id = ? AND major_version = ? ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentVersion()
	 */
	public boolean setInstrumentVersion() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_NEW);
			ps.clearParameters();
			ps.setInt(1, this.getInstrumentId());
			ps.setString(2, this.getInstanceTableName());
			ps.setInt(3, this.getInstrumentVersionMajor());
			ps.setInt(4, this.getInstrumentVersionMinor());
			ps.setString(5, this.getInstrumentNotes());
			;
			ps.setInt(6, this.getInstrumentStatus());

			ps.execute();
			// get the raw data id as last insert id
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setInstrumentVersionId(rs.getInt(1));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentVersion(int,
	 *      int, int)
	 */
	public boolean getInstrumentVersion(int id ) {
		boolean rtn = false;
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_GET_INDEX);
			ps.clearParameters();
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				setInstrumentVersionId(rs.getInt(1));
				setInstrumentId(rs.getInt(2));
				setInstanceTableName(rs.getString(3));
				setInstrumentVersionMajor(rs.getInt(4));
				setInstrumentVersionMinor(rs.getInt(5));
				setInstrumentNotes(rs.getString(6));
				setInstrumentStatus(rs.getInt(7));
				rtn = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
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
				logger.error(ps.toString(), fe);
			}
		}
		return rtn;
	}
	
	public boolean getInstrumentVersion(int id,int major,int minor) {
		boolean rtn = false;
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_GET_VERSION);
			ps.clearParameters();
			ps.setInt(1, id);
			ps.setInt(2, major);
			ps.setInt(3, minor);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setInstrumentVersionId(rs.getInt(1));
				this.setInstrumentId(rs.getInt(2));
				this.setInstanceTableName(rs.getString(3));
				this.setInstrumentVersionMajor(rs.getInt(4));
				this.setInstrumentVersionMinor(rs.getInt(5));
				this.setInstrumentNotes(rs.getString(6));
				this.setInstrumentStatus(rs.getInt(7));
				rtn = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
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
				logger.error(ps.toString(), fe);
			}
		}
		return rtn;
	}
	
	public boolean InstrumentMajorVersionExists(int id, int major ) {
		boolean rtn = false;
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_MAJOR_EXISTS);
			ps.clearParameters();
			ps.setInt(1, id);
			ps.setInt(2, major);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setInstanceTableName(rs.getString(3));
				rtn = true;
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
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
				logger.error(ps.toString(), fe);
			}
		}
		return rtn;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#deleteInstrumentVersion(int)
	 */
	public boolean deleteInstrumentVersion(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_DELETE);
			ps.clearParameters();
			ps.setInt(1, id);

			rtn = ps.execute();
			rtn = true;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#updateInstrumentversion(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean updateInstrumentversion() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_INSTRUMENT_VERSION_UPDATE);
			ps.clearParameters();
			ps.setInt(1, instrumentId);
			ps.setString(2, instanceTableName);
			ps.setInt(3, instrumentVersionMajor);
			ps.setInt(4, instrumentVersionMinor);
			ps.setString(5, instrumentNotes);
			ps.setInt(6, instrumentStatus);
			ps.setInt(7, this.getInstrumentVersionId());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentNotes()
	 */
	public String getInstrumentNotes() {
		return this.instrumentNotes;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstanceTableName()
	 */
	public String getInstanceTableName() {
		return this.instanceTableName;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentId()
	 */
	public int getInstrumentId() {
		return this.instrumentId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentStatus()
	 */
	public int getInstrumentStatus() {
		return this.instrumentStatus;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentVersionId()
	 */
	public int getInstrumentVersionId() {
		return this.instrumentVersionId;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentVersionLastInsertId()
	 */
	public int getInstrumentVersionLastInsertId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentNotes(java.lang.String)
	 */
	public void setInstrumentNotes(String notes) {
		this.instrumentNotes = notes;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstanceTableName(java.lang.String)
	 */
	public void setInstanceTableName(String name) {
		this.instanceTableName = name;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentId(int)
	 */
	public void setInstrumentId(int id) {
		this.instrumentId = id;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentStatus(int)
	 */
	public void setInstrumentStatus(int status) {
		this.instrumentStatus = status;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentVersionId(int)
	 */
	public void setInstrumentVersionId(int id) {
		this.instrumentVersionId = id;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentVersionMajor()
	 */
	public int getInstrumentVersionMajor() {
		return this.instrumentVersionMajor;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#getInstrumentVersionMinor()
	 */
	public int getInstrumentVersionMinor() {
		return this.instrumentVersionMinor;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentVersionMajor(int)
	 */
	public void setInstrumentVersionMajor(int version) {
		this.instrumentVersionMajor = version;
	}

	/* (non-Javadoc)
	 * @see org.dianexus.triceps.modules.data.InstrumentVersionDAO#setInstrumentVersionMinor(int)
	 */
	public void setInstrumentVersionMinor(int version) {
		this.instrumentVersionMinor=version;
	}
}
