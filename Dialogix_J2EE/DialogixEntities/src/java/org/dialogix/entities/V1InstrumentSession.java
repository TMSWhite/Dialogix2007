/*
 * V1InstrumentSession.java
 * 
 * Created on Nov 19, 2007, 9:05:40 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "v1_instrument_session")
public class V1InstrumentSession implements Serializable {
    @TableGenerator(name="V1InstrumentSession_Gen", pkColumnValue="V1InstrumentSession", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="V1InstrumentSession_Gen")
    @Column(name = "v1_instrument_session_id", nullable = false)
    private BigInteger v1InstrumentSessionID;
    @Column(name = "InstrumentVersionName", nullable = false)
    private String instrumentVersionName;
    @Column(name = "StartTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "LastAccessTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;
    @Column(name = "InstrumentStartingGroup")
    private Integer instrumentStartingGroup;
    @Column(name = "CurrentGroup")
    private Integer currentGroup;
    @Column(name = "DisplayNum")
    private Integer displayNum;
    @Column(name = "LanguageCode", length=2)
    private String languageCode;
    @Column(name = "ActionType")
    private String actionType;
    @Column(name = "StatusMsg")
    private String statusMsg;
    @Column(name = "MaxGroup")
    private Integer maxGroup;
    @Column(name = "MaxVarNum")
    private Integer maxVarNum;
    @Lob
    @Column(name = "InstrumentVersionFileName")
    private String instrumentVersionFileName;
    @Lob
    @Column(name = "InstrumentSessionFileName")
    private String instrumentSessionFileName;
    @Column(name = "NumVars")
    private Integer numVars;
    @Column(name = "VarListMD5")
    private String varListMD5;
    @Column(name = "NumGroups")
    private Integer numGroups;
    @Column(name = "Finished")
    private Integer finished;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "v1InstrumentSessionID")
    private Collection<V1ItemUsage> v1ItemUsageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "v1InstrumentSessionID")
    private Collection<V1DataElement> v1DataElementCollection;
    @Column(name = "IPAddress", nullable = true)
    private String ipAddress;    
    @Column(name = "Browser", nullable = true)
    private String browser;     
    
    public V1InstrumentSession() {
    }

    public V1InstrumentSession(BigInteger v1InstrumentSessionID) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
    }

    public V1InstrumentSession(BigInteger v1InstrumentSessionID, String instrumentVersionName, Date startTime, Date lastAccessTime) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
        this.instrumentVersionName = instrumentVersionName;
        this.startTime = startTime;
        this.lastAccessTime = lastAccessTime;
    }

    public BigInteger getV1InstrumentSessionID() {
        return v1InstrumentSessionID;
    }

    public void setV1InstrumentSessionID(BigInteger v1InstrumentSessionID) {
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

    public Integer getInstrumentStartingGroup() {
        return instrumentStartingGroup;
    }

    public void setInstrumentStartingGroup(Integer instrumentStartingGroup) {
        this.instrumentStartingGroup = instrumentStartingGroup;
    }

    public Integer getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Integer currentGroup) {
        this.currentGroup = currentGroup;
    }

    public Integer getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(Integer displayNum) {
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

    public Integer getNumVars() {
        return numVars;
    }

    public void setNumVars(Integer numVars) {
        this.numVars = numVars;
    }

    public String getVarListMD5() {
        return varListMD5;
    }

    public void setVarListMD5(String varListMD5) {
        this.varListMD5 = varListMD5;
    }

    public Integer getNumGroups() {
        return numGroups;
    }

    public void setNumGroups(Integer numGroups) {
        this.numGroups = numGroups;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Collection<V1ItemUsage> getV1ItemUsageCollection() {
        return v1ItemUsageCollection;
    }

    public void setV1ItemUsageCollection(Collection<V1ItemUsage> v1ItemUsageCollection) {
        this.v1ItemUsageCollection = v1ItemUsageCollection;
    }

    public Collection<V1DataElement> getV1DataElementCollection() {
        return v1DataElementCollection;
    }

    public void setV1DataElementCollection(Collection<V1DataElement> v1DataElementCollection) {
        this.v1DataElementCollection = v1DataElementCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (v1InstrumentSessionID != null ? v1InstrumentSessionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
