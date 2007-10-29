/*
 * AnswerLocalized.java
 * 
 * Created on Oct 26, 2007, 5:17:12 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answer_localized")
@NamedQueries({@NamedQuery(name = "AnswerLocalized.findByAnswerLocalizedID", query = "SELECT a FROM AnswerLocalized a WHERE a.answerLocalizedID = :answerLocalizedID"), @NamedQuery(name = "AnswerLocalized.findByLanguageCode", query = "SELECT a FROM AnswerLocalized a WHERE a.languageCode = :languageCode")})
public class AnswerLocalized implements Serializable {
    @TableGenerator(name="AnswerLocalized_Generator", pkColumnValue="AnswerLocalized", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="AnswerLocalized_Generator")
    @Column(name = "AnswerLocalized_ID", nullable = false)
    private Integer answerLocalizedID;
    @Column(name = "LanguageCode", nullable = false)
    private String languageCode;
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

    public AnswerLocalized(Integer answerLocalizedID, String languageCode) {
        this.answerLocalizedID = answerLocalizedID;
        this.languageCode = languageCode;
    }

    public Integer getAnswerLocalizedID() {
        return answerLocalizedID;
    }

    public void setAnswerLocalizedID(Integer answerLocalizedID) {
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
        return "org.dialogix.entities.AnswerLocalized[answerLocalizedID=" + answerLocalizedID + "]";
    }

}
