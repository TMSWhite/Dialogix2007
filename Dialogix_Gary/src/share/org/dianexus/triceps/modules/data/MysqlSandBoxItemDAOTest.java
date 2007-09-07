package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlSandBoxItemDAOTest extends TestCase {
	int testIntValue = 123;
	SandBoxItemDAO testDAO;
	DialogixDAOFactory mdao;
	int lastInsertId;

	
	public void setUp() throws Exception {
		mdao =  DialogixDAOFactory.getDAOFactory(1);
		testDAO = mdao.getSandBoxItemDAO();
	}


	public void tearDown() throws Exception {
		if(mdao != null){
			mdao=null;
		}
		if(testDAO != null){
			testDAO = null;
		}
	}

	public void testInsert(){
		testDAO.setId(testIntValue);
		assertEquals(testDAO.getId(), testIntValue);
		testDAO.setInstrumentId(testIntValue);
		assertEquals(testDAO.getInstrumentId(),testIntValue);
		testDAO.setInstrumentVersionId(testIntValue);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		testDAO.setSandBoxId(testIntValue);
		assertEquals(testDAO.getSandBoxId(),testIntValue);
		
//		test insert new row
		assertTrue(testDAO.setSandBoxItem());
		assertTrue(testDAO.getId() > 0);
		this.lastInsertId = testDAO.getId();
		
//		test get back the row	
		assertTrue(testDAO.getSandBoxItem(this.lastInsertId));
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		assertEquals(testDAO.getInstrumentId(),testIntValue);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		assertEquals(testDAO.getSandBoxId(),testIntValue);
		
//		update the row	
		int testIntValue = 123456;
		testDAO.setId(this.lastInsertId);
		testDAO.setInstrumentId(testIntValue);
		testDAO.setInstrumentVersionId(testIntValue);
		testDAO.setSandBoxId(testIntValue);
		
		assertTrue(testDAO.updateSandBoxItem(testDAO.getId()));
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		assertEquals(testDAO.getInstrumentId(),testIntValue);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		assertEquals(testDAO.getSandBoxId(),testIntValue);
		
//		test delete row
		assertTrue(testDAO.deleteSandBoxItem(testDAO.getId()));   
		assertFalse(testDAO.getSandBoxItem(this.lastInsertId)); 
	}
		
}
