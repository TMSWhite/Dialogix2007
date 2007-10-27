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
import org.apache.log4j.Logger;

/**
This class loads instruments from Excel files:
(1) Save as Unicode Text to needed directory
(2) Create horizontal database table, if needed
 */
public class InstrumentExcelLoader implements java.io.Serializable {

    static Logger logger = Logger.getLogger(InstrumentExcelLoader.class);
    private static int UseCounter = 0;
    private static int VarNameCounter = 0;
    private static int QuestionLocalizedCounter = 0;
    private static int AnswerLocalizedCounter = 0;
    private static int AnswerListCounter = 0;
    private static int AnswerListContentCounter = 0;
    private static int HelpLocalizedCounter = 0;
    
    private String contents = null;
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
    private EntityManagerFactory emf;
    private int groupNum = 0;
    private boolean withinBlock = false;
    private Instrument instrument = null;
    private InstrumentVersion instrumentVersion = null;

    /**
    Upload instrument
    @param filename The absolute path of the filename
    @return	true if succeeds
     */
    public InstrumentExcelLoader() {
        this.loadStaticContents();
    }

    private EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DialogixDomainPU");
        }
        return emf.createEntityManager();
    }
    
    private void  initInstrumentGraph() {
        instrument = new Instrument();
        instrumentVersion = new InstrumentVersion();
        languageCodes = new ArrayList<String>(); 
        instrumentHeaders = new ArrayList<InstrumentHeader>();
        instrumentContents = new ArrayList<InstrumentContent>();
        
        instrumentVersion.setInstrumentID(instrument);
        instrumentVersion.setInstrumentHeaderCollection(instrumentHeaders);
        instrumentVersion.setInstrumentContentCollection(instrumentContents);
    }

    public boolean loadInstrument(String filename) {
        if (filename == null || "".equals(filename.trim())) {
            this.status = false;
        }

        String justFileName = filename.substring(filename.lastIndexOf(File.separatorChar) + 1);

        logger.debug("Importing '" + justFileName + "' from '" + filename + "'");

        Workbook workbook = retrieveExcelWorkbook(filename);
        if (workbook != null && processWorkbook(workbook)) {
            this.status = writeFile("/bin/tomcat6/webapps/Demos/WEB-INF/schedules/InstrumentExcelLoader-test_" + InstrumentExcelLoader.UseCounter + ".txt");
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

    /* XXX Should this return a class representing the information needed to create table?
    title
    majorVersion
    minorVersion
    numLanguages
    varName[]
    FIXME - set Hash values?
    FIXME - parse and set Validation parameters
     */
    boolean processWorkbook(Workbook workbook) {
        try {
            ++InstrumentExcelLoader.UseCounter;
            initInstrumentGraph();            
            StringBuffer schedule = new StringBuffer();

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
                    schedule.append(cell.getContents() + "\t" + reservedName.getContents() + "\t" + reservedValue.getContents() + "\n");
                    // check for number of languages
                    // Find ReservedWord index from database and add InstrumentHeader entry
                    ReservedWord reservedWord = parseReservedWord(reservedName.getContents());
                    if (reservedWord != null) {
                        InstrumentHeader instrumentHeader = new InstrumentHeader(reservedWord.getReservedWordID(), reservedValue.getContents());
                        instrumentHeaders.add(instrumentHeader);
                        instrumentHeader.setReservedWordID(reservedWord);
                        // otherwise, report error and don't add it to list
                        // FIXME - after know InstrumentVersionID, must set it within InstrumentHeader?
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
                                languageCodes.add(langCode.substring(0,1));
                            }
                        }
                    } else if (reservedName.getContents().equals("__TITLE__")) {
                        this.title = (String) reservedValue.getContents();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MAJOR__")) {
                        this.majorVersion = reservedValue.getContents();
                    } else if (reservedName.getContents().equals("__SCHED_VERSION_MINOR__")) {
                        this.minorVersion = reservedValue.getContents();
                    }
                } else if (cell.getContents().startsWith("COMMENT")) {
                    schedule.append(cell.getContents());
                    for (int m = 1; m < numCols; m++) {
                        Cell myCell = sheet.getCell(m, i);
                        schedule.append("\t").append(myCell.getContents());
                    }
                    schedule.append("\n");
                    // otherwise it is a data row. Extract the data elements from the spreadsheet and build the text file
                } else {
                    String conceptString = sheet.getCell(0, i).getContents();
                    String varNameString = sheet.getCell(1, i).getContents();
                    String displayNameString = sheet.getCell(2, i).getContents();
                    String relevanceString = sheet.getCell(3, i).getContents();
                    String actionTypeString = sheet.getCell(4, i).getContents();
                    String defaultAnswer = null;

                    // Set an InstrumentContent row for this item
                    InstrumentContent instrumentContent = new InstrumentContent();
                    Item item = new Item(); // FIXME - populate it, then test whether unique?
                    Help help = new Help(); // FIXME - populate it, then test whether unique?
                    AnswerList answerList = new AnswerList(); // FIXME - populate it, then test whether unique?
                    Question question = null;
                    DisplayType displayType = null;
                    VarName varName = parseVarName(varNameString);

                    instrumentContent.setInstrumentVersionID(instrumentVersion);
                    instrumentContent.setItemID(item); // CHECK does Item need to be bidirectionally linked to InstrumentContent?
                    instrumentContent.setVarNameID(varName); // Find the VarName index, creating new one if needed
                    instrumentContent.setItemSequence(i + 1); // for convenience, set it to be the line number within the Excel file
                    instrumentContent.setHelpID(1);	// FIXME - should be object, but using int
                    instrumentContent.setIsRequired((short) 1); // true
                    instrumentContent.setIsReadOnly((short) 0); // false
                    instrumentContent.setDisplayName(displayNameString);
                    instrumentContent.setRelevance(relevanceString);

                    String actionType = parseActionType(actionTypeString);
                    instrumentContent.setActionType(actionType);
                    instrumentContent.setGroupNum(parseGroupNum(actionType));
                    instrumentContent.setFormatMask(parseFormatMask(actionTypeString));
                    instrumentContents.add(instrumentContent);

                    // Set the Item-specific values
                    item.setConcept(conceptString);
                    item.setHasLOINCcode(Boolean.FALSE); // by default - this could be overridden later
                    item.setLoincNum(null);
                    item.setAnswerListID(answerList);

                    // if the number of languages is more than one there will be 4 more columns per language to process
                    // cycle through for the number of languages
                    // There may be more languages listed than actual langauges entered - handle this gracefully
                    ArrayList<String> langCols = new ArrayList<String>();

                    for (int j = 1; j <= numLanguages; j++) {
                        String readback = sheet.getCell((j * 4) + 1, i).getContents(); // is this used in model?
                        String questionString = sheet.getCell((j * 4) + 2, i).getContents(); // action - questionString or evaluation
                        String responseOptions = sheet.getCell((j * 4) + 3, i).getContents(); // this gets parsed into dataType, displayType, and AnswerList
                        String helpString = sheet.getCell((j * 4) + 4, i).getContents();

                        // Save them to flat file
                        langCols.add(readback);
                        langCols.add(questionString);
                        langCols.add(responseOptions);
                        langCols.add(helpString);

                        String languageCode = getlanguageCode(j); // FIXME - locales use 2 chacter langauge codes, so need to use that, not an Integer
                        QuestionLocalized questionLocalized = parseQuestionLocalized(questionString, languageCode);

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

                        /*  This isn't following same pattern as Question - shouldn't it return an object? 
                        HelpLocalized helpLocalized = parseHelpLocalized(helpString, languageCode);
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
                        help.getHelpLocalizedCollection().add(helpLocalized());
                        }
                        */

                        displayType = parseDisplayType(responseOptions);
                        if (j < this.numLanguages) {
                            if (displayType.getHasAnswerList()) {
                                parseAnswerList(answerList, responseOptions, languageCode, j);
                            }
                        }
                        else {
                            // FIXME - parse extra columns, if present, for default value
                        }
                    }
                    // TODO - Check whether this works for multiple languages
                    schedule.append(conceptString).append("\t").append(varNameString).append("\t").append(displayNameString).append("\t").append(relevanceString).append("\t").append(actionTypeString);

                    for (int k = 0; k < langCols.size(); k++) {
                        schedule.append("\t").append(langCols.get(k));
                    }
                    schedule.append("\n");

                    instrumentContent.setIsMessage(displayType.getDisplayType().equals("nothing") ? (short) 1 : (short) 0);
                    instrumentContent.setDefaultAnswer(defaultAnswer); // FIXME - settable after all language-specific columns are loaded
                    instrumentContent.setVarNameID(varName);
                    /*
                    instrumentContent.setSPSSformat(parseSPSSformat();
                    instrumentContent.setSASinformat();
                    instrumentContent.setSASformat();
                     */
                }
            } // end for i loop
            workbook.close();
            this.contents = schedule.toString();

            // after parsing all reserved words, add them to instrumentVersion object
            instrumentVersion.setVersionString(this.majorVersion + "." + this.minorVersion);
            instrumentVersion.setInstrumentStatus(new Integer(1));
            instrumentVersion.setCreationTimeStamp(new Date(System.currentTimeMillis()));
            instrumentVersion.setInstrumentHashID(new InstrumentHash());    //  FIXME
            
            
            instrument.setInstrumentName(title);
            ArrayList<InstrumentVersion>instrumentVersionCollection = new ArrayList<InstrumentVersion>();
            instrumentVersionCollection.add(instrumentVersion);
            instrument.setInstrumentVersionCollection(instrumentVersionCollection);
            
            for (int i=0;i<instrumentHeaders.size();++i) {
                instrumentHeaders.get(i).setInstrumentVersionID(instrumentVersion);
            }
            
            // Store it to database
            persist(instrumentVersion);

            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
    Find index for this ReservedWord
    @return Null if token is empty or ReservedWord does not exist; or Integer of ReservedWord
     */
    ReservedWord parseReservedWord(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("ReservedWord is blank");
            return null;
        }
        if (ReservedWordHash.containsKey(token)) {
            return (ReservedWord) ReservedWordHash.get(token);
        } else {
            logger.error("Invalid Reserved Word " + token);
            return null;
        }
    }
    /**
    Find index for this VarName
    @return Null if token is empty, or Integer of VarName (adding an new VarNameID if needed)
     */
    private static HashMap VarNameHash = new HashMap(); // FIXME - should map name to ID, not to actual VarName, since can't truly re-used the objects?

    VarName parseVarName(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("VarName is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (VarNameHash.containsKey(token)) {
            return (VarName) VarNameHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            // There is a named query - how do I use it?
            String q = "SELECT v FROM VarName v WHERE v.varName = :varName";
            Query query = em.createQuery(q);
            query.setParameter("varName", token);
            VarName varName = null;
            try {
                varName = (VarName) query.getSingleResult();
            } catch (NoResultException e) {
                logger.info("VarName " + token + " Doesn't yet exist -- adding it");
                // How do I get the next VarNameID value in lieu of auto_increment?
                varName = new VarName();
                varName.setVarNameID(new Integer(++VarNameCounter));
                varName.setVarName(token);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            VarNameHash.put(token, varName);
            return varName;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            em.close();
        }
    }
    /**
    Find index for this QuestionLocalized
    @return Null if token is empty, or Integer of QuestionLocalized (adding an new QuestionLocalizedID if needed)
     */
    private static HashMap QuestionLocalizedHash = new HashMap();

    QuestionLocalized parseQuestionLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.error("QuestionLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (QuestionLocalizedHash.containsKey(token)) {
            return (QuestionLocalized) QuestionLocalizedHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            // There is a named query - how do I use it?
            String q = "SELECT v FROM QuestionLocalized v WHERE v.questionString = :questionString";
            Query query = em.createQuery(q);
            query.setParameter("questionString", token);
            QuestionLocalized questionLocalized = null;
            try {
                questionLocalized = (QuestionLocalized) query.getSingleResult();
            } catch (NoResultException e) {
                logger.info("QuestionLocalized " + token + " Doesn't yet exist -- adding it");
                // How do I get the next QuestionLocalizedID value in lieu of auto_increment?
                questionLocalized = new QuestionLocalized();
                questionLocalized.setQuestionLocalizedID(new Integer(++QuestionLocalizedCounter));
                questionLocalized.setQuestionString(token);
                questionLocalized.setLanguageCode(languageCode);
                // FIXME - what about setQuestionID() - should a new one be created if none is found and the language is English?
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            QuestionLocalizedHash.put(token, questionLocalized);
            return questionLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            em.close();
        }
    }
    /**
    Find index for this AnswerLocalized
    @return Null if token is empty, or Integer of AnswerLocalized (adding an new AnswerLocalizedID if needed)
     */
    private static HashMap AnswerLocalizedHash = new HashMap();

    AnswerLocalized parseAnswerLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.error("AnswerLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (AnswerLocalizedHash.containsKey(token)) {
            return (AnswerLocalized) AnswerLocalizedHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            // There is a named query - how do I use it?
            String q = "SELECT v FROM AnswerLocalized v WHERE v.answerString = :answerString";
            Query query = em.createQuery(q);
            query.setParameter("answerString", token);
            AnswerLocalized answerLocalized = null;
            try {
                answerLocalized = (AnswerLocalized) query.getSingleResult();
            } catch (NoResultException e) {
                logger.info("AnswerLocalized " + token + " Doesn't yet exist -- adding it");
                // How do I get the next AnswerLocalizedID value in lieu of auto_increment?
                answerLocalized = new AnswerLocalized();
                answerLocalized.setAnswerLocalizedID(new Integer(++AnswerLocalizedCounter));
                answerLocalized.setAnswerString(token);
                answerLocalized.setLanguageCode(languageCode);
                // FIXME - what about setAnswerID() - should a new one be created if none is found and the language is English?
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            AnswerLocalizedHash.put(token, answerLocalized);
            return answerLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            em.close();
        }
    }
    /**
    Find index for this HelpLocalized
    @return Null if token is empty, or Integer of HelpLocalized (adding an new HelpLocalizedID if needed)
     */
    private HashMap HelpLocalizedHash = new HashMap();

    HelpLocalized parseHelpLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.error("HelpLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (HelpLocalizedHash.containsKey(token)) {
            return (HelpLocalized) HelpLocalizedHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            // There is a named query - how do I use it?
            String q = "SELECT v FROM HelpLocalized v WHERE v.helpString = :helpString";
            Query query = em.createQuery(q);
            query.setParameter("helpString", token);
            HelpLocalized helpLocalized = null;
            try {
                helpLocalized = (HelpLocalized) query.getSingleResult();
            } catch (NoResultException e) {
                logger.info("HelpLocalized " + token + " Doesn't yet exist -- adding it");
                // How do I get the next HelpLocalizedID value in lieu of auto_increment?
                helpLocalized = new HelpLocalized();
                helpLocalized.setHelpLocalizedID(new Integer(++HelpLocalizedCounter));
                helpLocalized.setHelpString(token);
                helpLocalized.setLanguageCode(languageCode);
                // FIXME - what about setHelpID() - should a new one be created if none is found and the language is English?
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            HelpLocalizedHash.put(token, helpLocalized);
            return helpLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            em.close();
        }
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
        String actionType = token.substring(0, 0);
        if (actionType.equalsIgnoreCase("q") || actionType.equalsIgnoreCase("e") || actionType.equalsIgnoreCase("[") || actionType.equalsIgnoreCase("]")) {
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
            logger.error("FormatMask is blank");
            return null;
        }
        // FIXME - parse proper field of actionTypeString
        return null;
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

                    // FIXME - LEFT OFF HERE
                    // ensure all objects linked
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
                        AnswerLocalized answerLocalized = parseAnswerLocalized(msg, languageCode);
                        answer.getAnswerLocalizedCollection().add(answerLocalized);
                        answerLocalized.setAnswerID(answer);
                    } else {
                        if (answerListContents.size() < ansPos) {
                            // suggests that there are too many answers in this languageCounter?
                            logger.error("Language # " + languageCounter + " has more answer choices than prior languages");
                            // Add it anyway?
                        } else {
                            // Compare values from this language vs. those set for prior language
                            answerListContent = (AnswerListContent) answerListContents.toArray()[ansPos-1]; // FIXME - is this correct way to get index position/
                            if (!answerListContent.getValue().equals(val)) {
                                logger.error("Mismatch across languages - Position " + (ansPos-1) + " was set to " + answerListContent.getValue() + " but there is attempt to reset it to " + val);
                            }
                            Answer answer = answerListContent.getAnswerID();
                            AnswerLocalized answerLocalized = parseAnswerLocalized(msg, languageCode);
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

    /**
    Parse the responseOptions parameter to get the DisplayType
    @param responseOptions - the source string
    @return AnswerList
     */
    DisplayType parseDisplayType(String responseOptions) {
        if (responseOptions == null || responseOptions.trim().length() == 0) {
            logger.error("Missing DisplayType");
            return null;
        }

        StringTokenizer ans = new StringTokenizer(responseOptions, "|", true); // return '|' tokens too
        String token = null;

        // Determine the display type (first token)
        try {
            token = ans.nextToken();
        } catch (NoSuchElementException e) {
            logger.error("Missing Datatype", e);
        }
        if (DisplayTypeHash.containsKey(token)) {
            return (DisplayType) DisplayTypeHash.get(token);
        } else {
            logger.error("Invalid DisplayType" + token);
            return null;
        }
    }

    boolean writeFile(String filename) {
        if (filename == null || "".equals(filename.trim())) {
            return false;
        }
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-16"));
            out.write(this.contents);
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
            return languageCodes.get(i).substring(0,1);
        }
    }

    public String getContents() {
        return this.contents;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFormattedContents() {
        StringBuffer sf = new StringBuffer();

        sf.append("<TABLE BORDER='1'><TR>");
        for (int i = 0; i < this.numCols; ++i) {
            sf.append("<TH>Column ").append(i + 1).append("</TH>");
        }
        sf.append("</TR>");

        // iterate over rows, then columns
        StringTokenizer rows = new StringTokenizer(this.contents, "\n");

        while (rows.hasMoreTokens()) {
            sf.append("<TR>");
            StringTokenizer cols = new StringTokenizer(rows.nextToken(), "\t");
            int colCount = 0;
            while (cols.hasMoreTokens()) {
                ++colCount;
                sf.append("<TD>");
                String col = cols.nextToken();
                if ("".equals(col.trim())) {
                    sf.append("&nbsp;");
                } else {
                    sf.append(col);
                }
            }
            while (colCount < this.numCols) {
                ++colCount;
                sf.append("<TD>&nbsp;</TD>");
            }
            sf.append("</TR>");
        }
        sf.append("</TABLE>");
        return sf.toString();
    }

    public boolean getStatus() {
        return this.status;
    }

    private void persist(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            logger.debug("Before Persist");
            em.persist(object);
            logger.debug("After Persist");
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    private void merge(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            logger.debug("Before Merge");
            //em.persist(object);
            em.merge(object);
            logger.debug("After Merge");
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    private static HashMap ActionTypeHash = new HashMap();
    private static HashMap DataTypeHash = new HashMap();
    private static HashMap DisplayTypeHash = new HashMap();
    private static HashMap NullFlavorHash = new HashMap();
    private static HashMap ReservedWordHash = new HashMap();

    private void loadStaticContents() {
        if (InstrumentExcelLoader.UseCounter > 0) {
            return; // makes this a Singleton
        }
        EntityManager em = getEntityManager();
        String q;
        Query query;
        try {
            q = "SELECT rs FROM ActionType rs";
            query = em.createQuery(q);
            ListIterator<ActionType> ActionTypes = query.getResultList().listIterator();
            while (ActionTypes.hasNext()) {
                ActionType el = ActionTypes.next();
                ActionTypeHash.put(el.getActionName(), el);
            }

            q = "SELECT rs FROM DataType rs";
            query = em.createQuery(q);
            ListIterator<DataType> DataTypes = query.getResultList().listIterator();
            while (DataTypes.hasNext()) {
                DataType el = DataTypes.next();
                DataTypeHash.put(el.getDataType(), el);
            }

            q = "SELECT rs FROM DisplayType rs";
            query = em.createQuery(q);
            ListIterator<DisplayType> DisplayTypes = query.getResultList().listIterator();
            while (DisplayTypes.hasNext()) {
                DisplayType el = DisplayTypes.next();
                DisplayTypeHash.put(el.getDisplayType(), el);
            }

            q = "SELECT rs FROM NullFlavor rs";
            query = em.createQuery(q);
            ListIterator<NullFlavor> NullFlavors = query.getResultList().listIterator();
            while (NullFlavors.hasNext()) {
                NullFlavor el = NullFlavors.next();
                NullFlavorHash.put(el.getNullFlavor(), el);
            }

            q = "SELECT rs FROM ReservedWord rs";
            query = em.createQuery(q);
            ListIterator<ReservedWord> ReservedWords = query.getResultList().listIterator();
            while (ReservedWords.hasNext()) {
                ReservedWord el = ReservedWords.next();
                ReservedWordHash.put(el.getReservedWord(), el);
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            em.close();
        }
    }
}