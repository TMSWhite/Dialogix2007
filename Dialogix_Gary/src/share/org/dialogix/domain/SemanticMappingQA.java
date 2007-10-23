/*
 * SemanticMappingQA.java
 * 
 * Created on Oct 22, 2007, 4:09:04 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "semanticmapping_qa")
@NamedQueries({@NamedQuery(name = "SemanticMappingQA.findBySemanticMappingQAID", query = "SELECT s FROM SemanticMappingQA s WHERE s.semanticMappingQAID = :semanticMappingQAID")})
public class SemanticMappingQA implements Serializable {
    @Id
    @Column(name = "SemanticMapping_QA_ID", nullable = false)
    private Integer semanticMappingQAID;
    @Lob
    @Column(name = "Code")
    private String code;
    @Lob
    @Column(name = "CodeDisplayName")
    private String codeDisplayName;
    @JoinColumn(name = "Question_ID", referencedColumnName = "Question_ID")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "Answer_ID", referencedColumnName = "Answer_ID")
    @ManyToOne
    private Answer answerID;
    @JoinColumn(name = "CodeSystem_ID", referencedColumnName = "CodeSystem_ID")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingQA() {
    }

    public SemanticMappingQA(Integer semanticMappingQAID) {
        this.semanticMappingQAID = semanticMappingQAID;
    }

    public Integer getSemanticMappingQAID() {
        return semanticMappingQAID;
    }

    public void setSemanticMappingQAID(Integer semanticMappingQAID) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.SemanticMappingQA[semanticMappingQAID=" + semanticMappingQAID + "]";
    }

}
