/*
 * InstrumentHeader.java
 * 
 * Created on Nov 2, 2007, 11:15:06 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_header")
@NamedQueries({@NamedQuery(name = "InstrumentHeader.findByInstrumentHeaderID", query = "SELECT i FROM InstrumentHeader i WHERE i.instrumentHeaderID = :instrumentHeaderID")})
public class InstrumentHeader implements Serializable {
    @TableGenerator(name="InstrumentHeader_Generator", pkColumnValue="InstrumentHeader", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="InstrumentHeader_Generator")
    @Column(name = "InstrumentHeader_ID", nullable = false)
    private BigInteger instrumentHeaderID;
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

    public InstrumentHeader(BigInteger instrumentHeaderID) {
        this.instrumentHeaderID = instrumentHeaderID;
    }

    public InstrumentHeader(BigInteger instrumentHeaderID, String value) {
        this.instrumentHeaderID = instrumentHeaderID;
        this.value = value;
    }

    public BigInteger getInstrumentHeaderID() {
        return instrumentHeaderID;
    }

    public void setInstrumentHeaderID(BigInteger instrumentHeaderID) {
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
        return "org.dialogix.entities.InstrumentHeader[instrumentHeaderID=" + instrumentHeaderID + "]";
    }

}
