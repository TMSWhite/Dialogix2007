package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlInstrumentInfoDAOTest extends TestCase{
	
	public void testMysqlInstrumentInfoDAO(){
		MysqlInstrumentInfoDAO instInfoDAO=new MysqlInstrumentInfoDAO();
		//set up new values into DB
		instInfoDAO.setInstrumentVersionId(-1);
		instInfoDAO.setInstrumentInfoName("testInstrument");
		instInfoDAO.setInstrumentInfoValue("testValue");
		instInfoDAO.setInstrumentInfoMemo("testMemo");
		instInfoDAO.setInstrumentInfo();
		
        //get entered values from DB and verify them
		assertTrue(instInfoDAO.getInstrumentInfo(-1));
		assertEquals(instInfoDAO.getInstrumentInfoName(),"testInstrument");
        assertEquals(instInfoDAO.getInstrumentInfoValue(),"testValue");
        assertEquals(instInfoDAO.getInstrumentInfoMemo(),"testMemo");
		
		//try updating the entered values
        instInfoDAO.setInstrumentInfoName("newTestInstrument");
        instInfoDAO.setInstrumentInfoValue("newTestValue");
		instInfoDAO.setInstrumentInfoMemo("newTestMemo");
        instInfoDAO.updateInstrumentInfo();
        
        //verify again
		assertTrue(instInfoDAO.getInstrumentInfo(-1));
		assertEquals(instInfoDAO.getInstrumentInfoName(),"newTestInstrument");
        assertEquals(instInfoDAO.getInstrumentInfoValue(),"newTestValue");
        assertEquals(instInfoDAO.getInstrumentInfoMemo(),"newTestMemo");
        
        //clean up
        assertTrue(instInfoDAO.deleteInstrumentInfo(-1));
	}
}
