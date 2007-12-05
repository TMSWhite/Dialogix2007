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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "loinc_item_request")
@NamedQueries({@NamedQuery(name = "LoincItemRequest.findByLOINCItemRequestID", query = "SELECT l FROM LoincItemRequest l WHERE l.lOINCItemRequestID = :lOINCItemRequestID"), @NamedQuery(name = "LoincItemRequest.findByLOINCproperty", query = "SELECT l FROM LoincItemRequest l WHERE l.lOINCproperty = :lOINCproperty"), @NamedQuery(name = "LoincItemRequest.findByLOINCtimeAspect", query = "SELECT l FROM LoincItemRequest l WHERE l.lOINCtimeAspect = :lOINCtimeAspect"), @NamedQuery(name = "LoincItemRequest.findByLOINCsystem", query = "SELECT l FROM LoincItemRequest l WHERE l.lOINCsystem = :lOINCsystem"), @NamedQuery(name = "LoincItemRequest.findByLOINCscale", query = "SELECT l FROM LoincItemRequest l WHERE l.lOINCscale = :lOINCscale"), @NamedQuery(name = "LoincItemRequest.findByLOINCmethod", query = "SELECT l FROM LoincItemRequest l WHERE l.lOINCmethod = :lOINCmethod"), @NamedQuery(name = "LoincItemRequest.findByLoincNum", query = "SELECT l FROM LoincItemRequest l WHERE l.loincNum = :loincNum")})
public class LoincItemRequest implements Serializable {
    @TableGenerator(name="LoincItemRequest_Generator", pkColumnValue="LoincItemRequest", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="LoincItemRequest_Generator")
    @Column(name = "LOINC_ItemRequest_ID", nullable = false)
    private Integer lOINCItemRequestID;
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

    public LoincItemRequest(Integer lOINCItemRequestID) {
        this.lOINCItemRequestID = lOINCItemRequestID;
    }

    public Integer getLOINCItemRequestID() {
        return lOINCItemRequestID;
    }

    public void setLOINCItemRequestID(Integer lOINCItemRequestID) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
