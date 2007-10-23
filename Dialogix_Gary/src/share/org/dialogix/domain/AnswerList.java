/*
 * AnswerList.java
 * 
 * Created on Oct 22, 2007, 4:09:00 PM
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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answerlist")
@NamedQueries({@NamedQuery(name = "AnswerList.findByAnswerListID", query = "SELECT a FROM AnswerList a WHERE a.answerListID = :answerListID")})
public class AnswerList implements Serializable {
    @Id
    @Column(name = "AnswerList_ID", nullable = false)
    private Integer answerListID;
    @Lob
    @Column(name = "Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerListID")
    private Collection<AnswerListContent> answerListContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerListID")
    private Collection<Item> itemCollection;

    public AnswerList() {
    }

    public AnswerList(Integer answerListID) {
        this.answerListID = answerListID;
    }

    public Integer getAnswerListID() {
        return answerListID;
    }

    public void setAnswerListID(Integer answerListID) {
        this.answerListID = answerListID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.AnswerList[answerListID=" + answerListID + "]";
    }

}
