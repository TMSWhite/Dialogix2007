/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.*;
import org.dialogix.entities.*;
import javax.persistence.*;
import java.util.logging.*;
import java.util.regex.PatternSyntaxException;

/**
 */
//@Stateful
public class InstrumentLoaderFacade implements java.io.Serializable {

//    @PersistenceContext
    private EntityManagerFactory _emf = null;
    private EntityManager _em = null;
//    private Logger logger = Logger.getLogger("org.dialogix.session.InstrumentLoaderFacadeBean");
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
    private HashMap<String, AnswerListDenorm> AnswerListDenormHash = new HashMap<String, AnswerListDenorm>();
    private HashMap<String, HelpLocalized> HelpLocalizedHash = new HashMap<String, HelpLocalized>();
    private HashMap<String, ReadbackLocalized> ReadbackLocalizedHash = new HashMap<String, ReadbackLocalized>();
    private HashMap<String, Instrument> InstrumentHash = new HashMap<String, Instrument>();
    private HashMap<String, InstrumentVersion> InstrumentVersionHash = new HashMap<String, InstrumentVersion>();
    private HashMap<String, Validation> ValidationHash = new HashMap<String, Validation>();
    private HashMap<String, Item> ItemHash = new HashMap<String, Item>();

    private EntityManager getEM() {
        if (_emf == null) {
            _emf = Persistence.createEntityManagerFactory("DialogixEntitiesPU");
        }
        if (_em == null) {
            _em = _emf.createEntityManager();
        }
        return _em;
    }

    private void closeEM(EntityManager em) {
        if (em != null) {
            em.close();
            _em = null;
        }
    }

    /**
     * Find index for this ReservedWord
     * @param token
     * @return null if does not exist or token is empty
     */
    public ReservedWord parseReservedWord(String token) throws InstrumentLoadException  {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("ReservedWord is blank",Level.WARNING,null);
        }
        if (ReservedWordHash.containsKey(token)) {
            return ReservedWordHash.get(token);
        } else {
            throw new InstrumentLoadException("Invalid Reserved Word " + token, Level.SEVERE,null);
        }
    }

    /**
     * Find index for this NullFlavor
     * @param token
     * @return null if does not exist or token is empty
     */
    public Integer parseNullFlavor(String token) throws InstrumentLoadException  {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("Value being tested for NullFlavor status is blank", Level.SEVERE, new Integer(0));
        }
        if (NullFlavorHash.containsKey(token)) {
            return NullFlavorHash.get(token).getNullFlavorId();
        } else {
            throw new InstrumentLoadException("Invalid NullFlavor " + token, Level.SEVERE, new Integer(0));
        }
    }

    /**
     * Find index for this VarName, creating new ones as needed
     * @param token
     * @return a non-null  VarName, even if token is blank, creating new VarName if needed
     */
    public VarName parseVarName(String token) throws InstrumentLoadException {
        String err = null;
        if (token == null || token.trim().length() == 0) {
            err = "VarName is blank";
            token = ""; // must have a value
        }
        /* First check whether it exists to avoid DB query */
        if (VarNameHash.containsKey(token)) {
            if (err != null) {
                return VarNameHash.get(token);
            } else {
                throw new InstrumentLoadException(err, Level.WARNING, VarNameHash.get(token));
            }
        }

        String q = "SELECT v FROM VarName v WHERE v.varName = :varName";
        EntityManager em = getEM();
        VarName varName = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("varName", token);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i = 0; i < list.size(); ++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((VarName) list.get(i)).getVarNameId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for VarName (" + list.size() + "):" + token + sb.toString(), Level.SEVERE, list.get(0));
            }
            varName = (VarName) list.get(0);
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New VarName: " + token);
//            }
            varName = new VarName();
            varName.setVarName(token);
        } finally {
            closeEM(em);
        }
        VarNameHash.put(token, varName);
        return varName;
    }

    /**
     * Find index for this LanguageList, creating new ones as needed
     * @param token
     * @return null if blank
     */
    public LanguageList parseLanguageList(String token) throws InstrumentLoadException  {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("LanguageList is blank", Level.SEVERE, null);
        }
        /* First check whether it exists to avoid DB query */
        if (LanguageListHash.containsKey(token)) {
            return LanguageListHash.get(token);
        }

        String q = "SELECT v FROM LanguageList v WHERE v.languageList = :languageList";
        EntityManager em = getEM();
        LanguageList languageList = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("languageList", token);
                    List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((LanguageList) list.get(i)).getLanguageListId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for LanguageList (" + list.size() + "): " + token + sb.toString(), Level.SEVERE, list.get(0));
            }
            languageList = (LanguageList) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found LanguageList: " + token);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New LanguageList: " + token);
//            }
            languageList = new LanguageList();
            languageList.setLanguageList(token);
        } finally {
            closeEM(em);
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
    public QuestionLocalized parseQuestionLocalized(String token, String languageCode) throws InstrumentLoadException {
        lastQuestionWasNew = false;
        if (token == null || token.trim().length() == 0) {
//            logger.fine("QuestionLocalized is blank");
            token = ""; // was return null;
        }
        String key = languageCode + token;
        /* First check whether it exists to avoid DB query */
        if (QuestionLocalizedHash.containsKey(key)) {
            return QuestionLocalizedHash.get(key);
        }

        String q = "SELECT v FROM QuestionLocalized v WHERE v.questionString = :questionString and v.languageCode = :languageCode";
        EntityManager em = getEM();
        QuestionLocalized questionLocalized = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("questionString", token);
            query.setParameter("languageCode", languageCode);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((QuestionLocalized) list.get(i)).getQuestionLocalizedId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for QuestionLocalized (" + list.size() + "): " + key + sb.toString(), Level.SEVERE, list.get(0));
            }
            questionLocalized = (QuestionLocalized) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found QuestionLocalized: " + key);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New QuestionLocalized: " + key);
//            }
            lastQuestionWasNew = true;
            questionLocalized = new QuestionLocalized();
            questionLocalized.setQuestionString(token);
            questionLocalized.setLanguageCode(languageCode);
            questionLocalized.setQuestionLength(getStringLengthWithoutHtml(token));
        } finally {
            closeEM(em);
        }
        QuestionLocalizedHash.put(key, questionLocalized);
        return questionLocalized;
    }

    private int getStringLengthWithoutHtml(String _token) {
        String token;
        try {
            token = _token.replaceAll("<[/]?[a-zA-Z0-9-]+.*?>", "").replaceAll("&[a-zA-Z0-9]+?;", ""); // remove HTML tags & entities
            return token.trim().length();
        } catch(PatternSyntaxException pe) {
//            logger.severe("Invalid Regex to remove HTML tags from string" + _token);
            return 0;
        }
    }

    /**
     * Find index of AnswerLocalized, creating new ones as needed
     * @param token
     * @param languageCode
     * @return valid object, even if token is blank.
     */
    public AnswerLocalized parseAnswerLocalized(String token, String languageCode) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
//            throw new InstrumentLoadException("AnswerLocalized is blank");
            token = ""; // was return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (AnswerLocalizedHash.containsKey(key)) {
            return AnswerLocalizedHash.get(key);
        }

        String q = "SELECT v FROM AnswerLocalized v WHERE v.answerString = :answerString and v.languageCode = :languageCode";
        EntityManager em = getEM();
        AnswerLocalized answerLocalized = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("answerString", token);
            query.setParameter("languageCode", languageCode);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((AnswerLocalized) list.get(i)).getAnswerLocalizedId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for AnswerLocalized (" + list.size() + "): " + key + sb.toString(), Level.SEVERE, list.get(0));
            }
            answerLocalized = (AnswerLocalized) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found AnswerLocalized: " + key);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New AnswerLocalized: " + key);
//            }
            answerLocalized = new AnswerLocalized();
            answerLocalized.setAnswerString(token);
            answerLocalized.setLanguageCode(languageCode);
            answerLocalized.setAnswerLength(getStringLengthWithoutHtml(token));
        } finally {
            closeEM(em);
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
    public AnswerListDenorm parseAnswerListDenorm(String token, String languageCode) throws InstrumentLoadException {
        lastAnswerListWasNew = false;
        if (token == null || token.trim().length() == 0) {
            token = ""; // was return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (AnswerListDenormHash.containsKey(key)) {
            lastAnswerListWasNew = false;
            return AnswerListDenormHash.get(key);
        }

        String q = "SELECT v FROM AnswerListDenorm v WHERE v.answerListDenormString = :answerListDenormString and v.languageCode = :languageCode";
        EntityManager em = getEM();
        AnswerListDenorm answerListDenormalized = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("answerListDenormString", token);
            query.setParameter("languageCode", languageCode);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((AnswerListDenorm) list.get(i)).getAnswerListDenormId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for AnswerListDenorm (" + list.size() + "): " + key + sb.toString(), Level.SEVERE, list.get(0));
            }
            answerListDenormalized = (AnswerListDenorm) list.get(0);
            lastAnswerListWasNew = false;
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found AnswerListDenorm: " + key);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New AnswerListDenorm: " + key);
//            }
            answerListDenormalized = new AnswerListDenorm();
            answerListDenormalized.setAnswerListDenormString(token);
            answerListDenormalized.setLanguageCode(languageCode);
            answerListDenormalized.setAnswerListDenormLen(getStringLengthWithoutHtml(token));
            lastAnswerListWasNew = true;
        } finally {
            closeEM(em);
        }
        AnswerListDenormHash.put(key, answerListDenormalized);
        return answerListDenormalized;
    }

    /**
     * Find index of HelpLocalized, creating new ones as needed
     * @param token
     * @param languageCode
     * @return null if blank
     */
    public HelpLocalized parseHelpLocalized(String token, String languageCode) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
//            logger.fine("HelpLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (HelpLocalizedHash.containsKey(key)) {
            return HelpLocalizedHash.get(key);
        }

        String q = "SELECT v FROM HelpLocalized v WHERE v.helpString = :helpString and v.languageCode = :languageCode";
        EntityManager em = getEM();
        HelpLocalized helpLocalized = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("helpString", token);
            query.setParameter("languageCode", languageCode);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((HelpLocalized) list.get(i)).getHelpId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for HelpLocalized (" + list.size() + "): " + key + sb.toString());
            }
            helpLocalized = (HelpLocalized) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found HelpLocalized: " + key);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New HelpLocalized: " + key);
//            }
            helpLocalized = new HelpLocalized();
            helpLocalized.setHelpString(token);
            helpLocalized.setLanguageCode(languageCode);
        } finally {
            closeEM(em);
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
    public ReadbackLocalized parseReadbackLocalized(String token, String languageCode) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
//            logger.fine("ReadbackLocalized is blank");
            return null;
        }
        /* First check whether it exists to avoid DB query */
        String key = languageCode + token;
        if (ReadbackLocalizedHash.containsKey(key)) {
            return ReadbackLocalizedHash.get(key);
        }

        String q = "SELECT v FROM ReadbackLocalized v WHERE v.readbackString = :readbackString and v.languageCode = :languageCode";
        EntityManager em = getEM();
        ReadbackLocalized readbackLocalized = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("readbackString", token);
            query.setParameter("languageCode", languageCode);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((ReadbackLocalized) list.get(i)).getReadbackId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for ReadbackLocalized (" + list.size() + "): " + key + sb.toString(), Level.SEVERE, list.get(0));
            }
            readbackLocalized = (ReadbackLocalized) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found ReadbackLocalized: " + key);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New ReadbackLocalized: " + key);
//            }
            readbackLocalized = new ReadbackLocalized();
            readbackLocalized.setReadbackString(token);
            readbackLocalized.setLanguageCode(languageCode);
        } finally {
            closeEM(em);
        }
        ReadbackLocalizedHash.put(key, readbackLocalized);
        return readbackLocalized;
    }

    /**
     * Return ItemActionType, which is one of {q, e, [, ]}
     * @param token
     * @return
     */
    public String parseItemActionType(String token) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
//            throw new InstrumentLoadException("ItemActionType is blank " + token, Level.SEVERE, "");
            token = ""; // was return null;
        }
        String actionType = token.substring(0, 1);
        if (actionType.equalsIgnoreCase("q") || actionType.equalsIgnoreCase("[") || actionType.equalsIgnoreCase("]")) {
            return actionType.toLowerCase();
        } else if (actionType.equalsIgnoreCase("e")) {
            return actionType.toLowerCase();
        } else {
            throw new InstrumentLoadException("Invalid ItemActionType " + token, Level.SEVERE, null);
        }
    }

    /**
     * Return index of ActionType
     * @param token
     * @return null if missing or invalid
     */
    public ActionType parseActionType(String token) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("ActionType is blank", Level.SEVERE, null);
        }
        if (ActionTypeHash.containsKey(token)) {
            return ActionTypeHash.get(token);
        } else {
            throw new InstrumentLoadException("Invalid ActionType " + token, Level.SEVERE, null);
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
        EntityManager em = getEM();
        try {
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
        } finally {
            closeEM(em);
        }
//        logger.fine("Successfully loaded vocabularies");
        staticContentsLoaded = true;    // even if an error is thrown
    }

    /**
     * Find index of DisplayType
     * @param token
     * @return DisplayType("nothing") if blank or invalid
     */
    public DisplayType parseDisplayType(String token) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("Missing DisplayType", Level.SEVERE, parseDisplayType("nothing"));
        }
        if (DisplayTypeHash.containsKey(token)) {
            return DisplayTypeHash.get(token);
        } else {
            if (!token.equals("nothing")) {
                throw new InstrumentLoadException("Invalid DisplayType " + token, Level.SEVERE, parseDisplayType("nothing"));
            }
            else {
                throw new InstrumentLoadException("Invalid DisplayType " + token, Level.SEVERE, null);
            }
        }
    }

    /**
     * Find index of DataType for Casting results
     * @param token
     * @return
     */
    public DataType parseDataType(String token) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("Missing DataType",  Level.SEVERE, null);
        }
        if (DataTypeHash.containsKey(token)) {
            return DataTypeHash.get(token);
        } else {
            throw new InstrumentLoadException("Invalid CastTo DataType " + token, Level.SEVERE, null);
        }
    }

    /**
     * Find index of Instrument by its name, creating a new one if needed
     * @param token
     * @return null if blank
     */
    public Instrument parseInstrument(String token) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("Instrument is blank", Level.SEVERE, null);
        }
        /* First check whether it exists to avoid DB query */
        if (InstrumentHash.containsKey(token)) {
            return InstrumentHash.get(token);
        }

        String q = "SELECT v FROM Instrument v WHERE v.instrumentName = :instrumentName";
        EntityManager em = getEM();
        Instrument instrument = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("instrumentName", token);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((Instrument) list.get(i)).getInstrumentId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for Instrument (" + list.size() + "): " + token + sb.toString(), Level.SEVERE, list.get(0));
            }
            instrument = (Instrument) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found Instrument: " + token);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New Instrument: " + token);
//            }
            instrument = new Instrument();
            instrument.setInstrumentDescription("");    // Starts blank
            instrument.setInstrumentName(token);
        } finally {
            closeEM(em);
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
    public InstrumentVersion parseInstrumentVersion(String title, String token) throws InstrumentLoadException {
        if (token == null || token.trim().length() == 0) {
            throw new InstrumentLoadException("Instrument Version is blank" + title, Level.SEVERE, null);
        }
        if (title == null || title.trim().length() == 0) {
            throw new InstrumentLoadException("Instrument Name is blank", Level.SEVERE, null);
        }
//        /* First check whether it exists to avoid DB query */
//        if (InstrumentVersionHash.containsKey(token)) {
//            return InstrumentVersionHash.get(token);
//        }

        Instrument instrument = parseInstrument(title); // need this to set relationship
        String err = null;

        String q = "SELECT iv FROM InstrumentVersion iv JOIN iv.instrumentId i WHERE i.instrumentName = :instrumentName AND iv.versionString = :versionString";
        EntityManager em = getEM();
        InstrumentVersion instrumentVersion = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("instrumentName", title);
            query.setParameter("versionString", token);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((InstrumentVersion) list.get(i)).getInstrumentVersionId());
                }
                sb.append(")");
                err = "Instrument already exists (" + list.size() + "): " + title + " (" + token + ")" + sb.toString();
            }
        } catch (IndexOutOfBoundsException e) {
        } finally {
            closeEM(em);
        }

        instrumentVersion = new InstrumentVersion();
        instrumentVersion.setVersionString(token);
        instrumentVersion.setInstrumentNotes("");
        instrumentVersion.setInstrumentStatus(new Integer(0));  // default to active
        instrumentVersion.setCreationTimeStamp(new Date(System.currentTimeMillis()));
        instrumentVersion.setInstrumentId(instrument);

        if (err != null) {
            throw new InstrumentLoadException(err,Level.SEVERE,instrumentVersion);
        }
//        InstrumentVersionHash.put(token, instrumentVersion);
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
    public Validation parseValidation(DataType dataType, String minVal, String maxVal, String inputMask, String otherVals) throws InstrumentLoadException {
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
        String castTo = "";
        if (dataType != null) {
            castTo = dataType.getDataType();
        }
        String token = castTo + ";" + minVal + ";" + maxVal + ";" + inputMask + ";" + otherVals;

        if (ValidationHash.containsKey(token)) {
            return ValidationHash.get(token);
        }

        String q = "SELECT v FROM Validation v WHERE v.dataTypeId = :dataTypeId and v.minVal = :minVal and v.maxVal = :maxVal and v.otherVals = :otherVals and v.inputMask = :inputMask";
        EntityManager em = getEM();
        Validation validation = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("dataTypeId",dataType);
            query.setParameter("minVal", minVal);
            query.setParameter("maxVal", maxVal);
            query.setParameter("otherVals", otherVals);
            query.setParameter("inputMask", inputMask);
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((Validation) list.get(i)).getValidationId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for Validation (" + list.size() + "): " + token + sb.toString(), Level.SEVERE, list.get(0));
            }
            validation = (Validation) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found Validation: " + token);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New Validation: " + token);
//            }
            validation = new Validation();
            validation.setDataTypeId(dataType);
            validation.setMinVal(minVal);
            validation.setMaxVal(maxVal);
            validation.setOtherVals(otherVals);
            validation.setInputMask(inputMask);
        } finally {
            closeEM(em);
        }
        ValidationHash.put(token, validation);
        return validation;
    }

    /**
     * Find item by its contents, creating new one if needed
     * @param newItem
     * @param questionString
     * @param answerListDenormString
     * @param dataType
     * @param hasNewContents
     * @return
     */
    public Item findItem(Item newItem, String questionString, String answerListDenormString, String dataType, boolean hasNewContents) throws InstrumentLoadException {
        if (answerListDenormString == null) {
            answerListDenormString = "";
        }    // TODO - CHECK - may expect a null return
        String token = questionString + ";" + answerListDenormString + ";" + dataType + ";" + newItem.getItemType();

        if (hasNewContents == true) {   // then must be new
            ItemHash.put(token, newItem);   // TODO - CHECK - will the contents of newItem be updated with proper Ids after a persist?
//            if (logger.isLoggable(Level.INFO)) {
//                logger.fine("Adding New Item: " + token);
//            }
            return newItem;
        }
        if (ItemHash.containsKey(token)) {
            return ItemHash.get(token);
        }

        String q = "SELECT v FROM Item v WHERE v.questionId = :questionId and v.dataTypeId = :dataTypeId and v.answerListId = :answerListId and v.itemType = :itemType";
        EntityManager em = getEM();
        Item item = null;
        try {
            Query query = em.createQuery(q);
            query.setParameter("questionId", newItem.getQuestionId());
            query.setParameter("dataTypeId", newItem.getDataTypeId());
            query.setParameter("answerListId", newItem.getAnswerListId());
            query.setParameter("itemType", newItem.getItemType());
            List list = query.getResultList();
            if (list.size() > 1) {
                StringBuffer sb = new StringBuffer(" keys(");
                for (int i=0;i<list.size();++i) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(((Item) list.get(i)).getItemId());
                }
                sb.append(")");
                throw new InstrumentLoadException("Non-Unique Results for Item (" + list.size() + "): " + token + sb.toString(), Level.SEVERE, list.get(0));
            }
            item = (Item) list.get(0);
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Found Item: " + token);
//            }
        } catch (IndexOutOfBoundsException e) {
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Adding New Item: " + token);
//            }
            ItemHash.put(token, newItem);
            return newItem; // since contents already set
        } finally {
            closeEM(em);
        }
        ItemHash.put(token, item);
        return item;    // return existing one
    }

    /**
     * Check whether this instrument already exists
     * @param instrumentHash
     * @return
     * @throws org.dialogix.session.InstrumentLoadException
     */
     public InstrumentHash parseInstrumentHash(InstrumentHash instrumentHash) throws InstrumentLoadException {

        String q = "SELECT v FROM InstrumentHash v WHERE " +
            "v.instrumentMd5 = :instrumentMd5 and " +
            "v.numBranches = :numBranches and " +
            "v.numEquations = :numEquations and " +
            "v.numGroups = :numGroups and " +
            "v.numInstructions = :numInstructions and " +
            "v.numLanguages = :numLanguages and " +
            "v.numQuestions = :numQuestions and " +
            "v.numTailorings = :numTailorings and " +
            "v.numVars = :numVars and " +
            "v.varListMd5 = :varListMd5 and " +
            "v.languageListId = :languageListId";

        EntityManager em = getEM();
        try {
            Query query = em.createQuery(q);
            query.setParameter("instrumentMd5", instrumentHash.getInstrumentMd5());
            query.setParameter("numBranches", instrumentHash.getNumBranches());
            query.setParameter("numEquations", instrumentHash.getNumEquations());
            query.setParameter("numGroups", instrumentHash.getNumGroups());
            query.setParameter("numInstructions", instrumentHash.getNumInstructions());
            query.setParameter("numLanguages", instrumentHash.getNumLanguages());
            query.setParameter("numQuestions", instrumentHash.getNumQuestions());
            query.setParameter("numTailorings", instrumentHash.getNumTailorings());
            query.setParameter("numVars", instrumentHash.getNumVars());
            query.setParameter("varListMd5", instrumentHash.getVarListMd5());
            query.setParameter("languageListId", instrumentHash.getLanguageListId());
            List list = query.getResultList();
            StringBuffer sb = new StringBuffer(" keys(");
            for (int i=0;i<list.size();++i) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(((InstrumentHash) list.get(i)).getInstrumentHashId());
            }
            sb.append(")");
            throw new InstrumentLoadException("Instrument duplicates the contents of at least (" + list.size() + ") existing one(s): " + sb.toString(), Level.SEVERE, list.get(0));
        } catch (IndexOutOfBoundsException e) {
            return instrumentHash; // since contents already set
        } finally {
            closeEM(em);
        }
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
        _merge(instrument);
    }

    /**
     * Update InstrumentVersion
     * @param instrumentVersion
     */
    public void merge(InstrumentVersion instrumentVersion) {
        _merge(instrumentVersion);
    }

  private void _persist(Object object) {
    EntityManager em = getEM();
    EntityTransaction tx = null;
    try {
      tx = em.getTransaction();
      tx.begin();
      em.persist(object);
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw e; // or display error message
    } finally {
      closeEM(em);
    }
  }

  private void _merge(Object object) {
    EntityManager em = getEM();
    EntityTransaction tx = null;
    try {
      tx = em.getTransaction();
      tx.begin();
      em.merge(object);
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw e; // or display error message
    } finally {
      closeEM(em);
    }
  }
}
