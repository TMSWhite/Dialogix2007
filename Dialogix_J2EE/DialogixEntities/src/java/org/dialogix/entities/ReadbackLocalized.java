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
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "readback_localized")
public class ReadbackLocalized implements Serializable {
    @TableGenerator(name="ReadbackLocalized_Gen", pkColumnValue="ReadbackLocalized", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ReadbackLocalized_Gen")
    @Column(name = "readback_localized_id", nullable = false)
    private Long readbackLocalizedID;
    @Column(name = "LanguageCode", nullable = false, length=2)
    private String languageCode;
    @Lob
    @Column(name = "ReadbackString")
    private String readbackString;
    @JoinColumn(name = "readback_id", referencedColumnName = "readback_id")
    @ManyToOne
    private Readback readbackID;

    public ReadbackLocalized() {
    }

    public ReadbackLocalized(Long readbackLocalizedID) {
        this.readbackLocalizedID = readbackLocalizedID;
    }

    public ReadbackLocalized(Long readbackLocalizedID, String languageCode) {
        this.readbackLocalizedID = readbackLocalizedID;
        this.languageCode = languageCode;
    }

    public Long getReadbackLocalizedID() {
        return readbackLocalizedID;
    }

    public void setReadbackLocalizedID(Long readbackLocalizedID) {
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
