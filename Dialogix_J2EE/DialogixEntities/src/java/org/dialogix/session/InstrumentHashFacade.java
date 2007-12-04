/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.InstrumentHash;

/**
 *
 * @author George
 */
@Stateless
public class InstrumentHashFacade implements InstrumentHashFacadeLocal, InstrumentHashFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(InstrumentHash instrumentHash) {
        em.persist(instrumentHash);
    }

    public void edit(InstrumentHash instrumentHash) {
        em.merge(instrumentHash);
    }

    public void remove(InstrumentHash instrumentHash) {
        em.remove(em.merge(instrumentHash));
    }

    public InstrumentHash find(Object id) {
        return em.find(org.dialogix.entities.InstrumentHash.class, id);
    }

    public List<InstrumentHash> findAll() {
        return em.createQuery("select object(o) from InstrumentHash as o").getResultList();
    }

}
