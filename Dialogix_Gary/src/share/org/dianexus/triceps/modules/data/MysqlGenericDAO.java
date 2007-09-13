package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class MysqlGenericDAO implements GenericDAO{
  static Logger logger = Logger.getLogger(MysqlGenericDAO.class);
	
	String query;
	int lastInsertId;
	ResultSet rs = null;
	ResultSet rs2 = null;
	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

	public ResultSet getResultSet() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean runQuery() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(this.query);
			logger.info(ps.toString());
			rs = ps.executeQuery();
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs2 = ps.executeQuery();
			if(rs2.next()){
				this.lastInsertId = rs2.getInt(1);
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if(rs != null){
					rs.close();																																																																																																																					
				}
				if(rs2 != null){
					rs2.close();																																																																																																																					
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return true;
	}

	public int runUpdate() {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		int numRows=0;
		try {
			ps = con.prepareStatement(this.query);
			logger.info(ps.toString());
			numRows = ps.executeUpdate();
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs =  ps.executeQuery();
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return 0;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return numRows;
	}

	public void setQueryString(String query) {
		this.query = query;
	}

	public int getLastInsertId() {
		return this.lastInsertId;
	}
}
