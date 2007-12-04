/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.Instrument;

/**
 *
 * @author George
 */
@Local
public interface InstrumentFacadeLocal {

    void create(Instrument instrument);

    void edit(Instrument instrument);

    void remove(Instrument instrument);

    Instrument find(Object id);

    List<Instrument> findAll();

}
