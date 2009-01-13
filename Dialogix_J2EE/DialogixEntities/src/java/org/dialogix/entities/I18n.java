package org.dialogix.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author coevtmw
 */
@Entity
@Table(name = "i18n")
public class I18n implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "i18n_id")
    private Long i18nId;
    @Basic(optional = false)
    @Column(name = "label")
    private String label;
    @Basic(optional = false)
    @Lob
    @Column(name = "val")
    private String val;
    @Basic(optional = false)
    @Column(name = "iso3language")
    private String iso3language;

    public I18n() {
    }

    public I18n(Long i18nId) {
        this.i18nId = i18nId;
    }

    public I18n(Long i18nId, String label, String val, String iso3language) {
        this.i18nId = i18nId;
        this.label = label;
        this.val = val;
        this.iso3language = iso3language;
    }

    public Long getI18nId() {
        return i18nId;
    }

    public void setI18nId(Long i18nId) {
        this.i18nId = i18nId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getIso3language() {
        return iso3language;
    }

    public void setIso3language(String iso3language) {
        this.iso3language = iso3language;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (i18nId != null ? i18nId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof I18n)) {
            return false;
        }
        I18n other = (I18n) object;
        if ((this.i18nId == null && other.i18nId != null) || (this.i18nId != null && !this.i18nId.equals(other.i18nId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.dialogix.entities.I18n[i18nId=" + i18nId + "]";
    }

}
