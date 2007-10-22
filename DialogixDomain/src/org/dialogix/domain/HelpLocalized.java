/*
 * HelpLocalized.java
 * 
 * Created on Oct 22, 2007, 4:09:04 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "helplocalized")
@NamedQueries({@NamedQuery(name = "HelpLocalized.findByHelpLocalizedID", query = "SELECT h FROM HelpLocalized h WHERE h.helpLocalizedID = :helpLocalizedID"), @NamedQuery(name = "HelpLocalized.findByLanguageID", query = "SELECT h FROM HelpLocalized h WHERE h.languageID = :languageID")})
public class HelpLocalized implements Serializable {
    @Id
    @Column(name = "HelpLocalized_ID", nullable = false)
    private Integer helpLocalizedID;
    @Column(name = "Language_ID", nullable = false)
    private int languageID;
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

    public HelpLocalized(Integer helpLocalizedID, int languageID) {
        this.helpLocalizedID = helpLocalizedID;
        this.languageID = languageID;
    }

    public Integer getHelpLocalizedID() {
        return helpLocalizedID;
    }

    public void setHelpLocalizedID(Integer helpLocalizedID) {
        this.helpLocalizedID = helpLocalizedID;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
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
        return "org.dialogix.domain.HelpLocalized[helpLocalizedID=" + helpLocalizedID + "]";
    }

}
