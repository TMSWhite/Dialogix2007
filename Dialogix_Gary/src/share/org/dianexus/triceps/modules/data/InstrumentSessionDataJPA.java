package org.dianexus.triceps.modules.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class InstrumentSessionDataJPA implements InstrumentSessionDataDAO {
  static Logger logger = Logger.getLogger(InstrumentSessionDataJPA.class);
  static final SimpleDateFormat TimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	
	private int InstrumentStartingGroup; 
	private int instrumentSessionDataId;
	private String lastAction;
	private int CurrentGroup;
	private Timestamp sessionLastAccessTime;
	private Timestamp sessionStartTime;
	private Integer sessionId;
	private String statusMsg;
	private String tableName;
	private ArrayList dataColumns = new ArrayList();
	private ArrayList dataValues =  new ArrayList();
	private Hashtable updatedValues = null;	// This is array of values to be updated
	private int DisplayNum;
	private String LanguageCode;
	
	public boolean setInstrumentSessionDataDAO(String tablename) {
		// store a skeletal record that can be updated as the session progresses
		setTableName(tablename);
		String query = "INSERT INTO "+tablename+" (StartTime, "
		+ "LastAccessTime, InstrumentStartingGroup, CurrentGroup, LastAction, statusMsg,InstrumentSession_ID, DisplayNum, LanguageCode)"
		+ " VALUES(?,?,?,?,?,?,?,?,?)";
		Connection con = createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setTimestamp(1, getSessionStartTime());
			ps.setTimestamp(2, getSessionLastAccessTime());
			ps.setInt(3, getInstrumentStartingGroup());
			ps.setInt(4, getCurrentGroup());
			ps.setString(5, getLastAction());
			ps.setString(6, getStatusMsg());
			ps.setInt(7, getSessionId());
			ps.setInt(8, getDisplayNum());
			ps.setString(9, getLangCode());

			ps.execute();
			// get the raw data id as last insert id 
			logger.info(ps.toString());
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if(rs.next()){
				this.setInstrumentSessionDataId(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
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
				logger.error(ps.toString(), fe);
			}
		}
		return true;
	}
	
	public boolean getInstrumentSessionDataDAO(String table, int id) {
		// get a row from the session data table
		
		boolean rtn = false;
		String query = "SELECT * FROM "+table+" WHERE InstrumentSession_ID = ?";
		Connection con = createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsm = null;
		try {
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			rsm = rs.getMetaData();
			int numCols = rsm.getColumnCount();
			if(rs.next()){
				//TODO complete function
				for(int i=1;i<numCols;i++){
					dataValues.add(rs.getString(i));
					dataColumns.add(rsm.getColumnLabel(i));
				}
				rtn=true;
			}
			logger.info(ps.toString());
		} catch (Exception e) {
			logger.error(ps.toString(), e);
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
				logger.error(ps.toString(), fe);
			}
		}
		return rtn;
	}
	
	public boolean updateInstrumentSessionDataDAO(String column, String value) {
		if (updatedValues == null) {
			updatedValues = new Hashtable();
		}
		updatedValues.put(column, value);
		
		return true;
	}

	public boolean update() {
		Connection con = createConnection();
		Statement stmt = null;
		StringBuffer sb = null;
		
		try {
			/* Create query from parameters */
			sb = new StringBuffer("UPDATE ");
			sb.append(this.getTableName()).append(" SET ");
			sb.append(" CurrentGroup = '").append(getCurrentGroup()).append("'");
			sb.append(", LastAction = '").append(getLastAction()).append("'");
			sb.append(", statusMsg = '").append(getStatusMsg()).append("'");
			
			// Need proper format:  2007-10-17 12:38:15
			
			sb.append(", LastAccessTime = '").append(TimestampFormat.format(new Date(System.currentTimeMillis()))).append("'");
			sb.append(", DisplayNum = ").append(getDisplayNum());
			sb.append(", LanguageCode = '").append(getLangCode()).append("'");
			
			
			// Iterate over columns needing to be set
			if (updatedValues != null) {
				Enumeration keys = updatedValues.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					String value = (String) updatedValues.get(key);
					
					sb.append(", ").append(key).append(" = '").append(value).append("'");
				}					
			}
			updatedValues = null;	// clear it for next time
			
			sb.append(" WHERE InstrumentSession_ID = ").append(getInstrumentSessionDataId());
			
			stmt = con.createStatement();
	    int returnedKey = stmt.executeUpdate(sb.toString(), Statement.RETURN_GENERATED_KEYS);
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.error(sb.toString(), e);
			return false;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception fe) {
				logger.error(sb.toString(), fe);
			}
		}
		return true;
	}	

 // Hard-coding these here, since replacing this whole section soon.  Could be configured via ant.
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://localhost:3306/dialogix5?useUnicode=yes&characterEncoding=UTF-8";
    public static final String DBUSER = "dialogix5";
    public static final String DBPASS = "dialogix5_pass";
    
	public Connection createConnection() {
        Connection con = null;
    	try {
			Class.forName(DRIVER).newInstance();
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			logger.debug("got connection OK");
		} catch (Exception e) {
			logger.error("failed to create database connection", e);
		}
		return con;
	}

	public int getInstrumentStartingGroup() {
		return InstrumentStartingGroup;
	}

	public int getInstrumentSessionDataId() {
		return instrumentSessionDataId;
	}

	public String getLastAction() {
		return lastAction;
	}

	public int getCurrentGroup() {
		return CurrentGroup;
	}

	public Timestamp getSessionLastAccessTime() {
		return sessionLastAccessTime;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public Timestamp getSessionStartTime() {
		return sessionStartTime;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setInstrumentStartingGroup(int group) {
		InstrumentStartingGroup = group;
	}

	public void setInstrumentSessionDataId(int id) {
		instrumentSessionDataId = id;
	}

	public void setLastAction(String action) {
		lastAction = action;
	}

	public void setCurrentGroup(int group) {
		CurrentGroup = group;
	}

	public void setSessionStartTime(Timestamp time) {
		sessionStartTime = time;
	}

	public void setSessionLastAccessTime(Timestamp time) {
		sessionLastAccessTime = time;
	}

	public void setSessionId(Integer id) {
		sessionId = id;
	}

	public void setStatusMsg(String msg) {
		statusMsg = msg;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String table) {
		tableName = table;
	}
	
	public ArrayList getColumnArray() {
		return dataColumns;
	}
	
	public ArrayList getDataArray() {
		return dataValues;
	}
	
	public void setDisplayNum(int DisplayNum) {
		this.DisplayNum = DisplayNum;
	}
	public int getDisplayNum() {
		return DisplayNum;
	}	
	
	public void setLangCode(String LanguageCode) {
		this.LanguageCode = LanguageCode;
	}
	public String getLangCode() {
		return (LanguageCode == null) ? "" : LanguageCode;
	}		
}
