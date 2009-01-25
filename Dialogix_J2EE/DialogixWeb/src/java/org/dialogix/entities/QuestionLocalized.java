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
@Table(name = "question_localized")
public class QuestionLocalized implements Serializable {

    @TableGenerator(name = "QuestionLocalized_gen", pkColumnValue = "question_localized", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "QuestionLocalized_gen")
    @Column(name = "question_localized_id", nullable = false)
    private Long questionLocalizedId;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @Column(name = "question_length", nullable = false)
    private int questionLength;
    @Lob
    @Column(name = "question_string")
    private String questionString;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private Question questionId;

    public QuestionLocalized() {
    }

    public QuestionLocalized(Long questionLocalizedId) {
        this.questionLocalizedId = questionLocalizedId;
    }

    public QuestionLocalized(Long questionLocalizedId,
                             String languageCode,
                             int questionLength) {
        this.questionLocalizedId = questionLocalizedId;
        this.languageCode = languageCode;
        this.questionLength = questionLength;
    }

    public Long getQuestionLocalizedId() {
        return questionLocalizedId;
    }

    public void setQuestionLocalizedId(Long questionLocalizedId) {
        this.questionLocalizedId = questionLocalizedId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getQuestionLength() {
        return questionLength;
    }

    public void setQuestionLength(int questionLength) {
        this.questionLength = questionLength;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
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
        hash += (questionLocalizedId != null ? questionLocalizedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionLocalized)) {
            return false;
        }
        QuestionLocalized other = (QuestionLocalized) object;
        if ((this.questionLocalizedId == null && other.questionLocalizedId != null) || (this.questionLocalizedId != null && !this.questionLocalizedId.equals(other.questionLocalizedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.QuestionLocalized[questionLocalizedId=" + questionLocalizedId + "]";
    }
}
