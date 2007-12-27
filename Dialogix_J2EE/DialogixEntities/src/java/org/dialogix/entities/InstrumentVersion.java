/*
 * InstrumentVersion.java
 * 
 * Created on Nov 2, 2007, 11:15:08 AM
 * 
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_version")
public class InstrumentVersion implements Serializable {
    @TableGenerator(name="InstrumentVersion_Gen", pkColumnValue="InstrumentVersion", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="InstrumentVersion_Gen")
    @Column(name = "instrument_version_id", nullable = false)
    private Long instrumentVersionID;
    @Column(name = "VersionString", nullable = false)
    private String versionString;
    @Lob
    @Column(name = "InstrumentNotes")
    private String instrumentNotes;
    @Column(name = "InstrumentStatus")
    private Integer instrumentStatus;
    @Column(name = "CreationTimeStamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTimeStamp;
    @Lob
    @Column(name = "InstrumentVersionFileName")
    private String instrumentVersionFileName;
    @Column(name = "hasLOINCcode")
    private Boolean hasLOINCcode;
    @Column(name = "LOINC_NUM")
    private String loincNum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<InstrumentSession> instrumentSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<InstrumentHeader> instrumentHeaderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<LoincInstrumentRequest> loincInstrumentRequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<InstrumentLoadError> instrumentLoadErrorCollection;    
    @JoinColumn(name = "instrument_id", referencedColumnName = "instrument_id")
    @ManyToOne
    private Instrument instrumentID;
    @JoinColumn(name = "instrument_hash_id", referencedColumnName = "instrument_hash_id")
    @ManyToOne
    private InstrumentHash instrumentHashID;
    @OneToMany(mappedBy = "instrumentVersionID")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;

    public InstrumentVersion() {
    }

    public InstrumentVersion(Long instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public InstrumentVersion(Long instrumentVersionID, String versionString, Date creationTimeStamp) {
        this.instrumentVersionID = instrumentVersionID;
        this.versionString = versionString;
        this.creationTimeStamp = creationTimeStamp;
    }

    public Long getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(Long instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    public String getInstrumentNotes() {
        return instrumentNotes;
    }

    public void setInstrumentNotes(String instrumentNotes) {
        this.instrumentNotes = instrumentNotes;
    }

    public Integer getInstrumentStatus() {
        return instrumentStatus;
    }

    public void setInstrumentStatus(Integer instrumentStatus) {
        this.instrumentStatus = instrumentStatus;
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getInstrumentVersionFileName() {
        return instrumentVersionFileName;
    }

    public void setInstrumentVersionFileName(String instrumentVersionFileName) {
        this.instrumentVersionFileName = instrumentVersionFileName;
    }

    public Boolean getHasLOINCcode() {
        return hasLOINCcode;
    }

    public void setHasLOINCcode(Boolean hasLOINCcode) {
        this.hasLOINCcode = hasLOINCcode;
    }

    public String getLoincNum() {
        return loincNum;
    }

    public void setLoincNum(String loincNum) {
        this.loincNum = loincNum;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Collection<InstrumentSession> getInstrumentSessionCollection() {
        return instrumentSessionCollection;
    }

    public void setInstrumentSessionCollection(Collection<InstrumentSession> instrumentSessionCollection) {
        this.instrumentSessionCollection = instrumentSessionCollection;
    }

    public Collection<InstrumentHeader> getInstrumentHeaderCollection() {
        return instrumentHeaderCollection;
    }

    public void setInstrumentHeaderCollection(Collection<InstrumentHeader> instrumentHeaderCollection) {
        this.instrumentHeaderCollection = instrumentHeaderCollection;
    }

    public Collection<LoincInstrumentRequest> getLoincInstrumentRequestCollection() {
        return loincInstrumentRequestCollection;
    }

    public void setLoincInstrumentRequestCollection(Collection<LoincInstrumentRequest> loincInstrumentRequestCollection) {
        this.loincInstrumentRequestCollection = loincInstrumentRequestCollection;
    }
    
    public Collection<InstrumentLoadError> getInstrumentLoadErrorCollection() {
        return instrumentLoadErrorCollection;
    }

    public void setInstrumentLoadErrorCollection(Collection<InstrumentLoadError> instrumentLoadErrorCollection) {
        this.instrumentLoadErrorCollection = instrumentLoadErrorCollection;
    }    

    public Instrument getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(Instrument instrumentID) {
        this.instrumentID = instrumentID;
    }

    public InstrumentHash getInstrumentHashID() {
        return instrumentHashID;
    }

    public void setInstrumentHashID(InstrumentHash instrumentHashID) {
        this.instrumentHashID = instrumentHashID;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentVersionID != null ? instrumentVersionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InstrumentVersion)) {
            return false;
        }
        InstrumentVersion other = (InstrumentVersion) object;
        if ((this.instrumentVersionID == null && other.instrumentVersionID != null) || (this.instrumentVersionID != null && !this.instrumentVersionID.equals(other.instrumentVersionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentVersion[instrumentVersionID=" + instrumentVersionID + "]";
    }

}
