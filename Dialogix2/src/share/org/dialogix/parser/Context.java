/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import org.apache.log4j.Logger;
import java.text.*;
import java.util.*;

/** Helper class with X main interfaces:
  (1) DataAccessObject pattern -- so can store data in database, hashtable, or other runtime-configurable DataSource
  (2) LocalAccessObject -- like DAO, letting Locale be specified in DB or Java properties file.  
    This should give access to internationalized formats even if the minimal JRE is intalled
*/
public class Context implements java.io.Serializable {
  static Logger logger = Logger.getLogger(Context.class);
  /* */
  /* FIXME:  Should the following locale-related functions be part of Context, DatumMath, Locale, or something else? */
  /* */
  private static final Hashtable dateFormats = new Hashtable();
  private static final Hashtable numFormats = new Hashtable();
  private static final Locale defaultLocale = Locale.getDefault();
  private static final String BUNDLE_NAME = "org.dialogix.DialogixBundle";
  private static final String DEFAULT = "null";

  private ResourceBundle bundle = null;
  private Locale locale = defaultLocale;
  
  private DataAccessObject dao;
  private LocaleAccessObject lao;
  
  /**
    This NULL context is the default
    XXX:  Can it be removed, making Datum calls require Context to be passed to them?
  */
  static public Context NULL = new Context();
  
  /**
    Create new Context, with default Access Objects for Data and Locale
  */
  public Context() {
    dao = new DataAccessObject();
    lao = new LocaleAccessObject();
    loadBundle();	// XXX: Shouldn't this be done elsewhere?
  }

  public DataAccessObject getDAO() { return dao; }
  public LocaleAccessObject getLocale() { return lao; }

  /**
    Get a formmated string according the to current Locale
    
    @param s  The message string
    @return The formmated result
  */
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
  
  /**
    Return the desired Locale
    
    @param  lang  the language specifier
    @param  country the country specifier
    @param  extra the dialect specifier
    @return the associated Locale object
  */
  public static Locale getLocale(String lang, String country, String extra) {
    return new Locale((lang == null) ? "" : lang,
      (country == null) ? "" : country,
      (extra == null) ? "" : extra);
  }
  
  /**
    Set the Locale for this context, loading new bundles as needed
    
    @param  loc the Locale
  */
  public void setLocale(Locale loc) {
    locale = (loc == null) ? defaultLocale : loc;
    loadBundle();
  }
  
  /**
    Load the Locale resources from the properties bundle.  
    XXX:  This should be done via LocaleAccessObject
  */
  private void loadBundle() {
    try {
      bundle = PropertyResourceBundle.getBundle(BUNDLE_NAME,(locale == null) ? defaultLocale : locale);
    }
    catch (MissingResourceException t) {
      logger.error("error loading resources '" + BUNDLE_NAME + "': " + t.getMessage());
    }
    catch (Error e) {
    	logger.error(e.getMessage(), e);
    }
  }
  
  /**
    Return the desired DateFormat, keeping a hashtable of them.
    XXX: Doesn't this mean that I'll have multiple copies of the data format in each context?
    
    @param mask The formatting mask
    @return the DataFormat object
  */
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
  
  /**
    Get DecimalFormat based upon mask, with simple caching
    XXX:  Doesn't this cache it in each Context, taking up extra space?
    
    @param mask the formatting mask
    @return the DecimalFormat
  */
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
        logger.error(e.getMessage(),e);
      }
      catch (NullPointerException e) {
        logger.error("error creating DecimalFormat for locale " + locale.toString() + " using mask " + mask);
      }
      if (df == null) {
        ;  // allow this - will use Double.format() internally
      }
      numFormats.put(key,df);
      return df;
    }
  }
  
  /**
    Parse an object as number based upon a formatting mask
    
    @param obj  the Datum, Number, or String
    @param  mask  the formatting mask
    @return the Number, or null if invalid
  */
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
          logger.error("Error parsing number " + obj + " with mask " + mask,e);
        }
      }
    }

    return num;
  }

  /**
    Parse an object as a Date using a given mask
    
    @param obj  the Datum, Date, or Number
    @param mask the formatting mask
    @return the Date, or null if invalid
  */
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
        logger.error("Error parsing date " + obj + " with mask " + mask);
      }
    }
    else {
      date = null;
    }
    return date;
  }
  
  /**
    Get Boolean value of an object
    
    @param obj
    @return its Boolean value
  */
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

  /**
    Convert a Double to a Number format
    
    @param  d the Double
    @return the Number equivalent
  */
  private Number assessDouble(Double d) {
    Double nd = new Double((double) d.longValue());
    if (nd.equals(d)) {
      return new Long(d.longValue());
    }
    else {
      return d;
    }
  }
  
  /**
    Format an object as a number according to a mask
    
    @param  obj The Object, Datum, Boolean, etc.
    @param  mask  the formatting mask
    @return the value as a String
  */
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
          catch(NumberFormatException t) {
            logger.error(t.getMessage(),t);
          }
        }
      }
      else {
        try {
          s = df.format(obj);
        }
        catch(IllegalArgumentException e) {
          logger.error(e.getMessage(), e);
        }
      }
    }
    catch (Exception e) {
      logger.error(e.getMessage(), e);  // FIXME: is it risky to catch an arbitrary exception here?
    }

    return s;
  }

  /**
    Format an object as a Date according to a mask and return the String equivalent
    
    @param  obj the Datum, Date, etc.
    @param  mask  the formatting mask
    @return the String representation
  */
  public String formatDate(Object obj, String mask) {
    if (obj == null) {
      return null;
    }

    DateFormat df = getDateFormat(mask);

    try {
      return df.format(obj);
    }
    catch (IllegalArgumentException e) {
      logger.error(e.getMessage(),e);
      return null;
    }
  }  
}
