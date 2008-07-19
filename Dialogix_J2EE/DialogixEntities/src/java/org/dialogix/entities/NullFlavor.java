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
@Table(name = "null_flavor")
public class NullFlavor implements Serializable {

    @TableGenerator(name = "NullFlavor_gen", pkColumnValue = "null_flavor", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "NullFlavor_gen")
    @Column(name = "null_flavor_id", nullable = false)
    private Integer nullFlavorId;
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Column(name = "null_flavor", nullable = false)
    private String nullFlavor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nullFlavorId")
    private Collection<ItemUsage> itemUsageCollection1;

    public NullFlavor() {
    }

    public NullFlavor(Integer nullFlavorId) {
        this.nullFlavorId = nullFlavorId;
    }

    public NullFlavor(Integer nullFlavorId,
                      String displayName,
                      String nullFlavor) {
        this.nullFlavorId = nullFlavorId;
        this.displayName = displayName;
        this.nullFlavor = nullFlavor;
    }

    public Integer getNullFlavorId() {
        return nullFlavorId;
    }

    public void setNullFlavorId(Integer nullFlavorId) {
        this.nullFlavorId = nullFlavorId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNullFlavor() {
        return nullFlavor;
    }

    public void setNullFlavor(String nullFlavor) {
        this.nullFlavor = nullFlavor;
    }

    public Collection<ItemUsage> getItemUsageCollection1() {
        return itemUsageCollection1;
    }

    public void setItemUsageCollection1(
        Collection<ItemUsage> itemUsageCollection1) {
        this.itemUsageCollection1 = itemUsageCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nullFlavorId != null ? nullFlavorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NullFlavor)) {
            return false;
        }
        NullFlavor other = (NullFlavor) object;
        if ((this.nullFlavorId == null && other.nullFlavorId != null) || (this.nullFlavorId != null && !this.nullFlavorId.equals(other.nullFlavorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.NullFlavor[nullFlavorId=" + nullFlavorId + "]";
    }
}
