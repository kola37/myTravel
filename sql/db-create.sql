DROP database IF EXISTS travelagencydb;

CREATE database travelagencydb;

USE travelagencydb;

----------------------------------------------------------------
------------- Table ROLES
------------- users roles
----------------------------------------------------------------
CREATE TABLE roles(
	id INTEGER NOT NULL,
	name VARCHAR(15) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

--------------------------------------------------------
-------------- Table USERS
--------------------------------------------------------
CREATE TABLE users (
	id INTEGER AUTO_INCREMENT,
	login VARCHAR(15) NOT NULL UNIQUE,
	password VARCHAR(15) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    role_id INTEGER NOT NULL,
    is_blocked BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE RESTRICT
);


----------------------------------------------------------------
------------- Table HOTEL_TYPES
------------- hotels types
----------------------------------------------------------------
CREATE TABLE hotel_types(
	id INTEGER NOT NULL,
	name VARCHAR(15) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

--------------------------------------------------------
-------------- Table HOTELS
--------------------------------------------------------
CREATE TABLE hotels (
	id INTEGER AUTO_INCREMENT,
	name VARCHAR(15) NOT NULL,
    hotel_type_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (hotel_type_id) REFERENCES hotel_types(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

----------------------------------------------------------------
------------- Table TOUR_TYPES
------------- tours types
----------------------------------------------------------------
CREATE TABLE tour_types(
	id INTEGER NOT NULL,
	name VARCHAR(15) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

--------------------------------------------------------
-------------- Table TOURS
--------------------------------------------------------
CREATE TABLE tours (
	id INTEGER AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(500),
    image VARCHAR(100),
    price DOUBLE NOT NULL,
    tour_type_id INTEGER NOT NULL,
    num_of_persons INTEGER NOT NULL,
    hotel_id INTEGER,
    promotion_id INTEGER,
    is_hot BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY (id),
    FOREIGN KEY (tour_type_id) REFERENCES tour_types(id) ON DELETE CASCADE ON UPDATE RESTRICT,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE,
    FOREIGN KEY (promotion_id) REFERENCES promotions(id) ON DELETE CASCADE
);

----------------------------------------------------------------
------------- Table STATUSES
----------- statuses for orders
----------------------------------------------------------------
CREATE TABLE statuses(
	id INTEGER NOT NULL,
	name VARCHAR(15) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

--------------------------------------------------------
-------------- Table ORDERS
--------------------------------------------------------
CREATE TABLE orders (
	id INTEGER AUTO_INCREMENT,
	user_id INTEGER NOT NULL,
	tour_id INTEGER NOT NULL,
	status_id INTEGER NOT NULL,
	order_date DATE NOT NULL,
    discount INTEGER NOT NULL DEFAULT 0,
    total_price DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (tour_id) REFERENCES tours(id),
    FOREIGN KEY (status_id) REFERENCES statuses(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

----------------------------------------------------------------
------------- Table PROMOTIONS
----------------------------------------------------------------
CREATE TABLE promotions(
	id INTEGER AUTO_INCREMENT,
	discount_rate INTEGER NOT NULL DEFAULT 0,
	max_discount INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY (id)
);

---------------------------------------------------------------
------------- Table USERS_ORDERS
-----------represent relation between user and his order
----------------------------------------------------------------
--CREATE TABLE users_orders(
--    user_id INTEGER NOT NULL,
--    order_id INTEGER NOT NULL,
--    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
--    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
--    UNIQUE (user_id, order_id)
--);

