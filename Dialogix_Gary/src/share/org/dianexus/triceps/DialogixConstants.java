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
    private static HashMap<String,ActionType> ActionTypeHash = new HashMap<String,ActionType>();
    private static HashMap<String,DataType> DataTypeHash = new HashMap<String,DataType>();
    private static HashMap<String,DisplayType> DisplayTypeHash = new HashMap<String,DisplayType>();
    private static HashMap<String,NullFlavor> NullFlavorHash = new HashMap<String,NullFlavor>();

    /**
     */
    private DialogixConstants() {
    }

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
    private static HashMap<String,ReservedWord> ReservedWordHash = new HashMap<String,ReservedWord>(); 
     
    public static ReservedWord parseReservedWord(String token) {
        if (token == null || token.trim().length() == 0) {
            logger.info("ReservedWord is blank");
            return null;
        }
        if (ReservedWordHash.containsKey(token)) {
            return ReservedWordHash.get(token);
        } else {
            logger.error("Invalid Reserved Word " + token);
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
    private static HashMap<String,VarName> VarNameHash = new HashMap<String,VarName>(); 

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
                varName.setVarName(token);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            VarNameHash.put(token, varName);
            return varName;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    }
    
    /**
    Find index for this LanguageList
    @return Null if token is empty, or Integer of LanguageList (adding an new LanguageListID if needed)
     */
    private static HashMap<String,LanguageList> LanguageListHash = new HashMap<String,LanguageList>();

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
            // There is a named query - how do I use it?
            String q = "SELECT v FROM LanguageList v WHERE v.languageList = :languageList";
            Query query = em.createQuery(q);
            query.setParameter("languageList", token);
            LanguageList languageList = null;
            try {
                languageList = (LanguageList) query.getSingleResult();
            } catch (NoResultException e) {
                logger.info("LanguageList " + token + " Doesn't yet exist -- adding it");
                // How do I get the next LanguageListID value in lieu of auto_increment?
                languageList = new LanguageList();
                languageList.setLanguageList(token);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            LanguageListHash.put(token, languageList);
            return languageList;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    }    
    /**
    Find index for this QuestionLocalized
    @return Null if token is empty, or Integer of QuestionLocalized (adding an new QuestionLocalizedID if needed)
     */
    private static HashMap<String,QuestionLocalized> QuestionLocalizedHash = new HashMap<String,QuestionLocalized>();

    public static QuestionLocalized parseQuestionLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.error("QuestionLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (QuestionLocalizedHash.containsKey(token)) {
            return QuestionLocalizedHash.get(token);
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
                questionLocalized.setQuestionString(token);
                questionLocalized.setLanguageCode(languageCode);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            QuestionLocalizedHash.put(token, questionLocalized);
            return questionLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    }
    /**
    Find index for this AnswerLocalized
    @return Null if token is empty, or Integer of AnswerLocalized (adding an new AnswerLocalizedID if needed)
     */
    private static HashMap<String,AnswerLocalized> AnswerLocalizedHash = new HashMap<String,AnswerLocalized>();

    public static AnswerLocalized parseAnswerLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.error("AnswerLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (AnswerLocalizedHash.containsKey(token)) {
            return AnswerLocalizedHash.get(token);
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
                answerLocalized.setAnswerString(token);
                answerLocalized.setLanguageCode(languageCode);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            AnswerLocalizedHash.put(token, answerLocalized);
            return answerLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    }
    /**
    Find index for this HelpLocalized
    @return Null if token is empty, or Integer of HelpLocalized (adding an new HelpLocalizedID if needed)
     */
    private static HashMap<String,HelpLocalized> HelpLocalizedHash = new HashMap<String,HelpLocalized>();

    public static HelpLocalized parseHelpLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.debug("HelpLocalized is blank");
            token = "";
//            return null;
        }
        /* First check whether it exists to avoid DB query */
        if (HelpLocalizedHash.containsKey(token)) {
            return HelpLocalizedHash.get(token);
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
                helpLocalized.setHelpString(token);
                helpLocalized.setLanguageCode(languageCode);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            HelpLocalizedHash.put(token, helpLocalized);
            return helpLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e);  }
        }
        }
    
    /**
    Find index for this ReadbackLocalized
    @return Null if token is empty, or Integer of ReadbackLocalized (adding an new ReadbackLocalizedID if needed)
     */
    private static HashMap<String,ReadbackLocalized> ReadbackLocalizedHash = new HashMap<String,ReadbackLocalized>();

    public static ReadbackLocalized parseReadbackLocalized(String token, String languageCode) {
        if (token == null || token.trim().length() == 0) {
            logger.debug("ReadbackLocalized is blank");
            token = "";
 //           return null;
        }
        /* First check whether it exists to avoid DB query */
        if (ReadbackLocalizedHash.containsKey(token)) {
            return ReadbackLocalizedHash.get(token);
        }
        EntityManager em = getEntityManager();
        try {
            // There is a named query - how do I use it?
            String q = "SELECT v FROM ReadbackLocalized v WHERE v.readbackString = :readbackString";
            Query query = em.createQuery(q);
            query.setParameter("readbackString", token);
            ReadbackLocalized readbackLocalized = null;
            try {
                readbackLocalized = (ReadbackLocalized) query.getSingleResult();
            } catch (NoResultException e) {
                logger.info("ReadbackLocalized " + token + " Doesn't yet exist -- adding it");
                // How do I get the next ReadbackLocalizedID value in lieu of auto_increment?
                readbackLocalized = new ReadbackLocalized();
                readbackLocalized.setReadbackString(token);
                readbackLocalized.setLanguageCode(languageCode);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            ReadbackLocalizedHash.put(token, readbackLocalized);
            return readbackLocalized;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
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
        if (staticContentsLoaded == true)
            return;
        
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
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
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
            em.getTransaction().rollback();
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
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
            em.getTransaction().rollback();
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    }
    
    
    /** below here are hacked functions */
    static public InstrumentContent getDefaultInstrumentContent() {
        EntityManager em = getEntityManager();
        Integer Id = 1; // hack for test
        try {
            InstrumentContent instrumentContent = em.find(InstrumentContent.class, Id);
            return instrumentContent;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
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
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    }
    
    /**
    Find index for this Instrument
    @return Null if token is empty, or Integer of Instrument (adding an new InstrumentID if needed)
     */
    private static HashMap<String,Instrument> InstrumentHash = new HashMap<String,Instrument>(); 

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
            // There is a named query - how do I use it?
            String q = "SELECT v FROM Instrument v WHERE v.instrumentName = :instrumentName";
            Query query = em.createQuery(q);
            query.setParameter("instrumentName", token);
            Instrument instrument = null;
            try {
                instrument = (Instrument) query.getSingleResult();
            } catch (NoResultException e) {
                logger.info("Instrument " + token + " Doesn't yet exist -- adding it");
                // How do I get the next InstrumentID value in lieu of auto_increment?
                instrument = new Instrument();
                instrument.setInstrumentDescription("Instrument Description - blank, for now");                
                instrument.setInstrumentName(token);
                // Can I avoid persisting this until instrument is fully loaded?  What about concurrent requests for same IDs?
            }
            InstrumentHash.put(token, instrument);
            return instrument;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    } 
    
    /**
    Find index for this InstrumentVersion
    @return Null if token is empty, or Integer of InstrumentVersion (adding an new InstrumentVersionID if needed)
     */
    private static HashMap<String,InstrumentVersion> InstrumentVersionHash = new HashMap<String,InstrumentVersion>(); 

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
            
            // There is a named query - how do I use it?
            String q = "SELECT iv FROM InstrumentVersion AS iv JOIN iv.instrumentID as i WHERE i.instrumentName = :instrumentName AND iv.versionString = :versionString";            
            Query query = em.createQuery(q);
            query.setParameter("instrumentName", title);
            query.setParameter("versionString", token);
            InstrumentVersion instrumentVersion = null;
            try {
                instrumentVersion = (InstrumentVersion) query.getSingleResult();
                
                // If something is retrieved, then this is an attempt to create a new instrument with the same Version ID
                throw new Exception("Instrument " + title + "(" + token + ") already exists");
            } catch (NoResultException e) {
                logger.info("InstrumentVersion " + token + " Doesn't yet exist -- adding it");
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
            try { em.close(); }  catch(Exception e) { logger.error("", e); }
        }
    }       
 }