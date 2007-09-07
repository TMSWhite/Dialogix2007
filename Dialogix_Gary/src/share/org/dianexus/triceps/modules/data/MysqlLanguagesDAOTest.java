package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlLanguagesDAOTest extends TestCase{

	public void testMysqlLanguagesDAO(){
		MysqlLanguagesDAO languagesDAO=new MysqlLanguagesDAO();
		//set up new values into DB
		languagesDAO.setLanguageName("testChinese");
	 	languagesDAO.setDialogixAbbrev("tcn");
	 	languagesDAO.setCode("t123");
	    languagesDAO.setDesc("The Chinese language");
	    assertTrue(languagesDAO.setLanguagesDAO());
        
	    //get entered values from DB and verify them
		assertTrue(languagesDAO.getLanguagesDAO("t123"));
		assertEquals(languagesDAO.getLanguageName(),"testChinese");
        assertEquals(languagesDAO.getDialogixAbbrev(),"tcn");
        assertEquals(languagesDAO.getCode(),"t123");
        assertEquals(languagesDAO.getDesc(),"The Chinese language");
        
        //try updating the entered values
        languagesDAO.setDialogixAbbrev("newtcn");
        languagesDAO.setCode("newt123");
        assertTrue(languagesDAO.updateLanguagesDAO());

		//get entered values from DB and verify them
		assertTrue(languagesDAO.getLanguagesDAO("newt123"));
		assertEquals(languagesDAO.getDialogixAbbrev(),"newtcn");
        
        //clean up
		assertTrue(languagesDAO.getLanguagesDAO("new123"));
        assertTrue(languagesDAO.deleteLanguagesDAO());
	}
}
