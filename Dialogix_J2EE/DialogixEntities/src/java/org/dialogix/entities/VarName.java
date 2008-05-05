/*
 * VarName.java
 * 
 * Created on Nov 2, 2007, 11:15:08 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;

import java.util.Collection;
import javax.persistence.CascadeType;
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
@Table(name = "var_names")
public class VarName implements Serializable {
    @TableGenerator(name="var_name_gen", pkColumnValue="var_name", table="model_sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="var_name_gen")
    @Column(name = "id", nullable = false)
    private Long varNameID;
    @Column(name = "name", nullable = false)
    private String varName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<ItemUsage> itemUsageCollection;

    public VarName() {
    }

    public VarName(Long varNameID) {
        this.varNameID = varNameID;
    }

    public VarName(Long varNameID, String varName) {
        this.varNameID = varNameID;
        this.varName = varName;
    }

    public Long getVarNameID() {
        return varNameID;
    }

    public void setVarNameID(Long varNameID) {
        this.varNameID = varNameID;
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

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Collection<ItemUsage> getItemUsageCollection() {
        return itemUsageCollection;
    }

    public void setItemUsageCollection(Collection<ItemUsage> itemUsageCollection) {
        this.itemUsageCollection = itemUsageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (varNameID != null ? varNameID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof VarName)) {
            return false;
        }
        VarName other = (VarName) object;
        if ((this.varNameID == null && other.varNameID != null) || (this.varNameID != null && !this.varNameID.equals(other.varNameID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.VarName[varNameID=" + varNameID + "]";
    }

}
