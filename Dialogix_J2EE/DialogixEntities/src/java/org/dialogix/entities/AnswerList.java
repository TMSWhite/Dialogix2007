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
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answer_list")
public class AnswerList implements Serializable {

    @TableGenerator(name = "AnswerList_gen", pkColumnValue = "answer_list", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AnswerList_gen")
    @Column(name = "answer_list_id", nullable = false)
    private Long answerListId;
    @OneToMany(mappedBy = "answerListId")
    private Collection<AnswerListContent> answerListContentCollection;
    @OneToMany(mappedBy = "answerListId")
    private Collection<AnswerListDenorm> answerListDenormCollection;
    @OneToMany(mappedBy = "answerListId")
    private Collection<Item> itemCollection;

    public AnswerList() {
    }

    public AnswerList(Long answerListId) {
        this.answerListId = answerListId;
    }

    public Long getAnswerListId() {
        return answerListId;
    }

    public void setAnswerListId(Long answerListId) {
        this.answerListId = answerListId;
    }

    public Collection<AnswerListContent> getAnswerListContentCollection() {
        return answerListContentCollection;
    }

    public void setAnswerListContentCollection(
        Collection<AnswerListContent> answerListContentCollection) {
        this.answerListContentCollection = answerListContentCollection;
    }

    public Collection<AnswerListDenorm> getAnswerListDenormCollection() {
        return answerListDenormCollection;
    }

    public void setAnswerListDenormCollection(
        Collection<AnswerListDenorm> answerListDenormCollection) {
        this.answerListDenormCollection = answerListDenormCollection;
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
        hash += (answerListId != null ? answerListId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnswerList)) {
            return false;
        }
        AnswerList other = (AnswerList) object;
        if ((this.answerListId == null && other.answerListId != null) || (this.answerListId != null && !this.answerListId.equals(other.answerListId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.AnswerList[answerListId=" + answerListId + "]";
    }
}
