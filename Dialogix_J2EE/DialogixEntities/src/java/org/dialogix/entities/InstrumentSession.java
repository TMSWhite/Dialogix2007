/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_session")
public class InstrumentSession implements Serializable {

    @TableGenerator(name = "InstrumentSession_gen", pkColumnValue = "instrument_session", table = "sequence_data", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "InstrumentSession_gen")
    @Column(name = "instrument_session_id", nullable = false)
    private Long instrumentSessionId;
    @Column(name = "browser")
    private String browser;
    @Column(name = "current_group", nullable = false)
    private int currentGroup;
    @Column(name = "current_var_num", nullable = false)
    private int currentVarNum;
    @Column(name = "display_num", nullable = false)
    private int displayNum;
    @Column(name = "finished")
    private Integer finished;
    @Column(name = "instrument_starting_group", nullable = false)
    private int instrumentStartingGroup;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "language_code", nullable = false)
    private String languageCode;
    @Column(name = "last_access_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;
    @Column(name = "max_group_visited")
    private Integer maxGroupVisited;
    @Column(name = "max_var_num_visited")
    private Integer maxVarNumVisited;
    @Column(name = "num_groups")
    private Integer numGroups;
    @Column(name = "num_vars")
    private Integer numVars;
    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "status_msg")
    private String statusMsg;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionId;
    @JoinColumn(name = "action_type_id", referencedColumnName = "action_type_id")
    @ManyToOne
    private ActionType actionTypeId;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;
    @JoinColumn(name = "study_id", referencedColumnName = "study_id")
    @ManyToOne
    private Study studyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentSessionId")
    private Collection<DataElement> dataElementCollection;
    @OneToMany(mappedBy = "instrumentSessionId")
    private Collection<PageUsage> pageUsageCollection;
    @OneToMany(mappedBy = "instrumentSessionId")
    private Collection<SubjectSession> subjectSessionCollection;

    public InstrumentSession() {
    }

    public InstrumentSession(Long instrumentSessionId) {
        this.instrumentSessionId = instrumentSessionId;
    }

    public InstrumentSession(Long instrumentSessionId,
                             int currentGroup,
                             int currentVarNum,
                             int displayNum,
                             int instrumentStartingGroup,
                             String languageCode,
                             Date lastAccessTime,
                             Date startTime) {
        this.instrumentSessionId = instrumentSessionId;
        this.currentGroup = currentGroup;
        this.currentVarNum = currentVarNum;
        this.displayNum = displayNum;
        this.instrumentStartingGroup = instrumentStartingGroup;
        this.languageCode = languageCode;
        this.lastAccessTime = lastAccessTime;
        this.startTime = startTime;
    }

    public Long getInstrumentSessionId() {
        return instrumentSessionId;
    }

    public void setInstrumentSessionId(Long instrumentSessionId) {
        this.instrumentSessionId = instrumentSessionId;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public int getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(int currentGroup) {
        this.currentGroup = currentGroup;
    }

    public int getCurrentVarNum() {
        return currentVarNum;
    }

    public void setCurrentVarNum(int currentVarNum) {
        this.currentVarNum = currentVarNum;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public int getInstrumentStartingGroup() {
        return instrumentStartingGroup;
    }

    public void setInstrumentStartingGroup(int instrumentStartingGroup) {
        this.instrumentStartingGroup = instrumentStartingGroup;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Integer getMaxGroupVisited() {
        return maxGroupVisited;
    }

    public void setMaxGroupVisited(Integer maxGroupVisited) {
        this.maxGroupVisited = maxGroupVisited;
    }

    public Integer getMaxVarNumVisited() {
        return maxVarNumVisited;
    }

    public void setMaxVarNumVisited(Integer maxVarNumVisited) {
        this.maxVarNumVisited = maxVarNumVisited;
    }

    public Integer getNumGroups() {
        return numGroups;
    }

    public void setNumGroups(Integer numGroups) {
        this.numGroups = numGroups;
    }

    public Integer getNumVars() {
        return numVars;
    }

    public void setNumVars(Integer numVars) {
        this.numVars = numVars;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public InstrumentVersion getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(InstrumentVersion instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    public ActionType getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(ActionType actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public Study getStudyId() {
        return studyId;
    }

    public void setStudyId(Study studyId) {
        this.studyId = studyId;
    }    

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }

    public void setDataElementCollection(
        Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
    }

    public Collection<PageUsage> getPageUsageCollection() {
        return pageUsageCollection;
    }

    public void setPageUsageCollection(Collection<PageUsage> pageUsageCollection) {
        this.pageUsageCollection = pageUsageCollection;
    }

    public Collection<SubjectSession> getSubjectSessionCollection() {
        return subjectSessionCollection;
    }

    public void setSubjectSessionCollection(
        Collection<SubjectSession> subjectSessionCollection) {
        this.subjectSessionCollection = subjectSessionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentSessionId != null ? instrumentSessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentSession)) {
            return false;
        }
        InstrumentSession other = (InstrumentSession) object;
        if ((this.instrumentSessionId == null && other.instrumentSessionId != null) || (this.instrumentSessionId != null && !this.instrumentSessionId.equals(other.instrumentSessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentSession[instrumentSessionId=" + instrumentSessionId + "]";
    }
}
