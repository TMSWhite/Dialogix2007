/*
3 * To change this template, choose Tools | Templates
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
@Table(name = "role_menu")
public class RoleMenu implements Serializable {

    @TableGenerator(name = "RoleMenu_gen", pkColumnValue = "role_menu", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "RoleMenu_gen")
    @Column(name = "role_menu_id", nullable = false)
    private Integer roleMenuId;
    @JoinColumn(name = "menu_id", referencedColumnName = "menu_id")
    @ManyToOne
    private Menu menuId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne
    private Role roleId;

    public RoleMenu() {
    }

    public RoleMenu(Integer roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public Integer getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(Integer roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public Menu getMenuId() {
        return menuId;
    }

    public void setMenuId(Menu menuId) {
        this.menuId = menuId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleMenuId != null ? roleMenuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleMenu)) {
            return false;
        }
        RoleMenu other = (RoleMenu) object;
        if ((this.roleMenuId == null && other.roleMenuId != null) || (this.roleMenuId != null && !this.roleMenuId.equals(other.roleMenuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.RoleMenu[roleMenuId=" + roleMenuId + "]";
    }
}
