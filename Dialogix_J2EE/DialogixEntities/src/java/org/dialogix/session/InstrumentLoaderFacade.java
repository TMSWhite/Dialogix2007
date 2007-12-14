/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import javax.ejb.Stateful;
import java.io.*;
import java.util.*;
import org.dialogix.entities.*;
import javax.persistence.*;
import java.util.logging.*;

/**
 */
@Stateful
public class InstrumentLoaderFacade implements InstrumentLoaderFacadeRemote, InstrumentLoaderFacadeLocal {

    @PersistenceContext
    private EntityManager em;
    private Logger logger = Logger.getLogger("org.dialogix.session.InstrumentLoaderFacadeBean");
    private boolean staticContentsLoaded = false;
    private HashMap<String, ActionType> ActionTypeHash = new HashMap<String, ActionType>();
    private HashMap<String, DataType> DataTypeHash = new HashMap<String, DataType>();
    private HashMap<String, DisplayType> DisplayTypeHash = new HashMap<String, DisplayType>();
    private HashMap<String, NullFlavor> NullFlavorHash = new HashMap<String, NullFlavor>();
    private boolean lastQuestionWasNew = false;  // FIXME - Hack
    private boolean lastAnswerListWasNew = false;    // FIXME - Hack

    private HashMap<String, ReservedWord> ReservedWordHash = new HashMap<String, ReservedWord>();
    private HashMap<String, VarName> VarNameHash = new HashMap<String, VarName>();
    private HashMap<String, LanguageList> LanguageListHash = new HashMap<String, LanguageList>();
    private HashMap<String, QuestionLocalized> QuestionLocalizedHash = new HashMap<String, QuestionLocalized>();
    private HashMap<String, AnswerLocalized> AnswerLocalizedHash = new HashMap<String, AnswerLocalized>();
    private HashMap<String, AnswerListDenormalized> AnswerListDenormalizedHash = new HashMap<String, AnswerListDenormalized>();
    private HashMap<String, HelpLocalized> HelpLocalizedHash = new HashMap<String, HelpLocalized>();
    private HashMap<String, ReadbackLocalized> ReadbackLocalizedHash = new HashMap<String, ReadbackLocalized>();
    private HashMap<String, Instrument> InstrumentHash = new HashMap<String, Instrument>();
    private HashMap<String, InstrumentVersion> InstrumentVersionHash = new HashMap<String, InstrumentVersion>();
    private HashMap<String, Validation> ValidationHash = new HashMap<String, Validation>();
    private HashMap<String, Item> ItemHash = new HashMap<String, Item>();
   
    /**
     * Find index for this ReservedWord
     * @param token
     * @return null if does not exist or token is empty
     */
    public ReservedWord parseReservedWord(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.info("ReservedWord is blank");
            return null;
        }
        if (ReservedWordHash.containsKey(token)) {
            return ReservedWordHash.get(token);
        } else {
            logger.severe("Invalid Reserved Word " + token); 
            return null;
        }
    }

    /**
     * Find index for this NullFlavor
     * @param token
     * @return null if does not exist or token is empty
     */
    public Integer parseNullFlavor(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("Value being tested for NullFlavor status is blank");
            return new Integer(0);
        }
        if (NullFlavorHash.containsKey(token)) {
            return NullFlavorHash.get(token).getNullFlavorID();
        } else {
            return new Integer(0);
        }
    }
    
    /**
     * Find index for this VarName, creating new ones as needed
     * @param token
     * @return a non-null  VarName, even if token is blank, creating new VarName if needed
     */
    public VarName parseVarName(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("VarName is blank");
            token = ""; // must have a value
        }
        /* First check whether it exists to avoid DB query */
        if (VarNameHash.containsKey(token)) {
            return VarNameHash.get(token);
        }

        String q = "SELECT v FROM VarName v WHERE v.varName = :varName";
        Query query = em.createQuery(q);
        query.setParameter("varName", token);
        VarName varName = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for VarName (" + list.size() + "):" + token);
            }            
            varName = (VarName) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found VarName: " + token);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New VarName: " + token);
            }
            varName = new VarName();
            varName.setVarName(token);
        }
        VarNameHash.put(token, varName);
        return varName;
    }

    /**
     * Find index for this LanguageList, creating new ones as needed
     * @param token
     * @return null if blank
     */
    public LanguageList parseLanguageList(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("LanguageList is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (LanguageListHash.containsKey(token)) {
            return LanguageListHash.get(token);
        }

        String q = "SELECT v FROM LanguageList v WHERE v.languageList = :languageList";
        Query query = em.createQuery(q);
        query.setParameter("languageList", token);
        LanguageList languageList = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for LanguageList (" + list.size() + "): " + token);
            }
            languageList = (LanguageList) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found LanguageList: " + token);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New LanguageList: " + token);
            }
            languageList = new LanguageList();
            languageList.setLanguageList(token);
        }
        LanguageListHash.put(token, languageList);
        return languageList;
    }

    /**
     * Find index for this QuestionLocalized, creating new ones as needed
     * @param token
     * @param languageCode
     * @return valid QuestionLocalized, even if blank; creating new objects as needed
     */
    public QuestionLocalized parseQuestionLocalized(String token, String languageCode) {
        lastQuestionWasNew = false;
        if (token == null || token.trim().length() == 0) {
            logger.severe("QuestionLocalized is blank");
            token = ""; // was return null;
        }
        String key = languageCode + token;
        /* First check whether it exists to avoid DB query */
        if (QuestionLocalizedHash.containsKey(key)) {
            return QuestionLocalizedHash.get(key);
        }

        String q = "SELECT v FROM QuestionLocalized v WHERE v.questionString = :questionString and v.languageCode = :languageCode";
        Query query = em.createQuery(q);
        query.setParameter("questionString", token);
        query.setParameter("languageCode", languageCode);
        QuestionLocalized questionLocalized = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for QuestionLocalized (" + list.size() + "): " + key);
            }            
            questionLocalized = (QuestionLocalized) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found QuestionLocalized: " + key);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New QuestionLocalized: " + key);
            }
            lastQuestionWasNew = true;
            questionLocalized = new QuestionLocalized();
            questionLocalized.setQuestionString(token);
            questionLocalized.setLanguageCode(languageCode);
        }
        QuestionLocalizedHash.put(key, questionLocalized);
        return questionLocalized;
    }

    /**
     * Find index of AnswerLocalized, creating new ones as needed
     * @param token
     * @param languageCode
     * @return valid object, even if token is blank.
     */
    public AnswerLocalized parseAnswerLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("AnswerLocalized is blank");
            token = ""; // was return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (AnswerLocalizedHash.containsKey(key)) {
            return AnswerLocalizedHash.get(key);
        }

        String q = "SELECT v FROM AnswerLocalized v WHERE v.answerString = :answerString and v.languageCode = :languageCode";
        Query query = em.createQuery(q);
        query.setParameter("answerString", token);
        query.setParameter("languageCode", languageCode);
        AnswerLocalized answerLocalized = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for AnswerLocalized (" + list.size() + "): " + key);
            }            
            answerLocalized = (AnswerLocalized) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found AnswerLocalized: " + key);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New AnswerLocalized: " + key);
            }
            answerLocalized = new AnswerLocalized();
            answerLocalized.setAnswerString(token);
            answerLocalized.setLanguageCode(languageCode);
        }
        AnswerLocalizedHash.put(key, answerLocalized);
        return answerLocalized;
    }

    /**
     * Find index of AnswerDenormalized, creating new ones as needed
     * @param token
     * @param languageCode
     * @return a valid object, even if blank.
     */
    public AnswerListDenormalized parseAnswerListDenormalized(String token, String languageCode) {
        lastAnswerListWasNew = false;
        if (token == null || token.trim().length() == 0) {
            logger.severe("AnswerListDenormalized is blank");
            token = ""; // was return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (AnswerListDenormalizedHash.containsKey(key)) {
            lastAnswerListWasNew = false;
            return AnswerListDenormalizedHash.get(key);
        }

        String q = "SELECT v FROM AnswerListDenormalized v WHERE v.answerListDenormalizedString = :answerListDenormalizedString and v.languageCode = :languageCode";
        Query query = em.createQuery(q);
        query.setParameter("answerListDenormalizedString", token);
        query.setParameter("languageCode", languageCode);
        AnswerListDenormalized answerListDenormalized = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for AnswerListDenormalized (" + list.size() + "): " + key);
            }            
            answerListDenormalized = (AnswerListDenormalized) list.get(0);
            lastAnswerListWasNew = false;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found AnswerListDenormalized: " + key);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New AnswerListDenormalized: " + key);
            }
            answerListDenormalized = new AnswerListDenormalized();
            answerListDenormalized.setAnswerListDenormalizedString(token);
            answerListDenormalized.setLanguageCode(languageCode);
            lastAnswerListWasNew = true;
        }
        AnswerListDenormalizedHash.put(key, answerListDenormalized);
        return answerListDenormalized;
    }

    /**
     * Find index of HelpLocalized, creating new ones as needed
     * @param token
     * @param languageCode
     * @return null if blank
     */
    public HelpLocalized parseHelpLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.fine("HelpLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (HelpLocalizedHash.containsKey(key)) {
            return HelpLocalizedHash.get(key);
        }

        String q = "SELECT v FROM HelpLocalized v WHERE v.helpString = :helpString and v.languageCode = :languageCode";
        Query query = em.createQuery(q);
        query.setParameter("helpString", token);
        query.setParameter("languageCode", languageCode);
        HelpLocalized helpLocalized = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for HelpLocalized (" + list.size() + "): " + key);
            }            
            helpLocalized = (HelpLocalized) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found HelpLocalized: " + key);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New HelpLocalized: " + key);
            }
            helpLocalized = new HelpLocalized();
            helpLocalized.setHelpString(token);
            helpLocalized.setLanguageCode(languageCode);
        }
        HelpLocalizedHash.put(key, helpLocalized);
        return helpLocalized;
    }

    /**
     * Find index of ReadbackLocalized, creating new ones as needed
     * @param token
     * @param languageCode
     * @return null if blank.
     */
    public ReadbackLocalized parseReadbackLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.fine("ReadbackLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (ReadbackLocalizedHash.containsKey(key)) {
            return ReadbackLocalizedHash.get(key);
        }

        String q = "SELECT v FROM ReadbackLocalized v WHERE v.readbackString = :readbackString and v.languageCode = :languageCode";
        Query query = em.createQuery(q);
        query.setParameter("readbackString", token);
        query.setParameter("languageCode", languageCode);
        ReadbackLocalized readbackLocalized = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for ReadbackLocalized (" + list.size() + "): " + key);
            }            
            readbackLocalized = (ReadbackLocalized) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found ReadbackLocalized: " + key);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New ReadbackLocalized: " + key);
            }
            readbackLocalized = new ReadbackLocalized();
            readbackLocalized.setReadbackString(token);
            readbackLocalized.setLanguageCode(languageCode);
        }
        ReadbackLocalizedHash.put(key, readbackLocalized);
        return readbackLocalized;
    }

    /**
     * Return ItemActionType, which is one of {q, e, [, ]}
     * @param token
     * @return
     */
    public String parseItemActionType(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("ItemActionType is blank");
            token = ""; // was return null;
        }
        String actionType = token.substring(0, 1);
        if (actionType.equalsIgnoreCase("q") || actionType.equalsIgnoreCase("[") || actionType.equalsIgnoreCase("]")) {
            return actionType.toLowerCase();
        } else if (actionType.equalsIgnoreCase("e")) {
            return actionType.toLowerCase();
        } else {
            logger.severe("Invalid ItemActionType " + token);
            return null;
        }
    }

    /**
     * Return index of ActionType
     * @param token
     * @return null if missing or invalid
     */
    public ActionType parseActionType(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("ActionType is blank");
            return null;
        }
        if (ActionTypeHash.containsKey(token)) {
            return ActionTypeHash.get(token);
        } else {
            logger.severe("Invalid ActionType " + token);
            return null;
        }
    }

    /**
     * Load static contents
     */
    public void init() {
        if (staticContentsLoaded == true) {
            return;
        }

        String q;
        Query query;
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
        logger.info("Successfully loaded vocabularies");
        staticContentsLoaded = true;    // even if an error is thrown
    }

    /**
     * Find index of DisplayType
     * @param token
     * @return null if blank or invalid
     */
    public DisplayType parseDisplayType(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("Missing DisplayType");
            return null;
        }
        if (DisplayTypeHash.containsKey(token)) {
            return DisplayTypeHash.get(token);
        } else {
            logger.severe("Invalid DisplayType " + token);
            return null;
        }
    }

    /**
     * Find index of Instrument by its name, creating a new one if needed
     * @param token
     * @return null if blank
     */
    public Instrument parseInstrument(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("Instrument is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (InstrumentHash.containsKey(token)) {
            return InstrumentHash.get(token);
        }

        String q = "SELECT v FROM Instrument v WHERE v.instrumentName = :instrumentName";
        Query query = em.createQuery(q);
        query.setParameter("instrumentName", token);
        Instrument instrument = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for Instrument (" + list.size() + "): " + token);
            }            
            instrument = (Instrument) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found Instrument: " + token);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New Instrument: " + token);
            }
            instrument = new Instrument();
            instrument.setInstrumentDescription("Instrument Description - blank, for now");
            instrument.setInstrumentName(token);
        }
        InstrumentHash.put(token, instrument);
        return instrument;
    }

    /**
     * Find index of InstrumentVersion by title and version, creating a new one if needed
     * @param title
     * @param token
     * @return null if title or version is blank
     */
    public InstrumentVersion parseInstrumentVersion(String title, String token) {
        if (token == null || token.trim().length() == 0) {
            logger.severe("Instrument Version is blank");
            return null;
        }
        if (title == null || title.trim().length() == 0) {
            logger.severe("Instrument Name is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (InstrumentVersionHash.containsKey(token)) {
            return InstrumentVersionHash.get(token);
        }

        Instrument instrument = parseInstrument(title); // need this to set relationship

        String q = "SELECT iv FROM InstrumentVersion AS iv JOIN iv.instrumentID as i WHERE i.instrumentName = :instrumentName AND iv.versionString = :versionString";
        Query query = em.createQuery(q);
        query.setParameter("instrumentName", title);
        query.setParameter("versionString", token);
        InstrumentVersion instrumentVersion = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for InstrumentVersion (" + list.size() + "): " + title + " (" + token + ")");
            }            
            instrumentVersion = (InstrumentVersion) list.get(0);
            // If something is retrieved, then this is an attempt to create a new instrument with the same Version ID
            if (logger.isLoggable(Level.INFO)) {
                logger.info("Instrument " + title + "(" + token + ") already exists");
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New InstrumentVersion: " + title + token);
            }
            instrumentVersion = new InstrumentVersion();
            instrumentVersion.setVersionString(token);
            instrumentVersion.setInstrumentNotes("blank Instrument Notes");
            instrumentVersion.setInstrumentStatus(new Integer(1));  // default to active
            instrumentVersion.setCreationTimeStamp(new Date(System.currentTimeMillis()));
            instrumentVersion.setHasLOINCcode(Boolean.FALSE);   // default
            instrumentVersion.setLoincNum("LoincNum");
            instrumentVersion.setInstrumentID(instrument);
        }
        InstrumentVersionHash.put(token, instrumentVersion);
        return instrumentVersion;
    }

    /**
     * Find index of Validation, creating new one if needed
     * @param minVal
     * @param maxVal
     * @param inputMask
     * @param otherVals
     * @return
     */
    public Validation parseValidation(String minVal, String maxVal, String inputMask, String otherVals) {
        if (minVal == null) {
            minVal = "";
        }
        if (maxVal == null) {
            maxVal = "";
        }
        if (inputMask == null) {
            inputMask = "";
        }
        if (otherVals == null) {
            otherVals = "";
        }
        String token = minVal + ";" + maxVal + ";" + inputMask + ";" + otherVals;

        if (ValidationHash.containsKey(token)) {
            return ValidationHash.get(token);
        }

        String q = "SELECT v FROM Validation v WHERE v.minVal = :minVal and v.maxVal = :maxVal and v.otherVals = :otherVals and v.inputMask = :inputMask";
        Query query = em.createQuery(q);
        query.setParameter("minVal", minVal);
        query.setParameter("maxVal", maxVal);
        query.setParameter("otherVals", otherVals);
        query.setParameter("inputMask", inputMask);
        Validation validation = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for Validation (" + list.size() + "): " + token);
            }            
            validation = (Validation) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found Validation: " + token);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New Validation: " + token);
            }
            validation = new Validation();
            validation.setMinVal(minVal);
            validation.setMaxVal(maxVal);
            validation.setOtherVals(otherVals);
            validation.setInputMask(inputMask);
        }
        ValidationHash.put(token, validation);
        return validation;
    }

    /**
     * Find item by its contents, creating new one if needed
     * @param newItem
     * @param questionString
     * @param answerListDenormalizedString
     * @param dataType
     * @param hasNewContents
     * @return
     */
    public Item findItem(Item newItem, String questionString, String answerListDenormalizedString, String dataType, boolean hasNewContents) {
        if (answerListDenormalizedString == null) {
            answerListDenormalizedString = "";
        }    // TODO - CHECK - may expect a null return
        String token = questionString + ";" + answerListDenormalizedString + ";" + dataType + ";" + newItem.getItemType();

        if (hasNewContents == true) {   // then must be new
            ItemHash.put(token, newItem);   // TODO - CHECK - will the contents of newItem be updated with proper IDs after a persist?
            if (logger.isLoggable(Level.INFO)) {
                logger.info("Adding New Item: " + token);
            }
            return newItem;
        }
        if (ItemHash.containsKey(token)) {
            return ItemHash.get(token);
        }

        String q = "SELECT v FROM Item v WHERE v.questionID = :questionID and v.dataTypeID = :dataTypeID and v.answerListID = :answerListID and v.itemType = :itemType";
        Query query = em.createQuery(q);
        query.setParameter("questionID", newItem.getQuestionID());  
        query.setParameter("dataTypeID", newItem.getDataTypeID());
        query.setParameter("answerListID", newItem.getAnswerListID());
        query.setParameter("itemType", newItem.getItemType());
        Item item = null;
        try {
            List list = query.getResultList();
            if (list.size() > 1) {
                logger.severe("Non-Unique Results for Item (" + list.size() + "): " + token);
            }            
            item = (Item) list.get(0);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Found Item: " + token);
            }
        } catch (IndexOutOfBoundsException e) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding New Item: " + token);
            }
            ItemHash.put(token, newItem);
            return newItem; // since contents already set
        }
        ItemHash.put(token, item);
        return item;    // return existing one
    }

    /**
     * Utility function to see whether a new Item is being created
     * @return
     */
    public boolean lastItemComponentsHadNewContent() {
        if (lastAnswerListWasNew == true || lastQuestionWasNew == true) {
            return true;
        }
        return false;
    }
    
    /**
     * Update Instrument contents
     * @param instrument
     */
    public void merge(Instrument instrument) {
        em.merge(instrument);
    }
}
