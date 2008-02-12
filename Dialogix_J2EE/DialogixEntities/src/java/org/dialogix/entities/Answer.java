/*
 * Answer.java
 * 
 * Created on Nov 2, 2007, 11:15:09 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answers")
public class Answer implements Serializable {
    @TableGenerator(name="answer_gen", pkColumnValue="answer", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="answer_gen")
    @Column(name = "id", nullable = false)
    private Long answerID;
    @Column(name = "has_la_code")
    private Boolean hasLAcode;
    @Column(name = "la_code")
    private String lAcode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerID")
    private Collection<SemanticMappingA> semanticMappingACollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerID")
    private Collection<AnswerListContent> answerListContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerID")
    private Collection<SemanticMappingQA> semanticMappingQACollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerID")
    private Collection<AnswerLocalized> answerLocalizedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerID")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;

    public Answer() {
    }

    public Answer(Long answerID) {
        this.answerID = answerID;
    }

    public Long getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Long answerID) {
        this.answerID = answerID;
    }

    public Boolean getHasLAcode() {
        return hasLAcode;
    }

    public void setHasLAcode(Boolean hasLAcode) {
        this.hasLAcode = hasLAcode;
    }

    public String getLAcode() {
        return lAcode;
    }

    public void setLAcode(String lAcode) {
        this.lAcode = lAcode;
    }

    public Collection<SemanticMappingA> getSemanticMappingACollection() {
        return semanticMappingACollection;
    }

    public void setSemanticMappingACollection(Collection<SemanticMappingA> semanticMappingACollection) {
        this.semanticMappingACollection = semanticMappingACollection;
    }

    public Collection<AnswerListContent> getAnswerListContentCollection() {
        return answerListContentCollection;
    }

    public void setAnswerListContentCollection(Collection<AnswerListContent> answerListContentCollection) {
        this.answerListContentCollection = answerListContentCollection;
    }

    public Collection<SemanticMappingQA> getSemanticMappingQACollection() {
        return semanticMappingQACollection;
    }

    public void setSemanticMappingQACollection(Collection<SemanticMappingQA> semanticMappingQACollection) {
        this.semanticMappingQACollection = semanticMappingQACollection;
    }

    public Collection<AnswerLocalized> getAnswerLocalizedCollection() {
        return answerLocalizedCollection;
    }

    public void setAnswerLocalizedCollection(Collection<AnswerLocalized> answerLocalizedCollection) {
        this.answerLocalizedCollection = answerLocalizedCollection;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerID != null ? answerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.answerID == null && other.answerID != null) || (this.answerID != null && !this.answerID.equals(other.answerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Answer[answerID=" + answerID + "]";
    }

}
