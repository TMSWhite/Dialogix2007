/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.InstrumentSession;

/**
 *
 * @author George
 */
@Stateless
public class InstrumentSessionFacade implements InstrumentSessionFacadeLocal, InstrumentSessionFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(InstrumentSession instrumentSession) {
        em.persist(instrumentSession);
    }

    public void edit(InstrumentSession instrumentSession) {
        em.merge(instrumentSession);
    }

    public void remove(InstrumentSession instrumentSession) {
        em.remove(em.merge(instrumentSession));
    }

    public InstrumentSession find(Object id) {
        return em.find(org.dialogix.entities.InstrumentSession.class, id);
    }

    public List<InstrumentSession> findAll() {
        return em.createQuery("select object(o) from InstrumentSession as o").getResultList();
    }

}
