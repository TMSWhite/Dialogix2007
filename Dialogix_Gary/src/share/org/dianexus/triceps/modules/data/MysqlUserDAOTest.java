package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlUserDAOTest extends TestCase {
	
	public void testMysqlUserDAO(){
		MysqlUserDAO userDAO=new MysqlUserDAO();
		//set up new values into DB
		userDAO.setFirstName("testFirstName");
		userDAO.setLastName("testLastName");
        userDAO.setUserName("testUser");
        userDAO.setPassword("testPassword");
        userDAO.setEmail("testEmail");
        userDAO.setPhone("testPhoneNumber");
        assertTrue(userDAO.setUser());
        
		//get entered values from DB and verify them
		assertTrue(userDAO.getUser(userDAO.getId()));
		assertEquals(userDAO.getFirstName(),"testFirstName");
        assertEquals(userDAO.getLastName(),"testLastName");
        assertEquals(userDAO.getUserName(),"testUser");
        assertEquals(userDAO.getPassword(),"testPassword");
        assertEquals(userDAO.getEmail(),"testEmail");
        assertEquals(userDAO.getPhone(),"testPhoneNumber");
        
        //try updating the entered values
		assertTrue(userDAO.getUser(userDAO.getId()));
		userDAO.setFirstName("newTestFirstName");
		userDAO.setLastName("newTestLastName");
        userDAO.setUserName("newTestUser");
        userDAO.setPassword("newTestPassword");
        userDAO.setEmail("newTestEmail");
        userDAO.setPhone("newTestPhoneNumber");
		userDAO.updateUser(userDAO.getId());

	    //get entered values from DB and verify them
		assertTrue(userDAO.getUser(userDAO.getId()));
		assertEquals(userDAO.getFirstName(),"newTestFirstName");
        assertEquals(userDAO.getLastName(),"newTestLastName");
        assertEquals(userDAO.getUserName(),"newTestUser");
        assertEquals(userDAO.getPassword(),"newTestPassword");
        assertEquals(userDAO.getEmail(),"newTestEmail");
        assertEquals(userDAO.getPhone(),"newTestPhoneNumber");
        
        //clean up
        assertTrue(userDAO.deleteUser(userDAO.getId()));
	}
}
