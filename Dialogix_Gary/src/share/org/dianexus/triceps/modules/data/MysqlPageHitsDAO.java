/**
 * 
 */
package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.log4j.Logger;
 
/**
 * @author ISTCGXL
 * 
 */
public class MysqlPageHitsDAO implements PageHitsDAO {
  static Logger logger = Logger.getLogger(MysqlPageHitsDAO.class);

	private int pageVacillation;
	private int networkDuration;
	private int loadDuration;
	private int serverDuration;
	private int totalDuration;
	private String statusMessage;
	private String lastAction;
	private int displayNum;
	private int FromGroupNum;
	private int ToGroupNum;
	private int instrumentSessionId;
	private int pageHitId;
	private Timestamp timestamp;
	public int submitClick;
	public int loadTime;
	public int initTime;
	public int displayTime;
	private String langCode;
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String SQL_PAGEHITS_NEW = "INSERT INTO pagehits SET instrument_session_id = ? , "
			+ " timeStamp = ? , FromGroupNum = ? ,"
			+ " displayNum = ? , langCode = ?, lastAction = ? , statusMsg = ? ,"
			+ " totalDuration = ? , serverDuration = ? , loadDuration = ? ,"
			+ " networkDuration = ? , pageVacillation = ?, ToGroupNum = ? ";

	private static final String SQL_PAGEHITS_UPDATE = "UPDATE pagehits SET instrument_session_id = ? , "
			+ " timeStamp = ? , FromGroupNum = ? ,"
			+ " displayNum = ? , langCode = ?, lastAction = ? , statusMsg = ? ,"
			+ " totalDuration = ? , serverDuration = ? , loadDuration = ? ,"
			+ " networkDuration = ? , pageVacillation = ?, ToGroupNum = ? WHERE pageHitId = ?";
	private static final String SQL_PAGEHITS_GET = "SELECT * FROM pagehits WHERE pageHitId = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setPageHit()
	 */
	public boolean setPageHit() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs =null;
		try {
			ps = con.prepareStatement(SQL_PAGEHITS_NEW);
			ps.clearParameters();
			ps.setInt(1, this.getInstrumentSessionId());
			ps.setTimestamp(2, this.getTimeStamp());
			ps.setInt(3, this.getFromGroupNum());
			ps.setInt(4, this.getDisplayNum());
			ps.setString(5, this.getLangCode());
			ps.setString(6, this.getLastAction());
			ps.setString(7, this.getStatusMessage());
			ps.setInt(8, this.getTotalDuration());
			ps.setInt(9, this.getServerDuration());
			ps.setInt(10, this.getLoadDuration());
			ps.setInt(11, this.getNetworkDuration());
			ps.setInt(12, this.getPageVacillation());
			ps.setInt(13, this.getToGroupNum());

			ps.execute();
			logger.info(ps.toString());
			// get the raw data id as last insert id
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setPageHitId(rs.getInt(1));
			}
			else{
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
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getPageHit(int)
	 */
	public boolean getPageHit(int pageHitId) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_PAGEHITS_GET);
			ps.clearParameters();
			ps.setInt(1, this.getPageHitId());
			logger.info(ps.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setInstrumentSessionId(rs.getInt(2));
				this.setTimeStamp(rs.getTimestamp(3));
				this.setFromGroupNum(rs.getInt(4));
				this.setDisplayNum(rs.getInt(5));
				this.setLastAction(rs.getString(6));
				this.setStatusMessage(rs.getString(7));
				this.setTotalDuration(rs.getInt(8));
				this.setServerDuration(rs.getInt(9));
				this.setLoadDuration(rs.getInt(10));
				this.setNetworkDuration(rs.getInt(11));
				this.setPageVacillation(rs.getInt(12));
			}
			else{
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
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#updatePageHitColumn(java.lang.String)
	 */
	public boolean updatePageHitColumn(String column) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;

		String SQL_PAGEHITS_UPDATE = "UPDATE pagehits SET " + column
				+ " = ?   WHERE pageHitId = ?";
		try {
			ps = con.prepareStatement(SQL_PAGEHITS_UPDATE);
			ps.clearParameters();
			if (column.equals("instrument_session_id")) {
				ps.setInt(1, this.getInstrumentSessionId());
			} else if (column.equals("timeStamp")) {
				ps.setTimestamp(1, this.getTimeStamp());
			} else if (column.equals("FromGroupNum")) {
				ps.setInt(1, this.getFromGroupNum());
			} else if (column.equals("ToGroupNum")) {
				ps.setInt(1, this.getToGroupNum());				
			} else if (column.equals("displayNum")) {
				ps.setInt(1, this.getDisplayNum());
			} else if (column.equals("langCode")) {
				ps.setString(1, this.getLangCode());				
			} else if (column.equals("lastAction")) {
				ps.setString(1, this.getLastAction());
			} else if (column.equals("statusMsg")) {
				ps.setString(1, this.getStatusMessage());
			} else if (column.equals("totalDuration")) {
				ps.setInt(1, this.getTotalDuration());
			} else if (column.equals("serverDuration")) {
				ps.setInt(1, this.getServerDuration());
			} else if (column.equals("loadDuration")) {
				ps.setInt(1, this.getLoadDuration());
			} else if (column.equals("networkDuration")) {
				ps.setInt(1, this.getNetworkDuration());
			} else if (column.equals("pageVacillation")) {
				ps.setInt(11, this.getPageVacillation());
			}
			ps.setInt(2, this.getPageHitId());

			ps.execute();
			logger.info(ps.toString());

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#updatePageHit()
	 */
	public boolean updatePageHit() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(SQL_PAGEHITS_UPDATE);
			ps.clearParameters();
			ps.setInt(1, this.getInstrumentSessionId());
			ps.setTimestamp(2, this.getTimeStamp());
			ps.setInt(3, this.getFromGroupNum());
			ps.setInt(4, this.getDisplayNum());
			ps.setString(5, this.getLangCode());
			ps.setString(6, this.getLastAction());
			ps.setString(7, this.getStatusMessage());
			ps.setInt(8, this.getTotalDuration());
			ps.setInt(9, this.getServerDuration());
			ps.setInt(10, this.getLoadDuration());
			ps.setInt(12, this.getNetworkDuration());
			ps.setInt(13, this.getPageVacillation());
			ps.setInt(14, this.getToGroupNum());
			ps.setInt(15, this.getPageHitId());
			
			logger.info(ps.toString());
			if(ps.executeUpdate()< 1){
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setPageHitId(int)
	 */
	public void setPageHitId(int pageHitId) {
		
		this.pageHitId = pageHitId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getPageHitId()
	 */
	public int getPageHitId() {
		
		return this.pageHitId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setInstrumentSessionId(int)
	 */
	public void setInstrumentSessionId(int instrumentSessionId) {
		
		this.instrumentSessionId = instrumentSessionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getInstrumentSessinId()
	 */
	public int getInstrumentSessionId() {
		
		return this.instrumentSessionId;
	}

	public int getFromGroupNum() {
		return FromGroupNum;
	}

	public void setFromGroupNum(int groupNum) {
		this.FromGroupNum = groupNum;
	}
	
	public int getToGroupNum() {
		return ToGroupNum;
	}

	public void setToGroupNum(int groupNum) {
		this.ToGroupNum = groupNum;
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setDisplayNum(int)
	 */
	public void setDisplayNum(int displayNum) {
		
		this.displayNum = displayNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getDisplayNum()
	 */
	public int getDisplayNum() {
		
		return this.displayNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setLastAction(java.lang.String)
	 */
	public void setLastAction(String lastAction) {
		
		this.lastAction = lastAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getLastAction()
	 */
	public String getLastAction() {
		
		return this.lastAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setStatusMessage(java.lang.String)
	 */
	public void setStatusMessage(String statusMessage) {
		
		this.statusMessage = statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getStatusMessage()
	 */
	public String getStatusMessage() {
		
		return this.statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setTotalDuration(int)
	 */
	public void setTotalDuration(int totalDuration) {
		
		this.totalDuration = totalDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getTotalDutation()
	 */
	public int getTotalDuration() {

		return this.totalDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setServerDuration(int)
	 */
	public void setServerDuration(int serverDuration) {

			this.serverDuration = serverDuration;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getServerDuration()
	 */
	public int getServerDuration() {

		return this.serverDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setLoadDuration(int)
	 */
	public void setLoadDuration(int loadDuration) {
		
		this.loadDuration = loadDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getLoadDuration()
	 */
	public int getLoadDuration() {
		
		return this.loadDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setNetworkDuration(int)
	 */
	public void setNetworkDuration(int networkDuration) {
		
		this.networkDuration = networkDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getNetworkDuration()
	 */
	public int getNetworkDuration() {
		
		return this.networkDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setPageVacillation(int)
	 */
	public void setPageVacillation(int pageVacillation) {
		
		this.pageVacillation = pageVacillation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getPageVacillation()
	 */
	public int getPageVacillation() {
		
		return this.pageVacillation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#getTimeStamp()
	 */
	public Timestamp getTimeStamp() {
		
		return this.timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.PageHitsDAO#setTimeStamp(java.sql.Timestamp)
	 */
	public void setTimeStamp(Timestamp timestamp) {
		
		this.timestamp = timestamp;

	}

	public int getInitTime() {
		
		return this.initTime;
	}

	public int getLoadTime() {
		
		return this.loadTime;
	}

	public int getSubmitClick() {
		
		return this.submitClick;
	}

	public void setInitTime(int init) {
		this.initTime = init;
		
	}

	public void setLoadTime(int load) {
		this.loadTime = load;
		
	}

	public void setSubmitClick(int click) {
		this.submitClick = click;
		
	}

	public int getDisplayTime() {
		return this.displayTime;
	}

	public void setDisplayTime(int display) {
		this.displayTime = display;
		
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getLangCode() {
		return langCode;
	}				

}
