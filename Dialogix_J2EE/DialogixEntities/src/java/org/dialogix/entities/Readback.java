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
import java.math.BigInteger;
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
@Table(name = "readback")
public class Readback implements Serializable {
    @TableGenerator(name="Readback_Gen", pkColumnValue="Readback", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Readback_Gen")
    @Column(name = "Readback_ID", nullable = false)
    private BigInteger readbackID;
    @OneToMany(mappedBy = "readbackID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "readbackID")
    private Collection<ReadbackLocalized> readbackLocalizedCollection;

    public Readback() {
    }

    public Readback(BigInteger readbackID) {
        this.readbackID = readbackID;
    }

    public BigInteger getReadbackID() {
        return readbackID;
    }

    public void setReadbackID(BigInteger readbackID) {
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
