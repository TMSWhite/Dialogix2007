/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.beans;

/**
 *
 * @author Coevtmw
 */
public class InstrumentSessionResultBean {
    private Long instrumentSessionID;
    private Integer dataElementSequence;
    private Long varNameID;
    private String varNameString;
    private String answerCode;
    private String answerString;
    private Integer nullFlavorID;

    public InstrumentSessionResultBean() {
    }

    public InstrumentSessionResultBean(Long instrumentSessionID,
                                       Integer dataElementSequence,
                                       Long varNameID,
                                       String varNameString,
                                       String answerCode,
                                       String answerString,
                                       Integer nullFlavorID) {
        this.instrumentSessionID = instrumentSessionID;
        this.dataElementSequence = dataElementSequence;
        this.varNameID = varNameID;
        this.varNameString = varNameString;
        this.answerCode = answerCode;
        this.answerString = answerString;
        this.nullFlavorID = nullFlavorID;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public Integer getDataElementSequence() {
        return dataElementSequence;
    }

    public void setDataElementSequence(Integer dataElementSequence) {
        this.dataElementSequence = dataElementSequence;
    }

    public Long getInstrumentSessionID() {
        return instrumentSessionID;
    }

    public void setInstrumentSessionID(Long instrumentSessionID) {
        this.instrumentSessionID = instrumentSessionID;
    }

    public Integer getNullFlavorID() {
        return nullFlavorID;
    }

    public void setNullFlavorID(Integer nullFlavorID) {
        this.nullFlavorID = nullFlavorID;
    }

    public Long getVarNameID() {
        return varNameID;
    }

    public void setVarNameID(Long varNameID) {
        this.varNameID = varNameID;
    }

    public String getVarNameString() {
        return varNameString;
    }

    public void setVarNameString(String varNameString) {
        this.varNameString = varNameString;
    }
}
