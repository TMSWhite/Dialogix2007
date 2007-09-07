package org.dianexus.triceps.modules.data;

import junit.framework.TestCase;

public class MysqlMappingItemDAOTest extends TestCase{

	public void testMysqlMappingItemDAO(){
		MysqlMappingItemDAO mappingItemDAO=new MysqlMappingItemDAO();
		//set up new values into DB
		mappingItemDAO.setMappingId(-1);
		mappingItemDAO.setSourceColumn(-1);
		mappingItemDAO.setSourceColumnName("testSource");
        mappingItemDAO.setDestinationColumn(-1);
        mappingItemDAO.setDestinationColumnName("testDestination");
        mappingItemDAO.setTableName("testTable");
        mappingItemDAO.setDescription("TestDescription");
        assertTrue(mappingItemDAO.writeMappingItem());
        
		//get entered values from DB and verify them
		assertTrue(mappingItemDAO.readMappingItem(mappingItemDAO.getId()));
		assertEquals(mappingItemDAO.getMappingId(),-1);
        assertEquals(mappingItemDAO.getSourceColumn(),-1);
        assertEquals(mappingItemDAO.getSourceColumnName(),"testSource");
        assertEquals(mappingItemDAO.getDestinationColumn(),-1);
        assertEquals(mappingItemDAO.getDestinationColumnName(),"testDestination");
        assertEquals(mappingItemDAO.getTableName(),"testTable");
        assertEquals(mappingItemDAO.getDescription(),"TestDescription");
        
      //try updating the entered values
		assertTrue(mappingItemDAO.readMappingItem(mappingItemDAO.getId()));
		mappingItemDAO.setMappingId(-2);
		mappingItemDAO.setSourceColumn(-2);
		mappingItemDAO.setSourceColumnName("newTestSource");
        mappingItemDAO.setDestinationColumn(-2);
        mappingItemDAO.setDestinationColumnName("newTestDestination");
        mappingItemDAO.setTableName("newTestTable");
        mappingItemDAO.setDescription("newTestDescription");
		mappingItemDAO.updateMappingItem(mappingItemDAO.getId());

			//get entered values from DB and verify them
		assertTrue(mappingItemDAO.readMappingItem(mappingItemDAO.getId()));
		assertEquals(mappingItemDAO.getMappingId(),-2);
        assertEquals(mappingItemDAO.getSourceColumn(),-2);
        assertEquals(mappingItemDAO.getSourceColumnName(),"newTestSource");
        assertEquals(mappingItemDAO.getDestinationColumn(),-2);
        assertEquals(mappingItemDAO.getDestinationColumnName(),"newTestDestination");
        assertEquals(mappingItemDAO.getTableName(),"newTestTable");
        assertEquals(mappingItemDAO.getDescription(),"newTestDescription");
        
        //clean up
        assertTrue(mappingItemDAO.deleteMappingItem(mappingItemDAO.getId()));
	}
}
