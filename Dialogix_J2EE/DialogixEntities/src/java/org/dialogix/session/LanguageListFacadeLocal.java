/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.LanguageList;

/**
 *
 * @author George
 */
@Local
public interface LanguageListFacadeLocal {

    void create(LanguageList languageList);

    void edit(LanguageList languageList);

    void remove(LanguageList languageList);

    LanguageList find(Object id);

    List<LanguageList> findAll();

}
