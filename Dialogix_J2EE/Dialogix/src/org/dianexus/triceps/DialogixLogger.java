package org.dianexus.triceps;

import java.util.*;
import java.io.*;
import java.util.logging.*;


/* Inner class for logging - this is needed to support localization of error messages */
/*public*/ class DialogixLogger implements VersionIF {

    static Logger logger = Logger.getLogger("org.dianexus.triceps.DialogixLogger");

    /*public*/ static DialogixLogger NULL = new DialogixLogger(null, null, true);

//	private static PrintWriter STDERR = null;
//	private static String STDERR_DIR = ".";
//	static final String STDERR_FILENAME = "Dialogix.log.err";
//	static String STDERR_NAME = STDERR_DIR + STDERR_FILENAME;

    /*public*/ static final String DOS_EOL = "\n\r";
    /*public*/ static final String MAC_EOL = "\r";
    /*public*/ static final String UNIX_EOL = "\n";
    /*public*/ static final String HTML_EOL = "<br>";
    private File file = null;
    private Writer out = null;
    private String eol = HTML_EOL;
    private int callCount = 0;
    private int errCount = 0;
    private boolean alsoLogToStderr = false;
    private StringBuffer sb = null;		// backup copy of everything sent to DialogixLogger
    private boolean discard = false;


    /*public*/ DialogixLogger() {
        this(null, HTML_EOL, false);
    }
    /*public*/ DialogixLogger(String eol) {
        this(null, eol, false);
    }

    /*public*/ DialogixLogger(File out) {
        this(HTML_EOL, false, out);
    }
    /*public*/ DialogixLogger(File out,
                   String eol) {
        this(eol, false, out);
    }

    /*public*/ DialogixLogger(String eol,
                   boolean discard,
                   File w) {
//        if (!DB_WRITE_SYSTEM_FILES) {
//            return;
//        }
        this.discard = discard;
        file = w;
        openFile();
        this.eol = ((eol == null) ? HTML_EOL : eol);
        reset();
    }

    private void openFile() {
        if (!DB_WRITE_SYSTEM_FILES) {
            return;
        }
        if (file != null) {
            try {
//				out = new FileWriter(file.toString(),true);	// append to file, if it exists
//				out = new BufferedWriter(new OutputStreamWriter(new FileWriter(file.toString(),true),"UTF-16"));
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.toString(), true), "UTF-16"));
            } catch (IOException e) {
                logger.log(Level.SEVERE, file.toString(), e);
            } catch (SecurityException e) {
                logger.log(Level.SEVERE, file.toString(), e);
            }
        }
    }

    /*public*/ DialogixLogger(Writer out) {
        this(out, HTML_EOL, false);
    }
    /*public*/ DialogixLogger(Writer out,
                   String eol) {
        this(out, eol, false);
    }
    /*public*/ DialogixLogger(Writer w,
                   String eol,
                   boolean discard) {
//        if (!DB_WRITE_SYSTEM_FILES) {
//            return;
//        }
        this.discard = discard;
        out = w;
        this.eol = ((eol == null) ? HTML_EOL : eol);
        reset();
    }


    /*public*/ void setAlsoLogToStderr(boolean ok) {
        alsoLogToStderr = ok;
    }
    /*public*/ boolean isAlsoLogToStderr() {
        return alsoLogToStderr;
    }

    /*public*/ String getEol() {
        return eol;
    }
    /*public*/ void setEol(String eol) {
        this.eol = ((eol == null) ? HTML_EOL : eol);
    }
    /*public*/ Writer getWriter() {
        return out;
    }
    /*public*/ void setWriter(Writer w) {
        out = w;
    }

    /*public*/ void print(String s) {
        write(s, 0, 0, false);
    }
    /*public*/ void print(String s,
               int line,
               int column) {
        write(s, line, column, false);
    }
    /*public*/ void println(String s) {
        write(s, 0, 0, true);
    }
    /*public*/ void println(String s,
                 int line,
                 int column) {
        write(s, line, column, true);
    }

    private void write(String s,
                        int line,
                        int column,
                        boolean addEol) {
//        if (!DB_WRITE_SYSTEM_FILES) {
//            return;
//        }
        if (this == NULL) {
            return;
        }

//		if (discard)
//			return;
        try {
            if (out == null && file != null) {
                logger.log(Level.SEVERE, "##Logger.write(" + getFilename() + ") - had to re-open closed Logger");
                openFile();	// in case was closed during finalization to Jar file
            }

            ++callCount;
            if (addEol) {
                ++errCount;
            }

            String msg =
                ((addEol && line != 0) ? ("[" + line + "," + column + "] " + errCount + ") ") : "") +
                ((s != null) ? s : "") +
                ((addEol) ? eol : "");

            if (out != null) {
                out.write(msg);
                out.flush();
            }
            if (sb != null) {
                sb.append(msg);
            }
            if (alsoLogToStderr) {
//				writeln(msg);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

    /*public*/ static void writeln(String s) {
        DialogixLogger.write(s, true);
    }
    /*public*/ static void write(String s) {
        DialogixLogger.write(s, false);
    }

    /*public*/ static void write(String s,
                       boolean eol) {
    /*		if (s == null) {
    s = "null";
    }
    if (STDERR != null) {
    STDERR.write(s);
    if (eol)
    STDERR.write(UNIX_EOL);
    STDERR.flush();
    }
    else {
    System.err.print(s);
    if (eol)
    System.err.print(UNIX_EOL);
    }*/
    }

    /*public*/ static void printStackTrace(Throwable t) {
    /*		if (t == null) {
    return;
    }
    if (STDERR != null) {
    t.printStackTrace(STDERR);
    STDERR.flush();
    }
    else {
    t.printStackTrace(System.err);
    }*/
    }

    /*public*/ int size() {
        return callCount;
    }

    /*public*/ void reset() {
//        if (!DB_WRITE_SYSTEM_FILES) {
//            return;
//        }
        callCount = 0;
        errCount = 0;
        if (!discard) {
            sb = new StringBuffer();	// otherwise, this is the NULL DialogixLogger, which discards all messages
        }
    }

    public String toString() {
        return toString(true);
    }

    public StringBuffer getStringBuffer() {
        return sb;
    }

    /*public*/ String toString(boolean erase) {
        String temp = "";

        if (sb != null) {
            temp = sb.toString();
        }
        if (erase) {
            reset();
        }
        return temp;
    }

    /*public*/ void flush() {
        if (!DB_WRITE_SYSTEM_FILES) {
            return;
        }
        try {
            if (out != null) {
                out.flush();
            }
            if (file != null) {
                /* close and re-open file so that committed to disk */
                close();
                openFile();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

    /*public*/ void close() {
        if (!DB_WRITE_SYSTEM_FILES) {
            return;
        }
        try {
            if (out != null) {
                out.close();
                out = null;	// so know that closed;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

    /*public*/ boolean delete() {
        if (!DB_WRITE_SYSTEM_FILES) {
            return true;
        }
        close();
        try {
            if (file != null) {
                file.delete();
                file = null;
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "", e);
            return false;
        }
        return true;
    }

    /*public*/ String getFilename() {
        if (!DB_WRITE_SYSTEM_FILES) {
            return "";  // so doesn't through a NullPointerException
        }
        if (file != null) {
            return file.toString();
        }
        return null;
    }

    /*public*/ InputStream getInputStream() {
        if (!DB_WRITE_SYSTEM_FILES) {
            return null;
        }
        if (file == null) {
            return null;
        }

        try {
            close();
            FileInputStream fis = new FileInputStream(getFilename());
            return fis;
//N			return new InputStreamReader(new FileInputStream(getFilename()),"UTF-16");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
            return null;
        }
    }

    /*
    public static InputStream getDefaultInputStream() {
    try {
    STDERR.flush();
    FileInputStream fis = new FileInputStream(STDERR_NAME);
    return fis;
    }
    catch (Exception e) {
    logger.log(Level.SEVERE,"", e);
    return null;			
    }
    }
     */
    static void init(String dir) {
//        if (!DB_WRITE_SYSTEM_FILES) {
//            return;
//        }
        if (dir == null) {
            dir = "";
        }
//		STDERR_DIR = dir;
//		STDERR_NAME = STDERR_DIR + "../logs/" + STDERR_FILENAME;

//		try {
//			STDERR = new PrintWriter(new FileWriter(STDERR_NAME,true),true);	// append to log by default
        Runtime rt = Runtime.getRuntime();
        String msg = "**" + VERSION_NAME + " Log file started on " + new Date(System.currentTimeMillis()) + "with Runtime.maxMemory = " + rt.maxMemory() + "; RT.totalMemory = " + rt.totalMemory() + "; RT.freeMemory = " + rt.freeMemory();
//			writeln(msg);
        logger.log(Level.FINE, msg);
//		}
//		catch (IOException e) {
//			logger.log(Level.SEVERE,"", e);
//		}

        NULL = new DialogixLogger(null, null, true);	// reset the default value
    }
}
