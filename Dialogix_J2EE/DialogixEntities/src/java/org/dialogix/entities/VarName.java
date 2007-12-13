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
import java.math.BigInteger;
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
@Table(name = "var_name")
public class VarName implements Serializable {
    @TableGenerator(name="VarName_Gen", pkColumnValue="VarName", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=500)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="VarName_Gen")
    @Column(name = "VarName_ID", nullable = false)
    private BigInteger varNameID;
    @Column(name = "VarName", nullable = false)
    private String varName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<PageUsageEvent> pageUsageEventCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<ItemUsage> itemUsageCollection;

    public VarName() {
    }

    public VarName(BigInteger varNameID) {
        this.varNameID = varNameID;
    }

    public VarName(BigInteger varNameID, String varName) {
        this.varNameID = varNameID;
        this.varName = varName;
    }

    public BigInteger getVarNameID() {
        return varNameID;
    }

    public void setVarNameID(BigInteger varNameID) {
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

    public Collection<PageUsageEvent> getPageUsageEventCollection() {
        return pageUsageEventCollection;
    }

    public void setPageUsageEventCollection(Collection<PageUsageEvent> pageUsageEventCollection) {
        this.pageUsageEventCollection = pageUsageEventCollection;
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
