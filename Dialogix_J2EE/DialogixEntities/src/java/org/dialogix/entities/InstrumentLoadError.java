package org.dialogix.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "instrument_load_errors")
public class InstrumentLoadError implements Serializable {
    @TableGenerator(name="instrument_load_error_gen", pkColumnValue="instrument_load_error", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="instrument_load_error_gen")
    @Column(name = "id", nullable = false)
    private Long instrumentLoadErrorID;
    @Lob
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "source_row")
    private Integer sourceRow;
    @Column(name = "source_column")
    private Integer sourceColumn;
    @Lob
    @Column(name = "source_text")
    private String sourceText;    
    @Column(name = "log_level")
    private Integer logLevel;   // really a java.util.logging.Level
    @JoinColumn(name = "instrument_version_id", referencedColumnName="id")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;

    public InstrumentLoadError() {
    }

    public InstrumentLoadError(Integer sourceRow, Integer sourceColumn, Integer logLevel, String errorMessage, String sourceText) {
        this.sourceRow = sourceRow;
        this.sourceColumn = sourceColumn;
        this.errorMessage = errorMessage;
        this.logLevel = logLevel;
        this.sourceText = sourceText;
    }

    public Long getInstrumentLoadErrorID() {
        return instrumentLoadErrorID;
    }

    public void setInstrumentLoadErrorID(Long instrumentLoadErrorID) {
        this.instrumentLoadErrorID = instrumentLoadErrorID;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getSourceRow() {
        return sourceRow;
    }

    public void setSourceRow(Integer sourceRow) {
        this.sourceRow = sourceRow;
    }

    public Integer getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(Integer sourceColumn) {
        this.sourceColumn = sourceColumn;
    }
    
    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }    

    public Integer getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }

    public InstrumentVersion getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(InstrumentVersion instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instrumentLoadErrorID != null ? instrumentLoadErrorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InstrumentLoadError)) {
            return false;
        }
        InstrumentLoadError other = (InstrumentLoadError) object;
        if ((this.instrumentLoadErrorID == null && other.instrumentLoadErrorID != null) || (this.instrumentLoadErrorID != null && !this.instrumentLoadErrorID.equals(other.instrumentLoadErrorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.InstrumentLoadError[instrumentLoadErrorID=" + instrumentLoadErrorID + "]";
    }

}
