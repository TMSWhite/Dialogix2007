package org.dianexus.triceps;

import java.util.ArrayList;
import java.util.Hashtable;

import org.dianexus.triceps.modules.data.PageHitEventsDAO;
import org.dianexus.triceps.modules.data.PageHitsDAO;
import org.dianexus.triceps.modules.data.RawDataDAO;
import org.apache.log4j.Logger;


public class TimingDataBean {
  static Logger logger = Logger.getLogger(TimingDataBean.class);
	
	private Hashtable pageHitEventBeans = new Hashtable();// not needed
	private Hashtable pageHitBeans = new Hashtable();
	private Hashtable rawDataBeans = new Hashtable();//not needed
	
	// key is display:num:beanId:beantype
	private ArrayList dirtyBeans = new ArrayList();
	//?? last phb id ??
	
	
	public boolean writeData(){
		boolean rtn= true;
		// iterate through the dirty beans array to find DAO objects that need update
		for(int i=0; i< dirtyBeans.size();i++){
			String key = (String) dirtyBeans.get(i);
			//logger.debug(" In write data testing key "+key+ " as data element "+i);
			if(key.endsWith("pageHit")){
				PageHitsDAO ph = (PageHitsDAO) this.getPageHitBean(key);
				try{
					if (key.startsWith("update")){
						rtn = ph.updatePageHit();
					}else{
					rtn = ph.setPageHit(); 
					}
					dirtyBeans.set(i, null);
				}catch(Exception e){
					logger.error("",e);
					rtn = false;
					
				}
				}
			else if (key.endsWith("pageHitEvent")){
				PageHitEventsDAO phe = (PageHitEventsDAO) this.getPageHitEventBean(key);
				try{
					if (key.startsWith("update")){
						rtn = phe.updatePageHitEvent();
					}else {
					
					rtn = phe.setPageHitEvent();
					}
					dirtyBeans.set(i, null);
				}catch(Exception e){
					logger.error("",e);
					rtn = false;
					
				}
			}
			else if  (key.endsWith("rawData")){
				RawDataDAO rdd = (RawDataDAO) this.getRawDataBean(key);
				try{
					if (key.startsWith("update")){
					rtn = rdd.updateRawData();
				} else {
					rtn = rdd.setRawData(); 
					}
					dirtyBeans.set(i, null);
				}catch(Exception e){
					logger.error("",e);
					rtn = false;
				}
			}
			
		}
		// remove all sucessfull updates from dirty array
		for(int i=0; i< dirtyBeans.size();i++){
			String key = (String) dirtyBeans.get(i);
			if(key == null){
				dirtyBeans.remove(i);
			}
		}
		return rtn;
	}
	public boolean addPageHitBean(PageHitsDAO bean,String key){
		boolean rtn = false;
		try{
		pageHitBeans.put(key, bean);
		dirtyBeans.add(key);
		rtn =  true;
		}catch (Exception e){
			logger.error("",e);
		}
		return rtn;
	}
	public PageHitsDAO getPageHitBean(String key){
		return (PageHitsDAO) pageHitBeans.get(key);
	}
	public boolean addPageHitEventBean(PageHitEventsDAO bean, String key){
		boolean rtn = false;
		try{
			pageHitEventBeans.put(key, bean);
			dirtyBeans.add(key);
			rtn = true;
		}catch(Exception e){
			logger.error("",e);
		}
	return rtn;
}
	public PageHitEventsDAO getPageHitEventBean(String key){
		return (PageHitEventsDAO) pageHitEventBeans.get(key);
	}
	public boolean addRawDataBean(RawDataDAO bean, String key){
		boolean rtn = false;
		try{
			rawDataBeans.put(key, bean);
			dirtyBeans.add(key);
			rtn = true;
		}catch (Exception e){
			logger.error("",e);
		}
		return rtn;
	}
	public RawDataDAO getRawDataBean(String key){
		return (RawDataDAO) rawDataBeans.get(key);
	}
}