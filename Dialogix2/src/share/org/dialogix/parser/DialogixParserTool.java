/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.io.StringReader;
import java.util.*;
import org.apache.log4j.*;

/**
  Unit testing program.  Passed one or more equations; returns the results as Strings; 
    logs errors; and maintains history of parsed equations for insertion into a 4 column HTML table.
*/
public class DialogixParserTool {
  static Logger logger = Logger.getLogger(DialogixParserTool.class);
  private Context context = new Context();
  /* TODO: Is it easy to create a Pool or parsers rather than one per session? */
  private DialogixParser parser = new DialogixParser(new StringReader(""));
  private StringBuffer queryHistory = new StringBuffer();
  private int numQueries = 0;
  
  public void DialogixParserTool() {
  }
  
  /**
    Main function for parsing an equation.  If multi-line, or contains ';' to separate functions,
    first divides into a multiple equations; then parses each one and logs the results and errors.
    
    @param eqn  The string (can be multi-lined) of equations to parse
    @return The final answer
  */
  public String parse(String eqn) {
    String result="*EMPTY*";
    logger.debug("Parsing: " + eqn);
    if (eqn == null) {
      return result;
    }
    String[] eqns = eqn.split("\n|\r|;");
    for (int x=0;x<eqns.length;++x) {
      String aneqn;
      aneqn = eqns[x];
      if (aneqn.matches("^\\s*$")) {
        continue;  // don't process equations missing any contents
      }
      try {
        Datum datum;
        logger.debug("Parsing: " + aneqn);
        parser.ReInit(new StringReader(aneqn));
        datum = parser.parse(context);
        result = datum.stringVal();
        ++numQueries;
        logQueries(aneqn,result);
      }
      catch (Exception e) {
        // FIXME:  Is it risky to catch an arbitrary Exception here?
        logger.error(e.getMessage(),e);
        result = "*INVALID*";
        logQueries(aneqn,result);
      }
    }
    return result;
  }
  
  /**
    Creates an 4 columh HTML table of Equation, Results, Errors, and Dependencies.
    
    @param eqn  The equation which was parsed
    @param result The result of parsing that equation
  */
  private void logQueries(String eqn, String result) {
    StringBuffer sb = new StringBuffer();
    logger.debug("Result of <<" + eqn + ">> is <<" + result + ">>");
    sb.append("<TR><TD>");
    sb.append(eqn);
    sb.append("</TD><TD>");    
    sb.append(result);
    sb.append("</TD><TD>");
    if (parser.numErrors() > 0) {
      Iterator it = parser.getErrors().iterator();
      while (it.hasNext()) {
        sb.append(it.next());
        sb.append("<BR/>");
      }
    }
    else {
      sb.append("&nbsp;");
    }
    sb.append("</TD><TD>");
    if (parser.numDependencies() > 0) {
      Iterator it = parser.getDependencies().iterator();
      while (it.hasNext()) {
        sb.append(it.next());
        sb.append("<BR/>");
      }
    }
    else {
      sb.append("&nbsp;");
    }
    sb.append("</TD><TR>");
    
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
}
