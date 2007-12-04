/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.ActionType;

/**
 *
 * @author George
 */
@Stateless
public class ActionTypeFacade implements ActionTypeFacadeLocal, ActionTypeFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(ActionType actionType) {
        em.persist(actionType);
    }

    public void edit(ActionType actionType) {
        em.merge(actionType);
    }

    public void remove(ActionType actionType) {
        em.remove(em.merge(actionType));
    }

    public ActionType find(Object id) {
        return em.find(org.dialogix.entities.ActionType.class, id);
    }

    public List<ActionType> findAll() {
        return em.createQuery("select object(o) from ActionType as o").getResultList();
    }

}
