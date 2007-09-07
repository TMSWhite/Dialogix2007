package org.dianexus.triceps.modules.data;
import junit.framework.TestCase;

public class MysqlInstrumentSessionDataDAOTest extends TestCase {

	public void testMysqlInstrumentSessionDataDAO() {
		
		/*		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query;
		
		
		MysqlInstrumentSessionDataDAO instrumentSessionDataDAO = new MysqlInstrumentSessionDataDAO();
		// set up new values into DB
		instrumentSessionDataDAO.setInstrumentSessionId(-1);
		instrumentSessionDataDAO.setUserId(-1);

		instrumentSessionDataDAO.setComments("testComment");
		instrumentSessionDataDAO.setStatus("testStatus");
		assertTrue(instrumentSessionDataDAO.setUserSession());

		// get entered values from DB and verify them
		assertTrue(userSessionDAO.getUserSession(userSessionDAO.getUserSessionId()));
		assertEquals(userSessionDAO.getUserId(), -1);

		assertEquals(userSessionDAO.getComments(), "testComment");
		assertEquals(userSessionDAO.getStatus(), "testStatus");

		// try updating the entered values
		assertTrue(userSessionDAO.getUserSession(userSessionDAO
				.getUserSessionId()));
		userSessionDAO.setUserId(-2);
		userSessionDAO.setComments("newTestComment");
		userSessionDAO.setStatus("newStatus");
		userSessionDAO.updateUserSession(userSessionDAO.getUserSessionId());

		// get entered values from DB and verify them
		assertTrue(userSessionDAO.getUserSession(userSessionDAO
				.getUserSessionId()));
		assertEquals(userSessionDAO.getUserId(), -2);
		assertEquals(userSessionDAO.getComments(), "newTestComment");
		assertEquals(userSessionDAO.getStatus(), "newStatus");

		// clean up
		assertTrue(userSessionDAO.deleteUserSession(userSessionDAO.getUserSessionId()));*/
	}
}
