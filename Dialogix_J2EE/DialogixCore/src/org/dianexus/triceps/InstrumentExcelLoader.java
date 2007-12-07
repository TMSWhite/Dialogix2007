/* ********************************************************
 ** Copyright (c) 2000-2007, Thomas Maxwell White, all rights reserved.
 ** $Header$
 ******************************************************** */

package org.dianexus.triceps;

import jxl.*;
import java.io.*;
import java.util.*;
import org.dialogix.entities.*;
import org.dialogix.session.DialogixEntitiesFacadeLocal;
import java.security.*;
import org.dianexus.triceps.modules.data.InstrumentSessionDataJPA;  // FIXME, since this isn't part of EJB, it will be a problem - can we just remove it?
import java.util.logging.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
This class loads instruments from Excel files:
(1) Save as Unicode Text to needed directory
(2) Create horizontal database table, if needed
(3) Load everything into the full data model, enforcing uniqueness constraints
 */
public class InstrumentExcelLoader implements java.io.Serializable {
    private static final String DIALOGIX_SCHEDULES_DIR = "/bin/tomcat6/webapps/FirstResp/WEB-INF/schedules/";   // TODO - was "@@DIALOGIX.SCHEDULES.DIR@@"
    static Logger logger = Logger.getLogger("org.dianexus.triceps.InstrumentExcelLoader");
    private static int UseCounter = 0;
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
    private DialogixEntitiesFacadeLocal dialogixEntitiesFacade = null;
    private ArrayList<InstrumentLoadError> instrumentLoadErrors = new ArrayList<InstrumentLoadError>();
    private int instrumentLoadErrorCounter = 0;
    private int instrumentLoadMessageCounter = 0;

    /**
    Upload instrument
    @param filename The absolute path of the filename
    @return	true if succeeds
     */
    public InstrumentExcelLoader() {
        dialogixEntitiesFacade = lookupDialogixEntitiesFacadeLocal();
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

        justFileName = filename.substring(filename.lastIndexOf(File.separatorChar) + 1);
        varNameStrings = new ArrayList<String>();
        varNameMD5source = new StringBuffer();
        instrumentContentsMD5source = new StringBuffer();

        logger.info("Importing '" + justFileName + "' from '" + filename + "'");

        Workbook workbook = retrieveExcelWorkbook(filename);
        if (workbook != null) {
            this.status = processWorkbook(workbook);
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
            logger.log(Level.SEVERE,"",e);
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
        int rowNum = 0;
        int colNum = 0;
        try {
            ++InstrumentExcelLoader.UseCounter;
            languageCodes = new ArrayList<String>();
            instrumentHeaders = new ArrayList<InstrumentHeader>();
            instrumentContents = new ArrayList<InstrumentContent>();
            languageList = new LanguageList();
            
            Sheet sheet = workbook.getSheet(0);

            this.numCols = sheet.getColumns();
            this.numRows = sheet.getRows();

            // process rows one at a time
            // FIXME - will want to report errors based upon row and column within Excel so can annotate and return marked-up Excel showing errors
            for (rowNum = 0; rowNum < numRows; rowNum++) {
                //process cols
                colNum = 0; // must reset it with each new Row, else log messages are misleading.
                Cell cell = sheet.getCell(0, rowNum);
                // check to see if it is a header row
                // if it is we need to get the languages title and sched versions from the appropriate lines
                if (cell.getContents().equals("RESERVED")) {
                    Cell reservedName = sheet.getCell(1, rowNum);
                    Cell reservedValue = sheet.getCell(2, rowNum);
                    // check for number of languages
                    // Find ReservedWord index from database and add InstrumentHeader entry
                    if (reservedName.getContents().trim().equals("")) {
                        log(rowNum,colNum,Level.SEVERE,"Empty RESERVED word");
                        continue;
                    }
                    ReservedWord reservedWord = dialogixEntitiesFacade.parseReservedWord(reservedName.getContents());
                    if (reservedWord != null) {
                        InstrumentHeader instrumentHeader = new InstrumentHeader();
                        instrumentHeader.setReservedWordID(reservedWord);
                        instrumentHeader.setValue(reservedValue.getContents());
                        instrumentHeader.setInstrumentVersionID(instrumentVersion);
                        instrumentHeaders.add(instrumentHeader);
                    }
                    else {
                        log(rowNum,colNum,Level.SEVERE,"Invalid Reserved Word " + reservedName.getContents());
                    }

                    if (reservedName.getContents().equals("__LANGUAGES__")) {
                        StringTokenizer st = new StringTokenizer((String) reservedValue.getContents(), "|");
                        numLanguages = st.countTokens();
                        for (int l = 0; l < numLanguages; ++l) {
                            String langCode = st.nextToken();
                            if (langCode.length() < 2) {
                                log(rowNum,colNum,Level.SEVERE,"Invalid Language Code " + langCode);
                                //  FIXME - could also call Locale to check whether this is a valid Language Code
//                                Locale temp = new Locale(langCode);
                            } else {
                                languageCodes.add(langCode.substring(0, 2));
                            }
                        }
                        languageList = dialogixEntitiesFacade.parseLanguageList(reservedValue.getContents().trim());
                        if (languageList == null) {
                            log(rowNum, colNum, Level.SEVERE, "missing or invalid list of languages" + reservedValue.getContents().trim());
                        }
                    } else if (reservedName.getContents().equals("__TITLE__")) {
                        this.title = (String) reservedValue.getContents();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MAJOR__")) {
                        this.majorVersion = reservedValue.getContents();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MINOR__")) {
                        this.minorVersion = reservedValue.getContents();
                    }
                } else if (cell.getContents().startsWith("COMMENT")) {
                    ;
                } else {
                    // otherwise it is a data row. Extract the data elements from the spreadsheet and build the text file
                    String conceptString = sheet.getCell(0, rowNum).getContents().trim();
                    String varNameString = sheet.getCell(1, rowNum).getContents().trim();
                    String displayNameString = sheet.getCell(2, rowNum).getContents().trim();
                    String relevanceString = sheet.getCell(3, rowNum).getContents().trim();
                    String actionTypeString = sheet.getCell(4, rowNum).getContents().trim();
                    String defaultAnswer = null;
                    
                    if (varNameString.equals("")) {
                        log(rowNum,colNum,Level.SEVERE,"Missing variableName.  Skippping whole row.");
                        continue;
                    }
                    if (actionTypeString.equals("")) {
                        log(rowNum,colNum,Level.SEVERE,"Missing actionType");
                    }    
                    if (relevanceString.equals("")) {
                        log(rowNum,colNum,Level.SEVERE,"Missing relevance");
                    }                   

                    if (varNameStrings.contains(varNameString)) {
                        log(rowNum,colNum,Level.SEVERE,"Already contains variableName " + varNameString);
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
                    VarName varName = dialogixEntitiesFacade.parseVarName(varNameString);
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

                    String actionType = parseActionType(rowNum,colNum,actionTypeString);
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

                    // FIXME - get default answer
                    // FIXME - gracefully handle mismatch between declared # of languages and actual
                    //  NOTE - for each question in this list, they should mean the same thing, so if the QuestionLocalized String is found, it should(?) be reused?
                    for (colNum = 1; colNum <= numLanguages; colNum++) {
                        String readbackString = "";
                        String questionString = "";
                        String responseOptions = "";
                        String helpString = "";

                        if (numCols > (colNum * 4) + 1) {
                            readbackString = sheet.getCell((colNum * 4) + 1, rowNum).getContents().trim(); // is this used in model?
                        }
                        if (numCols > (colNum * 4) + 2) {
                            questionString = sheet.getCell((colNum * 4) + 2, rowNum).getContents().trim(); // action - questionString or evaluation
                            if (questionString.equals("")) {
                                log(rowNum,colNum,Level.SEVERE,"Missing question");
                            }                               
                        }
                        if (numCols > (colNum * 4) + 3) {
                            responseOptions = sheet.getCell((colNum * 4) + 3, rowNum).getContents().trim(); // this gets parsed into dataType, displayType, and AnswerLis
                            if (responseOptions.equals("")) {
                                log(rowNum,colNum,Level.SEVERE,"Missing responseOptions");
                            }                             
                        }
                        if (numCols > (colNum * 4) + 4) {
                            helpString = sheet.getCell((colNum * 4) + 4, rowNum).getContents().trim();
                        }

                        this.instrumentContentsMD5source.append(readbackString).append(questionString).append(responseOptions).append(helpString); // for MD5 hash

                        if (questionString.contains("`") || responseOptions.contains("`")) {
                            hasTailoring = true;
                        }

                        String languageCode = getlanguageCode(colNum - 1);
                        // CHECK - this should work gracefully even if blank
                        QuestionLocalized questionLocalized = dialogixEntitiesFacade.parseQuestionLocalized(questionString, languageCode);
                        if (colNum == 1) {
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

                        HelpLocalized helpLocalized = dialogixEntitiesFacade.parseHelpLocalized(helpString, languageCode);
                        if (helpLocalized != null) {
                            if (colNum == 1) {
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

                        ReadbackLocalized readbackLocalized = dialogixEntitiesFacade.parseReadbackLocalized(readbackString, languageCode);
                        if (readbackLocalized != null) {
                            if (colNum == 1) {
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
                            log(rowNum,colNum,Level.SEVERE,"Missing DataType");
                        }
                        else {
                            StringTokenizer ans = new StringTokenizer(responseOptions, "|", false);
                            String token = null;
                            try {
                                token = ans.nextToken();
                            } catch (NoSuchElementException e) {
                                log(rowNum,colNum,Level.SEVERE,"Missing DataType");
                            }
                            displayType = dialogixEntitiesFacade.parseDisplayType(token);

                            if (displayType.getHasAnswerList()) {
                                if (responseOptions.equals("") || !responseOptions.contains("|")) {
                                    log(rowNum,colNum,Level.SEVERE,"AnswerList is blank");
                                }
                                // Must handle missing answerlist gracefully?
                                answerListDenormalizedString = responseOptions.substring(responseOptions.indexOf("|") + 1).trim(); 
                                answerListDenormalized = dialogixEntitiesFacade.parseAnswerListDenormalized(answerListDenormalizedString, languageCode);

                                if (colNum == 1) {
                                    answerList = answerListDenormalized.getAnswerListID();
                                    if (answerList == null) {
                                        answerList = new AnswerList();
                                        answerList.setAnswerListDenormalizedCollection(new ArrayList<AnswerListDenormalized>());
                                    }
                                }
                                // FIXME - if there is an existing AnswerList, can I just load it and all of its descendants?
                                parseAnswerList(rowNum, colNum, answerList, answerListDenormalizedString, languageCode, colNum); // FIXME - ideally will re-use an AnswerList if identical across uses
                                answerList.getAnswerListDenormalizedCollection().add(answerListDenormalized);
                                answerListDenormalized.setAnswerListID(answerList);
                            }
                            if (displayType.getDisplayType().equals("nothing")) {
                                isInstruction = true;
                            }
                        }
                        if (colNum == 1) {
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

                    validation = parseValidation(rowNum,colNum,actionTypeString);

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

                    item = dialogixEntitiesFacade.findItem(item, firstQuestionString, firstAnswerListDenormalizedString, displayType.getDataTypeID().getDataType(), dialogixEntitiesFacade.lastItemComponentsHadNewContent()); // checks whether it alreaady exists, returning prior object, if available
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
            } // end for rowNum loop
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
                log(rowNum,colNum,Level.INFO,"Error generating MD5 hash of instrument");
            }
//            instrumentHash.setInstrumentVersionCollection((new ArrayList<InstrumentVersion>()).add(instrumentVersion));
            // Create new Instrument and Instrument Version, if needed.
            // FIXME Throw an error if the Instrument Name and Version both exist
            if (title.equals("")) {
                log(rowNum,colNum,Level.SEVERE,"Instrument has no Title");
                return false;
            }
            
            instrumentVersion = dialogixEntitiesFacade.parseInstrumentVersion(title, majorVersion + "." + minorVersion);
            if (instrumentVersion == null) {
                log(rowNum,colNum,Level.SEVERE,"Instrument " + title + "(" + majorVersion + "." + minorVersion + ") already exists.  Please change either the Title, Major_Version, or Minor_Version");
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
            
            for (int i=0; i < instrumentLoadErrors.size(); ++i) {
                instrumentLoadErrors.get(i).setInstrumentVersionID(instrumentVersion);
            }
            instrumentVersion.setInstrumentLoadErrorCollection(instrumentLoadErrors);

            // Store it to database
            boolean result = false;
            dialogixEntitiesFacade.merge(instrument); 

            // Now create the Horizontal table
            InstrumentSessionDataJPA horizontalTable = new InstrumentSessionDataJPA();
            horizontalTable.create(instrumentVersion.getInstrumentVersionID(), varNameStrings);

            result = true;

            ApelonDTSExporter apelonDTSexport = new ApelonDTSExporter(instrumentVersion, "Instruments");
            String apelonFile = DIALOGIX_SCHEDULES_DIR + "InstVer_" + 
                        instrumentVersion.getInstrumentVersionID() + "_apelon.xml";
            try {
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(
                        new FileOutputStream(apelonFile
                        ), "UTF-8"));
                out.write(apelonDTSexport.getNamespace().toString());
                out.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE,apelonFile, e);
            } 

            return result;
        } catch (Exception e) {
            log(rowNum,colNum,Level.SEVERE,"Unexpected Error Parsing " + workbook.getSheet(0).getCell(colNum, rowNum).getContents().trim());
            logger.log(Level.SEVERE,"", e);
        }
        return false;
    }

    /**
     * Return Validation class
     * */
    Validation parseValidation(int rowNum, int colNum, String token) {
        if (token == null || token.trim().length() == 0) {
            log(rowNum,colNum,Level.INFO,"Validation is blank");
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
        return dialogixEntitiesFacade.parseValidation(minVal, maxVal, inputMask, otherVals);
    }

    /**
    Return ActionType, which is one of {q, e, [, ]}
    @return actionType
     */
    String parseActionType(int rowNum, int colNum, String token) {
        if (token == null || token.trim().length() == 0) {
            log(rowNum,colNum,Level.SEVERE,"ActionType is blank");
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
            log(rowNum,colNum,Level.SEVERE,"Invalid ActionType " + token);
            return null;
        }
    }

    /**
    Return FormatMask, if present
    @return formatMask
     */
    String parseFormatMask(int rowNum, int colNum, String token) {
        if (token == null || token.trim().length() == 0) {
            log(rowNum,colNum,Level.INFO,"FormatMask is blank");
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
    int parseGroupNum(int rowNum, int colNum, String actionType) {
        if (actionType == null) {
            log(rowNum,colNum,Level.SEVERE,"No actionType specified");
        } else if (actionType.equals("[")) {
            if (withinBlock == true) {
                log(rowNum,colNum,Level.SEVERE,"Trying to create a nested group of items");
            } else {
                withinBlock = true;
                ++groupNum;
            }
        } else if (actionType.equals("]")) {
            if (withinBlock == false) {
                log(rowNum,colNum,Level.SEVERE,"Trying to close a group of items with no matching '['");
            } else {
                withinBlock = false;
            }
        } else if (actionType.equals("q")) {
            if (withinBlock == false) {
                ++groupNum;
            }
        } else if (actionType.equals("e")) {
            if (withinBlock == true) {
                log(rowNum,colNum,Level.SEVERE,"Trying to process an equation within a group of items");
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
    void parseAnswerList(int rowNum, int colNum, AnswerList answerList, String responseOptions, String languageCode, int languageCounter) {
        if (responseOptions == null || responseOptions.trim().length() == 0) {
            log(rowNum,colNum,Level.SEVERE,"AnswerList is blank");
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
            s = ans.nextToken().trim();

            if ("|".equals(s)) {
                ++field;
                continue;
            }
            switch (field) {
                default:
                    log(rowNum,colNum,Level.SEVERE,"Should never get here when parsing AnswerList");
                    break;
                case 1:
                    val = s; // this is the (usually numeric) value associated with the message
                    break;
                case 2:
                    msg = s; // This is the Answer String
                    field = 0; // so that cycle between val & msg;
                    ++ansPos;
                    
                   if (msg.equals("")) {
                        log(rowNum,colNum,Level.SEVERE,"Missing Answer at position " + ansPos + " within " + responseOptions);
                    }                    

                    if (languageCounter == 1) {
                        answerListContent = new AnswerListContent();
                        answerListContent.setAnswerOrder(ansPos);
                        answerListContent.setValue(val);
                        answerListContent.setAnswerListID(answerList);
                        
                        // Should handle this gracefully?
                        AnswerLocalized answerLocalized = dialogixEntitiesFacade.parseAnswerLocalized(msg, languageCode);
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
                            log(rowNum,colNum,Level.SEVERE,"Language # " + languageCounter + " has more answer choices than prior languages");
                            // Add it anyway?
                        } else {
                            // Compare values from this language vs. those set for prior language
                            answerListContent = (AnswerListContent) answerListContents.toArray()[ansPos-1];
                            if (!answerListContent.getValue().equals(val)) {
                                log(rowNum,colNum,Level.SEVERE,"Mismatch across languages - Position " + (ansPos - 1) + " was set to " + answerListContent.getValue() + " but there is attempt to reset it to " + val);
                            }
                            Answer answer = answerListContent.getAnswerID();
                            AnswerLocalized answerLocalized = dialogixEntitiesFacade.parseAnswerLocalized(msg, languageCode);
                            Answer answer2 = answerLocalized.getAnswerID();
                            if (answer != null && answer2 != null && !answer.getAnswerID().equals(answer2.getAnswerID())) { // FIXME - is error in Hebrew
                                log(rowNum,colNum,Level.SEVERE,"Answer " + msg + " already has AnswerID " + answer2.getAnswerID() + " but being reset to " + answer.getAnswerID());
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
            log(rowNum,colNum,Level.SEVERE,"Missing Answer List");
        }
        if (field == 1) {
            log(rowNum,colNum,Level.SEVERE,"Missing Answer Message at position " + ansPos);
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
    
    public boolean hasInstrumentLoadErrors() {
        return (instrumentLoadErrorCounter > 0);
    }
    
    public ArrayList<InstrumentLoadError> getInstrumentLoadErrors() {
        return instrumentLoadErrors;
    }
    
    private DialogixEntitiesFacadeLocal lookupDialogixEntitiesFacadeLocal() {
        try {
            Context c = new InitialContext();
            DialogixEntitiesFacadeLocal _dialogixEntitiesFacade = (DialogixEntitiesFacadeLocal) c.lookup("java:comp/env/DialogixEntitiesFacade_ejbref");
            _dialogixEntitiesFacade.init();
            return _dialogixEntitiesFacade;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"", e);
            return null;
        }
    }
    
    private void log(int rowNum, int colNum, Level level, String message) {
        ++rowNum;   // so from 1-N
        ++colNum;   // so from 1-N
        StringBuffer sb = new StringBuffer("Err(");
        sb.append(++instrumentLoadMessageCounter).append(")");
        sb.append("[").append(rowNum).append(",").append(colNum).append("] ");
        sb.append(message);
        logger.log(level, sb.toString());
        instrumentLoadErrors.add(new InstrumentLoadError(rowNum, colNum, level.intValue(),message));
        if (level.equals(Level.SEVERE) || level.equals(Level.WARNING)) {
            ++instrumentLoadErrorCounter;
        }
    }
}