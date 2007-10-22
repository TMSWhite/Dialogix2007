/*
 * ActionType.java
 * 
 * Created on Oct 22, 2007, 4:09:04 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dialogix.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "actiontype")
@NamedQueries({@NamedQuery(name = "ActionType.findByActionTypeID", query = "SELECT a FROM ActionType a WHERE a.actionTypeID = :actionTypeID"), @NamedQuery(name = "ActionType.findByActionName", query = "SELECT a FROM ActionType a WHERE a.actionName = :actionName")})
public class ActionType implements Serializable {
    @Id
    @Column(name = "ActionType_ID", nullable = false)
    private Integer actionTypeID;
    @Column(name = "ActionName", nullable = false)
    private String actionName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionTypeID")
    private Collection<InstrumentSession> instrumentSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionTypeID")
    private Collection<PageUsage> pageUsageCollection;

    public ActionType() {
    }

    public ActionType(Integer actionTypeID) {
        this.actionTypeID = actionTypeID;
    }

    public ActionType(Integer actionTypeID, String actionName) {
        this.actionTypeID = actionTypeID;
        this.actionName = actionName;
    }

    public Integer getActionTypeID() {
        return actionTypeID;
    }

    public void setActionTypeID(Integer actionTypeID) {
        this.actionTypeID = actionTypeID;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Collection<InstrumentSession> getInstrumentSessionCollection() {
        return instrumentSessionCollection;
    }

    public void setInstrumentSessionCollection(Collection<InstrumentSession> instrumentSessionCollection) {
        this.instrumentSessionCollection = instrumentSessionCollection;
    }

    public Collection<PageUsage> getPageUsageCollection() {
        return pageUsageCollection;
    }

    public void setPageUsageCollection(Collection<PageUsage> pageUsageCollection) {
        this.pageUsageCollection = pageUsageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actionTypeID != null ? actionTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActionType)) {
            return false;
        }
        ActionType other = (ActionType) object;
        if ((this.actionTypeID == null && other.actionTypeID != null) || (this.actionTypeID != null && !this.actionTypeID.equals(other.actionTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.domain.ActionType[actionTypeID=" + actionTypeID + "]";
    }

}
