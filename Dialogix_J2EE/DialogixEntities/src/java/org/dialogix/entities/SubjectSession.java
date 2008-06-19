/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "subject_session")
public class SubjectSession implements Serializable {

    @TableGenerator(name = "SubjectSession_gen", pkColumnValue = "subject_session", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SubjectSession_gen")
    @Column(name = "subject_session_id", nullable = false)
    private Long subjectSessionId;
    @Column(name = "pwd", nullable = false)
    private String pwd;
    @Column(name = "username", nullable = false)
    private String username;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectSessionId")
    private Collection<SubjectSessionData> subjectSessionDataCollection;
    @JoinColumn(name = "study_id", referencedColumnName = "study_id")
    @ManyToOne
    private Study studyId;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionId;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;
    @JoinColumn(name = "instrument_session_id", referencedColumnName = "instrument_session_id")
    @ManyToOne
    private InstrumentSession instrumentSessionId;

    public SubjectSession() {
    }

    public SubjectSession(Long subjectSessionId) {
        this.subjectSessionId = subjectSessionId;
    }

    public SubjectSession(Long subjectSessionId,
                          String pwd,
                          String username) {
        this.subjectSessionId = subjectSessionId;
        this.pwd = pwd;
        this.username = username;
    }

    public Long getSubjectSessionId() {
        return subjectSessionId;
    }

    public void setSubjectSessionId(Long subjectSessionId) {
        this.subjectSessionId = subjectSessionId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<SubjectSessionData> getSubjectSessionDataCollection() {
        return subjectSessionDataCollection;
    }

    public void setSubjectSessionDataCollection(
        Collection<SubjectSessionData> subjectSessionDataCollection) {
        this.subjectSessionDataCollection = subjectSessionDataCollection;
    }

    public Study getStudyId() {
        return studyId;
    }

    public void setStudyId(Study studyId) {
        this.studyId = studyId;
    }

    public InstrumentVersion getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(InstrumentVersion instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public InstrumentSession getInstrumentSessionId() {
        return instrumentSessionId;
    }

    public void setInstrumentSessionId(InstrumentSession instrumentSessionId) {
        this.instrumentSessionId = instrumentSessionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectSessionId != null ? subjectSessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectSession)) {
            return false;
        }
        SubjectSession other = (SubjectSession) object;
        if ((this.subjectSessionId == null && other.subjectSessionId != null) || (this.subjectSessionId != null && !this.subjectSessionId.equals(other.subjectSessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SubjectSession[subjectSessionId=" + subjectSessionId + "]";
    }
}
