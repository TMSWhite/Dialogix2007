/*
 * Validation.java
 * 
 * Created on Nov 2, 2007, 11:15:09 AM
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
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "validations")
public class Validation implements Serializable {
    @TableGenerator(name="validation_gen", pkColumnValue="validation", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="validation_gen")
    @Column(name = "validation_id", nullable = false)
    private Long validationID;
    @Column(name = "min_val")
    private String minVal;
    @Column(name = "max_val")
    private String maxVal;
    @Column(name = "other_vals")
    private String otherVals;
    @Column(name = "input_mask")
    private String inputMask;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "validationID")
    private Collection<Item> itemCollection;
    @JoinColumn(name = "data_type_id", referencedColumnName = "data_type_id")
    @ManyToOne
    private DataType dataTypeID;        

    public Validation() {
    }

    public Validation(Long validationID) {
        this.validationID = validationID;
    }

    public Long getValidationID() {
        return validationID;
    }

    public void setDataType(DataType castTo) {
        this.dataTypeID = castTo;
    }
    
    public DataType getDataType() {
        return this.dataTypeID;
    }    

    public void setValidationID(Long validationID) {
        this.validationID = validationID;
    }

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public String getOtherVals() {
        return otherVals;
    }

    public void setOtherVals(String otherVals) {
        this.otherVals = otherVals;
    }

    public String getInputMask() {
        return inputMask;
    }

    public void setInputMask(String inputMask) {
        this.inputMask = inputMask;
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
        hash += (validationID != null ? validationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Validation)) {
            return false;
        }
        Validation other = (Validation) object;
        if ((this.validationID == null && other.validationID != null) || (this.validationID != null && !this.validationID.equals(other.validationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Validation[validationID=" + validationID + "]";
    }

}
