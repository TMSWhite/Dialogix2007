/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.utils;
import org.apache.log4j.*;
import java.util.Properties;

/** 
  Class for manually setting the Log4J parameters while system is running
*/
public class LoggerStatus implements java.io.Serializable  {
  Logger logger = Logger.getLogger(LoggerStatus.class);
  Properties properties = new Properties();
  
  public static void LoggerStatus() {
  }
  
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

