/*
 * VarName.java
 * 
 * Created on Oct 22, 2007, 4:09:06 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "varname")
@NamedQueries({@NamedQuery(name = "VarName.findByVarNameID", query = "SELECT v FROM VarName v WHERE v.varNameID = :varNameID"), @NamedQuery(name = "VarName.findByVarName", query = "SELECT v FROM VarName v WHERE v.varName = :varName")})
public class VarName implements Serializable {
    @Id
    @Column(name = "VarName_ID", nullable = false)
    private Integer varNameID;
    @Column(name = "VarName", nullable = false)
    private String varName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<ItemUsage> itemUsageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varNameID")
    private Collection<PageUsageEvent> pageUsageEventCollection;

    public VarName() {
    }

    public VarName(Integer varNameID) {
        this.varNameID = varNameID;
    }

    public VarName(Integer varNameID, String varName) {
        this.varNameID = varNameID;
        this.varName = varName;
    }

    public Integer getVarNameID() {
        return varNameID;
    }

    public void setVarNameID(Integer varNameID) {
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

    public Collection<PageUsageEvent> getPageUsageEventCollection() {
        return pageUsageEventCollection;
    }

    public void setPageUsageEventCollection(Collection<PageUsageEvent> pageUsageEventCollection) {
        this.pageUsageEventCollection = pageUsageEventCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (varNameID != null ? varNameID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.VarName[varNameID=" + varNameID + "]";
    }

}
