/* ********************************************************
 ** Copyright (c) 2000-2007, Thomas Maxwell White, all rights reserved.
 ** $Header$
 ******************************************************** */

package org.dianexus.triceps;

import jxl.*;
import java.io.*;
import java.util.*;
import org.dialogix.entities.*;
import javax.persistence.*;
import java.security.*;
import org.dianexus.triceps.modules.data.InstrumentSessionDataJPA;
import org.apache.log4j.Logger;

/**
This class loads instruments from Excel files:
(1) Save as Unicode Text to needed directory
(2) Create horizontal database table, if needed
(3) Load everything into the full data model, enforcing uniqueness constraints
 */
public class InstrumentExcelLoader implements java.io.Serializable {

    static Logger logger = Logger.getLogger(InstrumentExcelLoader.class);
    private static int UseCounter = 0;
    private static final String DIALOGIX_SCHEDULES_DIR = "@@DIALOGIX.SCHEDULES.DIR@@";
    private StringBuffer instrumentAsText = null;
    private int numCols = 0;
    private int numRows = 0;
    private int numLanguages = 0;
    private ArrayList<String> languageCodes = null;
    private ArrayList<InstrumentHeader> instrumentHeaders = null;
    private ArrayList<InstrumentContent> instrumentContents = null;
    private String majorVersion = "1";
    private String minorVersion = "0";
    private boolean status = false;
    private String title = null;
    private EntityManagerFactory emf = null;
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
//    private ArrayList<String> errorList = new ArrayList<String>();  // FIXME - record errors  and success with reference to Excel table for easier correction (row/column)

    /**
    Upload instrument
    @param filename The absolute path of the filename
    @return	true if succeeds
     */
    public InstrumentExcelLoader() {
    }

    /**
    This is the main method for loading an Excel file
    @param filename  the full name of the Excel file
    @return true if everything succeeds
     */
    public boolean loadInstrument(String filename) {
        if (filename == null || "".equals(filename.trim())) {
            this.status = false;
        }

        DialogixConstants.init();

        justFileName = filename.substring(filename.lastIndexOf(File.separatorChar) + 1);
        varNameStrings = new ArrayList<String>();
        varNameMD5source = new StringBuffer();
        instrumentContentsMD5source = new StringBuffer();

        logger.info("Importing '" + justFileName + "' from '" + filename + "'");

        Workbook workbook = retrieveExcelWorkbook(filename);
        if (workbook != null && processWorkbook(workbook)) {
            this.status = writeFile(instrumentVersion.getInstrumentVersionFileName());
        } else {
            this.status = false;
        }
        return this.status;
    }

    /**
    Load the Excel file into the Java Workbook structures as needed for further processing
    @param  filename - the full name of the Excel file
    @return the Workbook
     */
    Workbook retrieveExcelWorkbook(String filename) {
        try {
            Workbook workbook = Workbook.getWorkbook(new File(filename));
            return workbook;
        } catch (Throwable e) {
            logger.error("", e);
        }
        return null;
    }

    /* Process and  Excel file, doing the following:
    (1) Save it as  Unicode .txt to the target directory
    (2) Populate the Dialogix data model with the instrument contents
    (3) Create the needed horizontal tables
    @param workbook the loaded Excel file
    @return true if everything succeeds
     */
    boolean processWorkbook(Workbook workbook) {
        try {
            ++InstrumentExcelLoader.UseCounter;
            languageCodes = new ArrayList<String>();
            instrumentHeaders = new ArrayList<InstrumentHeader>();
            instrumentContents = new ArrayList<InstrumentContent>();
            languageList = new LanguageList();

            instrumentAsText = new StringBuffer();

            Sheet sheet = workbook.getSheet(0);

            this.numCols = sheet.getColumns();
            this.numRows = sheet.getRows();

            // process rows one at a time
            // FIXME - will want to report errors based upon row and column within Excel so can annotate and return marked-up Excel showing errors
            for (int i = 0; i < numRows; i++) {
                //process cols
                Cell cell = sheet.getCell(0, i);
                // check to see if it is a header row
                // if it is we need to get the languages title and sched versions from the appropriate lines
                if (cell.getContents().equals("RESERVED")) {
                    Cell reservedName = sheet.getCell(1, i);
                    Cell reservedValue = sheet.getCell(2, i);
                    instrumentAsText.append(cell.getContents() + "\t" + reservedName.getContents() + "\t" + reservedValue.getContents() + "\n");
                    // check for number of languages
                    // Find ReservedWord index from database and add InstrumentHeader entry
                    ReservedWord reservedWord = DialogixConstants.parseReservedWord(reservedName.getContents());
                    if (reservedWord != null) {
                        InstrumentHeader instrumentHeader = new InstrumentHeader();
                        instrumentHeader.setReservedWordID(reservedWord);
                        instrumentHeader.setValue(reservedValue.getContents());
                        instrumentHeader.setInstrumentVersionID(instrumentVersion);
                        instrumentHeaders.add(instrumentHeader);
                        // otherwise, report error and don't add it to list
                    }

                    if (reservedName.getContents().equals("__LANGUAGES__")) {
                        StringTokenizer st = new StringTokenizer((String) reservedValue.getContents(), "|");
                        numLanguages = st.countTokens();
                        for (int l = 0; l < numLanguages; ++l) {
                            String langCode = st.nextToken();
                            if (langCode.length() < 2) {
                                logger.error("Invalid Language Code " + langCode);
                                //  FIXME - could also call Locale to check whether this is a valid Language Code
                            } else {
                                languageCodes.add(langCode.substring(0, 2));
                            }
                        }
                        languageList = DialogixConstants.parseLanguageList(reservedValue.getContents());
                    } else if (reservedName.getContents().equals("__TITLE__")) {
                        this.title = (String) reservedValue.getContents();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MAJOR__")) {
                        this.majorVersion = reservedValue.getContents();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MINOR__")) {
                        this.minorVersion = reservedValue.getContents();
                    }
                } else if (cell.getContents().startsWith("COMMENT")) {
                    // ignore comments, but keep of row count
                    instrumentAsText.append(cell.getContents());
                    for (int m = 1; m < numCols; m++) {
                        Cell myCell = sheet.getCell(m, i);
                        instrumentAsText.append("\t").append(myCell.getContents());
                    }
                    instrumentAsText.append("\n");
                } else {
                    // otherwise it is a data row. Extract the data elements from the spreadsheet and build the text file
                    String conceptString = sheet.getCell(0, i).getContents();
                    String varNameString = sheet.getCell(1, i).getContents();
                    String displayNameString = sheet.getCell(2, i).getContents();
                    String relevanceString = sheet.getCell(3, i).getContents();
                    String actionTypeString = sheet.getCell(4, i).getContents();
                    String defaultAnswer = null;

                    if (varNameStrings.contains(varNameString)) {
                        logger.error("Already contains variableName " + varNameString);
                        // continue;   // FIXME skip this row? - no, needed for Excel, but not for database
                    } else {
                        // FIXME - check the variable name, or give a prefix - and confirm that doesn't have embedded disallowed characters
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
                    VarName varName = DialogixConstants.parseVarName(varNameString);
                    String firstAnswerListDenormalizedString = null;
                    String firstQuestionString = null;

                    // NOTE - instrumentContent will always be new (for now) even if all parameters are identical to another instumentContent -- supports editing
                    instrumentContent.setInstrumentVersionID(instrumentVersion);
                    instrumentContent.setVarNameID(varName); // Find the VarName index, creating new one if needed
                    instrumentContent.setItemSequence(numVars); // set it to VarName count for easy sorting
                    instrumentContent.setIsRequired((short) 1); // true
                    instrumentContent.setIsReadOnly((short) 0); // false
                    instrumentContent.setDisplayName(displayNameString);
                    instrumentContent.setRelevance(relevanceString.trim());

                    String actionType = parseActionType(actionTypeString);
                    instrumentContent.setItemActionType(actionType);
                    instrumentContent.setGroupNum(parseGroupNum(actionType));
                    instrumentContent.setConcept(conceptString);
                    instrumentContents.add(instrumentContent);

                    if (!actionType.equalsIgnoreCase("e") && !relevanceString.trim().equals("1")) {
                        ++this.numBranches;
                    }

                    // if the number of languages is more than one there will be 4 more columns per language to process
                    // cycle through for the number of languages
                    // There may be more languages listed than actual langauges entered - handle this gracefully
                    ArrayList<String> langCols = new ArrayList<String>();

                    boolean hasTailoring = false;
                    boolean isInstruction = false;

                    // FIXME - get default answer
                    // FIXME - gracefully handle mismatch between declared # of languages and actual
                    //  NOTE - for each question in this list, they should mean the same thing, so if the QuestionLocalized String is found, it should(?) be reused?
                    for (int j = 1; j <= numLanguages; j++) {
                        String readbackString = "";
                        String questionString = "";
                        String responseOptions = "";
                        String helpString = "";

                        if (numCols > (j * 4) + 1) {
                            readbackString = sheet.getCell((j * 4) + 1, i).getContents(); // is this used in model?
                        }
                        if (numCols > (j * 4) + 2) {
                            questionString = sheet.getCell((j * 4) + 2, i).getContents(); // action - questionString or evaluation
                        }
                        if (numCols > (j * 4) + 3) {
                            responseOptions = sheet.getCell((j * 4) + 3, i).getContents(); // this gets parsed into dataType, displayType, and AnswerLis
                        }
                        if (numCols > (j * 4) + 4) {
                            helpString = sheet.getCell((j * 4) + 4, i).getContents();
                        }

                        this.instrumentContentsMD5source.append(readbackString).append(questionString).append(responseOptions).append(helpString); // for MD5 hash
                        // Save them to flat file
                        langCols.add(readbackString);
                        langCols.add(questionString);
                        langCols.add(responseOptions);
                        langCols.add(helpString);

                        if (questionString.contains("`") || responseOptions.contains("`")) {
                            hasTailoring = true;
                        }

                        String languageCode = getlanguageCode(j - 1);
                        QuestionLocalized questionLocalized = DialogixConstants.parseQuestionLocalized(questionString, languageCode);
                        if (j == 1) {
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

                        HelpLocalized helpLocalized = DialogixConstants.parseHelpLocalized(helpString, languageCode);
                        if (helpLocalized != null) {
                            if (j == 1) {
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

                        ReadbackLocalized readbackLocalized = DialogixConstants.parseReadbackLocalized(readbackString, languageCode);
                        if (readbackLocalized != null) {
                            if (j == 1) {
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
                        StringTokenizer ans = new StringTokenizer(responseOptions, "|", false);
                        String token = null;
                        try {
                            token = ans.nextToken();
                        } catch (NoSuchElementException e) {
                            logger.error("Missing Datatype", e);
                        }
                        displayType = DialogixConstants.parseDisplayType(token);

                        if (displayType.getHasAnswerList()) {
                            if (responseOptions == null || responseOptions.trim().length() == 0 || !responseOptions.contains("|")) {
                                logger.error("AnswerList is blank");
                            }
                            answerListDenormalizedString = responseOptions.substring(responseOptions.indexOf("|") + 1); // does this give there to end?
                            answerListDenormalized = DialogixConstants.parseAnswerListDenormalized(answerListDenormalizedString, languageCode);

                            if (j == 1) {
                                answerList = answerListDenormalized.getAnswerListID();
                                if (answerList == null) {
                                    answerList = new AnswerList();
                                    answerList.setAnswerListDenormalizedCollection(new ArrayList<AnswerListDenormalized>());
                                }
                            }
                            // FIXME - if there is an existing AnswerList, can I just load it and all of its descendants?
                            parseAnswerList(answerList, answerListDenormalizedString, languageCode, j); // FIXME - ideally will re-use an AnswerList if identical across uses
                            answerList.getAnswerListDenormalizedCollection().add(answerListDenormalized);
                            answerListDenormalized.setAnswerListID(answerList);
                        }
                        if (displayType.getDisplayType().equals("nothing")) {
                            isInstruction = true;
                        }

                        if (j == 1) {
                            // FIXME - this is a cheat to hold onto AnswerListDenormalized and  Question
                            firstAnswerListDenormalizedString = answerListDenormalizedString;
                            firstQuestionString = questionString;
                        }
                    }
                    // TODO - Check whether this works for multiple languages
                    instrumentAsText.append(conceptString).append("\t").append(varNameString).append("\t").append(displayNameString).append("\t").append(relevanceString).append("\t").append(actionTypeString);

                    for (int k = 0; k < langCols.size(); k++) {
                        instrumentAsText.append("\t").append(langCols.get(k));
                    }
                    instrumentAsText.append("\n");

                    if (hasTailoring == true) {
                        ++this.numTailorings;
                    }
                    if (isInstruction == true) {
                        ++this.numInstructions;
                    }

                    validation = parseValidation(actionTypeString);

                    // Set the Item-specific values so can retrieve and re-use similar ones, where possible
                    item = new Item(); // populate it, then test whether an equivalent one already exists
                    item.setHasLOINCcode(Boolean.FALSE); // by default - this could be overridden later
                    item.setLoincNum("LoincNum");
//                    item.setInstrumentContentCollection(instrumentContents);    // FIXME - is this needed?
                    item.setQuestionID(question);
                    item.setAnswerListID(answerList); // could be null if there is no enumerated list attached
                    item.setItemType(actionType.equalsIgnoreCase("e") ? "Equation" : "Question");
                    item.setDataTypeID(displayType.getDataTypeID());
                    item.setValidationID(validation);

                    item = DialogixConstants.findItem(item, firstQuestionString, firstAnswerListDenormalizedString, displayType.getDataTypeID().getDataType(), DialogixConstants.lastItemComponentsHadNewContent()); // checks whether it alreaady exists, returning prior object, if available
                    // CHECK - if an existing item is found, what parameters need to be updated, if any?
                    instrumentContent.setItemID(item);
                    instrumentContent.setFormatMask(validation.getInputMask()); // FIXME - should this be attached to Item?
                    instrumentContent.setIsMessage(displayType.getDisplayType().equals("nothing") ? (short) 1 : (short) 0);
                    instrumentContent.setDefaultAnswer(defaultAnswer); // FIXME - settable after all language-specific columns are loaded
                    instrumentContent.setVarNameID(varName);
                    instrumentContent.setDisplayTypeID(displayType);
                    instrumentContent.setSPSSformat(displayType.getSPSSformat());
                    instrumentContent.setSASinformat(displayType.getSASinformat());
                    instrumentContent.setSASformat(displayType.getSASformat());
                    // instrumentContent.setDataElementCollection(null);    // FIXME - when, if ever, does this need to be set?
                    // instrumentContent.setItemUsageCollection(null);      // FIXME - when, if ever, deoes this need to be set?
                }
            } // end for i loop
            workbook.close();

            // Compute InstrumentHash
            instrumentHash = new InstrumentHash();
            instrumentHash.setNumBranches(numBranches);
            instrumentHash.setNumTailorings(numTailorings);
            instrumentHash.setNumEquations(numEquations);
            instrumentHash.setNumQuestions(numQuestions);
            instrumentHash.setNumVars(numVars);
            instrumentHash.setNumLanguages(this.numLanguages);
            instrumentHash.setLanguageListID(languageList);
            instrumentHash.setNumInstructions(numInstructions);

            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                instrumentHash.setVarListMD5(md5.digest(this.varNameMD5source.toString().getBytes()).toString());
                instrumentHash.setInstrumentMD5(md5.digest(this.instrumentContentsMD5source.toString().getBytes()).toString());
            } catch (Throwable e) {
                logger.error("", e);
            }
//            instrumentHash.setInstrumentVersionCollection((new ArrayList<InstrumentVersion>()).add(instrumentVersion));
            // Create new Instrument and Instrument Version, if needed.
            // FIXME Throw an error if the Instrument Name and Version both exist
            instrumentVersion = DialogixConstants.parseInstrumentVersion(title, majorVersion + "." + minorVersion);

            if (instrumentVersion == null) {
                return false;
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
            instrumentVersion.setInstrumentVersionFileName(DIALOGIX_SCHEDULES_DIR + justFileName + "_" + InstrumentExcelLoader.UseCounter + ".txt");

            instrument = instrumentVersion.getInstrumentID();
            instrument.setInstrumentName(title);

//            instrument.setInstrumentSessionCollection(null);  // FIXME - when, if ever, will this be needed?
            ArrayList<InstrumentVersion> instrumentVersionCollection = new ArrayList<InstrumentVersion>();
            instrumentVersionCollection.add(instrumentVersion);
            instrument.setInstrumentVersionCollection(instrumentVersionCollection);

            // Store it to database
            boolean result = false;
            try {
                DialogixConstants.merge(instrument); 
                
                // Now create the Horizontal table
                InstrumentSessionDataJPA horizontalTable = new InstrumentSessionDataJPA();
                horizontalTable.create(instrumentVersion.getInstrumentVersionID(), varNameStrings);
                
                result = true;
            } catch (Throwable e) {
                logger.error("Uncaught Merge Throwable", e);
            }

            ApelonDTSExporter apelonDTSexport = new ApelonDTSExporter(instrumentVersion, "Instruments");
            String apelonFile = DIALOGIX_SCHEDULES_DIR + "InstVer_" + 
                        instrumentVersion.getInstrumentVersionID() + "_apelon.xml";
            try {
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(
                        new FileOutputStream(apelonFile
                        ), "UTF-8"));
//                out.write("<html><META http-equiv='Content-Type' content='text/html; charset=utf-8'><head><title>DTS Import for ");
//                out.write(instrumentVersion.getInstrumentID().getInstrumentName() + "(" + instrumentVersion.getVersionString() + ")");
//                out.write("</title></head><body><pre>");
                out.write(apelonDTSexport.getNamespace().toString());
//                out.write("</pre></body></html>");
                out.close();
            } catch (Throwable e) {
                logger.error(apelonFile, e);
                return false;
            } 

            return result;
        } catch (Throwable e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * Return Validation class
     * */
    Validation parseValidation(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("Validation is blank");
            return null;
        }
        StringTokenizer st = new StringTokenizer(token, ";");
        String minVal = null;
        String maxVal = null;
        String inputMask = null;
        String otherVals = null;

        if (st.hasMoreTokens()) {
            st.nextToken(); // discard it -- it is the ActionType
        }
        if (st.hasMoreTokens()) {
            st.nextToken(); // FIXME - this is the castTo parameter
        }
        if (st.hasMoreTokens()) {
            minVal = st.nextToken();
        }
        if (st.hasMoreTokens()) {
            maxVal = st.nextToken();
        }
        if (st.hasMoreTokens()) {
            inputMask = st.nextToken();
        }
        if (st.hasMoreTokens()) {
            StringBuffer sb = new StringBuffer(st.nextToken());
            while (st.hasMoreTokens()) {
                sb.append(";").append(st.nextToken());
            }
            otherVals = sb.toString();
        }
        // now that Validation is populated, test whether it already exists
        return DialogixConstants.parseValidation(minVal, maxVal, inputMask, otherVals);
    }

    /**
    Return ActionType, which is one of {q, e, [, ]}
    @return actionType
     */
    String parseActionType(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("ActionType is blank");
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
            logger.error("Invalid ActionType " + token);
            return null;
        }
    }

    /**
    Return FormatMask, if present
    @return formatMask
     */
    String parseFormatMask(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.info("FormatMask is blank");
            token = "";
//            return null;
        }
        // FIXME - parse proper field of actionTypeString
        return token;
    }

    /**
    Determine the GroupNum based upon item grouping paremeters (actionType)
    @return groupNum
     */
    int parseGroupNum(String actionType) {
        if (actionType == null) {
            logger.error("No actionType specified");
        } else if (actionType.equals("[")) {
            if (withinBlock == true) {
                logger.error("Trying to create a nested group of items");
            } else {
                withinBlock = true;
                ++groupNum;
            }
        } else if (actionType.equals("]")) {
            if (withinBlock == false) {
                logger.error("Trying to close a group of items with no matching '['");
            } else {
                withinBlock = false;
            }
        } else if (actionType.equals("q")) {
            if (withinBlock == false) {
                ++groupNum;
            }
        } else if (actionType.equals("e")) {
            if (withinBlock == true) {
                logger.error("Trying to process an equation within a group of items");
            }
        }
        return groupNum;
    }

    /**
    Parse the answerList parameter to get both the AnswerList, and the dataType
    @param responseOptions - the source string
    @param languageCode - the source language for these answers
    @param languageCounter - how many languages have been processed for this item [1-n]
    @return AnswerList
     */
    void parseAnswerList(AnswerList answerList, String responseOptions, String languageCode, int languageCounter) {
        if (responseOptions == null || responseOptions.trim().length() == 0) {
            logger.error("AnswerList is blank");
        }
        StringTokenizer ans = new StringTokenizer(responseOptions, "|", true); // return '|' tokens too
        String val = null;
        String msg = null;
        int field = 1;
        int ansPos = 0;
        Collection<AnswerListContent> answerListContents = answerList.getAnswerListContentCollection();
        if (answerListContents == null) {
            answerListContents = new ArrayList<AnswerListContent>();
            answerList.setAnswerListContentCollection(answerListContents);
        }
        AnswerListContent answerListContent = null;

        while (ans.hasMoreTokens()) {
            String s = null;
            s = ans.nextToken();

            if ("|".equals(s)) {
                ++field;
                continue;
            }
            switch (field) {
                default:
                    logger.error("Should never get here");
                    break;
                case 1:
                    val = s; // this is the (usually numeric) value associated with the message
                    break;
                case 2:
                    msg = s; // This is the Answer String
                    field = 0; // so that cycle between val & msg;
                    ++ansPos;

                    if (languageCounter == 1) {
                        answerListContent = new AnswerListContent();
                        answerListContent.setAnswerOrder(ansPos);
                        answerListContent.setValue(val);
                        answerListContent.setAnswerListID(answerList);

                        AnswerLocalized answerLocalized = DialogixConstants.parseAnswerLocalized(msg, languageCode);
                        Answer answer = answerLocalized.getAnswerID();
                        if (answer == null) {
                            answer = new Answer();
                            answer.setHasLAcode(Boolean.FALSE);
                            answer.setLAcode(null);
                            answerLocalized.setAnswerID(answer);
                            answer.setAnswerLocalizedCollection(new ArrayList<AnswerLocalized>());
                        }
                        answerListContent.setAnswerID(answer);
                        answer.getAnswerLocalizedCollection().add(answerLocalized); // CHECK - is this duplicative?
                    } else {
                        if (answerListContents.size() < ansPos) {
                            // suggests that there are too many answers in this languageCounter?
                            logger.error("Language # " + languageCounter + " has more answer choices than prior languages");
                            // Add it anyway?
                        } else {
                            // Compare values from this language vs. those set for prior language
                            answerListContent = (AnswerListContent) answerListContents.toArray()[ansPos-1];
                            if (!answerListContent.getValue().equals(val)) {
                                logger.error("Mismatch across languages - Position " + (ansPos - 1) + " was set to " + answerListContent.getValue() + " but there is attempt to reset it to " + val);
                            }
                            Answer answer = answerListContent.getAnswerID();
                            AnswerLocalized answerLocalized = DialogixConstants.parseAnswerLocalized(msg, languageCode);
                            Answer answer2 = answerLocalized.getAnswerID();
                            if (answer2 != null && !answer.getAnswerID().equals(answer2.getAnswerID())) {
                                logger.error("Answer " + msg + " already has AnswerID " + answer2.getAnswerID() + " but being reset to " + answer.getAnswerID());
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
            logger.error("Missing Answer List");
        }
        if (field == 1) {
            logger.error("Missing Answer Message at position " + ansPos);
        }
    }

    boolean writeFile(String filename) {
        if (filename == null || "".equals(filename.trim())) {
            return false;
        }
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-16"));
            out.write(instrumentAsText.toString());
            out.close();
            return true;
        } catch (Throwable e) {
            logger.error(filename, e);
            return false;
        }
    }

    private String getlanguageCode(int i) {
        if (i < 0 || i >= languageCodes.size()) {
            return "en";
        } else {
            return languageCodes.get(i).substring(0, 2);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getLaunchCommand() {
        if (getStatus() == false) {
            return "";
        }
        return "servlet/Dialogix?schedule=" + instrumentVersion.getInstrumentVersionFileName() + "&DIRECTIVE=START";
    }
}