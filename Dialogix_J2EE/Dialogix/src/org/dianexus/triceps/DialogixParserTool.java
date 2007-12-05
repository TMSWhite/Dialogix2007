/* ******************************************************** 
 ** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
 ** $Header$
 ******************************************************** */
package org.dianexus.triceps;

import org.dianexus.triceps.parser.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;

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
        String result = "*EMPTY*";
        if (logger.isDebugEnabled()) {
            logger.debug("Parsing: " + eqn);
        }
        if (eqn == null) {
            return result;
        }
        /* First separate into multiple lines */
        String[] eqns = eqn.split("\n|\r");
        for (int x = 0; x < eqns.length; ++x) {
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
                if (logger.isDebugEnabled()) {
                    logger.debug("Parsing: " + testEquation);
                }
                parser.ReInit(new StringReader(testEquation));
                datum = parser.parse(triceps);
                result = datum.stringVal();
                ++numQueries;
                logQueries(testEquation, result, expectedAnswer);

                StringBuffer sb = new StringBuffer("INSERT INTO ParserTest values (");
                sb.append("'").append(quoteSQL(testEquation)).append("',");
                sb.append("'").append(quoteSQL(result)).append("',");
                sb.append("'").append(quoteSQL(expectedAnswer)).append("',");
                sb.append(result.equals(expectedAnswer) ? 1 : 0).append(", NULL, NULL);");
//                writeToDB(sb.toString());
            } catch (Throwable e) {
                // FIXME:  Is it risky to catch an arbitrary Exception here?
                logger.error(e.getMessage(), e);
                result = "*INVALID*";
                logQueries(testEquation, result, expectedAnswer);
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
        if (logger.isDebugEnabled()) {
            logger.debug("Result of <<" + eqn + ">> is <<" + result + ">>");
        }
        sb.append("<TR><TD>");
        sb.append(XMLAttrEncoder.encode(eqn));
        sb.append("&nbsp;</TD><TD>");
        sb.append(result);
        sb.append("&nbsp;</TD>");
        if (expectedAnswer != null) {
            if (expectedAnswer.equals(result)) {
                sb.append("<TD BGCOLOR='green'>PASS");
            } else {
                sb.append("<TD BGCOLOR='red'>");
                sb.append(expectedAnswer);
            }
        } else {
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

    String quoteSQL(String src) {
        return src.replace("'", "\\'").replace("\"", "\\\"");
    }

    public String testExcelLoader(String filename) {
        InstrumentExcelLoader instrumentExcelLoader = new InstrumentExcelLoader();

        if (filename == null || "".equals(filename.trim())) {
            return "No Filename Specified";
        }

        logger.info("Testing Excel load from " + filename);

        instrumentExcelLoader.loadInstrument(filename);
        if (instrumentExcelLoader.getStatus() == true) {
            return "Successfully loaded instrument from " + filename + "<br>Launch it <a href='" + instrumentExcelLoader.getLaunchCommand() + "'>here</a>";
        } else {
            return "Error loading instrument from " + filename;
        }
    }
    
  Runtime rt = Runtime.getRuntime();
  
  /**
    @return the bytes of free memory
  */
  public long getFreeMemory() {
    return rt.freeMemory();
  }
  
  /**
    @return the bytes of Maximum available memory requestable
  */
  public long getMaxMemory() {
    return rt.maxMemory();
  }
  
  /**
    @return the total currently available memory
  */
  public long getTotalMemory() {
    return rt.totalMemory();
  }

  /**
    Manually run the garbage collection
  **/  
  public void garbageCollect() {
    rt.gc();
  } 
  
  /**
    @return memory used, in megabytes
  */
  public String getMemoryUsed() {
    long used = (getTotalMemory() - getFreeMemory());
    double kb = Math.floor(used / 1000);
    double mb = kb / 1000;
    return (Double.toString(mb) + "MB");
  }   
  
  Properties properties = new Properties();  
  /**
    Read a String of Log4J parameters and use them to reconfigure Log4J at runtime

    @param  params  The multi-line list of parameters (such as from the log4j.properties file)
  */
  public void setLoggerParams(String params) {
    try {
      String[] lines = params.split("\n|\r");
      properties = new Properties();
      for (int x=0;x<lines.length;++x) {
        if (logger.isDebugEnabled()) logger.debug("Logger param " + x + "= " + lines[x]);
        if (lines[x].matches("^\\s*$")) {
          continue;
        }
        String[] line = lines[x].split("=");
        if (line.length == 2) {
          if (logger.isDebugEnabled()) logger.debug("Logger line" + line[0] + "=" + line[1]);
          properties.setProperty(line[0],line[1]);
        }
        else {
          if (logger.isDebugEnabled()) logger.debug("Logger line missing an '='");
        }
      }
      LogManager.resetConfiguration();
      PropertyConfigurator.configure(properties);
    }
    catch (Exception e) {
      logger.error(e.getMessage(),e);
    }
  }
  
  /**
    Return the list of Log4J parameters
    XXX:  Only shows those set by this class -- can we retrieve the currently valid ones set however?
    
    @return an array of the most recently set Log4J parameters -- but only those set using this class
  */
  public String[] getLoggerParams() {
    return properties.toString().split(",");
  }  
}