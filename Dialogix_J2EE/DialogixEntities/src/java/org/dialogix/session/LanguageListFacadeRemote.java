/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Remote;
import org.dialogix.entities.LanguageList;

/**
 *
 * @author George
 */
@Remote
public interface LanguageListFacadeRemote {

    void create(LanguageList languageList);

    void edit(LanguageList languageList);

    void remove(LanguageList languageList);

    LanguageList find(Object id);

    List<LanguageList> findAll();

}
