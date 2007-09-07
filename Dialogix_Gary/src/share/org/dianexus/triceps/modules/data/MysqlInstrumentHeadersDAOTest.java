package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlInstrumentHeadersDAOTest extends TestCase {

	public void testMysqlInstrumentHeadersDAO(){
		MysqlInstrumentHeadersDAO instHeadDAO=new MysqlInstrumentHeadersDAO();
		//set up new values into DB
		instHeadDAO.setInstrumentVersionId(0);
		instHeadDAO.setReserved("test","testValue");
		instHeadDAO.setInstrumentHeaders();
        
		//get entered values from DB and verify them
		assertTrue(instHeadDAO.getInstrumentHeaders(instHeadDAO.getInstrumentHeadersLastInsertId()));
		assertEquals(instHeadDAO.getReservedVarName(),"test");
        assertEquals(instHeadDAO.getReservedVarValue(),"testValue");
        
        //try updating the entered values
        instHeadDAO.updateInstrumentHeader("newTestValue","test");

		//get entered values from DB and verify them
		assertTrue(instHeadDAO.getInstrumentHeaders(instHeadDAO.getInstrumentHeadersLastInsertId()));
		assertEquals(instHeadDAO.getReservedVarValue(),"newTestValue");
        assertEquals(instHeadDAO.getReservedVarValue(),"newTestValue");
        
        //clean up
        assertTrue(instHeadDAO.deleteInstrumentHeader("test"));
	}
}
