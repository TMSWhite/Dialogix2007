/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.util.*;

/**
  Object for implementing DataAccessObject pattern, loosely coupling the data source from the Context and parser.
  
  XXX/TODO: Should be an interface, with XML-based specification of the DataSource so can point to database,
  In which case this function is the simplest, Hashtable-based way of storing the list of variables and values.
*/
public class DataAccessObject implements java.io.Serializable  {
  private Hashtable data = new Hashtable();
  private static Functions functions = new Functions();
  
  public DataAccessObject() {
  }
  
  /**
    Find and call a function within the current context and return the result
    
    @param  context The Context
    @param  name  The name of the function
    @param  params  The list of parameters
    @param  line  The line number where this function starts
    @param  column  The column number where this functions starts
    @return The Datum storing the value
  */
  Datum function(Context context, String name, Vector params, int line, int column) {
    return functions.function(context,name,params,line,column);
  }

  /**
    Returns a variable by name, looking it from wherever it is stored.
    
    @param  s The name of the variable
    @return The Datum value for that variable
  */
  Datum getDatum(String s) { 
    return (Datum) data.get(s);
  }
  
  /**
    Sets a variable's value, storing it whereever appropriate
    
    @param  name  The name of the variable
    @param  val The Datum holdings its value
  */
  void set(String name, Datum val) { 
    data.put(name,val);
  }
}
