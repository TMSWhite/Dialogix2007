/*
 * Item.java
 * 
 * Created on Oct 22, 2007, 4:09:06 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "item")
@NamedQueries({@NamedQuery(name = "Item.findByItemID", query = "SELECT i FROM Item i WHERE i.itemID = :itemID"), @NamedQuery(name = "Item.findByItemType", query = "SELECT i FROM Item i WHERE i.itemType = :itemType"), @NamedQuery(name = "Item.findByHasLOINCcode", query = "SELECT i FROM Item i WHERE i.hasLOINCcode = :hasLOINCcode"), @NamedQuery(name = "Item.findByLoincNum", query = "SELECT i FROM Item i WHERE i.loincNum = :loincNum")})
public class Item implements Serializable {
    @Id
    @Column(name = "Item_ID", nullable = false)
    private Integer itemID;
    @Column(name = "ItemType", nullable = false)
    private String itemType;
    @Lob
    @Column(name = "Concept")
    private String concept;
    @Column(name = "hasLOINCcode")
    private Boolean hasLOINCcode;
    @Column(name = "LOINC_NUM")
    private String loincNum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemID")
    private Collection<LoincItemRequest> loincItemRequestCollection;
    @JoinColumn(name = "Question_ID", referencedColumnName = "Question_ID")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "DataType_ID", referencedColumnName = "DataType_ID")
    @ManyToOne
    private DataType dataTypeID;
    @JoinColumn(name = "AnswerList_ID", referencedColumnName = "AnswerList_ID")
    @ManyToOne
    private AnswerList answerListID;
    @JoinColumn(name = "Validation_ID", referencedColumnName = "Validation_ID")
    @ManyToOne
    private Validation validationID;

    public Item() {
    }

    public Item(Integer itemID) {
        this.itemID = itemID;
    }

    public Item(Integer itemID, String itemType) {
        this.itemID = itemID;
        this.itemType = itemType;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Boolean getHasLOINCcode() {
        return hasLOINCcode;
    }

    public void setHasLOINCcode(Boolean hasLOINCcode) {
        this.hasLOINCcode = hasLOINCcode;
    }

    public String getLoincNum() {
        return loincNum;
    }

    public void setLoincNum(String loincNum) {
        this.loincNum = loincNum;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Collection<LoincItemRequest> getLoincItemRequestCollection() {
        return loincItemRequestCollection;
    }

    public void setLoincItemRequestCollection(Collection<LoincItemRequest> loincItemRequestCollection) {
        this.loincItemRequestCollection = loincItemRequestCollection;
    }

    public Question getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Question questionID) {
        this.questionID = questionID;
    }

    public DataType getDataTypeID() {
        return dataTypeID;
    }

    public void setDataTypeID(DataType dataTypeID) {
        this.dataTypeID = dataTypeID;
    }

    public AnswerList getAnswerListID() {
        return answerListID;
    }

    public void setAnswerListID(AnswerList answerListID) {
        this.answerListID = answerListID;
    }

    public Validation getValidationID() {
        return validationID;
    }

    public void setValidationID(Validation validationID) {
        this.validationID = validationID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemID != null ? itemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.itemID == null && other.itemID != null) || (this.itemID != null && !this.itemID.equals(other.itemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.Item[itemID=" + itemID + "]";
    }

}
