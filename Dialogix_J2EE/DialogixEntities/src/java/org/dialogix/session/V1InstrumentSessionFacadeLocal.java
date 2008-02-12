/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.*;

/**
 *
 * @author Coevtmw
 */
@Local
public interface V1InstrumentSessionFacadeLocal {

    void create(V1InstrumentSession v1InstrumentSession);

    void edit(V1InstrumentSession v1InstrumentSession);

    void remove(V1InstrumentSession v1InstrumentSession);

    V1InstrumentSession find(Long id);

    List<V1InstrumentSession> findAll();
    
    V1InstrumentSession findByName(String name);

    public V1ItemUsage findV1ItemUsage(Long id);
    
    public V1DataElement findV1DataElement(Long v1DataElementID);

    public javax.persistence.EntityManager getEm();

    
}
