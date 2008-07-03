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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "study")
public class Study implements Serializable {

    @TableGenerator(name = "Study_gen", pkColumnValue = "study", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Study_gen")
    @Column(name = "study_id", nullable = false)
    private Long studyId;
    @Lob
    @Column(name = "grant_description")
    private String grantDescription;
    @Column(name = "grant_name")
    private String grantName;
    @Column(name = "pi_name")
    private String piName;
    @Column(name = "study_icon_path")
    private String studyIconPath;
    @Column(name = "study_name", nullable = false)
    private String studyName;
    @Column(name = "support_email")
    private String supportEmail;
    @Column(name = "support_name")
    private String supportName;
    @Column(name = "support_phone")
    private String supportPhone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studyId")
    private Collection<SubjectSession> subjectSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studyId")
    private Collection<PersonRoleStudy> personRoleStudyCollection;    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studyId")
    private Collection<StudyInstrumentVersion> studyInstrumentVersionCollection;

    public Study() {
    }

    public Study(Long studyId) {
        this.studyId = studyId;
    }

    public Study(Long studyId,
                 String studyName) {
        this.studyId = studyId;
        this.studyName = studyName;
    }

    public Long getStudyId() {
        return studyId;
    }

    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    public String getGrantDescription() {
        return grantDescription;
    }

    public void setGrantDescription(String grantDescription) {
        this.grantDescription = grantDescription;
    }

    public String getGrantName() {
        return grantName;
    }

    public void setGrantName(String grantName) {
        this.grantName = grantName;
    }

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        this.piName = piName;
    }

    public String getStudyIconPath() {
        return studyIconPath;
    }

    public void setStudyIconPath(String studyIconPath) {
        this.studyIconPath = studyIconPath;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName;
    }

    public String getSupportPhone() {
        return supportPhone;
    }

    public void setSupportPhone(String supportPhone) {
        this.supportPhone = supportPhone;
    }

    public Collection<SubjectSession> getSubjectSessionCollection() {
        return subjectSessionCollection;
    }

    public void setSubjectSessionCollection(
        Collection<SubjectSession> subjectSessionCollection) {
        this.subjectSessionCollection = subjectSessionCollection;
    }
    
    public Collection<PersonRoleStudy> getPersonRoleStudyCollection() {
        return personRoleStudyCollection;
    }

    public void setPersonRoleStudyCollection(Collection<PersonRoleStudy> personRoleStudyCollection) {
        this.personRoleStudyCollection = personRoleStudyCollection;
    }

    public Collection<StudyInstrumentVersion> getStudyInstrumentVersionCollection() {
        return studyInstrumentVersionCollection;
    }

    public void setStudyInstrumentVersionCollection(Collection<StudyInstrumentVersion> studyInstrumentVersionCollection) {
        this.studyInstrumentVersionCollection = studyInstrumentVersionCollection;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studyId != null ? studyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Study)) {
            return false;
        }
        Study other = (Study) object;
        if ((this.studyId == null && other.studyId != null) || (this.studyId != null && !this.studyId.equals(other.studyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Study[studyId=" + studyId + "]";
    }
}
