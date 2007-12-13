/*
 * LoincItemRequest.java
 * 
 * Created on Nov 2, 2007, 11:15:09 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "loinc_item_request")
public class LoincItemRequest implements Serializable {
    @TableGenerator(name="LoincItemRequest_Gen", pkColumnValue="LoincItemRequest", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="LoincItemRequest_Gen")
    @Column(name = "LOINC_ItemRequest_ID", nullable = false)
    private BigInteger lOINCItemRequestID;
    @Column(name = "LOINCproperty")
    private String lOINCproperty;
    @Column(name = "LOINCtimeAspect")
    private String lOINCtimeAspect;
    @Column(name = "LOINCsystem")
    private String lOINCsystem;
    @Column(name = "LOINCscale")
    private String lOINCscale;
    @Column(name = "LOINCmethod")
    private String lOINCmethod;
    @Column(name = "LOINC_NUM")
    private String loincNum;
    @JoinColumn(name = "Item_ID", referencedColumnName = "Item_ID")
    @ManyToOne
    private Item itemID;

    public LoincItemRequest() {
    }

    public LoincItemRequest(BigInteger lOINCItemRequestID) {
        this.lOINCItemRequestID = lOINCItemRequestID;
    }

    public BigInteger getLOINCItemRequestID() {
        return lOINCItemRequestID;
    }

    public void setLOINCItemRequestID(BigInteger lOINCItemRequestID) {
        this.lOINCItemRequestID = lOINCItemRequestID;
    }

    public String getLOINCproperty() {
        return lOINCproperty;
    }

    public void setLOINCproperty(String lOINCproperty) {
        this.lOINCproperty = lOINCproperty;
    }

    public String getLOINCtimeAspect() {
        return lOINCtimeAspect;
    }

    public void setLOINCtimeAspect(String lOINCtimeAspect) {
        this.lOINCtimeAspect = lOINCtimeAspect;
    }

    public String getLOINCsystem() {
        return lOINCsystem;
    }

    public void setLOINCsystem(String lOINCsystem) {
        this.lOINCsystem = lOINCsystem;
    }

    public String getLOINCscale() {
        return lOINCscale;
    }

    public void setLOINCscale(String lOINCscale) {
        this.lOINCscale = lOINCscale;
    }

    public String getLOINCmethod() {
        return lOINCmethod;
    }

    public void setLOINCmethod(String lOINCmethod) {
        this.lOINCmethod = lOINCmethod;
    }

    public String getLoincNum() {
        return loincNum;
    }

    public void setLoincNum(String loincNum) {
        this.loincNum = loincNum;
    }

    public Item getItemID() {
        return itemID;
    }

    public void setItemID(Item itemID) {
        this.itemID = itemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lOINCItemRequestID != null ? lOINCItemRequestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LoincItemRequest)) {
            return false;
        }
        LoincItemRequest other = (LoincItemRequest) object;
        if ((this.lOINCItemRequestID == null && other.lOINCItemRequestID != null) || (this.lOINCItemRequestID != null && !this.lOINCItemRequestID.equals(other.lOINCItemRequestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.LoincItemRequest[lOINCItemRequestID=" + lOINCItemRequestID + "]";
    }

}
