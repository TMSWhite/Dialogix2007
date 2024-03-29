/*
 * InstrumentHash.java
 * 
 * Created on Nov 2, 2007, 11:15:05 AM
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_hash")
@NamedQueries({@NamedQuery(name = "InstrumentHash.findByInstrumentHashID", query = "SELECT i FROM InstrumentHash i WHERE i.instrumentHashID = :instrumentHashID"), @NamedQuery(name = "InstrumentHash.findByNumVars", query = "SELECT i FROM InstrumentHash i WHERE i.numVars = :numVars"), @NamedQuery(name = "InstrumentHash.findByVarListMD5", query = "SELECT i FROM InstrumentHash i WHERE i.varListMD5 = :varListMD5"), @NamedQuery(name = "InstrumentHash.findByInstrumentMD5", query = "SELECT i FROM InstrumentHash i WHERE i.instrumentMD5 = :instrumentMD5"), @NamedQuery(name = "InstrumentHash.findByNumLanguages", query = "SELECT i FROM InstrumentHash i WHERE i.numLanguages = :numLanguages"), @NamedQuery(name = "InstrumentHash.findByNumInstructions", query = "SELECT i FROM InstrumentHash i WHERE i.numInstructions = :numInstructions"), @NamedQuery(name = "InstrumentHash.findByNumEquations", query = "SELECT i FROM InstrumentHash i WHERE i.numEquations = :numEquations"), @NamedQuery(name = "InstrumentHash.findByNumQuestions", query = "SELECT i FROM InstrumentHash i WHERE i.numQuestions = :numQuestions"), @NamedQuery(name = "InstrumentHash.findByNumBranches", query = "SELECT i FROM InstrumentHash i WHERE i.numBranches = :numBranches"), @NamedQuery(name = "InstrumentHash.findByNumTailorings", query = "SELECT i FROM InstrumentHash i WHERE i.numTailorings = :numTailorings")})
public class InstrumentHash implements Serializable {
    @TableGenerator(name="InstrumentHash_Generator", pkColumnValue="InstrumentHash", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="InstrumentHash_Generator")
    @Column(name = "InstrumentHash_ID", nullable = false)
    private Integer instrumentHashID;
    @Column(name = "NumVars", nullable = false)
    private int numVars;
    @Column(name = "VarListMD5", nullable = false)
    private String varListMD5;
    @Column(name = "InstrumentMD5", nullable = false)
    private String instrumentMD5;
    @Column(name = "NumLanguages", nullable = false)
    private int numLanguages;
    @Column(name = "NumInstructions", nullable = false)
    private int numInstructions;
    @Column(name = "NumEquations", nullable = false)
    private int numEquations;
    @Column(name = "NumQuestions", nullable = false)
    private int numQuestions;
    @Column(name = "NumBranches", nullable = false)
    private int numBranches;
    @Column(name = "NumTailorings", nullable = false)
    private int numTailorings;
    @JoinColumn(name = "LanguageList_ID", referencedColumnName = "LanguageList_ID")
    @ManyToOne
    private LanguageList languageListID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentHashID")
    private Collection<InstrumentVersion> instrumentVersionCollection;

    public InstrumentHash() {
    }

    public InstrumentHash(Integer instrumentHashID) {
        this.instrumentHashID = instrumentHashID;
    }

    public InstrumentHash(Integer instrumentHashID, int numVars, String varListMD5, String instrumentMD5, int numLanguages, int numInstructions, int numEquations, int numQuestions, int numBranches, int numTailorings) {
        this.instrumentHashID = instrumentHashID;
        this.numVars = numVars;
        this.varListMD5 = varListMD5;
        this.instrumentMD5 = instrumentMD5;
        this.numLanguages = numLanguages;
        this.numInstructions = numInstructions;
        this.numEquations = numEquations;
        this.numQuestions = numQuestions;
        this.numBranches = numBranches;
        this.numTailorings = numTailorings;
    }

    public Integer getInstrumentHashID() {
        return instrumentHashID;
    }

    public void setInstrumentHashID(Integer instrumentHashID) {
        this.instrumentHashID = instrumentHashID;
    }

    public int getNumVars() {
        return numVars;
    }

    public void setNumVars(int numVars) {
        this.numVars = numVars;
    }

    public String getVarListMD5() {
        return varListMD5;
    }

    public void setVarListMD5(String varListMD5) {
        this.varListMD5 = varListMD5;
    }

    public String getInstrumentMD5() {
        return instrumentMD5;
    }

    public void setInstrumentMD5(String instrumentMD5) {
        this.instrumentMD5 = instrumentMD5;
    }

    public int getNumLanguages() {
        return numLanguages;
    }

    public void setNumLanguages(int numLanguages) {
        this.numLanguages = numLanguages;
    }

    public int getNumInstructions() {
        return numInstructions;
    }

    public void setNumInstructions(int numInstructions) {
        this.numInstructions = numInstructions;
    }

    public int getNumEquations() {
        return numEquations;
    }

    public void setNumEquations(int numEquations) {
        this.numEquations = numEquations;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public int getNumBranches() {
        return numBranches;
    }

    public void setNumBranches(int numBranches) {
        this.numBranches = numBranches;
    }

    public int getNumTailorings() {
        return numTailorings;
    }

    public void setNumTailorings(int numTailorings) {
        this.numTailorings = numTailorings;
    }

    public LanguageList getLanguageListID() {
        return languageListID;
    }

    public void setLanguageListID(LanguageList languageListID) {
        this.languageListID = languageListID;
    }

    public Collection<InstrumentVersion> getInstrumentVersionCollection() {
        return instrumentVersionCollection;
    }

    public void setInstrumentVersionCollection(Collection<InstrumentVersion> instrumentVersionCollection) {
        this.instrumentVersionCollection = instrumentVersionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentHashID != null ? instrumentHashID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentHash)) {
            return false;
        }
        InstrumentHash other = (InstrumentHash) object;
        if ((this.instrumentHashID == null && other.instrumentHashID != null) || (this.instrumentHashID != null && !this.instrumentHashID.equals(other.instrumentHashID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentHash[instrumentHashID=" + instrumentHashID + "]";
    }

}
