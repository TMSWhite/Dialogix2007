/*
 * QuestionLocalized.java
 * 
 * Created on Oct 22, 2007, 4:08:58 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "questionlocalized")
@NamedQueries({@NamedQuery(name = "QuestionLocalized.findByQuestionLocalizedID", query = "SELECT q FROM QuestionLocalized q WHERE q.questionLocalizedID = :questionLocalizedID"), @NamedQuery(name = "QuestionLocalized.findByLanguageID", query = "SELECT q FROM QuestionLocalized q WHERE q.languageID = :languageID")})
public class QuestionLocalized implements Serializable {
    @Id
    @Column(name = "QuestionLocalized_ID", nullable = false)
    private Integer questionLocalizedID;
    @Column(name = "Language_ID", nullable = false)
    private int languageID;
    @Lob
    @Column(name = "QuestionString")
    private String questionString;
    @JoinColumn(name = "Question_ID", referencedColumnName = "Question_ID")
    @ManyToOne
    private Question questionID;

    public QuestionLocalized() {
    }

    public QuestionLocalized(Integer questionLocalizedID) {
        this.questionLocalizedID = questionLocalizedID;
    }

    public QuestionLocalized(Integer questionLocalizedID, int languageID) {
        this.questionLocalizedID = questionLocalizedID;
        this.languageID = languageID;
    }

    public Integer getQuestionLocalizedID() {
        return questionLocalizedID;
    }

    public void setQuestionLocalizedID(Integer questionLocalizedID) {
        this.questionLocalizedID = questionLocalizedID;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.QuestionLocalized[questionLocalizedID=" + questionLocalizedID + "]";
    }

}
