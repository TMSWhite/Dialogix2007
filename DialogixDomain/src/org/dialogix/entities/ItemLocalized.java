/*
 * ItemLocalized.java
 * 
 * Created on Oct 29, 2007, 12:40:46 PM
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "item_localized")
@NamedQueries({@NamedQuery(name = "ItemLocalized.findByItemLocalizedID", query = "SELECT i FROM ItemLocalized i WHERE i.itemLocalizedID = :itemLocalizedID"), @NamedQuery(name = "ItemLocalized.findByLanguageCode", query = "SELECT i FROM ItemLocalized i WHERE i.languageCode = :languageCode"), @NamedQuery(name = "ItemLocalized.findByAnswerListID", query = "SELECT i FROM ItemLocalized i WHERE i.answerListID = :answerListID")})
public class ItemLocalized implements Serializable {
    @TableGenerator(name="ItemLocalized_Generator", pkColumnValue="ItemLocalized", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ItemLocalized_Generator")
    @Column(name = "ItemLocalized_ID", nullable = false)
    private Integer itemLocalizedID;
    @Column(name = "LanguageCode")
    private String languageCode;
    @Lob
    @Column(name = "Readback")
    private String readback;
    @Lob
    @Column(name = "Question", nullable = false)
    private String question;
    @Lob
    @Column(name = "HelpURL")
    private String helpURL;
    @Column(name = "AnswerListID")
    private Integer answerListID;
    @JoinColumn(name = "Item_ID", referencedColumnName = "Item_ID")
    @ManyToOne
    private Item itemID;

    public ItemLocalized() {
    }

    public ItemLocalized(Integer itemLocalizedID) {
        this.itemLocalizedID = itemLocalizedID;
    }

    public ItemLocalized(Integer itemLocalizedID, String question) {
        this.itemLocalizedID = itemLocalizedID;
        this.question = question;
    }

    public Integer getItemLocalizedID() {
        return itemLocalizedID;
    }

    public void setItemLocalizedID(Integer itemLocalizedID) {
        this.itemLocalizedID = itemLocalizedID;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getReadback() {
        return readback;
    }

    public void setReadback(String readback) {
        this.readback = readback;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHelpURL() {
        return helpURL;
    }

    public void setHelpURL(String helpURL) {
        this.helpURL = helpURL;
    }

    public Integer getAnswerListID() {
        return answerListID;
    }

    public void setAnswerListID(Integer answerListID) {
        this.answerListID = answerListID;
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
        hash += (itemLocalizedID != null ? itemLocalizedID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemLocalized)) {
            return false;
        }
        ItemLocalized other = (ItemLocalized) object;
        if ((this.itemLocalizedID == null && other.itemLocalizedID != null) || (this.itemLocalizedID != null && !this.itemLocalizedID.equals(other.itemLocalizedID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ItemLocalized[itemLocalizedID=" + itemLocalizedID + "]";
    }

}
