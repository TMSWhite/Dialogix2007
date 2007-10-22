/*
 * FunctionName.java
 * 
 * Created on Oct 22, 2007, 4:09:00 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "functionname")
@NamedQueries({@NamedQuery(name = "FunctionName.findByFunctionNameID", query = "SELECT f FROM FunctionName f WHERE f.functionNameID = :functionNameID"), @NamedQuery(name = "FunctionName.findByName", query = "SELECT f FROM FunctionName f WHERE f.name = :name")})
public class FunctionName implements Serializable {
    @Id
    @Column(name = "FunctionName_ID", nullable = false)
    private Integer functionNameID;
    @Column(name = "Name", nullable = false)
    private String name;
    @Lob
    @Column(name = "Syntax", nullable = false)
    private String syntax;
    @Lob
    @Column(name = "Description", nullable = false)
    private String description;
    @Lob
    @Column(name = "Definition", nullable = false)
    private String definition;

    public FunctionName() {
    }

    public FunctionName(Integer functionNameID) {
        this.functionNameID = functionNameID;
    }

    public FunctionName(Integer functionNameID, String name, String syntax, String description, String definition) {
        this.functionNameID = functionNameID;
        this.name = name;
        this.syntax = syntax;
        this.description = description;
        this.definition = definition;
    }

    public Integer getFunctionNameID() {
        return functionNameID;
    }

    public void setFunctionNameID(Integer functionNameID) {
        this.functionNameID = functionNameID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (functionNameID != null ? functionNameID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FunctionName)) {
            return false;
        }
        FunctionName other = (FunctionName) object;
        if ((this.functionNameID == null && other.functionNameID != null) || (this.functionNameID != null && !this.functionNameID.equals(other.functionNameID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.FunctionName[functionNameID=" + functionNameID + "]";
    }

}
