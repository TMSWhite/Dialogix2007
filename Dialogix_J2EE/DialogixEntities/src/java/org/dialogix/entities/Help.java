/*
 * Help.java
 * 
 * Created on Nov 2, 2007, 11:15:05 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author Coevtmw
 */
@Entity
@Table(name = "help")
@NamedQueries({@NamedQuery(name = "Help.findByHelpID", query = "SELECT h FROM Help h WHERE h.helpID = :helpID")})
public class Help implements Serializable {
    @TableGenerator(name="Help_Generator", pkColumnValue="Help", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Help_Generator")
    @Column(name = "Help_ID", nullable = false)
    private BigInteger helpID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "helpID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "helpID")
    private Collection<HelpLocalized> helpLocalizedCollection;

    public Help() {
    }

    public Help(BigInteger helpID) {
        this.helpID = helpID;
    }

    public BigInteger getHelpID() {
        return helpID;
    }

    public void setHelpID(BigInteger helpID) {
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
