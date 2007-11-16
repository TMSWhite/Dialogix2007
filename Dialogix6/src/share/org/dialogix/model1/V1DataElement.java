package org.dialogix.model1;

import java.util.Date;

public class V1DataElement  {
    private Integer v1DataElementID;
    private int v1InstrumentSessionID;
    private String varName;
    private int dataElementSequence;
    private int groupNum;
    private int displayNum;
    private String languageCode;
    private String questionAsAsked;
    private String answerCode;
    private String answerString;
    private Date timeStamp;
    private long whenAsMS;
    private Integer itemVacillation;
    private Integer responseLatency;
    private Integer responseDuration;
    private String comments;

    public V1DataElement() {
    }

    public V1DataElement(Integer v1DataElementID) {
        this.v1DataElementID = v1DataElementID;
    }

    public V1DataElement(Integer v1DataElementID, int v1InstrumentSessionID, String varName, int dataElementSequence, int groupNum, int displayNum, Date timeStamp, long whenAsMS) {
        this.v1DataElementID = v1DataElementID;
        this.v1InstrumentSessionID = v1InstrumentSessionID;
        this.varName = varName;
        this.dataElementSequence = dataElementSequence;
        this.groupNum = groupNum;
        this.displayNum = displayNum;
        this.timeStamp = timeStamp;
        this.whenAsMS = whenAsMS;
    }

    public Integer getV1DataElementID() {
        return v1DataElementID;
    }

    public void setV1DataElementID(Integer v1DataElementID) {
        this.v1DataElementID = v1DataElementID;
    }

    public int getV1InstrumentSessionID() {
        return v1InstrumentSessionID;
    }

    public void setV1InstrumentSessionID(int v1InstrumentSessionID) {
        this.v1InstrumentSessionID = v1InstrumentSessionID;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public int getDataElementSequence() {
        return dataElementSequence;
    }

    public void setDataElementSequence(int dataElementSequence) {
        this.dataElementSequence = dataElementSequence;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getQuestionAsAsked() {
        return questionAsAsked;
    }

    public void setQuestionAsAsked(String questionAsAsked) {
        this.questionAsAsked = questionAsAsked;
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getWhenAsMS() {
        return whenAsMS;
    }

    public void setWhenAsMS(long whenAsMS) {
        this.whenAsMS = whenAsMS;
    }

    public Integer getItemVacillation() {
        return itemVacillation;
    }

    public void setItemVacillation(Integer itemVacillation) {
        this.itemVacillation = itemVacillation;
    }

    public Integer getResponseLatency() {
        return responseLatency;
    }

    public void setResponseLatency(Integer responseLatency) {
        this.responseLatency = responseLatency;
    }

    public Integer getResponseDuration() {
        return responseDuration;
    }

    public void setResponseDuration(Integer responseDuration) {
        this.responseDuration = responseDuration;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
