/* ******************************************************** 
** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dianexus.triceps;

/*import java.lang.*;*/
/*import java.util.*;*/
/*import java.io.*;*/
import java.util.Vector;
import java.util.Hashtable;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.util.Enumeration;
import java.text.DecimalFormat;
import org.apache.log4j.Logger;

/**
  This class specifies all of the properties of an individual Item.<br>
  An Item (Node) can include a Question (or Instructions), or be an Equation needing parsing.<br>
  If it is a Question, it has a response type and possibly a set of allowable response values.
*/
/*public*/ class Node implements VersionIF  {
  static Logger logger = Logger.getLogger(Node.class);
	/*public*/ static final int BADTYPE = 0;
	/*public*/ static final int NOTHING=1;	// do nothing
	/*public*/ static final int RADIO = 2;
	/*public*/ static final int CHECK = 3;
	/*public*/ static final int COMBO = 4;
	/*public*/ static final int LIST = 5;	// combo of size=(min (6,#lines))
	/*public*/ static final int TEXT = 6;
	/*public*/ static final int DOUBLE = 7;
	/*public*/ static final int RADIO_HORIZONTAL = 8;	// different layout
	/*public*/ static final int PASSWORD = 9;
	/*public*/ static final int MEMO = 10;
	/*public*/ static final int DATE = 11;
	/*public*/ static final int TIME = 12;
	/*public*/ static final int YEAR = 13;
	/*public*/ static final int MONTH = 14;
	/*public*/ static final int DAY = 15;
	/*public*/ static final int WEEKDAY = 16;
	/*public*/ static final int HOUR = 17;
	/*public*/ static final int MINUTE = 18;
	/*public*/ static final int SECOND = 19;
	/*public*/ static final int MONTH_NUM = 20;
	/*public*/ static final int DAY_NUM = 21;
	/*public*/ static final int RADIO_HORIZONTAL2 = 22;	// horizontal, but fits into answer field space, rather than on next line
	/*public*/ static final int COMBO2 = 23;	// no accelerator key (e.g. for long lists)
	/*public*/ static final int LIST2 = 24;		// no accelerator key

	private static final String QUESTION_TYPES[] = {
		"*badtype*", "nothing", "radio", "check", "combo", "list",
		"text", "double", "radio2", "password","memo",
		"date", "time", "year", "month", "day", "weekday", "hour", "minute", "second", "month_num", "day_num", "radio3", "combo2", "list2"};
	private static final int DATA_TYPES[] = {
		Datum.STRING, Datum.STRING, Datum.STRING, Datum.STRING, Datum.STRING, Datum.STRING,
		Datum.STRING, Datum.NUMBER, Datum.STRING, Datum.STRING, Datum.STRING,
		Datum.DATE, Datum.TIME, Datum.YEAR, Datum.MONTH, Datum.DAY, Datum.WEEKDAY, Datum.HOUR, Datum.MINUTE, Datum.SECOND, Datum.MONTH_NUM, Datum.DAY_NUM, Datum.STRING, 
		Datum.STRING, Datum.STRING };

	/*public*/ static final int QUESTION = 1;
	/*public*/ static final int EVAL = 2;
	/*public*/ static final int GROUP_OPEN = 3;
	/*public*/ static final int GROUP_CLOSE = 4;
	/*public*/ static final int BRACE_OPEN = 5;
	/*public*/ static final int BRACE_CLOSE = 6;
	/*public*/ static final int CALL_SCHEDULE = 7;
	/*public*/ static final String ACTION_TYPE_NAMES[] = {"*unknown*","question", "expression", "group_open", "group_close", "brace_open", "brace_close", "call_schedule"};
	/*public*/ static final String ACTION_TYPES[] = {"?","q","e","[","]", "{", "}", "call" };

	private static final int MAX_ITEMS_IN_LIST = 20;

	private static final String INTRA_OPTION_LINE_BREAK = "<br>";

	private static final Vector EMPTY_VECTOR = new Vector();

	/* These are the columns in the flat file database */
	private String conceptName = "";
	private String localName = "";
	private String externalName = ""; // name within DISC
	private String dependencies = "";

	private Vector readback = new Vector();
	private Vector questionOrEval = new Vector();
	private Vector answerChoicesStr = new Vector();
	private Vector answerChoicesVector = new Vector();	// of Vectors
	private Vector helpURL = new Vector();

	private int numLanguages = 0;
	private int answerLanguageNum = 0;
	private String questionAsAsked = "";
	private String answerGiven = "";
	private String answerTimeStampStr = "";
	private String comment = "";

	private Hashtable answerChoicesHash = new Hashtable();

	/* These are local variables */

	private int sourceLine = 0;
	private String sourceFile = "";
	private int questionOrEvalType = BADTYPE;
	private String questionOrEvalTypeField = "";	// questionOrEvalType;datumType;min;max;mask
	private int answerType = BADTYPE;
	private int datumType = Datum.INVALID;
	private org.dianexus.triceps.Logger runtimeErrors = new org.dianexus.triceps.Logger();
	private org.dianexus.triceps.Logger parseErrors = new org.dianexus.triceps.Logger();
	private org.dianexus.triceps.Logger namingErrors = new org.dianexus.triceps.Logger();

	private String questionOrEvalTypeStr = "";
	private String datumTypeStr = "";
	private String minStr = null;
	private String maxStr = null;
	private Vector allowableValues = null;	// additional validation - list of String values that are considered valid
	private Vector allowableDatumValues = null;	// parsed list of allowable values

	private Datum minDatum = null;
	private Datum maxDatum = null;

	private String mask = null;
	private InputValidator inputValidator = InputValidator.NULL;

	private Date timeStamp = null;
	private String timeStampStr = null;
	private Triceps triceps = Triceps.NULL;

	private Node() {
	}

	/**
		Create a new Item - reading the contents from the tab separated value set of columns.
		Loads it into the Node object
		
		@param lang	the Triceps context
		@param sourceLine	the line number within the source file (for debugging purposes)
		@param sourceFile	the name of the source file (for debugging purposes)
		@param tsv	the tab separated list of colums representing the node contents
		@param numLanguage	to know how many langauges to parsse?
	*/
	/*public*/ Node(Triceps lang, int sourceLine, String sourceFile, String tsv, int numLanguages) {
    	triceps = (lang == null) ? Triceps.NULL : lang;
		String token;
		int field = 0;

		if (numLanguages < 1) {
if (AUTHORABLE)	setParseError(triceps.get("numLanguages_must_be_greater_than_zero") + numLanguages);
else setParseError("syntax error");
			numLanguages = 1;	// the default
		}

		this.sourceLine = sourceLine;
		this.sourceFile = sourceFile;
		this.numLanguages = numLanguages;	// needs to be validated?

		int numLanguagesFound = 0;
		int numAnswersFound = 0;

		StringTokenizer ans = new StringTokenizer(tsv,"\t",true);

		while(ans.hasMoreTokens()) {
			String s = null;
			s = ans.nextToken();

			if (s.equals("\t")) {
				++field;
				if (field == 7) {
					++numLanguagesFound;	// since once that field has been entered, has successfully coded a language as present
				}
				if (field == 9 && numLanguagesFound < numLanguages) {
					field = 5;	// so that next element is readback for the next language
				}
				continue;
			}

			switch(field) {
				/* there should be one copy of each of these */
				case 0: conceptName = ExcelDecoder.decode(s); break;
				case 1: localName = ExcelDecoder.decode(s); break;
				case 2: externalName = ExcelDecoder.decode(s); break;
				case 3: dependencies= ExcelDecoder.decode(s); break;
				case 4: questionOrEvalTypeField = ExcelDecoder.decode(s); break;
				/* there are as many copies of each of these are there are languages */
				case 5: readback.addElement(ExcelDecoder.decode(s)); break;
				case 6: questionOrEval.addElement(ExcelDecoder.decode(s)); break;
				case 7: answerChoicesStr.addElement(ExcelDecoder.decode(s)); break;
				case 8: helpURL.addElement(ExcelDecoder.decode(s)); break;
				/* there are as many copies of each of these are there are answers - rudimentary support for arrays? */
				case 9: {
					int i = 0;
					try {
						i = Integer.parseInt(ExcelDecoder.decode(s));
					}
					catch (NumberFormatException t) {
						logger.error("",t);
if (AUTHORABLE) 		setParseError(triceps.get("languageNum_must_be_an_integer") + t.getMessage());
else setParseError("syntax error");
						i = 0; // default language
					}
					if (i < 0 || i >= numLanguages) {
if (AUTHORABLE)			setParseError(triceps.get("languageNum_must_be_in_range_zero_to") + (numLanguages - 1) + "): " + i);
else setParseError("syntax error");
						i = 0;	// default language
					}
					answerLanguageNum = i;
				}
					break;
				case 10: questionAsAsked = ExcelDecoder.decode(s); break;
				case 11: answerGiven = ExcelDecoder.decode(s); break;
				case 12: comment = ExcelDecoder.decode(s); break;
				case 13: answerTimeStampStr = ExcelDecoder.decode(s); break;
				default: break;	// ignore extras
			}
		}
   		if (dependencies == null || dependencies.trim().length() == 0) {
if (AUTHORABLE)			setParseError(triceps.get("dependencies_column_is_missing"));
else setParseError("syntax error");
		}
		if (localName != null && localName.trim().length() > 0) {
			localName = localName.trim();
			if (Character.isDigit(localName.charAt(0))) {
if (AUTHORABLE)				setNamingError(triceps.get("localName_may_not_begin_with_a_digit") + localName);
else setParseError("syntax error");
				localName = "_" + localName;
			}
			if (!XmlString.NULL.isNMTOKEN(localName)) {
if (AUTHORABLE)				setNamingError(triceps.get("localName_should_only_contain_letters_digits_and_underscores") + localName);
else setParseError("syntax error");
			}
		}
		else {
			setNamingError(triceps.get("localName_must_be_specified"));
	}

		parseQuestionOrEvalTypeField();

   		if (questionOrEvalType == BADTYPE) {
if (AUTHORABLE)			setParseError(triceps.get("invalid_questionOrEvalType") + questionOrEvalTypeField);
else setParseError("syntax error");
		}

		for (int i=0;i<answerChoicesStr.size();++i) {
			parseAnswerOptions(i,(String) answerChoicesStr.elementAt(i));
		}

  		if (datumType == Datum.INVALID) {
if (AUTHORABLE)			setParseError(triceps.get("invalid_dataType"));
		else setParseError("syntax error");
		}
	}
	
	/**
		Helper function to parse the Question or Eval field, which also includes optional validation criteria
	*/
	private void parseQuestionOrEvalTypeField() {
		StringTokenizer ans;
		int z;

   		if (questionOrEvalTypeField == null) {
if (AUTHORABLE)			setParseError(triceps.get("questionOrEvalTypeField_must_exist"));
else setParseError("syntax error");
			return;
		}

		ans = new StringTokenizer(questionOrEvalTypeField,";",true);	// return ';' tokens too

		for(int field=0;ans.hasMoreTokens();) {
			String s = null;
			s = ans.nextToken();

			if (";".equals(s)) {
				++field;
				continue;
			}
			switch(field) {
				case 0:
					questionOrEvalTypeStr = s;
					for (z=0;z<ACTION_TYPES.length;++z) {
						if (questionOrEvalTypeStr.equalsIgnoreCase(ACTION_TYPES[z])) {
							questionOrEvalType = z;
							break;
						}
					}
					break;
				case 1:
					datumTypeStr = s;
					datumType = Datum.parseDatumType(s);
   					if (datumType == -1) {
if (AUTHORABLE)						setParseError(triceps.get("invalid_dataType") + datumTypeStr);
else setParseError("syntax error");
						datumType = Datum.INVALID;
					}
					break;
				case 2:
					minStr = s;
					break;
				case 3:
					maxStr = s;
					break;
				case 4:
				/* FIXME:  HACK -- does double duty -- either a formatting mask, OR a regex input mask */
					inputValidator = InputValidator.getInstance(s);
					if (inputValidator.isNull()) {
						mask = s;	// since null, or doesn't start with "PERL5"
					}
					if (inputValidator.hasErrors()) {
						setParseError(inputValidator.getErrors());
					}
					break;
				default:
					/* extra parameters are additional allowable values, as Strings that will be parsed */
					if (allowableValues == null) {
						allowableValues = new Vector();
					}
					allowableValues.addElement(s);
					break;
			}
		}
	}

	/**
		Helper function to build OR list of data values
		
		@param v	vector of Nodes
	*/
	private String buildOrList(Vector v) {
		StringBuffer sb = new StringBuffer();

		for (int i=0;i<v.size();++i) {
			Datum d = (Datum) v.elementAt(i);
			sb.append("," + ((i == v.size()-1) ? (" " + triceps.get("or")) : "") + " " + d.stringVal());
		}
		return sb.toString();
	}

	/**
		@return Locale-specific sample input string which meets datatype criteria for node. Used for error reporting.
	*/
	/*public*/ String getSampleInputString() {
		/* Create the help-string showing allowable range of input values.
			Can be re-created (e.g. if range dynamically changes */

		String min = null;
		String max = null;
		String other = null;
		String rangeStr = null;
		String s = null;

		s = Datum.getExampleFormatStr(triceps,mask,datumType);

		if (s == null || s.equals(""))
			rangeStr = "";
		else
			rangeStr = " (e.g. " + s + ")";

		if ((minStr == null && maxStr == null && allowableValues == null && !inputValidator.isValid()) || answerType == PASSWORD) {
			return rangeStr;
		}

		if (minDatum != null) {
			setMinDatum(minDatum);
			min = minDatum.stringVal(true,mask);
		}
		if (maxDatum != null) {
			setMaxDatum(maxDatum);
			max = maxDatum.stringVal(true,mask);
		}
		if (allowableDatumValues != null) {
			other = buildOrList(allowableDatumValues);
		}

		if (minDatum != null && maxDatum != null) {
			if (DatumMath.lt(maxDatum,minDatum).booleanVal()) {
				setError(triceps.get("max_less_than_min") + "(" + minStr + " - " + maxStr + ")");
			}
		}

		rangeStr = "(" +
			((min != null) ? min : "") +
			" - " +
			((max != null) ? max : "") +
			")";
		if (other != null) {
			rangeStr = " [" + rangeStr + other + "]";
		}
		if (inputValidator.isValid()) {
			rangeStr = "(e.g. m/" + inputValidator.getPattern() + "/)";
		}

		return " " + rangeStr;
	}
	
	/**
		Helper function to parse Answer Options and populate Node object
		
		@param langNum	which language vector to populate
		@param src	The string to be parsed
		@return	true if suceeds
	*/
	private boolean parseAnswerOptions(int langNum, String src) {
		/* Need to make sure that the answer type, order of answers, and internal values of answers are the same across all languages */
   		if (src == null) {
if (AUTHORABLE)			setParseError(triceps.get("answerOptions_column_missing"));
else setParseError("syntax error");
			return false;
		}

		StringTokenizer ans = new StringTokenizer(src,"|",true);	// return '|' tokens too
		String token = "";

		// Determine the question type (first token)
		try {
			token = ans.nextToken();
		}
		catch (NoSuchElementException t) {
			logger.error("missing_display_type",t);
if (AUTHORABLE)	setParseError(triceps.get("missing_display_type") + t.getMessage());
else setParseError("syntax error");
		}

		if (langNum == 0) {
			for (int z=0;z<QUESTION_TYPES.length;++z) {
				if (token.equalsIgnoreCase(QUESTION_TYPES[z])) {
					answerType = z;
					break;
				}
			}
		}
		else {
   			if (!QUESTION_TYPES[answerType].equalsIgnoreCase(token)) {
if (AUTHORABLE)			setParseError(triceps.get("mismatch_across_languages_in_answerType"));
else setParseError("syntax error");
			}
			// don't change the known value for answerType
		}

		if (questionOrEvalType == EVAL) {
			answerType = NOTHING;	// so no further processing
		}
		else if (answerType == BADTYPE) {
if (AUTHORABLE)	setParseError(triceps.get("invalid_answerType"));
else setParseError("syntax error");
			answerType = NOTHING;
		}

		if (datumType == Datum.INVALID) {
			/* so only if not set via datumTypeStr */
			datumType = DATA_TYPES[answerType];
		}

		switch (answerType) {
			case CHECK:
			case COMBO:
			case LIST:
			case RADIO:
			case RADIO_HORIZONTAL:
			case RADIO_HORIZONTAL2:
			case COMBO2:
			case LIST2:
				String val=null;
				String msg=null;
				int field=0;
				Vector ansOptions = new Vector();
				Vector prevAnsOptions = null;

				if (langNum > 0) {
					prevAnsOptions = getValuesAt(answerChoicesVector,0);
				}

				int ansPos = 0;

				while(ans.hasMoreTokens()) {
					String s = null;
					s = ans.nextToken();

					if ("|".equals(s)) {
						++field;
						continue;
					}
					switch(field) {
						case 0:
							break;	// discard the first token - answerType
						case 1:
							val = s;
							if (langNum > 0) {
								boolean err = false;
								String s2 = null;	// previous answer
								try {
									s2 = ((AnswerChoice) prevAnsOptions.elementAt(ansPos++)).getValue();
									if (!s2.equals(val)) {
										err = true;
									}
								}
								catch (NullPointerException t) { logger.error("",t); err = true; }
								catch (ArrayIndexOutOfBoundsException t) { logger.error("",t); err = true; }
								if (err) {
if (AUTHORABLE)						setParseError(triceps.get("mismatch_across_languages_in_return_value_for_answerChoice_num") + (ansPos-1));
else setParseError("syntax error");
									val = s2;	// reset it to the previously known return value for consistency (?)
								}
							}
							break;
						case 2: msg = s;
							field = 0;	// so that cycle between val & msg;
							if (val == null || msg == null) {
if (AUTHORABLE)					setParseError(triceps.get("missing_value_or_message_for_answerChoice_num") + (ansPos-1));
else setParseError("syntax error");
							}
							else {
								AnswerChoice ac = new AnswerChoice(val,msg);
								ansOptions.addElement(ac);

				 				/* check for duplicate answer choice values */
								if (langNum == 0) {	// only for first pass
									if (answerChoicesHash.put(val, ac) != null) {
if (AUTHORABLE)            							setParseError(triceps.get("answerChoice_value_already_used") + val);
else setParseError("syntax error");
									}
								}
							}
							val = null;
							msg = null;
							break;
					}
				}
   				if (ansOptions.size() == 0) {
if (AUTHORABLE)					setParseError(triceps.get("answerChoices_must_be_specified"));
else setParseError("syntax error");
				}
				if (field == 1) {
if (AUTHORABLE)					setParseError(triceps.get("missing_message_for_answerChoice_num") + (ansPos-1));
else setParseError("syntax error");
				}
				if (langNum > 0) {
					if (prevAnsOptions.size() != ansOptions.size()) {
if (AUTHORABLE)						setParseError(triceps.get("mismatch_across_languages_in_number_of_answerChoices") + prevAnsOptions.size() + " != " + ansOptions.size());
else setParseError("syntax error");
					}
				}
				answerChoicesVector.addElement(ansOptions);
				break;
			default:
				break;
		}

		return true;
	}

	/**
		Create HTML input field for this node, given its currently selected value and possible error messages
		@param datum	the value
		@param errMsg	optional error messages
		@return	HTML fragment
	*/
	/*public*/ String prepareChoicesAsHTML(Datum datum, boolean autogen) {
		return prepareChoicesAsHTML(datum,"",autogen);
	}

	/**
		Helper function
		
		@return true if a specified answer choice within a enumerated list is selected.
	*/
	/*public*/ boolean isSelected(Datum datum, AnswerChoice ac) {
		return DatumMath.eq(datum,new Datum(triceps, ac.getValue(),DATA_TYPES[answerType])).booleanVal();
	}

	/**
		Create HTML input field for this node, given its currently selected value and possible error messages
		@param datum	the value
		@param errMsg	optional error messages
		@param autogen	XXX whether to auto-number the options
		@return	HTML fragment
	*/
	/*public*/ String prepareChoicesAsHTML(Datum datum, String errMsg, boolean autogen) {
		/* errMsg is a hack - only applies to RADIO_HORIZONTAL */
		StringBuffer sb = new StringBuffer();
		String defaultValue = "";
		AnswerChoice ac;
		Enumeration ans = null;
		Vector v = null;

		switch (answerType) {
		case RADIO:	// will store integers
			ans = getAnswerChoices().elements();
			while (ans.hasMoreElements()) { // for however many radio buttons there are
				ac = (AnswerChoice) ans.nextElement();
				ac.parse(triceps);
				sb.append("<input type='radio' name='" + getLocalName() + "' " + "value='" + ac.getValue() + "'" +
					(isSelected(datum,ac) ? " checked " : " ") + TricepsEngine.listEventHandlers("radio") + ">" + ac.getMessage() + "<br>");
			}
			break;
		case RADIO_HORIZONTAL: { // will store integers
			/* table underneath questions */
			v = getAnswerChoices();
			ans = v.elements();
			int count = v.size();

			if (count > 0) {
				Double pct = new Double(100. / (double) count);
				sb.append("<table cellpadding='0' cellspacing='1' border='1' width='100%'>");
				sb.append("<tr>");
				while (ans.hasMoreElements()) { // for however many radio buttons there are
					ac = (AnswerChoice) ans.nextElement();
					ac.parse(triceps);
					sb.append("<td valign='top' width='" + pct.toString() + "%'>");
					sb.append("<input type='radio' name='" + getLocalName() + "' " + "value='" + ac.getValue() + "'" +
						(isSelected(datum,ac)? " checked " : " ") + TricepsEngine.listEventHandlers("radio") + ">" + ac.getMessage());
					sb.append("</td>");
				}
				sb.append("</tr>");
				sb.append("</table>");
			}
		}
			break;	
		case RADIO_HORIZONTAL2: {
			/* table underneath questions */
			v = getAnswerChoices();
			ans = v.elements();
			int count = v.size();
			int max_width = Integer.parseInt(triceps.getSchedule().getReserved(Schedule.ANSWER_OPTION_FIELD_WIDTH));

			if (count > 0) {
				Double pct = new Double((double) max_width / (double) count);
				sb.append("<table cellpadding='0' cellspacing='1' border='1' width='100%'>");	// oddly, 100% means all of the enclosing <td>, but for embedded <td>s, need actual percent of top-level table!
				sb.append("<tr>");
				while (ans.hasMoreElements()) { // for however many radio buttons there are
					ac = (AnswerChoice) ans.nextElement();
					ac.parse(triceps);
					sb.append("<td valign='top' width='" + pct.toString() + "%'>");
					sb.append("<input type='radio' name='" + getLocalName() + "' " + "value='" + ac.getValue() + "'" +
						(isSelected(datum,ac)? " checked " : " ") + TricepsEngine.listEventHandlers("radio") + ">" + ac.getMessage());
					sb.append("</td>");
				}
				sb.append("</tr>");
				sb.append("</table>");
			}
		}
			break;						
		case CHECK:
			ans = getAnswerChoices().elements();
			while (ans.hasMoreElements()) { // for however many radio buttons there are
				ac = (AnswerChoice) ans.nextElement();
				ac.parse(triceps);
				sb.append("<input type='checkbox' name='" + getLocalName() + "' " + "value='" + ac.getValue() + "'" +
					(isSelected(datum,ac) ? " checked " : " ") + TricepsEngine.listEventHandlers("select") + ">" + ac.getMessage() + "<br>");
			}
			break;
		case COMBO:	// stores integers as value
		case COMBO2:
		case LIST2:
		case LIST: {
			StringBuffer choices = new StringBuffer();
			ans = getAnswerChoices().elements();

			int optionNum=0;
			int totalLines = 0;
			boolean nothingSelected = true;
			while (ans.hasMoreElements()) { // for however many radio buttons there are
				ac = (AnswerChoice) ans.nextElement();
				ac.parse(triceps);
				++optionNum;

				String messageStr = ac.getMessage();
				String prefix = "<option value='" + ac.getValue() + "'";
				boolean selected = isSelected(datum,ac);
				if (selected) {
					nothingSelected = false;
				}
				
				int max_text_len = Integer.parseInt(triceps.getSchedule().getReserved(Schedule.MAX_TEXT_LEN_FOR_COMBO));
				
				v = AnswerChoice.subdivideMessage(messageStr,max_text_len);
				
				for (int i=0;i<v.size();++i) {
					choices.append(prefix);
					if (i == 0 && selected) {
						choices.append(" selected");
					}
					choices.append(">");
					if (i == 0) {	// show selection number
						if (answerType == COMBO || answerType == LIST) {
							choices.append((autogen) ? String.valueOf(optionNum) : ac.getValue());
							choices.append(")&nbsp;");
						}
					}
					else {	// indent to indicate that same as previous
						choices.append("&nbsp;&nbsp;&nbsp;");
					}
					choices.append((String) v.elementAt(i));
					choices.append("</option>");
				}
				totalLines += v.size();
			}
			sb.append("<select name='" + getLocalName() + "'" +
				((answerType == LIST || answerType == LIST2) ? (" size = '" + Math.min(MAX_ITEMS_IN_LIST,totalLines+1) + "' ") : " ") +
				TricepsEngine.listEventHandlers("select") +
				">");
			sb.append("<option value=''" +
				((nothingSelected) ? " selected" : "") + ">" +	// so that focus is properly shifted on List box
				triceps.get("select_one_of_the_following") +
				"</option>");	// first choice is empty
			sb.append(choices);
			sb.append("</select>");
		}
			break;
		case TEXT:	// stores Text type
			if (datum != null && datum.exists())
				defaultValue = datum.stringVal();
			sb.append("<input type='text' " +
				TricepsEngine.listEventHandlers("text") +
				" name='" + getLocalName() + "' value='" + XMLAttrEncoder.encode(defaultValue) + "'>");
			break;
		case MEMO:
			if (datum != null && datum.exists())
				defaultValue = datum.stringVal();
			sb.append("<textarea rows='5'" +
				TricepsEngine.listEventHandlers("text") +
				" name='" + getLocalName() + "'>" + XMLAttrEncoder.encode(defaultValue) + "</textarea>");
			break;
		case PASSWORD:	// stores Text type
			if (datum != null && datum.exists())
				defaultValue = datum.stringVal();
			sb.append("<input type='password'" +
				TricepsEngine.listEventHandlers("text") +
				" name='" + getLocalName() + "' value='" + XMLAttrEncoder.encode(defaultValue) + "'>");
			break;
		case DOUBLE:	// stores Double type
			if (datum != null && datum.exists())
				defaultValue = datum.stringVal();
			sb.append("<input type='text'" +
				TricepsEngine.listEventHandlers("text") +
				" name='" + getLocalName() + "' value='" + defaultValue + "'>");
			break;
		default:
/*
		case DATE:
		case TIME:
		case YEAR:
		case MONTH:
		case DAY:
		case WEEKDAY:
		case HOUR:
		case MINUTE:
		case SECOND:
		case MONTH_NUM:
		case DAY_NUM:
*/
			if (datum != null && datum.exists())
				defaultValue = datum.stringVal();
			sb.append("<input type='text'" +
				TricepsEngine.listEventHandlers("text") +
				" name='" + getLocalName() + "' value='" + defaultValue + "'>");
			break;
		case NOTHING:
			sb.append("&nbsp;");
			break;
		}

		return sb.toString();
	}
	
	/**
		@return false if the selected value violates the validation criteria
	*/
	/*public*/ boolean isWithinRange(Datum d) {
		boolean err = false;

		if (minDatum != null) {
			if (!DatumMath.ge(d,minDatum).booleanVal())
				err = true;
		}
		if (maxDatum != null) {
			if (!DatumMath.le(d,maxDatum).booleanVal())
				err = true;
		}
		if (err && allowableDatumValues != null) {
			/* then not within valid range - so check if it is an outlying, but allowable value */
			for (int i=0;i<allowableDatumValues.size();++i) {
				if (DatumMath.eq(d,(Datum) allowableDatumValues.elementAt(i)).booleanVal()) {
					err = false;
					break;
				}
			}
		}
		if (!inputValidator.isNull()) {
			if (!inputValidator.isMatch(d.stringVal())) {
				err = true;
			}
		}

		if (err) {
			if (answerType == PASSWORD) {
				setError(triceps.get("incorrect_password"));
			}
			else {
				setError(triceps.get("please_enter_a") + Datum.getTypeName(triceps,datumType) + triceps.get("in_the_range") + getSampleInputString());
			}
		}
		return !(err);
	}

	/*public*/ int getAnswerType() { return answerType; }
	/*public*/ int getDatumType() { return datumType; }
	/*public*/ void setDatumType(int type) {
		if (Datum.isValidType(type)) {
			datumType = type;
		}
		else {
			datumType = Datum.INVALID;
		}
	}


	/*public*/ int getSourceLine() { return sourceLine; }
	/*public*/ String getSourceFile() { return sourceFile; }
	/*public*/ int getQuestionOrEvalType() { return questionOrEvalType; }
	/*public*/ String getQuestionOrEvalTypeField() { return questionOrEvalTypeField; }
	/*public*/ String getMask() { return mask; }

	/*public*/ void setMinDatum(Datum d) {
		if (d == null) {
			minDatum = null;
		}
		else {
			minDatum = d.cast(datumType,mask);
		}
	}

	/*public*/ void setMaxDatum(Datum d) {
		if (d == null) {
			maxDatum = null;
		}
		else {
			maxDatum = d.cast(datumType,mask);
		}
	}

	/*public*/ String getMinStr() { return minStr; }
	/*public*/ String getMaxStr() { return maxStr; }
	/*public*/ Vector getAllowableValues() { return allowableValues; }
	/*public*/ void setAllowableDatumValues(Vector v) { allowableDatumValues = v; }

	/**
		@return true if the answer field can be focused
	*/
	/*public*/ boolean focusable() { return (answerType != BADTYPE && answerType != NOTHING); }
	/**
		@return true of the answer field is an array (radio buttons, list box, combo)
	*/
	/*public*/ boolean focusableArray() { return (answerType == RADIO || answerType == RADIO_HORIZONTAL || answerType == RADIO_HORIZONTAL2 || answerType == CHECK); }

	/*public*/ void setNamingError(String error) {
		namingErrors.println(error);
	}

	/*public*/ void setParseError(String error) {
		logger.debug("##parseError:  " + error);		
		parseErrors.println(error);
	}
	/*public*/ void setError(String error) {
		runtimeErrors.print(error + "<br/>");
	}

	/*public*/ String getErrors() {
		return getParseErrors() + getRuntimeErrors();
	}

	/*public*/ boolean hasParseErrors() { return parseErrors.size() > 0; }
	/*public*/ boolean hasNamingErrors() { return namingErrors.size() > 0; }
	/*public*/ boolean hasRuntimeErrors() { return (runtimeErrors.size() > 0); }

	/*public*/ String getParseErrors() { return parseErrors.toString(false); }
	/*public*/ String getNamingErrors() { return namingErrors.toString(false); }
	/*public*/ String getRuntimeErrors() { return runtimeErrors.toString(); }


	/**
		@return Exported Node and values as tab separated values.
	*/
	/*public*/ String toTSV() {
	    StringBuffer sb = new StringBuffer();
if (AUTHORABLE) {
		sb.append(conceptName);
		sb.append("\t");
		sb.append(localName);
		sb.append("\t");
		sb.append(externalName);
		sb.append("\t");
		sb.append(dependencies);
		sb.append("\t");
		sb.append(questionOrEvalTypeField);

		for (int i = 0;i<numLanguages;++i) {
			try { sb.append("\t"); sb.append(readback.elementAt(i)); } catch (ArrayIndexOutOfBoundsException e) { logger.error("",e); }
			try { sb.append("\t"); sb.append(questionOrEval.elementAt(i)); } catch (ArrayIndexOutOfBoundsException e) { logger.error("",e); }
			try { sb.append("\t"); sb.append(answerChoicesStr.elementAt(i)); } catch (ArrayIndexOutOfBoundsException e) { logger.error("",e); }
			try { sb.append("\t"); sb.append(helpURL.elementAt(i)); } catch (ArrayIndexOutOfBoundsException e) { logger.error("",e); }
		}
}
		return sb.toString();
	}
	
	/**
		@return XML string of Node - out of date
	*/
	/*public*/ String toXML(Datum datum, boolean autogen) {
		StringBuffer ask = new StringBuffer();
if (XML) {
		StringBuffer sb = new StringBuffer();
		String defaultValue = "";
		AnswerChoice ac;
		Enumeration ans = null;
		int count=0;
		boolean nothingSelected = true;
		String msg = (new XmlString(triceps,triceps.getQuestionStr(this))).toString();
		
		ask.append("<node name=\"");
		ask.append(getLocalName());
		ask.append("\" concept=\"");
		ask.append(XMLAttrEncoder.encode(getConcept()));
		ask.append("\" extName=\"");
		ask.append(XMLAttrEncoder.encode(getExternalName()));
		ask.append("\" comment=\"");
		ask.append(XMLAttrEncoder.encode(getComment()));
		ask.append("\" refused=\"" + (datum.isRefused() ? "1" : "0") +
					"\" unknown=\"" + (datum.isUnknown() ? "1" : "0") +
					"\" huh=\"" + (datum.isNotUnderstood() ? "1" : "0"));
		ask.append("\" help=\"");
		ask.append(XMLAttrEncoder.encode(getHelpURL()));
		ask.append("\" err=\"");
		ask.append(XMLAttrEncoder.encode(getRuntimeErrors()));
		ask.append("\">\n	<ask>");
		ask.append(msg);	// can have embedded markup
		ask.append("</ask>\n	<listen>\n");
		
		switch(answerType) {
		case RADIO:
		case RADIO_HORIZONTAL:
		case RADIO_HORIZONTAL2:
		case CHECK:
			ans = getAnswerChoices().elements();
			sb.append("	<multi type=\"" + QUESTION_TYPES[answerType] + "\">\n");
			while (ans.hasMoreElements()) { // for however many choices there are
				++count;
				ac = (AnswerChoice) ans.nextElement();
				ac.parse(triceps);
				sb.append(ac.toXML(isSelected(datum,ac), -1, (autogen) ? Integer.toString(count) : ac.getValue()));
			}
			sb.append("	</multi>\n");
			break;
		case COMBO:	
		case LIST:
		case COMBO2:
		case LIST2:
			ans = getAnswerChoices().elements();
			while (ans.hasMoreElements()) { // for however many choices there are
				++count;
				ac = (AnswerChoice) ans.nextElement();
				ac.parse(triceps);
				boolean selected = isSelected(datum,ac);
				if (selected) {
					nothingSelected = false;
				}
				String key = "";
				if (answerType == COMBO || answerType == LIST) {
					key = (autogen) ? Integer.toString(count) : ac.getValue();
				}
				int max_text_len = Integer.parseInt(triceps.getSchedule().getReserved(Schedule.MAX_TEXT_LEN_FOR_COMBO));
				
				sb.append(ac.toXML(selected, max_text_len, key));
			}
			StringBuffer acs = sb;
			sb = new StringBuffer();
			
			sb.append("	<multi type=\"" + QUESTION_TYPES[answerType] + "\">\n");
			sb.append(AnswerChoice.toXML(triceps.get("select_one_of_the_following"),nothingSelected));
			sb.append(acs);
			sb.append("	</multi>\n");
			break;
		default:
		case TEXT:
		case MEMO:
		case PASSWORD:
		case DOUBLE:
			if (datum != null && datum.exists())
				defaultValue = datum.stringVal();
			sb.append("	<mono type=\"" + QUESTION_TYPES[answerType] + "\" val=\"" + XMLAttrEncoder.encode(defaultValue) + "\"/>\n");
			break;
		case NOTHING:
			sb.append("	<mono type=\"nothing\"/>\n");
			break;
		}
		
		ask.append(sb);
		ask.append("	</listen>\n</node>\n");
}	//XML	
		return ask.toString();
	}

	/*public*/ Date getTimeStamp() { return timeStamp; }
	/*public*/ String getTimeStampStr() { return timeStampStr; }

	/**
		Set the access time for this Node as Now()
	*/
	/*public*/ void setTimeStamp() {
		timeStamp = new Date(System.currentTimeMillis());
	}
	
	/**
		Set the access time for this Node as Now()
	*/	
	private void setTimeStampStr() {
		if (timeStamp == null) 
			timeStampStr = "";
			
		try {
			timeStampStr = Long.toString(timeStamp.getTime());
		}
		catch (NumberFormatException e) {
			logger.error("",e);
			timeStampStr = "";
		}
	}

	/**
		Set the access time for this Node
		
		@param timeStr	the selected time
	*/
	/*public*/ void setTimeStamp(String timeStr) {
		if (timeStr == null || timeStr.trim().length() == 0) {
			setTimeStamp();
			setTimeStampStr();
			return;
		}

		Date time = null;
		try {
			time = new Date(Long.parseLong(timeStr));
		}
		catch (NumberFormatException e) {
			logger.error("error parsing timeStamp",e);			
if (AUTHORABLE) 		setParseError("error parsing timeStamp " + timeStr + " " + e.getMessage());
else setParseError("syntax error");
		}			
		
		if (time == null) {
			setTimeStamp();
		}
		else {
			timeStamp = time;
		}
		setTimeStampStr();
	}

	/* These are the get functions for the language specific vectors */
	// these only occur once
	/*public*/ String getConcept() { return conceptName; }
	/*public*/ String getLocalName() { return localName; }
	/*public*/ String getExternalName() { return externalName; }
	/*public*/ String getDependencies() { return dependencies; }

	// these are a Vector of length #languages
	/**
		Get the language-specific value for a Node
		
		@param v	Vector of languages
		@param langNum	which index to select
		@return	v[langNum], or v[0] if v[langNum] is empty
	*/
	private String getValueAt(Vector v, int langNum) {
		/* If can't get the requested language, get the primary one */
		String s = null;
		if (v ==null)
			return "";
		if (v.size() == 0) {
			return "";
		}
		if (langNum >= v.size())
			langNum = 0;
		s = (String) v.elementAt(langNum);
		if (s == null)
			return "";
		else
			return s;
	}

	/**
		Get the language-specific value for a Node
		
		@param v	Vector of languages
		@param langNum	which index to select
		@return	v[langNum][], or v[0][] if v[langNum][] is empty
	*/
	private Vector getValuesAt(Vector v, int langNum) {
		Vector ans = null;
		if (v == null)
			return EMPTY_VECTOR;

		if (v.size() == 0) {
			return EMPTY_VECTOR;
		}
		if (langNum >= v.size())
			langNum = 0;
		ans = (Vector) v.elementAt(langNum);
		if (ans == null)
			return EMPTY_VECTOR;
		else
			return ans;
	}

	/*public*/ String getReadback(int lang) { return getValueAt(readback,lang); }
	/*public*/ String getQuestionOrEval() { return getQuestionOrEval(answerLanguageNum); }

	/*public*/ String getQuestionOrEval(int langNum) {
   		if (langNum < 0 || langNum >= numLanguages) {
if (AUTHORABLE)			setParseError("languageNum must be in range (0 - " + (numLanguages - 1) + "): " + langNum);
else setParseError("syntax error");
			return getValueAt(questionOrEval, answerLanguageNum);
		}
		return getValueAt(questionOrEval, langNum);
	}

	/**
		Return vector of answer choices for a language
		
		@param langNum	selected language
		@return	Vector of answer options
	*/
	/*public*/ Vector getAnswerChoices(int langNum) { return getValuesAt(answerChoicesVector,langNum); }
	/**
		Return vector of answer choices for the current language
		
		@return	Vector of answer options
	*/
	/*public*/ Vector getAnswerChoices() { return getValuesAt(answerChoicesVector,answerLanguageNum); }
	/**
		@return the number of answer choices for this Node
	*/
	/*public*/ int numAnswerChoices() { return getValuesAt(answerChoicesVector,answerLanguageNum).size(); }
	/*public*/ String getHelpURL() { return getValueAt(helpURL,answerLanguageNum); }

	/**
		Set most recent Question as Asked
		@param s	the tailored question
	*/
	/*public*/ void setQuestionAsAsked(String s) { questionAsAsked = s; }
	/*public*/ String getQuestionAsAsked() { return questionAsAsked; }
	/*public*/ String getAnswerGiven() { return answerGiven; }
	/*public*/ String getAnswerTimeStampStr() { return answerTimeStampStr; }
	/**
		Set comment, if one was entered
		@param c	the comment
	*/
	/*public*/ void setComment(String c) { comment = (c == null) ? "" : c; }
	/*public*/ String getComment() { return ((comment == null) ? "" : comment); }

	/**
		Set the language used for this traversal of the Node
		@param langNum	which one (from list within instrument.  FIXME - should use ISO codes
	*/
	/*public*/ void setAnswerLanguageNum(int langNum) {
   		if (langNum < 0 || langNum >= numLanguages) {
if (AUTHORABLE)			setParseError("languageNum must be in range (0 - " + (numLanguages - 1) + "): " + langNum);
else setParseError("syntax error");
			return;
		}
		answerLanguageNum = langNum;
	}
	/*public*/ int getAnswerLanguageNum() { return answerLanguageNum; }
}
