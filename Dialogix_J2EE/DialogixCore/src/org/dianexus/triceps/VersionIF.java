package org.dianexus.triceps;

// FIXME - which of these are used?

import java.io.Serializable;

// FIXME - these should all be configured by container, not at compile-time, so nothing would inherit from VersionIF
public interface VersionIF extends Serializable {

    public final static boolean DEBUG = true;
    public final static boolean AUTHORABLE = true;
    public final static boolean DEPLOYABLE = true;
    public final static boolean WEB_SERVER = true;
    public final static boolean DEMOABLE = (!AUTHORABLE && !DEPLOYABLE);
    public final static boolean DEVELOPERABLE = (AUTHORABLE && DEPLOYABLE);
    public final static String VERSION_MAJOR = "3.0";
    public final static String VERSION_MINOR = "2";
    public final static String VERSION_TYPE = ((DEVELOPERABLE) ? "Development System" : ((AUTHORABLE) ? "Authoring System" : ((DEPLOYABLE) ? "Interviewing System" : "Demo")));
    public final static String VERSION_NAME = "Dialogix " + VERSION_TYPE + " version " + VERSION_MAJOR + "." + VERSION_MINOR;
    public final static String LICENSE_MSG = VERSION_NAME;
    public final static boolean XML = false;
    public final static boolean DISPLAY_WORKING = (!WEB_SERVER);	// controls whether see working files
    public final static boolean DISPLAY_SPLASH = true;	// controls whether see splash screen
    public final static boolean SAVE_ERROR_LOG_WITH_DATA = false;	// (!WEB_SERVER);	// don't save error log file if running on server
    public final static int SESSION_TIMEOUT = ((WEB_SERVER) ? (60 * 30) : (60 * 60 * 12));	// 20 minutes for web server; 12 hours for laptop
    final static boolean DB_FOR_LOGIN = true;
    final static boolean DB_TRACK_LOGINS = true;
    final static boolean DB_LOG_RESULTS = false;
    final static boolean DB_LOG_MINIMAL = false;
    final static boolean DB_LOG_FULL = true;
    final static boolean DB_WRITE_SYSTEM_FILES = false;
}
