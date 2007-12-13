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
import java.math.BigInteger;
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
@Table(name = "question_localized")
public class QuestionLocalized implements Serializable {
    @TableGenerator(name="QuestionLocalized_Gen", pkColumnValue="QuestionLocalized", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=500)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="QuestionLocalized_Gen")
    @Column(name = "QuestionLocalized_ID", nullable = false)
    private BigInteger questionLocalizedID;
    @Column(name = "LanguageCode", nullable = false, length=2)
    private String languageCode;
    @Lob
    @Column(name = "QuestionString")
    private String questionString;
    @JoinColumn(name = "Question_ID", referencedColumnName = "Question_ID")
    @ManyToOne
    private Question questionID;

    public QuestionLocalized() {
    }

    public QuestionLocalized(BigInteger questionLocalizedID) {
        this.questionLocalizedID = questionLocalizedID;
    }

    public QuestionLocalized(BigInteger questionLocalizedID, String languageCode) {
        this.questionLocalizedID = questionLocalizedID;
        this.languageCode = languageCode;
    }

    public BigInteger getQuestionLocalizedID() {
        return questionLocalizedID;
    }

    public void setQuestionLocalizedID(BigInteger questionLocalizedID) {
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

}
