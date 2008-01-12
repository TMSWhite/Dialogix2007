/*
 * DataType.java
 * 
 * Created on Nov 2, 2007, 11:15:05 AM
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "data_types")
public class DataType implements Serializable {
    @Id
    @Column(name = "data_type_id", nullable = false)
    private Integer dataTypeID;
    @Column(name = "name", nullable = false)
    private String dataType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataTypeID")
    private Collection<Item> itemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataTypeID")
    private Collection<DisplayType> displayTypeCollection;

    public DataType() {
    }

    public DataType(Integer dataTypeID) {
        this.dataTypeID = dataTypeID;
    }

    public DataType(Integer dataTypeID, String dataType) {
        this.dataTypeID = dataTypeID;
        this.dataType = dataType;
    }

    public Integer getDataTypeID() {
        return dataTypeID;
    }

    public void setDataTypeID(Integer dataTypeID) {
        this.dataTypeID = dataTypeID;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    public Collection<DisplayType> getDisplayTypeCollection() {
        return displayTypeCollection;
    }

    public void setDisplayTypeCollection(Collection<DisplayType> displayTypeCollection) {
        this.displayTypeCollection = displayTypeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataTypeID != null ? dataTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DataType)) {
            return false;
        }
        DataType other = (DataType) object;
        if ((this.dataTypeID == null && other.dataTypeID != null) || (this.dataTypeID != null && !this.dataTypeID.equals(other.dataTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.DataType[dataTypeID=" + dataTypeID + "]";
    }

}
