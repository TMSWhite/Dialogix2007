/* ******************************************************** 
** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
** $Header$
******************************************************** */ 

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
import org.apache.log4j.Logger;

/*public*/ final class ScheduleList implements VersionIF {
  static Logger logger = Logger.getLogger(ScheduleList.class);
	private Vector schedules = new Vector();
	private String sourceDir = null;
	private org.dianexus.triceps.DialogixLogger oldlogger = new org.dianexus.triceps.DialogixLogger();
	private Triceps triceps = Triceps.NULL;

    /*public*/ ScheduleList(Triceps lang, String sourceDir, boolean isSuspended) {
     	triceps = (lang == null) ? Triceps.NULL : lang;
	   	this.sourceDir = sourceDir;
	    File dir = new File(sourceDir);

	    if (!dir.isDirectory()) {
	    	setError(sourceDir + triceps.get("is_not_a_directory"));
	    	logger.error(sourceDir + triceps.get("is_not_a_directory"));
	    	return;
	    }
	    else if (!dir.canRead()) {
	    	setError(sourceDir + triceps.get("is_not_accessible"));
	    	logger.error(sourceDir + triceps.get("is_not_accessible"));
	    	return;
	    }
	    
	    if (isSuspended) {
	    	unjarSuspendedInterviews(dir);
	    }

		String[] files = dir.list();

		int count=0;
		for (int i=0;i<files.length;++i) {
			File f = new File(dir.toString() + "/" + files[i]);
			if (!f.isDirectory()) {
				Schedule schedule = new Schedule(triceps, f.toString());
				if (schedule.isFound()) {
					schedules.addElement(schedule);
					++count;
				}
				else {
					schedule.getErrors();	// clear the schedule's errors so that don't report names of files not accessible to user
				}
			}
		}
		triceps.setLocale(null);	// set it to the default
    }
    
    private void unjarSuspendedInterviews(File dir) {
		String[] files = dir.list();

		int count=0;
		for (int i=0;i<files.length;++i) {
			File f = new File(dir.toString() + "/" + files[i]);
			try {
				if (f.getName().toLowerCase().endsWith(".jar") && f.canRead()) {
					// unjar file and remove it
					unjar(f);
					f.delete();	// delete regardless of whether successfully unjared
				}
			}
			catch (Exception e) {
				setError("unjarSuspendedInterviews: " + e.getMessage());
				logger.error("", e);
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
				logger.error("", e);		
    	}
    	try {
    		br.close();
    		br2.close();
    	}
    	catch (Exception e) { logger.error("", e); }
    	
    	if (!foundLast) {
    		// append to file
    		BufferedInputStream bis = null;
    		try {
				bis = new BufferedInputStream(new FileInputStream(file));
				byte[] buf = new byte[1000];
				int bytesRead = 0;
				Date now = new Date(System.currentTimeMillis());
				
				logger.debug("<!-- START COPYING LOG FILE CONTENTS FROM '" + file.getName() + "' [" + now + "] -->");
				while((bytesRead = bis.read(buf)) != -1) {
					logger.debug(new String(buf,0,bytesRead));
				}    			
				logger.debug("<!-- END COPYING LOG FILE CONTENTS FROM '" + file.getName() + "' [" + now + "] -->");				
			}
			catch (Exception e) {
				logger.error(file.getName(), e);		
			}
			try {
				bis.close();
			}
			catch (Exception e) { logger.error("", e); }
    	}
    	
    	try {
    		file.delete();	// remove the log file from working directory when done
    	}
    	catch (Exception e) { logger.error("", e); }
    }
*/
    
    private boolean unjar(File file) {
		ZipFile jf = null;
		boolean ok = true;
		
		try {
			jf = new ZipFile(file);
			Enumeration entries = jf.entries();
			
			while(entries.hasMoreElements()) {
				if (!saveUnjaredFile(jf, (ZipEntry) entries.nextElement())) {
					ok = false;
					break;
				}
			}
		}
		catch (Exception e) {
			setError("##unjar " + e.getMessage());
			logger.error("", e);
			ok = false;
		}
		if (jf != null) try { jf.close(); } catch (Exception t) { logger.error("", t); }
		return ok;
	}
	
	private boolean saveUnjaredFile(ZipFile jf, ZipEntry je) {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		File file = null;
		boolean ok = true;
		byte[] buf = new byte[1000];
		int bytesRead = 0;
		String name=null;
				
		try {
			name = je.getName();
			file = new File(sourceDir + name);
			if (file.exists()) {
				if (file.length() > je.getSize()) {
					File jarFile = new File(jf.getName());
					String jarFileName = jarFile.getName();
					setError("Unable to restore (" + jarFileName + "): existing file (" + file.getName() + ") is larger than the one you are trying to retore, so it may be more recent.");
					logger.error("Unable to restore (" + jarFileName + "): existing file (" + file.getName() + ") is larger than the one you are trying to retore, so it may be more recent.");
					String baseName = file.toString();
					baseName = baseName.substring(0,baseName.indexOf("."));
					setError("If you really want to overwrite the existing files, you must manually delete them (" + baseName + ".*) before restoring (" + jarFileName + ").");
					logger.error("If you really want to overwrite the existing files, you must manually delete them (" + baseName + ".*) before restoring (" + jarFileName + ").");
				}
				return false;
			}
			
			bis = new BufferedInputStream(jf.getInputStream(je));
			fos = new FileOutputStream(file);
			
			while((bytesRead = bis.read(buf)) != -1) {
				fos.write(buf,0,bytesRead);
			}
		}
		catch (Exception e) {
			setError("##saveUnjaredFile: " + e.getMessage());
			logger.error("", e);
			ok = false;
		}
		if (bis != null) {
			try { bis.close(); } catch (IOException t) { logger.error("", t); }
		}
		if (fos != null) {
			try { fos.close(); } catch (Exception e) {
				setError("saveUnjaredFile " + file.toString() + ": " + e.getMessage());	
				logger.error("", e);
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
    logger.error(s, new Throwable());
		oldlogger.println(s);
	}

	/*public*/ boolean hasErrors() { return (oldlogger.size() > 0); }
	/*public*/ String getErrors() { return oldlogger.toString(); }

    /*public*/ Vector getSchedules() { return schedules; }

}
