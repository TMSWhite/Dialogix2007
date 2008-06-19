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
@Table(name = "question")
public class Question implements Serializable {

    @TableGenerator(name = "Question_gen", pkColumnValue = "question", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Question_gen")
    @Column(name = "question_id", nullable = false)
    private Long questionId;
    @OneToMany(mappedBy = "questionId")
    private Collection<SemanticMappingQA> semanticMappingQACollection;
    @OneToMany(mappedBy = "questionId")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;
    @OneToMany(mappedBy = "questionId")
    private Collection<SemanticMappingQ> semanticMappingQCollection;
    @OneToMany(mappedBy = "questionId")
    private Collection<Item> itemCollection;
    @OneToMany(mappedBy = "questionId")
    private Collection<QuestionLocalized> questionLocalizedCollection;

    public Question() {
    }

    public Question(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Collection<SemanticMappingQA> getSemanticMappingQACollection() {
        return semanticMappingQACollection;
    }

    public void setSemanticMappingQACollection(
        Collection<SemanticMappingQA> semanticMappingQACollection) {
        this.semanticMappingQACollection = semanticMappingQACollection;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(
        Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    public Collection<SemanticMappingQ> getSemanticMappingQCollection() {
        return semanticMappingQCollection;
    }

    public void setSemanticMappingQCollection(
        Collection<SemanticMappingQ> semanticMappingQCollection) {
        this.semanticMappingQCollection = semanticMappingQCollection;
    }

    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    public Collection<QuestionLocalized> getQuestionLocalizedCollection() {
        return questionLocalizedCollection;
    }

    public void setQuestionLocalizedCollection(
        Collection<QuestionLocalized> questionLocalizedCollection) {
        this.questionLocalizedCollection = questionLocalizedCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Question[questionId=" + questionId + "]";
    }
}
