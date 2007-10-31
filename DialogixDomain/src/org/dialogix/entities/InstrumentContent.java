/*
 * InstrumentContent.java
 * 
 * Created on Oct 30, 2007, 11:21:51 PM
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_content")
@NamedQueries({@NamedQuery(name = "InstrumentContent.findByInstrumentContentID", query = "SELECT i FROM InstrumentContent i WHERE i.instrumentContentID = :instrumentContentID"), @NamedQuery(name = "InstrumentContent.findByItemSequence", query = "SELECT i FROM InstrumentContent i WHERE i.itemSequence = :itemSequence"), @NamedQuery(name = "InstrumentContent.findByIsRequired", query = "SELECT i FROM InstrumentContent i WHERE i.isRequired = :isRequired"), @NamedQuery(name = "InstrumentContent.findByIsReadOnly", query = "SELECT i FROM InstrumentContent i WHERE i.isReadOnly = :isReadOnly"), @NamedQuery(name = "InstrumentContent.findByGroupNum", query = "SELECT i FROM InstrumentContent i WHERE i.groupNum = :groupNum"), @NamedQuery(name = "InstrumentContent.findByItemActionType", query = "SELECT i FROM InstrumentContent i WHERE i.itemActionType = :itemActionType"), @NamedQuery(name = "InstrumentContent.findByIsMessage", query = "SELECT i FROM InstrumentContent i WHERE i.isMessage = :isMessage"), @NamedQuery(name = "InstrumentContent.findBySPSSformat", query = "SELECT i FROM InstrumentContent i WHERE i.sPSSformat = :sPSSformat"), @NamedQuery(name = "InstrumentContent.findBySASinformat", query = "SELECT i FROM InstrumentContent i WHERE i.sASinformat = :sASinformat"), @NamedQuery(name = "InstrumentContent.findBySASformat", query = "SELECT i FROM InstrumentContent i WHERE i.sASformat = :sASformat")})
public class InstrumentContent implements Serializable {
    @TableGenerator(name="InstrumentContent_Generator", pkColumnValue="InstrumentContent", table="SEQUENCE_GENERATOR_TABLE", pkColumnName="SEQUENCE_NAME", valueColumnName="SEQUENCE_VALUE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="InstrumentContent_Generator")
    @Column(name = "InstrumentContent_ID", nullable = false)
    private Integer instrumentContentID;
    @Column(name = "Item_Sequence", nullable = false)
    private int itemSequence;
    @Column(name = "isRequired", nullable = false)
    private int isRequired;
    @Column(name = "isReadOnly", nullable = false)
    private int isReadOnly;
    @Lob
    @Column(name = "DisplayName")
    private String displayName;
    @Column(name = "GroupNum", nullable = false)
    private int groupNum;
    @Lob
    @Column(name = "Relevance", nullable = false)
    private String relevance;
    @Column(name = "ItemActionType")
    private String itemActionType;
    @Lob
    @Column(name = "FormatMask")
    private String formatMask;
    @Column(name = "isMessage", nullable = false)
    private int isMessage;
    @Lob
    @Column(name = "DefaultAnswer")
    private String defaultAnswer;
    @Column(name = "SPSSformat")
    private String sPSSformat;
    @Column(name = "SASinformat")
    private String sASinformat;
    @Column(name = "SASformat")
    private String sASformat;
    @JoinColumn(name = "InstrumentVersion_ID", referencedColumnName = "InstrumentVersion_ID")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;
    @JoinColumn(name = "Item_ID", referencedColumnName = "Item_ID")
    @ManyToOne
    private Item itemID;
    @JoinColumn(name = "VarName_ID", referencedColumnName = "VarName_ID")
    @ManyToOne
    private VarName varNameID;
    @JoinColumn(name = "DisplayType_ID", referencedColumnName = "DisplayType_ID")
    @ManyToOne
    private DisplayType displayTypeID;
    @JoinColumn(name = "Help_ID", referencedColumnName = "Help_ID")
    @ManyToOne
    private Help helpID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentContentID")
    private Collection<DataElement> dataElementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentContentID")
    private Collection<ItemUsage> itemUsageCollection;

    public InstrumentContent() {
    }

    public InstrumentContent(Integer instrumentContentID) {
        this.instrumentContentID = instrumentContentID;
    }

    public InstrumentContent(Integer instrumentContentID, int itemSequence, int isRequired, int isReadOnly, int groupNum, String relevance, int isMessage) {
        this.instrumentContentID = instrumentContentID;
        this.itemSequence = itemSequence;
        this.isRequired = isRequired;
        this.isReadOnly = isReadOnly;
        this.groupNum = groupNum;
        this.relevance = relevance;
        this.isMessage = isMessage;
    }

    public Integer getInstrumentContentID() {
        return instrumentContentID;
    }

    public void setInstrumentContentID(Integer instrumentContentID) {
        this.instrumentContentID = instrumentContentID;
    }

    public int getItemSequence() {
        return itemSequence;
    }

    public void setItemSequence(int itemSequence) {
        this.itemSequence = itemSequence;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
