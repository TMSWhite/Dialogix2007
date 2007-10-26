
-- CREATE DATABASE, USERS, AND PERMISSIONS FOR THIS DOMAIN
--

DROP DATABASE dialogix5;
CREATE DATABASE dialogix5 DEFAULT CHARSET=utf8;
USE dialogix5;

DROP USER 'dialogix5'@'localhost';
CREATE USER 'dialogix5'@'localhost' IDENTIFIED BY 'dialogix5_pass';
GRANT USAGE ON * . * TO 'dialogix5'@'localhost' IDENTIFIED BY 'dialogix5_pass' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;
GRANT ALL PRIVILEGES ON dialogix5 . * TO 'dialogix5'@'localhost';
FLUSH PRIVILEGES ;


--
-- PRIMARY KEY GENERATOR TABLE
--

CREATE TABLE SEQUENCE_GENERATOR_TABLE (
  SEQUENCE_NAME VARCHAR(80) NOT NULL,
  SEQUENCE_VALUE INT(15) NOT NULL,
	PRIMARY KEY PK_SEQUENCE_NAME (SEQUENCE_NAME)
)ENGINE=InnoDB;



--
-- GLOBAL DEFINITION OF INSTRUMENTS
--

CREATE TABLE Instrument (
	Instrument_ID	int(15)	NOT NULL auto_increment,
	InstrumentName	varchar(200) NOT NULL,
	InstrumentDescription	mediumtext default '',
	
  KEY k1_Instrument (InstrumentName),
  PRIMARY KEY pk_Instrument (Instrument_ID)
) ENGINE=InnoDB;	

CREATE TABLE Instrument_Version (
  InstrumentVersion_ID int(15) NOT NULL auto_increment,
  Instrument_ID int(15) NOT NULL,
  InstrumentHash_ID int(15) NOT NULL,
  VersionString varchar(20) NOT NULL,
  InstrumentNotes text default '',
  InstrumentStatus int default NULL,	-- e.g. (draft, deployed, deprecated -- need enumerated list?)
  CreationTimeStamp timestamp NOT NULL default CURRENT_TIMESTAMP,	
  
  -- probably need other metadata - as joined table?
  
  hasLOINCcode boolean default false,	-- whether there is an official LOINC code for this instrument
  LOINC_NUM varchar(10) default NULL,	-- will be provided by LOINC if coding accepted

--  UNIQUE uni_InstrumentVersion (Instrument_ID, InstrumentHash_ID, VersionString),	-- to enforce instrument uniqueness?
  PRIMARY KEY pk_InstrumentVersion (InstrumentVersion_ID)
) ENGINE=InnoDB;


CREATE TABLE Language_List (
  LanguageList_ID int(15) NOT NULL auto_increment,
  LanguageList text NOT NULL,

  -- Avoid UNIQUE?  Length too restrictive
  PRIMARY KEY pk_LanguageList (LanguageList_ID)
) ENGINE=InnoDB;

CREATE TABLE Instrument_Hash (
	InstrumentHash_ID int(15) NOT NULL auto_increment,
	LanguageList_ID int(15) NOT NULL,
  NumVars int(11) NOT NULL default '0',
  VarListMD5 varchar(35) NOT NULL default '',
  InstrumentMD5 varchar(35) NOT NULL default '',
  NumLanguages int(11) NOT NULL default '0',
  NumInstructions int(11) NOT NULL default '0',
  NumEquations int(11) NOT NULL default '0',
  NumQuestions int(11) NOT NULL default '0',
  NumBranches int(11) NOT NULL default '0',
  NumTailorings int(11) NOT NULL default '0',

--  UNIQUE uni_InstrumentHash, (NumVars, VarListMD5, InstrumentMD5, LanguageList_ID, NumLanguages)
  PRIMARY KEY pk_InstrumentHash (InstrumentHash_ID)
) ENGINE=InnoDB;	

CREATE TABLE Var_Name (
	VarName_ID int(15) NOT NULL auto_increment,
	VarName varchar(100) NOT NULL,

--  UNIQUE uni_VarName (VarName),
  PRIMARY KEY pk_VarName (VarName_ID)
) ENGINE=InnoDB;	
 
CREATE TABLE Display_Type (
 	DisplayType_ID int(15) NOT NULL,
	DisplayType varchar(100) NOT NULL,
	DataType_ID int(15) NOT NULL,	-- default DataType for this DisplayType
	HasAnswerList boolean default false,
  SPSSformat varchar(25) NOT NULL,
  SASinformat varchar(25) NOT NULL,
  SASformat varchar(25) NOT NULL,		
  SPSSlevel varchar(20) NOT NULL,
  LOINCscale varchar(10) NOT NULL,

--  UNIQUE uni_DisplayType (DisplayType),
  PRIMARY KEY pk_DisplayType (DisplayType_ID)
) ENGINE=InnoDB;	

-- SAS and SPSS Formats are hints based upon data type - users can override them
CREATE TABLE Data_Type (
 	DataType_ID int(15) NOT NULL auto_increment,
	DataType varchar(100) NOT NULL,

--  UNIQUE uni_DataType (DataType),
  PRIMARY KEY pk_DataType (DataType_ID)
) ENGINE=InnoDB;	

--
-- Where do I put nesting level?
-- How do I include Sections within Sections?  What attributes do they have?
-- I would like Instruments to contain Sections, which can be Sections or Items, and unique ordering within each section and subsection level
-- 

CREATE TABLE Instrument_Content (
	InstrumentContent_ID	int(15)	NOT NULL auto_increment,
	InstrumentVersion_ID int(15) NOT NULL,
	Item_ID int(15) NOT NULL,
	VarName_ID int(15) NOT NULL,
	Item_Sequence int(11) NOT NULL,	-- should be enforced as 1-N?
	Help_ID int(15) NOT NULL,	-- only applys to Items
	
	DisplayType_ID int(15) NOT NULL,	-- hint, like list, radio, etc.
	
  isRequired int(11) NOT NULL default '0',	-- whether user must answer this before submit
  isReadOnly int(11) NOT NULL default '0',	-- display without allowing change
  DisplayName text default '',	-- optional local display name
  GroupNum int NOT NULL default '0',	-- calculated from matching braces
  Relevance text NOT NULL,	-- Boolean - whether question should be asked
  ActionType ENUM ('q', 'e', '[', ']'),
  FormatMask text,	
  
  isMessage int(11) NOT NULL default '0',	-- derived from Item - question but no answer
  DefaultAnswer text,
  
  -- These formats, by default, are derived from DataType class, but can be overridden
  SPSSformat varchar(25) default NULL,
  SASinformat varchar(25) default NULL,
  SASformat varchar(25) default NULL,	  
  
--	UNIQUE uni1_InstrumentContent (InstrumentVersion_ID, Item_ID, VarName_ID),
--	UNIQUE uni2_InstrumentContent (InstrumentVersion_ID, Item_Sequence),
  PRIMARY KEY pk_InstrumentContent (InstrumentContent_ID)
) ENGINE=InnoDB;

-- Can I Categorize Reserved Words based upon:
-- (1) Global Variables -- e.g. headers, titles, turn actions on/off which affect navigation and/or layout
-- (2) Other?

CREATE TABLE Reserved_Word (
	ReservedWord_ID int(15) NOT NULL auto_increment,
	ReservedWord varchar(100) NOT NULL,
	Meaning text,
	
--  UNIQUE uni_ReservedWord (ReservedWord)
  PRIMARY KEY pk_ReservedWord (ReservedWord_ID)
) ENGINE=InnoDB;	

CREATE TABLE Instrument_Header (
  InstrumentHeader_ID int(15) NOT NULL auto_increment,
  InstrumentVersion_ID int(15) NOT NULL,
  ReservedWord_ID int(15) NOT NULL,
  Value text NOT NULL,
  
  --  UNIQUE uni_InstrumentHeader (InstrumentVersion_ID, ReservedWord_ID),
	PRIMARY KEY pk_InstrumentHeader (InstrumentHeader_ID)
) ENGINE=InnoDB;

--
-- At what level should translations be specified?
-- Same string can be used for Question or Answer across instruments, but with different Question and Answer ID
-- This avoids assumption that same string has same meaning in different contexts, and allows instrument-specific edits
-- Simple solution for editting is to give new IDs each time upload new instrument, whether in dev or production
-- Rather than looking for String identity (which Mysql cannot do anyway for > 999 bytes)
--
CREATE TABLE Question (
	Question_ID int(15) NOT NULL auto_increment,

  PRIMARY KEY pk_Question (Question_ID)
) ENGINE=InnoDB;

CREATE TABLE Answer (
	Answer_ID int(15) NOT NULL auto_increment,
	hasLAcode boolean default false, -- whether there is a LOINC LA code for this answer
	LAcode varchar(20) default '',	-- the LA code, if present.  This is needed for HL7 2.5 and 3.0
 
  PRIMARY KEY pk_Answer (Answer_ID)
) ENGINE=InnoDB;

CREATE TABLE Help (
	Help_ID int(15) NOT NULL auto_increment,

  PRIMARY KEY pk_Help (Help_ID)
) ENGINE=InnoDB;

CREATE TABLE Question_Localized (
  QuestionLocalized_ID int(15) NOT NULL auto_increment,
  Question_ID int(15) NOT NULL,
  LanguageCode char(2) NOT NULL default 'en',
  QuestionString text,
  
 	KEY k1_QuestionLocalized (LanguageCode),
	KEY k2_QuestionLocalized (QuestionString(400)),
  PRIMARY KEY pk_QuestionLocalized (QuestionLocalized_ID)
) ENGINE=InnoDB;

CREATE TABLE Answer_Localized (
  AnswerLocalized_ID int(15) NOT NULL auto_increment,
  Answer_ID int(15) NOT NULL,
  LanguageCode char(2) NOT NULL default 'en',
  AnswerString text,
  
  KEY k1_AnswerLocalized (LanguageCode),
	KEY k2_AnswerLocalized (AnswerString(400)),
  PRIMARY KEY pk_AnswerLocalized (AnswerLocalized_ID)
) ENGINE=InnoDB;

CREATE TABLE Help_Localized (
  HelpLocalized_ID int(15) NOT NULL auto_increment,
  Help_ID int(15) NOT NULL,
  LanguageCode char(2) NOT NULL default 'en',
  HelpString text,
  
  KEY k1_HelpLocalized (LanguageCode),
	KEY k2_HelpLocalized (HelpString(400)),  
  PRIMARY KEY pk_HelpLocalized (HelpLocalized_ID)
) ENGINE=InnoDB;

CREATE TABLE Readback (
	Readback_ID int(15) NOT NULL auto_increment,

  PRIMARY KEY pk_Readback (Readback_ID)
) ENGINE=InnoDB;

CREATE TABLE Readback_Localized (
  ReadbackLocalized_ID int(15) NOT NULL auto_increment,
  Readback_ID int(15) NOT NULL,
  LanguageCode char(2) NOT NULL default 'en',
  ReadbackString text,
  
  KEY k1_ReadbackLocalized (LanguageCode),
	KEY k2_ReadbackLocalized (ReadbackString(400)),  
  PRIMARY KEY pk_ReadbackLocalized (ReadbackLocalized_ID)
) ENGINE=InnoDB;

--
-- Does it make sense to have normalized validation table?
-- It might help detection of whether items have changed validation criteria
--
CREATE TABLE Validation (
	Validation_ID	int(15)	NOT NULL auto_increment,
  MinVal varchar(75) default '',
  MaxVal varchar(75) default '',
  OtherVals varchar(100) default '',
  InputMask varchar(75) default '',	

--	UNIQUE uni_Validation (MinVal, MaxVal, OtherVals, InputMask),
	PRIMARY KEY pk_Validation (Validation_ID)
) ENGINE=InnoDB;

--
-- It is not safe to make this Unique.  Risk is that Item in different context may have different meaning
--

CREATE TABLE Item (
	Item_ID	int(15)	NOT NULL auto_increment,
	Question_ID	int(15)	NOT NULL,	-- this also points to equations, if ItemType is Equation
	Readback_ID	int(15)	NOT NULL,	-- this also points to equations, if ItemType is Equation
	DataType_ID int(15) NOT NULL,	
	AnswerList_ID	int(15)	NOT NULL,	-- in case an enumerated list of code set
	Validation_ID int(15) NOT NULL,
 	ItemType ENUM( 'Question', 'Equation' ) NOT NULL,
 	Concept text default '',
 	
  hasLOINCcode boolean default false,	-- whether there is an official LOINC code for this instrument
  LOINC_NUM varchar(10) default NULL,	-- will be provided by LOINC if coding accepted
	
--  UNIQUE (Question_ID, DataType_ID, AnswerList_ID, Validation_ID),
  PRIMARY KEY pk_Item (Item_ID)
) ENGINE=InnoDB;

CREATE TABLE Answer_List (
	AnswerList_ID	int(15)	NOT NULL auto_increment,
	Description text,	-- add other metadata, like ISO 11179?

  PRIMARY KEY pk_AnswerList (AnswerList_ID)
) ENGINE=InnoDB;

-- 
-- Want to extend this to use Code Sets
-- Should DataType be pushed down into AnswerList?
--

CREATE TABLE Answer_List_Content (
	AnswerListContent_ID	int(15)	NOT NULL auto_increment,
	AnswerList_ID int(15) NOT NULL,
	Answer_ID int(15) NOT NULL,
	Answer_Order int(15) NOT NULL,
	Value varchar(100) NOT NULL,	-- what if need longer value?

--	UNIQUE uni_AnswerListContent (AnswerList_ID, Answer_ID, Answer_Order, Value),
  PRIMARY KEY pk_AnswerListContent (AnswerListContent_ID)
) ENGINE=InnoDB;

--
-- Should there be a table of ActionTypes?
-- Next, Previous, Load, Save, Suspend, JumpTo, ShowSyntaxErrors, 
-- ToggleDeveloperMode, ToggleDebugMode, ChangeLanguage, etc.
-- This could be used for Status Messages?
--
CREATE TABLE Action_Type (
  ActionType_ID int(15) NOT NULL auto_increment,
  ActionName varchar(50) NOT NULL,	-- should this be internationalized?

	PRIMARY KEY pk_ActionType (ActionType_ID)
) ENGINE=InnoDB;

CREATE TABLE Null_Flavor (
  NullFlavor_ID int(15) NOT NULL auto_increment,
  NullFlavor varchar(100) NOT NULL,
	DisplayName varchar(100) NOT NULL,
	Description text,

--  UNIQUE uni_NullFlavor (NullFlavor),
  PRIMARY KEY pk_NullFlavor (NullFlavor_ID)
) ENGINE=InnoDB;

-- 
-- Functions - used by parser. May be able to remove them from the 
-- Java class and create Velocity templates for them, thereby allowing 
-- for easier expansion of functionality
--

CREATE TABLE Function_Name (
	FunctionName_ID int(15) NOT NULL auto_increment,
	Name varchar(30) NOT NULL, -- name used within function lookups
	Syntax text NOT NULL, -- specifies required input parameters
	Description text NOT NULL, -- narrative description of how to use it (in JavaDoc format?)
	Definition text NOT NULL, -- Velocity (or other) template to parse and process the input variables

--	UNIQUE uni_FunctionName (Name)
	PRIMARY KEY pk_FunctionName (FunctionName_ID)
) ENGINE=InnoDB;


--
-- DATA COLLECTED USING INSTRUMENTS
--

--
-- Could consider collection of DataElement Elements like this
-- Might facilitate identification of Dirty data which needs to be written
-- These get written to Horizontal Tables and to ItemData
-- Updated to reflect current status and values
--
-- Do we need both DataElement and ItemUsage?  ItemUsage has more fields (like durations) which would be helpful for DataElement.
-- DataElement is updated, wheras ItemUsage is a log file (so contains history of DataElements)
--

CREATE TABLE Data_Element (
	DataElement_ID	int(15) NOT NULL,
	InstrumentSession_ID int(15) NOT NULL,	-- if want permanent table of DataElements (final values for any instrument)
	InstrumentContent_ID int(15) NOT NULL,	-- gives access to everything
	LanguageCode char(2) default 'en', -- Language Used
	QuestionAsAsked text, -- parsed version of Question_ID
	AnswerString text,	-- value entered.  If from an AnswerList, this is the numeric coded value
	Answer_ID int(15) default NULL,	-- which answer was selected, but only for Items which have AnswerLists
	NullFlavor_ID int(15) NOT NULL,	-- whether to use Value	
	Comments text,	-- optional Comments
	Time_Stamp timestamp NOT NULL default CURRENT_TIMESTAMP,	-- so updated each time data is written?
	
  itemVacillation int(11) default NULL,
  responseLatency int(11) default NULL,
  responseDuration int(11) default NULL,
  
	KEY k1_DataElement (LanguageCode),
	PRIMARY KEY pk_DataElement (DataElement_ID)	-- do not need to set constraints, as they are set at InstrumentContents level?
) ENGINE=InnoDB;

--
-- Can InstrumentSession be used to maintain "state" of instrument so that person can save it, go to another location, and resume it?
-- For example, if have "Suspend" button, could give person custom login to retrieve the instrument in progress.
-- Updated to reflect current status and values
--

CREATE TABLE Instrument_Session (
  InstrumentSession_ID int(15) NOT NULL,
  InstrumentVersion_ID int(15) NOT NULL,
  Instrument_ID int(15) NOT NULL,
	User_ID int(15) NOT NULL,	-- could be anonymous.  IF so, NULL, or make new ANON user each time so can suspend/resume?
  InstrumentVersionData_ID int(15) NOT NULL,	-- primary key within Horizontal table; table name is "InstVer_" || InstrumentVersion_ID

  StartTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  LastAccessTime timestamp NOT NULL default '0000-00-00 00:00:00',	-- time of last access
  InstrumentStartingGroup int(11) NOT NULL,	-- starting location within instrument
  CurrentGroup int(11) NOT NULL,	-- current location within instrument
  DisplayNum int(11) NOT NULL,	-- current number of screens the user has seen
 	LanguageCode char(2) NOT NULL,	-- current language used for this instrument
  ActionType_ID int(15) NOT NULL,	-- what was the last action taken (next, previous, etc.)?
  StatusMsg varchar(200),	-- what is this used for, if anything?
  
	KEY k1_InstrumentSession (LanguageCode),
--  UNIQUE uni_InstrumentSession (InstrumentVersion_ID, User_ID, StartTime),	-- is this needed?
  PRIMARY KEY pk_InstrumentSession (InstrumentSession_ID)
) ENGINE=InnoDB;

--
-- PageUsage contains information about one page full worth of collected data.  This used to be called pageHits.
-- May represent 0 or more Items
-- Insert only
--

CREATE TABLE Page_Usage (
  PageUsage_ID int(15) NOT NULL,
  InstrumentSession_ID int(15) NOT NULL,
  
  LanguageCode char(2) NOT NULL default 'en',	-- active language
  Time_Stamp timestamp NOT NULL default CURRENT_TIMESTAMP,
  FromGroupNum int(11) NOT NULL,	-- GroupNum at when server received request (durations are about this one)
  ToGroupNum int(11) NOT NULL,	-- destination GroupNum (where subjects will go next)
  DisplayNum int(11) NOT NULL,	-- will be 1-N
  ActionType_ID int(15) NOT NULL,	-- last action (next, previous, etc.)
  StatusMsg varchar(25) default NULL,	-- what is this used for, if anything?
  
  totalDuration int(11) default NULL, -- milliseconds between successive getPost() calls)
  pageDuration int(11) default NULL,	-- milliseconds spent on page from first Focus to Submit
  serverDuration int(11) default NULL, -- millisec spent on server side from getPost() to flushing the writer
  loadDuration int(11) default NULL,	-- millisec from when client starts procesing page to onLoad event
  networkDuration int(11) default NULL,	-- millisec network latency (totalDuration - prior pageDuration, serverDuration and loadDuration

  pageVacillation int(11) default NULL,	-- what is this? Number of times the variable was visited?  If so, perhaps put in DataElement?

	KEY k1_PageUsage (LanguageCode),
  PRIMARY KEY pk_PageUsage_ID (PageUsage_ID)
) ENGINE=InnoDB;

--
-- PageUsageEvent is effectively a clickstream of events within a Page.  This used to be 'pageHitEvents'
-- Insert only
--

CREATE TABLE Page_Usage_Event (
  PageUsageEvent_ID int(15) NOT NULL,
  PageUsage_ID int(15) NOT NULL,
  VarName_ID int(15) NOT NULL,

  actionType varchar(18) NOT NULL default '',	-- e.g. focus, blur, submit
  eventType varchar(18) NOT NULL default '',	-- e.g. select-one, keypress
  Time_Stamp timestamp NULL default CURRENT_TIMESTAMP,
  duration int(11) NOT NULL default '0',	-- when is this duration calculated?
  value1 varchar(50) NOT NULL default '',	-- is this too tightly coupled to HTTP events?
  value2 varchar(250) NOT NULL default '',

  PRIMARY KEY pk_PageUsageEvent_ID (PageUsageEvent_ID)
) ENGINE=InnoDB;

--
-- This used to be called the RawData table
-- Insert only
--

CREATE TABLE Item_Usage (
  ItemUsage_ID bigint(20) NOT NULL auto_increment,
  InstrumentSession_ID int(15) NOT NULL,
  VarName_ID int(15) NOT NULL,	-- to facilitate retrieval of any data related to a variable
  PageUsage_ID int(15) NOT NULL,	-- to facilitate sending of page-level HL7 messages.  Should this be in DataElement?
  InstrumentContent_ID int(15) NOT NULL,	-- provides access to all other global details of instrument
  GroupNum int(15) NOT NULL,	-- current Group location within instrument (auto-derivable from Item?)
  DisplayNum int(15) NOT NULL,	-- how many screens the user has seen
  LanguageCode char(2) NOT NULL default 'en',	-- language Used for this ItemUsage
  WhenAsMS bigint(20) NOT NULL,	-- absolute time? Time since start?  Needed?
  Time_Stamp timestamp NOT NULL default CURRENT_TIMESTAMP,
	AnswerString text,	-- value entered.  If from an AnswerList, this is the numeric coded value
	Answer_ID int(15) default NULL,	-- which answer was selected, but only for Items which have AnswerLists
  NullFlavor_ID int(15) not NULL,
  QuestionAsAsked text NOT NULL,
  itemVacillation int(11) default NULL,
  responseLatency int(11) default NULL,
  responseDuration int(11) default NULL,
  Comments text NOT NULL,
  
	KEY k1_ItemUsage (LanguageCode),
  PRIMARY KEY pk_ItemUsage (ItemUsage_ID)
) ENGINE=InnoDB;

CREATE TABLE User (
  User_ID int(15) NOT NULL auto_increment,
  user_name varchar(20) NOT NULL,
  pwd varchar(20) NOT NULL,
  first_name varchar(30) NOT NULL,
  last_name varchar(30) NOT NULL,
  email varchar(80) NOT NULL,
  phone varchar(24) NOT NULL,

  PRIMARY KEY pk_User (User_ID)
) ENGINE=InnoDB;

--
-- Support tables needed for November Demo
--

CREATE TABLE LOINC_Instrument_Request (
	LOINC_InstrumentRequest_ID int(15) NOT NULL auto_increment, 
	
	InstrumentVersion_ID int(15) NOT NULL,	-- LOINC code is at granularity of instrument
	
  LOINCproperty varchar(30) default NULL,
  LOINCtimeAspect varchar(15) default NULL,
  LOINCsystem varchar(100) default NULL,
  LOINCscale varchar(30) default NULL,
  LOINCmethod varchar(50) default NULL,
  LOINC_NUM varchar(10) default NULL,	-- will be provided by LOINC if coding accepted
  
--  UNIQUE uni_LOINC_InstrumentRequest (InstrumentVersion_ID, LOINC_NUM),
  PRIMARY KEY pk_LOINC_InstrumentRequest (LOINC_InstrumentRequest_ID)
) ENGINE=InnoDB;

CREATE TABLE LOINC_Item_Request (
	LOINC_ItemRequest_ID int(15) NOT NULL auto_increment, 
	
	Item_ID int(15) NOT NULL,	-- is this the right granularity (question and answer list)
	
  LOINCproperty varchar(30) default NULL,
  LOINCtimeAspect varchar(15) default NULL,
  LOINCsystem varchar(100) default NULL,
  LOINCscale varchar(30) default NULL,
  LOINCmethod varchar(50) default NULL,
  LOINC_NUM varchar(10) default NULL,	-- will be provided by LOINC if coding accepted
  
--  UNIQUE uni_LOINC_ItemRequest (Item_ID, LOINC_NUM),
  PRIMARY KEY pk_LOINC_ItemRequest (LOINC_ItemRequest_ID)
) ENGINE=InnoDB;

--
-- Do we need a table or view for LA code requests?
--

--
-- SemanticMappings should be able to pull from UMLS MetaThesaurus
-- Zero-to many SemanticMappings of Q, A, Q+A, or I+Q+A to other code system.
--

CREATE TABLE Semantic_Mapping_I_Q_A (
	SemanticMapping_IQA_ID int(15) NOT NULL auto_increment,
	
	InstrumentVersion_ID int(15), -- may be NULL
	Question_ID	int(15) NOT NULL,
	Answer_ID int(15) NOT NULL,
	
	CodeSystem_ID int(15),	-- to refer to SNOMED, LOINC, ICD, etc.?, and retrieve OID value
	Code text,	-- value from the codesystem - how long of a varchar needed for this?
	CodeDisplayName text,	-- how long of a values needed for this?  Needed for <translation> in HL7
	
	PRIMARY KEY pk_SemanticMapping_IQA (SemanticMapping_IQA_ID)
) ENGINE=InnoDB;

--
-- SemanticMappings should be able to pull from UMLS MetaThesaurus
-- Zero-to many SemanticMappings of Q, A, Q+A, or I+Q+A to other code system.
--

CREATE TABLE Semantic_Mapping_Q_A (
	SemanticMapping_QA_ID int(15) NOT NULL auto_increment,
	
	Question_ID	int(15) NOT NULL,
	Answer_ID int(15) NOT NULL,
	
	CodeSystem_ID int(15),	-- to refer to SNOMED, LOINC, ICD, etc.?, and retrieve OID value
	Code text,	-- value from the codesystem - how long of a varchar needed for this?
	CodeDisplayName text,	-- how long of a values needed for this?  Needed for <translation> in HL7
	
	PRIMARY KEY pk_SemanticMapping_QA (SemanticMapping_QA_ID)
) ENGINE=InnoDB;

--
-- SemanticMappings should be able to pull from UMLS MetaThesaurus
-- Zero-to many SemanticMappings of Q, A, Q+A, or I+Q+A to other code system.
--

CREATE TABLE Semantic_Mapping_Q (
	SemanticMapping_Q_ID int(15) NOT NULL auto_increment,
	
	Question_ID	int(15) NOT NULL,
	
	CodeSystem_ID int(15),	-- to refer to SNOMED, LOINC, ICD, etc.?, and retrieve OID value
	Code text,	-- value from the codesystem - how long of a varchar needed for this?
	CodeDisplayName text,	-- how long of a values needed for this?  Needed for <translation> in HL7
	
	PRIMARY KEY pk_SemanticMapping_Q (SemanticMapping_Q_ID)
) ENGINE=InnoDB;

--
-- SemanticMappings should be able to pull from UMLS MetaThesaurus
-- Zero-to many SemanticMappings of Q, A, Q+A, or I+Q+A to other code system.
--

CREATE TABLE Semantic_Mapping_A (
	SemanticMapping_A_ID int(15) NOT NULL auto_increment,
	
	Answer_ID int(15) NOT NULL,
	
	CodeSystem_ID int(15),	-- to refer to SNOMED, LOINC, ICD, etc.?, and retrieve OID value
	Code text,	-- value from the codesystem - how long of a varchar needed for this?
	CodeDisplayName text,	-- how long of a values needed for this?  Needed for <translation> in HL7
	
	PRIMARY KEY pk_SemanticMapping_A (SemanticMapping_A_ID)
) ENGINE=InnoDB;

CREATE TABLE Code_System (
	CodeSystem_ID int(15) NOT NULL auto_increment,
	CodeSystemName varchar(50),	-- e.g. SNOMED, LOINC,
	CodeSystemOID varchar(50),	-- published OID for this CodeSystem.  Needed for <translation> element in HL7 CCD
	
	PRIMARY KEY pk_CodeSystem (CodeSystem_ID)
) ENGINE=InnoDB;	

--
-- There will be a unique horizontal table for each version of an instrument
-- There will be one column per unique VarName within that instrument
-- This table will be update to show the most recent value of each Item
-- So, the Table Name is "InstVer" || InstrumentVersion_ID
--

CREATE TABLE Inst_Ver_1 (
  InstrumentSession_ID int(15) NOT NULL,	-- this provides access to current status
  StartTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  LastAccessTime timestamp NOT NULL default '0000-00-00 00:00:00',
  DisplayNum int(11) NOT NULL,
  LanguageCode char(2) NOT NULL default 'en',	-- current langauge used
  InstrumentStartingGroup int(11) NOT NULL,
  CurrentGroup int(11) NOT NULL,
  LastAction varchar(35) default NULL,
  statusMsg varchar(35) NOT NULL, 
   
  --
  -- Put one column per VarName, with type Text
  -- 
  
	hasChild text,
	q2 text,
	male text,
	name text,
	demo5 text,
  
	KEY k1_InstVer_1 (LanguageCode),
	PRIMARY KEY pk_InstVer_1 (InstrumentSession_ID)
) ENGINE=InnoDB;


--
-- FOREIGN KEY CONSTRAINTS
--

ALTER TABLE Instrument_Version
  ADD CONSTRAINT InstrumentVersion_ibfk_1 FOREIGN KEY (Instrument_ID) REFERENCES Instrument (Instrument_ID),
  ADD CONSTRAINT InstrumentVersion_ibfk_2 FOREIGN KEY (InstrumentHash_ID) REFERENCES Instrument_Hash (InstrumentHash_ID);

ALTER TABLE Instrument_Hash
  ADD CONSTRAINT InstrumentHash_ibfk_1 FOREIGN KEY (LanguageList_ID) REFERENCES Language_List (LanguageList_ID);

ALTER TABLE Instrument_Content
  ADD CONSTRAINT InstrumentContent_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES Instrument_Version (InstrumentVersion_ID),
  ADD CONSTRAINT InstrumentContent_ibfk_2 FOREIGN KEY (Item_ID) REFERENCES Item (Item_ID),
  ADD CONSTRAINT InstrumentContent_ibfk_3 FOREIGN KEY (VarName_ID) REFERENCES Var_Name (VarName_ID),
  ADD CONSTRAINT InstrumentContent_ibfk_4 FOREIGN KEY (DisplayType_ID) REFERENCES Display_Type (DisplayType_ID);

ALTER TABLE Instrument_Header
  ADD CONSTRAINT InstrumentHeader_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES Instrument_Version (InstrumentVersion_ID),
  ADD CONSTRAINT InstrumentHeader_ibfk_2 FOREIGN KEY (ReservedWord_ID) REFERENCES Reserved_Word (ReservedWord_ID);
  
ALTER TABLE Question_Localized
  ADD CONSTRAINT QuestionLocalized_ibfk_2 FOREIGN KEY (Question_ID) REFERENCES Question (Question_ID);
  
 
ALTER TABLE Answer_Localized
  ADD CONSTRAINT AnswerLocalized_ibfk_2 FOREIGN KEY (Answer_ID) REFERENCES Answer (Answer_ID);
  
ALTER TABLE Help_Localized
  ADD CONSTRAINT HelpLocalized_ibfk_2 FOREIGN KEY (Help_ID) REFERENCES Help (Help_ID);
 
ALTER TABLE Item
  ADD CONSTRAINT Item_ibfk_1 FOREIGN KEY (Question_ID) REFERENCES Question (Question_ID),
  ADD CONSTRAINT Item_ibfk_2 FOREIGN KEY (DataType_ID) REFERENCES Data_Type (DataType_ID),
  ADD CONSTRAINT Item_ibfk_3 FOREIGN KEY (AnswerList_ID) REFERENCES Answer_List (AnswerList_ID),
  ADD CONSTRAINT Item_ibfk_4 FOREIGN KEY (Validation_ID) REFERENCES Validation (Validation_ID);
  
ALTER TABLE Answer_List_Content
  ADD CONSTRAINT AnswerListContent_ibfk_1 FOREIGN KEY (AnswerList_ID) REFERENCES Answer_List (AnswerList_ID),
  ADD CONSTRAINT AnswerListContent_ibfk_2 FOREIGN KEY (Answer_ID) REFERENCES Answer (Answer_ID);
  
ALTER TABLE Data_Element
  ADD CONSTRAINT DataElement_ibfk_1 FOREIGN KEY (InstrumentContent_ID) REFERENCES Instrument_Content (InstrumentContent_ID),
  ADD CONSTRAINT DataElement_ibfk_2 FOREIGN KEY (InstrumentSession_ID) REFERENCES Instrument_Session (InstrumentSession_ID);

ALTER TABLE Instrument_Session
  ADD CONSTRAINT InstrumentSession_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES Instrument_Version (InstrumentVersion_ID),
  ADD CONSTRAINT InstrumentSession_ibfk_2 FOREIGN KEY (ActionType_ID) REFERENCES Action_Type (ActionType_ID),
	ADD CONSTRAINT InstrumentSession_ibfk_3 FOREIGN KEY (User_ID) REFERENCES User (User_ID),
  ADD CONSTRAINT InstrumentSession_ibfk_4 FOREIGN KEY (Instrument_ID) REFERENCES Instrument (Instrument_ID);
  
ALTER TABLE Page_Usage
  ADD CONSTRAINT PageUsage_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES Instrument_Session (InstrumentSession_ID),
  ADD CONSTRAINT PageUsage_ibfk_2 FOREIGN KEY (ActionType_ID) REFERENCES Action_Type (ActionType_ID);
  
ALTER TABLE Page_Usage_Event
  ADD CONSTRAINT PageUsageEvent_ibfk_1 FOREIGN KEY (PageUsage_ID) REFERENCES Page_Usage (PageUsage_ID),
  ADD CONSTRAINT PageUsageEvent_ibfk_2 FOREIGN KEY (VarName_ID) REFERENCES Var_Name (VarName_ID);
  
ALTER TABLE Item_Usage
  ADD CONSTRAINT ItemUsage_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES Instrument_Session (InstrumentSession_ID),
  ADD CONSTRAINT ItemUsage_ibfk_2 FOREIGN KEY (VarName_ID) REFERENCES Var_Name (VarName_ID),
  ADD CONSTRAINT ItemUsage_ibfk_3 FOREIGN KEY (InstrumentContent_ID) REFERENCES Instrument_Content (InstrumentContent_ID);
  
ALTER TABLE LOINC_Instrument_Request
  ADD CONSTRAINT LOINC_InstrumentRequest_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES Instrument_Version (InstrumentVersion_ID);  

ALTER TABLE LOINC_Item_Request
  ADD CONSTRAINT LOINC_ItemRequest_ibfk_1 FOREIGN KEY (Item_ID) REFERENCES Item (Item_ID);

ALTER TABLE Inst_Ver_1
  ADD CONSTRAINT InstVer_1_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES Instrument_Session (InstrumentSession_ID);

ALTER TABLE Semantic_Mapping_I_Q_A
  ADD CONSTRAINT SemanticMapping_IQA_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES Instrument_Version (InstrumentVersion_ID),
  ADD CONSTRAINT SemanticMapping_IQA_ibfk_2 FOREIGN KEY (Question_ID) REFERENCES Question (Question_ID),
  ADD CONSTRAINT SemanticMapping_IQA_ibfk_3 FOREIGN KEY (Answer_ID) REFERENCES Answer (Answer_ID),
  ADD CONSTRAINT SemanticMapping_IQA_ibfk_4 FOREIGN KEY (CodeSystem_ID) REFERENCES Code_System (CodeSystem_ID);
  
ALTER TABLE Semantic_Mapping_Q_A
  ADD CONSTRAINT SemanticMapping_QA_ibfk_1 FOREIGN KEY (Question_ID) REFERENCES Question (Question_ID),
  ADD CONSTRAINT SemanticMapping_QA_ibfk_2 FOREIGN KEY (Answer_ID) REFERENCES Answer (Answer_ID),
  ADD CONSTRAINT SemanticMapping_QA_ibfk_3 FOREIGN KEY (CodeSystem_ID) REFERENCES Code_System (CodeSystem_ID);

ALTER TABLE Semantic_Mapping_Q
  ADD CONSTRAINT SemanticMapping_Q_ibfk_1 FOREIGN KEY (Question_ID) REFERENCES Question (Question_ID),
  ADD CONSTRAINT SemanticMapping_Q_ibfk_2 FOREIGN KEY (CodeSystem_ID) REFERENCES Code_System (CodeSystem_ID);

ALTER TABLE Semantic_Mapping_A
  ADD CONSTRAINT SemanticMapping_A_ibfk_1 FOREIGN KEY (Answer_ID) REFERENCES Answer (Answer_ID),
  ADD CONSTRAINT SemanticMapping_A_ibfk_2 FOREIGN KEY (CodeSystem_ID) REFERENCES Code_System (CodeSystem_ID);
  
ALTER TABLE Display_Type
	ADD CONSTRAINT DisplayType_ibfk_1 FOREIGN KEY (DataType_ID) REFERENCES Data_Type (DataType_ID);

--
-- INSERT VOCABULARIES
--

-- Within Dialogix, this is a 0-based index

INSERT INTO Reserved_Word (ReservedWord) VALUES 
('__LANGUAGES__'),
('__TITLE__'),
('__ICON__'),
('__HEADER_MSG__'),
('__STARTING_STEP__'),
('__PASSWORD_FOR_ADMIN_MODE__'),
('__SHOW_QUESTION_REF__'),
('__AUTOGEN_OPTION_NUM__'),
('__DEVELOPER_MODE__'),
('__DEBUG_MODE__'),
('__START_TIME__'),
('__FILENAME__'),
('__SHOW_ADMIN_ICONS__'),
('__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__'),
('__ALLOW_COMMENTS__'),
('__SCHEDULE_SOURCE__'),
('__LOADED_FROM__'),
('__CURRENT_LANGUAGE__'),
('__ALLOW_LANGUAGE_SWITCHING__'),
('__ALLOW_REFUSED__'),
('__ALLOW_UNKNOWN__'),
('__ALLOW_DONT_UNDERSTAND__'),
('__RECORD_EVENTS__'),
('__WORKING_DIR__'),
('__COMPLETED_DIR__'),
('__FLOPPY_DIR__'),
('__IMAGE_FILES_DIR__'),
('__COMMENT_ICON_ON__'),
('__COMMENT_ICON_OFF__'),
('__REFUSED_ICON_ON__'),
('__REFUSED_ICON_OFF__'),
('__UNKNOWN_ICON_ON__'),
('__UNKNOWN_ICON_OFF__'),
('__DONT_UNDERSTAND_ICON_ON__'),
('__DONT_UNDERSTAND_ICON_OFF__'),
('__TRICEPS_VERSION_MAJOR__'),
('__TRICEPS_VERSION_MINOR__'),
('__SCHED_AUTHORS__'),
('__SCHED_VERSION_MAJOR__'),
('__SCHED_VERSION_MINOR__'),
('__SCHED_HELP_URL__'),
('__HELP_ICON__'),
('__ACTIVE_BUTTON_PREFIX__'),
('__ACTIVE_BUTTON_SUFFIX__'),
('__TRICEPS_FILE_TYPE__'),
('__DISPLAY_COUNT__'),
('__SCHEDULE_DIR__'),
('__ALLOW_JUMP_TO__'),
('__BROWSER_TYPE__'),
('__IP_ADDRESS__'),
('__SUSPEND_TO_FLOPPY__'),
('__JUMP_TO_FIRST_UNASKED__'),
('__REDIRECT_ON_FINISH_URL__'),
('__REDIRECT_ON_FINISH_MSG__'),
('__SWAP_NEXT_AND_PREVIOUS__'),
('__ANSWER_OPTION_FIELD_WIDTH__'),
('__SET_DEFAULT_FOCUS__'),
('__ALWAYS_SHOW_ADMIN_ICONS__'),
('__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__'),
('__WRAP_ADMIN_ICONS__'),
('__DISALLOW_COMMENTS__'),
('__CONNECTION_TYPE__'),
('__REDIRECT_ON_FINISH_DELAY__'),
('__MAX_TEXT_LEN_FOR_COMBO__')
;

INSERT INTO Null_Flavor (NullFlavor, DisplayName) VALUES 
('*UNASKED*', '*UNASKED*'), 
('*NA*', '*NA*'),      
('*REFUSED*', '*REFUSED*'), 
('*INVALID*', '*INVALID*'), 
('*UNKNOWN*', '*UNKNOWN*'), 
('*HUH*', '*HUH*')
;

INSERT INTO Action_Type (ActionName) VALUES
('evaluate_expr'),
('finished'),
('jump_to'),
('jumpToFirstUnasked'),
('next'),
('previous'),
('refresh current'),
('reload_questions'),
('restart_clean'),
('RESTORE'),
('RESTORE_FROM_FLOPPY'),
('save_to'),
('select_new_interview'),
('show_Syntax_Errors'),
('sign_schedule'),
('START'),
('suspendToFloppy'),
('toggle_EventCollection'),
('turn_debugMode'),
('turn_developerMode'),
('turn_showQuestionNum')
;


INSERT INTO Data_Type (DataType_ID, DataType) VALUES
(6, 'number'),
(7, 'string'),
(8, 'date'),
(9, 'time'),
(10, 'year'),
(11, 'month'),
(12, 'day'),
(13, 'weekday'),
(14, 'hour'),
(15, 'minute'),
(16, 'second'),
(17, 'month_num'),
(18, 'day_num')
;

INSERT INTO Display_Type (DisplayType_ID, DisplayType, DataType_ID, HasAnswerList, SPSSformat, SASinformat, SASformat, SPSSlevel, LOINCscale) VALUES
(1, 'nothing', 6, '0', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(2, 'radio', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(3, 'check', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(4, 'combo', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(5, 'list', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(6, 'text', 7, '0', 'A100', '$100.', '$100.', 'NOMINAL', 'NAR'),
(7, 'double', 6, '0', 'F8.3', 'best32.', 'best12.', 'SCALE', 'QN'),
(8, 'radio2', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(9, 'password', 7, '0', 'A50', '$50.', '$50.', 'NOMINAL', 'NAR'),
(10, 'memo', 7, '0', 'A254', '$254.', '$254.', 'NOMINAL', 'NAR'),
(11, 'date', 8, '0', 'ADATE10', 'mmddyy10.', 'mmddyy10.', 'SCALE', 'QN'),
(12, 'time', 9, '0', 'TIME5.3', 'time8.0', 'time8.0', 'SCALE', 'QN'),
(13, 'year', 10, '0', 'date|yyyy', 'best32.', 'best12.', 'SCALE', 'QN'),
(14, 'month', 11, '0', 'MONTH', '$10.', '$10.', 'SCALE', 'QN'),
(15, 'day', 12, '0', 'date|dd', '$6.', '$6.', 'SCALE', 'QN'),
(16, 'weekday', 13, '0', 'WKDAY', '$10.', '$10.', 'SCALE', 'QN'),
(17, 'hour', 14, '0', 'date|hh', 'best32.', 'hour2.', 'SCALE', 'QN'),
(18, 'minute', 15, '0', 'date|mm', 'best32.', 'best12.', 'SCALE', 'QN'),
(19, 'second', 16, '0', 'date|ss', 'best32.', 'best12.', 'SCALE', 'QN'),
(20, 'month_num', 17, '0', 'date|mm', 'best32.', 'best12.', 'SCALE', 'QN'),
(21, 'day_num', 18, '0', 'date|dd', 'best32.', 'day2.', 'SCALE', 'QN'),
(22, 'radio3', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(23, 'combo2', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM'),
(24, 'list2', 6, '1', 'F8.0', 'best32.', 'best12.', 'NOMINAL', 'NOM')
;

-- 
--
-- INSERT DATA
--

INSERT INTO Instrument (Instrument_ID, InstrumentName, InstrumentDescription) VALUES 
(1, 'qam', ''), 
(2, 'EnglishRussianFrenchHebrew', '');

INSERT INTO Language_List VALUES
(1, 'en_US'),
(2, 'en_US|ru|fr|es|he');	

INSERT INTO Instrument_Hash VALUES
(1, 1, 10, ';aldfja;dfkas', 'a;slfkas;ldfkja', 1, 0, 0, 10, 0, 0),
(2, 2, 5, ';aasdfldfja;dfkas', 'a;slfkas;ldfkja', 5, 0, 0, 5, 1, 0);

INSERT INTO Instrument_Version (InstrumentVersion_ID, Instrument_ID, InstrumentHash_ID, VersionString, InstrumentNotes, InstrumentStatus) VALUES 
(1, 1, 1, '1.0', 'qam', 0),
(2, 2, 2, '1.0', 'EnglishRussianFrenchHebrew', 0),
(3, 1, 1, '1.1', 'qam', 1),
(4, 2, 2, '1.1', 'EnglishRussianFrenchHebrew', 1);

--
-- Do we need tables for these, or just views?
--
-- Apelon Export
-- UMLS Export
-- LOINC Export
-- LOINC Export-- Insert for testing
INSERT INTO question (
Question_ID 
)
VALUES (
'1'
), (
'2'
);

INSERT INTO answer_list (
AnswerList_ID ,
Description 
)
VALUES (
'1', 'Answer List 1'
), (
'2', 'Answer List 2'
);


INSERT INTO validation (
Validation_ID ,
MinVal ,
MaxVal ,
OtherVals ,
InputMask 
)
VALUES (
'1', '1', '100', '', ''
);

INSERT INTO item (
Item_ID ,
Question_ID ,
DataType_ID ,
AnswerList_ID ,
Validation_ID ,
ItemType ,
Concept ,
hasLOINCcode ,
LOINC_NUM 
)
VALUES (
'1', '1', '7', '1', '1', 'Question', NULL , '0', NULL 
);

INSERT INTO var_name (
VarName_ID ,
VarName 
)
VALUES (
'1', 'Varname'
);

INSERT INTO help (
Help_ID 
)
VALUES (
'Help'
), (
'Help'
);

INSERT INTO
  SEQUENCE_GENERATOR_TABLE (SEQUENCE_NAME, SEQUENCE_VALUE)
VALUES ('INSTRUMENT_SESSION', 0),
       ('DataElement_SEQUENCE', 0),
       ('PAGEUSAGE_SEQUENCE', 0),
       ('PAGEUSAGEEVENTS_SEQUENCE', 0);


--
-- Do we need tables for these, or just views?
--

CREATE TABLE Item_Localized (
	ItemLocalized_ID int(15) NOT NULL auto_increment,
	Item_ID int(15) NOT NULL,	
	LanguageCode char(2) default 'en',	-- the ISO language code
	
	Readback text default NULL,
	Question text NOT NULL,
	HelpURL text default NULL,
	AnswerListID int(15) default NULL,	-- will be non-null if there is an attached AnswerList
	

	KEY k1_ItemLocalized (LanguageCode),
-- 	UNIQUE (Item_ID, LanguageCode),	-- so don't duplicate language-based entries for items
  PRIMARY KEY pk_ItemLocalized (ItemLocalized_ID)
 ) ENGINE=InnoDB;
 
ALTER TABLE Item_Localized
  ADD CONSTRAINT ItemLocalized_ibfk_1 FOREIGN KEY (Item_ID) REFERENCES Item (Item_ID);
