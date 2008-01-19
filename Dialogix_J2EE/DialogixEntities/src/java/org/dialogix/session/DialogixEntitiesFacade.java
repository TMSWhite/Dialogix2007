package org.dialogix.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import org.dialogix.entities.*;
import javax.persistence.*;
import org.dialogix.beans.InstrumentSessionResultBean;

/**
 * This interface is for running instruments which already exist within the database.
 */
@Stateless  
public class DialogixEntitiesFacade implements DialogixEntitiesFacadeRemote, DialogixEntitiesFacadeLocal {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * Get list of ActionTypes for use locally
     * @return
     */
    public List<ActionType> getActionTypes() {
        return em.createQuery("select object(o) from ActionType as o").getResultList();                
    }
    
    /**
     * Get list of NullFlavors for use locally
     * @return
     */
    public List<NullFlavor> getNullFlavors() {
        return em.createQuery("select object(o) from NullFlavor as o").getResultList();        
    }

    /**
     * Update values of running InstrumentSession
     * @param instrumentSession
     */
    public void merge(InstrumentSession instrumentSession) {
        em.merge(instrumentSession);
    }

    /**
     * Initialize an InstrumentSession
     * @param instrumentSession
     */
    public void persist(InstrumentSession instrumentSession) {
        em.persist(instrumentSession);
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
        Query query = em.createQuery("SELECT iv FROM InstrumentVersion AS iv JOIN iv.instrumentID as i WHERE i.instrumentName = :title AND iv.versionString = :versionString");
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
     * @param instrumentVersionID
     * @return
     */
    public InstrumentVersion getInstrumentVersion(Long instrumentVersionID) {
        return em.find(org.dialogix.entities.InstrumentVersion.class, instrumentVersionID);
    }
    
    /**
     * Get list of Instruments - hopefully using shallow searching
     * @return
     */
    public List<InstrumentVersion> getInstrumentVersionCollection() {
        return em.createQuery("select object(o) from InstrumentVersion as o").getResultList();    
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
    
    public List<InstrumentSessionResultBean> getFinalInstrumentSessionResults(Long instrumentVersionID) {
        String q =
            "select " +
            "	de.instrument_session_id," +
            "	de.data_element_sequence," +
            "	de.var_name_id," +
            "	vn.name," +
            "	de.answer_code," +
            "	de.answer_string," +
            "	de.null_flavor_id" +
            " from data_elements de, var_names vn" +
            " where " +
            "	de.var_name_id = vn.var_name_id and" +
            "	de.instrument_session_id in" +
            "		(select instrument_session_id from instrument_sessions where instrument_version_id = " + instrumentVersionID + ")" +
            " order by de.instrument_session_id, de.data_element_sequence";
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
                (String) v.get(5), 
                (Integer) v.get(6)));
        }
        return isrb;
    }
    
}
