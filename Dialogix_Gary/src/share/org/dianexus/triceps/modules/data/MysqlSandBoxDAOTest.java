package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlSandBoxDAOTest extends TestCase{
	
	int testIntValue = 123;
	String testStringValue = "abc";
	SandBoxDAO testDAO;
	DialogixDAOFactory mdao;
	int lastInsertId;
	
	public void setUp() throws Exception {
		mdao =  DialogixDAOFactory.getDAOFactory(1);
		testDAO = mdao.getSandBoxDAO();
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
		testDAO.setName(testStringValue);
		assertEquals(testDAO.getName(),testStringValue);
		testDAO.setPort(testIntValue);
		assertEquals(testDAO.getPort(),testIntValue);
		testDAO.setURL(testStringValue);
		assertEquals(testDAO.getURL(),testStringValue);
		testDAO.setApplicationPath(testStringValue);
		assertEquals(testDAO.getApplicationPath(),testStringValue);
		
//		 test insert new row
		assertTrue(testDAO.setSandBox());
		assertTrue(testDAO.getId() > 0);
		this.lastInsertId = testDAO.getId();
		
//		test get back the row	
		assertTrue(testDAO.getSandBox(this.lastInsertId));
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getName(),testStringValue);
		assertEquals(testDAO.getPort(),testIntValue);
		assertEquals(testDAO.getURL(),testStringValue);
		assertEquals(testDAO.getApplicationPath(),testStringValue);
		
//		update the row	
		int testIntValue = 123456;
		String testStringValue = "this is a test string";
		testDAO.setId(this.lastInsertId);
		testDAO.setName(testStringValue);
		testDAO.setPort(testIntValue);
		testDAO.setURL(testStringValue);
		testDAO.setApplicationPath(testStringValue);
		
		assertTrue(testDAO.updateSandBox(this.lastInsertId));
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getName(),testStringValue);
		assertEquals(testDAO.getPort(),testIntValue);
		assertEquals(testDAO.getURL(),testStringValue);
		assertEquals(testDAO.getApplicationPath(),testStringValue);
		
//		test delete row
		assertTrue(testDAO.deleteSandBox(testDAO.getId()));   
		assertFalse(testDAO.getSandBox(this.lastInsertId)); 
	}
}
