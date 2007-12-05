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
import org.dialogix.entities.V1InstrumentSession;

/**
 *
 * @author Coevtmw
 */
@Stateless
public class V1InstrumentSessionFacade implements V1InstrumentSessionFacadeLocal, V1InstrumentSessionFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(V1InstrumentSession v1InstrumentSession) {
        em.persist(v1InstrumentSession);
    }

    public void edit(V1InstrumentSession v1InstrumentSession) {
        em.merge(v1InstrumentSession);
    }

    public void remove(V1InstrumentSession v1InstrumentSession) {
        em.remove(em.merge(v1InstrumentSession));
    }

    public V1InstrumentSession find(Object id) {
        return em.find(org.dialogix.entities.V1InstrumentSession.class, id);
    }

    public List<V1InstrumentSession> findAll() {
        return em.createQuery("select object(o) from V1InstrumentSession as o").getResultList();
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
        Query query = em.createQuery(q);
        query.setParameter("instrumentSessionFileName", name);
        V1InstrumentSession v1InstrumentSession = null;
        v1InstrumentSession = (V1InstrumentSession) query.getSingleResult();
        return v1InstrumentSession;
    }    

}
