/*
 * InstrumentSession.java
 * 
 * Created on Nov 2, 2007, 11:15:06 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_sessions")
public class InstrumentSession implements Serializable {
    @TableGenerator(name="instrument_session_gen", pkColumnValue="instrument_session", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="instrument_session_gen")
    @Column(name = "instrument_session_id", nullable = false)
    private Long instrumentSessionID;
    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "last_access_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;
    @Column(name = "instrument_starting_group", nullable = false)
    private int instrumentStartingGroup;
    @Column(name = "current_group", nullable = false)
    private int currentGroup;
    @Column(name = "current_var_name", nullable = false)
    private int currentVarNum;    
    @Column(name = "display_num", nullable = false)
    private int displayNum;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @Column(name = "status_msg")
    private String statusMsg;
    @Column(name = "max_group")
    private Integer maxGroup;
    @Column(name = "max_var_num")
    private Integer maxVarNum;    
    @Column(name = "finished")
    private Integer finished;
    @Column(name = "num_vars")
    private Integer numVars;    
    @Column(name = "num_groups")
    private Integer numGroups;    
    @Lob
    @Column(name = "instrument_session_file_name")
    private String instrumentSessionFileName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<DataElement> dataElementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<ItemUsage> itemUsageCollection;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "action_type_id", referencedColumnName = "action_type_id")
    @ManyToOne
    private ActionType actionTypeID;
    @JoinColumn(name = "dialogix_user_id", referencedColumnName = "dialogix_user_id")
    @ManyToOne
    private DialogixUser dialogixUserID;
    @JoinColumn(name = "instrument_id", referencedColumnName = "instrument_id")
    @ManyToOne
    private Instrument instrumentID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<PageUsage> pageUsageCollection;
    @Column(name = "ip_address", nullable = true)
    private String ipAddress;    
    @Column(name = "browser", nullable = true)
    private String browser;        

    public InstrumentSession() {
    }

    public InstrumentSession(Long instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public InstrumentSession(Long instrumentSessionID, Date startTime, Date lastAccessTime, int instrumentStartingGroup, int currentGroup, int displayNum, String languageCode) {
        this.instrumentSessionID = instrumentSessionID;
        this.startTime = startTime;
        this.lastAccessTime = lastAccessTime;
        this.instrumentStartingGroup = instrumentStartingGroup;
        this.currentGroup = currentGroup;
        this.displayNum = displayNum;
        this.languageCode = languageCode;
    }

    public Long getInstrumentSessionID() {
        return instrumentSessionID;
    }

    public void setInstrumentSessionID(Long instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public int getInstrumentStartingGroup() {
        return instrumentStartingGroup;
    }

    public void setInstrumentStartingGroup(int instrumentStartingGroup) {
        this.instrumentStartingGroup = instrumentStartingGroup;
    }

    public int getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(int currentGroup) {
        this.currentGroup = currentGroup;
    }

    public int getCurrentVarNum() {
        return currentVarNum;
    }

    public void setCurrentVarNum(int currentVarNum) {
        this.currentVarNum = currentVarNum;
    }
    
    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
    
    public Integer getMaxGroup() {
        return maxGroup;
    }

    public void setMaxGroup(Integer maxGroup) {
        this.maxGroup = maxGroup;
    }

    public Integer getMaxVarNum() {
        return maxVarNum;
    }

    public void setMaxVarNum(Integer maxVarNum) {
        this.maxVarNum = maxVarNum;
    }
    
    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }    
    
    public Integer getNumVars() {
        return numVars;
    }

    public void setNumVars(Integer numVars) {
        this.numVars = numVars;
    }
    
    public Integer getNumGroups() {
        return numGroups;
    }

    public void setNumGroups(Integer numGroups) {
        this.numGroups = numGroups;
    }    

    public String getInstrumentSessionFileName() {
        return instrumentSessionFileName;
    }

    public void setInstrumentSessionFileName(String instrumentSessionFileName) {
        this.instrumentSessionFileName = instrumentSessionFileName;
    }

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }

    public void setDataElementCollection(Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
    }

    public Collection<ItemUsage> getItemUsageCollection() {
        return itemUsageCollection;
    }

    public void setItemUsageCollection(Collection<ItemUsage> itemUsageCollection) {
        this.itemUsageCollection = itemUsageCollection;
    }

    public InstrumentVersion getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(InstrumentVersion instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public ActionType getActionTypeID() {
        return actionTypeID;
    }

    public void setActionTypeID(ActionType actionTypeID) {
        this.actionTypeID = actionTypeID;
    }

    public DialogixUser getDialogixUserID() {
        return dialogixUserID;
    }

    public void setDialogixUserID(DialogixUser dialogixUserID) {
        this.dialogixUserID = dialogixUserID;
    }

    public Instrument getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(Instrument instrumentID) {
        this.instrumentID = instrumentID;
    }

    public Collection<PageUsage> getPageUsageCollection() {
        return pageUsageCollection;
    }

    public void setPageUsageCollection(Collection<PageUsage> pageUsageCollection) {
        this.pageUsageCollection = pageUsageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentSessionID != null ? instrumentSessionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InstrumentSession)) {
            return false;
        }
        InstrumentSession other = (InstrumentSession) object;
        if ((this.instrumentSessionID == null && other.instrumentSessionID != null) || (this.instrumentSessionID != null && !this.instrumentSessionID.equals(other.instrumentSessionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentSession[instrumentSessionID=" + instrumentSessionID + "]";
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

}
