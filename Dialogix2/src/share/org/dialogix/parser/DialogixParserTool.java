/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.io.StringReader;
import java.util.*;
import org.apache.log4j.*;

/* Wrapper to make it easier to call DialogixParser */
public class DialogixParserTool {
  static Logger logger = Logger.getLogger(DialogixParserTool.class);
  private Context context = new Context();
  /* FIXME: Is it easy to create a Pool or parsers rather than one per session? */
  private DialogixParser parser = new DialogixParser(new StringReader(""));
  private StringBuffer queryHistory = new StringBuffer();
  private int numQueries = 0;
  
  public void DialogixParserTool() {
  }
  
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
  
  /* Create HTML table of equations added to parser, their errors and dependencies */
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
    
    parser.resetErrors();
  }
  
  public int numQueries() {
    return numQueries;
  }
  
  public String getQueryHistory() {
    return queryHistory.toString();
  }
}
