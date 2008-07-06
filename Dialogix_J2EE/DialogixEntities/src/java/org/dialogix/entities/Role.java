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
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @TableGenerator(name = "Role_gen", pkColumnValue = "role", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Role_gen")
    @Column(name = "role_id", nullable = false)
    private Integer roleId;
    @Column(name = "role_name")
    private String roleName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
    private Collection<RoleMenu> roleMenuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
    private Collection<PersonRoleStudy> personRoleStudyCollection;

    public Role() {
    }

    public Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Collection<RoleMenu> getRoleMenuCollection() {
        return roleMenuCollection;
    }

    public void setRoleMenuCollection(Collection<RoleMenu> roleMenuCollection) {
        this.roleMenuCollection = roleMenuCollection;
    }

    public Collection<PersonRoleStudy> getPersonRoleStudyCollection() {
        return personRoleStudyCollection;
    }

    public void setPersonRoleStudyCollection(
        Collection<PersonRoleStudy> personRoleStudyCollection) {
        this.personRoleStudyCollection = personRoleStudyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Role[roleId=" + roleId + "]";
    }
}
