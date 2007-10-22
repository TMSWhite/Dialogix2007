/*
 * Datum.java
 * 
 * Created on Oct 22, 2007, 4:08:58 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

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
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "datum")
@NamedQueries({@NamedQuery(name = "Datum.findByDatumID", query = "SELECT d FROM Datum d WHERE d.datumID = :datumID"), @NamedQuery(name = "Datum.findByLanguageID", query = "SELECT d FROM Datum d WHERE d.languageID = :languageID"), @NamedQuery(name = "Datum.findByAnswerID", query = "SELECT d FROM Datum d WHERE d.answerID = :answerID"), @NamedQuery(name = "Datum.findByNullFlavorID", query = "SELECT d FROM Datum d WHERE d.nullFlavorID = :nullFlavorID"), @NamedQuery(name = "Datum.findByTimeStamp", query = "SELECT d FROM Datum d WHERE d.timeStamp = :timeStamp"), @NamedQuery(name = "Datum.findByItemVacillation", query = "SELECT d FROM Datum d WHERE d.itemVacillation = :itemVacillation"), @NamedQuery(name = "Datum.findByResponseLatency", query = "SELECT d FROM Datum d WHERE d.responseLatency = :responseLatency"), @NamedQuery(name = "Datum.findByResponseDuration", query = "SELECT d FROM Datum d WHERE d.responseDuration = :responseDuration")})
public class Datum implements Serializable {
    @Id
    @Column(name = "Datum_ID", nullable = false)
    private Integer datumID;
    @Column(name = "Language_ID", nullable = false)
    private int languageID;
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
    @JoinColumn(name = "PageUsage_ID", referencedColumnName = "PageUsage_ID")
    @ManyToOne
    private PageUsage pageUsageID;

    public Datum() {
    }

    public Datum(Integer datumID) {
        this.datumID = datumID;
    }

    public Datum(Integer datumID, int languageID, int nullFlavorID, Date timeStamp) {
        this.datumID = datumID;
        this.languageID = languageID;
        this.nullFlavorID = nullFlavorID;
        this.timeStamp = timeStamp;
    }

    public Integer getDatumID() {
        return datumID;
    }

    public void setDatumID(Integer datumID) {
        this.datumID = datumID;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
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

    public PageUsage getPageUsageID() {
        return pageUsageID;
    }

    public void setPageUsageID(PageUsage pageUsageID) {
        this.pageUsageID = pageUsageID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datumID != null ? datumID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datum)) {
            return false;
        }
        Datum other = (Datum) object;
        if ((this.datumID == null && other.datumID != null) || (this.datumID != null && !this.datumID.equals(other.datumID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.Datum[datumID=" + datumID + "]";
    }

}
