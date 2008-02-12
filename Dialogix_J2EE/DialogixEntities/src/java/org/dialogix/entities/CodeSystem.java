/*
 * CodeSystem.java
 * 
 * Created on Nov 2, 2007, 11:15:07 AM
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
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "code_systems")
public class CodeSystem implements Serializable {
    @TableGenerator(name="code_system_gen", pkColumnValue="code_system", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=10)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="code_system_gen")
    @Column(name = "id", nullable = false)
    private Integer codeSystemID;
    @Column(name = "name")
    private String codeSystemName;
    @Column(name = "code_system_oid")
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
