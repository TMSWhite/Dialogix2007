/*
 * InstrumentSession.java
 * 
 * Created on Oct 22, 2007, 4:08:58 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrumentsession")
@NamedQueries({@NamedQuery(name = "InstrumentSession.findByInstrumentSessionID", query = "SELECT i FROM InstrumentSession i WHERE i.instrumentSessionID = :instrumentSessionID"), @NamedQuery(name = "InstrumentSession.findByInstrumentVersionDataID", query = "SELECT i FROM InstrumentSession i WHERE i.instrumentVersionDataID = :instrumentVersionDataID"), @NamedQuery(name = "InstrumentSession.findByStartTime", query = "SELECT i FROM InstrumentSession i WHERE i.startTime = :startTime"), @NamedQuery(name = "InstrumentSession.findByLastAccessTime", query = "SELECT i FROM InstrumentSession i WHERE i.lastAccessTime = :lastAccessTime"), @NamedQuery(name = "InstrumentSession.findByInstrumentStartingGroup", query = "SELECT i FROM InstrumentSession i WHERE i.instrumentStartingGroup = :instrumentStartingGroup"), @NamedQuery(name = "InstrumentSession.findByCurrentGroup", query = "SELECT i FROM InstrumentSession i WHERE i.currentGroup = :currentGroup"), @NamedQuery(name = "InstrumentSession.findByDisplayNum", query = "SELECT i FROM InstrumentSession i WHERE i.displayNum = :displayNum"), @NamedQuery(name = "InstrumentSession.findByLangCode", query = "SELECT i FROM InstrumentSession i WHERE i.langCode = :langCode"), @NamedQuery(name = "InstrumentSession.findByStatusMsg", query = "SELECT i FROM InstrumentSession i WHERE i.statusMsg = :statusMsg")})
public class InstrumentSession implements Serializable {
    @Id
    @Column(name = "InstrumentSession_ID", nullable = false)
    private Integer instrumentSessionID;
    @Column(name = "InstrumentVersionData_ID", nullable = false)
    private int instrumentVersionDataID;
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
    @Column(name = "LangCode", nullable = false)
    private String langCode;
    @Column(name = "StatusMsg")
    private String statusMsg;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "instrumentSession")
    private InstVer1 instVer1;
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
    private Collection<Datum> datumCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<ItemUsage> itemUsageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionID")
    private Collection<PageUsage> pageUsageCollection;

    public InstrumentSession() {
    }

    public InstrumentSession(Integer instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public InstrumentSession(Integer instrumentSessionID, int instrumentVersionDataID, Date startTime, Date lastAccessTime, int instrumentStartingGroup, int currentGroup, int displayNum, String langCode) {
        this.instrumentSessionID = instrumentSessionID;
        this.instrumentVersionDataID = instrumentVersionDataID;
        this.startTime = startTime;
        this.lastAccessTime = lastAccessTime;
        this.instrumentStartingGroup = instrumentStartingGroup;
        this.currentGroup = currentGroup;
        this.displayNum = displayNum;
        this.langCode = langCode;
    }

    public Integer getInstrumentSessionID() {
        return instrumentSessionID;
    }

    public void setInstrumentSessionID(Integer instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public int getInstrumentVersionDataID() {
        return instrumentVersionDataID;
    }

    public void setInstrumentVersionDataID(int instrumentVersionDataID) {
        this.instrumentVersionDataID = instrumentVersionDataID;
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

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public InstVer1 getInstVer1() {
        return instVer1;
    }

    public void setInstVer1(InstVer1 instVer1) {
        this.instVer1 = instVer1;
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

    public Collection<Datum> getDatumCollection() {
        return datumCollection;
    }

    public void setDatumCollection(Collection<Datum> datumCollection) {
        this.datumCollection = datumCollection;
    }

    public Collection<ItemUsage> getItemUsageCollection() {
        return itemUsageCollection;
    }

    public void setItemUsageCollection(Collection<ItemUsage> itemUsageCollection) {
        this.itemUsageCollection = itemUsageCollection;
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
        return "org.dialogix.domain.InstrumentSession[instrumentSessionID=" + instrumentSessionID + "]";
    }

}
