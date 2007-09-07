package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;
import java.util.Date;
import junit.framework.TestCase;

public class MysqlUserSessionDAOTest extends TestCase {

	public void testMysqlUserSessionDAO() {
		MysqlUserSessionDAO userSessionDAO=new MysqlUserSessionDAO();
		//set up new values into DB
		userSessionDAO.setInstrumentSessionId(-1);
		userSessionDAO.setUserId(11111111);
		Date currentTime=new Date();
		Timestamp tempStamp=new Timestamp(currentTime.getTime()/1000*1000);
		userSessionDAO.setTimestamp(tempStamp);
		userSessionDAO.setComments("testComment");
		userSessionDAO.setStatus("testStatus");
        assertTrue(userSessionDAO.setUserSession());
  
		//get entered values from DB and verify them
		assertTrue(userSessionDAO.getUserSession(userSessionDAO.getUserSessionId()));
		assertEquals(userSessionDAO.getUserId(),11111111);
		assertEquals(userSessionDAO.getTimestamp(),tempStamp);
		assertEquals(userSessionDAO.getComments(),"testComment");
		assertEquals(userSessionDAO.getStatus(),"testStatus");

        //try updating the entered values
		assertTrue(userSessionDAO.getUserSession(userSessionDAO.getUserSessionId()));
		userSessionDAO.setUserId(22222222);
		Date currentTime2=new Date();
		tempStamp.setTime(currentTime2.getTime()/1000*1000);
		userSessionDAO.setTimestamp(tempStamp);
		userSessionDAO.setComments("newTestComment");
		userSessionDAO.setStatus("newStatus");
		userSessionDAO.updateUserSession();

	    //get entered values from DB and verify them
		assertTrue(userSessionDAO.getUserSession(userSessionDAO.getUserSessionId()));
		assertEquals(userSessionDAO.getUserId(),22222222);
		assertEquals(userSessionDAO.getTimestamp(),tempStamp);
		assertEquals(userSessionDAO.getComments(),"newTestComment");
		assertEquals(userSessionDAO.getStatus(),"newStatus");
        
        //clean up
        assertTrue(userSessionDAO.deleteUserSession(userSessionDAO.getUserSessionId()));  
	}
}
