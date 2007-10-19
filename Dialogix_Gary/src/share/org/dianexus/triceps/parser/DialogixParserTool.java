/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dianexus.triceps.parser;

import org.dianexus.triceps.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;
import java.sql.*;

/**
  Unit testing program.  Passed one or more equations; returns the results as Strings; 
    logs errors; and maintains history of parsed equations for insertion into a 5 column HTML table.
*/
public class DialogixParserTool implements java.io.Serializable {
  static Logger logger = Logger.getLogger(DialogixParserTool.class);
  private Triceps triceps = new Triceps();
  private DialogixParser parser = new DialogixParser(new StringReader(""));
  private StringBuffer queryHistory = new StringBuffer();
  private int numQueries = 0;
  
  public void DialogixParserTool() {
  }
  
  /**
    Main function for parsing an equation.  
    InputFormat:
      <equation> <tab> <expected result>
      
    Separates the input into multiple lines; splits on TAB to get equation and expected result; 
    Parses each one and logs the results, answer, whether matches expected answer, errors, and dependencies
    
    @param eqn  The string (can be multi-lined) of equations to parse
    @return The final answer
  */
  public String parse(String eqn) {
    String result="*EMPTY*";
    if (logger.isDebugEnabled()) logger.debug("Parsing: " + eqn);
    if (eqn == null) {
      return result;
    }
    /* First separate into multiple lines */
    String[] eqns = eqn.split("\n|\r");
    for (int x=0;x<eqns.length;++x) {
      String line = eqns[x];
      if (line.matches("^\\s*$")) {
        continue;  // don't process equations missing any contents
      }
      if (line.matches("^#")) {
      	continue;	// don't parse lines starting with a comment
      }
      /* Next parse on tabs.  First column is equation, 2nd is correct answer (if present) */
      String[] cols = line.split("\t");
      String testEquation = cols[0];
      String expectedAnswer = null;
      if (cols.length > 1) {
        expectedAnswer = cols[1];
      }
      try {
        Datum datum;
        if (logger.isDebugEnabled()) logger.debug("Parsing: " + testEquation);
        parser.ReInit(new StringReader(testEquation));
        datum = parser.parse(triceps);
        result = datum.stringVal();
        ++numQueries;
        logQueries(testEquation,result,expectedAnswer);
        
        StringBuffer sb = new StringBuffer("INSERT INTO ParserTest values (");
        sb.append("'").append(quoteSQL(testEquation)).append("',");
        sb.append("'").append(quoteSQL(result)).append("',");
        sb.append("'").append(quoteSQL(expectedAnswer)).append("',");
        sb.append(result.equals(expectedAnswer) ? 1 : 0).append(", NULL, NULL);");
        writeToDB(sb.toString());
      }
      catch (Exception e) {
        // FIXME:  Is it risky to catch an arbitrary Exception here?
        logger.error(e.getMessage(),e);
        result = "*INVALID*";
        logQueries(testEquation,result,expectedAnswer);
      }
    }
    return result;
  }
  
  /**
    Creates an 5 columh HTML table of Equation, Results, Expected, Errors, and Dependencies.
    
    @param eqn  The equation which was parsed
    @param result The result of parsing that equation
  */
  private void logQueries(String eqn, String result, String expectedAnswer) {
    StringBuffer sb = new StringBuffer();
    if (logger.isDebugEnabled()) logger.debug("Result of <<" + eqn + ">> is <<" + result + ">>");
    sb.append("<TR><TD>");
    sb.append(XMLAttrEncoder.encode(eqn));
    sb.append("&nbsp;</TD><TD>");    
    sb.append(result);
    sb.append("&nbsp;</TD>");
    if (expectedAnswer != null) {
      if (expectedAnswer.equals(result)) {
        sb.append("<TD BGCOLOR='green'>PASS");
      }
      else {
        sb.append("<TD BGCOLOR='red'>");
        sb.append(expectedAnswer);
      }
    }
    else {
      sb.append("<TD>");
    }
    sb.append("&nbsp;</TD><TD>");
    if (parser.numErrors() > 0) {
      Iterator it = parser.getErrors().iterator();
      while (it.hasNext()) {
        sb.append(XMLAttrEncoder.encode(it.next().toString()));
        sb.append("<BR/>");
      }
    }
    sb.append("&nbsp;</TD><TD>");
    if (parser.numDependencies() > 0) {
      Iterator it = parser.getDependencies().iterator();
      while (it.hasNext()) {
        sb.append(it.next());
        sb.append("<BR/>");
      }
    }
    sb.append("&nbsp;</TD><TR>");
    
    queryHistory = sb.append(queryHistory);
  }
  
  /**
    Shows the number of non-null equations that have been processed.
    
    @return The number of non-null equations.
  */
  public int numQueries() {
    return numQueries;
  }
  
  /**
    Gets this history of queries as 4 column HTML
    
    @return A String of the queries in 4 column HTML format
    @see #logQueries
  */
  public String getQueryHistory() {
    return queryHistory.toString();
  }
  
  
  /* Hack to log results of ParserTests to database to confirm that DB's Unicode support works */
//	protected Context ctx = null;	// this ok as global, since used on servlet-by-servlet basis
//	protected DataSource ds = null;	// this ok as global, since used on servlet-by-servlet basis
	protected boolean isDBinit = false;
	/**
		Startup datbased logging
	*/
	/*
	boolean initDBLogging() {
		try {
			ctx = new InitialContext();
			if(ctx == null ) 
				throw new Exception("Boom - No Context");

			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/dialogix");
			if(ds == null ) 
				throw new Exception("Boom - No DataSource");	    
			isDBinit = true;
			return true;
		}catch(Exception e) {
			logger.error("",e);
			return false;
		}
	}	
	*/
	
	/**
		Since initDBLogging seems to be failing, get connection directly, hard-coded.
	*/
  Connection getDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/dialogix3?useUnicode=yes&characterEncoding=UTF-8", "dialogix3", "dialogix3_pass");			
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
			/*
			if (!isDBinit) {
				initDBLogging();
			}
			if (ds == null) throw new Exception("Unable to access DataSource");
			
			Connection conn = ds.getConnection();
			*/
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
	
	String quoteSQL(String src) {
		return src.replace("'","\\'").replace("\"","\\\"");
	}
	
	public void readSqlFromFile(String filename, String encoding) {
		BufferedReader br = null;

		if (filename == null || "".equals(filename.trim()))
			return;
		if (encoding == null || "".equals(encoding.trim())) {
			encoding = "UTF-8";
		}
		
		logger.info("Reading from '" + filename + "' using encoding '" + encoding + "'");
			
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), encoding));
			
			String fileLine = null;
			while ((fileLine = br.readLine()) != null) {
				if ("".equals(fileLine.trim())) {
					continue;	
				}
				else {
					writeToDB(fileLine);
				}
			}
		} catch (Throwable t) {
			logger.error("", t);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Throwable t) { 
					logger.error("", t); 
				}
			}
		}
	}
	
	public String testExcelLoader(String filename) {
		InstrumentExcelLoader instrumentExcelLoader = new InstrumentExcelLoader();
		
		if (filename == null || "".equals(filename.trim()))
			return "No Filename Specified";
		
		logger.info("Testing Excel load from " + filename);
		
		instrumentExcelLoader.loadInstrument(filename);
		if (instrumentExcelLoader.getStatus() == true) {
			return instrumentExcelLoader.getFormattedContents();
		}
		else {
			return "Error loading instrument from " + filename;
		}
	}	
}
