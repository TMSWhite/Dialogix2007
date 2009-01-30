package org.dialogix.session;

import java.util.ArrayList;
import javax.ejb.Local;
import org.dialogix.entities.InstrumentSession;
import org.dialogix.entities.InstrumentVersion;

@Local
public interface InstrumentVersionHorizontalFacadeLocal {
    void create(Long instrumentVersionId, ArrayList<Long> varNameIds);
    ArrayList<String> getRow(InstrumentSession instrumentSession, ArrayList<Long> varNameIds);
    ArrayList<ArrayList<String>> getRows(InstrumentVersion instrumentVersion, ArrayList<Long> varNameIds);
    void merge();
    void persist();
    void setInstrumentSessionId(Long instrumentSessionId);
    void setInstrumentVersionId(Long instrumentVersionId);
    void updateColumnValue(Long column, String value);
}
