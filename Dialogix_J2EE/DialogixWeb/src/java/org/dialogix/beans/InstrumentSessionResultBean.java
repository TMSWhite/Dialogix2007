/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.beans;

/**
 *
 * @author Coevtmw
 */
public class InstrumentSessionResultBean implements java.io.Serializable {
    private Long instrumentSessionId;
    private Integer dataElementSequence;
    private Long varNameId;
    private String varNameString;
    private String answerCode;
    private Integer nullFlavorId;

    public InstrumentSessionResultBean() {
    }

    public InstrumentSessionResultBean(Long instrumentSessionId,
                                       Integer dataElementSequence,
                                       Long varNameId,
                                       String varNameString,
                                       String answerCode,
                                       Integer nullFlavorId) {
        this.instrumentSessionId = instrumentSessionId;
        this.dataElementSequence = dataElementSequence;
        this.varNameId = varNameId;
        this.varNameString = varNameString;
        this.answerCode = answerCode;
        this.nullFlavorId = nullFlavorId;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public Integer getDataElementSequence() {
        return dataElementSequence;
    }

    public void setDataElementSequence(Integer dataElementSequence) {
        this.dataElementSequence = dataElementSequence;
    }

    public Long getInstrumentSessionId() {
        return instrumentSessionId;
    }

    public void setInstrumentSessionId(Long instrumentSessionId) {
        this.instrumentSessionId = instrumentSessionId;
    }

    public Integer getNullFlavorId() {
        return nullFlavorId;
    }

    public void setNullFlavorId(Integer nullFlavorId) {
        this.nullFlavorId = nullFlavorId;
    }

    public Long getVarNameId() {
        return varNameId;
    }

    public void setVarNameId(Long varNameId) {
        this.varNameId = varNameId;
    }

    public String getVarNameString() {
        return varNameString;
    }

    public void setVarNameString(String varNameString) {
        this.varNameString = varNameString;
    }
}
