/*
 * AnswerLocalized.java
 * 
 * Created on Nov 2, 2007, 11:15:10 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answer_localizeds")
public class AnswerLocalized implements Serializable {
    @TableGenerator(name="answer_localized_gen", pkColumnValue="answer_localized", table="model_sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="answer_localized_gen")
    @Column(name = "id", nullable = false)
    private Long answerLocalizedID;
    @Column(name = "language_code", nullable = false, length=2)
    private String languageCode;
    @Lob
    @Column(name = "name")
    private String answerString;
    @Column(name = "answer_length", nullable=false)
    private Integer answerLength;
    @JoinColumn(name = "answer_id", referencedColumnName="id")
    @ManyToOne
    private Answer answerID;

    public AnswerLocalized() {
    }

    public AnswerLocalized(Long answerLocalizedID) {
        this.answerLocalizedID = answerLocalizedID;
    }

    public AnswerLocalized(Long answerLocalizedID, String languageCode) {
        this.answerLocalizedID = answerLocalizedID;
        this.languageCode = languageCode;
    }

    public Long getAnswerLocalizedID() {
        return answerLocalizedID;
    }

    public void setAnswerLocalizedID(Long answerLocalizedID) {
        this.answerLocalizedID = answerLocalizedID;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public Answer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Answer answerID) {
        this.answerID = answerID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerLocalizedID != null ? answerLocalizedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AnswerLocalized)) {
            return false;
        }
        AnswerLocalized other = (AnswerLocalized) object;
        if ((this.answerLocalizedID == null && other.answerLocalizedID != null) || (this.answerLocalizedID != null && !this.answerLocalizedID.equals(other.answerLocalizedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.AnswerLocalized[answerLocalizedID=" + answerLocalizedID + "]";
    }

    public Integer getAnswerLength() {
        return answerLength;
    }

    public void setAnswerLength(Integer answerLength) {
        this.answerLength = answerLength;
    }

}
