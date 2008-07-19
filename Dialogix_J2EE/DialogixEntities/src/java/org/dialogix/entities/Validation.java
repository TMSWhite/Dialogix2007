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
@Table(name = "validation")
public class Validation implements Serializable {

    @TableGenerator(name = "Validation_gen", pkColumnValue = "validation", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Validation_gen")
    @Column(name = "validation_id", nullable = false)
    private Long validationId;
    @Column(name = "input_mask")
    private String inputMask;
    @Column(name = "max_val")
    private String maxVal;
    @Column(name = "min_val")
    private String minVal;
    @Column(name = "other_vals")
    private String otherVals;
    @JoinColumn(name = "data_type_id", referencedColumnName = "data_type_id")
    @ManyToOne
    private DataType dataTypeId;
    @OneToMany(mappedBy = "validationId")
    private Collection<Item> itemCollection;

    public Validation() {
    }

    public Validation(Long validationId) {
        this.validationId = validationId;
    }

    public Long getValidationId() {
        return validationId;
    }

    public void setValidationId(Long validationId) {
        this.validationId = validationId;
    }

    public String getInputMask() {
        return inputMask;
    }

    public void setInputMask(String inputMask) {
        this.inputMask = inputMask;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getOtherVals() {
        return otherVals;
    }

    public void setOtherVals(String otherVals) {
        this.otherVals = otherVals;
    }

    public DataType getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(DataType dataTypeId) {
        this.dataTypeId = dataTypeId;
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
        hash += (validationId != null ? validationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Validation)) {
            return false;
        }
        Validation other = (Validation) object;
        if ((this.validationId == null && other.validationId != null) || (this.validationId != null && !this.validationId.equals(other.validationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Validation[validationId=" + validationId + "]";
    }
}
