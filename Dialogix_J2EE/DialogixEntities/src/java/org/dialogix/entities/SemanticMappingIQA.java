/*
 * SemanticMappingIQA.java
 * 
 * Created on Nov 2, 2007, 11:15:11 AM
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
@Table(name = "semantic_mapping_i_q_as")
public class SemanticMappingIQA implements Serializable {
    @TableGenerator(name="semantic_mapping_i_q_a_gen", pkColumnValue="semantic_mapping_i_q_a", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="semantic_mapping_i_q_a_gen")
    @Column(name = "semantic_mapping_i_q_a_id", nullable = false)
    private Long semanticMappingIQAID;
    @Lob
    @Column(name = "code")
    private String code;
    @Lob
    @Column(name = "code_display_name")
    private String codeDisplayName;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerID;
    @JoinColumn(name = "code_system_id", referencedColumnName = "code_system_id")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingIQA() {
    }

    public SemanticMappingIQA(Long semanticMappingIQAID) {
        this.semanticMappingIQAID = semanticMappingIQAID;
    }

    public Long getSemanticMappingIQAID() {
        return semanticMappingIQAID;
    }

    public void setSemanticMappingIQAID(Long semanticMappingIQAID) {
        this.semanticMappingIQAID = semanticMappingIQAID;
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

    public InstrumentVersion getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(InstrumentVersion instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
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
        hash += (semanticMappingIQAID != null ? semanticMappingIQAID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SemanticMappingIQA)) {
            return false;
        }
        SemanticMappingIQA other = (SemanticMappingIQA) object;
        if ((this.semanticMappingIQAID == null && other.semanticMappingIQAID != null) || (this.semanticMappingIQAID != null && !this.semanticMappingIQAID.equals(other.semanticMappingIQAID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SemanticMappingIQA[semanticMappingIQAID=" + semanticMappingIQAID + "]";
    }

}
