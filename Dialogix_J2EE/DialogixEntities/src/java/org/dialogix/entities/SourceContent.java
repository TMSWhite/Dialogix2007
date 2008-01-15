/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "source_content")
public class SourceContent implements Serializable {
    @TableGenerator(name="source_content_gen", pkColumnValue="source_content", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="source_content_gen")
    private Long sourceContentID;
    @Column(name="row_num")
    private Integer rowNum;
    @Column(name="col_num")
    private Integer colNum;
    @Lob
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "instrument_version_id", referencedColumnName = "instrument_version_id")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;

    public SourceContent() {
    }

    public SourceContent(Integer rowNum,
                         Integer colNum,
                         String name,
                         InstrumentVersion instrumentVersionID) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.name = name;
        this.instrumentVersionID = instrumentVersionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sourceContentID != null ? sourceContentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the sourceContentID fields are not set
        if (!(object instanceof SourceContent)) {
            return false;
        }
        SourceContent other = (SourceContent) object;
        if ((this.sourceContentID == null && other.sourceContentID != null) || (this.sourceContentID != null && !this.sourceContentID.equals(other.sourceContentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.SourceContent[sourceContentID=" + sourceContentID + "]";
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public InstrumentVersion getInstrumentVersionID() {
        return instrumentVersionID;
    }

    public void setInstrumentVersionID(InstrumentVersion instrumentVersionID) {
        this.instrumentVersionID = instrumentVersionID;
    }

    public Long getSourceContentID() {
        return sourceContentID;
    }

    public void setSourceContentID(Long sourceContentID) {
        this.sourceContentID = sourceContentID;
    }
}
