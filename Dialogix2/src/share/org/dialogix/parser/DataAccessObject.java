/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.util.*;

public class DataAccessObject {
  private Hashtable data = new Hashtable();
  private static Functions functions = new Functions();
  
  public DataAccessObject() {
  }
  
  Datum function(Context context, String name, Vector params, int line, int column) {
    return functions.function(context,name,params,line,column);
  }

  Datum getDatum(String s) { 
    return (Datum) data.get(s);
  }
  
  void set(String name, Datum val) { 
    data.put(name,val);
  }
}
