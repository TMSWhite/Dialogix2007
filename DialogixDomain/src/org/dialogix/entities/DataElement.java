/*
 * DataElement.java
 * 
 * Created on Oct 29, 2007, 12:40:47 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "data_element")
@NamedQueries({@NamedQuery(name = "DataElement.findByDataElementID", query = "SELECT d FROM DataElement d WHERE d.dataElementID = :dataElementID"), @NamedQuery(name = "DataElement.findByLanguageCode", query = "SELECT d FROM DataElement d WHERE d.languageCode = :languageCode"), @NamedQuery(name = "DataElement.findByAnswerID", query = "SELECT d FROM DataElement d WHERE d.answerID = :answerID"), @NamedQuery(name = "DataElement.findByNullFlavorID", query = "SELECT d FROM DataElement d WHERE d.nullFlavorID = :nullFlavorID"), @NamedQuery(name = "DataElement.findByTimeStamp", query = "SELECT d FROM DataElement d WHERE d.timeStamp = :timeStamp"), @NamedQuery(name = "DataElement.findByItemVacillation", query = "SELECT d FROM DataElement d WHERE d.itemVacillation = :itemVacillation"), @NamedQuery(name = "DataElement.findByResponseLatency", query = "SELECT d FROM DataElement d WHERE d.responseLatency = :responseLatency"), @NamedQuery(name = "DataElement.findByResponseDuration", query = "SELECT d FROM DataElement d WHERE d.responseDuration = :responseDuration")})
public class DataElement implements Serializable {
    @TableGenerator(name="DataElement_Generator", pkColumnValue="DataElement", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DataElement_Generator")
    @Column(name = "DataElement_ID", nullable = false)
    private Integer dataElementID;
    @Column(name = "LanguageCode")
    private String languageCode;
    @Lob
    @Column(name = "QuestionAsAsked")
    private String questionAsAsked;
    @Lob
    @Column(name = "AnswerString")
    private String answerString;
    @Column(name = "Answer_ID")
    private Integer answerID;
    @Column(name = "NullFlavor_ID", nullable = false)
    private int nullFlavorID;
    @Lob
    @Column(name = "Comments")
    private String comments;
    @Column(name = "Time_Stamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Column(name = "itemVacillation")
    private Integer itemVacillation;
    @Column(name = "responseLatency")
    private Integer responseLatency;
    @Column(name = "responseDuration")
    private Integer responseDuration;
    @JoinColumn(name = "InstrumentContent_ID", referencedColumnName = "InstrumentContent_ID")
    @ManyToOne
    private InstrumentContent instrumentContentID;
    @JoinColumn(name = "InstrumentSession_ID", referencedColumnName = "InstrumentSession_ID")
    @ManyToOne
    private InstrumentSession instrumentSessionID;

    public DataElement() {
    }

    public DataElement(Integer dataElementID) {
        this.dataElementID = dataElementID;
    }

    public DataElement(Integer dataElementID, int nullFlavorID, Date timeStamp) {
        this.dataElementID = dataElementID;
        this.nullFlavorID = nullFlavorID;
        this.timeStamp = timeStamp;
    }

    public Integer getDataElementID() {
        return dataElementID;
    }

    public void setDataElementID(Integer dataElementID) {
        this.dataElementID = dataElementID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataElementID != null ? dataElementID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
