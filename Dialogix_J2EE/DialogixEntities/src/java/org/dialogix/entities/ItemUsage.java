/*
 * ItemUsage.java
 * 
 * Created on Nov 2, 2007, 12:12:09 PM
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
 * @author Coevtmw
 */
@Entity
@Table(name = "item_usage")
public class ItemUsage implements Serializable {
    @TableGenerator(name="ItemUsage_Gen", pkColumnValue="ItemUsage", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ItemUsage_Gen")
    @Column(name = "item_usage_id", nullable = false)
    private BigInteger itemUsageID;
    @Column(name = "ItemUsageSequence", nullable = false)
    private int itemUsageSequence;
    @Column(name = "DataElementSequence", nullable = false)
    private int dataElementSequence;
    @Column(name = "GroupNum")
    private Integer groupNum;    
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
    @Column(name = "answer_id")
    private BigInteger answerID;
    @Column(name = "null_flavor_id", nullable = false)
    private int nullFlavorID;
    @Lob
    @Column(name = "Comments")
    private String comments;
    @Column(name = "Time_Stamp", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "WhenAsMS", nullable = false)
    private long whenAsMS;    
    @Column(name = "DisplayNum", nullable = false)
    private int displayNum;
    @Column(name = "itemVisits")
    private Integer itemVisits;
    @Column(name = "responseLatency")
    private Integer responseLatency;
    @Column(name = "responseDuration")
    private Integer responseDuration;
    @JoinColumn(name = "instrument_content_id", referencedColumnName = "instrument_content_id")
    @ManyToOne
    private InstrumentContent instrumentContentID;
    @JoinColumn(name = "instrument_session_id", referencedColumnName = "instrument_session_id")
    @ManyToOne
    private InstrumentSession instrumentSessionID;
    @JoinColumn(name = "var_name_id", referencedColumnName = "var_name_id")
    @ManyToOne
    private VarName varNameID;    

    public ItemUsage() {
    }

    public ItemUsage(BigInteger itemUsageID) {
        this.itemUsageID = itemUsageID;
    }

    public ItemUsage(BigInteger itemUsageID, int itemUsageSequence, int groupNum, int displayNum, String languageCode, long whenAsMS, Date timeStamp, int nullFlavorID, String questionAsAsked, String comments) {
        this.itemUsageID = itemUsageID;
        this.itemUsageSequence = itemUsageSequence;
        this.groupNum = groupNum;
        this.displayNum = displayNum;
        this.languageCode = languageCode;
        this.whenAsMS = whenAsMS;
        this.timeStamp = timeStamp;
        this.nullFlavorID = nullFlavorID;
        this.questionAsAsked = questionAsAsked;
        this.comments = comments;
    }

    public BigInteger getItemUsageID() {
        return itemUsageID;
    }

    public void setItemUsageID(BigInteger itemUsageID) {
        this.itemUsageID = itemUsageID;
    }

    public int getItemUsageSequence() {
        return itemUsageSequence;
    }

    public void setItemUsageSequence(int itemUsageSequence) {
        this.itemUsageSequence = itemUsageSequence;
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

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }
    
    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public BigInteger getAnswerID() {
        return answerID;
    }

    public void setAnswerID(BigInteger answerID) {
        this.answerID = answerID;
    }

    public int getNullFlavorID() {
        return nullFlavorID;
    }

    public void setNullFlavorID(int nullFlavorID) {
        this.nullFlavorID = nullFlavorID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
    
    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public Integer getResponseLatency() {
        return responseLatency;
    }

    public void setResponseLatency(Integer responseLatency) {
        this.responseLatency = responseLatency;
    }

    public Integer getResponseDuration() {
        return responseDuration;
    }

    public void setResponseDuration(Integer responseDuration) {
        this.responseDuration = responseDuration;
    }

    public InstrumentContent getInstrumentContentID() {
        return instrumentContentID;
    }

    public void setInstrumentContentID(InstrumentContent instrumentContentID) {
        this.instrumentContentID = instrumentContentID;
    }

    public InstrumentSession getInstrumentSessionID() {
        return instrumentSessionID;
    }

    public void setInstrumentSessionID(InstrumentSession instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
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
        hash += (itemUsageID != null ? itemUsageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemUsage)) {
            return false;
        }
        ItemUsage other = (ItemUsage) object;
        if ((this.itemUsageID == null && other.itemUsageID != null) || (this.itemUsageID != null && !this.itemUsageID.equals(other.itemUsageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ItemUsage[itemUsageID=" + itemUsageID + "]";
    }

}
