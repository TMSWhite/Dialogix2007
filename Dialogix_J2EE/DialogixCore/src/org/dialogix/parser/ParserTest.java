/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.io.StringReader;

/**
  Unit testing program for parser.  Reads a tab separated input file with two columns:  First is equation, second is desired answer
  FIXME: Should generate a printout that shows the equations, their results, and whether it is accurate.
*/
public class ParserTest implements java.io.Serializable {
  private static final String TESTS[][] = {
    { "1 + 2",        "3" },
    { "7 * 5",        "35" },
    { "35 / 5",        "7" },
    { "5 * (2 + 3)",    "25" }
  };
  
  public static void main(String[] args) {
    Context context = new Context();
    Parser parser = new Parser();
    Datum datum;
    
    for (int i=0;i<TESTS.length;++i) {
      datum = parser.parse(context,TESTS[i][0]);
      System.out.println(TESTS[i][0] + "=>" + datum.stringVal(true) + "[" + TESTS[i][1] + "]");
    }    
  }
}
