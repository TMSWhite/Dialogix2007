/*
 * Readback.java
 * 
 * Created on Nov 5, 2007, 5:00:24 PM
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
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "readbacks")
public class Readback implements Serializable {
    @TableGenerator(name="readback_gen", pkColumnValue="readback", table="model_sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="readback_gen")
    @Column(name = "id", nullable = false)
    private Long readbackID;
    @OneToMany(mappedBy = "readbackID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "readbackID")
    private Collection<ReadbackLocalized> readbackLocalizedCollection;

    public Readback() {
    }

    public Readback(Long readbackID) {
        this.readbackID = readbackID;
    }

    public Long getReadbackID() {
        return readbackID;
    }

    public void setReadbackID(Long readbackID) {
        this.readbackID = readbackID;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Collection<ReadbackLocalized> getReadbackLocalizedCollection() {
        return readbackLocalizedCollection;
    }

    public void setReadbackLocalizedCollection(Collection<ReadbackLocalized> readbackLocalizedCollection) {
        this.readbackLocalizedCollection = readbackLocalizedCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (readbackID != null ? readbackID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
