package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlUserPermissionDAOTest extends TestCase {

	public void testMysqlUserPermissionDAO(){
		MysqlUserPermissionDAO userPermissionDAO=new MysqlUserPermissionDAO();
		//set up new values into DB
		userPermissionDAO.setUserId(-1);
		userPermissionDAO.setInstrumentId(-1);
		userPermissionDAO.setRole("testRole");
		userPermissionDAO.setComment("testComment");
        assertTrue(userPermissionDAO.setUserPermission());
  
		//get entered values from DB and verify them
		assertTrue(userPermissionDAO.getUserPermission(userPermissionDAO.getPermissionId()));
		assertEquals(userPermissionDAO.getUserId(),-1);
		assertEquals(userPermissionDAO.getInstrumentId(),-1);
		assertEquals(userPermissionDAO.getRole(),"testRole");
		assertEquals(userPermissionDAO.getComment(),"testComment");

        //try updating the entered values
		assertTrue(userPermissionDAO.getUserPermission(userPermissionDAO.getPermissionId()));
		userPermissionDAO.setUserId(-2);
		userPermissionDAO.setInstrumentId(-2);
		userPermissionDAO.setRole("newTestRole");
		userPermissionDAO.setComment("newTestComment");
		userPermissionDAO.updateUserPermission(userPermissionDAO.getPermissionId());

	    //get entered values from DB and verify them
		assertTrue(userPermissionDAO.getUserPermission(userPermissionDAO.getPermissionId()));
		assertEquals(userPermissionDAO.getUserId(),-2);
		assertEquals(userPermissionDAO.getInstrumentId(),-2);
		assertEquals(userPermissionDAO.getRole(),"newTestRole");
		assertEquals(userPermissionDAO.getComment(),"newTestComment");
        
        //clean up
        assertTrue(userPermissionDAO.deleteUserPermission(userPermissionDAO.getPermissionId()));    
	}
}
