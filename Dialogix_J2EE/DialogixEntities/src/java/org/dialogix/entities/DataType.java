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
@Table(name = "data_type")
public class DataType implements Serializable {

    @TableGenerator(name = "DataType_gen", pkColumnValue = "data_type", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "DataType_gen")
    @Column(name = "data_type_id", nullable = false)
    private Integer dataTypeId;
    @Column(name = "data_type", nullable = false)
    private String dataType;
    @OneToMany(mappedBy = "dataTypeId")
    private Collection<Validation> validationCollection;
    @OneToMany(mappedBy = "dataTypeId")
    private Collection<Item> itemCollection;
    @OneToMany(mappedBy = "dataTypeId")
    private Collection<DisplayType> displayTypeCollection;

    public DataType() {
    }

    public DataType(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public DataType(Integer dataTypeId,
                    String dataType) {
        this.dataTypeId = dataTypeId;
        this.dataType = dataType;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Collection<Validation> getValidationCollection() {
        return validationCollection;
    }

    public void setValidationCollection(
        Collection<Validation> validationCollection) {
        this.validationCollection = validationCollection;
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

    public void setDisplayTypeCollection(
        Collection<DisplayType> displayTypeCollection) {
        this.displayTypeCollection = displayTypeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataTypeId != null ? dataTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataType)) {
            return false;
        }
        DataType other = (DataType) object;
        if ((this.dataTypeId == null && other.dataTypeId != null) || (this.dataTypeId != null && !this.dataTypeId.equals(other.dataTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.DataType[dataTypeId=" + dataTypeId + "]";
    }
}
