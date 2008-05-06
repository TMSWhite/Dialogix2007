package org.dianexus.triceps;

import java.io.File;
import java.util.Hashtable;
import java.util.logging.*;

final class SourceInfo implements VersionIF {

    static final int SOURCE_OK = 0;
    static final int SOURCE_DOES_NOT_EXIST = 1;
    static final int SOURCE_IS_NOT_A_FILE = 2;
    static final int SOURCE_IS_NOT_READABLE = 3;
    static final int SOURCE_IS_NULL = 4;
    static final int SOURCE_READ_ERROR = 5;
    static final String[] STATUS_MSG = {"OK", "does not exist", "not a file", "not readable", "null filename", "read error"};
    private String source = null;
    private long lastModified = 0L;
    private long fileLength = 0L;
    private int status = SOURCE_IS_NULL;
    private static final Hashtable sources = new Hashtable();   // CONCURRENCY RISK?: YES

    private SourceInfo(String src) {
        source = src;
        getInfo();
    }

    static synchronized SourceInfo getInstance(String src) {	  // CONCURRENCY RISK?: YES - not fully managed
        SourceInfo si = new SourceInfo(src);
        Object o = sources.get(src);

        if (o instanceof SourceInfo) {
            SourceInfo old = (SourceInfo) o;
            if (si.equals(o)) {
                return old;	// so can more readily tell whether a file has changed.
            } else {
                sources.put(src, si);
                return si;
            }
        } else {
            sources.put(src, si);
            return si;
        }
    }

    private void getInfo() {
        try {
            if (source != null && source.trim().length() > 0) {
                File file = new File(source);
                if (!file.exists()) {
                    setStatus(SOURCE_DOES_NOT_EXIST);
                } else if (!file.isFile()) {
                    setStatus(SOURCE_IS_NOT_A_FILE);
                } else if (!file.canRead()) {
                    setStatus(SOURCE_IS_NOT_READABLE);
                } else {
                    lastModified = file.lastModified();
                    fileLength = file.length();
                    setStatus(SOURCE_OK);
                }
            } else {
                setStatus(SOURCE_IS_NULL);
            }
        } catch (Exception e) {
            Logger.getLogger("org.dianexus.triceps.SourceInfo").log(Level.SEVERE, "", e);
            setStatus(SOURCE_READ_ERROR);
        }
    }

    boolean isReadable() {
        return (status == SOURCE_OK);
    }

    int getError() {
        return status;
    }

    String getSource() {
        return source;
    }

    long getLastModified() {
        return lastModified;
    }

    long getLength() {
        return fileLength;
    }

    boolean equals(SourceInfo o) {
        if (o == null || this.status != SOURCE_OK) {
            return false;
        }

        return (source.equals(o.getSource()) &&
            lastModified == o.getLastModified() &&
            fileLength == o.getLength() &&
            status == o.getError());
    }

    private void setStatus(int st) {
        status = st;

        if (Logger.getLogger("org.dianexus.triceps.SourceInfo").isLoggable(Level.FINER)) {
            if (status != SOURCE_OK) {
                Logger.getLogger("org.dianexus.triceps.SourceInfo").log(Level.FINE, "##SourceInfo.setStatus(" + source + ")->" + STATUS_MSG[status]);
            }
        }
    }
}
		
