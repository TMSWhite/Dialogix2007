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
@Table(name = "person_role_study")
public class PersonRoleStudy implements Serializable {

    @TableGenerator(name = "PersonRoleStudy_gen", pkColumnValue = "person_role_study", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PersonRoleStudy_gen")
    @Column(name = "person_role_study_id", nullable = false)
    private Long personRoleStudyId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne
    private Role roleId;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;
    @JoinColumn(name = "study_id", referencedColumnName = "study_id", nullable = true)
    @ManyToOne
    private Study studyId;    

    public PersonRoleStudy() {
    }

    public PersonRoleStudy(Long personRoleId) {
        this.personRoleStudyId = personRoleId;
    }

    public Long getPersonRoleId() {
        return personRoleStudyId;
    }

    public void setPersonRoleId(Long personRoleId) {
        this.personRoleStudyId = personRoleId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
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
        hash += (personRoleStudyId != null ? personRoleStudyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonRoleStudy)) {
            return false;
        }
        PersonRoleStudy other = (PersonRoleStudy) object;
        if ((this.personRoleStudyId == null && other.personRoleStudyId != null) || (this.personRoleStudyId != null && !this.personRoleStudyId.equals(other.personRoleStudyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.PersonRoleStudy[personRoleStudyId=" + personRoleStudyId + "]";
    }
}
