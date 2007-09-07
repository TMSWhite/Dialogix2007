package org.dianexus.triceps.modules.data;


import junit.framework.TestCase;

public class MysqlInstrumentContentsDAOTest extends TestCase {
	
	int testIntValue = 123;
	String testStringValue = "abc";
	char testcharvalue = 'a';
	InstrumentContentsDAO testDAO;
	DialogixDAOFactory mdao;
	int lastInsertId;

	protected void setUp() throws Exception {
		
		mdao =  DialogixDAOFactory.getDAOFactory(1);
		testDAO = mdao.getInstrumentContentsDAO();
		
	}

	protected void tearDown() throws Exception {
		
		if(mdao != null){
			mdao=null;
		}
		if(testDAO != null){
			testDAO = null;
		}
		
	}
	
	public void testInstrumentContents() {
		//test getters and setters
		testDAO.setInstrumentName(testStringValue);
		assertEquals(testDAO.getInstrumentName(), testStringValue);
		testDAO.setInstrumentVarNum(testIntValue);
		assertEquals(testDAO.getInstrumentVarNum(),testIntValue);
		testDAO.setInstrumentVarName(testStringValue);
		assertEquals(testDAO.getInstrumentVarName(),testStringValue);
		testDAO.setInstrumentC8Name(testStringValue);
		assertEquals(testDAO.getInstrumentC8Name(),testStringValue);
		testDAO.setDisplayName(testStringValue);
		assertEquals(testDAO.getDisplayName(),testStringValue);
		testDAO.setGroupNum(testIntValue);
		assertEquals(testDAO.getGroupNum(),testIntValue);
		testDAO.setConcept(testStringValue);
		assertEquals(testDAO.getConcept(),testStringValue);
		testDAO.setRelevance(testStringValue);
		assertEquals(testDAO.getRelevance(),testStringValue);
		testDAO.setActionType(testcharvalue);
		assertEquals(testDAO.getActionType(),testcharvalue);
		testDAO.setValidation(testStringValue);
		assertEquals(testDAO.getValidation(),testStringValue);
		testDAO.setReturnType(testStringValue);
		assertEquals(testDAO.getReturnType(),testStringValue);
		testDAO.setMinValue(testStringValue);
		assertEquals(testDAO.getMinValue(),testStringValue);
		testDAO.setMaxValue(testStringValue);
		assertEquals(testDAO.getMaxValue(),testStringValue);
		testDAO.setOtherValues(testStringValue);
		assertEquals(testDAO.getOtherValues(),testStringValue);
		testDAO.setInputMask(testStringValue);
		assertEquals(testDAO.getInputMask(),testStringValue);
		testDAO.setFormatMask(testStringValue);
		assertEquals(testDAO.getFormatMask(),testStringValue);
		testDAO.setDisplayType(testStringValue);
		assertEquals(testDAO.getDisplayType(),testStringValue);
		testDAO.setIsRequired(testIntValue);
		assertEquals(testDAO.getIsRequired(),testIntValue);
		testDAO.setIsMessage(testIntValue);
		assertEquals(testDAO.getIsMessage(),testIntValue);
		testDAO.setLevel(testStringValue);
		assertEquals(testDAO.getLevel(),testStringValue);
		testDAO.setSPSSFormat(testStringValue);
		assertEquals(testDAO.getSPSSFormat(),testStringValue);
		testDAO.setSASFormat(testStringValue);
		assertEquals(testDAO.getSASFormat(),testStringValue);
		testDAO.setSASInformat(testStringValue);
		assertEquals(testDAO.getSASInformat(),testStringValue);
		testDAO.setAnswersNumeric(testIntValue);
		assertEquals(testDAO.getAnswersNumeric(),testIntValue);
		testDAO.setDefaultAnswer(testStringValue);
		assertEquals(testDAO.getDefaultAnswer(),testStringValue);
		testDAO.setDefaultComment(testStringValue);
		assertEquals(testDAO.getDefaultComment(),testStringValue);
		testDAO.setLOINCProperty(testStringValue);
		assertEquals(testDAO.getLOINCProperty(),testStringValue);
		testDAO.setLOINCTimeAspect(testStringValue);
		assertEquals(testDAO.getLOINCTimeAspect(),testStringValue);
		testDAO.setLOINCSystem(testStringValue);
		assertEquals(testDAO.getLOINCSystem(),testStringValue);
		testDAO.setLOINCScale(testStringValue);
		assertEquals(testDAO.getLOINCScale(),testStringValue);
		testDAO.setLOINCMethod(testStringValue);
		assertEquals(testDAO.getLOINCMethod(),testStringValue);
		testDAO.setLOINCNum(testStringValue);
		assertEquals(testDAO.getLOINCNum(),testStringValue);
		
		
	// test insert new row
		assertTrue(testDAO.setInstrumentContents());
		assertTrue(testDAO.getInstrumentId() > 0);
		this.lastInsertId = testDAO.getInstrumentId();
		//test get back the row
		assertTrue(testDAO.getInstrumentContents(this.lastInsertId));
		assertEquals(testDAO.getInstrumentId(), this.lastInsertId);
		assertEquals(testDAO.getInstrumentName(),testStringValue);
		assertEquals(testDAO.getInstrumentVarNum(),testIntValue);
		assertEquals(testDAO.getInstrumentVarName(),testStringValue);
		assertEquals(testDAO.getInstrumentC8Name(),testStringValue);
		assertEquals(testDAO.getDisplayName(),testStringValue);
		assertEquals(testDAO.getGroupNum(),testIntValue);
		assertEquals(testDAO.getConcept(),testStringValue);
		assertEquals(testDAO.getRelevance(),testStringValue);
		assertEquals(testDAO.getActionType(),testcharvalue);
		assertEquals(testDAO.getValidation(),testStringValue);
		assertEquals(testDAO.getReturnType(),testStringValue);
		assertEquals(testDAO.getMinValue(),testStringValue);
		assertEquals(testDAO.getMaxValue(),testStringValue);
		assertEquals(testDAO.getOtherValues(),testStringValue);
		assertEquals(testDAO.getInputMask(),testStringValue);
		assertEquals(testDAO.getFormatMask(),testStringValue);
		assertEquals(testDAO.getDisplayType(),testStringValue);
		assertEquals(testDAO.getIsRequired(),testIntValue);
		assertEquals(testDAO.getIsMessage(),testIntValue);
		assertEquals(testDAO.getLevel(),testStringValue);
		assertEquals(testDAO.getSPSSFormat(),testStringValue);
		assertEquals(testDAO.getSASInformat(),testStringValue);
		assertEquals(testDAO.getSASFormat(),testStringValue);
		assertEquals(testDAO.getAnswersNumeric(),testIntValue);
		assertEquals(testDAO.getDefaultAnswer(),testStringValue);
		assertEquals(testDAO.getDefaultComment(),testStringValue);
		assertEquals(testDAO.getLOINCProperty(),testStringValue);
		assertEquals(testDAO.getLOINCTimeAspect(),testStringValue);
		assertEquals(testDAO.getLOINCSystem(),testStringValue);
		assertEquals(testDAO.getLOINCScale(),testStringValue);
		assertEquals(testDAO.getLOINCMethod(),testStringValue);
		assertEquals(testDAO.getLOINCNum(),testStringValue);
		
	
		//test update row
		  
		int testIntValue = 123456;
		String testStringValue = "this is a test string";
		char testcharvalue = 's';
		testDAO.setInstrumentName(testStringValue);
		testDAO.setInstrumentVarNum(testIntValue);
		testDAO.setInstrumentC8Name(testStringValue);
		testDAO.setDisplayName(testStringValue);
		testDAO.setGroupNum(testIntValue);
		testDAO.setConcept(testStringValue);
		testDAO.setRelevance(testStringValue);
		testDAO.setActionType(testcharvalue);
		testDAO.setValidation(testStringValue);
		testDAO.setReturnType(testStringValue);
		testDAO.setMinValue(testStringValue);
		testDAO.setMaxValue(testStringValue);
		testDAO.setOtherValues(testStringValue);
		testDAO.setInputMask(testStringValue);
		testDAO.setFormatMask(testStringValue);
		testDAO.setDisplayType(testStringValue);
		testDAO.setIsRequired(testIntValue);
		testDAO.setIsMessage(testIntValue);
		testDAO.setLevel(testStringValue);
		testDAO.setSPSSFormat(testStringValue);
		testDAO.setSASFormat(testStringValue);
		testDAO.setSASInformat(testStringValue);
		testDAO.setAnswersNumeric(testIntValue);
		testDAO.setDefaultAnswer(testStringValue);
		testDAO.setDefaultComment(testStringValue);
		testDAO.setLOINCProperty(testStringValue);
		testDAO.setLOINCTimeAspect(testStringValue);
		testDAO.setLOINCSystem(testStringValue);
		testDAO.setLOINCScale(testStringValue);
		testDAO.setLOINCMethod(testStringValue);
		testDAO.setLOINCNum(testStringValue);
		
		assertTrue(testDAO.updateInstrumentContents());
		assertEquals(testDAO.getInstrumentName(),testStringValue);
		assertEquals(testDAO.getInstrumentVarNum(),testIntValue);
		assertEquals(testDAO.getInstrumentC8Name(),testStringValue);
		assertEquals(testDAO.getDisplayName(),testStringValue);
		assertEquals(testDAO.getGroupNum(),testIntValue);
		assertEquals(testDAO.getConcept(),testStringValue);
		assertEquals(testDAO.getRelevance(),testStringValue);
		assertEquals(testDAO.getActionType(),testcharvalue);
		assertEquals(testDAO.getValidation(),testStringValue);
		assertEquals(testDAO.getReturnType(),testStringValue);
		assertEquals(testDAO.getMinValue(),testStringValue);
		assertEquals(testDAO.getMaxValue(),testStringValue);
		assertEquals(testDAO.getOtherValues(),testStringValue);
		assertEquals(testDAO.getInputMask(),testStringValue);
		assertEquals(testDAO.getFormatMask(),testStringValue);
		assertEquals(testDAO.getDisplayType(),testStringValue);
		assertEquals(testDAO.getIsRequired(),testIntValue);
		assertEquals(testDAO.getIsMessage(),testIntValue);
		assertEquals(testDAO.getLevel(),testStringValue);
		assertEquals(testDAO.getSPSSFormat(),testStringValue);
		assertEquals(testDAO.getSASInformat(),testStringValue);
		assertEquals(testDAO.getSASFormat(),testStringValue);
		assertEquals(testDAO.getAnswersNumeric(),testIntValue);
		assertEquals(testDAO.getDefaultAnswer(),testStringValue);
		assertEquals(testDAO.getDefaultComment(),testStringValue);
		assertEquals(testDAO.getLOINCProperty(),testStringValue);
		assertEquals(testDAO.getLOINCTimeAspect(),testStringValue);
		assertEquals(testDAO.getLOINCSystem(),testStringValue);
		assertEquals(testDAO.getLOINCScale(),testStringValue);
		assertEquals(testDAO.getLOINCMethod(),testStringValue);
		assertEquals(testDAO.getLOINCNum(),testStringValue);

		//test delete row
		assertTrue(testDAO.deleteInstrumentContents(testDAO.getInstrumentId()));   
		assertFalse(testDAO.getInstrumentContents(this.lastInsertId)); 

	}

	}

