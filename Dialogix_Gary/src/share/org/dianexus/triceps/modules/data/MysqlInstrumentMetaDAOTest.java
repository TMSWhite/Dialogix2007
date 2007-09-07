package org.dianexus.triceps.modules.data;

import java.sql.Timestamp;

import junit.framework.TestCase;

public class MysqlInstrumentMetaDAOTest extends TestCase {
	
	int testIntValue = 123;
	String testStringValue = "abc";
	Timestamp ts = new Timestamp(100000000);
	InstrumentMetaDAO testDAO;
	DialogixDAOFactory mdao;
	int lastInsertId;

	
	public void setUp() throws Exception {
		mdao =  DialogixDAOFactory.getDAOFactory(1);
		testDAO = mdao.getInstrumentMetaDAO();
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
		testDAO.setTitle(testStringValue);
		assertEquals(testDAO.getTitle(),testStringValue);
		testDAO.setVersion(testStringValue);
		assertEquals(testDAO.getVersion(),testStringValue);
		testDAO.setCreationDate(ts);
		assertEquals(testDAO.getCreationDate(),ts);
		testDAO.setNumVars(testIntValue);
		assertEquals(testDAO.getNumVars(),testIntValue);
		testDAO.setVarListMD5(testStringValue);
		assertEquals(testDAO.getVarListMD5(),testStringValue);
		testDAO.setNumLanguages(testIntValue);
		assertEquals(testDAO.getNumLanguages(),testIntValue);
		testDAO.setNumInstructions(testIntValue);
		assertEquals(testDAO.getNumInstructions(),testIntValue);
		testDAO.setNumEquations(testIntValue);
		assertEquals(testDAO.getNumEquations(),testIntValue);
		testDAO.setNumQuestions(testIntValue);
		assertEquals(testDAO.getNumQuestions(),testIntValue);
		testDAO.setNumBranches(testIntValue);
		assertEquals(testDAO.getNumBranches(),testIntValue);
		testDAO.setNumTailorings(testIntValue);
		assertEquals(testDAO.getNumTailorings(),testIntValue);
		testDAO.setInstrumentMD5(testStringValue);
		assertEquals(testDAO.getInstrumentMD5(),testStringValue);
		testDAO.setLanguageList(testStringValue);
		assertEquals(testDAO.getLanguageList(),testStringValue);
		testDAO.setInstrumentVersionId(testIntValue);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		
//		 test insert new row
		assertTrue(testDAO.setInstrumentMeta());
		assertTrue(testDAO.getInstrumentMetaLastInsertId() > 0);
		this.lastInsertId = testDAO.getInstrumentMetaLastInsertId();
//		test get back the row	
		assertTrue(testDAO.getInstrumentMeta(testDAO.getInstrumentMetaLastInsertId()));
		assertEquals(testDAO.getTitle(),testStringValue);
		assertEquals(testDAO.getVersion(),testStringValue);
		assertEquals(testDAO.getCreationDate(),ts);
		assertEquals(testDAO.getNumVars(),testIntValue);
		assertEquals(testDAO.getVarListMD5(),testStringValue);
		assertEquals(testDAO.getNumLanguages(),testIntValue);
		assertEquals(testDAO.getNumInstructions(),testIntValue);
		assertEquals(testDAO.getNumEquations(),testIntValue);
		assertEquals(testDAO.getNumQuestions(),testIntValue);
		assertEquals(testDAO.getNumBranches(),testIntValue);
		assertEquals(testDAO.getNumTailorings(),testIntValue);
		assertEquals(testDAO.getInstrumentMD5(),testStringValue);
		assertEquals(testDAO.getLanguageList(),testStringValue);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		//update the row
		int testIntValue = 123456;
		String testStringValue = "this is a test string";
		Timestamp ts = new Timestamp(1000000);
		testDAO.setTitle(testStringValue);
		testDAO.setVersion(testStringValue);
		testDAO.setCreationDate(ts);
		testDAO.setNumVars(testIntValue);
		testDAO.setVarListMD5(testStringValue);
		testDAO.setNumLanguages(testIntValue);
		testDAO.setNumInstructions(testIntValue);
		testDAO.setNumEquations(testIntValue);
		testDAO.setNumQuestions(testIntValue);
		testDAO.setNumBranches(testIntValue);
		testDAO.setNumTailorings(testIntValue);
		testDAO.setInstrumentMD5(testStringValue);
		testDAO.setLanguageList(testStringValue);
		testDAO.setInstrumentVersionId(testIntValue);
		
		assertTrue(testDAO.updateInstrumentMeta(testStringValue,testStringValue));
		assertEquals(testDAO.getTitle(),testStringValue);
		assertEquals(testDAO.getVersion(),testStringValue);
		assertEquals(testDAO.getCreationDate(),ts);
		assertEquals(testDAO.getNumVars(),testIntValue);
		assertEquals(testDAO.getVarListMD5(),testStringValue);
		assertEquals(testDAO.getNumLanguages(),testIntValue);
		assertEquals(testDAO.getNumInstructions(),testIntValue);
		assertEquals(testDAO.getNumEquations(),testIntValue);
		assertEquals(testDAO.getNumQuestions(),testIntValue);
		assertEquals(testDAO.getNumBranches(),testIntValue);
		assertEquals(testDAO.getNumTailorings(),testIntValue);
		assertEquals(testDAO.getInstrumentMD5(),testStringValue);
		assertEquals(testDAO.getLanguageList(),testStringValue);
		assertEquals(testDAO.getInstrumentVersionId(),testIntValue);
		
//		test delete row
		assertTrue(testDAO.deleteInstrumentMeta(testDAO.getInstrumentMetaLastInsertId()));   
		assertFalse(testDAO.getInstrumentMeta(this.lastInsertId)); 
		
		
		
	}
		

}
