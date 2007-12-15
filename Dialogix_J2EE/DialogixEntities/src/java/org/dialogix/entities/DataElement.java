/*
 * DataElement.java
 * 
 * Created on Nov 2, 2007, 12:12:07 PM
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
@Table(name = "data_element")
public class DataElement implements Serializable {
    @TableGenerator(name="DataElement_Gen", pkColumnValue="DataElement", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DataElement_Gen")
    @Column(name = "data_element_id", nullable = false)
    private BigInteger dataElementID;
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

    public DataElement() {
    }

    public DataElement(BigInteger dataElementID) {
        this.dataElementID = dataElementID;
    }

    public DataElement(BigInteger dataElementID, int dataElementSequence, int nullFlavorID, Date timeStamp, int displayNum) {
        this.dataElementID = dataElementID;
        this.dataElementSequence = dataElementSequence;
        this.nullFlavorID = nullFlavorID;
        this.timeStamp = timeStamp;
        this.displayNum = displayNum;
    }

    public BigInteger getDataElementID() {
        return dataElementID;
    }

    public void setDataElementID(BigInteger dataElementID) {
        this.dataElementID = dataElementID;
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
        hash += (dataElementID != null ? dataElementID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DataElement)) {
            return false;
        }
        DataElement other = (DataElement) object;
        if ((this.dataElementID == null && other.dataElementID != null) || (this.dataElementID != null && !this.dataElementID.equals(other.dataElementID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.DataElement[dataElementID=" + dataElementID + "]";
    }

}
