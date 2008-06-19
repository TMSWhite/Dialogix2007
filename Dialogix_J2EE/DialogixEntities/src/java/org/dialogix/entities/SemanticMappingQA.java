/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "semantic_mapping_q_a")
public class SemanticMappingQA implements Serializable {

    @TableGenerator(name = "SemanticMappingQA_gen", pkColumnValue = "semantic_mapping_q_a", table = "sequence_map", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SemanticMappingQA_gen")
    @Column(name = "semantic_mapping_q_a_id", nullable = false)
    private Long semanticMappingQAId;
    @Lob
    @Column(name = "code_display_name")
    private String codeDisplayName;
    @Lob
    @Column(name = "code_value")
    private String codeValue;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private Question questionId;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerId;
    @JoinColumn(name = "code_system_id", referencedColumnName = "code_system_id")
    @ManyToOne
    private CodeSystem codeSystemId;

    public SemanticMappingQA() {
    }

    public SemanticMappingQA(Long semanticMappingQAId) {
        this.semanticMappingQAId = semanticMappingQAId;
    }

    public Long getSemanticMappingQAId() {
        return semanticMappingQAId;
    }

    public void setSemanticMappingQAId(Long semanticMappingQAId) {
        this.semanticMappingQAId = semanticMappingQAId;
    }

    public String getCodeDisplayName() {
        return codeDisplayName;
    }

    public void setCodeDisplayName(String codeDisplayName) {
        this.codeDisplayName = codeDisplayName;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

    public CodeSystem getCodeSystemId() {
        return codeSystemId;
    }

    public void setCodeSystemId(CodeSystem codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticMappingQAId != null ? semanticMappingQAId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemanticMappingQA)) {
            return false;
        }
        SemanticMappingQA other = (SemanticMappingQA) object;
        if ((this.semanticMappingQAId == null && other.semanticMappingQAId != null) || (this.semanticMappingQAId != null && !this.semanticMappingQAId.equals(other.semanticMappingQAId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SemanticMappingQA[semanticMappingQAId=" + semanticMappingQAId + "]";
    }
}
