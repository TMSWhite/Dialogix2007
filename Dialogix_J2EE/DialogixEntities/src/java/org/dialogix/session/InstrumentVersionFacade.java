/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.InstrumentVersion;

/**
 *
 * @author George
 */
@Stateless
public class InstrumentVersionFacade implements InstrumentVersionFacadeLocal, InstrumentVersionFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(InstrumentVersion instrumentVersion) {
        em.persist(instrumentVersion);
    }

    public void edit(InstrumentVersion instrumentVersion) {
        em.merge(instrumentVersion);
    }

    public void remove(InstrumentVersion instrumentVersion) {
        em.remove(em.merge(instrumentVersion));
    }

    public InstrumentVersion find(Object id) {
        return em.find(org.dialogix.entities.InstrumentVersion.class, id);
    }

    public List<InstrumentVersion> findAll() {
        return em.createQuery("select object(o) from InstrumentVersion as o").getResultList();
    }

}
