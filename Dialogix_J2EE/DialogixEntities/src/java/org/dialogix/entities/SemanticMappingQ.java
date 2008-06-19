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
@Table(name = "semantic_mapping_q")
public class SemanticMappingQ implements Serializable {

    @TableGenerator(name = "SemanticMappingQ_gen", pkColumnValue = "semantic_mapping_q", table = "sequence_map", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SemanticMappingQ_gen")
    @Column(name = "semantic_mapping_q_id", nullable = false)
    private Long semanticMappingQId;
    @Lob
    @Column(name = "code_display_name")
    private String codeDisplayName;
    @Lob
    @Column(name = "code_value")
    private String codeValue;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private Question questionId;
    @JoinColumn(name = "code_system_id", referencedColumnName = "code_system_id")
    @ManyToOne
    private CodeSystem codeSystemId;

    public SemanticMappingQ() {
    }

    public SemanticMappingQ(Long semanticMappingQId) {
        this.semanticMappingQId = semanticMappingQId;
    }

    public Long getSemanticMappingQId() {
        return semanticMappingQId;
    }

    public void setSemanticMappingQId(Long semanticMappingQId) {
        this.semanticMappingQId = semanticMappingQId;
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

    public CodeSystem getCodeSystemId() {
        return codeSystemId;
    }

    public void setCodeSystemId(CodeSystem codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticMappingQId != null ? semanticMappingQId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemanticMappingQ)) {
            return false;
        }
        SemanticMappingQ other = (SemanticMappingQ) object;
        if ((this.semanticMappingQId == null && other.semanticMappingQId != null) || (this.semanticMappingQId != null && !this.semanticMappingQId.equals(other.semanticMappingQId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SemanticMappingQ[semanticMappingQId=" + semanticMappingQId + "]";
    }
}
