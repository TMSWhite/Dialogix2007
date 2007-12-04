/*
 * User.java
 * 
 * Created on Nov 2, 2007, 11:15:12 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "dialogixuser")
@NamedQueries({@NamedQuery(name = "DialogixUser.findByUserID", query = "SELECT u FROM DialogixUser u WHERE u.userID = :userID"), @NamedQuery(name = "DialogixUser.findByUserName", query = "SELECT u FROM DialogixUser u WHERE u.userName = :userName"), @NamedQuery(name = "DialogixUser.findByPwd", query = "SELECT u FROM DialogixUser u WHERE u.pwd = :pwd"), @NamedQuery(name = "DialogxUser.findByFirstName", query = "SELECT u FROM DialogixUser u WHERE u.firstName = :firstName"), @NamedQuery(name = "DialogixUser.findByLastName", query = "SELECT u FROM DialogixUser u WHERE u.lastName = :lastName"), @NamedQuery(name = "DialogixUser.findByEmail", query = "SELECT u FROM DialogixUser u WHERE u.email = :email"), @NamedQuery(name = "DialogixUser.findByPhone", query = "SELECT u FROM DialogixUser u WHERE u.phone = :phone")})
public class DialogixUser implements Serializable {
    @TableGenerator(name="User_Generator", pkColumnValue="User", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="User_Generator")
    @Column(name = "User_ID", nullable = false)
    private Integer userID;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "pwd", nullable = false)
    private String pwd;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<InstrumentSession> instrumentSessionCollection;

    public DialogixUser() {
    }

    public DialogixUser(Integer userID) {
        this.userID = userID;
    }

    public DialogixUser(Integer userID, String userName, String pwd, String firstName, String lastName, String email, String phone) {
        this.userID = userID;
        this.userName = userName;
        this.pwd = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<InstrumentSession> getInstrumentSessionCollection() {
        return instrumentSessionCollection;
    }

    public void setInstrumentSessionCollection(Collection<InstrumentSession> instrumentSessionCollection) {
        this.instrumentSessionCollection = instrumentSessionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DialogixUser)) {
            return false;
        }
        DialogixUser other = (DialogixUser) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.DialogixUser[userID=" + userID + "]";
    }

}
