-- CREATE USER 'dialogix'@'%' IDENTIFIED BY 'dialogix_pass';                                                                                                                  
-- GRANT USAGE ON * . * TO 'dialogix'@'%' IDENTIFIED BY 'dialogix_pass' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

DROP DATABASE IF EXISTS dialogix_j2ee;
CREATE DATABASE IF NOT EXISTS dialogix_j2ee DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;     
USE dialogix_j2ee;                                                                                      
GRANT ALL PRIVILEGES ON dialogix_j2ee . * TO 'dialogix_j2ee'@'%';    

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


CREATE TABLE menu (
  menu_id int(11) NOT NULL,
  menu_code varchar(100) default NULL,
  display_text varchar(100) NOT NULL,
  menu_name varchar(100) NOT NULL,
  PRIMARY KEY  (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    
CREATE TABLE action_type (
  action_type_id int(11) NOT NULL,
  action_name varchar(255) NOT NULL,
  PRIMARY KEY (action_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE answer (
  answer_id bigint(20) default NULL,	-- to avoid an error
  PRIMARY KEY (answer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE answer_list (
  answer_list_id bigint(20) NOT NULL,
  PRIMARY KEY (answer_list_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE answer_list_content (
  answer_list_content_id bigint(20) NOT NULL,
  answer_code varchar(255) NOT NULL,
  answer_id bigint(20) default NULL, -- should be NOT NULL?
  answer_list_id bigint(20) default NULL, -- should be NOT NULL?
  answer_order int(11) NOT NULL,
  PRIMARY KEY (answer_list_content_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE answer_list_denorm (
  answer_list_denorm_id bigint(20) NOT NULL,
  answer_list_denorm_len int(11) NOT NULL,
  answer_list_denorm_string mediumtext NOT NULL,
  answer_list_id bigint(20) default NULL, -- should be NOT NULL?
  language_code varchar(2) NOT NULL,
  PRIMARY KEY (answer_list_denorm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE answer_localized (
  answer_localized_id bigint(20) NOT NULL,
  answer_id bigint(20) default NULL, -- should be NOT NULL?
  answer_length int(11) NOT NULL,
  answer_string mediumtext,
  language_code varchar(2) NOT NULL,
  PRIMARY KEY (answer_localized_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE code_system (
  code_system_id int(11) NOT NULL,
  code_system_name varchar(255) NOT NULL,
  code_system_oid varchar(255) default NULL,
  PRIMARY KEY (code_system_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE data_element (
  data_element_id bigint(20) NOT NULL,
  data_element_sequence int(11) NOT NULL,
  group_num int(11) NOT NULL,
  instrument_content_id bigint(20) default NULL,	-- must be NULL for RESERVED word (how annotated?)
  instrument_session_id bigint(20) NOT NULL,
  item_visits int(11) NOT NULL,
  var_name_id bigint(20) NOT NULL,
  PRIMARY KEY (data_element_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE data_type (
  data_type_id int(11) NOT NULL,
  data_type varchar(255) NOT NULL,
  PRIMARY KEY (data_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE display_type (
  display_type_id int(11) NOT NULL,
  data_type_id int(11) default NULL,
  display_type varchar(255) NOT NULL,
  has_answer_list tinyint(1) default '0',
  loinc_scale varchar(255) NOT NULL,
  sas_format varchar(255) NOT NULL,
  sas_informat varchar(255) NOT NULL,
  spss_format varchar(255) NOT NULL,
  spss_level varchar(255) NOT NULL,
  PRIMARY KEY (display_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE help (
  help_id bigint(20) NOT NULL,
  PRIMARY KEY (help_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE help_localized (
  help_localized_id bigint(20) NOT NULL,
  help_id bigint(20) default NULL,
  help_string mediumtext,
  language_code varchar(2) NOT NULL,
  PRIMARY KEY (help_localized_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE instrument (
  instrument_id bigint(20) NOT NULL,
  instrument_description text default NULL,
  instrument_name varchar(255) NOT NULL,
  PRIMARY KEY (instrument_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE instrument_content (
  instrument_content_id bigint(20) NOT NULL,
  concept mediumtext,
  default_answer mediumtext,
  display_name mediumtext,
  display_type_id int(11) default NULL, -- should be NOT NULL?
  format_mask mediumtext,
  group_num int(11) NOT NULL,
  help_id bigint(20) default NULL,
  instrument_version_id bigint(20) default NULL, -- should be NOT NULL?
  is_message int(11) NOT NULL,
  is_read_only int(11) NOT NULL,
  is_required int(11) NOT NULL,
  item_action_type varchar(255) default NULL, -- should be NOT NULL?
  item_id bigint(20) default NULL, -- should be NOT NULL?
  item_sequence int(11) NOT NULL,
  readback_id bigint(20) default NULL,
  relevance mediumtext NOT NULL,
  sas_format varchar(255) default NULL,
  sas_informat varchar(255) default NULL,
  spss_format varchar(255) default NULL,
  spss_level varchar(255) default NULL,
  var_name_id bigint(20) default NULL, -- should be NOT NULL?
  PRIMARY KEY (instrument_content_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE instrument_hash (
  instrument_hash_id bigint(20) NOT NULL,
  instrument_md5 varchar(255) NOT NULL,
  language_list_id int(11) default NULL,
  num_branches int(11) NOT NULL,
  num_equations int(11) NOT NULL,
  num_groups int(11) default NULL,
  num_instructions int(11) NOT NULL,
  num_languages int(11) NOT NULL,
  num_questions int(11) NOT NULL,
  num_tailorings int(11) NOT NULL,
  num_vars int(11) NOT NULL,
  var_list_md5 varchar(255) NOT NULL,
  PRIMARY KEY (instrument_hash_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE instrument_header (
  instrument_header_id bigint(20) NOT NULL,
  header_value mediumtext NOT NULL,
  instrument_version_id bigint(20) default NULL, -- should be NOT NULL?
  reserved_word_id int(11) default NULL, -- should be NOT NULL?
  PRIMARY KEY (instrument_header_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE instrument_load_error (
  instrument_load_error_id bigint(20) NOT NULL,
  error_message mediumtext,
  instrument_version_id bigint(20) default NULL, -- should be NOT NULL?
  log_level int(11) default NULL, -- should be NOT NULL?
  source_column int(11) default NULL, -- should be NOT NULL?
  source_row int(11) default NULL, -- should be NOT NULL?
  source_text mediumtext,
  PRIMARY KEY (instrument_load_error_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE instrument_session (
  instrument_session_id bigint(20) NOT NULL,
  action_type_id int(11) default NULL, -- should be NOT NULL?
  browser varchar(255) default NULL, -- should be NOT NULL?
  current_group int(11) NOT NULL,
  current_var_num int(11) NOT NULL,
  display_num int(11) NOT NULL,
  finished int(11) default NULL, -- should be NOT NULL?
  instrument_starting_group int(11) NOT NULL,
  instrument_version_id bigint(20) default NULL, -- should be NOT NULL?
  ip_address varchar(255) default NULL, -- should be NOT NULL?
  language_code varchar(2) NOT NULL,
  last_access_time datetime NOT NULL,
  max_group_visited int(11) default NULL, -- should be NOT NULL?
  max_var_num_visited int(11) default NULL, -- should be NOT NULL?
  num_groups int(11) default NULL, -- should be NOT NULL?
  num_vars int(11) default NULL, -- should be NOT NULL?
  person_id bigint(20) default NULL,	-- allows anonymous
  start_time datetime NOT NULL,
  status_msg varchar(255) default NULL,
  PRIMARY KEY (instrument_session_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE instrument_version (
  instrument_version_id bigint(20) NOT NULL,
  apelon_import_xml longtext,
  creation_time_stamp datetime NOT NULL,
  instrument_as_spreadsheet longtext,
  instrument_hash_id bigint(20) default NULL, -- should be NOT NULL?
  instrument_id bigint(20) default NULL, -- should be NOT NULL?
  instrument_notes mediumtext,
  instrument_status int(11) default NULL,
  version_string varchar(255) NOT NULL,
  num_cols int(11) NOT NULL,
  num_rows int(11) NOT NULL,
  PRIMARY KEY (instrument_version_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE item (
  item_id bigint(20) NOT NULL,
  answer_list_id bigint(20) default NULL, -- should be NOT NULL?
  data_type_id int(11) default NULL, -- should be NOT NULL?
  item_type char(2) NOT NULL,
  question_id bigint(20) default NULL, -- should be NOT NULL?
  validation_id bigint(20) default NULL, -- should be NOT NULL?
  PRIMARY KEY (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE item_usage (
  item_usage_id bigint(20) NOT NULL,
  answer_code mediumtext default NULL,
  answer_id bigint(20) default NULL, -- should be NOT NULL?
  answer_string mediumtext default NULL,
  comments mediumtext,
  data_element_id bigint(20) NOT NULL,
  display_num int(11) NOT NULL,
  item_usage_sequence int(11) NOT NULL,
  item_visit int(11) default NULL,
  language_code varchar(2) default NULL,
  null_flavor_id int(11) default NULL,	-- null or 0 means OK
  null_flavor_change_id int(11) default NULL,
  question_as_asked mediumtext,
  response_duration int(11) default NULL,
  response_latency int(11) default NULL,
  time_stamp datetime default NULL,
  when_as_ms bigint(20) NOT NULL,
  PRIMARY KEY (item_usage_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE language_list (
  language_list_id int(11) NOT NULL,
  language_list mediumtext NOT NULL,
  PRIMARY KEY (language_list_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE null_flavor (
  null_flavor_id int(11) NOT NULL,
  display_name varchar(255) NOT NULL,
  null_flavor varchar(255) NOT NULL,
  PRIMARY KEY (null_flavor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE null_flavor_change (
  null_flavor_change_id int(11) NOT NULL,
	null_flavor_change_code varchar(5) NOT NULL,
  null_flavor_change_string varchar(50) NOT NULL,
  PRIMARY KEY  (null_flavor_change_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE page_usage (
  page_usage_id bigint(20) NOT NULL,
  action_type_id int(11) default NULL, -- should be NOT NULL?
  browser varchar(255) default NULL, -- should be NOT NULL?
  display_num int(11) NOT NULL,
  from_group_num int(11) NOT NULL,
  instrument_session_id bigint(20) default NULL, -- should be NOT NULL?
  ip_address varchar(255) default NULL, -- should be NOT NULL?
  language_code varchar(2) NOT NULL,
  load_duration int(11) default NULL, -- should be NOT NULL?
  network_duration int(11) default NULL, -- should be NOT NULL?
  page_duration int(11) default NULL, -- should be NOT NULL?
  page_usage_sequence int(11) NOT NULL,
  page_visits int(11) default NULL, -- should be NOT NULL?
  server_duration int(11) default NULL, -- should be NOT NULL?
  status_msg varchar(255) default NULL,
  time_stamp datetime NOT NULL,
  to_group_num int(11) NOT NULL,
  total_duration int(11) default NULL, -- should be NOT NULL?
  used_jvm_memory bigint(20) default NULL, -- should be NOT NULL?
  PRIMARY KEY (page_usage_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE page_usage_event (
  page_usage_event_id bigint(20) NOT NULL,
  duration int(11) NOT NULL,
  event_type varchar(255) NOT NULL,
  gui_action_type varchar(255) NOT NULL,
  page_usage_event_sequence int(11) NOT NULL,
  page_usage_id bigint(20) default NULL, -- should be NOT NULL?
  time_stamp datetime default NULL, -- should be NOT NULL?
  value1 varchar(255) NOT NULL,
  value2 varchar(255) NOT NULL,
  var_name varchar(255) NOT NULL,
  PRIMARY KEY (page_usage_event_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE parser_test (
  answer text NOT NULL,
  correct int(11) NOT NULL,
  created_on timestamp NOT NULL default CURRENT_TIMESTAMP,
  equation text NOT NULL,
  expected text NOT NULL,
  parser_test_id int(11) NOT NULL,
  PRIMARY KEY (parser_test_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE person (
  person_id bigint(20) NOT NULL,
  email varchar(255) default NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  phone varchar(255) default NULL,
  pwd varchar(255) default NULL,
  user_name varchar(255) NOT NULL,
  PRIMARY KEY (person_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE person_role (
	person_role_id bigint( 20 ) NOT NULL ,
	person_id bigint( 20 ) NOT NULL ,
	role_id int( 11 ) NOT NULL ,
	PRIMARY KEY ( person_role_id ) 
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

CREATE TABLE question (
  question_id bigint(20) NOT NULL,
  PRIMARY KEY (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE question_localized (
  question_localized_id bigint(20) NOT NULL,
  language_code varchar(2) NOT NULL,
  question_id bigint(20) default NULL, -- should be NOT NULL?
  question_length int(11) NOT NULL,
  question_string mediumtext,
  PRIMARY KEY (question_localized_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE readback (
  readback_id bigint(20) NOT NULL,
  PRIMARY KEY (readback_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE readback_localized (
  readback_localized_id bigint(20) NOT NULL,
  readback_id bigint(20) default NULL, -- should be NOT NULL?
  language_code varchar(2) NOT NULL,
  readback_string mediumtext,
  PRIMARY KEY (readback_localized_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE reserved_word (
  reserved_word_id int(11) NOT NULL,
  meaning varchar(255) default NULL,
  reserved_word varchar(255) NOT NULL,
  PRIMARY KEY (reserved_word_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE role (
  role_id int(11) NOT NULL,
  codetype varchar(50) NOT NULL,
  role varchar(100) default NULL,
  PRIMARY KEY  (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE role_menu (
	role_menu_id int( 11 ) NOT NULL ,
	role_id int( 11 ) NOT NULL ,
	menu_id int( 11 ) NOT NULL ,
	PRIMARY KEY ( role_menu_id ) 
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

CREATE TABLE semantic_mapping_a (
  semantic_mapping_a_id bigint(20) NOT NULL,
  answer_id bigint(20) default NULL, -- should be NOT NULL?
  code_display_name mediumtext,
  code_system_id int(11) default NULL, -- should be NOT NULL?
  code_value mediumtext,
  PRIMARY KEY (semantic_mapping_a_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE semantic_mapping_i_q_a (
  semantic_mapping_i_q_a_id bigint(20) NOT NULL,
  answer_id bigint(20) default NULL, -- should be NOT NULL?
  code_display_name mediumtext,
  code_system_id int(11) default NULL, -- should be NOT NULL?
  code_value mediumtext,
  instrument_version_id bigint(20) default NULL, -- should be NOT NULL?
  question_id bigint(20) default NULL, -- should be NOT NULL?
  PRIMARY KEY (semantic_mapping_i_q_a_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE semantic_mapping_q (
  semantic_mapping_q_id bigint(20) NOT NULL,
  code_display_name mediumtext,
  code_system_id int(11) default NULL, -- should be NOT NULL?
  code_value mediumtext,
  question_id bigint(20) default NULL, -- should be NOT NULL?
  PRIMARY KEY (semantic_mapping_q_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE semantic_mapping_q_a (
  semantic_mapping_q_a_id bigint(20) NOT NULL,
  answer_id bigint(20) default NULL, -- should be NOT NULL?
  code_display_name mediumtext,
  code_system_id int(11) default NULL, -- should be NOT NULL?
  code_value mediumtext,
  question_id bigint(20) default NULL, -- should be NOT NULL?
  PRIMARY KEY (semantic_mapping_q_a_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE study (
  study_id bigint(20) NOT NULL,
  grant_description mediumtext default NULL,
  grant_name varchar(255) default NULL,
  pi_name varchar(255) default NULL,
  study_icon blob,
  study_name varchar(255) NOT NULL,
  support_email varchar(255) default  NULL,
  support_name varchar(255) default NULL,
  support_phone varchar(255) default NULL,
  PRIMARY KEY (study_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE subject_session (
  subject_session_id bigint(20) NOT NULL,
  instrument_version_id bigint(20) NOT NULL,
  pwd varchar(255) NOT NULL,
  person_id bigint(20) NOT NULL,
  instrument_session_id bigint(20) default NULL,	-- null means unstarted
  study_id bigint(20) NOT NULL,
  username varchar(255) NOT NULL,
  PRIMARY KEY (subject_session_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;  

CREATE TABLE subject_session_data (
	subject_session_data_id bigint(20) NOT NULL,
	subject_session_id bigint(20) NOT NULL,
	var_name varchar(255) NOT NULL,
	value mediumtext NOT NULL,
	PRIMARY KEY (subject_session_data_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;  

CREATE TABLE validation (
  validation_id bigint(20) NOT NULL,
  data_type_id int(11) default NULL,
  input_mask varchar(255) default NULL,
  max_val varchar(255) default NULL,
  min_val varchar(255) default NULL,
  other_vals varchar(255) default NULL,
  PRIMARY KEY (validation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE var_name (
  var_name_id bigint(20) NOT NULL,
  var_name varchar(255) NOT NULL,
  PRIMARY KEY (var_name_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- CONFIGURE FOREIGN KEYS --

ALTER TABLE answer_list_content ADD CONSTRAINT answer_list_content_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answer (answer_id);
ALTER TABLE answer_list_content ADD CONSTRAINT answer_list_content_ibfk_2 FOREIGN KEY (answer_list_id) REFERENCES answer_list (answer_list_id);

ALTER TABLE answer_list_denorm ADD CONSTRAINT answer_list_denorm_ibfk_1 FOREIGN KEY (answer_list_id) REFERENCES answer_list (answer_list_id);

ALTER TABLE answer_localized ADD CONSTRAINT answer_localized_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answer (answer_id);

ALTER TABLE data_element ADD CONSTRAINT data_element_ibfk_1 FOREIGN KEY (instrument_content_id) REFERENCES instrument_content (instrument_content_id);
ALTER TABLE data_element ADD CONSTRAINT data_element_ibfk_2 FOREIGN KEY (instrument_session_id) REFERENCES instrument_session (instrument_session_id);
ALTER TABLE data_element ADD CONSTRAINT data_element_ibfk_3 FOREIGN KEY (var_name_id) REFERENCES var_name (var_name_id);

ALTER TABLE display_type ADD CONSTRAINT display_type_ibfk_1 FOREIGN KEY (data_type_id) REFERENCES data_type (data_type_id);

ALTER TABLE help_localized ADD CONSTRAINT help_localized_ibfk_1 FOREIGN KEY (help_id) REFERENCES help (help_id);

ALTER TABLE instrument_content ADD CONSTRAINT instrument_content_ibfk_1 FOREIGN KEY (display_type_id) REFERENCES display_type (display_type_id);
ALTER TABLE instrument_content ADD CONSTRAINT instrument_content_ibfk_2 FOREIGN KEY (help_id) REFERENCES help (help_id);
ALTER TABLE instrument_content ADD CONSTRAINT instrument_content_ibfk_3 FOREIGN KEY (instrument_version_id) REFERENCES instrument_version (instrument_version_id);
ALTER TABLE instrument_content ADD CONSTRAINT instrument_content_ibfk_4 FOREIGN KEY (item_id) REFERENCES item (item_id);
ALTER TABLE instrument_content ADD CONSTRAINT instrument_content_ibfk_5 FOREIGN KEY (readback_id) REFERENCES readback (readback_id);
ALTER TABLE instrument_content ADD CONSTRAINT instrument_content_ibfk_6 FOREIGN KEY (var_name_id) REFERENCES var_name (var_name_id);

ALTER TABLE instrument_hash ADD CONSTRAINT instrument_hash_ibfk_1 FOREIGN KEY (language_list_id) REFERENCES language_list (language_list_id);

ALTER TABLE instrument_header ADD CONSTRAINT instrument_header_ibfk_1 FOREIGN KEY (instrument_version_id) REFERENCES instrument_version (instrument_version_id);
ALTER TABLE instrument_header ADD CONSTRAINT instrument_header_ibfk_2 FOREIGN KEY (reserved_word_id) REFERENCES reserved_word (reserved_word_id);

ALTER TABLE instrument_load_error ADD CONSTRAINT instrument_load_error_ibfk_1 FOREIGN KEY (instrument_version_id) REFERENCES instrument_version (instrument_version_id);

ALTER TABLE instrument_session ADD CONSTRAINT instrument_session_ibfk_1 FOREIGN KEY (action_type_id) REFERENCES action_type (action_type_id);
ALTER TABLE instrument_session ADD CONSTRAINT instrument_session_ibfk_2 FOREIGN KEY (person_id) REFERENCES person (person_id);
ALTER TABLE instrument_session ADD CONSTRAINT instrument_session_ibfk_3 FOREIGN KEY (instrument_version_id) REFERENCES instrument_version (instrument_version_id);

ALTER TABLE instrument_version ADD CONSTRAINT instrument_version_ibfk_1 FOREIGN KEY (instrument_hash_id) REFERENCES instrument_hash (instrument_hash_id);
ALTER TABLE instrument_version ADD CONSTRAINT instrument_version_ibfk_2 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);

ALTER TABLE item ADD CONSTRAINT item_ibfk_1 FOREIGN KEY (answer_list_id) REFERENCES answer_list (answer_list_id);
ALTER TABLE item ADD CONSTRAINT item_ibfk_2 FOREIGN KEY (data_type_id) REFERENCES data_type (data_type_id);
ALTER TABLE item ADD CONSTRAINT item_ibfk_3 FOREIGN KEY (question_id) REFERENCES question (question_id);
ALTER TABLE item ADD CONSTRAINT item_ibfk_4 FOREIGN KEY (validation_id) REFERENCES validation (validation_id);

ALTER TABLE item_usage ADD CONSTRAINT item_usage_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answer (answer_id);
ALTER TABLE item_usage ADD CONSTRAINT item_usage_ibfk_2 FOREIGN KEY (data_element_id) REFERENCES data_element (data_element_id);
ALTER TABLE item_usage ADD CONSTRAINT item_usage_ibfk_3 FOREIGN KEY (null_flavor_id) REFERENCES null_flavor (null_flavor_id);
ALTER TABLE item_usage ADD CONSTRAINT item_usage_ibfk_4 FOREIGN KEY (null_flavor_change_id) REFERENCES null_flavor_change (null_flavor_change_id);

ALTER TABLE page_usage ADD CONSTRAINT page_usage_ibfk_1 FOREIGN KEY (action_type_id) REFERENCES action_type (action_type_id);
ALTER TABLE page_usage ADD CONSTRAINT page_usage_ibfk_2 FOREIGN KEY (instrument_session_id) REFERENCES instrument_session (instrument_session_id);

ALTER TABLE page_usage_event ADD CONSTRAINT page_usage_event_ibfk_1 FOREIGN KEY (page_usage_id) REFERENCES page_usage (page_usage_id);

ALTER TABLE person_role ADD CONSTRAINT person_role_ibfk_1 FOREIGN KEY (person_id) REFERENCES person (person_id);
ALTER TABLE person_role ADD CONSTRAINT person_role_ibfk_2 FOREIGN KEY (role_id) REFERENCES role (role_id);

ALTER TABLE question_localized ADD CONSTRAINT question_localized_ibfk_1 FOREIGN KEY (question_id) REFERENCES question (question_id);

ALTER TABLE readback_localized ADD CONSTRAINT readback_localized_ibfk_1 FOREIGN KEY (readback_id) REFERENCES readback (readback_id);

ALTER TABLE role_menu ADD CONSTRAINT role_menu_ibfk_1 FOREIGN KEY (role_id) REFERENCES role (role_id);
ALTER TABLE role_menu ADD CONSTRAINT role_menu_ibfk_2 FOREIGN KEY (menu_id) REFERENCES menu (menu_id);

ALTER TABLE semantic_mapping_a ADD CONSTRAINT semantic_mapping_a_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answer (answer_id);
ALTER TABLE semantic_mapping_a ADD CONSTRAINT semantic_mapping_a_ibfk_2 FOREIGN KEY (code_system_id) REFERENCES code_system (code_system_id);

ALTER TABLE semantic_mapping_i_q_a ADD CONSTRAINT semantic_mapping_i_q_a_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answer (answer_id);
ALTER TABLE semantic_mapping_i_q_a ADD CONSTRAINT semantic_mapping_i_q_a_ibfk_2 FOREIGN KEY (code_system_id) REFERENCES code_system (code_system_id);
ALTER TABLE semantic_mapping_i_q_a ADD CONSTRAINT semantic_mapping_i_q_a_ibfk_3 FOREIGN KEY (instrument_version_id) REFERENCES instrument_version (instrument_version_id);
ALTER TABLE semantic_mapping_i_q_a ADD CONSTRAINT semantic_mapping_i_q_a_ibfk_4 FOREIGN KEY (question_id) REFERENCES question (question_id);

ALTER TABLE semantic_mapping_q ADD CONSTRAINT semantic_mapping_q_ibfk_1 FOREIGN KEY (code_system_id) REFERENCES code_system (code_system_id);
ALTER TABLE semantic_mapping_q ADD CONSTRAINT semantic_mapping_q_ibfk_2 FOREIGN KEY (question_id) REFERENCES question (question_id);

ALTER TABLE semantic_mapping_q_a ADD CONSTRAINT semantic_mapping_q_a_ibfk_1 FOREIGN KEY (answer_id) REFERENCES answer (answer_id);
ALTER TABLE semantic_mapping_q_a ADD CONSTRAINT semantic_mapping_q_a_ibfk_2 FOREIGN KEY (code_system_id) REFERENCES code_system (code_system_id);
ALTER TABLE semantic_mapping_q_a ADD CONSTRAINT semantic_mapping_q_a_ibfk_3 FOREIGN KEY (question_id) REFERENCES question (question_id);

ALTER TABLE subject_session ADD CONSTRAINT subject_session_ibfk_1 FOREIGN KEY (instrument_version_id) REFERENCES instrument_version (instrument_version_id);
ALTER TABLE subject_session ADD CONSTRAINT subject_session_ibfk_2 FOREIGN KEY (person_id) REFERENCES person (person_id);
ALTER TABLE subject_session ADD CONSTRAINT subject_session_ibfk_3 FOREIGN KEY (instrument_session_id) REFERENCES instrument_session (instrument_session_id);
ALTER TABLE subject_session ADD CONSTRAINT subject_session_ibfk_4 FOREIGN KEY (study_id) REFERENCES study (study_id);

ALTER TABLE subject_session_data ADD CONSTRAINT subject_session_data_ibfk_1 FOREIGN KEY (subject_session_id) REFERENCES subject_session (subject_session_id);

ALTER TABLE validation ADD CONSTRAINT validation_ibfk_1 FOREIGN KEY (data_type_id) REFERENCES data_type (data_type_id);


-- INSERT STARTING CONTENT --

INSERT INTO action_type (action_type_id, action_name) VALUES (1, 'evaluate_expr');
INSERT INTO action_type (action_type_id, action_name) VALUES (2, 'finished');
INSERT INTO action_type (action_type_id, action_name) VALUES (3, 'jump_to');
INSERT INTO action_type (action_type_id, action_name) VALUES (4, 'jumpToFirstUnasked');
INSERT INTO action_type (action_type_id, action_name) VALUES (5, 'next');
INSERT INTO action_type (action_type_id, action_name) VALUES (6, 'previous');
INSERT INTO action_type (action_type_id, action_name) VALUES (7, 'refresh current');
INSERT INTO action_type (action_type_id, action_name) VALUES (8, 'reload_questions');
INSERT INTO action_type (action_type_id, action_name) VALUES (9, 'restart_clean');
INSERT INTO action_type (action_type_id, action_name) VALUES (10, 'RESTORE');
INSERT INTO action_type (action_type_id, action_name) VALUES (11, 'RESTORE_FROM_FLOPPY');
INSERT INTO action_type (action_type_id, action_name) VALUES (12, 'save_to');
INSERT INTO action_type (action_type_id, action_name) VALUES (13, 'select_new_interview');
INSERT INTO action_type (action_type_id, action_name) VALUES (14, 'show_Syntax_Errors');
INSERT INTO action_type (action_type_id, action_name) VALUES (15, 'sign_schedule');
INSERT INTO action_type (action_type_id, action_name) VALUES (16, 'START');
INSERT INTO action_type (action_type_id, action_name) VALUES (17, 'suspendToFloppy');
INSERT INTO action_type (action_type_id, action_name) VALUES (18, 'toggle_EventCollection');
INSERT INTO action_type (action_type_id, action_name) VALUES (19, 'turn_debugMode');
INSERT INTO action_type (action_type_id, action_name) VALUES (20, 'turn_developerMode');
INSERT INTO action_type (action_type_id, action_name) VALUES (21, 'turn_showQuestionNum');

INSERT INTO code_system (code_system_id, code_system_name, code_system_oid) VALUES (1, 'LOINC', '2.16.840.1.113883.6.1');
INSERT INTO code_system (code_system_id, code_system_name, code_system_oid) VALUES (2, 'SNOMED-CT', '2.16.840.1.113883.6.96');

INSERT INTO data_type (data_type_id, data_type) VALUES (6, 'number');
INSERT INTO data_type (data_type_id, data_type) VALUES (7, 'string');
INSERT INTO data_type (data_type_id, data_type) VALUES (8, 'date');
INSERT INTO data_type (data_type_id, data_type) VALUES (9, 'time');
INSERT INTO data_type (data_type_id, data_type) VALUES (10, 'year');
INSERT INTO data_type (data_type_id, data_type) VALUES (11, 'month');
INSERT INTO data_type (data_type_id, data_type) VALUES (12, 'day');
INSERT INTO data_type (data_type_id, data_type) VALUES (13, 'weekday');
INSERT INTO data_type (data_type_id, data_type) VALUES (14, 'hour');
INSERT INTO data_type (data_type_id, data_type) VALUES (15, 'minute');
INSERT INTO data_type (data_type_id, data_type) VALUES (16, 'second');
INSERT INTO data_type (data_type_id, data_type) VALUES (17, 'month_num');
INSERT INTO data_type (data_type_id, data_type) VALUES (18, 'day_num');

INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (1, 'best32.', 'best12.', 'nothing', 'NOMINAL', 'F8.0', 'NOM', 0, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (2, 'best32.', 'best12.', 'radio', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (3, 'best32.', 'best12.', 'check', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (4, 'best32.', 'best12.', 'combo', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (5, 'best32.', 'best12.', 'list', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (6, '$100.', '$100.', 'text', 'NOMINAL', 'A100', 'NAR', 0, 7);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (7, 'best32.', 'best12.', 'double', 'SCALE', 'F8.3', 'QN', 0, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (8, 'best32.', 'best12.', 'radio2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (9, '$50.', '$50.', 'password', 'NOMINAL', 'A50', 'NAR', 0, 7);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (10, '$254.', '$254.', 'memo', 'NOMINAL', 'A254', 'NAR', 0, 7);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (11, 'mmddyy10.', 'mmddyy10.', 'date', 'SCALE', 'ADATE10', 'QN', 0, 8);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (12, 'time8.0', 'time8.0', 'time', 'SCALE', 'TIME5.3', 'QN', 0, 9);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (13, 'best32.', 'best12.', 'year', 'SCALE', 'date|yyyy', 'QN', 0, 10);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (14, '$10.', '$10.', 'month', 'SCALE', 'MONTH', 'QN', 0, 11);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (15, '$6.', '$6.', 'day', 'SCALE', 'date|dd', 'QN', 0, 12);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (16, '$10.', '$10.', 'weekday', 'SCALE', 'WKDAY', 'QN', 0, 13);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (17, 'best32.', 'hour2.', 'hour', 'SCALE', 'date|hh', 'QN', 0, 14);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (18, 'best32.', 'best12.', 'minute', 'SCALE', 'date|mm', 'QN', 0, 15);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (19, 'best32.', 'best12.', 'second', 'SCALE', 'date|ss', 'QN', 0, 16);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (20, 'best32.', 'best12.', 'month_num', 'SCALE', 'date|mm', 'QN', 0, 17);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (21, 'best32.', 'day2.', 'day_num', 'SCALE', 'date|dd', 'QN', 0, 18);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (22, 'best32.', 'best12.', 'radio3', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (23, 'best32.', 'best12.', 'combo2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);
INSERT INTO display_type (display_type_id, sas_informat, sas_format, display_type, spss_level, spss_format, loinc_scale, has_answer_list, data_type_id) VALUES (24, 'best32.', 'best12.', 'list2', 'NOMINAL', 'F8.0', 'NOM', 1, 6);

INSERT INTO null_flavor (null_flavor_id, null_flavor, display_name) VALUES (0, '*OK*', '*OK*');
INSERT INTO null_flavor (null_flavor_id, null_flavor, display_name) VALUES (1, '*UNASKED*', '*UNASKED*');
INSERT INTO null_flavor (null_flavor_id, null_flavor, display_name) VALUES (2, '*NA*', '*NA*');
INSERT INTO null_flavor (null_flavor_id, null_flavor, display_name) VALUES (3, '*REFUSED*', '*REFUSED*');
INSERT INTO null_flavor (null_flavor_id, null_flavor, display_name) VALUES (4, '*INVALID*', '*INVALID*');
INSERT INTO null_flavor (null_flavor_id, null_flavor, display_name) VALUES (5, '*UNKNOWN*', '*UNKNOWN*');
INSERT INTO null_flavor (null_flavor_id, null_flavor, display_name) VALUES (6, '*HUH*', '*HUH*');

INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (1, '0.0', 'Ok2Ok');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (2, '0.1', 'Ok2Unasked');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (3, '0.2', 'Ok2Na');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (4, '0.3', 'Ok2Refused');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (5, '0.4', 'Ok2Invalid');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (6, '0.5', 'Ok2Unknown');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (7, '0.6', 'Ok2Huh');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (8, '1.0', 'Unasked2Ok');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (9, '1.1', 'Unasked2Unasked');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (10, '1.2', 'Unasked2Na');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (11, '1.3', 'Unasked2Refused');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (12, '1.4', 'Unasked2Invalid');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (13, '1.5', 'Unasked2Unknown');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (14, '1.6', 'Unasked2Huh');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (15, '2.0', 'Na2Ok');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (16, '2.1', 'Na2Unasked');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (17, '2.2', 'Na2Na');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (18, '2.3', 'Na2Refused');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (19, '2.4', 'Na2Invalid');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (20, '2.5', 'Na2Unknown');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (21, '2.6', 'Na2Huh');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (22, '3.0', 'Refused2Ok');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (23, '3.1', 'Refused2Unasked');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (24, '3.2', 'Refused2Na');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (25, '3.3', 'Refused2Refused');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (26, '3.4', 'Refused2Invalid');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (27, '3.5', 'Refused2Unknown');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (28, '3.6', 'Refused2Huh');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (29, '4.0', 'Invalid2Ok');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (30, '4.1', 'Invalid2Unasked');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (31, '4.2', 'Invalid2Na');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (32, '4.3', 'Invalid2Refused');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (33, '4.4', 'Invalid2Invalid');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (34, '4.5', 'Invalid2Unknown');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (35, '4.6', 'Invalid2Huh');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (36, '5.0', 'Unknown2Ok');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (37, '5.1', 'Unknown2Unasked');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (38, '5.2', 'Unknown2Na');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (39, '5.3', 'Unknown2Refused');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (40, '5.4', 'Unknown2Invalid');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (41, '5.5', 'Unknown2Unknown');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (42, '5.6', 'Unknown2Huh');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (43, '6.0', 'Huh2Ok');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (44, '6.1', 'Huh2Unasked');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (45, '6.2', 'Huh2Na');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (46, '6.3', 'Huh2Refused');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (47, '6.4', 'Huh2Invalid');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (48, '6.5', 'Huh2Unknown');
INSERT INTO null_flavor_change (null_flavor_change_id, null_flavor_change_code, null_flavor_change_string) VALUES (49, '6.6', 'Huh2Huh');


INSERT INTO var_name (var_name_id, var_name) VALUES (0, '__LANGUAGES__');
INSERT INTO var_name (var_name_id, var_name) VALUES (1, '__TITLE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (2, '__ICON__');
INSERT INTO var_name (var_name_id, var_name) VALUES (3, '__HEADER_MSG__');
INSERT INTO var_name (var_name_id, var_name) VALUES (4, '__STARTING_STEP__');
INSERT INTO var_name (var_name_id, var_name) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (6, '__SHOW_QUESTION_REF__');
INSERT INTO var_name (var_name_id, var_name) VALUES (7, '__AUTOGEN_OPTION_NUM__');
INSERT INTO var_name (var_name_id, var_name) VALUES (8, '__DEVELOPER_MODE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (9, '__DEBUG_MODE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (10, '__START_TIME__');
INSERT INTO var_name (var_name_id, var_name) VALUES (11, '__FILENAME__');
INSERT INTO var_name (var_name_id, var_name) VALUES (12, '__SHOW_ADMIN_ICONS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (14, '__ALLOW_COMMENTS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (15, '__SCHEDULE_SOURCE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (16, '__LOADED_FROM__');
INSERT INTO var_name (var_name_id, var_name) VALUES (17, '__CURRENT_LANGUAGE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__');
INSERT INTO var_name (var_name_id, var_name) VALUES (19, '__ALLOW_REFUSED__');
INSERT INTO var_name (var_name_id, var_name) VALUES (20, '__ALLOW_UNKNOWN__');
INSERT INTO var_name (var_name_id, var_name) VALUES (21, '__ALLOW_DONT_UNDERSTAND__');
INSERT INTO var_name (var_name_id, var_name) VALUES (22, '__RECORD_EVENTS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (23, '__WORKING_DIR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (24, '__COMPLETED_DIR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (25, '__FLOPPY_DIR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (26, '__IMAGE_FILES_DIR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (27, '__COMMENT_ICON_ON__');
INSERT INTO var_name (var_name_id, var_name) VALUES (28, '__COMMENT_ICON_OFF__');
INSERT INTO var_name (var_name_id, var_name) VALUES (29, '__REFUSED_ICON_ON__');
INSERT INTO var_name (var_name_id, var_name) VALUES (30, '__REFUSED_ICON_OFF__');
INSERT INTO var_name (var_name_id, var_name) VALUES (31, '__UNKNOWN_ICON_ON__');
INSERT INTO var_name (var_name_id, var_name) VALUES (32, '__UNKNOWN_ICON_OFF__');
INSERT INTO var_name (var_name_id, var_name) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__');
INSERT INTO var_name (var_name_id, var_name) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__');
INSERT INTO var_name (var_name_id, var_name) VALUES (35, '__TRICEPS_VERSION_MAJOR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (36, '__TRICEPS_VERSION_MINOR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (37, '__SCHED_AUTHORS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (38, '__SCHED_VERSION_MAJOR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (39, '__SCHED_VERSION_MINOR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (40, '__SCHED_HELP_URL__');
INSERT INTO var_name (var_name_id, var_name) VALUES (41, '__HELP_ICON__');
INSERT INTO var_name (var_name_id, var_name) VALUES (42, '__ACTIVE_BUTTON_PREFIX__');
INSERT INTO var_name (var_name_id, var_name) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__');
INSERT INTO var_name (var_name_id, var_name) VALUES (44, '__TRICEPS_FILE_TYPE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (45, '__DISPLAY_COUNT__');
INSERT INTO var_name (var_name_id, var_name) VALUES (46, '__SCHEDULE_DIR__');
INSERT INTO var_name (var_name_id, var_name) VALUES (47, '__ALLOW_JUMP_TO__');
INSERT INTO var_name (var_name_id, var_name) VALUES (48, '__BROWSER_TYPE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (49, '__IP_ADDRESS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (50, '__SUSPEND_TO_FLOPPY__');
INSERT INTO var_name (var_name_id, var_name) VALUES (51, '__JUMP_TO_FIRST_UNASKED__');
INSERT INTO var_name (var_name_id, var_name) VALUES (52, '__REDIRECT_ON_FINISH_URL__');
INSERT INTO var_name (var_name_id, var_name) VALUES (53, '__REDIRECT_ON_FINISH_MSG__');
INSERT INTO var_name (var_name_id, var_name) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__');
INSERT INTO var_name (var_name_id, var_name) VALUES (56, '__SET_DEFAULT_FOCUS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (59, '__WRAP_ADMIN_ICONS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (60, '__DISALLOW_COMMENTS__');
INSERT INTO var_name (var_name_id, var_name) VALUES (61, '__CONNECTION_TYPE__');
INSERT INTO var_name (var_name_id, var_name) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__');
INSERT INTO var_name (var_name_id, var_name) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__');

INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (0, '__LANGUAGES__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (1, '__TITLE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (2, '__ICON__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (3, '__HEADER_MSG__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (4, '__STARTING_STEP__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (5, '__PASSWORD_FOR_ADMIN_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (6, '__SHOW_QUESTION_REF__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (7, '__AUTOGEN_OPTION_NUM__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (8, '__DEVELOPER_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (9, '__DEBUG_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (10, '__START_TIME__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (11, '__FILENAME__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (12, '__SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (13, '__TITLE_FOR_PICKLIST_WHEN_IN_PROGRESS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (14, '__ALLOW_COMMENTS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (15, '__SCHEDULE_SOURCE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (16, '__LOADED_FROM__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (17, '__CURRENT_LANGUAGE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (18, '__ALLOW_LANGUAGE_SWITCHING__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (19, '__ALLOW_REFUSED__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (20, '__ALLOW_UNKNOWN__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (21, '__ALLOW_DONT_UNDERSTAND__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (22, '__RECORD_EVENTS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (23, '__WORKING_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (24, '__COMPLETED_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (25, '__FLOPPY_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (26, '__IMAGE_FILES_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (27, '__COMMENT_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (28, '__COMMENT_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (29, '__REFUSED_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (30, '__REFUSED_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (31, '__UNKNOWN_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (32, '__UNKNOWN_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (33, '__DONT_UNDERSTAND_ICON_ON__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (34, '__DONT_UNDERSTAND_ICON_OFF__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (35, '__TRICEPS_VERSION_MAJOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (36, '__TRICEPS_VERSION_MINOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (37, '__SCHED_AUTHORS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (38, '__SCHED_VERSION_MAJOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (39, '__SCHED_VERSION_MINOR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (40, '__SCHED_HELP_URL__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (41, '__HELP_ICON__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (42, '__ACTIVE_BUTTON_PREFIX__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (43, '__ACTIVE_BUTTON_SUFFIX__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (44, '__TRICEPS_FILE_TYPE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (45, '__DISPLAY_COUNT__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (46, '__SCHEDULE_DIR__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (47, '__ALLOW_JUMP_TO__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (48, '__BROWSER_TYPE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (49, '__IP_ADDRESS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (50, '__SUSPEND_TO_FLOPPY__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (51, '__JUMP_TO_FIRST_UNASKED__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (52, '__REDIRECT_ON_FINISH_URL__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (53, '__REDIRECT_ON_FINISH_MSG__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (54, '__SWAP_NEXT_AND_PREVIOUS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (55, '__ANSWER_OPTION_FIELD_WIDTH__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (56, '__SET_DEFAULT_FOCUS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (57, '__ALWAYS_SHOW_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (58, '__SHOW_SAVE_TO_FLOPPY_IN_ADMIN_MODE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (59, '__WRAP_ADMIN_ICONS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (60, '__DISALLOW_COMMENTS__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (61, '__CONNECTION_TYPE__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (62, '__REDIRECT_ON_FINISH_DELAY__', NULL);
INSERT INTO reserved_word (reserved_word_id, reserved_word, meaning) VALUES (63, '__MAX_TEXT_LEN_FOR_COMBO__', NULL);

INSERT INTO person (person_id, first_name, last_name, user_name, email, phone, pwd) VALUES (0, '', '', 'Anonymous', '', '', '');
INSERT INTO person (person_id, first_name, last_name, user_name, email, phone, pwd) VALUES (1, 'George', 'de la Torre', 'George', 'delatorreg@att.net', '', 'admin');
INSERT INTO person (person_id, first_name, last_name, user_name, email, phone, pwd) VALUES (2, 'Tom', 'White', 'Tom', 'tw176@columbia.edu', '', 'admin');

INSERT INTO role (role_id, codetype, role) VALUES (1, '1', 'Main');
INSERT INTO role (role_id, codetype, role) VALUES (2, '2', 'Review');
INSERT INTO role (role_id, codetype, role) VALUES (3, '3', 'Author');
INSERT INTO role (role_id, codetype, role) VALUES (4, '4', 'Administer');

INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (1, 0, 1);
INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (2, 1, 1);
INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (3, 1, 2);
INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (4, 1, 3);
INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (5, 2, 1);
INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (6, 2, 2);
INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (7, 2, 3);
INSERT INTO person_role (person_role_id, person_id, role_id) VALUES (8, 2, 4);

INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (1, '0', '', 'Main');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (2, '1', 'Instruments', 'Instruments');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (3, '1', 'Collaborations', 'Collaborations');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (4, '1', 'Publications', 'Publications');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (5, '1', 'Contact', 'Contact');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (6, '0', '', 'Review');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (7, '1', 'ListInstruments', 'List Instruments');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (8, '1', 'DataExporter', 'Export');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (9, '1', 'UserManual', 'User Manual');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (10, '0', '', 'Author');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (11, '1', 'LoadInstruments', 'Load Instruments');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (12, '1', 'PasteInstrument', 'Paste Instrument');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (13, '0', '', 'Administer');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (14, '1', 'ParserTest', 'Test Parser');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (15, '1', 'Parser2Test', 'Test New Parser');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (16, '1', 'Status', 'Status');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (17, '2', 'InstrumentAllResults', '');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (18, '2', 'InstrumentLogicFile', '');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (19, '2', 'InstrumentSessionRecap', '');
INSERT INTO menu (menu_id, menu_code, menu_name, display_text) VALUES (20, '2', 'ListInstrumentSessions', '');


INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (1, 1, 1);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (2, 1, 2);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (3, 1, 3);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (4, 1, 4);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (5, 1, 5);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (6, 2, 6);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (7, 2, 7);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (8, 2, 8);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (9, 2, 9);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (10, 3, 10);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (11, 3, 11);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (12, 3, 12);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (13, 4, 13);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (14, 4, 14);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (15, 4, 15);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (16, 4, 16);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (17, 2, 17);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (18, 2, 18);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (19, 2, 19);
INSERT INTO role_menu (role_menu_id, role_id, menu_id) VALUES (20, 2, 20);



truncate table role_menu;
truncate table menu;
truncate table person_role;
truncate table role;

--
-- SEQUENCE GENERATOR TABLES 
--
CREATE TABLE sequence_admin (
  seq_name varchar(50) NOT NULL,
  seq_count decimal(38,0) default NULL,
  PRIMARY KEY  (seq_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE sequence_data (
  seq_name varchar(50) NOT NULL,
  seq_count decimal(38,0) default NULL,
  PRIMARY KEY  (seq_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE sequence_model (
  seq_name varchar(50) NOT NULL,
  seq_count decimal(38,0) default NULL,
  PRIMARY KEY  (seq_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE sequence_map (
  seq_name varchar(50) NOT NULL,
  seq_count decimal(38,0) default NULL,
  PRIMARY KEY  (seq_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('menu',12);
INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('person',4);
INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('person_role',9);
INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('role',5);
INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('role_menu',13);
INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('study',0);
INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('subject_session',0);
INSERT INTO sequence_admin (seq_name, seq_count) VALUES ('subject_session_data',0);

INSERT INTO sequence_data (seq_name, seq_count) VALUES ('data_element',0);
INSERT INTO sequence_data (seq_name, seq_count) VALUES ('instrument_session',0);
INSERT INTO sequence_data (seq_name, seq_count) VALUES ('item',0);
INSERT INTO sequence_data (seq_name, seq_count) VALUES ('item_usage',0);
INSERT INTO sequence_data (seq_name, seq_count) VALUES ('page_usage',0);
INSERT INTO sequence_data (seq_name, seq_count) VALUES ('page_usage_event',0);
INSERT INTO sequence_data (seq_name, seq_count) VALUES ('parser_test',0);

INSERT INTO sequence_map (seq_name, seq_count) VALUES ('semantic_mapping_a',0);
INSERT INTO sequence_map (seq_name, seq_count) VALUES ('semantic_mapping_i_q_a',0);
INSERT INTO sequence_map (seq_name, seq_count) VALUES ('semantic_mapping_q',0);
INSERT INTO sequence_map (seq_name, seq_count) VALUES ('semantic_mapping_q_a',0);

INSERT INTO sequence_model (seq_name, seq_count) VALUES ('action_type',22);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('answer',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('answer_list',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('answer_list_content',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('answer_list_denorm',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('answer_localized',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('code_system',3);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('data_type',19);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('display_type',25);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('help',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('help_localized',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('instrument',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('instrument_content',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('instrument_hash',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('instrument_header',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('instrument_load_error',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('instrument_version',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('language_list',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('null_flavor',7);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('null_flavor_change',50);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('question',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('question_localized',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('readback',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('readback_localized',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('reserved_word',64);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('validation',0);
INSERT INTO sequence_model (seq_name, seq_count) VALUES ('var_name',64);

UPDATE sequence_data SET seq_count = '0';
UPDATE sequence_map SET seq_count = '0';
UPDATE sequence_model SET seq_count = '0';
UPDATE sequence_model SET seq_count = '100' WHERE seq_name = 'var_name';