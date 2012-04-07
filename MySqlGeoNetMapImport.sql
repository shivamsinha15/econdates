/* NOTE: Be sure that you have a database created to import into 
   and all the map files are located in the databases data directory
   for example - c:/mysql/data/db_name 

   To load the map data, just copy and paste the following in to
   the MySql command line.
*/
select 'Creating Destination Tables' as '';
-- Create Tables
CREATE TABLE Subnets (
	SubNetAddress varchar (11) NOT NULL ,
	Certainty smallint NULL ,
	CityId int NULL ,
	RegionId int NULL ,
	CountryId int NULL ,
	DmaId smallint NULL ,
	RegionCertainty smallint NULL ,
	CountryCertainty smallint NULL ,
	PRIMARY KEY(SubNetAddress)
	);

CREATE TABLE Cities (
	CityId int AUTO_INCREMENT NOT NULL ,
	CountryID smallint NOT NULL ,
	RegionID smallint NOT NULL ,
	City varchar (45) NOT NULL ,
	Latitude float NOT NULL ,
	Longitude float NOT NULL ,
	TimeZone varchar (10) NOT NULL ,
	DmaId smallint NULL ,
	County varchar (25) NULL ,
	Code varchar (4) NULL ,
	PRIMARY KEY(CityId)
	); 

CREATE TABLE Regions (
	RegionID smallint AUTO_INCREMENT NOT NULL ,
	CountryID smallint NOT NULL ,
	Region varchar (45) NOT NULL ,
	Code varchar (8) NOT NULL ,
	ADM1Code char (4) NOT NULL ,
	PRIMARY KEY(RegionID)
	);

CREATE TABLE Countries (
	CountryId smallint AUTO_INCREMENT NOT NULL ,
	Country varchar (50) NOT NULL ,
	FIPS104 varchar (2) NOT NULL ,
	ISO2 varchar (2) NOT NULL ,
	ISO3 varchar (3) NOT NULL ,
	ISON varchar (4) NOT NULL ,
	Internet varchar (2) NOT NULL ,
	Capital varchar (25) NULL ,
	MapReference varchar (50) NULL ,
	NationalitySingular varchar (35) NULL ,
	NationalityPlural varchar (35) NULL ,
	Currency varchar (30) NULL ,
	CurrencyCode varchar (3) NULL ,
	Population bigint NULL ,
	Title varchar (50) NULL ,
	Comment varchar (255) NULL ,
	PRIMARY KEY(CountryId)
	);

CREATE TABLE Dmas (
	DmaId smallint NOT NULL ,
	CountryId smallint NULL ,
	DMA varchar (3) NULL ,
	Market varchar (50) NULL
	);

CREATE TABLE ProxyNetworks (
	SubnetAddress varchar (11) NULL ,
	Network varchar (50) NULL ,
	CityId int NULL
	);

CREATE TABLE PrivateAddresses (
	AddressPrefix varchar (11) NOT NULL,
	PRIMARY KEY(AddressPrefix)
	);

CREATE TABLE Nbc (
	PrimaryCityId int NOT NULL ,
	CityId int NOT NULL ,
	Distance smallint NULL 
	);

select 'Loading Data Files Please Wait ...' as '';
-- Load Data From Flat Files
LOAD DATA INFILE 'Subnets.txt' INTO TABLE Subnets
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA INFILE 'Cities.txt' INTO TABLE Cities
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA INFILE 'Regions.txt' INTO TABLE Regions
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA INFILE 'Countries.txt' INTO TABLE Countries
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA INFILE 'Dmas.txt' INTO TABLE Dmas
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA INFILE 'ProxyNetworks.txt' INTO TABLE ProxyNetworks
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA INFILE 'PrivateAddresses.txt' INTO TABLE PrivateAddresses
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA INFILE 'Nbc.txt' INTO TABLE Nbc
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' IGNORE 1 LINES;