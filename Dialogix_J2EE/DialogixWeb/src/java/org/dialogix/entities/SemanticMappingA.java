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
@Table(name = "semantic_mapping_a")
public class SemanticMappingA implements Serializable {

    @TableGenerator(name = "SemanticMappingA_gen", pkColumnValue = "semantic_mapping_a", table = "sequence_map", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SemanticMappingA_gen")
    @Column(name = "semantic_mapping_a_id", nullable = false)
    private Long semanticMappingAId;
    @Lob
    @Column(name = "code_display_name")
    private String codeDisplayName;
    @Lob
    @Column(name = "code_value")
    private String codeValue;
    @JoinColumn(name = "code_system_id", referencedColumnName = "code_system_id")
    @ManyToOne
    private CodeSystem codeSystemId;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerId;

    public SemanticMappingA() {
    }

    public SemanticMappingA(Long semanticMappingAId) {
        this.semanticMappingAId = semanticMappingAId;
    }

    public Long getSemanticMappingAId() {
        return semanticMappingAId;
    }

    public void setSemanticMappingAId(Long semanticMappingAId) {
        this.semanticMappingAId = semanticMappingAId;
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

    public CodeSystem getCodeSystemId() {
        return codeSystemId;
    }

    public void setCodeSystemId(CodeSystem codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticMappingAId != null ? semanticMappingAId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemanticMappingA)) {
            return false;
        }
        SemanticMappingA other = (SemanticMappingA) object;
        if ((this.semanticMappingAId == null && other.semanticMappingAId != null) || (this.semanticMappingAId != null && !this.semanticMappingAId.equals(other.semanticMappingAId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SemanticMappingA[semanticMappingAId=" + semanticMappingAId + "]";
    }
}
