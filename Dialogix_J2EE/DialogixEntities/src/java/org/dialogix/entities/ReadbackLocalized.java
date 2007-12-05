/*
 * ReadbackLocalized.java
 * 
 * Created on Nov 2, 2007, 11:15:06 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "readback_localized")
@NamedQueries({@NamedQuery(name = "ReadbackLocalized.findByReadbackLocalizedID", query = "SELECT r FROM ReadbackLocalized r WHERE r.readbackLocalizedID = :readbackLocalizedID"), @NamedQuery(name = "ReadbackLocalized.findByLanguageCode", query = "SELECT r FROM ReadbackLocalized r WHERE r.languageCode = :languageCode")})
public class ReadbackLocalized implements Serializable {
    @TableGenerator(name="ReadbackLocalized_Generator", pkColumnValue="ReadbackLocalized", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ReadbackLocalized_Generator")
    @Column(name = "ReadbackLocalized_ID", nullable = false)
    private Integer readbackLocalizedID;
    @Column(name = "LanguageCode", nullable = false)
    private String languageCode;
    @Lob
    @Column(name = "ReadbackString")
    private String readbackString;
    @JoinColumn(name = "Readback_ID", referencedColumnName = "Readback_ID")
    @ManyToOne
    private Readback readbackID;

    public ReadbackLocalized() {
    }

    public ReadbackLocalized(Integer readbackLocalizedID) {
        this.readbackLocalizedID = readbackLocalizedID;
    }

    public ReadbackLocalized(Integer readbackLocalizedID, String languageCode) {
        this.readbackLocalizedID = readbackLocalizedID;
        this.languageCode = languageCode;
    }

    public Integer getReadbackLocalizedID() {
        return readbackLocalizedID;
    }

    public void setReadbackLocalizedID(Integer readbackLocalizedID) {
        this.readbackLocalizedID = readbackLocalizedID;
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

    public Readback getReadbackID() {
        return readbackID;
    }

    public void setReadbackID(Readback readbackID) {
        this.readbackID = readbackID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (readbackLocalizedID != null ? readbackLocalizedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReadbackLocalized)) {
            return false;
        }
        ReadbackLocalized other = (ReadbackLocalized) object;
        if ((this.readbackLocalizedID == null && other.readbackLocalizedID != null) || (this.readbackLocalizedID != null && !this.readbackLocalizedID.equals(other.readbackLocalizedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ReadbackLocalized[readbackLocalizedID=" + readbackLocalizedID + "]";
    }

}
