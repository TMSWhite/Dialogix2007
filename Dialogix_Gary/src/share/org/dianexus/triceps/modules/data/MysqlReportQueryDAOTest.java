package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;

import junit.framework.TestCase;



public class MysqlReportQueryDAOTest extends TestCase {
	
	int testIntValue = 123;
	String testStringValue = "abc";
	Timestamp ts = new Timestamp(100000000);
	ReportQueryDAO testDAO;
	DialogixDAOFactory mdao;
	int lastInsertId;

	
	public void setUp() throws Exception {
		mdao =  DialogixDAOFactory.getDAOFactory(1);
		testDAO = mdao.getReportQueryDAO();
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
		testDAO.setInstrumentVersionId(testIntValue);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		testDAO.setQueryString(testStringValue);
		assertEquals(testDAO.getQueryString(),testStringValue);
		testDAO.setTs(ts);
		assertEquals(testDAO.getTs(),ts);
		testDAO.setUserId(testIntValue);
		assertEquals(testDAO.getUserId(),testIntValue);
		
//		 test insert new row
		assertTrue(testDAO.setReportQueryDAO());
		assertTrue(testDAO.getId() > 0);
		this.lastInsertId = testDAO.getId();
		
//		test get back the row	
		assertTrue(testDAO.getReportQueryDAO(this.lastInsertId));
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		assertEquals(testDAO.getQueryString(),testStringValue);
		assertEquals(testDAO.getTs(),ts);
		assertEquals(testDAO.getUserId(),testIntValue);
		
//		update the row	
		int testIntValue = 123456;
		String testStringValue = "this is a test string";
		Timestamp ts = new Timestamp(1000000);
		testDAO.setId(this.lastInsertId);
		testDAO.setInstrumentVersionId(testIntValue);
		testDAO.setQueryString(testStringValue);
		testDAO.setTs(ts);
		testDAO.setUserId(testIntValue);
		
		assertTrue(testDAO.updateReportQueryDAO());
		assertEquals(testDAO.getId(), this.lastInsertId);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		assertEquals(testDAO.getQueryString(),testStringValue);
		assertEquals(testDAO.getTs(),ts);
		assertEquals(testDAO.getUserId(),testIntValue); 
		
//		test delete row
		assertTrue(testDAO.deleteReportQueryDAO(testDAO.getId()));   
		assertFalse(testDAO.getReportQueryDAO(this.lastInsertId)); 
	}	
}
	
