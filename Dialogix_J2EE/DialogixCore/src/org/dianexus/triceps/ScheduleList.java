package org.dianexus.triceps;

/*import java.io.*;*/
/*import java.util.*;*/
import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.util.Date;
import java.util.logging.*;

final class ScheduleList implements VersionIF {

    private static final String LoggerName = "org.dianexus.triceps.ScheduleList";
    private Vector schedules = new Vector();
    private String sourceDir = null;
    private org.dianexus.triceps.DialogixLogger oldLogger = new org.dianexus.triceps.DialogixLogger();
    private Triceps triceps;    // FIXME = new Triceps();

    private ScheduleList(Triceps lang,
                 String sourceDir,
                 boolean isSuspended) {
        triceps = /*(lang == null) ? new Triceps() :*/ lang;
        this.sourceDir = sourceDir;
        File dir = new File(sourceDir);

        if (!dir.isDirectory()) {
            setError(sourceDir + triceps.get("is_not_a_directory"));
            Logger.getLogger(LoggerName).log(Level.SEVERE, sourceDir + triceps.get("is_not_a_directory"));
            return;
        } else if (!dir.canRead()) {
            setError(sourceDir + triceps.get("is_not_accessible"));
            Logger.getLogger(LoggerName).log(Level.SEVERE, sourceDir + triceps.get("is_not_accessible"));
            return;
        }

        if (isSuspended) {
            unjarSuspendedInterviews(dir);
        }

        String[] files = dir.list();

        int count = 0;
        for (int i = 0; i < files.length; ++i) {
            File f = new File(dir.toString() + "/" + files[i]);
            if (!f.isDirectory()) {
                Schedule schedule = new Schedule(triceps, f.toString(), false, null);
                if (schedule.isFound()) {
                    schedules.addElement(schedule);
                    ++count;
                } else {
                    schedule.getErrors();	// clear the schedule's errors so that don't report names of files not accessible to user
                }
            }
        }
        triceps.setLocale(null);	// set it to the default
    }

    private void unjarSuspendedInterviews(File dir) {
        String[] files = dir.list();

        int count = 0;
        for (int i = 0; i < files.length; ++i) {
            File f = new File(dir.toString() + "/" + files[i]);
            try {
                if (f.getName().toLowerCase().endsWith(".jar") && f.canRead()) {
                    // unjar file and remove it
                    unjar(f);
                    f.delete();	// delete regardless of whether successfully unjared
                }
            } catch (Exception e) {
                setError("unjarSuspendedInterviews: " + e.getMessage());
                Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            }
        }
    }
    /*
    private void processErrorLog(File file) {
    // check whether this contents is in the existing Triceps.log.err file -- if not, append it
    BufferedReader br = null;
    BufferedReader br2 = null;
    String line=null;
    String line2=null;
    boolean foundFirst = false;
    boolean foundLast = false;
    try {
    br = new BufferedReader(new FileReader(org.dianexus.triceps.DialogixLogger.STDERR_NAME));
    br2 = new BufferedReader(new FileReader(file));
    line2 = br2.readLine();
    while (line2 != null) {
    line = br.readLine();
    if (line == null) {
    break;
    }
    if (line2.equals(line)) {
    foundFirst = true;
    break;
    }
    }
    while (foundFirst) {
    line2 = br2.readLine();
    if (line2 == null) {	// last line in this file
    foundLast = true;
    break;
    }
    line = br.readLine();
    if (!line2.equals(line)) {
    break;
    }
    }
    }
    catch (Exception e) {
    Logger.getLogger(LoggerName).log(Level.SEVERE,"", e);
    }
    try {
    br.close();
    br2.close();
    }
    catch (Exception e) { Logger.getLogger(LoggerName).log(Level.SEVERE,"", e); }
    if (!foundLast) {
    // append to file
    BufferedInputStream bis = null;
    try {
    bis = new BufferedInputStream(new FileInputStream(file));
    byte[] buf = new byte[1000];
    int bytesRead = 0;
    Date now = new Date(System.currentTimeMillis());
    Logger.getLogger(LoggerName).log(Level.FINER,"<!-- START COPYING LOG FILE CONTENTS FROM '" + file.getName() + "' [" + now + "] -->");
    while((bytesRead = bis.read(buf)) != -1) {
    Logger.getLogger(LoggerName).log(Level.FINER,new String(buf,0,bytesRead));
    }
    Logger.getLogger(LoggerName).log(Level.FINER,"<!-- END COPYING LOG FILE CONTENTS FROM '" + file.getName() + "' [" + now + "] -->");
    }
    catch (Exception e) {
    Logger.getLogger(LoggerName).log(Level.SEVERE,file.getName(), e);
    }
    try {
    bis.close();
    }
    catch (Exception e) { Logger.getLogger(LoggerName).log(Level.SEVERE,"", e); }
    }
    try {
    file.delete();	// remove the log file from working directory when done
    }
    catch (Exception e) { Logger.getLogger(LoggerName).log(Level.SEVERE,"", e); }
    }
     */

    private boolean unjar(File file) {
        ZipFile jf = null;
        boolean ok = true;

        try {
            jf = new ZipFile(file);
            Enumeration entries = jf.entries();

            while (entries.hasMoreElements()) {
                if (!saveUnjaredFile(jf, (ZipEntry) entries.nextElement())) {
                    ok = false;
                    break;
                }
            }
        } catch (Exception e) {
            setError("##unjar " + e.getMessage());
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            ok = false;
        }
        if (jf != null) {
            try {
                jf.close();
            } catch (Exception t) {
                Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
            }
        }
        return ok;
    }

    private boolean saveUnjaredFile(ZipFile jf,
                                     ZipEntry je) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        File file = null;
        boolean ok = true;
        byte[] buf = new byte[1000];
        int bytesRead = 0;
        String name = null;

        try {
            name = je.getName();
            file = new File(sourceDir + name);
            if (file.exists()) {
                if (file.length() > je.getSize()) {
                    File jarFile = new File(jf.getName());
                    String jarFileName = jarFile.getName();
                    setError("Unable to restore (" + jarFileName + "): existing file (" + file.getName() + ") is larger than the one you are trying to retore, so it may be more recent.");
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "Unable to restore (" + jarFileName + "): existing file (" + file.getName() + ") is larger than the one you are trying to retore, so it may be more recent.");
                    String baseName = file.toString();
                    baseName = baseName.substring(0, baseName.indexOf("."));
                    setError("If you really want to overwrite the existing files, you must manually delete them (" + baseName + ".*) before restoring (" + jarFileName + ").");
                    Logger.getLogger(LoggerName).log(Level.SEVERE, "If you really want to overwrite the existing files, you must manually delete them (" + baseName + ".*) before restoring (" + jarFileName + ").");
                }
                return false;
            }

            bis = new BufferedInputStream(jf.getInputStream(je));
            fos = new FileOutputStream(file);

            while ((bytesRead = bis.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            setError("##saveUnjaredFile: " + e.getMessage());
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            ok = false;
        }
        if (bis != null) {
            try {
                bis.close();
            } catch (IOException t) {
                Logger.getLogger(LoggerName).log(Level.SEVERE, "", t);
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (Exception e) {
                setError("saveUnjaredFile " + file.toString() + ": " + e.getMessage());
                Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
                ok = false;
            }
        }
        /*
        if (name.toLowerCase().endsWith(Triceps.ERRORLOG_SUFFIX)) {
        processErrorLog(file);
        }
         */
        return ok;
    }

    private void setError(String s) {
        Logger.getLogger(LoggerName).log(Level.SEVERE, s, new Throwable());
        oldLogger.println(s);
    }

    boolean hasErrors() {
        return (oldLogger.size() > 0);
    }

    String getErrors() {
        return oldLogger.toString();
    }

    Vector getSchedules() {
        return schedules;
    }
}
