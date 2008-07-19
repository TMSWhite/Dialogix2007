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
@Table(name = "item")
public class Item implements Serializable {

    @TableGenerator(name = "Item_gen", pkColumnValue = "item", table = "sequence_data", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Item_gen")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    @Column(name = "item_type", nullable = false)
    private Character itemType;
    @OneToMany(mappedBy = "itemId")
    private Collection<InstrumentContent> instrumentContentCollection;
    @JoinColumn(name = "validation_id", referencedColumnName = "validation_id")
    @ManyToOne
    private Validation validationId;
    @JoinColumn(name = "answer_list_id", referencedColumnName = "answer_list_id")
    @ManyToOne
    private AnswerList answerListId;
    @JoinColumn(name = "data_type_id", referencedColumnName = "data_type_id")
    @ManyToOne
    private DataType dataTypeId;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private Question questionId;

    public Item() {
    }

    public Item(Long itemId) {
        this.itemId = itemId;
    }

    public Item(Long itemId,
                Character itemType) {
        this.itemId = itemId;
        this.itemType = itemType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Character getItemType() {
        return itemType;
    }

    public void setItemType(Character itemType) {
        this.itemType = itemType;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(
        Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Validation getValidationId() {
        return validationId;
    }

    public void setValidationId(Validation validationId) {
        this.validationId = validationId;
    }

    public AnswerList getAnswerListId() {
        return answerListId;
    }

    public void setAnswerListId(AnswerList answerListId) {
        this.answerListId = answerListId;
    }

    public DataType getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(DataType dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Item[itemId=" + itemId + "]";
    }
}
