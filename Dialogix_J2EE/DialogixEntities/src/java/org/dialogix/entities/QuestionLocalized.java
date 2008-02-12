/*
 * QuestionLocalized.java
 * 
 * Created on Nov 2, 2007, 11:15:11 AM
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
@Table(name = "question_localizeds")
public class QuestionLocalized implements Serializable {
    @TableGenerator(name="question_localized_gen", pkColumnValue="question_localized", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=500)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="question_localized_gen")
    @Column(name = "id", nullable = false)
    private Long questionLocalizedID;
    @Column(name ="question_length", nullable=false)
    private Integer questionLength;
    @Column(name = "language_code", nullable = false, length=2)
    private String languageCode;
    @Lob
    @Column(name = "name")
    private String questionString;
    @JoinColumn(name = "question_id", referencedColumnName="id")
    @ManyToOne
    private Question questionID;

    public QuestionLocalized() {
    }

    public QuestionLocalized(Long questionLocalizedID) {
        this.questionLocalizedID = questionLocalizedID;
    }

    public QuestionLocalized(Long questionLocalizedID, String languageCode) {
        this.questionLocalizedID = questionLocalizedID;
        this.languageCode = languageCode;
    }

    public Long getQuestionLocalizedID() {
        return questionLocalizedID;
    }

    public void setQuestionLocalizedID(Long questionLocalizedID) {
        this.questionLocalizedID = questionLocalizedID;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public Question getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Question questionID) {
        this.questionID = questionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionLocalizedID != null ? questionLocalizedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof QuestionLocalized)) {
            return false;
        }
        QuestionLocalized other = (QuestionLocalized) object;
        if ((this.questionLocalizedID == null && other.questionLocalizedID != null) || (this.questionLocalizedID != null && !this.questionLocalizedID.equals(other.questionLocalizedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.QuestionLocalized[questionLocalizedID=" + questionLocalizedID + "]";
    }

    public Integer getQuestionLength() {
        return questionLength;
    }

    public void setQuestionLength(Integer questionLength) {
        this.questionLength = questionLength;
    }

}
