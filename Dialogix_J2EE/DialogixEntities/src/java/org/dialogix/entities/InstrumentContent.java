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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_content")
public class InstrumentContent implements Serializable {

    @TableGenerator(name = "InstrumentContent_gen", pkColumnValue = "instrument_content", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "InstrumentContent_gen")
    @Column(name = "instrument_content_id", nullable = false)
    private Long instrumentContentId;
    @Lob
    @Column(name = "concept")
    private String concept;
    @Lob
    @Column(name = "default_answer")
    private String defaultAnswer;
    @Lob
    @Column(name = "display_name")
    private String displayName;
    @Lob
    @Column(name = "format_mask")
    private String formatMask;
    @Column(name = "group_num", nullable = false)
    private int groupNum;
    @Column(name = "is_message", nullable = false)
    private int isMessage;
    @Column(name = "is_read_only", nullable = false)
    private int isReadOnly;
    @Column(name = "is_required", nullable = false)
    private int isRequired;
    @Column(name = "item_action_type")
    private String itemActionType;
    @Column(name = "item_sequence", nullable = false)
    private int itemSequence;
    @Lob
    @Column(name = "relevance", nullable = false)
    private String relevance;
    @Column(name = "sas_format")
    private String sasFormat;
    @Column(name = "sas_informat")
    private String sasInformat;
    @Column(name = "spss_format")
    private String spssFormat;
    @Column(name = "spss_level")
    private String spssLevel;
    @JoinColumn(name = "var_name_id", referencedColumnName = "var_name_id")
    @ManyToOne
    private VarName varNameId;
    @JoinColumn(name = "display_type_id", referencedColumnName = "display_type_id")
    @ManyToOne
    private DisplayType displayTypeId;
    @JoinColumn(name = "help_id", referencedColumnName = "help_id")
    @ManyToOne
    private Help helpId;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionId;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne
    private Item itemId;
    @JoinColumn(name = "readback_id", referencedColumnName = "readback_id")
    @ManyToOne
    private Readback readbackId;
    @OneToMany(mappedBy = "instrumentContentId")
    private Collection<DataElement> dataElementCollection;

    public InstrumentContent() {
    }

    public InstrumentContent(Long instrumentContentId) {
        this.instrumentContentId = instrumentContentId;
    }

    public InstrumentContent(Long instrumentContentId,
                             int groupNum,
                             int isMessage,
                             int isReadOnly,
                             int isRequired,
                             int itemSequence,
                             String relevance) {
        this.instrumentContentId = instrumentContentId;
        this.groupNum = groupNum;
        this.isMessage = isMessage;
        this.isReadOnly = isReadOnly;
        this.isRequired = isRequired;
        this.itemSequence = itemSequence;
        this.relevance = relevance;
    }

    public Long getInstrumentContentId() {
        return instrumentContentId;
    }

    public void setInstrumentContentId(Long instrumentContentId) {
        this.instrumentContentId = instrumentContentId;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getDefaultAnswer() {
        return defaultAnswer;
    }

    public void setDefaultAnswer(String defaultAnswer) {
        this.defaultAnswer = defaultAnswer;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFormatMask() {
        return formatMask;
    }

    public void setFormatMask(String formatMask) {
        this.formatMask = formatMask;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(int isMessage) {
        this.isMessage = isMessage;
    }

    public int getIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(int isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public String getItemActionType() {
        return itemActionType;
    }

    public void setItemActionType(String itemActionType) {
        this.itemActionType = itemActionType;
    }

    public int getItemSequence() {
        return itemSequence;
    }

    public void setItemSequence(int itemSequence) {
        this.itemSequence = itemSequence;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getSasFormat() {
        return sasFormat;
    }

    public void setSasFormat(String sasFormat) {
        this.sasFormat = sasFormat;
    }

    public String getSasInformat() {
        return sasInformat;
    }

    public void setSasInformat(String sasInformat) {
        this.sasInformat = sasInformat;
    }

    public String getSpssFormat() {
        return spssFormat;
    }

    public void setSpssFormat(String spssFormat) {
        this.spssFormat = spssFormat;
    }

    public String getSpssLevel() {
        return spssLevel;
    }

    public void setSpssLevel(String spssLevel) {
        this.spssLevel = spssLevel;
    }

    public VarName getVarNameId() {
        return varNameId;
    }

    public void setVarNameId(VarName varNameId) {
        this.varNameId = varNameId;
    }

    public DisplayType getDisplayTypeId() {
        return displayTypeId;
    }

    public void setDisplayTypeId(DisplayType displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public Help getHelpId() {
        return helpId;
    }

    public void setHelpId(Help helpId) {
        this.helpId = helpId;
    }

    public InstrumentVersion getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(InstrumentVersion instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Readback getReadbackId() {
        return readbackId;
    }

    public void setReadbackId(Readback readbackId) {
        this.readbackId = readbackId;
    }

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }

    public void setDataElementCollection(
        Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentContentId != null ? instrumentContentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentContent)) {
            return false;
        }
        InstrumentContent other = (InstrumentContent) object;
        if ((this.instrumentContentId == null && other.instrumentContentId != null) || (this.instrumentContentId != null && !this.instrumentContentId.equals(other.instrumentContentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentContent[instrumentContentId=" + instrumentContentId + "]";
    }
}
