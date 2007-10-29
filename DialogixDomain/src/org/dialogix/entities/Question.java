/*
 * Question.java
 * 
 * Created on Oct 29, 2007, 12:40:49 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "question")
@NamedQueries({@NamedQuery(name = "Question.findByQuestionID", query = "SELECT q FROM Question q WHERE q.questionID = :questionID")})
public class Question implements Serializable {
    @TableGenerator(name="Question_Generator", pkColumnValue="Question", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Question_Generator")
    @Column(name = "Question_ID", nullable = false)
    private Integer questionID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionID")
    private Collection<SemanticMappingQ> semanticMappingQCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionID")
    private Collection<SemanticMappingQA> semanticMappingQACollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionID")
    private Collection<Item> itemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionID")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionID")
    private Collection<QuestionLocalized> questionLocalizedCollection;

    public Question() {
    }

    public Question(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Collection<SemanticMappingQ> getSemanticMappingQCollection() {
        return semanticMappingQCollection;
    }

    public void setSemanticMappingQCollection(Collection<SemanticMappingQ> semanticMappingQCollection) {
        this.semanticMappingQCollection = semanticMappingQCollection;
    }

    public Collection<SemanticMappingQA> getSemanticMappingQACollection() {
        return semanticMappingQACollection;
    }

    public void setSemanticMappingQACollection(Collection<SemanticMappingQA> semanticMappingQACollection) {
        this.semanticMappingQACollection = semanticMappingQACollection;
    }

    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    public Collection<QuestionLocalized> getQuestionLocalizedCollection() {
        return questionLocalizedCollection;
    }

    public void setQuestionLocalizedCollection(Collection<QuestionLocalized> questionLocalizedCollection) {
        this.questionLocalizedCollection = questionLocalizedCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionID != null ? questionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionID == null && other.questionID != null) || (this.questionID != null && !this.questionID.equals(other.questionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Question[questionID=" + questionID + "]";
    }

}
