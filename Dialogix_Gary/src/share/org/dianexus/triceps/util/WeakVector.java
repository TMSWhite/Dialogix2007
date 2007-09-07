package org.dianexus.triceps.util;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

public class WeakVector {
	
	WeakReference myRef ;
	
	public WeakVector(){
		 myRef = new WeakReference(new Vector());
	}
	
	public boolean destroy(){
		myRef=null;
		if(myRef == null){
			return true;
		}else{
			return false;
		}
		}
	
	public boolean add(Object ob){
		return ((Vector)myRef.get()).add(ob);
		
	}
	
	public void add(int i , Object ob){
		((Vector)myRef.get()).add(i,ob);
	}
	
	public boolean addAll(Collection col){
		return ((Vector)myRef.get()).addAll(col);
	}
	
	public boolean addAll(int i, Collection col){
		return ((Vector)myRef.get()).addAll(i, col);
	}
	public void addElement(Object ob){
		((Vector)myRef.get()).addElement(ob);
		
	}
	public int capacity(){
		return ((Vector)myRef.get()).capacity();
	}
	public void clear(){
		((Vector)myRef.get()).clear();
	}
	
	public boolean contains(Object ob){
		return ((Vector)myRef.get()).contains(ob);
	}
	
	public boolean containsAll(Collection col){
		return ((Vector)myRef.get()).containsAll(col);
	}
	public void copyInto(Collection[] col){
		((Vector)myRef.get()).copyInto(col);
	}
	public Object elementAt(int i){
		return ((Vector)myRef.get()).elementAt(i);
	}
	public Enumeration elements(){
		return ((Vector)myRef.get()).elements();
	}
	public void ensureCapacity(int i){
		((Vector)myRef.get()).ensureCapacity(i);
	}
	public Object firstElement(){
		return ((Vector)myRef.get()).firstElement();
	}
	public Object get(int i){
		return ((Vector)myRef.get()).get(i);
	}
	public int size(){
		return ((Vector)myRef.get()).size();
	}
	
}
