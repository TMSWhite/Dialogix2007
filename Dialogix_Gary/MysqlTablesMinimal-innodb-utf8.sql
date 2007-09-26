-- phpMyAdmin SQL Dump
-- version 2.10.3
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Sep 12, 2007 at 07:04 PM
-- Server version: 5.0.45
-- PHP Version: 5.2.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

DROP USER 'dialogix4'@'localhost';

CREATE USER 'dialogix4'@'localhost' IDENTIFIED BY 'dialogix4_pass';

GRANT USAGE ON * . * TO 'dialogix4'@'localhost' IDENTIFIED BY 'dialogix4_pass' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

DROP DATABASE `dialogix4`;

CREATE DATABASE IF NOT EXISTS `dialogix4` DEFAULT CHARSET=utf8;

GRANT ALL PRIVILEGES ON `dialogix4` . * TO 'dialogix4'@'localhost';

FLUSH PRIVILEGES ;

USE `dialogix4`;

-- --------------------------------------------------------

-- 
-- Table structure for table `instrument`
-- 

DROP TABLE IF EXISTS `instrument`;
CREATE TABLE IF NOT EXISTS `instrument` (
  `instrument_id` int(11) NOT NULL auto_increment,
  `instrument_name` varchar(120) NOT NULL,
  `instrument_description` mediumtext,
  PRIMARY KEY  (`instrument_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `instrument`
-- 

INSERT INTO `instrument` (`instrument_id`, `instrument_name`, `instrument_description`) VALUES 
(1, 'qam', '');

-- --------------------------------------------------------

-- 
-- Table structure for table `instrument_session`
-- 

DROP TABLE IF EXISTS `instrument_session`;
CREATE TABLE IF NOT EXISTS `instrument_session` (
  `instrument_session_id` int(11) NOT NULL auto_increment,
  `start_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL default '0000-00-00 00:00:00',
  `instrument_id` int(11) NOT NULL,
  `instrument_version_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `first_group` int(11) NOT NULL,
  `last_group` int(11) NOT NULL,
  `last_action` varchar(20) default NULL,
  `last_access` varchar(20) default NULL,
  `statusMsg` mediumtext,
  PRIMARY KEY  (`instrument_session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `instrument_session`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `instrument_version`
-- 

DROP TABLE IF EXISTS `instrument_version`;
CREATE TABLE IF NOT EXISTS `instrument_version` (
  `instrument_version_id` int(11) NOT NULL auto_increment,
  `instrument_id` int(11) NOT NULL,
  `instance_table_name` varchar(150) default NULL,
  `major_version` int(11) NOT NULL,
  `minor_version` int(11) NOT NULL,
  `instrument_notes` mediumtext,
  `instrument_status` int(11) default NULL,
  PRIMARY KEY  (`instrument_version_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `instrument_version`
-- 

INSERT INTO `instrument_version` (`instrument_version_id`, `instrument_id`, `instance_table_name`, `major_version`, `minor_version`, `instrument_notes`, `instrument_status`) VALUES 
(1, 1, 'qam', 1, 1, '', 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `pagehitevents`
-- 

DROP TABLE IF EXISTS `pagehitevents`;
CREATE TABLE IF NOT EXISTS `pagehitevents` (
  `pageHitEventsID` int(11) NOT NULL auto_increment,
  `pageHitID` int(11) NOT NULL default '0',
  `varName` varchar(40) NOT NULL default '',
  `actionType` varchar(18) NOT NULL default '',
  `eventType` varchar(18) NOT NULL default '',
  `timestamp` timestamp NULL default CURRENT_TIMESTAMP,
  `duration` int(11) NOT NULL default '0',
  `value1` varchar(50) NOT NULL default '',
  `value2` varchar(250) NOT NULL default '',
  PRIMARY KEY  (`pageHitEventsID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `pagehitevents`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `pagehits`
-- 

DROP TABLE IF EXISTS `pagehits`;
CREATE TABLE IF NOT EXISTS `pagehits` (
  `pageHitID` int(11) NOT NULL auto_increment,
  `instrument_session_id` int(11) NOT NULL,
  `timeStamp` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `accessCount` int(11) NOT NULL,
  `groupNum` int(11) NOT NULL,
  `displayNum` int(11) NOT NULL,
  `lastAction` varchar(15) default NULL,
  `statusMsg` varchar(35) NOT NULL,
  `totalDuration` int(10) unsigned default NULL,
  `serverDuration` int(10) unsigned default NULL,
  `loadDuration` int(10) unsigned default NULL,
  `networkDuration` int(10) unsigned default NULL,
  `pageVacillation` int(10) unsigned default NULL,
  PRIMARY KEY  (`pageHitID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `pagehits`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `qam`
-- 

DROP TABLE IF EXISTS `qam`;
CREATE TABLE IF NOT EXISTS `qam` (
  `ID` bigint(20) NOT NULL auto_increment,
  `InstrumentName` varchar(200) NOT NULL,
  `InstanceName` varchar(200) NOT NULL,
  `StartTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `qam_1` text,
  `qam_2` text,
  `qam_3` text,
  `qam_4` text,
  `qam_5` text,
  `qam_6` text,
  `qam_7` text,
  `qam_8` text,
  `qam_9` text,
  `qam_10` text,
  `end_time` timestamp NOT NULL default '0000-00-00 00:00:00',
  `first_group` int(11) default NULL,
  `last_group` int(11) default NULL,
  `last_action` text,
  `last_access` text,
  `status_message` text,
  `instrument_session_id` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `qam`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `rawdata`
-- 

DROP TABLE IF EXISTS `rawdata`;
CREATE TABLE IF NOT EXISTS `rawdata` (
  `RawDataID` bigint(20) NOT NULL auto_increment,
  `instrument_session_id` int(11) NOT NULL,
  `InstrumentName` varchar(200) NOT NULL,
  `InstanceName` varchar(200) NOT NULL,
  `VarName` varchar(100) NOT NULL,
  `VarNum` int(11) NOT NULL,
  `GroupNum` smallint(6) NOT NULL,
  `DisplayNum` smallint(6) NOT NULL,
  `LangNum` smallint(6) NOT NULL,
  `WhenAsMS` bigint(20) NOT NULL,
  `TimeStamp` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `AnswerType` int(4) NOT NULL,
  `Answer` text NOT NULL,
  `QuestionAsAsked` text NOT NULL,
  `itemVacillation` int(10) unsigned default NULL,
  `responseLatency` int(10) unsigned default NULL,
  `responseDuration` int(10) unsigned default NULL,
  `nullFlavor` int(10) unsigned default NULL,
  `Comment` text NOT NULL,
  PRIMARY KEY  (`RawDataID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Table structure for table `user_session`
-- 

DROP TABLE IF EXISTS `user_session`;
CREATE TABLE IF NOT EXISTS `user_session` (
  `user_session_id` int(11) NOT NULL auto_increment,
  `instrument_session_id` int(11) NOT NULL,
  `user_id` int(10) unsigned default NULL,
  `timestamp` timestamp NULL default NULL,
  `comments` mediumtext,
  `status` varchar(10) default NULL,
  PRIMARY KEY  (`user_session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `user_session`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `users`
-- 

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL auto_increment,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(80) NOT NULL,
  `phone` varchar(24) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `users`
-- 

INSERT INTO `users` (`id`, `user_name`, `password`, `first_name`, `last_name`, `email`, `phone`) VALUES 
(1, 'test', 'test', 'Test', 'User', '', '');


-- 
-- Table structure for table `instrumentcontents`
-- 

CREATE TABLE `instrumentcontents` (
  `ID` int(11) NOT NULL auto_increment,
  `InstrumentName` varchar(200) NOT NULL default '',
  `VarNum` smallint(6) NOT NULL default '0',
  `VarName` varchar(100) default NULL,
  `c8name` varchar(10) default NULL,
  `DisplayName` text,
  `GroupNum` smallint(6) NOT NULL default '0',
  `Concept` text,
  `Relevance` text,
  `ActionType` char(1) default NULL,
  `Validation` text,
  `ReturnType` varchar(10) default NULL,
  `MinVal` text,
  `MaxVal` text,
  `OtherVals` text,
  `InputMask` text,
  `FormatMask` text,
  `DisplayType` varchar(15) default NULL,
  `IsRequired` smallint(6) NOT NULL default '0',
  `isMessage` smallint(6) NOT NULL default '0',
  `LEVEL` varchar(10) default NULL,
  `SPSSformat` varchar(20) default NULL,
  `SASinformat` varchar(20) default NULL,
  `SASformat` varchar(20) default NULL,
  `AnswersNumeric` smallint(6) default NULL,
  `DefaultAnswer` text,
  `DefaultComment` text,
  `LOINCproperty` varchar(30) default NULL,
  `LOINCtimeAspect` varchar(15) default NULL,
  `LOINCsystem` varchar(100) default NULL,
  `LOINCscale` varchar(30) default NULL,
  `LOINCmethod` varchar(50) default NULL,
  `LOINC_NUM` varchar(10) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `InstrumentName` (`InstrumentName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- 
-- Table structure for table `instrumentheaders`
-- 

CREATE TABLE `instrumentheaders` (
  `ID` int(11) NOT NULL auto_increment,
  `instrument_version_id` int(200) NOT NULL,
  `ReservedVarName` varchar(100) NOT NULL default '',
  `Value` text NOT NULL,
  PRIMARY KEY  (`ID`),
  KEY `InstrumentName` (`instrument_version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- 
-- Table structure for table `instrumentmeta`
-- 

CREATE TABLE `instrumentmeta` (
  `instrument_meta_id` int(11) NOT NULL auto_increment,
  `instrument_version_id` int(11) NOT NULL default '0',
  `Title` text NOT NULL,
  `Version` varchar(20) NOT NULL default '',
  `CreationDate` date NOT NULL,
  `NumVars` smallint(6) NOT NULL default '0',
  `VarListMD5` varchar(35) NOT NULL default '',
  `InstrumentMD5` varchar(35) NOT NULL default '',
  `LanguageList` text NOT NULL,
  `NumLanguages` smallint(6) NOT NULL default '0',
  `NumInstructions` smallint(6) NOT NULL default '0',
  `NumEquations` smallint(6) NOT NULL default '0',
  `NumQuestions` smallint(6) NOT NULL default '0',
  `NumBranches` smallint(6) NOT NULL default '0',
  `NumTailorings` smallint(6) NOT NULL default '0',
  PRIMARY KEY  (`instrument_meta_id`),
  KEY `InstrumentVersion` (`instrument_version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- 
-- Table structure for table `instrumenttranslations`
-- 

CREATE TABLE `instrumenttranslations` (
  `ID` int(11) NOT NULL auto_increment,
  `InstrumentName` varchar(200) default NULL,
  `LanguageNum` smallint(6) NOT NULL default '0',
  `LanguageName` varchar(10) default NULL,
  `VarNum` smallint(6) NOT NULL default '0',
  `VarName` varchar(100) default NULL,
  `c8name` varchar(10) NOT NULL,
  `ActionType` char(1) default NULL,
  `Readback` text,
  `ActionPhrase` text,
  `DisplayType` varchar(15) default NULL,
  `AnswerOptions` text NOT NULL,
  `HelpURL` text,
  `QuestionLen` smallint(6) default '0',
  `AnswerLen` smallint(6) default '0',
  `QuestionMD5` varchar(35) default NULL,
  `AnswerMD5` varchar(35) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `InstrumentName` (`InstrumentName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


