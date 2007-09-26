

DROP DATABASE IF EXISTS codeset;
CREATE DATABASE IF NOT EXISTS codeset DEFAULT CHARSET=utf8;
USE codeset;


CREATE TABLE code_value_general(
    CODE_SET_NM                     varchar(20)     NOT NULL,
    SEQ_NUM                         INT(5)	    NOT NULL,
    CODE                            varchar(20)     NOT NULL,
    ASSIGNING_AUTHORITY_CD          varchar(20),
    ASSIGNING_AUTHORITY_DESC_TXT    varchar(100),
    CODE_DESC_TXT                   varchar(300),
    CODE_SHORT_DESC_TXT             varchar(50),
    CODE_SYSTEM_CD                  varchar(20),
    CODE_SYSTEM_DESC_TXT            varchar(100),
    EFFECTIVE_FROM_TIME             DATE,
    EFFECTIVE_TO_TIME               DATE,
    INDENT_LEVEL_NBR                INT(5),
    IS_MODIFIABLE_IND               CHAR(1)          DEFAULT 'Y',
    DIALOGIX_UID                    INT(10)    NOT NULL,
    PARENT_IS_CD                    varchar(20),
    SOURCE_CONCEPT_ID               varchar(20),
    SUPER_CODE_SET_NM               varchar(10),
    SUPER_CODE                      varchar(20),
    STATUS_CD                       CHAR(1),
    STATUS_TIME                     DATE,
    PRIMARY KEY (CODE_SET_NM, SEQ_NUM, CODE)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- 
-- TABLE: CODESET 
--


CREATE TABLE codeset(
    CODE_SET_NM                     varchar(20)      NOT NULL,
    SEQ_NUM                         INT(5)      NOT NULL,
    ASSIGNING_AUTHORITY_CD          varchar(20),
    ASSIGNING_AUTHORITY_DESC_TXT    varchar(100),
    CODE_SET_DESC_TXT               varchar(2000),
    CODE_SYSTEM_CD                  varchar(20),
    CODE_SYSTEM_DESC_TXT            varchar(100),
    CLASS_CD                        varchar(20),
    EFFECTIVE_FROM_TIME             DATE,
    EFFECTIVE_TO_TIME               DATE,
    IS_MODIFIABLE_IND               CHAR(1),
    DIALOGIX_UID                    INT(10),
    SOURCE_VERSION_TXT              varchar(20),
    SOURCE_DOMAIN_NM                varchar(50),
    STATUS_CD                       CHAR(1),
    STATUS_TO_TIME                  DATE,
    PRIMARY KEY (CODE_SET_NM, SEQ_NUM)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 
-- TABLE: CONDITION_CODE 
--


CREATE TABLE condition_code(
    CONDITION_CD                    varchar(20)     NOT NULL,
    CONDITION_CODESET_NM            varchar(15),
    CONDITION_SEQ_NUM               INT(5),
    ASSIGNING_AUTHORITY_CD          varchar(20),
    ASSIGNING_AUTHORITY_DESC_TXT    varchar(100),
    CODE_SYSTEM_CD                  varchar(20),
    CODE_SYSTEM_DESC_TXT            varchar(100),
    CONDITION_DESC_TXT              varchar(300),
    CONDITION_SHORT_NM              varchar(50),
    EFFECTIVE_FROM_TIME             DATE,
    EFFECTIVE_TO_TIME               DATE,
    INDENT_LEVEL_NBR                INT(5),
    INVESTIGATION_FORM_CD           varchar(50),
    IS_MODIFIABLE_IND               CHAR(1),
    DIALOGIX_UID                    INT(10),
    NND_IND                         CHAR(1),
    PARENT_IS_CD                    varchar(20),
    PROG_AREA_CD                    varchar(20),
    REPORTABLE_MORBIDITY_IND        CHAR(1),
    REPORTABLE_SUMMARY_IND          CHAR(1),
    STATUS_CD                       CHAR(1),
    STATUS_TIME                     DATE,
    PRIMARY KEY (CONDITION_CD)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 
-- TABLE: LANGUAGE_CODE 
--

CREATE TABLE language_code(
    CODE                            varchar(20)      NOT NULL,
    ASSIGNING_AUTHORITY_CD          varchar(20),
    ASSIGNING_AUTHORITY_DESC_TXT    varchar(80),
    CODE_DESC_TXT                   varchar(255),
    CODE_SHORT_DESC_TXT             varchar(50),
    EFFECTIVE_FROM_TIME             DATE,
    EFFECTIVE_TO_TIME               DATE,
    KEY_INFO_TXT                    varchar(2000),
    INDENT_LEVEL_NBR                INT(5),
    IS_MODIFIABLE_IND               CHAR(1)           DEFAULT NULL,
    PARENT_IS_CD                    varchar(20),
    STATUS_CD                       CHAR(1)           DEFAULT NULL,
    STATUS_TIME                     DATE              DEFAULT NULL,
    CODE_SET_NM                     varchar(20),
    SEQ_NUM                         INT(5),
    DIALOGIX_UID                    INT(10),
    SOURCE_CONCEPT_ID               varchar(20),
    PRIMARY KEY (CODE)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
-- 
-- TABLE: LOINC_CODE 
--


CREATE TABLE LOINC_CODE(
    LOINC_CD               varchar(20)     NOT NULL,
    COMPONENT_NAME         varchar(200),
    PROPERTY               varchar(10),
    TIME_ASPECT            varchar(10),
    SYSTEM_CD              varchar(10),
    SCALE_TYPE             varchar(20),
    METHOD_TYPE            varchar(20),
    DISPLAY_NM             varchar(300),
    DIALOGIX_UID               INT(10),
    EFFECTIVE_FROM_TIME    DATE,
    EFFECTIVE_TO_TIME      DATE,
    RELATED_CLASS_CD       varchar(20),
    PRIMARY KEY (LOINC_CD)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
-- 
-- TABLE: LOINC_CONDITION 
--

CREATE TABLE LOINC_CONDITION(
    LOINC_CD                  varchar(20)     NOT NULL,
    CONDITION_CD              varchar(20)     NOT NULL,
    DISEASE_NM                varchar(200),
    REPORTED_VALUE            varchar(20),
    REPORTED_NUMERIC_VALUE    varchar(20),
    STATUS_CD                 CHAR(1),
    STATUS_TIME               DATE,
    EFFECTIVE_FROM_TIME       DATE,
    EFFECTIVE_TO_TIME         DATE,
    PRIMARY KEY (LOINC_CD, CONDITION_CD)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
-- 
-- TABLE: LOINC_SNOMED_CONDITION 
--

CREATE TABLE LOINC_SNOMED_CONDITION(
    LOINC_SNOMED_CC_UID    INT(20)    NOT NULL,
    SNOMED_CD              varchar(20),
    LOINC_CD               varchar(20),
    CONDITION_CD           varchar(20),
    STATUS_CD              CHAR(1),
    STATUS_TIME            DATE,
    EFFECTIVE_FROM_TIME    DATE,
    EFFECTIVE_TO_TIME      DATE,
    PRIMARY KEY (LOINC_SNOMED_CC_UID)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- 
-- TABLE: RACE_CODE 
--

CREATE TABLE RACE_CODE(
    CODE                            varchar(20)      NOT NULL,
    ASSIGNING_AUTHORITY_CD          varchar(20),
    ASSIGNING_AUTHORITY_DESC_TXT    varchar(80),
    CODE_DESC_TXT                   varchar(255),
    CODE_SHORT_DESC_TXT             varchar(50),
    EFFECTIVE_FROM_TXT              DATE,
    EFFECTIVE_TO_TXT                DATE,
    EXCLUDED_TXT                    varchar(255),
    KEY_INFO_TXT                    varchar(2000),
    INDENT_LEVEL_NBR                INT(5),
    IS_MODIFIABLE_IND               CHAR(1)           DEFAULT 'Y',
    PARENT_IS_CD                    varchar(20),
    STATUS_CD                       CHAR(1)           DEFAULT 'A',
    STATUS_TIME                     DATE,
    CODE_SET_NM                     varchar(20),
    SEQ_NUM                         INT(5),
    DIALOGIX_UID                    INT(10),
    SOURCE_CONCEPT_ID               varchar(20),
    PRIMARY KEY (CODE)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
-- 
-- TABLE: SNOMED_CODE 
--

CREATE TABLE SNOMED_CODE(
    SNOMED_CD              varchar(20)     NOT NULL,
    SNOMED_DESC_TXT        varchar(100),
    SOURCE_CONCEPT_ID      varchar(20),
    SOURCE_VERSION_ID      varchar(20),
    STATUS_CD              CHAR(1),
    STATUS_TIME            DATE,
    DIALOGIX_UID           INT(10),
    EFFECTIVE_FROM_TIME    DATE,
    EFFECTIVE_TO_TIME      DATE,
    PRIMARY KEY (SNOMED_CD)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
-- 
-- TABLE: SNOMED_CONDITION 
--

CREATE TABLE SNOMED_CONDITION(
    SNOMED_CD              varchar(20)     NOT NULL,
    CONDITION_CD           varchar(20)     NOT NULL,
    DISEASE_NM             varchar(200),
    ORGANISM_SET_NM        varchar(100),
    STATUS_CD              CHAR(1),
    STATUS_TIME            DATE,
    EFFECTIVE_FROM_TIME    DATE,
    EFFECTIVE_TO_TIME      DATE,
    PRIMARY KEY (SNOMED_CD, CONDITION_CD)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- 
-- TABLE: CODE_VALUE_GENERAL 
--

ALTER TABLE CODE_VALUE_GENERAL 
ADD CONSTRAINT FK_CODE_VALUE_GENERAL
    FOREIGN KEY (CODE_SET_NM,SEQ_NUM)
    REFERENCES CODESET(CODE_SET_NM,SEQ_NUM);
 


-- 
-- TABLE: CONDITION_CODE 
--

ALTER TABLE CONDITION_CODE ADD CONSTRAINT FK_CONDITION_CODE 
    FOREIGN KEY (CONDITION_CODESET_NM,CONDITION_SEQ_NUM)
    REFERENCES CODESET(CODE_SET_NM,SEQ_NUM);
 


-- 
-- TABLE: LANGUAGE_CODE 
--

ALTER TABLE LANGUAGE_CODE ADD CONSTRAINT FK_LANGUAGE_CODE_VALUE 
    FOREIGN KEY (CODE_SET_NM,SEQ_NUM)
    REFERENCES CODESET(CODE_SET_NM,SEQ_NUM);
 


-- 
-- TABLE: LOINC_CONDITION 
--

ALTER TABLE LOINC_CONDITION ADD CONSTRAINT FK_PARTICIPATION1 
    FOREIGN KEY (CONDITION_CD)
    REFERENCES CONDITION_CODE(CONDITION_CD);
 

ALTER TABLE LOINC_CONDITION ADD CONSTRAINT FK_PARTICIPATION2
    FOREIGN KEY (LOINC_CD)
    REFERENCES LOINC_CODE(LOINC_CD);
 


-- 
-- TABLE: LOINC_SNOMED_CONDITION 
--

ALTER TABLE LOINC_SNOMED_CONDITION ADD CONSTRAINT FK_EVENT1 
    FOREIGN KEY (LOINC_CD)
    REFERENCES LOINC_CODE(LOINC_CD);
 

ALTER TABLE LOINC_SNOMED_CONDITION ADD CONSTRAINT FK_EVENT2 
    FOREIGN KEY (SNOMED_CD)
    REFERENCES SNOMED_CODE(SNOMED_CD);
 

ALTER TABLE LOINC_SNOMED_CONDITION ADD CONSTRAINT FK_LOINC_SNOMED_CC 
    FOREIGN KEY (CONDITION_CD)
    REFERENCES CONDITION_CODE(CONDITION_CD);
 


-- 
-- TABLE: RACE_CODE 
--

ALTER TABLE RACE_CODE ADD CONSTRAINT FK_RACE_CODE_VALUE
    FOREIGN KEY (CODE_SET_NM,SEQ_NUM)
    REFERENCES CODESET(CODE_SET_NM,SEQ_NUM);
 


-- 
-- TABLE: SNOMED_CONDITION 
--

ALTER TABLE SNOMED_CONDITION ADD CONSTRAINT FK_SNOMED_PART1 
    FOREIGN KEY (CONDITION_CD)
    REFERENCES CONDITION_CODE(CONDITION_CD);
 

ALTER TABLE SNOMED_CONDITION ADD CONSTRAINT FK_SNOMED_PART2 
    FOREIGN KEY (SNOMED_CD)
    REFERENCES SNOMED_CODE(SNOMED_CD);
 


