/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.io.StringReader;
import java.util.*;
import org.apache.log4j.Logger;

/** 
  Interface originally used by rest of Dialogix system.
  FIXME:  Is this interface still needed?
*/
public class Parser {
  static Logger logger = Logger.getLogger(Parser.class);

  private DialogixParser parser = null;

  public Parser() {
    parser = new DialogixParser(new StringReader(""));
  }

  public boolean booleanVal(Context context, String exp) {
    return parse(context, exp).booleanVal();
  }

  public String stringVal(Context context, String exp) {
    return parse(context, exp).stringVal(false);
  }

  public String stringVal(Context context, String exp, boolean showReserved) {
    return parse(context,exp).stringVal(showReserved);
  }

  public double doubleVal(Context context, String exp) {
    return parse(context, exp).doubleVal();
  }

  public Datum parse(Context context, String exp) {
    parser.ReInit(new StringReader(exp));
    Datum ans = parser.parse(context);

    return ans;
  }

  public boolean hasErrors() {
    return (parser.numErrors() > 0);
  }

  public String getErrors() {
    StringBuffer sb = new StringBuffer();
    Iterator errors = parser.getErrors().iterator();
    while (errors.hasNext()) {
      sb.append((String) errors.next());
    }
    return sb.toString();
  }

  public String parseJSP(Context context, String msg) {
    java.util.StringTokenizer st = new java.util.StringTokenizer(msg,"`",true);
    StringBuffer sb = new StringBuffer();
    String s;
    boolean inside = false;
    
    logger.debug(msg);

    while(st.hasMoreTokens()) {
      s = st.nextToken();
      if ("`".equals(s)) {
        inside = (inside) ? false : true;
        continue;
      }
      else {
        if (inside) {
          sb.append(stringVal(context,s,true));  // so that see the *REFUSED*, etc as part of questions
        }
        else {
          sb.append(s);
        }
      }
    }

    return sb.toString();
  }

  public void resetErrorCount() {
    parser.resetErrors();
  }
}
