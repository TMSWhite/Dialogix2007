/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    /* Legacy Reserved Words from Schedule */
    @Column(name="active_button_prefix")
    private String activeButtonPrefix;
    @Column(name="active_button_suffix")
    private String activeButtonSuffix;
    @Column(name="allow_comments")
    private Boolean allowComments;
    @Column(name="allow_dont_understand")
    private Boolean allowDontUnderstand;
    @Column(name="allow_jump_to")
    private Boolean allowJumpTo;
    @Column(name="allow_language_switching")
    private Boolean allowLanguageSwitching;
    @Column(name="allow_refused")
    private Boolean allowRefused;
    @Column(name="allow_unknown")
    private Boolean allowUnknown;
    @Column(name="always_show_admin_icons")
    private Boolean alwaysShowAdminIcons;
    @Column(name="answer_option_field_width")
    private Integer answerOptionFieldWidth;
    @Column(name="autogen_option_num")
    private Boolean autogenOptionNum;
    @Column(name="comment_icon_off")
    private String commentIconOff;
    @Column(name="comment_icon_on")
    private String commentIconOn;
    @Column(name="completed_dir")
    private String completedDir;
    @Column(name="debug_mode")
    private Boolean debugMode;
    @Column(name="developer_mode")
    private Boolean developerMode;
    @Column(name="disallow_comments")
    private Boolean disallowComments;
    @Column(name="dont_understand_icon_off")
    private String dontUnderstandIconOff;
    @Column(name="dont_understand_icon_on")
    private String dontUnderstandIconOn;
    @Column(name="floppy_dir")
    private String floppyDir;
    @Column(name="header_msg")
    private String headerMsg;
    @Column(name="help_icon")
    private String helpIcon;
    @Column(name="icon")
    private String icon;
    @Column(name="image_files_dir")
    private String imageFilesDir;
    @Column(name="jump_to_first_unasked")
    private Boolean jumpToFirstUnasked;
    @Column(name="loaded_from")
    private String loadedFrom;
    @Column(name="max_text_len_for_combo")
    private Integer maxTextLenForCombo;
    @Column(name="password_for_admin_mode")
    private String passwordForAdminMode;
    @Column(name="redirect_on_finish_delay")
    private String redirectOnFinishDelay;
    @Column(name="redirect_on_finish_msg")
    private String redirectOnFinishMsg;
    @Column(name="redirect_on_finish_url")
    private String redirectOnFinishUrl;
    @Column(name="refused_icon_off")
    private String refusedIconOff;
    @Column(name="refused_icon_on")
    private String refusedIconOn;
    @Column(name="schedule_dir")
    private String scheduleDir;
    @Column(name="schedule_source")
    private String scheduleSource;
    @Column(name="sched_version_major")
    private String schedVersionMajor;
    @Column(name="sched_version_minor")
    private String schedVersionMinor;
    @Column(name="set_default_focus")
    private Boolean setDefaultFocus;
    @Column(name="show_admin_icons")
    private Boolean showAdminIcons;
    @Column(name="show_question_ref")
    private Boolean showQuestionRef;
    @Column(name="show_save_to_floppy_in_admin_mode")
    private Boolean showSaveToFloppyInAdminMode;
    @Column(name="suspend_to_floppy")
    private Boolean suspendToFloppy;
    @Column(name="swap_next_and_previous")
    private Boolean swapNextAndPrevious;
    @Column(name="title_for_picklist_when_in_progress")
    private String titleForPicklistWhenInProgress;
    @Column(name="title")
    private String title;
    @Column(name="unknown_icon_off")
    private String unknownIconOff;
    @Column(name="unknown_icon_on")
    private String unknownIconOn;
    @Column(name="working_dir")
    private String working_Dir;
    @Column(name="wrap_admin_icons")
    private Boolean wrapAdminIcons;    

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
    
    public String getActiveButtonPrefix() {
        return activeButtonPrefix;
    }

    public void setActiveButtonPrefix(String activeButtonPrefix) {
        this.activeButtonPrefix = activeButtonPrefix;
    }

    public String getActiveButtonSuffix() {
        return activeButtonSuffix;
    }

    public void setActiveButtonSuffix(String activeButtonSuffix) {
        this.activeButtonSuffix = activeButtonSuffix;
    }

    public Boolean getAllowComments() {
        return allowComments;
    }

    public void setAllowComments(Boolean allowComments) {
        this.allowComments = allowComments;
    }

    public Boolean getAllowDontUnderstand() {
        return allowDontUnderstand;
    }

    public void setAllowDontUnderstand(Boolean allowDontUnderstand) {
        this.allowDontUnderstand = allowDontUnderstand;
    }

    public Boolean getAllowJumpTo() {
        return allowJumpTo;
    }

    public void setAllowJumpTo(Boolean allowJumpTo) {
        this.allowJumpTo = allowJumpTo;
    }

    public Boolean getAllowLanguageSwitching() {
        return allowLanguageSwitching;
    }

    public void setAllowLanguageSwitching(Boolean allowLanguageSwitching) {
        this.allowLanguageSwitching = allowLanguageSwitching;
    }

    public Boolean getAllowRefused() {
        return allowRefused;
    }

    public void setAllowRefused(Boolean allowRefused) {
        this.allowRefused = allowRefused;
    }

    public Boolean getAllowUnknown() {
        return allowUnknown;
    }

    public void setAllowUnknown(Boolean allowUnknown) {
        this.allowUnknown = allowUnknown;
    }

    public Boolean getAlwaysShowAdminIcons() {
        return alwaysShowAdminIcons;
    }

    public void setAlwaysShowAdminIcons(Boolean alwaysShowAdminIcons) {
        this.alwaysShowAdminIcons = alwaysShowAdminIcons;
    }

    public Integer getAnswerOptionFieldWidth() {
        return answerOptionFieldWidth;
    }

    public void setAnswerOptionFieldWidth(Integer answerOptionFieldWidth) {
        this.answerOptionFieldWidth = answerOptionFieldWidth;
    }

    public Boolean getAutogenOptionNum() {
        return autogenOptionNum;
    }

    public void setAutogenOptionNum(Boolean autogenOptionNum) {
        this.autogenOptionNum = autogenOptionNum;
    }

    public String getCommentIconOff() {
        return commentIconOff;
    }

    public void setCommentIconOff(String commentIconOff) {
        this.commentIconOff = commentIconOff;
    }

    public String getCommentIconOn() {
        return commentIconOn;
    }

    public void setCommentIconOn(String commentIconOn) {
        this.commentIconOn = commentIconOn;
    }

    public String getCompletedDir() {
        return completedDir;
    }

    public void setCompletedDir(String completedDir) {
        this.completedDir = completedDir;
    }

    public Boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Boolean debugMode) {
        this.debugMode = debugMode;
    }

    public Boolean getDeveloperMode() {
        return developerMode;
    }

    public void setDeveloperMode(Boolean developerMode) {
        this.developerMode = developerMode;
    }

    public Boolean getDisallowComments() {
        return disallowComments;
    }

    public void setDisallowComments(Boolean disallowComments) {
        this.disallowComments = disallowComments;
    }

    public String getDontUnderstandIconOff() {
        return dontUnderstandIconOff;
    }

    public void setDontUnderstandIconOff(String dontUnderstandIconOff) {
        this.dontUnderstandIconOff = dontUnderstandIconOff;
    }

    public String getDontUnderstandIconOn() {
        return dontUnderstandIconOn;
    }

    public void setDontUnderstandIconOn(String dontUnderstandIconOn) {
        this.dontUnderstandIconOn = dontUnderstandIconOn;
    }

    public String getFloppyDir() {
        return floppyDir;
    }

    public void setFloppyDir(String floppyDir) {
        this.floppyDir = floppyDir;
    }

    public String getHeaderMsg() {
        return headerMsg;
    }

    public void setHeaderMsg(String headerMsg) {
        this.headerMsg = headerMsg;
    }

    public String getHelpIcon() {
        return helpIcon;
    }

    public void setHelpIcon(String helpIcon) {
        this.helpIcon = helpIcon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImageFilesDir() {
        return imageFilesDir;
    }

    public void setImageFilesDir(String imageFilesDir) {
        this.imageFilesDir = imageFilesDir;
    }

    public Boolean getJumpToFirstUnasked() {
        return jumpToFirstUnasked;
    }

    public void setJumpToFirstUnasked(Boolean jumpToFirstUnasked) {
        this.jumpToFirstUnasked = jumpToFirstUnasked;
    }

    public String getLoadedFrom() {
        return loadedFrom;
    }

    public void setLoadedFrom(String loadedFrom) {
        this.loadedFrom = loadedFrom;
    }

    public Integer getMaxTextLenForCombo() {
        return maxTextLenForCombo;
    }

    public void setMaxTextLenForCombo(Integer maxTextLenForCombo) {
        this.maxTextLenForCombo = maxTextLenForCombo;
    }

    public String getPasswordForAdminMode() {
        return passwordForAdminMode;
    }

    public void setPasswordForAdminMode(String passwordForAdminMode) {
        this.passwordForAdminMode = passwordForAdminMode;
    }

    public String getRedirectOnFinishDelay() {
        return redirectOnFinishDelay;
    }

    public void setRedirectOnFinishDelay(String redirectOnFinishDelay) {
        this.redirectOnFinishDelay = redirectOnFinishDelay;
    }

    public String getRedirectOnFinishMsg() {
        return redirectOnFinishMsg;
    }

    public void setRedirectOnFinishMsg(String redirectOnFinishMsg) {
        this.redirectOnFinishMsg = redirectOnFinishMsg;
    }

    public String getRedirectOnFinishUrl() {
        return redirectOnFinishUrl;
    }

    public void setRedirectOnFinishUrl(String redirectOnFinishUrl) {
        this.redirectOnFinishUrl = redirectOnFinishUrl;
    }

    public String getRefusedIconOff() {
        return refusedIconOff;
    }

    public void setRefusedIconOff(String refusedIconOff) {
        this.refusedIconOff = refusedIconOff;
    }

    public String getRefusedIconOn() {
        return refusedIconOn;
    }

    public void setRefusedIconOn(String refusedIconOn) {
        this.refusedIconOn = refusedIconOn;
    }

    public String getSchedVersionMajor() {
        return schedVersionMajor;
    }

    public void setSchedVersionMajor(String schedVersionMajor) {
        this.schedVersionMajor = schedVersionMajor;
    }

    public String getSchedVersionMinor() {
        return schedVersionMinor;
    }

    public void setSchedVersionMinor(String schedVersionMinor) {
        this.schedVersionMinor = schedVersionMinor;
    }

    public String getScheduleDir() {
        return scheduleDir;
    }

    public void setScheduleDir(String scheduleDir) {
        this.scheduleDir = scheduleDir;
    }

    public String getScheduleSource() {
        return scheduleSource;
    }

    public void setScheduleSource(String scheduleSource) {
        this.scheduleSource = scheduleSource;
    }

    public Boolean getSetDefaultFocus() {
        return setDefaultFocus;
    }

    public void setSetDefaultFocus(Boolean setDefaultFocus) {
        this.setDefaultFocus = setDefaultFocus;
    }

    public Boolean getShowAdminIcons() {
        return showAdminIcons;
    }

    public void setShowAdminIcons(Boolean showAdminIcons) {
        this.showAdminIcons = showAdminIcons;
    }

    public Boolean getShowQuestionRef() {
        return showQuestionRef;
    }

    public void setShowQuestionRef(Boolean showQuestionRef) {
        this.showQuestionRef = showQuestionRef;
    }

    public Boolean getShowSaveToFloppyInAdminMode() {
        return showSaveToFloppyInAdminMode;
    }

    public void setShowSaveToFloppyInAdminMode(Boolean showSaveToFloppyInAdminMode) {
        this.showSaveToFloppyInAdminMode = showSaveToFloppyInAdminMode;
    }

    public Boolean getSuspendToFloppy() {
        return suspendToFloppy;
    }

    public void setSuspendToFloppy(Boolean suspendToFloppy) {
        this.suspendToFloppy = suspendToFloppy;
    }

    public Boolean getSwapNextAndPrevious() {
        return swapNextAndPrevious;
    }

    public void setSwapNextAndPrevious(Boolean swapNextAndPrevious) {
        this.swapNextAndPrevious = swapNextAndPrevious;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleForPicklistWhenInProgress() {
        return titleForPicklistWhenInProgress;
    }

    public void setTitleForPicklistWhenInProgress(String titleForPicklistWhenInProgress) {
        this.titleForPicklistWhenInProgress = titleForPicklistWhenInProgress;
    }

    public String getUnknownIconOff() {
        return unknownIconOff;
    }

    public void setUnknownIconOff(String unknownIconOff) {
        this.unknownIconOff = unknownIconOff;
    }

    public String getUnknownIconOn() {
        return unknownIconOn;
    }

    public void setUnknownIconOn(String unknownIconOn) {
        this.unknownIconOn = unknownIconOn;
    }

    public String getWorking_Dir() {
        return working_Dir;
    }

    public void setWorking_Dir(String working_Dir) {
        this.working_Dir = working_Dir;
    }

    public Boolean getWrapAdminIcons() {
        return wrapAdminIcons;
    }

    public void setWrapAdminIcons(Boolean wrapAdminIcons) {
        this.wrapAdminIcons = wrapAdminIcons;
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
    
    /* Below are static values neeeded for mapping from String representation of variable to internal bean access */
    public String getReserved(String which) {
        Integer val = -1;
        val = ReservedWordMap.get(which);
        if (val == null) {
            return "";
        }        
        switch (val) {
            case ACTIVE_BUTTON_PREFIX:
                return this.getActiveButtonPrefix();
            case ACTIVE_BUTTON_SUFFIX:
                return this.getActiveButtonSuffix();
            case ALLOW_COMMENTS:
                return this.getAllowComments().toString();
            case ALLOW_DONT_UNDERSTAND:
                return this.getAllowDontUnderstand().toString();
            case ALLOW_JUMP_TO:
                return this.getAllowJumpTo().toString();
            case ALLOW_LANGUAGE_SWITCHING:
                return this.getAllowLanguageSwitching().toString();
            case ALLOW_REFUSED:
                return this.getAllowRefused().toString();
            case ALLOW_UNKNOWN:
                return this.getAllowUnknown().toString();
            case ALWAYS_SHOW_ADMIN_ICONS:
                return this.getAlwaysShowAdminIcons().toString();
            case ANSWER_OPTION_FIELD_WIDTH:
                return this.getAnswerOptionFieldWidth().toString();
            case AUTOGEN_OPTION_NUM:
                return this.getAutogenOptionNum().toString();
            case COMMENT_ICON_OFF:
                return this.getCommentIconOff();
            case COMMENT_ICON_ON:
                return this.getCommentIconOn();
            case COMPLETED_DIR:
                return this.getCompletedDir();
            case DEBUG_MODE:
                return this.getDebugMode().toString();
            case DEVELOPER_MODE:
                return this.getDeveloperMode().toString();
            case DISALLOW_COMMENTS:
                return this.getDisallowComments().toString();
            case DONT_UNDERSTAND_ICON_OFF:
                return this.getDontUnderstandIconOff();
            case DONT_UNDERSTAND_ICON_ON:
                return this.getDontUnderstandIconOn();
            case FLOPPY_DIR:
                return this.getFloppyDir();
            case HEADER_MSG:
                return this.getHeaderMsg();
            case HELP_ICON:
                return this.getHelpIcon();
            case ICON:
                return this.getIcon();
            case IMAGE_FILES_DIR:
                return this.getImageFilesDir();
            case JUMP_TO_FIRST_UNASKED:
                return this.getJumpToFirstUnasked().toString();
            case LOADED_FROM:
                return this.getLoadedFrom();
            case MAX_TEXT_LEN_FOR_COMBO:
                return this.getMaxTextLenForCombo().toString();
            case PASSWORD_FOR_ADMIN_MODE:
                return this.getPasswordForAdminMode();
            case REDIRECT_ON_FINISH_DELAY:
                return this.getRedirectOnFinishDelay();
            case REDIRECT_ON_FINISH_MSG:
                return this.getRedirectOnFinishMsg();
            case REDIRECT_ON_FINISH_URL:
                return this.getRedirectOnFinishUrl();
            case REFUSED_ICON_OFF:
                return this.getRefusedIconOff();
            case REFUSED_ICON_ON:
                return this.getRefusedIconOn();
            case SCHEDULE_DIR:
                return this.getScheduleDir();
            case SCHEDULE_SOURCE:
                return this.getScheduleSource();
            case SCHED_VERSION_MAJOR:
                return this.getSchedVersionMajor();
            case SCHED_VERSION_MINOR:
                return this.getSchedVersionMinor();
            case SET_DEFAULT_FOCUS:
                return this.getSetDefaultFocus().toString();
            case SHOW_ADMIN_ICONS:
                return this.getShowAdminIcons().toString();
            case SHOW_QUESTION_REF:
                return this.getShowQuestionRef().toString();
            case SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE:
                return this.getShowSaveToFloppyInAdminMode().toString();
            case SUSPEND_TO_FLOPPY:
                return this.getSuspendToFloppy().toString();
            case SWAP_NEXT_AND_PREVIOUS:
                return this.getSwapNextAndPrevious().toString();
            case TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS:
                return this.getTitleForPicklistWhenInProgress();
            case TITLE:
                return this.getTitle();
            case UNKNOWN_ICON_OFF:
                return this.getUnknownIconOff();
            case UNKNOWN_ICON_ON:
                return this.getUnknownIconOn();
            case WORKING_DIR:
                return this.getWorking_Dir();
            case WRAP_ADMIN_ICONS:
                return this.getWrapAdminIcons().toString();
            default:
                return "";
        }
    }
    
    public void setReserved(String which, String value) {
        Integer val = -1;
        val = ReservedWordMap.get(which);
        if (val == null) {
            return;
        }
        switch (val) {
            case ACTIVE_BUTTON_PREFIX:
                setActiveButtonPrefix(value); return;
            case ACTIVE_BUTTON_SUFFIX:
                setActiveButtonSuffix(value); return;
            case ALLOW_COMMENTS:
                setAllowComments(Boolean.parseBoolean(value)); return;
            case ALLOW_DONT_UNDERSTAND:
                setAllowDontUnderstand(Boolean.parseBoolean(value)); return;
            case ALLOW_JUMP_TO:
                setAllowJumpTo(Boolean.parseBoolean(value)); return;
            case ALLOW_LANGUAGE_SWITCHING:
                setAllowLanguageSwitching(Boolean.parseBoolean(value)); return;
            case ALLOW_REFUSED:
                setAllowRefused(Boolean.parseBoolean(value)); return;
            case ALLOW_UNKNOWN:
                setAllowUnknown(Boolean.parseBoolean(value)); return;
            case ALWAYS_SHOW_ADMIN_ICONS:
                setAlwaysShowAdminIcons(Boolean.parseBoolean(value)); return;
            case ANSWER_OPTION_FIELD_WIDTH:
                setAnswerOptionFieldWidth(Integer.parseInt(value)); return;
            case AUTOGEN_OPTION_NUM:
                setAutogenOptionNum(Boolean.parseBoolean(value)); return;
            case COMMENT_ICON_OFF:
                setCommentIconOff(value); return;
            case COMMENT_ICON_ON:
                setCommentIconOn(value); return;
            case COMPLETED_DIR:
                setCompletedDir(value); return;
            case DEBUG_MODE:
                setDebugMode(Boolean.parseBoolean(value)); return;
            case DEVELOPER_MODE:
                setDeveloperMode(Boolean.parseBoolean(value)); return;
            case DISALLOW_COMMENTS:
                setDisallowComments(Boolean.parseBoolean(value)); return;
            case DONT_UNDERSTAND_ICON_OFF:
                setDontUnderstandIconOff(value); return;
            case DONT_UNDERSTAND_ICON_ON:
                setDontUnderstandIconOn(value); return;
            case FLOPPY_DIR:
                setFloppyDir(value); return;
            case HEADER_MSG:
                setHeaderMsg(value); return;
            case HELP_ICON:
                setHelpIcon(value); return;
            case ICON:
                setIcon(value); return;
            case IMAGE_FILES_DIR:
                setImageFilesDir(value); return;
            case JUMP_TO_FIRST_UNASKED:
                setJumpToFirstUnasked(Boolean.parseBoolean(value)); return;
            case LOADED_FROM:
                setLoadedFrom(value); return;
            case MAX_TEXT_LEN_FOR_COMBO:
                setMaxTextLenForCombo(Integer.parseInt(value)); return;
            case PASSWORD_FOR_ADMIN_MODE:
                setPasswordForAdminMode(value); return;
            case REDIRECT_ON_FINISH_DELAY:
                setRedirectOnFinishDelay(value); return;
            case REDIRECT_ON_FINISH_MSG:
                setRedirectOnFinishMsg(value); return;
            case REDIRECT_ON_FINISH_URL:
                setRedirectOnFinishUrl(value); return;
            case REFUSED_ICON_OFF:
                setRefusedIconOff(value); return;
            case REFUSED_ICON_ON:
                setRefusedIconOn(value); return;
            case SCHEDULE_DIR:
                setScheduleDir(value); return;
            case SCHEDULE_SOURCE:
                setScheduleSource(value); return;
            case SCHED_VERSION_MAJOR:
                setSchedVersionMajor(value); return;
            case SCHED_VERSION_MINOR:
                setSchedVersionMinor(value); return;
            case SET_DEFAULT_FOCUS:
                setSetDefaultFocus(Boolean.parseBoolean(value)); return;
            case SHOW_ADMIN_ICONS:
                setShowAdminIcons(Boolean.parseBoolean(value)); return;
            case SHOW_QUESTION_REF:
                setShowQuestionRef(Boolean.parseBoolean(value)); return;
            case SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE:
                setShowSaveToFloppyInAdminMode(Boolean.parseBoolean(value)); return;
            case SUSPEND_TO_FLOPPY:
                setSuspendToFloppy(Boolean.parseBoolean(value)); return;
            case SWAP_NEXT_AND_PREVIOUS:
                setSwapNextAndPrevious(Boolean.parseBoolean(value)); return;
            case TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS:
                setTitleForPicklistWhenInProgress(value); return;
            case TITLE:
                setTitle(value); return;
            case UNKNOWN_ICON_OFF:
                setUnknownIconOff(value); return;
            case UNKNOWN_ICON_ON:
                setUnknownIconOn(value); return;
            case WORKING_DIR:
                setWorking_Dir(value); return;
            case WRAP_ADMIN_ICONS:
                setWrapAdminIcons(Boolean.parseBoolean(value)); return;
            default:
                return;
        }
    }    
    
    private static final int ACTIVE_BUTTON_PREFIX = 1;
    private static final int ACTIVE_BUTTON_SUFFIX = 2;
    private static final int ALLOW_COMMENTS = 3;
    private static final int ALLOW_DONT_UNDERSTAND = 4;
    private static final int ALLOW_JUMP_TO = 5;
    private static final int ALLOW_LANGUAGE_SWITCHING = 6;
    private static final int ALLOW_REFUSED = 7;
    private static final int ALLOW_UNKNOWN = 8;
    private static final int ALWAYS_SHOW_ADMIN_ICONS = 9;
    private static final int ANSWER_OPTION_FIELD_WIDTH = 10;
    private static final int AUTOGEN_OPTION_NUM = 11;
    private static final int COMMENT_ICON_OFF = 12;
    private static final int COMMENT_ICON_ON = 13;
    private static final int COMPLETED_DIR = 14;
    private static final int DEBUG_MODE = 15;
    private static final int DEVELOPER_MODE = 16;
    private static final int DISALLOW_COMMENTS = 17;
    private static final int DONT_UNDERSTAND_ICON_OFF = 18;
    private static final int DONT_UNDERSTAND_ICON_ON = 19;
    private static final int FLOPPY_DIR = 20;
    private static final int HEADER_MSG = 21;
    private static final int HELP_ICON = 22;
    private static final int ICON = 23;
    private static final int IMAGE_FILES_DIR = 24;
    private static final int JUMP_TO_FIRST_UNASKED = 25;
    private static final int LOADED_FROM = 26;
    private static final int MAX_TEXT_LEN_FOR_COMBO = 27;
    private static final int PASSWORD_FOR_ADMIN_MODE = 28;
    private static final int REDIRECT_ON_FINISH_DELAY = 29;
    private static final int REDIRECT_ON_FINISH_MSG = 30;
    private static final int REDIRECT_ON_FINISH_URL = 31;
    private static final int REFUSED_ICON_OFF = 32;
    private static final int REFUSED_ICON_ON = 33;
    private static final int SCHEDULE_DIR = 34;
    private static final int SCHEDULE_SOURCE = 35;
    private static final int SCHED_VERSION_MAJOR = 36;
    private static final int SCHED_VERSION_MINOR = 37;
    private static final int SET_DEFAULT_FOCUS = 38;
    private static final int SHOW_ADMIN_ICONS = 39;
    private static final int SHOW_QUESTION_REF = 40;
    private static final int SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE = 41;
    private static final int SUSPEND_TO_FLOPPY = 42;
    private static final int SWAP_NEXT_AND_PREVIOUS = 43;
    private static final int TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS = 44;
    private static final int TITLE = 45;
    private static final int UNKNOWN_ICON_OFF = 46;
    private static final int UNKNOWN_ICON_ON = 47;
    private static final int WORKING_DIR = 48;
    private static final int WRAP_ADMIN_ICONS = 49;
    
    private static final HashMap<String,Integer> ReservedWordMap;
    
    static {
        ReservedWordMap = new HashMap<String,Integer>();
        ReservedWordMap.put("__ACTIVE_BUTTON_PREFIX__", ACTIVE_BUTTON_PREFIX);
        ReservedWordMap.put("__ACTIVE_BUTTON_SUFFIX__", ACTIVE_BUTTON_SUFFIX);
        ReservedWordMap.put("__ALLOW_COMMENTS__", ALLOW_COMMENTS);
        ReservedWordMap.put("__ALLOW_DONT_UNDERSTAND__", ALLOW_DONT_UNDERSTAND);
        ReservedWordMap.put("__ALLOW_JUMP_TO__", ALLOW_JUMP_TO);
        ReservedWordMap.put("__ALLOW_LANGUAGE_SWITCHING__", ALLOW_LANGUAGE_SWITCHING);
        ReservedWordMap.put("__ALLOW_REFUSED__", ALLOW_REFUSED);
        ReservedWordMap.put("__ALLOW_UNKNOWN__", ALLOW_UNKNOWN);
        ReservedWordMap.put("__ALWAYS_SHOW_ADMIN_ICONS__", ALWAYS_SHOW_ADMIN_ICONS);
        ReservedWordMap.put("__ANSWER_OPTION_FIELD_WIDTH__", ANSWER_OPTION_FIELD_WIDTH);
        ReservedWordMap.put("__AUTOGEN_OPTION_NUM__", AUTOGEN_OPTION_NUM);
        ReservedWordMap.put("__COMMENT_ICON_OFF__", COMMENT_ICON_OFF);
        ReservedWordMap.put("__COMMENT_ICON_ON__", COMMENT_ICON_ON);
        ReservedWordMap.put("__COMPLETED_DIR__", COMPLETED_DIR);
        ReservedWordMap.put("__DEBUG_MODE__", DEBUG_MODE);
        ReservedWordMap.put("__DEVELOPER_MODE__", DEVELOPER_MODE);
        ReservedWordMap.put("__DISALLOW_COMMENTS__", DISALLOW_COMMENTS);
        ReservedWordMap.put("__DONT_UNDERSTAND_ICON_OFF__", DONT_UNDERSTAND_ICON_OFF);
        ReservedWordMap.put("__DONT_UNDERSTAND_ICON_ON__", DONT_UNDERSTAND_ICON_ON);
        ReservedWordMap.put("__FLOPPY_DIR__", FLOPPY_DIR);
        ReservedWordMap.put("__HEADER_MSG__", HEADER_MSG);
        ReservedWordMap.put("__HELP_ICON__", HELP_ICON);
        ReservedWordMap.put("__ICON__", ICON);
        ReservedWordMap.put("__IMAGE_FILES_DIR__", IMAGE_FILES_DIR);
        ReservedWordMap.put("__JUMP_TO_FIRST_UNASKED__", JUMP_TO_FIRST_UNASKED);
        ReservedWordMap.put("__LOADED_FROM__", LOADED_FROM);
        ReservedWordMap.put("__MAX_TEXT_LEN_FOR_COMBO__", MAX_TEXT_LEN_FOR_COMBO);
        ReservedWordMap.put("__PASSWORD_FOR_ADMIN_MODE__", PASSWORD_FOR_ADMIN_MODE);
        ReservedWordMap.put("__REDIRECT_ON_FINISH_DELAY__", REDIRECT_ON_FINISH_DELAY);
        ReservedWordMap.put("__REDIRECT_ON_FINISH_MSG__", REDIRECT_ON_FINISH_MSG);
        ReservedWordMap.put("__REDIRECT_ON_FINISH_URL__", REDIRECT_ON_FINISH_URL);
        ReservedWordMap.put("__REFUSED_ICON_OFF__", REFUSED_ICON_OFF);
        ReservedWordMap.put("__REFUSED_ICON_ON__", REFUSED_ICON_ON);
        ReservedWordMap.put("__SCHEDULE_DIR__", SCHEDULE_DIR);
        ReservedWordMap.put("__SCHEDULE_SOURCE__", SCHEDULE_SOURCE);
        ReservedWordMap.put("__SCHED_VERSION_MAJOR__", SCHED_VERSION_MAJOR);
        ReservedWordMap.put("__SCHED_VERSION_MINOR__", SCHED_VERSION_MINOR);
        ReservedWordMap.put("__SET_DEFAULT_FOCUS__", SET_DEFAULT_FOCUS);
        ReservedWordMap.put("__SHOW_ADMIN_ICONS__", SHOW_ADMIN_ICONS);
        ReservedWordMap.put("__SHOW_QUESTION_REF__", SHOW_QUESTION_REF);
        ReservedWordMap.put("__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__", SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE);
        ReservedWordMap.put("__SUSPEND_TO_FLOPPY__", SUSPEND_TO_FLOPPY);
        ReservedWordMap.put("__SWAP_NEXT_AND_PREVIOUS__", SWAP_NEXT_AND_PREVIOUS);
        ReservedWordMap.put("__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__", TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS);
        ReservedWordMap.put("__TITLE__", TITLE);
        ReservedWordMap.put("__UNKNOWN_ICON_OFF__", UNKNOWN_ICON_OFF);
        ReservedWordMap.put("__UNKNOWN_ICON_ON__", UNKNOWN_ICON_ON);
        ReservedWordMap.put("__WORKING_DIR__", WORKING_DIR);
        ReservedWordMap.put("__WRAP_ADMIN_ICONS__", WRAP_ADMIN_ICONS);
    }

    public static HashMap<String, Integer> getReservedWordMap() {
        return ReservedWordMap;
    }
    
}
