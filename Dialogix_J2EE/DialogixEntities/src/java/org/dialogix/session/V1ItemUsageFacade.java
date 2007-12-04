/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.V1ItemUsage;

/**
 *
 * @author George
 */
@Stateless
public class V1ItemUsageFacade implements V1ItemUsageFacadeLocal, V1ItemUsageFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(V1ItemUsage v1ItemUsage) {
        em.persist(v1ItemUsage);
    }

    public void edit(V1ItemUsage v1ItemUsage) {
        em.merge(v1ItemUsage);
    }

    public void remove(V1ItemUsage v1ItemUsage) {
        em.remove(em.merge(v1ItemUsage));
    }

    public V1ItemUsage find(Object id) {
        return em.find(org.dialogix.entities.V1ItemUsage.class, id);
    }

    public List<V1ItemUsage> findAll() {
        return em.createQuery("select object(o) from V1ItemUsage as o").getResultList();
    }

}
