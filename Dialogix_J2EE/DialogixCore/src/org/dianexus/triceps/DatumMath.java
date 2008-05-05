package org.dianexus.triceps;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.logging.*;

/**
These helper functions perform all math operations between Datum values, properly handling MISSING values
 */
public final class DatumMath implements VersionIF {
    /**
    Internal helper function -- if either argument is INVALID, propagate the INVALID value
    XXX:  Should propagate REFUSED and/or NA values?
    @param  a the 1st Datum
    @param  b the 2nd Datum
    @return INVALID if either is INVALID, else null to indicate that neither is an error
     */
    
    public DatumMath() {
    }
    
    private Datum hasError(Datum a,   
                                   Datum b) {
        // This function needs to be reconsidered as to the proper way to handle error propagation
        if (a.isType(Datum.INVALID) || (b != null && b.isType(Datum.INVALID))) {
            return new Datum(Datum.INVALID,a.triceps);
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
        return null;	// to indicate that there is no error that needs propagating
    }

    /**
    Convert from DataType to CalendarType
    @param  datumType the DataType
    @return the associated Calendar type
     */
    private int datumToCalendar(int datumType) {   
        switch (datumType) {
            case Datum.YEAR:
                return Calendar.YEAR;
            case Datum.MONTH:
                return Calendar.MONTH;
            case Datum.DAY:
                return Calendar.DAY_OF_MONTH;
            case Datum.WEEKDAY:
                return Calendar.DAY_OF_WEEK;
            case Datum.HOUR:
                return Calendar.HOUR_OF_DAY;
            case Datum.MINUTE:
                return Calendar.MINUTE;
            case Datum.SECOND:
                return Calendar.SECOND;
            case Datum.MONTH_NUM:
                return Calendar.MONTH;
            case Datum.DAY_NUM:
                return Calendar.DAY_OF_YEAR;
            default:
                return 0;	// should never get here
        }
    }

    /**
    Create a Date value from a numeric type
    @param  val the Integer
    @param  datumType the DataType
    @return a sample Date value
     */
    public Date createDate(int val,  
                                    int datumType) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.set(datumToCalendar(datumType), val);
        return calendar.getTime();
    }

    /**
    Return the desired calendar field from a Date given a DataType to make it easier to compare them
    For example, return the numeric month or year field.
    @param d  the Datum
    @param datumType  the DataType
    @return the integer value for that field
     */
    private int getCalendarField(Datum d,   
                                         int datumType) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d.dateVal());
        return calendar.get(datumToCalendar(datumType));
    }

    /**
    Add two values
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return their sum
     */
    public Datum add(Datum a,   
                                                 Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        switch (a.type()) {
            default:
            case Datum.NUMBER:
            case Datum.STRING:
                return new Datum(a.triceps, a.doubleVal() + b.doubleVal());
            case Datum.DATE:
            case Datum.TIME:
                /* need way to throw error here */
                return new Datum(Datum.INVALID,a.triceps);
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
                    return new Datum(Datum.INVALID,a.triceps);
                } else {
                    int field = datumToCalendar(a.type());
                    GregorianCalendar gc = new GregorianCalendar();	// should happen infrequently (not a garbage collection problem?)

                    gc.setTime(a.dateVal());
                    gc.add(field, (int) b.doubleVal());
                    return new Datum(a.triceps, gc.getTime(), a.type());	// set to type of first number in expression
                }
        }
    }

    /**
    Do logical AND of two values (a & b)
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return (a & b)
     */
    public Datum and(Datum a,   
                                                 Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, (double) ((long) a.doubleVal() & (long) b.doubleVal()));
    }

    /**
    Do conditional AND of two values (a && b)
    NOTE (XXX):  This has a side-effect -- both a and b sides will always be processed
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return (a && b)
     */
    public Datum andand(Datum a,   
                                                    Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, a.booleanVal() && b.booleanVal());
    }

    /** 
    Concatenates two Datum objects of type String and returns the resulting Datum object
    @param a  the 1st Datum
    @param b  the 2nd Datum
    @return (a . b)
     */
    public Datum concat(Datum a,   
                                                    Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            return new Datum(a.triceps, a.stringVal().concat(b.stringVal()), Datum.STRING);
        } catch (NullPointerException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
            return new Datum(a.triceps, a.stringVal(), Datum.STRING);
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
    public Datum conditional(Datum a,   
                                                         Datum b,
                                                         Datum c) {
        Datum d = hasError(a, null);	// if conditional based upon a REFUSED or INVALID, always return that type
        if (d != null) {
            return d;
        }

        if (a.booleanVal()) {
            return b;
        } else {
            return c;
        }
    }

    /**
    @return (a / b)
     */
    public Datum divide(Datum a,   
                                                    Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            return new Datum(a.triceps, a.doubleVal() / b.doubleVal());
        } catch (ArithmeticException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
            return new Datum(Datum.INVALID,a.triceps);
        }
    }

    /**
    @return (a == b)
     */
    public Datum eq(Datum a,   
                                                Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            switch (a.type()) {
                case Datum.DATE:
                case Datum.TIME:
                    return new Datum(a.triceps, (a.dateVal().equals(b.dateVal())));
                case Datum.WEEKDAY:
                case Datum.MONTH:
                case Datum.YEAR:
                case Datum.DAY:
                case Datum.HOUR:
                case Datum.MINUTE:
                case Datum.SECOND:
                case Datum.MONTH_NUM:
                case Datum.DAY_NUM: {
                    boolean ans = false;
                    if (b.isDate()) {
                        ans = (getCalendarField(a, a.type()) == getCalendarField(b, a.type()));
                    } else if (b.isNumeric()) {
                        ans = (a.doubleVal() == b.doubleVal());
                    }
                    return new Datum(a.triceps, ans);
                }
                case Datum.STRING:
                    if (a.isNumeric()) {
                        return new Datum(a.triceps, a.doubleVal() == b.doubleVal());
                    } else {
                        return new Datum(a.triceps, a.stringVal().compareTo(b.stringVal()) == 0);
                    }
                case Datum.NUMBER:
                    return new Datum(a.triceps, a.doubleVal() == b.doubleVal());
                default:
                    return new Datum(a.triceps, false);
            }
        } catch (NullPointerException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
        }
        return new Datum(a.triceps, false);
    }

    /**
    @return (a >= b)
     */
    public Datum ge(Datum a,   
                                                Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            switch (a.type()) {
                case Datum.DATE:
                case Datum.TIME:
                    return new Datum(a.triceps,
                        (a.dateVal().after(b.dateVal())) ||
                        (a.dateVal().equals(b.dateVal())));
                case Datum.WEEKDAY:
                case Datum.MONTH:
                case Datum.YEAR:
                case Datum.DAY:
                case Datum.HOUR:
                case Datum.MINUTE:
                case Datum.SECOND:
                case Datum.MONTH_NUM:
                case Datum.DAY_NUM: {
                    boolean ans = false;
                    if (b.isDate()) {
                        ans = (getCalendarField(a, a.type()) >= getCalendarField(b, a.type()));
                    } else if (b.isNumeric()) {
                        ans = (a.doubleVal() >= b.doubleVal());
                    }
                    return new Datum(a.triceps, ans);
                }
                case Datum.STRING:
                    if (a.isNumeric()) {
                        return new Datum(a.triceps, a.doubleVal() >= b.doubleVal());
                    } else {
                        return new Datum(a.triceps, a.stringVal().compareTo(b.stringVal()) >= 0);
                    }
                case Datum.NUMBER:
                    return new Datum(a.triceps, a.doubleVal() >= b.doubleVal());
                default:
                    return new Datum(a.triceps, false);
            }
        } catch (NullPointerException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
        }
        return new Datum(a.triceps, false);
    }

    /**
    @return (a > b)
     */
    public Datum gt(Datum a,   
                                                Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            switch (a.type()) {
                case Datum.DATE:
                case Datum.TIME:
                    return new Datum(a.triceps,
                        (a.dateVal().after(b.dateVal())));
                case Datum.WEEKDAY:
                case Datum.MONTH:
                case Datum.YEAR:
                case Datum.DAY:
                case Datum.HOUR:
                case Datum.MINUTE:
                case Datum.SECOND:
                case Datum.MONTH_NUM:
                case Datum.DAY_NUM: {
                    boolean ans = false;
                    if (b.isDate()) {
                        ans = (getCalendarField(a, a.type()) > getCalendarField(b, a.type()));
                    } else if (b.isNumeric()) {
                        ans = (a.doubleVal() > b.doubleVal());
                    }
                    return new Datum(a.triceps, ans);
                }
                case Datum.STRING:
                    if (a.isNumeric()) {
                        return new Datum(a.triceps, a.doubleVal() > b.doubleVal());
                    } else {
                        return new Datum(a.triceps, a.stringVal().compareTo(b.stringVal()) > 0);
                    }
                case Datum.NUMBER:
                    return new Datum(a.triceps, a.doubleVal() > b.doubleVal());
                default:
                    return new Datum(a.triceps, false);
            }
        } catch (NullPointerException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
        }
        return new Datum(a.triceps, false);
    }

    /**
    @return (a <= b)
     */
    public Datum le(Datum a,   
                                                Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            switch (a.type()) {
                case Datum.DATE:
                case Datum.TIME:
                    return new Datum(a.triceps,
                        (a.dateVal().before(b.dateVal())) ||
                        (a.dateVal().equals(b.dateVal())));
                case Datum.WEEKDAY:
                case Datum.MONTH:
                case Datum.YEAR:
                case Datum.DAY:
                case Datum.HOUR:
                case Datum.MINUTE:
                case Datum.SECOND:
                case Datum.MONTH_NUM:
                case Datum.DAY_NUM: {
                    boolean ans = false;
                    if (b.isDate()) {
                        ans = (getCalendarField(a, a.type()) <= getCalendarField(b, a.type()));
                    } else if (b.isNumeric()) {
                        ans = (a.doubleVal() <= b.doubleVal());
                    }
                    return new Datum(a.triceps, ans);
                }
                case Datum.STRING:
                    if (a.isNumeric()) {
                        return new Datum(a.triceps, a.doubleVal() <= b.doubleVal());
                    } else {
                        return new Datum(a.triceps, a.stringVal().compareTo(b.stringVal()) <= 0);
                    }
                case Datum.NUMBER:
                    return new Datum(a.triceps, a.doubleVal() <= b.doubleVal());
                default:
                    return new Datum(a.triceps, false);
            }
        } catch (NullPointerException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
        }
        return new Datum(a.triceps, false);
    }

    /**
    @return (a < b)
     */
    public Datum lt(Datum a,   
                                                Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            switch (a.type()) {
                case Datum.DATE:
                case Datum.TIME:
                    return new Datum(a.triceps,
                        (a.dateVal().before(b.dateVal())));
                case Datum.WEEKDAY:
                case Datum.MONTH:
                case Datum.YEAR:
                case Datum.DAY:
                case Datum.HOUR:
                case Datum.MINUTE:
                case Datum.SECOND:
                case Datum.MONTH_NUM:
                case Datum.DAY_NUM: {
                    boolean ans = false;
                    if (b.isDate()) {
                        ans = (getCalendarField(a, a.type()) < getCalendarField(b, a.type()));
                    } else if (b.isNumeric()) {
                        ans = (a.doubleVal() < b.doubleVal());
                    }
                    return new Datum(a.triceps, ans);
                }
                case Datum.STRING:
                    if (a.isNumeric()) {
                        return new Datum(a.triceps, a.doubleVal() < b.doubleVal());
                    } else {
                        return new Datum(a.triceps, a.stringVal().compareTo(b.stringVal()) < 0);
                    }
                case Datum.NUMBER:
                    return new Datum(a.triceps, a.doubleVal() < b.doubleVal());
                default:
                    return new Datum(a.triceps, false);
            }
        } catch (NullPointerException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
        }
        return new Datum(a.triceps, false);
    }

    /**
    @return (a % b)
     */
    public Datum modulus(Datum a,   
                                                     Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        try {
            return new Datum(a.triceps, a.doubleVal() % b.doubleVal());
        } catch (ArithmeticException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
            return new Datum(Datum.INVALID,a.triceps);
        }
    }

    /**
    @return (a * b)
     */
    public Datum multiply(Datum a,   
                                                      Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, a.doubleVal() * b.doubleVal());
    }

    /**
    @return (-a)
     */
    public Datum neg(Datum a) {   
        Datum d = hasError(a, null);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, -a.doubleVal());
    }

    /**
    @return (a != b)
     */
    public Datum neq(Datum a,   
                                                 Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        if (a.type() == Datum.NA || b.type() == Datum.NA) {
            return new Datum(a.triceps, true);	// neq to anything
        }

        try {
            switch (a.type()) {
                case Datum.DATE:
                case Datum.TIME:
                    return new Datum(a.triceps, !(a.dateVal().equals(b.dateVal())));
                case Datum.WEEKDAY:
                case Datum.MONTH:
                case Datum.YEAR:
                case Datum.DAY:
                case Datum.HOUR:
                case Datum.MINUTE:
                case Datum.SECOND:
                case Datum.MONTH_NUM:
                case Datum.DAY_NUM: {
                    boolean ans = false;
                    if (b.isDate()) {
                        ans = (getCalendarField(a, a.type()) != getCalendarField(b, a.type()));
                    } else if (b.isNumeric()) {
                        ans = (a.doubleVal() != b.doubleVal());
                    }
                    return new Datum(a.triceps, ans);
                }
                case Datum.STRING:
                    if (a.isNumeric()) {
                        return new Datum(a.triceps, a.doubleVal() != b.doubleVal());
                    } else {
                        return new Datum(a.triceps, a.stringVal().compareTo(b.stringVal()) != 0);
                    }
                case Datum.NUMBER:
                    return new Datum(a.triceps, a.doubleVal() != b.doubleVal());
                default:
                    return new Datum(a.triceps, false);	// value is indeterminate - neither eq nor neq
            }
        } catch (NullPointerException e) {
            Logger.getLogger("org.dianexus.triceps.DatumMath").log(Level.SEVERE, "", e);
        }
        return new Datum(a.triceps, false);
    }

    /**
    @return (!a)
     */
    public Datum not(Datum a) {   
        Datum d = hasError(a, null);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, !a.booleanVal());
    }

    /**
    @return (a | b)
     */
    public Datum or(Datum a,   
                                                Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, (double) ((long) a.doubleVal() | (long) b.doubleVal()));
    }

    /**
    @return (a || b)
     */
    public Datum oror(Datum a,   
                                                  Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, a.booleanVal() || b.booleanVal());
    }

    /**
    Returns difference between two values.
    If a is a date, and b is a number, subtracts that many units from a's date value
    FIXME:  if both values are dates, should return a number (or Duration), not a Date!
    @return (a - b)
     */
    public Datum subtract(Datum a,   
                                                      Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        switch (a.type()) {
            default:
                return new Datum(a.triceps, a.doubleVal() - b.doubleVal());
            case Datum.DATE:
            case Datum.TIME:
                /* need way to throw error here */
                return new Datum(Datum.INVALID,a.triceps);
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
                    return new Datum(Datum.INVALID,a.triceps);
                } else {
                    int field = datumToCalendar(a.type());
                    GregorianCalendar gc = new GregorianCalendar();	// should happen infrequently (not a garbage collection problem?)

                    gc.setTime(a.dateVal());
                    gc.add(field, -((int) b.doubleVal()));
                    return new Datum(a.triceps, gc.getTime(), a.type());	// set to type of first number in expression
                }
        }
    }

    /**
    @return (a xor b)
     */
    public Datum xor(Datum a,   
                                                 Datum b) {
        Datum d = hasError(a, b);
        if (d != null) {
            return d;
        }

        return new Datum(a.triceps, (double) ((long) a.doubleVal() ^ (long) b.doubleVal()));
    }
}
