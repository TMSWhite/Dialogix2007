package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlInstrumentDAOTest extends TestCase {
	
	int testIntValue = 123;
	String testStringValue = "abc";
	InstrumentDAO testDAO;
	DialogixDAOFactory mdao;
	int lastInsertId;
	

	protected void setUp() throws Exception {
		mdao =  DialogixDAOFactory.getDAOFactory(1);
		testDAO = mdao.getInstrumentDAO();
	}

	protected void tearDown() throws Exception {
		if(mdao != null){
			mdao=null;
		}
		if(testDAO != null){
			testDAO = null;
		}
	}
	public void testInsert(){
		testDAO.setInstrumentDescription(testStringValue);
		assertEquals(testDAO.getInstrumentDescription(), testStringValue);
		testDAO.setInstrumentId(testIntValue);
		assertEquals(testDAO.getInstrumentId(),testIntValue);
		testDAO.setInstrumentName(testStringValue);
		assertEquals(testDAO.getInstrumentName(),testStringValue);
		
	// test insert new row
		assertTrue(testDAO.setInstrument());
		assertTrue(testDAO.getInstrumentId() > 0);
		this.lastInsertId = testDAO.getInstrumentId();
	
		//	test get back the row	
		assertTrue(testDAO.getInstrument(this.lastInsertId));
		assertEquals(testDAO.getInstrumentId(), this.lastInsertId);
		assertEquals(testDAO.getInstrumentDescription(),testStringValue);
		assertEquals(testDAO.getInstrumentName(),testStringValue);

		//update the row	
		String testStringValue = "this is a test string";
		testDAO.setInstrumentDescription(testStringValue);
		testDAO.setInstrumentId(this.lastInsertId);
		testDAO.setInstrumentName(testStringValue);
		
		assertTrue(testDAO.updateInstrument(this.lastInsertId));
		assertEquals(testDAO.getInstrumentDescription(),testStringValue);
		assertEquals(testDAO.getInstrumentId(),this.lastInsertId);
		assertEquals(testDAO.getInstrumentName(),testStringValue); 
		
		//	test delete row
		assertTrue(testDAO.deleteInstrument(this.lastInsertId));   
		assertFalse(testDAO.getInstrument(this.lastInsertId));

		
		
	}
}
