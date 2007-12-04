/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.V1InstrumentSession;

/**
 *
 * @author George
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
    
    

}
