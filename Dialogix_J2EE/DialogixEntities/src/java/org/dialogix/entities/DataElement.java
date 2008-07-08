/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "data_element")
public class DataElement implements Serializable {

    @TableGenerator(name = "DataElement_gen", pkColumnValue = "data_element", table = "sequence_data", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "DataElement_gen")
    @Column(name = "data_element_id", nullable = false)
    private Long dataElementId;
    @Column(name = "data_element_sequence", nullable = false)
    private int dataElementSequence;
    @Column(name = "group_num", nullable = false)
    private int groupNum;
    @Column(name = "item_visits", nullable = false)
    private int itemVisits;
    @JoinColumn(name = "var_name_id", referencedColumnName = "var_name_id")
    @ManyToOne
    private VarName varNameId;
    @JoinColumn(name = "instrument_content_id", referencedColumnName = "instrument_content_id")
    @ManyToOne
    private InstrumentContent instrumentContentId;
    @JoinColumn(name = "instrument_session_id", referencedColumnName = "instrument_session_id")
    @ManyToOne
    private InstrumentSession instrumentSessionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataElementId")
    private Collection<ItemUsage> itemUsageCollection;
    @JoinColumn(name = "last_item_usage_id", referencedColumnName = "item_usage_id" , nullable=true)
    @OneToOne
    private ItemUsage lastItemUsageId;

    public DataElement() {
    }

    public DataElement(Long dataElementId) {
        this.dataElementId = dataElementId;
    }

    public DataElement(Long dataElementId,
                       int dataElementSequence,
                       int groupNum,
                       int itemVisits) {
        this.dataElementId = dataElementId;
        this.dataElementSequence = dataElementSequence;
        this.groupNum = groupNum;
        this.itemVisits = itemVisits;
    }

    public Long getDataElementId() {
        return dataElementId;
    }

    public void setDataElementId(Long dataElementId) {
        this.dataElementId = dataElementId;
    }

    public int getDataElementSequence() {
        return dataElementSequence;
    }

    public void setDataElementSequence(int dataElementSequence) {
        this.dataElementSequence = dataElementSequence;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getItemVisits() {
        return itemVisits;
    }

    public void setItemVisits(int itemVisits) {
        this.itemVisits = itemVisits;
    }

    public VarName getVarNameId() {
        return varNameId;
    }

    public void setVarNameId(VarName varNameId) {
        this.varNameId = varNameId;
    }

    public InstrumentContent getInstrumentContentId() {
        return instrumentContentId;
    }

    public void setInstrumentContentId(InstrumentContent instrumentContentId) {
        this.instrumentContentId = instrumentContentId;
    }

    public InstrumentSession getInstrumentSessionId() {
        return instrumentSessionId;
    }

    public void setInstrumentSessionId(InstrumentSession instrumentSessionId) {
        this.instrumentSessionId = instrumentSessionId;
    }

    public Collection<ItemUsage> getItemUsageCollection() {
        return itemUsageCollection;
    }

    public void setItemUsageCollection(Collection<ItemUsage> itemUsageCollection) {
        this.itemUsageCollection = itemUsageCollection;
    }
    
    public ItemUsage getLastItemUsageId() {
        return lastItemUsageId;
    }

    public void setLastItemUsageId(ItemUsage lastItemUsageId) {
        this.lastItemUsageId = lastItemUsageId;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataElementId != null ? dataElementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataElement)) {
            return false;
        }
        DataElement other = (DataElement) object;
        if ((this.dataElementId == null && other.dataElementId != null) || (this.dataElementId != null && !this.dataElementId.equals(other.dataElementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.DataElement[dataElementId=" + dataElementId + "]";
    }
}
