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
import java.math.BigInteger;
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
@Table(name = "semantic_mapping_i_q_a")
public class SemanticMappingIQA implements Serializable {
    @TableGenerator(name="SemanticMappingIQA_Gen", pkColumnValue="SemanticMappingIQA", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="SemanticMappingIQA_Gen")
    @Column(name = "SemanticMapping_IQA_ID", nullable = false)
    private BigInteger semanticMappingIQAID;
    @Lob
    @Column(name = "Code")
    private String code;
    @Lob
    @Column(name = "CodeDisplayName")
    private String codeDisplayName;
    @JoinColumn(name = "InstrumentVersion_ID", referencedColumnName = "InstrumentVersion_ID")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "Question_ID", referencedColumnName = "Question_ID")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "Answer_ID", referencedColumnName = "Answer_ID")
    @ManyToOne
    private Answer answerID;
    @JoinColumn(name = "CodeSystem_ID", referencedColumnName = "CodeSystem_ID")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingIQA() {
    }

    public SemanticMappingIQA(BigInteger semanticMappingIQAID) {
        this.semanticMappingIQAID = semanticMappingIQAID;
    }

    public BigInteger getSemanticMappingIQAID() {
        return semanticMappingIQAID;
    }

    public void setSemanticMappingIQAID(BigInteger semanticMappingIQAID) {
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
