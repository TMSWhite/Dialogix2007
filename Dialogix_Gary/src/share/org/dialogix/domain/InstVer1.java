/*
 * InstVer1.java
 * 
 * Created on Oct 22, 2007, 4:08:58 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instver_1")
@NamedQueries({@NamedQuery(name = "InstVer1.findByInstrumentSessionID", query = "SELECT i FROM InstVer1 i WHERE i.instrumentSessionID = :instrumentSessionID"), @NamedQuery(name = "InstVer1.findByStartTime", query = "SELECT i FROM InstVer1 i WHERE i.startTime = :startTime"), @NamedQuery(name = "InstVer1.findByLastAccessTime", query = "SELECT i FROM InstVer1 i WHERE i.lastAccessTime = :lastAccessTime"), @NamedQuery(name = "InstVer1.findByDisplayNum", query = "SELECT i FROM InstVer1 i WHERE i.displayNum = :displayNum"), @NamedQuery(name = "InstVer1.findByLanguageID", query = "SELECT i FROM InstVer1 i WHERE i.languageID = :languageID"), @NamedQuery(name = "InstVer1.findByLangCode", query = "SELECT i FROM InstVer1 i WHERE i.langCode = :langCode"), @NamedQuery(name = "InstVer1.findByInstrumentStartingGroup", query = "SELECT i FROM InstVer1 i WHERE i.instrumentStartingGroup = :instrumentStartingGroup"), @NamedQuery(name = "InstVer1.findByCurrentGroup", query = "SELECT i FROM InstVer1 i WHERE i.currentGroup = :currentGroup"), @NamedQuery(name = "InstVer1.findByLastAction", query = "SELECT i FROM InstVer1 i WHERE i.lastAction = :lastAction"), @NamedQuery(name = "InstVer1.findByStatusMsg", query = "SELECT i FROM InstVer1 i WHERE i.statusMsg = :statusMsg")})
public class InstVer1 implements Serializable {
    @Id
    @Column(name = "InstrumentSession_ID", nullable = false)
    private Integer instrumentSessionID;
    @Column(name = "StartTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "LastAccessTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;
    @Column(name = "DisplayNum", nullable = false)
    private int displayNum;
    @Column(name = "Language_ID", nullable = false)
    private int languageID;
    @Column(name = "LangCode", nullable = false)
    private String langCode;
    @Column(name = "InstrumentStartingGroup", nullable = false)
    private int instrumentStartingGroup;
    @Column(name = "CurrentGroup", nullable = false)
    private int currentGroup;
    @Column(name = "LastAction")
    private String lastAction;
    @Column(name = "statusMsg", nullable = false)
    private String statusMsg;
    @Lob
    @Column(name = "hasChild")
    private String hasChild;
    @Lob
    @Column(name = "q2")
    private String q2;
    @Lob
    @Column(name = "male")
    private String male;
    @Lob
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "demo5")
    private String demo5;
    @JoinColumn(name = "InstrumentSession_ID", referencedColumnName = "InstrumentSession_ID", insertable = false, updatable = false)
    @OneToOne
    private InstrumentSession instrumentSession;

    public InstVer1() {
    }

    public InstVer1(Integer instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public InstVer1(Integer instrumentSessionID, Date startTime, Date lastAccessTime, int displayNum, int languageID, String langCode, int instrumentStartingGroup, int currentGroup, String statusMsg) {
        this.instrumentSessionID = instrumentSessionID;
        this.startTime = startTime;
        this.lastAccessTime = lastAccessTime;
        this.displayNum = displayNum;
        this.languageID = languageID;
        this.langCode = langCode;
        this.instrumentStartingGroup = instrumentStartingGroup;
        this.currentGroup = currentGroup;
        this.statusMsg = statusMsg;
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

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
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

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDemo5() {
        return demo5;
    }

    public void setDemo5(String demo5) {
        this.demo5 = demo5;
    }

    public InstrumentSession getInstrumentSession() {
        return instrumentSession;
    }

    public void setInstrumentSession(InstrumentSession instrumentSession) {
        this.instrumentSession = instrumentSession;
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
        if (!(object instanceof InstVer1)) {
            return false;
        }
        InstVer1 other = (InstVer1) object;
        if ((this.instrumentSessionID == null && other.instrumentSessionID != null) || (this.instrumentSessionID != null && !this.instrumentSessionID.equals(other.instrumentSessionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.InstVer1[instrumentSessionID=" + instrumentSessionID + "]";
    }

}
