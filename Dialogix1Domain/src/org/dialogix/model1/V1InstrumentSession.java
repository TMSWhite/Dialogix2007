/*
 * V1InstrumentSession.java
 * 
 * Created on Nov 17, 2007, 12:42:21 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.model1;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "v1_instrument_session")
@NamedQueries({})
public class V1InstrumentSession implements Serializable {
    @TableGenerator(name="V1_Instrument_Session_Generator", pkColumnValue="V1_Instrument_Session", table="V1_SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="V1_Instrument_Session_Generator")
    @Column(name = "V1InstrumentSession_ID", nullable = false)
    private Integer v1InstrumentSessionID;
    @Column(name = "InstrumentVersionName", nullable = false)
    private String instrumentVersionName;
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
    @Column(name = "ActionType")
    private String actionType;
    @Column(name = "StatusMsg")
    private String statusMsg;
    @Lob
    @Column(name = "InstrumentVersionFileName")
    private String instrumentVersionFileName;
    @Lob
    @Column(name = "InstrumentSessionFileName")
    private String instrumentSessionFileName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "v1InstrumentSessionID")
    private Collection<V1ItemUsage> v1ItemUsageCollection;

    public V1InstrumentSession() {
    }

    public V1InstrumentSession(Integer v1InstrumentSessionID) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
    }

    public V1InstrumentSession(Integer v1InstrumentSessionID, String instrumentVersionName, Date startTime, Date lastAccessTime, int instrumentStartingGroup, int currentGroup, int displayNum, String languageCode) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
        this.instrumentVersionName = instrumentVersionName;
        this.startTime = startTime;
        this.lastAccessTime = lastAccessTime;
        this.instrumentStartingGroup = instrumentStartingGroup;
        this.currentGroup = currentGroup;
        this.displayNum = displayNum;
        this.languageCode = languageCode;
    }

    public Integer getV1InstrumentSessionID() {
        return v1InstrumentSessionID;
    }

    public void setV1InstrumentSessionID(Integer v1InstrumentSessionID) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
    }

    public String getInstrumentVersionName() {
        return instrumentVersionName;
    }

    public void setInstrumentVersionName(String instrumentVersionName) {
        this.instrumentVersionName = instrumentVersionName;
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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getInstrumentVersionFileName() {
        return instrumentVersionFileName;
    }

    public void setInstrumentVersionFileName(String instrumentVersionFileName) {
        this.instrumentVersionFileName = instrumentVersionFileName;
    }

    public String getInstrumentSessionFileName() {
        return instrumentSessionFileName;
    }

    public void setInstrumentSessionFileName(String instrumentSessionFileName) {
        this.instrumentSessionFileName = instrumentSessionFileName;
    }

    public Collection<V1ItemUsage> getV1ItemUsageCollection() {
        return v1ItemUsageCollection;
    }

    public void setV1ItemUsageCollection(Collection<V1ItemUsage> v1ItemUsageCollection) {
        this.v1ItemUsageCollection = v1ItemUsageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (v1InstrumentSessionID != null ? v1InstrumentSessionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof V1InstrumentSession)) {
            return false;
        }
        V1InstrumentSession other = (V1InstrumentSession) object;
        if ((this.v1InstrumentSessionID == null && other.v1InstrumentSessionID != null) || (this.v1InstrumentSessionID != null && !this.v1InstrumentSessionID.equals(other.v1InstrumentSessionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.model1.V1InstrumentSession[v1InstrumentSessionID=" + v1InstrumentSessionID + "]";
    }

}
