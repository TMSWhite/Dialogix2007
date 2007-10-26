package org.dianexus.triceps.modules.data;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.log4j.Logger;
//import org.dialogix.domain.Englishfrenchdemo;

public class InstrumentSessionDataJPA implements InstrumentSessionDataDAO {

    static final SimpleDateFormat TimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static Logger logger = Logger.getLogger(InstrumentSessionDataJPA.class);
    private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    // get the driver for jdbc.mysql

    public static final String DRIVER = Messages.getString("DialogixMysqlDAOFactory.DRIVER"); //$NON-NLS-1$
	// get the url for the db server

    public static final String DBURL = Messages.getString("DialogixMysqlDAOFactory.DBURL"); //$NON-NLS-1$
	// get the user name for the db server

    public static final String DBUSER = Messages.getString("DialogixMysqlDAOFactory.DBUSER"); //$NON-NLS-1$
	// get the password for the db server

    public static final String DBPASS = Messages.getString("DialogixMysqlDAOFactory.DBPASS"); //$NON-NLS-1$
	// Mysql setup variables

    static Connection con = null;
    static Statement stmt = null;
    private int InstrumentStartingGroup;
    private int firstGroup;
    private int CurrentGroup;
    private String instrumentName;
    private int instrumentSessionDataId;
    private String lastAccess;
    private String lastAction;
    private Timestamp sessionLastAccessTime;
    private int lastGroup;
    private Timestamp sessionEndTime;
    private Timestamp sessionStartTime;
    private int sessionId;
    private String statusMsg;
    private String tableName;
    private ArrayList dataColumns = new ArrayList();
    private ArrayList dataValues = new ArrayList();
    private Hashtable updatedValues = null;	// This is array of values to be updated

    private String instanceName;
    private int displayNum;
    private String langCode;

    public static Connection createConnection() {
        try {
            Class.forName(DRIVER).newInstance();
            logger.debug(DRIVER);
            logger.debug(DBURL);
            logger.debug(DBUSER);
            logger.debug(DBPASS);
            con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            logger.debug("JDBC connection OK");
        } catch (Exception e) {
            logger.error("failed to create database connection", e);
        }
        return con;
    }

    public boolean setInstrumentSessionDataDAO(String tablename) {
        // store a skeletal record that can be updated as the session progresses
        setTableName(tablename);
        String query = "INSERT INTO " + tablename + " (InstrumentName, InstanceName, StartTime, " + "end_time, first_group, last_group, last_action, last_access, status_message,instrument_session_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
        con = createConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(query);
            ps.clearParameters();
            ps.setString(1, getInstrmentName());
            ps.setString(2, getInstanceName());
            ps.setTimestamp(3, getSessionStartTime());
            ps.setTimestamp(4, getSessionEndTime());
            ps.setInt(5, getFirstGroup());
            ps.setInt(6, getLastGroup());
            ps.setString(7, getLastAction());
            ps.setString(8, getLastAccess());
            ps.setString(9, getStatusMsg());
            ps.setInt(10, getSessionId());

            ps.execute();
            // get the raw data id as last insert id 
            logger.info(ps.toString());
            ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.setInstrumentSessionDataId(rs.getInt(1));
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

    public boolean deleteInstrumentSessionDataDAO(String tableName, int id) {
        // delete a row from the session data table using session id
        String query = "DELETE FROM " + tableName + " WHERE instrument_session_id = ?";
        con = DialogixMysqlDAOFactory.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.clearParameters();
            ps.setInt(1, id);

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

    public boolean getInstrumentSessionDataDAO(String table, int id) {
        // get a row from the session data table

        boolean rtn = false;
        String query = "SELECT * FROM " + table + " WHERE instrument_session_id = ?";
        con = DialogixMysqlDAOFactory.createConnection();
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
            if (rs.next()) {
                //TODO complete function
                for (int i = 1; i < numCols; i++) {
                    dataValues.add(rs.getString(i));
                    dataColumns.add(rsm.getColumnLabel(i));
                }
                rtn = true;
            }
            logger.info(ps.toString());
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
        con = createConnection();
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
            sb.append(", displayNum = ").append(getDisplayNum());
            sb.append(", langCode = '").append(getLangCode()).append("'");


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

            sb.append(" WHERE ID = ").append(getInstrumentSessionDataId());

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

    public int getFirstGroup() {
        return firstGroup;
    }

    public String getInstrmentName() {
        return instrumentName;
    }

    public int getInstrumentSessionDataId() {
        return instrumentSessionDataId;
    }

    public String getLastAccess() {
        return lastAccess;
    }

    public String getLastAction() {
        return lastAction;
    }

    public int getCurrentGroup() {
        return CurrentGroup;
    }

    public int getLastGroup() {
        return lastGroup;
    }

    public Timestamp getSessionEndTime() {
        return sessionEndTime;
    }

    public int getSessionId() {
        return sessionId;
    }

    public Timestamp getSessionStartTime() {
        return sessionStartTime;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setFirstGroup(int group) {
        firstGroup = group;
    }

    public void setInstrumentName(String name) {
        instrumentName = name;
    }

    public void setInstrumentSessionDataId(int id) {
        instrumentSessionDataId = id;
    }

    public void setLastAccess(String access) {
        lastAccess = access;
    }

    public void setLastAction(String action) {
        lastAction = action;
    }

    public void setLastGroup(int group) {
        lastGroup = group;
    }

    public void setCurrentGroup(int group) {
        CurrentGroup = group;
    }

    public void setSessionStartTime(Timestamp time) {
        sessionEndTime = time;
    }

    public void setSessionEndTime(Timestamp time) {
        sessionEndTime = time;
    }

    public void setSessionId(int id) {
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

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String name) {
        instanceName = name;
    }

    public ArrayList getColumnArray() {
        return dataColumns;
    }

    public ArrayList getDataArray() {
        return dataValues;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangCode() {
        return (langCode == null) ? "" : langCode;
    }

    public void setSessionLastAccessTime(Timestamp time) {
        sessionLastAccessTime = time;
    }

    public Timestamp getSessionLastAccessTime() {
        return sessionLastAccessTime;

    }

    public void setInstrumentStartingGroup(int group) {
        InstrumentStartingGroup = group;

    }

    public int getInstrumentStartingGroup() {
        return InstrumentStartingGroup;

    }
}
