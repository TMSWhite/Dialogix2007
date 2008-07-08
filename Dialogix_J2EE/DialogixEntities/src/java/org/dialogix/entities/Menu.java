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
@Table(name = "menu")
public class Menu implements Serializable {

    @TableGenerator(name = "Menu_gen", pkColumnValue = "menu", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Menu_gen")
    @Column(name = "menu_id", nullable = false)
    private Integer menuId;
    @Column(name = "menu_type")
    private Integer menuType;
    @Column(name = "display_text", nullable = false)
    private String displayText;
    @Column(name = "menu_name", nullable = false)
    private String menuName;
    @Column(name = "menu_order", nullable = false)
    private String menuOrder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuId")
    private Collection<RoleMenu> roleMenuCollection;

    public Menu() {
    }

    public Menu(Integer menuId) {
        this.menuId = menuId;
    }

    public Menu(Integer menuId,
                String displayText,
                String menuName) {
        this.menuId = menuId;
        this.displayText = displayText;
        this.menuName = menuName;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Collection<RoleMenu> getRoleMenuCollection() {
        return roleMenuCollection;
    }

    public void setRoleMenuCollection(Collection<RoleMenu> roleMenuCollection) {
        this.roleMenuCollection = roleMenuCollection;
    }
    
    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuId != null ? menuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.menuId == null && other.menuId != null) || (this.menuId != null && !this.menuId.equals(other.menuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Menu[menuId=" + menuId + "]";
    }
}
