/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Remote;
import org.dialogix.entities.Instrument;

/**
 *
 * @author George
 */
@Remote
public interface InstrumentFacadeRemote {

    void create(Instrument instrument);

    void edit(Instrument instrument);

    void remove(Instrument instrument);

    Instrument find(Object id);

    List<Instrument> findAll();

}
