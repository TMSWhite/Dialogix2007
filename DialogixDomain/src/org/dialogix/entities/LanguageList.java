/*
 * LanguageList.java
 * 
 * Created on Oct 29, 2007, 12:40:54 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "language_list")
@NamedQueries({@NamedQuery(name = "LanguageList.findByLanguageListID", query = "SELECT l FROM LanguageList l WHERE l.languageListID = :languageListID")})
public class LanguageList implements Serializable {
    @TableGenerator(name="LanguageList_Generator", pkColumnValue="LanguageList", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="LanguageList_Generator")
    @Column(name = "LanguageList_ID", nullable = false)
    private Integer languageListID;
    @Lob
    @Column(name = "LanguageList", nullable = false)
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
