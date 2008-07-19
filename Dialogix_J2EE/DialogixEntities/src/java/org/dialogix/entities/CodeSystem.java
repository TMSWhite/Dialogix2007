/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "code_system")
public class CodeSystem implements Serializable {

    @TableGenerator(name = "CodeSystem_gen", pkColumnValue = "code_system", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CodeSystem_gen")
    @Column(name = "code_system_id", nullable = false)
    private Integer codeSystemId;
    @Column(name = "code_system_name", nullable = false)
    private String codeSystemName;
    @Column(name = "code_system_oid")
    private String codeSystemOid;
    @OneToMany(mappedBy = "codeSystemId")
    private Collection<SemanticMappingA> semanticMappingACollection;
    @OneToMany(mappedBy = "codeSystemId")
    private Collection<SemanticMappingQA> semanticMappingQACollection;
    @OneToMany(mappedBy = "codeSystemId")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;
    @OneToMany(mappedBy = "codeSystemId")
    private Collection<SemanticMappingQ> semanticMappingQCollection;

    public CodeSystem() {
    }

    public CodeSystem(Integer codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    public CodeSystem(Integer codeSystemId,
                      String codeSystemName) {
        this.codeSystemId = codeSystemId;
        this.codeSystemName = codeSystemName;
    }

    public Integer getCodeSystemId() {
        return codeSystemId;
    }

    public void setCodeSystemId(Integer codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }

    public String getCodeSystemOid() {
        return codeSystemOid;
    }

    public void setCodeSystemOid(String codeSystemOid) {
        this.codeSystemOid = codeSystemOid;
    }

    public Collection<SemanticMappingA> getSemanticMappingACollection() {
        return semanticMappingACollection;
    }

    public void setSemanticMappingACollection(
        Collection<SemanticMappingA> semanticMappingACollection) {
        this.semanticMappingACollection = semanticMappingACollection;
    }

    public Collection<SemanticMappingQA> getSemanticMappingQACollection() {
        return semanticMappingQACollection;
    }

    public void setSemanticMappingQACollection(
        Collection<SemanticMappingQA> semanticMappingQACollection) {
        this.semanticMappingQACollection = semanticMappingQACollection;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(
        Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    public Collection<SemanticMappingQ> getSemanticMappingQCollection() {
        return semanticMappingQCollection;
    }

    public void setSemanticMappingQCollection(
        Collection<SemanticMappingQ> semanticMappingQCollection) {
        this.semanticMappingQCollection = semanticMappingQCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeSystemId != null ? codeSystemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodeSystem)) {
            return false;
        }
        CodeSystem other = (CodeSystem) object;
        if ((this.codeSystemId == null && other.codeSystemId != null) || (this.codeSystemId != null && !this.codeSystemId.equals(other.codeSystemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.CodeSystem[codeSystemId=" + codeSystemId + "]";
    }
}
