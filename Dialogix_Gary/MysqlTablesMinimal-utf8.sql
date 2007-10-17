-- phpMyAdmin SQL Dump
-- version 2.10.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 12, 2007 at 07:04 PM
-- Server version: 5.0.45
-- PHP Version: 5.2.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

DROP USER 'dialogix3'@'localhost';

CREATE USER 'dialogix3'@'localhost' IDENTIFIED BY 'dialogix3_pass';

GRANT USAGE ON * . * TO 'dialogix3'@'localhost' IDENTIFIED BY 'dialogix3_pass' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

DROP DATABASE `dialogix3`;

CREATE DATABASE IF NOT EXISTS `dialogix3` DEFAULT CHARSET=utf8;

GRANT ALL PRIVILEGES ON `dialogix3` . * TO 'dialogix3'@'localhost';

FLUSH PRIVILEGES ;

USE `dialogix3`;

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instrument`
-- 

INSERT INTO `instrument` (`instrument_id`, `instrument_name`, `instrument_description`) VALUES 
(1, 'qam', ''), 
(2, 'EnglishFrenchDemo', ''), 
(3, 'EnglishRussianFrenchDemo', ''), 
(4, 'EnglishRussianFrenchHebrew', '');

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
  `DisplayNum` int(11) NOT NULL,
  `last_action` varchar(35) default NULL,
  `statusMsg` varchar(35) NOT NULL,
  PRIMARY KEY  (`instrument_session_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
  `instrument_status` int(11) NOT NULL,
  PRIMARY KEY  (`instrument_version_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `instrument_version`
-- 

INSERT INTO `instrument_version` (`instrument_version_id`, `instrument_id`, `instance_table_name`, `major_version`, `minor_version`, `instrument_notes`, `instrument_status`) VALUES 
(1, 1, 'qam', 1, 0, '', 0),
(2, 2, 'EnglishFrenchDemo', 1, 0, '', 0),
(3, 3, 'EnglishRussianFrenchDemo', 1, 0, '', 0),
(4, 4, 'EnglishRussianFrenchHebrew', 1, 0, '', 0),
(5, 1, 'qam', 1, 1, '', 1),
(6, 2, 'EnglishFrenchDemo', 1, 1, '', 1),
(7, 3, 'EnglishRussianFrenchDemo', 1, 1, '', 1),
(8, 4, 'EnglishRussianFrenchHebrew', 1, 1, '', 1);


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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
  `StartingGroupNum` int(11) NOT NULL,
  `EndingGroupNum` int(11) NOT NULL,
  `DisplayNum` int(11) NOT NULL,
  `lastAction` varchar(35) default NULL,
  `statusMsg` varchar(35) NOT NULL,
  `totalDuration` int(11)  NOT NULL,
  `serverDuration` int(11)  NOT NULL,
  `loadDuration` int(11)  NOT NULL,
  `networkDuration` int(11)  NOT NULL,
  `pageVacillation` int(11)  NOT NULL,
  PRIMARY KEY  (`pageHitID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
  `StartTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
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
  `DisplayNum` int(11) NOT NULL,
  `first_group` int(11) NOT NULL,
  `last_group` int(11) NOT NULL,
  `last_action` varchar(35) default NULL,
  `statusMsg` varchar(35) NOT NULL,
  `instrument_session_id` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `qam`
-- 

-- 
-- Table structure for table `EnglishFrenchDemo`
-- 

DROP TABLE IF EXISTS `EnglishFrenchDemo`;
CREATE TABLE IF NOT EXISTS `EnglishFrenchDemo` (
  `ID` bigint(20) NOT NULL auto_increment,
  `InstrumentName` varchar(200) NOT NULL,
  `InstanceName` varchar(200) NOT NULL,
  `StartTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `hasChild` text,
  `q2` text,
  `male` text,
  `name` text,
  `demo5` text,
  `end_time` timestamp NOT NULL default '0000-00-00 00:00:00',
  `DisplayNum` int(11) NOT NULL,
  `first_group` int(11) NOT NULL,
  `last_group` int(11) NOT NULL,
  `last_action` varchar(35) default NULL,
  `statusMsg` varchar(35) NOT NULL,
  `instrument_session_id` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Table structure for table `EnglishRussianFrenchDemo`
-- 

DROP TABLE IF EXISTS `EnglishRussianFrenchDemo`;
CREATE TABLE IF NOT EXISTS `EnglishRussianFrenchDemo` (
  `ID` bigint(20) NOT NULL auto_increment,
  `InstrumentName` varchar(200) NOT NULL,
  `InstanceName` varchar(200) NOT NULL,
  `StartTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `hasChild` text,
  `q2` text,
  `male` text,
  `name` text,
  `demo5` text,
  `end_time` timestamp NOT NULL default '0000-00-00 00:00:00',
  `DisplayNum` int(11) NOT NULL,
  `first_group` int(11) NOT NULL,
  `last_group` int(11) NOT NULL,
  `last_action` varchar(35) default NULL,
  `statusMsg` varchar(35) NOT NULL,
  `instrument_session_id` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Table structure for table `EnglishRussianFrenchHebrew`
-- 

DROP TABLE IF EXISTS `EnglishRussianFrenchHebrew`;
CREATE TABLE IF NOT EXISTS `EnglishRussianFrenchHebrew` (
  `ID` bigint(20) NOT NULL auto_increment,
  `InstrumentName` varchar(200) NOT NULL,
  `InstanceName` varchar(200) NOT NULL,
  `StartTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `hasChild` text,
  `q2` text,
  `male` text,
  `name` text,
  `demo5` text,
  `end_time` timestamp NOT NULL default '0000-00-00 00:00:00',
  `DisplayNum` int(11) NOT NULL,
  `first_group` int(11) NOT NULL,
  `last_group` int(11) NOT NULL,
  `last_action` varchar(35) default NULL,
  `statusMsg` varchar(35) NOT NULL,
  `instrument_session_id` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
  `GroupNum` int(11) NOT NULL,
  `DisplayNum` int(11) NOT NULL,
  `LangNum` smallint(6) NOT NULL,
  `WhenAsMS` bigint(20) NOT NULL,
  `TimeStamp` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `AnswerType` int(4) NOT NULL,
  `Answer` text NOT NULL,
  `QuestionAsAsked` text NOT NULL,
  `itemVacillation` int(11)  NOT NULL,
  `responseLatency` int(11)  NOT NULL,
  `responseDuration` int(11)  NOT NULL,
  `nullFlavor` int(11)  NOT NULL,
  `Comment` text NOT NULL,
  PRIMARY KEY  (`RawDataID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



CREATE TABLE `ParserTest` (
  `equation` text NOT NULL,
  `result` text NOT NULL,
  `expected` text,
  `accurate` int NOT NULL,
  `when` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `ParserTest_ID` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`ParserTest_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

