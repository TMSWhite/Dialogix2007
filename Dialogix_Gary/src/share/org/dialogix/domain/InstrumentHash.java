/*
 * InstrumentHash.java
 * 
 * Created on Oct 22, 2007, 4:09:02 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

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
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrumenthash")
@NamedQueries({@NamedQuery(name = "InstrumentHash.findByInstrumentHashID", query = "SELECT i FROM InstrumentHash i WHERE i.instrumentHashID = :instrumentHashID"), @NamedQuery(name = "InstrumentHash.findByNumVars", query = "SELECT i FROM InstrumentHash i WHERE i.numVars = :numVars"), @NamedQuery(name = "InstrumentHash.findByVarListMD5", query = "SELECT i FROM InstrumentHash i WHERE i.varListMD5 = :varListMD5"), @NamedQuery(name = "InstrumentHash.findByInstrumentMD5", query = "SELECT i FROM InstrumentHash i WHERE i.instrumentMD5 = :instrumentMD5"), @NamedQuery(name = "InstrumentHash.findByNumLanguages", query = "SELECT i FROM InstrumentHash i WHERE i.numLanguages = :numLanguages"), @NamedQuery(name = "InstrumentHash.findByNumInstructions", query = "SELECT i FROM InstrumentHash i WHERE i.numInstructions = :numInstructions"), @NamedQuery(name = "InstrumentHash.findByNumEquations", query = "SELECT i FROM InstrumentHash i WHERE i.numEquations = :numEquations"), @NamedQuery(name = "InstrumentHash.findByNumQuestions", query = "SELECT i FROM InstrumentHash i WHERE i.numQuestions = :numQuestions"), @NamedQuery(name = "InstrumentHash.findByNumBranches", query = "SELECT i FROM InstrumentHash i WHERE i.numBranches = :numBranches"), @NamedQuery(name = "InstrumentHash.findByNumTailorings", query = "SELECT i FROM InstrumentHash i WHERE i.numTailorings = :numTailorings")})
public class InstrumentHash implements Serializable {
    @Id
    @Column(name = "InstrumentHash_ID", nullable = false)
    private Integer instrumentHashID;
    @Column(name = "NumVars", nullable = false)
    private short numVars;
    @Column(name = "VarListMD5", nullable = false)
    private String varListMD5;
    @Column(name = "InstrumentMD5", nullable = false)
    private String instrumentMD5;
    @Column(name = "NumLanguages", nullable = false)
    private short numLanguages;
    @Column(name = "NumInstructions", nullable = false)
    private short numInstructions;
    @Column(name = "NumEquations", nullable = false)
    private short numEquations;
    @Column(name = "NumQuestions", nullable = false)
    private short numQuestions;
    @Column(name = "NumBranches", nullable = false)
    private short numBranches;
    @Column(name = "NumTailorings", nullable = false)
    private short numTailorings;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentHashID")
    private Collection<InstrumentVersion> instrumentVersionCollection;
    @JoinColumn(name = "LanguageList_ID", referencedColumnName = "LanguageList_ID")
    @ManyToOne
    private LanguageList languageListID;

    public InstrumentHash() {
    }

    public InstrumentHash(Integer instrumentHashID) {
        this.instrumentHashID = instrumentHashID;
    }

    public InstrumentHash(Integer instrumentHashID, short numVars, String varListMD5, String instrumentMD5, short numLanguages, short numInstructions, short numEquations, short numQuestions, short numBranches, short numTailorings) {
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

    public short getNumVars() {
        return numVars;
    }

    public void setNumVars(short numVars) {
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

    public short getNumLanguages() {
        return numLanguages;
    }

    public void setNumLanguages(short numLanguages) {
        this.numLanguages = numLanguages;
    }

    public short getNumInstructions() {
        return numInstructions;
    }

    public void setNumInstructions(short numInstructions) {
        this.numInstructions = numInstructions;
    }

    public short getNumEquations() {
        return numEquations;
    }

    public void setNumEquations(short numEquations) {
        this.numEquations = numEquations;
    }

    public short getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(short numQuestions) {
        this.numQuestions = numQuestions;
    }

    public short getNumBranches() {
        return numBranches;
    }

    public void setNumBranches(short numBranches) {
        this.numBranches = numBranches;
    }

    public short getNumTailorings() {
        return numTailorings;
    }

    public void setNumTailorings(short numTailorings) {
        this.numTailorings = numTailorings;
    }

    public Collection<InstrumentVersion> getInstrumentVersionCollection() {
        return instrumentVersionCollection;
    }

    public void setInstrumentVersionCollection(Collection<InstrumentVersion> instrumentVersionCollection) {
        this.instrumentVersionCollection = instrumentVersionCollection;
    }

    public LanguageList getLanguageListID() {
        return languageListID;
    }

    public void setLanguageListID(LanguageList languageListID) {
        this.languageListID = languageListID;
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
        return "org.dialogix.domain.InstrumentHash[instrumentHashID=" + instrumentHashID + "]";
    }

}
