/*
 * AnswerLocalized.java
 * 
 * Created on Oct 22, 2007, 4:09:01 PM
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
@Table(name = "answerlocalized")
@NamedQueries({@NamedQuery(name = "AnswerLocalized.findByAnswerLocalizedID", query = "SELECT a FROM AnswerLocalized a WHERE a.answerLocalizedID = :answerLocalizedID"), @NamedQuery(name = "AnswerLocalized.findByLanguageID", query = "SELECT a FROM AnswerLocalized a WHERE a.languageID = :languageID")})
public class AnswerLocalized implements Serializable {
    @Id
    @Column(name = "AnswerLocalized_ID", nullable = false)
    private Integer answerLocalizedID;
    @Column(name = "Language_ID", nullable = false)
    private int languageID;
    @Lob
    @Column(name = "AnswerString")
    private String answerString;
    @JoinColumn(name = "Answer_ID", referencedColumnName = "Answer_ID")
    @ManyToOne
    private Answer answerID;

    public AnswerLocalized() {
    }

    public AnswerLocalized(Integer answerLocalizedID) {
        this.answerLocalizedID = answerLocalizedID;
    }

    public AnswerLocalized(Integer answerLocalizedID, int languageID) {
        this.answerLocalizedID = answerLocalizedID;
        this.languageID = languageID;
    }

    public Integer getAnswerLocalizedID() {
        return answerLocalizedID;
    }

    public void setAnswerLocalizedID(Integer answerLocalizedID) {
        this.answerLocalizedID = answerLocalizedID;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.AnswerLocalized[answerLocalizedID=" + answerLocalizedID + "]";
    }

}
