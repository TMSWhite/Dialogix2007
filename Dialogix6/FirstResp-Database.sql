DROP DATABASE FirstResp;
DROP USER 'FirstResp'@'localhost';


CREATE DATABASE FirstResp DEFAULT CHARSET=utf8;
USE FirstResp;

CREATE USER 'FirstResp'@'localhost' IDENTIFIED BY 'FirstResp_pass';
GRANT USAGE ON * . * TO 'FirstResp'@'localhost' IDENTIFIED BY 'FirstResp_pass' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;
GRANT ALL PRIVILEGES ON FirstResp . * TO 'FirstResp'@'localhost';
FLUSH PRIVILEGES ;

CREATE TABLE V1_Data_Element (
	V1DataElement_ID int(15) NOT NULL,
	V1InstrumentSession_ID int(15) NOT NULL,	
	VarName varchar(200) NOT NULL,
	DataElementSequence int(11) NOT NULL,	-- order set within InstrumentSession
  GroupNum int(11),	-- current Group location within instrument (auto-derivable from Item?)
  DisplayNum int(11) NOT NULL,	-- current number of screens the user has seen
	LanguageCode char(2) default 'en', -- Language Used
	QuestionAsAsked text, -- parsed version of Question_ID
	AnswerCode text,	-- value entered.  If from an AnswerList, this is the numeric coded value
	AnswerString text, -- if from an AnswerList, this is the message seen in the current language
		
	Time_Stamp timestamp default CURRENT_TIMESTAMP,	-- so updated each time data is written?
  WhenAsMS bigint(20) NOT NULL,	-- absolute time? Time since start?  Needed?
	
  itemVisits int(11) default NULL,	-- counts number of visits

  totalDuration int(11) default NULL, -- milliseconds between successive getPost() calls)
  pageDuration int(11) default NULL,	-- milliseconds spent on page from first Focus to Submit
  serverDuration int(11) default NULL, -- millisec spent on server side from getPost() to flushing the writer
  loadDuration int(11) default NULL,	-- millisec from when client starts procesing page to onLoad event
  networkDuration int(11) default NULL,	-- millisec network latency (totalDuration - prior pageDuration, serverDuration and loadDuration  
  
	Comments text default NULL,	-- optional Comments
	
	KEY k1_V1ItemUsage (LanguageCode),
	KEY k2_V1ItemUsage (VarName),
	PRIMARY KEY pk_V1DataElement (V1DataElement_ID)	
) ENGINE=MyISAM;


CREATE TABLE V1_Item_Usage (
  V1ItemUsage_ID bigint(20) NOT NULL,
	ItemUsageSequence int(11) NOT NULL,	-- order set within InstrumentSession
	V1InstrumentSession_ID int(15) NOT NULL,	
	VarName varchar(200) NOT NULL,
	DataElementSequence int(11) NOT NULL,	-- order set within InstrumentSession
  GroupNum int(11),	-- current Group location within instrument (auto-derivable from Item?)
  DisplayNum int(11) NOT NULL,	-- current number of screens the user has seen
	LanguageCode char(2) default 'en', -- Language Used
	QuestionAsAsked text, -- parsed version of Question_ID
	AnswerCode text,	-- value entered.  If from an AnswerList, this is the numeric coded value
	AnswerString text, -- if from an AnswerList, this is the message seen in the current language
		
	Time_Stamp timestamp default CURRENT_TIMESTAMP,	-- so updated each time data is written?
  WhenAsMS bigint(20) NOT NULL,	-- absolute time? Time since start?  Needed?
	
  itemVisits int(11) default NULL,	-- counts number of visits

  totalDuration int(11) default NULL, -- milliseconds between successive getPost() calls)
  pageDuration int(11) default NULL,	-- milliseconds spent on page from first Focus to Submit
  serverDuration int(11) default NULL, -- millisec spent on server side from getPost() to flushing the writer
  loadDuration int(11) default NULL,	-- millisec from when client starts procesing page to onLoad event
  networkDuration int(11) default NULL,	-- millisec network latency (totalDuration - prior pageDuration, serverDuration and loadDuration  
  
	Comments text default NULL,	-- optional Comments
	
	KEY k1_V1ItemUsage (LanguageCode),
	KEY k2_V1ItemUsage (VarName),
  PRIMARY KEY pk_V1ItemUsage (V1ItemUsage_ID)
) ENGINE=MyISAM;

CREATE TABLE V1_Instrument_Session (
  V1InstrumentSession_ID int(15) NOT NULL,
	InstrumentVersionName	varchar(400) NOT NULL,	-- this should include the Version number

  StartTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  LastAccessTime timestamp NOT NULL default '0000-00-00 00:00:00',	-- time of last access
  InstrumentStartingGroup int(11),	-- starting location within instrument
  CurrentGroup int(11),	-- current location within instrument
  DisplayNum int(11),	-- current number of screens the user has seen
 	LanguageCode char(2),	-- current language used for this instrument
  ActionType varchar(100),	-- should this be internationalized?
  StatusMsg varchar(100),	-- what is this used for, if anything?
  MaxGroup int(11), -- maximum Group number reached
  MaxVarNum int(11), -- maximum VarNum reached -- in case importing old data which lack Group information

  InstrumentVersionFileName text default NULL, -- this is the full path (hack) of the .txt file which will be loaded at run-time
  InstrumentSessionFileName text default NULL, -- hack - the full path of the filename which is storing the data
  
  NumVars int(11),	-- 
  VarListMD5 varchar(120),	-- 
  NumGroups int(11),	-- so if MaxGroup == NumGroups, have reached end
  Finished int(11),	-- really boolean.  if (MaxGroup == NumGroups, or lastVar reached, or Jar file created, then Finished = true)
  
	KEY k1_V1InstrumentSession (LanguageCode),
	KEY k2_InstrumentVersionName (InstrumentVersionName),
  PRIMARY KEY pk_V1InstrumentSession (V1InstrumentSession_ID)
) ENGINE=MyISAM;

CREATE TABLE V1_SEQUENCE_GENERATOR_TABLE (
  SEQUENCE_NAME VARCHAR(100) NOT NULL,
  SEQUENCE_VALUE INT(15) NOT NULL,
	PRIMARY KEY PK_V1_SEQUENCE_NAME (SEQUENCE_NAME)
) ENGINE=MyISAM;


INSERT INTO V1_SEQUENCE_GENERATOR_TABLE (SEQUENCE_NAME, SEQUENCE_VALUE) VALUES
('V1_Instrument_Session', 0),
('V1_Item_Usage', 0),
('V1_Data_Element', 0)
;  

