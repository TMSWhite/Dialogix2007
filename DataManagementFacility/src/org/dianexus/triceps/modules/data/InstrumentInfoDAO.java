package org.dianexus.triceps.modules.data;

public interface InstrumentInfoDAO {

    boolean getInstrumentInfo(int versionid);
    boolean setInstrumentInfo();
    boolean updateInstrumentInfo();
    boolean deleteInstrumentInfo(int versionid);
    int getInstrumentVersionInfoId();
    void setInstrumentVersionInfoId(int infoID);
    void setInstrumentVersionId(int versionid);
    int getInstrumentVersionId();
    void setInstrumentInfoName(String name);
    String getInstrumentInfoName();
    void setInstrumentInfoValue(String value);
    String getInstrumentInfoValue();
    void setInstrumentInfoMemo(String memo);
    String getInstrumentInfoMemo();
}