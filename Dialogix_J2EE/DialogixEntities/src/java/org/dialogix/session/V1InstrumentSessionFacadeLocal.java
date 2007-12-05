/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.V1InstrumentSession;

/**
 *
 * @author Coevtmw
 */
@Local
public interface V1InstrumentSessionFacadeLocal {

    void create(V1InstrumentSession v1InstrumentSession);

    void edit(V1InstrumentSession v1InstrumentSession);

    void remove(V1InstrumentSession v1InstrumentSession);

    V1InstrumentSession find(Object id);

    List<V1InstrumentSession> findAll();
    
    V1InstrumentSession findByName(String name);    

}
