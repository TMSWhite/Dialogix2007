/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "help_localized")
public class HelpLocalized implements Serializable {

    @TableGenerator(name = "HelpLocalized_gen", pkColumnValue = "help_localized", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "HelpLocalized_gen")
    @Column(name = "help_localized_id", nullable = false)
    private Long helpLocalizedId;
    @Lob
    @Column(name = "help_string")
    private String helpString;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @JoinColumn(name = "help_id", referencedColumnName = "help_id")
    @ManyToOne
    private Help helpId;

    public HelpLocalized() {
    }

    public HelpLocalized(Long helpLocalizedId) {
        this.helpLocalizedId = helpLocalizedId;
    }

    public HelpLocalized(Long helpLocalizedId,
                         String languageCode) {
        this.helpLocalizedId = helpLocalizedId;
        this.languageCode = languageCode;
    }

    public Long getHelpLocalizedId() {
        return helpLocalizedId;
    }

    public void setHelpLocalizedId(Long helpLocalizedId) {
        this.helpLocalizedId = helpLocalizedId;
    }

    public String getHelpString() {
        return helpString;
    }

    public void setHelpString(String helpString) {
        this.helpString = helpString;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Help getHelpId() {
        return helpId;
    }

    public void setHelpId(Help helpId) {
        this.helpId = helpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (helpLocalizedId != null ? helpLocalizedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HelpLocalized)) {
            return false;
        }
        HelpLocalized other = (HelpLocalized) object;
        if ((this.helpLocalizedId == null && other.helpLocalizedId != null) || (this.helpLocalizedId != null && !this.helpLocalizedId.equals(other.helpLocalizedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.HelpLocalized[helpLocalizedId=" + helpLocalizedId + "]";
    }
}
