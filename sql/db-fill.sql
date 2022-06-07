USE travelagencydb;

-----------------------------------------
------insert data into roles table-------
-----------------------------------------
INSERT INTO roles VALUES(1, 'user');
INSERT INTO roles VALUES(2, 'manager');
INSERT INTO roles VALUES(3, 'admin');



-----------------------------------------------
------insert data into hotel_types table-------
-----------------------------------------------
INSERT INTO hotel_types VALUES(1, 'apartment');
INSERT INTO hotel_types VALUES(2, 'hostel');
INSERT INTO hotel_types VALUES(3, 'tourist_hotel');
INSERT INTO hotel_types VALUES(4, 'comfort_hotel');
INSERT INTO hotel_types VALUES(5, 'premium_hotel');
INSERT INTO hotel_types VALUES(6, 'boutique_hotel');



-----------------------------------------------
------insert data into tour_types table--------
-----------------------------------------------
INSERT INTO tour_types VALUES(1, 'holidays');
INSERT INTO tour_types VALUES(2, 'excursion');
INSERT INTO tour_types VALUES(3, 'shopping');





-----------------------------------------------
------insert data into statuses table--------
-----------------------------------------------
INSERT INTO statuses VALUES(1, 'registered');
INSERT INTO statuses VALUES(2, 'paid');
INSERT INTO statuses VALUES(3, 'canceled');