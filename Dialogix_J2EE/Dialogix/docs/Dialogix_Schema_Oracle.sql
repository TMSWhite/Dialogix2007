CREATE TABLE v1_page_usages (id NUMBER(19) NOT NULL, server_duration NUMBER(19) NULL, load_duration NUMBER(19) NULL, display_num NUMBER(10) NOT NULL, network_duration NUMBER(19) NULL, page_duration NUMBER(19) NULL, language_code VARCHAR2(2) NULL, total_duration NUMBER(19) NULL, action_type VARCHAR2(255) NULL, v1_instrument_session_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE v1_item_usages (id NUMBER(19) NOT NULL, answer_code0 CLOB NULL, answer_string0 CLOB NULL, display_num NUMBER(10) NOT NULL, time_stamp TIMESTAMP NULL, question_as_asked CLOB NULL, when_as_ms NUMBER(19) NULL, answer_string CLOB NULL, item_visits NUMBER(10) NULL, language_code VARCHAR2(2) NULL, comments CLOB NULL, item_usage_sequence NUMBER(10) NOT NULL, comments0 CLOB NULL, answer_code CLOB NULL, v1_data_element_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE v1_instrument_sessions (id NUMBER(19) NOT NULL, max_group NUMBER(10) NULL, max_var_num NUMBER(10) NULL, instrument_version_name VARCHAR2(255) NOT NULL, instrument_version_file_name CLOB NULL, last_access_time TIMESTAMP NOT NULL, instrument_session_file_name CLOB NULL, current_group NUMBER(10) NULL, num_vars NUMBER(10) NULL, language_code VARCHAR2(2) NULL, var_list_md5 VARCHAR2(255) NULL, status_msg VARCHAR2(255) NULL, num_groups NUMBER(10) NULL, start_time TIMESTAMP NOT NULL, finished NUMBER(10) NULL, display_num NUMBER(10) NULL, ip_address VARCHAR2(255) NULL, browser VARCHAR2(255) NULL, action_type VARCHAR2(255) NULL, instrument_starting_group NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE instrument_versions (id NUMBER(19) NOT NULL, num_cols NUMBER(10) NULL, instrument_as_spreadsheet CLOB NULL, name VARCHAR2(255) NOT NULL, has_loinc_code NUMBER(1) default 0 NULL, loinc_num VARCHAR2(255) NULL, creation_time_stamp TIMESTAMP NOT NULL, num_rows NUMBER(10) NULL, instrument_status NUMBER(10) NULL, instrument_version_file_name CLOB NULL, instrument_notes CLOB NULL, instrument_hash_id NUMBER(19) NULL, instrument_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE instrument_contents (id NUMBER(19) NOT NULL, default_answer CLOB NULL, spss_format VARCHAR2(255) NULL, item_sequence NUMBER(10) NOT NULL, sas_informat VARCHAR2(255) NULL, is_required NUMBER(10) NOT NULL, sas_format VARCHAR2(255) NULL, display_name CLOB NULL, spss_level VARCHAR2(255) NULL, relevance CLOB NOT NULL, concept CLOB NULL, format_mask CLOB NULL, is_read_only NUMBER(10) NOT NULL, group_num NUMBER(10) NOT NULL, is_message NUMBER(10) NOT NULL, item_action_type VARCHAR2(255) NULL, var_name_id NUMBER(19) NULL, display_type_id NUMBER(10) NULL, readback_id NUMBER(19) NULL, instrument_version_id NUMBER(19) NULL, help_id NUMBER(19) NULL, item_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE v1_data_elements (id NUMBER(19) NOT NULL, group_num NUMBER(10) NULL, item_visits NUMBER(10) NULL, data_element_sequence NUMBER(10) NOT NULL, var_name VARCHAR2(200) NOT NULL, v1_instrument_session_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE answers (id NUMBER(19) NOT NULL, has_la_code NUMBER(1) default 0 NULL, la_code VARCHAR2(255) NULL, PRIMARY KEY (id));
CREATE TABLE code_systems (id NUMBER(10) NOT NULL, name VARCHAR2(255) NULL, code_system_oid VARCHAR2(255) NULL, PRIMARY KEY (id));
CREATE TABLE validations (id NUMBER(19) NOT NULL, other_vals VARCHAR2(255) NULL, input_mask VARCHAR2(255) NULL, max_val VARCHAR2(255) NULL, min_val VARCHAR2(255) NULL, data_type_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE semantic_mapping_i_q_as (id NUMBER(19) NOT NULL, code CLOB NULL, code_display_name CLOB NULL, answer_id NUMBER(19) NULL, instrument_version_id NUMBER(19) NULL, question_id NUMBER(19) NULL, code_system_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE page_usage_events (id NUMBER(19) NOT NULL, time_stamp TIMESTAMP NULL, duration NUMBER(10) NOT NULL, page_usage_event_sequence NUMBER(10) NOT NULL, value1 VARCHAR2(255) NOT NULL, event_type VARCHAR2(255) NOT NULL, value2 VARCHAR2(255) NOT NULL, gui_action_type VARCHAR2(255) NOT NULL, var_name VARCHAR2(255) NOT NULL, page_usage_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE source_content (id NUMBER(19) NOT NULL, col_num NUMBER(10) NULL, name CLOB NULL, row_num NUMBER(10) NULL, instrument_version_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE page_usages (id NUMBER(19) NOT NULL, page_duration NUMBER(10) NULL, server_duration NUMBER(10) NULL, page_usage_sequence NUMBER(10) NOT NULL, load_duration NUMBER(10) NULL, time_stamp TIMESTAMP NOT NULL, network_duration NUMBER(10) NULL, to_group_num NUMBER(10) NOT NULL, page_visits NUMBER(10) NULL, status_msg VARCHAR2(255) NULL, used_jvm_memory NUMBER(19) NULL, ip_address VARCHAR2(255) NULL, from_group_num NUMBER(10) NOT NULL, browser VARCHAR2(255) NULL, total_duration NUMBER(10) NULL, display_num NUMBER(10) NOT NULL, language_code VARCHAR2(2) NOT NULL, instrument_session_id NUMBER(19) NULL, action_type_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE answer_localizeds (id NUMBER(19) NOT NULL, name CLOB NULL, answer_length NUMBER(10) NOT NULL, language_code VARCHAR2(2) NOT NULL, answer_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE answer_list_contents (id NUMBER(19) NOT NULL, answer_code VARCHAR2(255) NOT NULL, answer_order NUMBER(10) NOT NULL, answer_list_id NUMBER(19) NULL, answer_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE semantic_mapping_q_as (id NUMBER(19) NOT NULL, code_display_name CLOB NULL, code CLOB NULL, answer_id NUMBER(19) NULL, question_id NUMBER(19) NULL, code_system_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE display_types (id NUMBER(10) NOT NULL, sas_informat VARCHAR2(255) NOT NULL, sas_format VARCHAR2(255) NOT NULL, name VARCHAR2(255) NOT NULL, spss_level VARCHAR2(255) NOT NULL, spss_format VARCHAR2(255) NOT NULL, loinc_scale VARCHAR2(255) NOT NULL, has_answer_list NUMBER(1) default 0 NULL, data_type_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE data_types (id NUMBER(10) NOT NULL, name VARCHAR2(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE question_localizeds (id NUMBER(19) NOT NULL, language_code VARCHAR2(2) NOT NULL, name CLOB NULL, question_length NUMBER(10) NOT NULL, question_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE reserved_words (id NUMBER(10) NOT NULL, name VARCHAR2(255) NOT NULL, meaning VARCHAR2(255) NULL, PRIMARY KEY (id));
CREATE TABLE null_flavors (id NUMBER(10) NOT NULL, name VARCHAR2(255) NOT NULL, display_name VARCHAR2(255) NOT NULL, description CLOB NULL, PRIMARY KEY (id));
CREATE TABLE semantic_mapping_qs (id NUMBER(19) NOT NULL, code_display_name CLOB NULL, code CLOB NULL, question_id NUMBER(19) NULL, code_system_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE instrument_sessions (id NUMBER(19) NOT NULL, finished NUMBER(10) NULL, num_vars NUMBER(10) NULL, start_time TIMESTAMP NOT NULL, num_groups NUMBER(10) NULL, instrument_starting_group NUMBER(10) NOT NULL, instrument_session_file_name VARCHAR2(255) NULL, current_var_name NUMBER(10) NOT NULL, ip_address VARCHAR2(255) NULL, language_code VARCHAR2(255) NOT NULL, browser VARCHAR2(255) NULL, max_group NUMBER(10) NULL, current_group NUMBER(10) NOT NULL, display_num NUMBER(10) NOT NULL, last_access_time TIMESTAMP NOT NULL, status_msg VARCHAR2(255) NULL, max_var_num NUMBER(10) NULL, dialogix_user_id NUMBER(10) NULL, instrument_id NUMBER(19) NULL, instrument_version_id NUMBER(19) NULL, action_type_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE item_usages (id NUMBER(19) NOT NULL, null_flavor_id NUMBER(10) NOT NULL, comments CLOB NULL, item_usage_sequence NUMBER(10) NOT NULL, time_stamp TIMESTAMP NULL, group_num NUMBER(10) NULL, when_as_ms NUMBER(19) NOT NULL, question_as_asked CLOB NULL, display_num NUMBER(10) NOT NULL, answer_string CLOB NULL, item_visits NUMBER(10) NULL, response_latency NUMBER(10) NULL, language_code VARCHAR2(2) NULL, response_duration NUMBER(10) NULL, answer_id NUMBER(19) NULL, answer_code CLOB NULL, data_element_sequence NUMBER(10) NOT NULL, instrument_session_id NUMBER(19) NULL, var_name_id NUMBER(19) NULL, instrument_content_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE dialogix_users (id NUMBER(10) NOT NULL, first_name VARCHAR2(255) NOT NULL, last_name VARCHAR2(255) NOT NULL, user_name VARCHAR2(255) NOT NULL, email VARCHAR2(255) NOT NULL, phone VARCHAR2(255) NOT NULL, pwd VARCHAR2(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE instrument_load_errors (id NUMBER(19) NOT NULL, source_column NUMBER(10) NULL, source_text CLOB NULL, source_row NUMBER(10) NULL, log_level NUMBER(10) NULL, error_message CLOB NULL, instrument_version_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE help_localizeds (id NUMBER(19) NOT NULL, language_code VARCHAR2(2) NOT NULL, name CLOB NULL, help_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE questions (id NUMBER(19) NOT NULL, PRIMARY KEY (id));
CREATE TABLE loinc_item_requests (id NUMBER(19) NOT NULL, loinc_system VARCHAR2(255) NULL, loinc_scale VARCHAR2(255) NULL, loinc_property VARCHAR2(255) NULL, loinc_method VARCHAR2(255) NULL, loinc_num VARCHAR2(255) NULL, loinc_time_aspect VARCHAR2(255) NULL, item_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE action_types (id NUMBER(10) NOT NULL, name VARCHAR2(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE answer_lists (id NUMBER(19) NOT NULL, PRIMARY KEY (id));
CREATE TABLE helps (id NUMBER(19) NOT NULL, PRIMARY KEY (id));
CREATE TABLE semantic_mapping_as (id NUMBER(19) NOT NULL, code_display_name CLOB NULL, code CLOB NULL, answer_id NUMBER(19) NULL, code_system_id NUMBER(10) NULL, PRIMARY KEY (id));
CREATE TABLE language_lists (id NUMBER(10) NOT NULL, name VARCHAR2(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE instrument_headers (id NUMBER(19) NOT NULL, name CLOB NOT NULL, reserved_word_id NUMBER(10) NULL, instrument_version_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE function_names (id NUMBER(10) NOT NULL, syntax CLOB NOT NULL, description CLOB NOT NULL, name VARCHAR2(255) NOT NULL, definition CLOB NOT NULL, PRIMARY KEY (id));
CREATE TABLE var_names (id NUMBER(19) NOT NULL, name VARCHAR2(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE items (id NUMBER(19) NOT NULL, item_type VARCHAR2(255) NOT NULL, has_loinc_code NUMBER(1) default 0 NULL, loinc_num VARCHAR2(255) NULL, answer_list_id NUMBER(19) NULL, data_type_id NUMBER(10) NULL, question_id NUMBER(19) NULL, validation_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE loinc_instrument_requests (id NUMBER(19) NOT NULL, loinc_system VARCHAR2(255) NULL, loinc_scale VARCHAR2(255) NULL, loinc_property VARCHAR2(255) NULL, loinc_method VARCHAR2(255) NULL, loinc_num VARCHAR2(255) NULL, loinc_time_aspect VARCHAR2(255) NULL, instrument_version_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE readbacks (id NUMBER(19) NOT NULL, PRIMARY KEY (id));
CREATE TABLE answer_list_denormalizeds (id NUMBER(19) NOT NULL, answer_list_denormalized_len NUMBER(10) NOT NULL, language_code VARCHAR2(2) NOT NULL, name CLOB NOT NULL, answer_list_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE data_elements (id NUMBER(19) NOT NULL, comments CLOB NULL, time_stamp TIMESTAMP NULL, group_num NUMBER(10) NULL, when_as_ms NUMBER(19) NOT NULL, question_as_asked CLOB NULL, display_num NUMBER(10) NOT NULL, answer_string CLOB NULL, item_visits NUMBER(10) NULL, null_flavor_id NUMBER(10) NOT NULL, response_latency NUMBER(10) NULL, language_code VARCHAR2(2) NULL, response_duration NUMBER(10) NULL, answer_id NUMBER(19) NULL, answer_code CLOB NULL, data_element_sequence NUMBER(10) NOT NULL, instrument_session_id NUMBER(19) NULL, var_name_id NUMBER(19) NULL, instrument_content_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE readback_localizeds (id NUMBER(19) NOT NULL, language_code VARCHAR2(2) NOT NULL, name CLOB NULL, readback_id NUMBER(19) NULL, PRIMARY KEY (id));
CREATE TABLE instruments (id NUMBER(19) NOT NULL, instrument_description CLOB NULL, name VARCHAR2(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE instrument_hashes (id NUMBER(19) NOT NULL, num_equations NUMBER(10) NOT NULL, num_questions NUMBER(10) NOT NULL, var_list_md5 VARCHAR2(255) NOT NULL, num_branches NUMBER(10) NOT NULL, num_languages NUMBER(10) NOT NULL, num_tailorings NUMBER(10) NOT NULL, num_vars NUMBER(10) NOT NULL, num_groups NUMBER(10) NULL, num_instructions NUMBER(10) NOT NULL, instrument_md5 VARCHAR2(255) NOT NULL, language_list_id NUMBER(10) NULL, PRIMARY KEY (id));

-- 
-- Dumping data for table 'action_types'
-- 

INSERT INTO action_types (id, name) VALUES (1, 'evaluate_expr');
INSERT INTO action_types (id, name) VALUES (2, 'finished');
INSERT INTO action_types (id, name) VALUES (3, 'jump_to');
INSERT INTO action_types (id, name) VALUES (4, 'jumpToFirstUnasked');
INSERT INTO action_types (id, name) VALUES (5, 'next');
INSERT INTO action_types (id, name) VALUES (6, 'previous');
INSERT INTO action_types (id, name) VALUES (7, 'refresh current');
INSERT INTO action_types (id, name) VALUES (8, 'reload_questions');
INSERT INTO action_types (id, name) VALUES (9, 'restart_clean');
INSERT INTO action_types (id, name) VALUES (10, 'RESTORE');
INSERT INTO action_types (id, name) VALUES (11, 'RESTORE_FROM_FLOPPY');
INSERT INTO action_types (id, name) VALUES (12, 'save_to');
INSERT INTO action_types (id, name) VALUES (13, 'select_new_interview');
INSERT INTO action_types (id, name) VALUES (14, 'show_Syntax_Errors');
INSERT INTO action_types (id, name) VALUES (15, 'sign_schedule');
INSERT INTO action_types (id, name) VALUES (16, 'START');
INSERT INTO action_types (id, name) VALUES (17, 'suspendToFloppy');
INSERT INTO action_types (id, name) VALUES (18, 'toggle_EventCollection');
INSERT INTO action_types (id, name) VALUES (19, 'turn_debugMode');
INSERT INTO action_types (id, name) VALUES (20, 'turn_developerMode');
INSERT INTO action_types (id, name) VALUES (21, 'turn_showQuestionNum');

-- 
-- Dumping data for table 'answers'
-- 

INSERT INTO answers (id, has_la_code, la_code) VALUES (1, 0, NULL);
INSERT INTO answers (id, has_la_code, la_code) VALUES (2, 0, NULL);
INSERT INTO answers (id, has_la_code, la_code) VALUES (3, 0, NULL);
INSERT INTO answers (id, has_la_code, la_code) VALUES (4, 0, NULL);

-- 
-- Dumping data for table 'answer_lists'
-- 

INSERT INTO answer_lists (id) VALUES (1);
INSERT INTO answer_lists (id) VALUES (2);

-- 
-- Dumping data for table 'answer_list_contents'
-- 

INSERT INTO answer_list_contents (id, answer_code, answer_order, answer_list_id, answer_id) VALUES (1, '0', 1, 1, 1);
INSERT INTO answer_list_contents (id, answer_code, answer_order, answer_list_id, answer_id) VALUES (2, '0', 1, 2, 2);
INSERT INTO answer_list_contents (id, answer_code, answer_order, answer_list_id, answer_id) VALUES (3, '1', 2, 2, 4);
INSERT INTO answer_list_contents (id, answer_code, answer_order, answer_list_id, answer_id) VALUES (4, '1', 2, 1, 3);

-- 
-- Dumping data for table 'answer_list_denormalizeds'
-- 

INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (1, 15, 'en', '0|female|1|male', 2);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (2, 10, 'en', '0|no|1|yes', 1);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (3, 16, 'fr', '0|fille|1|garcon', 2);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (4, 12, 'he', '0|זכר|1|נקבה', 2);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (5, 11, 'fr', '0|non|1|oui', 1);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (6, 9, 'he', '0|כן|1|לא', 1);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (7, 10, 'ru', '0|нет|1|да', 1);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (8, 19, 'ru', '0|мальчик|1|девочка', 2);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (9, 16, 'es', '0|fille|1|garcon', 2);
INSERT INTO answer_list_denormalizeds (id, answer_list_denormalized_len, language_code, name, answer_list_id) VALUES (10, 11, 'es', '0|non|1|oui', 1);

-- 
-- Dumping data for table 'answer_localizeds'
-- 

INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (1, 'fille', 5, 'es', 2);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (2, 'male', 4, 'en', 4);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (3, 'oui', 3, 'fr', 3);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (4, 'זכר', 3, 'he', 2);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (5, 'да', 2, 'ru', 3);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (6, 'мальчик', 7, 'ru', 2);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (7, 'oui', 3, 'es', 3);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (8, 'garcon', 6, 'fr', 4);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (9, 'נקבה', 4, 'he', 4);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (10, 'לא', 2, 'he', 3);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (11, 'non', 3, 'es', 1);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (12, 'нет', 3, 'ru', 1);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (13, 'female', 6, 'en', 2);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (14, 'девочка', 7, 'ru', 4);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (15, 'non', 3, 'fr', 1);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (16, 'fille', 5, 'fr', 2);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (17, 'yes', 3, 'en', 3);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (18, 'no', 2, 'en', 1);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (19, 'כן', 2, 'he', 1);
INSERT INTO answer_localizeds (id, name, answer_length, language_code, answer_id) VALUES (20, 'garcon', 6, 'es', 4);

-- 
-- Dumping data for table 'code_systems'
-- 

INSERT INTO code_systems (id, name, code_system_oid) VALUES (1, 'LOINC', '2.16.840.1.113883.6.1');
INSERT INTO code_systems (id, name, code_system_oid) VALUES (2, 'SNOMED-CT', '2.16.840.1.113883.6.96');

-- 
-- Dumping data for table 'data_types'
-- 

INSERT INTO data_types (id, name) VALUES (6, 'number');
INSERT INTO data_types (id, name) VALUES (7, 'string');
INSERT INTO data_types (id, name) VALUES (8, 'date');
INSERT INTO data_types (id, name) VALUES (9, 'time');
INSERT INTO data_types (id, name) VALUES (10, 'year');
INSERT INTO data_types (id, name) VALUES (11, 'month');
INSERT INTO data_types (id, name) VALUES (12, 'day');
INSERT INTO data_types (id, name) VALUES (13, 'weekday');
INSERT INTO data_types (id, name) VALUES (14, 'hour');
INSERT INTO data_types (id, name) VALUES (15, 'minute');
INSERT INTO data_types (id, name) VALUES (16, 'second');
INSERT INTO data_types (id, name) VALUES (17, 'month_num');
INSERT INTO data_types (id, name) VALUES (18, 'day_num');

-- 
-- Dumping data for table 'display_types'
-- 

INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (1, 'best32.', 'best12.', 'nothing', 'NOMINAL', 'F8.0', 'NOM', 0, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (2, 'best32.', 'best12.', 'radio', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (3, 'best32.', 'best12.', 'check', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (4, 'best32.', 'best12.', 'combo', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (5, 'best32.', 'best12.', 'list', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (6, '$100.', '$100.', 'text', 'NOMINAL', 'A100', 'NAR', 0, 7);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (7, 'best32.', 'best12.', 'double', 'SCALE', 'F8.3', 'QN', 0, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (8, 'best32.', 'best12.', 'radio2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (9, '$50.', '$50.', 'password', 'NOMINAL', 'A50', 'NAR', 0, 7);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (10, '$254.', '$254.', 'memo', 'NOMINAL', 'A254', 'NAR', 0, 7);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (11, 'mmddyy10.', 'mmddyy10.', 'date', 'SCALE', 'ADATE10', 'QN', 0, 8);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (12, 'time8.0', 'time8.0', 'time', 'SCALE', 'TIME5.3', 'QN', 0, 9);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (13, 'best32.', 'best12.', 'year', 'SCALE', 'date|yyyy', 'QN', 0, 10);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (14, '$10.', '$10.', 'month', 'SCALE', 'MONTH', 'QN', 0, 11);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (15, '$6.', '$6.', 'day', 'SCALE', 'date|dd', 'QN', 0, 12);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (16, '$10.', '$10.', 'weekday', 'SCALE', 'WKDAY', 'QN', 0, 13);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (17, 'best32.', 'hour2.', 'hour', 'SCALE', 'date|hh', 'QN', 0, 14);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (18, 'best32.', 'best12.', 'minute', 'SCALE', 'date|mm', 'QN', 0, 15);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (19, 'best32.', 'best12.', 'second', 'SCALE', 'date|ss', 'QN', 0, 16);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (20, 'best32.', 'best12.', 'month_num', 'SCALE', 'date|mm', 'QN', 0, 17);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (21, 'best32.', 'day2.', 'day_num', 'SCALE', 'date|dd', 'QN', 0, 18);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (22, 'best32.', 'best12.', 'radio3', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (23, 'best32.', 'best12.', 'combo2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_types (id, sas_informat, sas_format, name, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (24, 'best32.', 'best12.', 'list2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);

-- 
-- Dumping data for table 'instruments'
-- 

INSERT INTO instruments (id, instrument_description, name) VALUES (1, '', 'EnglishRussianFrenchHebrew');

-- 
-- Dumping data for table 'instrument_contents'
-- 

INSERT INTO instrument_contents (id, default_answer, spss_format, item_sequence, sas_informat, is_required, sas_format, display_name, spss_level, relevance, concept, format_mask, is_read_only, group_num, is_message, item_action_type, var_name_id, instrument_version_id, display_type_id, item_id, help_id, readback_id) VALUES (1, '', 'A100', 4, '$100.', 1, '$100.', 'dem4', 'NOMINAL', '1', '', '', 0, 4, 0, 'q', 105, 1, 6, 2, NULL, NULL);
INSERT INTO instrument_contents (id, default_answer, spss_format, item_sequence, sas_informat, is_required, sas_format, display_name, spss_level, relevance, concept, format_mask, is_read_only, group_num, is_message, item_action_type, var_name_id, instrument_version_id, display_type_id, item_id, help_id, readback_id) VALUES (2, '', 'F8.0', 3, 'best32.', 1, 'best12.', 'dem3', 'NOMINAL', '1', '', '', 0, 3, 0, 'q', 102, 1, 5, 3, NULL, NULL);
INSERT INTO instrument_contents (id, default_answer, spss_format, item_sequence, sas_informat, is_required, sas_format, display_name, spss_level, relevance, concept, format_mask, is_read_only, group_num, is_message, item_action_type, var_name_id, instrument_version_id, display_type_id, item_id, help_id, readback_id) VALUES (3, '', 'F8.3', 2, 'best32.', 1, 'best12.', 'dem2', 'SCALE', 'hasChild', '', '', 0, 2, 0, 'q', 101, 1, 7, 4, NULL, NULL);
INSERT INTO instrument_contents (id, default_answer, spss_format, item_sequence, sas_informat, is_required, sas_format, display_name, spss_level, relevance, concept, format_mask, is_read_only, group_num, is_message, item_action_type, var_name_id, instrument_version_id, display_type_id, item_id, help_id, readback_id) VALUES (4, '', 'F8.0', 5, 'best32.', 1, 'best12.', 'dem5', 'NOMINAL', '1', '', '', 0, 5, 1, 'q', 103, 1, 1, 1, NULL, NULL);
INSERT INTO instrument_contents (id, default_answer, spss_format, item_sequence, sas_informat, is_required, sas_format, display_name, spss_level, relevance, concept, format_mask, is_read_only, group_num, is_message, item_action_type, var_name_id, instrument_version_id, display_type_id, item_id, help_id, readback_id) VALUES (5, '', 'F8.0', 1, 'best32.', 1, 'best12.', 'dem1', 'NOMINAL', '1', '', '', 0, 1, 0, 'q', 104, 1, 5, 5, NULL, NULL);

-- 
-- Dumping data for table 'instrument_hashes'
-- 

INSERT INTO instrument_hashes (id, num_equations, num_questions, var_list_md5, num_branches, num_languages, num_tailorings, num_vars, num_groups, num_instructions, instrument_md5, language_list_id) VALUES (1, 0, 5, 'ceac35c5e59244554f38473dd3b43ab4a5c6fb65f638c21913608092adc16', 1, 5, 3, 5, 5, 1, 'c2ebc9acb4cd69a72e357b96254259e7da99d057b9a1c56ae890c1187ed0e4d', 1);

-- 
-- Dumping data for table 'instrument_headers'
-- 

INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (1, 'English Russian French Hebrew Demo', 3, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (2, '2', 38, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (3, 'bypass', 5, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (4, 'true', 6, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (5, '1', 39, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (6, 'SCHEDULE', 44, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (7, 'true', 18, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (8, 'true', 8, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (9, 'true', 8, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (10, 'dialogo.jpg', 2, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (11, 'false', 7, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (12, 'en_US|ru|fr|es|he', 0, 1);
INSERT INTO instrument_headers (id, name, reserved_word_id, instrument_version_id) VALUES (13, 'EnglishRussianFrenchHebrew', 1, 1);

-- 
-- Dumping data for table 'instrument_versions'
-- 

INSERT INTO instrument_versions (id, instrument_status, creation_time_stamp, name, instrument_version_file_name, has_loinc_code, instrument_notes, loinc_num, num_rows, num_cols, instrument_as_spreadsheet, instrument_hash_id, instrument_id) VALUES (1, 1, '2008-05-22 15:39:47', '2.1', '', 0, '', '', 19, 30, 'RESERVED	__TRICEPS_FILE_TYPE__	SCHEDULE																												\nRESERVED	__ICON__	dialogo.jpg																												\nRESERVED	__DEVELOPER_MODE__	true																												\nRESERVED	__TITLE__	EnglishRussianFrenchHebrew																												\nRESERVED	__HEADER_MSG__	English Russian French Hebrew Demo																												\nRESERVED	__SCHED_VERSION_MAJOR__	2																												\nRESERVED	__SCHED_VERSION_MINOR__	1																												\nRESERVED	__ALLOW_LANGUAGE_SWITCHING__	true																												\nRESERVED	__PASSWORD_FOR_ADMIN_MODE__	bypass																												\nRESERVED	__SHOW_QUESTION_REF__	true																												\nRESERVED	__AUTOGEN_OPTION_NUM__	false																												\nRESERVED	__DEVELOPER_MODE__	true																												\nRESERVED	__LANGUAGES__	en_US|ru|fr|es|he																												\nCOMMENT concept	VariableName	externalName	Relevance	ActionType	readback[0]	Action[0]	ResponseOptions[0]	helpURL[0]	readback[1]	Action[1]	ResponseOptions[1]	helpURL[1]	readback[2]	Action[2]	ResponseOptions[2]	helpURL[2]	readback[3]	Action[3]	ResponseOptions[3]	helpURL[3]	readback[4]	Action[4]	ResponseOptions[4]	helpURL[4]	languageNum	questionAsAsked	answerGiven	comment	timeStamp	\n	hasChild	dem1	1	q		Do you have any children?	list|0|no|1|yes			У Вас есть дети?	list|0|нет|1|да			Avez vous des enfants?	list|0|non|1|oui			Avez vous des enfants?	list|0|non|1|oui			האם יש לך ילדים?	list|0|כן|1|לא							\n	q2	dem2	hasChild	q		How many children do you have?	double			Сколько у Вас детей?	double			Combien d''enfants avez vous?	double			Combien d''enfants avez vous?	double			כמה ילדים?	double							\n	male	dem3	1	q		What gender `(hasChild)?''is your oldest child'':''might you want your first child to be''`?	list|0|female|1|male			Какова пола `(hasChild)?''Ваш старший ребенок?'':''Вы бы хотели чтобы был первенец?''`	list|0|мальчик|1|девочка			Quel genre `(hasChild)?''est votre enfant le plus ancien?'':''sera votre premier enfant?''`	list|0|fille|1|garcon			Quel genre `(hasChild)?''est votre enfant le plus ancien?'':''sera votre premier enfant?''`	list|0|fille|1|garcon			מה המין `(hasChild)?'' שתרצה/י שילדך הראשון'':''יהיה''`?	list|0|זכר|1|נקבה							\n	name	dem4	1	q		What `(hasChild)?''is'':''might you want''` `(male)?''his'':''her''` name`(!hasChild)?'' to be'':''''`?	text			Как бы Вы хотели `(male)?''его'':''ее''` назвать?	text			Comment s''appelle votre `(male)?''fils'':''fille''`?	text			Comment s''appelle votre `(male)?''fils'':''fille''`?	text			מה `(hasChild)?''השם'':''שתרצה/י לבחור''`?	text							\n	demo5	dem5	1	q		`name`!  Thanks for trying Dialogix!	nothing			`name`!  Спасибо за участие в опросе!	nothing			`name`!  Merci!	nothing			`name`!  Gracias!	nothing			`name` תודה על השימוש בדיאלוגיקס	nothing							\n', 1, 1);

-- 
-- Dumping data for table 'items'
-- 

INSERT INTO items (id, item_type, has_loinc_code, loinc_num, validation_id, question_id, data_type_id, answer_list_id) VALUES (1, 'Question', 0, '', 1, 5, 6, NULL);
INSERT INTO items (id, item_type, has_loinc_code, loinc_num, validation_id, question_id, data_type_id, answer_list_id) VALUES (2, 'Question', 0, '', 1, 1, 7, NULL);
INSERT INTO items (id, item_type, has_loinc_code, loinc_num, validation_id, question_id, data_type_id, answer_list_id) VALUES (3, 'Question', 0, '', 1, 4, 6, 2);
INSERT INTO items (id, item_type, has_loinc_code, loinc_num, validation_id, question_id, data_type_id, answer_list_id) VALUES (4, 'Question', 0, '', 1, 3, 6, NULL);
INSERT INTO items (id, item_type, has_loinc_code, loinc_num, validation_id, question_id, data_type_id, answer_list_id) VALUES (5, 'Question', 0, '', 1, 2, 6, 1);

-- 
-- Dumping data for table 'language_lists'
-- 

INSERT INTO language_lists (id, name) VALUES (1, 'en_US|ru|fr|es|he');

-- 
-- Dumping data for table 'map_sequence'
-- 

INSERT INTO map_sequence (seq_name, seq_count) VALUES ('loinc_instrument_request', 0);
INSERT INTO map_sequence (seq_name, seq_count) VALUES ('loinc_item_request', 0);
INSERT INTO map_sequence (seq_name, seq_count) VALUES ('semantic_mapping_a', 0);
INSERT INTO map_sequence (seq_name, seq_count) VALUES ('semantic_mapping_i_q_a', 0);
INSERT INTO map_sequence (seq_name, seq_count) VALUES ('semantic_mapping_q', 0);
INSERT INTO map_sequence (seq_name, seq_count) VALUES ('semantic_mapping_q_a', 0);

-- 
-- Dumping data for table 'model_sequence'
-- 

INSERT INTO model_sequence (seq_name, seq_count) VALUES ('answer', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('answer_list', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('answer_list_content', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('answer_list_denormalized', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('answer_localized', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('code_system', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('dialogix_user', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('entry_answer', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('entry_answers_entry_item', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('entry_instrument', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('entry_item', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('help', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('help_localized', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('instrument', 100);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('instrument_content', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('instrument_hash', 100);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('instrument_header', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('instrument_load_error', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('instrument_version', 100);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('item', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('language_list', 100);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('question', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('question_localized', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('readback', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('readback_localized', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('source_content', 0);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('validation', 1000);
INSERT INTO model_sequence (seq_name, seq_count) VALUES ('var_name', 1100);

-- 
-- Dumping data for table 'null_flavors'
-- 

INSERT INTO null_flavors (id, name, display_name, description) VALUES (1, '*UNASKED*', '*UNASKED*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (2, '*NA*', '*NA*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (3, '*REFUSED*', '*REFUSED*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (4, '*INVALID*', '*INVALID*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (5, '*UNKNOWN*', '*UNKNOWN*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (6, '*HUH*', '*HUH*', NULL);

-- 
-- Dumping data for table 'questions'
-- 

INSERT INTO questions (id) VALUES (1);
INSERT INTO questions (id) VALUES (2);
INSERT INTO questions (id) VALUES (3);
INSERT INTO questions (id) VALUES (4);
INSERT INTO questions (id) VALUES (5);

-- 
-- Dumping data for table 'question_localizeds'
-- 

INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (1, 'es', 'Quel genre `(hasChild)?''est votre enfant le plus ancien?'':''sera votre premier enfant?''`', 87, 4);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (2, 'ru', 'Какова пола `(hasChild)?''Ваш старший ребенок?'':''Вы бы хотели чтобы был первенец?''`', 82, 4);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (3, 'fr', 'Quel genre `(hasChild)?''est votre enfant le plus ancien?'':''sera votre premier enfant?''`', 87, 4);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (4, 'es', 'Comment s''appelle votre `(male)?''fils'':''fille''`?', 48, 1);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (5, 'en', 'What `(hasChild)?''is'':''might you want''` `(male)?''his'':''her''` name`(!hasChild)?'' to be'':''''`?', 91, 1);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (6, 'he', 'מה המין `(hasChild)?'' שתרצה/י שילדך הראשון'':''יהיה''`?', 52, 4);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (7, 'ru', 'Сколько у Вас детей?', 20, 3);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (8, 'fr', 'Avez vous des enfants?', 22, 2);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (9, 'fr', '`name`!  Merci!', 15, 5);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (10, 'he', 'האם יש לך ילדים?', 16, 2);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (11, 'en', '`name`!  Thanks for trying Dialogix!', 36, 5);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (12, 'ru', 'Как бы Вы хотели `(male)?''его'':''ее''` назвать?', 45, 1);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (13, 'es', '`name`!  Gracias!', 17, 5);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (14, 'en', 'What gender `(hasChild)?''is your oldest child'':''might you want your first child to be''`?', 88, 4);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (15, 'es', 'Avez vous des enfants?', 22, 2);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (16, 'ru', 'У Вас есть дети?', 16, 2);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (17, 'en', 'Do you have any children?', 25, 2);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (18, 'he', '`name` תודה על השימוש בדיאלוגיקס', 32, 5);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (19, 'fr', 'Comment s''appelle votre `(male)?''fils'':''fille''`?', 48, 1);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (20, 'fr', 'Combien d''enfants avez vous?', 28, 3);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (21, 'he', 'כמה ילדים?', 10, 3);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (22, 'es', 'Combien d''enfants avez vous?', 28, 3);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (23, 'he', 'מה `(hasChild)?''השם'':''שתרצה/י לבחור''`?', 38, 1);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (24, 'en', 'How many children do you have?', 30, 3);
INSERT INTO question_localizeds (id, language_code, name, question_length, question_id) VALUES (25, 'ru', '`name`!  Спасибо за участие в опросе!', 37, 5);

-- 
-- Dumping data for table 'reserved_words'
-- 

INSERT INTO reserved_words (id, name, meaning) VALUES (0, '__LANGUAGES__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (1, '__TITLE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (2, '__ICON__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (3, '__HEADER_MSG__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (4, '__STARTING_STEP__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (6, '__SHOW_QUESTION_REF__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (7, '__AUTOGEN_OPTION_NUM__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (8, '__DEVELOPER_MODE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (9, '__DEBUG_MODE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (10, '__START_TIME__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (11, '__FILENAME__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (12, '__SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (14, '__ALLOW_COMMENTS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (15, '__SCHEDULE_SOURCE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (16, '__LOADED_FROM__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (17, '__CURRENT_LANGUAGE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (19, '__ALLOW_REFUSED__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (20, '__ALLOW_UNKNOWN__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (21, '__ALLOW_DONT_UNDERSTAND__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (22, '__RECORD_EVENTS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (23, '__WORKING_DIR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (24, '__COMPLETED_DIR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (25, '__FLOPPY_DIR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (26, '__IMAGE_FILES_DIR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (27, '__COMMENT_ICON_ON__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (28, '__COMMENT_ICON_OFF__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (29, '__REFUSED_ICON_ON__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (30, '__REFUSED_ICON_OFF__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (31, '__UNKNOWN_ICON_ON__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (32, '__UNKNOWN_ICON_OFF__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (35, '__TRICEPS_VERSION_MAJOR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (36, '__TRICEPS_VERSION_MINOR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (37, '__SCHED_AUTHORS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (38, '__SCHED_VERSION_MAJOR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (39, '__SCHED_VERSION_MINOR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (40, '__SCHED_HELP_URL__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (41, '__HELP_ICON__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (42, '__ACTIVE_BUTTON_PREFIX__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (44, '__TRICEPS_FILE_TYPE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (45, '__DISPLAY_COUNT__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (46, '__SCHEDULE_DIR__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (47, '__ALLOW_JUMP_TO__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (48, '__BROWSER_TYPE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (49, '__IP_ADDRESS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (50, '__SUSPEND_TO_FLOPPY__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (51, '__JUMP_TO_FIRST_UNASKED__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (52, '__REDIRECT_ON_FINISH_URL__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (53, '__REDIRECT_ON_FINISH_MSG__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (56, '__SET_DEFAULT_FOCUS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (59, '__WRAP_ADMIN_ICONS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (60, '__DISALLOW_COMMENTS__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (61, '__CONNECTION_TYPE__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__', NULL);
INSERT INTO reserved_words (id, name, meaning) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__', NULL);

-- 
-- Dumping data for table 'v1_sequence'
-- 

INSERT INTO v1_sequence (seq_name, seq_count) VALUES ('v1_data_element', 0);
INSERT INTO v1_sequence (seq_name, seq_count) VALUES ('v1_instrument_session', 0);
INSERT INTO v1_sequence (seq_name, seq_count) VALUES ('v1_item_usage', 0);
INSERT INTO v1_sequence (seq_name, seq_count) VALUES ('v1_page_usage', 0);

-- 
-- Dumping data for table 'v2_sequence'
-- 

INSERT INTO v2_sequence (seq_name, seq_count) VALUES ('data_element', 0);
INSERT INTO v2_sequence (seq_name, seq_count) VALUES ('instrument_session', 0);
INSERT INTO v2_sequence (seq_name, seq_count) VALUES ('item_usage', 0);
INSERT INTO v2_sequence (seq_name, seq_count) VALUES ('page_usage', 0);
INSERT INTO v2_sequence (seq_name, seq_count) VALUES ('page_usage_event', 0);

-- 
-- Dumping data for table 'validations'
-- 

INSERT INTO validations (id, other_vals, input_mask, max_val, min_val, data_type_id) VALUES (1, '', '', '', '', NULL);

-- 
-- Dumping data for table 'var_names'
-- 

INSERT INTO var_names (id, name) VALUES (0, '__LANGUAGES__');
INSERT INTO var_names (id, name) VALUES (1, '__TITLE__');
INSERT INTO var_names (id, name) VALUES (2, '__ICON__');
INSERT INTO var_names (id, name) VALUES (3, '__HEADER_MSG__');
INSERT INTO var_names (id, name) VALUES (4, '__STARTING_STEP__');
INSERT INTO var_names (id, name) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__');
INSERT INTO var_names (id, name) VALUES (6, '__SHOW_QUESTION_REF__');
INSERT INTO var_names (id, name) VALUES (7, '__AUTOGEN_OPTION_NUM__');
INSERT INTO var_names (id, name) VALUES (8, '__DEVELOPER_MODE__');
INSERT INTO var_names (id, name) VALUES (9, '__DEBUG_MODE__');
INSERT INTO var_names (id, name) VALUES (10, '__START_TIME__');
INSERT INTO var_names (id, name) VALUES (11, '__FILENAME__');
INSERT INTO var_names (id, name) VALUES (12, '__SHOW_ADMIN_ICONS__');
INSERT INTO var_names (id, name) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__');
INSERT INTO var_names (id, name) VALUES (14, '__ALLOW_COMMENTS__');
INSERT INTO var_names (id, name) VALUES (15, '__SCHEDULE_SOURCE__');
INSERT INTO var_names (id, name) VALUES (16, '__LOADED_FROM__');
INSERT INTO var_names (id, name) VALUES (17, '__CURRENT_LANGUAGE__');
INSERT INTO var_names (id, name) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__');
INSERT INTO var_names (id, name) VALUES (19, '__ALLOW_REFUSED__');
INSERT INTO var_names (id, name) VALUES (20, '__ALLOW_UNKNOWN__');
INSERT INTO var_names (id, name) VALUES (21, '__ALLOW_DONT_UNDERSTAND__');
INSERT INTO var_names (id, name) VALUES (22, '__RECORD_EVENTS__');
INSERT INTO var_names (id, name) VALUES (23, '__WORKING_DIR__');
INSERT INTO var_names (id, name) VALUES (24, '__COMPLETED_DIR__');
INSERT INTO var_names (id, name) VALUES (25, '__FLOPPY_DIR__');
INSERT INTO var_names (id, name) VALUES (26, '__IMAGE_FILES_DIR__');
INSERT INTO var_names (id, name) VALUES (27, '__COMMENT_ICON_ON__');
INSERT INTO var_names (id, name) VALUES (28, '__COMMENT_ICON_OFF__');
INSERT INTO var_names (id, name) VALUES (29, '__REFUSED_ICON_ON__');
INSERT INTO var_names (id, name) VALUES (30, '__REFUSED_ICON_OFF__');
INSERT INTO var_names (id, name) VALUES (31, '__UNKNOWN_ICON_ON__');
INSERT INTO var_names (id, name) VALUES (32, '__UNKNOWN_ICON_OFF__');
INSERT INTO var_names (id, name) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__');
INSERT INTO var_names (id, name) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__');
INSERT INTO var_names (id, name) VALUES (35, '__TRICEPS_VERSION_MAJOR__');
INSERT INTO var_names (id, name) VALUES (36, '__TRICEPS_VERSION_MINOR__');
INSERT INTO var_names (id, name) VALUES (37, '__SCHED_AUTHORS__');
INSERT INTO var_names (id, name) VALUES (38, '__SCHED_VERSION_MAJOR__');
INSERT INTO var_names (id, name) VALUES (39, '__SCHED_VERSION_MINOR__');
INSERT INTO var_names (id, name) VALUES (40, '__SCHED_HELP_URL__');
INSERT INTO var_names (id, name) VALUES (41, '__HELP_ICON__');
INSERT INTO var_names (id, name) VALUES (42, '__ACTIVE_BUTTON_PREFIX__');
INSERT INTO var_names (id, name) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__');
INSERT INTO var_names (id, name) VALUES (44, '__TRICEPS_FILE_TYPE__');
INSERT INTO var_names (id, name) VALUES (45, '__DISPLAY_COUNT__');
INSERT INTO var_names (id, name) VALUES (46, '__SCHEDULE_DIR__');
INSERT INTO var_names (id, name) VALUES (47, '__ALLOW_JUMP_TO__');
INSERT INTO var_names (id, name) VALUES (48, '__BROWSER_TYPE__');
INSERT INTO var_names (id, name) VALUES (49, '__IP_ADDRESS__');
INSERT INTO var_names (id, name) VALUES (50, '__SUSPEND_TO_FLOPPY__');
INSERT INTO var_names (id, name) VALUES (51, '__JUMP_TO_FIRST_UNASKED__');
INSERT INTO var_names (id, name) VALUES (52, '__REDIRECT_ON_FINISH_URL__');
INSERT INTO var_names (id, name) VALUES (53, '__REDIRECT_ON_FINISH_MSG__');
INSERT INTO var_names (id, name) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__');
INSERT INTO var_names (id, name) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__');
INSERT INTO var_names (id, name) VALUES (56, '__SET_DEFAULT_FOCUS__');
INSERT INTO var_names (id, name) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__');
INSERT INTO var_names (id, name) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__');
INSERT INTO var_names (id, name) VALUES (59, '__WRAP_ADMIN_ICONS__');
INSERT INTO var_names (id, name) VALUES (60, '__DISALLOW_COMMENTS__');
INSERT INTO var_names (id, name) VALUES (61, '__CONNECTION_TYPE__');
INSERT INTO var_names (id, name) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__');
INSERT INTO var_names (id, name) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__');
INSERT INTO var_names (id, name) VALUES (101, 'q2');
INSERT INTO var_names (id, name) VALUES (102, 'male');
INSERT INTO var_names (id, name) VALUES (103, 'demo5');
INSERT INTO var_names (id, name) VALUES (104, 'hasChild');
INSERT INTO var_names (id, name) VALUES (105, 'name');

--
-- ADD CONSTRAINTS
--
ALTER TABLE v1_page_usages ADD CONSTRAINT v1pgusagesv1nstrumentsessionid FOREIGN KEY (v1_instrument_session_id) REFERENCES v1_instrument_sessions (id)
ALTER TABLE v1_item_usages ADD CONSTRAINT v1itemusagesv1_data_element_id FOREIGN KEY (v1_data_element_id) REFERENCES v1_data_elements (id)
ALTER TABLE instrument_versions ADD CONSTRAINT instrumentversionsinstrumentid FOREIGN KEY (instrument_id) REFERENCES instruments (id)
ALTER TABLE instrument_versions ADD CONSTRAINT nstrmentversionsnstrmenthashid FOREIGN KEY (instrument_hash_id) REFERENCES instrument_hashes (id)
ALTER TABLE instrument_contents ADD CONSTRAINT instrument_contentsreadback_id FOREIGN KEY (readback_id) REFERENCES readbacks (id)
ALTER TABLE instrument_contents ADD CONSTRAINT FK_instrument_contents_help_id FOREIGN KEY (help_id) REFERENCES helps (id)
ALTER TABLE instrument_contents ADD CONSTRAINT nstrumentcontentsdisplaytypeid FOREIGN KEY (display_type_id) REFERENCES display_types (id)
ALTER TABLE instrument_contents ADD CONSTRAINT nstrmntcntentsnstrmntversionid FOREIGN KEY (instrument_version_id) REFERENCES instrument_versions (id)
ALTER TABLE instrument_contents ADD CONSTRAINT FK_instrument_contents_item_id FOREIGN KEY (item_id) REFERENCES items (id)
ALTER TABLE instrument_contents ADD CONSTRAINT instrument_contentsvar_name_id FOREIGN KEY (var_name_id) REFERENCES var_names (id)
ALTER TABLE v1_data_elements ADD CONSTRAINT v1dtlementsv1nstrmentsessionid FOREIGN KEY (v1_instrument_session_id) REFERENCES v1_instrument_sessions (id)
ALTER TABLE validations ADD CONSTRAINT FK_validations_data_type_id FOREIGN KEY (data_type_id) REFERENCES data_types (id)
ALTER TABLE semantic_mapping_i_q_as ADD CONSTRAINT semanticmappingiqasquestion_id FOREIGN KEY (question_id) REFERENCES questions (id)
ALTER TABLE semantic_mapping_i_q_as ADD CONSTRAINT smanticmappingiqascodesystemid FOREIGN KEY (code_system_id) REFERENCES code_systems (id)
ALTER TABLE semantic_mapping_i_q_as ADD CONSTRAINT smntcmppingiqasnstrmntvrsionid FOREIGN KEY (instrument_version_id) REFERENCES instrument_versions (id)
ALTER TABLE semantic_mapping_i_q_as ADD CONSTRAINT semanticmappingi_q_asanswer_id FOREIGN KEY (answer_id) REFERENCES answers (id)
ALTER TABLE page_usage_events ADD CONSTRAINT page_usage_eventspage_usage_id FOREIGN KEY (page_usage_id) REFERENCES page_usages (id)
ALTER TABLE source_content ADD CONSTRAINT surcecontentnstrumentversionid FOREIGN KEY (instrument_version_id) REFERENCES instrument_versions (id)
ALTER TABLE page_usages ADD CONSTRAINT pageusagesinstrumentsession_id FOREIGN KEY (instrument_session_id) REFERENCES instrument_sessions (id)
ALTER TABLE page_usages ADD CONSTRAINT FK_page_usages_action_type_id FOREIGN KEY (action_type_id) REFERENCES action_types (id)
ALTER TABLE answer_localizeds ADD CONSTRAINT FK_answer_localizeds_answer_id FOREIGN KEY (answer_id) REFERENCES answers (id)
ALTER TABLE answer_list_contents ADD CONSTRAINT answerlistcontentsanswerlistid FOREIGN KEY (answer_list_id) REFERENCES answer_lists (id)
ALTER TABLE answer_list_contents ADD CONSTRAINT answer_list_contents_answer_id FOREIGN KEY (answer_id) REFERENCES answers (id)
ALTER TABLE semantic_mapping_q_as ADD CONSTRAINT semanticmappingq_asquestion_id FOREIGN KEY (question_id) REFERENCES questions (id)
ALTER TABLE semantic_mapping_q_as ADD CONSTRAINT semantic_mapping_q_asanswer_id FOREIGN KEY (answer_id) REFERENCES answers (id)
ALTER TABLE semantic_mapping_q_as ADD CONSTRAINT semanticmappingqascodesystemid FOREIGN KEY (code_system_id) REFERENCES code_systems (id)
ALTER TABLE display_types ADD CONSTRAINT FK_display_types_data_type_id FOREIGN KEY (data_type_id) REFERENCES data_types (id)
ALTER TABLE question_localizeds ADD CONSTRAINT question_localizedsquestion_id FOREIGN KEY (question_id) REFERENCES questions (id)
ALTER TABLE semantic_mapping_qs ADD CONSTRAINT semantic_mapping_qsquestion_id FOREIGN KEY (question_id) REFERENCES questions (id)
ALTER TABLE semantic_mapping_qs ADD CONSTRAINT semanticmappingqscodesystem_id FOREIGN KEY (code_system_id) REFERENCES code_systems (id)
ALTER TABLE instrument_sessions ADD CONSTRAINT instrumentsessionsinstrumentid FOREIGN KEY (instrument_id) REFERENCES instruments (id)
ALTER TABLE instrument_sessions ADD CONSTRAINT instrumentsessionsactiontypeid FOREIGN KEY (action_type_id) REFERENCES action_types (id)
ALTER TABLE instrument_sessions ADD CONSTRAINT nstrmntsssionsnstrmntversionid FOREIGN KEY (instrument_version_id) REFERENCES instrument_versions (id)
ALTER TABLE instrument_sessions ADD CONSTRAINT nstrumentsessionsdalogixuserid FOREIGN KEY (dialogix_user_id) REFERENCES dialogix_users (id)
ALTER TABLE item_usages ADD CONSTRAINT itemusagesinstrumentcontent_id FOREIGN KEY (instrument_content_id) REFERENCES instrument_contents (id)
ALTER TABLE item_usages ADD CONSTRAINT FK_item_usages_var_name_id FOREIGN KEY (var_name_id) REFERENCES var_names (id)
ALTER TABLE item_usages ADD CONSTRAINT itemusagesinstrumentsession_id FOREIGN KEY (instrument_session_id) REFERENCES instrument_sessions (id)
ALTER TABLE instrument_load_errors ADD CONSTRAINT nstrmntlderrorsnstrmntvrsionid FOREIGN KEY (instrument_version_id) REFERENCES instrument_versions (id)
ALTER TABLE help_localizeds ADD CONSTRAINT FK_help_localizeds_help_id FOREIGN KEY (help_id) REFERENCES helps (id)
ALTER TABLE loinc_item_requests ADD CONSTRAINT FK_loinc_item_requests_item_id FOREIGN KEY (item_id) REFERENCES items (id)
ALTER TABLE semantic_mapping_as ADD CONSTRAINT semantic_mapping_as_answer_id FOREIGN KEY (answer_id) REFERENCES answers (id)
ALTER TABLE semantic_mapping_as ADD CONSTRAINT semanticmappingascodesystem_id FOREIGN KEY (code_system_id) REFERENCES code_systems (id)
ALTER TABLE instrument_headers ADD CONSTRAINT nstrmntheadersnstrmntversionid FOREIGN KEY (instrument_version_id) REFERENCES instrument_versions (id)
ALTER TABLE instrument_headers ADD CONSTRAINT nstrumentheadersreservedwordid FOREIGN KEY (reserved_word_id) REFERENCES reserved_words (id)
ALTER TABLE items ADD CONSTRAINT FK_items_validation_id FOREIGN KEY (validation_id) REFERENCES validations (id)
ALTER TABLE items ADD CONSTRAINT FK_items_data_type_id FOREIGN KEY (data_type_id) REFERENCES data_types (id)
ALTER TABLE items ADD CONSTRAINT FK_items_question_id FOREIGN KEY (question_id) REFERENCES questions (id)
ALTER TABLE items ADD CONSTRAINT FK_items_answer_list_id FOREIGN KEY (answer_list_id) REFERENCES answer_lists (id)
ALTER TABLE loinc_instrument_requests ADD CONSTRAINT lncnstrmntrquestsnstrmntvrsnid FOREIGN KEY (instrument_version_id) REFERENCES instrument_versions (id)
ALTER TABLE answer_list_denormalizeds ADD CONSTRAINT nswrlstdenormalizedsnswrlistid FOREIGN KEY (answer_list_id) REFERENCES answer_lists (id)
ALTER TABLE data_elements ADD CONSTRAINT dataelementsnstrumentcontentid FOREIGN KEY (instrument_content_id) REFERENCES instrument_contents (id)
ALTER TABLE data_elements ADD CONSTRAINT dataelementsnstrumentsessionid FOREIGN KEY (instrument_session_id) REFERENCES instrument_sessions (id)
ALTER TABLE data_elements ADD CONSTRAINT FK_data_elements_var_name_id FOREIGN KEY (var_name_id) REFERENCES var_names (id)
ALTER TABLE readback_localizeds ADD CONSTRAINT readback_localizedsreadback_id FOREIGN KEY (readback_id) REFERENCES readbacks (id)
ALTER TABLE instrument_hashes ADD CONSTRAINT instrumenthasheslanguagelistid FOREIGN KEY (language_list_id) REFERENCES language_lists (id)
CREATE TABLE model_sequence (seq_name VARCHAR2(50) NOT NULL, seq_count NUMBER(38) NULL, PRIMARY KEY (seq_name))
CREATE TABLE v2_sequence (seq_name VARCHAR2(50) NOT NULL, seq_count NUMBER(38) NULL, PRIMARY KEY (seq_name))
CREATE TABLE map_sequence (seq_name VARCHAR2(50) NOT NULL, seq_count NUMBER(38) NULL, PRIMARY KEY (seq_name))
CREATE TABLE v1_sequence (seq_name VARCHAR2(50) NOT NULL, seq_count NUMBER(38) NULL, PRIMARY KEY (seq_name))
