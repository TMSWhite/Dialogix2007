/*
 * DisplayType.java
 * 
 * Created on Oct 22, 2007, 4:09:01 PM
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
@Table(name = "displaytype")
@NamedQueries({@NamedQuery(name = "DisplayType.findByDisplayTypeID", query = "SELECT d FROM DisplayType d WHERE d.displayTypeID = :displayTypeID"), @NamedQuery(name = "DisplayType.findByDisplayType", query = "SELECT d FROM DisplayType d WHERE d.displayType = :displayType")})
public class DisplayType implements Serializable {
    @Id
    @Column(name = "DisplayType_ID", nullable = false)
    private Integer displayTypeID;
    @Column(name = "DisplayType", nullable = false)
    private String displayType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "displayTypeID")
    private Collection<InstrumentContent> instrumentContentCollection;

    public DisplayType() {
    }

    public DisplayType(Integer displayTypeID) {
        this.displayTypeID = displayTypeID;
    }

    public DisplayType(Integer displayTypeID, String displayType) {
        this.displayTypeID = displayTypeID;
        this.displayType = displayType;
    }

    public Integer getDisplayTypeID() {
        return displayTypeID;
    }

    public void setDisplayTypeID(Integer displayTypeID) {
        this.displayTypeID = displayTypeID;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (displayTypeID != null ? displayTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisplayType)) {
            return false;
        }
        DisplayType other = (DisplayType) object;
        if ((this.displayTypeID == null && other.displayTypeID != null) || (this.displayTypeID != null && !this.displayTypeID.equals(other.displayTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.DisplayType[displayTypeID=" + displayTypeID + "]";
    }

}
