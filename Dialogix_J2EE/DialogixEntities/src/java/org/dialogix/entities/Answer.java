/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answer")
public class Answer implements Serializable {

    @TableGenerator(name = "Answer_gen", pkColumnValue = "answer", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Answer_gen")
    @Column(name = "answer_id", nullable = false)
    private Long answerId;
    @OneToMany(mappedBy = "answerId")
    private Collection<SemanticMappingA> semanticMappingACollection;
    @OneToMany(mappedBy = "answerId")
    private Collection<AnswerListContent> answerListContentCollection;
    @OneToMany(mappedBy = "answerId")
    private Collection<SemanticMappingQA> semanticMappingQACollection;
    @OneToMany(mappedBy = "answerId")
    private Collection<AnswerLocalized> answerLocalizedCollection;
    @OneToMany(mappedBy = "answerId")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;
    @OneToMany(mappedBy = "answerId")
    private Collection<ItemUsage> itemUsageCollection;

    public Answer() {
    }

    public Answer(Long answerId) {
        this.answerId = answerId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Collection<SemanticMappingA> getSemanticMappingACollection() {
        return semanticMappingACollection;
    }

    public void setSemanticMappingACollection(
        Collection<SemanticMappingA> semanticMappingACollection) {
        this.semanticMappingACollection = semanticMappingACollection;
    }

    public Collection<AnswerListContent> getAnswerListContentCollection() {
        return answerListContentCollection;
    }

    public void setAnswerListContentCollection(
        Collection<AnswerListContent> answerListContentCollection) {
        this.answerListContentCollection = answerListContentCollection;
    }

    public Collection<SemanticMappingQA> getSemanticMappingQACollection() {
        return semanticMappingQACollection;
    }

    public void setSemanticMappingQACollection(
        Collection<SemanticMappingQA> semanticMappingQACollection) {
        this.semanticMappingQACollection = semanticMappingQACollection;
    }

    public Collection<AnswerLocalized> getAnswerLocalizedCollection() {
        return answerLocalizedCollection;
    }

    public void setAnswerLocalizedCollection(
        Collection<AnswerLocalized> answerLocalizedCollection) {
        this.answerLocalizedCollection = answerLocalizedCollection;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(
        Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    public Collection<ItemUsage> getItemUsageCollection() {
        return itemUsageCollection;
    }

    public void setItemUsageCollection(Collection<ItemUsage> itemUsageCollection) {
        this.itemUsageCollection = itemUsageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerId != null ? answerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.answerId == null && other.answerId != null) || (this.answerId != null && !this.answerId.equals(other.answerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Answer[answerId=" + answerId + "]";
    }
}
