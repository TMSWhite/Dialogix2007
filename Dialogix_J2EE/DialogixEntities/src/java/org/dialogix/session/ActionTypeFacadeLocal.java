/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.ActionType;

/**
 *
 * @author George
 */
@Local
public interface ActionTypeFacadeLocal {

    void create(ActionType actionType);

    void edit(ActionType actionType);

    void remove(ActionType actionType);

    ActionType find(Object id);

    List<ActionType> findAll();

}
