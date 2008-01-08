/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument")
public class Instrument implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableGenerator(name="Instrument_Gen", pkColumnValue="Instrument", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=10)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Instrument_Gen")    
    @Column(name = "Instrument_ID", nullable = false)
    private Integer instrumentID;
    @Column(name = "InstrumentName")
    private String instrumentName;
    @Column(name = "DisplayCount")
    private Integer displayCount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentID")
    private Collection<Item> itemCollection;

    public Instrument() {
    }

    public Instrument(Integer instrumentID) {
        this.instrumentID = instrumentID;
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

    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
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
        return "entities.Instrument[instrumentID=" + instrumentID + "]";
    }

    public Integer getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Integer displayCount) {
        this.displayCount = displayCount;
    }

}
