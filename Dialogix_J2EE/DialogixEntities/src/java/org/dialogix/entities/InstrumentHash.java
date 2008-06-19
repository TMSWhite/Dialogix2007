/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_hash")
public class InstrumentHash implements Serializable {

    @TableGenerator(name = "InstrumentHash_gen", pkColumnValue = "instrument_hash", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "InstrumentHash_gen")
    @Column(name = "instrument_hash_id", nullable = false)
    private Long instrumentHashId;
    @Column(name = "instrument_md5", nullable = false)
    private String instrumentMd5;
    @Column(name = "num_branches", nullable = false)
    private int numBranches;
    @Column(name = "num_equations", nullable = false)
    private int numEquations;
    @Column(name = "num_groups")
    private Integer numGroups;
    @Column(name = "num_instructions", nullable = false)
    private int numInstructions;
    @Column(name = "num_languages", nullable = false)
    private int numLanguages;
    @Column(name = "num_questions", nullable = false)
    private int numQuestions;
    @Column(name = "num_tailorings", nullable = false)
    private int numTailorings;
    @Column(name = "num_vars", nullable = false)
    private int numVars;
    @Column(name = "var_list_md5", nullable = false)
    private String varListMd5;
    @JoinColumn(name = "language_list_id", referencedColumnName = "language_list_id")
    @ManyToOne
    private LanguageList languageListId;
    @OneToMany(mappedBy = "instrumentHashId")
    private Collection<InstrumentVersion> instrumentVersionCollection;

    public InstrumentHash() {
    }

    public InstrumentHash(Long instrumentHashId) {
        this.instrumentHashId = instrumentHashId;
    }

    public InstrumentHash(Long instrumentHashId,
                          String instrumentMd5,
                          int numBranches,
                          int numEquations,
                          int numInstructions,
                          int numLanguages,
                          int numQuestions,
                          int numTailorings,
                          int numVars,
                          String varListMd5) {
        this.instrumentHashId = instrumentHashId;
        this.instrumentMd5 = instrumentMd5;
        this.numBranches = numBranches;
        this.numEquations = numEquations;
        this.numInstructions = numInstructions;
        this.numLanguages = numLanguages;
        this.numQuestions = numQuestions;
        this.numTailorings = numTailorings;
        this.numVars = numVars;
        this.varListMd5 = varListMd5;
    }

    public Long getInstrumentHashId() {
        return instrumentHashId;
    }

    public void setInstrumentHashId(Long instrumentHashId) {
        this.instrumentHashId = instrumentHashId;
    }

    public String getInstrumentMd5() {
        return instrumentMd5;
    }

    public void setInstrumentMd5(String instrumentMd5) {
        this.instrumentMd5 = instrumentMd5;
    }

    public int getNumBranches() {
        return numBranches;
    }

    public void setNumBranches(int numBranches) {
        this.numBranches = numBranches;
    }

    public int getNumEquations() {
        return numEquations;
    }

    public void setNumEquations(int numEquations) {
        this.numEquations = numEquations;
    }

    public Integer getNumGroups() {
        return numGroups;
    }

    public void setNumGroups(Integer numGroups) {
        this.numGroups = numGroups;
    }

    public int getNumInstructions() {
        return numInstructions;
    }

    public void setNumInstructions(int numInstructions) {
        this.numInstructions = numInstructions;
    }

    public int getNumLanguages() {
        return numLanguages;
    }

    public void setNumLanguages(int numLanguages) {
        this.numLanguages = numLanguages;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public int getNumTailorings() {
        return numTailorings;
    }

    public void setNumTailorings(int numTailorings) {
        this.numTailorings = numTailorings;
    }

    public int getNumVars() {
        return numVars;
    }

    public void setNumVars(int numVars) {
        this.numVars = numVars;
    }

    public String getVarListMd5() {
        return varListMd5;
    }

    public void setVarListMd5(String varListMd5) {
        this.varListMd5 = varListMd5;
    }

    public LanguageList getLanguageListId() {
        return languageListId;
    }

    public void setLanguageListId(LanguageList languageListId) {
        this.languageListId = languageListId;
    }

    public Collection<InstrumentVersion> getInstrumentVersionCollection() {
        return instrumentVersionCollection;
    }

    public void setInstrumentVersionCollection(
        Collection<InstrumentVersion> instrumentVersionCollection) {
        this.instrumentVersionCollection = instrumentVersionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentHashId != null ? instrumentHashId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentHash)) {
            return false;
        }
        InstrumentHash other = (InstrumentHash) object;
        if ((this.instrumentHashId == null && other.instrumentHashId != null) || (this.instrumentHashId != null && !this.instrumentHashId.equals(other.instrumentHashId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentHash[instrumentHashId=" + instrumentHashId + "]";
    }
}
