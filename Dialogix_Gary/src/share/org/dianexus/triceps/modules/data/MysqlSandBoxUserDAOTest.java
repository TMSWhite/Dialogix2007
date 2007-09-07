package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlSandBoxUserDAOTest extends TestCase {
	
	int testIntValue = 123;
	SandBoxUserDAO testDAO;
	DialogixDAOFactory mdao;
	int lastInsertId;
	
	protected void setUp() throws Exception {
		mdao =  DialogixDAOFactory.getDAOFactory(1);
		testDAO = mdao.getSandBoxUserDAO();
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
		testDAO.setId(testIntValue);
		assertEquals(testDAO.getId(), testIntValue);
		testDAO.setSandBoxId(testIntValue);
		assertEquals(testDAO.getSandBoxId(),testIntValue);
		testDAO.setSandBoxUserId(testIntValue);
		assertEquals(testDAO.getSandBoxUserId(),testIntValue);
		testDAO.setSandBoxUserRoleId(testIntValue);
		assertEquals(testDAO.getSandBoxUserRoleId(),testIntValue);
		
//		 test insert new row
		assertTrue(testDAO.setSandBoxUser());
		assertTrue(testDAO.getId() > 0);
		this.lastInsertId = testDAO.getId();
		
//		test get back the row	
		assertTrue(testDAO.getSandUserSandboxes(testDAO.getSandBoxUserId()));
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getSandBoxId(),testIntValue);
		assertEquals(testDAO.getSandBoxUserId(),testIntValue);
		assertEquals(testDAO.getSandBoxUserRoleId(),testIntValue);
		
//		update the row	
		int testIntValue = 123456;
		testDAO.setId(this.lastInsertId);
		testDAO.setSandBoxId(testIntValue);
		testDAO.setSandBoxUserId(testIntValue);
		testDAO.setSandBoxUserRoleId(testIntValue);
		
		assertTrue(testDAO.updateSandBoxUser());
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getSandBoxId(),testIntValue);
		assertEquals(testDAO.getSandBoxUserId(),testIntValue);
		assertEquals(testDAO.getSandBoxUserRoleId(),testIntValue);
		
//		test delete row
		assertTrue(testDAO.deleteSandBoxUser(testDAO.getId()));   
		//assertFalse(testDAO.getSandUserSandboxes(testDAO.getSandBoxUserId())); 

		
	}
	
	
}
