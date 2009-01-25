/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.beans;

import java.util.Date;

/**
 *
 * @author coevtmw
 */
public class MyInstrumentSession {
    Long instrumentVersionId;
    Long instrumentId;
    String instrumentName;
    String versionString;
    Date startTime;
    Long instrumentSessionId;
    Date lastAccessTime;
    String titleForPicklistWhenInProgress;
    Integer finished;
    Integer displayNum;

    public MyInstrumentSession(Long instrumentVersionId,
                                Long instrumentId,
                                String instrumentName,
                                String versionString,
                                Date startTime,
                                Long instrumentSessionId,
                                Date lastAccessTime,
                                String titleForPicklistWhenInProgress,
                                Integer finished,
                                Integer displayNum) {
        this.instrumentVersionId = instrumentVersionId;
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
        this.versionString = versionString;
        this.startTime = startTime;
        this.instrumentSessionId = instrumentSessionId;
        this.lastAccessTime = lastAccessTime;
        this.titleForPicklistWhenInProgress = titleForPicklistWhenInProgress;
        this.finished = finished;
        this.displayNum = displayNum;
    }
    
    

    public Integer getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(Integer displayNum) {
        this.displayNum = displayNum;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public Long getInstrumentSessionId() {
        return instrumentSessionId;
    }

    public void setInstrumentSessionId(Long instrumentSessionId) {
        this.instrumentSessionId = instrumentSessionId;
    }

    public Long getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(Long instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getTitleForPicklistWhenInProgress() {
        return titleForPicklistWhenInProgress;
    }

    public void setTitleForPicklistWhenInProgress(String titleForPicklistWhenInProgress) {
        this.titleForPicklistWhenInProgress = titleForPicklistWhenInProgress;
    }

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }
    
    
}
