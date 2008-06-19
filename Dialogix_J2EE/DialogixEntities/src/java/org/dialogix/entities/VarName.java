/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
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
@Table(name = "var_name")
public class VarName implements Serializable {

    @TableGenerator(name = "VarName_gen", pkColumnValue = "var_name", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "VarName_gen")
    @Column(name = "var_name_id", nullable = false)
    private Long varNameId;
    @Column(name = "var_name", nullable = false)
    private String varName;
    @OneToMany(mappedBy = "varNameId")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameId")
    private Collection<DataElement> dataElementCollection;

    public VarName() {
    }

    public VarName(Long varNameId) {
        this.varNameId = varNameId;
    }

    public VarName(Long varNameId,
                   String varName) {
        this.varNameId = varNameId;
        this.varName = varName;
    }

    public Long getVarNameId() {
        return varNameId;
    }

    public void setVarNameId(Long varNameId) {
        this.varNameId = varNameId;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(
        Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }

    public void setDataElementCollection(
        Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (varNameId != null ? varNameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VarName)) {
            return false;
        }
        VarName other = (VarName) object;
        if ((this.varNameId == null && other.varNameId != null) || (this.varNameId != null && !this.varNameId.equals(other.varNameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.VarName[varNameId=" + varNameId + "]";
    }
}
