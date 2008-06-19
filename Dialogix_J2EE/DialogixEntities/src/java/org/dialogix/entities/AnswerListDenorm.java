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
@Table(name = "answer_list_denorm")
public class AnswerListDenorm implements Serializable {

    @TableGenerator(name = "AnswerListDenorm_gen", pkColumnValue = "answer_list_denorm", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AnswerListDenorm_gen")
    @Column(name = "answer_list_denorm_id", nullable = false)
    private Long answerListDenormId;
    @Column(name = "answer_list_denorm_len", nullable = false)
    private int answerListDenormLen;
    @Lob
    @Column(name = "answer_list_denorm_string", nullable = false)
    private String answerListDenormString;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @JoinColumn(name = "answer_list_id", referencedColumnName = "answer_list_id")
    @ManyToOne
    private AnswerList answerListId;

    public AnswerListDenorm() {
    }

    public AnswerListDenorm(Long answerListDenormId) {
        this.answerListDenormId = answerListDenormId;
    }

    public AnswerListDenorm(Long answerListDenormId,
                            int answerListDenormLen,
                            String answerListDenormString,
                            String languageCode) {
        this.answerListDenormId = answerListDenormId;
        this.answerListDenormLen = answerListDenormLen;
        this.answerListDenormString = answerListDenormString;
        this.languageCode = languageCode;
    }

    public Long getAnswerListDenormId() {
        return answerListDenormId;
    }

    public void setAnswerListDenormId(Long answerListDenormId) {
        this.answerListDenormId = answerListDenormId;
    }

    public int getAnswerListDenormLen() {
        return answerListDenormLen;
    }

    public void setAnswerListDenormLen(int answerListDenormLen) {
        this.answerListDenormLen = answerListDenormLen;
    }

    public String getAnswerListDenormString() {
        return answerListDenormString;
    }

    public void setAnswerListDenormString(String answerListDenormString) {
        this.answerListDenormString = answerListDenormString;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public AnswerList getAnswerListId() {
        return answerListId;
    }

    public void setAnswerListId(AnswerList answerListId) {
        this.answerListId = answerListId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerListDenormId != null ? answerListDenormId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnswerListDenorm)) {
            return false;
        }
        AnswerListDenorm other = (AnswerListDenorm) object;
        if ((this.answerListDenormId == null && other.answerListDenormId != null) || (this.answerListDenormId != null && !this.answerListDenormId.equals(other.answerListDenormId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.AnswerListDenorm[answerListDenormId=" + answerListDenormId + "]";
    }
}
