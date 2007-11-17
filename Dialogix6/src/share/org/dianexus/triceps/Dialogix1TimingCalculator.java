package org.dianexus.triceps;

import java.sql.Timestamp; // FIXME - shouldn't we be moving away from sql Timestamps?
import java.util.*;
import javax.persistence.*;

import org.dialogix.model1.*;
import org.apache.log4j.Logger;

/**
This class consolidates all of the timing functionality, including processing events, and determining response times
 */
public class Dialogix1TimingCalculator {

    static Logger logger = Logger.getLogger(Dialogix1TimingCalculator.class);
    private static EntityManagerFactory emf;    
    private int displayCount = 0;
    private int FromGroupNum = 0;
    private int ToGroupNum = 0;
    private String lastAction = "";
    private String statusMsg = "";
    private int startingStep;
    private String instrumentTitle = "";
    private String instrumentVersionFileName = null;
    private boolean initialized = false;
    private long priorTimeEndServerProcessing;
    private long timeBeginServerProcessing;
    private long timeEndServerProcessing;
    private int networkDuration;
    private int serverDuration;
    private int loadDuration;
    private int pageDuration;
    private int totalDuration;
    private String langCode;
    private V1InstrumentSession v1InstrumentSession = null;
    private V1ItemUsage v1ItemUsage = null;
    private V1DataElement v1DataElement = null;
    private ArrayList<V1DataElement> v1DataElements = null;
    private ArrayList<V1ItemUsage> v1ItemUsages = null;
    private int v1ItemUsages_lastCount = 0;
    private HashMap<String,V1DataElement> v1DataElementHash = null;
    private String v1InstrumentSessionFileName = null;
    private int v1ItemUsageCounter = 0;
    private int groupNum = 0;
    private boolean withinBlock = false;

    /**
    Empty constructor to avoid NullPointerException
     */
    public Dialogix1TimingCalculator() {
        initialized = false;
    }

    /**
    Constructor.  This loads the proper instrument from the database (based upon title, and version).
    Initializes the session.
    @param instrumentTitle	The title of the instrument
    @param major_version Major version
    @param minor_version Minor version
    @param filename name of file to which data is written
    @param startingStep	The starting step (first group)
    @param varNames list of  variable names
    @param actionTypes list of actionTypes - needed to determine the GroupNum
     */
    public Dialogix1TimingCalculator(String instrumentFilename, String instrumentTitle, String major_version, String minor_version, int startingStep, String filename, ArrayList<String> varNames, ArrayList<String> actionTypes) {
        try {
            beginServerProcessing(System.currentTimeMillis());
            
            setStatusMsg("init");
            setLastAction("START");
            setFromGroupNum(startingStep);
            setToGroupNum(startingStep);
            setPriorTimeEndServerProcessing(getTimeBeginServerProcessing());

            if (major_version == null) {
                major_version = "0";
            }
            if (minor_version == null) {
                minor_version = "0";
            }
            
            setInstrumentTitle(instrumentTitle + "(" + major_version + "." + minor_version + ")");
            this.startingStep = startingStep;
            this.v1InstrumentSessionFileName = filename;
            
            setInstrumentVersionFileName(instrumentFilename);

            // Create V1InstrumentSession Bean - as side effect, sets all startup values (which may be inappropriate)
            v1InstrumentSession = new V1InstrumentSession();
            v1InstrumentSession.setInstrumentVersionName(getInstrumentTitle());
            v1InstrumentSession.setStartTime(new Timestamp(System.currentTimeMillis()));
            v1InstrumentSession.setLastAccessTime(v1InstrumentSession.getStartTime());
            v1InstrumentSession.setInstrumentStartingGroup(startingStep);            
            v1InstrumentSession.setCurrentGroup(startingStep);
            v1InstrumentSession.setDisplayNum(getDisplayCount());
            v1InstrumentSession.setLanguageCode("en");
            v1InstrumentSession.setActionType("START");
            v1InstrumentSession.setStatusMsg("init");
            v1InstrumentSession.setInstrumentVersionFileName(getInstrumentVersionFileName()); 
            v1InstrumentSession.setInstrumentSessionFileName(getV1InstrumentSessionFileName()); 
            
            v1DataElements = new ArrayList<V1DataElement>();
            v1DataElementHash = new HashMap<String,V1DataElement>();
            
            Iterator<String> iterator = varNames.iterator();
            Iterator<String> actionTypeIterator = actionTypes.iterator();
            int dataElementSequence = 0;
            while (iterator.hasNext()) {
                String varName = iterator.next();
                v1DataElement = new V1DataElement();
                
                v1DataElement.setAnswerCode("*UNASKED*");
                v1DataElement.setAnswerString("*UNASKED*");
                v1DataElement.setDisplayNum(0);
                v1DataElement.setDataElementSequence(++dataElementSequence);   
                
                if (actionTypeIterator.hasNext()) {
                    String actionType = actionTypeIterator.next();
                    parseGroupNum(actionType);
                    v1DataElement.setGroupNum(groupNum);
                }
                else {
                    v1DataElement.setGroupNum(-1); 
                }
                v1DataElement.setItemVisits(-1);
                v1DataElement.setLanguageCode(getLangCode());
                v1DataElement.setVarName(varName);
                v1DataElements.add(v1DataElement);
                v1DataElementHash.put(varName, v1DataElement);
            }
            
            v1ItemUsages = new ArrayList<V1ItemUsage>();
            
            v1InstrumentSession.setV1ItemUsageCollection(v1ItemUsages);
            persist(v1InstrumentSession);  
            
            initialized = true;
        } catch (Throwable e) {
            logger.error("", e);
        }
    }

    /**
    This is called when the server receives a request.  It starts the the clock for server processing time.
    Side effects include:
    (1) Increasing displayCount
    (2) Setting displayCount, groupNum, and storing that information to pageHits
    @param timestamp	System time in milliseconds
     */
    public void beginServerProcessing(Long timestamp) {
        try {
            setTimeBeginServerProcessing(System.currentTimeMillis());
            incrementDisplayCount();
        } catch (Throwable e) {
            logger.error("beginServerProcessing", e);
        }
    }

    /**
    This is called when the server is ready to return a response to the user.
    ServerProcessingTime = (finishServerProcessing.timestamp - beginServerProcessing.timestamp)
    @param timestamp	Current system time
     */
    public void finishServerProcessing(Long timestamp) {
        try {
            if (initialized == false) {
                logger.info("Dialogix1TimingCalculator not yet initialized");
                return;
            }
            
            setTimeEndServerProcessing(System.currentTimeMillis()); 
            setServerDuration((int) (getTimeEndServerProcessing() - getTimeBeginServerProcessing()));
            setTotalDuration((int) (getTimeBeginServerProcessing() - getPriorTimeEndServerProcessing()));
            setNetworkDuration(getTotalDuration() - getLoadDuration() - getPageDuration() - getServerDuration());
            
            // Set timing infomation for all new ItemUsage
            for (int i=v1ItemUsages_lastCount;i<v1ItemUsages.size();++i) {
                v1ItemUsage = v1ItemUsages.get(i);
                v1ItemUsage.setLoadDuration(getLoadDuration());
                v1ItemUsage.setNetworkDuration(getNetworkDuration());
                v1ItemUsage.setPageDuration(getPageDuration());
                v1ItemUsage.setServerDuration(getServerDuration());
                v1ItemUsage.setTotalDuration(getTotalDuration());
            }

             // Update Session State
            v1InstrumentSession.setLastAccessTime(new Timestamp(timestamp.longValue()));
            v1InstrumentSession.setCurrentGroup(getToGroupNum());
            v1InstrumentSession.setActionType(getLastAction());
            v1InstrumentSession.setDisplayNum(getDisplayCount());
            v1InstrumentSession.setLanguageCode(getLangCode());
            v1InstrumentSession.setStatusMsg(getStatusMsg());

            // Finally, update GroupNum to reflect where should land
            setFromGroupNum(getToGroupNum());
            setPriorTimeEndServerProcessing(getTimeEndServerProcessing());
            
            merge(v1InstrumentSession);

            v1ItemUsages_lastCount = v1ItemUsages.size();
        } catch (Throwable e) {
            logger.error("", e);
        }
    }
    
    /**
    This assigns a value (Datum) to an item (Node).
    @param ques	the Item
    @param ans	the Value
     */
    public void writeNode(Node ques, Datum ans) {
        try {
            if (ques != null && ans != null) {
                String answerCode = InputEncoder.encode(ans.stringVal(true));
                String answerString = InputEncoder.encode(ques.getLocalizedAnswer(ans)); 
                String questionAsAsked = InputEncoder.encode(ques.getQuestionAsAsked());
                String v1VarNameString = ques.getLocalName();
                Timestamp timestamp = new Timestamp(ques.getTimeStamp().getTime());
                
                v1DataElement = v1DataElementHash.get(v1VarNameString);
                if (v1DataElement == null) {
                    logger.error("Attempt to write to unitialized V1DataElement " + v1VarNameString);
                    return;
                }
                
                v1DataElement.setItemVisits(v1DataElement.getItemVisits() + 1);
                
                if (v1DataElement.getItemVisits() == 0) {
                    return; // don't write initial *UNASKED* values
                }
               
                // Update log-file of changed values
                v1ItemUsage = new V1ItemUsage();
                v1ItemUsage.setItemUsageSequence(++v1ItemUsageCounter);
                v1ItemUsage.setAnswerCode(answerCode);
                v1ItemUsage.setAnswerString(answerString);
                v1ItemUsage.setComments(ques.getComment());
                v1ItemUsage.setDataElementSequence(v1DataElement.getDataElementSequence());
                v1ItemUsage.setDisplayNum(getDisplayCount());
                v1ItemUsage.setGroupNum(v1DataElement.getGroupNum());
                v1ItemUsage.setItemVisits(v1DataElement.getItemVisits());
                v1ItemUsage.setLanguageCode(getLangCode());
                v1ItemUsage.setQuestionAsAsked(questionAsAsked);
                v1ItemUsage.setWhenAsMS(ques.getTimeStamp().getTime());
                v1ItemUsage.setTimeStamp(timestamp);
                v1ItemUsage.setV1InstrumentSessionID(v1InstrumentSession);   
                v1ItemUsage.setVarName(v1DataElement.getVarName());
                v1ItemUsages.add(v1ItemUsage);
                
//                v1InstrumentSession.getV1ItemUsageCollection().add(v1ItemUsage);
            }
        } catch (Throwable e) {
            logger.error("WriteNode Error", e);
        }
    }

    public void incrementDisplayCount() {
        setDisplayCount(getDisplayCount() + 1);
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getLastAction() {
        return lastAction;
    }
    
    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setTimeBeginServerProcessing(long time) {
        timeBeginServerProcessing = time;
    }

    public long getTimeBeginServerProcessing() {
        return timeBeginServerProcessing;
    }

    public void setPriorTimeEndServerProcessing(long time) {
        priorTimeEndServerProcessing = time;
    }

    public long getPriorTimeEndServerProcessing() {
        return priorTimeEndServerProcessing;
    }

    public void setTimeEndServerProcessing(long time) {
        timeEndServerProcessing = time;
    }

    public long getTimeEndServerProcessing() {
        return timeEndServerProcessing;
    }

    public void setNetworkDuration(int time) {
        networkDuration = time;
    }

    public int getNetworkDuration() {
        return networkDuration;
    }

    public void setServerDuration(int serverDuration) {
        this.serverDuration = serverDuration;
    }

    public int getServerDuration() {
        return serverDuration;
    }

    public void setLangCode(String langCode) {
        if (langCode.length() > 2) {
            this.langCode = langCode.substring(0,2);
        }
        else {
            this.langCode = langCode;
        }
    }

    public String getLangCode() {
        if (langCode == null) {
            return "en";
        } else {
            return langCode;
        }
    }
    
    public int getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(int displayCount) {
        this.displayCount = displayCount;
    }

    public int getStartingStep() {
        return startingStep;
    }

    public void setStartingStep(int startingStep) {
        this.startingStep = startingStep;
    }
    
    public void setV1InstrumentSessionFileName(String filename) {
        this.v1InstrumentSessionFileName = filename;
    }
    
    public String getV1InstrumentSessionFileName() {
        return this.v1InstrumentSessionFileName;
    }

    private void setFromGroupNum(int groupNum) {
        this.FromGroupNum = groupNum;
    }
    
    private int getFromGroupNum() {
        return this.FromGroupNum;
    }    

    public void setToGroupNum(int groupNum) {
        this.ToGroupNum = groupNum;
    }
    
    private int getToGroupNum() {
        return this.ToGroupNum;
    }
    
    public String getInstrumentTitle() {
        return instrumentTitle;
    }

    public void setInstrumentTitle(String instrumentTitle) {
        this.instrumentTitle = instrumentTitle;
    }

    public int getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(int loadDuration) {
        this.loadDuration = loadDuration;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getPageDuration() {
        return pageDuration;
    }

    public void setPageDuration(int pageDuration) {
        this.pageDuration = pageDuration;
    }    
    
    public String getInstrumentVersionFileName() {
        return instrumentVersionFileName;
    }

    public void setInstrumentVersionFileName(String instrumentVersionFileName) {
        this.instrumentVersionFileName = instrumentVersionFileName;
    }    
    
    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("Dialogix1DomainPU");
        }
        return emf.createEntityManager();
    }
    
    static public void persist(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Throwable e) {
            logger.error("", e);
            em.getTransaction().rollback();
        } finally {
            try {
                em.close();
            } catch (Throwable e) {
            }
        }
    }

    static public void merge(Object object) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(object);
            em.getTransaction().commit();
        } catch (Throwable e) {
            logger.error("", e);
            em.getTransaction().rollback();
        } finally {
            try {
                em.close();
            } catch (Throwable e) {
            }
        }
    }
    
    /**
    Determine the GroupNum based upon item grouping paremeters (actionType)
    @return groupNum
     */
    int parseGroupNum(String actionType) {
        if (actionType == null) {
            logger.error("No actionType specified");
        }
        if (actionType.length() > 1) {
            actionType = actionType.substring(0,1);
        }
        
        if (actionType.equals("[")) {
            if (withinBlock == true) {
                logger.error("Trying to create a nested group of items");
            } else {
                withinBlock = true;
                ++groupNum;
            }
        } else if (actionType.equals("]")) {
            if (withinBlock == false) {
                logger.error("Trying to close a group of items with no matching '['");
            } else {
                withinBlock = false;
            }
        } else if (actionType.equals("q")) {
            if (withinBlock == false) {
                ++groupNum;
            }
        } else if (actionType.equals("e")) {
            if (withinBlock == true) {
                logger.error("Trying to process an equation within a group of items");
            }
        }
        return groupNum;
    }
    
    /**
     * Takes parameter src which contains the event timing data from the
     * http request parameter EVENT_TIMINGS and creates an event timing bean for each
     * event.
     *
     * @param eventStrng
     */
    public void processEvents(String eventString) {
        if (eventString == null || eventString.trim().length() == 0) {
            return;
        }
        if (initialized == false) {
            return;
        }

        StringTokenizer st = new StringTokenizer(eventString, "\t", false);
        int tokenCount = st.countTokens();
        for (int count = 1; st.hasMoreTokens(); ++count) {
            String token = st.nextToken();
            if (count == 1 || count == tokenCount)  {
                StringTokenizer str = new StringTokenizer(token,",",false);
                int subtCount = 0;
                while (str.hasMoreTokens()) {
                    String subt = str.nextToken();
                    switch(subtCount) {
                    case 4:
                        if (count == 1) {
                            setLoadDuration(new Integer(subt).intValue());
                        }
                        if (count == tokenCount) {
                            setPageDuration(new Integer(subt).intValue() - getLoadDuration());
                        }
                    }
                    ++subtCount;
                }
            }
        }
    }
}