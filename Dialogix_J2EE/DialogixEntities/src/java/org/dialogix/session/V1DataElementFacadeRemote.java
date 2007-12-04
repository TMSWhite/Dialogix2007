/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Remote;
import org.dialogix.entities.V1DataElement;

/**
 *
 * @author George
 */
@Remote
public interface V1DataElementFacadeRemote {

    void create(V1DataElement v1DataElement);

    void edit(V1DataElement v1DataElement);

    void remove(V1DataElement v1DataElement);

    V1DataElement find(Object id);

    List<V1DataElement> findAll();

}
