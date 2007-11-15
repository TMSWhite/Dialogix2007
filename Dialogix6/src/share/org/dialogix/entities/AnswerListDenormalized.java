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
 * @author coevtmw
 */
@Entity
@Table(name = "answer_list_denormalized")
@NamedQueries({@NamedQuery(name = "AnswerListDenormalized.findByAnswerListDenormalizedID", query = "SELECT a FROM AnswerListDenormalized a WHERE a.answerListDenormalizedID = :answerListDenormalizedID"), @NamedQuery(name = "AnswerListDenormalized.findByLanguageCode", query = "SELECT a FROM AnswerListDenormalized a WHERE a.languageCode = :languageCode")})
public class AnswerListDenormalized implements Serializable {
    @TableGenerator(name="AnswerListDenormalized_Generator", pkColumnValue="AnswerListDenormalized", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="AnswerListDenormalized_Generator")    
    @Column(name = "AnswerListDenormalized_ID", nullable = false)
    private Integer answerListDenormalizedID;
    @Lob
    @Column(name = "AnswerListDenormalizedString", nullable = false)
    private String answerListDenormalizedString;
    @Column(name = "LanguageCode", nullable = false)
    private String languageCode;
    @JoinColumn(name = "AnswerList_ID", referencedColumnName = "AnswerList_ID")
    @ManyToOne
    private AnswerList answerListID;

    public AnswerListDenormalized() {
    }

    public AnswerListDenormalized(Integer answerListDenormalizedID) {
        this.answerListDenormalizedID = answerListDenormalizedID;
    }

    public AnswerListDenormalized(Integer answerListDenormalizedID, String answerListDenormalizedString, String languageCode) {
        this.answerListDenormalizedID = answerListDenormalizedID;
        this.answerListDenormalizedString = answerListDenormalizedString;
        this.languageCode = languageCode;
    }

    public Integer getAnswerListDenormalizedID() {
        return answerListDenormalizedID;
    }

    public void setAnswerListDenormalizedID(Integer answerListDenormalizedID) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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

}
