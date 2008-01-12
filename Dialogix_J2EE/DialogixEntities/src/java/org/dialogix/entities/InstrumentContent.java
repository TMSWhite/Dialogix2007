/*
 * InstrumentContent.java
 * 
 * Created on Nov 5, 2007, 5:00:23 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "instrument_contents")
public class InstrumentContent implements Serializable {
    @TableGenerator(name="instrument_content_gen", pkColumnValue="instrument_content", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=500)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="instrument_content_gen")
    @Column(name = "instrument_content_id", nullable = false)
    private Long instrumentContentID;
    @Column(name = "item_sequence", nullable = false)
    private int itemSequence;
    @Lob
    @Column(name = "concept")
    private String concept;
    @Column(name = "is_required", nullable = false)
    private int isRequired;
    @Column(name = "is_read_only", nullable = false)
    private int isReadOnly;
    @Lob
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "group_num", nullable = false)
    private int groupNum;
    @Lob
    @Column(name = "relevance", nullable = false)
    private String relevance;
    @Column(name = "item_action_type")
    private String itemActionType;
    @Lob
    @Column(name = "format_mask")
    private String formatMask;
    @Column(name = "is_message", nullable = false)
    private int isMessage;
    @Lob
    @Column(name = "default_answer")
    private String defaultAnswer;
    @Column(name = "spss_format")
    private String sPSSformat;
    @Column(name = "sas_informat")
    private String sASinformat;
    @Column(name = "sas_format")
    private String sASformat;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne
    private Item itemID;
    @JoinColumn(name = "var_name_id", referencedColumnName = "var_name_id")
    @ManyToOne
    private VarName varNameID;
    @JoinColumn(name = "display_type_id", referencedColumnName = "display_type_id")
    @ManyToOne
    private DisplayType displayTypeID;
    @JoinColumn(name = "help_id", referencedColumnName = "help_id")
    @ManyToOne
    private Help helpID;
    @JoinColumn(name = "readback_id", referencedColumnName = "readback_id")
    @ManyToOne
    private Readback readbackID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentContentID")
    private Collection<DataElement> dataElementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentContentID")
    private Collection<ItemUsage> itemUsageCollection;

    public InstrumentContent() {
    }

    public InstrumentContent(Long instrumentContentID) {
        this.instrumentContentID = instrumentContentID;
    }

    public InstrumentContent(Long instrumentContentID, int itemSequence, int isRequired, int isReadOnly, int groupNum, String relevance, int isMessage) {
        this.instrumentContentID = instrumentContentID;
        this.itemSequence = itemSequence;
        this.isRequired = isRequired;
        this.isReadOnly = isReadOnly;
        this.groupNum = groupNum;
        this.relevance = relevance;
        this.isMessage = isMessage;
    }

    public Long getInstrumentContentID() {
        return instrumentContentID;
    }

    public void setInstrumentContentID(Long instrumentContentID) {
        this.instrumentContentID = instrumentContentID;
    }

    public int getItemSequence() {
        return itemSequence;
    }

    public void setItemSequence(int itemSequence) {
        this.itemSequence = itemSequence;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public int getIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(int isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getItemActionType() {
        return itemActionType;
    }

    public void setItemActionType(String itemActionType) {
        this.itemActionType = itemActionType;
    }

    public String getFormatMask() {
        return formatMask;
    }

    public void setFormatMask(String formatMask) {
        this.formatMask = formatMask;
    }

    public int getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(int isMessage) {
        this.isMessage = isMessage;
    }

    public String getDefaultAnswer() {
        return defaultAnswer;
    }

    public void setDefaultAnswer(String defaultAnswer) {
        this.defaultAnswer = defaultAnswer;
    }

    public String getSPSSformat() {
        return sPSSformat;
    }

    public void setSPSSformat(String sPSSformat) {
        this.sPSSformat = sPSSformat;
    }

    public String getSASinformat() {
        return sASinformat;
    }

    public void setSASinformat(String sASinformat) {
        this.sASinformat = sASinformat;
    }

    public String getSASformat() {
        return sASformat;
    }

    public void setSASformat(String sASformat) {
        this.sASformat = sASformat;
    }

    public InstrumentVersion getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(InstrumentVersion instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public Item getItemID() {
        return itemID;
    }

    public void setItemID(Item itemID) {
        this.itemID = itemID;
    }

    public VarName getVarNameID() {
        return varNameID;
    }

    public void setVarNameID(VarName varNameID) {
        this.varNameID = varNameID;
    }

    public DisplayType getDisplayTypeID() {
        return displayTypeID;
    }

    public void setDisplayTypeID(DisplayType displayTypeID) {
        this.displayTypeID = displayTypeID;
    }

    public Help getHelpID() {
        return helpID;
    }

    public void setHelpID(Help helpID) {
        this.helpID = helpID;
    }

    public Readback getReadbackID() {
        return readbackID;
    }

    public void setReadbackID(Readback readbackID) {
        this.readbackID = readbackID;
    }

    public Collection<DataElement> getDataElementCollection() {
        return dataElementCollection;
    }

    public void setDataElementCollection(Collection<DataElement> dataElementCollection) {
        this.dataElementCollection = dataElementCollection;
    }

    public Collection<ItemUsage> getItemUsageCollection() {
        return itemUsageCollection;
    }

    public void setItemUsageCollection(Collection<ItemUsage> itemUsageCollection) {
        this.itemUsageCollection = itemUsageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentContentID != null ? instrumentContentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InstrumentContent)) {
            return false;
        }
        InstrumentContent other = (InstrumentContent) object;
        if ((this.instrumentContentID == null && other.instrumentContentID != null) || (this.instrumentContentID != null && !this.instrumentContentID.equals(other.instrumentContentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentContent[instrumentContentID=" + instrumentContentID + "]";
    }

}
