USE master;

IF DB_ID('CyCalFX23') IS NOT NULL
	DROP DATABASE CyCalFX23;

CREATE DATABASE CyCalFX23;
GO

USE CyCalFX23;

CREATE TABLE [category] (
  [id] NVARCHAR(255) PRIMARY KEY,
  [priority] INT NOT NULL
);

CREATE TABLE [race] (
  [id] INT PRIMARY KEY,
  [category_id] NVARCHAR(255) NOT NULL,
  [name] NVARCHAR(255) NOT NULL,
  [start_date] DATE NOT NULL,
  [end_date] DATE NOT NULL,
  [logo] NVARCHAR(255),
  [flag] NVARCHAR(255),
  [distance] NVARCHAR(255),
  [profile_icon] NVARCHAR(255),
  [profile] NVARCHAR(255),
  FOREIGN KEY ([category_id]) REFERENCES [category]([id])
);

CREATE TABLE [stage] (
  [id] INT PRIMARY KEY IDENTITY (1, 1),
  [race_id] INT NOT NULL,
  [number] INT NOT NULL, -- 0 = prologue
  [date] DATE NOT NULL,
  [distance] NVARCHAR(255),
  [profile_icon] NVARCHAR(255),
  [profile] NVARCHAR(255),
  FOREIGN KEY ([race_id]) REFERENCES [race]([id])
);

INSERT INTO [category] VALUES ('WCRR', 1);
INSERT INTO [category] VALUES ('WCTT', 2);
INSERT INTO [category] VALUES ('1.UWT', 3);
INSERT INTO [category] VALUES ('2.UWT', 4);
INSERT INTO [category] VALUES ('CCRR', 5);
INSERT INTO [category] VALUES ('CCTT', 6);
INSERT INTO [category] VALUES ('1.Pro', 7);
INSERT INTO [category] VALUES ('2.Pro', 8);
INSERT INTO [category] VALUES ('1.1', 9);
INSERT INTO [category] VALUES ('2.1', 10);