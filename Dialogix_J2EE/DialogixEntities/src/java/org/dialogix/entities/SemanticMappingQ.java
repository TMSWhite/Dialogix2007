/*
 * SemanticMappingQ.java
 * 
 * Created on Nov 2, 2007, 11:15:05 AM
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
@Table(name = "semantic_mapping_qs")
public class SemanticMappingQ implements Serializable {
    @TableGenerator(name="semantic_mapping_q_gen", pkColumnValue="semantic_mapping_q", table="map_sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="semantic_mapping_q_gen")
    @Column(name = "id", nullable = false)
    private Long semanticMappingQID;
    @Lob
    @Column(name = "code")
    private String code;
    @Lob
    @Column(name = "code_display_name")
    private String codeDisplayName;
    @JoinColumn(name = "question_id", referencedColumnName="id")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "code_system_id", referencedColumnName="id")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingQ() {
    }

    public SemanticMappingQ(Long semanticMappingQID) {
        this.semanticMappingQID = semanticMappingQID;
    }

    public Long getSemanticMappingQID() {
        return semanticMappingQID;
    }

    public void setSemanticMappingQID(Long semanticMappingQID) {
        this.semanticMappingQID = semanticMappingQID;
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

    public CodeSystem getCodeSystemID() {
        return codeSystemID;
    }

    public void setCodeSystemID(CodeSystem codeSystemID) {
        this.codeSystemID = codeSystemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanticMappingQID != null ? semanticMappingQID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SemanticMappingQ)) {
            return false;
        }
        SemanticMappingQ other = (SemanticMappingQ) object;
        if ((this.semanticMappingQID == null && other.semanticMappingQID != null) || (this.semanticMappingQID != null && !this.semanticMappingQID.equals(other.semanticMappingQID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SemanticMappingQ[semanticMappingQID=" + semanticMappingQID + "]";
    }

}
