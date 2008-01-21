/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.session;

import java.util.Collection;
import java.util.List;
import javax.ejb.Remote;
import org.dialogix.beans.InstrumentSessionResultBean;
import org.dialogix.entities.*;
/**
 *
 * @author Coevtmw
 */
@Remote
public interface DialogixEntitiesFacadeRemote {
        List<InstrumentSessionResultBean> getFinalInstrumentSessionResults(Long instrumentVersionID, String inVarNameIDs, Boolean sortByName);    
        InstrumentVersion getInstrumentVersion(Long instrumentVersionID);    
	InstrumentVersion getInstrumentVersion(String name, String major, String minor);
        List<InstrumentVersion> getInstrumentVersionCollection();        
	InstrumentSession findInstrumentSessionByName(String name);
	Collection<ActionType> getActionTypes();
	Collection<NullFlavor> getNullFlavors();
	void merge(InstrumentSession instrumentSession);
	void persist(InstrumentSession instrumentSession); 
}
