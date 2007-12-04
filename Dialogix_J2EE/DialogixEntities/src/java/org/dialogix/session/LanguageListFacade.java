/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dialogix.entities.LanguageList;

/**
 *
 * @author George
 */
@Stateless
public class LanguageListFacade implements LanguageListFacadeLocal, LanguageListFacadeRemote {
    @PersistenceContext
    private EntityManager em;

    public void create(LanguageList languageList) {
        em.persist(languageList);
    }

    public void edit(LanguageList languageList) {
        em.merge(languageList);
    }

    public void remove(LanguageList languageList) {
        em.remove(em.merge(languageList));
    }

    public LanguageList find(Object id) {
        return em.find(org.dialogix.entities.LanguageList.class, id);
    }

    public List<LanguageList> findAll() {
        return em.createQuery("select object(o) from LanguageList as o").getResultList();
    }

}
