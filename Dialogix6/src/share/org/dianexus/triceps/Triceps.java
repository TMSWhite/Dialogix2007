/* ******************************************************** 
 ** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
 ** $Header$
 ******************************************************** */ 

package org.dianexus.triceps;

import java.util.Date;
import java.lang.String;
import java.util.Random;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Hashtable;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.MissingResourceException;
import java.text.SimpleDateFormat;
import java.lang.SecurityException;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

import org.apache.log4j.Logger;

/**
	This is effectively a Context class which links to:
	<ul><li>Schedule (@see Schedule) - the Items (@see Node) for this instrument</li>
	<li>Evidence (@see Evidence) - the data store</li>
	<li>Parser (@see Parser) - the relevance and equation parser</li>
	<li>Locale-specific functionality</li>
	</ul>
*/

public class Triceps implements VersionIF {
  static Logger logger = Logger.getLogger(Triceps.class);

	private static final String DATAFILE_PREFIX = "tri";
	private static final String DATAFILE_SUFFIX = ".dat";
	private static final String EVENTFILE_SUFFIX = ".evt";
	static final String ERRORLOG_SUFFIX = ".log.err";

	/*public*/ static final int ERROR = 1;
	/*public*/ static final int OK = 2;
	/*public*/ static final int AT_END = 3;
	/*public*/ static final int AT_START = 4;
	/*public*/ static final int WORKING_DIR = 1;
	/*public*/ static final int COMPLETED_DIR = 2;
	/*public*/ static final int FLOPPY_DIR = 3;
	/*public*/ static final String SUSPEND_DIR = "suspended/";


	private Schedule schedule = null;
	private Evidence evidence = null;
	private Parser parser = null;
	private Dialogix1TimingCalculator ttc = null;

	private org.dianexus.triceps.Logger errorLogger = null;
	private int currentStep=0;
	private int numQuestions=0;	// so know how many to skip for compount question
	private int firstStep = 0;
	private Date startTime = null;
	private String startTimeStr = null;
	private String stopTimeStr = null;
	private boolean isValid = false;
	private Random random = new Random();
	private String tempPassword = null;
	org.dianexus.triceps.Logger dataLogger = org.dianexus.triceps.Logger.NULL;	
	org.dianexus.triceps.Logger eventLogger = org.dianexus.triceps.Logger.NULL;
	private int displayCount = -1;	// count the number of times data has been sent 
	private String displayCountStr = null;
	private long timeSent = 0;
	private long timeReceived = 0;

	/* formerly from Lingua */
	private static final Locale defaultLocale = Locale.getDefault();
	private ResourceBundle bundle = null;
	private static final String BUNDLE_NAME = "TricepsBundle";
	private Locale locale = defaultLocale;
	private String localeDirectionality = "LTR";
	private Vector currentNodeSet = new Vector();	// starts with zero schedule

	/* Hold on to instances of Date and Number format for fast and easy retrieval */
	private static final Hashtable dateFormats = new Hashtable();
	private static final Hashtable numFormats = new Hashtable();
	private static final String DEFAULT = "null";

  /**
    This NULL context is the default
    XXX:  Can it be removed, making Datum calls require Triceps Context to be passed to them?
  */
	/*public*/ static final Triceps NULL = new Triceps();

  /**
    Create new Context
  */
	public Triceps() {
		this(null,null,null,null);
		isValid = false;
	}

	/**
		Create a new context
		
		@param scheduleLoc The absolute filename of the instrument
		@param workingFilesDir	Where to write the .dat and .dat.evt files
		@param	completedFilesDir	Where to write the completed .jar files
		@param	floppyDir	Where to write the backup .jar files
	*/
	/*public*/ Triceps(String scheduleLoc, String workingFilesDir, String completedFilesDir, String floppyDir) {
		/* initialize required variables */
		timeSent = timeReceived = System.currentTimeMillis();	// gets a sense of the class load time
		parser = new Parser();	
		setLocale(null);	// the default
		errorLogger = new org.dianexus.triceps.Logger();
		if (scheduleLoc != null) {
			createDataLogger(workingFilesDir,null);
		}
		isValid = init(scheduleLoc,workingFilesDir,completedFilesDir,floppyDir,true);
		initDisplayCount();
	}

	/**
		Initializes the context, creating Evidence and loading the Schedule
		@return true if succeeds
		@see Evidence
		@see Schedule
	*/
	private boolean init(String scheduleLoc, String workingFilesDir, String completedFilesDir, String floppyDir,boolean log) {
		evidence = new Evidence(this);
		boolean val = setSchedule(scheduleLoc,workingFilesDir,completedFilesDir,floppyDir,log);
//		if (logger.isDebugEnabled())	showNodes();
		return val;		
	}
	
	/**
		Remove closed data loggers (e.g. if finish instrument and move working files to completed
	*/
	/*public*/ void deleteDataLoggers() {
		dataLogger.delete();
		dataLogger = org.dianexus.triceps.Logger.NULL;
		eventLogger.delete();
		eventLogger = org.dianexus.triceps.Logger.NULL;
	}

	/**
		Close data loggers (e.g. if finish instrument and move working files to completed
	*/
	/*public*/ void closeDataLogger() {
		if (DEPLOYABLE) {
			if (dataLogger != null)
				dataLogger.close();
			if (eventLogger != null)
				eventLogger.close();
		}			
	}

	/**
		Create a new data logger.  This writes the data to the flat files in the working directory
		
		@param dir	the directory
		@param name	the name of the file.
	*/
	/*public*/ void createDataLogger(String dir, String name) {
		if (DEPLOYABLE) {		
			try {
				File tempDataFile = null;
				File tempEventFile = null;

				if (name == null) {
					tempDataFile = EvidenceIO.createTempFile(DATAFILE_PREFIX, DATAFILE_SUFFIX, new File(dir));
					tempEventFile = new File(tempDataFile.toString() + EVENTFILE_SUFFIX);
				}
				else {
					tempDataFile = new File(name);
					tempEventFile = new File(name + EVENTFILE_SUFFIX);
				}
				if (!tempDataFile.toString().equals(dataLogger.getFilename())) {
					dataLogger.delete();
				}
				if (!tempEventFile.toString().equals(eventLogger.getFilename())) {
					eventLogger.delete();
				}

				dataLogger = new org.dianexus.triceps.Logger(org.dianexus.triceps.Logger.UNIX_EOL,true,tempDataFile);
				eventLogger = new org.dianexus.triceps.Logger(org.dianexus.triceps.Logger.UNIX_EOL,true,tempEventFile);
			}
			catch (Exception t) {
				setError("Triceps.createDataLogger()-unable to create temp file" + t.getMessage());
				logger.error("Triceps.createDataLogger()-unable to create temp file" + t.getMessage());
			}
		}	// DEPLOYABLE
		if (dataLogger == null) {
			dataLogger = org.dianexus.triceps.Logger.NULL;
			setError("Triceps.createDataLogger()->writer is null");			
			logger.error("Triceps.createDataLogger()->writer is null");			
		}
		if (eventLogger == null) {
			eventLogger = org.dianexus.triceps.Logger.NULL;
			setError("Triceps.createEventLogger()->writer is null");			
			logger.error("Triceps.createEventLogger()->writer is null");			
		}
		if (DEPLOYABLE) {
			eventLogger.println("**" + VERSION_NAME + " Log file started on " + new Date(System.currentTimeMillis()));
		}			
	}

	/**
		Specify which Instrument (schedule) to run.
		
		@param scheduleLoc The absolute filename of the instrument
		@param workingFilesDir	Where to write the .dat and .dat.evt files
		@param	completedFilesDir	Where to write the completed .jar files
		@param	floppyDir	Where to write the backup .jar files
		@param	log	??? Whether to log the initialization?
		@return	true if instrument loads
	*/
	/*public*/ boolean setSchedule(String scheduleLoc, String workingFilesDir, String completedFilesDir, String floppyDir, boolean log) {
		if (scheduleLoc == null) {
			schedule = Schedule.NULL;
			return false;
		}

		schedule = new Schedule(this, scheduleLoc);
		setLanguage(null);	// the default until overidden

		createTempPassword();

		if (schedule.init(log) && setExpertValues()) {
			schedule.setReserved(Schedule.WORKING_DIR,workingFilesDir);
			schedule.setReserved(Schedule.COMPLETED_DIR,completedFilesDir);
			schedule.setReserved(Schedule.FLOPPY_DIR,floppyDir);
			return true;
		}
		else {
			setError(schedule.getErrors());
			logger.error(schedule.getErrors());
			return false;
		}
	}

	/**
		Not used
	*/	
	void setLoginRecord(LoginTricepsServlet lts, LoginRecord lr) {
		/*
		this.loginTricepsServlet = lts;
		this.loginRecord = lr;
		logger.debug("setLoginRecord(" + loginRecord + "," + loginTricepsServlet + ")");
		 */
	}

	/**
		Not used
	*/
	boolean setStatusCompleted() {
		return false;
		/*		
		if (loginRecord != null && loginTricepsServlet != null) {
			loginRecord.setStatusCompleted();
			return loginTricepsServlet.updateRecord(loginRecord);
		}
		else {
			logger.debug("setStatusCompleted(" + loginRecord + "," + loginTricepsServlet + ")");
			return false;
		}
		 */		
	}

	/** 
		Not used
	 */
	/*public*/ boolean setExpertValues() {
		/* FIXME:
			compare Triceps versions - abort if incompatible
			compare Schedule versions - abort if incompatible
		 */
		return true;
	}

	/**
		@return	whether Triceps Context is valid
	*/
	/*public*/ boolean isValid() { return isValid; }

	/**
		Reload the same instrument, clearing all data.  This is only needed for debugging, so not sure what should happen
		at the database level
	*/
	/*public*/ boolean reloadSchedule() {
		if (AUTHORABLE) {
			Schedule oldSchedule = schedule;
			Evidence oldEvidence = evidence;
			boolean ok = false;

			ok = init(oldSchedule.getReserved(Schedule.SCHEDULE_SOURCE), 
					oldSchedule.getReserved(Schedule.WORKING_DIR), 
					oldSchedule.getReserved(Schedule.COMPLETED_DIR), 
					oldSchedule.getReserved(Schedule.FLOPPY_DIR),
					false);

			if (!ok) {
				setError("Unable to reload schedule");			
				logger.error("Unable to reload schedule");			
				schedule = oldSchedule;
				evidence = oldEvidence;
				return false;
			}

			for (int i=0;i<oldSchedule.size();++i) {
				Node oldNode = oldSchedule.getNode(i);
				Node newNode = evidence.getNode(oldNode);	// get newNode with same name or concept as old ones
				if (newNode != null) {
					evidence.set(newNode, oldEvidence.getDatum(oldNode),oldNode.getTimeStampStr(),false);	// don't record this, since already recorded
				}
			}

			/* data/evidence is loaded from working file; but the schedule are from the schedule source directory */
			schedule.overloadReserved(oldSchedule);
		}
		return true;
	}

	/**
		Get the value of a Node
		@param n	the variable
		@return the value as Datum
	*/
	/*public*/ Datum getDatum(Node n) {
		return evidence.getDatum(n);
	}

	/*public*/ Date getStartTime() {
		return startTime;
	}

	/*public*/ String getStartTimeStr() {
		return startTimeStr;
	}
	/*public*/ String getStopTimeStr() {
		return stopTimeStr;
	}

	/**
		Get any parsing errors from the Instrument as HTML-formatted output
	*/
	/*public*/ String getScheduleErrors() { return schedule.getErrors(); }

	/**
		Get the tailored text of a question for an Item (Node)
		@param q	the Node
		@return the tailored string
	*/
	/*public*/ String getQuestionStr(Node q) {
		/* recompute the min and max ranges, if necessary - must be done before premature abort (if invalid entry)*/

		String s;

		s = q.getMinStr();
		if (s != null) {
			Datum d = parser.parse(this,s);
			q.setMinDatum(d);
		}
		else
			q.setMinDatum(null);

		s = q.getMaxStr();
		if (s != null) {
			Datum d = parser.parse(this,s);
			q.setMaxDatum(d);
		}
		else
			q.setMaxDatum(null);

		Vector v = q.getAllowableValues();
		if (v != null) {
			Vector vd = new Vector();
			for (int i=0;i<v.size();++i) {
				vd.addElement(parser.parse(this,(String) v.elementAt(i)));
			}
			q.setAllowableDatumValues(vd);
		}

		q.setQuestionAsAsked(parser.parseJSP(this, q.getQuestionOrEval()) + q.getSampleInputString());
		return q.getQuestionAsAsked();
	}

	/**
		Navigate to first Item in the instrument
		@return true if successful
	*/
	/*public*/ int gotoFirst() {
		currentStep = 0;
		numQuestions = 0;
		int ok = gotoNext();
		firstStep = currentStep;
		return ok;
	}
	/**
		Navigate to starting step in instrument (may not be first node if some data are prefilled)
		@return true if succesful
	*/
	/*public*/ int gotoStarting() {
//		gotoFirst();	// to set firstStep for determining minimum step number
		currentStep = Integer.parseInt(schedule.getReserved(Schedule.STARTING_STEP));
		if (currentStep < 0)
			currentStep = 0;
		numQuestions = 0;
		return gotoNext();
	}

	/**
		Navigate to the first unasked item
		@return true if successful
	*/
	/*public*/ int jumpToFirstUnasked() {
		int ok=OK;
		Node node;
		Datum datum;
		Enumeration enumeration;

		while(true) {
			enumeration = getQuestions();
			while(enumeration.hasMoreElements()) {
				node = (Node) enumeration.nextElement();
				datum = evidence.getDatum(node);
				if (datum.isUnasked()) {
					return ok;	// break to use this set of questions
				}
			}
			ok = gotoNext();
			if (ok != OK) {
				return ok;
			}
		}
	}

	/**
		Jump to a specific node
		@param val	the name of the node
		@return true if the node exists and can jump to it
	*/
	/*public*/ int gotoNode(Object val) {
		Node n = evidence.getNode(val);
		if (n == null) {
			setError(get("unknown_node") + val.toString());
			logger.error(get("unknown_node") + val.toString());
			return ERROR;
		}
		int result = evidence.getStep(n);
		if (result == -1) {
			setError(get("node_does_not_exist_within_schedule") + n);
			logger.error(get("node_does_not_exist_within_schedule") + n);
			return ERROR;
		} else {
			currentStep = result;
			numQuestions = 0;	// so that selects next group of schedule (and skips over 'e's and not applicables)
			return gotoNext();
//			// will jump to that single node (and not show surrounding issues)
//			currentNodeSet = new Vector();
//			currentNodeSet.addElement(n);
//			numQuestions = 1;
//			return OK;
		}
	}

	/**
		Set the starting time for this instance (usage) of an instrument
		@param time	Now()
	*/
	private void startTimer(Date time) {
		startTime = time;
		startTimeStr = formatDate(startTime,Datum.TIME_MASK);
		schedule.setReserved(Schedule.START_TIME,Long.toString(time.getTime()));	// so that saved schedule knows when it was started
	}

	/**
		Reset the Evidence (dataStore) for the instrument (e.g. clear all values and revert to defaults)
	*/
	/*public*/ void resetEvidence(boolean toUnasked) {
		startTimer(new Date(System.currentTimeMillis()));	// use current time
		evidence.reset();
	}

	/*public*/ void resetEvidence() {
		resetEvidence(true);
	}

	/**
		Store a value to Evidence (and optionally database).<br>
		Each time a screenful of data is collected, each submitted value is validated, and those which pass validation are saved
		
		@param q	the Node (Item)
		@param answer	the answer given (internal coded value)
		@param comment	the optional comment
		@param special	the optional nullFlavor flag
		@param adminMode	whether adminMode is active (needed for validation)
		@return	true if data is successfully stored (e.g. Node must exist)
	*/
	/*public*/ boolean storeValue(Node q, String answer, String comment, String special, boolean adminMode) {
		boolean ok = false;
		Datum d = null;

		if (q == null) {
			setError(get("node_does_not_exist"));
			logger.error(get("node_does_not_exist"));
			return false;
		}

		if (answer == null || answer.trim().equals("")) {
			if (q.getAnswerType() == Node.CHECK) {
				answer = "0";	// unchecked defaults to false
			}
		}

		if (comment != null) {
			q.setComment(comment);
		}

		if (special != null && special.trim().length() > 0) {
			if (q.getAnswerType() == Node.PASSWORD) {
				/* 9/24/2002 - Modify this to prevent bypassing of password fields */
				q.setError(get("passwords_cannot_be_bypassed"));
				return false;
			}
			if (adminMode) {
				d = Datum.parseSpecialType(this, special);
				if (d != null) {
					evidence.set(q,d);
					return true;
				}
				else {
					setError(get("unknown_special_datatype"));
					logger.error(get("unknown_special_datatype"));
					return false;
				}
			}
			else {
				setError(get("entry_into_admin_mode_disallowed"));
				logger.error(get("entry_into_admin_mode_disallowed"));
				return false;
			}
		}

		if (q.getAnswerType() == Node.NOTHING && q.getQuestionOrEvalType() != Node.EVAL) {
			evidence.set(q,new Datum(this, "",Datum.STRING));
			return true;
		}
		else {
			d = new Datum(this, answer,q.getDatumType(),q.getMask()); // use expected value type
			/* check for type error */

			if (!d.exists()) {
				String s = d.getError();
				if (s.length() == 0) {
					q.setError(get("answer_this_question"));	// remove arrow - might bias towards a certain answer
				}
				else {
					q.setError(s);	// remove arrow - might bias answer
				}
				d = Datum.getInstance(this,Datum.UNASKED);
				ok = false;
			}
			else {
				/* check if out of range */
				if (!q.isWithinRange(d)) {
					d = Datum.getInstance(this,Datum.UNASKED);
					ok = false;	// shouldn't wording of error be done here, not in Node?
				}
				else {
					ok = true;
				}
			}
			evidence.set(q,d);
			return ok;			
		}
	}

	/**
		Number of Nodes within the Instrument
	*/
	/*public*/ int size() { return schedule.size(); }

	/**
		Show the value of an Item (Node) showing nullFlavor as blank
		@param n	the Node
		@return the human-readable value, by default showing nullFlavor as blank
	*/
	/*public*/ String toString(Node n) {
		return toString(n,false);
	}

	/**
		Show the value of an Item (Node), optionally spelling out nullFlavor
		@param n	the Node
		@param showReserved	whether to spell out nullFlavor
		@return the human-readable value
	*/
	/*public*/ String toString(Node n, boolean showReserved) {
		Datum d = getDatum(n);
		if (d == null)
			return "null";
		else
			return d.stringVal(showReserved);
	}

	/**
		Does the Node have a value?
	*/
	/*public*/ boolean isSet(Node n) {
		Datum d = getDatum(n);
		if (d == null || d.isType(Datum.UNASKED))
			return false;
		else
			return true;
	}

	/**
		Return Vector of parse Errors in instrument - for debugging
	*/
	/*public*/ Vector collectParseErrors() {
		/* Simply cycle through schedule, processing dependencies & actions */
		Node n = null;
		Vector parseErrors = new Vector();
		if (AUTHORABLE) {
			String dependenciesErrors = null;
			String actionErrors = null;
			String answerChoicesErrors = null;
			String readbackErrors = null;
			String nodeParseErrors = null;
			String nodeNamingErrors = null;
			boolean hasErrors = false;
			int currentLanguage = schedule.getLanguage();

			parser.resetErrorCount();

			for (int i=0;i<size();++i) {
				n = schedule.getNode(i);
				if (n == null)
					continue;

				hasErrors = false;
				dependenciesErrors = null;
				actionErrors = null;
				answerChoicesErrors = null;
				readbackErrors = null;
				nodeParseErrors = null;
				nodeNamingErrors = null;

				parser.booleanVal(this, n.getDependencies());

				if (parser.hasErrors()) {
					hasErrors = true;
					dependenciesErrors = parser.getErrors();
				}

				int actionType = n.getQuestionOrEvalType();
				String s = n.getQuestionOrEval();

				/* Check questionOrEval for syntax errors */
				if (s != null) {
					if (actionType == Node.QUESTION) {
						parser.parseJSP(this, s);
					}
					else if (actionType == Node.EVAL) {
						parser.stringVal(this, s);
					}
				}

				/* Check min & max range delimiters for syntax errors */
				s = n.getMinStr();
				if (s != null) {
					parser.stringVal(this, s);
				}
				s = n.getMaxStr();
				if (s != null) {
					parser.stringVal(this, s);
				}

				if (parser.hasErrors()) {
					hasErrors = true;
					actionErrors = parser.getErrors();
				}

				Vector v = n.getAnswerChoices();
				if (v != null) {
					for (int j=0;j<v.size();++j) {
						AnswerChoice ac = (AnswerChoice) v.elementAt(j);
						if (ac != null)
							ac.parse(this);	// any errors will be associated with the parser, not the node (although this is misleading)
					}
				}

				if (parser.hasErrors()) {
					hasErrors = true;
					answerChoicesErrors = parser.getErrors();
				}

				if (n.hasParseErrors()) {
					hasErrors = true;
					nodeParseErrors = n.getParseErrors();
				}
				if (n.hasNamingErrors()) {
					hasErrors = true;
					nodeNamingErrors = n.getNamingErrors();
				}

				if (n.getReadback(currentLanguage) != null) {
					parser.parseJSP(this,n.getReadback(currentLanguage));
				}
				if (parser.hasErrors()) {
					hasErrors = true;
					readbackErrors = parser.getErrors();
				}

				if (hasErrors) {
					parseErrors.addElement(new ParseError(n, dependenciesErrors, actionErrors, answerChoicesErrors, readbackErrors, nodeParseErrors, nodeNamingErrors));
				}
			}
		}
		return parseErrors;
	}
	
	/**
		Save completed data to a directory
		@param subdir	the directory
		@return	name	Name of saved file if successful, otherwise null
	*/
	/*public*/ String saveCompletedInfo(String subdir) {
		if (DEPLOYABLE) {
			if (dataLogger == org.dianexus.triceps.Logger.NULL || eventLogger == org.dianexus.triceps.Logger.NULL) {
				setError("Triceps.saveCompletedInfo:  data and/or event loggers already closed");			
				logger.error("Triceps.saveCompletedInfo:  data and/or event loggers already closed");			
				return null;	// indicates that info was already logged, or some more fundamental error occurred
			}

			String name = saveAsJar(subdir, schedule.getReserved(Schedule.FILENAME));
			if (name != null) {
				return name;
			}
			setError("Triceps.saveCompletedInfo: unable to saveAsJar");						
			logger.error("Triceps.saveCompletedInfo: unable to saveAsJar");						
			return null;
		}
		return null;		
	}
	
	/**
		Create a subdirectory (usually for saving files)
		@param subdir	the directory to create
		@return true if able to create the directory (or already exists)
	*/
	public boolean createDir(String subdir) {
		if ("".equals(subdir) || ".".equals(subdir)) {
			return true;	// indicates that writing to current directory
		}

		File dir = new File(subdir);
		try {
			if (!dir.isDirectory()) {
				if (dir.isFile()) {
					setError("unable to create directory with the same name as a file: " + subdir);
					logger.error("unable to create directory with the same name as a file: " + subdir);
					return false;
				}
				else {
					if (!dir.mkdir()) {
						setError("unable to create directory " + subdir);
						logger.error("unable to create directory " + subdir);
						return false;
					}
				}
			}
			if (!dir.canWrite()) {
				setError("unable to write to directory " + subdir);
				logger.error("unable to write to directory " + subdir);
				return false;
			}
			if (!dir.canRead()) {
				setError("unable to read from directory " + subdir);
				logger.error("unable to read from directory " + subdir);
				return false;
			}
			return true;

		}
		catch (Exception e) {
			setError("Triceps.testDir:  " + e.getMessage());
			logger.error("Triceps.testDir:  " + e.getMessage());
			return false;
		}
	}

	/**
		Save the Data and Events as a JAR file
		@param subdir	directory to save to
		@param fn	the filename
		@return the name if the file if save is successful, otherwise null
	*/
	private String saveAsJar(String subdir, String fn) {
		if (DEPLOYABLE) {
			/* create jar or zip file of data and events */
			JarWriter jw = null;

			String sourceDir = schedule.getReserved(Schedule.COMPLETED_DIR) + subdir;
			String name = sourceDir + fn + ".jar";

			if (!createDir(sourceDir)) {
				return null;
			}

			jw = JarWriter.getInstance(name);

			if (jw == null)
				return null;

			boolean ok = true;
			ok = jw.addEntry(fn + DATAFILE_SUFFIX, dataLogger.getInputStream());
			ok = jw.addEntry(fn + DATAFILE_SUFFIX + EVENTFILE_SUFFIX, eventLogger.getInputStream()) && ok;
//			if (SAVE_ERROR_LOG_WITH_DATA) {
//				ok = jw.addEntry(fn + ERRORLOG_SUFFIX, org.dianexus.triceps.Logger.getDefaultInputStream()) && ok;		
//			}
			jw.close();

			File f = new File(name);
			if (f.length() == 0L) {
				setError("saveAsJar: file has 0 size");
				logger.error("saveAsJar: file has 0 size");
				ok = false;
			}

			/* integrity check - ensure that JarFile has two entries of proper size */
			ZipFile jf = null;
			File srcFile = null;

			try {
				jf = new ZipFile(name);
				ZipEntry je = null;
				String srcName = null;
				String srcLogger = null;

				for (int i=0;i<2;++i) {
					if (i == 0) {
						srcName = fn + DATAFILE_SUFFIX;
						srcLogger = dataLogger.getFilename();
					}
					else {
						srcName = fn + DATAFILE_SUFFIX + EVENTFILE_SUFFIX;
						srcLogger = eventLogger.getFilename();
					}
					// ignore whether saved Triceps.err.log correctly
					je = jf.getEntry(srcName);
					srcFile = new File(srcLogger);

					if (je.getSize() != srcFile.length()) {
						setError("Error saving data:  " + srcName + "(" + srcFile.getName() + ") has size " + srcFile.length() + ", but copy stored in " + jf.getName() + " has size " + je.getSize());
						logger.error("Error saving data:  " + srcName + "(" + srcFile.getName() + ") has size " + srcFile.length() + ", but copy stored in " + jf.getName() + " has size " + je.getSize());
						ok = false;
					}
				}
			}
			catch (Exception e) {
				setError("##saveAsJar " + e.getMessage());
				logger.error("##saveAsJar " + e.getMessage());
				ok = false;
			}
			if (jf != null) try { jf.close(); } catch (Exception t) { }

			if (!ok) {
				setError("Please try again!");
				logger.error("Please try again!");
			}

			return ((ok) ? name : null);
		}
		return null;		
	}

	/**
		Copy a Jar file of data and events to the backup save directory (floppyDir)
		@param subdir	the directory to which to save
		@return	the filename of the copied file, or null if fails to copy
	*/
	/*public*/ String copyCompletedToFloppy(String subdir) {
		// change this so that copies to a:\suspended directory
		String name = schedule.getReserved(Schedule.FILENAME) + ".jar";
		String sourceDir = schedule.getReserved(Schedule.COMPLETED_DIR) + subdir;
		String floppyDir = schedule.getReserved(Schedule.FLOPPY_DIR) + subdir;

		if (!createDir(sourceDir)) {
			return null;
		}

		if (!createDir(floppyDir)) {
			return null;
		}

		boolean ok = JarWriter.NULL.copyFile(sourceDir + name, floppyDir + name);
		if (JarWriter.NULL.hasErrors()) {
			setError(JarWriter.NULL.getErrors());
			logger.error(JarWriter.NULL.getErrors());
		}
		if (ok)
			return name;
		else {
			setError(get("error_saving_data_to") + floppyDir);
			logger.error(get("error_saving_data_to") + floppyDir);
			return null;
		}
	}

	/**
		Suspend the current state of the instrument to the backup save directory.  
		This is used to let users save partially completed instruments so that they can be later resumed.
		@return the name if the file if save is successful, otherwise null
	*/		
	/*public*/ String suspendToFloppy() {
		String savedName = this.saveCompletedInfo(Triceps.SUSPEND_DIR);
		savedName = this.copyCompletedToFloppy(Triceps.SUSPEND_DIR);
		if (savedName != null) {
			// now delete copy from completed dir so that they don't accumulate?
			String name = schedule.getReserved(Schedule.COMPLETED_DIR) + Triceps.SUSPEND_DIR + schedule.getReserved(Schedule.FILENAME) + ".jar";
			File file = new File(name);
			try {
				file.delete(); 
			}
			catch (Exception e) {
				this.setError("unable to delete " + name + ": " + e.getMessage());
				logger.error("unable to delete " + name + ": " + e.getMessage());
			}				
			return savedName;
		}
		else {
			return null;
		}
	}	

	/**
		Get Title of instrument
	*/
	/*public*/ String getTitle() {
		return schedule.getReserved(Schedule.TITLE);
	}

	/**
		Get the password for adminstator mode, if present
		@return null if no password, else the password
	*/
	/*public*/ String getPasswordForAdminMode() {
		String s = schedule.getReserved(Schedule.PASSWORD_FOR_ADMIN_MODE);
		if (s == null || s.trim().length() == 0)
			return null;
		else
			return s;
	}

	/**
		Get the icon to display at the top left of the page
		@return the filename of the icon
	*/
	/*public*/ String getIcon() { return schedule.getReserved(Schedule.ICON); }
	
	/**
		Get the header message to display atop the instrument pages
	*/
	/*public*/ String getHeaderMsg() { return schedule.getReserved(Schedule.HEADER_MSG); }

	/**
		Parse an expression and return the Datum results.
		This duplicates (?) Parser.parse()
		@param expr	the expression to parse
		@return the Datum value
	*/
	/*public*/ Datum evaluateExpr(String expr) {
		if (AUTHORABLE) {
			return parser.parse(this,expr);
		} else { return null; }
	}
	
	/**
		Get the name of the file to which date are being written
		@return the name of the file
	*/
	/*public*/ String getFilename() { return schedule.getReserved(Schedule.FILENAME); }

	/**
		Change the language for the instrument.  Internally, this will recompute strings which might be affected by the language change
		@param language	the new language code
		@return true if successfully changes language
	*/
	/*public*/ boolean setLanguage(String language) {
		return schedule.setReserved(Schedule.CURRENT_LANGUAGE,language);
	}
	
	/**
		Get the index of the currently used language. Should really log and ISO language code
		@return the index of the language from within the instrument
	*/
	/*public*/ int getLanguage() { return schedule.getLanguage(); }

	/**
		Create a temporay password to enforse single-use tokens when maintaining state
	*/
	/*public*/ String createTempPassword() {
		tempPassword = Long.toString(random.nextLong());
		return tempPassword;
	}

	/** 
		Check whether passwords match to enforse single use tokens when maintining state
		@return true if matches
	*/
	/*public*/ boolean isTempPassword(String s) {
		String temp = tempPassword;
		createTempPassword();	// reset it

		if (s == null)
			return false;
		return s.equals(temp);
	}

	/**
		Log and error message.  This may now be obsolete now that all logging also happens to log4J
		@param s	the message to log
	*/
	/*public*/ void setError(String s) { 
		logger.error(s, new Throwable());
		errorLogger.println(s); 
	}
	
	/**
		Are there any errors?<br>
		FIXME These may be used to display lists of errors to the user, but need to confirm this, and a Visitor class would be more appropriate
	*/
	/*public*/ boolean hasErrors() { return (errorLogger.size() > 0); }
	
	/**
		Return the list of errors.<br>
		FIXME - is HTML formatted.  Should be vector, if at all
	*/
	/*public*/ String getErrors() { return errorLogger.toString(); }

	/**
		Get the active Instrument (Schedule)
	*/
	/*public*/ Schedule getSchedule() { return schedule; }
	
	/**
		Get the active DataStore (Evidence)
	*/
	public Evidence getEvidence() { return evidence; }
	
	/**
		Get the active Parser
	*/
	/*public*/ Parser getParser() { return parser; }

	/**
		Is the user at the beginning of the instrument?
	*/
	/*public*/ boolean isAtBeginning() { return (currentStep <= firstStep); }
	
	/**
		Is the user at the end of the instrument?
	*/
	/*public*/ boolean isAtEnd() { return (currentStep >= size()); }
	
	/**
		What is the current step in the instrument?
	*/
	/*public*/ int getCurrentStep() { return currentStep; }

	/**
		Process the Event Timings collected from the web page.<br>
		These are stored to the .dat.evt file<br>
		They are also logged to the pageHitEvents table
		@param src	the string of events, created by JavaScript on the client side
	*/
	/*public*/ void processEventTimings(String src) {
		logger.debug("in triceps process event timings");
		if (DEPLOYABLE) {		
			if (src == null) {
				return;
			}

			StringTokenizer lines = new StringTokenizer(src,"\t",false);
			StringTokenizer vals = null;
			String line = null;
			String token = null;
			int tokenCount = 0;
			StringBuffer sb = null;

			while(lines.hasMoreTokens()) {
				line = (String) lines.nextToken();
				vals = new StringTokenizer(line,",",true);
				tokenCount = vals.countTokens();
				eventLogger.print(displayCountStr + "\t");

				tokenCount = 0;
				while(vals.hasMoreTokens()) {
					token = (String) vals.nextToken();
					if (tokenCount >= 6) {
						sb = new StringBuffer(token);
						// remaining contents may contain commas, and thus be incorrectly treated as tokens
						// so, merge remaining contents into a single value
						while (vals.hasMoreTokens()) {
							sb.append((String) vals.nextToken());
						}
						token = sb.toString();
					}
					else if (token.equals(",")) {
						eventLogger.print("\t");
						++tokenCount;
						continue;
					}
					if (tokenCount >= 5) {
						eventLogger.print(InputEncoder.encode(token));
					}
					else {
						eventLogger.print(token);
					}
				}
				eventLogger.println("");
			}

			eventLogger.flush();	// so that committed to disk
		}	


	}
	
	/**
		Set the DisplayCount variable to 0.  This should also log to the database?
	*/
	private void initDisplayCount() {
		displayCountStr = schedule.getReserved(Schedule.DISPLAY_COUNT);
		displayCount = 0;
		try {
			displayCount = Integer.parseInt(displayCountStr);
		}
		catch (NumberFormatException e) {
			logger.error("",e);
			displayCount = 0;
		}
		schedule.setReserved(Schedule.DISPLAY_COUNT,Integer.toString(displayCount));
	}

	/**
		Record that the request was sent to the user, logging the timestamp, and incrementing the display counter
	*/
	/*public*/ void sentRequestToUser() {
		logger.debug("in triceps sent request");
		incrementDisplayCount();
		if (DEPLOYABLE) {		
			timeSent = System.currentTimeMillis();
			eventLogger.println(displayCountStr + "\t\t\tsent_request\t" + timeSent + "\t" + (timeSent - timeReceived) + "\t\t");
			eventLogger.flush();	// so that committed to disk
		}		
	}

	/**
		Record that a response was received from the user, including logging the time, and updating certain events.<br>
		This should update some of the database objects.
	*/
	/*public*/ void receivedResponseFromUser() {
		logger.debug("in triceps received response");
		if (DEPLOYABLE) {		
			timeReceived = System.currentTimeMillis();
			eventLogger.println(displayCountStr + "\t\t\treceived_response\t" + timeReceived + "\t" + (timeReceived - timeSent) + "\t\t");
			eventLogger.flush();	// so that committed to disk
		}
	}

	/**
		Increment the display counter so know how many screens full of information the user has seen
	*/
	private void incrementDisplayCount() {
		displayCountStr = Integer.toString(++displayCount);
		schedule.setReserved(Schedule.DISPLAY_COUNT,displayCountStr);	// so that can track the screen count over temporally disjointed sessions
	}

	/**
		Get the current display counter, showing how many pages the user has seen
	*/
	String getDisplayCount() {
		return displayCountStr;
	}

  /**
    Return the desired Locale
    
    @param  lang  the language specifier
    @param  country the country specifier
    @param  extra the dialect specifier
    @return the associated Locale object
  */
	/*public*/ static Locale getLocale(String lang, String country, String extra) {
		return new Locale((lang == null) ? "" : lang,
				(country == null) ? "" : country,
						(extra == null) ? "" : extra);
	}

  /**
    Set the Locale for this context, loading new bundles as needed
    
    @param  loc the Locale
  */
	/*public*/ void setLocale(Locale loc) {
		locale = (loc == null) ? defaultLocale : loc;
		loadBundle();
	}
	
	public String getLocaleDirectionality() {
		return this.localeDirectionality;
	}

  /**
    Load the Locale resources from the properties bundle.  
  */
	private void loadBundle() {
		try {
			bundle = ResourceBundle.getBundle(BUNDLE_NAME,locale);
			if (locale.getLanguage().equals("he") ||
				locale.getLanguage().equals("iw")	||	
				locale.getLanguage().equals("yi")	||	
				locale.getLanguage().equals("ji")	||	
				locale.getLanguage().equals("ar")) {
				this.localeDirectionality = "RTL";
			}
			else {
				this.localeDirectionality = "LTR";
			}
			logger.debug("Locale set to " + locale.getLanguage());
			logger.debug("LocaleDirectionality set to " + localeDirectionality);
		}
		catch (MissingResourceException t) {
			setError("error loading resources '" + BUNDLE_NAME + "': " + t.getMessage());
			logger.error("error loading resources '" + BUNDLE_NAME + "': " + t.getMessage());
		}
	}

  /**
    Get a formated string according the to current Locale
    
    @param s  The message string
    @return The formated result
  */
	public String get(String localizeThis) {
		if (bundle == null || localizeThis == null) {
			return "";
		}
		else {
			String s = null;

			try {
				s = bundle.getString(localizeThis);
			}
			catch (MissingResourceException e) {
				setError("MissingResourceException @ Triceps.get()" + e.getMessage());
				logger.error("MissingResourceException @ Triceps.get()" + e.getMessage());
			}

			if (s == null || s.trim().length() == 0) {
				setError("error accessing resource '" + BUNDLE_NAME + "[" + localizeThis + "]'");
				logger.error("error accessing resource '" + BUNDLE_NAME + "[" + localizeThis + "]'");
				return "";
			}
			else {
				return s;
			}
		}
	}

  /**
    Return the desired DateFormat, keeping a hashtable of them.
    XXX: Doesn't this mean that I'll have multiple copies of the data format in each context?
    
    @param mask The formatting mask
    @return the DataFormat object
  */
	private DateFormat getDateFormat(String mask) {
		String key = locale.toString() + "_" + ((mask == null) ? DEFAULT : mask);

		Object obj = dateFormats.get(key);
		if (obj != null) {
			return (DateFormat) obj;
		}

		DateFormat sdf = null;

		if (mask != null) {
			sdf = new SimpleDateFormat(mask,locale);
		}

		if (sdf == null) {
			Locale.setDefault(locale);
			sdf = new SimpleDateFormat();	// get the default for the locale
			Locale.setDefault(defaultLocale);
		}
		dateFormats.put(key,sdf);
		return sdf;
	}

  /**
    Get DecimalFormat based upon mask, with simple caching
    XXX:  Doesn't this cache it in each Context, taking up extra space?
    
    @param mask the formatting mask
    @return the DecimalFormat
  */
	private DecimalFormat getDecimalFormat(String mask) {
		String key = locale.toString() + "_" + ((mask == null) ? DEFAULT : mask);

		Object obj = numFormats.get(key);
		DecimalFormat df = null;

		if (obj != null) {
			return (DecimalFormat) obj;
		}
		else {
			try {
				if (mask != null) {
					Locale.setDefault(locale);
					df = new DecimalFormat(mask);
					Locale.setDefault(defaultLocale);
				}
			}
			catch (SecurityException e ) {
				logger.error("",e);
			}
			catch (NullPointerException e) {
				logger.error("##error creating DecimalFormat for locale " + locale.toString() + " using mask " + mask,e);
			}
			if (df == null) {
				;	// allow this - will use Double.format() internally
			}
			numFormats.put(key,df);
			return df;
		}
	}

  /**
    Parse an object as number based upon a formatting mask
    
    @param obj  the Datum, Number, or String
    @param  mask  the formatting mask
    @return the Number, or null if invalid
  */
	/*public*/ Number parseNumber(Object obj, String mask) {
		Number num = null;

		if (obj == null || obj instanceof Date) {
			num = null;
		}
		else if (obj instanceof Number) {
			num = (Number) obj;
		}
		else {
			DecimalFormat df;
			String str = (String) obj;
			if (str.trim().length() == 0)
				return null;

			if (mask == null || ((df = getDecimalFormat(mask)) == null)) {
				Double d = null;

				try {
					d = Double.valueOf(str);
				}
				catch (NumberFormatException t) {}
				catch (NullPointerException e) {}
				if (d != null) {
					num = assessDouble(d);
				}
			}
			else {
				try {
					num = df.parse(str);
				}
				catch (java.text.ParseException e) {
					logger.error("",e);
				}
			}
		}

		return num;
	}

  /**
    Parse an object as a Date using a given mask
    
    @param obj  the Datum, Date, or Number
    @param mask the formatting mask
    @return the Date, or null if invalid
  */
	/*public*/ Date parseDate(Object obj, String mask) {
		Date date = null;
		if (obj == null) {
			date = null;
		}
		else if (obj instanceof Date) {
			date = (Date) obj;
		}
		else if (obj instanceof String) {
			String src = (String) obj;
			try {
				if (src.trim().length() > 0) {
					DateFormat df = getDateFormat(mask);
					date = df.parse(src);
				}
				else {
					date = null;
				}
			}
			catch (java.text.ParseException e) {
				logger.error("##Error parsing date " + obj + " with mask " + mask, e);
			}
		}
		else {
			date = null;
		}
		return date;
	}

  /**
    Get Boolean value of an object
    
    @param obj
    @return its Boolean value
  */
	/*public*/ boolean parseBoolean(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (obj instanceof Number) {
			return (((Number) obj).doubleValue() != 0);
		}
		else if (obj instanceof String) {
			return Boolean.valueOf((String) obj).booleanValue();
		}
		else {
			return false;
		}
	}

  /**
    Convert a Double to a Number format
    
    @param  d the Double
    @return the Number equivalent
  */
	private Number assessDouble(Double d) {
		Double nd = new Double((double) d.longValue());
		if (nd.equals(d)) {
			return new Long(d.longValue());
		}
		else {
			return d;
		}
	}

  /**
    Format an object as a number according to a mask
    
    @param  obj The Object, Datum, Boolean, etc.
    @param  mask  the formatting mask
    @return the value as a String
  */
	/*public*/ String formatNumber(Object obj, String mask) {
		String s = null;

		if (obj == null) {
			return null;
		}

		DecimalFormat df;

		if (mask == null || ((df = getDecimalFormat(mask)) == null)) {
			if (obj instanceof Date) {
				s = "**DATE**";		// FIXME
			}
			else if (obj instanceof Long) {
				s = ((Long) obj).toString();
			}
			else if (obj instanceof Boolean) {
				s = ((Boolean) obj).toString();
			}
			else if (obj instanceof Double) {
				Number num = assessDouble((Double) obj);
				s = num.toString();
			}
			else if (obj instanceof Number) {
				s = ((Number) obj).toString();
			}
			else {
				try {
					Double d = Double.valueOf((String) obj);
					if (d == null) {
						s = null;
					}
					else {
						s = d.toString();
					}
				}
				catch(NumberFormatException t) { }
			}
		}
		else {
			try {
				s = df.format(obj);
			}
			catch(IllegalArgumentException e) {
				logger.error("", e);
			}
		}

		return s;
	}
  /**
    Format an object as a Date according to a mask and return the String equivalent
    
    @param  obj the Datum, Date, etc.
    @param  mask  the formatting mask
    @return the String representation
  */
	/*public*/ String formatDate(Object obj, String mask) {
		if (obj == null) {
			return null;
		}

		DateFormat df = getDateFormat(mask);

		try {
			return df.format(obj);
		}
		catch (IllegalArgumentException e) {
			logger.error("", e);
			return null;
		}
	}

	/** 
		Collect next set of Node that might be relevant (collect them first, determine relevance later).
		@return Vector of schedule from instrument
	 */
	private Vector collectNextNodeSet() {
		logger.debug(" in triceps collectNextNodeSet");
		Vector e = collectNextNodeSet1();
//		showVector("collectNextNodeSet1",e);
		if (e == null) {
			return null;	// indicates that at end
		}
		numQuestions = e.size();
		return e;
	}
	
	/**
		Helper function to collect next set of Nodes.  
		Called recursively until either a Vector of schedule is found, or end of instrument is reached.
	*/
	private Vector collectNextNodeSet1() {
		logger.debug(" in triceps collectNextNodeSet1");
		Node node=null;
		int step=0;
		Vector e = new Vector();
		int braceLevel = 0;
		int actionType;

		step = currentStep;	//  + numQuestions;	// skips past current block of questions.  If successful, will update currentStep
		// return null if already at the end, and no elements in vector
		if (step >= size()) {
			return null;
		}
		while(true) {
			if (step >= size()) {	
				// return block if at end, regardless of whether matching braces found
				return e;
			}

			if ((node = schedule.getNode(step)) == null) {
				setError(get("invalid_node_at_step") + step);
				logger.error(get("invalid_node_at_step") + step);
				return e;
			}

			// add the node to the collection
			e.addElement(node);
			logger.debug(" in triceps getNextNodeSEt1 added element");
			actionType = node.getQuestionOrEvalType();			
			if (actionType == Node.GROUP_OPEN) {
				++braceLevel;
			}
			else if (actionType == Node.GROUP_CLOSE) {
				--braceLevel;	// close an open block
			}
			else if (actionType == Node.EVAL) {
				;
			}
			else if (actionType == Node.QUESTION) {
				;
			}
			if (braceLevel == 0) {
				return e;
			}
			// finally, ++ step
			++step;
		}
	}
	
	/**
		Collect previous set of Nodes (if user clicks "previous"), determining relevance later.
	*/
	private Vector collectPreviousNodeSet() {
		Vector e = collectPreviousNodeSet1();
//		showVector("collectPreviousNodeSet1",e);
		if (e == null) {
			return null;
		}
		numQuestions = e.size();
		// reverse order of schedule (since collected backwards!)
		Vector dst = new Vector();
		for (int i=(numQuestions-1);i>=0;--i) {
			dst.addElement(e.elementAt(i));
		}
		return dst;
	}

	/**
		Helper function for collecting previous set of schedule - called recursively until either a Vector of schedule is found,
		or the start of the instrument is reached.
	*/
	private Vector collectPreviousNodeSet1() {
		Node node=null;
		int step = currentStep;
		Vector e = new Vector();
		int braceLevel = 0;
		int actionType;

		while (true) {
			if (--step < firstStep) {	// find previous block of questions.  If successful, will update currentStep
				if (e.size() == 0) {
					return null;	// indicates already at beginning
				}
				else {
					return e;
				}
			}

			if ((node = schedule.getNode(step)) == null) {
				setError(get("invalid_node_at_step") + step);
				logger.error(get("invalid_node_at_step") + step);
				return e;
			}

			// add the node to the collection
			e.addElement(node);

			actionType = node.getQuestionOrEvalType();			
			if (actionType == Node.EVAL) {
				;	// skip these going backwards, but don't reset values when going backwards
			}
			else if (actionType == Node.GROUP_CLOSE) {
				--braceLevel;
			}
			else if (actionType == Node.GROUP_OPEN) {
				++braceLevel;
			}
			else if (actionType == Node.QUESTION) {
				;
			}
			else {
				node.setError(get("invalid_action_type"));
				return e;
			}
			if (braceLevel == 0) {
				return e;
			}
		}
	}

	/**
		Determine which subset of a Vector of Nodes is relevant.  Any non-relevant Nodes are flagged as N/A in data file
		If no schedule are relevant, system keeps calling collectNext or collectPrevious until some found or start/end of instrument found
		@param src	the set of candidates schedule
	*/ 
	private Vector getRelevantNodes(Vector src) {
		Node node;
		Vector dst = new Vector();
		if (src == null) {
			return null;	// should never happen
		}
		for (int i=0;i<src.size();++i) {
			node = (Node) src.elementAt(i);
			if (parser.booleanVal(this, node.getDependencies())) {
				dst.addElement(node);
			}
			else {
				evidence.set(node, Datum.getInstance(this,Datum.NA));	// if doesn't satisfy dependencies, store NA
			}
		}
		return dst;
	}

	/** 
		Checks whether there are errors in a collected block of schedule 
	*/
	private boolean isBlockOK(Vector v) {
		int braceLevel = 0;

		if (v == null) {
			return false;	// should not be called, since null means that at beginning or end
		}

		int size = v.size();
		Node node = null;
		int actionType = 0;

		if (size == 0) {
			return false;	// should not be zero sized
		}
		for (int i=0;i<size;++i) {
			node = (Node) v.elementAt(i);
			actionType = node.getQuestionOrEvalType();

			if (actionType == Node.GROUP_OPEN) {
				++braceLevel;
			}
			else if (actionType == Node.GROUP_CLOSE) {
				--braceLevel;
			}

			if (size == 1 && i == 0) {
				if (!(actionType == Node.EVAL || actionType == Node.QUESTION)) {
					setError("invalid block of schedule");	// FIXME -- need better error, and translation file
					logger.error("invalid block of schedule");	// FIXME -- need better error, and translation file
					return false;
				}
				return true;	// block contains single item - an 'e' or a 'q'
			}
			if (i == 0) {
				if (actionType != Node.GROUP_OPEN) {
					setError("first node in a block must be '['");	// FIXME -- need better error, and translation file
					logger.error("first node in a block must be '['");	// FIXME -- need better error, and translation file
					return false;
				}
			} 
			else if (i == (size-1)) {
				if (actionType != Node.GROUP_CLOSE) {
					setError("last node in block must be ']'");	// FIXME -- need better error, and translation file
					logger.error("last node in block must be ']'");	// FIXME -- need better error, and translation file
					return false;
				}
			}
			else {
				if (actionType == Node.EVAL) {
					node.setError(get("evals_disallowed_within_question_block"));	// and don't add it to the collection
					evidence.set(node, Datum.getInstance(this,Datum.INVALID));	// evals can't be embedded in a block, so mark as INVALID
					return false;
				}
				else if (actionType == Node.GROUP_OPEN) {
					node.setError(get("extra_opening_brace"));
					return false;
				}
				else if (actionType == Node.GROUP_CLOSE){
					node.setError(get("extra_closing_brace"));
					return false;
				}
			}
		}
		if (braceLevel > 0) {
			setError(get("missing") + braceLevel + get("closing_braces"));
			logger.error(get("missing") + braceLevel + get("closing_braces"));
			return false;
		}
		else if (braceLevel < 0) {
			setError(get("missing") + braceLevel + get("opening_braces"));
			logger.error(get("missing") + braceLevel + get("opening_braces"));
			return false;
		}
		return true;
	}

	/**
		Navigate to next set of relevant schedule, if any.  If errors, return ERROR.  If at end, return AT_END
		@return ERROR, AT_END, or OK
	*/
	/*public*/ int gotoNext() {
//		showVector("gotoNext@start",currentNodeSet);
		logger.debug("in triceps gotonext");
		int old_step = currentStep;	// so know where started
		Vector old_nodeSet = currentNodeSet;
		int old_numQuestions = numQuestions;
		int ans = gotoNext1();
		logger.debug("in triceps gotonext returned from gotonext1");
		if (ans == AT_END) {
			numQuestions = 0;
			currentNodeSet = new Vector();
			if (old_step >= currentStep) {
				setError(get("already_at_end_of_interview"));	
				logger.error(get("already_at_end_of_interview"));	
			}
		}
		if (ans == ERROR) {
			currentStep = old_step;
			numQuestions = old_numQuestions;
			currentNodeSet = old_nodeSet;
			setError("invalid block of schedule");	// FIXME -- need better error, and translation file
			logger.error("invalid block of schedule");	// FIXME -- need better error, and translation file
		}

		schedule.setReserved(Schedule.STARTING_STEP,Integer.toString(currentStep));
		dataLogger.flush();	
//		showVector("gotoNext@end",currentNodeSet);
		logger.debug("in triceps gotonext returning");
		return ans;		
	}

	/**
		Helper for gotoNext() which is called recursively until the next set of available schedule (if any) are found
		@return ERROR, AT_END, or OK
	*/
	private int gotoNext1() {
		logger.debug("in triceps gotonext1");
		Vector e = null;
		currentStep += numQuestions;	// jump over the currently active block of questions (since know they are valid)
		e = collectNextNodeSet();
		if (e == null) {
			return AT_END;
		}

		if (!isBlockOK(e)) {
			return ERROR;
		}

		e = getRelevantNodes(e);	// will mark as NA those embedded which are not relevant; and will set numQuestions
		currentNodeSet = e;	// store for getQuestions()? -- so that don't recalculate each step
//		showVector("getNextRelevant",currentNodeSet);
		logger.debug("in triceps gotonext1 got relevant schedule");
		if (e.size() == 0) {
			// then no relevent in this block
			return gotoNext1();
		}
		if (e.size() == 1) {
			Node node;
			node = (Node) e.elementAt(0);
			if (node.getQuestionOrEvalType() == Node.EVAL) {
				Datum datum = parser.parse(this, node.getQuestionOrEval());
				int type = node.getDatumType();
				if (type != Datum.STRING && type != datum.type()) {
					datum = datum.cast(type,null);
				}
				evidence.set(node, datum);
				logger.debug("in triceps gotonext1 about to recurse");
				return gotoNext1();	// since want to find next non-eval node -- FIXME -- what happens if last node in instrument is eval?
			}
		}
		return OK;
	}

	/**
		Goto prior set of relevant schedule, if any
		@return ERROR, AT_START, or OK
	*/
	/*public*/ int gotoPrevious() {
//		showVector("gotoPrevious@start",currentNodeSet);
		int old_step = currentStep;
		Vector old_nodeSet = currentNodeSet;
		int old_numQuestions = numQuestions;
		int ans = gotoPrevious1();

		if (ans != OK) {
			currentStep = old_step;
			numQuestions = old_numQuestions;
			currentNodeSet = old_nodeSet;
			if (ans == AT_START) {
				setError(get("already_at_beginning"));
				logger.error(get("already_at_beginning"));
			}
		}
		schedule.setReserved(Schedule.STARTING_STEP,Integer.toString(currentStep));
		dataLogger.flush();			

//		showVector("gotoPrevious@end",currentNodeSet);
		return ans;		
	}

	/**
		Helper class for gotoPrevious() which is called recursively until any relevant schedule are found
		@return ERROR, AT_START, or OK
	*/
	private int gotoPrevious1() {
		Vector e = null;

		e = collectPreviousNodeSet();
		if (e == null) {
			return AT_START;
		}

		if (!isBlockOK(e)) {
			return ERROR;
		}

		currentStep -= numQuestions;	// set here, after detecting potential errors

		e = getRelevantNodes(e);	// will mark as NA those embedded which are not relevant; and will set numQuestions
		currentNodeSet = e;	// store for getQuestions()? -- so that don't recalculate each step
//		showVector("gotoPreviousRelevant",currentNodeSet);

		if (e.size() == 0) {
			// then no relevent in this block
			return gotoPrevious1();
		}
		if (e.size() == 1) {
			Node node = (Node) e.elementAt(0);
			if (node.getQuestionOrEvalType() == Node.EVAL) {
				// ignore it going backwards (or undo, once supported)
				return gotoPrevious1();
			}
		}
		return OK;		
	}

	/**
		Get Enumeration of Locale tailored questions
	*/
	/*public*/ Enumeration getQuestions() {
		logger.debug(" in triceps getQuestions");
		Enumeration enumeration = currentNodeSet.elements();
		// set default language for them (how many times should this be done?)
		int lang = getLanguage();
		while (enumeration.hasMoreElements()) {
			Node node = (Node) enumeration.nextElement();
			node.setAnswerLanguageNum(lang);
			logger.debug(" in triceps getQuestions in while loop got node"+node.getQuestionOrEval());
		}
		return currentNodeSet.elements();
	}

	/**
		Graceful shutdown of data loggers
	*/
	void shutdown() {
		closeDataLogger();
	}

	public boolean existsTtc() {
		return (this.ttc != null);
	}

    public Dialogix1TimingCalculator getTtc() {
		if(this.ttc==null){
			this.ttc = new Dialogix1TimingCalculator();
		}
		return this.ttc;
	}

	public void setTtc(Dialogix1TimingCalculator ttc) {
		this.ttc = ttc;
	}
}


