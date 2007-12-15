/*
 * AnswerListContent.java
 * 
 * Created on Nov 2, 2007, 11:15:04 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answer_list_content")
public class AnswerListContent implements Serializable {
    @TableGenerator(name="AnswerListContent_Gen", pkColumnValue="AnswerListContent", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="AnswerListContent_Gen")
    @Column(name = "answer_list_content_id", nullable = false)
    private BigInteger answerListContentID;
    @Column(name = "AnswerOrder", nullable = false)
    private int answerOrder;
    @Column(name = "AnswerCode", nullable = false)
    private String answerCode;
    @JoinColumn(name = "answer_list_id", referencedColumnName = "answer_list_id")
    @ManyToOne
    private AnswerList answerListID;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerID;

    public AnswerListContent() {
    }

    public AnswerListContent(BigInteger answerListContentID) {
        this.answerListContentID = answerListContentID;
    }

    public AnswerListContent(BigInteger answerListContentID, int answerOrder, String answerCode) {
        this.answerListContentID = answerListContentID;
        this.answerOrder = answerOrder;
        this.answerCode = answerCode;
    }

    public BigInteger getAnswerListContentID() {
        return answerListContentID;
    }

    public void setAnswerListContentID(BigInteger answerListContentID) {
        this.answerListContentID = answerListContentID;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
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
        return "org.dialogix.entities.AnswerListContent[answerListContentID=" + answerListContentID + "]";
    }

}
