/*
 * Language.java
 * 
 * Created on Oct 22, 2007, 4:09:07 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "language")
@NamedQueries({@NamedQuery(name = "Language.findByLanguageID", query = "SELECT l FROM Language l WHERE l.languageID = :languageID"), @NamedQuery(name = "Language.findByLanguageCode", query = "SELECT l FROM Language l WHERE l.languageCode = :languageCode"), @NamedQuery(name = "Language.findByDescription", query = "SELECT l FROM Language l WHERE l.description = :description")})
public class Language implements Serializable {
    @Id
    @Column(name = "Language_ID", nullable = false)
    private Integer languageID;
    @Column(name = "LanguageCode", nullable = false)
    private String languageCode;
    @Column(name = "Description")
    private String description;

    public Language() {
    }

    public Language(Integer languageID) {
        this.languageID = languageID;
    }

    public Language(Integer languageID, String languageCode) {
        this.languageID = languageID;
        this.languageCode = languageCode;
    }

    public Integer getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Integer languageID) {
        this.languageID = languageID;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (languageID != null ? languageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Language)) {
            return false;
        }
        Language other = (Language) object;
        if ((this.languageID == null && other.languageID != null) || (this.languageID != null && !this.languageID.equals(other.languageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.Language[languageID=" + languageID + "]";
    }

}
