/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.beans;

/**
 *
 * @author Coevtmw
 */
public class InstrumentVersionView {
    private Long instrumentVersionID;
    private String instrumentName;
    private String instrumentVersion;
    private Long numSessions;
    private Integer numEquations;
    private Integer numQuestions;
    private Integer numBranches;
    private Integer numLanguages;
    private Integer numTailorings;
    private Integer numVars;
    private Integer numGroups;
    private Integer numInstructions;
    private String instrumentVersionFileName;

    public InstrumentVersionView(Long instrumentVersionID,
                                 String instrumentName,
                                 String instrumentVersion,
                                 Long numSessions,
                                 Integer numEquations,
                                 Integer numQuestions,
                                 Integer numBranches,
                                 Integer numLanguages,
                                 Integer numTailorings,
                                 Integer numVars,
                                 Integer numGroups,
                                 Integer numInstructions,
                                 String instrumentVersionFileName) {
        this.instrumentVersionID = instrumentVersionID;
        this.instrumentName = instrumentName;
        this.instrumentVersion = instrumentVersion;
        this.numSessions = numSessions;
        this.numEquations = numEquations;
        this.numQuestions = numQuestions;
        this.numBranches = numBranches;
        this.numLanguages = numLanguages;
        this.numTailorings = numTailorings;
        this.numVars = numVars;
        this.numGroups = numGroups;
        this.numInstructions = numInstructions;
        this.instrumentVersionFileName = instrumentVersionFileName;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentVersion() {
        return instrumentVersion;
    }

    public void setInstrumentVersion(String instrumentVersion) {
        this.instrumentVersion = instrumentVersion;
    }

    public Long getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(Long instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public Integer getNumBranches() {
        return numBranches;
    }

    public void setNumBranches(Integer numBranches) {
        this.numBranches = numBranches;
    }

    public Integer getNumEquations() {
        return numEquations;
    }

    public void setNumEquations(Integer numEquations) {
        this.numEquations = numEquations;
    }

    public Integer getNumGroups() {
        return numGroups;
    }

    public void setNumGroups(Integer numGroups) {
        this.numGroups = numGroups;
    }

    public Integer getNumInstructions() {
        return numInstructions;
    }

    public void setNumInstructions(Integer numInstructions) {
        this.numInstructions = numInstructions;
    }

    public Integer getNumLanguages() {
        return numLanguages;
    }

    public void setNumLanguages(Integer numLanguages) {
        this.numLanguages = numLanguages;
    }

    public Integer getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(Integer numQuestions) {
        this.numQuestions = numQuestions;
    }

    public Long getNumSessions() {
        return numSessions;
    }

    public void setNumSessions(Long numSessions) {
        this.numSessions = numSessions;
    }

    public Integer getNumTailorings() {
        return numTailorings;
    }

    public void setNumTailorings(Integer numTailorings) {
        this.numTailorings = numTailorings;
    }

    public Integer getNumVars() {
        return numVars;
    }

    public void setNumVars(Integer numVars) {
        this.numVars = numVars;
    }

    public String getInstrumentVersionFileName() {
        return instrumentVersionFileName;
    }

    public void setInstrumentVersionFileName(String instrumentVersionFileName) {
        this.instrumentVersionFileName = instrumentVersionFileName;
    }
    
    
}