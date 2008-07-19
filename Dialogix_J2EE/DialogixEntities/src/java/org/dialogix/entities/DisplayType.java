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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "display_type")
public class DisplayType implements Serializable {

    @TableGenerator(name = "DisplayType_gen", pkColumnValue = "display_type", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "DisplayType_gen")
    @Column(name = "display_type_id", nullable = false)
    private Integer displayTypeId;
    @Column(name = "display_type", nullable = false)
    private String displayType;
    @Column(name = "has_answer_list")
    private Boolean hasAnswerList;
    @Column(name = "loinc_scale", nullable = false)
    private String loincScale;
    @Column(name = "sas_format", nullable = false)
    private String sasFormat;
    @Column(name = "sas_informat", nullable = false)
    private String sasInformat;
    @Column(name = "spss_format", nullable = false)
    private String spssFormat;
    @Column(name = "spss_level", nullable = false)
    private String spssLevel;
    @OneToMany(mappedBy = "displayTypeId")
    private Collection<InstrumentContent> instrumentContentCollection;
    @JoinColumn(name = "data_type_id", referencedColumnName = "data_type_id")
    @ManyToOne
    private DataType dataTypeId;

    public DisplayType() {
    }

    public DisplayType(Integer displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public DisplayType(Integer displayTypeId,
                       String displayType,
                       String loincScale,
                       String sasFormat,
                       String sasInformat,
                       String spssFormat,
                       String spssLevel) {
        this.displayTypeId = displayTypeId;
        this.displayType = displayType;
        this.loincScale = loincScale;
        this.sasFormat = sasFormat;
        this.sasInformat = sasInformat;
        this.spssFormat = spssFormat;
        this.spssLevel = spssLevel;
    }

    public Integer getDisplayTypeId() {
        return displayTypeId;
    }

    public void setDisplayTypeId(Integer displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Boolean getHasAnswerList() {
        return hasAnswerList;
    }

    public void setHasAnswerList(Boolean hasAnswerList) {
        this.hasAnswerList = hasAnswerList;
    }

    public String getLoincScale() {
        return loincScale;
    }

    public void setLoincScale(String loincScale) {
        this.loincScale = loincScale;
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

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(
        Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public DataType getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(DataType dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (displayTypeId != null ? displayTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisplayType)) {
            return false;
        }
        DisplayType other = (DisplayType) object;
        if ((this.displayTypeId == null && other.displayTypeId != null) || (this.displayTypeId != null && !this.displayTypeId.equals(other.displayTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.DisplayType[displayTypeId=" + displayTypeId + "]";
    }
}
