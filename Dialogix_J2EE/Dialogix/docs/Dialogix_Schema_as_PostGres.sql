-- 
-- Table structure for table 'action_types'
-- 

CREATE TABLE action_types (
  id bigserial,
  "name" varchar(255) NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'answer_list_contents'
-- 

CREATE TABLE answer_list_contents (
  id bigserial,
  answer_code varchar(255) NOT NULL,
  answer_order bigint NOT NULL,
  answer_list_id bigint default NULL,
  answer_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'answer_list_denormalizeds'
-- 

CREATE TABLE answer_list_denormalizeds (
  id bigserial,
  answer_list_denormalized_length bigint NOT NULL,
  language_code varchar(2) NOT NULL,
  "name" text NOT NULL,
  answer_list_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'answer_lists'
-- 

CREATE TABLE answer_lists (
  id bigserial,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'answer_localizeds'
-- 

CREATE TABLE answer_localizeds (
  id bigserial,
  "name" text,
  answer_length bigint NOT NULL,
  language_code varchar(2) NOT NULL,
  answer_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'answers'
-- 

CREATE TABLE answers (
  id bigserial,
  has_la_code smallint default '0',
  la_code varchar(255) default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'code_systems'
-- 

CREATE TABLE code_systems (
  id bigserial,
  "name" varchar(255) default NULL,
  code_system_oid varchar(255) default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'data_elements'
-- 

CREATE TABLE data_elements (
  id bigserial,
  comments text,
  time_stamp timestamp default NULL,
  group_num bigint default NULL,
  when_as_ms bigint NOT NULL,
  question_as_asked text,
  display_num bigint NOT NULL,
  answer_string text,
  item_visits bigint default NULL,
  null_flavor_id bigserial,
  response_latency bigint default NULL,
  language_code varchar(2) default NULL,
  response_duration bigint default NULL,
  answer_id bigint default NULL,
  answer_code text,
  data_element_sequence bigint NOT NULL,
  var_name_id bigint default NULL,
  instrument_content_id bigint default NULL,
  instrument_session_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'data_types'
-- 

CREATE TABLE data_types (
  id bigserial,
  "name" varchar(255) NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'dialogix_actions'
-- 

CREATE TABLE dialogix_actions (
  id bigserial,
  action_code varchar(100) default NULL,
  display_text varchar(100) NOT NULL,
  "name" varchar(100) NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'dialogix_roles'
-- 

CREATE TABLE dialogix_roles (
  id bigserial,
  codetype varchar(50) NOT NULL,
  "name" varchar(100) default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'dialogix_users'
-- 

CREATE TABLE dialogix_users (
  id bigserial,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  user_name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  phone varchar(255) NOT NULL,
  pwd varchar(255) NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'display_types'
-- 

CREATE TABLE display_types (
  id bigserial,
  sas_informat varchar(255) NOT NULL,
  sas_format varchar(255) NOT NULL,
  "name" varchar(255) NOT NULL,
  spss_level varchar(255) NOT NULL,
  spss_format varchar(255) NOT NULL,
  loinc_scale varchar(255) NOT NULL,
  has_answer_list smallint default '0',
  data_type_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'function_names'
-- 

CREATE TABLE function_names (
  id bigserial,
  syntax text NOT NULL,
  description text NOT NULL,
  "name" varchar(255) NOT NULL,
  definition text NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'help_localizeds'
-- 

CREATE TABLE help_localizeds (
  id bigserial,
  language_code varchar(2) NOT NULL,
  "name" text,
  help_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'helps'
-- 

CREATE TABLE helps (
  id bigserial,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'instrument_contents'
-- 

CREATE TABLE instrument_contents (
  id bigserial,
  default_answer text,
  spss_format varchar(255) default NULL,
  item_sequence bigint NOT NULL,
  sas_informat varchar(255) default NULL,
  is_required bigint NOT NULL,
  sas_format varchar(255) default NULL,
  display_name text,
  spss_level varchar(255) default NULL,
  relevance text NOT NULL,
  concept text,
  format_mask text,
  is_read_only bigint NOT NULL,
  group_num bigint NOT NULL,
  is_message bigint NOT NULL,
  item_action_type varchar(255) default NULL,
  var_name_id bigint default NULL,
  instrument_version_id bigint default NULL,
  display_type_id bigint default NULL,
  item_id bigint default NULL,
  help_id bigint default NULL,
  readback_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'instrument_hashes'
-- 

CREATE TABLE instrument_hashes (
  id bigserial,
  num_equations bigint NOT NULL,
  num_questions bigint NOT NULL,
  var_list_md5 varchar(255) NOT NULL,
  num_branches bigint NOT NULL,
  num_languages bigint NOT NULL,
  num_tailorings bigint NOT NULL,
  num_vars bigint NOT NULL,
  num_groups bigint default NULL,
  num_instructions bigint NOT NULL,
  instrument_md5 varchar(255) NOT NULL,
  language_list_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'instrument_headers'
-- 

CREATE TABLE instrument_headers (
  id bigserial,
  "name" text NOT NULL,
  reserved_word_id bigint default NULL,
  instrument_version_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'instrument_load_errors'
-- 

CREATE TABLE instrument_load_errors (
  id bigserial,
  source_column bigint default NULL,
  source_text text,
  source_row bigint default NULL,
  log_level bigint default NULL,
  error_message text,
  instrument_version_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'instrument_sessions'
-- 

CREATE TABLE instrument_sessions (
  id bigserial,
  finished bigint default NULL,
  num_vars bigint default NULL,
  start_time timestamp NOT NULL,
  num_groups bigint default NULL,
  instrument_starting_group bigint NOT NULL,
  instrument_session_file_name text,
  current_var_name bigint NOT NULL,
  ip_address varchar(255) default NULL,
  language_code varchar(2) NOT NULL,
  browser varchar(255) default NULL,
  max_group bigint default NULL,
  current_group bigint NOT NULL,
  display_num bigint NOT NULL,
  last_access_time timestamp NOT NULL,
  status_msg varchar(255) default NULL,
  max_var_num bigint default NULL,
  dialogix_user_id bigint default NULL,
  instrument_version_id bigint default NULL,
  instrument_id bigint default NULL,
  action_type_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'instrument_versions'
-- 

CREATE TABLE instrument_versions (
  id bigserial,
  instrument_status bigint default NULL,
  creation_time_stamp timestamp NOT NULL,
  "name" varchar(255) NOT NULL,
  instrument_version_file_name text,
  has_loinc_code smallint default '0',
  instrument_notes text,
  loinc_num varchar(255) default NULL,
  num_rows bigint NOT NULL,
  num_cols bigint NOT NULL,
  instrument_as_spreadsheet_contents text,
  instrument_hash_id bigint default NULL,
  instrument_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'instruments'
-- 

CREATE TABLE instruments (
  id bigserial,
  instrument_description text,
  "name" varchar(255) NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'item_usages'
-- 

CREATE TABLE item_usages (
  id bigserial,
  null_flavor_id bigserial,
  comments text,
  item_usage_sequence bigint NOT NULL,
  time_stamp timestamp default NULL,
  group_num bigint default NULL,
  when_as_ms bigint NOT NULL,
  question_as_asked text,
  display_num bigint NOT NULL,
  answer_string text,
  item_visits bigint default NULL,
  response_latency bigint default NULL,
  language_code varchar(2) default NULL,
  response_duration bigint default NULL,
  answer_id bigint default NULL,
  answer_code text,
  data_element_sequence bigint NOT NULL,
  var_name_id bigint default NULL,
  instrument_content_id bigint default NULL,
  instrument_session_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'items'
-- 

CREATE TABLE items (
  id bigserial,
  item_type varchar(255) NOT NULL,
  has_loinc_code smallint default '0',
  loinc_num varchar(255) default NULL,
  validation_id bigint default NULL,
  question_id bigint default NULL,
  data_type_id bigint default NULL,
  answer_list_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'language_lists'
-- 

CREATE TABLE language_lists (
  id bigserial,
  "name" text NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'loinc_instrument_requests'
-- 

CREATE TABLE loinc_instrument_requests (
  id bigserial,
  loinc_system varchar(255) default NULL,
  loinc_scale varchar(255) default NULL,
  loinc_property varchar(255) default NULL,
  loinc_method varchar(255) default NULL,
  loinc_num varchar(255) default NULL,
  loinc_time_aspect varchar(255) default NULL,
  instrument_version_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'loinc_item_requests'
-- 

CREATE TABLE loinc_item_requests (
  id bigserial,
  loinc_system varchar(255) default NULL,
  loinc_scale varchar(255) default NULL,
  loinc_property varchar(255) default NULL,
  loinc_method varchar(255) default NULL,
  loinc_num varchar(255) default NULL,
  loinc_time_aspect varchar(255) default NULL,
  item_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'null_flavors'
-- 

CREATE TABLE null_flavors (
  id bigserial,
  "name" varchar(255) NOT NULL,
  display_name varchar(255) NOT NULL,
  description text,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'page_usage_events'
-- 

CREATE TABLE page_usage_events (
  id bigserial,
  time_stamp timestamp default NULL,
  duration bigint NOT NULL,
  page_usage_event_sequence bigint NOT NULL,
  value1 varchar(255) NOT NULL,
  event_type varchar(255) NOT NULL,
  value2 varchar(255) NOT NULL,
  gui_action_type varchar(255) NOT NULL,
  var_name varchar(255) NOT NULL,
  page_usage_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'page_usages'
-- 

CREATE TABLE page_usages (
  id bigserial,
  page_duration bigint default NULL,
  server_duration bigint default NULL,
  page_usage_sequence bigint NOT NULL,
  load_duration bigint default NULL,
  time_stamp timestamp NOT NULL,
  network_duration bigint default NULL,
  to_group_num bigint NOT NULL,
  page_visits bigint default NULL,
  status_msg varchar(255) default NULL,
  used_jvm_memory bigint default NULL,
  ip_address varchar(255) default NULL,
  from_group_num bigint NOT NULL,
  browser varchar(255) default NULL,
  total_duration bigint default NULL,
  display_num bigint NOT NULL,
  language_code varchar(2) NOT NULL,
  action_type_id bigint default NULL,
  instrument_session_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'parser_tests'
-- 

CREATE TABLE parser_tests (
  id bigserial,
  created_on timestamp NOT NULL default CURRENT_TIMESTAMP,
  jsession text NOT NULL,
  equation text NOT NULL,
  answer text NOT NULL,
  expected text NOT NULL,
  correct bigint NOT NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'question_localizeds'
-- 

CREATE TABLE question_localizeds (
  id bigserial,
  language_code varchar(2) NOT NULL,
  "name" text,
  question_length bigint NOT NULL,
  question_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'questions'
-- 

CREATE TABLE questions (
  id bigserial,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'readback_localizeds'
-- 

CREATE TABLE readback_localizeds (
  id bigserial,
  language_code varchar(2) NOT NULL,
  "name" text,
  readback_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'readbacks'
-- 

CREATE TABLE readbacks (
  id bigserial,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'reserved_words'
-- 

CREATE TABLE reserved_words (
  id bigserial,
  "name" varchar(255) NOT NULL,
  meaning varchar(255) default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'roles_actions'
-- 

CREATE TABLE roles_actions (
  id bigserial,
  dialogix_roles_id bigserial,
  dialogix_actions_id bigserial,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'semantic_mapping_as'
-- 

CREATE TABLE semantic_mapping_as (
  id bigserial,
  code_display_name text,
  "code" text,
  answer_id bigint default NULL,
  code_system_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'semantic_mapping_i_q_as'
-- 

CREATE TABLE semantic_mapping_i_q_as (
  id bigserial,
  "code" text,
  code_display_name text,
  question_id bigint default NULL,
  code_system_id bigint default NULL,
  answer_id bigint default NULL,
  instrument_version_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'semantic_mapping_q_as'
-- 

CREATE TABLE semantic_mapping_q_as (
  id bigserial,
  code_display_name text,
  "code" text,
  question_id bigint default NULL,
  answer_id bigint default NULL,
  code_system_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'semantic_mapping_qs'
-- 

CREATE TABLE semantic_mapping_qs (
  id bigserial,
  code_display_name text,
  "code" text,
  question_id bigint default NULL,
  code_system_id bigint default NULL,
  PRIMARY KEY  (id)
);


-- --------------------------------------------------------

-- 
-- Table structure for table 'users_roles'
-- 

CREATE TABLE users_roles (
  id bigserial,
  dialogix_users_id bigserial,
  dialogix_roles_id bigserial,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'v1_data_elements'
-- 

CREATE TABLE v1_data_elements (
  id bigserial,
  group_num bigint default NULL,
  item_visits bigint default NULL,
  data_element_sequence bigint NOT NULL,
  var_name varchar(200) NOT NULL,
  v1_instrument_session_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'v1_instrument_sessions'
-- 

CREATE TABLE v1_instrument_sessions (
  id bigserial,
  max_group bigint default NULL,
  max_var_num bigint default NULL,
  instrument_version_name varchar(255) NOT NULL,
  instrument_version_file_name text,
  last_access_time timestamp NOT NULL,
  instrument_session_file_name text,
  current_group bigint default NULL,
  num_vars bigint default NULL,
  language_code varchar(2) default NULL,
  var_list_md5 varchar(255) default NULL,
  status_msg varchar(255) default NULL,
  num_groups bigint default NULL,
  start_time timestamp NOT NULL,
  finished bigint default NULL,
  display_num bigint default NULL,
  ip_address varchar(255) default NULL,
  browser varchar(255) default NULL,
  action_type varchar(255) default NULL,
  instrument_starting_group bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'v1_item_usages'
-- 

CREATE TABLE v1_item_usages (
  id bigserial,
  answer_code0 text,
  answer_string0 text,
  display_num bigint NOT NULL,
  time_stamp timestamp default NULL,
  question_as_asked text,
  when_as_ms bigint default NULL,
  answer_string text,
  item_visits bigint default NULL,
  language_code varchar(2) default NULL,
  comments text,
  item_usage_sequence bigint NOT NULL,
  comments0 text,
  answer_code text,
  v1_data_element_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'v1_page_usages'
-- 

CREATE TABLE v1_page_usages (
  id bigserial,
  server_duration bigint default NULL,
  load_duration bigint default NULL,
  display_num bigint NOT NULL,
  network_duration bigint default NULL,
  page_duration bigint default NULL,
  language_code varchar(2) default NULL,
  total_duration bigint default NULL,
  action_type varchar(255) default NULL,
  v1_instrument_session_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'validations'
-- 

CREATE TABLE validations (
  id bigserial,
  other_vals varchar(255) default NULL,
  input_mask varchar(255) default NULL,
  max_val varchar(255) default NULL,
  min_val varchar(255) default NULL,
  data_type_id bigint default NULL,
  PRIMARY KEY  (id)
);

-- --------------------------------------------------------

-- 
-- Table structure for table 'var_names'
-- 

CREATE TABLE var_names (
  id bigserial,
  "name" varchar(255) NOT NULL,
  PRIMARY KEY  (id)
);


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
-- Dumping data for table code_systems
-- 

INSERT INTO code_systems (id, name, code_system_oid) VALUES (1, 'LOINC', '2.16.840.1.113883.6.1');
INSERT INTO code_systems (id, name, code_system_oid) VALUES (2, 'SNOMED-CT', '2.16.840.1.113883.6.96');

-- 
-- Dumping data for table data_types
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
-- Dumping data for table null_flavors
-- 

INSERT INTO null_flavors (id, name, display_name, description) VALUES (1, '*UNASKED*', '*UNASKED*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (2, '*NA*', '*NA*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (3, '*REFUSED*', '*REFUSED*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (4, '*INVALID*', '*INVALID*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (5, '*UNKNOWN*', '*UNKNOWN*', NULL);
INSERT INTO null_flavors (id, name, display_name, description) VALUES (6, '*HUH*', '*HUH*', NULL);

-- 
-- Dumping data for table reserved_words
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
-- Dumping data for table var_names
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