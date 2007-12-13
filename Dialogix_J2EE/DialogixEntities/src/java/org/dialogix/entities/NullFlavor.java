/*
 * NullFlavor.java
 * 
 * Created on Nov 2, 2007, 11:15:05 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "null_flavor")
public class NullFlavor implements Serializable {
    @Id
    @Column(name = "NullFlavor_ID", nullable = false)
    private Integer nullFlavorID;
    @Column(name = "NullFlavor", nullable = false)
    private String nullFlavor;
    @Column(name = "DisplayName", nullable = false)
    private String displayName;
    @Lob
    @Column(name = "Description")
    private String description;

    public NullFlavor() {
    }

    public NullFlavor(Integer nullFlavorID) {
        this.nullFlavorID = nullFlavorID;
    }

    public NullFlavor(Integer nullFlavorID, String nullFlavor, String displayName) {
        this.nullFlavorID = nullFlavorID;
        this.nullFlavor = nullFlavor;
        this.displayName = displayName;
    }

    public Integer getNullFlavorID() {
        return nullFlavorID;
    }

    public void setNullFlavorID(Integer nullFlavorID) {
        this.nullFlavorID = nullFlavorID;
    }

    public String getNullFlavor() {
        return nullFlavor;
    }

    public void setNullFlavor(String nullFlavor) {
        this.nullFlavor = nullFlavor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nullFlavorID != null ? nullFlavorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NullFlavor)) {
            return false;
        }
        NullFlavor other = (NullFlavor) object;
        if ((this.nullFlavorID == null && other.nullFlavorID != null) || (this.nullFlavorID != null && !this.nullFlavorID.equals(other.nullFlavorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.NullFlavor[nullFlavorID=" + nullFlavorID + "]";
    }

}
