/*
 * PageUsage.java
 * 
 * Created on Oct 22, 2007, 4:09:08 PM
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "pageusage")
@NamedQueries({@NamedQuery(name = "PageUsage.findByPageUsageID", query = "SELECT p FROM PageUsage p WHERE p.pageUsageID = :pageUsageID"), @NamedQuery(name = "PageUsage.findByLanguageID", query = "SELECT p FROM PageUsage p WHERE p.languageID = :languageID"), @NamedQuery(name = "PageUsage.findByLangCode", query = "SELECT p FROM PageUsage p WHERE p.langCode = :langCode"), @NamedQuery(name = "PageUsage.findByTimeStamp", query = "SELECT p FROM PageUsage p WHERE p.timeStamp = :timeStamp"), @NamedQuery(name = "PageUsage.findByFromGroupNum", query = "SELECT p FROM PageUsage p WHERE p.fromGroupNum = :fromGroupNum"), @NamedQuery(name = "PageUsage.findByToGroupNum", query = "SELECT p FROM PageUsage p WHERE p.toGroupNum = :toGroupNum"), @NamedQuery(name = "PageUsage.findByDisplayNum", query = "SELECT p FROM PageUsage p WHERE p.displayNum = :displayNum"), @NamedQuery(name = "PageUsage.findByStatusMsg", query = "SELECT p FROM PageUsage p WHERE p.statusMsg = :statusMsg"), @NamedQuery(name = "PageUsage.findByTotalDuration", query = "SELECT p FROM PageUsage p WHERE p.totalDuration = :totalDuration"), @NamedQuery(name = "PageUsage.findByPageDuration", query = "SELECT p FROM PageUsage p WHERE p.pageDuration = :pageDuration"), @NamedQuery(name = "PageUsage.findByServerDuration", query = "SELECT p FROM PageUsage p WHERE p.serverDuration = :serverDuration"), @NamedQuery(name = "PageUsage.findByLoadDuration", query = "SELECT p FROM PageUsage p WHERE p.loadDuration = :loadDuration"), @NamedQuery(name = "PageUsage.findByNetworkDuration", query = "SELECT p FROM PageUsage p WHERE p.networkDuration = :networkDuration"), @NamedQuery(name = "PageUsage.findByPageVacillation", query = "SELECT p FROM PageUsage p WHERE p.pageVacillation = :pageVacillation")})
public class PageUsage implements Serializable {
    @Id
    @Column(name = "PageUsage_ID", nullable = false)
    private Integer pageUsageID;
    @Column(name = "Language_ID", nullable = false)
    private int languageID;
    @Column(name = "LangCode", nullable = false)
    private String langCode;
    @Column(name = "Time_Stamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "FromGroupNum", nullable = false)
    private int fromGroupNum;
    @Column(name = "ToGroupNum", nullable = false)
    private int toGroupNum;
    @Column(name = "DisplayNum", nullable = false)
    private int displayNum;
    @Column(name = "StatusMsg")
    private String statusMsg;
    @Column(name = "totalDuration")
    private Integer totalDuration;
    @Column(name = "pageDuration")
    private Integer pageDuration;
    @Column(name = "serverDuration")
    private Integer serverDuration;
    @Column(name = "loadDuration")
    private Integer loadDuration;
    @Column(name = "networkDuration")
    private Integer networkDuration;
    @Column(name = "pageVacillation")
    private Integer pageVacillation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pageUsageID")
    private Collection<Datum> datumCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pageUsageID")
    private Collection<PageUsageEvent> pageUsageEventCollection;
    @JoinColumn(name = "InstrumentSession_ID", referencedColumnName = "InstrumentSession_ID")
    @ManyToOne
    private InstrumentSession instrumentSessionID;
    @JoinColumn(name = "ActionType_ID", referencedColumnName = "ActionType_ID")
    @ManyToOne
    private ActionType actionTypeID;

    public PageUsage() {
    }

    public PageUsage(Integer pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    public PageUsage(Integer pageUsageID, int languageID, String langCode, Date timeStamp, int fromGroupNum, int toGroupNum, int displayNum) {
        this.pageUsageID = pageUsageID;
        this.languageID = languageID;
        this.langCode = langCode;
        this.timeStamp = timeStamp;
        this.fromGroupNum = fromGroupNum;
        this.toGroupNum = toGroupNum;
        this.displayNum = displayNum;
    }

    public Integer getPageUsageID() {
        return pageUsageID;
    }

    public void setPageUsageID(Integer pageUsageID) {
        this.pageUsageID = pageUsageID;
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getFromGroupNum() {
        return fromGroupNum;
    }

    public void setFromGroupNum(int fromGroupNum) {
        this.fromGroupNum = fromGroupNum;
    }

    public int getToGroupNum() {
        return toGroupNum;
    }

    public void setToGroupNum(int toGroupNum) {
        this.toGroupNum = toGroupNum;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getPageDuration() {
        return pageDuration;
    }

    public void setPageDuration(Integer pageDuration) {
        this.pageDuration = pageDuration;
    }

    public Integer getServerDuration() {
        return serverDuration;
    }

    public void setServerDuration(Integer serverDuration) {
        this.serverDuration = serverDuration;
    }

    public Integer getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(Integer loadDuration) {
        this.loadDuration = loadDuration;
    }

    public Integer getNetworkDuration() {
        return networkDuration;
    }

    public void setNetworkDuration(Integer networkDuration) {
        this.networkDuration = networkDuration;
    }

    public Integer getPageVacillation() {
        return pageVacillation;
    }

    public void setPageVacillation(Integer pageVacillation) {
        this.pageVacillation = pageVacillation;
    }

    public Collection<Datum> getDatumCollection() {
        return datumCollection;
    }

    public void setDatumCollection(Collection<Datum> datumCollection) {
        this.datumCollection = datumCollection;
    }

    public Collection<PageUsageEvent> getPageUsageEventCollection() {
        return pageUsageEventCollection;
    }

    public void setPageUsageEventCollection(Collection<PageUsageEvent> pageUsageEventCollection) {
        this.pageUsageEventCollection = pageUsageEventCollection;
    }

    public InstrumentSession getInstrumentSessionID() {
        return instrumentSessionID;
    }

    public void setInstrumentSessionID(InstrumentSession instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public ActionType getActionTypeID() {
        return actionTypeID;
    }

    public void setActionTypeID(ActionType actionTypeID) {
        this.actionTypeID = actionTypeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageUsageID != null ? pageUsageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PageUsage)) {
            return false;
        }
        PageUsage other = (PageUsage) object;
        if ((this.pageUsageID == null && other.pageUsageID != null) || (this.pageUsageID != null && !this.pageUsageID.equals(other.pageUsageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.PageUsage[pageUsageID=" + pageUsageID + "]";
    }

}
