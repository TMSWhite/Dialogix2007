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
        void merge(SubjectSession subjectSession);
        SubjectSession findSubjectSessionById(Long id);
        List<InstrumentLoadError> getInstrumentLoadErrors(InstrumentVersion instrumentVersion);
        Collection<InstrumentSession> getMyInstrumentSessions(Person person);
        Study findStudyById(Long studyId);
        Person findPersonById(Long personId);
        List<PageUsageEvent> getPageUsageEvents(Long pageUsageId);
        List<InstrumentSessionResultBean> getFinalInstrumentSessionResults(Long instrumentVersionID, String inVarNameIDs, Boolean sortByName);
        InstrumentSession getInstrumentSession(Long instrumentSessionID);
        List<InstrumentSession> getInstrumentSessions(InstrumentVersion instrumentVersionID);        
        InstrumentVersion getInstrumentVersion(Long instrumentVersionID);
	InstrumentVersion getInstrumentVersion(String name, String major, String minor);
        List<InstrumentVersion> getInstrumentVersionCollection();
        List<InstrumentVersionView> getAuthorizedInstrumentVersions(Person person);
        List<ItemUsage> getItemUsages(Long instrumentSessionID);
        ItemUsage getItemUsage(Long itemUsageId);
	InstrumentSession findInstrumentSessionByName(String name);
        VarName findVarNameByName(String name);
	Collection<ActionType> getActionTypes();
	Collection<NullFlavor> getNullFlavors();
        Collection<NullFlavorChange> getNullFlavorChanges();
	void merge(InstrumentSession instrumentSession);
	void persist(InstrumentSession instrumentSession);   
        Person getPerson(String userName, String pwd);
        List<Menu> getMenus(Person person);
        List<Study> getStudies();
        InstrumentContent findInstrumentContentByInstrumentVersionAndVarName(InstrumentVersion instrumentVersionId, String varName);
        void refresh(Object object);
}
