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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "help_localized")
@NamedQueries({@NamedQuery(name = "HelpLocalized.findByHelpLocalizedID", query = "SELECT h FROM HelpLocalized h WHERE h.helpLocalizedID = :helpLocalizedID"), @NamedQuery(name = "HelpLocalized.findByLanguageCode", query = "SELECT h FROM HelpLocalized h WHERE h.languageCode = :languageCode")})
public class HelpLocalized implements Serializable {
    @TableGenerator(name="HelpLocalized_Generator", pkColumnValue="HelpLocalized", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="HelpLocalized_Generator")
    @Column(name = "HelpLocalized_ID", nullable = false)
    private Integer helpLocalizedID;
    @Column(name = "LanguageCode", nullable = false)
    private String languageCode;
    @Lob
    @Column(name = "HelpString")
    private String helpString;
    @JoinColumn(name = "Help_ID", referencedColumnName = "Help_ID")
    @ManyToOne
    private Help helpID;

    public HelpLocalized() {
    }

    public HelpLocalized(Integer helpLocalizedID) {
        this.helpLocalizedID = helpLocalizedID;
    }

    public HelpLocalized(Integer helpLocalizedID, String languageCode) {
        this.helpLocalizedID = helpLocalizedID;
        this.languageCode = languageCode;
    }

    public Integer getHelpLocalizedID() {
        return helpLocalizedID;
    }

    public void setHelpLocalizedID(Integer helpLocalizedID) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
