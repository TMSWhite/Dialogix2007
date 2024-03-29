/* ********************************************************
 ** Copyright (c) 2000-2006, Thomas Maxwell White, all rights reserved.
 ** DialogixDAOFactory.java ,v 3.0.0 Created on February 23, 2006, 11:27 AM
 ** @author Gary Lyons
 ******************************************************** */

package org.dianexus.triceps.modules.data;
/**
 * DialogixDAOFactory is an implementation of the Abstract DAO Factory Design Pattern.
 * <p>
 * When the underlying storage is subject to change from one implementation to another, 
 * this strategy may be implemented using the Abstract Factory pattern. The Abstract Factory
 * can in turn build on and use the Factory Method implementation, as suggested in 
 * Design Patterns: Elements of Reusable Object-Oriented Software [GoF]. In this case, 
 * this strategy provides an abstract DAO factory object (Abstract Factory) that can 
 * construct various types of concrete DAO factories, each factory supporting a different 
 * type of persistent storage implementation. Once you obtain the concrete DAO factory for 
 * a specific implementation, you use it to produce DAOs supported and implemented in that 
 * implementation.
**/
public abstract class  DialogixDAOFactory {
    
    // put these statics in a properties file
    //defines which database to use
    public static final int MYSQL = 1;
    public static final int ORACLE = 2;   
    
   // instrument definition DAO's

    public abstract InstrumentDAO getInstrumentDAO();
    public abstract InstrumentVersionDAO getInstrumentVersionDAO();

    public abstract UserDAO getUserDAO();
    public abstract UserPermissionDAO getUserPermissionDAO();
    public abstract SandBoxDAO getSandBoxDAO();
    public abstract SandBoxItemDAO getSandBoxItemDAO();
    public abstract SandBoxUserDAO getSandBoxUserDAO();
    public abstract ReportQueryDAO getReportQueryDAO();
    public abstract InstrumentInfoDAO getInstrumentInfoDAO();

     public static DialogixDAOFactory getDAOFactory(int factory_id){
        switch(factory_id){      
            case MYSQL:
                return new DialogixMysqlDAOFactory();         
            case ORACLE:
                return null;//new DialogixOracleDAOFactory();        
            default:
                return null;                     
        }  
    }

}
