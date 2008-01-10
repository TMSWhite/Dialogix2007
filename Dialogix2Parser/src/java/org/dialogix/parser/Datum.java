/* ******************************************************** 
** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.util.Hashtable;
import java.util.Date;
import java.util.logging.*;

/**
  This class implements loosely typed data values (Datum), which have the added 
  capability of storing multiple types of MISSING values.
  
  TODO/XXX:  Currently keeps a local reference to the Context, which might hamper garbage collection. 
    Can this reference to Context be removed; changing all Datum requests to also pass the Context?
*/
public final class Datum implements java.io.Serializable   {
  static Logger logger = Logger.getLogger("org.dialogix.parser.Datum");
  
  /*
    List of known data types
  */
  private static final int FIRST_DATUM_TYPE = 0;
  public static final int UNASKED = 0;    // haven't asked
  public static final int NA = 1;        // don't need to ask - not applicable
  public static final int REFUSED = 2;    // question asked, but subject refuses to answer
  public static final int INVALID = 3;    // if an exception occurs - so propagated
  public static final int UNKNOWN = 4;    // subject indicates that they don't know the answer
  public static final int NOT_UNDERSTOOD = 5;
  public static final int NUMBER = 6;
  public static final int STRING = 7;
  public static final int DATE = 8;
  public static final int TIME = 9;
  public static final int YEAR = 10;
  public static final int MONTH = 11;
  public static final int DAY = 12;
  public static final int WEEKDAY = 13;
  public static final int HOUR = 14;
  public static final int MINUTE = 15;
  public static final int SECOND = 16;
  public static final int MONTH_NUM = 17;
  public static final int DAY_NUM = 18;
  private static final int LAST_DATUM_TYPE = 18;

  private static final Date SAMPLE_DATE = new Date(System.currentTimeMillis());
  private static final Double SAMPLE_NUMBER = new Double(12345.678);

  private static final String SPECIAL_TYPES[] = { "*UNASKED*", "*NA*", "*REFUSED*", "*INVALID*", "*UNKNOWN*", "*HUH*" };
  private static final String DATUM_TYPES[] = { "number", "string", "date", "time", "year", "month", "day", "weekday", "hour", "minute", "second", "month_num", "day_num" };

  private static final String defaultDateFormat = "MM/dd/yyyy";
  private static final String defaultMonthFormat = "MMMM";
  private static final String defaultTimeFormat = "HH:mm:ss";
  private static final String defaultYearFormat = "yyyy";
  private static final String defaultDayFormat = "d";
  private static final String defaultWeekdayFormat = "E";
  private static final String defaultHourFormat = "H";
  private static final String defaultMinuteFormat = "m";
  private static final String defaultSecondFormat = "s";
  private static final String defaultNumberFormat = null;  // so that context pretty-prints it.

  public static final String defaultMonthNumFormat = "M";
  public static final String defaultDayNumFormat = "D";
  public static final String TIME_MASK = "yyyy.MM.dd..HH.mm.ss";

  private int type = INVALID;
  private String sVal = null;
  private double dVal = Double.NaN;
  private Date date = null;
  private String mask = null;
  private String error = null;
  private String variableName = null;
  /* XXX: Do I really need a handle to Context?  Wouldn't it make it hard for the garbage collection to work?
    Would need to modify all getter methods to pass in the value of Context -- what would that take?
  */
  public Context context = Context.NULL;  
  private static final Hashtable SPECIAL_DATA = new Hashtable();

  /**
    Create a Datum from a double
    
    @param  context The context
    @param  d The value
  */
  public Datum(Context context, double d) { init(context, new Double(d), NUMBER, null); }
  
  /**
    Create a Datum from a long
    
    @param  context The context
    @param  l The value
  */
  public Datum(Context context, long l) { init(context, new Long(l), NUMBER, null); }
  
  /**
    Create a Datum from an integer
    
    @param  context The context
    @param  i The value
  */  
  public Datum(Context context, int i) { init(context, new Integer(i), NUMBER, null); }
  
  /**
    For each of the special missing value types, use only a single instance of that type
    
    @param  context The context
    @param  i The type of missing value
  */
  public static synchronized Datum getInstance(Context context, int i) {
    if (i == INVALID) {
      logger.log(Level.FINE,"INVALID Datum");  //  modify to show the datum's internals?
    }
    String key = (context.toString() + i);
    Datum datum = (Datum) SPECIAL_DATA.get(key);
    if (datum != null)
      return datum;

    datum = new Datum(i,context);
    SPECIAL_DATA.put(key,datum);
    return datum;
  }

  /**
    Create a reserved word
    
    @param  i The type of reserved word
    @param  context The context
  */
  private Datum(int i, Context context) {
    // only for creating reserved instances
    this.context = context;
    type = i;
  }


  /**
    Create a Datum from a variable name, also casting it to numeric and date if appropriate
    
    @param  val The Datum's value
    @param  name  The variable's name
  */
  public Datum(Datum val, String name) {
    dVal = val.dVal;
    sVal = val.sVal;
    date = val.date;
    type = val.type;
    mask = val.mask;
    context = val.context;
    error = val.error;
    if (name != null) {
      variableName = name;
    }
    else {
      variableName = val.variableName;
    }
  }

  /**
    Create a copy of a Datum
    
    @param  val The Datum to be copied
  */
  public Datum(Datum val) {
    this(val,null);
  }

  /**
    Create a Datum from a Date according to one of the internal formatting masks (MONTH, YEAR, etc.)
  
    @param  context The context
    @param  d The date
    @param  t The style of formatting
  */
  public Datum(Context context, Date d, int t) {
    this(context,d,t,Datum.getDefaultMask(t));
  }

  /**
    Create a Datum from a Date according to a user-defined formatting mask
  
    @param  context The context
    @param  d The date
    @param  t The style of formatting for the result (MONTH, YEAR, etc.)
    @param  mask  The input formatting mask
  */
  public Datum(Context context, Date d, int t, String mask) {
    init(context,d,t,mask);
  }

  /**
    Create a Datum from a String according to one of the internal formatting masks (MONTH, YEAR, etc.)
  
    @param  context The context
    @param  s The String
    @param  t The style of formatting
  */  
  public Datum(Context context, String s, int t) {
    init(context,s,t,Datum.getDefaultMask(t));
  }

  /**
    Create a Datum from a String according to a user-defined formatting mask (such as for Numbers)
  
    @param  context The context
    @param  s The String
    @param  t The style of formatting for the result
    @param  mask  The input formatting mask
  */  
  public Datum(Context context, String s, int t, String mask) {
    init(context,s,t,mask);
  }

  /**
    Cast a Datum to a new type or mask, returning the new value
    
    @param  newType The optional target type
    @param  newMask The optional new mask
    @return The new Datum
  */
  public Datum cast(int newType, String newMask) {
    /* Cast a value from one type to another */

    if (this.type == newType && this.mask == newMask) {
      return this;
    }

    Datum datum = null;
    String useMask = ((newMask == null || newMask.trim().length() == 0) ? getDefaultMask(newType) : newMask);


    if (this.type == newType) {
      datum = new Datum(this);
      datum.mask = useMask;
      return datum;
    }

    switch(this.type) {
      case TIME:
      case MONTH:
      case DATE:
      case YEAR:
      case DAY:
      case WEEKDAY:
      case HOUR:
      case MINUTE:
      case SECOND:
      case MONTH_NUM:
      case DAY_NUM:
        if (isDate(newType)) {
          datum = new Datum(this);
          datum.type = newType;
          datum.mask = useMask;
        }
        else if (newType == NUMBER) {
          datum = new Datum(context,this.doubleVal());
        }
        else if (newType == STRING) {
          return datum;  // don't cast to STRING
//          datum = new Datum(context,this.stringVal(),STRING);
        }
        else {
          datum = Datum.getInstance(context,Datum.INVALID);
        }
        break;
      case NUMBER:
        if (isDate(newType)) {
          if (newType == TIME || newType == DATE) {
            datum = Datum.getInstance(context,Datum.INVALID);
          }
          else {
            datum = new Datum(this);
            datum.date = DatumMath.createDate((int) this.doubleVal(), newType);
            datum.sVal = null;
            datum.dVal = Double.NaN;
            datum.type = newType;
            datum.mask = useMask;
          }
        }
        else if (newType == STRING) {
          return datum;
//          datum = new Datum(context,this.stringVal(),STRING);
        }
        else {
          datum = Datum.getInstance(context,Datum.INVALID);
        }
        break;
      case STRING:
        /* try to parse the string using a new format */
        datum = new Datum(context,this.stringVal(),newType,useMask);
        break;
      default:
      case INVALID:
      case NA:
      case UNKNOWN:
      case REFUSED:
      case UNASKED:
      case NOT_UNDERSTOOD:
        /* can't cast any of these to a new type */
        datum = new Datum(context,this.type);
    }
    return datum;
  }

  /**
    Create a new Datum, identifying and storing its loosely typed representations.
    This is a support function for all of the new Datum() operators
    
    @param  context The context
    @param  obj The object to be parsed into a Datum (Date, String, Datum)
    @param  t The DatumType
    @param  maskStr The optional input formatting mask
  */
  private void init(Context context, Object obj, int t, String maskStr) {
    context = (context == null) ? context.NULL : context;

    if (obj == null && !isSpecial(t)) {
      logger.log(Level.SEVERE,"null obj");
      t = INVALID;
    }

    dVal = Double.NaN;
    date = null;
    sVal = null;
    type = t;  // assume success - enumerate failure conditions

    if (maskStr == null || maskStr.trim().length() == 0) {
      mask = getDefaultMask(t);
    }
    else {
      mask = maskStr;
    }
    Number num = null;

    switch (t) {
      case NUMBER:
        num = context.parseNumber(obj,mask);

        if (num == null) {
          type = INVALID;
        }
        else {
          dVal = num.doubleValue();
        }
        break;
      case STRING:
        sVal = obj.toString();
        /* also check whether can be considered a number */
        num = context.parseNumber(obj,null);
        if (num != null) {
          dVal = num.doubleValue();
        }
        break;
      case WEEKDAY:
      case MONTH:
      case DATE:
      case TIME:
      case YEAR:
      case DAY:
      case HOUR:
      case MINUTE:
      case SECOND:
      case MONTH_NUM:
      case DAY_NUM:
        date = context.parseDate(obj,mask);
        if (date == null) {
          type = INVALID;
        }
        else {
          num = context.parseNumber(context.formatDate(date,Datum.getDefaultMask(t)),null);
          if (num != null) {
            dVal = num.doubleValue();
          }
        }
        break;
      case REFUSED:
      case INVALID:
      case NA:
      case UNASKED:
      case NOT_UNDERSTOOD:
      case UNKNOWN:
        type = t;
        break;
    }
    if (type == INVALID) {
      if (t == INVALID) {
        error = context.get("Please_answer_this_question");
      }
      else {
        String ex = getExampleFormatStr(mask,t);
        if (ex.length() > 0)
          ex = " (e.g. " + ex + ")";
        error = context.get("please_enter_a") + getTypeName(context,t) + ex;
      }
      sVal = null;
      dVal = Double.NaN;
      date = null;
    }
  }

  /**
    Create a Datum from a boolen value
    
    @param  context The context
    @param  b The boolean value
  */
  public Datum(Context context, boolean b) {
     context = (context == null) ? context.NULL : context;

    type = NUMBER;
    dVal = (b ? 1 : 0);
  }
  
  /**
    Return the Datum's value as a String
    
    @return The String view
  */
  public String stringVal() { return stringVal(false,mask); }
  
  /**
    Return the Datum's value as a String, optionally showing Reserved words
    
    @param  showReserved  Boolean of whether to show Reserved words
    @return The String view of the Datum
  */
  public String stringVal(boolean showReserved) { return stringVal(showReserved,mask); }

  /**
    Return the Datum's value as a Sting, optionally using a formatting mask
    
    @param  showReserved  Boolean of whether to show Reserved words
    @param  mask  The optional formatting mask
    @return The String view of the Datum
  */
  public String stringVal(boolean showReserved, String mask) {
    switch(type) {
      case TIME:
      case MONTH:
      case DATE:
      case YEAR:
      case DAY:
      case WEEKDAY:
      case HOUR:
      case MINUTE:
      case SECOND:
      case MONTH_NUM:
      case DAY_NUM:
        if (mask == null)
          return format(context, this,type,Datum.getDefaultMask(type));
        else
          return format(context, this, type, mask);
      case NUMBER:
        if (mask == null)
          return format(context, this, type, Datum.getDefaultMask(type));
        else
          return format(context, this, type, mask);
      case STRING:
        return sVal;
      default:
        if (logger.isLoggable(Level.FINE)) logger.log(Level.FINE,"stringVal(" + showReserved + "," + mask + ") -> invalid type " + type);
        return getTypeName(context,INVALID);
      case INVALID:
      case NA:
      case UNKNOWN:
      case REFUSED:
      case UNASKED:
      case NOT_UNDERSTOOD:
        if (showReserved)
          return getTypeName(context,type);
        else
          return "";
    }
  }

  /**
    @return the boolean value of the Datum
  */
  public boolean booleanVal() {
    if (isNumeric()) {
      return (Double.isNaN(dVal) || (dVal == 0)) ? false : true;
    }
    else if (sVal != null) {
      return Boolean.valueOf(sVal).booleanValue();
    }
    else {
      return false;
    }
  }

  /**
    @return the double view of the Datum
  */
  public double doubleVal() { return dVal; }
  
  /**
    @return the Date view of the Datum
  */
  public Date dateVal() { return date; }
  
  /**
    @return the Month name of the Datum
  */
  public String monthVal() { if (date == null) return ""; return format(date,Datum.MONTH); }
  
  /**
    @return the formatted Time value of the Datum
  */
  public String timeVal() { if (date == null) return ""; return format(date,Datum.TIME); }
  
  /**
    @return the DataType
  */
  public int type() { return type; }
  
  /**
    @return the Datum's formatting mask
  */
  public String getMask() { return mask; }

//  public void setName(String name) { variableName = name; }
  
  /**
    @return the Datum's associated variable name, if any
  */
  public String getName() { return variableName; }

  /**
    @return false if the DataType is INVALID
  */
  public boolean isValid() {
    return (isType(type) && type != INVALID);
  }

  /**
    @return true if the Datum is VALID and non-null
  */
  public boolean exists() {
    /* not only must it be valid, but STRING vals must be non-null */
    return (type != UNASKED && isValid() && ((type == STRING) ? !sVal.equals("") : true));
  }

  /**
    @return true if the Datum is one of the Special Missing values
  */
  public boolean isSpecial() { return (type >= UNASKED && type <= NOT_UNDERSTOOD); }
  
  /**
    @return true if the DataType is one of the Special Missing values
  */
  static public boolean isSpecial(int t) { return (t >= UNASKED && t <= NOT_UNDERSTOOD); }
  
  /**
    @return true if the Datum can be used as a valid number
  */
  public boolean isNumeric() { return (!Double.isNaN(dVal)); }
  
  /**
    @return true if the Datum can be viewed as a Date
  */
  public boolean isDate() { return (date != null); }
  
  /**
    @return true if the DataType is one of the Date types
  */
  static public boolean isDate(int t) { return (t >= DATE && t <= DAY_NUM); }
  
  /**
    @return true if the Datum is of type REFUSED
  */
  public boolean isRefused() { return (type == REFUSED); }
  
  /**
    @return true if the Datum is of type UNKNOWN
  */
  public boolean isUnknown() { return (type == UNKNOWN); }
  
  /**
    @return true if the Datum is of type NOT_UNDERSTOOD
  */
  public boolean isNotUnderstood() { return (type == NOT_UNDERSTOOD); }
  
  /**
    @return true if the Datum is of type UNASKED
  */
  public boolean isUnasked() { return (type == UNASKED); }

  /**
    @param  t Is the Datum of this DataType?
    @return true if the Datum is of the specified type
  */
  public boolean isType(int t) {
    switch(t) {
      case TIME:
      case MONTH:
      case DATE:
      case YEAR:
      case DAY:
      case WEEKDAY:
      case HOUR:
      case MINUTE:
      case SECOND:
      case MONTH_NUM:
      case DAY_NUM:
        return (date != null);
      case NUMBER:
        return (type == NUMBER);
      case STRING:
        return (type == STRING);
      case INVALID:
        return (type == INVALID);
      case NA:
        return (type == NA);
      case UNKNOWN:
        return (type == UNKNOWN);
      case REFUSED:
        return (type == REFUSED);
      case UNASKED:
        return (type == UNASKED);
      case NOT_UNDERSTOOD:
        return (type == NOT_UNDERSTOOD);
      default:
        return false;
    }
  }

  /**
    @param  t The requested DataType
    @return true if the DataType is valid
  */
  static public boolean isValidType(int t) {
    switch(t) {
      case UNASKED:
      case NA:
      case REFUSED:
      case INVALID:
      case UNKNOWN:
      case NOT_UNDERSTOOD:
      case NUMBER:
      case STRING:
      case DATE:
      case TIME:
      case YEAR:
      case MONTH:
      case DAY:
      case WEEKDAY:
      case HOUR:
      case MINUTE:
      case SECOND:
      case MONTH_NUM:
      case DAY_NUM:
        return true;
      default:
        return false;
    }
  }

  /**
    Returns an internal Datum error, and clears the error
    XXX:  Is this used?  Should it be?
    
    @return The error
  */
  public String getError() {
    if (error == null)
      return "";

    String temp = error;
    error = null;
    return temp;
  }

  /**
    Returns a human-readable format string based upon the requested input mask and data type, and the current context
    
    @param  mask  The input format mask
    @param  t The target DataType
    @return The human-readable String, using today's date
  */
  public String getExampleFormatStr(String mask, int t) {
    return getExampleFormatStr(context, mask, t);
  }

  /**
    Returns a human-readable format string based upon the requested input mask, data type, and context
    
    @param  context The desired context
    @param  mask  The input format mask
    @param  t The target DataType
    @return The human-readable String, using today's date
  */
  static public String getExampleFormatStr(Context context, String mask, int t) {
    switch (t) {
      case MONTH:
      case DATE:
      case TIME:
      case YEAR:
      case DAY:
      case WEEKDAY:
      case HOUR:
      case MINUTE:
      case SECOND:
      case MONTH_NUM:
      case DAY_NUM:
        if (mask == null)
          return format(context, SAMPLE_DATE,t,Datum.getDefaultMask(t));
        else
          return format(context, SAMPLE_DATE, t, mask);
      case NUMBER:
        if (mask == defaultNumberFormat || mask == null)
          return "";
        else
          return format(context, SAMPLE_NUMBER, t, mask);
      default:
      case INVALID:
      case NA:
      case UNKNOWN:
      case NOT_UNDERSTOOD:
      case UNASKED:
      case STRING:
      case REFUSED:
        return "";  // no formatting string to contrain input
    }
  }

  /**
    Returns the default format mask for a specified DataType
    
    @param  t The DataType
    @return The default format mask
  */
  static public String getDefaultMask(int t) {
    switch (t) {
      case MONTH:
        return defaultMonthFormat;
      case DATE:
        return defaultDateFormat;
      case TIME:
        return defaultTimeFormat;
      case NUMBER:
        return defaultNumberFormat;
      case YEAR:
        return defaultYearFormat;
      case DAY:
        return defaultDayFormat;
      case WEEKDAY:
        return defaultWeekdayFormat;
      case HOUR:
        return defaultHourFormat;
      case MINUTE:
        return defaultMinuteFormat;
      case SECOND:
        return defaultSecondFormat;
      case MONTH_NUM:
        return defaultMonthNumFormat;
      case DAY_NUM:
        return defaultDayNumFormat;
      default:
      case INVALID:
      case NA:
      case UNKNOWN:
      case UNASKED:
      case NOT_UNDERSTOOD:
      case STRING:
      case REFUSED:
        break;
    }
    return null;
  }

  /**
    Returns a human-readable view of the Datum given the desired formatting mask
    
    @param  d The Datum
    @param  mask  The formatting mask
    @return The human-readable view
  */
  public String format(Datum d, String mask) {
    return format(context, d, d.type(), mask);
  }

  /**
    Returns a human-readable view of the Datum given the desired formatting mask
    
    @param  context The desired context
    @param  o The Datum or String to format
    @param  type  the DataType
    @param  mask  The formatting mask
    @return The human-readable view of the Datum
  */
  static public String format(Context context, Object o, int type, String mask) {
    String s;

    switch (type) {
      case MONTH:
      case DATE:
      case TIME:
      case YEAR:
      case DAY:
      case WEEKDAY:
      case HOUR:
      case MINUTE:
      case SECOND:
      case MONTH_NUM:
      case DAY_NUM:
        if (o instanceof Datum) {
          s = context.formatDate(((Datum) o).dateVal(),mask);
        }
        else {
          s = context.formatDate(o,mask);
        }
        if (s != null)
          return s;
        break;
      case NUMBER:
        if (o instanceof Datum) {
          Double doub = new Double(((Datum) o).doubleVal());
          s = context.formatNumber(doub,mask);
        }
        else {
          s = context.formatNumber(o,mask);
        }
        if (s != null)
          return s;
        break;
      default:
        return Datum.getTypeName(context,INVALID);
      case INVALID:
      case NA:
      case REFUSED:
      case UNKNOWN:
      case NOT_UNDERSTOOD:
        return Datum.getTypeName(context,type);
      case UNASKED:
        return "";  // empty string to indicate that has not been assessed yet.
      case STRING:
        if (o instanceof Datum) {
          return ((Datum) o).stringVal();
        }
        else {
          return o.toString();
        }
    }
    return Datum.getTypeName(context,INVALID);
  }
  
  /**
    Returns a human-readable view of the Datum given the default formatting type for that DataType
    
    @param  o The Datum or String to format
    @param  t  the DataType
    @return The human-readable view of the Datum
  */  
  public String format(Object o, int t) {
    return format(context, o,t,Datum.getDefaultMask(t));
  }
  
  /**
    @return the human-readable name of the current DataType
  */
  public String getTypeName() { return getTypeName(context,type); }
  
  /**
    Returns the human-readable name of the DataType for one of the special MISSING values
    @param  t the DataType
    @return its name
  */
  static public String getSpecialName(int t) {
    switch (t) {
      // must have static strings for reserved words so that correctly parsed from data files
      case UNASKED:
      case NA:
      case REFUSED:
      case INVALID:
      case UNKNOWN:
      case NOT_UNDERSTOOD:
        return SPECIAL_TYPES[t];
      default:
        return SPECIAL_TYPES[INVALID];
    }
  }

  /**
    Returns the human-readable name of a DataType, using the current locale
    
    @param  context The context
    @param  t The DataType
    @return the human-readable value
  */
  static public String getTypeName(Context context, int t) {
    switch (t) {
      // must have static strings for reserved words so that correctly parsed from data files
      case UNASKED:
      case NA:
      case REFUSED:
      case INVALID:
      case UNKNOWN:
      case NOT_UNDERSTOOD:
        return SPECIAL_TYPES[t];
      default:
        return SPECIAL_TYPES[INVALID];

      // these can and should be localized
      case NUMBER: return context.get("NUMBER");
      case STRING: return context.get("STRING");
      case DATE: return context.get("DATE");
      case TIME: return context.get("TIME");
      case YEAR: return context.get("YEAR");
      case MONTH: return context.get("MONTH");
      case DAY: return context.get("DAY");
      case WEEKDAY: return context.get("WEEKDAY");
      case HOUR: return context.get("HOUR");
      case MINUTE: return context.get("MINUTE");
      case SECOND: return context.get("SECOND");
      case MONTH_NUM: return context.get("MONTH_NUM");
      case DAY_NUM: return context.get("DAY_NUM");
    }
  }

  /**
    Parse a string to determine whether it is one of the speciall missing values, and if so return the associated special Datum
    
    @param context  the Context
    @param  s the String to parse
    @return null if not special, else the special Datum reference
  */
  static public Datum parseSpecialType(Context context, String s) {
    if (s == null || s.trim().length() == 0)
      return null;  // not a special datatype

    for (int i=0;i<SPECIAL_TYPES.length;++i) {
      if (SPECIAL_TYPES[i].equals(s))
        return getInstance(context,i);
    }
    return null;  // not a special datumType
  }

  /**
    Parse a string to determine whether it is the name of a DataType
    
    @param  s the string to parse
    @return -1 if it is not the name of a DataType, else the # of the DataType
  */
  static public int parseDatumType(String s) {
    if (s == null)
      return -1;

    for (int i=0;i<DATUM_TYPES.length;++i) {
      if (DATUM_TYPES[i].equals(s))
        return (i + SPECIAL_TYPES.length);
    }
    return -1;
  }
}
