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
import java.math.BigInteger;
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

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "v1_item_usage")
public class V1ItemUsage implements Serializable {
    @TableGenerator(name="V1ItemUsage_Gen", pkColumnValue="V1ItemUsage", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=50)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="V1ItemUsage_Gen")  
    @Column(name = "v1_item_usage_id", nullable = false)
    private BigInteger v1ItemUsageID;
    @Column(name = "ItemUsageSequence", nullable = false)
    private int itemUsageSequence;
    @Column(name = "VarName", nullable = false, length=200)
    private String varName;
    @Column(name = "DataElementSequence", nullable = false)
    private int dataElementSequence;
    @Column(name = "GroupNum")
    private Integer groupNum;
    @Column(name = "DisplayNum", nullable = false)
    private int displayNum;
    @Column(name = "LanguageCode", length=2)
    private String languageCode;
    @Lob
    @Column(name = "QuestionAsAsked")
    private String questionAsAsked;
    @Lob
    @Column(name = "AnswerCode")
    private String answerCode;
    @Lob
    @Column(name = "AnswerString")
    private String answerString;
    @Column(name = "Time_Stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "WhenAsMS")
    private long whenAsMS;
    @Column(name = "itemVisits")
    private Integer itemVisits;
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
    @Lob
    @Column(name = "Comments")
    private String comments;
    @JoinColumn(name = "v1_instrument_session_id", referencedColumnName = "v1_instrument_session_id")
    @ManyToOne
    private V1InstrumentSession v1InstrumentSessionID;

    public V1ItemUsage() {
    }

    public V1ItemUsage(BigInteger v1ItemUsageID) {
        this.v1ItemUsageID = v1ItemUsageID;
    }

    public V1ItemUsage(BigInteger v1ItemUsageID, int itemUsageSequence, String varName, int dataElementSequence, int displayNum, Date timeStamp, long whenAsMS) {
        this.v1ItemUsageID = v1ItemUsageID;
        this.itemUsageSequence = itemUsageSequence;
        this.varName = varName;
        this.dataElementSequence = dataElementSequence;
        this.displayNum = displayNum;
        this.timeStamp = timeStamp;
        this.whenAsMS = whenAsMS;
    }

    public BigInteger getV1ItemUsageID() {
        return v1ItemUsageID;
    }

    public void setV1ItemUsageID(BigInteger v1ItemUsageID) {
        this.v1ItemUsageID = v1ItemUsageID;
    }

    public int getItemUsageSequence() {
        return itemUsageSequence;
    }

    public void setItemUsageSequence(int itemUsageSequence) {
        this.itemUsageSequence = itemUsageSequence;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public int getDataElementSequence() {
        return dataElementSequence;
    }

    public void setDataElementSequence(int dataElementSequence) {
        this.dataElementSequence = dataElementSequence;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
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

    public Integer getItemVisits() {
        return itemVisits;
    }

    public void setItemVisits(Integer itemVisits) {
        this.itemVisits = itemVisits;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public V1InstrumentSession getV1InstrumentSessionID() {
        return v1InstrumentSessionID;
    }

    public void setV1InstrumentSessionID(V1InstrumentSession v1InstrumentSessionID) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
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
        return "org.dialogix.model1.V1ItemUsage[v1ItemUsageID=" + v1ItemUsageID + "]";
    }

}
