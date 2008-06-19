/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "help")
public class Help implements Serializable {

    @TableGenerator(name = "Help_gen", pkColumnValue = "help", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Help_gen")
    @Column(name = "help_id", nullable = false)
    private Long helpId;
    @OneToMany(mappedBy = "helpId")
    private Collection<HelpLocalized> helpLocalizedCollection;
    @OneToMany(mappedBy = "helpId")
    private Collection<InstrumentContent> instrumentContentCollection;

    public Help() {
    }

    public Help(Long helpId) {
        this.helpId = helpId;
    }

    public Long getHelpId() {
        return helpId;
    }

    public void setHelpId(Long helpId) {
        this.helpId = helpId;
    }

    public Collection<HelpLocalized> getHelpLocalizedCollection() {
        return helpLocalizedCollection;
    }

    public void setHelpLocalizedCollection(
        Collection<HelpLocalized> helpLocalizedCollection) {
        this.helpLocalizedCollection = helpLocalizedCollection;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(
        Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (helpId != null ? helpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Help)) {
            return false;
        }
        Help other = (Help) object;
        if ((this.helpId == null && other.helpId != null) || (this.helpId != null && !this.helpId.equals(other.helpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Help[helpId=" + helpId + "]";
    }
}
