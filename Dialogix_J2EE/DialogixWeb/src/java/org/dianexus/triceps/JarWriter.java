package org.dianexus.triceps;

import java.io.File;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.logging.*;

class JarWriter implements VersionIF {

    private static final String LoggerName = "org.dianexus.triceps.JarWriter";
    private String filename = null;
    private FileOutputStream fos = null;
    private ZipOutputStream jos = null;
    private StringBuffer errorLogger = new StringBuffer();

    JarWriter() {
    }

    public JarWriter getInstance(String name) {   // CONCURRENCY RISK?: NO
        JarWriter jf = new JarWriter();
        if (jf.init(name)) {
            return jf;
        } else {
            return null;
        }
    }

    private boolean init(String name) {
        Exception err = null;

        try {
            filename = name;
            fos = new FileOutputStream(filename);
            jos = new ZipOutputStream(fos);
        } catch (Exception e) {	// FileNotFoundException, SecurityException, IOException, NullPointerException
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            err = e;	// unable to create file
        }

        if (err != null) {
            if (jos != null) {
                try {
                    jos.close();
                    jos = null;
                } catch (Exception t) {
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (Exception t) {
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
                }
            }

            setError("JarWriter.init(" + name + ")->" + err.getMessage());
            return false;
        }
        return true;
    }

    boolean addEntry(String name,
                     File file) {
        FileInputStream fis = null;
        boolean ok = false;

        if (name == null || file == null) {
            return false;
        }

        try {
            fis = new FileInputStream(file);
        } catch (Exception e) {	// FileNotFoundException, SecurityException
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            setError("JarWriter.addEntry(" + name + "," + file + ")->" + e.getMessage());
            ok = false;
        }

        ok = addEntry(name, fis);

//		if (fis != null) try { fis.close(); } catch (Exception t) { }	// should be closed by addEntry()
        return ok;
    }

    boolean addEntry(String name,
                     InputStream is) {
        ZipEntry ze = null;
        Exception err = null;

        if (name == null || is == null) {
            return false;
        }

        try {
            ze = new ZipEntry(name);
            jos.putNextEntry(ze);

            byte[] buf = new byte[1000];
            int bytesRead = 0;

            while ((bytesRead = is.read(buf)) != -1) {
                jos.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            // NullPointerException, IllegalArgumentException
            // UnsupportedEncodingException, ZipException, IOException
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            err = e;
        }
        if (err != null) {
            setError("JarWriter.addEntry()->" + err.getMessage());
        }
        try {
            is.close();
        } catch (Exception e) {
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
        }

        return (err == null);
    }

    boolean close() {
        if (jos != null) {
            try {
                jos.close();
                jos = null;
            } catch (Exception t) {
                Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
            }
        }
        return true;
    }

    boolean copyFile(String src,
                     String dst) {
        if (src == null || dst == null) {
            setError("copyFile:  src or dst is null");
            return false;
        }

        Exception err = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File fi = null;
        File fo = null;

        try {
            fi = new File(src);
            fo = new File(dst);

            if (!fi.canRead()) {
                setError("copyFile: " + src + " is unreadable");
                return false;
            }
            if (fi.length() == 0L) {
                setError("copyFile: " + src + " has 0 size");
                return false;
            }
            // do not overwrite a good copy of the jar File currently on the floppy with a bad (smaller) version
            if (fo.exists()) {
                if (fo.length() > fi.length()) {
                    setError("copyFile(" + src + ")->(" + dst + "): dst is larger than source");
                    return false;
                }
            }

            fis = new FileInputStream(fi);
            fos = new FileOutputStream(fo);

            byte[] buf = new byte[1000];
            int bytesRead = 0;

            while ((bytesRead = fis.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            // FileNotFoundException, SecurityException, IOException
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            err = e;
        }

        if (fis != null) {
            try {
                fis.close();
            } catch (Exception t) {
                Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
                setError("copyFile: " + t.getMessage());
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (Exception t) {
                Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
                setError("copyFile: " + t.getMessage());
            }
        }

        if (err != null) {
            setError("copyFile(" + src + ")->(" + dst + "): " + err.getMessage());
            return false;
        }
        Logger.getLogger(LoggerName).log(Level.FINE, "copyFile(" + src + ")->(" + dst + "): SUCCESS");
        return true;
    }

    private void setError(String s) {
        Logger.getLogger(LoggerName).log(Level.SEVERE, "##" + s);
        errorLogger.append(s).append("<br/>");
    }

    boolean hasErrors() {
        return (errorLogger.length() > 0);
    }

    String getErrors() {
        String errors = errorLogger.toString();
        errorLogger = new StringBuffer();
        return errors;
    }
}
