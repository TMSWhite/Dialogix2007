/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import org.dialogix.util.*;
import java.io.StringReader;
import java.util.*;
import java.util.logging.*;

/**
  Unit testing program.  Passed one or more equations; returns the results as Strings; 
    logs errors; and maintains history of parsed equations for insertion into a 5 column HTML table.
*/
public class DialogixParserTool implements java.io.Serializable {
  static Logger logger = Logger.getLogger("org.dialogix.parser.DialogixParserTool");
  private Context context = new Context();
  /* TODO: Is it easy to create a Pool or parsers rather than one per session? */
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
    if (logger.isLoggable(Level.FINE)) logger.log(Level.FINE,"Parsing: " + eqn);
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
      /* Next parse on tabs.  First column is equation, 2nd is correct answer (if present) */
      String[] cols = line.split("\t");
      String testEquation = cols[0];
      String expectedAnswer = null;
      if (cols.length > 1) {
        expectedAnswer = cols[1];
      }
      try {
        Datum datum;
        if (logger.isLoggable(Level.FINE)) logger.log(Level.FINE,"Parsing: " + testEquation);
        parser.ReInit(new StringReader(testEquation));
        datum = parser.parse(context);
        result = datum.stringVal();
        ++numQueries;
        logQueries(testEquation,result,expectedAnswer);
      }
      catch (Exception e) {
        // FIXME:  Is it risky to catch an arbitrary Exception here?
        logger.log(Level.SEVERE,e.getMessage(),e);
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
    if (logger.isLoggable(Level.FINE)) logger.log(Level.FINE,"Result of <<" + eqn + ">> is <<" + result + ">>");
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
}
