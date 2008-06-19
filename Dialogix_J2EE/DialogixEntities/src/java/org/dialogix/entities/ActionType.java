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
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Coevtmw
 */
@Entity
@Table(name = "action_type")
public class ActionType implements Serializable {

    @TableGenerator(name = "ActionType_gen", pkColumnValue = "action_type", table = "sequence_model", pkColumnName = "seq_name", valueColumnName = "seq_count", allocationSize = 1000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ActionType_gen")
    @Column(name = "action_type_id", nullable = false)
    private Integer actionTypeId;
    @Column(name = "action_name", nullable = false)
    private String actionName;
    @OneToMany(mappedBy = "actionTypeId")
    private Collection<InstrumentSession> instrumentSessionCollection;
    @OneToMany(mappedBy = "actionTypeId")
    private Collection<PageUsage> pageUsageCollection;

    public ActionType() {
    }

    public ActionType(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    public ActionType(Integer actionTypeId,
                      String actionName) {
        this.actionTypeId = actionTypeId;
        this.actionName = actionName;
    }

    public Integer getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
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

    public void setInstrumentSessionCollection(
        Collection<InstrumentSession> instrumentSessionCollection) {
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
        hash += (actionTypeId != null ? actionTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActionType)) {
            return false;
        }
        ActionType other = (ActionType) object;
        if ((this.actionTypeId == null && other.actionTypeId != null) || (this.actionTypeId != null && !this.actionTypeId.equals(other.actionTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.ActionType[actionTypeId=" + actionTypeId + "]";
    }
}
