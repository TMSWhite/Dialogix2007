/*
 * PageUsageEvent.java
 * 
 * Created on Oct 26, 2007, 5:17:05 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "page_usage_event")
@NamedQueries({@NamedQuery(name = "PageUsageEvent.findByPageUsageEventID", query = "SELECT p FROM PageUsageEvent p WHERE p.pageUsageEventID = :pageUsageEventID"), @NamedQuery(name = "PageUsageEvent.findByActionType", query = "SELECT p FROM PageUsageEvent p WHERE p.actionType = :actionType"), @NamedQuery(name = "PageUsageEvent.findByEventType", query = "SELECT p FROM PageUsageEvent p WHERE p.eventType = :eventType"), @NamedQuery(name = "PageUsageEvent.findByTimeStamp", query = "SELECT p FROM PageUsageEvent p WHERE p.timeStamp = :timeStamp"), @NamedQuery(name = "PageUsageEvent.findByDuration", query = "SELECT p FROM PageUsageEvent p WHERE p.duration = :duration"), @NamedQuery(name = "PageUsageEvent.findByValue1", query = "SELECT p FROM PageUsageEvent p WHERE p.value1 = :value1"), @NamedQuery(name = "PageUsageEvent.findByValue2", query = "SELECT p FROM PageUsageEvent p WHERE p.value2 = :value2")})
public class PageUsageEvent implements Serializable {
    @TableGenerator(name="PageUsageEvent_Generator", pkColumnValue="PageUsageEvent", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="PageUsageEvent_Generator")
    @Column(name = "PageUsageEvent_ID", nullable = false)
    private Integer pageUsageEventID;
    @Column(name = "actionType", nullable = false)
    private String actionType;
    @Column(name = "eventType", nullable = false)
    private String eventType;
    @Column(name = "Time_Stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "value1", nullable = false)
    private String value1;
    @Column(name = "value2", nullable = false)
    private String value2;
    @JoinColumn(name = "PageUsage_ID", referencedColumnName = "PageUsage_ID")
    @ManyToOne
    private PageUsage pageUsageID;
    @JoinColumn(name = "VarName_ID", referencedColumnName = "VarName_ID")
    @ManyToOne
    private VarName varNameID;

    public PageUsageEvent() {
    }

    public PageUsageEvent(Integer pageUsageEventID) {
        this.pageUsageEventID = pageUsageEventID;
    }

    public PageUsageEvent(Integer pageUsageEventID, String actionType, String eventType, int duration, String value1, String value2) {
        this.pageUsageEventID = pageUsageEventID;
        this.actionType = actionType;
        this.eventType = eventType;
        this.duration = duration;
        this.value1 = value1;
        this.value2 = value2;
    }

    public Integer getPageUsageEventID() {
        return pageUsageEventID;
    }

    public void setPageUsageEventID(Integer pageUsageEventID) {
        this.pageUsageEventID = pageUsageEventID;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public PageUsage getPageUsageID() {
        return pageUsageID;
    }

    public void setPageUsageID(PageUsage pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    public VarName getVarNameID() {
        return varNameID;
    }

    public void setVarNameID(VarName varNameID) {
        this.varNameID = varNameID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageUsageEventID != null ? pageUsageEventID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PageUsageEvent)) {
            return false;
        }
        PageUsageEvent other = (PageUsageEvent) object;
        if ((this.pageUsageEventID == null && other.pageUsageEventID != null) || (this.pageUsageEventID != null && !this.pageUsageEventID.equals(other.pageUsageEventID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.PageUsageEvent[pageUsageEventID=" + pageUsageEventID + "]";
    }

}
