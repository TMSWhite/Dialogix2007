/*
 * AnswerListContent.java
 * 
 * Created on Oct 22, 2007, 4:08:58 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answerlistcontent")
@NamedQueries({@NamedQuery(name = "AnswerListContent.findByAnswerListContentID", query = "SELECT a FROM AnswerListContent a WHERE a.answerListContentID = :answerListContentID"), @NamedQuery(name = "AnswerListContent.findByAnswerOrder", query = "SELECT a FROM AnswerListContent a WHERE a.answerOrder = :answerOrder"), @NamedQuery(name = "AnswerListContent.findByValue", query = "SELECT a FROM AnswerListContent a WHERE a.value = :value")})
public class AnswerListContent implements Serializable {
    @Id
    @Column(name = "AnswerListContent_ID", nullable = false)
    private Integer answerListContentID;
    @Column(name = "Answer_Order", nullable = false)
    private int answerOrder;
    @Column(name = "Value", nullable = false)
    private String value;
    @JoinColumn(name = "AnswerList_ID", referencedColumnName = "AnswerList_ID")
    @ManyToOne
    private AnswerList answerListID;
    @JoinColumn(name = "Answer_ID", referencedColumnName = "Answer_ID")
    @ManyToOne
    private Answer answerID;

    public AnswerListContent() {
    }

    public AnswerListContent(Integer answerListContentID) {
        this.answerListContentID = answerListContentID;
    }

    public AnswerListContent(Integer answerListContentID, int answerOrder, String value) {
        this.answerListContentID = answerListContentID;
        this.answerOrder = answerOrder;
        this.value = value;
    }

    public Integer getAnswerListContentID() {
        return answerListContentID;
    }

    public void setAnswerListContentID(Integer answerListContentID) {
        this.answerListContentID = answerListContentID;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AnswerList getAnswerListID() {
        return answerListID;
    }

    public void setAnswerListID(AnswerList answerListID) {
        this.answerListID = answerListID;
    }

    public Answer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Answer answerID) {
        this.answerID = answerID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerListContentID != null ? answerListContentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnswerListContent)) {
            return false;
        }
        AnswerListContent other = (AnswerListContent) object;
        if ((this.answerListContentID == null && other.answerListContentID != null) || (this.answerListContentID != null && !this.answerListContentID.equals(other.answerListContentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.AnswerListContent[answerListContentID=" + answerListContentID + "]";
    }

}
