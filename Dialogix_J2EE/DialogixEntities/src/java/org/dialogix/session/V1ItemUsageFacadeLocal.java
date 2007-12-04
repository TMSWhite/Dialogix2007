/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.V1ItemUsage;

/**
 *
 * @author George
 */
@Local
public interface V1ItemUsageFacadeLocal {

    void create(V1ItemUsage v1ItemUsage);

    void edit(V1ItemUsage v1ItemUsage);

    void remove(V1ItemUsage v1ItemUsage);

    V1ItemUsage find(Object id);

    List<V1ItemUsage> findAll();

}
