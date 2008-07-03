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
@Table(name = "person")
public class Person implements Serializable {

    @TableGenerator(name = "Person_gen", pkColumnValue = "person", table = "sequence_admin", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Person_gen")
    @Column(name = "person_id", nullable = false)
    private Long personId;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "pwd")
    private String pwd;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @OneToMany(mappedBy = "personId")
    private Collection<InstrumentSession> instrumentSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private Collection<PersonRoleStudy> personRoleStudyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private Collection<SubjectSession> subjectSessionCollection;

    public Person() {
    }

    public Person(Long personId) {
        this.personId = personId;
    }

    public Person(Long personId,
                  String firstName,
                  String lastName,
                  String userName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<InstrumentSession> getInstrumentSessionCollection() {
        return instrumentSessionCollection;
    }

    public void setInstrumentSessionCollection(
        Collection<InstrumentSession> instrumentSessionCollection) {
        this.instrumentSessionCollection = instrumentSessionCollection;
    }

    public Collection<PersonRoleStudy> getPersonRoleStudyCollection() {
        return personRoleStudyCollection;
    }

    public void setPersonRoleStudyCollection(
        Collection<PersonRoleStudy> personRoleStudyCollection) {
        this.personRoleStudyCollection = personRoleStudyCollection;
    }

    public Collection<SubjectSession> getSubjectSessionCollection() {
        return subjectSessionCollection;
    }

    public void setSubjectSessionCollection(
        Collection<SubjectSession> subjectSessionCollection) {
        this.subjectSessionCollection = subjectSessionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personId != null ? personId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personId == null && other.personId != null) || (this.personId != null && !this.personId.equals(other.personId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.Person[personId=" + personId + "]";
    }
}
