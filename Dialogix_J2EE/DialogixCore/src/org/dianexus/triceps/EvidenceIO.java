package org.dianexus.triceps;

import java.io.*;
import java.util.Random;
import java.util.logging.*;

class EvidenceIO implements VersionIF {   //  CONCURRENCY RISK?: Most of these functions, like create temp file, now have internal Java support
    private static final String LoggerName = "org.dianexus.triceps.EvidenceIO";
    public EvidenceIO() {
    }

    String createTempFile() {   //  CONCURRENCY RISK?: sychnonized might help avoid naming conflicts I've seen in the past
        try {
            File name = createTempFile("tmp", null);
//			name.deleteOnExit();	// to facilitate cleanup
            return name.toString();
        } catch (Exception e) {
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
            return null;
        }
    }

    boolean saveEvidence(Evidence ev,   // CONCURRENCY RISK?:  NO, does nothing
                                 String filename,
                                 String[] names) {
        return false;	// XXX
    }

    boolean saveAll(Schedule sched,   // CONCURRENCY RISK?:  Should be OK
                            String filename) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean ok = false;

        try {
            Evidence ev = sched.getTriceps().getEvidence();
            int length = sched.size();
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);


            for (int i = 0; i < length; ++i) {
                Node node = sched.getNode(i);
                printData(bw, ev, node);
            }
            ok = true;
        } catch (Exception e) {
            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
        }
        try {
            if (fw != null) {
                if (bw != null) {
                    bw.close();
                } else {
                    fw.close();
                }
            }
        } catch (Exception ex) {
        }
        return ok;
    }

    private void printData(BufferedWriter bw,   //  CONCURRENCY RISK?:  NOT USED
                                    Evidence ev,
                                    Node node) {
        try {
            bw.write(node.getLocalName());
            bw.write("\t");

            Value v = ev.getValue(node);
            if (v == null) {
                bw.write("");
            } else {
                bw.write(v.getDatum().stringVal());
            }
            bw.newLine();
        } catch (Exception e) {
        }
    }
//    private static final String WIN_ID = "Windows";
//    private static final String WIN_PATH = "rundll32";
//    private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
//
//    static boolean exec(String commands) {   //  CONCURRENCY RISK?: but never used
//        // FIXME: this is generating errors, rather than running a sub-process -- why?
//        Runtime rt = Runtime.getRuntime();
//        Process pr = null;
//        String cmd = null;
//
//        try {
//            if (isWindowsPlatform()) {
//                cmd = WIN_PATH + " " + WIN_FLAG + " " + commands;
//            } else {
//                cmd = commands;
//            }
//            pr = rt.exec(cmd);	// XXX -- check that works for Unix too
//            pr.waitFor();
//            int exit = pr.exitValue();
//            return (exit == 0);	// means normal exit
//        } catch (Exception e) {
//            Logger.getLogger(LoggerName).log(Level.SEVERE, "", e);
//            return false;
//        }
//    }
//
//    private static boolean isWindowsPlatform() {   //  CONCURRENCY RISK?: NO
//        String os = System.getProperty("os.name");
//        if (os != null && os.startsWith(WIN_ID)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    /** Modified from JDK 1.3 createTempFile () */
    private static final Object tmpFileLock = new Object();   //  CONCURRENCY RISK?: NO
    private static int counter = -1; /* Protected by tmpFileLock */   // CONCURRENCY RISK?:  NO


    public File createTempFile(String prefix,   //  CONCURRENCY RISK?:   NO
                                        String suffix)
        throws IOException {
        return createTempFile(prefix, suffix, null);
    }

    public File createTempFile(String prefix,   //  CONCURRENCY RISK?: NO
                                        String suffix,
                                        File directory)
        throws IOException {
        if (prefix == null) {
            throw new NullPointerException();
        }
        if (prefix.length() < 3) {
            throw new IllegalArgumentException("Prefix string too short");
        }
        String s = (suffix == null) ? ".tmp" : suffix;
        synchronized (tmpFileLock) {
            if (directory == null) {
                directory = new File(getTempDir());
            }
            SecurityManager sm = System.getSecurityManager();
            File f;
            do {
                f = generateFile(prefix, s, directory);
            } while (!checkAndCreate(f.getPath(), sm));
            return f;
        }
    }
    private static String tmpdir; /* Protected by tmpFileLock */   //  CONCURRENCY RISK?: NO


    private String getTempDir() {   //  CONCURRENCY RISK?: NO
        if (tmpdir == null) {
            tmpdir = "/tmp";	// this is cheating
//	    GetPropertyAction a = new GetPropertyAction("java.io.tmpdir");
//	    tmpdir = ((String) AccessController.doPrivileged(a));
        }
        return tmpdir;
    }

    private File generateFile(String prefix,   //  CONCURRENCY RISK?: NO
                                       String suffix,
                                       File dir)
        throws IOException {
        if (counter == -1) {
            counter = new Random().nextInt() & 0xffff;
        }
        counter++;
        return new File(dir, prefix + Integer.toString(counter) + suffix);
    }

    private boolean checkAndCreate(String filename,   //  CONCURRENCY RISK?: NO
                                            SecurityManager sm)
        throws IOException {
        if (sm != null) {
            try {
                sm.checkWrite(filename);
            } catch (Exception x) {
                /* Throwing the original AccessControlException could disclose
                the location of the default temporary directory, so we
                re-throw a more innocuous SecurityException */
                throw new SecurityException("Unable to create temporary file");
            }
        }
        return true;	// is this correct -- don't I need to create the actual file?
//	return fs.createFileExclusively(filename);
    }
}
