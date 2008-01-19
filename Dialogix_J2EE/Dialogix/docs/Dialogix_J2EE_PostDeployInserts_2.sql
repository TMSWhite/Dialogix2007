-- 
-- Dumping data for table action_types
-- 

INSERT INTO action_types (action_type_id, name) VALUES (1, 'evaluate_expr');
INSERT INTO action_types (action_type_id, name) VALUES (2, 'finished');
INSERT INTO action_types (action_type_id, name) VALUES (3, 'jump_to');
INSERT INTO action_types (action_type_id, name) VALUES (4, 'jumpToFirstUnasked');
INSERT INTO action_types (action_type_id, name) VALUES (5, 'next');
INSERT INTO action_types (action_type_id, name) VALUES (6, 'previous');
INSERT INTO action_types (action_type_id, name) VALUES (7, 'refresh current');
INSERT INTO action_types (action_type_id, name) VALUES (8, 'reload_questions');
INSERT INTO action_types (action_type_id, name) VALUES (9, 'restart_clean');
INSERT INTO action_types (action_type_id, name) VALUES (10, 'RESTORE');
INSERT INTO action_types (action_type_id, name) VALUES (11, 'RESTORE_FROM_FLOPPY');
INSERT INTO action_types (action_type_id, name) VALUES (12, 'save_to');
INSERT INTO action_types (action_type_id, name) VALUES (13, 'select_new_interview');
INSERT INTO action_types (action_type_id, name) VALUES (14, 'show_Syntax_Errors');
INSERT INTO action_types (action_type_id, name) VALUES (15, 'sign_schedule');
INSERT INTO action_types (action_type_id, name) VALUES (16, 'START');
INSERT INTO action_types (action_type_id, name) VALUES (17, 'suspendToFloppy');
INSERT INTO action_types (action_type_id, name) VALUES (18, 'toggle_EventCollection');
INSERT INTO action_types (action_type_id, name) VALUES (19, 'turn_debugMode');
INSERT INTO action_types (action_type_id, name) VALUES (20, 'turn_developerMode');
INSERT INTO action_types (action_type_id, name) VALUES (21, 'turn_showQuestionNum');

-- 
-- Dumping data for table code_systems
-- 

INSERT INTO code_systems (code_system_id, name, code_system_oid) VALUES (1, 'LOINC', '2.16.840.1.113883.6.1');
INSERT INTO code_systems (code_system_id, name, code_system_oid) VALUES (2, 'SNOMED-CT', '2.16.840.1.113883.6.96');

-- 
-- Dumping data for table data_types
-- 

INSERT INTO data_types (data_type_id, name) VALUES (6, 'number');
INSERT INTO data_types (data_type_id, name) VALUES (7, 'string');
INSERT INTO data_types (data_type_id, name) VALUES (8, 'date');
INSERT INTO data_types (data_type_id, name) VALUES (9, 'time');
INSERT INTO data_types (data_type_id, name) VALUES (10, 'year');
INSERT INTO data_types (data_type_id, name) VALUES (11, 'month');
INSERT INTO data_types (data_type_id, name) VALUES (12, 'day');
INSERT INTO data_types (data_type_id, name) VALUES (13, 'weekday');
INSERT INTO data_types (data_type_id, name) VALUES (14, 'hour');
INSERT INTO data_types (data_type_id, name) VALUES (15, 'minute');
INSERT INTO data_types (data_type_id, name) VALUES (16, 'second');
INSERT INTO data_types (data_type_id, name) VALUES (17, 'month_num');
INSERT INTO data_types (data_type_id, name) VALUES (18, 'day_num');

-- 
-- Dumping data for table 'display_types'
-- 

INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (1, 'best32.', 'best12.', 'nothing', 'NOMINAL', 'F8.0', 'NOM', 0, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (2, 'best32.', 'best12.', 'radio', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (3, 'best32.', 'best12.', 'check', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (4, 'best32.', 'best12.', 'combo', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (5, 'best32.', 'best12.', 'list', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (6, '$100.', '$100.', 'text', 'NOMINAL', 'A100', 'NAR', 0, 7);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (7, 'best32.', 'best12.', 'double', 'SCALE', 'F8.3', 'QN', 0, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (8, 'best32.', 'best12.', 'radio2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (9, '$50.', '$50.', 'password', 'NOMINAL', 'A50', 'NAR', 0, 7);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (10, '$254.', '$254.', 'memo', 'NOMINAL', 'A254', 'NAR', 0, 7);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (11, 'mmddyy10.', 'mmddyy10.', 'date', 'SCALE', 'ADATE10', 'QN', 0, 8);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (12, 'time8.0', 'time8.0', 'time', 'SCALE', 'TIME5.3', 'QN', 0, 9);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (13, 'best32.', 'best12.', 'year', 'SCALE', 'date|yyyy', 'QN', 0, 10);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (14, '$10.', '$10.', 'month', 'SCALE', 'MONTH', 'QN', 0, 11);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (15, '$6.', '$6.', 'day', 'SCALE', 'date|dd', 'QN', 0, 12);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (16, '$10.', '$10.', 'weekday', 'SCALE', 'WKDAY', 'QN', 0, 13);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (17, 'best32.', 'hour2.', 'hour', 'SCALE', 'date|hh', 'QN', 0, 14);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (18, 'best32.', 'best12.', 'minute', 'SCALE', 'date|mm', 'QN', 0, 15);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (19, 'best32.', 'best12.', 'second', 'SCALE', 'date|ss', 'QN', 0, 16);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (20, 'best32.', 'best12.', 'month_num', 'SCALE', 'date|mm', 'QN', 0, 17);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (21, 'best32.', 'day2.', 'day_num', 'SCALE', 'date|dd', 'QN', 0, 18);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (22, 'best32.', 'best12.', 'radio3', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (23, 'best32.', 'best12.', 'combo2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (display_type_id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (24, 'best32.', 'best12.', 'list2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);

-- 
-- Dumping data for table null_flavors
-- 

INSERT INTO null_flavors (null_flavor_id, name, display_name, description) VALUES (1, '*UNASKED*', '*UNASKED*', NULL);
INSERT INTO null_flavors (null_flavor_id, name, display_name, description) VALUES (2, '*NA*', '*NA*', NULL);
INSERT INTO null_flavors (null_flavor_id, name, display_name, description) VALUES (3, '*REFUSED*', '*REFUSED*', NULL);
INSERT INTO null_flavors (null_flavor_id, name, display_name, description) VALUES (4, '*INVALID*', '*INVALID*', NULL);
INSERT INTO null_flavors (null_flavor_id, name, display_name, description) VALUES (5, '*UNKNOWN*', '*UNKNOWN*', NULL);
INSERT INTO null_flavors (null_flavor_id, name, display_name, description) VALUES (6, '*HUH*', '*HUH*', NULL);

-- 
-- Dumping data for table reserved_words
-- 

INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (0, '__LANGUAGES__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (1, '__TITLE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (2, '__ICON__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (3, '__HEADER_MSG__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (4, '__STARTING_STEP__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (6, '__SHOW_QUESTION_REF__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (7, '__AUTOGEN_OPTION_NUM__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (8, '__DEVELOPER_MODE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (9, '__DEBUG_MODE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (10, '__START_TIME__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (11, '__FILENAME__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (12, '__SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (14, '__ALLOW_COMMENTS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (15, '__SCHEDULE_SOURCE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (16, '__LOADED_FROM__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (17, '__CURRENT_LANGUAGE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (19, '__ALLOW_REFUSED__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (20, '__ALLOW_UNKNOWN__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (21, '__ALLOW_DONT_UNDERSTAND__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (22, '__RECORD_EVENTS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (23, '__WORKING_DIR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (24, '__COMPLETED_DIR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (25, '__FLOPPY_DIR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (26, '__IMAGE_FILES_DIR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (27, '__COMMENT_ICON_ON__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (28, '__COMMENT_ICON_OFF__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (29, '__REFUSED_ICON_ON__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (30, '__REFUSED_ICON_OFF__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (31, '__UNKNOWN_ICON_ON__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (32, '__UNKNOWN_ICON_OFF__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (35, '__TRICEPS_VERSION_MAJOR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (36, '__TRICEPS_VERSION_MINOR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (37, '__SCHED_AUTHORS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (38, '__SCHED_VERSION_MAJOR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (39, '__SCHED_VERSION_MINOR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (40, '__SCHED_HELP_URL__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (41, '__HELP_ICON__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (42, '__ACTIVE_BUTTON_PREFIX__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (44, '__TRICEPS_FILE_TYPE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (45, '__DISPLAY_COUNT__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (46, '__SCHEDULE_DIR__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (47, '__ALLOW_JUMP_TO__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (48, '__BROWSER_TYPE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (49, '__IP_ADDRESS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (50, '__SUSPEND_TO_FLOPPY__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (51, '__JUMP_TO_FIRST_UNASKED__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (52, '__REDIRECT_ON_FINISH_URL__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (53, '__REDIRECT_ON_FINISH_MSG__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (56, '__SET_DEFAULT_FOCUS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (59, '__WRAP_ADMIN_ICONS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (60, '__DISALLOW_COMMENTS__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (61, '__CONNECTION_TYPE__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__', NULL);
INSERT INTO reserved_words (reserved_word_id, name, meaning) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__', NULL);


-- 
-- Dumping data for table var_names
-- 

INSERT INTO var_names (var_name_id, name) VALUES (0, '__LANGUAGES__');
INSERT INTO var_names (var_name_id, name) VALUES (1, '__TITLE__');
INSERT INTO var_names (var_name_id, name) VALUES (2, '__ICON__');
INSERT INTO var_names (var_name_id, name) VALUES (3, '__HEADER_MSG__');
INSERT INTO var_names (var_name_id, name) VALUES (4, '__STARTING_STEP__');
INSERT INTO var_names (var_name_id, name) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__');
INSERT INTO var_names (var_name_id, name) VALUES (6, '__SHOW_QUESTION_REF__');
INSERT INTO var_names (var_name_id, name) VALUES (7, '__AUTOGEN_OPTION_NUM__');
INSERT INTO var_names (var_name_id, name) VALUES (8, '__DEVELOPER_MODE__');
INSERT INTO var_names (var_name_id, name) VALUES (9, '__DEBUG_MODE__');
INSERT INTO var_names (var_name_id, name) VALUES (10, '__START_TIME__');
INSERT INTO var_names (var_name_id, name) VALUES (11, '__FILENAME__');
INSERT INTO var_names (var_name_id, name) VALUES (12, '__SHOW_ADMIN_ICONS__');
INSERT INTO var_names (var_name_id, name) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__');
INSERT INTO var_names (var_name_id, name) VALUES (14, '__ALLOW_COMMENTS__');
INSERT INTO var_names (var_name_id, name) VALUES (15, '__SCHEDULE_SOURCE__');
INSERT INTO var_names (var_name_id, name) VALUES (16, '__LOADED_FROM__');
INSERT INTO var_names (var_name_id, name) VALUES (17, '__CURRENT_LANGUAGE__');
INSERT INTO var_names (var_name_id, name) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__');
INSERT INTO var_names (var_name_id, name) VALUES (19, '__ALLOW_REFUSED__');
INSERT INTO var_names (var_name_id, name) VALUES (20, '__ALLOW_UNKNOWN__');
INSERT INTO var_names (var_name_id, name) VALUES (21, '__ALLOW_DONT_UNDERSTAND__');
INSERT INTO var_names (var_name_id, name) VALUES (22, '__RECORD_EVENTS__');
INSERT INTO var_names (var_name_id, name) VALUES (23, '__WORKING_DIR__');
INSERT INTO var_names (var_name_id, name) VALUES (24, '__COMPLETED_DIR__');
INSERT INTO var_names (var_name_id, name) VALUES (25, '__FLOPPY_DIR__');
INSERT INTO var_names (var_name_id, name) VALUES (26, '__IMAGE_FILES_DIR__');
INSERT INTO var_names (var_name_id, name) VALUES (27, '__COMMENT_ICON_ON__');
INSERT INTO var_names (var_name_id, name) VALUES (28, '__COMMENT_ICON_OFF__');
INSERT INTO var_names (var_name_id, name) VALUES (29, '__REFUSED_ICON_ON__');
INSERT INTO var_names (var_name_id, name) VALUES (30, '__REFUSED_ICON_OFF__');
INSERT INTO var_names (var_name_id, name) VALUES (31, '__UNKNOWN_ICON_ON__');
INSERT INTO var_names (var_name_id, name) VALUES (32, '__UNKNOWN_ICON_OFF__');
INSERT INTO var_names (var_name_id, name) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__');
INSERT INTO var_names (var_name_id, name) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__');
INSERT INTO var_names (var_name_id, name) VALUES (35, '__TRICEPS_VERSION_MAJOR__');
INSERT INTO var_names (var_name_id, name) VALUES (36, '__TRICEPS_VERSION_MINOR__');
INSERT INTO var_names (var_name_id, name) VALUES (37, '__SCHED_AUTHORS__');
INSERT INTO var_names (var_name_id, name) VALUES (38, '__SCHED_VERSION_MAJOR__');
INSERT INTO var_names (var_name_id, name) VALUES (39, '__SCHED_VERSION_MINOR__');
INSERT INTO var_names (var_name_id, name) VALUES (40, '__SCHED_HELP_URL__');
INSERT INTO var_names (var_name_id, name) VALUES (41, '__HELP_ICON__');
INSERT INTO var_names (var_name_id, name) VALUES (42, '__ACTIVE_BUTTON_PREFIX__');
INSERT INTO var_names (var_name_id, name) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__');
INSERT INTO var_names (var_name_id, name) VALUES (44, '__TRICEPS_FILE_TYPE__');
INSERT INTO var_names (var_name_id, name) VALUES (45, '__DISPLAY_COUNT__');
INSERT INTO var_names (var_name_id, name) VALUES (46, '__SCHEDULE_DIR__');
INSERT INTO var_names (var_name_id, name) VALUES (47, '__ALLOW_JUMP_TO__');
INSERT INTO var_names (var_name_id, name) VALUES (48, '__BROWSER_TYPE__');
INSERT INTO var_names (var_name_id, name) VALUES (49, '__IP_ADDRESS__');
INSERT INTO var_names (var_name_id, name) VALUES (50, '__SUSPEND_TO_FLOPPY__');
INSERT INTO var_names (var_name_id, name) VALUES (51, '__JUMP_TO_FIRST_UNASKED__');
INSERT INTO var_names (var_name_id, name) VALUES (52, '__REDIRECT_ON_FINISH_URL__');
INSERT INTO var_names (var_name_id, name) VALUES (53, '__REDIRECT_ON_FINISH_MSG__');
INSERT INTO var_names (var_name_id, name) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__');
INSERT INTO var_names (var_name_id, name) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__');
INSERT INTO var_names (var_name_id, name) VALUES (56, '__SET_DEFAULT_FOCUS__');
INSERT INTO var_names (var_name_id, name) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__');
INSERT INTO var_names (var_name_id, name) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__');
INSERT INTO var_names (var_name_id, name) VALUES (59, '__WRAP_ADMIN_ICONS__');
INSERT INTO var_names (var_name_id, name) VALUES (60, '__DISALLOW_COMMENTS__');
INSERT INTO var_names (var_name_id, name) VALUES (61, '__CONNECTION_TYPE__');
INSERT INTO var_names (var_name_id, name) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__');
INSERT INTO var_names (var_name_id, name) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__');


UPDATE sequence SET seq_count = '0';

UPDATE sequence SET seq_count = '100' WHERE seq_name = 'var_name';

ALTER TABLE v1_data_elements ADD INDEX ( var_name ) ;

ALTER TABLE v1_item_usages ADD INDEX ( language_code ) ;

ALTER TABLE v1_instrument_sessions ADD INDEX ( language_code ) ;


CREATE TABLE entry_answers (
  entry_answer_id bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  answer_code varchar(255) default NULL,
  created_on timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  updated_on timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (entry_answer_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

CREATE TABLE entry_answers_entry_items (
  entry_answer_id bigint(20) NOT NULL auto_increment,
  entry_item_id bigint(20) NOT NULL,
  PRIMARY KEY  (entry_answer_id,entry_item_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE entry_instruments (
  entry_instrument_id bigint(20) NOT NULL auto_increment,
  version varchar(255) NOT NULL,
  instrument_description mediumtext,
  `name` varchar(255) NOT NULL,
  created_on timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  updated_on timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (entry_instrument_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

CREATE TABLE entry_items (
  entry_item_id bigint(20) NOT NULL auto_increment,
  entry_instrument_id bigint(20) NOT NULL,
  display_type_id int(11) default NULL,
  `name` varchar(255) default NULL,
  created_on timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  updated_on timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (entry_item_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

CREATE TABLE entry_items_entry_questions (
  entry_item_id bigint(20) NOT NULL auto_increment,
  entry_question_id bigint(20) NOT NULL,
  PRIMARY KEY  (entry_item_id,entry_question_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE entry_questions (
  entry_question_id bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  created_on timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  updated_on timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (entry_question_id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;
