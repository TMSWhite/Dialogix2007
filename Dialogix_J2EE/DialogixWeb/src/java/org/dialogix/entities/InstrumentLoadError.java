/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "instrument_load_error")
public class InstrumentLoadError implements Serializable {

    @TableGenerator(name = "InstrumentLoadError_gen", pkColumnValue = "instrument_load_error", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "InstrumentLoadError_gen")
    @Column(name = "instrument_load_error_id", nullable = false)
    private Long instrumentLoadErrorId;
    @Lob
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "log_level")
    private Integer logLevel;
    @Column(name = "source_column")
    private Integer sourceColumn;
    @Column(name = "source_row")
    private Integer sourceRow;
    @Lob
    @Column(name = "source_text")
    private String sourceText;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionId;

    public InstrumentLoadError() {
    }

    public InstrumentLoadError(Long instrumentLoadErrorId) {
        this.instrumentLoadErrorId = instrumentLoadErrorId;
    }
    
    public InstrumentLoadError(int sourceRow, int sourceColumn, int logLevel, String errorMessage, String sourceText) {
        this.sourceRow = sourceRow;
        this.sourceColumn = sourceColumn;
        this.logLevel = logLevel;
        this.errorMessage = errorMessage;
        this.sourceText = sourceText;
    }

    public Long getInstrumentLoadErrorId() {
        return instrumentLoadErrorId;
    }

    public void setInstrumentLoadErrorId(Long instrumentLoadErrorId) {
        this.instrumentLoadErrorId = instrumentLoadErrorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }

    public Integer getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(Integer sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public Integer getSourceRow() {
        return sourceRow;
    }

    public void setSourceRow(Integer sourceRow) {
        this.sourceRow = sourceRow;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public InstrumentVersion getInstrumentVersionId() {
        return instrumentVersionId;
    }

    public void setInstrumentVersionId(InstrumentVersion instrumentVersionId) {
        this.instrumentVersionId = instrumentVersionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentLoadErrorId != null ? instrumentLoadErrorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentLoadError)) {
            return false;
        }
        InstrumentLoadError other = (InstrumentLoadError) object;
        if ((this.instrumentLoadErrorId == null && other.instrumentLoadErrorId != null) || (this.instrumentLoadErrorId != null && !this.instrumentLoadErrorId.equals(other.instrumentLoadErrorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentLoadError[instrumentLoadErrorId=" + instrumentLoadErrorId + "]";
    }
}
