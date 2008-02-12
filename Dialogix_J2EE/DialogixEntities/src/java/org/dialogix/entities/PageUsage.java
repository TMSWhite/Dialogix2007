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

import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "page_usages")
public class PageUsage implements Serializable {
    @TableGenerator(name="page_usage_gen", pkColumnValue="page_usage", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="page_usage_gen")
    @Column(name = "id", nullable = false)
    private Long pageUsageID;
    @Column(name = "page_usage_sequence", nullable = false)
    private int pageUsageSequence;
    @Column(name = "language_code", nullable = false, length=2)
    private String languageCode;
    @Column(name = "time_stamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "from_group_num", nullable = false)
    private int fromGroupNum;
    @Column(name = "to_group_num", nullable = false)
    private int toGroupNum;
    @Column(name = "display_num", nullable = false)
    private int displayNum;
    @Column(name = "status_msg")
    private String statusMsg;
    @Column(name = "total_duration")
    private Integer totalDuration;
    @Column(name = "page_duration")
    private Integer pageDuration;
    @Column(name = "server_duration")
    private Integer serverDuration;
    @Column(name = "load_duration")
    private Integer loadDuration;
    @Column(name = "network_duration")
    private Integer networkDuration;
    @Column(name = "page_visits")
    private Integer pageVisits;
    @Column(name = "used_jvm_memory")
    private long usedJvmMemory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pageUsageID")
    private Collection<PageUsageEvent> pageUsageEventCollection;
    @JoinColumn(name = "instrument_session_id", referencedColumnName="id")
    @ManyToOne
    private InstrumentSession instrumentSessionID;
    @JoinColumn(name = "action_type_id", referencedColumnName="id")
    @ManyToOne
    private ActionType actionTypeID;
    @Column(name = "ip_address", nullable = true)
    private String ipAddress;    
    @Column(name = "browser", nullable = true)
    private String browser;         

    public PageUsage() {
    }

    public PageUsage(Long pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    public PageUsage(Long pageUsageID, int pageUsageSequence, String languageCode, Date timeStamp, int fromGroupNum, int toGroupNum, int displayNum) {
        this.pageUsageID = pageUsageID;
        this.pageUsageSequence = pageUsageSequence;
        this.languageCode = languageCode;
        this.timeStamp = timeStamp;
        this.fromGroupNum = fromGroupNum;
        this.toGroupNum = toGroupNum;
        this.displayNum = displayNum;
    }

    public Long getPageUsageID() {
        return pageUsageID;
    }

    public void setPageUsageID(Long pageUsageID) {
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

    public Integer getPageVisits() {
        return pageVisits;
    }

    public void setPageVisits(Integer pageVisits) {
        this.pageVisits = pageVisits;
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

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getUsedJvmMemory() {
        return usedJvmMemory;
    }

    public void setUsedJvmMemory(long usedJvmMemory) {
        this.usedJvmMemory = usedJvmMemory;
    }
}
