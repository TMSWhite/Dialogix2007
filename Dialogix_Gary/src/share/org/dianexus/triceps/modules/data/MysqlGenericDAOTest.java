package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlGenericDAOTest extends TestCase {
	
	private final String queryString="SELECT *  FROM `instrument` LIMIT 0 , 30";
	private final String createString="create table test(testInt int);";
	private final String insertString="insert into test(testInt) values(123);";
	private final String dropString="drop table test;";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testMysqlGenericDAO(){
		MysqlGenericDAO gDAO=new MysqlGenericDAO();
		gDAO.setQueryString(queryString);
		assertTrue(gDAO.runQuery());
		gDAO.setQueryString(createString);
		assertEquals(0,gDAO.runUpdate());
		gDAO.setQueryString(insertString);
		assertEquals(1,gDAO.runUpdate());
		gDAO.setQueryString(dropString);  //remove the test table
		gDAO.runUpdate();
	}
}
