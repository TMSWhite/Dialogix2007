/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "parser_test")
public class ParserTest implements Serializable {

    @TableGenerator(name = "ParserTest_gen", pkColumnValue = "parser_test", table = "sequence_data", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ParserTest_gen")
    @Column(name = "parser_test_id", nullable = false)
    private Integer parserTestId;
    @Lob
    @Column(name = "answer", nullable = false)
    private String answer;
    @Column(name = "correct", nullable = false)
    private int correct;
    @Column(name = "created_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Lob
    @Column(name = "equation", nullable = false)
    private String equation;
    @Lob
    @Column(name = "expected", nullable = false)
    private String expected;

    public ParserTest() {
    }

    public ParserTest(Integer parserTestId) {
        this.parserTestId = parserTestId;
    }

    public ParserTest(Integer parserTestId,
                      String answer,
                      int correct,
                      Date createdOn,
                      String equation,
                      String expected) {
        this.parserTestId = parserTestId;
        this.answer = answer;
        this.correct = correct;
        this.createdOn = createdOn;
        this.equation = equation;
        this.expected = expected;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public Integer getParserTestId() {
        return parserTestId;
    }

    public void setParserTestId(Integer parserTestId) {
        this.parserTestId = parserTestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parserTestId != null ? parserTestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParserTest)) {
            return false;
        }
        ParserTest other = (ParserTest) object;
        if ((this.parserTestId == null && other.parserTestId != null) || (this.parserTestId != null && !this.parserTestId.equals(other.parserTestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ParserTest[parserTestId=" + parserTestId + "]";
    }
}
