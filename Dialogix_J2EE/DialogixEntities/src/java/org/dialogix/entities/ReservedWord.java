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
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "reserved_word")
public class ReservedWord implements Serializable {

    @TableGenerator(name = "ReservedWord_gen", pkColumnValue = "reserved_word", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ReservedWord_gen")
    @Column(name = "reserved_word_id", nullable = false)
    private Integer reservedWordId;
    @Column(name = "meaning")
    private String meaning;
    @Column(name = "reserved_word", nullable = false)
    private String reservedWord;
    @OneToMany(mappedBy = "reservedWordId")
    private Collection<InstrumentHeader> instrumentHeaderCollection;

    public ReservedWord() {
    }

    public ReservedWord(Integer reservedWordId) {
        this.reservedWordId = reservedWordId;
    }

    public ReservedWord(Integer reservedWordId,
                        String reservedWord) {
        this.reservedWordId = reservedWordId;
        this.reservedWord = reservedWord;
    }

    public Integer getReservedWordId() {
        return reservedWordId;
    }

    public void setReservedWordId(Integer reservedWordId) {
        this.reservedWordId = reservedWordId;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getReservedWord() {
        return reservedWord;
    }

    public void setReservedWord(String reservedWord) {
        this.reservedWord = reservedWord;
    }

    public Collection<InstrumentHeader> getInstrumentHeaderCollection() {
        return instrumentHeaderCollection;
    }

    public void setInstrumentHeaderCollection(
        Collection<InstrumentHeader> instrumentHeaderCollection) {
        this.instrumentHeaderCollection = instrumentHeaderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservedWordId != null ? reservedWordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservedWord)) {
            return false;
        }
        ReservedWord other = (ReservedWord) object;
        if ((this.reservedWordId == null && other.reservedWordId != null) || (this.reservedWordId != null && !this.reservedWordId.equals(other.reservedWordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ReservedWord[reservedWordId=" + reservedWordId + "]";
    }
}
