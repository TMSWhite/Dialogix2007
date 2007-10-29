/*
 * SequenceGeneratorTable.java
 * 
 * Created on Oct 26, 2007, 5:17:09 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "sequence_generator_table")
@NamedQueries({@NamedQuery(name = "SequenceGeneratorTable.findBySequenceName", query = "SELECT s FROM SequenceGeneratorTable s WHERE s.sequenceName = :sequenceName"), @NamedQuery(name = "SequenceGeneratorTable.findBySequenceValue", query = "SELECT s FROM SequenceGeneratorTable s WHERE s.sequenceValue = :sequenceValue")})
public class SequenceGeneratorTable implements Serializable {
    @Id
    @Column(name = "SEQUENCE_NAME", nullable = false)
    private String sequenceName;
    @Column(name = "SEQUENCE_VALUE", nullable = false)
    private int sequenceValue;

    public SequenceGeneratorTable() {
    }

    public SequenceGeneratorTable(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public SequenceGeneratorTable(String sequenceName, int sequenceValue) {
        this.sequenceName = sequenceName;
        this.sequenceValue = sequenceValue;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public int getSequenceValue() {
        return sequenceValue;
    }

    public void setSequenceValue(int sequenceValue) {
        this.sequenceValue = sequenceValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sequenceName != null ? sequenceName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SequenceGeneratorTable)) {
            return false;
        }
        SequenceGeneratorTable other = (SequenceGeneratorTable) object;
        if ((this.sequenceName == null && other.sequenceName != null) || (this.sequenceName != null && !this.sequenceName.equals(other.sequenceName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SequenceGeneratorTable[sequenceName=" + sequenceName + "]";
    }

}
