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
import javax.persistence.Lob;
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
@Table(name = "instrument_version")
public class InstrumentVersion implements Serializable {

    @TableGenerator(name = "InstrumentVersion_gen", pkColumnValue = "instrument_version", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "InstrumentVersion_gen")
    @Column(name = "instrument_version_id", nullable = false)
    private Long instrumentVersionId;
    @Lob
    @Column(name = "apelon_import_xml")
    private String apelonImportXml;
    @Column(name = "creation_time_stamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTimeStamp;
    @Lob
    @Column(name = "instrument_as_spreadsheet")
    private String instrumentAsSpreadsheet;
    @Lob
    @Column(name = "instrument_notes")
    private String instrumentNotes;
    @Column(name = "instrument_status")
    private Integer instrumentStatus;
    @Column(name = "version_string", nullable = false)
    private String versionString;
    @Column(name = "num_cols", nullable = false)
    private int numCols;
    @Column(name = "num_rows", nullable = false)
    private int numRows;
    @OneToMany(mappedBy = "instrumentVersionId")
    private Collection<InstrumentSession> instrumentSessionCollection;
    @JoinColumn(name = "instrument_id", referencedColumnName = "instrument_id")
    @ManyToOne
    private Instrument instrumentId;
    @JoinColumn(name = "instrument_hash_id", referencedColumnName = "instrument_hash_id")
    @ManyToOne
    private InstrumentHash instrumentHashId;
    @OneToMany(mappedBy = "instrumentVersionId")
    private Collection<SemanticMappingIQA> semanticMappingIQACollection;
    @OneToMany(mappedBy = "instrumentVersionId")
    private Collection<InstrumentLoadError> instrumentLoadErrorCollection;
    @OneToMany(mappedBy = "instrumentVersionId")
    private Collection<InstrumentContent> instrumentContentCollection;
    @OneToMany(mappedBy = "instrumentVersionId")
    private Collection<InstrumentHeader> instrumentHeaderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentVersionId")
    private Collection<SubjectSession> subjectSessionCollection;

    public InstrumentVersion() {
    }

    public InstrumentVersion(Long instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    public InstrumentVersion(Long instrumentVersionId,
                             Date creationTimeStamp,
                             String versionString,
                             int numCols,
                             int numRows) {
        this.instrumentVersionId = instrumentVersionId;
        this.creationTimeStamp = creationTimeStamp;
        this.versionString = versionString;
        this.numCols = numCols;
        this.numRows = numRows;
    }

    public Long getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(Long instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    public String getApelonImportXml() {
        return apelonImportXml;
    }

    public void setApelonImportXml(String apelonImportXml) {
        this.apelonImportXml = apelonImportXml;
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getInstrumentAsSpreadsheet() {
        return instrumentAsSpreadsheet;
    }

    public void setInstrumentAsSpreadsheet(String instrumentAsSpreadsheet) {
        this.instrumentAsSpreadsheet = instrumentAsSpreadsheet;
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

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public Collection<InstrumentSession> getInstrumentSessionCollection() {
        return instrumentSessionCollection;
    }

    public void setInstrumentSessionCollection(
        Collection<InstrumentSession> instrumentSessionCollection) {
        this.instrumentSessionCollection = instrumentSessionCollection;
    }

    public Instrument getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Instrument instrumentId) {
        this.instrumentId = instrumentId;
    }

    public InstrumentHash getInstrumentHashId() {
        return instrumentHashId;
    }

    public void setInstrumentHashId(InstrumentHash instrumentHashId) {
        this.instrumentHashId = instrumentHashId;
    }

    public Collection<SemanticMappingIQA> getSemanticMappingIQACollection() {
        return semanticMappingIQACollection;
    }

    public void setSemanticMappingIQACollection(
        Collection<SemanticMappingIQA> semanticMappingIQACollection) {
        this.semanticMappingIQACollection = semanticMappingIQACollection;
    }

    public Collection<InstrumentLoadError> getInstrumentLoadErrorCollection() {
        return instrumentLoadErrorCollection;
    }

    public void setInstrumentLoadErrorCollection(
        Collection<InstrumentLoadError> instrumentLoadErrorCollection) {
        this.instrumentLoadErrorCollection = instrumentLoadErrorCollection;
    }

    public Collection<InstrumentContent> getInstrumentContentCollection() {
        return instrumentContentCollection;
    }

    public void setInstrumentContentCollection(
        Collection<InstrumentContent> instrumentContentCollection) {
        this.instrumentContentCollection = instrumentContentCollection;
    }

    public Collection<InstrumentHeader> getInstrumentHeaderCollection() {
        return instrumentHeaderCollection;
    }

    public void setInstrumentHeaderCollection(
        Collection<InstrumentHeader> instrumentHeaderCollection) {
        this.instrumentHeaderCollection = instrumentHeaderCollection;
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
        hash += (instrumentVersionId != null ? instrumentVersionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentVersion)) {
            return false;
        }
        InstrumentVersion other = (InstrumentVersion) object;
        if ((this.instrumentVersionId == null && other.instrumentVersionId != null) || (this.instrumentVersionId != null && !this.instrumentVersionId.equals(other.instrumentVersionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentVersion[instrumentVersionId=" + instrumentVersionId + "]";
    }
}
