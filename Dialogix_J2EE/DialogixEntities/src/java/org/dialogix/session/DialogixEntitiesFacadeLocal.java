/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.Collection;
import java.util.List;
import javax.ejb.Local;
import org.dialogix.beans.InstrumentSessionResultBean;
import org.dialogix.beans.InstrumentVersionView;
import org.dialogix.entities.*;
/**
 *
 * @author Coevtmw
 */
@Local
public interface DialogixEntitiesFacadeLocal {
        List<InstrumentSessionResultBean> getFinalInstrumentSessionResults(Long instrumentVersionID, String inVarNameIDs, Boolean sortByName);
        InstrumentSession getInstrumentSession(Long instrumentSessionID);
        List<InstrumentSession> getInstrumentSessions(InstrumentVersion instrumentVersionID);        
        InstrumentVersion getInstrumentVersion(Long instrumentVersionID);
	InstrumentVersion getInstrumentVersion(String name, String major, String minor);
        List<InstrumentVersion> getInstrumentVersionCollection();
        List<InstrumentVersionView> getInstrumentVersions();       
        List<ItemUsage> getItemUsages(Long instrumentSessionID);
	InstrumentSession findInstrumentSessionByName(String name);
	Collection<ActionType> getActionTypes();
	Collection<NullFlavor> getNullFlavors();
	void merge(InstrumentSession instrumentSession);
	void persist(InstrumentSession instrumentSession);   
}
