package org.dianexus.triceps.modules.data;



	public interface InstrumentVersionDAO {
		
	
	    boolean getInstrumentVersion( int _id, int major, int minor);
	    boolean getInstrumentVersion(int id);
	    boolean setInstrumentVersion();
	    boolean updateInstrumentversion();
	    boolean deleteInstrumentVersion(int _id);
	    int getInstrumentVersionLastInsertId();
	    public boolean InstrumentMajorVersionExists(int _id, int major );
	    
	    void setInstrumentVersionId(int id);
	    int getInstrumentVersionId();
	    void setInstrumentId(int id);
	    int getInstrumentId();
	    void setInstanceTableName(String name);
	    String getInstanceTableName();
	    void setInstrumentNotes(String notes);
	    String getInstrumentNotes();
	    void setInstrumentStatus(int status);
	    int getInstrumentStatus();
	    void setInstrumentVersionMajor(int version);
	    int getInstrumentVersionMajor();
	    void setInstrumentVersionMinor(int version);
	    int getInstrumentVersionMinor();

	}



