/*
 * LoincInstrumentRequest.java
 * 
 * Created on Oct 22, 2007, 4:09:02 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "loinc_instrumentrequest")
@NamedQueries({@NamedQuery(name = "LoincInstrumentRequest.findByLOINCInstrumentRequestID", query = "SELECT l FROM LoincInstrumentRequest l WHERE l.lOINCInstrumentRequestID = :lOINCInstrumentRequestID"), @NamedQuery(name = "LoincInstrumentRequest.findByLOINCproperty", query = "SELECT l FROM LoincInstrumentRequest l WHERE l.lOINCproperty = :lOINCproperty"), @NamedQuery(name = "LoincInstrumentRequest.findByLOINCtimeAspect", query = "SELECT l FROM LoincInstrumentRequest l WHERE l.lOINCtimeAspect = :lOINCtimeAspect"), @NamedQuery(name = "LoincInstrumentRequest.findByLOINCsystem", query = "SELECT l FROM LoincInstrumentRequest l WHERE l.lOINCsystem = :lOINCsystem"), @NamedQuery(name = "LoincInstrumentRequest.findByLOINCscale", query = "SELECT l FROM LoincInstrumentRequest l WHERE l.lOINCscale = :lOINCscale"), @NamedQuery(name = "LoincInstrumentRequest.findByLOINCmethod", query = "SELECT l FROM LoincInstrumentRequest l WHERE l.lOINCmethod = :lOINCmethod"), @NamedQuery(name = "LoincInstrumentRequest.findByLoincNum", query = "SELECT l FROM LoincInstrumentRequest l WHERE l.loincNum = :loincNum")})
public class LoincInstrumentRequest implements Serializable {
    @Id
    @Column(name = "LOINC_InstrumentRequest_ID", nullable = false)
    private Integer lOINCInstrumentRequestID;
    @Column(name = "LOINCproperty")
    private String lOINCproperty;
    @Column(name = "LOINCtimeAspect")
    private String lOINCtimeAspect;
    @Column(name = "LOINCsystem")
    private String lOINCsystem;
    @Column(name = "LOINCscale")
    private String lOINCscale;
    @Column(name = "LOINCmethod")
    private String lOINCmethod;
    @Column(name = "LOINC_NUM")
    private String loincNum;
    @JoinColumn(name = "InstrumentVersion_ID", referencedColumnName = "InstrumentVersion_ID")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;

    public LoincInstrumentRequest() {
    }

    public LoincInstrumentRequest(Integer lOINCInstrumentRequestID) {
        this.lOINCInstrumentRequestID = lOINCInstrumentRequestID;
    }

    public Integer getLOINCInstrumentRequestID() {
        return lOINCInstrumentRequestID;
    }

    public void setLOINCInstrumentRequestID(Integer lOINCInstrumentRequestID) {
        this.lOINCInstrumentRequestID = lOINCInstrumentRequestID;
    }

    public String getLOINCproperty() {
        return lOINCproperty;
    }

    public void setLOINCproperty(String lOINCproperty) {
        this.lOINCproperty = lOINCproperty;
    }

    public String getLOINCtimeAspect() {
        return lOINCtimeAspect;
    }

    public void setLOINCtimeAspect(String lOINCtimeAspect) {
        this.lOINCtimeAspect = lOINCtimeAspect;
    }

    public String getLOINCsystem() {
        return lOINCsystem;
    }

    public void setLOINCsystem(String lOINCsystem) {
        this.lOINCsystem = lOINCsystem;
    }

    public String getLOINCscale() {
        return lOINCscale;
    }

    public void setLOINCscale(String lOINCscale) {
        this.lOINCscale = lOINCscale;
    }

    public String getLOINCmethod() {
        return lOINCmethod;
    }

    public void setLOINCmethod(String lOINCmethod) {
        this.lOINCmethod = lOINCmethod;
    }

    public String getLoincNum() {
        return loincNum;
    }

    public void setLoincNum(String loincNum) {
        this.loincNum = loincNum;
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
        hash += (lOINCInstrumentRequestID != null ? lOINCInstrumentRequestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoincInstrumentRequest)) {
            return false;
        }
        LoincInstrumentRequest other = (LoincInstrumentRequest) object;
        if ((this.lOINCInstrumentRequestID == null && other.lOINCInstrumentRequestID != null) || (this.lOINCInstrumentRequestID != null && !this.lOINCInstrumentRequestID.equals(other.lOINCInstrumentRequestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.LoincInstrumentRequest[lOINCInstrumentRequestID=" + lOINCInstrumentRequestID + "]";
    }

}
