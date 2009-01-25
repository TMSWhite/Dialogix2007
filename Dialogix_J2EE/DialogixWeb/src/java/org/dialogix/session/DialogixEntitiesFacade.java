package org.dialogix.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.dialogix.entities.*;
import javax.persistence.*;
import org.dialogix.beans.InstrumentSessionResultBean;
import org.dialogix.beans.InstrumentVersionView;

/**
 * This interface is for running instruments which already exist within the database.
 */
//@Stateless
public class DialogixEntitiesFacade implements java.io.Serializable {

//    @PersistenceContext
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("DialogixEntitiesPU");
    private EntityManager em = emf.createEntityManager();
    /**
     * Get list of ActionTypes for use locally
     * @return
     */
    public List<ActionType> getActionTypes() {
        return em.createQuery("select object(o) from ActionType o").getResultList();
    }

    /**
     * Get list of NullFlavors for use locally
     * @return
     */
    public List<NullFlavor> getNullFlavors() {
        return em.createQuery("select object(o) from NullFlavor o").getResultList();
    }

    /**
     * Get list of NullFlavorChange for use locally
     * @return
     */
    public List<NullFlavorChange> getNullFlavorChanges() {
        return em.createQuery("select object(o) from NullFlavorChange o").getResultList();
    }

    /**
     * Update values of running InstrumentSession
     * @param instrumentSession
     */
    public void merge(InstrumentSession instrumentSession) {
        _merge(instrumentSession);
    }

    public void refresh(Object object) {
       try {
            em.refresh(object);
        } catch (Throwable e) {
        }
    }

    /**
     * Initialize an InstrumentSession
     * @param instrumentSession
     */
    public void persist(InstrumentSession instrumentSession) {
        _persist(instrumentSession);
    }

    private void _persist(Object object) {
      EntityManager _em = emf.createEntityManager();
      EntityTransaction tx = null;
      try {
        tx = _em.getTransaction();
        tx.begin();
        _em.persist(object);
        tx.commit();
      } catch (RuntimeException e) {
        if (tx != null && tx.isActive()) {
          tx.rollback();
        }
        throw e; // or display error message
      } finally {
        _em.close();
      }
    }

    private void _merge(Object object) {
      EntityManager _em = emf.createEntityManager();
      EntityTransaction tx = null;
      try {
        tx = _em.getTransaction();
        tx.begin();
        _em.merge(object);
        tx.commit();
      } catch (RuntimeException e) {
        if (tx != null && tx.isActive()) {
          tx.rollback();
        }
        throw e; // or display error message
      } finally {
        _em.close();
      }
    }

    /**
     * Load an instance of InstrumentVersion from the database.  This will be used to create an InstrumentSession.
     * @param name
     * @param major
     * @param minor
     * @return null if the InstrumentVersion doesn't exist
     */
    public InstrumentVersion getInstrumentVersion(String name, String major, String minor) {
        InstrumentVersion _instrumentVersion = null;
        Query query = em.createQuery("SELECT iv FROM InstrumentVersion iv JOIN iv.instrumentId i WHERE i.instrumentName = :title AND iv.versionString = :versionString");
        String version = major.concat(".").concat(minor);
        query.setParameter("versionString", version);
        query.setParameter("title", name);
        try {
            List list = query.getResultList();
            _instrumentVersion = (InstrumentVersion) list.get(0);
        } catch (NoResultException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        if (_instrumentVersion == null) {
            return null;
        }
        return _instrumentVersion;
    }

    /**
     * FIXME - may need InstrumentVersion object, not Integer
     * @param instrumentVersionId
     * @return
     */
    public InstrumentVersion getInstrumentVersion(Long instrumentVersionId) {
        return em.find(org.dialogix.entities.InstrumentVersion.class, instrumentVersionId);
    }

    /**
     * Get an instrument session by its  Id
     * @param instrumentSessionId
     * @return
     */
    public InstrumentSession getInstrumentSession(Long instrumentSessionId) {
        return em.find(org.dialogix.entities.InstrumentSession.class, instrumentSessionId);
    }

    /**
     * Get and itemUsage by its id
     * @param itemUsageId
     * @return
     */
    public ItemUsage getItemUsage(Long itemUsageId) {
        return em.find(org.dialogix.entities.ItemUsage.class, itemUsageId);
    }

    /**
     * Get list of Instruments - hopefully using shallow searching
     * @return
     */
    public List<InstrumentVersion> getInstrumentVersionCollection() {
        return em.createQuery("select object(o) from InstrumentVersion o").getResultList();
    }

    public List<ItemUsage> getItemUsages(Long instrumentSessionId) {
        return em.createQuery("select object(iu) from ItemUsage iu JOIN iu.dataElementId de JOIN de.instrumentSessionId ins " +
            "where ins.instrumentSessionId = :instrumentSessionId " +
            "and iu.displayNum > 0" +
            "order by iu.itemUsageId, iu.itemUsageSequence").
            setParameter("instrumentSessionId", instrumentSessionId).
            getResultList();
    }

    public List<PageUsageEvent> getPageUsageEvents(Long pageUsageId) {
        return em.createQuery("select object(pue) from PageUsageEvent pue JOIN pue.pageUsageId pu " +
            "where pu.pageUsageId= :pageUsageId " +
            "order by pue.pageUsageEventSequence").
            setParameter("pageUsageId", pageUsageId).
            getResultList();
    }

    public List<PageUsageEvent> getAllPageUsageEvents(Long instrumentSessionId) {
        return em.createQuery("select object(pue) from PageUsageEvent pue JOIN pue.pageUsageId pu JOIN pu.instrumentSessionId iss " +
            "where iss.instrumentSessionId = :instrumentSessionId " +
            "order by pu.displayNum, pue.pageUsageEventSequence").
            setParameter("instrumentSessionId", instrumentSessionId).
            getResultList();
    }

    /**
     * Get list of all available instruments, showing title,  version, versionId, and number of started sessions
     * @return
     */
    public List<InstrumentVersionView> getAuthorizedInstrumentVersions(Person person) {
        List<InstrumentVersionView> instrumentVersionViewList = new ArrayList<InstrumentVersionView> ();
        boolean personIsStudyAdmin = false;
        Long personId = null;
        if (person == null) {
            personId = (long) 1;   // the Anonymous User
        }
        else {
            // Check whether the person has access to all studies
            Iterator<PersonRoleStudy> personRoleStudyIterator = person.getPersonRoleStudyCollection().iterator();
            while (personRoleStudyIterator.hasNext()) {
                PersonRoleStudy personRoleStudy = personRoleStudyIterator.next();
                if (personRoleStudy.getStudyId() == null) {
                    personIsStudyAdmin = true;
                    break;
                }
            }
            personId = person.getPersonId();
        }

        String q =
            "select distinct " +
            "	iv.instrument_version_id,    " +
            "	i.instrument_name as title,    " +
            "	iv.version_string as version,    " +
            "	ins.num_sessions,   " +
            "   h.num_equations,   " +
            "   h.num_questions,    " +
            "   h.num_branches,   " +
            "   h.num_languages,    " +
            "   h.num_tailorings,    " +
            "   h.num_vars,   " +
            "   h.num_groups,   " +
            "   h.num_instructions,   " +
            "   'empty' as instrument_version_file_name,   " +
            "   iv.num_errors, " +
            "   iv.instrument_status " +
            " from instrument i, instrument_hash h, instrument_version iv, ";
            if (!personIsStudyAdmin) {
                q = q + "study_inst_ver siv, study s, person p, person_role_study prs, ";
            }
            q = q + "	(select iv2.instrument_version_id,  " +
            "		count(ins2.instrument_session_id) as  num_sessions  " +
            "		from instrument_version iv2 left join instrument_session ins2  " +
            "		on iv2.instrument_version_id = ins2.instrument_version_id  " +
            "		group by iv2.instrument_version_id  " +
            "		order by iv2.instrument_version_id) ins " +
            " where iv.instrument_id = i.instrument_id      " +
            "	and iv.instrument_version_id = ins.instrument_version_id    " +
            "   and iv.instrument_hash_id = h.instrument_hash_id   ";
            if (!personIsStudyAdmin) {
                q = q +
            "   and iv.instrument_version_id = siv.instrument_version_id " +
            "   and siv.study_id = s.study_id " +
            "   and prs.study_id = s.study_id " +
            "   and p.person_id = prs.person_id " +
            "   and p.person_id = " + personId;
            }
            q = q + " order by title, version";
        Query query = em.createNativeQuery(q);
        List<Vector> results = query.getResultList();
        if (results == null) {
            return null;
        }
        Iterator<Vector> iterator = results.iterator();
        while (iterator.hasNext()) {
            Vector vector = iterator.next();
            instrumentVersionViewList.add(new InstrumentVersionView(
                (Long) vector.get(0),   // instrumentVersionId
                (String) vector.get(1), // instrumentName
                (String) vector.get(2), // instrumentVersion
                (Long) vector.get(3), // numSessions
                (Integer) vector.get(4), // numEquations
                (Integer) vector.get(5), // numQuestions
                (Integer) vector.get(6), // numBranches
                (Integer) vector.get(7), // numLanguages
                (Integer) vector.get(8), // numTailorings
                (Integer) vector.get(9), // numVars
                (Integer) vector.get(10), // numGroups
                (Integer) vector.get(11), // numInstructions
                (String) vector.get(12), // instrumentVersionFileName
                (Integer) vector.get(13), //numErrors
                (Integer) vector.get(14) // instrumentStatus
                ));
        }
        return instrumentVersionViewList;
    }

    /**
     * Retrieve an InstrumentSession by its filename.
     * TODO:  Once the system is fully databased, this won't be needed.
     * @param name
     * @return  null if the session doesn't exist
     */
    public InstrumentSession findInstrumentSessionByName(String name) {
        if (name == null || name.trim().length() == 0) {
            return null;
        }
        String q = "SELECT v FROM InstrumentSession v WHERE v.instrumentSessionFileName = :instrumentSessionFileName";
        Query query = em.createQuery(q);
        query.setParameter("instrumentSessionFileName", name);
        InstrumentSession instrumentSession = null;
        try {
            instrumentSession = (InstrumentSession) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        }
        return instrumentSession;
    }

    /**
     * Find an existing variable name by its name
     * @param name
     * @return
     */
    public VarName findVarNameByName(String name) {
        if (name == null || name.trim().length() == 0) {
            return null;
        }
        String q = "SELECT v FROM VarName v WHERE v.varName = :varName";
        Query query = em.createQuery(q);
        query.setParameter("varName", name);
        VarName varName = null;
        try {
            varName = (VarName) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        }
        return varName;
    }

    /**
     * Extract raw results, including sessionId, dataElementSequence, varNameId, varName, answerCode, answerString, and nullFlavor.
     * @param instrumentVersionId
     * @param inVarNameIds
     * @param sortByName
     * @return
     * //FIXME - should be constrained by study permissions
     */
    public List<InstrumentSessionResultBean> getFinalInstrumentSessionResults(Long instrumentVersionId, String inVarNameIds, Boolean sortByName) {
        String q =
            " SELECT ic3.instrument_session_id, ic3.item_sequence, ic3.var_name_id, ic3.var_name, iu.answer_code, iu.null_flavor_id " +
            " FROM ( " +
            "   SELECT ic2.instrument_session_id, ic2.item_sequence, ic2.var_name_id, ic2.var_name, de.last_item_usage_id " +
            "   FROM ( " +
            "     SELECT iss.instrument_session_id, ic.instrument_content_id, ic.item_sequence, ic.var_name_id, vn.var_name " +
            "     FROM instrument_session iss, instrument_content ic, var_name vn " +
            "     WHERE  " +
            "       vn.var_name_id = ic.var_name_id " +
            "       AND ic.instrument_version_id = iss.instrument_version_id " +
            "       AND iss.instrument_session_id IN (SELECT instrument_session_id FROM instrument_session WHERE instrument_version_id = " + instrumentVersionId + " ) " +
            ((inVarNameIds != null) ? "       AND vn.var_name_id in " + inVarNameIds : "") +
            "     ) ic2 " +
            "   LEFT OUTER JOIN data_element de " +
            "   ON ic2.instrument_content_id = de.instrument_content_id  " +
            "     AND ic2.instrument_session_id = de.instrument_session_id) ic3 " +
            " LEFT OUTER JOIN item_usage iu  " +
            " ON ic3.last_item_usage_id = iu.item_usage_id " +
            " ORDER BY ic3.instrument_session_id, " +
            ((sortByName == true) ? "ic3.var_name" : "ic3.item_sequence");

        Query query = em.createNativeQuery(q);
        List<Vector> results = query.getResultList();
        if (results == null) {
            return null;
        }
        Iterator<Vector> iterator = results.iterator();
        ArrayList<InstrumentSessionResultBean> isrb = new ArrayList<InstrumentSessionResultBean>();
        while (iterator.hasNext()) {
            Vector v = iterator.next();
            isrb.add(new InstrumentSessionResultBean((Long) v.get(0),
                (Integer) v.get(1),
                (Long) v.get(2),
                (String) v.get(3),
                (String) v.get(4),
                (Integer) v.get(5)));
        }
        return isrb;
    }

    public List<InstrumentSession> getInstrumentSessions(InstrumentVersion instrumentVersionId) {
        return em.
            createQuery("select object(o) from InstrumentSession o where o.instrumentVersionId = :instrumentVersionId").
            setParameter("instrumentVersionId",instrumentVersionId).
            getResultList();
    }

    public Person getPerson(String userName, String pwd) {
        Person _person = null;
        Query query = em.createQuery("SELECT object(p) FROM Person p WHERE p.userName = :userName AND p.pwd = :pwd");
        query.setParameter("pwd", pwd);
        query.setParameter("userName", userName);
        try {
            _person = (Person) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        }
        return _person;
    }

    public List<Menu> getMenus(Person person) {
        Long id = (long) 1;
        if (person != null) {
            id = person.getPersonId();
        }
        String q =
            "SELECT DISTINCT menu.menu_name, menu.display_text, menu.menu_type " +
            "FROM role, menu, person, person_role_study, role_menu " +
            "WHERE" +
            "  person.person_id = " + id +
            "  AND person.person_id = person_role_study.person_id " +
            "  AND person_role_study.role_id = role.role_id " +
            "  AND role.role_id = role_menu.role_id " +
            "  AND role_menu.menu_id = menu.menu_id " +
            "ORDER BY role.role_id, menu.menu_order";
        Query query = em.createNativeQuery(q);
        List<Vector> results = query.getResultList();
        if (results == null) {
            return null;
        }
        Iterator<Vector> iterator = results.iterator();
        ArrayList<Menu> menus = new ArrayList<Menu>();
        while (iterator.hasNext()) {
            Vector v = iterator.next();
            Menu menu = new Menu();
            menu.setMenuName((String) v.get(0));
            menu.setDisplayText((String) v.get(1));
            menu.setMenuType((Integer) v.get(2));
            menus.add(menu);
        }
        return menus;
    }

    public List<Study> getStudies() {
        return em.createQuery("SELECT object(s) FROM Study s ORDER BY s.studyName").getResultList();
    }

    public Study findStudyById(Long studyId) {
        return em.find(org.dialogix.entities.Study.class, studyId);
    }

    public Person findPersonById(Long personId) {
        return em.find(org.dialogix.entities.Person.class, personId);
    }

    public InstrumentContent findInstrumentContentByInstrumentVersionAndVarName(InstrumentVersion instrumentVersionId, String varName) {
        try {
            String q =
                "SELECT object(ic) " +
                "FROM InstrumentContent ic, VarName vn " +
                "WHERE ic.varNameId = vn.varNameId " +
                "AND vn.varName = :varName " +
                "AND ic.instrumentVersionId = :instrumentVersionId";
            Query query = em.createQuery(q);
            query.setParameter("varName", varName);
            query.setParameter("instrumentVersionId", instrumentVersionId);
            return (InstrumentContent) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            return null;
        }
    }

    public List<InstrumentSession> getMyInstrumentSessions(Person personId) {
        return em.createQuery("SELECT iss FROM InstrumentSession iss WHERE iss.personId = :personId").setParameter("personId", personId).getResultList();
    }

    public List<InstrumentLoadError> getInstrumentLoadErrors(InstrumentVersion instrumentVersion) {
        return em.createQuery("SELECT object(ile) FROM InstrumentLoadError ile WHERE ile.instrumentVersionId = :instrumentVersionId ORDER BY ile.sourceRow, ile.sourceColumn")
            .setParameter("instrumentVersionId", instrumentVersion)
            .getResultList();
    }

    public SubjectSession findSubjectSessionById(Long id) {
        return em.find(org.dialogix.entities.SubjectSession.class, id);
    }

    public void merge(SubjectSession subjectSession) {
        _merge(subjectSession);
    }

    public List<I18n> getI18nByIso3language(String iso3language) {
        // returns localized strings, defaulting to english if can't find requested locale
        try {
            return em.createQuery("SELECT object(obj) FROM I18n obj WHERE obj.iso3language = :iso3language")
            .setParameter("iso3language", iso3language)
            .getResultList();
        } catch (NoResultException e) {
            return em.createQuery("SELECT object(obj) FROM I18n obj WHERE obj.iso3language = :iso3language")
            .setParameter("iso3language", "eng")
            .getResultList();
        }
    }
}
