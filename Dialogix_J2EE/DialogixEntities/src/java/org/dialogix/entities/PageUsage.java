/*
 * PageUsage.java
 * 
 * Created on Nov 2, 2007, 12:12:12 PM
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
import javax.persistence.JoinColumn;
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
@Table(name = "page_usage")
@NamedQueries({@NamedQuery(name = "PageUsage.findByPageUsageID", query = "SELECT p FROM PageUsage p WHERE p.pageUsageID = :pageUsageID"), @NamedQuery(name = "PageUsage.findByPageUsageSequence", query = "SELECT p FROM PageUsage p WHERE p.pageUsageSequence = :pageUsageSequence"), @NamedQuery(name = "PageUsage.findByLanguageCode", query = "SELECT p FROM PageUsage p WHERE p.languageCode = :languageCode"), @NamedQuery(name = "PageUsage.findByTimeStamp", query = "SELECT p FROM PageUsage p WHERE p.timeStamp = :timeStamp"), @NamedQuery(name = "PageUsage.findByFromGroupNum", query = "SELECT p FROM PageUsage p WHERE p.fromGroupNum = :fromGroupNum"), @NamedQuery(name = "PageUsage.findByToGroupNum", query = "SELECT p FROM PageUsage p WHERE p.toGroupNum = :toGroupNum"), @NamedQuery(name = "PageUsage.findByDisplayNum", query = "SELECT p FROM PageUsage p WHERE p.displayNum = :displayNum"), @NamedQuery(name = "PageUsage.findByStatusMsg", query = "SELECT p FROM PageUsage p WHERE p.statusMsg = :statusMsg"), @NamedQuery(name = "PageUsage.findByTotalDuration", query = "SELECT p FROM PageUsage p WHERE p.totalDuration = :totalDuration"), @NamedQuery(name = "PageUsage.findByPageDuration", query = "SELECT p FROM PageUsage p WHERE p.pageDuration = :pageDuration"), @NamedQuery(name = "PageUsage.findByServerDuration", query = "SELECT p FROM PageUsage p WHERE p.serverDuration = :serverDuration"), @NamedQuery(name = "PageUsage.findByLoadDuration", query = "SELECT p FROM PageUsage p WHERE p.loadDuration = :loadDuration"), @NamedQuery(name = "PageUsage.findByNetworkDuration", query = "SELECT p FROM PageUsage p WHERE p.networkDuration = :networkDuration"), @NamedQuery(name = "PageUsage.findByPageVacillation", query = "SELECT p FROM PageUsage p WHERE p.pageVacillation = :pageVacillation")})
public class PageUsage implements Serializable {
    @TableGenerator(name="PageUsage_Generator", pkColumnValue="PageUsage", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="PageUsage_Generator")
    @Column(name = "PageUsage_ID", nullable = false)
    private BigInteger pageUsageID;
    @Column(name = "PageUsageSequence", nullable = false)
    private int pageUsageSequence;
    @Column(name = "LanguageCode", nullable = false, length=2)
    private String languageCode;
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
    private Collection<PageUsageEvent> pageUsageEventCollection;
    @JoinColumn(name = "InstrumentSession_ID", referencedColumnName = "InstrumentSession_ID")
    @ManyToOne
    private InstrumentSession instrumentSessionID;
    @JoinColumn(name = "ActionType_ID", referencedColumnName = "ActionType_ID")
    @ManyToOne
    private ActionType actionTypeID;

    public PageUsage() {
    }

    public PageUsage(BigInteger pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    public PageUsage(BigInteger pageUsageID, int pageUsageSequence, String languageCode, Date timeStamp, int fromGroupNum, int toGroupNum, int displayNum) {
        this.pageUsageID = pageUsageID;
        this.pageUsageSequence = pageUsageSequence;
        this.languageCode = languageCode;
        this.timeStamp = timeStamp;
        this.fromGroupNum = fromGroupNum;
        this.toGroupNum = toGroupNum;
        this.displayNum = displayNum;
    }

    public BigInteger getPageUsageID() {
        return pageUsageID;
    }

    public void setPageUsageID(BigInteger pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    public int getPageUsageSequence() {
        return pageUsageSequence;
    }

    public void setPageUsageSequence(int pageUsageSequence) {
        this.pageUsageSequence = pageUsageSequence;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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
        return "org.dialogix.entities.PageUsage[pageUsageID=" + pageUsageID + "]";
    }

}
