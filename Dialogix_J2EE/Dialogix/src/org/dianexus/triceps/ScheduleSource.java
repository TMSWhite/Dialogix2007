package org.dianexus.triceps;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;
import java.util.HashMap;

import java.util.zip.ZipFile;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.util.logging.*;

final class ScheduleSource implements VersionIF {

    private boolean isValid = false;
    private Vector<String> headers = new Vector<String>();
    private Vector<String> body = new Vector<String>();
    private SourceInfo sourceInfo = null;
    private int reservedCount = 0;
//    private static final ScheduleSource NULL = new ScheduleSource();  // CONCURRENCY RISK?: YES
    /* maintain Pooled Collection of ScheduleSources, indexed by name.  Only update if file has changed */
    private static final HashMap<String, ScheduleSource> sources = new HashMap<String, ScheduleSource>();  // CONCURRENCY RISK?: YES - munanaged

    private ScheduleSource() {
    }

    private ScheduleSource(SourceInfo si) {
        sourceInfo = si;

        if (sourceInfo.isReadable() && load()) {
            if (headers.size() > 0 && body.size() > 0 && reservedCount > 0) {
                isValid = true;
            }
        }
    }

    static synchronized ScheduleSource getInstance(String src) {  // CONCURRENCY RISK?: YES, the synchronization doesn't fully manage this
        if (src == null) {
            return new ScheduleSource();
        }

        ScheduleSource ss = (ScheduleSource) sources.get(src);

        SourceInfo newSI = SourceInfo.getInstance(src);
        SourceInfo oldSI = ((ss == null) ? null : ss.getSourceInfo());

        if (!newSI.isReadable()) {
            // then does not exist, or deleted
            Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "##ScheduleSource(" + src + ") is not accessible, or has been deleted");
            ss = new ScheduleSource();
            sources.put(src, ss);
            return ss;
        } else if (ss == null || oldSI == null) {
            // then this is the first time it is accessed, or the file has changed
            ss = new ScheduleSource(newSI);
            sources.put(src, ss);
            if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, "##ScheduleSource(" + src + ") is new ->(" + ss.getHeaders().size() + "," + ss.getBody().size() + ")");
            }
            return ss;
        } else if (!oldSI.equals(newSI)) {
            // then the file has changed and needs to be reloaded
            if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, "##ScheduleSource(" + src + ") has changed from (" + ss.getHeaders().size() + "," + ss.getBody().size() + ")");
            }
            ss = new ScheduleSource(newSI);
            sources.put(src, ss);
            if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, " -> (" + ss.getHeaders().size() + "," + ss.getBody().size() + ")");
            }
            return ss;
        } else {
            // file is unchanged - use buffered copy
            if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, "##ScheduleSource(" + src + ") unchanged");
            }
            return ss;
        }
    }

    private boolean load() {
        String name = sourceInfo.getSource();

        if ((DEPLOYABLE || DEMOABLE) && name.toLowerCase().endsWith(".jar")) {
            return readFromJar();
        } else if (AUTHORABLE && name.toLowerCase().endsWith(".txt")) {
            return readFromAscii();
        } else if (name.toLowerCase().endsWith(".dat")) {
            return readFromAscii();
        } else {
            return false;
        }
    }

    /* Is there a way to detect the character encoding of a file?  Excel's Save as Unicode Text is UTF-16.  
    Should I convert all files to UTF-16?  If so, could either convert everything to UTF-16, or test first.  */
    private boolean readFromAscii() {
        // load from text file

        boolean pastHeaders = false;
        BufferedReader br = null;
///		InputStreamReader isr = null;
        try {
////			br = new BufferedReader(new FileReader(sourceInfo.getSource()));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(sourceInfo.getSource())), "UTF-16"));

            String fileLine = null;
            while ((fileLine = br.readLine()) != null) {
                if ("".equals(fileLine.trim())) {
                    continue;
                }
                if (!pastHeaders && fileLine.startsWith("RESERVED")) {
                    ++reservedCount;
                    headers.addElement(fileLine);
                    if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                        Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, "[Header]\t" + fileLine);
                    }
                    continue;
                }
                if (fileLine.startsWith("COMMENT")) {
                    if (pastHeaders) {
                        body.addElement(fileLine);
                        if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                            Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, "[Body]\t" + fileLine);
                        }
                    } else {
                        headers.addElement(fileLine);
                        if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                            Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, "[Header]\t" + fileLine);
                        }
                    }
                    continue;
                }
                // otherwise a body line
                pastHeaders = true;	// so that datafile RESERVED words are added in sequence
                body.addElement(fileLine);
                if (Logger.getLogger("org.dianexus.triceps.ScheduleSource").isLoggable(Level.FINER)) {
                    Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER, "[Body]\t" + fileLine);
                }
            }
        } catch (Exception e) {
            Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "", e);
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException t) {
                Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "", t);
            }
        }
        return true;
    }

    Vector<String> getHeaders() {
        return headers;
    }

    Vector<String> getBody() {
        return body;
    }

    boolean isValid() {
        return isValid;
    }

    String getSrcName() {
        if (sourceInfo == null) {
            return "";
        }
        return sourceInfo.getSource();
    }

    SourceInfo getSourceInfo() {
        return sourceInfo;
    }

    private boolean readFromJar() {
        // load from Jar file
        ZipFile jf = null;
        ZipEntry je = null;
        boolean ok = true;

        try {
            jf = new ZipFile(sourceInfo.getSource());
            headers = jarEntryToVector(jf, "headers");
            body = jarEntryToVector(jf, "body");
            reservedCount = headers.size();
        } catch (Exception e) {
            Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "", e);
            ok = false;
        }
        if (jf != null) {
            try {
                jf.close();
            } catch (Exception t) {
                Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "", t);
            }
        }
        return ok;
    }

    private Vector<String> jarEntryToVector(ZipFile jf,
                                             String name) {
        Vector<String> v = new Vector<String>();

        try {
            ZipEntry je = jf.getEntry(name);

            /* first read the data */

            InputStreamReader isr = new InputStreamReader(jf.getInputStream(je));
            BufferedReader br = new BufferedReader(isr);

            String fileLine = null;
            try {
                while ((fileLine = br.readLine()) != null) {
                    if ("".equals(fileLine.trim())) {
                        continue;
                    }
                    v.addElement(fileLine);
                }
            } catch (Exception e) {	// IOException
                Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "", e);
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException t) {
                    Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "", t);
                }
            }

        /* then validate certificates */
        /*	Temporarily remove need to validate certificates
        Certificate certs[] = je.getCertificates();
        if (certs == null || certs.length == 0) {
        Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.FINER,"##ScheduleSource.jarEntryToVector(" + sourceInfo.getSource() + "," + name + ") is not signed");
        if (DEPLOYABLE)	return new Vector<String>();	// empty;		
        }
        else {
        Certificate cert = certs[0];
        try {
        //Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE,"##verifying certificate " + cert.toString());
        cert.verify(cert.getPublicKey());
        }
        catch (Exception t) {
        Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE,"##invalid certificate or corrupted signing: " + t.getMessage());
        if (DEPLOYABLE)	return new Vector<String>();	// empty;		
        }
        }
         */
        } catch (Exception e) {
            Logger.getLogger("org.dianexus.triceps.ScheduleSource").log(Level.SEVERE, "", e);
        }
        return v;
    }

    String saveAsJar(String name) {
        if (!DB_WRITE_SYSTEM_FILES) {
            return null;
        }
        if (AUTHORABLE) {
            JarWriter jf = null;

            int lastPeriod = name.lastIndexOf(".");
            if (lastPeriod != -1) {
                name = name.substring(0, lastPeriod) + ".jar";
            } else {
                name = name + ".jar";
            }

            jf = (new JarWriter()).getInstance(name);

            if (jf == null) {
                return null;
            }

            boolean ok = false;

            ok = jf.addEntry("headers", VectorToIS(getHeaders()));
            ok = jf.addEntry("body", VectorToIS(getBody())) && ok;
            jf.close();

            File f = new File(name);
            if (f.length() == 0L) {
                ok = false;
            }

            return (ok) ? name : null;
        }
        return null;
    }

    private ByteArrayInputStream VectorToIS(Vector<String> v) {
        if (v == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.size(); ++i) {
            sb.append((String) v.elementAt(i));
            sb.append("\n");
        }
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
