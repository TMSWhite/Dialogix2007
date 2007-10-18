/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** MysqlRawDataDAO.java ,v 3.0.0 Created on February 27, 2006, 1:04 PM
 ** @author Gary Lyons
 ******************************************************** */

package org.dianexus.triceps.modules.data;

import java.sql.*;
import org.apache.log4j.Logger;

/**
 * MysqlRawDataDAO is a Mysql implementation of the Interface RawDataDAO This
 * DAO acts as an instance bean for the raw data table containing a data as
 * returned from a item page submission. Data is written one row per item.
 * 
 */
public class MysqlRawDataDAO implements RawDataDAO {
  static Logger logger = Logger.getLogger(MysqlRawDataDAO.class);

	private int rawDataId;

	private String varName;

	private String questionAsAsked;

	private String answer;

	private String comment;

	private String instanceName;

	private String instrumentName;

	private Timestamp timeStamp;

	private int langNum;

	private int answerType;

	private int displayNum;

	private int groupNum;

	private long whenAsMS;

	private int instrumentSessionId;

	private int itemVacillation;

	private int responseLatency;

	private int responseDuration;

	private int nullFlavor;
	
	private int latencyStart;
	
	private int latencyStop;
	
	private int durationStart;
	
	private int durationStop;
	

	// sql queries
	private static final String SQL_RAW_DATA_NEW = "INSERT INTO rawdata SET InstrumentName = ?,"
			+ "InstanceName = ?, VarName = ?, "
			+ "GroupNum = ?, DisplayNum = ?, LangNum = ?,"
			+ "WhenAsMS = ?, TimeStamp = ?, AnswerType = ? ,"
			+ "Answer = ?, QuestionAsAsked = ?, Comment = ? ,"
			+ "instrument_session_id = ?, itemVacillation = ? ,"
			+ "responseLatency = ? , responseDuration = ? , nullFlavor = ?";

	private static final String SQL_RAW_DATA_UPDATE = "UPDATE rawdata SET InstrumentName = ?,"
			+ "InstanceName = ?, VarName = ?, "
			+ "GroupNum = ?, DisplayNum = ?, LangNum = ?,"
			+ "WhenAsMS = ?, TimeStamp = ?, AnswerType = ? ,"
			+ "Answer = ?, QuestionAsAsked = ?, Comment = ? ,"
			+ "instrument_session_id = ?, itemVacillation = ? ,"
			+ "responseLatency = ? , responseDuration = ? , nullFlavor = ? WHERE RawDataID = ?";

	private static final String SQL_RAW_DATA_GET = " SELECT *  FROM rawdata WHERE RawDataID = ?";

	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

	public boolean updateRawData() {

		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_RAW_DATA_UPDATE);
			ps.clearParameters();
			ps.setString(1, instrumentName);
			ps.setString(2, instanceName);
			ps.setString(3, varName);
			ps.setInt(4, groupNum);
			ps.setInt(5, displayNum);
			ps.setInt(6, langNum);
			ps.setLong(7, whenAsMS);
			ps.setTimestamp(8, timeStamp);
			ps.setInt(9, answerType);
			ps.setString(10, answer);
			ps.setString(11, questionAsAsked);
			ps.setString(12, comment);
			ps.setInt(13, instrumentSessionId);
			ps.setInt(14, itemVacillation);
			ps.setInt(15, responseLatency);
			ps.setInt(16, responseDuration);
			ps.setInt(17, nullFlavor);
			ps.setInt(18, this.getRawDataId());
			logger.info(ps.toString());
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

	public boolean setRawData() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_RAW_DATA_NEW);
			ps.clearParameters();
			ps.setString(1, instrumentName);
			ps.setString(2, instanceName);
			ps.setString(3, varName);
			ps.setInt(4, groupNum);
			ps.setInt(5, displayNum);
			ps.setInt(6, langNum);
			ps.setLong(7, whenAsMS);
			ps.setTimestamp(8, timeStamp);
			ps.setInt(9, answerType);
			ps.setString(10, answer);
			ps.setString(11, questionAsAsked);
			ps.setString(12, comment);
			ps.setInt(13, instrumentSessionId);
			ps.setInt(14, itemVacillation);
			ps.setInt(15, responseLatency);
			ps.setInt(16, responseDuration);
			ps.setInt(17, nullFlavor);
			ps.execute();
			// get the raw data id as last insert id
			logger.info(ps.toString());
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setRawDataId(rs.getInt(1));
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

	public boolean getRawData() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(SQL_RAW_DATA_GET);
			ps.clearParameters();
			ps.setInt(1, this.getRawDataId());
			logger.info(ps.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				this.setInstrumentSessionId(rs.getInt(2));
				this.setInstrumentName(rs.getString(3));
				this.setInstanceName(rs.getString(4));
				this.setVarName(rs.getString(5));
				this.setGroupNum(rs.getInt(6));
				this.setDisplayNum(rs.getInt(7));
				this.setLangNum(rs.getInt(8));
				this.setWhenAsMS(rs.getLong(9));
				this.setTimeStamp(rs.getTimestamp(10));
				this.setAnswerType(rs.getInt(11));
				this.setAnswer(rs.getString(12));
				this.setQuestionAsAsked(rs.getString(13));
				this.setItemVacillation(rs.getInt(14));
				this.setResponseLatency(rs.getInt(15));
				this.setResponseDuration(rs.getInt(16));
				this.setNullFlavor(rs.getInt(17));
				this.setComment(rs.getString(18));
			} else {
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

	public void clearRawDataStructure() {
		this.rawDataId = 0;

		this.varName = null;

		this.questionAsAsked = null;

		this.answer = null;

		this.comment = null;

		this.instanceName = null;

		this.instrumentName = null;

		this.timeStamp = null;

		this.langNum = 0;

		this.answerType = 0;

		this.displayNum = 0;

		this.groupNum = 0;

		this.whenAsMS = 0;

		this.instrumentSessionId = 0;

		this.itemVacillation = 0;

		this.responseLatency = 0;

		this.responseDuration = 0;

		this.nullFlavor = 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getAnswer()
	 */
	public String getAnswer() {

		return this.answer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getAnswerType()
	 */
	public int getAnswerType() {

		return this.answerType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getComment()
	 */
	public String getComment() {

		return this.comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getDisplayNum()
	 */
	public int getDisplayNum() {

		return this.displayNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getGroupNum()
	 */
	public int getGroupNum() {

		return this.groupNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getInstanceName()
	 */
	public String getInstanceName() {

		return this.instanceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getInstrumentName()
	 */
	public String getInstrumentName() {

		return this.instrumentName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getInstrumentSessionId()
	 */
	public int getInstrumentSessionId() {

		return this.instrumentSessionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getItemVacillation()
	 */
	public int getItemVacillation() {
		return this.itemVacillation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getLangNum()
	 */
	public int getLangNum() {

		return this.langNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getNullFlavor()
	 */
	public int getNullFlavor() {

		return this.nullFlavor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getQuestionAsAsked()
	 */
	public String getQuestionAsAsked() {

		return this.questionAsAsked;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getRawDataId()
	 */
	public int getRawDataId() {
		return this.rawDataId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getResponseDuration()
	 */
	public int getResponseDuration() {
		if( this.durationStart >0 && this.durationStop > 0){
			this.responseDuration = this.durationStop - this.durationStart;
		}
		else {
			this.responseDuration =0;
		}
		return this.responseDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getResponseLatency()
	 */
	public int getResponseLatency() {
		if(this.latencyStart > 0 && this.latencyStop >0){
			this.responseLatency = this.latencyStop = this.latencyStart;
		}
		else{
			this.responseLatency =0;
		}
		return this.responseLatency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getTimeStamp()
	 */
	public Timestamp getTimeStamp() {

		return this.timeStamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getVarName()
	 */
	public String getVarName() {

		return this.varName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#getWhenAsMS()
	 */
	public long getWhenAsMS() {

		return this.whenAsMS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setAnswer(java.lang.String)
	 */
	public void setAnswer(String answer) {

		this.answer = answer;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setAnswerType(int)
	 */
	public void setAnswerType(int answerType) {

		this.answerType = answerType;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setComment(java.lang.String)
	 */
	public void setComment(String comment) {

		this.comment = comment;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setDisplayNum(int)
	 */
	public void setDisplayNum(int displayNum) {

		this.displayNum = displayNum;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setGroupNum(int)
	 */
	public void setGroupNum(int groupNum) {

		this.groupNum = groupNum;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setInstanceName(java.lang.String)
	 */
	public void setInstanceName(String instanceName) {

		this.instanceName = instanceName;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setInstrumentName(java.lang.String)
	 */
	public void setInstrumentName(String instrumentName) {

		this.instrumentName = instrumentName;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setInstrumentSessionId(int)
	 */
	public void setInstrumentSessionId(int instrumentSessionId) {

		this.instrumentSessionId = instrumentSessionId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setItemVacillation(int)
	 */
	public void setItemVacillation(int itemVacillation) {

		this.itemVacillation = itemVacillation;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setLangNum(int)
	 */
	public void setLangNum(int langNum) {

		this.langNum = langNum;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setNullFlavor(int)
	 */
	public void setNullFlavor(int nullFlavor) {

		this.nullFlavor = nullFlavor;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setQuestionAsAsked(java.lang.String)
	 */
	public void setQuestionAsAsked(String questionAsAsked) {

		this.questionAsAsked = questionAsAsked;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setRawDataId(int)
	 */
	public void setRawDataId(int rawDataId) {

		this.rawDataId = rawDataId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setResponseDuration(int)
	 */
	public void setResponseDuration(int responseDuration) {

		this.responseDuration = responseDuration;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setResponseLatency(int)
	 */
	public void setResponseLatency(int responseLatency) {

		this.responseLatency = responseLatency;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setTimeStamp(java.sql.Timestamp)
	 */
	public void setTimeStamp(Timestamp timeStamp) {

		this.timeStamp = timeStamp;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setVarName(java.lang.String)
	 */
	public void setVarName(String varName) {

		this.varName = varName;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dianexus.triceps.modules.data.RawDataDAO#setWhenAsMS(long)
	 */
	public void setWhenAsMS(long whenAsMS) {

		this.whenAsMS = whenAsMS;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0) {
		
		return super.equals(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
	
		return super.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
	
		super.finalize();
	}

	public int getDurationStart() {
		return this.durationStart;
	}

	public int getDurationStop() {
		return this.durationStop;
	}

	public int getLatencyStart() {
		return this.latencyStart;
	}

	public int getLatencyStop() {
		return this.latencyStop;
	}

	public void incrementVacillation() {
		this.itemVacillation ++;
		
	}

	public void setDurationStart(int start) {
		this.durationStart = start;
	}

	public void setDurationStop(int stop) {
		this.durationStop = stop;
		
	}

	public void setLatencyStart(int start) {
		this.latencyStart = start;
		
	}

	public void setLatencyStop(int stop) {
		this.latencyStop = stop;
		
	}

}
