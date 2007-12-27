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

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "help")
public class Help implements Serializable {
    @TableGenerator(name="Help_Gen", pkColumnValue="Help", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Help_Gen")
    @Column(name = "help_id", nullable = false)
    private Long helpID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "helpID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "helpID")
    private Collection<HelpLocalized> helpLocalizedCollection;

    public Help() {
    }

    public Help(Long helpID) {
        this.helpID = helpID;
    }

    public Long getHelpID() {
        return helpID;
    }

    public void setHelpID(Long helpID) {
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
