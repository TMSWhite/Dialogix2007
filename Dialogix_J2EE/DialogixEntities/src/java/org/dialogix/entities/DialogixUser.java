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
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "dialogix_user")
public class DialogixUser implements Serializable {
    @TableGenerator(name="DialogixUser_Gen", pkColumnValue="DialogixUser", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DialogixUser_Gen")
    @Column(name = "dialogix_user_id", nullable = false)
    private Integer dialogixUserID;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dialogixUserID")
    private Collection<InstrumentSession> instrumentSessionCollection;

    public DialogixUser() {
    }

    public DialogixUser(Integer dialogixUserID) {
        this.dialogixUserID = dialogixUserID;
    }

    public DialogixUser(Integer dialogixUserID, String userName, String pwd, String firstName, String lastName, String email, String phone) {
        this.dialogixUserID = dialogixUserID;
        this.userName = userName;
        this.pwd = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Integer getDialogixUserID() {
        return dialogixUserID;
    }

    public void setDialogixUserID(Integer dialogixUserID) {
        this.dialogixUserID = dialogixUserID;
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
        hash += (dialogixUserID != null ? dialogixUserID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DialogixUser)) {
            return false;
        }
        DialogixUser other = (DialogixUser) object;
        if ((this.dialogixUserID == null && other.dialogixUserID != null) || (this.dialogixUserID != null && !this.dialogixUserID.equals(other.dialogixUserID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.DialogixUser[dialogixUserID=" + dialogixUserID + "]";
    }

}
