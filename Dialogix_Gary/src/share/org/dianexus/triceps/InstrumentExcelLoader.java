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
 */
public class InstrumentExcelLoader implements java.io.Serializable {

    static Logger logger = Logger.getLogger(InstrumentExcelLoader.class);
    private static int UseCounter = 0;
    private static final String DIALOGIX_SCHEDULES_DIR = "/bin/tomcat6/webapps/Demos/WEB-INF/schedules/";
    
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
    private boolean instrumentExists = false;   // FIXME
    private boolean instrumentVersionExists = false;    // FIXME

    /**
    Upload instrument
    @param filename The absolute path of the filename
    @return	true if succeeds
     */
    public InstrumentExcelLoader() {
    }
    
    private void  initInstrumentGraph() {
        languageCodes = new ArrayList<String>(); 
        instrumentHeaders = new ArrayList<InstrumentHeader>();
        instrumentContents = new ArrayList<InstrumentContent>();
        languageList = new LanguageList();
    }

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

    Workbook retrieveExcelWorkbook(String filename) {
        try {
            Workbook workbook = Workbook.getWorkbook(new File(filename));
            return workbook;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /* Process and  Excel file, doing the following:
     (1) Save it as  Unicode .txt to the target directory
     (2) Populate the Dialogix data model with the instrument contents
     (3) Create the needed horizontal tables
    FIXME - parse and set Validation parameters
     */
    boolean processWorkbook(Workbook workbook) {
        try {
            ++InstrumentExcelLoader.UseCounter;
            initInstrumentGraph();              // FIXME - will things have to be reset before next usage of this class?
            instrumentAsText = new StringBuffer();

            Sheet sheet = workbook.getSheet(0);

            this.numCols = sheet.getColumns();
            this.numRows = sheet.getRows();

            // process rows one at a time
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
                        instrumentHeader.setInstrumentVersionID(instrumentVersion); // FIXME - will be null until have retrieved an InstrumentVersion
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
                            }
                            else {
                                languageCodes.add(langCode.substring(0,2));
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
                    instrumentAsText.append(cell.getContents());
                    for (int m = 1; m < numCols; m++) {
                        Cell myCell = sheet.getCell(m, i);
                        instrumentAsText.append("\t").append(myCell.getContents());
                    }
                    instrumentAsText.append("\n");
                    // otherwise it is a data row. Extract the data elements from the spreadsheet and build the text file
                } else {
                    String conceptString = sheet.getCell(0, i).getContents();
                    String varNameString = sheet.getCell(1, i).getContents();
                    String displayNameString = sheet.getCell(2, i).getContents();
                    String relevanceString = sheet.getCell(3, i).getContents();
                    String actionTypeString = sheet.getCell(4, i).getContents();
                    String defaultAnswer = null;
                    
                    if (varNameStrings.contains(varNameString)) {
                        logger.error("Already contains variableName " + varNameString);
                        // continue;   // FIXME skip this row? - no, needed for Excel, but not for database
                    }
                    else {
                        // FIXME - check the variable name, or give a prefix - and confirm that doesn't have embedded disallowed characters
                        varNameStrings.add(varNameString);
                    }

                    // Set an InstrumentContent row for this item
                    ++this.numVars;
                    this.varNameMD5source.append(varNameString);    // for MD5 hash
                    this.instrumentContentsMD5source.append(relevanceString).append(actionTypeString);  // for MD5 hash
                    
                    InstrumentContent instrumentContent = new InstrumentContent();
                    Item item = new Item(); // FIXME - populate it, then test whether unique?
                    Help help = null; 
                    Readback readback = null;
                    AnswerList answerList = new AnswerList(); // FIXME - populate it, then test whether unique?
                    Question question = null;
                    DisplayType displayType = null;
                    Validation validation = new Validation();   // FIXME - should be parsed
                    VarName varName = DialogixConstants.parseVarName(varNameString);

                    instrumentContent.setInstrumentVersionID(instrumentVersion);    // FIXME -- it will be null until we have retrieved and instrument
                    instrumentContent.setItemID(item); // CHECK does Item need to be bidirectionally linked to InstrumentContent?
                    instrumentContent.setVarNameID(varName); // Find the VarName index, creating new one if needed
                    instrumentContent.setItemSequence(numVars); // for convenience, set it to be the line number within the Excel file -- NO - use the VarName count
                    instrumentContent.setHelpID(help);	
                    instrumentContent.setIsRequired((short) 1); // true
                    instrumentContent.setIsReadOnly((short) 0); // false
                    instrumentContent.setDisplayName(displayNameString);
                    instrumentContent.setRelevance(relevanceString.trim());
                    
                    if (!relevanceString.trim().equals(1)) {
                        ++this.numBranches;
                    }

                    String actionType = parseActionType(actionTypeString);
                    instrumentContent.setItemActionType(actionType);
                    instrumentContent.setGroupNum(parseGroupNum(actionType));
                    instrumentContent.setFormatMask(parseFormatMask(actionTypeString)); // FIXME - this is currently blank
                    instrumentContents.add(instrumentContent);

                    // Set the Item-specific values
                    item.setConcept(conceptString);
                    item.setHasLOINCcode(Boolean.FALSE); // by default - this could be overridden later
                    item.setLoincNum("LoincNum");
                    item.setAnswerListID(answerList);
                    item.setInstrumentContentCollection(instrumentContents);    
//                    item.setLoincItemRequestCollection(null);   // FIXME

                    // if the number of languages is more than one there will be 4 more columns per language to process
                    // cycle through for the number of languages
                    // There may be more languages listed than actual langauges entered - handle this gracefully
                    ArrayList<String> langCols = new ArrayList<String>();
                    
                    boolean hasTailoring = false;
                    boolean isInstruction = false;

                    // FIXME - test whether those column exist, else get NullPointerException
                    // FIXME - get default answer
                    // FIXME - gracefully handle mismatch between declared # of languages and actual
                    for (int j = 1; j <= numLanguages; j++) {
                        String readbackString="";
                        String questionString="";
                        String responseOptions="";
                        String helpString="";
                        
                        if(numCols > (j*4)+1) {
                            readbackString = sheet.getCell((j * 4) + 1, i).getContents(); // is this used in model?
                        }
                        if (numCols > (j*4)+2) {
                            questionString = sheet.getCell((j * 4) + 2, i).getContents(); // action - questionString or evaluation
                        }
                        if (numCols > (j*4)+3) {
                            responseOptions = sheet.getCell((j * 4) + 3, i).getContents(); // this gets parsed into dataType, displayType, and AnswerLis
                        }
                        if (numCols > (j*4)+4) {
                            helpString = sheet.getCell((j * 4) + 4, i).getContents();
                        }
                        
                        this.instrumentContentsMD5source.append(readbackString).append(questionString).append(responseOptions).append(helpString);  // for MD5 hash                        

                        // Save them to flat file
                        langCols.add(readbackString);
                        langCols.add(questionString);
                        langCols.add(responseOptions);
                        langCols.add(helpString);
                        
                        if (questionString.contains("`") || responseOptions.contains("`")) {
                            hasTailoring = true;
                        }
                        if (j == 1) {   // FIXME - with the current model, I can only store concatenated AnswerLists in this fashion.
                            if (responseOptions == null || responseOptions.trim().length() == 0) {
                                item.setAnswerOptionsString("nothing");
                            } else {
                                item.setAnswerOptionsString(responseOptions);
                            }
                        }

                        String languageCode = getlanguageCode(j-1); 
                        QuestionLocalized questionLocalized = DialogixConstants.parseQuestionLocalized(questionString, languageCode);

                        question = item.getQuestionID();
                        if (question == null) {
                            // Then none exists, so create a new one, and assign that QuestionLocalized object to it
                            question = new Question();
                            ArrayList<QuestionLocalized> questionLocalizedCollection = new ArrayList<QuestionLocalized>();
                            questionLocalizedCollection.add(questionLocalized);
                            question.setQuestionLocalizedCollection(questionLocalizedCollection);
                            questionLocalized.setQuestionID(question);
                            item.setQuestionID(question);
                        } else {
                            question.getQuestionLocalizedCollection().add(questionLocalized);
                            questionLocalized.setQuestionID(question);
                        }

                        HelpLocalized helpLocalized = DialogixConstants.parseHelpLocalized(helpString, languageCode);
                        
                        help = instrumentContent.getHelpID();
                        if (help == null) {
                            // Then none exists, so create a new one, and assign that HelpLocalized object to it
                            help = new Help();
                            ArrayList<HelpLocalized> helpLocalizedCollection = new ArrayList<HelpLocalized>();
                            helpLocalizedCollection.add(helpLocalized);
                            help.setHelpLocalizedCollection(helpLocalizedCollection);
                            instrumentContent.setHelpID(help);
                        }
                        else {
                            help.getHelpLocalizedCollection().add(helpLocalized);
                            helpLocalized.setHelpID(help);
                        }
                        
                        ReadbackLocalized readbackLocalized = DialogixConstants.parseReadbackLocalized(readbackString, languageCode);
                        
                        readback = item.getReadbackID();
                        if (readback == null) {
                            // Then none exists, so create a new one, and assign that ReadbackLocalized object to it
                            readback = new Readback();
                            ArrayList<ReadbackLocalized> readbackLocalizedCollection = new ArrayList<ReadbackLocalized>();
                            readbackLocalizedCollection.add(readbackLocalized);
                            readback.setReadbackLocalizedCollection(readbackLocalizedCollection);
                            readbackLocalized.setReadbackID(readback);
                            item.setReadbackID(readback);
                        } else {
                            readback.getReadbackLocalizedCollection().add(readbackLocalized);
                            readbackLocalized.setReadbackID(readback);
                        }                        
                        
                        StringTokenizer ans = new StringTokenizer(responseOptions, "|", false);
                        String token = null;
                        try {
                            token = ans.nextToken();
                        } catch (NoSuchElementException e) {
                            logger.error("Missing Datatype", e);
                        }
                        displayType = DialogixConstants.parseDisplayType(token);
                        
                        if (j < this.numLanguages) {
                            if (displayType.getHasAnswerList()) {
                                parseAnswerList(answerList, responseOptions, languageCode, j);
                            }
                            if (displayType.getDisplayType().equals("nothing")) {
                                isInstruction = true;
                            }
                        }
                        else {
                            // FIXME - parse extra columns, if present, for default value
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
                    
                    item.setItemType(actionType.equalsIgnoreCase("e") ? "Equation" : "Question");
                    item.setDataTypeID(displayType.getDataTypeID());
                    item.setValidationID(validation);   // FIXME - this isn't really parsed

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
            } catch (Exception e) {
                logger.error("", e);
            }
//            instrumentHash.setInstrumentVersionCollection((new ArrayList<InstrumentVersion>()).add(instrumentVersion));
            
            // Create new Instrument and Instrument Version, if needed.  
            // FIXME Throw an error if the Instrument Name and Version both exist
            instrumentVersion = DialogixConstants.parseInstrumentVersion(title,majorVersion + "." + minorVersion);

            instrumentVersion.setInstrumentContentCollection(instrumentContents); 
            for (int i=0;i<instrumentContents.size();++i) {
                instrumentContents.get(i).setInstrumentVersionID(instrumentVersion);
            }
            
//            instrumentVersion.setInstrumentSessionCollection(null); // FIXME - when should this be set?
            instrumentVersion.setInstrumentHeaderCollection(instrumentHeaders); 
            for (int i=0;i<instrumentHeaders.size();++i) {
                instrumentHeaders.get(i).setInstrumentVersionID(instrumentVersion);
            }
            
//            instrumentVersion.setLoincInstrumentRequestCollection(null);    // FIXME - when should this be set?
            
            instrumentVersion.setInstrumentHashID(instrumentHash);
            
//            instrumentVersion.setSemanticMappingIQACollection(null);  // FIXME - when should this be set?
            
            instrumentVersion.setInstrumentVersionFileName(DIALOGIX_SCHEDULES_DIR + justFileName + "_" + InstrumentExcelLoader.UseCounter + ".txt");

            instrument = instrumentVersion.getInstrumentID();
            instrument.setInstrumentName(title);

//            instrument.setInstrumentSessionCollection(null);  // FIXME - when, if ever, will this be needed?
            ArrayList<InstrumentVersion>instrumentVersionCollection = new ArrayList<InstrumentVersion>();
            instrumentVersionCollection.add(instrumentVersion);
            instrument.setInstrumentVersionCollection(instrumentVersionCollection);
            
            // Store it to database
            DialogixConstants.merge(instrument);
            
            // Now create the Horizontal table
            InstrumentSessionDataJPA horizontalTable = new InstrumentSessionDataJPA();
            horizontalTable.create(instrumentVersion.getInstrumentVersionID(), varNameStrings);

            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
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
        int field = 0;
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
                case 0:
                    break; // discard the first token - answerType
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

                        Answer answer = new Answer();
                        answer.setHasLAcode(Boolean.FALSE);
                        answer.setLAcode(null);
                        answerListContent.setAnswerID(answer);
                        answer.setAnswerLocalizedCollection(new ArrayList<AnswerLocalized>());
                        AnswerLocalized answerLocalized = DialogixConstants.parseAnswerLocalized(msg, languageCode);
                        answer.getAnswerLocalizedCollection().add(answerLocalized);
                        answerLocalized.setAnswerID(answer);
                    } else {
                        if (answerListContents.size() < ansPos) {
                            // suggests that there are too many answers in this languageCounter?
                            logger.error("Language # " + languageCounter + " has more answer choices than prior languages");
                            // Add it anyway?
                        } else {
                            // Compare values from this language vs. those set for prior language
                            answerListContent = (AnswerListContent) answerListContents.toArray()[ansPos-1]; 
                            if (!answerListContent.getValue().equals(val)) {
                                logger.error("Mismatch across languages - Position " + (ansPos-1) + " was set to " + answerListContent.getValue() + " but there is attempt to reset it to " + val);
                            }
                            Answer answer = answerListContent.getAnswerID();
                            AnswerLocalized answerLocalized = DialogixConstants.parseAnswerLocalized(msg, languageCode);
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
        } catch (Exception e) {
            logger.error(filename, e);
            return false;
        }
    }

    private String getlanguageCode(int i) {
        if (i < 0 || i >= languageCodes.size()) {
            return "en";
        } else {
            return languageCodes.get(i).substring(0,2);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getLaunchCommand() {
        if (getStatus() == false) 
            return "";
        
        return "servlet/Dialogix?schedule=" + instrumentVersion.getInstrumentVersionFileName() + "&DIRECTIVE=START";
    }
}