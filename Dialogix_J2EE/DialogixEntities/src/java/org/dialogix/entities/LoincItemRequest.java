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
@Table(name = "loinc_item_requests")
public class LoincItemRequest implements Serializable {
    @TableGenerator(name="loinc_item_request_gen", pkColumnValue="loinc_item_request", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="loinc_item_request_gen")
    @Column(name = "id", nullable = false)
    private Long lOINCItemRequestID;
    @Column(name = "loinc_property")
    private String lOINCproperty;
    @Column(name = "loinc_time_aspect")
    private String lOINCtimeAspect;
    @Column(name = "loinc_system")
    private String lOINCsystem;
    @Column(name = "loinc_scale")
    private String lOINCscale;
    @Column(name = "loinc_method")
    private String lOINCmethod;
    @Column(name = "loinc_num")
    private String loincNum;
    @JoinColumn(name = "item_id", referencedColumnName="id")
    @ManyToOne
    private Item itemID;

    public LoincItemRequest() {
    }

    public LoincItemRequest(Long lOINCItemRequestID) {
        this.lOINCItemRequestID = lOINCItemRequestID;
    }

    public Long getLOINCItemRequestID() {
        return lOINCItemRequestID;
    }

    public void setLOINCItemRequestID(Long lOINCItemRequestID) {
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
