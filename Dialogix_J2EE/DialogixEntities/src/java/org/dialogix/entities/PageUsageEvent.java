/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "page_usage_event")
public class PageUsageEvent implements Serializable {

    @TableGenerator(name = "PageUsageEvent_gen", pkColumnValue = "page_usage_event", table = "sequence_data", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PageUsageEvent_gen")
    @Column(name = "page_usage_event_id", nullable = false)
    private Long pageUsageEventId;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "event_type", nullable = false)
    private String eventType;
    @Column(name = "gui_action_type", nullable = false)
    private String guiActionType;
    @Column(name = "page_usage_event_sequence", nullable = false)
    private int pageUsageEventSequence;
    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "value1", nullable = false)
    private String value1;
    @Column(name = "value2", nullable = false)
    private String value2;
    @Column(name = "var_name", nullable = false)
    private String varName;
    @JoinColumn(name = "page_usage_id", referencedColumnName = "page_usage_id")
    @ManyToOne
    private PageUsage pageUsageId;

    public PageUsageEvent() {
    }

    public PageUsageEvent(Long pageUsageEventId) {
        this.pageUsageEventId = pageUsageEventId;
    }

    public PageUsageEvent(Long pageUsageEventId,
                          int duration,
                          String eventType,
                          String guiActionType,
                          int pageUsageEventSequence,
                          String value1,
                          String value2,
                          String varName) {
        this.pageUsageEventId = pageUsageEventId;
        this.duration = duration;
        this.eventType = eventType;
        this.guiActionType = guiActionType;
        this.pageUsageEventSequence = pageUsageEventSequence;
        this.value1 = value1;
        this.value2 = value2;
        this.varName = varName;
    }

    public Long getPageUsageEventId() {
        return pageUsageEventId;
    }

    public void setPageUsageEventId(Long pageUsageEventId) {
        this.pageUsageEventId = pageUsageEventId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getGuiActionType() {
        return guiActionType;
    }

    public void setGuiActionType(String guiActionType) {
        this.guiActionType = guiActionType;
    }

    public int getPageUsageEventSequence() {
        return pageUsageEventSequence;
    }

    public void setPageUsageEventSequence(int pageUsageEventSequence) {
        this.pageUsageEventSequence = pageUsageEventSequence;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
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

    public PageUsage getPageUsageId() {
        return pageUsageId;
    }

    public void setPageUsageId(PageUsage pageUsageId) {
        this.pageUsageId = pageUsageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageUsageEventId != null ? pageUsageEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PageUsageEvent)) {
            return false;
        }
        PageUsageEvent other = (PageUsageEvent) object;
        if ((this.pageUsageEventId == null && other.pageUsageEventId != null) || (this.pageUsageEventId != null && !this.pageUsageEventId.equals(other.pageUsageEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.PageUsageEvent[pageUsageEventId=" + pageUsageEventId + "]";
    }
}
