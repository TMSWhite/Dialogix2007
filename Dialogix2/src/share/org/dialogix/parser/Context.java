/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import org.apache.log4j.Logger;
import java.text.*;
import java.util.*;

public class Context {
  static Logger logger = Logger.getLogger(Context.class);

  private DataAccessObject dao;
  private LocaleAccessObject lao;
  
  static public Context NULL = new Context();  /* FIXME: Is this needed?  Called from Datum */
  
  public Context() {
    dao = new DataAccessObject();
    lao = new LocaleAccessObject();
  }

  public DataAccessObject getDAO() { return dao; }
  public LocaleAccessObject getLocale() { return lao; }

  /* Replace this with call to locale? */  
  public String get(String s) {
    return s;
  }
  
  /* */
  /* FIXME:  These are navigation functions -- should be elsewhere */
  /* */
  void gotoFirst() { }
  void jumpToFirstUnasked() { }
  void gotoPrevious() { }
  void resetEvidence() { }
  void gotoNext() { }
  
  /* */
  /* FIXME:  Should the following functions be part of Context, DatumMath, Locale, or something else? */
  /* */
  private static final Hashtable dateFormats = new Hashtable();
  private static final Hashtable numFormats = new Hashtable();
  private static final Locale defaultLocale = Locale.getDefault();
  private ResourceBundle bundle = null;
  private static final String BUNDLE_NAME = "DialogixBundle";
  private Locale locale = defaultLocale;
  private static final String DEFAULT = "null";
  
  public static Locale getLocale(String lang, String country, String extra) {
    return new Locale((lang == null) ? "" : lang,
      (country == null) ? "" : country,
      (extra == null) ? "" : extra);
  }

  public void setLocale(Locale loc) {
    locale = (loc == null) ? defaultLocale : loc;
    loadBundle();
  }

  private void loadBundle() {
    try {
      bundle = ResourceBundle.getBundle(BUNDLE_NAME,locale);
    }
    catch (MissingResourceException t) {
      logger.error("error loading resources '" + BUNDLE_NAME + "': " + t.getMessage());
    }
  }
      
  private DateFormat getDateFormat(String mask) {
    String key = locale.toString() + "_" + ((mask == null) ? DEFAULT : mask);

    Object obj = dateFormats.get(key);
    if (obj != null) {
      return (DateFormat) obj;
    }

    DateFormat sdf = null;

    if (mask != null) {
      sdf = new SimpleDateFormat(mask,locale);
    }

    if (sdf == null) {
      Locale.setDefault(locale);
      sdf = new SimpleDateFormat();  // get the default for the locale
      Locale.setDefault(defaultLocale);
    }
    dateFormats.put(key,sdf);
    return sdf;
  }

  private DecimalFormat getDecimalFormat(String mask) {
    String key = locale.toString() + "_" + ((mask == null) ? DEFAULT : mask);

    Object obj = numFormats.get(key);
    DecimalFormat df = null;

    if (obj != null) {
      return (DecimalFormat) obj;
    }
    else {
      try {
        if (mask != null) {
          Locale.setDefault(locale);
          df = new DecimalFormat(mask);
          Locale.setDefault(defaultLocale);
        }
      }
      catch (SecurityException e ) {
        logger.error("##SecurityException @ Triceps.getDecimalFormat()" + e.getMessage());
        }
      catch (NullPointerException e) {
        logger.error("##error creating DecimalFormat for locale " + locale.toString() + " using mask " + mask);
      }
      if (df == null) {
        ;  // allow this - will use Double.format() internally
      }
      numFormats.put(key,df);
      return df;
    }
  }
  
  public Number parseNumber(Object obj, String mask) {
    Number num = null;

    if (obj == null || obj instanceof Date) {
      num = null;
    }
    else if (obj instanceof Number) {
      num = (Number) obj;
    }
    else {
      DecimalFormat df;
      String str = (String) obj;
      if (str.trim().length() == 0)
        return null;

      if (mask == null || ((df = getDecimalFormat(mask)) == null)) {
        Double d = null;

        try {
          d = Double.valueOf(str);
        }
        catch (NumberFormatException t) {}
        catch (NullPointerException e) {}
        if (d != null) {
          num = assessDouble(d);
        }
      }
      else {
        try {
          num = df.parse(str);
        }
        catch (java.text.ParseException e) {
          logger.error("##ParseException @ Triceps.parseNumber()" + e.getMessage());
        }
      }
    }

    return num;
  }

  public Date parseDate(Object obj, String mask) {
    Date date = null;
    if (obj == null) {
      date = null;
    }
    else if (obj instanceof Date) {
      date = (Date) obj;
    }
    else if (obj instanceof String) {
      String src = (String) obj;
      try {
        if (src.trim().length() > 0) {
          DateFormat df = getDateFormat(mask);
          date = df.parse(src);
        }
        else {
          date = null;
        }
      }
      catch (java.text.ParseException e) {
        logger.error("##Error parsing date " + obj + " with mask " + mask);
      }
    }
    else {
      date = null;
    }
    return date;
  }

  public boolean parseBoolean(Object obj) {
    if (obj == null) {
      return false;
    }
    else if (obj instanceof Number) {
      return (((Number) obj).doubleValue() != 0);
    }
    else if (obj instanceof String) {
      return Boolean.valueOf((String) obj).booleanValue();
    }
    else {
      return false;
    }
  }

  private Number assessDouble(Double d) {
    Double nd = new Double((double) d.longValue());
    if (nd.equals(d)) {
      return new Long(d.longValue());
    }
    else {
      return d;
    }
  }

  public String formatNumber(Object obj, String mask) {
    String s = null;

    if (obj == null) {
      return null;
    }

    DecimalFormat df;
    try {
      if (mask == null || ((df = getDecimalFormat(mask)) == null)) {
        if (obj instanceof Date) {
          s = "**DATE**";    // FIXME
        }
        else if (obj instanceof Long) {
          s = ((Long) obj).toString();
        }
        else if (obj instanceof Boolean) {
          s = ((Boolean) obj).toString();
        }
        else if (obj instanceof Double) {
          Number num = assessDouble((Double) obj);
          s = num.toString();
        }
        else if (obj instanceof Number) {
          s = ((Number) obj).toString();
        }
        else {
          try {
            Double d = Double.valueOf((String) obj);
            if (d == null) {
              s = null;
            }
            else {
              s = d.toString();
            }
          }
          catch(NumberFormatException t) { }
        }
      }
      else {
        try {
          s = df.format(obj);
        }
        catch(IllegalArgumentException e) {
          logger.error("IllegalArgumentException", e);
        }
      }
    }
    catch (Exception e) {
      logger.error("Exception", e);  // FIXME: is it risky to catch an arbitrary exception here?
    }

    return s;
  }

  public String formatDate(Object obj, String mask) {
    if (obj == null) {
      return null;
    }

    DateFormat df = getDateFormat(mask);

    try {
      return df.format(obj);
    }
    catch (IllegalArgumentException e) {
      logger.error("##IllegalArgumentException @ Triceps.formatDate()" + e.getMessage());
      return null;
    }
  }  
}
