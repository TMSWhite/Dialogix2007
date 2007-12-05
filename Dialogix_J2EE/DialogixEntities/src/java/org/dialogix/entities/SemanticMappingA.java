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
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "semantic_mapping_a")
@NamedQueries({@NamedQuery(name = "SemanticMappingA.findBySemanticMappingAID", query = "SELECT s FROM SemanticMappingA s WHERE s.semanticMappingAID = :semanticMappingAID")})
public class SemanticMappingA implements Serializable {
    @TableGenerator(name="SemanticMappingA_Generator", pkColumnValue="SemanticMappingA", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="SemanticMappingA_Generator")
    @Column(name = "SemanticMapping_A_ID", nullable = false)
    private BigInteger semanticMappingAID;
    @Lob
    @Column(name = "Code")
    private String code;
    @Lob
    @Column(name = "CodeDisplayName")
    private String codeDisplayName;
    @JoinColumn(name = "Answer_ID", referencedColumnName = "Answer_ID")
    @ManyToOne
    private Answer answerID;
    @JoinColumn(name = "CodeSystem_ID", referencedColumnName = "CodeSystem_ID")
    @ManyToOne
    private CodeSystem codeSystemID;

    public SemanticMappingA() {
    }

    public SemanticMappingA(BigInteger semanticMappingAID) {
        this.semanticMappingAID = semanticMappingAID;
    }

    public BigInteger getSemanticMappingAID() {
        return semanticMappingAID;
    }

    public void setSemanticMappingAID(BigInteger semanticMappingAID) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
