/*
 * ReservedWord.java
 * 
 * Created on Oct 29, 2007, 12:40:43 PM
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
import javax.persistence.Table;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "reserved_word")
@NamedQueries({@NamedQuery(name = "ReservedWord.findByReservedWordID", query = "SELECT r FROM ReservedWord r WHERE r.reservedWordID = :reservedWordID"), @NamedQuery(name = "ReservedWord.findByReservedWord", query = "SELECT r FROM ReservedWord r WHERE r.reservedWord = :reservedWord")})
public class ReservedWord implements Serializable {
    @Id
    @Column(name = "ReservedWord_ID", nullable = false)
    private Integer reservedWordID;
    @Column(name = "ReservedWord", nullable = false)
    private String reservedWord;
    @Lob
    @Column(name = "Meaning")
    private String meaning;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservedWordID")
    private Collection<InstrumentHeader> instrumentHeaderCollection;

    public ReservedWord() {
    }

    public ReservedWord(Integer reservedWordID) {
        this.reservedWordID = reservedWordID;
    }

    public ReservedWord(Integer reservedWordID, String reservedWord) {
        this.reservedWordID = reservedWordID;
        this.reservedWord = reservedWord;
    }

    public Integer getReservedWordID() {
        return reservedWordID;
    }

    public void setReservedWordID(Integer reservedWordID) {
        this.reservedWordID = reservedWordID;
    }

    public String getReservedWord() {
        return reservedWord;
    }

    public void setReservedWord(String reservedWord) {
        this.reservedWord = reservedWord;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Collection<InstrumentHeader> getInstrumentHeaderCollection() {
        return instrumentHeaderCollection;
    }

    public void setInstrumentHeaderCollection(Collection<InstrumentHeader> instrumentHeaderCollection) {
        this.instrumentHeaderCollection = instrumentHeaderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservedWordID != null ? reservedWordID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservedWord)) {
            return false;
        }
        ReservedWord other = (ReservedWord) object;
        if ((this.reservedWordID == null && other.reservedWordID != null) || (this.reservedWordID != null && !this.reservedWordID.equals(other.reservedWordID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ReservedWord[reservedWordID=" + reservedWordID + "]";
    }

}
