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
@Table(name = "person_role")
public class PersonRole implements Serializable {

    @TableGenerator(name = "PersonRole_gen", pkColumnValue = "person_role", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PersonRole_gen")
    @Column(name = "person_role_id", nullable = false)
    private Long personRoleId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne
    private Role roleId;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;

    public PersonRole() {
    }

    public PersonRole(Long personRoleId) {
        this.personRoleId = personRoleId;
    }

    public Long getPersonRoleId() {
        return personRoleId;
    }

    public void setPersonRoleId(Long personRoleId) {
        this.personRoleId = personRoleId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personRoleId != null ? personRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonRole)) {
            return false;
        }
        PersonRole other = (PersonRole) object;
        if ((this.personRoleId == null && other.personRoleId != null) || (this.personRoleId != null && !this.personRoleId.equals(other.personRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.PersonRole[personRoleId=" + personRoleId + "]";
    }
}
