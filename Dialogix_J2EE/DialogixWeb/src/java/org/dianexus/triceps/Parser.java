package org.dianexus.triceps;

import org.dianexus.triceps.parser.*;
/*import java.io.StringReader;*/
/*import java.io.StringWriter;*/
/*import java.lang.*;*/
/*import java.util.*;*/
import java.io.StringReader;
import java.util.logging.*;

/** 
Interface to make it easier to call DialogixParser.
 */
class Parser implements VersionIF {

    private static final String LoggerName = "org.dianexus.triceps.Parser";
//    private StringBuffer debugLogger = StringBuffer.NULL;
//    private StringBuffer errorLogger = StringBuffer.NULL;
    private DialogixParser dialogixparser = null;

    /**
    Initialize the Parser.
     */
    Parser() {
  //      dialogixparser = new DialogixParser(new StringReader(""));
//		setErrorLogger(new StringBuffer());
    }

    /**
    Return the Boolean interpretation of the expression.
    @param triceps	The context
    @param exp	The expression to parse
    @return Boolean(exp)
     */
    boolean booleanVal(Triceps triceps,
                       String exp) {
        return parse(triceps, exp).booleanVal();
    }

    /**
    Return the String value of the expression.
    @param triceps	The context
    @param exp	The expression to parse
    @return String value of parsing exp
     */
    String stringVal(Triceps triceps,
                     String exp) {
        return parse(triceps, exp).stringVal(false);
    }

    /**
    Return the String value of the expression.
    @param triceps	The context
    @param exp	The expression to parse
    @param showReserved	if true, display RESERVED words visibly, like *INVALID*
    @return String value of parsing exp
     */
    String stringVal(Triceps triceps,
                     String exp,
                     boolean showReserved) {
        return parse(triceps, exp).stringVal(showReserved);
    }

    /**
    Return the Double value of the expression.
    @param triceps	The context
    @param exp	The expression to parse
    @return numerical value of parsing exp
     */
    double doubleVal(Triceps triceps,
                     String exp) {
        return parse(triceps, exp).doubleVal();
    }

    /**
    Return the Datum result of parsing the expression
    @param triceps	The context
    @param exp	The expression to parse
    @return Datum
    @see Datum
     */
    Datum parse(Triceps triceps,
                String exp) {
//		debugLogger.println(exp);

        dialogixparser = new  DialogixParser(new StringReader(exp));
        Datum ans = dialogixparser.parse(triceps);

        return ans;
    }

    /**
    @return whether there were any parsing errors
     */
    boolean hasErrors() {
        return (dialogixparser.numErrors() > 0);
    }

    /**
    @return the pre-formatted String of any parsing errors found
     */
    String getErrors() {
        return dialogixparser.getErrors().toString();
    }

    /**
    Parse a String with embedeed expressions.  If the value of variable <i>name</i> is <i>Tom</i>, then parsing 
    </i>"Hi there `name`!</i> results in <i>Hi there Tom!</i>
    @param triceps	The context
    @param exp	The expression to parse
    @return	the composed String of parsing the equations within the message
     */
    String parseJSP(Triceps triceps,
                    String msg) {
        java.util.StringTokenizer st = new java.util.StringTokenizer(msg, "`", true);
        StringBuffer sb = new StringBuffer();
        String s;
        boolean inside = false;

//		debugLogger.println(msg);

        while (st.hasMoreTokens()) {
            s = st.nextToken();
            if ("`".equals(s)) {
                inside = (inside) ? false : true;
                continue;
            } else {
                if (inside) {
                    sb.append(stringVal(triceps, s, true));	// so that see the *REFUSED*, etc as part of questions
                } else {
                    sb.append(s);
                }
            }
        }

        return sb.toString();
    }

    /**
    Reset parsing error counts.
    XXX  Is this used?
     */
    void resetErrorCount() {
    /*
    dialogixparser.resetErrorCount();
     */
    }

    /**
    Set Debugging logger
    XXX is this used?
     */
    void setDebugLogger(StringBuffer l) {
    /*
    if (l != null) {
    debugLogger = l;
    dialogixparser.debugLogger = l;
    l.reset();
    }
     */
    }

    /**
    Set Error DialogixLogger
    XXX Is this used?
     */
    void setErrorLogger(StringBuffer l) {
    /*
    if (l != null) {
    errorLogger = l;
    dialogixparser.errorLogger = l;
    l.reset();
    }
     */
    }
}
