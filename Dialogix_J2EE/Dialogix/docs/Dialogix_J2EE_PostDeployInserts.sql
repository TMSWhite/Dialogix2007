update sequence_generator_table set SEQUENCE_VALUE = 0;
update sequence set SEQ_COUNT = 0;
update v1_sequence_generator_table set SEQUENCE_VALUE = 0;

INSERT INTO Reserved_Word (ReservedWord_ID, ReservedWord) VALUES 
(0, '__LANGUAGES__'),
(1, '__TITLE__'),
(2, '__ICON__'),
(3, '__HEADER_MSG__'),
(4, '__STARTING_STEP__'),
(5, '__PASSWORD_FOR_ADMIN_MODE__'),
(6, '__SHOW_QUESTION_REF__'),
(7, '__AUTOGEN_OPTION_NUM__'),
(8, '__DEVELOPER_MODE__'),
(9, '__DEBUG_MODE__'),
(10, '__START_TIME__'),
(11, '__FILENAME__'),
(12, '__SHOW_ADMIN_ICONS__'),
(13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__'),
(14, '__ALLOW_COMMENTS__'),
(15, '__SCHEDULE_SOURCE__'),
(16, '__LOADED_FROM__'),
(17, '__CURRENT_LANGUAGE__'),
(18, '__ALLOW_LANGUAGE_SWITCHING__'),
(19, '__ALLOW_REFUSED__'),
(20, '__ALLOW_UNKNOWN__'),
(21, '__ALLOW_DONT_UNDERSTAND__'),
(22, '__RECORD_EVENTS__'),
(23, '__WORKING_DIR__'),
(24, '__COMPLETED_DIR__'),
(25, '__FLOPPY_DIR__'),
(26, '__IMAGE_FILES_DIR__'),
(27, '__COMMENT_ICON_ON__'),
(28, '__COMMENT_ICON_OFF__'),
(29, '__REFUSED_ICON_ON__'),
(30, '__REFUSED_ICON_OFF__'),
(31, '__UNKNOWN_ICON_ON__'),
(32, '__UNKNOWN_ICON_OFF__'),
(33, '__DONT_UNDERSTAND_ICON_ON__'),
(34, '__DONT_UNDERSTAND_ICON_OFF__'),
(35, '__TRICEPS_VERSION_MAJOR__'),
(36, '__TRICEPS_VERSION_MINOR__'),
(37, '__SCHED_AUTHORS__'),
(38, '__SCHED_VERSION_MAJOR__'),
(39, '__SCHED_VERSION_MINOR__'),
(40, '__SCHED_HELP_URL__'),
(41, '__HELP_ICON__'),
(42, '__ACTIVE_BUTTON_PREFIX__'),
(43, '__ACTIVE_BUTTON_SUFFIX__'),
(44, '__TRICEPS_FILE_TYPE__'),
(45, '__DISPLAY_COUNT__'),
(46, '__SCHEDULE_DIR__'),
(47, '__ALLOW_JUMP_TO__'),
(48, '__BROWSER_TYPE__'),
(49, '__IP_ADDRESS__'),
(50, '__SUSPEND_TO_FLOPPY__'),
(51, '__JUMP_TO_FIRST_UNASKED__'),
(52, '__REDIRECT_ON_FINISH_URL__'),
(53, '__REDIRECT_ON_FINISH_MSG__'),
(54, '__SWAP_NEXT_AND_PREVIOUS__'),
(55, '__ANSWER_OPTION_FIELD_WIDTH__'),
(56, '__SET_DEFAULT_FOCUS__'),
(57, '__ALWAYS_SHOW_ADMIN_ICONS__'),
(58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__'),
(59, '__WRAP_ADMIN_ICONS__'),
(60, '__DISALLOW_COMMENTS__'),
(61, '__CONNECTION_TYPE__'),
(62, '__REDIRECT_ON_FINISH_DELAY__'),
(63, '__MAX_TEXT_LEN_FOR_COMBO__')
;

INSERT INTO Null_Flavor (NullFlavor_ID, NullFlavor, DisplayName) VALUES 
(1, '*UNASKED*', '*UNASKED*'), 
(2, '*NA*', '*NA*'),      
(3, '*REFUSED*', '*REFUSED*'), 
(4, '*INVALID*', '*INVALID*'), 
(5, '*UNKNOWN*', '*UNKNOWN*'), 
(6, '*HUH*', '*HUH*')
;

INSERT INTO Action_Type (ActionType_ID, ActionName) VALUES
(1, 'evaluate_expr'),
(2, 'finished'),
(3, 'jump_to'),
(4, 'jumpToFirstUnasked'),
(5, 'next'),
(6, 'previous'),
(7, 'refresh current'),
(8, 'reload_questions'),
(9, 'restart_clean'),
(10, 'RESTORE'),
(11, 'RESTORE_FROM_FLOPPY'),
(12, 'save_to'),
(13, 'select_new_interview'),
(14, 'show_Syntax_Errors'),
(15, 'sign_schedule'),
(16, 'START'),
(17, 'suspendToFloppy'),
(18, 'toggle_EventCollection'),
(19, 'turn_debugMode'),
(20, 'turn_developerMode'),
(21, 'turn_showQuestionNum')
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


INSERT INTO dialogix_user (DialogixUser_ID) VALUES
(1)
;

INSERT INTO code_system (CodeSystem_ID, CodeSystemName, CodeSystemOID) VALUES 
(1, 'LOINC', '2.16.840.1.113883.6.1'),
(2, 'SNOMED-CT', '2.16.840.1.113883.6.96');
