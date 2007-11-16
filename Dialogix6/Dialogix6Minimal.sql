DROP DATABASE dialogix6;
CREATE DATABASE dialogix6 DEFAULT CHARSET=utf8;
USE dialogix6;

DROP USER 'dialogix6'@'localhost';
CREATE USER 'dialogix6'@'localhost' IDENTIFIED BY 'dialogix6_pass';
GRANT USAGE ON * . * TO 'dialogix6'@'localhost' IDENTIFIED BY 'dialogix6_pass' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;
GRANT ALL PRIVILEGES ON dialogix6 . * TO 'dialogix6'@'localhost';
FLUSH PRIVILEGES ;



CREATE TABLE V1_Item_Usage (
  V1ItemUsage_ID bigint(20) NOT NULL AUTO_INCREMENT,
	ItemUsageSequence int(11) NOT NULL,	-- order set within InstrumentSession
	V1InstrumentSession_ID int(15) NOT NULL,	
	VarName varchar(200) NOT NULL,
	DataElementSequence int(11) NOT NULL,	-- order set within InstrumentSession
  GroupNum int(15) NOT NULL,	-- current Group location within instrument (auto-derivable from Item?)
  DisplayNum int(11) NOT NULL,	-- current number of screens the user has seen
	LanguageCode char(2) default 'en', -- Language Used
	QuestionAsAsked text, -- parsed version of Question_ID
	AnswerCode text,	-- value entered.  If from an AnswerList, this is the numeric coded value
	AnswerString text, -- if from an AnswerList, this is the message seen in the current language
		
	Time_Stamp timestamp NOT NULL default CURRENT_TIMESTAMP,	-- so updated each time data is written?
  WhenAsMS bigint(20) NOT NULL,	-- absolute time? Time since start?  Needed?
	
  itemVacillation int(11) default NULL,
  responseLatency int(11) default NULL,
  responseDuration int(11) default NULL,
  
	Comments text default NULL,	-- optional Comments
  
	KEY k1_V1ItemUsage (LanguageCode),
	KEY k2_V1ItemUsage (VarName),
  PRIMARY KEY pk_V1ItemUsage (V1ItemUsage_ID)
) ENGINE=InnoDB;

CREATE TABLE V1_Instrument_Session (
  V1InstrumentSession_ID int(15) NOT NULL,
	InstrumentVersionName	varchar(400) NOT NULL,	-- this should include the Version number

  StartTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  LastAccessTime timestamp NOT NULL default '0000-00-00 00:00:00',	-- time of last access
  InstrumentStartingGroup int(11) NOT NULL,	-- starting location within instrument
  CurrentGroup int(11) NOT NULL,	-- current location within instrument
  DisplayNum int(11) NOT NULL,	-- current number of screens the user has seen
 	LanguageCode char(2) NOT NULL,	-- current language used for this instrument
  ActionType varchar(100) default NULL,	-- should this be internationalized?
  StatusMsg varchar(100),	-- what is this used for, if anything?

  InstrumentVersionFileName text default NULL, -- this is the full path (hack) of the .txt file which will be loaded at run-time
  InstrumentSessionFileName text default NULL, -- hack - the full path of the filename which is storing the data
  
	KEY k1_V1InstrumentSession (LanguageCode),
	KEY k2_InstrumentVersionName (InstrumentVersionName),
  PRIMARY KEY pk_V1InstrumentSession (V1InstrumentSession_ID)
) ENGINE=InnoDB;

CREATE TABLE V1_SEQUENCE_GENERATOR_TABLE (
  SEQUENCE_NAME VARCHAR(100) NOT NULL,
  SEQUENCE_VALUE INT(15) NOT NULL,
	PRIMARY KEY PK_V1_SEQUENCE_NAME (SEQUENCE_NAME)
) ENGINE=MyISAM;


ALTER TABLE V1_Item_Usage
  ADD CONSTRAINT V1ItemUsage_ibfk_1 FOREIGN KEY (V1InstrumentSession_ID) REFERENCES V1_Instrument_Session (V1InstrumentSession_ID);
  
INSERT INTO V1_SEQUENCE_GENERATOR_TABLE (SEQUENCE_NAME, SEQUENCE_VALUE) VALUES
('V1_Instrument_Session', 0),
('V1_Item_Usage', 0)
;  

