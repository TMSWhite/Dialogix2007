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
@Table(name = "answer_localized")
public class AnswerLocalized implements Serializable {
    @TableGenerator(name="AnswerLocalized_Gen", pkColumnValue="AnswerLocalized", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="AnswerLocalized_Gen")
    @Column(name = "answer_localized_id", nullable = false)
    private BigInteger answerLocalizedID;
    @Column(name = "LanguageCode", nullable = false, length=2)
    private String languageCode;
    @Lob
    @Column(name = "AnswerString")
    private String answerString;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerID;

    public AnswerLocalized() {
    }

    public AnswerLocalized(BigInteger answerLocalizedID) {
        this.answerLocalizedID = answerLocalizedID;
    }

    public AnswerLocalized(BigInteger answerLocalizedID, String languageCode) {
        this.answerLocalizedID = answerLocalizedID;
        this.languageCode = languageCode;
    }

    public BigInteger getAnswerLocalizedID() {
        return answerLocalizedID;
    }

    public void setAnswerLocalizedID(BigInteger answerLocalizedID) {
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

}
