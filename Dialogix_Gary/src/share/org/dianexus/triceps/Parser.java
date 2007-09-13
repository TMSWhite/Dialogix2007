/* ******************************************************** 
** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dianexus.triceps;

/*import java.io.StringReader;*/
/*import java.io.StringWriter;*/
/*import java.lang.*;*/
/*import java.util.*;*/
import java.io.StringReader;
import org.apache.log4j.Logger;

/* Wrapper to make it easier to call Qss */
/*public*/ class Parser implements VersionIF {
  static Logger logger = Logger.getLogger(Parser.class);
	private org.dianexus.triceps.Logger debugLogger = org.dianexus.triceps.Logger.NULL;
	private org.dianexus.triceps.Logger errorLogger = org.dianexus.triceps.Logger.NULL;
	private Qss qss = null;

	/*public*/ Parser() {
		qss = new Qss(new StringReader(""));
		setErrorLogger(new org.dianexus.triceps.Logger());
	}

	/*public*/ boolean booleanVal(Triceps triceps, String exp) {
		return parse(triceps, exp).booleanVal();
	}

	/*public*/ String stringVal(Triceps triceps, String exp) {
		return parse(triceps, exp).stringVal(false);
	}

	/*public*/ String stringVal(Triceps triceps, String exp, boolean showReserved) {
		return parse(triceps,exp).stringVal(showReserved);
	}

	/*public*/ double doubleVal(Triceps triceps, String exp) {
		return parse(triceps, exp).doubleVal();
	}

	/*public*/ Datum parse(Triceps triceps, String exp) {
		debugLogger.println(exp);

		qss.ReInit(new StringReader(exp));
		Datum ans = qss.parse(triceps);

		return ans;
	}

	/*public*/ boolean hasErrors() {
		return (errorLogger.size() > 0);
	}

	/*public*/ String getErrors() {
		return errorLogger.toString();
	}

	/*public*/ String parseJSP(Triceps triceps, String msg) {
		java.util.StringTokenizer st = new java.util.StringTokenizer(msg,"`",true);
		StringBuffer sb = new StringBuffer();
		String s;
		boolean inside = false;

		debugLogger.println(msg);

		while(st.hasMoreTokens()) {
			s = st.nextToken();
			if ("`".equals(s)) {
				inside = (inside) ? false : true;
				continue;
			}
			else {
				if (inside) {
					sb.append(stringVal(triceps,s,true));	// so that see the *REFUSED*, etc as part of questions
				}
				else {
					sb.append(s);
				}
			}
		}

		return sb.toString();
	}

	/*public*/ void resetErrorCount() {
		qss.resetErrorCount();
	}

	/*public*/ void setDebugLogger(org.dianexus.triceps.Logger l) {
		if (l != null) {
			debugLogger = l;
			qss.debugLogger = l;
			l.reset();
		}
	}

	/*public*/ void setErrorLogger(org.dianexus.triceps.Logger l) {
		if (l != null) {
			errorLogger = l;
			qss.errorLogger = l;
			l.reset();
		}
	}
}
