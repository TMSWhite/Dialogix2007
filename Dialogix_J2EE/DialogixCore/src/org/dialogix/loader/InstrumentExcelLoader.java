package org.dialogix.loader;

import jxl.*;
import java.io.*;
import java.util.*;
import org.dialogix.entities.*;
import org.dialogix.session.InstrumentLoadException;
import org.dialogix.session.InstrumentLoaderFacadeLocal;
import java.security.*;
import java.util.logging.*;
import java.util.regex.*;
import java.util.zip.ZipFile;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Load instrument into full data model, enforcing uniqueness constraints
 */
public class InstrumentExcelLoader implements java.io.Serializable, org.dianexus.triceps.VersionIF {

    private String DIALOGIX_SCHEDULES_DIR = "/usr/local/dialogix3/instruments/";   // TODO - was "@@DIALOGIX.SCHEDULES.DIR@@"
    private Logger logger = Logger.getLogger("org.dialogix.loader.InstrumentExcelLoader");
    private static int UseCounter = 0;
    private int numCols = 0;
    private int numRows = 0;
    private int numLanguages = 0;
    private ArrayList<String> languageCodes = null;
    private ArrayList<InstrumentHeader> instrumentHeaders = null;
    private ArrayList<InstrumentContent> instrumentContents = null;
    private String majorVersion = "0";
    private String minorVersion = "0";
    private String title = "unknown";
    private int groupNum = 0;
    private boolean withinBlock = false;
    private Instrument instrument = null;
    private InstrumentVersion instrumentVersion = null;
    private LanguageList languageList = null;
    /* These are needed for InstrumentHash */
    private InstrumentHash instrumentHash = null;
    private int numVars = 0;
    private ArrayList<String> varNameStrings = null;
    private int numEquations = 0;
    private int numQuestions = 0;
    private int numBranches = 0;
    private int numTailorings = 0;
    private int numInstructions = 0;
    private StringBuffer varNameMD5source = null;
    private StringBuffer instrumentContentsMD5source = null;
    private String justFileName = null;
    private InstrumentLoaderFacadeLocal instrumentLoaderFacade = null;
    private ArrayList<InstrumentLoadError> instrumentLoadErrors = new ArrayList<InstrumentLoadError>();
    private int instrumentLoadErrorCounter = 0;
    private int instrumentLoadMessageCounter = 0;
    private String[][] source = null;
    private String instrumentVersionFilename = null;
    private boolean databaseStatus = false;
    private boolean versionFileStatus = false;
    private String varListMD5Hash;
    private String instrumentMD5Hash;
    private Vector<String> rows = new Vector<String>();

    /**
     * Constructor
     */
    public InstrumentExcelLoader() {
        instrumentLoaderFacade = lookupInstrumentLoaderFacadeLocal();
    }

    /**
     * Load instrument from Excel, .txt, or .jar file
     * @param filename
     * @return
     */
    public boolean loadInstrument(String filename) {
        this.databaseStatus = false;
        this.versionFileStatus = false;
        if (filename == null || "".equals(filename.trim())) {
            return false;
        }
        justFileName = filename.substring(filename.lastIndexOf(File.separatorChar) + 1);
        justFileName = justFileName.substring(0, justFileName.lastIndexOf(".")); // Remove extension
        varNameStrings = new ArrayList<String>();
        varNameMD5source = new StringBuffer();
        instrumentContentsMD5source = new StringBuffer();
        instrumentVersionFilename = createInstrumentVersionFilename(DIALOGIX_SCHEDULES_DIR, justFileName);

        logger.log(Level.FINE, "Importing '" + justFileName + "' from '" + filename + "'");

        if (convertFileToArray(filename) == true) {
            this.databaseStatus = processInstrumentSource();
            if (DB_WRITE_SYSTEM_FILES) {
                this.versionFileStatus = writeInstrumentArrayToFile();
            }
            else {
                this.instrumentVersionFilename = instrumentVersion.getInstrumentVersionID().toString();
                this.versionFileStatus = true;
            }
        }
        return (this.databaseStatus || this.versionFileStatus);
    }
    
    /**
     * Load instrument from Excel, .txt, or .jar file
     * @param filename
     * @return
     */
    public boolean loadInstrument(String title, String source) {
        this.databaseStatus = false;
        this.versionFileStatus = false;
        if (source == null || source.length() == 0) {
            return false;
        }
        justFileName = title;
        varNameStrings = new ArrayList<String>();
        varNameMD5source = new StringBuffer();
        instrumentContentsMD5source = new StringBuffer();
        instrumentVersionFilename = createInstrumentVersionFilename(DIALOGIX_SCHEDULES_DIR, justFileName);

        logger.log(Level.FINE, "Importing '" + justFileName + "' from source array");

        if (convertStringToArray(source) == true) {
            this.databaseStatus = processInstrumentSource();
            this.versionFileStatus = writeInstrumentArrayToFile();
        }
        return (this.databaseStatus || this.versionFileStatus);
    }    

    /**
     * Load contents of filename into String Array, setting numRows, numCols, and source
     * @param filename
     * @return
     */
    private boolean convertFileToArray(String filename) {
        if (filename.endsWith(".xls")) {
            return convertWorkbookToArray(filename);
        } else if (filename.endsWith(".txt")) {
            return convertTxtToArray(filename, "ISO-8859-1"); 
        } else if (filename.endsWith(".jar")) {
            return convertJarToArray(filename, "ISO-8859-1"); 
        } else {
            logger.log(Level.SEVERE, "Unable to process file " + filename);
            return false;
        }
    }
    
    private boolean convertStringToArray(String source) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new StringReader(source));
            if (convertBufferedReaderToVector(br)) {
                return convertVectorToArray();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return false;        
    }

    /**
     * Load Jarred instrument into String Array
     * @param filename
     * @param charset
     * @return
     */
    private boolean convertJarToArray(String filename, String charset) {
        ZipFile jf = null;
        BufferedReader headers = null;
        BufferedReader body = null;

        try {
            jf = new ZipFile(filename);
            headers = new BufferedReader(new InputStreamReader(jf.getInputStream(jf.getEntry("headers")), charset));
            body = new BufferedReader(new InputStreamReader(jf.getInputStream(jf.getEntry("body")), charset));
            if (convertBufferedReaderToVector(headers) && convertBufferedReaderToVector(body)) {
                return convertVectorToArray();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
            return false;
        }
        if (jf != null) {
            try {
                if (headers != null) {
                    headers.close();
                }
                if (body != null) {
                    body.close();
                }
                jf.close();                
            } catch (Exception t) {
                logger.log(Level.SEVERE, "", t);
            }
        }
        return false;
    }

    /**
     * Load .txt instrument into String Array
     * @param filename
     * @param charset
     * @return
     */
    private boolean convertTxtToArray(String filename, String charset) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), charset));
            if (convertBufferedReaderToVector(br)) {
                return convertVectorToArray();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return false;
    }
    
    /**
     * Convert Vector of Lines into a 2D array Strings
     * @return
     */
    private boolean convertVectorToArray() {
        source = new String[numCols][numRows];

        for (int row = 0; row < numRows; ++row) {
            String[] tokens = rows.get(row).split("\t");
            int col;
            int numTokens = tokens.length; 
            for (col = 0; col < numTokens; ++col) {
                String token = tokens[col];
                if (token == null || token.trim().length() == 0) {
                    token = "";
                }
                token = token.trim();
                // Remove Excel-specific formatting
                if (token.startsWith("\"") && token.endsWith("\"")) {
                    token = token.substring(1,token.length()-1);
                }
                token = token.replaceAll("\"\"", "\"");
                source[col][row] = token;
            }
            for (col = numTokens; col < numCols; ++col) {
                source[col][row] = "";
            }
        } 
        return true;
    }

    /**
     * Convert BufferedReader of lines into Vector of Strings
     * @param br
     * @return
     */
    private boolean convertBufferedReaderToVector(BufferedReader br) {
        String line = null;

        while (true) {
            try {
                line = br.readLine();
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                return false;
            }
            if (line == null) {
                break;
            }
            ++numRows;
            int cols = line.split("\t").length;
            if (cols > numCols) {
                numCols = cols;
            }
            rows.add(line);
        }
        return true;
    }

    /**
     * Convert Excel Workbook to 2 dimensional String array
     * @param filename
     * @return
     */
    private boolean convertWorkbookToArray(String filename) {
        Workbook workbook = null;
        int row = 0;
        int col = 0;
        try {
            workbook = Workbook.getWorkbook(new File(filename));
            if (workbook == null) {
                return false;
            }
            Sheet sheet = workbook.getSheet(0);
            numRows = sheet.getRows();
            numCols = sheet.getColumns();

            source = new String[numCols][numRows];

            for (row = 0; row < numRows; ++row) {
                StringBuffer rowBuffer = new StringBuffer();
                for (col = 0; col < numCols; ++col) {
                    String s = sheet.getCell(col, row).getContents().trim();
                    if (s == null) {
                        s = "";
                    } 
                    source[col][row] = s;
                    rowBuffer.append(s).append("\t");
                }
                rows.add(rowBuffer.toString());
            }
            return true;
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Converting row,col [" + row + "," + col + "]", e);
            return false;
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                
            }
        }
    }

    /**
     * Using Instrument Source, do all validation and load it into the data model
     * @return
     */
    boolean processInstrumentSource() {
        int rowNum = 0;
        int colNum = 0;
        int langNum = 1;
        try {
            ++InstrumentExcelLoader.UseCounter;
            languageCodes = new ArrayList<String>();
            instrumentHeaders = new ArrayList<InstrumentHeader>();
            instrumentContents = new ArrayList<InstrumentContent>();
            languageList = null;
            String languagesString = "en"; // the default;
            

            // process rows one at a time
            for (rowNum = 0; rowNum < numRows; rowNum++) {
                try {
                    String col0 = source[0][rowNum];
                    //process cols
                    colNum = 0;	// must reset to 0 for each row
                    // check to see if it is a header row
                    // if it is we need to get the languages title and sched versions from the appropriate lines
                    if (col0.equals("RESERVED")) {
                        String reservedName = source[1][rowNum];
                        String reservedValue = source[2][rowNum];
                        // check for number of languages
                        // Find ReservedWord index from database and add InstrumentHeader entry
//                        if (reservedName.equals("")) {
//                            log(rowNum, 1, Level.SEVERE, "Empty RESERVED word");
//                            continue;
//                        }
                        ReservedWord reservedWord = null;
                        try {
                            reservedWord = instrumentLoaderFacade.parseReservedWord(reservedName);
                        } catch (InstrumentLoadException ex) {
                            reservedWord = (ReservedWord) ex.getObject();
                            log(rowNum, 1, ex.getLevel(), ex.getMessage());
                        }
                        if (reservedWord != null) {
                            InstrumentHeader instrumentHeader = new InstrumentHeader();
                            instrumentHeader.setReservedWordID(reservedWord);
                            instrumentHeader.setHeaderValue(reservedValue);
                            instrumentHeader.setInstrumentVersionID(instrumentVersion);
                            instrumentHeaders.add(instrumentHeader);
                        } else {
//                            log(rowNum, 1, Level.SEVERE, "Invalid Reserved Word " + reservedName);
                        }

                        if (reservedName.equals("__LANGUAGES__")) {
                            languagesString = reservedValue;
                        } else if (reservedName.equals("__TITLE__")) {
                            this.title = reservedValue;
                        } else if (reservedName.equals("__SCHED_VERSION_MAJOR__")) {
                            this.majorVersion = reservedValue;
                        } else if (reservedName.equals("__SCHED_VERSION_MINOR__")) {
                            this.minorVersion = reservedValue;
                        }
                    } else if (col0.startsWith("COMMENT")) {
                        continue;
                    } else {
                        if (languageList == null) {
                            // First pass
                            StringTokenizer st = new StringTokenizer(languagesString, "|");
                            numLanguages = st.countTokens();
                            for (int l = 0; l < numLanguages; ++l) {
                                String langCode = st.nextToken();
                                if (langCode.length() < 2) {
                                    log(rowNum, 2, Level.SEVERE, "Invalid Language Code " + langCode);
                                //  FIXME - could also call Locale to check whether this is a valid Language Code
//                                Locale temp = new Locale(langCode);
                                } else {
                                    languageCodes.add(langCode.substring(0, 2));
                                }
                            }
                            try {
                                languageList = instrumentLoaderFacade.parseLanguageList(languagesString);
                            } catch (InstrumentLoadException ex) {
                                languageList = (LanguageList) ex.getObject();
                                log(rowNum, 2, ex.getLevel(), ex.getMessage());
                            }
//                            if (languageList == null) {
//                                log(rowNum, 2, Level.SEVERE, "missing or invalid list of languages" + reservedValue);
//                            }                            
                        }
                        // otherwise it is a data row. Extract the data elements from the spreadsheet and build the text file
                        String conceptString = source[0][rowNum];   // CONCEPT in column 0
                        String varNameString = source[1][rowNum];   // VarName in column 1
                        String displayNameString = source[2][rowNum];  // DisplayName in column 2
                        String relevanceString = source[3][rowNum]; // Relevance in column 3
                        String actionTypeString = source[4][rowNum];    //ActionType in column 4
                        String defaultAnswer = null;

                        if (varNameString.equals("")) {
                            log(rowNum, 1, Level.WARNING, "Missing variableName.  Skippping whole row.");
                            continue;
                        }
                        if (actionTypeString.equals("")) {
                            log(rowNum, 4, Level.SEVERE, "Missing actionType");
                        }
                        if (relevanceString.equals("")) {
                            log(rowNum, 3, Level.SEVERE, "Missing relevance");
                        }

                        if (varNameStrings.contains(varNameString)) {
                            log(rowNum, 1, Level.SEVERE, "Already contains variableName " + varNameString);
                        } else {
                            // FIXME - check the variable name, or give a prefix - and confirm that doesn't have embedded disallowed characters - use Regex for valid names (including length?)
                            varNameStrings.add(varNameString);
                        }

                        // Set an InstrumentContent row for this item
                        ++this.numVars;
                        this.varNameMD5source.append(varNameString); // for MD5 hash
                        this.instrumentContentsMD5source.append(relevanceString).append(actionTypeString); // for MD5 hash
                        InstrumentContent instrumentContent = new InstrumentContent();
                        Item item = null;
                        Help help = null;
                        Readback readback = null;
                        AnswerList answerList = null;
                        AnswerListDenormalized answerListDenormalized = null;
                        Question question = null;
                        DisplayType displayType = null;
                        Validation validation = null;
                        String answerListDenormalizedString = null;
                        VarName varName = null;
                        try {
                            varName = instrumentLoaderFacade.parseVarName(varNameString);
                        } catch (InstrumentLoadException ex) {
                            varName = (VarName) ex.getObject();
                            log(rowNum, colNum, ex.getLevel(), ex.getMessage());
                        }
                        String firstAnswerListDenormalizedString = null;
                        String firstQuestionString = null;

                        // NOTE - instrumentContent will always be new (for now) even if all parameters are identical to another instumentContent -- supports editing
                        instrumentContent.setInstrumentVersionID(instrumentVersion);
                        instrumentContent.setVarNameID(varName); // Find the VarName index, creating new one if needed
                        instrumentContent.setItemSequence(numVars); // set it to VarName count for easy sorting
                        instrumentContent.setIsRequired((short) 1); // true
                        instrumentContent.setIsReadOnly((short) 0); // false
                        instrumentContent.setDisplayName(displayNameString);
                        instrumentContent.setRelevance(relevanceString);

                        colNum = 4;	// in case throw errors for this columnn
                        String actionType = parseActionType(rowNum, colNum, actionTypeString);
                        instrumentContent.setItemActionType(actionType);
                        instrumentContent.setGroupNum(parseGroupNum(rowNum, colNum, actionType));
                        instrumentContent.setConcept(conceptString);
                        instrumentContents.add(instrumentContent);

                        if (!actionType.equalsIgnoreCase("e") && !relevanceString.equals("1")) {
                            ++this.numBranches;
                        }

                        // if the number of languages is more than one there will be 4 more columns per language to process
                        // cycle through for the number of languages
                        // There may be more languages listed than actual langauges entered - handle this gracefully

                        boolean hasTailoring = false;
                        boolean isInstruction = false;

                        // FIXME - gracefully handle mismatch between declared # of languages and actual
                        //  NOTE - for each question in this list, they should mean the same thing, so if the QuestionLocalized String is found, it should(?) be reused?
                        for (langNum = 1; langNum <= numLanguages; langNum++) {
                            String readbackString = "";
                            String questionString = "";
                            String responseOptions = "";
                            String helpString = "";

                            if (numCols > (langNum * 4) + 1) {
                                readbackString = source[(langNum * 4) + 1][rowNum]; // is this used in model?
                            }
                            if (numCols > (langNum * 4) + 2) {
                                questionString = source[(langNum * 4) + 2][rowNum]; // action - questionString or evaluation
                                if (questionString.equals("")) {
                                    log(rowNum, (langNum * 4) + 2, Level.FINE, "Missing question");
                                }
                            }
                            if (numCols > (langNum * 4) + 3) {
                                responseOptions = source[(langNum * 4) + 3][rowNum]; // this gets parsed into dataType, displayType, and AnswerLis
                                if (responseOptions.equals("")) {
                                    log(rowNum, (langNum * 4) + 3, Level.SEVERE, "Missing responseOptions");
                                }
                            }
                            if (numCols > (langNum * 4) + 4) {
                                helpString = source[(langNum * 4) + 4][rowNum];
                            }

                            this.instrumentContentsMD5source.append(readbackString).append(questionString).append(responseOptions).append(helpString); // for MD5 hash

                            if (questionString.contains("`") || responseOptions.contains("`")) {
                                hasTailoring = true;
                            }

                            String languageCode = getlanguageCode(langNum - 1);
                            // TODO - CHECK - this should work gracefully even if blank
                            QuestionLocalized questionLocalized = null;
                            try {
                                questionLocalized = instrumentLoaderFacade.parseQuestionLocalized(questionString, languageCode);
                            } catch (InstrumentLoadException ex) {
                                questionLocalized = (QuestionLocalized) ex.getObject();
                                log(rowNum, (langNum * 4) + 2, ex.getLevel(), ex.getMessage());
                            }
                            if (langNum == 1) {
                                question = questionLocalized.getQuestionID();
                                if (question == null) {
                                    // Then none exists, so create a new one, and assign that QuestionLocalized object to it.  This should only occur on first pass
                                    question = new Question();
                                    ArrayList<QuestionLocalized> questionLocalizedCollection = new ArrayList<QuestionLocalized>();
                                    question.setQuestionLocalizedCollection(questionLocalizedCollection);
                                }
                            }
                            question.getQuestionLocalizedCollection().add(questionLocalized);
                            questionLocalized.setQuestionID(question);

                            HelpLocalized helpLocalized = null;
                            try {
                                helpLocalized = instrumentLoaderFacade.parseHelpLocalized(helpString, languageCode);
                            } catch (InstrumentLoadException ex) {
                                helpLocalized = (HelpLocalized) ex.getObject();
                                log(rowNum, (langNum * 4) + 4, ex.getLevel(), ex.getMessage());
                            }
                            if (helpLocalized != null) {
                                if (langNum == 1) {
                                    help = helpLocalized.getHelpID();
                                    if (help == null) {
                                        // Then none exists, so create a new one, and assign that HelpLocalized object to it
                                        help = new Help();
                                        ArrayList<HelpLocalized> helpLocalizedCollection = new ArrayList<HelpLocalized>();
                                        help.setHelpLocalizedCollection(helpLocalizedCollection);
                                        instrumentContent.setHelpID(help);
                                    }
                                }
                                help.getHelpLocalizedCollection().add(helpLocalized);
                                helpLocalized.setHelpID(help);
                            }

                            ReadbackLocalized readbackLocalized = null;
                            try {
                                readbackLocalized = instrumentLoaderFacade.parseReadbackLocalized(readbackString, languageCode);
                            } catch (InstrumentLoadException ex) {
                                readbackLocalized = (ReadbackLocalized) ex.getObject();
                                log(rowNum, (langNum * 4) + 1, ex.getLevel(), ex.getMessage());
                            }
                            if (readbackLocalized != null) {
                                if (langNum == 1) {
                                    readback = readbackLocalized.getReadbackID();
                                    if (readback == null) {
                                        // Then none exists, so create a new one, and assign that ReadbackLocalized object to it
                                        readback = new Readback();
                                        ArrayList<ReadbackLocalized> readbackLocalizedCollection = new ArrayList<ReadbackLocalized>();
                                        readback.setReadbackLocalizedCollection(readbackLocalizedCollection);
                                        instrumentContent.setReadbackID(readback);
                                    }
                                }
                                readback.getReadbackLocalizedCollection().add(readbackLocalized);
                                readbackLocalized.setReadbackID(readback);
                            }

                            // FIXME - this should happen once, not once per language (then check whether there is discrepancy across languages)
                            if (responseOptions.equals("")) {
                                log(rowNum, (langNum * 4) + 3, Level.SEVERE, "Missing DataType");
                            } else {
                                StringTokenizer ans = new StringTokenizer(responseOptions, "|", false);
                                String token = null;
                                try {
                                    token = ans.nextToken();
                                } catch (NoSuchElementException e) {
                                    log(rowNum, (langNum * 4) + 3, Level.SEVERE, "Missing DataType");
                                }
                                try {
                                    displayType = instrumentLoaderFacade.parseDisplayType(token);
                                } catch (InstrumentLoadException ex) {
                                    displayType = (DisplayType) ex.getObject();
                                    log(rowNum, (langNum * 4) + 3, ex.getLevel(), ex.getMessage());
                                }

                                if (displayType.getHasAnswerList()) {
                                    colNum = (langNum * 4) + 3;
                                    if (responseOptions.equals("") || !responseOptions.contains("|")) {
                                        log(rowNum, colNum, Level.SEVERE, "AnswerList is blank");
                                    }
                                    // Must handle missing answerlist gracefully?
                                    answerListDenormalizedString = responseOptions.substring(responseOptions.indexOf("|") + 1);
                                    try {
                                        answerListDenormalized = instrumentLoaderFacade.parseAnswerListDenormalized(answerListDenormalizedString, languageCode);
                                    } catch (InstrumentLoadException ex) {
                                        answerListDenormalized = (AnswerListDenormalized) ex.getObject();
                                        log(rowNum, colNum, ex.getLevel(), ex.getMessage());
                                    }

                                    if (langNum == 1) {
                                        answerList = answerListDenormalized.getAnswerListID();
                                        if (answerList == null) {
                                            answerList = new AnswerList();
                                            answerList.setAnswerListDenormalizedCollection(new ArrayList<AnswerListDenormalized>());
                                        }
                                    }
                                    // FIXME - if there is an existing AnswerList, can I just load it and all of its descendants?
                                    parseAnswerList(rowNum, colNum, answerList, answerListDenormalizedString, languageCode, langNum); // FIXME - ideally will re-use an AnswerList if identical across uses
                                    answerList.getAnswerListDenormalizedCollection().add(answerListDenormalized);
                                    answerListDenormalized.setAnswerListID(answerList);
                                }
                                if (displayType.getDisplayType().equals("nothing")) {
                                    isInstruction = true;
                                }
                            }
                            if (langNum == 1) {
                                // FIXME - this is a cheat to hold onto AnswerListDenormalized and  Question
                                firstAnswerListDenormalizedString = answerListDenormalizedString;
                                firstQuestionString = questionString;
                            }
                        }

                        if (hasTailoring == true) {
                            ++this.numTailorings;
                        }
                        if (isInstruction == true) {
                            ++this.numInstructions;
                        }

                        colNum = 4;
                        validation = parseValidation(rowNum, colNum, actionTypeString);

                        //  find Default answer, if any - third column after end of list of languages
                        colNum = numLanguages * 4 + 4 + 3;
                        defaultAnswer = cell(rowNum, colNum, false);

                        // Set the Item-specific values so can retrieve and re-use similar ones, where possible
                        item = new Item(); // populate it, then test whether an equivalent one already exists
                        item.setHasLOINCcode(Boolean.FALSE); // by default - this could be overridden later
                        item.setLoincNum("");
//                    item.setInstrumentContentCollection(instrumentContents);    // FIXME - is this needed?
                        item.setQuestionID(question);
                        item.setAnswerListID(answerList); // could be null if there is no enumerated list attached
                        item.setItemType(actionType.equalsIgnoreCase("e") ? "Equation" : "Question");
//                        if (displayType == null) {
//                            logger.log(Level.FINE,"displayType is null"); 
//                        }
                        DataType dataType = null;
                        String dataTypeName = "unknown";
                        String displayTypeName = "unknown";
                        if (displayType == null) {
                            displayTypeName = "unknown";
                            log(rowNum,colNum,Level.SEVERE,"Missing or Invalid displayType");
                        } else {
                            dataType = displayType.getDataTypeID();
                            displayTypeName = displayType.getDisplayType();
                        }
                        if (dataType == null) {
                            dataTypeName = "unknown";
                            log(rowNum,colNum,Level.SEVERE,"Missing or Invalid DataType");
                        } else {
                            dataTypeName = dataType.getDataType();
                        }
                        item.setDataTypeID(dataType);
                        item.setValidationID(validation);

                        try {
                            item = instrumentLoaderFacade.findItem(item, firstQuestionString, firstAnswerListDenormalizedString, dataTypeName, instrumentLoaderFacade.lastItemComponentsHadNewContent());
                        // checks whether it alreaady exists, returning prior object, if available
                        } catch (InstrumentLoadException ex) {
                            item = (Item) ex.getObject();
                            log(rowNum, 0, ex.getLevel(), ex.getMessage());
                        }
                        // TODO - CHECK - if an existing item is found, what parameters need to be updated, if any?
                        instrumentContent.setItemID(item);
                        instrumentContent.setFormatMask(validation.getInputMask()); // FIXME - should this be attached to Item?
                        instrumentContent.setIsMessage(displayTypeName.equals("nothing") ? (short) 1 : (short) 0);
                        instrumentContent.setDefaultAnswer(defaultAnswer);
                        instrumentContent.setVarNameID(varName);
                        instrumentContent.setDisplayTypeID(displayType);
                        if (displayType != null) {  // FIXME - shouldn't get here
                            instrumentContent.setSPSSFormat(displayType.getSPSSformat());
                            instrumentContent.setSASInformat(displayType.getSASinformat());
                            instrumentContent.setSASFormat(displayType.getSASformat());
                            instrumentContent.setSPSSLevel(displayType.getSPSSlevel());
                        }
                    // instrumentContent.setDataElementCollection(null);    // FIXME - when, if ever, does this need to be set?
                    // instrumentContent.setItemUsageCollection(null);      // FIXME - when, if ever, deoes this need to be set?
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "", e);
                    log(rowNum, colNum, Level.SEVERE, "Unexpected Error " + e.getMessage());
                }
            } // end for rowNum loop

            // Compute InstrumentHash
            try {
                MessageDigest md5 = MessageDigest.getInstance("SHA-256");
                varListMD5Hash = convertByteArrayToHexString(md5.digest(this.varNameMD5source.toString().getBytes()));
                instrumentMD5Hash = convertByteArrayToHexString(md5.digest(this.instrumentContentsMD5source.toString().getBytes()));
            } catch (Throwable e) {
                log(rowNum, 0, Level.INFO, "Error generating MD5 hash of instrument");
            }

            instrumentHash = new InstrumentHash();
            instrumentHash.setNumBranches(numBranches);
            instrumentHash.setNumTailorings(numTailorings);
            instrumentHash.setNumEquations(numEquations);
            instrumentHash.setNumQuestions(numQuestions);
            instrumentHash.setNumVars(numVars);
            instrumentHash.setNumLanguages(numLanguages);
            instrumentHash.setLanguageListID(languageList);
            instrumentHash.setNumInstructions(numInstructions);
            instrumentHash.setNumGroups(groupNum);  // this will be the highest value for groupNum, so = NumGroups
            instrumentHash.setVarListMD5(varListMD5Hash);
            instrumentHash.setInstrumentMD5(instrumentMD5Hash);

            // Check whether this instrumentHash already exists
            try {
                instrumentHash = instrumentLoaderFacade.parseInstrumentHash(instrumentHash);
            } catch (InstrumentLoadException ex) {
                instrumentHash = (InstrumentHash) ex.getObject();
                log(rowNum, 0, ex.getLevel(), ex.getMessage());
            }
//            instrumentHash.setInstrumentVersionCollection((new ArrayList<InstrumentVersion>()).add(instrumentVersion));
            // Create new Instrument and Instrument Version, if needed.
            // FIXME Throw an error if the Instrument Name and Version both exist
            if (title.equals("")) {
                log(rowNum, 0, Level.SEVERE, "Instrument has no Title");
                return false;
            }

            try {
                instrumentVersion = instrumentLoaderFacade.parseInstrumentVersion(title, majorVersion + "." + minorVersion);    //  FIXME - thrown by DISC
            } catch (InstrumentLoadException ex) {
                instrumentVersion = (InstrumentVersion) ex.getObject();
                log(rowNum, 0, ex.getLevel(), ex.getMessage());
            }
            if (instrumentVersion == null) {
                return false;   // FIXME - when loading old instruments, must support clashing version #s
            }

            instrumentVersion.setInstrumentContentCollection(instrumentContents);
            for (int i = 0; i < instrumentContents.size(); ++i) {
                instrumentContents.get(i).setInstrumentVersionID(instrumentVersion);
            }

//            instrumentVersion.setInstrumentSessionCollection(null); // FIXME - when should this be set?
            instrumentVersion.setInstrumentHeaderCollection(instrumentHeaders);
            for (int i = 0; i < instrumentHeaders.size(); ++i) {
                instrumentHeaders.get(i).setInstrumentVersionID(instrumentVersion);
            }

//            instrumentVersion.setLoincInstrumentRequestCollection(null);    // FIXME - when should this be set?
            instrumentVersion.setInstrumentHashID(instrumentHash);

//            instrumentVersion.setSemanticMappingIQACollection(null);  // FIXME - when should this be set?
            instrumentVersion.setInstrumentVersionFileName(instrumentVersionFilename);

            instrument = instrumentVersion.getInstrumentID();
            instrument.setInstrumentName(title);

//            instrument.setInstrumentSessionCollection(null);  // FIXME - when, if ever, will this be needed?
            ArrayList<InstrumentVersion> instrumentVersionCollection = new ArrayList<InstrumentVersion>();
            instrumentVersionCollection.add(instrumentVersion);
            instrument.setInstrumentVersionCollection(instrumentVersionCollection);

            for (int i = 0; i < instrumentLoadErrors.size(); ++i) {
                instrumentLoadErrors.get(i).setInstrumentVersionID(instrumentVersion);
            }
            instrumentVersion.setInstrumentLoadErrorCollection(instrumentLoadErrors);
            
            // add the source content for reference - this can also replace the need for a source file
            /* This seems to add considerable overhead at questionable cost.  Instead, trying to create one CLOB with full contents
            ArrayList sourceContent = new ArrayList<SourceContent>();
            for (int i=0;i<numRows;++i) {
                for (int j=0;j<numCols;++j) {
                    String val = cell(i,j,false);
                    if (val.trim().length() > 0) {  // to make it a sparse database
                        sourceContent.add(new SourceContent(i,j,cell(i,j,false),instrumentVersion));
                    }
                }
            }
            instrumentVersion.setSourceContentCollection(sourceContent);
             */
            instrumentVersion.setNumRows(numRows);
            instrumentVersion.setNumCols(numCols);
           
            StringBuffer sourceBuffer = new StringBuffer();
            Iterator<String> rowIterator = rows.iterator();
            while (rowIterator.hasNext()) {
                sourceBuffer.append(rowIterator.next()).append("\n");
            }
            instrumentVersion.setInstrumentAsSpreadsheetContents(sourceBuffer.toString());

            // Store it to database
            boolean result = false;
            instrumentLoaderFacade.merge(instrument);

            result = true;

            /* TODO - add this back in after debug size problem
            ApelonDTSExporter apelonDTSexport = new ApelonDTSExporter(instrumentVersion, "Instruments");
            String apelonFile = DIALOGIX_SCHEDULES_DIR + "InstVer_" +
            instrumentVersion.getInstrumentVersionID() + "_apelon.xml";
            try {
            BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(
            new FileOutputStream(apelonFile), "UTF-8"));
            out.write(apelonDTSexport.getNamespace().toString());
            out.close();
            } catch (Exception e) {
            logger.log(Level.SEVERE, apelonFile, e);
            }
             */

            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
            log(rowNum, colNum, Level.SEVERE, "Unexpected Error " + e.getMessage());
        }
        return false;
    }

    /**
     * Utility function to convert MD5 hashes into Hex Strings
     * @param bytes
     * @return
     */
    private String convertByteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            int val = Byte.valueOf(bytes[i]).intValue();
            if (val < 0) {
                val = Byte.MAX_VALUE * 2 + 2 + val;
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    /**
     * Parse validation criteria
     * @param rowNum
     * @param colNum
     * @param token
     * @return
     */
    Validation parseValidation(int rowNum,
                               int colNum,
                               String token) {
        String castTo = null;
        String minVal = null;
        String maxVal = null;
        String inputMask = null;
        String otherVals = null;
        DataType dataType = null;

        if (token != null) {
            StringTokenizer st = new StringTokenizer(token, ";");
            if (st.hasMoreTokens()) {
                st.nextToken(); // discard it -- it is the ActionType
            }
            if (st.hasMoreTokens()) {
                castTo = st.nextToken();
                if (!(castTo == null || castTo.trim().length() == 0)) {
                    try {
                        dataType = instrumentLoaderFacade.parseDataType(castTo);
                    } catch (InstrumentLoadException ex) {
                        dataType = (DataType) ex.getObject();
                        log(rowNum, colNum, ex.getLevel(), "Invalid CastTo Datatype" + castTo);
                    }
                }
            }
            if (st.hasMoreTokens()) {
                minVal = st.nextToken();
            }
            if (st.hasMoreTokens()) {
                maxVal = st.nextToken();
            }
            if (st.hasMoreTokens()) {
                inputMask = st.nextToken();
                validateFormatMask(rowNum, colNum, inputMask);  // N.B.  This might also be a Regex mask, starting with "Perl5"
            }
            if (st.hasMoreTokens()) {
                StringBuffer sb = new StringBuffer(st.nextToken());
                while (st.hasMoreTokens()) {
                    sb.append(";").append(st.nextToken());
                }
                otherVals = sb.toString();
            }
        }
        // now that Validation is populated, test whether it already exists
        Validation validation = null;
        try {
            validation = instrumentLoaderFacade.parseValidation(dataType, minVal, maxVal, inputMask, otherVals);
        } catch (InstrumentLoadException ex) {
            validation = (Validation) ex.getObject();
            log(rowNum, colNum, ex.getLevel(), ex.getMessage());
        }
        return validation;
    }

    /**
     * Return ActionType, which is one of {q, e, [, ]}
     * @param rowNum
     * @param colNum
     * @param token
     * @return
     */
    String parseActionType(int rowNum,
                           int colNum,
                           String token) {
        if (token == null || token.trim().length() == 0) {
            log(rowNum, colNum, Level.SEVERE, "ActionType is blank");
            return null;
        }
        String actionType = token.substring(0, 1);
        if (actionType.equalsIgnoreCase("q") || actionType.equalsIgnoreCase("[") || actionType.equalsIgnoreCase("]")) {
            ++this.numQuestions;
            return actionType.toLowerCase();
        } else if (actionType.equalsIgnoreCase("e")) {
            ++this.numEquations;
            return actionType.toLowerCase();
        } else {
            log(rowNum, colNum, Level.SEVERE, "Invalid ActionType " + token);
            return null;
        }
    }

    /**
     * Validates Format Mask, if it is Perl
     * @param rowNum
     * @param colNum
     * @param token
     * @return
     */
    void validateFormatMask(int rowNum,
                            int colNum,
                            String token) {
        if (token == null || token.trim().length() == 0 || !token.startsWith("PERL5")) {
            return;
        }
        String regex = token.substring("PERL5".length());
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException ex) {
            log(rowNum, colNum, Level.SEVERE, "Invalid Perl Regular Expression Formatting Mask" + token + ex.getMessage());
        }
    }

    /**
     * Determine the GroupNum based upon item grouping paremeters (actionType)
     * @param rowNum
     * @param colNum
     * @param actionType
     * @return
     */
    int parseGroupNum(int rowNum,
                      int colNum,
                      String actionType) {
        if (actionType == null) {
            log(rowNum, colNum, Level.SEVERE, "No actionType specified");
        } else if (actionType.equals("[")) {
            if (withinBlock == true) {
                log(rowNum, colNum, Level.SEVERE, "Trying to create a nested group of items");
            } else {
                withinBlock = true;
                ++groupNum;
            }
        } else if (actionType.equals("]")) {
            if (withinBlock == false) {
                log(rowNum, colNum, Level.SEVERE, "Trying to close a group of items with no matching '['");
            } else {
                withinBlock = false;
            }
        } else if (actionType.equals("q")) {
            if (withinBlock == false) {
                ++groupNum;
            }
        } else if (actionType.equals("e")) {
            if (withinBlock == true) {
                log(rowNum, colNum, Level.SEVERE, "Trying to process an equation within a group of items");
            }
        }
        return groupNum;
    }

    /**
     * Parse the answerList parameter to get both the AnswerList, and the dataType
     * @param rowNum
     * @param colNum
     * @param answerList
     * @param responseOptions - the source string for the answers
     * @param languageCode - soruce language for these answers
     * @param languageCounter - how many languages have been processed for this item [1-n]
     */
    void parseAnswerList(int rowNum,
                         int colNum,
                         AnswerList answerList,
                         String responseOptions,
                         String languageCode,
                         int languageCounter) {
        if (responseOptions == null || responseOptions.trim().length() == 0) {
            log(rowNum, colNum, Level.SEVERE, "AnswerList is blank");
        }
        StringTokenizer ans = new StringTokenizer(responseOptions, "|", true); // return '|' tokens too
        String val = null;
        String msg = null;
        int field = 1;
        int ansPos = 0;
        ArrayList<AnswerListContent> answerListContents = new ArrayList();
        if (answerList.getAnswerListContentCollection() == null) {
            answerListContents = new ArrayList<AnswerListContent>();
            answerList.setAnswerListContentCollection(answerListContents);
        } else {
            answerListContents.addAll(answerList.getAnswerListContentCollection());
        }
        AnswerListContent answerListContent = null;

        while (ans.hasMoreTokens()) {
            String s = null;
            s = ans.nextToken().trim();

            if ("|".equals(s)) {
                ++field;
                continue;
            }
            switch (field) {
                default:
                    log(rowNum, colNum, Level.SEVERE, "Should never get here when parsing AnswerList");
                    break;
                case 1:
                    val = s; // this is the (usually numeric) value associated with the message
                    break;
                case 2:
                    msg = s; // This is the Answer String
                    field = 0; // so that cycle between val & msg;
                    ++ansPos;

                    if (msg.equals("")) {
                        log(rowNum, colNum, Level.SEVERE, "Missing Answer at position " + ansPos + " within " + responseOptions);
                    }

                    if (languageCounter == 1) {
                        answerListContent = new AnswerListContent();
                        answerListContent.setAnswerOrder(ansPos);
                        answerListContent.setAnswerCode(val);
                        answerListContent.setAnswerListID(answerList);

                        // Should handle this gracefully?
                        AnswerLocalized answerLocalized = null;
                        try {
                            answerLocalized = instrumentLoaderFacade.parseAnswerLocalized(msg, languageCode);
                        } catch (InstrumentLoadException ex) {
                            answerLocalized = (AnswerLocalized) ex.getObject();
                            log(rowNum, colNum, ex.getLevel(), ex.getMessage());
                        }
                        Answer answer = answerLocalized.getAnswerID();
                        if (answer == null) {
                            answer = new Answer();
                            answer.setHasLAcode(Boolean.FALSE);
                            answer.setLAcode(null);
                            answerLocalized.setAnswerID(answer);
                            answer.setAnswerLocalizedCollection(new ArrayList<AnswerLocalized>());
                        }
                        answerListContent.setAnswerID(answer);
                        answer.getAnswerLocalizedCollection().add(answerLocalized); // TODO - CHECK - is this duplicative?
                    } else {
                        // This is a secondary language.  The same position should be the same Answer, thus linked to the same AnswerID for that AnswerListContent's position
                        if (answerListContents.size() < ansPos) {
                            // suggests that there are too many answers in this languageCounter?
                            log(rowNum, colNum, Level.SEVERE, "Language # " + languageCounter + " has more answer choices than prior languages");
                        // Add it anyway?
                        } else {
                            // Compare values from this language vs. those set for prior language -- FIXME - add more details
                            answerListContent = answerListContents.get(ansPos - 1);
                            if (!answerListContent.getAnswerCode().equals(val)) {
                                log(rowNum, colNum, Level.SEVERE, "Mismatch across languages - Position " + (ansPos - 1) + ": " + answerListContent.getAnswerCode() + " =>" + val);
                            }
                            Answer answer = answerListContent.getAnswerID();    // must be set by now, since secondary language
                            AnswerLocalized answerLocalized = null;
                            try {
                                answerLocalized = instrumentLoaderFacade.parseAnswerLocalized(msg, languageCode);   // never returns null
                            } catch (InstrumentLoadException ex) {
                                answerLocalized = (AnswerLocalized) ex.getObject();
                                log(rowNum, colNum, ex.getLevel(), ex.getMessage());
                            }
                            Answer answer2 = answerLocalized.getAnswerID(); // if this already has an AnswerObject set, then this AnswerLocalized has been used elsewhere - potential class across AnswerIDs

                            // FIXME - this is happening a lot - is it a data modeling problem? - YES, something is wrong with this
                            if (answer2 != null && !answer2.equals(answer)) {
                                log(rowNum, colNum, Level.FINE, "Answer " + msg + " already has AnswerID " + answer2.getAnswerID() + " but being reset to " + answer.getAnswerID());
                            }
                            answer.getAnswerLocalizedCollection().add(answerLocalized);
                            answerLocalized.setAnswerID(answer);
                        }
                    }
                    answerListContents.add(answerListContent);

                    val = null;
                    msg = null;
                    break;
            }
        }
        if (answerListContents.size() == 0) {
            log(rowNum, colNum, Level.SEVERE, "Missing Answer List");
        }
        if (field == 1) {
            log(rowNum, colNum, Level.SEVERE, "Missing Answer Message at position " + ansPos);
        }
    }

    /**
     * Return 2 char language code from index of loaded languages
     * @param i
     * @return
     */
    private String getlanguageCode(int i) {
        if (i < 0 || i >= languageCodes.size()) {
            return "en";
        } else {
            return languageCodes.get(i).substring(0, 2);
        }
    }

    /**
     * 
     * @return
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 
     * @return
     */
    public boolean getDatabaseStatus() {
        return this.databaseStatus;
    }

    public boolean getVersionFileStatus() {
        return this.versionFileStatus;
    }

    /**
     * Get the URL fragment needed to launch the instrument from its filename
     * @return
     */
    public String getLaunchCommand() {
        if (getVersionFileStatus() == false) {
            return "";
        }
        return "servlet/Dialogix?schedule=" + instrumentVersionFilename + "&DIRECTIVE=START";
    }

    /**
     * Does the instrument have load errors?
     * @return
     */
    public boolean hasInstrumentLoadErrors() {
        return (instrumentLoadErrorCounter > 0);
    }

    /**
     * Get load errors, if any
     * @return
     */
    public ArrayList<InstrumentLoadError> getInstrumentLoadErrors() {
        return instrumentLoadErrors;
    }

    /**
     * Initialize the EJB Session Bean
     * @return
     */
    private InstrumentLoaderFacadeLocal lookupInstrumentLoaderFacadeLocal() {
        try {
            Context c = new InitialContext();
            InstrumentLoaderFacadeLocal _instrumentLoaderFacade = (InstrumentLoaderFacadeLocal) c.lookup("java:comp/env/InstrumentLoaderFacade_ejbref");
            _instrumentLoaderFacade.init();
            return _instrumentLoaderFacade;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
            return null;
        }
    }

    /**
     * Log load errors for later use
     * @param rowNum
     * @param colNum
     * @param level
     * @param message
     */
    private void log(int rowNum,
                      int colNum,
                      Level level,
                      String message) {
        String cell = cell(rowNum, colNum, false);
        if (cell == null) {
            cell = "";
        }
        StringBuffer sb = new StringBuffer("Err(");
        sb.append(++instrumentLoadMessageCounter).append(")");
        sb.append("[").append(rowNum + 1).append(",").append(colNum + 1).append("] ");
        sb.append(message);
//        append(" [").append(cell).append("]");
        logger.log(level, sb.toString());
        instrumentLoadErrors.add(new InstrumentLoadError(rowNum, colNum, level.intValue(), message, cell));
        if (level.equals(Level.SEVERE)) {
            ++instrumentLoadErrorCounter;
        }
    }

    /**
     * Format load errors as an  HTML table
     * TODO - eventually replace this with a view which iterates over the InstrumentLoadError collection
     * @return
     */
    public String getLoadErrorsAsHtmlTable() {
        if (instrumentLoadErrors.size() == 0) {
            return "No Errors Found";
        }
        Collections.sort(instrumentLoadErrors, new InstrumentLoadErrorComparator());
        Iterator<InstrumentLoadError> iterator = instrumentLoadErrors.iterator();
        StringBuffer sb = new StringBuffer("<table border='1'>");
        sb.append("<tr><td>Row");
        for (int c = 1; c < numCols; ++c) {
            sb.append("</td><td>Col").append(c + 1).append(": ");
            int c2 = c;
            while (c2 > 8) {
                c2 = c2 - 4;
            }
            switch (c2) {
                case 0:
                    sb.append("Concept");
                    break;
                case 1:
                    sb.append("VarName");
                    break;
                case 2:
                    sb.append("DisplayName");
                    break;
                case 3:
                    sb.append("Relevance");
                    break;
                case 4:
                    sb.append("ActionType + Validation");
                    break;
                case 5:
                    sb.append("Readback");
                    break;
                case 6:
                    sb.append("Question or Equation");
                    break;
                case 7:
                    sb.append("DataType + AnswerList");
                    break;
                case 8:
                    sb.append("HelpURL");
                    break;
            }
        }

        // Show errors in an array which looks like instrument
        int lastRow = -1;
        int lastCol = -1;
        int thisRow = 0;
        int thisCol = 0;
        while (iterator.hasNext()) {
            InstrumentLoadError error = iterator.next();
            if (error.getLogLevel() < Level.WARNING.intValue()) {
                continue;
            }
            thisRow = error.getSourceRow();
            thisCol = error.getSourceColumn();
            if (thisRow != lastRow) {
                // Finish off previous line, if needed
                if (lastRow != -1) {
                    for (int c = lastCol + 1; c < numCols; ++c) {
                        sb.append("</td><td>").append(cell(lastRow, c, true));
                    }
                }
                lastRow = thisRow;
                lastCol = -1;
                sb.append("</td></tr><tr><td>").append(thisRow + 1);
            }
            if (thisCol == lastCol) {
                // another error message for this cell
                sb.append("<br><font color='");
                if (error.getLogLevel().equals(Level.SEVERE.intValue())) {
                    sb.append("red");
                } else {
                    sb.append("blue");
                }
                sb.append("'>").append(error.getErrorMessage()).append("</font>");
            }
            if (thisCol > lastCol) {
                // first new error message for this column
                if (lastCol == -1) {
                    lastCol = 0;    // so don't have indexOutOfBounds
                }
                for (int c = lastCol + 1; c < thisCol; ++c) {
                    sb.append("</td><td>").append(cell(thisRow, c, true)).append("</td>");
                }
                sb.append("<td>").append(cell(thisRow, thisCol, true));
                sb.append("<br><font color='red'>").append(error.getErrorMessage()).append("</font>");
                lastCol = thisCol;
            }
        }
        sb.append("</td></tr></table>");
        return sb.toString();
    }

    /**
     * Helper function to get HTML-formatted version of source contents
     * @param row
     * @param col
     * @return
     */
    private String cell(int row,
                         int col,
                         boolean convertSpaceToHtml) {
        String str;
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            str = "";
        } else {
            str = source[col][row];
        }
        if (convertSpaceToHtml == true && str.length() == 0) {
            return "&nbsp;";
        } else {
            return str;
        }
    }
    
    private String createInstrumentVersionFilename(String path, String base) {
        if (!DB_WRITE_SYSTEM_FILES) {
            return "";
        }
        try {
            File file = new File(path + base + ".txt");
            File dir = new File(path);
            if (file.exists()) {
                // then don't want to overwrite it
                File newFile = File.createTempFile(base + "_", ".txt", dir);
                return newFile.getCanonicalPath();
            }
            return path + base + ".txt";
        }
        catch (IOException e) {
            logger.log(Level.SEVERE,e.getMessage());
            return "";
        }
    }

    boolean writeInstrumentArrayToFile() {
        if (!DB_WRITE_SYSTEM_FILES) {
            return true;
        }        
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(instrumentVersionFilename), "UTF-16"));
            for (int row = 0; row < numRows; ++row) {
                for (int col = 0; col < numCols; ++col) {
                    if (col > 0) {
                        out.write("\t");
                    }
                    out.write(cell(row, col, false));
                }
                out.write("\n");
            }
            out.close();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, instrumentVersionFilename, e);
            return false;
        }
    }
}
