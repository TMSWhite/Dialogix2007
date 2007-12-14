/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.Collection;
import javax.ejb.Local;
import org.dialogix.entities.*;
/**
 *
 * @author Coevtmw
 */
@Local
public interface DialogixEntitiesFacadeLocal {
	InstrumentVersion getInstrumentVersion(String name, String major, String minor);
	InstrumentSession findInstrumentSessionByName(String name);
	Collection<ActionType> getActionTypes();
	Collection<NullFlavor> getNullFlavors();
	void merge(InstrumentSession instrumentSession);
	void persist(InstrumentSession instrumentSession);   
}
