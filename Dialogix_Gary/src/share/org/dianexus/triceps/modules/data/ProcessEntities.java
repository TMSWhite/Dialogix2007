/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dianexus.triceps.modules.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dialogix.entities.InstrumentVersion;
import org.dialogix.entities.InstVer4;
import org.dialogix.entities.InstrumentSession;
import org.dialogix.entities.InstrumentContent;
import org.dialogix.entities.DataElement;
import org.dialogix.entities.LoincInstrumentRequest;
import org.dialogix.entities.InstrumentHeader;
import org.dialogix.entities.ActionType;
import org.dialogix.entities.Instrument;
import org.dialogix.entities.LanguageList;
import org.dialogix.entities.Help;
import org.dialogix.entities.DisplayType;
import org.dialogix.entities.Item;
import org.dialogix.entities.VarName;

import org.dialogix.entities.PageUsage;
import org.dialogix.entities.PageUsageEvent;

/**
 *
 * @author George
 */
public class ProcessEntities {

    private EntityManagerFactory emf;
    private InstrumentContent instrumentContent = null;
    private InstrumentVersion instrumentVersion = null;
    private InstrumentSession instrumentSession = null;
    private InstVer4 InstVer4 = null;

    public void executePersistEntities() {

        try {
            instrumentVersion = getInstrumentVersionById(1);
            //addInstrumentContent();
            //merge(instrumentVersion);
            addInstrumentSession();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instrumentVersion = null;
        }
    }

    public void executeProcessedEntities() {

        try {
            instrumentSession = getInstrumentSessionById(1);
            if (instrumentSession != null) {

                if (instrumentSession.getDataElementCollection().size() > 0) {
                    
                }

               
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instrumentVersion = null;
        }
    }

    public InstrumentVersion getInstrumentVersion() {
        return instrumentVersion;
    }

    private EntityManager getEntityManager() {
        System.out.println("Getting EntityManager");

        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DialogixEntitiesPU");
        }
        return emf.createEntityManager();
    }

    public InstrumentVersion getInstrumentVersionById(Integer Id) {
        EntityManager em = getEntityManager();
        System.out.println("Getting InstrumentVersion");
        try {
            return em.find(InstrumentVersion.class, Id);
        } finally {
            em.close();
        }
    }

    public InstrumentSession getInstrumentSessionById(Integer Id) {
        EntityManager em = getEntityManager();
        System.out.println("Getting InstrumentSession");
        try {
            return em.find(InstrumentSession.class, Id);
        } finally {
            em.close();
        }
    }

    private void persist(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            System.out.println("Before Persist");
            em.persist(object);
            System.out.println("After Persist");
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    private void merge(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            System.out.println("Before Merge");
            em.merge(object);
            System.out.println("After Merge");
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void setInstrumentVersion() {
        // populate InstrumentVersion    
        instrumentVersion.setVersionString("1.1");
        instrumentVersion.setInstrumentNotes("instrumentNotes");
        instrumentVersion.setInstrumentStatus(0);
        instrumentVersion.setCreationTimeStamp(new Date());
        instrumentVersion.setHasLOINCcode(new Boolean(true));
        instrumentVersion.setLoincNum("loincNum");
        // ManytoOne begin-------------------------------------------------
        //instrumentVersion.setInstrumentHashID(instrumentHashID);
        //instrumentVersion.setInstrumentID(instrumentID);
        // ManytoOne end -------------------------------------------------
    }

    // Many to One 
    
       public void addInstrumentSession() {
        System.out.println("Adding InstrumentSession ");
        EntityManager em = getEntityManager();
        instrumentSession = new InstrumentSession();
        instrumentSession.setInstrumentVersionID(instrumentVersion);
        instrumentSession.setCurrentGroup(1);
        instrumentSession.setDisplayNum(1);
        instrumentSession.setInstrumentStartingGroup(1);
        instrumentSession.setLanguageCode("en");
        instrumentSession.setLastAccessTime(new Date());
        instrumentSession.setStartTime(new Date());
        instrumentSession.setStatusMsg("statusMsg");
        // Lookups begin-------------
        Integer Id = 1;
        try {
            System.out.println("Lookup and set ActionType");
            ActionType actionType = em.find(ActionType.class, Id);
            instrumentSession.setActionTypeID(actionType);
            System.out.println("Lookup and set Instrument");
            Instrument instrument = em.find(Instrument.class, Id);
            instrumentSession.setInstrumentID(instrument);
        } finally {
            em.close();
        }
        // George - Assemble object graph 
        addDataElement();
        addPageUsage();
        merge(instrumentSession);
     
    }

    public static void addItemLookups() {
    /*
    Item item = new Item();
    // Locate the record to link the created link item record 
    return em.find(InstrumentVersion.class, Id);
    item.setInstrument(instrument);
    // Create item record and set instrument foreign key 
    entityManager.persist(item);
     */
    }

    // One to One or One to Many

    public void addIntver1() {
        System.out.println("Adding Intver1 ");
        InstVer4 = new InstVer4();
        InstVer4.setCurrentGroup(1);
        InstVer4.setDemo5("demo");
        InstVer4.setDisplayNum(1);
        InstVer4.setHasChild("hasChild");
        InstVer4.setInstrumentStartingGroup(1);
        InstVer4.setLanguageCode("en");
        InstVer4.setLastAccessTime(new Date());
        InstVer4.setLastAction("lastAction");
        InstVer4.setMale("male");
        InstVer4.setName("name");
        InstVer4.setQ2("q2");
        InstVer4.setStartTime(new Date());
        //ManytoOne begin-----------
        //InstVer4.setLanguageID(i);
        //InstVer4.setInstrumentSessionID(instrumentSessionID);
        //InstVer4.setInstrumentVersionID(instrumentVersion);
        //ManytoOne end-------------
    }

 
    public void addDataElement() {
        Collection<DataElement> datums = new ArrayList<DataElement>();
        for (int i = 1; i < 6; i++) {
            String num = Integer.toString(i);
            System.out.println("Adding DataElement records: " + num);
            DataElement datum = new DataElement();
            //datum.setDataElementID(i); // hack for test
            datum.setAnswerString("answerString");
            datum.setComments("comments");
            datum.setItemVacillation(i);
            datum.setQuestionAsAsked("questionAsAsked");
            datum.setResponseDuration(i);
            datum.setResponseLatency(i);
            datum.setTimeStamp(new Date());
            // Lookups begin-------------
            EntityManager em = getEntityManager();
            Integer Id = 1;
            try {
                System.out.println("Lookup and set InstrumentContent");
                instrumentContent = em.find(InstrumentContent.class, Id);
                datum.setInstrumentContentID(instrumentContent);
            //datum.setVarNameID(varNameID);
               //datum.setLanguageID(languageID);
               //datum.setItemID(itemID);
               //datum.setNullFlavorID(nullFlavorID);
               //datum.setPageUsageID(pageUsageID);
            } finally {
                em.close();
            }
            datum.setInstrumentSessionID(instrumentSession);
            datums.add(datum);
        }
        instrumentSession.setDataElementCollection(datums);
    }

    public void addInstrumentContent() {
        //Collection<InstVer4> InstVer4Collection = (Collection<InstVer4>) new InstVer4();

        System.out.println("Adding InstrumentContent");
        // Defintion 
        instrumentContent = new InstrumentContent();
        instrumentContent.setInstrumentVersionID(instrumentVersion);

        instrumentContent.setActionType("q");
        instrumentContent.setDefaultAnswer("defaultAnswer");
        instrumentContent.setDisplayName("displayName");
        instrumentContent.setFormatMask("formatMask");
        instrumentContent.setGroupNum(1);
        instrumentContent.setIsMessage((short) 0);
        instrumentContent.setIsReadOnly((short) 0);
        instrumentContent.setIsRequired((short) 0);
        instrumentContent.setItemSequence(1);
        instrumentContent.setRelevance("relevance");
        instrumentContent.setSASformat("sASformat");
        instrumentContent.setSASinformat("sASinformat");
        instrumentContent.setSPSSformat("sPSSformat");
        // Get Codesets
        EntityManager em = getEntityManager();
        Integer Id = 1;
        try {
            System.out.println("Lookup and set ActionType");
            Help help = em.find(Help.class, Id);
            instrumentContent.setHelpID(help.getHelpID());
            System.out.println("Lookup and set DisplyionType");
            DisplayType displayType = em.find(DisplayType.class, Id);
            instrumentContent.setDisplayTypeID(displayType);
            System.out.println("Lookup and set Item");
            Item item = em.find(Item.class, Id);
            instrumentContent.setItemID(item);
            System.out.println("Lookup and set Item");
            VarName varName = em.find(VarName.class, Id);
            instrumentContent.setVarNameID(varName);
        } finally {
            em.close();
        }
        instrumentVersion.getInstrumentContentCollection().add(instrumentContent);
    }

    public void addLoincInstrumentRequest() {
        //Collection<InstVer4> InstVer4Collection = (Collection<InstVer4>) new InstVer4();
        //OnetoOne
        System.out.println("Adding InstrumentRequest records");
        LoincInstrumentRequest loincInstrumentRequest = new LoincInstrumentRequest();
        loincInstrumentRequest.setLOINCmethod("lOINCmethod");
        loincInstrumentRequest.setLOINCproperty("lOINCproperty");
        loincInstrumentRequest.setLOINCsystem("lOINCsystem");
        loincInstrumentRequest.setLOINCtimeAspect("lOINCtimeAspect");
        loincInstrumentRequest.setLoincNum("loincNum");
        // ManytoOne begin --------------------
        //loincInstrumentRequest.setInstrumentVersionID(instrumentVersion);
        // ManytoOne end ----------------------
        instrumentVersion.getLoincInstrumentRequestCollection().add(loincInstrumentRequest);
    }

    public void addInstrumentHeader(InstrumentVersion instrumentVersion) {
        //Collection<InstVer4> InstVer4Collection = (Collection<InstVer4>) new InstVer4();
        // Definition Table 
        InstrumentHeader instrumentHeader = new InstrumentHeader();
        System.out.println("Adding InstrumentHeader");
        instrumentHeader.setValue("value");
        // ManytoOne begin ------------------
        //instrumentHeader.setReservedWordID(reservedWordID);
        //instrumentHeader.setInstrumentVersionID(instrumentVersion);
        // ManytoOne bend -------------------
        instrumentVersion.getInstrumentHeaderCollection().add(instrumentHeader);
    }

    public void addPageUsage() {
        // Create and populate InstrumentVersion  
        Collection<PageUsage> pageUsages = new ArrayList<PageUsage>();
        for (int i = 1; i < 6; i++) {
            PageUsage pageUsage = new PageUsage();
            String num = Integer.toString(i);
            System.out.println("Adding PageUsage records: " + num);
            //pageUsage.setPageUsageID(i);
            pageUsage.setDisplayNum(i);
            pageUsage.setFromGroupNum(i);
            pageUsage.setLanguageCode("en");
            pageUsage.setLoadDuration(i);
            pageUsage.setNetworkDuration(i);
            pageUsage.setPageDuration(i);
            pageUsage.setPageVacillation(i);
            pageUsage.setServerDuration(i);
            pageUsage.setStatusMsg("statusMsg");
            pageUsage.setTimeStamp(new Date());
            pageUsage.setToGroupNum(i);
            pageUsage.setTotalDuration(i);
            EntityManager em = getEntityManager();
            Integer Id = 1;
            try {
                System.out.println("Lookup and set ActionType");
                ActionType actionType = em.find(ActionType.class, Id);
                pageUsage.setActionTypeID(actionType);
                System.out.println("Lookup and set   Instrument");
                LanguageList languagelist = em.find(LanguageList.class, Id);
                pageUsage.setLanguageCode("en");
            } finally {
                em.close();
            }

            Collection<PageUsageEvent> pageusageevents = new ArrayList<PageUsageEvent>();
            for (int j = 1; j < 11; j++) {
                PageUsageEvent pageUsageEvent = new PageUsageEvent();
                String numj = Integer.toString(j);
                //pageUsageEvent.setPageUsageEventID(j);
                pageUsageEvent.setDuration(j);
                pageUsageEvent.setTimeStamp(new Date());
                pageUsageEvent.setEventType("EventType");
                pageUsageEvent.setValue1("value1_" + num);
                pageUsageEvent.setValue2("value2_" + num);
                pageUsageEvent.setActionType("actionType");
                EntityManager em2 = getEntityManager();
                Integer Id2 = 1;
                try {
                    VarName varName = em2.find(VarName.class, Id2);
                    pageUsageEvent.setVarNameID(varName);
                } finally {
                    em2.close();
                }
                System.out.println("Adding PageUsageEvent records: " + numj);
                pageUsageEvent.setPageUsageID(pageUsage);
                pageusageevents.add(pageUsageEvent);
            }
            pageUsage.setPageUsageEventCollection(pageusageevents);
            pageUsage.setInstrumentSessionID(instrumentSession);
            pageUsages.add(pageUsage);
        }
        instrumentSession.setPageUsageCollection(pageUsages);
    }
}
