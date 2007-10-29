/*
 * Help.java
 * 
 * Created on Oct 29, 2007, 12:40:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "help")
@NamedQueries({@NamedQuery(name = "Help.findByHelpID", query = "SELECT h FROM Help h WHERE h.helpID = :helpID")})
public class Help implements Serializable {
    @TableGenerator(name="Help_Generator", pkColumnValue="Help", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Help_Generator")
    @Column(name = "Help_ID", nullable = false)
    private Integer helpID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "helpID")
    private Collection<InstrumentContent> instrumentContentCollection;
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

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
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
        return "org.dialogix.entities.Help[helpID=" + helpID + "]";
    }

}
