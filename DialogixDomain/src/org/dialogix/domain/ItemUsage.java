/*
 * ItemUsage.java
 * 
 * Created on Oct 22, 2007, 4:09:00 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "itemusage")
@NamedQueries({@NamedQuery(name = "ItemUsage.findByItemUsageID", query = "SELECT i FROM ItemUsage i WHERE i.itemUsageID = :itemUsageID"), @NamedQuery(name = "ItemUsage.findByPageUsageID", query = "SELECT i FROM ItemUsage i WHERE i.pageUsageID = :pageUsageID"), @NamedQuery(name = "ItemUsage.findByGroupNum", query = "SELECT i FROM ItemUsage i WHERE i.groupNum = :groupNum"), @NamedQuery(name = "ItemUsage.findByDisplayNum", query = "SELECT i FROM ItemUsage i WHERE i.displayNum = :displayNum"), @NamedQuery(name = "ItemUsage.findByLanguageID", query = "SELECT i FROM ItemUsage i WHERE i.languageID = :languageID"), @NamedQuery(name = "ItemUsage.findByLangCode", query = "SELECT i FROM ItemUsage i WHERE i.langCode = :langCode"), @NamedQuery(name = "ItemUsage.findByWhenAsMS", query = "SELECT i FROM ItemUsage i WHERE i.whenAsMS = :whenAsMS"), @NamedQuery(name = "ItemUsage.findByTimeStamp", query = "SELECT i FROM ItemUsage i WHERE i.timeStamp = :timeStamp"), @NamedQuery(name = "ItemUsage.findByAnswerID", query = "SELECT i FROM ItemUsage i WHERE i.answerID = :answerID"), @NamedQuery(name = "ItemUsage.findByNullFlavorID", query = "SELECT i FROM ItemUsage i WHERE i.nullFlavorID = :nullFlavorID"), @NamedQuery(name = "ItemUsage.findByItemVacillation", query = "SELECT i FROM ItemUsage i WHERE i.itemVacillation = :itemVacillation"), @NamedQuery(name = "ItemUsage.findByResponseLatency", query = "SELECT i FROM ItemUsage i WHERE i.responseLatency = :responseLatency"), @NamedQuery(name = "ItemUsage.findByResponseDuration", query = "SELECT i FROM ItemUsage i WHERE i.responseDuration = :responseDuration")})
public class ItemUsage implements Serializable {
    @Id
    @Column(name = "ItemUsage_ID", nullable = false)
    private BigInteger itemUsageID;
    @Column(name = "PageUsage_ID", nullable = false)
    private int pageUsageID;
    @Column(name = "GroupNum", nullable = false)
    private int groupNum;
    @Column(name = "DisplayNum", nullable = false)
    private int displayNum;
    @Column(name = "Language_ID", nullable = false)
    private int languageID;
    @Column(name = "LangCode", nullable = false)
    private String langCode;
    @Column(name = "WhenAsMS", nullable = false)
    private long whenAsMS;
    @Column(name = "Time_Stamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Lob
    @Column(name = "AnswerString")
    private String answerString;
    @Column(name = "Answer_ID")
    private Integer answerID;
    @Column(name = "NullFlavor_ID", nullable = false)
    private int nullFlavorID;
    @Lob
    @Column(name = "QuestionAsAsked", nullable = false)
    private String questionAsAsked;
    @Column(name = "itemVacillation")
    private Integer itemVacillation;
    @Column(name = "responseLatency")
    private Integer responseLatency;
    @Column(name = "responseDuration")
    private Integer responseDuration;
    @Lob
    @Column(name = "Comments", nullable = false)
    private String comments;
    @JoinColumn(name = "InstrumentSession_ID", referencedColumnName = "InstrumentSession_ID")
    @ManyToOne
    private InstrumentSession instrumentSessionID;
    @JoinColumn(name = "VarName_ID", referencedColumnName = "VarName_ID")
    @ManyToOne
    private VarName varNameID;
    @JoinColumn(name = "InstrumentContent_ID", referencedColumnName = "InstrumentContent_ID")
    @ManyToOne
    private InstrumentContent instrumentContentID;

    public ItemUsage() {
    }

    public ItemUsage(BigInteger itemUsageID) {
        this.itemUsageID = itemUsageID;
    }

    public ItemUsage(BigInteger itemUsageID, int pageUsageID, int groupNum, int displayNum, int languageID, String langCode, long whenAsMS, Date timeStamp, int nullFlavorID, String questionAsAsked, String comments) {
        this.itemUsageID = itemUsageID;
        this.pageUsageID = pageUsageID;
        this.groupNum = groupNum;
        this.displayNum = displayNum;
        this.languageID = languageID;
        this.langCode = langCode;
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

    public int getPageUsageID() {
        return pageUsageID;
    }

    public void setPageUsageID(int pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public long getWhenAsMS() {
        return whenAsMS;
    }

    public void setWhenAsMS(long whenAsMS) {
        this.whenAsMS = whenAsMS;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer answerID) {
        this.answerID = answerID;
    }

    public int getNullFlavorID() {
        return nullFlavorID;
    }

    public void setNullFlavorID(int nullFlavorID) {
        this.nullFlavorID = nullFlavorID;
    }

    public String getQuestionAsAsked() {
        return questionAsAsked;
    }

    public void setQuestionAsAsked(String questionAsAsked) {
        this.questionAsAsked = questionAsAsked;
    }

    public Integer getItemVacillation() {
        return itemVacillation;
    }

    public void setItemVacillation(Integer itemVacillation) {
        this.itemVacillation = itemVacillation;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public InstrumentContent getInstrumentContentID() {
        return instrumentContentID;
    }

    public void setInstrumentContentID(InstrumentContent instrumentContentID) {
        this.instrumentContentID = instrumentContentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemUsageID != null ? itemUsageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.ItemUsage[itemUsageID=" + itemUsageID + "]";
    }

}
