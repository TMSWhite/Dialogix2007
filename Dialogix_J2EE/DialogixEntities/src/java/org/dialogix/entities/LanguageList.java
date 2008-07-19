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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "language_list")
public class LanguageList implements Serializable {

    @TableGenerator(name = "LanguageList_gen", pkColumnValue = "language_list", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LanguageList_gen")
    @Column(name = "language_list_id", nullable = false)
    private Integer languageListId;
    @Lob
    @Column(name = "language_list", nullable = false)
    private String languageList;
    @OneToMany(mappedBy = "languageListId")
    private Collection<InstrumentHash> instrumentHashCollection;

    public LanguageList() {
    }

    public LanguageList(Integer languageListId) {
        this.languageListId = languageListId;
    }

    public LanguageList(Integer languageListId,
                        String languageList) {
        this.languageListId = languageListId;
        this.languageList = languageList;
    }

    public Integer getLanguageListId() {
        return languageListId;
    }

    public void setLanguageListId(Integer languageListId) {
        this.languageListId = languageListId;
    }

    public String getLanguageList() {
        return languageList;
    }

    public void setLanguageList(String languageList) {
        this.languageList = languageList;
    }

    public Collection<InstrumentHash> getInstrumentHashCollection() {
        return instrumentHashCollection;
    }

    public void setInstrumentHashCollection(
        Collection<InstrumentHash> instrumentHashCollection) {
        this.instrumentHashCollection = instrumentHashCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (languageListId != null ? languageListId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LanguageList)) {
            return false;
        }
        LanguageList other = (LanguageList) object;
        if ((this.languageListId == null && other.languageListId != null) || (this.languageListId != null && !this.languageListId.equals(other.languageListId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.LanguageList[languageListId=" + languageListId + "]";
    }
}
