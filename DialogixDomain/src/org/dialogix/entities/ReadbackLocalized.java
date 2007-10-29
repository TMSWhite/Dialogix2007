/*
 * ReadbackLocalized.java
 * 
 * Created on Oct 26, 2007, 5:17:08 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "readback_localized")
@NamedQueries({@NamedQuery(name = "ReadbackLocalized.findByReadbackLocalizedID", query = "SELECT r FROM ReadbackLocalized r WHERE r.readbackLocalizedID = :readbackLocalizedID"), @NamedQuery(name = "ReadbackLocalized.findByReadbackID", query = "SELECT r FROM ReadbackLocalized r WHERE r.readbackID = :readbackID"), @NamedQuery(name = "ReadbackLocalized.findByLanguageCode", query = "SELECT r FROM ReadbackLocalized r WHERE r.languageCode = :languageCode")})
public class ReadbackLocalized implements Serializable {
    @TableGenerator(name="ReadbackLocalized_Generator", pkColumnValue="ReadbackLocalized", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ReadbackLocalized_Generator")
    @Column(name = "ReadbackLocalized_ID", nullable = false)
    private Integer readbackLocalizedID;
    @Column(name = "Readback_ID", nullable = false)
    private int readbackID;
    @Column(name = "LanguageCode", nullable = false)
    private String languageCode;
    @Lob
    @Column(name = "ReadbackString")
    private String readbackString;

    public ReadbackLocalized() {
    }

    public ReadbackLocalized(Integer readbackLocalizedID) {
        this.readbackLocalizedID = readbackLocalizedID;
    }

    public ReadbackLocalized(Integer readbackLocalizedID, int readbackID, String languageCode) {
        this.readbackLocalizedID = readbackLocalizedID;
        this.readbackID = readbackID;
        this.languageCode = languageCode;
    }

    public Integer getReadbackLocalizedID() {
        return readbackLocalizedID;
    }

    public void setReadbackLocalizedID(Integer readbackLocalizedID) {
        this.readbackLocalizedID = readbackLocalizedID;
    }

    public int getReadbackID() {
        return readbackID;
    }

    public void setReadbackID(int readbackID) {
        this.readbackID = readbackID;
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
