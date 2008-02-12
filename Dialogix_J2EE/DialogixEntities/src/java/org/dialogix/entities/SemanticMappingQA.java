/*
 * SemanticMappingQA.java
 * 
 * Created on Nov 2, 2007, 11:15:10 AM
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
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "semantic_mapping_q_as")
public class SemanticMappingQA implements Serializable {
    @TableGenerator(name="semantic_mapping_q_a_gen", pkColumnValue="semantic_mapping_q_a", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="semantic_mapping_q_a_gen")
    @Column(name = "id", nullable = false)
    private Long semanticMappingQAID;
    @Lob
    @Column(name = "code")
    private String code;
    @Lob
    @Column(name = "code_display_name")
    private String codeDisplayName;
    @JoinColumn(name = "question_id", referencedColumnName="id")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "answer_id", referencedColumnName="id")
    @ManyToOne
    private Answer answerID;
    @JoinColumn(name = "code_system_id", referencedColumnName="id")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingQA() {
    }

    public SemanticMappingQA(Long semanticMappingQAID) {
        this.semanticMappingQAID = semanticMappingQAID;
    }

    public Long getSemanticMappingQAID() {
        return semanticMappingQAID;
    }

    public void setSemanticMappingQAID(Long semanticMappingQAID) {
        this.semanticMappingQAID = semanticMappingQAID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDisplayName() {
        return codeDisplayName;
    }

    public void setCodeDisplayName(String codeDisplayName) {
        this.codeDisplayName = codeDisplayName;
    }

    public Question getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Question questionID) {
        this.questionID = questionID;
    }

    public Answer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Answer answerID) {
        this.answerID = answerID;
    }

    public CodeSystem getCodeSystemID() {
        return codeSystemID;
    }

    public void setCodeSystemID(CodeSystem codeSystemID) {
        this.codeSystemID = codeSystemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticMappingQAID != null ? semanticMappingQAID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SemanticMappingQA)) {
            return false;
        }
        SemanticMappingQA other = (SemanticMappingQA) object;
        if ((this.semanticMappingQAID == null && other.semanticMappingQAID != null) || (this.semanticMappingQAID != null && !this.semanticMappingQAID.equals(other.semanticMappingQAID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SemanticMappingQA[semanticMappingQAID=" + semanticMappingQAID + "]";
    }

}
