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

import org.dialogix.entities.Instrumentversion;
import org.dialogix.entities.Instver1;
import org.dialogix.entities.Instrumentsession;
import org.dialogix.entities.Instrumentcontent;
import org.dialogix.entities.Datum;
import org.dialogix.entities.LoincInstrumentrequest;
import org.dialogix.entities.Instrumentheader;
import org.dialogix.entities.Actiontype;
import org.dialogix.entities.Instrument;
import org.dialogix.entities.Languagelist;
import org.dialogix.entities.Help;
import org.dialogix.entities.Displaytype;
import org.dialogix.entities.Item;
import org.dialogix.entities.Varname;

import org.dialogix.entities.Pageusage;
import org.dialogix.entities.Pageusageevent;

/**
 *
 * @author George
 */
public class ProcessEntities {

    private EntityManagerFactory emf;
    private Instrumentcontent instrumentContent = null;
    private Instrumentversion instrumentVersion = null;
    private Instrumentsession instrumentSession = null;
    private Instver1 instver1 = null;

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

                if (instrumentSession.getDatumCollection().size() > 0) {
                    
                }

               
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instrumentVersion = null;
        }
    }

    public Instrumentversion getInstrumentVersion() {
        return instrumentVersion;
    }

    private EntityManager getEntityManager() {
        System.out.println("Getting EntityManager");

        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DialogixEntitiesPU");
        }
        return emf.createEntityManager();
    }

    public Instrumentversion getInstrumentVersionById(Integer Id) {
        EntityManager em = getEntityManager();
        System.out.println("Getting InstrumentVersion");
        try {
            return em.find(Instrumentversion.class, Id);
        } finally {
            em.close();
        }
    }

    public Instrumentsession getInstrumentSessionById(Integer Id) {
        EntityManager em = getEntityManager();
        System.out.println("Getting InstrumentSession");
        try {
            return em.find(Instrumentsession.class, Id);
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
        instrumentVersion.setMajorVersion(1);
        instrumentVersion.setMinorVersion(1);
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
        instrumentSession = new Instrumentsession();
        instrumentSession.setInstrumentVersionID(instrumentVersion);
        instrumentSession.setCurrentGroup(1);
        instrumentSession.setDisplayNum(1);
        instrumentSession.setInstrumentStartingGroup(1);
        instrumentSession.setLangCode("langCode");
        instrumentSession.setLastAccessTime(new Date());
        instrumentSession.setStartTime(new Date());
        instrumentSession.setStatusMsg("statusMsg");
        // Lookups begin-------------
        Integer Id = 1;
        try {
            System.out.println("Lookup and set ActionType");
            Actiontype actionType = em.find(Actiontype.class, Id);
            instrumentSession.setActionTypeID(actionType);
            System.out.println("Lookup and set Instrument");
            Instrument instrument = em.find(Instrument.class, Id);
            instrumentSession.setInstrumentID(instrument);
        } finally {
            em.close();
        }
        // George - Assemble object graph 
        addDatum();
        addPageUsage();
        merge(instrumentSession);
     
    }

    public static void addItemLookups() {
    /*
    Item item = new Item();
    // Locate the record to link the created link item record 
    return em.find(Instrumentversion.class, Id);
    item.setInstrument(instrument);
    // Create item record and set instrument foreign key 
    entityManager.persist(item);
     */
    }

    // One to One or One to Many

    public void addIntver1() {
        System.out.println("Adding Intver1 ");
        instver1 = new Instver1();
        instver1.setCurrentGroup(1);
        instver1.setDemo5("demo");
        instver1.setDisplayNum(1);
        instver1.setHasChild("hasChild");
        instver1.setInstrumentStartingGroup(1);
        instver1.setLangCode("LangCode");
        instver1.setLastAccessTime(new Date());
        instver1.setLastAction("lastAction");
        instver1.setMale("male");
        instver1.setName("name");
        instver1.setQ2("q2");
        instver1.setStartTime(new Date());
        //ManytoOne begin-----------
        //instver1.setLanguageID(i);
        //instver1.setInstrumentSessionID(instrumentSessionID);
        //instver1.setInstrumentVersionID(instrumentVersion);
        //ManytoOne end-------------
    }

 
    public void addDatum() {
        Collection<Datum> datums = new ArrayList<Datum>();
        for (int i = 1; i < 6; i++) {
            String num = Integer.toString(i);
            System.out.println("Adding Datum records: " + num);
            Datum datum = new Datum();
            //datum.setDatumID(i); // hack for test
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
                instrumentContent = em.find(Instrumentcontent.class, Id);
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
        instrumentSession.setDatumCollection(datums);
    }

    public void addInstrumentContent() {
        //Collection<Instver1> instver1Collection = (Collection<Instver1>) new Instver1();

        System.out.println("Adding InstrumentContent");
        // Defintion 
        instrumentContent = new Instrumentcontent();
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
            Displaytype displayType = em.find(Displaytype.class, Id);
            instrumentContent.setDisplayTypeID(displayType);
            System.out.println("Lookup and set Item");
            Item item = em.find(Item.class, Id);
            instrumentContent.setItemID(item);
            System.out.println("Lookup and set Item");
            Varname varName = em.find(Varname.class, Id);
            instrumentContent.setVarNameID(varName);
        } finally {
            em.close();
        }
        instrumentVersion.getInstrumentcontentCollection().add(instrumentContent);
    }

    public void addLoincInstrumentRequest() {
        //Collection<Instver1> instver1Collection = (Collection<Instver1>) new Instver1();
        //OnetoOne
        System.out.println("Adding InstrumentRequest records");
        LoincInstrumentrequest loincInstrumentRequest = new LoincInstrumentrequest();
        loincInstrumentRequest.setLOINCmethod("lOINCmethod");
        loincInstrumentRequest.setLOINCproperty("lOINCproperty");
        loincInstrumentRequest.setLOINCsystem("lOINCsystem");
        loincInstrumentRequest.setLOINCtimeAspect("lOINCtimeAspect");
        loincInstrumentRequest.setLoincNum("loincNum");
        // ManytoOne begin --------------------
        //loincInstrumentRequest.setInstrumentVersionID(instrumentVersion);
        // ManytoOne end ----------------------
        instrumentVersion.getLoincInstrumentrequestCollection().add(loincInstrumentRequest);
    }

    public void addInstrumentHeader(Instrumentversion instrumentVersion) {
        //Collection<Instver1> instver1Collection = (Collection<Instver1>) new Instver1();
        // Definition Table 
        Instrumentheader instrumentHeader = new Instrumentheader();
        System.out.println("Adding InstrumentHeader");
        instrumentHeader.setValue("value");
        // ManytoOne begin ------------------
        //instrumentHeader.setReservedWordID(reservedWordID);
        //instrumentHeader.setInstrumentVersionID(instrumentVersion);
        // ManytoOne bend -------------------
        instrumentVersion.getInstrumentheaderCollection().add(instrumentHeader);
    }

    public void addPageUsage() {
        // Create and populate InstrumentVersion  
        Collection<Pageusage> pageUsages = new ArrayList<Pageusage>();
        for (int i = 1; i < 6; i++) {
            Pageusage pageUsage = new Pageusage();
            String num = Integer.toString(i);
            System.out.println("Adding PageUsage records: " + num);
            //pageUsage.setPageUsageID(i);
            pageUsage.setDisplayNum(i);
            pageUsage.setFromGroupNum(i);
            pageUsage.setLangCode("langCode");
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
                Actiontype actionType = em.find(Actiontype.class, Id);
                pageUsage.setActionTypeID(actionType);
                System.out.println("Lookup and set   Instrument");
                Languagelist languagelist = em.find(Languagelist.class, Id);
                pageUsage.setLanguageID(languagelist.getLanguageListID());
            } finally {
                em.close();
            }

            Collection<Pageusageevent> pageusageevents = new ArrayList<Pageusageevent>();
            for (int j = 1; j < 11; j++) {
                Pageusageevent pageUsageEvent = new Pageusageevent();
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
                    Varname varName = em2.find(Varname.class, Id2);
                    pageUsageEvent.setVarNameID(varName);
                } finally {
                    em2.close();
                }
                System.out.println("Adding PageUsageEvent records: " + numj);
                pageUsageEvent.setPageUsageID(pageUsage);
                pageusageevents.add(pageUsageEvent);
            }
            pageUsage.setPageusageeventCollection(pageusageevents);
            pageUsage.setInstrumentSessionID(instrumentSession);
            pageUsages.add(pageUsage);
        }
        instrumentSession.setPageusageCollection(pageUsages);
    }
}
