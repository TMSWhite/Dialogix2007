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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "validation")
@NamedQueries({@NamedQuery(name = "Validation.findByValidationID", query = "SELECT v FROM Validation v WHERE v.validationID = :validationID"), @NamedQuery(name = "Validation.findByMinVal", query = "SELECT v FROM Validation v WHERE v.minVal = :minVal"), @NamedQuery(name = "Validation.findByMaxVal", query = "SELECT v FROM Validation v WHERE v.maxVal = :maxVal"), @NamedQuery(name = "Validation.findByOtherVals", query = "SELECT v FROM Validation v WHERE v.otherVals = :otherVals"), @NamedQuery(name = "Validation.findByInputMask", query = "SELECT v FROM Validation v WHERE v.inputMask = :inputMask")})
public class Validation implements Serializable {
    @TableGenerator(name="Validation_Generator", pkColumnValue="Validation", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Validation_Generator")
    @Column(name = "Validation_ID", nullable = false)
    private Integer validationID;
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

    public Validation() {
    }

    public Validation(Integer validationID) {
        this.validationID = validationID;
    }

    public Integer getValidationID() {
        return validationID;
    }

    public void setValidationID(Integer validationID) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
