# config file variables for unjar.pl
# customized for @@LICENSE.STUDY_NAME@@

JAR = /jdk1.4/bin/jar				# path to jar program (will be different on Unix)

JAR_FILES = /Dialogix/@@LICENSE.PACKAGE_DIR@@/data/completed/*.jar			# path to jar files (e.g. if on a server)
RESULTS_DIR = /Dialogix/@@LICENSE.PACKAGE_DIR@@/data/analysis			# path to where analyses should be put
UNJAR_DIR = /Dialogix/@@LICENSE.PACKAGE_DIR@@/data/unjar				# path where unjared files should be put
UNFINISHED_DIR = /Dialogix/@@LICENSE.PACKAGE_DIR@@/data/working			# path where incomplete files are stored
INSTRUMENT_DIR = /Dialogix/@@LICENSE.PACKAGE_DIR@@/data/instruments		# path where source schedule / instrument stored
PERL_SCRIPTS_PATH = /Dialogix/perl	# path to perl scripts - use . if in same directory as files

UNIQUE_ID = *						#unique identifier
modularizeByPrefix = *
discardVarsMatchingPattern = .	# don't discard anything
NA = . #99999							# values to substitute for reserved words (. means do not substitute)
REFUSED = .	#55555
UNKNOWN = . #33333
HUH = . #22222
INVALID = . #11111
UNASKED = . #44444

SORTBY=sortby_order_asked

# flow control parameters (selects which options in &main to run
sched2sas=1							# post-process instrument file
unjarall=1							# uncompresses all of the .jar files in the directory
moveWorkingFiles=1					# move incomplete (unjarred) data files to appropriate sub directories
removeOldAnalysisFiles=1			# removes any lingering .log files (since dat2sas appends to them)
dat2sas=1							# runs dat2sas on the .dat files in the directory
evt2sas=1							# runs evt2sas.pl