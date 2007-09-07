package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlMappingDAOTest extends TestCase {

	public void testMysqlMappingDAO(){
		MysqlMappingDAO mappingDAO=new MysqlMappingDAO();
		//set up new values into DB
		mappingDAO.setMapName("testMapName");
		mappingDAO.setMapDescription("testDescription");
		mappingDAO.setMap("testMap");
		assertTrue(mappingDAO.storeMapping());
       
		//get entered values from DB and verify them
		assertTrue(mappingDAO.loadMapping("testMapName"));
		assertEquals(mappingDAO.getMapName(),"testMapName");
        assertEquals(mappingDAO.getMapDescription(),"testDescription");
        assertEquals(mappingDAO.getMap(),"testMap");
        
        //try updating the entered values
		assertTrue(mappingDAO.loadMapping("testMapName"));
		mappingDAO.setMapName("newTestMapName");
		mappingDAO.setMapDescription("newTestDescription");
		mappingDAO.setMap("newTestMap");
        mappingDAO.updateMapping(mappingDAO.getId());

		//get entered values from DB and verify them
		assertTrue(mappingDAO.loadMapping("newTestMapName"));
		assertEquals(mappingDAO.getMapName(),"newTestMapName");
        assertEquals(mappingDAO.getMapDescription(),"newTestDescription");
        
        //clean up
		assertTrue(mappingDAO.loadMapping("newTestMapName"));
        assertTrue(mappingDAO.deleteMapping(mappingDAO.getId())); 
	}
}
