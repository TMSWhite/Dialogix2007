/* ******************************************************** 
 ** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
 ** $Header$
 ******************************************************** */
package org.dialogix.parser;

import java.util.logging.*;
import java.util.*;
import java.io.StringReader;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/** 
This class lists all the functions which can operate on Datum objects.
TODO:  Add introspection as in Velocity so that external functions can also process Datum objects
 */
public class Functions implements java.io.Serializable {

    static Logger logger = Logger.getLogger("org.dialogix.parser.Functions");
    private DialogixParser parser = new DialogixParser(new StringReader(""));

    public Functions() {
    }
    private static final int FUNCTION_INDEX = 2;
    private static final int FUNCTION_NUM_PARAMS = 1;
    private static final int FUNCTION_NAME = 0;
    private static final Integer ZERO = new Integer(0);
    private static final Integer ONE = new Integer(1);
    private static final Integer TWO = new Integer(2);
    private static final Integer UNLIMITED = new Integer(-1);

    /* Function declarations */
    private static final int DESC = 0;
    private static final int ISASKED = 1;
    private static final int ISNA = 2;
    private static final int ISREFUSED = 3;
    private static final int ISUNKNOWN = 4;
    private static final int ISNOTUNDERSTOOD = 5;
    private static final int ISDATE = 6;
    private static final int ISANSWERED = 7;
    private static final int GETDATE = 8;
    private static final int GETYEAR = 9;
    private static final int GETMONTH = 10;
    private static final int GETMONTHNUM = 11;
    private static final int GETDAY = 12;
    private static final int GETWEEKDAY = 13;
    private static final int GETTIME = 14;
    private static final int GETHOUR = 15;
    private static final int GETMINUTE = 16;
    private static final int GETSECOND = 17;
    private static final int NOW = 18;
    private static final int STARTTIME = 19;
    private static final int COUNT = 20;
    private static final int ANDLIST = 21;
    private static final int ORLIST = 22;
    private static final int NEWDATE = 23;
    private static final int NEWTIME = 24;
    private static final int ISINVALID = 25;
    private static final int MIN = 26;
    private static final int MAX = 27;
    private static final int GETDAYNUM = 28;
    private static final int HASCOMMENT = 29;
    private static final int GETCOMMENT = 30;
    private static final int GETTYPE = 31;
    private static final int ISSPECIAL = 32;
    private static final int NUMANSOPTIONS = 33;
    private static final int GETANSOPTION = 34;
    private static final int CHARAT = 35;
    private static final int COMPARETO = 36;
    private static final int COMPARETOIGNORECASE = 37;
    private static final int ENDSWITH = 38;
    private static final int INDEXOF = 39;
    private static final int LASTINDEXOF = 40;
    private static final int LENGTH = 41;
    private static final int STARTSWITH = 42;
    private static final int SUBSTRING = 43;
    private static final int TOLOWERCASE = 44;
    private static final int TOUPPERCASE = 45;
    private static final int TRIM = 46;
    private static final int ISNUMBER = 47;
    private static final int FILEEXISTS = 48;
    private static final int ABS = 49;
    private static final int ACOS = 50;
    private static final int ASIN = 51;
    private static final int ATAN = 52;
    private static final int ATAN2 = 53;
    private static final int CEIL = 54;
    private static final int COS = 55;
    private static final int EXP = 56;
    private static final int FLOOR = 57;
    private static final int LOG = 58;
    private static final int POW = 59;
    private static final int RANDOM = 60;
    private static final int ROUND = 61;
    private static final int SIN = 62;
    private static final int SQRT = 63;
    private static final int TAN = 64;
    private static final int TODEGREES = 65;
    private static final int TORADIANS = 66;
    private static final int PI = 67;
    private static final int E = 68;
    private static final int FORMAT_NUMBER = 69;
    private static final int PARSE_NUMBER = 70;
    private static final int FORMAT_DATE = 71;
    private static final int PARSE_DATE = 72;
    private static final int GET_CONCEPT = 73;
    private static final int GET_LOCAL_NAME = 74;
    private static final int GET_EXTERNAL_NAME = 75;
    private static final int GET_DEPENDENCIES = 76;
    private static final int GET_ACTION_TEXT = 77;
    private static final int JUMP_TO = 78;
    private static final int GOTO_FIRST = 79;
    private static final int JUMP_TO_FIRST_UNASKED = 80;
    private static final int GOTO_PREVIOUS = 81;
    private static final int ERASE_DATA = 82;
    private static final int GOTO_NEXT = 83;
    private static final int MEAN = 84;
    private static final int STDDEV = 85;
    private static final int SUSPEND_TO_FLOPPY = 86;
    private static final int REGEX_MATCH = 87;
    private static final int CREATE_TEMP_FILE = 88;
    private static final int SAVE_DATA = 89;
    private static final int EXEC = 90;
    private static final int SET_STATUS_COMPLETED = 91;
    private static final int SHOW_TABLE_OF_ANSWERS = 92;
    private static final int PARSE_EXPR = 93;
    /**
    The list of function names, how many arguments they take, and an Object referring the the index within this array
     */
    private static final Object FUNCTION_ARRAY[][] = {
        {"desc", ONE, new Integer(DESC)},
        {"isAsked", ONE, new Integer(ISASKED)},
        {"isNA", ONE, new Integer(ISNA)},
        {"isRefused", ONE, new Integer(ISREFUSED)},
        {"isUnknown", ONE, new Integer(ISUNKNOWN)},
        {"isNotUnderstood", ONE, new Integer(ISNOTUNDERSTOOD)},
        {"isDate", ONE, new Integer(ISDATE)},
        {"isAnswered", ONE, new Integer(ISANSWERED)},
        {"toDate", ONE, new Integer(GETDATE)},
        {"toYear", ONE, new Integer(GETYEAR)},
        {"toMonth", ONE, new Integer(GETMONTH)},
        {"toMonthNum", ONE, new Integer(GETMONTHNUM)},
        {"toDay", ONE, new Integer(GETDAY)},
        {"toWeekday", ONE, new Integer(GETWEEKDAY)},
        {"toTime", ONE, new Integer(GETTIME)},
        {"toHour", ONE, new Integer(GETHOUR)},
        {"toMinute", ONE, new Integer(GETMINUTE)},
        {"toSecond", ONE, new Integer(GETSECOND)},
        {"getNow", ZERO, new Integer(NOW)},
        {"getStartTime", ZERO, new Integer(STARTTIME)},
        {"count", UNLIMITED, new Integer(COUNT)},
        {"list", UNLIMITED, new Integer(ANDLIST)},
        {"orlist", UNLIMITED, new Integer(ORLIST)},
        {"newDate", UNLIMITED, new Integer(NEWDATE)},
        {"newTime", UNLIMITED, new Integer(NEWTIME)},
        {"isInvalid", ONE, new Integer(ISINVALID)},
        {"min", UNLIMITED, new Integer(MIN)},
        {"max", UNLIMITED, new Integer(MAX)},
        {"toDayNum", ONE, new Integer(GETDAYNUM)},
        {"hasComment", ONE, new Integer(HASCOMMENT)},
        {"getComment", ONE, new Integer(GETCOMMENT)},
        {"getType", ONE, new Integer(GETTYPE)},
        {"isSpecial", ONE, new Integer(ISSPECIAL)},
        {"numAnsOptions", ONE, new Integer(NUMANSOPTIONS)},
        {"getAnsOption", UNLIMITED, new Integer(GETANSOPTION)},
        {"charAt", TWO, new Integer(CHARAT)},
        {"compareTo", TWO, new Integer(COMPARETO)},
        {"compareToIgnoreCase", TWO, new Integer(COMPARETOIGNORECASE)},
        {"endsWith", TWO, new Integer(ENDSWITH)},
        {"indexOf", UNLIMITED, new Integer(INDEXOF)},
        {"lastIndexOf", UNLIMITED, new Integer(LASTINDEXOF)},
        {"length", ONE, new Integer(LENGTH)},
        {"startsWith", UNLIMITED, new Integer(STARTSWITH)},
        {"substring", UNLIMITED, new Integer(SUBSTRING)},
        {"toLowerCase", ONE, new Integer(TOLOWERCASE)},
        {"toUpperCase", ONE, new Integer(TOUPPERCASE)},
        {"trim", ONE, new Integer(TRIM)},
        {"isNumber", ONE, new Integer(ISNUMBER)},
        {"fileExists", ONE, new Integer(FILEEXISTS)},
        {"abs", ONE, new Integer(ABS)},
        {"acos", ONE, new Integer(ACOS)},
        {"asin", ONE, new Integer(ASIN)},
        {"atan", ONE, new Integer(ATAN)},
        {"atan2", TWO, new Integer(ATAN2)},
        {"ceil", ONE, new Integer(CEIL)},
        {"cos", ONE, new Integer(COS)},
        {"exp", ONE, new Integer(EXP)},
        {"floor", ONE, new Integer(FLOOR)},
        {"log", ONE, new Integer(LOG)},
        {"pow", TWO, new Integer(POW)},
        {"random", ZERO, new Integer(RANDOM)},
        {"round", ONE, new Integer(ROUND)},
        {"sin", ONE, new Integer(SIN)},
        {"sqrt", ONE, new Integer(SQRT)},
        {"tan", ONE, new Integer(TAN)},
        {"todegrees", ONE, new Integer(TODEGREES)},
        {"toradians", ONE, new Integer(TORADIANS)},
        {"pi", ZERO, new Integer(PI)},
        {"e", ZERO, new Integer(E)},
        {"formatNumber", TWO, new Integer(FORMAT_NUMBER)},
        {"parseNumber", TWO, new Integer(PARSE_NUMBER)},
        {"formatDate", TWO, new Integer(FORMAT_DATE)},
        {"parseDate", TWO, new Integer(PARSE_DATE)},
        {"getConcept", ONE, new Integer(GET_CONCEPT)},
        {"getLocalName", ONE, new Integer(GET_LOCAL_NAME)},
        {"getExternalName", ONE, new Integer(GET_EXTERNAL_NAME)},
        {"getDependencies", ONE, new Integer(GET_DEPENDENCIES)},
        {"getActionText", ONE, new Integer(GET_ACTION_TEXT)},
        {"jumpTo", ONE, new Integer(JUMP_TO)},
        {"gotoFirst", ZERO, new Integer(GOTO_FIRST)},
        {"jumpToFirstUnasked", ZERO, new Integer(JUMP_TO_FIRST_UNASKED)},
        {"gotoPrevious", ZERO, new Integer(GOTO_PREVIOUS)},
        {"eraseData", ZERO, new Integer(ERASE_DATA)},
        {"gotoNext", ZERO, new Integer(GOTO_NEXT)},
        {"mean", UNLIMITED, new Integer(MEAN)},
        {"stddev", UNLIMITED, new Integer(STDDEV)},
        {"suspendToFloppy", UNLIMITED, new Integer(SUSPEND_TO_FLOPPY)},
        {"regexMatch", TWO, new Integer(REGEX_MATCH)},
        {"createTempFile", ZERO, new Integer(CREATE_TEMP_FILE)},
        {"saveData", ONE, new Integer(SAVE_DATA)},
        {"exec", ONE, new Integer(EXEC)},
        {"setStatusCompleted", ZERO, new Integer(SET_STATUS_COMPLETED)},
        {"showTableOfAnswers", UNLIMITED, new Integer(SHOW_TABLE_OF_ANSWERS)},
        {"parseExpr", ONE, new Integer(PARSE_EXPR)},
       

     
            };

  /**
    The list of functions, mapping the name to the Integer value for use in the main function switch statement
  */
  private static final Hashtable FUNCTIONS = new Hashtable();

  static {
    for (int i=0; i < FUNCTION_ARRAY.length; ++i) {
            FUNCTIONS.put(FUNCTION_ARRAY[i][FUNCTION_NAME], FUNCTION_ARRAY[i][FUNCTION_INDEX]);
        }
    }

    /**
    This class performs the requested function on the parameter list and returns the resulting Datum object.
    @param context  The Context
    @param name  The name of the function
    @param params The list of parameters for the function
    @param line The line number from which the function was called (for debugging purposes)
    @param column The column number from which the function was called (for debugging purposes)
    @return  The Datum holding the value
    @see Context
    @see Datum
     */
    public Datum function(Context context,
                           String name,
                           Vector params,
                           int line,
                           int column) {
        /* passed a vector of Datum values */
        try {
            Integer func = (Integer) FUNCTIONS.get(name);
            int funcNum = 0;

            if (func == null || ((funcNum = func.intValue()) < 0)) {
                /* then not found - could consider calling JavaBean! */
                setError(context.get("unsupported_function") + name, line, column, null);
                return Datum.getInstance(context, Datum.INVALID);
            }

            Integer numParams = (Integer) FUNCTION_ARRAY[funcNum][FUNCTION_NUM_PARAMS];

            if (!(UNLIMITED.equals(numParams) || params.size() == numParams.intValue())) {
                setError(context.get("function") + name + context.get("expects") + " " + numParams + " " + context.get("parameters"), line, column, params.size());
                return Datum.getInstance(context, Datum.INVALID);
            }

            Datum datum = null;

            if (params.size() > 0) {
                datum = context.getParam(params.elementAt(0));
            }


            switch (funcNum) {
                case DESC: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,nodeName);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context, 
                context.getParser().parseJSP(context,node.getReadback(context.getLanguage())),
                Datum.STRING);
                 */
                }
                case ISINVALID:
                    return new Datum(context, datum.isType(Datum.INVALID));
                case ISASKED:
                    return new Datum(context, !(datum.isType(Datum.NA) || datum.isType(Datum.UNASKED) || datum.isType(Datum.INVALID)));
                case ISNA:
                    return new Datum(context, datum.isType(Datum.NA));
                case ISREFUSED:
                    return new Datum(context, datum.isType(Datum.REFUSED));
                case ISUNKNOWN:
                    return new Datum(context, datum.isType(Datum.UNKNOWN));
                case ISNOTUNDERSTOOD:
                    return new Datum(context, datum.isType(Datum.NOT_UNDERSTOOD));
                case ISDATE:
                    return new Datum(context, datum.isType(Datum.DATE));
                case ISANSWERED:
                    return new Datum(context, datum.exists());
                case GETDATE:
                    return new Datum(context, datum.dateVal(), Datum.DATE);
                case GETYEAR:
                    return new Datum(context, datum.dateVal(), Datum.YEAR);
                case GETMONTH:
                    return new Datum(context, datum.dateVal(), Datum.MONTH);
                case GETMONTHNUM:
                    return new Datum(context, datum.dateVal(), Datum.MONTH_NUM);
                case GETDAY:
                    return new Datum(context, datum.dateVal(), Datum.DAY);
                case GETWEEKDAY:
                    return new Datum(context, datum.dateVal(), Datum.WEEKDAY);
                case GETTIME:
                    return new Datum(context, datum.dateVal(), Datum.TIME);
                case GETHOUR:
                    return new Datum(context, datum.dateVal(), Datum.HOUR);
                case GETMINUTE:
                    return new Datum(context, datum.dateVal(), Datum.MINUTE);
                case GETSECOND:
                    return new Datum(context, datum.dateVal(), Datum.SECOND);
                case NOW:
                    return new Datum(context, new Date(System.currentTimeMillis()), Datum.DATE);
                case STARTTIME:
                /*
                return new Datum(context, startTime,Datum.TIME);
                 */
                case COUNT: // unlimited number of parameters
                {
                    long count = 0;
                    for (int i = 0; i < params.size(); ++i) {
                        datum = context.getParam(params.elementAt(i));
                        if (datum.booleanVal()) {
                            ++count;
                        }
                    }
                    return new Datum(context, count);
                }
                case ANDLIST:
                case ORLIST: // unlimited number of parameters
                {
                    StringBuffer sb = new StringBuffer();
                    Vector v = new Vector();
                    for (int i = 0; i < params.size(); ++i) {
                        datum = context.getParam(params.elementAt(i));
                        if (datum.exists()) {
                            v.addElement(datum);
                        }
                    }
                    for (int i = 0; i < v.size(); ++i) {
                        datum = (Datum) v.elementAt(i);
                        if (sb.length() > 0) {
                            if ((v.size() > 2)) {
                                sb.append(", ");
                            } else {
                                sb.append(" ");
                            }
                        }
                        if ((i == (v.size() - 1)) && (v.size() > 1)) {
                            if (funcNum == ANDLIST) {
                                sb.append(context.get("and") + " ");
                            } else if (funcNum == ORLIST) {
                                sb.append(context.get("or") + " ");
                            }
                        }
                        sb.append(datum.stringVal());
                    }
                    return new Datum(context, sb.toString(), Datum.STRING);
                }
                case NEWDATE:
                    if (params.size() == 1) {
                        /* newDate(int weekdaynum) */
                        GregorianCalendar gc = new GregorianCalendar();  // should happen infrequently (not a garbage collection problem?)
                        gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        gc.add(Calendar.DAY_OF_WEEK, ((int) (context.getParam(params.elementAt(0)).doubleVal()) - 1));
                        return new Datum(context, gc.getTime(), Datum.WEEKDAY);
                    }
                    if (params.size() == 2) {
                        /* newDate(String image, String mask) */
                        return new Datum(context, context.getParam(params.elementAt(0)).stringVal(), Datum.DATE, context.getParam(params.elementAt(1)).stringVal());
                    } else if (params.size() == 3) {
                        /* newDate(int y, int m, int d) */
                        StringBuffer sb = new StringBuffer();
                        sb.append(context.getParam(params.elementAt(0)).stringVal() + "/");
                        sb.append(context.getParam(params.elementAt(1)).stringVal() + "/");
                        sb.append(context.getParam(params.elementAt(2)).stringVal());
                        return new Datum(context, sb.toString(), Datum.DATE, "yy/mm/dd");
                    }
                    break;
                case NEWTIME:
                    if (params.size() == 2) {
                        /* newTime(String image, String mask) */
                        return new Datum(context, context.getParam(params.elementAt(0)).stringVal(), Datum.TIME, context.getParam(params.elementAt(1)).stringVal());
                    } else if (params.size() == 3) {
                        /* newTime(int hh, int mm, int ss) */
                        StringBuffer sb = new StringBuffer();
                        sb.append(context.getParam(params.elementAt(0)).stringVal() + ":");
                        sb.append(context.getParam(params.elementAt(1)).stringVal() + ":");
                        sb.append(context.getParam(params.elementAt(2)).stringVal());
                        return new Datum(context, sb.toString(), Datum.TIME, "hh:mm:ss");
                    }
                    break;
                case MIN:
                    if (params.size() == 0) {
                        return Datum.getInstance(context, Datum.INVALID);
                    } else {
                        Datum minVal = null;

                        for (int i = 0; i < params.size(); ++i) {
                            Datum a = context.getParam(params.elementAt(i));

                            if (i == 0) {
                                minVal = a;
                            } else {
                                if (DatumMath.lt(a, minVal).booleanVal()) {
                                    minVal = a;
                                }
                            }
                        }
                        return new Datum(minVal);
                    }
                case MAX:
                    if (params.size() == 0) {
                        return Datum.getInstance(context, Datum.INVALID);
                    } else {
                        Datum maxVal = null;

                        for (int i = 0; i < params.size(); ++i) {
                            Datum a = context.getParam(params.elementAt(i));

                            if (i == 0) {
                                maxVal = a;
                            } else {
                                if (DatumMath.gt(a, maxVal).booleanVal()) {
                                    maxVal = a;
                                }
                            }
                        }
                        return new Datum(maxVal);
                    }
                case GETDAYNUM:
                    return new Datum(context, datum.dateVal(), Datum.DAY_NUM);
                case HASCOMMENT: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return new Datum(context,false);
                }
                String comment = node.getComment();
                return new Datum(context, (comment != null && comment.trim().length() > 0) ? true : false);
                 */
                }
                case GETCOMMENT: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context, node.getComment(), Datum.STRING);
                 */
                }
                case GETTYPE:
                    return new Datum(context, datum.getTypeName(), Datum.STRING);
                case ISSPECIAL:
                    return new Datum(context, datum.isSpecial());
                case NUMANSOPTIONS: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                Vector choices = node.getAnswerChoices();
                return new Datum(context, choices.size());
                 */
                }
                case GETANSOPTION: {
                /*
                if (params.size() < 1 || params.size() > 2)
                break;
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                Vector choices = node.getAnswerChoices();
                if (params.size() == 1) {
                if (datum.isSpecial()) {
                return new Datum(datum);
                }
                else {
                String s = datum.stringVal();
                for (int i=0;i<choices.size();++i) {
                AnswerChoice ac = (AnswerChoice) choices.elementAt(i);
                ac.parse(context);  // in case language has changed
                if (ac.getValue().equals(s)) {  // what will parsing answerchoice do to stored datum value?
                return new Datum(context, ac.getMessage(), Datum.STRING);
                }
                }
                return Datum.getInstance(context,Datum.INVALID);
                }
                }
                else { // if (params.size() == 2) {
                datum = context.getParam(params.elementAt(1));
                if (!datum.isNumeric()) {
                setError(functionError(context, funcNum,Datum.NUMBER,2),datum);
                return Datum.getInstance(context,Datum.INVALID);
                }
                int index = (int) datum.doubleVal();
                if (index < 0) {
                setError(context.get("index_too_low"),index);
                return Datum.getInstance(context,Datum.INVALID);
                }
                else if (index >= choices.size()) {
                setError(context.get("index_too_high"),index);
                return Datum.getInstance(context,Datum.INVALID);
                }
                else {
                AnswerChoice ac = (AnswerChoice) choices.elementAt(index);
                ac.parse(context);
                return new Datum(context,ac.getMessage(), Datum.STRING);
                }
                }
                 */
                }
                case CHARAT: {
                    String src = datum.stringVal();
                    datum = context.getParam(params.elementAt(1));
                    if (!datum.isNumeric()) {
                        setError(functionError(context, funcNum, Datum.NUMBER, 2), datum);
                        return Datum.getInstance(context, Datum.INVALID);
                    }
                    int index = (int) datum.doubleVal();
                    if (index < 0) {
                        setError(context.get("index_too_low"), index);
                        return Datum.getInstance(context, Datum.INVALID);
                    } else if (index >= src.length()) {
                        setError(context.get("index_too_high"), index);
                        return Datum.getInstance(context, Datum.INVALID);
                    } else {
                        return new Datum(context, String.valueOf(src.charAt(index)), Datum.STRING);
                    }
                }
                case COMPARETO:
                    return new Datum(context, datum.stringVal().compareTo(context.getParam(params.elementAt(1)).stringVal()));
                case COMPARETOIGNORECASE: {
                    String src = datum.stringVal().toLowerCase();
                    String dst = context.getParam(params.elementAt(1)).stringVal().toLowerCase();
                    return new Datum(context, src.compareTo(dst));
                }
                case ENDSWITH:
                    return new Datum(context, datum.stringVal().endsWith(context.getParam(params.elementAt(1)).stringVal()));
                case INDEXOF: {
                    if (params.size() < 2 || params.size() > 3) {
                        break;
                    }

                    String str1 = context.getParam(params.elementAt(0)).stringVal();
                    String str2 = context.getParam(params.elementAt(1)).stringVal();

                    if (params.size() == 2) {
                        return new Datum(context, str1.indexOf(str2));
                    } else if (params.size() == 3) {
                        Datum datum2 = context.getParam(params.elementAt(2));
                        if (!datum2.isNumeric()) {
                            setError(functionError(context, funcNum, Datum.NUMBER, 3), datum2);
                            return Datum.getInstance(context, Datum.INVALID);
                        }
                        int index = (int) datum2.doubleVal();
                        if (index < 0) {
                            setError(context.get("index_too_low"), index);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else if (index >= str1.length()) {
                            setError(context.get("index_too_high"), index);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else {
                            return new Datum(context, str1.indexOf(str2, index));
                        }
                    } else {
                        break;
                    }
                }
                case LASTINDEXOF: {
                    if (params.size() < 2 || params.size() > 3) {
                        break;
                    }

                    String str1 = context.getParam(params.elementAt(0)).stringVal();
                    String str2 = context.getParam(params.elementAt(1)).stringVal();

                    if (params.size() == 2) {
                        return new Datum(context, str1.lastIndexOf(str2));
                    } else if (params.size() == 3) {
                        Datum datum2 = context.getParam(params.elementAt(2));
                        if (!datum2.isNumeric()) {
                            setError(functionError(context, funcNum, Datum.NUMBER, 3), datum2);
                            return Datum.getInstance(context, Datum.INVALID);
                        }
                        int index = (int) datum2.doubleVal();
                        if (index < 0) {
                            setError(context.get("index_too_low"), index);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else if (index >= str1.length()) {
                            setError(context.get("index_too_high"), index);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else {
                            return new Datum(context, str1.lastIndexOf(str2, index));
                        }
                    } else {
                        break;
                    }
                }
                case LENGTH:
                    return new Datum(context, datum.stringVal().length());
                case STARTSWITH: {
                    if (params.size() < 2 || params.size() > 3) {
                        break;
                    }

                    String str1 = context.getParam(params.elementAt(0)).stringVal();
                    String str2 = context.getParam(params.elementAt(1)).stringVal();

                    if (params.size() == 2) {
                        return new Datum(context, str1.startsWith(str2));
                    } else if (params.size() == 3) {
                        Datum datum2 = context.getParam(params.elementAt(2));
                        if (!datum2.isNumeric()) {
                            setError(functionError(context, funcNum, Datum.NUMBER, 3), datum2);
                            return Datum.getInstance(context, Datum.INVALID);
                        }
                        int index = (int) datum2.doubleVal();
                        if (index < 0) {
                            setError(context.get("index_too_low"), index);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else if (index >= str1.length()) {
                            setError(context.get("index_too_high"), index);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else {
                            return new Datum(context, str1.startsWith(str2, index));
                        }
                    } else {
                        break;
                    }
                }
                case SUBSTRING: {
                    if (params.size() < 2 || params.size() > 3) {
                        break;
                    }

                    String str1 = context.getParam(params.elementAt(0)).stringVal();
                    Datum start = context.getParam(params.elementAt(1));
                    Datum end = null;
                    int from, to;

                    if (params.size() == 3) {
                        end = context.getParam(params.elementAt(2));
                    }

                    if (!start.isNumeric()) {
                        setError(functionError(context, funcNum, Datum.NUMBER, 2), start);
                        return Datum.getInstance(context, Datum.INVALID);
                    } else {
                        from = (int) start.doubleVal();
                        if (from < 0) {
                            setError(context.get("index_too_low"), from);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else if (from >= str1.length()) {
                            setError(context.get("index_too_high"), from);
                            return Datum.getInstance(context, Datum.INVALID);
                        }

                    }

                    if (end != null) {
                        if (!end.isNumeric()) {
                            setError(functionError(context, funcNum, Datum.NUMBER, 3), end);
                            return Datum.getInstance(context, Datum.INVALID);
                        } else {
                            to = (int) end.doubleVal();
                            if (to < from) {
                                setError(context.get("index_too_low"), to);
                                return Datum.getInstance(context, Datum.INVALID);
                            } else if (to >= str1.length()) {
                                setError(context.get("index_too_high"), to);
                                return Datum.getInstance(context, Datum.INVALID);
                            } else {
                                return new Datum(context, str1.substring(from, to), Datum.STRING);
                            }
                        }
                    } else {
                        return new Datum(context, str1.substring(from), Datum.STRING);
                    }
                }
                case TOLOWERCASE:
                    return new Datum(context, datum.stringVal().toLowerCase(), Datum.STRING);
                case TOUPPERCASE:
                    return new Datum(context, datum.stringVal().toUpperCase(), Datum.STRING);
                case TRIM:
                    return new Datum(context, datum.stringVal().trim(), Datum.STRING);
                case ISNUMBER:
                    return new Datum(context, datum.isNumeric());
                case FILEEXISTS: {
                /* FIXME Needs to be modified to check for not only the actual filenames in the completed dir,
                but also the pending filenames as indicated by the temp files in the working dir */
                /*
                String fext = datum.stringVal();
                if (fext == null)
                return new Datum(context,false);
                fext = fext.trim();
                if (fext.length() == 0)
                return new Datum(context,false);;
                // now check whether this name is available in both working and completed dirs
                File file;
                Schedule sched = context.getSchedule();
                String fname;
                // Working dir - read schedules and get their FILENAMEs 
                ScheduleList interviews = new ScheduleList(context, sched.getReserved(Schedule.WORKING_DIR), true);
                Schedule sc = null;
                Vector schedules = interviews.getSchedules();
                for (int i=0;i<schedules.size();++i) {
                sc = (Schedule) schedules.elementAt(i);
                if (sc.getReserved(Schedule.FILENAME).equals(fext)) {
                if (sc.getReserved(Schedule.LOADED_FROM).equals(context.dataLogger.getFilename())) {
                continue;  // since examining the current file
                }
                else {
                return new Datum(context,true);
                }
                }
                }
                // For Completed dir - check actual filenames
                try {
                fname = sched.getReserved(Schedule.COMPLETED_DIR) + fext + ".jar";
                if (logger.isDebugEnabled()) logger.log(Level.FINE,("exists(" + fname + ")");
                file = new File(fname);
                if (file.exists())
                return new Datum(context,true);
                }
                catch (SecurityException e) {
                logger.log(Level.SEVERE,e.getMessage(),e);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context,false);
                 */
                }
                case ABS:
                    return new Datum(context, Math.abs(datum.doubleVal()));
                case ACOS:
                    return new Datum(context, Math.acos(datum.doubleVal()));
                case ASIN:
                    return new Datum(context, Math.asin(datum.doubleVal()));
                case ATAN:
                    return new Datum(context, Math.atan(datum.doubleVal()));
                case ATAN2:
                    return new Datum(context, Math.atan2(datum.doubleVal(), context.getParam(params.elementAt(1)).doubleVal()));
                case CEIL:
                    return new Datum(context, Math.ceil(datum.doubleVal()));
                case COS:
                    return new Datum(context, Math.cos(datum.doubleVal()));
                case EXP:
                    return new Datum(context, Math.exp(datum.doubleVal()));
                case FLOOR:
                    return new Datum(context, Math.floor(datum.doubleVal()));
                case LOG:
                    return new Datum(context, Math.log(datum.doubleVal()));
                case POW:
                    return new Datum(context, Math.pow(datum.doubleVal(), context.getParam(params.elementAt(1)).doubleVal()));
                case RANDOM:
                    return new Datum(context, Math.random());
                case ROUND:
                    return new Datum(context, Math.round(datum.doubleVal()));
                case SIN:
                    return new Datum(context, Math.sin(datum.doubleVal()));
                case SQRT:
                    return new Datum(context, Math.sqrt(datum.doubleVal()));
                case TAN:
                    return new Datum(context, Math.tan(datum.doubleVal()));
                case TODEGREES:
//          return new Datum(context, Math.toDegrees(datum.doubleVal()));        
                    return new Datum(context, Double.NaN);
                case TORADIANS:
//          return new Datum(context, Math.toRadians(datum.doubleVal()));        
                    return new Datum(context, Double.NaN);
                case PI:
                    return new Datum(context, Math.PI);
                case E:
                    return new Datum(context, Math.E);
                case FORMAT_NUMBER:
                    return new Datum(context, context.formatNumber(new Double(datum.doubleVal()), context.getParam(params.elementAt(1)).stringVal()), Datum.STRING);
                case PARSE_NUMBER:
                    return new Datum(context, context.parseNumber(datum.stringVal(), context.getParam(params.elementAt(1)).stringVal()).doubleValue());
                case FORMAT_DATE:
                    return new Datum(context, context.formatDate(datum.dateVal(), context.getParam(params.elementAt(1)).stringVal()), Datum.STRING);
                case PARSE_DATE:
                    return new Datum(context, context.parseDate(datum.stringVal(), context.getParam(params.elementAt(1)).stringVal()), Datum.DATE, context.getParam(params.elementAt(1)).stringVal());
                case GET_CONCEPT: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context, node.getConcept(), Datum.STRING);
                 */
                }
                case GET_LOCAL_NAME: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context, node.getLocalName(), Datum.STRING);
                 */
                }
                case GET_EXTERNAL_NAME: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context, node.getExternalName(), Datum.STRING);
                 */
                }
                case GET_DEPENDENCIES: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context, node.getDependencies(), Datum.STRING);
                 */
                }
                case GET_ACTION_TEXT: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                return new Datum(context, node.getQuestionOrEval(), Datum.STRING);
                 */
                }
                case JUMP_TO: {
                /*
                String nodeName = datum.getName();
                Node node = null;
                if (nodeName == null || ((node = getNode(nodeName)) == null)) {
                setError(context.get("unknown_node") + nodeName, line, column,null);
                return Datum.getInstance(context,Datum.INVALID);
                }
                context.gotoNode(node);
                return new Datum(context, "", Datum.STRING);
                 */
                }
                case GOTO_FIRST:
                    context.gotoFirst();
                    return new Datum(context, "", Datum.STRING);
                case JUMP_TO_FIRST_UNASKED:
                    context.jumpToFirstUnasked();
                    return new Datum(context, "", Datum.STRING);
                case GOTO_PREVIOUS:
                    context.gotoPrevious();
                    return new Datum(context, "", Datum.STRING);
                case ERASE_DATA:
                    context.resetEvidence();
                    return new Datum(context, "", Datum.STRING);
                case GOTO_NEXT:
                    context.gotoNext();
                    return new Datum(context, "", Datum.STRING);
                case MEAN:
                    if (params.size() == 0) {
                        return Datum.getInstance(context, Datum.INVALID);
                    } else {
                        int count = 0;
                        double sum = 0;
                        double mean = 0;

                        for (int i = 0; i < params.size(); ++i) {
                            Datum a = context.getParam(params.elementAt(i));
                            ++count;
                            sum += a.doubleVal();
                        }
                        mean = sum / count;
                        return new Datum(context, mean);
                    }
                case STDDEV:
                    if (params.size() == 0) {
                        return Datum.getInstance(context, Datum.INVALID);
                    } else {
                        int count = 0;
                        double sum = 0;
                        double mean = 0;
                        double sumsqdiff = 0;
                        double std = 0;

                        for (int i = 0; i < params.size(); ++i) {
                            Datum a = context.getParam(params.elementAt(i));
                            ++count;
                            sum += a.doubleVal();
                        }
                        mean = sum / count;

                        for (int i = 0; i < params.size(); ++i) {
                            Datum a = context.getParam(params.elementAt(i));
                            double diff = (a.doubleVal() - mean);
                            sumsqdiff += (diff * diff);
                        }
                        std = Math.sqrt(sumsqdiff / (count - 1));
                        return new Datum(context, std);
                    }
                case SUSPEND_TO_FLOPPY: {
                /* revise this so can jump to next available question, if appropriate */
                /*
                if (params.size() == 1) {
                // then set the starting step in the file to be saved (but not in the master file) 
                String nodeName = datum.getName();
                Node n = getNode(nodeName);
                if (n == null) {
                setError(context.get("unknown_node") + nodeName,null);
                }
                else {
                int result = getStep(n);        
                StringBuffer sb = new StringBuffer("RESERVED\t");
                sb.append(Schedule.RESERVED_WORDS[Schedule.STARTING_STEP]).append("\t");
                sb.append(result).append("\t").append(System.currentTimeMillis()).append("\t\t\t");
                context.dataLogger.println(sb.toString());
                }
                }
                String savedFile = context.suspendToFloppy();
                return new Datum(context, (savedFile == null) ? "null" : savedFile, Datum.STRING);
                 */
                }
                case REGEX_MATCH: {
                    /** syntax:  regexMatch(text,pattern) */
                    String text = context.getParam(params.elementAt(0)).stringVal();
                    String pattern = context.getParam(params.elementAt(1)).stringVal();
                    if (pattern == null || pattern.trim().length() == 0) {
                        return Datum.getInstance(context, Datum.INVALID);
                    }
                    try {
                        if (Pattern.matches(pattern, text)) {
                            return new Datum(context, true);
                        } else {
                            return new Datum(context, false);
                        }
                    } catch (PatternSyntaxException ex) {
                        logger.log(Level.SEVERE, "Invalid Perl Regular Expression Formatting Mask" + pattern + ex.getMessage());
                        return Datum.getInstance(context, Datum.INVALID);
                    }

                }
                case CREATE_TEMP_FILE: {
                /*
                String temp = EvidenceIO.createTempFile();
                if (temp == null) {
                return Datum.getInstance(context,Datum.INVALID);
                }
                else {
                return new Datum(context,temp,Datum.STRING);
                }
                 */
                }
                case SAVE_DATA: {
                /*
                String file = context.getParam(params.elementAt(0)).stringVal();
                boolean ok = EvidenceIO.saveAll(context.getSchedule(),file);
                return new Datum(context,ok);
                 */
                }
                case EXEC: {
                /*
                return new Datum(context,EvidenceIO.exec(datum.stringVal()));
                 */
                }
                case SET_STATUS_COMPLETED: {
                /* allow specification of a file as completed, even if it is mid-stream */
                /* HUGE hack - requires refernce to LoginServlet! */
                /*
                return new Datum(context,context.setStatusCompleted());
                 */
                }
                case SHOW_TABLE_OF_ANSWERS: {
                }
                case PARSE_EXPR: {
                    String src = datum.stringVal();
                    java.util.StringTokenizer st = new java.util.StringTokenizer(src, "`", true);
                    StringBuffer sb = new StringBuffer();
                    String s;
                    boolean inside = false;

//		    logger.info(src);

                    while (st.hasMoreTokens()) {
                        s = st.nextToken();
//		      logger.info(s);
                        if ("`".equals(s)) {
                            inside = (inside) ? false : true;
                            continue;
                        } else {
                            if (inside) {
//		        	logger.info("[Inside - parsing] " + s);
                                parser.ReInit(new StringReader(s));
                                Datum ans = parser.parse(context);
                                sb.append(ans.stringVal(true));  // so that see the *REFUSED*, etc as part of questions
                            } else {
//		        	logger.info("[Outside - inline] " + s);
                                sb.append(s);
                            }
                        }
                    }
                    return new Datum(context, sb.toString(), Datum.STRING);
                }
            }
        } catch (Exception t) {
            logger.log(Level.SEVERE, t.getMessage(), t);
        }
        setError("unexpected error running function " + name, line, column, null);
        return Datum.getInstance(context, Datum.INVALID);
    }

    /**
    Records a parsing error.
    @param s  The error message
    @param line The line at which it occured
    @param column The column at which it occured
    @param val an integer value (e.g. to show IndexOutOfBounds exceptions
     */
    private void setError(String s,
                           int line,
                           int column,
                           int val) {
        setError(s, line, column, new Integer(val));
    }

    /**
    Records a parsing error.
    @param s  The error message
    @param val an integer value (e.g. to show IndexOutOfBounds exceptions
     */
    private void setError(String s,
                           int val) {
        setError(s, new Integer(val));
    }

    /**
    Records a parsing error.
    @param s  The error message
    @param line The line at which it occured
    @param column The column at which it occured
    @param val An Object (integer or Datum) showing the bad value.
     */
    private void setError(String s,
                           int line,
                           int column,
                           Object val) {
        String msg = null;
        if (val != null) {
            msg = s + ": " + ((val instanceof Datum) ? ((Datum) val).stringVal() : val.toString());
        } else {
            msg = s;
        }
        logger.log(Level.SEVERE, "[" + line + ":" + column + "]" + msg);
    }

    /**
    Records a parsing error.
    @param s  The error message
    @param val An Object (integer or Datum) showing the bad value.
     */
    private void setError(String s,
                           Object val) {
        String msg = null;
        if (val != null) {
            msg = s + ": " + ((val instanceof Datum) ? ((Datum) val).stringVal() : val.toString());
        } else {
            msg = s;
        }
        logger.log(Level.SEVERE, msg);
    }

    /**
    Generate a debug message that tells how a function should be used (e.g. "Expects a Number at position 3").
    @param context  The context
    @param funcNum  The function number
    @param datumType  The expected data type of the Datum at 
    @param index  The index at which the error occured.
    @return A descriptive message.
     */
    private String functionError(Context context,
                                  int funcNum,
                                  int datumType,
                                  int index) {
        return FUNCTION_ARRAY[funcNum][FUNCTION_NAME] + " " +
            context.get("expects") + " " +
            Datum.getTypeName(context, datumType) + " " +
            context.get("at_index") + " " +
            index;
    }
}
