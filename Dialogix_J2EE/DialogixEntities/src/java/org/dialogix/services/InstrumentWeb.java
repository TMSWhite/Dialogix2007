/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.services;

import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.dialogix.beans.InstrumentSessionResultBean;
import org.dialogix.beans.InstrumentVersionView;
import org.dialogix.entities.ActionType;
import org.dialogix.entities.InstrumentSession;
import org.dialogix.entities.InstrumentVersion;
import org.dialogix.entities.ItemUsage;
import org.dialogix.entities.NullFlavor;
import org.dialogix.session.DialogixEntitiesFacadeLocal;

/**
 *
 * @author George
 */
@WebService()
@Stateless()
public class InstrumentWeb {
    @EJB
    private DialogixEntitiesFacadeLocal ejbRef;
    // Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "getFinalInstrumentSessionResults")
    public List<InstrumentSessionResultBean> getFinalInstrumentSessionResults(Long instrumentVersionID, String inVarNameIDs, Boolean sortByName) {
        return ejbRef.getFinalInstrumentSessionResults(instrumentVersionID, inVarNameIDs, sortByName);
    }

    @WebMethod(operationName = "getInstrumentSession")
    public InstrumentSession getInstrumentSession(Long instrumentSessionID) {
        return ejbRef.getInstrumentSession(instrumentSessionID);
    }

    @WebMethod(operationName = "getInstrumentSessions")
    public List<InstrumentSession> getInstrumentSessions(InstrumentVersion instrumentVersionID) {
        return ejbRef.getInstrumentSessions(instrumentVersionID);
    }

    
    @WebMethod(operationName = "getItemUsages")
    public List<ItemUsage> getItemUsages(Long instrumentSessionID) {
        return ejbRef.getItemUsages(instrumentSessionID);
    }

    @WebMethod(operationName = "findInstrumentSessionByName")
    public InstrumentSession findInstrumentSessionByName(String name) {
        return ejbRef.findInstrumentSessionByName(name);
    }

    @WebMethod(operationName = "merge")
    @Oneway
    public void merge(InstrumentSession instrumentSession) {
        ejbRef.merge(instrumentSession);
    }

    @WebMethod(operationName = "persist")
    @Oneway
    public void persist(InstrumentSession instrumentSession) {
        ejbRef.persist(instrumentSession);
    }

}
