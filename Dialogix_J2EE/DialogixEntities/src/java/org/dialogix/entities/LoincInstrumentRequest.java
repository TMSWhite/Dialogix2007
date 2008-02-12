/*
 * LoincInstrumentRequest.java
 * 
 * Created on Nov 2, 2007, 11:15:07 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*  ;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "loinc_instrument_requests")
public class LoincInstrumentRequest implements Serializable {
    @TableGenerator(name="loinc_instrument_request_gen", pkColumnValue="loinc_instrument_request", table="sequence", pkColumnName="seq_name", valueColumnName="seq_count", allocationSize=100)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="loinc_instrument_request_gen")
    @Column(name = "id", nullable = false)
    private Long lOINCInstrumentRequestID;
    @Column(name = "loinc_property")
    private String lOINCproperty;
    @Column(name = "loinc_time_aspect")
    private String lOINCtimeAspect;
    @Column(name = "loinc_system")
    private String lOINCsystem;
    @Column(name = "loinc_scale")
    private String lOINCscale;
    @Column(name = "loinc_method")
    private String lOINCmethod;
    @Column(name = "loinc_num")
    private String loincNum;
    @JoinColumn(name = "instrument_version_id", referencedColumnName="id")
    @ManyToOne
    private InstrumentVersion instrumentVersionID;

    public LoincInstrumentRequest() {
    }

    public LoincInstrumentRequest(Long lOINCInstrumentRequestID) {
        this.lOINCInstrumentRequestID = lOINCInstrumentRequestID;
    }

    public Long getLOINCInstrumentRequestID() {
        return lOINCInstrumentRequestID;
    }

    public void setLOINCInstrumentRequestID(Long lOINCInstrumentRequestID) {
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
        return "org.dialogix.entities.LoincInstrumentRequest[lOINCInstrumentRequestID=" + lOINCInstrumentRequestID + "]";
    }

}
