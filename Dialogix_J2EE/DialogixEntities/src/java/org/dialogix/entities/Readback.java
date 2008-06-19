/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "readback")
public class Readback implements Serializable {

    @TableGenerator(name = "Readback_gen", pkColumnValue = "readback", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Readback_gen")
    @Column(name = "readback_id", nullable = false)
    private Long readbackId;
    @OneToMany(mappedBy = "readbackId")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(mappedBy = "readbackId")
    private Collection<ReadbackLocalized> readbackLocalizedCollection;

    public Readback() {
    }

    public Readback(Long readbackId) {
        this.readbackId = readbackId;
    }

    public Long getReadbackId() {
        return readbackId;
    }

    public void setReadbackId(Long readbackId) {
        this.readbackId = readbackId;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(
        Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Collection<ReadbackLocalized> getReadbackLocalizedCollection() {
        return readbackLocalizedCollection;
    }

    public void setReadbackLocalizedCollection(
        Collection<ReadbackLocalized> readbackLocalizedCollection) {
        this.readbackLocalizedCollection = readbackLocalizedCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (readbackId != null ? readbackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Readback)) {
            return false;
        }
        Readback other = (Readback) object;
        if ((this.readbackId == null && other.readbackId != null) || (this.readbackId != null && !this.readbackId.equals(other.readbackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Readback[readbackId=" + readbackId + "]";
    }
}
