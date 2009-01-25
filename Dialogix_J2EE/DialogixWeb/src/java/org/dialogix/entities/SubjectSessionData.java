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
@Table(name = "subject_session_data")
public class SubjectSessionData implements Serializable {

    @TableGenerator(name = "SubjectSessionData_gen", pkColumnValue = "subject_session_data", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SubjectSessionData_gen")
    @Column(name = "subject_session_data_id", nullable = false)
    private Long subjectSessionDataId;
    @Column(name = "var_name", nullable = false)
    private String varName;
    @Lob
    @Column(name = "value", nullable = false)
    private String value;
    @JoinColumn(name = "subject_session_id", referencedColumnName = "subject_session_id")
    @ManyToOne
    private SubjectSession subjectSessionId;

    public SubjectSessionData() {
    }

    public SubjectSessionData(Long subjectSessionDataId) {
        this.subjectSessionDataId = subjectSessionDataId;
    }

    public SubjectSessionData(Long subjectSessionDataId,
                              String varName,
                              String value) {
        this.subjectSessionDataId = subjectSessionDataId;
        this.varName = varName;
        this.value = value;
    }

    public Long getSubjectSessionDataId() {
        return subjectSessionDataId;
    }

    public void setSubjectSessionDataId(Long subjectSessionDataId) {
        this.subjectSessionDataId = subjectSessionDataId;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SubjectSession getSubjectSessionId() {
        return subjectSessionId;
    }

    public void setSubjectSessionId(SubjectSession subjectSessionId) {
        this.subjectSessionId = subjectSessionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectSessionDataId != null ? subjectSessionDataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectSessionData)) {
            return false;
        }
        SubjectSessionData other = (SubjectSessionData) object;
        if ((this.subjectSessionDataId == null && other.subjectSessionDataId != null) || (this.subjectSessionDataId != null && !this.subjectSessionDataId.equals(other.subjectSessionDataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SubjectSessionData[subjectSessionDataId=" + subjectSessionDataId + "]";
    }
}
