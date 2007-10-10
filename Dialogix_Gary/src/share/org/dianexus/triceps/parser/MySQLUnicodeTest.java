/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dianexus.triceps.parser;

import java.io.*;
import java.util.*;
import org.apache.log4j.*;
import java.sql.*;

/**
  Unit testing program.  Passed one or more equations; returns the results as Strings; 
    logs errors; and maintains history of parsed equations for insertion into a 5 column HTML table.
*/
public class MySQLUnicodeTest implements java.io.Serializable {
  static Logger logger = Logger.getLogger(MySQLUnicodeTest.class);
  
	static final String Tests[] = {
		"insert into dialogix3.parsertest values ('test', 'test', 'test', 1, NULL, NULL);",
		"insert into dialogix3.parsertest values ('parseExpr(\"\u041A\u0430\u043A\u043E\u0432\u0430 \u043F\u043E\u043B\u0430 `(0)?\'\u0412\u0430\u0448 \u0441\u0442\u0430\u0440\u0448\u0438\u0439 \u0440\u0435\u0431\u0435\u043D\u043E\u043A?\':\'\u0412\u044B \u0431\u044B \u0445\u043E\u0442\u0435\u043B\u0438 \u0447\u0442\u043E\u0431\u044B \u0431\u044B\u043B \u043F\u0435\u0440\u0432\u0435\u043D\u0435\u0446?\'`\")', '\u041A\u0430\u043A\u043E\u0432\u0430 \u043F\u043E\u043B\u0430 \u0412\u044B \u0431\u044B \u0445\u043E\u0442\u0435\u043B\u0438 \u0447\u0442\u043E\u0431\u044B \u0431\u044B\u043B \u043F\u0435\u0440\u0432\u0435\u043D\u0435\u0446?', '\u041A\u0430\u043A\u043E\u0432\u0430 \u043F\u043E\u043B\u0430 \u0412\u044B \u0431\u044B \u0445\u043E\u0442\u0435\u043B\u0438 \u0447\u0442\u043E\u0431\u044B \u0431\u044B\u043B \u043F\u0435\u0440\u0432\u0435\u043D\u0435\u0446?', 1, NULL, NULL);",
		"insert into dialogix3.parsertest values ('parseExpr(\"\u041A\u0430\u043A \u0431\u044B \u0412\u044B \u0445\u043E\u0442\u0435\u043B\u0438 `(0)?\'\u0435\u0433\u043E\':\'\u0435\u0435\'` \u043D\u0430\u0437\u0432\u0430\u0442\u044C?\")', '\u041A\u0430\u043A \u0431\u044B \u0412\u044B \u0445\u043E\u0442\u0435\u043B\u0438 \u0435\u0435 \u043D\u0430\u0437\u0432\u0430\u0442\u044C?', '\u041A\u0430\u043A \u0431\u044B \u0412\u044B \u0445\u043E\u0442\u0435\u043B\u0438 \u0435\u0435 \u043D\u0430\u0437\u0432\u0430\u0442\u044C?', 1, NULL, NULL);"
	};
  
  public void MySQLUnicodeTest() {
  }
  
  public String runTests() {
  	for (int i=0;i<Tests.length;++i) {
  		writeToDB(Tests[i]);
  	}
  	return "Hope";
  }
  		
  Connection getDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/dialogix3", "dialogix3", "dialogix3_pass");			
		}catch(Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
  /**
  	Log and execute a command against a database, creating the connection if needed
  */ 
	boolean writeToDB(String command) {
		logger.info(command);
		try {
			Connection conn = getDBConnection();
			
			if (conn == null) throw new Exception("Unable to connect to database");
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(command);
			stmt.close();
			
			conn.close();
	        
			return true;
		}
		catch (Exception t) {
			logger.error("SQL-ERROR", t);
			return false;
		}
	}
}
