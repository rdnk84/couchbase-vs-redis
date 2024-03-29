CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS movies;


CREATE TABLE orders(
id serial NOT NULL PRIMARY KEY,
info json NOT NULL
);

CREATE TABLE movies(
id VARCHAR(250) DEFAULT NULL,
name VARCHAR(250) NOT NULL,
description VARCHAR(250) NOT NULL
);

--CREATE TABLE movies(
--id uuid DEFAULT uuid_generate_v4(),
--name VARCHAR(250) NOT NULL,
--description VARCHAR(250) NOT NULL
--);

--CREATE TABLE movies(
--id serial NOT NULL PRIMARY KEY,
--name VARCHAR(250) NOT NULL,
--description VARCHAR(250) NOT NULL
--);

--CREATE TABLE authors(
--id INT PRIMARY KEY,
--photo VARCHAR(250) NOT NULL,
--slug VARCHAR(250) NOT NULL,
--full_name VARCHAR(250) DEFAULT NULL,
--description VARCHAR(250) DEFAULT NULL
--);


--CREATE TABLE movies(
--id INT AUTO_INCREMENT PRIMARY KEY,
--name VARCHAR(250) NOT NULL,
--description VARCHAR(250) NOT NULL
--);
--
--CREATE TABLE main_doc(
--id INT AUTO_INCREMENT PRIMARY KEY,
--name VARCHAR(250) NOT NULL
--);

--CREATE TABLE main_doc (
--  id int IDENTITY(1,1) NOT NULL PRIMARY KEY,
--  name nvarchar(255) NOT NULL
--)
--CREATE TABLE movies (
--  id int IDENTITY(1,1) NOT NULL PRIMARY KEY,
--  name nvarchar(255) NOT NULL,
--  ParentId int NOT NULL
--);