package org.dianexus.triceps.modules.data;

public interface InstrumentDAO {
	boolean getInstrument(int _id);
    boolean getInstrument(String _name);
    boolean setInstrument();
    boolean updateInstrument(int id);
    int getInstrumentLastInsertId();
    
    void setInstrumentId(int _id);
    int getInstrumentId();
    void setInstrumentName(String _instrumentName);
    public String getInstrumentName();
    void setInstrumentDescription(String instrument_description);
    public String getInstrumentDescription();  
}
