/*
 * LanguageList.java
 * 
 * Created on Nov 2, 2007, 11:15:10 AM
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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "language_lists")
public class LanguageList implements Serializable {
    @TableGenerator(name="language_list_gen", pkColumnValue="language_list", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="language_list_gen")
    @Column(name = "id", nullable = false)
    private Integer languageListID;
    @Lob
    @Column(name = "name", nullable = false)
    private String languageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "languageListID")
    private Collection<InstrumentHash> instrumentHashCollection;

    public LanguageList() {
    }

    public LanguageList(Integer languageListID) {
        this.languageListID = languageListID;
    }

    public LanguageList(Integer languageListID, String languageList) {
        this.languageListID = languageListID;
        this.languageList = languageList;
    }

    public Integer getLanguageListID() {
        return languageListID;
    }

    public void setLanguageListID(Integer languageListID) {
        this.languageListID = languageListID;
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

    public void setInstrumentHashCollection(Collection<InstrumentHash> instrumentHashCollection) {
        this.instrumentHashCollection = instrumentHashCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (languageListID != null ? languageListID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LanguageList)) {
            return false;
        }
        LanguageList other = (LanguageList) object;
        if ((this.languageListID == null && other.languageListID != null) || (this.languageListID != null && !this.languageListID.equals(other.languageListID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.LanguageList[languageListID=" + languageListID + "]";
    }

}
