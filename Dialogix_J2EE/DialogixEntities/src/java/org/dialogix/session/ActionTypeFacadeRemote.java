/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Remote;
import org.dialogix.entities.ActionType;

/**
 *
 * @author George
 */
@Remote
public interface ActionTypeFacadeRemote {

    void create(ActionType actionType);

    void edit(ActionType actionType);

    void remove(ActionType actionType);

    ActionType find(Object id);

    List<ActionType> findAll();

}
