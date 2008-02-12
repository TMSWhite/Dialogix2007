/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.dialogix.entities.*;

/**
 *
 * @author Coevtmw
 */
@Stateless
public class V1InstrumentSessionFacade implements V1InstrumentSessionFacadeLocal, V1InstrumentSessionFacadeRemote {

    @PersistenceContext
    private EntityManager em;

    public void create(V1InstrumentSession v1InstrumentSession) {
        getEm().persist(v1InstrumentSession);
    }

    public void edit(V1InstrumentSession v1InstrumentSession) {
        getEm().merge(v1InstrumentSession);
    }

    public void remove(V1InstrumentSession v1InstrumentSession) {
        getEm().remove(getEm().merge(v1InstrumentSession));
    }

    public V1InstrumentSession find(Long id) {
        V1InstrumentSession v1InstrumentSession =
                getEm().find(V1InstrumentSession.class, id);
        
        return v1InstrumentSession;
    }

    public List<V1InstrumentSession> findAll() {
        return getEm().createQuery("select object(o) from V1InstrumentSession as o").getResultList();
    }

    /**
    Find index for this V1InstrumentSession
    @return Null if name is empty, or Integer of V1InstrumentSession (adding an new V1InstrumentSessionID if needed)
     */
    public V1InstrumentSession findByName(String name) {
        if (name == null || name.trim().length() == 0) {
            return null;
        }
        String q = "SELECT v FROM V1InstrumentSession v WHERE v.instrumentSessionFileName = :instrumentSessionFileName";
        Query query = getEm().createQuery(q);
        query.setParameter("instrumentSessionFileName", name);
        V1InstrumentSession v1InstrumentSession = null;
        try {
            v1InstrumentSession = (V1InstrumentSession) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return v1InstrumentSession;
    }

    public V1ItemUsage findV1ItemUsage(Long id) {
        return getEm().find(V1ItemUsage.class, id);
    }

    public V1DataElement findV1DataElement(Long v1DataElementID) {
        return getEm().find(V1DataElement.class, v1DataElementID);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
