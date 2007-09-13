package org.dianexus.triceps.modules.map;

import java.util.ArrayList;
import java.util.Iterator;

import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.MappingDAO;
import org.dianexus.triceps.modules.data.MappingItemDAO;
import org.apache.log4j.Logger;

public class Map {
  static Logger logger = Logger.getLogger(Map.class);
	
	private MappingDAO mdao;
	private ArrayList mapItems = new ArrayList();
	private ArrayList mapItemIds;
	private String mapName;
	private String tableName;
	private int mapId;
	
	
	public Map(){
		
	}
	public int getMapId(){
		return this.mapId;
	}
	public void setMapName(String name){
		this.mapName = name;
	}
	public String getMapName(){
		return this.mapName;
	}
	public ArrayList getMapItems(){
		return this.mapItems;
	}
	public void setTableName(String table_name){
		this.tableName = table_name;
	}
	public String getTableName(){
		return this.tableName;
	}
	public MappingDAO getMappingDAO(){
		return this.mdao;
	}
	public MappingItemDAO getMappingItemDAO(int index){
		return (MappingItemDAO) this.mapItemIds.get(index);
	}
	public boolean loadMap(){
		
		boolean rtn = false;
		
		DialogixDAOFactory daof = DialogixDAOFactory.getDAOFactory(1);
		mdao = daof.getMappingDAO();
		logger.debug("got mdao");
		mdao.setMapName(this.getMapName());
		logger.debug("set map name to "+this.getMapName());
		//TODO check this corrections
		if(mdao.loadMapping(this.getMapId())){
			// get index of map items
			logger.debug("in loop");
			MappingItemDAO mad = daof.getMappingItemDAO();
			logger.debug("got mad");
			mapItemIds = mad.getTableItemsIndex(mdao.getId(),this.getTableName());
			logger.debug("got ids");
			Iterator it = mapItemIds.listIterator();
			logger.debug("map size is:"+mapItemIds.size());
			int i =0;
			while(it.hasNext()){
				MappingItemDAO mad2 = daof.getMappingItemDAO();
				logger.debug("in while");
				Integer id = (Integer)it.next();
				logger.debug("in while: id is"+id.intValue());
				if(mad2.readMappingItem(id.intValue())){
					logger.debug("in while mad data is: source col "+mad2.getSourceColumn()+" dest col is "+mad2.getDestinationColumn());
					mapItems.add(i,mad2);
					i++;
				
				}
				
			}
			rtn = true;
		}
		
		return rtn;
	}

}
