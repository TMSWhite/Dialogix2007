/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** DialogixMysqlDAOFactory.java ,v 3.0.0 Created on February 23, 2006, 11:30 AM
 ** @author Gary Lyons
 ******************************************************** 
 */
package org.dianexus.triceps.modules.data;

import java.sql.*;
import org.apache.log4j.Logger;

/**
 * DialogixMysqlDAOFactory is a Mysql implementation of the Interface DialogixDAOFactory
 * The abstract factory design pattern, when used in conjunction with the DAO pattern,
 * allows for the dynamic selection of concrete DAO instances at run time. The factory
 * products are DAOs and the product families are concrete instances of the same data
 * store.
 * 
 */

public class DialogixMysqlDAOFactory extends DialogixDAOFactory {
  static Logger logger = Logger.getLogger(DialogixMysqlDAOFactory.class);
	// TODO Add generic exception logging

	// Hard-coding these here, since replacing this whole section soon.  Could be configured via ant.
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/dialogix3?useUnicode=yes&characterEncoding=UTF-8";
	public static final String DBUSER = "dialogix3";
	public static final String DBPASS = "dialogix3_pass";
	// Mysql setup variables
	static Connection con = null;
	static Statement stmt = null;
	
	public DialogixMysqlDAOFactory() {
	}

	public static Connection createConnection() {
		try {
			Class.forName(DRIVER).newInstance();
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			logger.debug("got connection OK");
		} catch (Exception e) {
			logger.error("failed to create database connection", e);
		}
		return con;
	}
	
	/**
	 * 
	 * Returns MysqlSessionDataDAO Class as SessionDataDAO Interface
	 */
	public SessionDataDAO getSessionDataDAO() {
		return new MysqlSessionDataDAO();
	}
	
	/**
	 * 
	 * Returns MysqlRawDataDAO Class as RawDataDAO Interface
	 */
	public RawDataDAO getRawDataDAO() {
		return new MysqlRawDataDAO();
	}
	
	/**
	 * 
	 * Returns MysqlInstrumentSessionDAO Class as InstrumentSessionDAO Interface
	 */
	public InstrumentSessionDAO getInstrumentSessionDAO() {
		return new MysqlInstrumentSessionDAO();
	}

	/**
	 * 
	 * Returns MysqlPageHitEventsDAO Class as PageHitEventsDAO Interface
	 */
	public PageHitEventsDAO getPageHitEventsDAO() {
		return new MysqlPageHitEventsDAO();
	}

	/**
	 * 
	 * Returns MysqlPageHitsDAO Class as PageHitsDAO Interface
	 */
	public PageHitsDAO getPageHitsDAO() {
		return new MysqlPageHitsDAO();
	}

	public InstrumentDAO getInstrumentDAO() {
		return new MysqlInstrumentDAO();
	}

	public InstrumentVersionDAO getInstrumentVersionDAO() {
		return new MysqlInstrumentVersionDAO();
	}

	public InstrumentSessionDataDAO getInstrumentSessionDataDAO() {
		return new MysqlInstrumentSessionDataDAO();
	}
}