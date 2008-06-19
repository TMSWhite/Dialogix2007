/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answer_list_content")
public class AnswerListContent implements Serializable {

    @TableGenerator(name = "AnswerListContent_gen", pkColumnValue = "answer_list_content", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AnswerListContent_gen")
    @Column(name = "answer_list_content_id", nullable = false)
    private Long answerListContentId;
    @Column(name = "answer_code", nullable = false)
    private String answerCode;
    @Column(name = "answer_order", nullable = false)
    private int answerOrder;
    @JoinColumn(name = "answer_list_id", referencedColumnName = "answer_list_id")
    @ManyToOne
    private AnswerList answerListId;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerId;

    public AnswerListContent() {
    }

    public AnswerListContent(Long answerListContentId) {
        this.answerListContentId = answerListContentId;
    }

    public AnswerListContent(Long answerListContentId,
                             String answerCode,
                             int answerOrder) {
        this.answerListContentId = answerListContentId;
        this.answerCode = answerCode;
        this.answerOrder = answerOrder;
    }

    public Long getAnswerListContentId() {
        return answerListContentId;
    }

    public void setAnswerListContentId(Long answerListContentId) {
        this.answerListContentId = answerListContentId;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }

    public AnswerList getAnswerListId() {
        return answerListId;
    }

    public void setAnswerListId(AnswerList answerListId) {
        this.answerListId = answerListId;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerListContentId != null ? answerListContentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnswerListContent)) {
            return false;
        }
        AnswerListContent other = (AnswerListContent) object;
        if ((this.answerListContentId == null && other.answerListContentId != null) || (this.answerListContentId != null && !this.answerListContentId.equals(other.answerListContentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.AnswerListContent[answerListContentId=" + answerListContentId + "]";
    }
}
