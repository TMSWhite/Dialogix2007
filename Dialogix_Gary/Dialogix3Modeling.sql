
DROP DATABASE innodb2;
CREATE DATABASE innodb2 DEFAULT CHARSET=utf8;
USE innodb2;

--
-- GLOBAL DEFINITION OF INSTRUMENTS
--

CREATE TABLE Instrument (
	Instrument_ID	int(11)	NOT NULL auto_increment,
	name	varchar(120) NOT NULL,
	description	mediumtext,
  PRIMARY KEY  (Instrument_ID)
) ENGINE=InnoDB;	

CREATE TABLE InstrumentVersion (
  InstrumentVersion_ID int(11) NOT NULL auto_increment,
  Instrument_ID int(11) NOT NULL,
  major_version int(11) NOT NULL,
  minor_version int(11) NOT NULL,
  instrument_notes mediumtext,
  instrument_status int default NULL,
  CreationDate date NOT NULL,
  NumVars smallint(6) NOT NULL default '0',
  VarListMD5 varchar(35) NOT NULL default '',
  InstrumentMD5 varchar(35) NOT NULL default '',
  LanguageList text NOT NULL,
  NumLanguages smallint(6) NOT NULL default '0',
  NumInstructions smallint(6) NOT NULL default '0',
  NumEquations smallint(6) NOT NULL default '0',
  NumQuestions smallint(6) NOT NULL default '0',
  NumBranches smallint(6) NOT NULL default '0',
  NumTailorings smallint(6) NOT NULL default '0',	  
  PRIMARY KEY  (InstrumentVersion_ID),
  KEY (Instrument_ID)
) ENGINE=InnoDB;

CREATE TABLE InstrumentContent (
	InstrumentContent_ID	int(11)	NOT NULL auto_increment,
	InstrumentVersion_ID int(11) NOT NULL,
	Item_ID int(11) NOT NULL,
	displayOrder int(11) NOT NULL,
  VarNum smallint(6) NOT NULL default '0',
  VarName varchar(100) default NULL,
  c8name varchar(10) default NULL,
  DisplayName text,
  GroupNum smallint(6) NOT NULL default '0',
  Concept text,
  Relevance text NOT NULL,
  ActionType char(1) default NULL,
  Validation text,
  ReturnType varchar(10) default NULL,
  MinVal text,
  MaxVal text,
  OtherVals text,
  InputMask text,
  FormatMask text,
  DisplayType varchar(15) default NULL,
  IsRequired smallint(6) NOT NULL default '0',
  isMessage smallint(6) NOT NULL default '0',
  SPSSformat varchar(20) default NULL,
  SASinformat varchar(20) default NULL,
  SASformat varchar(20) default NULL,
  DefaultAnswer text,
  DefaultComment text,	
  PRIMARY KEY  (InstrumentContent_ID),
	KEY (InstrumentVersion_ID, Item_ID, displayOrder)
) ENGINE=InnoDB;

CREATE TABLE InstrumentHeader (
  InstrumentHeader_ID int(11) NOT NULL auto_increment,
  InstrumentVersion_ID int(11) NOT NULL,
  ReservedVarName varchar(100) NOT NULL default '',
  Value text NOT NULL,
  PRIMARY KEY  (InstrumentHeader_ID),
  KEY (InstrumentVersion_ID)
) ENGINE=InnoDB;

CREATE TABLE InstrumentTranslation (
  InstrumentTranslation_ID int(11) NOT NULL auto_increment,
	InstrumentVersion_ID int(11) NOT NULL,
  Language_ID INT(11) NOT NULL,
  VarNum smallint(6) NOT NULL default '0',
  VarName varchar(100) default NULL,
  c8name varchar(10) NOT NULL,
  ActionType char(1) default NULL,
  Readback text,
  ActionPhrase text,
  DisplayType varchar(15) default NULL,
  AnswerOptions text NOT NULL,
  HelpURL text,
  QuestionLen smallint(6) default '0',
  AnswerLen smallint(6) default '0',
  QuestionMD5 varchar(35) default NULL,
  AnswerMD5 varchar(35) default NULL,
  PRIMARY KEY  (InstrumentTranslation_ID),
  KEY (InstrumentVersion_ID, Language_ID)
) ENGINE=InnoDB;

CREATE TABLE Language (
	Language_ID	int(11)	NOT NULL auto_increment,
	JavaAbbreviation varchar(10)  NOT NULL,
	Meaning text NOT NULL,
	PRIMARY KEY (Language_ID)
) ENGINE=InnoDB;

CREATE TABLE Item (
	Item_ID	int(11)	NOT NULL auto_increment,
	Question_ID	int(11)	NOT NULL,
	AnswerList_ID	int(11)	NOT NULL,
  PRIMARY KEY  (Item_ID),
  KEY (Question_ID, AnswerList_ID)
) ENGINE=InnoDB;
 
CREATE TABLE UniqueString (
	UniqueString_ID	int(11)	NOT NULL auto_increment,
	string text NOT NULL,
  PRIMARY KEY  (UniqueString_ID)
) ENGINE=InnoDB;

CREATE TABLE Question (
	Question_ID	int(11)	NOT NULL auto_increment,
	UniqueString_ID	int(11) NOT NULL,
  PRIMARY KEY  (Question_ID)
) ENGINE=InnoDB;

CREATE TABLE Answer (
	Answer_ID	int(11)	NOT NULL auto_increment,
	UniqueString_ID	int(11) NOT NULL,
  PRIMARY KEY  (Answer_ID)
) ENGINE=InnoDB;

CREATE TABLE AnswerList (
	AnswerList_ID	int(11)	NOT NULL auto_increment,
	description text,
  PRIMARY KEY  (AnswerList_ID)
) ENGINE=InnoDB;

CREATE TABLE AnswerListContent (
	AnswerListContent_ID	int(11)	NOT NULL auto_increment,
	AnswerList_ID int(11) NOT NULL,
	Answer_ID int(11) NOT NULL,
	displayOrder int(11) NOT NULL,
	val varchar(20) NOT NULL,
  PRIMARY KEY  (AnswerListContent_ID),
	KEY (AnswerList_ID, Answer_ID, displayOrder)
) ENGINE=InnoDB;

--
-- DATA COLLECTED USING INSTRUMENTS
--

CREATE TABLE InstrumentSession (
  InstrumentSession_ID int(11) NOT NULL auto_increment,
  InstrumentVersion_ID int(11) NOT NULL,
  start_time timestamp NOT NULL default CURRENT_TIMESTAMP,
  end_time timestamp NOT NULL default '0000-00-00 00:00:00',
  User_ID int(11) NOT NULL,
  first_group int(11) NOT NULL,
  last_group int(11) NOT NULL,
  last_action varchar(20) default NULL,
  last_access varchar(20) default NULL,
  statusMsg mediumtext,
  PRIMARY KEY  (InstrumentSession_ID),
  KEY (InstrumentVersion_ID, User_ID)
) ENGINE=InnoDB;

CREATE TABLE PageHit (
  PageHit_ID int(11) NOT NULL auto_increment,
  InstrumentSession_ID int(11) NOT NULL,
  time_stamp timestamp NOT NULL default CURRENT_TIMESTAMP,
  accessCount int(11) NOT NULL,
  groupNum int(11) NOT NULL,
  displayNum int(11) NOT NULL,
  lastAction varchar(15) default NULL,
  statusMsg varchar(35) NOT NULL,
  totalDuration int(10) unsigned default NULL,
  serverDuration int(10) unsigned default NULL,
  loadDuration int(10) unsigned default NULL,
  networkDuration int(10) unsigned default NULL,
  pageVacillation int(10) unsigned default NULL,
  PRIMARY KEY  (PageHit_ID),
  KEY (InstrumentSession_ID)
) ENGINE=InnoDB;

CREATE TABLE PageHitEvent (
  PageHitEvent_ID int(11) NOT NULL auto_increment,
  PageHit_ID int(11) NOT NULL default '0',
  VarName varchar(100) NOT NULL,
  actionType varchar(18) NOT NULL default '',
  eventType varchar(18) NOT NULL default '',
  time_stamp timestamp NULL default CURRENT_TIMESTAMP,
  duration int(11) NOT NULL default '0',
  value1 varchar(50) NOT NULL default '',
  value2 varchar(250) NOT NULL default '',
  PRIMARY KEY  (PageHitEvent_ID),
  KEY (PageHit_ID)
) ENGINE=InnoDB;

CREATE TABLE RawData (
  RawData_ID bigint(20) NOT NULL auto_increment,
  InstrumentSession_ID int(11) NOT NULL,
  VarName varchar(100) NOT NULL,
  VarNum int(11) NOT NULL,
  GroupNum smallint(6) NOT NULL,
  DisplayNum smallint(6) NOT NULL,
  LangNum smallint(6) NOT NULL,
  WhenAsMS bigint(20) NOT NULL,
  time_stamp timestamp NOT NULL default CURRENT_TIMESTAMP,
  AnswerType int(4) NOT NULL,
  Answer text NOT NULL,
  QuestionAsAsked text NOT NULL,
  itemVacillation int(10) unsigned default NULL,
  responseLatency int(10) unsigned default NULL,
  responseDuration int(10) unsigned default NULL,
  nullFlavor int(10) unsigned default NULL,
  Comment text NOT NULL,
  PRIMARY KEY  (RawData_ID),
  KEY (InstrumentSession_ID)
) ENGINE=InnoDB;

CREATE TABLE UserSession (
  UserSession_ID int(11) NOT NULL auto_increment,
  InstrumentSession_ID int(11) NOT NULL,
  User_ID int(10) unsigned default NULL,
  time_stamp timestamp NULL default NULL,
  comments mediumtext,
  status varchar(10) default NULL,
  PRIMARY KEY  (UserSession_ID),
  KEY (InstrumentSession_ID),
  KEY (User_ID)
) ENGINE=InnoDB;

CREATE TABLE Users (
  User_ID int(11) NOT NULL auto_increment,
  user_name varchar(20) NOT NULL,
  pwd varchar(20) NOT NULL,
  first_name varchar(30) NOT NULL,
  last_name varchar(30) NOT NULL,
  email varchar(80) NOT NULL,
  phone varchar(24) NOT NULL,
  PRIMARY KEY  (User_ID)
) ENGINE=InnoDB;

--
-- FOREIGN KEY CONSTRAINTS
--

ALTER TABLE Answer
  ADD CONSTRAINT Answer_ibfk_1 FOREIGN KEY (UniqueString_ID) REFERENCES UniqueString (UniqueString_ID) ON UPDATE CASCADE;
  
ALTER TABLE Question
  ADD CONSTRAINT Question_ibfk_1 FOREIGN KEY (UniqueString_ID) REFERENCES UniqueString (UniqueString_ID) ON UPDATE CASCADE;
  
ALTER TABLE AnswerListContent
  ADD CONSTRAINT AnswerListContent_ibfk_1 FOREIGN KEY (AnswerList_ID) REFERENCES AnswerList (AnswerList_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT AnswerListContent_ibfk_2 FOREIGN KEY (Answer_ID) REFERENCES Answer (Answer_ID) ON UPDATE CASCADE;
 
ALTER TABLE Item
  ADD CONSTRAINT Item_ibfk_1 FOREIGN KEY (Question_ID) REFERENCES Question (Question_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Item_ibfk_2 FOREIGN KEY (AnswerList_ID) REFERENCES AnswerList (AnswerList_ID) ON UPDATE CASCADE;
  
ALTER TABLE InstrumentContent
  ADD CONSTRAINT InstrumentContent_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT InstrumentContent_ibfk_2 FOREIGN KEY (Item_ID) REFERENCES Item (Item_ID) ON UPDATE CASCADE;
  
ALTER TABLE InstrumentHeader
  ADD CONSTRAINT InstrumentHeader_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE;
  
ALTER TABLE InstrumentTranslation
  ADD CONSTRAINT InstrumentTranslation_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT Language_ibfk_2 FOREIGN KEY (Language_ID) REFERENCES Language (Language_ID) ON UPDATE CASCADE;

ALTER TABLE InstrumentVersion
  ADD CONSTRAINT InstrumentVersion_ibfk_1 FOREIGN KEY (Instrument_ID) REFERENCES Instrument (Instrument_ID) ON UPDATE CASCADE;

ALTER TABLE InstrumentSession
  ADD CONSTRAINT InstrumentSession_ibfk_1 FOREIGN KEY (InstrumentVersion_ID) REFERENCES InstrumentVersion (InstrumentVersion_ID) ON UPDATE CASCADE;

ALTER TABLE PageHit
  ADD CONSTRAINT PageHit_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES InstrumentSession (InstrumentSession_ID) ON UPDATE CASCADE;
	
ALTER TABLE PageHitEvent
  ADD CONSTRAINT PageHitEvent_ibfk_1 FOREIGN KEY (PageHit_ID) REFERENCES PageHit (PageHit_ID) ON UPDATE CASCADE;
  
ALTER TABLE RawData
  ADD CONSTRAINT RawData_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES InstrumentSession (InstrumentSession_ID) ON UPDATE CASCADE;

ALTER TABLE UserSession
  ADD CONSTRAINT UserSession_ibfk_1 FOREIGN KEY (InstrumentSession_ID) REFERENCES InstrumentSession (InstrumentSession_ID) ON UPDATE CASCADE,
  ADD CONSTRAINT UserSession_ibfk_2 FOREIGN KEY (User_ID) REFERENCES Users (User_ID) ON UPDATE CASCADE;

