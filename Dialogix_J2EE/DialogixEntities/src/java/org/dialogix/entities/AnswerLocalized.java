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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "answer_localized")
public class AnswerLocalized implements Serializable {

    @TableGenerator(name = "AnswerLocalized_gen", pkColumnValue = "answer_localized", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AnswerLocalized_gen")
    @Column(name = "answer_localized_id", nullable = false)
    private Long answerLocalizedId;
    @Column(name = "answer_length", nullable = false)
    private int answerLength;
    @Lob
    @Column(name = "answer_string")
    private String answerString;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne
    private Answer answerId;

    public AnswerLocalized() {
    }

    public AnswerLocalized(Long answerLocalizedId) {
        this.answerLocalizedId = answerLocalizedId;
    }

    public AnswerLocalized(Long answerLocalizedId,
                           int answerLength,
                           String languageCode) {
        this.answerLocalizedId = answerLocalizedId;
        this.answerLength = answerLength;
        this.languageCode = languageCode;
    }

    public Long getAnswerLocalizedId() {
        return answerLocalizedId;
    }

    public void setAnswerLocalizedId(Long answerLocalizedId) {
        this.answerLocalizedId = answerLocalizedId;
    }

    public int getAnswerLength() {
        return answerLength;
    }

    public void setAnswerLength(int answerLength) {
        this.answerLength = answerLength;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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
        hash += (answerLocalizedId != null ? answerLocalizedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnswerLocalized)) {
            return false;
        }
        AnswerLocalized other = (AnswerLocalized) object;
        if ((this.answerLocalizedId == null && other.answerLocalizedId != null) || (this.answerLocalizedId != null && !this.answerLocalizedId.equals(other.answerLocalizedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.AnswerLocalized[answerLocalizedId=" + answerLocalizedId + "]";
    }
}
