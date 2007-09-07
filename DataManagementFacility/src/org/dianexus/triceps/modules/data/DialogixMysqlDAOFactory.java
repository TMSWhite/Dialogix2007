/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** DialogixMysqlDAOFactory.java ,v 3.0.0 Created on February 23, 2006, 11:30 AM
 ** @author Gary Lyons
 ********************************************************
 */
package org.dianexus.triceps.modules.data;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 * DialogixMysqlDAOFactory is a Mysql implementation of the Interface DialogixDAOFactory
 * The abstract factory design pattern, when used in conjunction with the DAO pattern,
 * allows for the dynamic selection of concrete DAO instances at run time. The factory
 * products are DAOs and the product families are concrete instances of the same data
 * store.
 *
 */

public class DialogixMysqlDAOFactory extends DialogixDAOFactory {
    // TODO Add generic exception logging
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
    
    


	
	
	public DialogixMysqlDAOFactory() {
	}

	public static Connection createConnection() {
		try {
			Class.forName(DRIVER).newInstance();
			System.out.println(DRIVER);
			System.out.println(DBURL);
			System.out.println(DBUSER);
			System.out.println(DBPASS);
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			System.out.println("got connection OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("connection failed error is "+e.getLocalizedMessage());
		}
		return con;
	}

    
    
    
    /**
     *
     * Returns MysqlInstrumentContentsDAO Class as InstrumentContentsDAO Interface
     */
    
    
    public InstrumentDAO getInstrumentDAO() {
        return new MysqlInstrumentDAO();
    }
    
    public InstrumentVersionDAO getInstrumentVersionDAO() {
        return new MysqlInstrumentVersionDAO();
    }
    
    
    
    public UserDAO getUserDAO() {
        return new MysqlUserDAO();
    }
    public UserPermissionDAO getUserPermissionDAO() {
        
        return new MysqlUserPermissionDAO();
    }
    
    public SandBoxDAO getSandBoxDAO() {
        return new MysqlSandBoxDAO();
    }
    
    public SandBoxItemDAO getSandBoxItemDAO() {
        return new MysqlSandBoxItemDAO();
    }
    
    public SandBoxUserDAO getSandBoxUserDAO() {
        return new MysqlSandBoxUserDAO();
    }
    public ReportQueryDAO getReportQueryDAO() {
        return new MysqlReportQueryDAO();
    }
    
    public InstrumentInfoDAO getInstrumentInfoDAO() {
        return new MysqlInstrumentInfoDAO();
    }
}