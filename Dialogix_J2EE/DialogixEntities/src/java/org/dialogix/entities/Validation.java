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
import java.math.BigInteger;
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
@Table(name = "validation")
public class Validation implements Serializable {
    @TableGenerator(name="Validation_Gen", pkColumnValue="Validation", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Validation_Gen")
    @Column(name = "validation_id", nullable = false)
    private BigInteger validationID;
    @Column(name = "MinVal")
    private String minVal;
    @Column(name = "MaxVal")
    private String maxVal;
    @Column(name = "OtherVals")
    private String otherVals;
    @Column(name = "InputMask")
    private String inputMask;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "validationID")
    private Collection<Item> itemCollection;
    @JoinColumn(name = "data_type_id", referencedColumnName = "data_type_id")
    @ManyToOne
    private DataType dataTypeID;        

    public Validation() {
    }

    public Validation(BigInteger validationID) {
        this.validationID = validationID;
    }

    public BigInteger getValidationID() {
        return validationID;
    }

    public void setDataType(DataType castTo) {
        this.dataTypeID = castTo;
    }
    
    public DataType getDataType() {
        return this.dataTypeID;
    }    

    public void setValidationID(BigInteger validationID) {
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
