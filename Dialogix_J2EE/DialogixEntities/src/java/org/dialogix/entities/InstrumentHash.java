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
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_hashes")
public class InstrumentHash implements Serializable {
    @TableGenerator(name="instrument_hash_gen", pkColumnValue="instrument_hash", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="instrument_hash_gen")
    @Column(name = "id", nullable = false)
    private Long instrumentHashID;
    @Column(name = "num_vars", nullable = false)
    private int numVars;
    @Column(name = "var_list_md5", nullable = false)
    private String varListMD5;
    @Column(name = "instrument_md5", nullable = false)
    private String instrumentMD5;
    @Column(name = "num_languages", nullable = false)
    private int numLanguages;
    @Column(name = "num_instructions", nullable = false)
    private int numInstructions;
    @Column(name = "num_equations", nullable = false)
    private int numEquations;
    @Column(name = "num_questions", nullable = false)
    private int numQuestions;
    @Column(name = "num_branches", nullable = false)
    private int numBranches;
    @Column(name = "num_tailorings", nullable = false)
    private int numTailorings;
    @Column(name = "num_groups")
    private Integer numGroups;     
    @JoinColumn(name = "language_list_id", referencedColumnName="id")
    @ManyToOne
    private LanguageList languageListID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentHashID")
    private Collection<InstrumentVersion> instrumentVersionCollection;

    public InstrumentHash() {
    }

    public InstrumentHash(Long instrumentHashID) {
        this.instrumentHashID = instrumentHashID;
    }

    public InstrumentHash(Long instrumentHashID, int numVars, String varListMD5, String instrumentMD5, int numLanguages, int numInstructions, int numEquations, int numQuestions, int numBranches, int numTailorings, int numGroups) {
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
        this.numGroups = numGroups;
    }

    public Long getInstrumentHashID() {
        return instrumentHashID;
    }

    public void setInstrumentHashID(Long instrumentHashID) {
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
    
    public Integer getNumGroups() {
        return numGroups;
    }

    public void setNumGroups(Integer numGroups) {
        this.numGroups = numGroups;
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
