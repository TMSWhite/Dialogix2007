/*
 * InstrumentVersion.java
 * 
 * Created on Oct 22, 2007, 4:09:00 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrumentversion")
@NamedQueries({@NamedQuery(name = "InstrumentVersion.findByInstrumentVersionID", query = "SELECT i FROM InstrumentVersion i WHERE i.instrumentVersionID = :instrumentVersionID"), @NamedQuery(name = "InstrumentVersion.findByMajorVersion", query = "SELECT i FROM InstrumentVersion i WHERE i.majorVersion = :majorVersion"), @NamedQuery(name = "InstrumentVersion.findByMinorVersion", query = "SELECT i FROM InstrumentVersion i WHERE i.minorVersion = :minorVersion"), @NamedQuery(name = "InstrumentVersion.findByInstrumentStatus", query = "SELECT i FROM InstrumentVersion i WHERE i.instrumentStatus = :instrumentStatus"), @NamedQuery(name = "InstrumentVersion.findByCreationTimeStamp", query = "SELECT i FROM InstrumentVersion i WHERE i.creationTimeStamp = :creationTimeStamp"), @NamedQuery(name = "InstrumentVersion.findByHasLOINCcode", query = "SELECT i FROM InstrumentVersion i WHERE i.hasLOINCcode = :hasLOINCcode"), @NamedQuery(name = "InstrumentVersion.findByLoincNum", query = "SELECT i FROM InstrumentVersion i WHERE i.loincNum = :loincNum")})
public class InstrumentVersion implements Serializable {
    @Id
    @Column(name = "InstrumentVersion_ID", nullable = false)
    private Integer instrumentVersionID;
    @Column(name = "MajorVersion", nullable = false)
    private int majorVersion;
    @Column(name = "MinorVersion", nullable = false)
    private int minorVersion;
    @Lob
    @Column(name = "InstrumentNotes")
    private String instrumentNotes;
    @Column(name = "InstrumentStatus")
    private Integer instrumentStatus;
    @Column(name = "CreationTimeStamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTimeStamp;
    @Column(name = "hasLOINCcode")
    private Boolean hasLOINCcode;
    @Column(name = "LOINC_NUM")
    private String loincNum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<InstrumentSession> instrumentSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<InstrumentContent> instrumentContentCollection;
    @JoinColumn(name = "Instrument_ID", referencedColumnName = "Instrument_ID")
    @ManyToOne
    private Instrument instrumentID;
    @JoinColumn(name = "InstrumentHash_ID", referencedColumnName = "InstrumentHash_ID")
    @ManyToOne
    private InstrumentHash instrumentHashID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<LoincInstrumentRequest> loincInstrumentRequestCollection;
    @OneToMany(mappedBy = "instrumentVersionID")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionID")
    private Collection<InstrumentHeader> instrumentHeaderCollection;

    public InstrumentVersion() {
    }

    public InstrumentVersion(Integer instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public InstrumentVersion(Integer instrumentVersionID, int majorVersion, int minorVersion, Date creationTimeStamp) {
        this.instrumentVersionID = instrumentVersionID;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.creationTimeStamp = creationTimeStamp;
    }

    public Integer getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(Integer instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
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

    public Collection<InstrumentSession> getInstrumentSessionCollection() {
        return instrumentSessionCollection;
    }

    public void setInstrumentSessionCollection(Collection<InstrumentSession> instrumentSessionCollection) {
        this.instrumentSessionCollection = instrumentSessionCollection;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
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

    public Collection<LoincInstrumentRequest> getLoincInstrumentRequestCollection() {
        return loincInstrumentRequestCollection;
    }

    public void setLoincInstrumentRequestCollection(Collection<LoincInstrumentRequest> loincInstrumentRequestCollection) {
        this.loincInstrumentRequestCollection = loincInstrumentRequestCollection;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    public Collection<InstrumentHeader> getInstrumentHeaderCollection() {
        return instrumentHeaderCollection;
    }

    public void setInstrumentHeaderCollection(Collection<InstrumentHeader> instrumentHeaderCollection) {
        this.instrumentHeaderCollection = instrumentHeaderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentVersionID != null ? instrumentVersionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "org.dialogix.domain.InstrumentVersion[instrumentVersionID=" + instrumentVersionID + "]";
    }

}
