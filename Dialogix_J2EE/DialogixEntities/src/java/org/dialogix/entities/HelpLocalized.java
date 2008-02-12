/*
 * HelpLocalized.java
 * 
 * Created on Nov 2, 2007, 11:15:12 AM
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
@Table(name = "help_localizeds")
public class HelpLocalized implements Serializable {
    @TableGenerator(name="help_localized_gen", pkColumnValue="help_localized", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="help_localized_gen")
    @Column(name = "id", nullable = false)
    private Long helpLocalizedID;
    @Column(name = "language_code", nullable = false, length=2)
    private String languageCode;
    @Lob
    @Column(name = "name")
    private String helpString;
    @JoinColumn(name = "help_id", referencedColumnName="id")
    @ManyToOne
    private Help helpID;

    public HelpLocalized() {
    }

    public HelpLocalized(Long helpLocalizedID) {
        this.helpLocalizedID = helpLocalizedID;
    }

    public HelpLocalized(Long helpLocalizedID, String languageCode) {
        this.helpLocalizedID = helpLocalizedID;
        this.languageCode = languageCode;
    }

    public Long getHelpLocalizedID() {
        return helpLocalizedID;
    }

    public void setHelpLocalizedID(Long helpLocalizedID) {
        this.helpLocalizedID = helpLocalizedID;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getHelpString() {
        return helpString;
    }

    public void setHelpString(String helpString) {
        this.helpString = helpString;
    }

    public Help getHelpID() {
        return helpID;
    }

    public void setHelpID(Help helpID) {
        this.helpID = helpID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (helpLocalizedID != null ? helpLocalizedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HelpLocalized)) {
            return false;
        }
        HelpLocalized other = (HelpLocalized) object;
        if ((this.helpLocalizedID == null && other.helpLocalizedID != null) || (this.helpLocalizedID != null && !this.helpLocalizedID.equals(other.helpLocalizedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.HelpLocalized[helpLocalizedID=" + helpLocalizedID + "]";
    }

}
