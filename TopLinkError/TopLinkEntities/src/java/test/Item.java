/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableGenerator(name="Item_Gen", pkColumnValue="Item", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=10)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="Item_Gen")  
    @Column(name = "Item_ID", nullable = false)
    private Integer itemID;
    @Column(name = "ItemSequence")
    private Integer itemSequence;
    @Column(name = "DisplayCount")
    private Integer displayCount;
    @Column(name = "VarName")
    private String varName;
    @Column(name = "Question")
    private String question;
    @Column(name = "Answer")
    private String answer;
    @JoinColumn(name = "Instrument_ID", referencedColumnName = "Instrument_ID")
    @ManyToOne
    private Instrument instrumentID;

    public Item() {
    }

    public Item(Integer itemID) {
        this.itemID = itemID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Instrument getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(Instrument instrumentID) {
        this.instrumentID = instrumentID;
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
        return "entities.Item[itemID=" + itemID + "]";
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Integer getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Integer displayCount) {
        this.displayCount = displayCount;
    }

    public Integer getItemSequence() {
        return itemSequence;
    }

    public void setItemSequence(Integer itemSequence) {
        this.itemSequence = itemSequence;
    }

}
