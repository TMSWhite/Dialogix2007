/*
 * Readback.java
 * 
 * Created on Oct 29, 2007, 12:40:45 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "readback")
@NamedQueries({@NamedQuery(name = "Readback.findByReadbackID", query = "SELECT r FROM Readback r WHERE r.readbackID = :readbackID")})
public class Readback implements Serializable {
    @TableGenerator(name="Readback_Generator", pkColumnValue="Readback", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Readback_Generator")
    @Column(name = "Readback_ID", nullable = false)
    private Integer readbackID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "readbackID")
    private Collection<ReadbackLocalized> readbackLocalizedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "readbackID")
    private Collection<Item> itemCollection;

    public Readback() {
    }

    public Readback(Integer readbackID) {
        this.readbackID = readbackID;
    }

    public Integer getReadbackID() {
        return readbackID;
    }

    public void setReadbackID(Integer readbackID) {
        this.readbackID = readbackID;
    }

    public Collection<ReadbackLocalized> getReadbackLocalizedCollection() {
        return readbackLocalizedCollection;
    }

    public void setReadbackLocalizedCollection(Collection<ReadbackLocalized> readbackLocalizedCollection) {
        this.readbackLocalizedCollection = readbackLocalizedCollection;
    }

    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (readbackID != null ? readbackID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Readback)) {
            return false;
        }
        Readback other = (Readback) object;
        if ((this.readbackID == null && other.readbackID != null) || (this.readbackID != null && !this.readbackID.equals(other.readbackID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Readback[readbackID=" + readbackID + "]";
    }

}
