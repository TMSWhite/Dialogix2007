/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.utils;
import org.apache.log4j.*;
import java.util.Properties;

/** Routines for extracting logger status information
*/
public class LoggerStatus {
   Logger logger = Logger.getLogger(LoggerStatus.class);
   Properties properties = new Properties();
   
   public static void LoggerStatus() {
   }
   
   public void setLoggerParams(String params) {
      try {
         String[] lines = params.split("\n|\r");
         properties = new Properties();
         for (int x=0;x<lines.length;++x) {
            logger.debug("Logger param " + x + "= " + lines[x]);
            if (lines[x].matches("^\\s*$")) {
               continue;
            }
            String[] line = lines[x].split("=");
            if (line.length == 2) {
               logger.debug("Logger line" + line[0] + "=" + line[1]);
               properties.setProperty(line[0],line[1]);
            }
            else {
               logger.debug("Logger line missing an '='");
            }
         }
         LogManager.resetConfiguration();
         PropertyConfigurator.configure(properties);
      }
      catch (Exception e) {
         logger.error(e.getMessage(),e);
      }
   }
   
   public String[] getLoggerParams() {
      return properties.toString().split(",");
   }
}


