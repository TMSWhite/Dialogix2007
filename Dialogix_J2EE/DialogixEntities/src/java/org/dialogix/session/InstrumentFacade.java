/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.Instrument;

/**
 *
 * @author George
 */
@Stateless
public class InstrumentFacade implements InstrumentFacadeLocal, InstrumentFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(Instrument instrument) {
        em.persist(instrument);
    }

    public void edit(Instrument instrument) {
        em.merge(instrument);
    }

    public void remove(Instrument instrument) {
        em.remove(em.merge(instrument));
    }

    public Instrument find(Object id) {
        return em.find(org.dialogix.entities.Instrument.class, id);
    }

    public List<Instrument> findAll() {
        return em.createQuery("select object(o) from Instrument as o").getResultList();
    }

}
