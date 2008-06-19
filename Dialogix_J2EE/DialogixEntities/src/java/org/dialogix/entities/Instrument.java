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
@Table(name = "instrument")
public class Instrument implements Serializable {

    @TableGenerator(name = "Instrument_gen", pkColumnValue = "instrument", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Instrument_gen")
    @Column(name = "instrument_id", nullable = false)
    private Long instrumentId;
    @Lob
    @Column(name = "instrument_description")
    private String instrumentDescription;
    @Column(name = "instrument_name", nullable = false)
    private String instrumentName;
    @OneToMany(mappedBy = "instrumentId")
    private Collection<InstrumentVersion> instrumentVersionCollection;

    public Instrument() {
    }

    public Instrument(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Instrument(Long instrumentId,
                      String instrumentName) {
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentDescription() {
        return instrumentDescription;
    }

    public void setInstrumentDescription(String instrumentDescription) {
        this.instrumentDescription = instrumentDescription;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public Collection<InstrumentVersion> getInstrumentVersionCollection() {
        return instrumentVersionCollection;
    }

    public void setInstrumentVersionCollection(
        Collection<InstrumentVersion> instrumentVersionCollection) {
        this.instrumentVersionCollection = instrumentVersionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentId != null ? instrumentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instrument)) {
            return false;
        }
        Instrument other = (Instrument) object;
        if ((this.instrumentId == null && other.instrumentId != null) || (this.instrumentId != null && !this.instrumentId.equals(other.instrumentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Instrument[instrumentId=" + instrumentId + "]";
    }
}
