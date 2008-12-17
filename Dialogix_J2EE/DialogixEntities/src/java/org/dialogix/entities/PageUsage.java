/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "page_usage")
public class PageUsage implements Serializable {

    @TableGenerator(name = "PageUsage_gen", pkColumnValue = "page_usage", table = "sequence_data", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PageUsage_gen")
    @Column(name = "page_usage_id", nullable = false)
    private Long pageUsageId;
    @Column(name = "browser")
    private String browser;
    @Column(name = "display_num", nullable = false)
    private int displayNum;
    @Column(name = "from_group_num", nullable = false)
    private int fromGroupNum;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @Column(name = "load_duration")
    private Integer loadDuration;
    @Column(name = "network_duration")
    private Integer networkDuration;
    @Column(name = "page_duration")
    private Integer pageDuration;
    @Column(name = "page_visits")
    private Integer pageVisits;
    @Column(name = "server_duration")
    private Integer serverDuration;
    @Column(name = "status_msg")
    private String statusMsg;
    @Column(name = "storage_duration")
    private Integer storageDuration;
    @Column(name = "server_send_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverSendTime;
    @Column(name = "to_group_num", nullable = false)
    private int toGroupNum;
    @Column(name = "total_duration")
    private Integer totalDuration;
    @Column(name = "used_jvm_memory")
    private Long usedJvmMemory;
    @OneToMany(mappedBy = "pageUsageId")
    private Collection<PageUsageEvent> pageUsageEventCollection;
    @JoinColumn(name = "instrument_session_id", referencedColumnName = "instrument_session_id")
    @ManyToOne
    private InstrumentSession instrumentSessionId;
    @JoinColumn(name = "action_type_id", referencedColumnName = "action_type_id")
    @ManyToOne
    private ActionType actionTypeId;

    public PageUsage() {
    }

    public Long getPageUsageId() {
        return pageUsageId;
    }

    public void setPageUsageId(Long pageUsageId) {
        this.pageUsageId = pageUsageId;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public int getFromGroupNum() {
        return fromGroupNum;
    }

    public void setFromGroupNum(int fromGroupNum) {
        this.fromGroupNum = fromGroupNum;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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

    public Integer getPageDuration() {
        return pageDuration;
    }

    public void setPageDuration(Integer pageDuration) {
        this.pageDuration = pageDuration;
    }

    public Integer getPageVisits() {
        return pageVisits;
    }

    public void setPageVisits(Integer pageVisits) {
        this.pageVisits = pageVisits;
    }

    public Integer getServerDuration() {
        return serverDuration;
    }

    public void setServerDuration(Integer serverDuration) {
        this.serverDuration = serverDuration;
    }

    public Date getServerSendTime() {
        return serverSendTime;
    }

    public void setServerSendTime(Date serverSendTime) {
        this.serverSendTime = serverSendTime;
    }
    
    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Integer getStorageDuration() {
        return storageDuration;
    }

    public void setStorageDuration(Integer storageDuration) {
        this.storageDuration = storageDuration;
    }
    
    public int getToGroupNum() {
        return toGroupNum;
    }

    public void setToGroupNum(int toGroupNum) {
        this.toGroupNum = toGroupNum;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Long getUsedJvmMemory() {
        return usedJvmMemory;
    }

    public void setUsedJvmMemory(Long usedJvmMemory) {
        this.usedJvmMemory = usedJvmMemory;
    }

    public Collection<PageUsageEvent> getPageUsageEventCollection() {
        return pageUsageEventCollection;
    }

    public void setPageUsageEventCollection(
        Collection<PageUsageEvent> pageUsageEventCollection) {
        this.pageUsageEventCollection = pageUsageEventCollection;
    }

    public InstrumentSession getInstrumentSessionId() {
        return instrumentSessionId;
    }

    public void setInstrumentSessionId(InstrumentSession instrumentSessionId) {
        this.instrumentSessionId = instrumentSessionId;
    }

    public ActionType getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(ActionType actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageUsageId != null ? pageUsageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PageUsage)) {
            return false;
        }
        PageUsage other = (PageUsage) object;
        if ((this.pageUsageId == null && other.pageUsageId != null) || (this.pageUsageId != null && !this.pageUsageId.equals(other.pageUsageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.PageUsage[pageUsageId=" + pageUsageId + "]";
    }
}
