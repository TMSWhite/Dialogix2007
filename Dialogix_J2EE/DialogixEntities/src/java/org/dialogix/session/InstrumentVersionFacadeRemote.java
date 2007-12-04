/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.List;
import javax.ejb.Remote;
import org.dialogix.entities.InstrumentVersion;

/**
 *
 * @author George
 */
@Remote
public interface InstrumentVersionFacadeRemote {

    void create(InstrumentVersion instrumentVersion);

    void edit(InstrumentVersion instrumentVersion);

    void remove(InstrumentVersion instrumentVersion);

    InstrumentVersion find(Object id);

    List<InstrumentVersion> findAll();

}
