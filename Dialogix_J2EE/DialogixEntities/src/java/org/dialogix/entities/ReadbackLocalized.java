/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "readback_localized")
public class ReadbackLocalized implements Serializable {

    @TableGenerator(name = "ReadbackLocalized_gen", pkColumnValue = "readback_localized", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ReadbackLocalized_gen")
    @Column(name = "readback_localized_id", nullable = false)
    private Long readbackLocalizedId;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @Lob
    @Column(name = "readback_string")
    private String readbackString;
    @JoinColumn(name = "readback_id", referencedColumnName = "readback_id")
    @ManyToOne
    private Readback readbackId;

    public ReadbackLocalized() {
    }

    public ReadbackLocalized(Long readbackLocalizedId) {
        this.readbackLocalizedId = readbackLocalizedId;
    }

    public ReadbackLocalized(Long readbackLocalizedId,
                             String languageCode) {
        this.readbackLocalizedId = readbackLocalizedId;
        this.languageCode = languageCode;
    }

    public Long getReadbackLocalizedId() {
        return readbackLocalizedId;
    }

    public void setReadbackLocalizedId(Long readbackLocalizedId) {
        this.readbackLocalizedId = readbackLocalizedId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getReadbackString() {
        return readbackString;
    }

    public void setReadbackString(String readbackString) {
        this.readbackString = readbackString;
    }

    public Readback getReadbackId() {
        return readbackId;
    }

    public void setReadbackId(Readback readbackId) {
        this.readbackId = readbackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (readbackLocalizedId != null ? readbackLocalizedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReadbackLocalized)) {
            return false;
        }
        ReadbackLocalized other = (ReadbackLocalized) object;
        if ((this.readbackLocalizedId == null && other.readbackLocalizedId != null) || (this.readbackLocalizedId != null && !this.readbackLocalizedId.equals(other.readbackLocalizedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ReadbackLocalized[readbackLocalizedId=" + readbackLocalizedId + "]";
    }
}
