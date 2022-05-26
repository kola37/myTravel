DROP database IF EXISTS travelagencydb;

CREATE database travelagencydb;

USE travelagencydb;

-- -----------------------------------------------------
-- Table onlineShop.users
-- -----------------------------------------------------
CREATE TABLE users (
	id INT PRIMARY KEY auto_increment,
	login VARCHAR(10) UNIQUE
);