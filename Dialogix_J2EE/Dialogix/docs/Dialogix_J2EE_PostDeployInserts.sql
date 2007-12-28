-- 
-- Dumping data for table action_type
-- 

INSERT INTO action_type (action_type_id, ActionName) VALUES (1, 'evaluate_expr');
INSERT INTO action_type (action_type_id, ActionName) VALUES (2, 'finished');
INSERT INTO action_type (action_type_id, ActionName) VALUES (3, 'jump_to');
INSERT INTO action_type (action_type_id, ActionName) VALUES (4, 'jumpToFirstUnasked');
INSERT INTO action_type (action_type_id, ActionName) VALUES (5, 'next');
INSERT INTO action_type (action_type_id, ActionName) VALUES (6, 'previous');
INSERT INTO action_type (action_type_id, ActionName) VALUES (7, 'refresh current');
INSERT INTO action_type (action_type_id, ActionName) VALUES (8, 'reload_questions');
INSERT INTO action_type (action_type_id, ActionName) VALUES (9, 'restart_clean');
INSERT INTO action_type (action_type_id, ActionName) VALUES (10, 'RESTORE');
INSERT INTO action_type (action_type_id, ActionName) VALUES (11, 'RESTORE_FROM_FLOPPY');
INSERT INTO action_type (action_type_id, ActionName) VALUES (12, 'save_to');
INSERT INTO action_type (action_type_id, ActionName) VALUES (13, 'select_new_interview');
INSERT INTO action_type (action_type_id, ActionName) VALUES (14, 'show_Syntax_Errors');
INSERT INTO action_type (action_type_id, ActionName) VALUES (15, 'sign_schedule');
INSERT INTO action_type (action_type_id, ActionName) VALUES (16, 'START');
INSERT INTO action_type (action_type_id, ActionName) VALUES (17, 'suspendToFloppy');
INSERT INTO action_type (action_type_id, ActionName) VALUES (18, 'toggle_EventCollection');
INSERT INTO action_type (action_type_id, ActionName) VALUES (19, 'turn_debugMode');
INSERT INTO action_type (action_type_id, ActionName) VALUES (20, 'turn_developerMode');
INSERT INTO action_type (action_type_id, ActionName) VALUES (21, 'turn_showQuestionNum');

-- 
-- Dumping data for table code_system
-- 

INSERT INTO code_system (code_system_id, CodeSystemName, CodeSystemOID) VALUES (1, 'LOINC', '2.16.840.1.113883.6.1');
INSERT INTO code_system (code_system_id, CodeSystemName, CodeSystemOID) VALUES (2, 'SNOMED-CT', '2.16.840.1.113883.6.96');

-- 
-- Dumping data for table data_type
-- 

INSERT INTO data_type (data_type_id, DataType) VALUES (6, 'number');
INSERT INTO data_type (data_type_id, DataType) VALUES (7, 'string');
INSERT INTO data_type (data_type_id, DataType) VALUES (8, 'date');
INSERT INTO data_type (data_type_id, DataType) VALUES (9, 'time');
INSERT INTO data_type (data_type_id, DataType) VALUES (10, 'year');
INSERT INTO data_type (data_type_id, DataType) VALUES (11, 'month');
INSERT INTO data_type (data_type_id, DataType) VALUES (12, 'day');
INSERT INTO data_type (data_type_id, DataType) VALUES (13, 'weekday');
INSERT INTO data_type (data_type_id, DataType) VALUES (14, 'hour');
INSERT INTO data_type (data_type_id, DataType) VALUES (15, 'minute');
INSERT INTO data_type (data_type_id, DataType) VALUES (16, 'second');
INSERT INTO data_type (data_type_id, DataType) VALUES (17, 'month_num');
INSERT INTO data_type (data_type_id, DataType) VALUES (18, 'day_num');

-- 
-- Dumping data for table 'display_type'
-- 

INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (1, 'best32.', 'best12.', 'nothing', 'NOMINAL', 'F8.0', 'NOM', 0, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (2, 'best32.', 'best12.', 'radio', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (3, 'best32.', 'best12.', 'check', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (4, 'best32.', 'best12.', 'combo', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (5, 'best32.', 'best12.', 'list', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (6, '$100.', '$100.', 'text', 'NOMINAL', 'A100', 'NAR', 0, 7);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (7, 'best32.', 'best12.', 'double', 'SCALE', 'F8.3', 'QN', 0, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (8, 'best32.', 'best12.', 'radio2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (9, '$50.', '$50.', 'password', 'NOMINAL', 'A50', 'NAR', 0, 7);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (10, '$254.', '$254.', 'memo', 'NOMINAL', 'A254', 'NAR', 0, 7);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (11, 'mmddyy10.', 'mmddyy10.', 'date', 'SCALE', 'ADATE10', 'QN', 0, 8);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (12, 'time8.0', 'time8.0', 'time', 'SCALE', 'TIME5.3', 'QN', 0, 9);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (13, 'best32.', 'best12.', 'year', 'SCALE', 'date|yyyy', 'QN', 0, 10);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (14, '$10.', '$10.', 'month', 'SCALE', 'MONTH', 'QN', 0, 11);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (15, '$6.', '$6.', 'day', 'SCALE', 'date|dd', 'QN', 0, 12);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (16, '$10.', '$10.', 'weekday', 'SCALE', 'WKDAY', 'QN', 0, 13);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (17, 'best32.', 'hour2.', 'hour', 'SCALE', 'date|hh', 'QN', 0, 14);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (18, 'best32.', 'best12.', 'minute', 'SCALE', 'date|mm', 'QN', 0, 15);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (19, 'best32.', 'best12.', 'second', 'SCALE', 'date|ss', 'QN', 0, 16);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (20, 'best32.', 'best12.', 'month_num', 'SCALE', 'date|mm', 'QN', 0, 17);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (21, 'best32.', 'day2.', 'day_num', 'SCALE', 'date|dd', 'QN', 0, 18);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (22, 'best32.', 'best12.', 'radio3', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (23, 'best32.', 'best12.', 'combo2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, SASinformat, SASformat, DisplayType, SPSSlevel, SPSSformat, LOINCscale, HasAnswerList, data_type_id) VALUES (24, 'best32.', 'best12.', 'list2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);

-- 
-- Dumping data for table null_flavor
-- 

INSERT INTO null_flavor (null_flavor_id, NullFlavor, DisplayName, Description) VALUES (1, '*UNASKED*', '*UNASKED*', NULL);
INSERT INTO null_flavor (null_flavor_id, NullFlavor, DisplayName, Description) VALUES (2, '*NA*', '*NA*', NULL);
INSERT INTO null_flavor (null_flavor_id, NullFlavor, DisplayName, Description) VALUES (3, '*REFUSED*', '*REFUSED*', NULL);
INSERT INTO null_flavor (null_flavor_id, NullFlavor, DisplayName, Description) VALUES (4, '*INVALID*', '*INVALID*', NULL);
INSERT INTO null_flavor (null_flavor_id, NullFlavor, DisplayName, Description) VALUES (5, '*UNKNOWN*', '*UNKNOWN*', NULL);
INSERT INTO null_flavor (null_flavor_id, NullFlavor, DisplayName, Description) VALUES (6, '*HUH*', '*HUH*', NULL);

-- 
-- Dumping data for table reserved_word
-- 

INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (0, '__LANGUAGES__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (1, '__TITLE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (2, '__ICON__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (3, '__HEADER_MSG__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (4, '__STARTING_STEP__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (6, '__SHOW_QUESTION_REF__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (7, '__AUTOGEN_OPTION_NUM__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (8, '__DEVELOPER_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (9, '__DEBUG_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (10, '__START_TIME__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (11, '__FILENAME__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (12, '__SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (14, '__ALLOW_COMMENTS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (15, '__SCHEDULE_SOURCE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (16, '__LOADED_FROM__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (17, '__CURRENT_LANGUAGE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (19, '__ALLOW_REFUSED__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (20, '__ALLOW_UNKNOWN__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (21, '__ALLOW_DONT_UNDERSTAND__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (22, '__RECORD_EVENTS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (23, '__WORKING_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (24, '__COMPLETED_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (25, '__FLOPPY_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (26, '__IMAGE_FILES_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (27, '__COMMENT_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (28, '__COMMENT_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (29, '__REFUSED_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (30, '__REFUSED_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (31, '__UNKNOWN_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (32, '__UNKNOWN_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (35, '__TRICEPS_VERSION_MAJOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (36, '__TRICEPS_VERSION_MINOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (37, '__SCHED_AUTHORS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (38, '__SCHED_VERSION_MAJOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (39, '__SCHED_VERSION_MINOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (40, '__SCHED_HELP_URL__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (41, '__HELP_ICON__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (42, '__ACTIVE_BUTTON_PREFIX__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (44, '__TRICEPS_FILE_TYPE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (45, '__DISPLAY_COUNT__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (46, '__SCHEDULE_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (47, '__ALLOW_JUMP_TO__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (48, '__BROWSER_TYPE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (49, '__IP_ADDRESS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (50, '__SUSPEND_TO_FLOPPY__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (51, '__JUMP_TO_FIRST_UNASKED__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (52, '__REDIRECT_ON_FINISH_URL__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (53, '__REDIRECT_ON_FINISH_MSG__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (56, '__SET_DEFAULT_FOCUS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (59, '__WRAP_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (60, '__DISALLOW_COMMENTS__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (61, '__CONNECTION_TYPE__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__', NULL);
INSERT INTO reserved_word (reserved_word_id, ReservedWord, Meaning) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__', NULL);


-- 
-- Dumping data for table var_name
-- 

INSERT INTO var_name (var_name_id, VarName) VALUES (0, '__LANGUAGES__');
INSERT INTO var_name (var_name_id, VarName) VALUES (1, '__TITLE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (2, '__ICON__');
INSERT INTO var_name (var_name_id, VarName) VALUES (3, '__HEADER_MSG__');
INSERT INTO var_name (var_name_id, VarName) VALUES (4, '__STARTING_STEP__');
INSERT INTO var_name (var_name_id, VarName) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (6, '__SHOW_QUESTION_REF__');
INSERT INTO var_name (var_name_id, VarName) VALUES (7, '__AUTOGEN_OPTION_NUM__');
INSERT INTO var_name (var_name_id, VarName) VALUES (8, '__DEVELOPER_MODE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (9, '__DEBUG_MODE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (10, '__START_TIME__');
INSERT INTO var_name (var_name_id, VarName) VALUES (11, '__FILENAME__');
INSERT INTO var_name (var_name_id, VarName) VALUES (12, '__SHOW_ADMIN_ICONS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (14, '__ALLOW_COMMENTS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (15, '__SCHEDULE_SOURCE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (16, '__LOADED_FROM__');
INSERT INTO var_name (var_name_id, VarName) VALUES (17, '__CURRENT_LANGUAGE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__');
INSERT INTO var_name (var_name_id, VarName) VALUES (19, '__ALLOW_REFUSED__');
INSERT INTO var_name (var_name_id, VarName) VALUES (20, '__ALLOW_UNKNOWN__');
INSERT INTO var_name (var_name_id, VarName) VALUES (21, '__ALLOW_DONT_UNDERSTAND__');
INSERT INTO var_name (var_name_id, VarName) VALUES (22, '__RECORD_EVENTS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (23, '__WORKING_DIR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (24, '__COMPLETED_DIR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (25, '__FLOPPY_DIR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (26, '__IMAGE_FILES_DIR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (27, '__COMMENT_ICON_ON__');
INSERT INTO var_name (var_name_id, VarName) VALUES (28, '__COMMENT_ICON_OFF__');
INSERT INTO var_name (var_name_id, VarName) VALUES (29, '__REFUSED_ICON_ON__');
INSERT INTO var_name (var_name_id, VarName) VALUES (30, '__REFUSED_ICON_OFF__');
INSERT INTO var_name (var_name_id, VarName) VALUES (31, '__UNKNOWN_ICON_ON__');
INSERT INTO var_name (var_name_id, VarName) VALUES (32, '__UNKNOWN_ICON_OFF__');
INSERT INTO var_name (var_name_id, VarName) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__');
INSERT INTO var_name (var_name_id, VarName) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__');
INSERT INTO var_name (var_name_id, VarName) VALUES (35, '__TRICEPS_VERSION_MAJOR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (36, '__TRICEPS_VERSION_MINOR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (37, '__SCHED_AUTHORS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (38, '__SCHED_VERSION_MAJOR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (39, '__SCHED_VERSION_MINOR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (40, '__SCHED_HELP_URL__');
INSERT INTO var_name (var_name_id, VarName) VALUES (41, '__HELP_ICON__');
INSERT INTO var_name (var_name_id, VarName) VALUES (42, '__ACTIVE_BUTTON_PREFIX__');
INSERT INTO var_name (var_name_id, VarName) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__');
INSERT INTO var_name (var_name_id, VarName) VALUES (44, '__TRICEPS_FILE_TYPE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (45, '__DISPLAY_COUNT__');
INSERT INTO var_name (var_name_id, VarName) VALUES (46, '__SCHEDULE_DIR__');
INSERT INTO var_name (var_name_id, VarName) VALUES (47, '__ALLOW_JUMP_TO__');
INSERT INTO var_name (var_name_id, VarName) VALUES (48, '__BROWSER_TYPE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (49, '__IP_ADDRESS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (50, '__SUSPEND_TO_FLOPPY__');
INSERT INTO var_name (var_name_id, VarName) VALUES (51, '__JUMP_TO_FIRST_UNASKED__');
INSERT INTO var_name (var_name_id, VarName) VALUES (52, '__REDIRECT_ON_FINISH_URL__');
INSERT INTO var_name (var_name_id, VarName) VALUES (53, '__REDIRECT_ON_FINISH_MSG__');
INSERT INTO var_name (var_name_id, VarName) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__');
INSERT INTO var_name (var_name_id, VarName) VALUES (56, '__SET_DEFAULT_FOCUS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (59, '__WRAP_ADMIN_ICONS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (60, '__DISALLOW_COMMENTS__');
INSERT INTO var_name (var_name_id, VarName) VALUES (61, '__CONNECTION_TYPE__');
INSERT INTO var_name (var_name_id, VarName) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__');
INSERT INTO var_name (var_name_id, VarName) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__');

UPDATE SEQUENCE SET SEQ_COUNT = '0';

UPDATE SEQUENCE SET SEQ_COUNT = '100' WHERE SEQ_NAME = 'VarName';

ALTER TABLE v1_data_element ADD INDEX ( VarName ) ;
-- ALTER TABLE v1_data_element ADD INDEX ( LanguageCode ) ;

-- ALTER TABLE v1_item_usage ADD INDEX ( VarName ) ;
ALTER TABLE v1_item_usage ADD INDEX ( LanguageCode ) ;

ALTER TABLE v1_instrument_session ADD INDEX ( LanguageCode ) ;
