/* ******************************************************** 
 ** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
 ** $Header$
 ******************************************************** */
 
package org.dianexus.triceps;

/* import java.lang.*; */
/* import java.util.*; */
/* import java.io.*; */
/* import java.net.*; */ 
import java.util.Date;
import java.util.*;
import java.io.*;
//import java.sql.*;
import org.dianexus.triceps.modules.data.*;
import org.apache.log4j.Logger;

/** 
  This class lists all the functions which can operate on Datum objects.
  TODO:  Add introspection as in Velocity so that external functions can also process Datum objects
*/
public class Evidence implements VersionIF {
  static Logger logger = Logger.getLogger(Evidence.class);
	
	private static final int FUNCTION_INDEX = 2;
	private static final int FUNCTION_NUM_PARAMS = 1;
	private static final int FUNCTION_NAME = 0;
	private static final Integer ZERO = new Integer(0);
	private static final Integer ONE = new Integer(1);
	private static final Integer TWO = new Integer(2);
	private static final Integer UNLIMITED = new Integer(-1);

	/* Function declarations */
	private static final int 
	DESC = 0,
	ISASKED = 1,
	ISNA = 2,
	ISREFUSED = 3,
	ISUNKNOWN = 4,
	ISNOTUNDERSTOOD = 5,
	ISDATE = 6,
	ISANSWERED = 7,
	GETDATE = 8,
	GETYEAR = 9,
	GETMONTH = 10,
	GETMONTHNUM = 11,
	GETDAY = 12,
	GETWEEKDAY = 13,
	GETTIME = 14,
	GETHOUR = 15,
	GETMINUTE = 16,
	GETSECOND = 17,
	NOW = 18,
	STARTTIME = 19,
	COUNT = 20,
	ANDLIST = 21,
	ORLIST = 22,
	NEWDATE = 23,
	NEWTIME = 24,
	ISINVALID = 25,
	MIN = 26,
	MAX = 27,
	GETDAYNUM = 28,
	HASCOMMENT = 29,
	GETCOMMENT = 30,
	GETTYPE = 31,
	ISSPECIAL = 32,
	NUMANSOPTIONS = 33,
	GETANSOPTION = 34,
	CHARAT = 35,
	COMPARETO = 36,
	COMPARETOIGNORECASE = 37,
	ENDSWITH = 38,
	INDEXOF = 39,
	LASTINDEXOF = 40,
	LENGTH = 41,
	STARTSWITH = 42,
	SUBSTRING = 43,
	TOLOWERCASE = 44,
	TOUPPERCASE = 45,
	TRIM = 46,
	ISNUMBER = 47,
	FILEEXISTS = 48,
	ABS = 49,
	ACOS = 50,
	ASIN = 51,
	ATAN = 52,
	ATAN2 = 53,
	CEIL = 54,
	COS = 55,
	EXP = 56,
	FLOOR = 57,
	LOG = 58,
	POW = 59,
	RANDOM = 60,
	ROUND = 61,
	SIN = 62,
	SQRT = 63,
	TAN = 64,
	TODEGREES = 65,
	TORADIANS = 66,
	PI = 67,
	E = 68,
	FORMAT_NUMBER = 69,
	PARSE_NUMBER = 70,
	FORMAT_DATE = 71,
	PARSE_DATE = 72,
	GET_CONCEPT = 73,
	GET_LOCAL_NAME = 74,
	GET_EXTERNAL_NAME = 75,
	GET_DEPENDENCIES = 76,
	GET_ACTION_TEXT = 77,
	JUMP_TO = 78,
	GOTO_FIRST = 79,
	JUMP_TO_FIRST_UNASKED = 80,
	GOTO_PREVIOUS = 81,
	ERASE_DATA = 82,
	GOTO_NEXT = 83,
	MEAN = 84,
	STDDEV = 85,
	SUSPEND_TO_FLOPPY = 86,
	REGEX_MATCH = 87,
	CREATE_TEMP_FILE = 88,
	SAVE_DATA = 89,
	EXEC = 90,
	SET_STATUS_COMPLETED = 91,
	SHOW_TABLE_OF_ANSWERS = 92,
	PARSE_EXPR = 93,
	LOAD_INSTRUMENT = 94;

  /**
    The list of function names, how many arguments they take, and an Object referring the the index within this array
  */
	private static final Object FUNCTION_ARRAY[][] = {
		{ "desc", ONE, new Integer(DESC) },
		{ "isAsked", ONE, new Integer(ISASKED) },
		{ "isNA", ONE, new Integer(ISNA) },
		{ "isRefused", ONE, new Integer(ISREFUSED) },
		{ "isUnknown", ONE, new Integer(ISUNKNOWN) },
		{ "isNotUnderstood", ONE, new Integer(ISNOTUNDERSTOOD) },
		{ "isDate", ONE, new Integer(ISDATE) },
		{ "isAnswered", ONE, new Integer(ISANSWERED) },
		{ "toDate", ONE, new Integer(GETDATE) },
		{ "toYear", ONE, new Integer(GETYEAR) },
		{ "toMonth", ONE, new Integer(GETMONTH) },
		{ "toMonthNum", ONE, new Integer(GETMONTHNUM) },
		{ "toDay", ONE, new Integer(GETDAY) },
		{ "toWeekday", ONE, new Integer(GETWEEKDAY) },
		{ "toTime", ONE, new Integer(GETTIME) },
		{ "toHour", ONE, new Integer(GETHOUR) },
		{ "toMinute", ONE, new Integer(GETMINUTE) },
		{ "toSecond", ONE, new Integer(GETSECOND) },
		{ "getNow", ZERO, new Integer(NOW) },
		{ "getStartTime", ZERO, new Integer(STARTTIME) },
		{ "count", UNLIMITED, new Integer(COUNT) },
		{ "list", UNLIMITED, new Integer(ANDLIST) },
		{ "orlist", UNLIMITED, new Integer(ORLIST) },
		{ "newDate", UNLIMITED, new Integer(NEWDATE) },
		{ "newTime", UNLIMITED, new Integer(NEWTIME) },
		{ "isInvalid", ONE, new Integer(ISINVALID) },
		{ "min", UNLIMITED, new Integer(MIN) },
		{ "max", UNLIMITED, new Integer(MAX) },
		{ "toDayNum", ONE, new Integer(GETDAYNUM) },
		{ "hasComment", ONE, new Integer(HASCOMMENT) },
		{ "getComment", ONE, new Integer(GETCOMMENT) },
		{ "getType", ONE, new Integer(GETTYPE) },
		{ "isSpecial", ONE, new Integer(ISSPECIAL) },
		{ "numAnsOptions", ONE, new Integer(NUMANSOPTIONS) },
		{ "getAnsOption", UNLIMITED, new Integer(GETANSOPTION) },
		{ "charAt", TWO, new Integer(CHARAT) },
		{ "compareTo", TWO, new Integer(COMPARETO) },
		{ "compareToIgnoreCase", TWO, new Integer(COMPARETOIGNORECASE) },
		{ "endsWith", TWO, new Integer(ENDSWITH) },
		{ "indexOf", UNLIMITED, new Integer(INDEXOF) },
		{ "lastIndexOf", UNLIMITED, new Integer(LASTINDEXOF) },
		{ "length", ONE, new Integer(LENGTH) },
		{ "startsWith", UNLIMITED, new Integer(STARTSWITH) },
		{ "substring", UNLIMITED, new Integer(SUBSTRING) },
		{ "toLowerCase", ONE, new Integer(TOLOWERCASE) },
		{ "toUpperCase", ONE, new Integer(TOUPPERCASE) },
		{ "trim", ONE, new Integer(TRIM) },
		{ "isNumber", ONE, new Integer(ISNUMBER) },
		{ "fileExists", ONE, new Integer(FILEEXISTS) },
		{ "abs", ONE, new Integer(ABS) },
		{ "acos", ONE, new Integer(ACOS) },
		{ "asin", ONE, new Integer(ASIN) },
		{ "atan", ONE, new Integer(ATAN) },
		{ "atan2", TWO, new Integer(ATAN2) },
		{ "ceil", ONE, new Integer(CEIL) },
		{ "cos", ONE, new Integer(COS) },
		{ "exp", ONE, new Integer(EXP) },
		{ "floor", ONE, new Integer(FLOOR) },
		{ "log", ONE, new Integer(LOG) },
		{ "pow", TWO, new Integer(POW) },
		{ "random", ZERO, new Integer(RANDOM) },
		{ "round", ONE, new Integer(ROUND) },
		{ "sin", ONE, new Integer(SIN) },
		{ "sqrt", ONE, new Integer(SQRT) },
		{ "tan", ONE, new Integer(TAN) },
		{ "todegrees", ONE, new Integer(TODEGREES) },
		{ "toradians", ONE, new Integer(TORADIANS) },
		{ "pi", ZERO, new Integer(PI) },
		{ "e", ZERO, new Integer(E) },
		{ "formatNumber", TWO, new Integer(FORMAT_NUMBER) },
		{ "parsenumber", TWO, new Integer(PARSE_NUMBER) },
		{ "formatDate", TWO, new Integer(FORMAT_DATE) },
		{ "parseDate", TWO, new Integer(PARSE_DATE) },
		{ "getConcept", ONE, new Integer(GET_CONCEPT) },
		{ "getLocalName", ONE, new Integer(GET_LOCAL_NAME) },
		{ "getExternalName", ONE, new Integer(GET_EXTERNAL_NAME) },
		{ "getDependencies", ONE, new Integer(GET_DEPENDENCIES) },
		{ "getActionText", ONE, new Integer(GET_ACTION_TEXT) },
		{ "jumpTo", ONE, new Integer(JUMP_TO) },
		{ "gotoFirst", ZERO, new Integer(GOTO_FIRST) },
		{ "jumpToFirstUnasked", ZERO, new Integer(JUMP_TO_FIRST_UNASKED) },
		{ "gotoPrevious", ZERO, new Integer(GOTO_PREVIOUS) },
		{ "eraseData", ZERO, new Integer(ERASE_DATA) },
		{ "gotoNext", ZERO, new Integer(GOTO_NEXT) },
		{ "mean", UNLIMITED, new Integer(MEAN) },
		{ "stddev", UNLIMITED, new Integer(STDDEV) },
		{ "suspendToFloppy", UNLIMITED, new Integer(SUSPEND_TO_FLOPPY) },
		{ "regexMatch", TWO, new Integer(REGEX_MATCH) },
		{ "createTempFile", ZERO, new Integer(CREATE_TEMP_FILE) },
		{ "saveData", ONE, new Integer(SAVE_DATA) },
		{ "exec", ONE, new Integer(EXEC) },
		{ "setStatusCompleted", ZERO, new Integer(SET_STATUS_COMPLETED) },
		{ "showTableOfAnswers", UNLIMITED, new Integer(SHOW_TABLE_OF_ANSWERS) }, 
	    { "parseExpr",                 ONE,      new Integer(PARSE_EXPR) },
		{ "loadInstrument", ONE, new Integer(LOAD_INSTRUMENT) },
		};

  /**
    The list of functions, mapping the name to the Integer value for use in the main function switch statement
  */
	private static final Hashtable FUNCTIONS = new Hashtable();

	static {
		for (int i = 0; i < FUNCTION_ARRAY.length; ++i) {
			FUNCTIONS.put(FUNCTION_ARRAY[i][FUNCTION_NAME],
					FUNCTION_ARRAY[i][FUNCTION_INDEX]);
		}
	}

	private Hashtable aliases = new Hashtable();
	private Vector values = null;
	private int numReserved = 0;
	private Date startTime = new Date(System.currentTimeMillis());
	private org.dianexus.triceps.Logger errorLogger = new org.dianexus.triceps.Logger();
	Triceps triceps = null; // need package-level access in Qss

	/* public */static final Evidence NULL = new Evidence(null);

	/* public */Evidence(Triceps tri) {
		triceps = (tri == null) ? Triceps.NULL : tri;

	}

	/* public */void createReserved() {
		values = new Vector();
		numReserved = Schedule.RESERVED_WORDS.length; // these are always
		// added at the
		// beginning
		Schedule schedule = triceps.getSchedule();
		logger.debug("##Evidence.createReserved()");

		Value value = null;
		int idx = 0;

		for (idx = 0; idx < numReserved; ++idx) {
			value = new Value(Schedule.RESERVED_WORDS[idx], Datum.getInstance(
					triceps, Datum.UNKNOWN), idx, schedule);
			values.addElement(value);
			aliases.put(Schedule.RESERVED_WORDS[idx], new Integer(idx));
			logger.debug("##Evidence.createReserved(" + Schedule.RESERVED_WORDS[idx] + "," + schedule.getReserved(idx) + ")");
		}
	}

	/* public */void initReserved() {
		numReserved = Schedule.RESERVED_WORDS.length; // these are always
		// added at the
		// beginning
		Schedule schedule = triceps.getSchedule();
		if (schedule == null) {
			if (logger.isDebugEnabled())
				logger.debug("##Evidence.initReserved()-schedule=null");
			schedule = Schedule.NULL;
		}

		Value value = null;
		int idx = 0;

		for (idx = 0; idx < numReserved; ++idx) {
			value = (Value) values.elementAt(idx);
			value.setDatum(new Datum(triceps, schedule.getReserved(idx),
					Datum.STRING), null);
			// if (logger.isDebugEnabled()) logger.debug("##Evidence.initReserved(" +
			// Schedule.RESERVED_WORDS[idx] + "," + schedule.getReserved(idx) +
			// "," + ((Value) values.elementAt(idx)).isReserved() + "," +
			// ((Value) values.elementAt(idx)).getDatum().stringVal() + "," +
			// getNodeIndex(Schedule.RESERVED_WORDS[idx]) + "," +
			// getValue(Schedule.RESERVED_WORDS[idx]).getDatum().stringVal() +
			// ")");
		}
	}

	/* public */void init() {
		Node node = null;
		Value value = null;
		String init = null;
		Datum datum = null;
		int idx = numReserved;

		Schedule schedule = triceps.getSchedule();
		int size = schedule.size();
		int startingStep = Integer.parseInt(schedule.getReserved(Schedule.STARTING_STEP));
		String timeStamp = null;
		String startTime = schedule.getReserved(Schedule.START_TIME);
		
			String instrumentTitle = schedule.getReserved(Schedule.TITLE);
			String major_version = schedule.getReserved(Schedule.SCHED_VERSION_MAJOR);
			String minor_version = schedule.getReserved(Schedule.SCHED_VERSION_MINOR);
			
			int userId = 0;
			/* This should be added back in eventually *
			UserDAO tu = triceps.getUserDAO();
			if(tu!=null){
				userId = tu.getId();
			}
			*/
			
			if (DB_LOG_RESULTS) {
				triceps.setDtc(new DialogixTimingCalculator(instrumentTitle,major_version, minor_version, userId, startingStep, triceps.dataLogger.getFilename()));
                logger.debug("triceps.setDtc called with title "+instrumentTitle+" maj "+major_version+" min "+minor_version+" uid "+userId+" ss "+startingStep);
			}

		/* then assign the user-defined words */
            
        // Need list of variable names in order to initialize Dialogix1TimingCalculator model
        ArrayList<String> varNames = new ArrayList<String>();
        ArrayList<String> actionTypes = new ArrayList<String>();
        
		for (int i = 0; i < size; ++i, ++idx) {
			node = schedule.getNode(i);
            varNames.add(node.getLocalName());
            actionTypes.add(node.getQuestionOrEvalTypeField()); // needed to determine Group Num

			/* read default values from schedule file */
			init = node.getAnswerGiven();
			if (init == null || init.length() == 0) {
				if (i < startingStep && node.getAnswerType() == Node.NOTHING) {
					datum = new Datum(triceps, "", Datum.STRING); // so that
					// not
					// marked as
					// UNASKED
				} else {
					datum = Datum.getInstance(triceps, Datum.UNASKED);
				}
			} else {
				datum = Datum.parseSpecialType(triceps, init);
				if (datum == null) {
					/* then not special, so use the init value */
					datum = new Datum(triceps, init, node.getDatumType(), node
							.getMask());
				}
			}
			timeStamp = node.getAnswerTimeStampStr();
			if (timeStamp == null || timeStamp.trim().length() == 0)
				timeStamp = startTime;

			value = new Value(node, datum, timeStamp);
			values.addElement(value);

			Integer j = new Integer(idx);

			addAlias(node, node.getLocalName(), j);
			aliases.put(node, j);
		}
            
        if (DB_LOG_MINIMAL) {
            triceps.setTtc(new Dialogix1TimingCalculator(schedule.getScheduleSource(), instrumentTitle,major_version, minor_version, startingStep, triceps.dataLogger.getFilename(), varNames, actionTypes));
        }            
	}

	/* public */void reset() {
		Node node = null;
		Schedule schedule = triceps.getSchedule();
		int size = schedule.size();
		for (int i = 0; i < size; ++i) {
			node = schedule.getNode(i);
			set(node, Datum.getInstance(triceps, Datum.UNASKED));
		}
	}

	private void addAlias(Node n, String alias, Integer index) {
		if (alias == null || alias.equals(""))
			return; // ignore invalid aliases

		Object o = aliases.put(alias, index);
		if (o != null) {
			int pastIndex = ((Integer) o).intValue();
			if (pastIndex != index.intValue()) {
				/*
				 * Allow a single node to try to set the same alias for itself
				 * multiple times. However, each node must have non-overlapping
				 * aliases with other nodes
				 */
				aliases.put(alias, o); // restore overwritten alias?
				Node prevNode = ((Value) values.elementAt(pastIndex)).getNode();
				if (prevNode == null)
					return;
				n.setParseError(triceps.get("alias_previously_used_on_line")
						+ prevNode.getSourceLine() + ": " + alias);
			}
		}
	}

	/* public */boolean containsKey(Object val) {
		if (val == null)
			return false;
		return aliases.containsKey(val);
	}

	public Datum getDatum(Object val) {
		int i = getNodeIndex(val);
		if (i == -1) {
			return null;
		}
		return ((Value) values.elementAt(i)).getDatum();
	}

	public Node getNode(Object val) {
		int i = getNodeIndex(val);
		if (i == -1) {
			setError(triceps.get("node_not_found"), val);
			return null;
		}
		return ((Value) values.elementAt(i)).getNode();
	}

	/* public */int getStep(Object n) {
		if (n == null)
			return -1;
		int step = getNodeIndex(n);
		if (step == -1)
			return -1;
		else
			return (step - numReserved);
	}

	private int getNodeIndex(Object n) {
		if (n == null)
			return -1;
		Object o = aliases.get(n); // String, or Node
		if (o != null && o instanceof Integer)
			return ((Integer) o).intValue();

		if (!(n instanceof Node))
			return -1;

		Node node = (Node) n;

		o = aliases.get(node.getLocalName());
		if (o != null && o instanceof Integer)
			return ((Integer) o).intValue();

		return -1;
	}

	/* public */Value getValue(Object n) {
		int idx = getNodeIndex(n);
		if (idx == -1) {
			return null;
		}
		return (Value) values.elementAt(idx);
	}

	/* public */void set(Node node, Datum val, String time, boolean record) {
		if (node == null) {
			setError(triceps.get("null_node"), null);
			return;
		}
		if (val == null) {
			setError(triceps.get("null_datum"), null);
			return;
		}
		int i;

		i = getNodeIndex(node);
		if (i == -1) {
			setError(triceps.get("node_does_not_exist"), node.getLocalName());
			return;
		}

		Value value = (Value) values.elementAt(i);
		value.setDatum(val, time);

		if (!record)
			return;

		if (DEPLOYABLE) {
			if (value.isReserved()) {
				triceps.getSchedule().writeReserved(value.getReservedNum());
			} else {
				writeNode(node, val);
			}
		}
	}

	/* public */void set(Node node, Datum val) {
		set(node, val, null, true);
	}

	public void set(String name, Datum val) {
		if (name == null) {
			setError(triceps.get("null_node"), null);
			return;
		}
		if (val == null) {
			setError(triceps.get("null_datum"), null);
			return;
		}

		int i = getNodeIndex(name);
		Value value = null;
		if (i == -1) {
			i = size(); // append to end
			value = new Value(name, val);
			values.addElement(value);
			aliases.put(name, new Integer(i));

			String errmsg = triceps.get("new_variable_will_be_transient")
			+ name;
			setError(errmsg, null);
		} else {
			/* variables don't change their names after being created */
			value = (Value) values.elementAt(i);
			value.setDatum(val, null);
		}

		if (DEPLOYABLE) {
			if (value.isReserved()) {
				// triceps.getSchedule().writeReserved(value.getReservedNum());
				// // duplicate - not needed
			} else {
				Node node = getNode(name);
				if (node != null) {
					writeNode(node, val);
				} else {
					logger.debug("%% transient val " + name + "="
							+ val.stringVal());
					writeValue(name, val);
				}
			}
		}
	}

	private void writeValue(String name, Datum d) {
		if (DEPLOYABLE) {
			String ans = null;
			StringBuffer sb = new StringBuffer("\t");

			if (d == null) {
				ans = "";
			} else {
				ans = d.stringVal(true);
			}

			sb.append(name);
			sb.append("\t\t"); // do I know the current language number?
			sb.append(System.currentTimeMillis());
			sb.append("\t\t");
			sb.append(InputEncoder.encode(ans));
			sb.append("\t");
			triceps.dataLogger.println(sb.toString());
		}
	}

	private void writeNode(Node q, Datum d) {
		logger.debug("### in Evidence.writeNode: q is"+q.getLocalName()+" node is:"+d.stringVal());
		if (DEPLOYABLE) {
			String ans = null;
			String comment = null;
			StringBuffer sb = new StringBuffer("\t");
			
			if (d == null) {
				ans = "";
			} else {
				ans = d.stringVal(true);
			}
			comment = q.getComment();
			if (comment == null)
				comment = "";
			
			sb.append(q.getLocalName());
			sb.append("\t");
			sb.append(q.getAnswerLanguageNum());
			sb.append("\t");
			sb.append(q.getTimeStampStr());
			sb.append("\t");
			sb.append(q.getQuestionAsAsked());
			sb.append("\t");
			sb.append(InputEncoder.encode(ans));
			sb.append("\t");
			sb.append(InputEncoder.encode(comment));
			triceps.dataLogger.println(sb.toString());
			// This does all database writing for the node, to horizontal and RawData tables
            if (DB_LOG_MINIMAL) {
				triceps.getTtc().writeNode(q, d);                
            }
			if (DB_LOG_RESULTS) {
				triceps.getDtc().writeNode(q, d);
			}
		} 
	}

	/* public */void writeDatafileHeaders() {
		if (DEPLOYABLE) {
			Schedule schedule = triceps.getSchedule();

			schedule.setReserved(Schedule.TRICEPS_FILE_TYPE,Schedule.TRICEPS_DATA_FILE);
			schedule.writeReserved(Schedule.TRICEPS_FILE_TYPE);
			schedule.writeReserved(Schedule.START_TIME);
			schedule.writeReserved(Schedule.FILENAME);
			schedule.writeReserved(Schedule.SCHEDULE_SOURCE);
			schedule.writeReserved(Schedule.TRICEPS_VERSION_MAJOR);
			schedule.writeReserved(Schedule.TRICEPS_VERSION_MINOR);
			schedule.writeReserved(Schedule.SCHED_VERSION_MAJOR);
			schedule.writeReserved(Schedule.SCHED_VERSION_MINOR);
			schedule.writeReserved(Schedule.SCHED_AUTHORS);
			schedule.writeReserved(Schedule.STARTING_STEP);
			schedule.writeReserved(Schedule.TITLE);
			schedule.writeReserved(Schedule.TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS);
		}
	}

	/* public */void writeStartingValues() {
		if (DEPLOYABLE) {
			Node node = null;
			Datum datum = null;
			Schedule schedule = triceps.getSchedule();

			for (int i = 0; i < schedule.size(); ++i) {
				node = schedule.getNode(i);
				datum = triceps.getDatum(node);
				writeNode(node, datum);
			}
		}
	}

	/* public */int size() {
		return values.size();
	}

	public String toString(Object val) {
		Datum d = getDatum(val);
		if (d == null)
			return "null";
		else
			return d.stringVal();
	}

	/* public */Date getStartTime() {
		return startTime;
	}

	public Datum getParam(Object o) {
		if (o == null)
			return Datum.getInstance(triceps, Datum.INVALID);
		else if (o instanceof String)
			return getDatum(o);
		else
			return (Datum) o;
	}

  /**
    This class performs the requested function on the parameter list and returns the resulting Datum object.
    
    @param name  The name of the function
    @param params The list of parameters for the function
    @param line The line number from which the function was called (for debugging purposes)
    @param column The column number from which the function was called (for debugging purposes)
    @return  The Datum holding the value
    @see Datum
   */
	public Datum function(String name, Vector params, int line, int column) {
		/* passed a vector of Datum values */
		try {
			Integer func = (Integer) FUNCTIONS.get(name);
			int funcNum = 0;

			if (func == null || ((funcNum = func.intValue()) < 0)) {
				/* then not found - could consider calling JavaBean! */
				setError(triceps.get("unsupported_function") + name, line,
						column, null);
				return Datum.getInstance(triceps, Datum.INVALID);
			}

			Integer numParams = (Integer) FUNCTION_ARRAY[funcNum][FUNCTION_NUM_PARAMS];

			if (!(UNLIMITED.equals(numParams) || params.size() == numParams.intValue())) {
				setError(triceps.get("function") + name
						+ triceps.get("expects") + " " + numParams + " "
						+ triceps.get("parameters"), line, column, params.size());
				return Datum.getInstance(triceps, Datum.INVALID);
			}

			Datum datum = null;

			if (params.size() > 0) {
				datum = getParam(params.elementAt(0));
			}

			switch (funcNum) {
			case DESC: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, nodeName);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, triceps.getParser().parseJSP(triceps,
						node.getReadback(triceps.getLanguage())), Datum.STRING);
			}
			case ISINVALID:
				return new Datum(triceps, datum.isType(Datum.INVALID));
			case ISASKED:
				return new Datum(triceps, !(datum.isType(Datum.NA)
						|| datum.isType(Datum.UNASKED) || datum.isType(Datum.INVALID)));
			case ISNA:
				return new Datum(triceps, datum.isType(Datum.NA));
			case ISREFUSED:
				return new Datum(triceps, datum.isType(Datum.REFUSED));
			case ISUNKNOWN:
				return new Datum(triceps, datum.isType(Datum.UNKNOWN));
			case ISNOTUNDERSTOOD:
				return new Datum(triceps, datum.isType(Datum.NOT_UNDERSTOOD));
			case ISDATE:
				return new Datum(triceps, datum.isType(Datum.DATE));
			case ISANSWERED:
				return new Datum(triceps, datum.exists());
			case GETDATE:
				return new Datum(triceps, datum.dateVal(), Datum.DATE);
			case GETYEAR:
				return new Datum(triceps, datum.dateVal(), Datum.YEAR);
			case GETMONTH:
				return new Datum(triceps, datum.dateVal(), Datum.MONTH);
			case GETMONTHNUM:
				return new Datum(triceps, datum.dateVal(), Datum.MONTH_NUM);
			case GETDAY:
				return new Datum(triceps, datum.dateVal(), Datum.DAY);
			case GETWEEKDAY:
				return new Datum(triceps, datum.dateVal(), Datum.WEEKDAY);
			case GETTIME:
				return new Datum(triceps, datum.dateVal(), Datum.TIME);
			case GETHOUR:
				return new Datum(triceps, datum.dateVal(), Datum.HOUR);
			case GETMINUTE:
				return new Datum(triceps, datum.dateVal(), Datum.MINUTE);
			case GETSECOND:
				return new Datum(triceps, datum.dateVal(), Datum.SECOND);
			case NOW:
				return new Datum(triceps, new Date(System.currentTimeMillis()),
						Datum.DATE);
			case STARTTIME:
				return new Datum(triceps, startTime, Datum.TIME);
			case COUNT: // unlimited number of parameters
			{
				long count = 0;
				for (int i = 0; i < params.size(); ++i) {
					datum = getParam(params.elementAt(i));
					if (datum.booleanVal()) {
						++count;
					}
				}
				return new Datum(triceps, count);
			}
			case ANDLIST:
			case ORLIST: // unlimited number of parameters
			{
				StringBuffer sb = new StringBuffer();
				Vector v = new Vector();
				for (int i = 0; i < params.size(); ++i) {
					datum = getParam(params.elementAt(i));
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
							sb.append(triceps.get("and") + " ");
						} else if (funcNum == ORLIST) {
							sb.append(triceps.get("or") + " ");
						}
					}
					sb.append(datum.stringVal());
				}
				return new Datum(triceps, sb.toString(), Datum.STRING);
			}
			case NEWDATE:
				if (params.size() == 1) {
					/* newDate(int weekdaynum) */
					GregorianCalendar gc = new GregorianCalendar(); // should
					// happen
					// infrequently
					// (not a
					// garbage
					// collection
					// problem?)
					gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					gc.add(Calendar.DAY_OF_WEEK, ((int) (getParam(params
							.elementAt(0)).doubleVal()) - 1));
					return new Datum(triceps, gc.getTime(), Datum.WEEKDAY);
				}
				if (params.size() == 2) {
					/* newDate(String image, String mask) */
					return new Datum(triceps, getParam(params.elementAt(0))
							.stringVal(), Datum.DATE, getParam(
									params.elementAt(1)).stringVal());
				} else if (params.size() == 3) {
					/* newDate(int y, int m, int d) */
					StringBuffer sb = new StringBuffer();
					sb.append(getParam(params.elementAt(0)).stringVal() + "/");
					sb.append(getParam(params.elementAt(1)).stringVal() + "/");
					sb.append(getParam(params.elementAt(2)).stringVal());
					return new Datum(triceps, sb.toString(), Datum.DATE,
					"yy/mm/dd");
				}
				break;
			case NEWTIME:
				if (params.size() == 2) {
					/* newTime(String image, String mask) */
					return new Datum(triceps, getParam(params.elementAt(0))
							.stringVal(), Datum.TIME, getParam(
									params.elementAt(1)).stringVal());
				} else if (params.size() == 3) {
					/* newTime(int hh, int mm, int ss) */
					StringBuffer sb = new StringBuffer();
					sb.append(getParam(params.elementAt(0)).stringVal() + ":");
					sb.append(getParam(params.elementAt(1)).stringVal() + ":");
					sb.append(getParam(params.elementAt(2)).stringVal());
					return new Datum(triceps, sb.toString(), Datum.TIME,
					"hh:mm:ss");
				}
				break;
			case MIN:
				if (params.size() == 0) {
					return Datum.getInstance(triceps, Datum.INVALID);
				} else {
					Datum minVal = null;

					for (int i = 0; i < params.size(); ++i) {
						Datum a = getParam(params.elementAt(i));

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
					return Datum.getInstance(triceps, Datum.INVALID);
				} else {
					Datum maxVal = null;

					for (int i = 0; i < params.size(); ++i) {
						Datum a = getParam(params.elementAt(i));

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
				return new Datum(triceps, datum.dateVal(), Datum.DAY_NUM);
			case HASCOMMENT: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return new Datum(triceps, false);
				}
				String comment = node.getComment();
				return new Datum(triceps, (comment != null && comment.trim().length() > 0) ? true : false);
			}
			case GETCOMMENT: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, node.getComment(), Datum.STRING);
			}
			case GETTYPE:
				return new Datum(triceps, datum.getTypeName(), Datum.STRING);
			case ISSPECIAL:
				return new Datum(triceps, datum.isSpecial());
			case NUMANSOPTIONS: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				Vector choices = node.getAnswerChoices();
				return new Datum(triceps, choices.size());
			}
			case GETANSOPTION: {
				if (params.size() < 1 || params.size() > 2)
					break;

				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				Vector choices = node.getAnswerChoices();
				if (params.size() == 1) {
					if (datum.isSpecial()) {
						return new Datum(datum);
					} else {
						String s = datum.stringVal();
						for (int i = 0; i < choices.size(); ++i) {
							AnswerChoice ac = (AnswerChoice) choices
							.elementAt(i);
							ac.parse(triceps); // in case language has changed
							if (ac.getValue().equals(s)) { // what will parsing
								// answerchoice do
								// to stored datum
								// value?
								return new Datum(triceps, ac.getMessage(),
										Datum.STRING);
							}
						}
						return Datum.getInstance(triceps, Datum.INVALID);
					}
				} else { // if (params.size() == 2) {
					datum = getParam(params.elementAt(1));
					if (!datum.isNumeric()) {
						setError(functionError(funcNum, Datum.NUMBER, 2), datum);
						return Datum.getInstance(triceps, Datum.INVALID);
					}
					int index = (int) datum.doubleVal();
					if (index < 0) {
						setError(triceps.get("index_too_low"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else if (index >= choices.size()) {
						setError(triceps.get("index_too_high"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else {
						AnswerChoice ac = (AnswerChoice) choices.elementAt(index);
						ac.parse(triceps);
						return new Datum(triceps, ac.getMessage(), Datum.STRING);
					}
				}
			}
			case CHARAT: {
				String src = datum.stringVal();
				datum = getParam(params.elementAt(1));
				if (!datum.isNumeric()) {
					setError(functionError(funcNum, Datum.NUMBER, 2), datum);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				int index = (int) datum.doubleVal();
				if (index < 0) {
					setError(triceps.get("index_too_low"), index);
					return Datum.getInstance(triceps, Datum.INVALID);
				} else if (index >= src.length()) {
					setError(triceps.get("index_too_high"), index);
					return Datum.getInstance(triceps, Datum.INVALID);
				} else {
					return new Datum(triceps,
							String.valueOf(src.charAt(index)), Datum.STRING);
				}
			}
			case COMPARETO:
				return new Datum(triceps, datum.stringVal().compareTo(
						getParam(params.elementAt(1)).stringVal()));
			case COMPARETOIGNORECASE: {
				String src = datum.stringVal().toLowerCase();
				String dst = getParam(params.elementAt(1)).stringVal().toLowerCase();
				return new Datum(triceps, src.compareTo(dst));
			}
			case ENDSWITH:
				return new Datum(triceps, datum.stringVal().endsWith(
						getParam(params.elementAt(1)).stringVal()));
			case INDEXOF: {
				if (params.size() < 2 || params.size() > 3)
					break;

				String str1 = getParam(params.elementAt(0)).stringVal();
				String str2 = getParam(params.elementAt(1)).stringVal();

				if (params.size() == 2) {
					return new Datum(triceps, str1.indexOf(str2));
				} else if (params.size() == 3) {
					Datum datum2 = getParam(params.elementAt(2));
					if (!datum2.isNumeric()) {
						setError(functionError(funcNum, Datum.NUMBER, 3),
								datum2);
						return Datum.getInstance(triceps, Datum.INVALID);
					}
					int index = (int) datum2.doubleVal();
					if (index < 0) {
						setError(triceps.get("index_too_low"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else if (index >= str1.length()) {
						setError(triceps.get("index_too_high"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else {
						return new Datum(triceps, str1.indexOf(str2, index));
					}
				} else {
					break;
				}
			}
			case LASTINDEXOF: {
				if (params.size() < 2 || params.size() > 3)
					break;

				String str1 = getParam(params.elementAt(0)).stringVal();
				String str2 = getParam(params.elementAt(1)).stringVal();

				if (params.size() == 2) {
					return new Datum(triceps, str1.lastIndexOf(str2));
				} else if (params.size() == 3) {
					Datum datum2 = getParam(params.elementAt(2));
					if (!datum2.isNumeric()) {
						setError(functionError(funcNum, Datum.NUMBER, 3),
								datum2);
						return Datum.getInstance(triceps, Datum.INVALID);
					}
					int index = (int) datum2.doubleVal();
					if (index < 0) {
						setError(triceps.get("index_too_low"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else if (index >= str1.length()) {
						setError(triceps.get("index_too_high"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else {
						return new Datum(triceps, str1.lastIndexOf(str2, index));
					}
				} else {
					break;
				}
			}
			case LENGTH:
				return new Datum(triceps, datum.stringVal().length());
			case STARTSWITH: {
				if (params.size() < 2 || params.size() > 3)
					break;

				String str1 = getParam(params.elementAt(0)).stringVal();
				String str2 = getParam(params.elementAt(1)).stringVal();

				if (params.size() == 2) {
					return new Datum(triceps, str1.startsWith(str2));
				} else if (params.size() == 3) {
					Datum datum2 = getParam(params.elementAt(2));
					if (!datum2.isNumeric()) {
						setError(functionError(funcNum, Datum.NUMBER, 3),
								datum2);
						return Datum.getInstance(triceps, Datum.INVALID);
					}
					int index = (int) datum2.doubleVal();
					if (index < 0) {
						setError(triceps.get("index_too_low"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else if (index >= str1.length()) {
						setError(triceps.get("index_too_high"), index);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else {
						return new Datum(triceps, str1.startsWith(str2, index));
					}
				} else {
					break;
				}
			}
			case SUBSTRING: {
				if (params.size() < 2 || params.size() > 3)
					break;

				String str1 = getParam(params.elementAt(0)).stringVal();
				Datum start = getParam(params.elementAt(1));
				Datum end = null;
				int from, to;

				if (params.size() == 3) {
					end = getParam(params.elementAt(2));
				}

				if (!start.isNumeric()) {
					setError(functionError(funcNum, Datum.NUMBER, 2), start);
					return Datum.getInstance(triceps, Datum.INVALID);
				} else {
					from = (int) start.doubleVal();
					if (from < 0) {
						setError(triceps.get("index_too_low"), from);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else if (from >= str1.length()) {
						setError(triceps.get("index_too_high"), from);
						return Datum.getInstance(triceps, Datum.INVALID);
					}

				}

				if (end != null) {
					if (!end.isNumeric()) {
						setError(functionError(funcNum, Datum.NUMBER, 3), end);
						return Datum.getInstance(triceps, Datum.INVALID);
					} else {
						to = (int) end.doubleVal();
						if (to < from) {
							setError(triceps.get("index_too_low"), to);
							return Datum.getInstance(triceps, Datum.INVALID);
						} else if (to >= str1.length()) {
							setError(triceps.get("index_too_high"), to);
							return Datum.getInstance(triceps, Datum.INVALID);
						} else {
							return new Datum(triceps, str1.substring(from, to),
									Datum.STRING);
						}
					}
				} else {
					return new Datum(triceps, str1.substring(from),
							Datum.STRING);
				}
			}
			case TOLOWERCASE:
				return new Datum(triceps, datum.stringVal().toLowerCase(),
						Datum.STRING);
			case TOUPPERCASE:
				return new Datum(triceps, datum.stringVal().toUpperCase(),
						Datum.STRING);
			case TRIM:
				return new Datum(triceps, datum.stringVal().trim(),
						Datum.STRING);
			case ISNUMBER:
				return new Datum(triceps, datum.isNumeric());
			case FILEEXISTS: {
				/*
				 * FIXME Needs to be modified to check for not only the actual
				 * filenames in the completed dir, but also the pending
				 * filenames as indicated by the temp files in the working dir
				 */

				String fext = datum.stringVal();
				if (fext == null)
					return new Datum(triceps, false);
				fext = fext.trim();
				if (fext.length() == 0)
					return new Datum(triceps, false);
				;

				/*
				 * now check whether this name is available in both working and
				 * completed dirs
				 */
				File file;
				Schedule sched = triceps.getSchedule();
				String fname;

				/* Working dir - read schedules and get their FILENAMEs */
				ScheduleList interviews = new ScheduleList(triceps, sched
						.getReserved(Schedule.WORKING_DIR), true);
				Schedule sc = null;
				Vector schedules = interviews.getSchedules();
				for (int i = 0; i < schedules.size(); ++i) {
					sc = (Schedule) schedules.elementAt(i);
					if (sc.getReserved(Schedule.FILENAME).equals(fext)) {
						if (sc.getReserved(Schedule.LOADED_FROM).equals(
								triceps.dataLogger.getFilename())) {
							continue; // since examining the current file
						} else {
							return new Datum(triceps, true);
						}
					}
				}

				/* For Completed dir - check actual filenames */
				try {
					fname = sched.getReserved(Schedule.COMPLETED_DIR) + fext
					+ ".jar";
					logger.debug("##exists(" + fname + ")");
					file = new File(fname);
					if (file.exists())
						return new Datum(triceps, true);
				} catch (SecurityException e) {
					logger.error("",e);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, false);
			}
			case ABS:
				return new Datum(triceps, Math.abs(datum.doubleVal()));
			case ACOS:
				return new Datum(triceps, Math.acos(datum.doubleVal()));
			case ASIN:
				return new Datum(triceps, Math.asin(datum.doubleVal()));
			case ATAN:
				return new Datum(triceps, Math.atan(datum.doubleVal()));
			case ATAN2:
				return new Datum(triceps, Math.atan2(datum.doubleVal(),
						getParam(params.elementAt(1)).doubleVal()));
			case CEIL:
				return new Datum(triceps, Math.ceil(datum.doubleVal()));
			case COS:
				return new Datum(triceps, Math.cos(datum.doubleVal()));
			case EXP:
				return new Datum(triceps, Math.exp(datum.doubleVal()));
			case FLOOR:
				return new Datum(triceps, Math.floor(datum.doubleVal()));
			case LOG:
				return new Datum(triceps, Math.log(datum.doubleVal()));
			case POW:
				return new Datum(triceps, Math.pow(datum.doubleVal(), getParam(
						params.elementAt(1)).doubleVal()));
			case RANDOM:
				return new Datum(triceps, Math.random());
			case ROUND:
				return new Datum(triceps, Math.round(datum.doubleVal()));
			case SIN:
				return new Datum(triceps, Math.sin(datum.doubleVal()));
			case SQRT:
				return new Datum(triceps, Math.sqrt(datum.doubleVal()));
			case TAN:
				return new Datum(triceps, Math.tan(datum.doubleVal()));
			case TODEGREES:
				// return new Datum(triceps, Math.toDegrees(datum.doubleVal()));
				return new Datum(triceps, Double.NaN);
			case TORADIANS:
				// return new Datum(triceps, Math.toRadians(datum.doubleVal()));
				return new Datum(triceps, Double.NaN);
			case PI:
				return new Datum(triceps, Math.PI);
			case E:
				return new Datum(triceps, Math.E);
			case FORMAT_NUMBER:
				return new Datum(triceps, triceps.formatNumber(new Double(datum
						.doubleVal()), getParam(params.elementAt(1))
						.stringVal()), Datum.STRING);
			case PARSE_NUMBER:
				return new Datum(triceps, triceps.parseNumber(
						datum.stringVal(),
						getParam(params.elementAt(1)).stringVal()).doubleValue());
			case FORMAT_DATE:
				return new Datum(triceps, triceps.formatDate(datum.dateVal(),
						getParam(params.elementAt(1)).stringVal()),
						Datum.STRING);
			case PARSE_DATE:
				return new Datum(triceps, triceps.parseDate(datum.stringVal(),
						getParam(params.elementAt(1)).stringVal()), Datum.DATE,
						getParam(params.elementAt(1)).stringVal());
			case GET_CONCEPT: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, node.getConcept(), Datum.STRING);
			}
			case GET_LOCAL_NAME: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, node.getLocalName(), Datum.STRING);
			}
			case GET_EXTERNAL_NAME: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, node.getExternalName(), Datum.STRING);
			}
			case GET_DEPENDENCIES: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, node.getDependencies(), Datum.STRING);
			}
			case GET_ACTION_TEXT: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				return new Datum(triceps, node.getQuestionOrEval(),
						Datum.STRING);
			}
			case JUMP_TO: {
				String nodeName = datum.getName();
				Node node = null;
				if (nodeName == null || ((node = getNode(nodeName)) == null)) {
					setError(triceps.get("unknown_node") + nodeName, line,
							column, null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				triceps.gotoNode(node);
				return new Datum(triceps, "", Datum.STRING);
			}
			case GOTO_FIRST:
				triceps.gotoFirst();
				return new Datum(triceps, "", Datum.STRING);
			case JUMP_TO_FIRST_UNASKED:
				triceps.jumpToFirstUnasked();
				return new Datum(triceps, "", Datum.STRING);
			case GOTO_PREVIOUS:
				triceps.gotoPrevious();
				return new Datum(triceps, "", Datum.STRING);
			case ERASE_DATA:
				triceps.resetEvidence();
				return new Datum(triceps, "", Datum.STRING);
			case GOTO_NEXT:
				triceps.gotoNext();
				return new Datum(triceps, "", Datum.STRING);
			case MEAN:
				if (params.size() == 0) {
					return Datum.getInstance(triceps, Datum.INVALID);
				} else {
					int count = 0;
					double sum = 0;
					double mean = 0;

					for (int i = 0; i < params.size(); ++i) {
						Datum a = getParam(params.elementAt(i));
						++count;
						sum += a.doubleVal();
					}
					mean = sum / count;
					return new Datum(triceps, mean);
				}
			case STDDEV:
				if (params.size() == 0) {
					return Datum.getInstance(triceps, Datum.INVALID);
				} else {
					int count = 0;
					double sum = 0;
					double mean = 0;
					double sumsqdiff = 0;
					double std = 0;

					for (int i = 0; i < params.size(); ++i) {
						Datum a = getParam(params.elementAt(i));
						++count;
						sum += a.doubleVal();
					}
					mean = sum / count;

					for (int i = 0; i < params.size(); ++i) {
						Datum a = getParam(params.elementAt(i));
						double diff = (a.doubleVal() - mean);
						sumsqdiff += (diff * diff);
					}
					std = Math.sqrt(sumsqdiff / (count - 1));
					return new Datum(triceps, std);
				}
			case SUSPEND_TO_FLOPPY: {
				/*
				 * revise this so can jump to next available question, if
				 * appropriate
				 */
				if (params.size() == 1) {
					/*
					 * then set the starting step in the file to be saved (but
					 * not in the master file)
					 */
					String nodeName = datum.getName();
					Node n = getNode(nodeName);
					if (n == null) {
						setError(triceps.get("unknown_node") + nodeName, null);
					} else {
						int result = getStep(n);
						StringBuffer sb = new StringBuffer("RESERVED\t");
						sb.append(Schedule.RESERVED_WORDS[Schedule.STARTING_STEP]).append("\t");
						sb.append(result).append("\t").append(
								System.currentTimeMillis()).append("\t\t\t");
						triceps.dataLogger.println(sb.toString());
					}
				}
				String savedFile = triceps.suspendToFloppy();
				return new Datum(triceps, (savedFile == null) ? "null"
						: savedFile, Datum.STRING);
			}
			case REGEX_MATCH: {
				/** syntax: regexMatch(text,pattern) */
				InputValidator iv = InputValidator.getInstance(getParam(
						params.elementAt(1)).stringVal());
				if (!iv.isValid()) {
					setError(iv.getErrors(), null);
					return Datum.getInstance(triceps, Datum.INVALID);
				}
				if (iv.isMatch(getParam(params.elementAt(0)).stringVal())) {
					return new Datum(triceps, true);
				} else {
					return new Datum(triceps, false);
				}
			}
			case CREATE_TEMP_FILE: {
				String temp = EvidenceIO.createTempFile();
				if (temp == null) {
					return Datum.getInstance(triceps, Datum.INVALID);
				} else {
					return new Datum(triceps, temp, Datum.STRING);
				}
			}
			case SAVE_DATA: {
				String file = getParam(params.elementAt(0)).stringVal();
				boolean ok = EvidenceIO.saveAll(triceps.getSchedule(), file);
				return new Datum(triceps, ok);
			}
			case EXEC: {
				return new Datum(triceps, EvidenceIO.exec(datum.stringVal()));
			}
			case SET_STATUS_COMPLETED: {
				/*
				 * allow specification of a file as completed, even if it is
				 * mid-stream
				 */
				/* HUGE hack - requires refernce to LoginServlet! */
				return new Datum(triceps, triceps.setStatusCompleted());
			}
			case PARSE_EXPR: {
				return new Datum(triceps, triceps.getParser().parseJSP(triceps, datum.stringVal()),  Datum.STRING);
			}			
			case LOAD_INSTRUMENT: {
				logger.debug("Trying to load from " + datum.stringVal());
				if (triceps != null) {
					triceps.closeDataLogger();
				}
		
				if (name == null || name.trim().length() == 0) {
					triceps = Triceps.NULL;
				}
				else {
					triceps = new Triceps(datum.stringVal(),"/temp/","/temp/","/temp/");
				}
				if (triceps.hasErrors()) {
					logger.error(triceps.getErrors());
				}
		
				if (!triceps.getSchedule().isLoaded()) {
					triceps = Triceps.NULL;
					logger.error("Failed to load instrument");
				}
				return new Datum(triceps, triceps.isValid());				
			}
			case SHOW_TABLE_OF_ANSWERS: {
				/*
				 * 4/4/2006 Ideally, would like to let users set row format and
				 * iterate over that for each requested variable, so for now,
				 * just do minimal needed
				 * 
				 * How about use keywords with '|' separating values:
				 * 
				 * Name Question Answer Value
				 * 
				 * Syntax: showTableOfAnswers("column list", "title list",
				 * variables);
				 * 
				 */
				if (params.size() < 3) {
					setError(triceps.get("function") + name
							+ triceps.get("expects") + " >=3 "
							+ triceps.get("parameters"), line, column, params.size());
					return Datum.getInstance(triceps, Datum.INVALID);
				}

				StringBuffer sb = new StringBuffer(
				"<table width='100%' border='1'>");
				Vector v = new Vector();
				for (int i = 0; i < params.size(); ++i) {
					datum = getParam(params.elementAt(i));
					if (datum.exists()) {
						v.addElement(datum);
					}
				}

				datum = (Datum) v.elementAt(0);
				String optionlist = datum.stringVal().trim();
				StringTokenizer ans = new StringTokenizer(optionlist, "|",
						false); // don't return the '|' tokens too
				Vector options = new Vector();
				while (ans.hasMoreTokens()) {
					String s = null;
					try {
						s = ans.nextToken();
						if (s == null || s.trim().length() == 0)
							continue;
						s = s.trim();

						if ("Name".equals(s) || "Question".equals(s)
								|| "Answer".equals(s) || "Value".equals(s)) {
							options.addElement(s); // so have list of options
						}
					} catch (NoSuchElementException e) {
						logger.error("",e);
					}
				}

				/*
				 * 4/10/06 - syntax now lets user specify column titles -
				 * however, does not ensure that they are proper matches for
				 * those listed in optionlist
				 */
				datum = (Datum) v.elementAt(1);
				String headerlist = datum.stringVal().trim();
				ans = new StringTokenizer(headerlist, "|", false);
				Vector headers = new Vector();

				while (ans.hasMoreTokens()) {
					String s = null;
					try {
						s = ans.nextToken();
						if (s == null || s.trim().length() == 0)
							continue;
						s = s.trim();
						headers.addElement(s);
					} catch (NoSuchElementException e) {
						logger.error("",e);
					}
				}
				if (options.size() != headers.size()) {
					setError(
							triceps.get("function")
							+ "showTableOfAnswers(column_variables,column_headers,rows,...) must have same number of columns for variable names and header messages",
							line, column, params.size());
				}

				/* generate list of headers */
				sb.append("<tr>");
				for (int i = 0; i < headers.size(); ++i) {
					String header = (String) headers.elementAt(i);
					sb.append("<th>");
					sb.append(header);
					sb.append("</th>");
				}
				sb.append("</tr>");

				/* Now show output for each selected option */

				for (int i = 2; i < v.size(); ++i) {
					datum = (Datum) v.elementAt(i);
					/* Get the node */
					String nodeName = datum.getName();
					Node node = null;
					if (nodeName == null
							|| ((node = getNode(nodeName)) == null)) {
						setError(triceps.get("unknown_node") + nodeName, line,
								column, null);
						return Datum.getInstance(triceps, Datum.INVALID);
					}
					/* Get result for the node */
					datum = getDatum(node);
					if (datum == null || datum.isType(Datum.UNASKED)
							|| datum.isType(Datum.NA)) {
						continue; /* skip this row */
					}

					/* Show values in appropriate columns */
					sb.append("<tr>");
					for (int k = 0; k < options.size(); ++k) {
						String option = (String) options.elementAt(k);

						sb.append("<td>");

						if ("Name".equals(option)) {
							sb.append(node.getExternalName());
						} else if ("Question".equals(option)) {
							String question = node.getQuestionAsAsked();
							if ("".equals(question)) {
								sb.append("&nbsp;");
							} else {
								sb.append(question);
							}
						} else if ("Answer".equals(option)) {
							int num_choices = node.numAnswerChoices();
							String answer = "&nbsp;";
							if (num_choices > 0) {
								/*
								 * Then select text value from the list of
								 * choices
								 */
								Vector choices = node.getAnswerChoices();
								if (datum.isSpecial()) {
									answer = datum.toString();
								} else {
									String s = datum.stringVal();
									for (int j = 0; j < choices.size(); ++j) {
										AnswerChoice ac = (AnswerChoice) choices
										.elementAt(j);
										ac.parse(triceps); // in case language
										// has changed
										if (ac.getValue().equals(s)) { // what
											// will
											// parsing
											// answerchoice
											// do to
											// stored
											// datum
											// value?
											answer = ac.getMessage();
										}
									}
								}
							} else {
								answer = triceps.toString(node, true);
							}
							sb.append(answer);
						} else if ("Value".equals(option)) {
							sb.append(triceps.toString(node, true));
						}

						sb.append("</td>");
					}
					sb.append("</tr>");
				}
				sb.append("</table>");

				return new Datum(triceps, sb.toString(), Datum.STRING);
			}
			}
		} catch (Exception t) {
			logger.error("",t);
		}
		setError("unexpected error running function " + name, line, column,
				null);
		return Datum.getInstance(triceps, Datum.INVALID);
	}

  /**
    Records a parsing error.
    
    @param s  The error message
    @param line The line at which it occured
    @param column The column at which it occured
    @param val an integer value (e.g. to show IndexOutOfBounds exceptions
  */
	private void setError(String s, int line, int column, int val) {
		setError(s, line, column, new Integer(val));
	}

  /**
    Records a parsing error.
    
    @param s  The error message
    @param val an integer value (e.g. to show IndexOutOfBounds exceptions
  */
	private void setError(String s, int val) {
		setError(s, new Integer(val));
	}

  /**
    Records a parsing error.
    
    @param s  The error message
    @param line The line at which it occured
    @param column The column at which it occured
    @param val An Object (integer or Datum) showing the bad value.
  */
	private void setError(String s, int line, int column, Object val) {
		String msg = null;
		if (val != null) {
			msg = s
			+ ": "
			+ triceps.get("got")
			+ ((val instanceof Datum) ? ((Datum) val).stringVal() : val.toString());
		} else {
			msg = s;
		}
		errorLogger.print(msg, line, column);
		logger.error("##" + msg);
	}

  /**
    Records a parsing error.
    
    @param s  The error message
    @param val An Object (integer or Datum) showing the bad value.
  */
	private void setError(String s, Object val) {
		String msg = null;
		if (val != null) {
			msg = s
			+ ": "
			+ triceps.get("got")
			+ ((val instanceof Datum) ? ((Datum) val).stringVal() : val.toString());
		} else {
			msg = s;
		}
		errorLogger.println(msg);
		logger.error("##" + msg);
	}

	/* public */boolean hasErrors() {
		return (errorLogger.size() > 0);
	}

	/* public */String getErrors() {
		return errorLogger.toString();
	}

  /**
    Generate a debug message that tells how a function should be used (e.g. "Expects a Number at position 3").
    
    @param funcNum  The function number
    @param datumType  The expected data type of the Datum at 
    @param index  The index at which the error occured.
    @return A descriptive message.
  */
	private String functionError(int funcNum, int datumType, int index) {
		return FUNCTION_ARRAY[funcNum][FUNCTION_NAME] + " "
		+ triceps.get("expects") + " "
		+ Datum.getTypeName(triceps, datumType) + " "
		+ triceps.get("at_index") + " " + index;
	}
}
