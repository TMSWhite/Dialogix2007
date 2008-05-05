/*
 * PageUsageEvent.java
 * 
 * Created on Nov 2, 2007, 12:12:07 PM
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "page_usage_events")
public class PageUsageEvent implements Serializable {
    @TableGenerator(name="page_usage_event_gen", pkColumnValue="page_usage_event", table="v2_sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=10000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="page_usage_event_gen")
    @Column(name = "id", nullable = false)
    private Long pageUsageEventID;
    @Column(name = "page_usage_event_sequence", nullable = false)
    private int pageUsageEventSequence;
    @Column(name = "gui_action_type", nullable = false)
    private String guiActionType;
    @Column(name = "event_type", nullable = false)
    private String eventType;
    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "value1", nullable = false)
    private String value1;
    @Column(name = "value2", nullable = false)
    private String value2;
    @Column(name = "var_name", nullable = false)
    private String varName;    
    @JoinColumn(name = "page_usage_id", referencedColumnName="id")
    @ManyToOne
    private PageUsage pageUsageID;

    public PageUsageEvent() {
    }

    public PageUsageEvent(Long pageUsageEventID) {
        this.pageUsageEventID = pageUsageEventID;
    }

    public PageUsageEvent(Long pageUsageEventID, int pageUsageEventSequence, String guiActionType, String eventType, int duration, String value1, String value2) {
        this.pageUsageEventID = pageUsageEventID;
        this.pageUsageEventSequence = pageUsageEventSequence;
        this.guiActionType = guiActionType;
        this.eventType = eventType;
        this.duration = duration;
        this.value1 = value1;
        this.value2 = value2;
    }

    public Long getPageUsageEventID() {
        return pageUsageEventID;
    }

    public void setPageUsageEventID(Long pageUsageEventID) {
        this.pageUsageEventID = pageUsageEventID;
    }

    public int getPageUsageEventSequence() {
        return pageUsageEventSequence;
    }

    public void setPageUsageEventSequence(int pageUsageEventSequence) {
        this.pageUsageEventSequence = pageUsageEventSequence;
    }

    public String getGuiActionType() {
        return guiActionType;
    }

    public void setGuiActionType(String guiActionType) {
        this.guiActionType = guiActionType;
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

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
    
    public PageUsage getPageUsageID() {
        return pageUsageID;
    }

    public void setPageUsageID(PageUsage pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageUsageEventID != null ? pageUsageEventID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
