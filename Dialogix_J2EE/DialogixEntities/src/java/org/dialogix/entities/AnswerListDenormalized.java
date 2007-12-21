/*
 * AnswerListDenormalized.java
 * 
 * Created on Nov 5, 2007, 5:00:23 PM
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
 * @author coevtmw
 */
@Entity
@Table(name = "answer_list_denormalized")
public class AnswerListDenormalized implements Serializable {
    @TableGenerator(name="AnswerListDenormalized_Gen", pkColumnValue="AnswerListDenormalized", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="AnswerListDenormalized_Gen")    
    @Column(name = "answer_list_denormalized_id", nullable = false)
    private BigInteger answerListDenormalizedID;
    @Lob
    @Column(name = "AnswerListDenormalizedString", nullable = false)
    private String answerListDenormalizedString;
    @Column(name = "AnswerListDenormalizedLength", nullable = false)
    private Integer answerListDenormalizedLength;
    @Column(name = "LanguageCode", nullable = false, length=2)
    private String languageCode;
    @JoinColumn(name = "answer_list_id", referencedColumnName = "answer_list_id")
    @ManyToOne
    private AnswerList answerListID;

    public AnswerListDenormalized() {
    }

    public AnswerListDenormalized(BigInteger answerListDenormalizedID) {
        this.answerListDenormalizedID = answerListDenormalizedID;
    }

    public AnswerListDenormalized(BigInteger answerListDenormalizedID, String answerListDenormalizedString, String languageCode) {
        this.answerListDenormalizedID = answerListDenormalizedID;
        this.answerListDenormalizedString = answerListDenormalizedString;
        this.languageCode = languageCode;
    }

    public BigInteger getAnswerListDenormalizedID() {
        return answerListDenormalizedID;
    }

    public void setAnswerListDenormalizedID(BigInteger answerListDenormalizedID) {
        this.answerListDenormalizedID = answerListDenormalizedID;
    }

    public String getAnswerListDenormalizedString() {
        return answerListDenormalizedString;
    }

    public void setAnswerListDenormalizedString(String answerListDenormalizedString) {
        this.answerListDenormalizedString = answerListDenormalizedString;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public AnswerList getAnswerListID() {
        return answerListID;
    }

    public void setAnswerListID(AnswerList answerListID) {
        this.answerListID = answerListID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerListDenormalizedID != null ? answerListDenormalizedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AnswerListDenormalized)) {
            return false;
        }
        AnswerListDenormalized other = (AnswerListDenormalized) object;
        if ((this.answerListDenormalizedID == null && other.answerListDenormalizedID != null) || (this.answerListDenormalizedID != null && !this.answerListDenormalizedID.equals(other.answerListDenormalizedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.AnswerListDenormalized[answerListDenormalizedID=" + answerListDenormalizedID + "]";
    }

    public Integer getAnswerListDenormalizedLength() {
        return answerListDenormalizedLength;
    }

    public void setAnswerListDenormalizedLength(Integer answerListDenormalizedLength) {
        this.answerListDenormalizedLength = answerListDenormalizedLength;
    }

}
