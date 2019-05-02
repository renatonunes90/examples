CREATE TABLE APPLICATION_LOG
(
    APPLICATION_LOG_ID integer PRIMARY KEY,
    "CURRENT_DATE" timestamp,
    USERNAME varchar(255),
    MESSAGE blob
);

CREATE GENERATOR SEQ_APPLICATION_LOG;
SET GENERATOR SEQ_APPLICATION_LOG TO 0;

CREATE TABLE SERVICE_LOG
(
    SERVICE_LOG_ID integer PRIMARY KEY,
    "CURRENT_DATE" timestamp,
    USERNAME varchar(255),
    SOURCE varchar(255),
    METHOD varchar(32),
    PATH varchar(255),
    PARAMS blob,
    PAYLOAD blob,
    RESPONSE blob,
    SUCCESS integer
);

CREATE GENERATOR SEQ_SERVICE_LOG;
SET GENERATOR SEQ_SERVICE_LOG TO 0;

CREATE TABLE AUTHENTICATION_TYPE
(
    AUTHENTICATION_TYPE_ID integer PRIMARY KEY,
    DESCRIPTION varchar(255)
);

CREATE GENERATOR SEQ_AUTHENTICATION_TYPE;
SET GENERATOR SEQ_AUTHENTICATION_TYPE TO 0;

CREATE TABLE BREED
(
    BREED_ID integer PRIMARY KEY,
    BREED_TEXT varchar(255)
);

CREATE GENERATOR SEQ_BREED;
SET GENERATOR SEQ_BREED TO 0;

CREATE TABLE MARITAL_STATUS
(
    MARITAL_STATUS_ID integer PRIMARY KEY,
    MARITAL_STATUS_TEXT varchar(255)
);

CREATE GENERATOR SEQ_MARITAL_STATUS;
SET GENERATOR SEQ_MARITAL_STATUS TO 0;

CREATE TABLE PHONE_TYPE
(
    PHONE_TYPE_ID integer PRIMARY KEY,
    PHONE_TYPE_TEXT varchar(255)
);

CREATE GENERATOR SEQ_PHONE_TYPE;
SET GENERATOR SEQ_PHONE_TYPE TO 0;

CREATE TABLE PERSON
(
    PERSON_ID integer PRIMARY KEY,
    GENDER varchar(2),
    BIRTH_DATE timestamp,
	 NAME varchar(255),
	 CPF varchar(16),
	 BIRTH_COUNTRY varchar(32),
	 MARITAL_STATUS_ID integer,
	 BREED_ID integer,
	 BIRTH_CITY varchar(255),
	 FATHER_NAME varchar(255),
	 MOTHER_NAME varchar(255),
	 DELETED_DATE timestamp,
	 UPDATED_DATE timestamp,
 	"ACTIVE" integer
);

CREATE GENERATOR SEQ_PERSON;
SET GENERATOR SEQ_PERSON TO 0;

CREATE TABLE ACCOUNT
(
    ACCOUNT_ID integer PRIMARY KEY,
	"ACTIVE" integer
);

CREATE GENERATOR SEQ_ACCOUNT;
SET GENERATOR SEQ_ACCOUNT TO 0;

CREATE TABLE AUTHENTICATION
(
    AUTHENTICATION_ID integer PRIMARY KEY,
    AUTHENTICATION_TYPE_ID integer,
    ACCOUNT_ID integer,
    AUTHENTICATION varchar(255),
	"ACTIVE" integer
);

CREATE GENERATOR SEQ_AUTHENTICATION;
SET GENERATOR SEQ_AUTHENTICATION TO 0;

CREATE TABLE EMAIL
(
    EMAIL_ID integer PRIMARY KEY,
    PERSON_ID integer,
    EMAIL varchar(255),
	UPDATED_DATE timestamp
);

CREATE GENERATOR SEQ_EMAIL;
SET GENERATOR SEQ_EMAIL TO 0;

CREATE TABLE PHONE
(
    PHONE_ID integer PRIMARY KEY,
    PERSON_ID integer,
    PHONE_TYPE_ID integer,
    NUMBER varchar(32),
    EMAIL varchar(255),
    DDD varchar(3),
    DDI varchar(3),
	UPDATED_DATE timestamp
);

CREATE GENERATOR SEQ_PHONE;
SET GENERATOR SEQ_PHONE TO 0;