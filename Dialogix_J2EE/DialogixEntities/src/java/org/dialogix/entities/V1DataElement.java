/*
 * V1DataElement.java
 * 
 * Created on Nov 19, 2007, 9:05:40 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "v1_data_elements")
public class V1DataElement implements Serializable {

    @TableGenerator(name = "v1_data_element_gen", pkColumnValue = "v1_data_element", table = "sequence", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "v1_data_element_gen")
    @Column(name = "id", nullable = false)
    private Long v1DataElementID;
    @Column(name = "var_name", nullable = false, length = 200)
    private String varName;
    @Column(name = "data_element_sequence", nullable = false)
    private int dataElementSequence;
    @Column(name = "group_num")
    private Integer groupNum;
    @Column(name = "item_visits")
    private Integer itemVisits;
    @JoinColumn(name = "v1_instrument_session_id", referencedColumnName="id")
    @ManyToOne
    private V1InstrumentSession v1InstrumentSessionID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "v1DataElementID")
    private Collection<V1ItemUsage> v1ItemUsageCollection;

    public V1DataElement() {
    }

    public V1DataElement(Long v1DataElementID) {
        this.v1DataElementID = v1DataElementID;
    }

    public V1DataElement(Long v1DataElementID, String varName, int dataElementSequence) {
        this.v1DataElementID = v1DataElementID;
        this.varName = varName;
        this.dataElementSequence = dataElementSequence;
    }

    public Long getV1DataElementID() {
        return v1DataElementID;
    }

    public void setV1DataElementID(Long v1DataElementID) {
        this.v1DataElementID = v1DataElementID;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public int getDataElementSequence() {
        return dataElementSequence;
    }

    public void setDataElementSequence(int dataElementSequence) {
        this.dataElementSequence = dataElementSequence;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    @XmlTransient
    public V1InstrumentSession getV1InstrumentSessionID() {
        return v1InstrumentSessionID;
    }

    public void setV1InstrumentSessionID(V1InstrumentSession v1InstrumentSessionID) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (v1DataElementID != null ? v1DataElementID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof V1DataElement)) {
            return false;
        }
        V1DataElement other = (V1DataElement) object;
        if ((this.v1DataElementID == null && other.v1DataElementID != null) || (this.v1DataElementID != null && !this.v1DataElementID.equals(other.v1DataElementID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.V1DataElement[v1DataElementID=" + v1DataElementID + "]";
    }

    public Collection<V1ItemUsage> getV1ItemUsageCollection() {
        return v1ItemUsageCollection;
    }

    public void setV1ItemUsageCollection(Collection<V1ItemUsage> v1ItemUsageCollection) {
        this.v1ItemUsageCollection = v1ItemUsageCollection;
    }

    public Integer getItemVisits() {
        return itemVisits;
    }

    public void setItemVisits(Integer itemVisits) {
        this.itemVisits = itemVisits;
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.v1InstrumentSessionID = (V1InstrumentSession) parent;
    }
}
