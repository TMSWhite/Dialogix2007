/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "v1_page_usages")
public class V1PageUsage implements Serializable {
    @TableGenerator(name="v1_page_usage_gen", pkColumnValue="v1_page_usage", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="v1_page_usage_gen")      
    @Column(name = "v1_page_usage_id", nullable = false)    
    private Long v1PageUsageID;
    @Column(name = "display_num", nullable = false)
    private int displayNum;    
    @Column(name = "total_duration")
    private Long totalDuration;
    @Column(name = "page_duration")
    private Long pageDuration;
    @Column(name = "server_duration")
    private Long serverDuration;
    @Column(name = "load_duration")
    private Long loadDuration;
    @Column(name = "network_duration")
    private Long networkDuration;   
    @Column(name = "language_code", length=2)
    private String languageCode;
    @Column(name = "action_type")
    private String actionType;    
    @JoinColumn(name = "v1_instrument_session_id", referencedColumnName = "v1_instrument_session_id")
    @ManyToOne
    private V1InstrumentSession v1InstrumentSessionID;    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (v1PageUsageID != null ? v1PageUsageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the v1PageUsageID fields are not set
        if (!(object instanceof V1PageUsage)) {
            return false;
        }
        V1PageUsage other = (V1PageUsage) object;
        if ((this.v1PageUsageID == null && other.v1PageUsageID != null) || (this.v1PageUsageID != null && !this.v1PageUsageID.equals(other.v1PageUsageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.V1PageUsage[v1PageUsageID=" + v1PageUsageID + "]";
    }

    public Long getV1PageUsageID() {
        return v1PageUsageID;
    }

    public void setV1PageUsageID(Long v1PageUsageID) {
        this.v1PageUsageID = v1PageUsageID;
    }

    public Long getPageDuration() {
        return pageDuration;
    }

    public void setPageDuration(Long pageDuration) {
        this.pageDuration = pageDuration;
    }

    public Long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Long getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(Long loadDuration) {
        this.loadDuration = loadDuration;
    }

    public Long getNetworkDuration() {
        return networkDuration;
    }

    public void setNetworkDuration(Long networkDuration) {
        this.networkDuration = networkDuration;
    }

    public Long getServerDuration() {
        return serverDuration;
    }

    public void setServerDuration(Long serverDuration) {
        this.serverDuration = serverDuration;
    }
    

    public V1InstrumentSession getV1InstrumentSessionID() {
        return v1InstrumentSessionID;
    }

    public void setV1InstrumentSessionID(V1InstrumentSession v1InstrumentSessionID) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

}
