/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Local;
import org.dialogix.entities.InstrumentVersion;

/**
 *
 * @author George
 */
@Local
public interface InstrumentVersionFacadeLocal {

    void create(InstrumentVersion instrumentVersion);

    void edit(InstrumentVersion instrumentVersion);

    void remove(InstrumentVersion instrumentVersion);

    InstrumentVersion find(Object id);

    List<InstrumentVersion> findAll();

}
