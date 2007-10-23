/*
 * InstrumentSessionFacade.java
 * 
 * Created on Oct 8, 2007, 4:13:05 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domainManager;


import javax.persistence.EntityManager; 
import javax.persistence.EntityManagerFactory; 
import javax.persistence.Persistence;
import org.dialogix.domain.InstrumentSession;
//import javax.persistence

/**
 *
 * @author George
 */
public class InstrumentSessionFacade {
    
    private EntityManagerFactory emf;

    private EntityManager getEntityManager() {

        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DialogixDomainPU");
        }
        return emf.createEntityManager();
    }
    /*
    public Users[] getUsers() {

        EntityManager em = getEntityManager();
        try {
            javax.persistence.Query q = em.createQuery("select c from Users as c");
            return (Users[]) q.getResultList().toArray(new Users[0]);
        } finally {
            em.close();
        }
    }
    */
    public InstrumentSession getInstrumentSessionById(long Id) {
        EntityManager em = getEntityManager();        
        
        try {
            return em.find(InstrumentSession.class, Id);
        } finally {
            em.close();
        }
    }

    public void  createInstrumentSession(InstrumentSession InstrumentSession){
    EntityManager em = getEntityManager();
    try{
        em.getTransaction().begin();
        
        em.persist(InstrumentSession);
        em.getTransaction().commit();
    }finally{
        
    }
}

    
    public void editInstrumentSession(long InstrumentSessionId){
    EntityManager em = getEntityManager();
    try{
        em.getTransaction().begin();
        InstrumentSession InstrumentSession = getInstrumentSessionById(InstrumentSessionId);
        //InstrumentSession.setEndTime(endTime);
//        InstrumentSession.setFirstGroup(1);
        em.getTransaction().commit();
    }finally{
        em.close();
    }
}

    public void deleteInstrumentSession(long Id){
    EntityManager em = getEntityManager();
    try{
        em.getTransaction().begin();
        InstrumentSession InstrumentSession = em.find(InstrumentSession.class, Id);
        em.remove(InstrumentSession);
        em.getTransaction().commit();
    }finally{
        em.close();
    }
}

    
}