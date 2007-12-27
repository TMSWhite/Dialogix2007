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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_header")
public class InstrumentHeader implements Serializable {
    @TableGenerator(name="InstrumentHeader_Gen", pkColumnValue="InstrumentHeader", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="InstrumentHeader_Gen")
    @Column(name = "instrument_header_id", nullable = false)
    private Long instrumentHeaderID;
    @Lob
    @Column(name = "HeaderValue", nullable = false)
    private String headerValue;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "reserved_word_id", referencedColumnName = "reserved_word_id")
    @ManyToOne
    private ReservedWord reservedWordID;

    public InstrumentHeader() {
    }

    public InstrumentHeader(Long instrumentHeaderID) {
        this.instrumentHeaderID = instrumentHeaderID;
    }

    public InstrumentHeader(Long instrumentHeaderID, String headerValue) {
        this.instrumentHeaderID = instrumentHeaderID;
        this.headerValue = headerValue;
    }

    public Long getInstrumentHeaderID() {
        return instrumentHeaderID;
    }

    public void setInstrumentHeaderID(Long instrumentHeaderID) {
        this.instrumentHeaderID = instrumentHeaderID;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
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
