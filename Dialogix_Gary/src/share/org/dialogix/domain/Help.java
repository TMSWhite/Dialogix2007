/*
 * Help.java
 * 
 * Created on Oct 22, 2007, 4:08:59 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "help")
@NamedQueries({@NamedQuery(name = "Help.findByHelpID", query = "SELECT h FROM Help h WHERE h.helpID = :helpID")})
public class Help implements Serializable {
    @Id
    @Column(name = "Help_ID", nullable = false)
    private Integer helpID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "helpID")
    private Collection<HelpLocalized> helpLocalizedCollection;

    public Help() {
    }

    public Help(Integer helpID) {
        this.helpID = helpID;
    }

    public Integer getHelpID() {
        return helpID;
    }

    public void setHelpID(Integer helpID) {
        this.helpID = helpID;
    }

    public Collection<HelpLocalized> getHelpLocalizedCollection() {
        return helpLocalizedCollection;
    }

    public void setHelpLocalizedCollection(Collection<HelpLocalized> helpLocalizedCollection) {
        this.helpLocalizedCollection = helpLocalizedCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (helpID != null ? helpID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Help)) {
            return false;
        }
        Help other = (Help) object;
        if ((this.helpID == null && other.helpID != null) || (this.helpID != null && !this.helpID.equals(other.helpID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.Help[helpID=" + helpID + "]";
    }

}
