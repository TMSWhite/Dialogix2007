/*
 * Instrument.java
 * 
 * Created on Oct 29, 2007, 12:40:53 PM
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
@Table(name = "instrument")
@NamedQueries({@NamedQuery(name = "Instrument.findByInstrumentID", query = "SELECT i FROM Instrument i WHERE i.instrumentID = :instrumentID"), @NamedQuery(name = "Instrument.findByInstrumentName", query = "SELECT i FROM Instrument i WHERE i.instrumentName = :instrumentName")})
public class Instrument implements Serializable {
    @TableGenerator(name="Instrument_Generator", pkColumnValue="Instrument", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Instrument_Generator")
    @Column(name = "Instrument_ID", nullable = false)
    private Integer instrumentID;
    @Column(name = "InstrumentName", nullable = false)
    private String instrumentName;
    @Lob
    @Column(name = "InstrumentDescription")
    private String instrumentDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentID")
    private Collection<InstrumentSession> instrumentSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentID")
    private Collection<InstrumentVersion> instrumentVersionCollection;

    public Instrument() {
    }

    public Instrument(Integer instrumentID) {
        this.instrumentID = instrumentID;
    }

    public Instrument(Integer instrumentID, String instrumentName) {
        this.instrumentID = instrumentID;
        this.instrumentName = instrumentName;
    }

    public Integer getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(Integer instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentDescription() {
        return instrumentDescription;
    }

    public void setInstrumentDescription(String instrumentDescription) {
        this.instrumentDescription = instrumentDescription;
    }

    public Collection<InstrumentSession> getInstrumentSessionCollection() {
        return instrumentSessionCollection;
    }

    public void setInstrumentSessionCollection(Collection<InstrumentSession> instrumentSessionCollection) {
        this.instrumentSessionCollection = instrumentSessionCollection;
    }

    public Collection<InstrumentVersion> getInstrumentVersionCollection() {
        return instrumentVersionCollection;
    }

    public void setInstrumentVersionCollection(Collection<InstrumentVersion> instrumentVersionCollection) {
        this.instrumentVersionCollection = instrumentVersionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentID != null ? instrumentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instrument)) {
            return false;
        }
        Instrument other = (Instrument) object;
        if ((this.instrumentID == null && other.instrumentID != null) || (this.instrumentID != null && !this.instrumentID.equals(other.instrumentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Instrument[instrumentID=" + instrumentID + "]";
    }

}
