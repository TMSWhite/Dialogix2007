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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_session")
@NamedQueries({@NamedQuery(name = "InstrumentSession.findByInstrumentSessionID", query = "SELECT i FROM InstrumentSession i WHERE i.instrumentSessionID = :instrumentSessionID"), @NamedQuery(name = "InstrumentSession.findByStartTime", query = "SELECT i FROM InstrumentSession i WHERE i.startTime = :startTime"), @NamedQuery(name = "InstrumentSession.findByLastAccessTime", query = "SELECT i FROM InstrumentSession i WHERE i.lastAccessTime = :lastAccessTime"), @NamedQuery(name = "InstrumentSession.findByInstrumentStartingGroup", query = "SELECT i FROM InstrumentSession i WHERE i.instrumentStartingGroup = :instrumentStartingGroup"), @NamedQuery(name = "InstrumentSession.findByCurrentGroup", query = "SELECT i FROM InstrumentSession i WHERE i.currentGroup = :currentGroup"), @NamedQuery(name = "InstrumentSession.findByDisplayNum", query = "SELECT i FROM InstrumentSession i WHERE i.displayNum = :displayNum"), @NamedQuery(name = "InstrumentSession.findByLanguageCode", query = "SELECT i FROM InstrumentSession i WHERE i.languageCode = :languageCode"), @NamedQuery(name = "InstrumentSession.findByStatusMsg", query = "SELECT i FROM InstrumentSession i WHERE i.statusMsg = :statusMsg")})
public class InstrumentSession implements Serializable {
    @TableGenerator(name="InstrumentSession_Generator", pkColumnValue="InstrumentSession", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="InstrumentSession_Generator")
    @Column(name = "InstrumentSession_ID", nullable = false)
    private Integer instrumentSessionID;
    @Column(name = "StartTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "LastAccessTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;
    @Column(name = "InstrumentStartingGroup", nullable = false)
    private int instrumentStartingGroup;
    @Column(name = "CurrentGroup", nullable = false)
    private int currentGroup;
    @Column(name = "DisplayNum", nullable = false)
    private int displayNum;
    @Column(name = "LanguageCode", nullable = false)
    private String languageCode;
    @Column(name = "StatusMsg")
    private String statusMsg;
    @Lob
    @Column(name = "InstrumentSessionFileName")
    private String instrumentSessionFileName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<DataElement> dataElementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<ItemUsage> itemUsageCollection;
    @JoinColumn(name = "InstrumentVersion_ID", referencedColumnName = "InstrumentVersion_ID")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "ActionType_ID", referencedColumnName = "ActionType_ID")
    @ManyToOne
    private ActionType actionTypeID;
    @JoinColumn(name = "User_ID", referencedColumnName = "User_ID")
    @ManyToOne
    private User userID;
    @JoinColumn(name = "Instrument_ID", referencedColumnName = "Instrument_ID")
    @ManyToOne
    private Instrument instrumentID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<PageUsage> pageUsageCollection;

    public InstrumentSession() {
    }

    public InstrumentSession(Integer instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public InstrumentSession(Integer instrumentSessionID, Date startTime, Date lastAccessTime, int instrumentStartingGroup, int currentGroup, int displayNum, String languageCode) {
        this.instrumentSessionID = instrumentSessionID;
        this.startTime = startTime;
        this.lastAccessTime = lastAccessTime;
        this.instrumentStartingGroup = instrumentStartingGroup;
        this.currentGroup = currentGroup;
        this.displayNum = displayNum;
        this.languageCode = languageCode;
    }

    public Integer getInstrumentSessionID() {
        return instrumentSessionID;
    }

    public void setInstrumentSessionID(Integer instrumentSessionID) {
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

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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

}
