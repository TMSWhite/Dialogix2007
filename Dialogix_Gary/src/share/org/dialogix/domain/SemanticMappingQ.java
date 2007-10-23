/*
 * SemanticMappingQ.java
 * 
 * Created on Oct 22, 2007, 4:08:59 PM
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
@Table(name = "semanticmapping_q")
@NamedQueries({@NamedQuery(name = "SemanticMappingQ.findBySemanticMappingQID", query = "SELECT s FROM SemanticMappingQ s WHERE s.semanticMappingQID = :semanticMappingQID")})
public class SemanticMappingQ implements Serializable {
    @Id
    @Column(name = "SemanticMapping_Q_ID", nullable = false)
    private Integer semanticMappingQID;
    @Lob
    @Column(name = "Code")
    private String code;
    @Lob
    @Column(name = "CodeDisplayName")
    private String codeDisplayName;
    @JoinColumn(name = "Question_ID", referencedColumnName = "Question_ID")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "CodeSystem_ID", referencedColumnName = "CodeSystem_ID")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingQ() {
    }

    public SemanticMappingQ(Integer semanticMappingQID) {
        this.semanticMappingQID = semanticMappingQID;
    }

    public Integer getSemanticMappingQID() {
        return semanticMappingQID;
    }

    public void setSemanticMappingQID(Integer semanticMappingQID) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.SemanticMappingQ[semanticMappingQID=" + semanticMappingQID + "]";
    }

}
