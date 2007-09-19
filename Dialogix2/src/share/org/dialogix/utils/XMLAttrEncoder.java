/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.utils;

import java.text.DecimalFormat;
import java.lang.IllegalArgumentException;
import org.apache.log4j.Logger;

/** 
  Encoder to massage XML Attribute Strings to prevent premature termination of Attribute Nodes
**/
public class XMLAttrEncoder {
  static Logger logger = Logger.getLogger(XMLAttrEncoder.class);
  private static DecimalFormat ATT_ENTITY_FORMAT = null;
  
  static {
    try {
      ATT_ENTITY_FORMAT = new DecimalFormat("'&#'000';'");
    }
    catch (IllegalArgumentException e) { }
  }

  /** 
    Encode XML Attribute values.  Replace any character that might prematurely terminate an XML attribute with an XML entity
    
    @param s  The string to be encoded
    @return The XML-safe value
  **/
  public static String encode(String s) {
    StringBuffer sb = new StringBuffer();
    
    for (int i=0;i<s.length();++i) {
    	int cp = s.codePointAt(i);
    	char c = s.charAt(i);
      if (Character.isISOControl(cp) || c == '\'' || c == '<' || c == '>' || c == '"' || c == '&') {
        sb.append(attEntityFormat(c));
      }
      else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
/*
  public static String encode(String s) {
    StringBuffer sb = new StringBuffer();
    char[] chars = s.toCharArray();
    char c;
    
    for (int i=0;i<chars.length;++i) {
      c = chars[i];
      if (Character.isISOControl(c) || c == '\'' || c == '<' || c == '>' || c == '"' || c == '&') {
        sb.append(attEntityFormat(c));
      }
      else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
*/
  
  /** 
    Format XML Entities using &#000; syntax.  
    
    @param  c The next character in the sequence of a presumed XML Attribute
    @return the String value for that attribute
  */
  private static String attEntityFormat(char c) {
    try {
      return ATT_ENTITY_FORMAT.format((long) (c & 0x00ff));  // must strip high byte for HTML
    }
    catch (Exception t) {
      logger.error("attEntityFormat" + t.getMessage(), t);
      return "";
    }
  }  
}
