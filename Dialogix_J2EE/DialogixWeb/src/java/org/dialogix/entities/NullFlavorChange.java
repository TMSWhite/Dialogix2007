/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "null_flavor_change")
public class NullFlavorChange implements Serializable {

    @TableGenerator(name = "NullFlavorChange_gen", pkColumnValue = "null_flavor_change", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "NullFlavorChange_gen")
    @Column(name = "null_flavor_change_id", nullable = false)
    private Integer nullFlavorChangeId;
    @Column(name = "null_flavor_change_code", nullable = false)
    private String nullFlavorChangeCode;
    @Column(name = "null_flavor_change_string", nullable = false)
    private String nullFlavorChangeString;

    public NullFlavorChange() {
    }

    public NullFlavorChange(Integer nullFlavorChangeId) {
        this.nullFlavorChangeId = nullFlavorChangeId;
    }

    public NullFlavorChange(Integer nullFlavorChangeId,
                            String nullFlavorChangeCode,
                            String nullFlavorChangeString) {
        this.nullFlavorChangeId = nullFlavorChangeId;
        this.nullFlavorChangeCode = nullFlavorChangeCode;
        this.nullFlavorChangeString = nullFlavorChangeString;
    }

    public Integer getNullFlavorChangeId() {
        return nullFlavorChangeId;
    }

    public void setNullFlavorChangeId(Integer nullFlavorChangeId) {
        this.nullFlavorChangeId = nullFlavorChangeId;
    }

    public String getNullFlavorChangeCode() {
        return nullFlavorChangeCode;
    }

    public void setNullFlavorChangeCode(String nullFlavorChangeCode) {
        this.nullFlavorChangeCode = nullFlavorChangeCode;
    }

    public String getNullFlavorChangeString() {
        return nullFlavorChangeString;
    }

    public void setNullFlavorChangeString(String nullFlavorChangeString) {
        this.nullFlavorChangeString = nullFlavorChangeString;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nullFlavorChangeId != null ? nullFlavorChangeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NullFlavorChange)) {
            return false;
        }
        NullFlavorChange other = (NullFlavorChange) object;
        if ((this.nullFlavorChangeId == null && other.nullFlavorChangeId != null) || (this.nullFlavorChangeId != null && !this.nullFlavorChangeId.equals(other.nullFlavorChangeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.NullFlavorChange[nullFlavorChangeId=" + nullFlavorChangeId + "]";
    }
}
