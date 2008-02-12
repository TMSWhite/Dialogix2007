/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.services;

import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ejb.Stateless;
import org.dialogix.entities.V1DataElement;
import org.dialogix.entities.V1InstrumentSession;
import org.dialogix.entities.V1ItemUsage;
import org.dialogix.session.V1InstrumentSessionFacadeLocal;

/**
 *
 * @author George
 */
@WebService()
@Stateless()
public class DialogixWeb {

    @EJB
    private V1InstrumentSessionFacadeLocal ejbRef;
    // Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"
    @WebMethod(operationName = "create")
    @Oneway
    public void create(V1InstrumentSession v1InstrumentSession) {
        ejbRef.create(v1InstrumentSession);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(V1InstrumentSession v1InstrumentSession) {
        ejbRef.edit(v1InstrumentSession);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(V1InstrumentSession v1InstrumentSession) {
        ejbRef.remove(v1InstrumentSession);
    }

    @WebMethod(operationName = "find")
    public V1InstrumentSession find(Long id) {
        V1InstrumentSession v1InstrumentSession = ejbRef.find(id);        
        // detach EntityManager to not persist changes 
        ejbRef.getEm().clear();
        v1InstrumentSession.setV1PageUsageCollection(null);
        /*
        Iterator<V1DataElement> v1DataElementIterator =
                v1InstrumentSession.getV1DataElementCollection().iterator();
        while (v1DataElementIterator.hasNext()) {
            V1DataElement v1DataElement = v1DataElementIterator.next();
            //v1DataElement.setV1ItemUsageCollection(null);
            //v1DataElement.setV1InstrumentSessionID(null);
        }
        */
        return v1InstrumentSession;
    }

    @WebMethod(operationName = "findAll")
    public List<V1InstrumentSession> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findByName")
    public V1InstrumentSession findByName(String name) {
        return ejbRef.findByName(name);
    }

    @WebMethod(operationName = "findV1ItemUsage")
    public V1ItemUsage findV1ItemUsage(Long id) {
        return ejbRef.findV1ItemUsage(id);
    }

    @WebMethod(operationName = "findV1DataElement")
    public V1DataElement findV1DataElement(Long v1DataElementID) {
        return ejbRef.findV1DataElement(v1DataElementID);
    }
}
