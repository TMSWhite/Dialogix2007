/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import org.apache.log4j.Logger;

/**
  These static helper functions perform all math operations between Datum values, properly handling MISSING values
*/
public final class DatumMath {
  static Logger logger = Logger.getLogger(DatumMath.class);
  
  /**
    Internal helper function -- if either argument is INVALID, propagate the INVALID value
    XXX:  Should propagate REFUSED and/or NA values?
    
    @param  a the 1st Datum
    @param  b the 2nd Datum
    @return INVALID if either is INVALID, else null to indicate that neither is an error
  */
  static Datum hasError(Datum a, Datum b) {
    // This function needs to be reconsidered as to the proper way to handle error propagation
    if (a.isType(Datum.INVALID) || (b != null && b.isType(Datum.INVALID))) {
      return Datum.getInstance(a.context,Datum.INVALID);
    }
    /*
    if (a.isType(Datum.REFUSED) || (b != null && b.isType(Datum.REFUSED))) {
      return Datum.REFUSED_DATUM;
    }
    // Do NOT throw an error message if try to access a NA datatype?
    if (a.isType(Datum.NA) || (b != null && b.isType(Datum.NA))) {
      return Datum.NA_DATUM;
    }
    */
    return null;  // to indicate that there is no error that needs propagating
  }

  /**
    Convert from DataType to CalendarType
    
    @param  datumType the DataType
    @return the associated Calendar type
  */
  static int datumToCalendar(int datumType) {
    switch (datumType) {
      case Datum.YEAR: return Calendar.YEAR;
      case Datum.MONTH: return Calendar.MONTH;
      case Datum.DAY: return Calendar.DAY_OF_MONTH;
      case Datum.WEEKDAY: return Calendar.DAY_OF_WEEK;
      case Datum.HOUR: return Calendar.HOUR_OF_DAY;
      case Datum.MINUTE: return Calendar.MINUTE;
      case Datum.SECOND: return Calendar.SECOND;
      case Datum.MONTH_NUM: return Calendar.MONTH;
      case Datum.DAY_NUM: return Calendar.DAY_OF_YEAR;
      default: return 0;  // should never get here
    }
  }

  /**
    Create a Date value from a numeric type
    
    @param  val the Integer
    @param  datumType the DataType
    @return a sample Date value
  */
  static Date createDate(int val, int datumType) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(new Date(System.currentTimeMillis()));
    calendar.set(datumToCalendar(datumType),val);
    return calendar.getTime();
  }

  /**
    Return the desired calendar field from a Date given a DataType to make it easier to compare them
    For example, return the numeric month or year field.
    
    @param d  the Datum
    @param datumType  the DataType
    @return the integer value for that field
  */
  static int getCalendarField(Datum d, int datumType) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(d.dateVal());
    return calendar.get(DatumMath.datumToCalendar(datumType));
  }

  /**
    Add two values
    
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return their sum
  */
  static Datum add(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    switch (a.type()) {
      default:
      case Datum.NUMBER:
      case Datum.STRING:
        return new Datum(a.context, a.doubleVal() + b.doubleVal());
       case Datum.DATE:
       case Datum.TIME:
         /* XXX need way to throw error here? */
        return Datum.getInstance(a.context,Datum.INVALID);
      case Datum.WEEKDAY:
      case Datum.MONTH:
      case Datum.YEAR:
      case Datum.DAY:
      case Datum.HOUR:
      case Datum.MINUTE:
      case Datum.SECOND:
      case Datum.MONTH_NUM:
      case Datum.DAY_NUM:
        if (!b.isNumeric()) {
          /* XXX need way to throw an error here? */
          return Datum.getInstance(a.context,Datum.INVALID);
        }
        else {
          int field = DatumMath.datumToCalendar(a.type());
          GregorianCalendar gc = new GregorianCalendar();  // XXX? should happen infrequently (not a garbage collection problem?)

          gc.setTime(a.dateVal());
          gc.add(field, (int) b.doubleVal());
          return new Datum(a.context,gc.getTime(),a.type());  // set to type of first number in expression
        }
    }
  }

  /**
    Do logical AND of two values (a & b)
    
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return (a & b)
  */
  static Datum and(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, (double) ((long) a.doubleVal() & (long) b.doubleVal()));
  }
  
  /**
    Do conditional AND of two values (a && b)
    NOTE (XXX):  This has a side-effect -- both a and b sides will always be processed
    
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return (a && b)
  */
  static Datum andand(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, a.booleanVal() && b.booleanVal());
  }
  
  /** 
    Concatenates two Datum objects of type String and returns the resulting Datum object
    
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return (a . b)
  */
  static Datum concat(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      return new Datum(a.context, a.stringVal().concat(b.stringVal()),Datum.STRING);
    }
    catch(NullPointerException e) {
      logger.error(e.getMessage(),e);
      return new Datum(a.context, a.stringVal(),Datum.STRING);
    }
  }
  
  /**
    This method evaluates the boolean value of the first Datum object, returns the second Datum object if true,
    returns the third Datum object if false.
    
    @param a the expression
    @param b the value to return if true
    @param c the value to return if false
    @return  (a) ? b : c
  */
  static Datum conditional(Datum a, Datum b, Datum c) {
    Datum d = DatumMath.hasError(a,null);  // if conditional based upon a REFUSED or INVALID, always return that type
    if (d != null)
      return d;

    if (a.booleanVal())
      return b;
    else
      return c;
  }
  
  /**
    @return (a / b)
  */
  static Datum divide(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      return new Datum(a.context, a.doubleVal() / b.doubleVal());
    }
    catch(ArithmeticException e) {
      logger.error(e.getMessage(),e);
      return Datum.getInstance(a.context,Datum.INVALID);
    }
  }
  
  /**
    @return (a == b)
  */
  static Datum eq(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      switch (a.type()) {
        case Datum.DATE:
        case Datum.TIME:
          return new Datum(a.context, (a.dateVal().equals(b.dateVal())));
        case Datum.WEEKDAY:
        case Datum.MONTH:
        case Datum.YEAR:
        case Datum.DAY:
        case Datum.HOUR:
        case Datum.MINUTE:
        case Datum.SECOND:
        case Datum.MONTH_NUM:
        case Datum.DAY_NUM:
        {
          boolean ans = false;
          if (b.isDate()) {
            ans = (DatumMath.getCalendarField(a,a.type()) == DatumMath.getCalendarField(b,a.type()));
          }
          else if (b.isNumeric()) {
            ans = (a.doubleVal()== b.doubleVal());
          }
          if (logger.isDebugEnabled()) logger.debug("eq(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
          return new Datum(a.context, ans);
        }
        case Datum.STRING:
          if (a.isNumeric())
            return new Datum(a.context, a.doubleVal() == b.doubleVal());
          else {
            return new Datum(a.context, a.stringVal().compareTo(b.stringVal()) == 0);
          }
        case Datum.NUMBER:
          return new Datum(a.context, a.doubleVal() == b.doubleVal());
        default:
          return new Datum(a.context, false);
      }
    }
    catch(NullPointerException e) {
      logger.error(e.getMessage(),e);
    }
    return new Datum(a.context, false);
  }
  
  /**
    @return (a >= b)
  */
  static Datum ge(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      switch (a.type()) {
        case Datum.DATE:
        case Datum.TIME:
          return new Datum(a.context,
            (a.dateVal().after(b.dateVal())) ||
            (a.dateVal().equals(b.dateVal()))
            );
        case Datum.WEEKDAY:
        case Datum.MONTH:
        case Datum.YEAR:
        case Datum.DAY:
        case Datum.HOUR:
        case Datum.MINUTE:
        case Datum.SECOND:
        case Datum.MONTH_NUM:
        case Datum.DAY_NUM:
        {
          boolean ans = false;
          if (b.isDate()) {
            ans = (DatumMath.getCalendarField(a,a.type()) >= DatumMath.getCalendarField(b,a.type()));
          }
          else if (b.isNumeric()) {
            ans = (a.doubleVal()>= b.doubleVal());
          }
          if (logger.isDebugEnabled()) logger.debug("ge(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
          return new Datum(a.context, ans);
        }
        case Datum.STRING:
          if (a.isNumeric())
            return new Datum(a.context, a.doubleVal() >= b.doubleVal());
          else {
            return new Datum(a.context, a.stringVal().compareTo(b.stringVal()) >= 0);
          }
        case Datum.NUMBER:
          return new Datum(a.context, a.doubleVal() >= b.doubleVal());
        default:
          return new Datum(a.context, false);
      }
    }
    catch(NullPointerException e) {
      logger.error(e.getMessage(),e);
    }
    return new Datum(a.context, false);
  }
  
  /**
    @return (a > b)
  */
  static Datum gt(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      switch (a.type()) {
        case Datum.DATE:
        case Datum.TIME:
          return new Datum(a.context,
            (a.dateVal().after(b.dateVal()))
            );
        case Datum.WEEKDAY:
        case Datum.MONTH:
        case Datum.YEAR:
        case Datum.DAY:
        case Datum.HOUR:
        case Datum.MINUTE:
        case Datum.SECOND:
        case Datum.MONTH_NUM:
        case Datum.DAY_NUM:
        {
          boolean ans = false;
          if (b.isDate()) {
            ans = (DatumMath.getCalendarField(a,a.type()) > DatumMath.getCalendarField(b,a.type()));
          }
          else if (b.isNumeric()) {
            ans = (a.doubleVal()> b.doubleVal());
          }
          if (logger.isDebugEnabled()) logger.debug("gt(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
          return new Datum(a.context, ans);
        }
        case Datum.STRING:
          if (a.isNumeric())
            return new Datum(a.context, a.doubleVal() > b.doubleVal());
          else {
            return new Datum(a.context, a.stringVal().compareTo(b.stringVal()) > 0);
          }
        case Datum.NUMBER:
          return new Datum(a.context, a.doubleVal() > b.doubleVal());
        default:
          return new Datum(a.context, false);
      }
    }
    catch(NullPointerException e) {
      logger.error(e.getMessage(),e);
    }
    return new Datum(a.context, false);
  }
  
  /**
    @return (a <= b)
  */
  static Datum le(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      switch (a.type()) {
        case Datum.DATE:
        case Datum.TIME:
          return new Datum(a.context,
            (a.dateVal().before(b.dateVal())) ||
            (a.dateVal().equals(b.dateVal()))
            );
        case Datum.WEEKDAY:
        case Datum.MONTH:
        case Datum.YEAR:
        case Datum.DAY:
        case Datum.HOUR:
        case Datum.MINUTE:
        case Datum.SECOND:
        case Datum.MONTH_NUM:
        case Datum.DAY_NUM:
        {
          boolean ans = false;
          if (b.isDate()) {
            ans = (DatumMath.getCalendarField(a,a.type()) <= DatumMath.getCalendarField(b,a.type()));
          }
          else if (b.isNumeric()) {
            ans = (a.doubleVal()<= b.doubleVal());
          }
          if (logger.isDebugEnabled()) logger.debug("le(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
          return new Datum(a.context, ans);
        }
        case Datum.STRING:
          if (a.isNumeric())
            return new Datum(a.context, a.doubleVal() <= b.doubleVal());
          else {
            return new Datum(a.context, a.stringVal().compareTo(b.stringVal()) <= 0);
          }
        case Datum.NUMBER:
          return new Datum(a.context, a.doubleVal() <= b.doubleVal());
        default:
          return new Datum(a.context, false);
      }
    }
    catch(NullPointerException e) {
      logger.error(e.getMessage(),e);
    }
    return new Datum(a.context, false);
  }
  
  /**
    @return (a < b)
  */
  static Datum lt(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      switch (a.type()) {
        case Datum.DATE:
        case Datum.TIME:
          return new Datum(a.context,
            (a.dateVal().before(b.dateVal()))
            );
        case Datum.WEEKDAY:
        case Datum.MONTH:
        case Datum.YEAR:
        case Datum.DAY:
        case Datum.HOUR:
        case Datum.MINUTE:
        case Datum.SECOND:
        case Datum.MONTH_NUM:
        case Datum.DAY_NUM:
        {
          boolean ans = false;
          if (b.isDate()) {
            ans = (DatumMath.getCalendarField(a,a.type()) < DatumMath.getCalendarField(b,a.type()));
          }
          else if (b.isNumeric()) {
            ans = (a.doubleVal()< b.doubleVal());
          }
          if (logger.isDebugEnabled()) logger.debug("lt(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
          return new Datum(a.context, ans);
        }
        case Datum.STRING:
          if (a.isNumeric())
            return new Datum(a.context, a.doubleVal() < b.doubleVal());
          else {
            return new Datum(a.context, a.stringVal().compareTo(b.stringVal()) < 0);
          }
        case Datum.NUMBER:
          return new Datum(a.context, a.doubleVal() < b.doubleVal());
        default:
          return new Datum(a.context, false);
      }
    }
    catch(NullPointerException e) {
      logger.error(e.getMessage(),e);
    }
    return new Datum(a.context, false);
  }
  
  /**
    @return (a % b)
  */
  static Datum modulus(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      return new Datum(a.context, a.doubleVal() % b.doubleVal());
    }
    catch(ArithmeticException e) {
      logger.error(e.getMessage(),e);
      return Datum.getInstance(a.context,Datum.INVALID);
    }
  }
  
  /**
    @return (a * b)
  */
  static Datum multiply(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, a.doubleVal() * b.doubleVal());
  }
  
  /**
    @return (-a)
  */
  static Datum neg(Datum a) {
    Datum d = DatumMath.hasError(a,null);
    if (d != null)
      return d;

    return new Datum(a.context, -a.doubleVal());
  }
  
  /**
    @return (a != b)
  */
  static Datum neq(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    if (a.type() == Datum.NA || b.type() == Datum.NA) {
      return new Datum(a.context, true);  // neq to anything
    }

    try {
      switch (a.type()) {
        case Datum.DATE:
        case Datum.TIME:
          return new Datum(a.context, !
            (a.dateVal().equals(b.dateVal()))
            );
        case Datum.WEEKDAY:
        case Datum.MONTH:
        case Datum.YEAR:
        case Datum.DAY:
        case Datum.HOUR:
        case Datum.MINUTE:
        case Datum.SECOND:
        case Datum.MONTH_NUM:
        case Datum.DAY_NUM:
        {
          boolean ans = false;
          if (b.isDate()) {
            ans = (DatumMath.getCalendarField(a,a.type()) != DatumMath.getCalendarField(b,a.type()));
          }
          else if (b.isNumeric()) {
            ans = (a.doubleVal()!= b.doubleVal());
          }
          if (logger.isDebugEnabled()) logger.debug("neq(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
          return new Datum(a.context, ans);
        }
        case Datum.STRING:
          if (a.isNumeric())
            return new Datum(a.context, a.doubleVal() != b.doubleVal());
          else {
            return new Datum(a.context, a.stringVal().compareTo(b.stringVal()) != 0);
          }
        case Datum.NUMBER:
          return new Datum(a.context, a.doubleVal() != b.doubleVal());
        default:
          return new Datum(a.context, false);  // value is indeterminate - neither eq nor neq
      }
    }
    catch(NullPointerException e) {
      logger.error(e.getMessage(),e);
    }
    return new Datum(a.context, false);
  }
  
  /**
    @return (!a)
  */
  static Datum not(Datum a) {
    Datum d = DatumMath.hasError(a,null);
    if (d != null)
      return d;

    return new Datum(a.context, !a.booleanVal());
  }
  
  /**
    @return (a | b)
  */
  static Datum or(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, (double) ((long) a.doubleVal() | (long) b.doubleVal()));
  }
  
  /**
    @return (a || b)
  */
  static Datum oror(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, a.booleanVal() || b.booleanVal());
  }
  
  /**
    Returns difference between two values.
    If a is a date, and b is a number, subtracts that many units from a's date value
    FIXME:  if both values are dates, should return a number (or Duration), not a Date!
    
    @return (a - b)
  */
  static Datum subtract(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    switch (a.type()) {
      default:
        return new Datum(a.context, a.doubleVal() - b.doubleVal());
       case Datum.DATE:
       case Datum.TIME:
         /* need way to throw error here */
        return Datum.getInstance(a.context,Datum.INVALID);
      case Datum.WEEKDAY:
      case Datum.MONTH:
      case Datum.YEAR:
      case Datum.DAY:
      case Datum.HOUR:
      case Datum.MINUTE:
      case Datum.SECOND:
      case Datum.MONTH_NUM:
      case Datum.DAY_NUM:
        if (!b.isNumeric()) {
          /* need way to throw an error here */
          return Datum.getInstance(a.context,Datum.INVALID);
        }
        else {
          int field = DatumMath.datumToCalendar(a.type());
          GregorianCalendar gc = new GregorianCalendar();  // should happen infrequently (not a garbage collection problem?)

          gc.setTime(a.dateVal());
          gc.add(field, -((int) b.doubleVal()));
          return new Datum(a.context,gc.getTime(),a.type());  // set to type of first number in expression
        }
    }
  }
  
  /**
    @return (a xor b)
  */
  static Datum xor(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, (double) ((long) a.doubleVal() ^ (long) b.doubleVal()));
  }
}
