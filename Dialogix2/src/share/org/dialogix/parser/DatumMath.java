/* ******************************************************** 
** Copyright (c) 2000-2005, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

package org.dialogix.parser;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import org.apache.log4j.Logger;

public final class DatumMath {
  static Logger logger = Logger.getLogger(DatumMath.class);
  
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

  static Date createDate(int val, int datumType) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(new Date(System.currentTimeMillis()));
    calendar.set(datumToCalendar(datumType),val);
    return calendar.getTime();
  }

  static int getCalendarField(Datum d, int datumType) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(d.dateVal());
    return calendar.get(DatumMath.datumToCalendar(datumType));
  }

  /** This method returns the sum of two Datum objects of type double. */
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
          gc.add(field, (int) b.doubleVal());
          return new Datum(a.context,gc.getTime(),a.type());  // set to type of first number in expression
        }
    }
  }

  static Datum and(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, (double) ((long) a.doubleVal() & (long) b.doubleVal()));
  }
  static Datum andand(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, a.booleanVal() && b.booleanVal());
  }
  /** This method concatenates two Datum objects of type String and returns the resulting Datum object. */
  static Datum concat(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      return new Datum(a.context, a.stringVal().concat(b.stringVal()),Datum.STRING);
    }
    catch(NullPointerException e) {
      
      logger.error("##NullPointerException @ concat");
      return new Datum(a.context, a.stringVal(),Datum.STRING);
    }
  }
  /**
   * This method evaluates the boolean value of the first Datum object, returns the second Datum object if true,
   * returns the third Datum object if false.
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
  /** This method returns the division of two Datum objects of type double. */
  static Datum divide(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      return new Datum(a.context, a.doubleVal() / b.doubleVal());
    }
    catch(ArithmeticException e) {
      logger.error("##ArithmeticException @ divide");
      return Datum.getInstance(a.context,Datum.INVALID);
    }
  }
  /**
   * This method returns a Datum of type boolean, value true, if two Datum objects of type String or double are equal
   * or value false if not equal.
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
          if (logger.isDebugEnabled()) logger.debug("##eq(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
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
      logger.error("##NullPointerException @ eq");
    }
    return new Datum(a.context, false);
  }
  /**
   * This method returns a Datum of type boolean upon comparing two Datum objects of type String or double for
   * greater than or equal.
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
          if (logger.isDebugEnabled()) logger.debug("##ge(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
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
      logger.error("##NullPointerException @ ge");
    }
    return new Datum(a.context, false);
  }
  /**
   * This method returns a Datum of type boolean upon comparing two Datum objects of type String or double for greater than.
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
          if (logger.isDebugEnabled()) logger.debug("##gt(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
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
      logger.error("##NullPointerException @ gt");
    }
    return new Datum(a.context, false);
  }
  /**
   * This method returns a Datum of type boolean upon comparing two Datum objects of type String or double for
   * less than or equal.
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
          if (logger.isDebugEnabled()) logger.debug("##le(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
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
      logger.error("##NullPointerException @ le");
    }
    return new Datum(a.context, false);
  }
  /** This method returns a Datum of type boolean upon comparing two Datum objects of type String or double for less than. */
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
          if (logger.isDebugEnabled()) logger.debug("##lt(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
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
      logger.error("##NullPointerException @ lt");
    }
    return new Datum(a.context, false);
  }
  /** This method returns a Datum object of type double that is the modulus of two Datum objects of type double. */
  static Datum modulus(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    try {
      return new Datum(a.context, a.doubleVal() % b.doubleVal());
    }
    catch(ArithmeticException e) {
      logger.error("##ArithmeticException @ modulus");
      return Datum.getInstance(a.context,Datum.INVALID);
    }
  }
  /** This method returns the product of two Datum objects of type double. */
  static Datum multiply(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, a.doubleVal() * b.doubleVal());
  }
  /** This method returns a Datum object of type double that is the negative of the passed Datum object. */
  static Datum neg(Datum a) {
    Datum d = DatumMath.hasError(a,null);
    if (d != null)
      return d;

    return new Datum(a.context, -a.doubleVal());
  }
  /**
   * This method returns a Datum of type boolean, value true, if two Datum objects of type String or double are not equal
   * or value false if equal.
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
          if (logger.isDebugEnabled()) logger.debug("##neq(" + a.doubleVal() + "," + a.dateVal() + ";" + b.doubleVal() + "," + b.dateVal() + ")-> " + ans);
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
      logger.error("##NullPointerException @ neq");
    }
    return new Datum(a.context, false);
  }
  /** This method returns a Datum object of the opposite value of the Datum object passed. */
  static Datum not(Datum a) {
    Datum d = DatumMath.hasError(a,null);
    if (d != null)
      return d;

    return new Datum(a.context, !a.booleanVal());
  }
  /** This method returns a Datum of type boolean, value true, if either of two Datum objects of type long are true. */
  static Datum or(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, (double) ((long) a.doubleVal() | (long) b.doubleVal()));
  }
  static Datum oror(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, a.booleanVal() || b.booleanVal());
  }
  /** This method returns the difference between two Datum objects of type double. */
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
  static Datum xor(Datum a, Datum b) {
    Datum d = DatumMath.hasError(a,b);
    if (d != null)
      return d;

    return new Datum(a.context, (double) ((long) a.doubleVal() ^ (long) b.doubleVal()));
  }
}
