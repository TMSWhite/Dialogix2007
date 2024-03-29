package org.dianexus.triceps;

/*import java.lang.*;*/
/*import java.util.*;*/
/*import java.io.*;*/
import org.dialogix.util.XMLAttrEncoder;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.util.Enumeration;
import java.text.DecimalFormat;
import java.util.logging.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
This class specifies all of the properties of an individual Item.<br>
An Item (Node) can include a Question (or Instructions), or be an Equation needing parsing.<br>
If it is a Question, it has a response type and possibly a set of allowable response values.
 */
class Node implements VersionIF {

    private static final String LoggerName = "org.dianexus.triceps.Node";
    static final int BADTYPE = 0;
    static final int NOTHING = 1;	// do nothing
    static final int RADIO = 2;
    static final int CHECK = 3;
    static final int COMBO = 4;
    static final int LIST = 5;	// combo of size=(min (6,#lines))
    static final int TEXT = 6;
    static final int DOUBLE = 7;
    static final int RADIO_HORIZONTAL = 8;	// different layout
    static final int PASSWORD = 9;
    static final int MEMO = 10;
    static final int DATE = 11;
    static final int TIME = 12;
    static final int YEAR = 13;
    static final int MONTH = 14;
    static final int DAY = 15;
    static final int WEEKDAY = 16;
    static final int HOUR = 17;
    static final int MINUTE = 18;
    static final int SECOND = 19;
    static final int MONTH_NUM = 20;
    static final int DAY_NUM = 21;
    static final int RADIO_HORIZONTAL2 = 22;	// horizontal, but fits into answer field space, rather than on next line
    static final int COMBO2 = 23;	// no accelerator key (e.g. for long lists)
    static final int LIST2 = 24;		// no accelerator key
    private static final String QUESTION_TYPES[] = {
        "*badtype*", "nothing", "radio", "check", "combo", "list",
        "text", "double", "radio2", "password", "memo",
        "date", "time", "year", "month", "day", "weekday", "hour", "minute", "second", "month_num", "day_num", "radio3", "combo2", "list2"
    };
    private static final String JAVASCRIPT_TYPES[] = {
        "null", "null", "select-one", "checkbox", "select-one", "select-one",
        "text", "text", "select-one", "password", "textarea",
        "text", "text", "text", "text", "text", "text", "text", "text", "text", "text", "text", "select-one", "select-one", "select-one"
    };
    private static final int DATA_TYPES[] = {
        Datum.STRING, Datum.STRING, Datum.STRING, Datum.STRING, Datum.STRING, Datum.STRING,
        Datum.STRING, Datum.NUMBER, Datum.STRING, Datum.STRING, Datum.STRING,
        Datum.DATE, Datum.TIME, Datum.YEAR, Datum.MONTH, Datum.DAY, Datum.WEEKDAY, Datum.HOUR, Datum.MINUTE, Datum.SECOND, Datum.MONTH_NUM, Datum.DAY_NUM, Datum.STRING,
        Datum.STRING, Datum.STRING
    };
    static final int QUESTION = 1;
    static final int EVAL = 2;
    static final int GROUP_OPEN = 3;
    static final int GROUP_CLOSE = 4;
    static final int BRACE_OPEN = 5;
    static final int BRACE_CLOSE = 6;
    static final int CALL_SCHEDULE = 7;
    static final String ACTION_TYPE_NAMES[] = {"*unknown*", "question", "expression", "group_open", "group_close", "brace_open", "brace_close", "call_schedule"};
    static final String ACTION_TYPES[] = {"?", "q", "e", "[", "]", "{", "}", "call"};
    private static final int MAX_ITEMS_IN_LIST = 20;
    private static final String INTRA_OPTION_LINE_BREAK = "<br>";

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
    private String pattern = null;
    private Hashtable answerChoicesHash = new Hashtable();

    /* These are local variables */
    private int sourceLine = 0;
    private String sourceFile = "";
    private int questionOrEvalType = BADTYPE;
    private String questionOrEvalTypeField = "";	// questionOrEvalType;datumType;min;max;mask
    private int answerType = BADTYPE;
    private int datumType = Datum.INVALID;
    private StringBuffer runtimeErrors = new StringBuffer();
    private StringBuffer parseErrors = new StringBuffer();
    private StringBuffer namingErrors = new StringBuffer();
    private String questionOrEvalTypeStr = "";
    private String datumTypeStr = "";
    private String minStr = null;
    private String maxStr = null;
    private Vector allowableValues = null;	// additional validation - list of String values that are considered valid
    private Vector allowableDatumValues = null;	// parsed list of allowable values
    private Datum minDatum = null;
    private Datum maxDatum = null;
    private String mask = null;
//	private InputValidator inputValidator = InputValidator.NULL;
    private Date timeStamp = null;
    private String timeStampStr = null;
    private Triceps triceps = null;

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
    Node(Triceps lang,
         int sourceLine,
         String sourceFile,
         String tsv,
         int numLanguages) {
        triceps = /*(lang == null) ? new Triceps() :*/ lang;
        String token;
        int field = 0;

        if (numLanguages < 1) {
            if (AUTHORABLE) {
                setParseError(triceps.get("numLanguages_must_be_greater_than_zero") + numLanguages);
            } else {
                setParseError("syntax error");
            }
            numLanguages = 1;	// the default
        }

        this.sourceLine = sourceLine;
        this.sourceFile = sourceFile;
        this.numLanguages = numLanguages;	// needs to be validated?

        int numLanguagesFound = 0;
        int numAnswersFound = 0;

        StringTokenizer ans = new StringTokenizer(tsv, "\t", true);

        while (ans.hasMoreTokens()) {
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

            switch (field) {
                /* there should be one copy of each of these */
                case 0:
                    conceptName = (new ExcelDecoder()).decode(s);
                    break;
                case 1:
                    localName = (new ExcelDecoder()).decode(s);
                    break;
                case 2:
                    externalName = (new ExcelDecoder()).decode(s);
                    break;
                case 3:
                    dependencies = (new ExcelDecoder()).decode(s);
                    break;
                case 4:
                    questionOrEvalTypeField = (new ExcelDecoder()).decode(s);
                    break;
                /* there are as many copies of each of these are there are languages */
                case 5:
                    readback.addElement((new ExcelDecoder()).decode(s));
                    break;
                case 6:
                    questionOrEval.addElement((new ExcelDecoder()).decode(s));
                    break;
                case 7:
                    answerChoicesStr.addElement((new ExcelDecoder()).decode(s));
                    break;
                case 8:
                    helpURL.addElement((new ExcelDecoder()).decode(s));
                    break;
                /* there are as many copies of each of these are there are answers - rudimentary support for arrays? */
                case 9:
                     {
                        int i = 0;
                        try {
                            i = Integer.parseInt((new ExcelDecoder()).decode(s));
                        } catch (NumberFormatException t) {
                            Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
                            if (AUTHORABLE) {
                                setParseError(triceps.get("languageNum_must_be_an_integer") + t.getMessage());
                            } else {
                                setParseError("syntax error");
                            }
                            i = 0; // default language
                        }
                        if (i < 0 || i >= numLanguages) {
                            if (AUTHORABLE) {
                                setParseError(triceps.get("languageNum_must_be_in_range_zero_to") + (numLanguages - 1) + "): " + i);
                            } else {
                                setParseError("syntax error");
                            }
                            i = 0;	// default language
                        }
                        answerLanguageNum = i;
                    }
                    break;
                case 10:
                    questionAsAsked = (new ExcelDecoder()).decode(s);
                    break;
                case 11:
                    answerGiven = (new ExcelDecoder()).decode(s);
                    break;
                case 12:
                    comment = (new ExcelDecoder()).decode(s);
                    break;
                case 13:
                    answerTimeStampStr = (new ExcelDecoder()).decode(s);
                    break;
                default:
                    break;	// ignore extras
            }
        }
        if (dependencies == null || dependencies.trim().length() == 0) {
            if (AUTHORABLE) {
                setParseError(triceps.get("dependencies_column_is_missing"));
            } else {
                setParseError("syntax error");
            }
        }
        if (localName != null && localName.trim().length() > 0) {
            localName = localName.trim();
            if (Character.isDigit(localName.charAt(0))) {
                if (AUTHORABLE) {
                    setNamingError(triceps.get("localName_may_not_begin_with_a_digit") + localName);
                } else {
                    setParseError("syntax error");
                }
                localName = "_" + localName;
            }
            if (!isNMTOKEN(localName)) {
                if (AUTHORABLE) {
                    setNamingError(triceps.get("localName_should_only_contain_letters_digits_and_underscores") + localName);
                } else {
                    setParseError("syntax error");
                }
            }
        } else {
            setNamingError(triceps.get("localName_must_be_specified"));
        }

        parseQuestionOrEvalTypeField();

        if (questionOrEvalType == BADTYPE) {
            if (AUTHORABLE) {
                setParseError(triceps.get("invalid_questionOrEvalType") + questionOrEvalTypeField);
            } else {
                setParseError("syntax error");
            }
        }

        for (int i = 0; i < answerChoicesStr.size(); ++i) {
            parseAnswerOptions(i, (String) answerChoicesStr.elementAt(i));
        }

        if (datumType == Datum.INVALID) {
            if (AUTHORABLE) {
                setParseError(triceps.get("invalid_dataType"));
            } else {
                setParseError("syntax error");
            }
        }
    }

    String getJavascriptType() {
        return JAVASCRIPT_TYPES[this.answerType];
    }

    /**
    Helper function to parse the Question or Eval field, which also includes optional validation criteria
     */
    private void parseQuestionOrEvalTypeField() {
        StringTokenizer ans;
        int z;

        if (questionOrEvalTypeField == null) {
            if (AUTHORABLE) {
                setParseError(triceps.get("questionOrEvalTypeField_must_exist"));
            } else {
                setParseError("syntax error");
            }
            return;
        }

        ans = new StringTokenizer(questionOrEvalTypeField, ";", true);	// return ';' tokens too

        for (int field = 0; ans.hasMoreTokens();) {
            String s = null;
            s = ans.nextToken();

            if (";".equals(s)) {
                ++field;
                continue;
            }
            switch (field) {
                case 0:
                    questionOrEvalTypeStr = s;
                    for (z = 0; z < ACTION_TYPES.length; ++z) {
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
                        if (AUTHORABLE) {
                            setParseError(triceps.get("invalid_dataType") + datumTypeStr);
                        } else {
                            setParseError("syntax error");
                        }
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
                    if (s == null || s.trim().length() == 0 || !s.startsWith("PERL5")) {
                        mask = s;
                        pattern = null;
                    } else {
                        pattern = s.substring("PERL5".length());
                        try {
                            Pattern.compile(pattern);
                        } catch (PatternSyntaxException ex) {
                            setParseError("Invalid Regular Expression Pattern " + pattern + " " + ex.getMessage());
                        }
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

        for (int i = 0; i < v.size(); ++i) {
            Datum d = (Datum) v.elementAt(i);
            sb.append("," + ((i == v.size() - 1) ? (" " + triceps.get("or")) : "") + " " + d.stringVal());
        }
        return sb.toString();
    }

    /**
    @return Locale-specific sample input string which meets datatype criteria for node. Used for error reporting.
     */
    String getSampleInputString() {
        /* Create the help-string showing allowable range of input values.
        Can be re-created (e.g. if range dynamically changes */

        String min = null;
        String max = null;
        String other = null;
        String rangeStr = null;
        String s = null;

        s = Datum.getExampleFormatStr(triceps, mask, datumType);

        if (!(s == null || s.equals(""))) {
            rangeStr = " (e.g. " + s + ")";
        }

        if (answerType == PASSWORD) {
            return "";
        }
        if (minDatum == null && maxDatum == null && allowableDatumValues == null && pattern == null && rangeStr != null) {
            return rangeStr;
        }

        if (minDatum != null) {
            setMinDatum(minDatum);
            min = minDatum.stringVal(true, mask);
        }
        if (maxDatum != null) {
            setMaxDatum(maxDatum);
            max = maxDatum.stringVal(true, mask);
        }
        if (allowableDatumValues != null) {
            other = buildOrList(allowableDatumValues);
        }

        if (minDatum != null && maxDatum != null) {
            if ((new DatumMath()).lt(maxDatum, minDatum).booleanVal()) {
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
        if (pattern != null) {
            rangeStr = "(e.g. m/" + pattern + "/";
        }

        if (!rangeStr.equals("( - )")) {
            return " " + rangeStr;
        }
        return "";
    }

    /**
    Helper function to parse Answer Options and populate Node object
    @param langNum	which language vector to populate
    @param src	The string to be parsed
    @return	true if suceeds
     */
    private boolean parseAnswerOptions(int langNum,
                                        String src) {
        /* Need to make sure that the answer type, order of answers, and internal values of answers are the same across all languages */
        if (src == null) {
            if (AUTHORABLE) {
                setParseError(triceps.get("answerOptions_column_missing"));
            } else {
                setParseError("syntax error");
            }
            return false;
        }

        StringTokenizer ans = new StringTokenizer(src, "|", true);	// return '|' tokens too
        String token = "";

        // Determine the question type (first token)
        try {
            token = ans.nextToken();
        } catch (NoSuchElementException t) {
            Logger.getLogger(LoggerName).log(Level.SEVERE, "missing_display_type", t);
            if (AUTHORABLE) {
                setParseError(triceps.get("missing_display_type") + t.getMessage());
            } else {
                setParseError("syntax error");
            }
        }

        if (langNum == 0) {
            for (int z = 0; z < QUESTION_TYPES.length; ++z) {
                if (token.equalsIgnoreCase(QUESTION_TYPES[z])) {
                    answerType = z;
                    break;
                }
            }
        } else {
            if (!QUESTION_TYPES[answerType].equalsIgnoreCase(token)) {
                if (AUTHORABLE) {
                    setParseError(triceps.get("mismatch_across_languages_in_answerType"));
                } else {
                    setParseError("syntax error");
                }
            }
        // don't change the known value for answerType
        }

        if (questionOrEvalType == EVAL) {
            answerType = NOTHING;	// so no further processing
        } else if (answerType == BADTYPE) {
            if (AUTHORABLE) {
                setParseError(triceps.get("invalid_answerType"));
            } else {
                setParseError("syntax error");
            }
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
                String val = null;
                String msg = null;
                int field = 0;
                Vector ansOptions = new Vector();
                Vector prevAnsOptions = null;

                if (langNum > 0) {
                    prevAnsOptions = getValuesAt(answerChoicesVector, 0);
                }

                int ansPos = 0;

                while (ans.hasMoreTokens()) {
                    String s = null;
                    s = ans.nextToken();

                    if ("|".equals(s)) {
                        ++field;
                        continue;
                    }
                    switch (field) {
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
                                } catch (NullPointerException t) {
                                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
                                    err = true;
                                } catch (ArrayIndexOutOfBoundsException t) {
                                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
                                    err = true;
                                }
                                if (err) {
                                    if (AUTHORABLE) {
                                        setParseError(triceps.get("mismatch_across_languages_in_return_value_for_answerChoice_num") + (ansPos - 1));
                                    } else {
                                        setParseError("syntax error");
                                    }
                                    val = s2;	// reset it to the previously known return value for consistency (?)
                                }
                            }
                            break;
                        case 2:
                            msg = s;
                            field = 0;	// so that cycle between val & msg;
                            if (val == null || msg == null) {
                                if (AUTHORABLE) {
                                    setParseError(triceps.get("missing_value_or_message_for_answerChoice_num") + (ansPos - 1));
                                } else {
                                    setParseError("syntax error");
                                }
                            } else {
                                AnswerChoice ac = new AnswerChoice(val, msg);
                                ansOptions.addElement(ac);

                                /* check for duplicate answer choice values */
                                if (langNum == 0) {	// only for first pass
                                    if (answerChoicesHash.put(val, ac) != null) {
                                        if (AUTHORABLE) {
                                            setParseError(triceps.get("answerChoice_value_already_used") + val);
                                        } else {
                                            setParseError("syntax error");
                                        }
                                    }
                                }
                            }
                            val = null;
                            msg = null;
                            break;
                    }
                }
                if (ansOptions.size() == 0) {
                    if (AUTHORABLE) {
                        setParseError(triceps.get("answerChoices_must_be_specified"));
                    } else {
                        setParseError("syntax error");
                    }
                }
                if (field == 1) {
                    if (AUTHORABLE) {
                        setParseError(triceps.get("missing_message_for_answerChoice_num") + (ansPos - 1));
                    } else {
                        setParseError("syntax error");
                    }
                }
                if (langNum > 0) {
                    if (prevAnsOptions.size() != ansOptions.size()) {
                        if (AUTHORABLE) {
                            setParseError(triceps.get("mismatch_across_languages_in_number_of_answerChoices") + prevAnsOptions.size() + " != " + ansOptions.size());
                        } else {
                            setParseError("syntax error");
                        }
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
    String prepareChoicesAsHTML(Datum datum,
                                boolean autogen) {
        return prepareChoicesAsHTML(datum, "", autogen);
    }

    /**
    Helper function
    @return true if a specified answer choice within a enumerated list is selected.
     */
    boolean isSelected(Datum datum,
                       AnswerChoice ac) {
        return (new DatumMath()).eq(datum, new Datum(triceps, ac.getValue(), DATA_TYPES[answerType])).booleanVal();
    }

    /**
    Create HTML input field for this node, given its currently selected value and possible error messages
    @param datum	the value
    @param errMsg	optional error messages
    @param autogen	whether to auto-number the options
    @return	HTML fragment
     */
    String prepareChoicesAsHTML(Datum datum,
                                String errMsg,
                                boolean autogen) {
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
                    sb.append("<input type='radio' name='" + getLocalName() + "' id ='" + getLocalName() + "' value='" + ac.getValue() + "'" +
                        (isSelected(datum, ac) ? " checked " : " ") + ">" + ac.getMessage() + "<br>");
                }
                break;
            case RADIO_HORIZONTAL:
                 { // will store integers
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
                            sb.append("<input type='radio' name='" + getLocalName() + "' id='" + getLocalName() + "' value='" + ac.getValue() + "'" +
                                (isSelected(datum, ac) ? " checked " : " ") + ">" + ac.getMessage());
                            sb.append("</td>");
                        }
                        sb.append("</tr>");
                        sb.append("</table>");
                    }
                }
                break;
            case RADIO_HORIZONTAL2:
                 {
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
                            sb.append("<input type='radio' name='" + getLocalName() + "' id='" + getLocalName() + "' value='" + ac.getValue() + "'" +
                                (isSelected(datum, ac) ? " checked " : " ")+ ">" + ac.getMessage());
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
                    sb.append("<input type='checkbox' name='" + getLocalName() + "' id='" + getLocalName() + "' value='" + ac.getValue() + "'" +
                        (isSelected(datum, ac) ? " checked " : " ") + ">" + ac.getMessage() + "<br>");
                }
                break;
            case COMBO:	// stores integers as value
            case COMBO2:
            case LIST2:
            case LIST:
                 {
                    StringBuffer choices = new StringBuffer();
                    ans = getAnswerChoices().elements();

                    int optionNum = 0;
                    int totalLines = 0;
                    boolean nothingSelected = true;
                    while (ans.hasMoreElements()) { // for however many radio buttons there are
                        ac = (AnswerChoice) ans.nextElement();
                        ac.parse(triceps);
                        ++optionNum;

                        String messageStr = ac.getMessage();
                        String prefix = "<option value='" + ac.getValue() + "'";
                        boolean selected = isSelected(datum, ac);
                        if (selected) {
                            nothingSelected = false;
                        }

                        int max_text_len = Integer.parseInt(triceps.getSchedule().getReserved(Schedule.MAX_TEXT_LEN_FOR_COMBO));

                        v = subdivideMessage(messageStr, max_text_len);

                        for (int i = 0; i < v.size(); ++i) {
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
                            } else {	// indent to indicate that same as previous
                                choices.append("&nbsp;&nbsp;&nbsp;");
                            }
                            choices.append((String) v.elementAt(i));
                            choices.append("</option>");
                        }
                        totalLines += v.size();
                    }
                    sb.append("<select name='" + getLocalName() + "' id='" + getLocalName() + "' " +
                        ((answerType == LIST || answerType == LIST2) ? (" size = '" + Math.min(MAX_ITEMS_IN_LIST, totalLines + 1) + "' ") : " ") +
                        ">");
                    sb.append("<option value=''" +
                        ((nothingSelected) ? " selected" : "") + ">" + // so that focus is properly shifted on List box
                        triceps.get("select_one_of_the_following") +
                        "</option>");	// first choice is empty
                    sb.append(choices);
                    sb.append("</select>");
                }
                break;
            case TEXT:	// stores Text type
                if (datum != null && datum.exists()) {
                    defaultValue = datum.stringVal();
                }
                sb.append("<input type='text' " +
                    " name='" + getLocalName() + "' id='" + getLocalName() + "' value='" + (new XMLAttrEncoder()).encode(defaultValue) + "'>");
                break;
            case MEMO:
                if (datum != null && datum.exists()) {
                    defaultValue = datum.stringVal();
                }
                sb.append("<textarea rows='5'" +
                    " name='" + getLocalName() + "' id='" + getLocalName() + "'>" + (new XMLAttrEncoder()).encode(defaultValue) + "</textarea>");
                break;
            case PASSWORD:	// stores Text type
                if (datum != null && datum.exists()) {
                    defaultValue = datum.stringVal();
                }
                sb.append("<input type='password'" +
                    " name='" + getLocalName() + "' id='" + getLocalName() + "' value='" + (new XMLAttrEncoder()).encode(defaultValue) + "'>");
                break;
            case DOUBLE:	// stores Double type
                if (datum != null && datum.exists()) {
                    defaultValue = datum.stringVal();
                }
                sb.append("<input type='text'" +
                    " name='" + getLocalName() + "' id='" + getLocalName() + "' value='" + defaultValue + "'>");
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
                if (datum != null && datum.exists()) {
                    defaultValue = datum.stringVal();
                }
                sb.append("<input type='text'" +
                    " name='" + getLocalName() + "' id='" + getLocalName() + "' value='" + defaultValue + "'>");
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
    boolean isWithinRange(Datum d) {
        boolean err = false;

        if (minDatum != null) {
            if (!(new DatumMath()).ge(d, minDatum).booleanVal()) {
                err = true;
            }
        }
        if (maxDatum != null) {
            if (!(new DatumMath()).le(d, maxDatum).booleanVal()) {
                err = true;
            }
        }
        if (err && allowableDatumValues != null) {
            /* then not within valid range - so check if it is an outlying, but allowable value */
            for (int i = 0; i < allowableDatumValues.size(); ++i) {
                if ((new DatumMath()).eq(d, (Datum) allowableDatumValues.elementAt(i)).booleanVal()) {
                    err = false;
                    break;
                }
            }
        }
        if (pattern != null) {
            if (!Pattern.matches(pattern, d.stringVal())) {
                err = true;
            }
        }

        if (err) {
            if (answerType == PASSWORD) {
                setError(triceps.get("incorrect_password"));
            } else {
                setError(triceps.get("please_enter_a") + Datum.getTypeName(triceps, datumType) + triceps.get("in_the_range") + getSampleInputString());
            }
        }
        return !(err);
    }

    int getAnswerType() {
        return answerType;
    }

    int getDatumType() {
        return datumType;
    }

    void setDatumType(int type) {
        if (Datum.isValidType(type)) {
            datumType = type;
        } else {
            datumType = Datum.INVALID;
        }
    }

    int getSourceLine() {
        return sourceLine;
    }

    String getSourceFile() {
        return sourceFile;
    }

    int getQuestionOrEvalType() {
        return questionOrEvalType;
    }

    String getQuestionOrEvalTypeField() {
        return questionOrEvalTypeField;
    }

    String getMask() {
        return mask;
    }

    void setMinDatum(Datum d) {
        if (d == null) {
            minDatum = null;
        } else {
            minDatum = d.cast(datumType, mask);
        }
    }

    void setMaxDatum(Datum d) {
        if (d == null) {
            maxDatum = null;
        } else {
            maxDatum = d.cast(datumType, mask);
        }
    }

    String getMinStr() {
        return minStr;
    }

    String getMaxStr() {
        return maxStr;
    }

    Vector getAllowableValues() {
        return allowableValues;
    }

    void setAllowableDatumValues(Vector v) {
        allowableDatumValues = v;
    }

    /**
    @return true if the answer field can be focused
     */
    boolean focusable() {
        return (answerType != BADTYPE && answerType != NOTHING);
    }

    /**
    @return true of the answer field is an array (radio buttons, list box, combo)
     */
    boolean focusableArray() {
        return (answerType == RADIO || answerType == RADIO_HORIZONTAL || answerType == RADIO_HORIZONTAL2 || answerType == CHECK);
    }

    void setNamingError(String error) {
        namingErrors.append(error).append("<br/>");
    }

    void setParseError(String error) {
//        logger.log(Level.FINE, "##parseError:  " + error);
        parseErrors.append(error).append("<br/>");
    }

    void setError(String error) {
        runtimeErrors.append(error).append("<br/>");
    }

    String getErrors() {
        return getParseErrors() + getRuntimeErrors();
    }

    boolean hasParseErrors() {
        return parseErrors.length() > 0;
    }

    boolean hasNamingErrors() {
        return namingErrors.length() > 0;
    }

    boolean hasRuntimeErrors() {
        return (runtimeErrors.length() > 0);
    }

    String getParseErrors() {
        String errors = parseErrors.toString();
        parseErrors = new StringBuffer();
        return errors;
    }

    String getNamingErrors() {
        String errors = namingErrors.toString();
        namingErrors = new StringBuffer();
        return errors;
    }

    String getRuntimeErrors() {
        String errors = runtimeErrors.toString();
        runtimeErrors = new StringBuffer();
        return errors;
    }

    /**
    @return Exported Node and values as tab separated values.
     */
    String toTSV() {
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

            for (int i = 0; i < numLanguages; ++i) {
                try {
                    sb.append("\t");
                    sb.append(readback.elementAt(i));
                } catch (ArrayIndexOutOfBoundsException e) {
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
                }
                try {
                    sb.append("\t");
                    sb.append(questionOrEval.elementAt(i));
                } catch (ArrayIndexOutOfBoundsException e) {
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
                }
                try {
                    sb.append("\t");
                    sb.append(answerChoicesStr.elementAt(i));
                } catch (ArrayIndexOutOfBoundsException e) {
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
                }
                try {
                    sb.append("\t");
                    sb.append(helpURL.elementAt(i));
                } catch (ArrayIndexOutOfBoundsException e) {
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
                }
            }
        }
        return sb.toString();
    }

    /**
    @return XML string of Node - out of date
     */
//    String toXML(Datum datum,
//                 boolean autogen) {
//        StringBuffer ask = new StringBuffer();
//        if (XML) {
//            StringBuffer sb = new StringBuffer();
//            String defaultValue = "";
//            AnswerChoice ac;
//            Enumeration ans = null;
//            int count = 0;
//            boolean nothingSelected = true;
//            String msg = (new XmlString(triceps, triceps.getQuestionStr(this))).toString();
//
//            ask.append("<node name=\"");
//            ask.append(getLocalName());
//            ask.append("\" concept=\"");
//            ask.append((new XMLAttrEncoder()).encode(getConcept()));
//            ask.append("\" extName=\"");
//            ask.append((new XMLAttrEncoder()).encode(getExternalName()));
//            ask.append("\" comment=\"");
//            ask.append((new XMLAttrEncoder()).encode(getComment()));
//            ask.append("\" refused=\"" + (datum.isRefused() ? "1" : "0") +
//                "\" unknown=\"" + (datum.isUnknown() ? "1" : "0") +
//                "\" huh=\"" + (datum.isNotUnderstood() ? "1" : "0"));
//            ask.append("\" help=\"");
//            ask.append((new XMLAttrEncoder()).encode(getHelpURL()));
//            ask.append("\" err=\"");
//            ask.append((new XMLAttrEncoder()).encode(getRuntimeErrors()));
//            ask.append("\">\n	<ask>");
//            ask.append(msg);	// can have embedded markup
//            ask.append("</ask>\n	<listen>\n");
//
//            switch (answerType) {
//                case RADIO:
//                case RADIO_HORIZONTAL:
//                case RADIO_HORIZONTAL2:
//                case CHECK:
//                    ans = getAnswerChoices().elements();
//                    sb.append("	<multi type=\"" + QUESTION_TYPES[answerType] + "\">\n");
//                    while (ans.hasMoreElements()) { // for however many choices there are
//                        ++count;
//                        ac = (AnswerChoice) ans.nextElement();
//                        ac.parse(triceps);
//                        sb.append(ac.toXML(isSelected(datum, ac), -1, (autogen) ? Integer.toString(count) : ac.getValue()));
//                    }
//                    sb.append("	</multi>\n");
//                    break;
//                case COMBO:
//                case LIST:
//                case COMBO2:
//                case LIST2:
//                    ans = getAnswerChoices().elements();
//                    while (ans.hasMoreElements()) { // for however many choices there are
//                        ++count;
//                        ac = (AnswerChoice) ans.nextElement();
//                        ac.parse(triceps);
//                        boolean selected = isSelected(datum, ac);
//                        if (selected) {
//                            nothingSelected = false;
//                        }
//                        String key = "";
//                        if (answerType == COMBO || answerType == LIST) {
//                            key = (autogen) ? Integer.toString(count) : ac.getValue();
//                        }
//                        int max_text_len = Integer.parseInt(triceps.getSchedule().getReserved(Schedule.MAX_TEXT_LEN_FOR_COMBO));
//
//                        sb.append(ac.toXML(selected, max_text_len, key));
//                    }
//                    StringBuffer acs = sb;
//                    sb = new StringBuffer();
//
//                    sb.append("	<multi type=\"" + QUESTION_TYPES[answerType] + "\">\n");
//                    sb.append(AnswerChoice.toXML(triceps.get("select_one_of_the_following"), nothingSelected));
//                    sb.append(acs);
//                    sb.append("	</multi>\n");
//                    break;
//                default:
//                case TEXT:
//                case MEMO:
//                case PASSWORD:
//                case DOUBLE:
//                    if (datum != null && datum.exists()) {
//                        defaultValue = datum.stringVal();
//                    }
//                    sb.append("	<mono type=\"" + QUESTION_TYPES[answerType] + "\" val=\"" + (new XMLAttrEncoder()).encode(defaultValue) + "\"/>\n");
//                    break;
//                case NOTHING:
//                    sb.append("	<mono type=\"nothing\"/>\n");
//                    break;
//            }
//
//            ask.append(sb);
//            ask.append("	</listen>\n</node>\n");
//        }	//XML	
//        return ask.toString();
//    }

    Date getTimeStamp() {
        return timeStamp;
    }

    String getTimeStampStr() {
        return timeStampStr;
    }

    /**
    Set the access time for this Node as Now()
     */
    void setTimeStamp() {
        timeStamp = new Date(System.currentTimeMillis());
    }

    /**
    Set the access time for this Node as Now()
     */
    private void setTimeStampStr() {
        if (timeStamp == null) {
            timeStampStr = "";
        }

        try {
            timeStampStr = Long.toString(timeStamp.getTime());
        } catch (NumberFormatException e) {
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            timeStampStr = "";
        }
    }

    /**
    Set the access time for this Node
    @param timeStr	the selected time
     */
    void setTimeStamp(String timeStr) {
        if (timeStr == null || timeStr.trim().length() == 0) {
            setTimeStamp();
            setTimeStampStr();
            return;
        }

        Date time = null;
        try {
            time = new Date(Long.parseLong(timeStr));
        } catch (NumberFormatException e) {
            Logger.getLogger(LoggerName).log(Level.SEVERE, "error parsing timeStamp", e);
            if (AUTHORABLE) {
                setParseError("error parsing timeStamp " + timeStr + " " + e.getMessage());
            } else {
                setParseError("syntax error");
            }
        }

        if (time == null) {
            setTimeStamp();
        } else {
            timeStamp = time;
        }
        setTimeStampStr();
    }

    /* These are the get functions for the language specific vectors */
    // these only occur once
    String getConcept() {
        return conceptName;
    }

    String getLocalName() {
        return localName;
    }

    String getExternalName() {
        return externalName;
    }

    String getDependencies() {
        return dependencies;
    }

    // these are a Vector of length #languages
    /**
    Get the language-specific value for a Node
    @param v	Vector of languages
    @param langNum	which index to select
    @return	v[langNum], or v[0] if v[langNum] is empty
     */
    private String getValueAt(Vector v,
                               int langNum) {
        /* If can't get the requested language, get the primary one */
        String s = null;
        if (v == null) {
            return "";
        }
        if (v.size() == 0) {
            return "";
        }
        if (langNum >= v.size()) {
            langNum = 0;
        }
        s = (String) v.elementAt(langNum);
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    /**
    Get the language-specific value for a Node
    @param v	Vector of languages
    @param langNum	which index to select
    @return	v[langNum][], or v[0][] if v[langNum][] is empty
     */
    private Vector getValuesAt(Vector v,
                                int langNum) {
        Vector ans = null;
        if (v == null) {
            return new Vector();
        }

        if (v.size() == 0) {
            return new Vector();
        }
        if (langNum >= v.size()) {
            langNum = 0;
        }
        ans = (Vector) v.elementAt(langNum);
        if (ans == null) {
            return new Vector();
        } else {
            return ans;
        }
    }

    String getReadback(int lang) {
        return getValueAt(readback, lang);
    }

    String getQuestionOrEval() {
        return getQuestionOrEval(answerLanguageNum);
    }

    String getQuestionOrEval(int langNum) {
        if (langNum < 0 || langNum >= numLanguages) {
            if (AUTHORABLE) {
                setParseError("languageNum must be in range (0 - " + (numLanguages - 1) + "): " + langNum);
            } else {
                setParseError("syntax error");
            }
            return getValueAt(questionOrEval, answerLanguageNum);
        }
        return getValueAt(questionOrEval, langNum);
    }

    /**
    Return vector of answer choices for a language
    @param langNum	selected language
    @return	Vector of answer options
     */
    Vector getAnswerChoices(int langNum) {
        return getValuesAt(answerChoicesVector, langNum);
    }

    /**
    Return vector of answer choices for the current language
    @return	Vector of answer options
     */
    Vector getAnswerChoices() {
        return getValuesAt(answerChoicesVector, answerLanguageNum);
    }

    /**
    @return the number of answer choices for this Node
     */
    int numAnswerChoices() {
        return getValuesAt(answerChoicesVector, answerLanguageNum).size();
    }

    String getHelpURL() {
        return getValueAt(helpURL, answerLanguageNum);
    }

    /**
    Set most recent Question as Asked
    @param s	the tailored question
     */
    void setQuestionAsAsked(String s) {
        questionAsAsked = s;
    }

    String getQuestionAsAsked() {
        return questionAsAsked;
    }

    String getAnswerGiven() {
        return answerGiven;
    }

    String getAnswerTimeStampStr() {
        return answerTimeStampStr;
    }

    /**
    Set comment, if one was entered
    @param c	the comment
     */
    void setComment(String c) {
        comment = (c == null) ? "" : c;
    }

    String getComment() {
        return ((comment == null) ? "" : comment);
    }

    /**
    Set the language used for this traversal of the Node
    @param langNum	which one (from list within instrument.  FIXME - should use ISO codes
     */
    void setAnswerLanguageNum(int langNum) {
        if (langNum < 0 || langNum >= numLanguages) {
            if (AUTHORABLE) {
                setParseError("languageNum must be in range (0 - " + (numLanguages - 1) + "): " + langNum);
            } else {
                setParseError("syntax error");
            }
            return;
        }
        answerLanguageNum = langNum;
    }

    int getAnswerLanguageNum() {
        return answerLanguageNum;
    }

    public String getLocalizedAnswer(Datum datum) {
        int num_choices = numAnswerChoices();
        String answer = "";
        if (num_choices > 0) {
            Vector choices = getAnswerChoices();
            if (datum.isSpecial()) {
                answer = datum.toString();
            } else {
                String s = datum.stringVal();
                for (int j = 0; j < choices.size(); ++j) {
                    AnswerChoice ac = (AnswerChoice) choices.elementAt(j);
                    if (ac.getValue().equals(s)) {
                        answer = ac.getMessage();
                    }
                }
            }
        } else {
            answer = triceps.toString(this, true);
        }
        return answer;
    }
    
    boolean isNMTOKEN(String token) {
        char[] chars = token.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            if (!(Character.isLetterOrDigit(chars[i]) || chars[i] == '_')) {
                return false;
            }
        }
        return true;
    }    
    
    private Vector subdivideMessage(String src,   //  CONCURRENCY RISK?:  Should be OK
                                    int maxLen) {
        /** splits a string at a natural boundaries so that no line is longer than maxLen */
        Vector<String> choices = new Vector<String>();
        int start = 0;
        int stop = 0;
        int toadd = 0;
        int lineBreak = 0;
        char breakChar;
        char[] breakChars = {' ', '-', '.', ':', ']', '[', '(', ')'};
        int breakCharIdx = 0;
        String option = null;
        String messageStr = src;

        if (maxLen == -1) {
            choices.addElement(messageStr);
            return choices;
        }

        /* also detects <br> for intra-option line-breaks */
        while (start < messageStr.length()) {
            toadd = 0;

            lineBreak = messageStr.indexOf(INTRA_OPTION_LINE_BREAK, start);
            if (lineBreak == -1) {
                option = messageStr.substring(start, messageStr.length());
            } else {
                option = messageStr.substring(start, lineBreak);
            }

            if (option.length() <= maxLen) {
                stop = option.length();
                choices.addElement(option.substring(0, stop));
                if (lineBreak != -1) {
                    toadd = INTRA_OPTION_LINE_BREAK.length();
                }
            } else {
                for (breakCharIdx = 0; breakCharIdx < breakChars.length; ++breakCharIdx) {
                    stop = option.lastIndexOf(breakChars[breakCharIdx], maxLen);
                    if (stop != -1) {
                        toadd = 1;
                        break;
                    }
                }
                if (breakCharIdx == 0 || stop == -1) {
                    choices.addElement(option.substring(0, stop));	// exclude the space
                } else {
                    choices.addElement(option.substring(0, stop + 1));	// include the punctuation
                }
            }
            start += (stop + toadd);
        }
        return choices;
    }    
}
