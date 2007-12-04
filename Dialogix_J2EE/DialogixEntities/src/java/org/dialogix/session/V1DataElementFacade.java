/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.V1DataElement;

/**
 *
 * @author George
 */
@Stateless
public class V1DataElementFacade implements V1DataElementFacadeLocal, V1DataElementFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(V1DataElement v1DataElement) {
        em.persist(v1DataElement);
    }

    public void edit(V1DataElement v1DataElement) {
        em.merge(v1DataElement);
    }

    public void remove(V1DataElement v1DataElement) {
        em.remove(em.merge(v1DataElement));
    }

    public V1DataElement find(Object id) {
        return em.find(org.dialogix.entities.V1DataElement.class, id);
    }

    public List<V1DataElement> findAll() {
        return em.createQuery("select object(o) from V1DataElement as o").getResultList();
    }

}
