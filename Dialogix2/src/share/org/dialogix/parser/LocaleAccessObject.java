/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

/**
  DataAccessObject-like abstraction for accessing Locale information.
  FIXME:  Goal is to let Locale be specified in Java properties files OR database
*/

public class LocaleAccessObject {
  public LocaleAccessObject() {
  }
  
  String get(String message) {
    return message;
  }
}
