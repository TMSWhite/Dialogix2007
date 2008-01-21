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

    public InstrumentVersionView(String instrumentName,
                          String instrumentVersion,
                          Long instrumentVersionID, 
                          Long numSessions) {
        this.instrumentName = instrumentName;
        this.instrumentVersion = instrumentVersion;
        this.instrumentVersionID = instrumentVersionID;
        this.numSessions = numSessions;
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
    
    public Long getNumSessions() {
        return numSessions;
    }

    public void setNumSessions(Long numSessions) {
        this.numSessions = numSessions;
    }    
}
