/*
 * DisplayType.java
 * 
 * Created on Oct 29, 2007, 12:40:55 PM
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "display_type")
@NamedQueries({@NamedQuery(name = "DisplayType.findByDisplayTypeID", query = "SELECT d FROM DisplayType d WHERE d.displayTypeID = :displayTypeID"), @NamedQuery(name = "DisplayType.findByDisplayType", query = "SELECT d FROM DisplayType d WHERE d.displayType = :displayType"), @NamedQuery(name = "DisplayType.findByHasAnswerList", query = "SELECT d FROM DisplayType d WHERE d.hasAnswerList = :hasAnswerList"), @NamedQuery(name = "DisplayType.findBySPSSformat", query = "SELECT d FROM DisplayType d WHERE d.sPSSformat = :sPSSformat"), @NamedQuery(name = "DisplayType.findBySASinformat", query = "SELECT d FROM DisplayType d WHERE d.sASinformat = :sASinformat"), @NamedQuery(name = "DisplayType.findBySASformat", query = "SELECT d FROM DisplayType d WHERE d.sASformat = :sASformat"), @NamedQuery(name = "DisplayType.findBySPSSlevel", query = "SELECT d FROM DisplayType d WHERE d.sPSSlevel = :sPSSlevel"), @NamedQuery(name = "DisplayType.findByLOINCscale", query = "SELECT d FROM DisplayType d WHERE d.lOINCscale = :lOINCscale")})
public class DisplayType implements Serializable {
    @Id
    @Column(name = "DisplayType_ID", nullable = false)
    private Integer displayTypeID;
    @Column(name = "DisplayType", nullable = false)
    private String displayType;
    @Column(name = "HasAnswerList")
    private Boolean hasAnswerList;
    @Column(name = "SPSSformat", nullable = false)
    private String sPSSformat;
    @Column(name = "SASinformat", nullable = false)
    private String sASinformat;
    @Column(name = "SASformat", nullable = false)
    private String sASformat;
    @Column(name = "SPSSlevel", nullable = false)
    private String sPSSlevel;
    @Column(name = "LOINCscale", nullable = false)
    private String lOINCscale;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "displayTypeID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @JoinColumn(name = "DataType_ID", referencedColumnName = "DataType_ID")
    @ManyToOne
    private DataType dataTypeID;

    public DisplayType() {
    }

    public DisplayType(Integer displayTypeID) {
        this.displayTypeID = displayTypeID;
    }

    public DisplayType(Integer displayTypeID, String displayType, String sPSSformat, String sASinformat, String sASformat, String sPSSlevel, String lOINCscale) {
        this.displayTypeID = displayTypeID;
        this.displayType = displayType;
        this.sPSSformat = sPSSformat;
        this.sASinformat = sASinformat;
        this.sASformat = sASformat;
        this.sPSSlevel = sPSSlevel;
        this.lOINCscale = lOINCscale;
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

    public Boolean getHasAnswerList() {
        return hasAnswerList;
    }

    public void setHasAnswerList(Boolean hasAnswerList) {
        this.hasAnswerList = hasAnswerList;
    }

    public String getSPSSformat() {
        return sPSSformat;
    }

    public void setSPSSformat(String sPSSformat) {
        this.sPSSformat = sPSSformat;
    }

    public String getSASinformat() {
        return sASinformat;
    }

    public void setSASinformat(String sASinformat) {
        this.sASinformat = sASinformat;
    }

    public String getSASformat() {
        return sASformat;
    }

    public void setSASformat(String sASformat) {
        this.sASformat = sASformat;
    }

    public String getSPSSlevel() {
        return sPSSlevel;
    }

    public void setSPSSlevel(String sPSSlevel) {
        this.sPSSlevel = sPSSlevel;
    }

    public String getLOINCscale() {
        return lOINCscale;
    }

    public void setLOINCscale(String lOINCscale) {
        this.lOINCscale = lOINCscale;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public DataType getDataTypeID() {
        return dataTypeID;
    }

    public void setDataTypeID(DataType dataTypeID) {
        this.dataTypeID = dataTypeID;
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
        return "org.dialogix.entities.DisplayType[displayTypeID=" + displayTypeID + "]";
    }

}
