/*
 * AnswerList.java
 * 
 * Created on Nov 5, 2007, 5:00:25 PM
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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "answer_lists")
public class AnswerList implements Serializable {
    @TableGenerator(name="answer_list_gen", pkColumnValue="answer_list", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="answer_list_gen")
    @Column(name = "id", nullable = false)
    private Long answerListID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerListID")
    private Collection<AnswerListDenormalized> answerListDenormalizedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerListID")
    private Collection<AnswerListContent> answerListContentCollection;
    @OneToMany(mappedBy = "answerListID")
    private Collection<Item> itemCollection;

    public AnswerList() {
    }

    public AnswerList(Long answerListID) {
        this.answerListID = answerListID;
    }

    public Long getAnswerListID() {
        return answerListID;
    }

    public void setAnswerListID(Long answerListID) {
        this.answerListID = answerListID;
    }

    public Collection<AnswerListDenormalized> getAnswerListDenormalizedCollection() {
        return answerListDenormalizedCollection;
    }

    public void setAnswerListDenormalizedCollection(Collection<AnswerListDenormalized> answerListDenormalizedCollection) {
        this.answerListDenormalizedCollection = answerListDenormalizedCollection;
    }

    public Collection<AnswerListContent> getAnswerListContentCollection() {
        return answerListContentCollection;
    }

    public void setAnswerListContentCollection(Collection<AnswerListContent> answerListContentCollection) {
        this.answerListContentCollection = answerListContentCollection;
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
        hash += (answerListID != null ? answerListID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AnswerList)) {
            return false;
        }
        AnswerList other = (AnswerList) object;
        if ((this.answerListID == null && other.answerListID != null) || (this.answerListID != null && !this.answerListID.equals(other.answerListID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.AnswerList[answerListID=" + answerListID + "]";
    }

}
