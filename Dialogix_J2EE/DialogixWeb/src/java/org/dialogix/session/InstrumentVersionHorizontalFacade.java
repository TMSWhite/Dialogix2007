/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialogix.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.persistence.*;
import org.dialogix.entities.InstrumentSession;
import org.dialogix.entities.InstrumentVersion;

public class InstrumentVersionHorizontalFacade implements Serializable {

    private EntityManagerFactory _emf = null;
    private EntityManager _em = null;
    private static final String IVH_PREFIX = "ivh_";
    private static final String VAR_PREFIX = "v_";
    private Long instrumentVersionId;
    private Long instrumentSessionId;
    private Hashtable<Long, String> updatedValues = null;	// This is array of values to be updated

    public InstrumentVersionHorizontalFacade(Long instrumentVersionId, Long instrumentSessionId) {
        this.instrumentVersionId = instrumentVersionId;
        this.instrumentSessionId = instrumentSessionId;
    }

    public InstrumentVersionHorizontalFacade() {
    }

    private EntityManager getEM() {
        if (_emf == null) {
            _emf = Persistence.createEntityManagerFactory("DialogixEntitiesPU");
        }
        if (_em == null) {
            _em = _emf.createEntityManager();
        }
        return _em;
    }

    private void closeEM(EntityManager em) {
        if (em != null) {
            em.close();
            _em = null;
        }
    }

    public void persist() {
        // store a skeletal record that can be updated as the session progresses
        String sql = "INSERT INTO " + IVH_PREFIX + instrumentVersionId +
                " (instrument_session_id) VALUES( ? )";

        EntityManager em = getEM();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, instrumentSessionId);
            query.executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e; // or display error message
        } finally {
            closeEM(em);;
        }
    }

    public void create(Long instrumentVersionId, ArrayList<Long> varNameIds) {
        /* This is tighly coupled to Mysql syntax */
//    this.varNameIds = varNameIds;

        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ").append(IVH_PREFIX).append(instrumentVersionId).append(" (");
        sb.append("instrument_session_id bigint(20) NOT NULL,\n");
        // Add one variable per VarName
        Iterator<Long> it = varNameIds.iterator();
        while (it.hasNext()) {
            sb.append(VAR_PREFIX).append(it.next()).append(" text default NULL,\n");
        }
        sb.append("PRIMARY KEY pk_" + IVH_PREFIX).append(instrumentVersionId).append(" (instrument_session_id)\n");
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;\n");

        EntityManager em = getEM();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Query query = em.createNativeQuery(sb.toString());
            query.executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e; // or display error message
        } finally {
            closeEM(em);;
        }
    }

    public void updateColumnValue(Long column, String value) {
        if (updatedValues == null) {
            updatedValues = new Hashtable();
        }
        updatedValues.put(column, value);
    }

    public void merge() {
        StringBuffer sb = new StringBuffer("UPDATE ");
        if (updatedValues == null) {
            return;
        }
        sb.append(IVH_PREFIX).append(instrumentVersionId);
        sb.append(" SET ");
        // Iterate over columns needing to be set
        Enumeration<Long> keys = updatedValues.keys();
        int count = 0;
        while (keys.hasMoreElements()) {
            Long key = keys.nextElement();
            if (count++ > 0) {
                sb.append(", ");
            }
            sb.append(VAR_PREFIX).append(key).append(" = ?");
        }
        sb.append(" WHERE instrument_session_id = ").append(instrumentSessionId);

        EntityManager em = getEM();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Query query = em.createNativeQuery(sb.toString());
            keys = updatedValues.keys();
            int keynum = 0;
            while (keys.hasMoreElements()) {
                Long key = keys.nextElement();
                String value = updatedValues.get(key);
                query.setParameter(++keynum, value);
            }
            query.executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e; // or display error message
        } finally {
            closeEM(em);;
            updatedValues = null;	// clear it for next time
        }
    }

    public ArrayList<String> getRow(InstrumentSession instrumentSession, ArrayList<Long> varNameIds) {
        ArrayList<String> cols = new ArrayList<String>();
        StringBuffer sb = new StringBuffer("SELECT ");
        int i = 0;
        for (i = 0; i < varNameIds.size() - 1; ++i) {
            sb.append(VAR_PREFIX).append(varNameIds.get(i)).append(", ");
        }
        sb.append(VAR_PREFIX).append(varNameIds.get(i)).append("\n");
        sb.append("FROM ").append(IVH_PREFIX).append(instrumentSession.getInstrumentVersionId().getInstrumentVersionId());
        sb.append(" WHERE instrument_session_id = ?;");

        EntityManager em = getEM();
        try {
            Query query = em.createNativeQuery(sb.toString());
            query.setParameter(1, instrumentSession.getInstrumentSessionId());
            List<Vector> results = query.getResultList();
            if (results == null) {
                return null;
            }
            Iterator<Vector> rowIterator = results.iterator();
            if (rowIterator.hasNext()) {
                Vector v = rowIterator.next();
                Iterator colIterator = v.iterator();
                while (colIterator.hasNext()) {
                    cols.add((String) colIterator.next());
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } finally {
            closeEM(em);;
        }
        return cols;
    }

    public ArrayList<ArrayList<String>> getRows(InstrumentVersion instrumentVersion, ArrayList<Long> varNameIds) {
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
        StringBuffer sb = new StringBuffer("SELECT instrument_session_id, ");
        int i = 0;
        for (i = 0; i < varNameIds.size() - 1; ++i) {
            sb.append(VAR_PREFIX).append(varNameIds.get(i)).append(", ");
        }
        sb.append(VAR_PREFIX).append(varNameIds.get(i)).append("\n");
        sb.append("FROM ").append(IVH_PREFIX).append(instrumentVersion.getInstrumentVersionId());
        sb.append(" ORDER BY instrument_session_id;");

        EntityManager em = getEM();
        try {
            Query query = em.createNativeQuery(sb.toString());
            List<Vector> results = query.getResultList();
            if (results == null) {
                return null;
            }
            Iterator<Vector> rowIterator = results.iterator();
            int col = 0;
            while (rowIterator.hasNext()) {
                ArrayList<String> cols = new ArrayList<String>();
                Vector v = rowIterator.next();
                Iterator colIterator = v.iterator();
                while (colIterator.hasNext()) {
                    if (col++ == 0) {
                        cols.add(Long.toString((Long) colIterator.next()));
                    } else {
                        Object obj = colIterator.next();
                        String value;
                        if (obj == null) {
                            value = "null";
                        } else {
                            value = obj.toString();
                        }
                        cols.add(value);
                    }
                }
                rows.add(cols);
            }
        } catch (RuntimeException e) {
            throw e;
        } finally {
            closeEM(em);;
        }
        return rows;
    }
}
