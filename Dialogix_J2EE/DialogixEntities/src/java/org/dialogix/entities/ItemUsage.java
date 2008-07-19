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
import javax.persistence.Lob;
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
@Table(name = "item_usage")
public class ItemUsage implements Serializable {

    @TableGenerator(name = "ItemUsage_gen", pkColumnValue = "item_usage", table = "sequence_data", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ItemUsage_gen")
    @Column(name = "item_usage_id", nullable = false)
    private Long itemUsageId;
    @Lob
    @Column(name = "answer_code")
    private String answerCode;
    @Lob
    @Column(name = "answer_string")
    private String answerString;
    @Lob
    @Column(name = "comments")
    private String comments;
    @Column(name = "display_num", nullable = false)
    private int displayNum;
    @Column(name = "item_usage_sequence", nullable = false)
    private int itemUsageSequence;
    @Column(name = "item_visit")
    private Integer itemVisit;
    @Column(name = "language_code")
    private String languageCode;
    @Lob
    @Column(name = "question_as_asked")
    private String questionAsAsked;
    @Column(name = "response_duration")
    private Integer responseDuration;
    @Column(name = "response_latency")
    private Integer responseLatency;
    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "when_as_ms", nullable = false)
    private long whenAsMs;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerId;
    @JoinColumn(name = "data_element_id", referencedColumnName = "data_element_id", nullable=true)  // FIXME:  needed to avoid key problem; yet data looks OK
    @ManyToOne
    private DataElement dataElementId;
    @JoinColumn(name = "null_flavor_id", referencedColumnName = "null_flavor_id", nullable = true)
    @ManyToOne
    private NullFlavor nullFlavorId;
    @JoinColumn(name = "null_flavor_change_id", referencedColumnName = "null_flavor_change_id", nullable = true)
    @ManyToOne
    private NullFlavorChange nullFlavorChangeId;  

    public ItemUsage() {
    }

    public ItemUsage(Long itemUsageId) {
        this.itemUsageId = itemUsageId;
    }

    public ItemUsage(Long itemUsageId,
                     int displayNum,
                     int itemUsageSequence,
                     long whenAsMs) {
        this.itemUsageId = itemUsageId;
        this.displayNum = displayNum;
        this.itemUsageSequence = itemUsageSequence;
        this.whenAsMs = whenAsMs;
    }

    public Long getItemUsageId() {
        return itemUsageId;
    }

    public void setItemUsageId(Long itemUsageId) {
        this.itemUsageId = itemUsageId;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public int getItemUsageSequence() {
        return itemUsageSequence;
    }

    public void setItemUsageSequence(int itemUsageSequence) {
        this.itemUsageSequence = itemUsageSequence;
    }

    public Integer getItemVisit() {
        return itemVisit;
    }

    public void setItemVisit(Integer itemVisit) {
        this.itemVisit = itemVisit;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getQuestionAsAsked() {
        return questionAsAsked;
    }

    public void setQuestionAsAsked(String questionAsAsked) {
        this.questionAsAsked = questionAsAsked;
    }

    public Integer getResponseDuration() {
        return responseDuration;
    }

    public void setResponseDuration(Integer responseDuration) {
        this.responseDuration = responseDuration;
    }

    public Integer getResponseLatency() {
        return responseLatency;
    }

    public void setResponseLatency(Integer responseLatency) {
        this.responseLatency = responseLatency;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getWhenAsMs() {
        return whenAsMs;
    }

    public void setWhenAsMs(long whenAsMs) {
        this.whenAsMs = whenAsMs;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

    public DataElement getDataElementId() {
        return dataElementId;
    }

    public void setDataElementId(DataElement dataElementId) {
        this.dataElementId = dataElementId;
    }

    public NullFlavor getNullFlavorId() {
        return nullFlavorId;
    }

    public void setNullFlavorId(NullFlavor nullFlavorId) {
        this.nullFlavorId = nullFlavorId;
    }

    public NullFlavorChange getNullFlavorChangeId() {
        return nullFlavorChangeId;
    }

    public void setNullFlavorChangeId(NullFlavorChange nullFlavorChangeId) {
        this.nullFlavorChangeId = nullFlavorChangeId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemUsageId != null ? itemUsageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemUsage)) {
            return false;
        }
        ItemUsage other = (ItemUsage) object;
        if ((this.itemUsageId == null && other.itemUsageId != null) || (this.itemUsageId != null && !this.itemUsageId.equals(other.itemUsageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ItemUsage[itemUsageId=" + itemUsageId + "]";
    }
}
