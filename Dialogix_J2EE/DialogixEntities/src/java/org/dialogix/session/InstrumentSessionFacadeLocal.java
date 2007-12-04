/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.InstrumentSession;

/**
 *
 * @author George
 */
@Local
public interface InstrumentSessionFacadeLocal {

    void create(InstrumentSession instrumentSession);

    void edit(InstrumentSession instrumentSession);

    void remove(InstrumentSession instrumentSession);

    InstrumentSession find(Object id);

    List<InstrumentSession> findAll();

}
