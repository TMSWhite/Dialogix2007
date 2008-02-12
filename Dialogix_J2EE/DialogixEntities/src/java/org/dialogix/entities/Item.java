/*
 * Item.java
 * 
 * Created on Nov 5, 2007, 5:00:32 PM
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "items")
public class Item implements Serializable {
    @TableGenerator(name="item_gen", pkColumnValue="item", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=500)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="item_gen")
    @Column(name = "id", nullable = false)
    private Long itemID;
    @Column(name = "item_type", nullable = false)
    private String itemType;
    @Column(name = "has_loinc_code")
    private Boolean hasLOINCcode;
    @Column(name = "loinc_num")
    private String loincNum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemID")
    private Collection<LoincItemRequest> loincItemRequestCollection;
    @JoinColumn(name = "question_id", referencedColumnName="id")
    @ManyToOne
    private Question questionID;
    @JoinColumn(name = "data_type_id", referencedColumnName="id")
    @ManyToOne
    private DataType dataTypeID;
    @JoinColumn(name = "answer_list_id", referencedColumnName="id")
    @ManyToOne
    private AnswerList answerListID;
    @JoinColumn(name = "validation_id", referencedColumnName="id")
    @ManyToOne
    private Validation validationID;

    public Item() {
    }

    public Item(Long itemID) {
        this.itemID = itemID;
    }

    public Item(Long itemID, String itemType) {
        this.itemID = itemID;
        this.itemType = itemType;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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
        return "org.dialogix.entities.Item[itemID=" + itemID + "]";
    }

}
