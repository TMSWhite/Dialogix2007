/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.export;

/**
 *
 * @author Coevtmw
 */
public class InstrumentVersionView {
    private Long instrumentVersionID;
    private String instrumentName;

    InstrumentVersionView(String instrumentName,
                          Long instrumentVersionID) {
        this.instrumentName = instrumentName;
        this.instrumentVersionID = instrumentVersionID;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public Long getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(Long instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }
}
