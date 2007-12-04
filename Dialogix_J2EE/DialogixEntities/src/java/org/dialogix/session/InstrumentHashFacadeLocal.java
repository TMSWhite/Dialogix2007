/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.InstrumentHash;

/**
 *
 * @author George
 */
@Local
public interface InstrumentHashFacadeLocal {

    void create(InstrumentHash instrumentHash);

    void edit(InstrumentHash instrumentHash);

    void remove(InstrumentHash instrumentHash);

    InstrumentHash find(Object id);

    List<InstrumentHash> findAll();

}
