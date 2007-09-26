/*
Created		9/21/2007
Modified		9/21/2007
Project		
Model		
Company		
Author		
Version		
Database		Oracle 10g 
*/


-- Create Types section


-- Create Tables section


Create table "actiontype" (
	"ActionType_ID" Integer NOT NULL ,
	"ActionName" Varchar2 (50) NOT NULL ,
primary key ("ActionType_ID") 
) 
/

Create table "answerlist" (
	"AnswerList_ID" Integer NOT NULL ,
	"Description" Long,
primary key ("AnswerList_ID") 
) 
/

Create table "answerlistcontent" (
	"AnswerListContent_ID" Integer NOT NULL ,
	"AnswerList_ID" Integer NOT NULL ,
	"Answer_ID" Integer NOT NULL ,
	"Answer_Order" Integer NOT NULL ,
	"Value" Varchar2 (100) NOT NULL ,
primary key ("AnswerListContent_ID") 
) 
/

Create table "answerlocalized" (
	"AnswerLocalized_ID" Integer NOT NULL ,
	"Answer_ID" Integer NOT NULL ,
	"Language_ID" Integer NOT NULL ,
	"AnswerString" Long,
primary key ("AnswerLocalized_ID") 
) 
/

Create table "datatype" (
	"DataType_ID" Integer NOT NULL ,
	"DataType" Varchar2 (100) NOT NULL ,
	"SPSSformat" Varchar2 (25),
	"SASinformat" Varchar2 (25),
	"SASformat" Varchar2 (25),
primary key ("DataType_ID") 
) 
/

Create table "datum" (
	"Datum_ID" Integer NOT NULL ,
	"InstrumentContent_ID" Integer NOT NULL ,
	"InstrumentVersion_ID" Integer NOT NULL ,
	"Item_ID" Integer NOT NULL ,
	"VarName_ID" Integer NOT NULL ,
	"Language_ID" Integer NOT NULL ,
	"PageUsage_ID" Integer NOT NULL ,
	"QuestionAsAsked" Long,
	"Value" Long,
	"NullFlavor_ID" Integer NOT NULL ,
	"Comments" Long,
	"Time_Stamp" Date Default CURRENT_TIMESTAMP NOT NULL ,
primary key ("Datum_ID") 
) 
/

Create table "displaytype" (
	"DisplayType_ID" Integer NOT NULL ,
	"DisplayType" Varchar2 (100) NOT NULL ,
primary key ("DisplayType_ID") 
) 
/

Create table "functionname" (
	"FunctionName_ID" Integer NOT NULL ,
	"Name" Varchar2 (30) NOT NULL ,
	"Syntax" Long NOT NULL ,
	"Description" Long NOT NULL ,
	"Definition" Long NOT NULL ,
primary key ("FunctionName_ID") 
) 
/

Create table "helplocalized" (
	"HelpLocalized_ID" Integer NOT NULL ,
	"Help_ID" Integer NOT NULL ,
	"Language_ID" Integer NOT NULL ,
	"HelpString" Long,
primary key ("HelpLocalized_ID") 
) 
/

Create table "instrument" (
	"Instrument_ID" Integer NOT NULL ,
	"Name" Varchar2 (120) NOT NULL ,
	"description" Blob,
primary key ("Instrument_ID") 
) 
/

Create table "instrumentcontent" (
	"InstrumentContent_ID" Integer NOT NULL ,
	"InstrumentVersion_ID" Integer NOT NULL ,
	"Item_ID" Integer NOT NULL ,
	"VarName_ID" Integer NOT NULL ,
	"Item_Sequence" Integer NOT NULL ,
	"Help_ID" Integer NOT NULL ,
	"DisplayType_ID" Integer NOT NULL ,
	"isRequired" Smallint Default "0" NOT NULL ,
	"isReadOnly" Smallint Default "0" NOT NULL ,
	"DisplayName" Long,
	"VarName_8char" Varchar2 (8),
	"GroupNum" Integer Default "0" NOT NULL ,
	"Relevance" Long NOT NULL ,
	"ActionType" Varchar2 (30) 'q','e','[',']',
	"FormatMask" Long,
	"isMessage" Smallint Default "0" NOT NULL ,
	"DefaultAnswer" Long,
	"SPSSformat" Varchar2 (25),
	"SASinformat" Varchar2 (25),
	"SASformat" Varchar2 (25),
primary key ("InstrumentContent_ID") 
) 
/

Create table "instrumenthash" (
	"InstrumentHash_ID" Integer NOT NULL ,
	"LanguageList_ID" Integer NOT NULL ,
	"NumVars" Smallint Default "0" NOT NULL ,
	"VarListMD5" Varchar2 (35) NOT NULL ,
	"InstrumentMD5" Varchar2 (35) NOT NULL ,
	"NumLanguages" Smallint Default "0" NOT NULL ,
	"NumInstructions" Smallint Default "0" NOT NULL ,
	"NumEquations" Smallint Default "0" NOT NULL ,
	"NumQuestions" Smallint Default "0" NOT NULL ,
	"NumBranches" Smallint Default "0" NOT NULL ,
	"NumTailorings" Smallint Default "0" NOT NULL ,
primary key ("InstrumentHash_ID") 
) 
/

Create table "instrumentheader" (
	"InstrumentHeader_ID" Integer NOT NULL ,
	"InstrumentVersion_ID" Integer NOT NULL ,
	"ReservedWord_ID" Integer NOT NULL ,
	"Value" Long NOT NULL ,
primary key ("InstrumentHeader_ID") 
) 
/

Create table "instrumentsession" (
	"InstrumentSession_ID" Integer NOT NULL ,
	"InstrumentVersion_ID" Integer NOT NULL ,
	"User_ID" Integer NOT NULL ,
	"InstrumentVersionData_ID" Integer NOT NULL ,
	"StartTime" Date Default CURRENT_TIMESTAMP NOT NULL ,
	"EndTime" Date Default "0000-00-00 00:00:00" NOT NULL ,
	"First_Group" Integer NOT NULL ,
	"Current_Group" Integer NOT NULL ,
	"ActionType_ID" Integer NOT NULL ,
	"Last_Access" Varchar2 (20),
	"StatusMsg" Varchar2 (200),
primary key ("InstrumentSession_ID") 
) 
/

Create table "instrumentversion" (
	"InstrumentVersion_ID" Integer NOT NULL ,
	"Instrument_ID" Integer NOT NULL ,
	"InstrumentHash_ID" Integer NOT NULL ,
	"MajorVersion" Integer NOT NULL ,
	"MinorVersion" Integer NOT NULL ,
	"Instrument_notes" Long,
	"Instrument_status" Integer,
	"CreationDate" Date NOT NULL ,
primary key ("InstrumentVersion_ID") 
) 
/

Create table "instver_1" (
	"InstrumentVersionData_ID" Integer NOT NULL ,
	"InstrumentVersion_ID" Integer NOT NULL ,
	"InstrumentSession_ID" Integer NOT NULL ,
primary key ("InstrumentVersionData_ID") 
) 
/

Create table "item" (
	"Item_ID" Integer NOT NULL ,
	"Question_ID" Integer NOT NULL ,
	"DataType_ID" Integer NOT NULL ,
	"AnswerList_ID" Integer NOT NULL ,
	"Validation_ID" Integer NOT NULL ,
	"ItemType" Varchar2 (30) 'Question','Equation' NOT NULL ,
	"Concept" Long,
primary key ("Item_ID") 
) 
/

Create table "itemusage" (
	"ItemUsage_ID" Double precision NOT NULL ,
	"InstrumentSession_ID" Integer NOT NULL ,
	"VarName_ID" Integer NOT NULL ,
	"InstrumentContent_ID" Integer NOT NULL ,
	"GroupNum" Smallint NOT NULL ,
	"DisplayNum" Smallint NOT NULL ,
	"Language_ID" Integer NOT NULL ,
	"WhenAsMS" Double precision NOT NULL ,
	"Time_Stamp" Date Default CURRENT_TIMESTAMP NOT NULL ,
	"AnswerType" Integer NOT NULL ,
	"Value" Long NOT NULL ,
	"NullFlavor_ID" Integer NOT NULL ,
	"QuestionAsAsked" Long NOT NULL ,
	"itemVacillation" Integer,
	"responseLatency" Integer,
	"responseDuration" Integer,
	"Comments" Long NOT NULL ,
primary key ("ItemUsage_ID") 
) 
/

Create table "language" (
	"Language_ID" Integer NOT NULL ,
	"LanguageCode" Varchar2 (10) NOT NULL ,
	"Description" Varchar2 (100),
primary key ("Language_ID") 
) 
/

Create table "languagelist" (
	"LanguageList_ID" Integer NOT NULL ,
	"LanguageList" Long NOT NULL ,
primary key ("LanguageList_ID") 
) 
/

Create table "loinc_itemrequest" (
	"LOINC_ItemRequest_ID" Integer NOT NULL ,
	"Item_ID" Integer NOT NULL ,
	"TempLOINCcode" Integer NOT NULL ,
	"LOINCproperty" Varchar2 (30),
	"LOINCtimeAspect" Varchar2 (15),
	"LOINCsystem" Varchar2 (100),
	"LOINCscale" Varchar2 (30),
	"LOINCmethod" Varchar2 (50),
	"LOINC_NUM" Varchar2 (10),
primary key ("LOINC_ItemRequest_ID") 
) 
/

Create table "nullflavor" (
	"NullFlavor_ID" Integer NOT NULL ,
	"NullFlavor" Varchar2 (100) NOT NULL ,
	"DisplayName" Varchar2 (100) NOT NULL ,
	"Description" Long,
primary key ("NullFlavor_ID") 
) 
/

Create table "pageusage" (
	"PageUsage_ID" Integer NOT NULL ,
	"InstrumentSession_ID" Integer NOT NULL ,
	"Language_ID" Integer NOT NULL ,
	"Time_Stamp" Date Default CURRENT_TIMESTAMP NOT NULL ,
	"AccessCount" Integer NOT NULL ,
	"GroupNum" Integer NOT NULL ,
	"DisplayNum" Integer NOT NULL ,
	"ActionType_ID" Integer NOT NULL ,
	"StatusMsg" Varchar2 (200),
	"pageDuration" Integer,
	"serverDuration" Integer,
	"loadDuration" Integer,
	"networkDuration" Integer,
	"pageVacillation" Integer,
primary key ("PageUsage_ID") 
) 
/

Create table "pageusageevent" (
	"PageUsageEvent_ID" Integer NOT NULL ,
	"PageUsage_ID" Integer NOT NULL ,
	"VarName_ID" Integer NOT NULL ,
	"actionType" Varchar2 (18) NOT NULL ,
	"eventType" Varchar2 (18) NOT NULL ,
	"Time_Stamp" Date Default CURRENT_TIMESTAMP,
	"duration" Integer Default "0" NOT NULL ,
	"value1" Varchar2 (50) NOT NULL ,
	"value2" Varchar2 (250) NOT NULL ,
primary key ("PageUsageEvent_ID") 
) 
/

Create table "questionlocalized" (
	"QuestionLocalized_ID" Integer NOT NULL ,
	"Question_ID" Integer NOT NULL ,
	"Language_ID" Integer NOT NULL ,
	"QuestionString" Long,
primary key ("QuestionLocalized_ID") 
) 
/

Create table "reservedword" (
	"ReservedWord_ID" Integer NOT NULL ,
	"ReservedWord" Varchar2 (100) NOT NULL ,
	"Meaning" Long,
primary key ("ReservedWord_ID") 
) 
/

Create table "semanticmapping" (
	"SemanticMapping_ID" Integer NOT NULL ,
	"InstrumentVersion_ID" Integer,
	"Question_ID" Integer,
	"Answer_ID" Integer,
	"CodeSet_ID" Integer,
	"Value" Long,
primary key ("SemanticMapping_ID") 
) 
/

Create table "user" (
	"User_ID" Integer NOT NULL ,
	"user_name" Varchar2 (20) NOT NULL ,
	"pwd" Varchar2 (20) NOT NULL ,
	"first_name" Varchar2 (30) NOT NULL ,
	"last_name" Varchar2 (30) NOT NULL ,
	"email" Varchar2 (80) NOT NULL ,
	"phone" Varchar2 (24) NOT NULL ,
primary key ("User_ID") 
) 
/

Create table "validation" (
	"Validation_ID" Integer NOT NULL ,
	"MinVal" Varchar2 (75),
	"MaxVal" Varchar2 (75),
	"OtherVals" Varchar2 (100),
	"InputMask" Varchar2 (75),
primary key ("Validation_ID") 
) 
/

Create table "varname" (
	"VarName_ID" Integer NOT NULL ,
	"VarName" Varchar2 (100) NOT NULL ,
primary key ("VarName_ID") 
) 
/


-- Create Alternate keys section

Alter table "answerlistcontent" add Constraint "uni_AnswerListContent" unique ("AnswerList_ID","Answer_ID","Answer_Order","Value") 
/
Alter table "answerlocalized" add Constraint "uni_AnswerLocalized" unique ("Answer_ID","Language_ID") 
/
Alter table "answerlocalized" add Constraint "ak_AnswerListContent_ibfk_2" unique ("Answer_ID") 
/
Alter table "answerlocalized" add Constraint "ak_SemanticMapping_ibfk_3" unique ("Answer_ID") 
/
Alter table "datatype" add Constraint "uni_DataType" unique ("DataType") 
/
Alter table "datum" add Constraint "InstrumentVersion_ID" unique ("InstrumentVersion_ID","VarName_ID") 
/
Alter table "displaytype" add Constraint "uni_DisplayType" unique ("DisplayType") 
/
Alter table "functionname" add Constraint "uni_FunctionName" unique ("Name") 
/
Alter table "helplocalized" add Constraint "uni_HelpLocalized" unique ("Help_ID","Language_ID") 
/
Alter table "instrument" add Constraint "uni_Instrument" unique ("Name") 
/
Alter table "instrumentcontent" add Constraint "uni_InstrumentContent" unique ("InstrumentVersion_ID","Item_ID","VarName_ID","Item_Sequence") 
/
Alter table "instrumenthash" add Constraint "uni_InstrumentHash" unique ("NumVars","VarListMD5","InstrumentMD5","LanguageList_ID","NumLanguages") 
/
Alter table "instrumentheader" add Constraint "uni_InstrumentHeader" unique ("InstrumentVersion_ID","ReservedWord_ID") 
/
Alter table "instrumentsession" add Constraint "uni_InstrumentSession" unique ("InstrumentVersion_ID","User_ID","StartTime") 
/
Alter table "instrumentversion" add Constraint "uni_InstrumentVersion" unique ("Instrument_ID","InstrumentHash_ID") 
/
Alter table "language" add Constraint "uni_Language" unique ("LanguageCode") 
/
Alter table "loinc_itemrequest" add Constraint "uni_LOINC_ItemRequest" unique ("Item_ID","TempLOINCcode","LOINC_NUM") 
/
Alter table "nullflavor" add Constraint "uni_NullFlavor" unique ("NullFlavor") 
/
Alter table "questionlocalized" add Constraint "uni_QuestionLocalized" unique ("Question_ID","Language_ID") 
/
Alter table "questionlocalized" add Constraint "ak_Item_ibfk_1" unique ("Question_ID") 
/
Alter table "questionlocalized" add Constraint "ak_SemanticMapping_ibfk_2" unique ("Question_ID") 
/
Alter table "reservedword" add Constraint "uni_ReservedWord" unique ("ReservedWord") 
/
Alter table "validation" add Constraint "uni_Validation" unique ("MinVal","MaxVal","OtherVals","InputMask") 
/
Alter table "varname" add Constraint "uni_VarName" unique ("VarName") 
/

-- Create Indexes section

Create Index "AnswerListContent_ibfk_2" ON "answerlistcontent" ("Answer_ID") 
/
Create Index "AnswerLocalized_ibfk_1" ON "answerlocalized" ("Language_ID") 
/
Create Index "k1_Datum" ON "datum" ("Language_ID") 
/
Create Index "k2_Datum" ON "datum" ("PageUsage_ID") 
/
Create Index "Datum_ibfk_1" ON "datum" ("InstrumentContent_ID") 
/
Create Index "Datum_ibfk_3" ON "datum" ("Item_ID") 
/
Create Index "Datum_ibfk_4" ON "datum" ("VarName_ID") 
/
Create Index "Datum_ibfk_7" ON "datum" ("NullFlavor_ID") 
/
Create Index "HelpLocalized_ibfk_1" ON "helplocalized" ("Language_ID") 
/
Create Index "k1_InstrumentContent" ON "instrumentcontent" ("DisplayType_ID") 
/
Create Index "InstrumentContent_ibfk_2" ON "instrumentcontent" ("Item_ID") 
/
Create Index "InstrumentContent_ibfk_3" ON "instrumentcontent" ("VarName_ID") 
/
Create Index "InstrumentHash_ibfk_1" ON "instrumenthash" ("LanguageList_ID") 
/
Create Index "InstrumentHeader_ibfk_2" ON "instrumentheader" ("ReservedWord_ID") 
/
Create Index "k1_InstrumentSession" ON "instrumentsession" ("InstrumentVersion_ID","User_ID") 
/
Create Index "InstrumentSession_ibfk_2" ON "instrumentsession" ("ActionType_ID") 
/
Create Index "InstrumentSession_ibfk_3" ON "instrumentsession" ("User_ID") 
/
Create Index "k1_InstrumentVersion" ON "instrumentversion" ("Instrument_ID") 
/
Create Index "InstrumentVersion_ibfk_2" ON "instrumentversion" ("InstrumentHash_ID") 
/
Create Index "InstVer_1_ibfk_1" ON "instver_1" ("InstrumentVersion_ID") 
/
Create Index "InstVer_1_ibfk_2" ON "instver_1" ("InstrumentSession_ID") 
/
Create Index "k1_Item" ON "item" ("Question_ID") 
/
Create Index "k2_Item" ON "item" ("DataType_ID") 
/
Create Index "k3_Item" ON "item" ("AnswerList_ID") 
/
Create Index "k4_Item" ON "item" ("Validation_ID") 
/
Create Index "k1_ItemUsage" ON "itemusage" ("InstrumentSession_ID") 
/
Create Index "k2_ItemUsage" ON "itemusage" ("VarName_ID") 
/
Create Index "ItemUsage_ibfk_3" ON "itemusage" ("InstrumentContent_ID") 
/
Create Index "ItemUsage_ibfk_4" ON "itemusage" ("Language_ID") 
/
Create Index "ItemUsage_ibfk_5" ON "itemusage" ("NullFlavor_ID") 
/
Create Index "k1_PageUsage" ON "pageusage" ("InstrumentSession_ID") 
/
Create Index "PageUsage_ibfk_2" ON "pageusage" ("ActionType_ID") 
/
Create Index "k1_PageUsageEvent" ON "pageusageevent" ("PageUsage_ID","VarName_ID") 
/
Create Index "PageUsageEvent_ibfk_2" ON "pageusageevent" ("VarName_ID") 
/
Create Index "QuestionLocalized_ibfk_1" ON "questionlocalized" ("Language_ID") 
/
Create Index "SemanticMapping_ibfk_1" ON "semanticmapping" ("InstrumentVersion_ID") 
/
Create Index "SemanticMapping_ibfk_2" ON "semanticmapping" ("Question_ID") 
/
Create Index "SemanticMapping_ibfk_3" ON "semanticmapping" ("Answer_ID") 
/


-- Create Foreign keys section

Alter table "instrumentsession" add  foreign key ("ActionType_ID") references "actiontype" ("ActionType_ID") 
/

Alter table "pageusage" add  foreign key ("ActionType_ID") references "actiontype" ("ActionType_ID") 
/

Alter table "answerlistcontent" add  foreign key ("AnswerList_ID") references "answerlist" ("AnswerList_ID") 
/

Alter table "item" add  foreign key ("AnswerList_ID") references "answerlist" ("AnswerList_ID") 
/

Alter table "answerlistcontent" add  foreign key ("Answer_ID") references "answerlocalized" ("Answer_ID") 
/

Alter table "semanticmapping" add  foreign key ("Answer_ID") references "answerlocalized" ("Answer_ID") 
/

Alter table "item" add  foreign key ("DataType_ID") references "datatype" ("DataType_ID") 
/

Alter table "instrumentcontent" add  foreign key ("DisplayType_ID") references "displaytype" ("DisplayType_ID") 
/

Alter table "instrumentversion" add  foreign key ("Instrument_ID") references "instrument" ("Instrument_ID") 
/

Alter table "datum" add  foreign key ("InstrumentContent_ID") references "instrumentcontent" ("InstrumentContent_ID") 
/

Alter table "itemusage" add  foreign key ("InstrumentContent_ID") references "instrumentcontent" ("InstrumentContent_ID") 
/

Alter table "instrumentversion" add  foreign key ("InstrumentHash_ID") references "instrumenthash" ("InstrumentHash_ID") 
/

Alter table "instver_1" add  foreign key ("InstrumentSession_ID") references "instrumentsession" ("InstrumentSession_ID") 
/

Alter table "itemusage" add  foreign key ("InstrumentSession_ID") references "instrumentsession" ("InstrumentSession_ID") 
/

Alter table "pageusage" add  foreign key ("InstrumentSession_ID") references "instrumentsession" ("InstrumentSession_ID") 
/

Alter table "datum" add  foreign key ("InstrumentVersion_ID") references "instrumentversion" ("InstrumentVersion_ID") 
/

Alter table "instrumentcontent" add  foreign key ("InstrumentVersion_ID") references "instrumentversion" ("InstrumentVersion_ID") 
/

Alter table "instrumentheader" add  foreign key ("InstrumentVersion_ID") references "instrumentversion" ("InstrumentVersion_ID") 
/

Alter table "instrumentsession" add  foreign key ("InstrumentVersion_ID") references "instrumentversion" ("InstrumentVersion_ID") 
/

Alter table "instver_1" add  foreign key ("InstrumentVersion_ID") references "instrumentversion" ("InstrumentVersion_ID") 
/

Alter table "semanticmapping" add  foreign key ("InstrumentVersion_ID") references "instrumentversion" ("InstrumentVersion_ID") 
/

Alter table "datum" add  foreign key ("Item_ID") references "item" ("Item_ID") 
/

Alter table "instrumentcontent" add  foreign key ("Item_ID") references "item" ("Item_ID") 
/

Alter table "loinc_itemrequest" add  foreign key ("Item_ID") references "item" ("Item_ID") 
/

Alter table "answerlocalized" add  foreign key ("Language_ID") references "language" ("Language_ID") 
/

Alter table "datum" add  foreign key ("Language_ID") references "language" ("Language_ID") 
/

Alter table "helplocalized" add  foreign key ("Language_ID") references "language" ("Language_ID") 
/

Alter table "itemusage" add  foreign key ("Language_ID") references "language" ("Language_ID") 
/

Alter table "questionlocalized" add  foreign key ("Language_ID") references "language" ("Language_ID") 
/

Alter table "instrumenthash" add  foreign key ("LanguageList_ID") references "languagelist" ("LanguageList_ID") 
/

Alter table "datum" add  foreign key ("NullFlavor_ID") references "nullflavor" ("NullFlavor_ID") 
/

Alter table "itemusage" add  foreign key ("NullFlavor_ID") references "nullflavor" ("NullFlavor_ID") 
/

Alter table "datum" add  foreign key ("PageUsage_ID") references "pageusage" ("PageUsage_ID") 
/

Alter table "pageusageevent" add  foreign key ("PageUsage_ID") references "pageusage" ("PageUsage_ID") 
/

Alter table "item" add  foreign key ("Question_ID") references "questionlocalized" ("Question_ID") 
/

Alter table "semanticmapping" add  foreign key ("Question_ID") references "questionlocalized" ("Question_ID") 
/

Alter table "instrumentheader" add  foreign key ("ReservedWord_ID") references "reservedword" ("ReservedWord_ID") 
/

Alter table "instrumentsession" add  foreign key ("User_ID") references "user" ("User_ID") 
/

Alter table "item" add  foreign key ("Validation_ID") references "validation" ("Validation_ID") 
/

Alter table "datum" add  foreign key ("VarName_ID") references "varname" ("VarName_ID") 
/

Alter table "instrumentcontent" add  foreign key ("VarName_ID") references "varname" ("VarName_ID") 
/

Alter table "itemusage" add  foreign key ("VarName_ID") references "varname" ("VarName_ID") 
/

Alter table "pageusageevent" add  foreign key ("VarName_ID") references "varname" ("VarName_ID") 
/


-- Create Object Tables section


-- Create XMLType Tables section


-- Create Procedures section


-- Create Functions section


-- Create Views section


-- Create Sequences section


-- Create Triggers from referential integrity section

-- Update trigger for "actiontype"
Create Trigger "tu_actiontype"
after update of "ActionType_ID" on "actiontype" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instrumentsession" update when parent "actiontype" changed
if (:old_upd."ActionType_ID" != :new_upd."ActionType_ID")  then
	begin
	update "instrumentsession"
	set 	"ActionType_ID" = :new_upd."ActionType_ID"
	where 	"instrumentsession"."ActionType_ID" = :old_upd."ActionType_ID" ;
	end;
end if;
-- Cascade child "pageusage" update when parent "actiontype" changed
if (:old_upd."ActionType_ID" != :new_upd."ActionType_ID")  then
	begin
	update "pageusage"
	set 	"ActionType_ID" = :new_upd."ActionType_ID"
	where 	"pageusage"."ActionType_ID" = :old_upd."ActionType_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "answerlist"
Create Trigger "tu_answerlist"
after update of "AnswerList_ID" on "answerlist" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "answerlistcontent" update when parent "answerlist" changed
if (:old_upd."AnswerList_ID" != :new_upd."AnswerList_ID")  then
	begin
	update "answerlistcontent"
	set 	"AnswerList_ID" = :new_upd."AnswerList_ID"
	where 	"answerlistcontent"."AnswerList_ID" = :old_upd."AnswerList_ID" ;
	end;
end if;
-- Cascade child "item" update when parent "answerlist" changed
if (:old_upd."AnswerList_ID" != :new_upd."AnswerList_ID")  then
	begin
	update "item"
	set 	"AnswerList_ID" = :new_upd."AnswerList_ID"
	where 	"item"."AnswerList_ID" = :old_upd."AnswerList_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "answerlocalized"
Create Trigger "tu_answerlocalized"
after update of "AnswerLocalized_ID","Language_ID" on "answerlocalized" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "answerlistcontent" update when parent "answerlocalized" changed
if (:old_upd."Answer_ID" != :new_upd."Answer_ID")  then
	begin
	update "answerlistcontent"
	set 	"Answer_ID" = :new_upd."Answer_ID"
	where 	"answerlistcontent"."Answer_ID" = :old_upd."Answer_ID" ;
	end;
end if;
-- Cascade child "semanticmapping" update when parent "answerlocalized" changed
if (:old_upd."Answer_ID" != :new_upd."Answer_ID")  then
	begin
	update "semanticmapping"
	set 	"Answer_ID" = :new_upd."Answer_ID"
	where 	"semanticmapping"."Answer_ID" = :old_upd."Answer_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "datatype"
Create Trigger "tu_datatype"
after update of "DataType_ID" on "datatype" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "item" update when parent "datatype" changed
if (:old_upd."DataType_ID" != :new_upd."DataType_ID")  then
	begin
	update "item"
	set 	"DataType_ID" = :new_upd."DataType_ID"
	where 	"item"."DataType_ID" = :old_upd."DataType_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "displaytype"
Create Trigger "tu_displaytype"
after update of "DisplayType_ID" on "displaytype" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instrumentcontent" update when parent "displaytype" changed
if (:old_upd."DisplayType_ID" != :new_upd."DisplayType_ID")  then
	begin
	update "instrumentcontent"
	set 	"DisplayType_ID" = :new_upd."DisplayType_ID"
	where 	"instrumentcontent"."DisplayType_ID" = :old_upd."DisplayType_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "instrument"
Create Trigger "tu_instrument"
after update of "Instrument_ID" on "instrument" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instrumentversion" update when parent "instrument" changed
if (:old_upd."Instrument_ID" != :new_upd."Instrument_ID")  then
	begin
	update "instrumentversion"
	set 	"Instrument_ID" = :new_upd."Instrument_ID"
	where 	"instrumentversion"."Instrument_ID" = :old_upd."Instrument_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "instrumentcontent"
Create Trigger "tu_instrumentcontent"
after update of "InstrumentContent_ID","InstrumentVersion_ID","Item_ID","VarName_ID","DisplayType_ID" on "instrumentcontent" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "datum" update when parent "instrumentcontent" changed
if (:old_upd."InstrumentContent_ID" != :new_upd."InstrumentContent_ID")  then
	begin
	update "datum"
	set 	"InstrumentContent_ID" = :new_upd."InstrumentContent_ID"
	where 	"datum"."InstrumentContent_ID" = :old_upd."InstrumentContent_ID" ;
	end;
end if;
-- Cascade child "itemusage" update when parent "instrumentcontent" changed
if (:old_upd."InstrumentContent_ID" != :new_upd."InstrumentContent_ID")  then
	begin
	update "itemusage"
	set 	"InstrumentContent_ID" = :new_upd."InstrumentContent_ID"
	where 	"itemusage"."InstrumentContent_ID" = :old_upd."InstrumentContent_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "instrumenthash"
Create Trigger "tu_instrumenthash"
after update of "InstrumentHash_ID","LanguageList_ID" on "instrumenthash" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instrumentversion" update when parent "instrumenthash" changed
if (:old_upd."InstrumentHash_ID" != :new_upd."InstrumentHash_ID")  then
	begin
	update "instrumentversion"
	set 	"InstrumentHash_ID" = :new_upd."InstrumentHash_ID"
	where 	"instrumentversion"."InstrumentHash_ID" = :old_upd."InstrumentHash_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "instrumentsession"
Create Trigger "tu_instrumentsession"
after update of "InstrumentSession_ID","InstrumentVersion_ID","User_ID","ActionType_ID" on "instrumentsession" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instver_1" update when parent "instrumentsession" changed
if (:old_upd."InstrumentSession_ID" != :new_upd."InstrumentSession_ID")  then
	begin
	update "instver_1"
	set 	"InstrumentSession_ID" = :new_upd."InstrumentSession_ID"
	where 	"instver_1"."InstrumentSession_ID" = :old_upd."InstrumentSession_ID" ;
	end;
end if;
-- Cascade child "itemusage" update when parent "instrumentsession" changed
if (:old_upd."InstrumentSession_ID" != :new_upd."InstrumentSession_ID")  then
	begin
	update "itemusage"
	set 	"InstrumentSession_ID" = :new_upd."InstrumentSession_ID"
	where 	"itemusage"."InstrumentSession_ID" = :old_upd."InstrumentSession_ID" ;
	end;
end if;
-- Cascade child "pageusage" update when parent "instrumentsession" changed
if (:old_upd."InstrumentSession_ID" != :new_upd."InstrumentSession_ID")  then
	begin
	update "pageusage"
	set 	"InstrumentSession_ID" = :new_upd."InstrumentSession_ID"
	where 	"pageusage"."InstrumentSession_ID" = :old_upd."InstrumentSession_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "instrumentversion"
Create Trigger "tu_instrumentversion"
after update of "InstrumentVersion_ID","Instrument_ID","InstrumentHash_ID" on "instrumentversion" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "datum" update when parent "instrumentversion" changed
if (:old_upd."InstrumentVersion_ID" != :new_upd."InstrumentVersion_ID")  then
	begin
	update "datum"
	set 	"InstrumentVersion_ID" = :new_upd."InstrumentVersion_ID"
	where 	"datum"."InstrumentVersion_ID" = :old_upd."InstrumentVersion_ID" ;
	end;
end if;
-- Cascade child "instrumentcontent" update when parent "instrumentversion" changed
if (:old_upd."InstrumentVersion_ID" != :new_upd."InstrumentVersion_ID")  then
	begin
	update "instrumentcontent"
	set 	"InstrumentVersion_ID" = :new_upd."InstrumentVersion_ID"
	where 	"instrumentcontent"."InstrumentVersion_ID" = :old_upd."InstrumentVersion_ID" ;
	end;
end if;
-- Cascade child "instrumentheader" update when parent "instrumentversion" changed
if (:old_upd."InstrumentVersion_ID" != :new_upd."InstrumentVersion_ID")  then
	begin
	update "instrumentheader"
	set 	"InstrumentVersion_ID" = :new_upd."InstrumentVersion_ID"
	where 	"instrumentheader"."InstrumentVersion_ID" = :old_upd."InstrumentVersion_ID" ;
	end;
end if;
-- Cascade child "instrumentsession" update when parent "instrumentversion" changed
if (:old_upd."InstrumentVersion_ID" != :new_upd."InstrumentVersion_ID")  then
	begin
	update "instrumentsession"
	set 	"InstrumentVersion_ID" = :new_upd."InstrumentVersion_ID"
	where 	"instrumentsession"."InstrumentVersion_ID" = :old_upd."InstrumentVersion_ID" ;
	end;
end if;
-- Cascade child "instver_1" update when parent "instrumentversion" changed
if (:old_upd."InstrumentVersion_ID" != :new_upd."InstrumentVersion_ID")  then
	begin
	update "instver_1"
	set 	"InstrumentVersion_ID" = :new_upd."InstrumentVersion_ID"
	where 	"instver_1"."InstrumentVersion_ID" = :old_upd."InstrumentVersion_ID" ;
	end;
end if;
-- Cascade child "semanticmapping" update when parent "instrumentversion" changed
if (:old_upd."InstrumentVersion_ID" != :new_upd."InstrumentVersion_ID")  then
	begin
	update "semanticmapping"
	set 	"InstrumentVersion_ID" = :new_upd."InstrumentVersion_ID"
	where 	"semanticmapping"."InstrumentVersion_ID" = :old_upd."InstrumentVersion_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "item"
Create Trigger "tu_item"
after update of "Item_ID","Question_ID","DataType_ID","AnswerList_ID","Validation_ID" on "item" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "datum" update when parent "item" changed
if (:old_upd."Item_ID" != :new_upd."Item_ID")  then
	begin
	update "datum"
	set 	"Item_ID" = :new_upd."Item_ID"
	where 	"datum"."Item_ID" = :old_upd."Item_ID" ;
	end;
end if;
-- Cascade child "instrumentcontent" update when parent "item" changed
if (:old_upd."Item_ID" != :new_upd."Item_ID")  then
	begin
	update "instrumentcontent"
	set 	"Item_ID" = :new_upd."Item_ID"
	where 	"instrumentcontent"."Item_ID" = :old_upd."Item_ID" ;
	end;
end if;
-- Cascade child "loinc_itemrequest" update when parent "item" changed
if (:old_upd."Item_ID" != :new_upd."Item_ID")  then
	begin
	update "loinc_itemrequest"
	set 	"Item_ID" = :new_upd."Item_ID"
	where 	"loinc_itemrequest"."Item_ID" = :old_upd."Item_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "language"
Create Trigger "tu_language"
after update of "Language_ID" on "language" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "answerlocalized" update when parent "language" changed
if (:old_upd."Language_ID" != :new_upd."Language_ID")  then
	begin
	update "answerlocalized"
	set 	"Language_ID" = :new_upd."Language_ID"
	where 	"answerlocalized"."Language_ID" = :old_upd."Language_ID" ;
	end;
end if;
-- Cascade child "datum" update when parent "language" changed
if (:old_upd."Language_ID" != :new_upd."Language_ID")  then
	begin
	update "datum"
	set 	"Language_ID" = :new_upd."Language_ID"
	where 	"datum"."Language_ID" = :old_upd."Language_ID" ;
	end;
end if;
-- Cascade child "helplocalized" update when parent "language" changed
if (:old_upd."Language_ID" != :new_upd."Language_ID")  then
	begin
	update "helplocalized"
	set 	"Language_ID" = :new_upd."Language_ID"
	where 	"helplocalized"."Language_ID" = :old_upd."Language_ID" ;
	end;
end if;
-- Cascade child "itemusage" update when parent "language" changed
if (:old_upd."Language_ID" != :new_upd."Language_ID")  then
	begin
	update "itemusage"
	set 	"Language_ID" = :new_upd."Language_ID"
	where 	"itemusage"."Language_ID" = :old_upd."Language_ID" ;
	end;
end if;
-- Cascade child "questionlocalized" update when parent "language" changed
if (:old_upd."Language_ID" != :new_upd."Language_ID")  then
	begin
	update "questionlocalized"
	set 	"Language_ID" = :new_upd."Language_ID"
	where 	"questionlocalized"."Language_ID" = :old_upd."Language_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "languagelist"
Create Trigger "tu_languagelist"
after update of "LanguageList_ID" on "languagelist" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instrumenthash" update when parent "languagelist" changed
if (:old_upd."LanguageList_ID" != :new_upd."LanguageList_ID")  then
	begin
	update "instrumenthash"
	set 	"LanguageList_ID" = :new_upd."LanguageList_ID"
	where 	"instrumenthash"."LanguageList_ID" = :old_upd."LanguageList_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "nullflavor"
Create Trigger "tu_nullflavor"
after update of "NullFlavor_ID" on "nullflavor" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "datum" update when parent "nullflavor" changed
if (:old_upd."NullFlavor_ID" != :new_upd."NullFlavor_ID")  then
	begin
	update "datum"
	set 	"NullFlavor_ID" = :new_upd."NullFlavor_ID"
	where 	"datum"."NullFlavor_ID" = :old_upd."NullFlavor_ID" ;
	end;
end if;
-- Cascade child "itemusage" update when parent "nullflavor" changed
if (:old_upd."NullFlavor_ID" != :new_upd."NullFlavor_ID")  then
	begin
	update "itemusage"
	set 	"NullFlavor_ID" = :new_upd."NullFlavor_ID"
	where 	"itemusage"."NullFlavor_ID" = :old_upd."NullFlavor_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "pageusage"
Create Trigger "tu_pageusage"
after update of "PageUsage_ID","InstrumentSession_ID","ActionType_ID" on "pageusage" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "datum" update when parent "pageusage" changed
if (:old_upd."PageUsage_ID" != :new_upd."PageUsage_ID")  then
	begin
	update "datum"
	set 	"PageUsage_ID" = :new_upd."PageUsage_ID"
	where 	"datum"."PageUsage_ID" = :old_upd."PageUsage_ID" ;
	end;
end if;
-- Cascade child "pageusageevent" update when parent "pageusage" changed
if (:old_upd."PageUsage_ID" != :new_upd."PageUsage_ID")  then
	begin
	update "pageusageevent"
	set 	"PageUsage_ID" = :new_upd."PageUsage_ID"
	where 	"pageusageevent"."PageUsage_ID" = :old_upd."PageUsage_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "questionlocalized"
Create Trigger "tu_questionlocalized"
after update of "QuestionLocalized_ID","Language_ID" on "questionlocalized" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "item" update when parent "questionlocalized" changed
if (:old_upd."Question_ID" != :new_upd."Question_ID")  then
	begin
	update "item"
	set 	"Question_ID" = :new_upd."Question_ID"
	where 	"item"."Question_ID" = :old_upd."Question_ID" ;
	end;
end if;
-- Cascade child "semanticmapping" update when parent "questionlocalized" changed
if (:old_upd."Question_ID" != :new_upd."Question_ID")  then
	begin
	update "semanticmapping"
	set 	"Question_ID" = :new_upd."Question_ID"
	where 	"semanticmapping"."Question_ID" = :old_upd."Question_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "reservedword"
Create Trigger "tu_reservedword"
after update of "ReservedWord_ID" on "reservedword" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instrumentheader" update when parent "reservedword" changed
if (:old_upd."ReservedWord_ID" != :new_upd."ReservedWord_ID")  then
	begin
	update "instrumentheader"
	set 	"ReservedWord_ID" = :new_upd."ReservedWord_ID"
	where 	"instrumentheader"."ReservedWord_ID" = :old_upd."ReservedWord_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "user"
Create Trigger "tu_user"
after update of "User_ID" on "user" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "instrumentsession" update when parent "user" changed
if (:old_upd."User_ID" != :new_upd."User_ID")  then
	begin
	update "instrumentsession"
	set 	"User_ID" = :new_upd."User_ID"
	where 	"instrumentsession"."User_ID" = :old_upd."User_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "validation"
Create Trigger "tu_validation"
after update of "Validation_ID" on "validation" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "item" update when parent "validation" changed
if (:old_upd."Validation_ID" != :new_upd."Validation_ID")  then
	begin
	update "item"
	set 	"Validation_ID" = :new_upd."Validation_ID"
	where 	"item"."Validation_ID" = :old_upd."Validation_ID" ;
	end;
end if;
 

end;
/
-- Update trigger for "varname"
Create Trigger "tu_varname"
after update of "VarName_ID" on "varname" 
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "datum" update when parent "varname" changed
if (:old_upd."VarName_ID" != :new_upd."VarName_ID")  then
	begin
	update "datum"
	set 	"VarName_ID" = :new_upd."VarName_ID"
	where 	"datum"."VarName_ID" = :old_upd."VarName_ID" ;
	end;
end if;
-- Cascade child "instrumentcontent" update when parent "varname" changed
if (:old_upd."VarName_ID" != :new_upd."VarName_ID")  then
	begin
	update "instrumentcontent"
	set 	"VarName_ID" = :new_upd."VarName_ID"
	where 	"instrumentcontent"."VarName_ID" = :old_upd."VarName_ID" ;
	end;
end if;
-- Cascade child "itemusage" update when parent "varname" changed
if (:old_upd."VarName_ID" != :new_upd."VarName_ID")  then
	begin
	update "itemusage"
	set 	"VarName_ID" = :new_upd."VarName_ID"
	where 	"itemusage"."VarName_ID" = :old_upd."VarName_ID" ;
	end;
end if;
-- Cascade child "pageusageevent" update when parent "varname" changed
if (:old_upd."VarName_ID" != :new_upd."VarName_ID")  then
	begin
	update "pageusageevent"
	set 	"VarName_ID" = :new_upd."VarName_ID"
	where 	"pageusageevent"."VarName_ID" = :old_upd."VarName_ID" ;
	end;
end if;
 

end;
/


-- Create Packages section


-- Create Synonyms section


-- Create Table comments section


-- Create Attribute comments section


