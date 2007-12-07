-- 
-- Dumping data for table action_type
-- 

INSERT INTO action_type (ActionType_ID, ActionName) VALUES (1, 'evaluate_expr');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (2, 'finished');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (3, 'jump_to');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (4, 'jumpToFirstUnasked');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (5, 'next');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (6, 'previous');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (7, 'refresh current');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (8, 'reload_questions');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (9, 'restart_clean');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (10, 'RESTORE');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (11, 'RESTORE_FROM_FLOPPY');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (12, 'save_to');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (13, 'select_new_interview');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (14, 'show_Syntax_Errors');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (15, 'sign_schedule');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (16, 'START');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (17, 'suspendToFloppy');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (18, 'toggle_EventCollection');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (19, 'turn_debugMode');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (20, 'turn_developerMode');
INSERT INTO action_type (ActionType_ID, ActionName) VALUES (21, 'turn_showQuestionNum');

-- 
-- Dumping data for table code_system
-- 

INSERT INTO code_system (CodeSystem_ID, CodeSystemName, CodeSystemOID) VALUES (1, 'LOINC', '2.16.840.1.113883.6.1');
INSERT INTO code_system (CodeSystem_ID, CodeSystemName, CodeSystemOID) VALUES (2, 'SNOMED-CT', '2.16.840.1.113883.6.96');

-- 
-- Dumping data for table data_type
-- 

INSERT INTO data_type (DataType_ID, DataType) VALUES (6, 'number');
INSERT INTO data_type (DataType_ID, DataType) VALUES (7, 'string');
INSERT INTO data_type (DataType_ID, DataType) VALUES (8, 'date');
INSERT INTO data_type (DataType_ID, DataType) VALUES (9, 'time');
INSERT INTO data_type (DataType_ID, DataType) VALUES (10, 'year');
INSERT INTO data_type (DataType_ID, DataType) VALUES (11, 'month');
INSERT INTO data_type (DataType_ID, DataType) VALUES (12, 'day');
INSERT INTO data_type (DataType_ID, DataType) VALUES (13, 'weekday');
INSERT INTO data_type (DataType_ID, DataType) VALUES (14, 'hour');
INSERT INTO data_type (DataType_ID, DataType) VALUES (15, 'minute');
INSERT INTO data_type (DataType_ID, DataType) VALUES (16, 'second');
INSERT INTO data_type (DataType_ID, DataType) VALUES (17, 'month_num');
INSERT INTO data_type (DataType_ID, DataType) VALUES (18, 'day_num');

-- 
-- Dumping data for table dialogix_user
-- 

INSERT INTO dialogix_user (DialogixUser_ID, FIRST_NAME, LAST_NAME, USER_NAME, EMAIL, PHONE, PWD) VALUES (1, 'Test', 'Test', 'Test', 'test@test.com', '555-555-5555', 'testtest');

-- 
-- Dumping data for table null_flavor
-- 

INSERT INTO null_flavor (NullFlavor_ID, NullFlavor, DisplayName, Description) VALUES (1, '*UNASKED*', '*UNASKED*', NULL);
INSERT INTO null_flavor (NullFlavor_ID, NullFlavor, DisplayName, Description) VALUES (2, '*NA*', '*NA*', NULL);
INSERT INTO null_flavor (NullFlavor_ID, NullFlavor, DisplayName, Description) VALUES (3, '*REFUSED*', '*REFUSED*', NULL);
INSERT INTO null_flavor (NullFlavor_ID, NullFlavor, DisplayName, Description) VALUES (4, '*INVALID*', '*INVALID*', NULL);
INSERT INTO null_flavor (NullFlavor_ID, NullFlavor, DisplayName, Description) VALUES (5, '*UNKNOWN*', '*UNKNOWN*', NULL);
INSERT INTO null_flavor (NullFlavor_ID, NullFlavor, DisplayName, Description) VALUES (6, '*HUH*', '*HUH*', NULL);

-- 
-- Dumping data for table reserved_word
-- 

INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (0, '__LANGUAGES__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (1, '__TITLE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (2, '__ICON__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (3, '__HEADER_MSG__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (4, '__STARTING_STEP__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (6, '__SHOW_QUESTION_REF__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (7, '__AUTOGEN_OPTION_NUM__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (8, '__DEVELOPER_MODE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (9, '__DEBUG_MODE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (10, '__START_TIME__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (11, '__FILENAME__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (12, '__SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (14, '__ALLOW_COMMENTS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (15, '__SCHEDULE_SOURCE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (16, '__LOADED_FROM__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (17, '__CURRENT_LANGUAGE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (19, '__ALLOW_REFUSED__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (20, '__ALLOW_UNKNOWN__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (21, '__ALLOW_DONT_UNDERSTAND__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (22, '__RECORD_EVENTS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (23, '__WORKING_DIR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (24, '__COMPLETED_DIR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (25, '__FLOPPY_DIR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (26, '__IMAGE_FILES_DIR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (27, '__COMMENT_ICON_ON__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (28, '__COMMENT_ICON_OFF__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (29, '__REFUSED_ICON_ON__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (30, '__REFUSED_ICON_OFF__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (31, '__UNKNOWN_ICON_ON__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (32, '__UNKNOWN_ICON_OFF__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (35, '__TRICEPS_VERSION_MAJOR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (36, '__TRICEPS_VERSION_MINOR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (37, '__SCHED_AUTHORS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (38, '__SCHED_VERSION_MAJOR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (39, '__SCHED_VERSION_MINOR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (40, '__SCHED_HELP_URL__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (41, '__HELP_ICON__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (42, '__ACTIVE_BUTTON_PREFIX__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (44, '__TRICEPS_FILE_TYPE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (45, '__DISPLAY_COUNT__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (46, '__SCHEDULE_DIR__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (47, '__ALLOW_JUMP_TO__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (48, '__BROWSER_TYPE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (49, '__IP_ADDRESS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (50, '__SUSPEND_TO_FLOPPY__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (51, '__JUMP_TO_FIRST_UNASKED__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (52, '__REDIRECT_ON_FINISH_URL__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (53, '__REDIRECT_ON_FINISH_MSG__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (56, '__SET_DEFAULT_FOCUS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (59, '__WRAP_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (60, '__DISALLOW_COMMENTS__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (61, '__CONNECTION_TYPE__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__', NULL);
INSERT INTO reserved_word (ReservedWord_ID, ReservedWord, Meaning) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__', NULL);
