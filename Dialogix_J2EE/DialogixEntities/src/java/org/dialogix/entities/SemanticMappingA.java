/*
 * SemanticMappingA.java
 * 
 * Created on Nov 2, 2007, 11:15:04 AM
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
@Table(name = "semantic_mapping_as")
public class SemanticMappingA implements Serializable {
    @TableGenerator(name="semantic_mapping_a_gen", pkColumnValue="semantic_mapping_a", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="semantic_mapping_a_gen")
    @Column(name = "semantic_mapping_a_id", nullable = false)
    private Long semanticMappingAID;
    @Lob
    @Column(name = "code")
    private String code;
    @Lob
    @Column(name = "code_display_name")
    private String codeDisplayName;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerID;
    @JoinColumn(name = "code_system_id", referencedColumnName = "code_system_id")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingA() {
    }

    public SemanticMappingA(Long semanticMappingAID) {
        this.semanticMappingAID = semanticMappingAID;
    }

    public Long getSemanticMappingAID() {
        return semanticMappingAID;
    }

    public void setSemanticMappingAID(Long semanticMappingAID) {
        this.semanticMappingAID = semanticMappingAID;
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
        hash += (semanticMappingAID != null ? semanticMappingAID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SemanticMappingA)) {
            return false;
        }
        SemanticMappingA other = (SemanticMappingA) object;
        if ((this.semanticMappingAID == null && other.semanticMappingAID != null) || (this.semanticMappingAID != null && !this.semanticMappingAID.equals(other.semanticMappingAID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SemanticMappingA[semanticMappingAID=" + semanticMappingAID + "]";
    }

}
