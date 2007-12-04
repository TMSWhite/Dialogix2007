/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.V1DataElement;

/**
 *
 * @author George
 */
@Local
public interface V1DataElementFacadeLocal {

    void create(V1DataElement v1DataElement);

    void edit(V1DataElement v1DataElement);

    void remove(V1DataElement v1DataElement);

    V1DataElement find(Object id);

    List<V1DataElement> findAll();

}
