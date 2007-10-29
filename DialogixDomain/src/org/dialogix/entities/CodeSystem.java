/*
 * CodeSystem.java
 * 
 * Created on Oct 29, 2007, 12:40:49 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "code_system")
@NamedQueries({@NamedQuery(name = "CodeSystem.findByCodeSystemID", query = "SELECT c FROM CodeSystem c WHERE c.codeSystemID = :codeSystemID"), @NamedQuery(name = "CodeSystem.findByCodeSystemName", query = "SELECT c FROM CodeSystem c WHERE c.codeSystemName = :codeSystemName"), @NamedQuery(name = "CodeSystem.findByCodeSystemOID", query = "SELECT c FROM CodeSystem c WHERE c.codeSystemOID = :codeSystemOID")})
public class CodeSystem implements Serializable {
    @TableGenerator(name="CodeSystem_Generator", pkColumnValue="CodeSystem", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="CodeSystem_Generator")
    @Column(name = "CodeSystem_ID", nullable = false)
    private Integer codeSystemID;
    @Column(name = "CodeSystemName")
    private String codeSystemName;
    @Column(name = "CodeSystemOID")
    private String codeSystemOID;
    @OneToMany(mappedBy = "codeSystemID")
    private Collection<SemanticMappingA> semanticMappingACollection;
    @OneToMany(mappedBy = "codeSystemID")
    private Collection<SemanticMappingQ> semanticMappingQCollection;
    @OneToMany(mappedBy = "codeSystemID")
    private Collection<SemanticMappingQA> semanticMappingQACollection;
    @OneToMany(mappedBy = "codeSystemID")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;

    public CodeSystem() {
    }

    public CodeSystem(Integer codeSystemID) {
        this.codeSystemID = codeSystemID;
    }

    public Integer getCodeSystemID() {
        return codeSystemID;
    }

    public void setCodeSystemID(Integer codeSystemID) {
        this.codeSystemID = codeSystemID;
    }

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }

    public String getCodeSystemOID() {
        return codeSystemOID;
    }

    public void setCodeSystemOID(String codeSystemOID) {
        this.codeSystemOID = codeSystemOID;
    }

    public Collection<SemanticMappingA> getSemanticMappingACollection() {
        return semanticMappingACollection;
    }

    public void setSemanticMappingACollection(Collection<SemanticMappingA> semanticMappingACollection) {
        this.semanticMappingACollection = semanticMappingACollection;
    }

    public Collection<SemanticMappingQ> getSemanticMappingQCollection() {
        return semanticMappingQCollection;
    }

    public void setSemanticMappingQCollection(Collection<SemanticMappingQ> semanticMappingQCollection) {
        this.semanticMappingQCollection = semanticMappingQCollection;
    }

    public Collection<SemanticMappingQA> getSemanticMappingQACollection() {
        return semanticMappingQACollection;
    }

    public void setSemanticMappingQACollection(Collection<SemanticMappingQA> semanticMappingQACollection) {
        this.semanticMappingQACollection = semanticMappingQACollection;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeSystemID != null ? codeSystemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodeSystem)) {
            return false;
        }
        CodeSystem other = (CodeSystem) object;
        if ((this.codeSystemID == null && other.codeSystemID != null) || (this.codeSystemID != null && !this.codeSystemID.equals(other.codeSystemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.CodeSystem[codeSystemID=" + codeSystemID + "]";
    }

}
