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
@Table(name = "study_inst_ver")
public class StudyInstrumentVersion implements Serializable {

    @TableGenerator(name = "StudyInstrumentVersion_gen", pkColumnValue = "study_inst_ver", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "StudyInstrumentVersion_gen")
    @Column(name = "study_inst_ver_id", nullable = false)
    private Long studyInstrumentVersionId;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionId;
    @JoinColumn(name = "study_id", referencedColumnName = "study_id", nullable = true)
    @ManyToOne
    private Study studyId;    

    public StudyInstrumentVersion() {
    }

    public StudyInstrumentVersion(Long studyInstrumentVersionId) {
        this.studyInstrumentVersionId = studyInstrumentVersionId;
    }

    public Long getInstrumentVersionRoleId() {
        return studyInstrumentVersionId;
    }

    public void setInstrumentVersionRoleId(Long studyInstrumentVersionId) {
        this.studyInstrumentVersionId = studyInstrumentVersionId;
    }

    public InstrumentVersion getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(InstrumentVersion instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }
    
    public Study getStudyId() {
        return studyId;
    }

    public void setStudyId(Study studyId) {
        this.studyId = studyId;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studyInstrumentVersionId != null ? studyInstrumentVersionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudyInstrumentVersion)) {
            return false;
        }
        StudyInstrumentVersion other = (StudyInstrumentVersion) object;
        if ((this.studyInstrumentVersionId == null && other.studyInstrumentVersionId != null) || (this.studyInstrumentVersionId != null && !this.studyInstrumentVersionId.equals(other.studyInstrumentVersionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.StudyInstrumentVersion[StudyInstrumentVersionId=" + studyInstrumentVersionId + "]";
    }
}
