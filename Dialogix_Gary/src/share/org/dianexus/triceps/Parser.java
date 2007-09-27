/* ******************************************************** 
** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dianexus.triceps;

import org.dianexus.triceps.parser.*;
/*import java.io.StringReader;*/
/*import java.io.StringWriter;*/
/*import java.lang.*;*/
/*import java.util.*;*/
import java.io.StringReader;
import org.apache.log4j.Logger;

/* Wrapper to make it easier to call DialogixParser */
/*public*/ class Parser implements VersionIF {
  static Logger logger = Logger.getLogger(Parser.class);
//	private org.dianexus.triceps.Logger debugLogger = org.dianexus.triceps.Logger.NULL;
//	private org.dianexus.triceps.Logger errorLogger = org.dianexus.triceps.Logger.NULL;
	private DialogixParser dialogixparser = null;

	/*public*/ Parser() {
		dialogixparser = new DialogixParser(new StringReader(""));
//		setErrorLogger(new org.dianexus.triceps.Logger());
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
//		debugLogger.println(exp);

		dialogixparser.ReInit(new StringReader(exp));
		Datum ans = dialogixparser.parse(triceps);

		return ans;
	}

	/*public*/ boolean hasErrors() {
		return (dialogixparser.numErrors() > 0);
	}

	/*public*/ String getErrors() {
		return dialogixparser.getErrors().toString();
	}

	/*public*/ String parseJSP(Triceps triceps, String msg) {
		java.util.StringTokenizer st = new java.util.StringTokenizer(msg,"`",true);
		StringBuffer sb = new StringBuffer();
		String s;
		boolean inside = false;

//		debugLogger.println(msg);

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
		/*
		dialogixparser.resetErrorCount();
		*/
	}

	/*public*/ void setDebugLogger(org.dianexus.triceps.Logger l) {
		/*
		if (l != null) {
			debugLogger = l;
			dialogixparser.debugLogger = l;
			l.reset();
		}
		*/
	}

	/*public*/ void setErrorLogger(org.dianexus.triceps.Logger l) {
		/*
		if (l != null) {
			errorLogger = l;
			dialogixparser.errorLogger = l;
			l.reset();
		}
		*/
	}
}
