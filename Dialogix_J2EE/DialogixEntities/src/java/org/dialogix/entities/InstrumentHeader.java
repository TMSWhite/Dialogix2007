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
@Table(name = "instrument_header")
public class InstrumentHeader implements Serializable {

    @TableGenerator(name = "InstrumentHeader_gen", pkColumnValue = "instrument_header", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "InstrumentHeader_gen")
    @Column(name = "instrument_header_id", nullable = false)
    private Long instrumentHeaderId;
    @Lob
    @Column(name = "header_value", nullable = false)
    private String headerValue;
    @JoinColumn(name = "reserved_word_id", referencedColumnName = "reserved_word_id")
    @ManyToOne
    private ReservedWord reservedWordId;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionId;

    public InstrumentHeader() {
    }

    public InstrumentHeader(Long instrumentHeaderId) {
        this.instrumentHeaderId = instrumentHeaderId;
    }

    public InstrumentHeader(Long instrumentHeaderId,
                            String headerValue) {
        this.instrumentHeaderId = instrumentHeaderId;
        this.headerValue = headerValue;
    }

    public Long getInstrumentHeaderId() {
        return instrumentHeaderId;
    }

    public void setInstrumentHeaderId(Long instrumentHeaderId) {
        this.instrumentHeaderId = instrumentHeaderId;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    public ReservedWord getReservedWordId() {
        return reservedWordId;
    }

    public void setReservedWordId(ReservedWord reservedWordId) {
        this.reservedWordId = reservedWordId;
    }

    public InstrumentVersion getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(InstrumentVersion instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentHeaderId != null ? instrumentHeaderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentHeader)) {
            return false;
        }
        InstrumentHeader other = (InstrumentHeader) object;
        if ((this.instrumentHeaderId == null && other.instrumentHeaderId != null) || (this.instrumentHeaderId != null && !this.instrumentHeaderId.equals(other.instrumentHeaderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentHeader[instrumentHeaderId=" + instrumentHeaderId + "]";
    }
}
