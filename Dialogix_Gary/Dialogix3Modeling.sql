
DROP DATABASE innodb2;
CREATE DATABASE innodb2 DEFAULT CHARSET=utf8;
USE innodb2;

--
-- GLOBAL DEFINITION OF INSTRUMENTS
--

CREATE TABLE Instrument (
	Instrument_ID	int(11)	NOT NULL auto_increment,
	Name	varchar(120) NOT NULL,
	description	mediumtext default '',
  PRIMARY KEY pk_Instrument (Instrument_ID),
  UNIQUE uni_Instrument (Name)
) ENGINE=InnoDB;	

CREATE TABLE InstrumentVersion (
  InstrumentVersion_ID int(11) NOT NULL auto_increment,
  Instrument_ID int(11) NOT NULL,
  InstrumentHash_ID int(11) NOT NULL,
  MajorVersion int(11) NOT NULL,
  MinorVersion int(11) NOT NULL,
  Instrument_notes text default '',
  Instrument_status int default NULL,
  CreationDate date NOT NULL,	-- probably need other metadata - as joined table?

  PRIMARY KEY pk_InstrumentVersion (InstrumentVersion_ID),
  KEY k1_InstrumentVersion (Instrument_ID),
  UNIQUE uni_InstrumentVersion (Instrument_ID, InstrumentHash_ID)	-- to enforce instrument uniqueness?
) ENGINE=InnoDB;

CREATE TABLE LanguageList (
  LanguageList_ID int(11) NOT NULL auto_increment,
  LanguageList text NOT NULL,
  PRIMARY KEY pk_LanguageList (LanguageList_ID)
  -- Avoid UNIQUE?  Length too restrictive
) ENGINE=InnoDB;

CREATE TABLE InstrumentHash (
	InstrumentHash_ID int(11) NOT NULL auto_increment,
	LanguageList_ID int(11) NOT NULL,
  NumVars smallint(6) NOT NULL default '0',
  VarListMD5 varchar(35) NOT NULL default '',
  InstrumentMD5 varchar(35) NOT NULL default '',
  NumLanguages smallint(6) NOT NULL default '0',
  NumInstructions smallint(6) NOT NULL default '0',
  NumEquations smallint(6) NOT NULL default '0',
  NumQuestions smallint(6) NOT NULL default '0',
  NumBranches smallint(6) NOT NULL default '0',
  NumTailorings smallint(6) NOT NULL default '0',
  PRIMARY KEY pk_InstrumentHash (InstrumentHash_ID),
  UNIQUE uni_InstrumentHash (NumVars, VarListMD5, InstrumentMD5, LanguageList_ID, NumLanguages)
) ENGINE=InnoDB;	

CREATE TABLE VarName (
	VarName_ID int(11) NOT NULL auto_increment,
	VarName varchar(100) NOT NULL,
  PRIMARY KEY pk_VarName (VarName_ID),
  UNIQUE uni_VarName (VarName)
) ENGINE=InnoDB;	
 
CREATE TABLE DisplayType (
 	DisplayType_ID int(11) NOT NULL auto_increment,
	DisplayType varchar(100) NOT NULL,
  PRIMARY KEY pk_DisplayType (DisplayType_ID),
  UNIQUE uni_DisplayType (DisplayType)
) ENGINE=InnoDB;	

-- SAS and SPSS Formats are hints based upon data type - users can override them
CREATE TABLE DataType (
 	DataType_ID int(11) NOT NULL auto_increment,
	DataType varchar(100) NOT NULL,
  SPSSformat varchar(25) default NULL,
  SASinformat varchar(25) default NULL,
  SASformat varchar(25) default NULL,	
  PRIMARY KEY pk_DataType (DataType_ID),
  UNIQUE uni_DataType (DataType)
) ENGINE=InnoDB;	

--
-- Where do I put nesting level?
-- How do I include Sections within Sections?  What attributes do they have?
-- I would like Instruments to contain Sections, which can be Sections or Items, and unique ordering within each section and subsection level
-- 

CREATE TABLE InstrumentContent (
	InstrumentContent_ID	int(11)	NOT NULL auto_increment,
	InstrumentVersion_ID int(11) NOT NULL,
	Item_ID int(11) NOT NULL,
	VarName_ID int(11) NOT NULL,
	Item_Sequence int(11) NOT NULL,	-- should be enforced as 1-N?
	Help_ID int(11) NOT NULL,	-- only applys to Items
	
	DisplayType_ID int(11) NOT NULL,	-- hint, like list, radio, etc.
	
  isRequired smallint(6) NOT NULL default '0',	-- whether user must answer this before submit
  isReadOnly smallint(6) NOT NULL default '0',	-- display without allowing change
  DisplayName text default '',	-- optional local display name
  VarName_8char varchar(8) default NULL,	-- variable name limited to 8 characters - locally scoped to instrument
  GroupNum int NOT NULL default '0',	-- calculated from matching braces
  Relevance text NOT NULL,	-- Boolean - whether question should be asked
  ActionType ENUM ('q', 'e', '[', ']'),
  FormatMask text,	
  
  isMessage smallint(6) NOT NULL default '0',	-- derived from Item - question but no answer
  DefaultAnswer text,
  
  -- These formats, by default, are derived from DataType class, but can be overridden
  SPSSformat varchar(25) default NULL,
  SASinformat varchar(25) default NULL,
  SASformat varchar(25) default NULL,	  
  
  PRIMARY KEY pk_InstrumentContent (InstrumentContent_ID),
	UNIQUE uni_InstrumentContent (InstrumentVersion_ID, Item_ID, VarName_ID, Item_Sequence),
	KEY k1_InstrumentContent (DisplayType_ID)
) ENGINE=InnoDB;

-- Can I Categorize Reserved Words based upon:
-- (1) Global Variables -- e.g. headers, titles, turn actions on/off which affect navigation and/or layout
-- (2) Other?

CREATE TABLE ReservedWord (
	ReservedWord_ID int(11) NOT NULL auto_increment,
	ReservedWord varchar(100) NOT NULL,
	Meaning text,
  PRIMARY KEY pk_ReservedWord (ReservedWord_ID),
  UNIQUE uni_ReservedWord (ReservedWord)
) ENGINE=InnoDB;	

CREATE TABLE InstrumentHeader (
  InstrumentHeader_ID int(11) NOT NULL auto_increment,
  InstrumentVersion_ID int(11) NOT NULL,
  ReservedWord_ID int(11) NOT NULL,
  Value text NOT NULL,
  PRIMARY KEY pk_InstrumentHeader (InstrumentHeader_ID),
  UNIQUE uni_InstrumentHeader (InstrumentVersion_ID, ReservedWord_ID)
) ENGINE=InnoDB;

--
-- At what level should translations be specified?
-- Same string can be used for Question or Answer across instruments, but with different Question and Answer ID
-- This avoids assumption that same string has same meaning in different contexts, and allows instrument-specific edits
-- Simple solution for editting is to give new IDs each time upload new instrument, whether in dev or production
-- Rather than looking for String identity (which Mysql cannot do anyway for > 999 bytes)
--
CREATE TABLE QuestionLocalized (
  QuestionLocalized_ID int(11) NOT NULL auto_increment,
  Question_ID int(11) NOT NULL,
  Language_ID int(11) NOT NULL,
  QuestionString text,
  PRIMARY KEY pk_QuestionLocalized (QuestionLocalized_ID),
  UNIQUE uni_QuestionLocalized (Question_ID, Language_ID)
) ENGINE=InnoDB;

CREATE TABLE AnswerLocalized (
  AnswerLocalized_ID int(11) NOT NULL auto_increment,
  Answer_ID int(11) NOT NULL,
  Language_ID int(11) NOT NULL,
  AnswerString text,
  PRIMARY KEY pk_AnswerLocalized (AnswerLocalized_ID),
  UNIQUE uni_AnswerLocalized (Answer_ID, Language_ID)
) ENGINE=InnoDB;

CREATE TABLE HelpLocalized (
  HelpLocalized_ID int(11) NOT NULL auto_increment,
  Help_ID int(11) NOT NULL,
  Language_ID int(11) NOT NULL,
  HelpString text,
  PRIMARY KEY pk_HelpLocalized (HelpLocalized_ID),
  UNIQUE uni_HelpLocalized (Help_ID, Language_ID)
) ENGINE=InnoDB;

CREATE TABLE Language (
	Language_ID	int(11)	NOT NULL auto_increment,
	LanguageCode varchar(10)  NOT NULL,
	Description varchar(100),
	PRIMARY KEY pk_Language (Language_ID),
	UNIQUE uni_Language (LanguageCode)
) ENGINE=InnoDB;

--
-- Does it make sense to have normalized validation table?
-- It might help detection of whether items have changed validation criteria
--
CREATE TABLE Validation (
	Validation_ID	int(11)	NOT NULL auto_increment,
  MinVal varchar(75) default '',
  MaxVal varchar(75) default '',
  OtherVals varchar(100) default '',
  InputMask varchar(75) default '',	
	PRIMARY KEY pk_Validation (Validation_ID),
	UNIQUE uni_Validation (MinVal, MaxVal, OtherVals, InputMask)
) ENGINE=InnoDB;

--
-- It is not safe to make this Unique.  Risk is that Item in different context may have different meaning
--

CREATE TABLE Item (
	Item_ID	int(11)	NOT NULL auto_increment,
	Question_ID	int(11)	NOT NULL,	-- this also points to equations, if ItemType is Equation
	DataType_ID int(11) NOT NULL,	
	AnswerList_ID	int(11)	NOT NULL,	-- in case an enumerated list of code set
	Validation_ID int(11) NOT NULL,
 	ItemType ENUM( 'Question', 'Equation' ) NOT NULL,
 	Concept text default '',
	
  PRIMARY KEY pk_Item (Item_ID),
  KEY k1_Item (Question_ID),
  KEY k2_Item (DataType_ID),
  KEY k3_Item (AnswerList_ID),
  KEY k4_Item (Validation_ID)
--  UNIQUE (Question_ID, DataType_ID, AnswerList_ID, Validation_ID)
) ENGINE=InnoDB;

CREATE TABLE AnswerList (
	AnswerList_ID	int(11)	NOT NULL auto_increment,
	Description text,	-- add other metadata, like ISO 11179?
  PRIMARY KEY pk_AnswerList (AnswerList_ID)
) ENGINE=InnoDB;

-- 
-- Want to extend this to use Code Sets
-- Should DataType be pushed down into AnswerList?
--

CREATE TABLE AnswerListContent (
	AnswerListContent_ID	int(11)	NOT NULL auto_increment,
	AnswerList_ID int(11) NOT NULL,
	Answer_ID int(11) NOT NULL,
	Answer_Order int(11) NOT NULL,
	Value varchar(100) NOT NULL,	-- what if need longer value?
  PRIMARY KEY pk_AnswerListContent (AnswerListContent_ID),
	UNIQUE uni_AnswerListContent (AnswerList_ID, Answer_ID, Answer_Order, Value)
) ENGINE=InnoDB;

--
-- Should there be a table of ActionTypes?
-- Next, Previous, Load, Save, Suspend, JumpTo, ShowSyntaxErrors, 
-- ToggleDeveloperMode, ToggleDebugMode, ChangeLanguage, etc.
-- This could be used for Status Messages?
--
CREATE TABLE ActionType (
  ActionType_ID int(11) NOT NULL auto_increment,
  ActionName varchar(50) NOT NULL,	-- should this be internationalized?
	PRIMARY KEY pk_ActionType (ActionType_ID)
) ENGINE=InnoDB;

CREATE TABLE NullFlavor (
  NullFlavor_ID int(11) NOT NULL auto_increment,
  NullFlavor varchar(100) NOT NULL,
	DisplayName varchar(100) NOT NULL,
	Description text,
  PRIMARY KEY pk_NullFlavor (NullFlavor_ID),
  UNIQUE uni_NullFlavor (NullFlavor)
) ENGINE=InnoDB;

-- 
-- Functions - used by parser. May be able to remove them from the 
-- Java class and create Velocity templates for them, thereby allowing 
-- for easier expansion of functionality
--

CREATE TABLE FunctionName (
	FunctionName_ID int(11) NOT NULL auto_increment,
	Name varchar(30) NOT NULL, -- name used within function lookups
	Syntax text NOT NULL, -- specifies required input parameters
	Description text NOT NULL, -- narrative description of how to use it (in JavaDoc format?)
	Definition text NOT NULL, -- Velocity (or other) template to parse and process the input variables
	PRIMARY KEY pk_FunctionName (FunctionName_ID),
	UNIQUE uni_FunctionName (Name)
) ENGINE=InnoDB;


--
-- DATA COLLECTED USING INSTRUMENTS
--

--
-- Could consider collection of Datum Elements like this
-- Might facilitate identification of Dirty data which needs to be written
-- These get written to Horizontal Tables and to ItemData
-- Updated to reflect current status and values
--

CREATE TABLE Datum (
	Datum_ID	int(11) NOT NULL auto_increment,
	InstrumentContent_ID int(11) NOT NULL,	-- gives access to everything
	InstrumentVersion_ID int(11) NOT NULL,
	Item_ID int(11) NOT NULL,
	VarName_ID int(11) NOT NULL,
	Language_ID int(11) NOT NULL, -- Language Used
	PageUsage_ID int(11) NOT NULL,	-- Updated to reflect PageUsage which set this Datum?
	QuestionAsAsked text, -- parsed version of Question_ID
	Value text,	-- value entered
	NullFlavor_ID int(11) NOT NULL,	-- whether to use Value	
	Comments text,	-- optional Comments
	Time_Stamp timestamp NOT NULL default CURRENT_TIMESTAMP,	-- so updated each time data is written?
	PRIMARY KEY pk_Datum (Datum_ID),	-- do not need to set constraints, as they are set at InstrumentContents level?
	UNIQUE (InstrumentVersion_ID, VarName_ID),
	KEY k1_Datum (Language_ID),
	KEY k2_Datum (PageUsage_ID)
) ENGINE=InnoDB;

--
-- Can InstrumentSession be used to maintain "state" of instrument so that person can save it, go to another location, and resume it?
-- For example, if have "Suspend" button, could give person custom login to retrieve the instrument in progress.
-- Updated to reflect current status and values
--

CREATE TABLE InstrumentSession (
  InstrumentSession_ID int(11) NOT NULL auto_increment,
  InstrumentVersion_ID int(11) NOT NULL,
  User_ID int(11) NOT NULL,	-- could be anonymous.  IF so, NULL, or make new ANON user each time so can suspend/resume?
  InstrumentVersionData_ID int(11) NOT NULL,	-- primary key within Horizontal table; table name is "InstVer_" || InstrumentVersion_ID

  StartTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  EndTime timestamp NOT NULL default '0000-00-00 00:00:00',	-- time of last access
  First_Group int(11) NOT NULL,	-- starting location within instrument
  Current_Group int(11) NOT NULL,	-- current location within instrument
  ActionType_ID int(11) NOT NULL,	-- what was the last action taken (next, previous, etc.)?

  Last_Access varchar(20) default NULL,	-- What is this used for, if anything?
  StatusMsg varchar(200),	-- what is this used for, if anything?
  PRIMARY KEY pk_InstrumentSession (InstrumentSession_ID),
  KEY k1_InstrumentSession (InstrumentVersion_ID, User_ID),
  UNIQUE uni_InstrumentSession (InstrumentVersion_ID, User_ID, StartTime)	-- is this needed?
) ENGINE=InnoDB;

--
-- PageUsage contains information about one page full worth of collected data
-- May represent 0 or more Items
-- Insert only
--

CREATE TABLE PageUsage (
  PageUsage_ID int(11) NOT NULL auto_increment,
  InstrumentSession_ID int(11) NOT NULL,
  
  Language_ID int(11) NOT NULL,	-- active language
  Time_Stamp timestamp NOT NULL default CURRENT_TIMESTAMP,
  AccessCount int(11) NOT NULL,	-- is this needed?
  GroupNum int(11) NOT NULL,	-- current GroupNum from Instrument, so know if go back and forth
  DisplayNum int(11) NOT NULL,	-- will be 1-N
  ActionType_ID int(11) NOT NULL,
  StatusMsg varchar(200),	-- what is this used for, if anything?
  
  pageDuration int(11) default NULL,	-- milliseconds spent on page from first Focus to Submit
  serverDuration int(11) default NULL, -- millisec spent on server side from getPost() to flushing the writer
  loadDuration int(11) default NULL,	-- millisec from onLoad event to first Focus event on client
  networkDuration int(11) default NULL,	-- millisec network latency (from one getPost() to next, minus prior pageDuration, serverDuration and loadDuration)

  pageVacillation int(11) default NULL,	-- what is this? Number of times the variable was visited?  If so, perhaps put in Datum?
  PRIMARY KEY pk_PageUsage_ID (PageUsage_ID),
  KEY k1_PageUsage (InstrumentSession_ID)
) ENGINE=InnoDB;

--
-- PageUsageEvent is effectively a clickstream of events within a Page
-- Insert only
--

CREATE TABLE PageUsageEvent (
  PageUsageEvent_ID int(11) NOT NULL auto_increment,
  PageUsage_ID int(11) NOT NULL,
  VarName_ID int(11) NOT NULL,

  actionType varchar(18) NOT NULL default '',	-- what is this?
  eventType varchar(18) NOT NULL default '',	-- what is this?
  Time_Stamp timestamp NULL default CURRENT_TIMESTAMP,
  duration int(11) NOT NULL default '0',	-- when is this duration calculated?
  value1 varchar(50) NOT NULL default '',	-- is this too tightly coupled to HTTP events?
  value2 varchar(250) NOT NULL default '',
  PRIMARY KEY pk_PageUsageEvent_ID (PageUsageEvent_ID),
  KEY k1_PageUsageEvent (PageUsage_ID, VarName_ID)
) ENGINE=InnoDB;

--
-- This used to be called the RawData table
-- Insert only
--

CREATE TABLE ItemUsage (
  ItemUsage_ID bigint(20) NOT NULL auto_increment,
  InstrumentSession_ID int(11) NOT NULL,
  VarName_ID int(11) NOT NULL,	-- to facilitate retrieval of any data related to a variable
  InstrumentContent_ID int(11) NOT NULL,	-- provides access to all other global details of instrument
  GroupNum smallint(6) NOT NULL,	-- current Group location within instrument (auto-derivable from Item?)
  DisplayNum smallint(6) NOT NULL,	-- how many screens the user has seen
  Language_ID int(11) NOT NULL,	-- language Used for this ItemUsage
  WhenAsMS bigint(20) NOT NULL,	-- absolute time? Time since start?  Needed?
  Time_Stamp timestamp NOT NULL default CURRENT_TIMESTAMP,
  AnswerType int(4) NOT NULL,	-- Needed?
  Value text NOT NULL,	-- what was entered for answer, as String?
  NullFlavor_ID int(11) not NULL,
  QuestionAsAsked text NOT NULL,
  itemVacillation int(11)default NULL,
  responseLatency int(11) default NULL,
  responseDuration int(11) default NULL,
  Comments text NOT NULL,
  PRIMARY KEY pk_ItemUsage (ItemUsage_ID),
  KEY k1_ItemUsage (InstrumentSession_ID),
  KEY k2_ItemUsage (VarName_ID)
) ENGINE=InnoDB;

CREATE TABLE User (
  User_ID int(11) NOT NULL auto_increment,
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
-- May need additional LOINC tables, e.g. for Instrument and LA code requests
--

CREATE TABLE LOINC_ItemRequest (
	LOINC_ItemRequest_ID int(11) NOT NULL auto_increment, 
	
	Item_ID int(11) NOT NULL,	-- is this the right granularity (question and answer list)
	TempLOINCcode int(11) NOT NULL, -- use this until a real one is assigned
	
  LOINCproperty varchar(30) default NULL,
  LOINCtimeAspect varchar(15) default NULL,
  LOINCsystem varchar(100) default NULL,
  LOINCscale varchar(30) default NULL,
  LOINCmethod varchar(50) default NULL,
  LOINC_NUM varchar(10) default NULL,
  
  PRIMARY KEY pk_LOINC_ItemRequest (LOINC_ItemRequest_ID),
  UNIQUE uni_LOINC_ItemRequest (Item_ID, TempLOINCcode, LOINC_NUM)
) ENGINE=InnoDB;

--
-- SemanticMappings should be able to pull from UMLS MetaThesaurus
-- Zero-to many SemanticMappings of Q, A, Q+A, or I+Q+A to other code system
--

CREATE TABLE SemanticMapping (
	SemanticMapping_ID int(11) NOT NULL auto_increment,
	
	InstrumentVersion_ID int(11), -- may be NULL
	Question_ID	int(11),	-- may be NULL
	Answer_ID int(11),	-- may be NULL
	
	CodeSet_ID int(11),	-- to refer to SNOMED, LOINC, ICD, etc.?
	Value text,	-- value from the codeset
	
	PRIMARY KEY pk_SemanticMapping (SemanticMapping_ID)
) ENGINE=InnoDB;

--
-- There will be a unique horizontal table for each version of an instrument
-- There will be one column per unique VarName within that instrument
-- This table will be update to show the most recent value of each Item
-- So, the Table Name is "InstVer" || InstrumentVersion_ID
--

CREATE TABLE InstVer_1 (
  InstrumentVersionData_ID int(11) NOT NULL auto_increment,

  InstrumentVersion_ID int(11) NOT NULL,
  InstrumentSession_ID int(11) NOT NULL,	-- this provides access to current status
  
  --
  -- Put one column per VarName, with type Text
  -- 
  
	PRIMARY KEY pk_InstrumentVersionData (InstrumentVersionData_ID)
) ENGINE=InnoDB;


--
-- FOREIGN KEY CONSTRAINTS
--

ALTER TABLE InstrumentVersion
  ADD CONSTRAINT InstrumentVersion_ibfk_1 FOREIGN KEY (Instrument_ID) REFERENCES Instrument (Instrument_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentVersion_ibfk_2 FOREIGN KEY (InstrumentHash_ID) REFERENCES InstrumentHash (InstrumentHash_ID) ON UPDATE CASCADE;

ALTER TABLE InstrumentHash
  ADD CONSTRAINT InstrumentHash_ibfk_1 FOREIGN KEY (LanguageList_ID) REFERENCES LanguageList (LanguageList_ID) ON UPDATE CASCADE;

ALTER TABLE InstrumentContent
  ADD CONSTRAINT InstrumentContent_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentContent_ibfk_2 FOREIGN KEY (Item_ID) REFERENCES Item (Item_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentContent_ibfk_3 FOREIGN KEY (VarName_ID) REFERENCES VarName (VarName_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentContent_ibfk_4 FOREIGN KEY (DisplayType_ID) REFERENCES DisplayType (DisplayType_ID) ON UPDATE CASCADE;

ALTER TABLE InstrumentHeader
  ADD CONSTRAINT InstrumentHeader_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentHeader_ibfk_2 FOREIGN KEY (ReservedWord_ID) REFERENCES ReservedWord (ReservedWord_ID) ON UPDATE CASCADE;
  
ALTER TABLE QuestionLocalized
  ADD CONSTRAINT QuestionLocalized_ibfk_1 FOREIGN KEY (Language_ID) REFERENCES Language (Language_ID) ON UPDATE CASCADE;
 
ALTER TABLE AnswerLocalized
  ADD CONSTRAINT AnswerLocalized_ibfk_1 FOREIGN KEY (Language_ID) REFERENCES Language (Language_ID) ON UPDATE CASCADE;
  
ALTER TABLE HelpLocalized
  ADD CONSTRAINT HelpLocalized_ibfk_1 FOREIGN KEY (Language_ID) REFERENCES Language (Language_ID) ON UPDATE CASCADE;
 
ALTER TABLE Item
  ADD CONSTRAINT Item_ibfk_1 FOREIGN KEY (Question_ID) REFERENCES QuestionLocalized (Question_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Item_ibfk_2 FOREIGN KEY (DataType_ID) REFERENCES DataType (DataType_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Item_ibfk_3 FOREIGN KEY (AnswerList_ID) REFERENCES AnswerList (AnswerList_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Item_ibfk_4 FOREIGN KEY (Validation_ID) REFERENCES Validation (Validation_ID) ON UPDATE CASCADE;
  
ALTER TABLE AnswerListContent
  ADD CONSTRAINT AnswerListContent_ibfk_1 FOREIGN KEY (AnswerList_ID) REFERENCES AnswerList (AnswerList_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT AnswerListContent_ibfk_2 FOREIGN KEY (Answer_ID) REFERENCES AnswerLocalized (Answer_ID) ON UPDATE CASCADE;
  
ALTER TABLE Datum
  ADD CONSTRAINT Datum_ibfk_1 FOREIGN KEY (InstrumentContent_ID) REFERENCES InstrumentContent (InstrumentContent_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Datum_ibfk_2 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Datum_ibfk_3 FOREIGN KEY (Item_ID) REFERENCES Item (Item_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Datum_ibfk_4 FOREIGN KEY (VarName_ID) REFERENCES VarName (VarName_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Datum_ibfk_5 FOREIGN KEY (Language_ID) REFERENCES Language (Language_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Datum_ibfk_6 FOREIGN KEY (PageUsage_ID) REFERENCES PageUsage (PageUsage_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Datum_ibfk_7 FOREIGN KEY (NullFlavor_ID) REFERENCES NullFlavor (NullFlavor_ID) ON UPDATE CASCADE;

ALTER TABLE InstrumentSession
  ADD CONSTRAINT InstrumentSession_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentSession_ibfk_2 FOREIGN KEY (ActionType_ID) REFERENCES ActionType (ActionType_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentSession_ibfk_3 FOREIGN KEY (User_ID) REFERENCES User (User_ID) ON UPDATE CASCADE;
  
ALTER TABLE PageUsage
  ADD CONSTRAINT PageUsage_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES InstrumentSession (InstrumentSession_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT PageUsage_ibfk_2 FOREIGN KEY (ActionType_ID) REFERENCES ActionType (ActionType_ID) ON UPDATE CASCADE;
  
ALTER TABLE PageUsageEvent
  ADD CONSTRAINT PageUsageEvent_ibfk_1 FOREIGN KEY (PageUsage_ID) REFERENCES PageUsage (PageUsage_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT PageUsageEvent_ibfk_2 FOREIGN KEY (VarName_ID) REFERENCES VarName (VarName_ID) ON UPDATE CASCADE;
  
ALTER TABLE ItemUsage
  ADD CONSTRAINT ItemUsage_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES InstrumentSession (InstrumentSession_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT ItemUsage_ibfk_2 FOREIGN KEY (VarName_ID) REFERENCES VarName (VarName_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT ItemUsage_ibfk_3 FOREIGN KEY (InstrumentContent_ID) REFERENCES InstrumentContent (InstrumentContent_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT ItemUsage_ibfk_4 FOREIGN KEY (Language_ID) REFERENCES Language (Language_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT ItemUsage_ibfk_5 FOREIGN KEY (NullFlavor_ID) REFERENCES NullFlavor (NullFlavor_ID) ON UPDATE CASCADE;

ALTER TABLE LOINC_ItemRequest
  ADD CONSTRAINT LOINC_ItemRequest_ibfk_1 FOREIGN KEY (Item_ID) REFERENCES Item (Item_ID) ON UPDATE CASCADE;

ALTER TABLE InstVer_1
  ADD CONSTRAINT InstVer_1_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstVer_1_ibfk_2 FOREIGN KEY (InstrumentSession_ID) REFERENCES InstrumentSession (InstrumentSession_ID) ON UPDATE CASCADE;

ALTER TABLE SemanticMapping
  ADD CONSTRAINT SemanticMapping_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT SemanticMapping_ibfk_2 FOREIGN KEY (Question_ID) REFERENCES QuestionLocalized (Question_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT SemanticMapping_ibfk_3 FOREIGN KEY (Answer_ID) REFERENCES AnswerLocalized (Answer_ID) ON UPDATE CASCADE;
