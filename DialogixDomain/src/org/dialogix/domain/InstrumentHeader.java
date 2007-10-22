/*
 * InstrumentHeader.java
 * 
 * Created on Oct 22, 2007, 4:09:06 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrumentheader")
@NamedQueries({@NamedQuery(name = "InstrumentHeader.findByInstrumentHeaderID", query = "SELECT i FROM InstrumentHeader i WHERE i.instrumentHeaderID = :instrumentHeaderID")})
public class InstrumentHeader implements Serializable {
    @Id
    @Column(name = "InstrumentHeader_ID", nullable = false)
    private Integer instrumentHeaderID;
    @Lob
    @Column(name = "Value", nullable = false)
    private String value;
    @JoinColumn(name = "InstrumentVersion_ID", referencedColumnName = "InstrumentVersion_ID")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "ReservedWord_ID", referencedColumnName = "ReservedWord_ID")
    @ManyToOne
    private ReservedWord reservedWordID;

    public InstrumentHeader() {
    }

    public InstrumentHeader(Integer instrumentHeaderID) {
        this.instrumentHeaderID = instrumentHeaderID;
    }

    public InstrumentHeader(Integer instrumentHeaderID, String value) {
        this.instrumentHeaderID = instrumentHeaderID;
        this.value = value;
    }

    public Integer getInstrumentHeaderID() {
        return instrumentHeaderID;
    }

    public void setInstrumentHeaderID(Integer instrumentHeaderID) {
        this.instrumentHeaderID = instrumentHeaderID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public InstrumentVersion getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(InstrumentVersion instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public ReservedWord getReservedWordID() {
        return reservedWordID;
    }

    public void setReservedWordID(ReservedWord reservedWordID) {
        this.reservedWordID = reservedWordID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentHeaderID != null ? instrumentHeaderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentHeader)) {
            return false;
        }
        InstrumentHeader other = (InstrumentHeader) object;
        if ((this.instrumentHeaderID == null && other.instrumentHeaderID != null) || (this.instrumentHeaderID != null && !this.instrumentHeaderID.equals(other.instrumentHeaderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.InstrumentHeader[instrumentHeaderID=" + instrumentHeaderID + "]";
    }

}
