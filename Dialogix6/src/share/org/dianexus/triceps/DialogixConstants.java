 /* ********************************************************
 ** Copyright (c) 2000-2007, Thomas Maxwell White, all rights reserved.
 ** $Header$
 ******************************************************** */
package org.dianexus.triceps;

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
public class DialogixConstants implements java.io.Serializable {

    private static Logger logger = Logger.getLogger(DialogixConstants.class);
    private static boolean staticContentsLoaded = false;
    private static EntityManagerFactory emf;
    private static HashMap<String, ActionType> ActionTypeHash = new HashMap<String, ActionType>();
    private static HashMap<String, DataType> DataTypeHash = new HashMap<String, DataType>();
    private static HashMap<String, DisplayType> DisplayTypeHash = new HashMap<String, DisplayType>();
    private static HashMap<String, NullFlavor> NullFlavorHash = new HashMap<String, NullFlavor>();
    private static boolean lastQuestionWasNew = false;  // FIXME - Hack
    private static boolean lastAnswerListWasNew = false;    // FIXME - Hack

    /**
     */
    private DialogixConstants() {
    }

    /**
    Initialize the Entity manager
     */
    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DialogixDomainPU");
        }
        return emf.createEntityManager();
    }
    /**
    Find index for this ReservedWord
    @return Null if token is empty or ReservedWord does not exist; or Integer of ReservedWord
     */
    private static HashMap<String, ReservedWord> ReservedWordHash = new HashMap<String, ReservedWord>();

    public static ReservedWord parseReservedWord(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.info("ReservedWord is blank");
            return null;
        }
        if (ReservedWordHash.containsKey(token)) {
            return ReservedWordHash.get(token);
        } else {
            logger.error("Invalid Reserved Word " + token); // FIXME - throw error which, when caught, will show location of error in source file
            return null;
        }
    }

    /**
    Find index for this NullFlavor
    @return Null if token is empty or NullFlavor does not exist; or Integer of NullFlavor
     */
    public static Integer parseNullFlavor(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("Value being tested for NullFlavor status is blank");
            return new Integer(0);
        }
        if (NullFlavorHash.containsKey(token)) {
            return NullFlavorHash.get(token).getNullFlavorID();
        } else {
            return new Integer(0);
        }
    }
    /**
    Find index for this VarName
    @return Null if token is empty, or Integer of VarName (adding an new VarNameID if needed)
     */
    private static HashMap<String, VarName> VarNameHash = new HashMap<String, VarName>();

    public static VarName parseVarName(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("VarName is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (VarNameHash.containsKey(token)) {
            return VarNameHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM VarName v WHERE v.varName = :varName";
            Query query = em.createQuery(q);
            query.setParameter("varName", token);
            VarName varName = null;
            try {
                varName = (VarName) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found VarName: " + token);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New VarName: " + token);
                }
                varName = new VarName();
                varName.setVarName(token);
                persist(varName); // Persist *and* return inserted record
            // What about concurrent requests for same IDs?
            // Container transation will handle concurrent clients
            }
            VarNameHash.put(token, varName);
            return varName;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
    /**
    Find index for this LanguageList
    @return Null if token is empty, or Integer of LanguageList (adding an new LanguageListID if needed)
     */
    private static HashMap<String, LanguageList> LanguageListHash = new HashMap<String, LanguageList>();

    public static LanguageList parseLanguageList(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("LanguageList is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (LanguageListHash.containsKey(token)) {
            return LanguageListHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM LanguageList v WHERE v.languageList = :languageList";
            Query query = em.createQuery(q);
            query.setParameter("languageList", token);
            LanguageList languageList = null;
            try {
                languageList = (LanguageList) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found LanguageList: " + token);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New LanguageList: " + token);
                }
                languageList = new LanguageList();
                languageList.setLanguageList(token);
                persist(languageList); // Persist *and* return inserted record
            }
            LanguageListHash.put(token, languageList);
            return languageList;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
    /**
    Find index for this QuestionLocalized.  This presumes that the same string can be used in different languages 
    @return Null if token is empty, or Integer of QuestionLocalized (adding an new QuestionLocalizedID if needed)
     */
    private static HashMap<String, QuestionLocalized> QuestionLocalizedHash = new HashMap<String, QuestionLocalized>();

    public static QuestionLocalized parseQuestionLocalized(String token, String languageCode) {
        lastQuestionWasNew = false;
        if (token == null || token.trim().length() == 0) {
            logger.error("QuestionLocalized is blank");
            return null;
        }
        String key = languageCode + token;
        /* First check whether it exists to avoid DB query */
        if (QuestionLocalizedHash.containsKey(key)) {
            return QuestionLocalizedHash.get(key);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM QuestionLocalized v WHERE v.questionString = :questionString and v.languageCode = :languageCode";
            Query query = em.createQuery(q);
            query.setParameter("questionString", token);
            query.setParameter("languageCode", languageCode);
            QuestionLocalized questionLocalized = null;
            try {
                questionLocalized = (QuestionLocalized) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found QuestionLocalized: " + key);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New QuestionLocalized: " + key);
                }
                lastQuestionWasNew = true;
                questionLocalized = new QuestionLocalized();
                questionLocalized.setQuestionString(token);
                questionLocalized.setLanguageCode(languageCode);
//                persist(questionLocalized); // Persist *and* return inserted record
            // CHECK What about setting the Question ID for this? -- it is done by calling routine?
            // What about concurrent requests for same IDs?
            }
            QuestionLocalizedHash.put(key, questionLocalized);
            return questionLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
    /**
    Find index for this AnswerLocalized. This presumes that the same string can be used in different languages
    @return Null if token is empty, or Integer of AnswerLocalized (adding an new AnswerLocalizedID if needed)
     */
    private static HashMap<String, AnswerLocalized> AnswerLocalizedHash = new HashMap<String, AnswerLocalized>();

    public static AnswerLocalized parseAnswerLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.error("AnswerLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (AnswerLocalizedHash.containsKey(key)) {
            return AnswerLocalizedHash.get(key);
        }
        EntityManager em = getEntityManager();
        // CHECK - should a new Answer_ID be created for each new AnswerLocalized?  No, since don't know what it is attached to!
        try {
            String q = "SELECT v FROM AnswerLocalized v WHERE v.answerString = :answerString and v.languageCode = :languageCode";
            Query query = em.createQuery(q);
            query.setParameter("answerString", token);
            query.setParameter("languageCode", languageCode);
            AnswerLocalized answerLocalized = null;
            try {
                answerLocalized = (AnswerLocalized) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found AnswerLocalized: " + key);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New AnswerLocalized: " + key);
                }
                // How do I get the next AnswerLocalizedID value in lieu of auto_increment?
                answerLocalized = new AnswerLocalized();
                answerLocalized.setAnswerString(token);
                answerLocalized.setLanguageCode(languageCode);
//                persist(answerLocalized); // Persist *and* return inserted record
            }
            AnswerLocalizedHash.put(key, answerLocalized);
            return answerLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
    /**
    Find index for this AnswerListDenormalized. This presumes that the same string can be used in different languages
    @return Null if token is empty, or Integer of AnswerListDenormalized (adding an new AnswerListDenormalizedID if needed)
     */
    private static HashMap<String, AnswerListDenormalized> AnswerListDenormalizedHash = new HashMap<String, AnswerListDenormalized>();

    public static AnswerListDenormalized parseAnswerListDenormalized(String token, String languageCode) {
        lastAnswerListWasNew = false;
        if (token == null || token.trim().length() == 0) {
            logger.error("AnswerListDenormalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (AnswerListDenormalizedHash.containsKey(key)) {
            lastAnswerListWasNew = false;
            return AnswerListDenormalizedHash.get(key);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM AnswerListDenormalized v WHERE v.answerListDenormalizedString = :answerListDenormalizedString and v.languageCode = :languageCode";
            Query query = em.createQuery(q);
            query.setParameter("answerListDenormalizedString", token);
            query.setParameter("languageCode", languageCode);
            AnswerListDenormalized answerListDenormalized = null;
            try {
                answerListDenormalized = (AnswerListDenormalized) query.getSingleResult();
                lastAnswerListWasNew = false;
                if (logger.isDebugEnabled()) {
                    logger.debug("Found AnswerListDenormalized: " + key);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New AnswerListDenormalized: " + key);
                }
                // How do I get the next AnswerListDenormalizedID value in lieu of auto_increment?
                answerListDenormalized = new AnswerListDenormalized();
                answerListDenormalized.setAnswerListDenormalizedString(token);
                answerListDenormalized.setLanguageCode(languageCode);
//               persist(answerListDenormalized); // Persist *and* return inserted record
                lastAnswerListWasNew = true;
            }
            AnswerListDenormalizedHash.put(key, answerListDenormalized);
            return answerListDenormalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
    /**
    Find index for this HelpLocalized.  This presumes that the same string can be used in different languages
    @return Null if token is empty, or Integer of HelpLocalized (adding an new HelpLocalizedID if needed)
     */
    private static HashMap<String, HelpLocalized> HelpLocalizedHash = new HashMap<String, HelpLocalized>();

    public static HelpLocalized parseHelpLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.debug("HelpLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (HelpLocalizedHash.containsKey(key)) {
            return HelpLocalizedHash.get(key);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM HelpLocalized v WHERE v.helpString = :helpString and v.languageCode = :languageCode";
            Query query = em.createQuery(q);
            query.setParameter("helpString", token);
            query.setParameter("languageCode", languageCode);
            HelpLocalized helpLocalized = null;
            try {
                helpLocalized = (HelpLocalized) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found HelpLocalized: " + key);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New HelpLocalized: " + key);
                }
                helpLocalized = new HelpLocalized();
                helpLocalized.setHelpString(token);
                helpLocalized.setLanguageCode(languageCode);
            }
            HelpLocalizedHash.put(key, helpLocalized);
            return helpLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
    /**
    Find index for this ReadbackLocalized.  This presumes that the same string can be used in different languages
    @return Null if token is empty, or Integer of ReadbackLocalized (adding an new ReadbackLocalizedID if needed)
     */
    private static HashMap<String, ReadbackLocalized> ReadbackLocalizedHash = new HashMap<String, ReadbackLocalized>();

    public static ReadbackLocalized parseReadbackLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.debug("ReadbackLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (ReadbackLocalizedHash.containsKey(key)) {
            return ReadbackLocalizedHash.get(key);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM ReadbackLocalized v WHERE v.readbackString = :readbackString and v.languageCode = :languageCode";
            Query query = em.createQuery(q);
            query.setParameter("readbackString", token);
            query.setParameter("languageCode", languageCode);
            ReadbackLocalized readbackLocalized = null;
            try {
                readbackLocalized = (ReadbackLocalized) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found ReadbackLocalized: " + key);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New ReadbackLocalized: " + key);
                }
                readbackLocalized = new ReadbackLocalized();
                readbackLocalized.setReadbackString(token);
                readbackLocalized.setLanguageCode(languageCode);
                persist(readbackLocalized); // Persist *and* return inserted record
            }
            ReadbackLocalizedHash.put(key, readbackLocalized);
            return readbackLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    /**
    Return ItemActionType, which is one of {q, e, [, ]}
    @return actionType
     */
    public static String parseItemActionType(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("ItemActionType is blank");
            return null;
        }
        String actionType = token.substring(0, 1);
        if (actionType.equalsIgnoreCase("q") || actionType.equalsIgnoreCase("[") || actionType.equalsIgnoreCase("]")) {
            return actionType.toLowerCase();
        } else if (actionType.equalsIgnoreCase("e")) {
            return actionType.toLowerCase();
        } else {
            logger.error("Invalid ItemActionType " + token);
            return null;
        }
    }

    /**
    Return ActionType
    @return actionType
     */
    public static ActionType parseActionType(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("ActionType is blank");
            return null;
        }
        if (ActionTypeHash.containsKey(token)) {
            return ActionTypeHash.get(token);
        } else {
            logger.error("Invalid ActionType " + token);
            return null;
        }
    }

    public static void init() {
        if (staticContentsLoaded == true) {
            return;
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
            logger.info("Successfully loaded static vocabularies");
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        staticContentsLoaded = true;    // even if an error is thrown
    }

    /**
    Parse the token parameter to get the DisplayType
    @param the displayType to parse
    @return DisplayType, or NULL if doesn't exist
     */
    static DisplayType parseDisplayType(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("Missing DisplayType");
            return null;
        }
        if (DisplayTypeHash.containsKey(token)) {
            return DisplayTypeHash.get(token);
        } else {
            logger.error("Invalid DisplayType " + token);
            return null;
        }
    }

    static public void persist(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            logger.debug("Before Persist");
            em.persist(object);
            logger.debug("After Persist");
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("", e);
//            em.getTransaction().rollback();
        } finally {
            try {
                em.close();
            } catch (Exception e) {
            }
        }
    }

    static public void merge(Object object) {
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
//            em.getTransaction().rollback();
        } finally {
            try {
                em.close();
            } catch (Exception e) {
            }
        }
    }

    static public User getDefaultUserID() {
        EntityManager em = getEntityManager();
        Integer Id = 1; // hack for test
        try {
            User user = em.find(User.class, Id);
            return user;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
            }
        }
    }
    /**
    Find index for this Instrument
    @return Null if token is empty, or Integer of Instrument (adding an new InstrumentID if needed)
     */
    private static HashMap<String, Instrument> InstrumentHash = new HashMap<String, Instrument>();

    public static Instrument parseInstrument(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("Instrument is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (InstrumentHash.containsKey(token)) {
            return InstrumentHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM Instrument v WHERE v.instrumentName = :instrumentName";
            Query query = em.createQuery(q);
            query.setParameter("instrumentName", token);
            Instrument instrument = null;
            try {
                instrument = (Instrument) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found Instrument: " + token);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New Instrument: " + token);
                }
                // How do I get the next InstrumentID value in lieu of auto_increment?
                instrument = new Instrument();
                instrument.setInstrumentDescription("Instrument Description - blank, for now");
                instrument.setInstrumentName(token);
            }
            InstrumentHash.put(token, instrument);
            return instrument;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
            }
        }
    }
    /**
    Find index for this InstrumentVersion
    @return Null if token is empty, or Integer of InstrumentVersion (adding an new InstrumentVersionID if needed)
     */
    private static HashMap<String, InstrumentVersion> InstrumentVersionHash = new HashMap<String, InstrumentVersion>();

    public static InstrumentVersion parseInstrumentVersion(String title, String token) {
        if (token == null || token.trim().length() == 0) {
            logger.error("Instrument Version is blank");
            return null;
        }
        if (title == null || title.trim().length() == 0) {
            logger.error("Instrument Name is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (InstrumentVersionHash.containsKey(token)) {
            return InstrumentVersionHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            Instrument instrument = parseInstrument(title); // need this to set relationship

            String q = "SELECT iv FROM InstrumentVersion AS iv JOIN iv.instrumentID as i WHERE i.instrumentName = :instrumentName AND iv.versionString = :versionString";
            Query query = em.createQuery(q);
            query.setParameter("instrumentName", title);
            query.setParameter("versionString", token);
            InstrumentVersion instrumentVersion = null;
            try {
                instrumentVersion = (InstrumentVersion) query.getSingleResult();
                // If something is retrieved, then this is an attempt to create a new instrument with the same Version ID
                if (logger.isDebugEnabled()) {
                    logger.debug("Found InstrumentVersion: " + title + token);
                }
                throw new Exception("Instrument " + title + "(" + token + ") already exists");
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New InstrumentVersion: " + title + token);
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
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
            }
        }
    }
    /**
    Find index for this Validation
    @return Null if token is empty, or Integer of Validation (adding an new ValidationID if needed)
     */
    private static HashMap<String, Validation> ValidationHash = new HashMap<String, Validation>();

    public static Validation parseValidation(String minVal, String maxVal, String inputMask, String otherVals) {
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
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM Validation v WHERE v.minVal = :minVal and v.maxVal = :maxVal and v.otherVals = :otherVals and v.inputMask = :inputMask";
            Query query = em.createQuery(q);
            query.setParameter("minVal", minVal);
            query.setParameter("maxVal", maxVal);
            query.setParameter("otherVals", otherVals);
            query.setParameter("inputMask", inputMask);
            Validation validation = null;
            try {
                validation = (Validation) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found Validation: " + token);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New Validation: " + token);
                }
                validation = new Validation();
                validation.setMinVal(minVal);
                validation.setMaxVal(maxVal);
                validation.setOtherVals(otherVals);
                validation.setInputMask(inputMask);
            }
            ValidationHash.put(token, validation);
            return validation;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
            }
        }
    }
    /**
    Find index for this Item
    @return Null if token is empty, or Integer of Item (adding an new ItemID if needed)
     */
    private static HashMap<String, Item> ItemHash = new HashMap<String, Item>();

    public static Item findItem(Item newItem, String questionString, String answerListDenormalizedString, String dataType, boolean hasNewContents) {
        if (answerListDenormalizedString == null) {
            answerListDenormalizedString = "";
        }    // CHECK - may expect a null return
        String token = questionString + ";" + answerListDenormalizedString + ";" + dataType + ";" + newItem.getItemType();

        if (hasNewContents == true) {   // then must be new
            ItemHash.put(token, newItem);   // CHECK - will the contents of newItem be updated with proper IDs after a persist?
            if (logger.isInfoEnabled()) {
                logger.info("Adding New Item: " + token);
            }
            DialogixConstants.persist(newItem);
            return newItem;
        }
        if (ItemHash.containsKey(token)) {
            return ItemHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            String q = "SELECT v FROM Item v WHERE v.questionID = :questionID and v.dataTypeID = :dataTypeID and v.answerListID = :answerListID and v.itemType = :itemType";
            Query query = em.createQuery(q);
            query.setParameter("questionID", newItem.getQuestionID());  // CHECK does this use Integer or Objects?
            query.setParameter("dataTypeID", newItem.getDataTypeID());
            query.setParameter("answerListID", newItem.getAnswerListID());
            query.setParameter("itemType", newItem.getItemType());
            Item item = null;
            try {
                item = (Item) query.getSingleResult();
                if (logger.isDebugEnabled()) {
                    logger.debug("Found Item: " + token);
                }
            } catch (NoResultException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding New Item: " + token);
                }
                DialogixConstants.persist(newItem);
                ItemHash.put(token, newItem);
                return newItem; // since contents already set
            }
            ItemHash.put(token, item);
            return item;    // return existing one
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try {
                em.close();
            } catch (Exception e) {
            }
        }
    }

    static boolean lastItemComponentsHadNewContent() {
        if (lastAnswerListWasNew == true || lastQuestionWasNew == true) {
            return true;
        }
        return false;
    }
}
