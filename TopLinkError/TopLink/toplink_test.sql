DROP DATABASE toplink;
CREATE DATABASE toplink;
USE toplink;

DROP USER 'toplink'@'localhost';
CREATE USER 'toplink'@'localhost' IDENTIFIED BY 'toplink_pass';
GRANT USAGE ON * . * TO 'toplink'@'localhost' IDENTIFIED BY 'toplink_pass';
GRANT ALL PRIVILEGES ON toplink . * TO 'toplink'@'localhost';
FLUSH PRIVILEGES ;

CREATE TABLE Instrument (
	Instrument_ID	int(15)	NOT NULL,
	InstrumentName	varchar(255),
	InstrumentDescription	varchar(255),
	
  PRIMARY KEY pk_Instrument (Instrument_ID)
) ENGINE=InnoDB;

CREATE TABLE Item (
	Item_ID int(15) NOT NULL,
	Instrument_ID	int(15)	NOT NULL,
	VarName varchar(255),
	Question varchar(255),
	Answer varchar(255),
	
	PRIMARY KEY pk_Item (Item_ID)
) ENGINE=InnoDB;

ALTER TABLE Item
  ADD CONSTRAINT Item_ibfk_1 FOREIGN KEY (Instrument_ID) REFERENCES Instrument (Instrument_ID);