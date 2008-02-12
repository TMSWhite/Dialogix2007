/*
 * V1ItemUsage.java
 * 
 * Created on Nov 19, 2007, 9:05:39 PM
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "v1_item_usages")
public class V1ItemUsage implements Serializable {
    @TableGenerator(name="v1_item_usage_gen", pkColumnValue="v1_item_usage", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=50)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="v1_item_usage_gen")  
    @Column(name = "id", nullable = false)
    private Long v1ItemUsageID;
    @Column(name = "item_usage_sequence", nullable = false)
    private int itemUsageSequence;
    @Column(name = "display_num", nullable = false)
    private int displayNum;
    @Column(name = "language_code", length=2)
    private String languageCode;
    @Lob
    @Column(name = "question_as_asked")
    private String questionAsAsked;
    @Lob
    @Column(name = "answer_code")
    private String answerCode;
    @Lob
    @Column(name = "answer_string")
    private String answerString;
    @Lob
    @Column(name = "answer_code0")
    private String answerCode0;
    @Lob
    @Column(name = "answer_string0")
    private String answerString0;    
    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "when_as_ms")
    private long whenAsMS;
    @Column(name = "item_visits")
    private Integer itemVisits;    
    @Lob
    @Column(name = "comments")
    private String comments;
    @Lob
    @Column(name = "comments0")
    private String comments0;    
    @JoinColumn(name = "v1_data_element_id", referencedColumnName="id")
    @ManyToOne
    private V1DataElement v1DataElementID;

    public V1ItemUsage() {
    }

    public V1ItemUsage(Long v1ItemUsageID) {
        this.v1ItemUsageID = v1ItemUsageID;
    }

    public V1ItemUsage(Long v1ItemUsageID, int itemUsageSequence, int displayNum, Date timeStamp, long whenAsMS) {
        this.v1ItemUsageID = v1ItemUsageID;
        this.itemUsageSequence = itemUsageSequence;
        this.displayNum = displayNum;
        this.timeStamp = timeStamp;
        this.whenAsMS = whenAsMS;
    }

    public Long getV1ItemUsageID() {
        return v1ItemUsageID;
    }

    public void setV1ItemUsageID(Long v1ItemUsageID) {
        this.v1ItemUsageID = v1ItemUsageID;
    }

    public int getItemUsageSequence() {
        return itemUsageSequence;
    }

    public void setItemUsageSequence(int itemUsageSequence) {
        this.itemUsageSequence = itemUsageSequence;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getWhenAsMS() {
        return whenAsMS;
    }

    public void setWhenAsMS(long whenAsMS) {
        this.whenAsMS = whenAsMS;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (v1ItemUsageID != null ? v1ItemUsageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof V1ItemUsage)) {
            return false;
        }
        V1ItemUsage other = (V1ItemUsage) object;
        if ((this.v1ItemUsageID == null && other.v1ItemUsageID != null) || (this.v1ItemUsageID != null && !this.v1ItemUsageID.equals(other.v1ItemUsageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.V1ItemUsage[v1ItemUsageID=" + v1ItemUsageID + "]";
    }

    public Integer getItemVisits() {
        return itemVisits;
    }

    public void setItemVisits(Integer itemVisits) {
        this.itemVisits = itemVisits;
    }
    @XmlTransient
    public V1DataElement getV1DataElementID() {
        return v1DataElementID;
    }

    public void setV1DataElementID(V1DataElement v1DataElementID) {
        this.v1DataElementID = v1DataElementID;
    }

    public String getAnswerCode0() {
        return answerCode0;
    }

    public void setAnswerCode0(String answerCode0) {
        this.answerCode0 = answerCode0;
    }

    public String getAnswerString0() {
        return answerString0;
    }

    public void setAnswerString0(String answerString0) {
        this.answerString0 = answerString0;
    }

    public String getComments0() {
        return comments0;
    }

    public void setComments0(String comments0) {
        this.comments0 = comments0;
    }
    
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.v1DataElementID = (V1DataElement) parent;
    }
}
