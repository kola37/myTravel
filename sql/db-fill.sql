USE travelagencydb;

-----------------------------------------
------insert data into roles table-------
-----------------------------------------
INSERT INTO roles VALUES(1, 'user');
INSERT INTO roles VALUES(2, 'manager');
INSERT INTO roles VALUES(3, 'admin');


-----------------------------------------
------insert data into users table-------
-----------------------------------------
INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Admin', 'Admin', 'admin@admin.com', 3, false);


-----------------------------------------------
------insert data into hotel_types table-------
-----------------------------------------------
INSERT INTO hotel_types VALUES(1, 'apartment');
INSERT INTO hotel_types VALUES(2, 'hostel');
INSERT INTO hotel_types VALUES(3, 'tourist_hotel');
INSERT INTO hotel_types VALUES(4, 'comfort_hotel');
INSERT INTO hotel_types VALUES(5, 'premium_hotel');
INSERT INTO hotel_types VALUES(6, 'boutique_hotel');


-----------------------------------------
------insert data into hotels table------
-----------------------------------------
INSERT INTO hotels VALUES(DEFAULT, 'NO HOTEL', 1);
INSERT INTO hotels VALUES(DEFAULT, 'Travel-Inn Berlin', 4);
INSERT INTO hotels VALUES(DEFAULT, 'Travel-Inn', 4);
INSERT INTO hotels VALUES(DEFAULT, 'Residence-Inn by Marriott', 5);
INSERT INTO hotels VALUES(DEFAULT, 'Hyatt House Pleasant', 5);
INSERT INTO hotels VALUES(DEFAULT, 'Sun Valley Inn San Francisco', 3);
INSERT INTO hotels VALUES(DEFAULT, 'Hilton San Francisco', 4);
INSERT INTO hotels VALUES(DEFAULT, 'Hilton Bora Bora', 4);
INSERT INTO hotels VALUES(DEFAULT, 'Kater Apartments Berlin', 1);
INSERT INTO hotels VALUES(DEFAULT, 'L''Amour Apartments Paris', 1);
INSERT INTO hotels VALUES(DEFAULT, 'Mercure Hotel Istanbul', 3);
INSERT INTO hotels VALUES(DEFAULT, 'Mercure Hotel Paris', 3);
INSERT INTO hotels VALUES(DEFAULT, 'Park Inn by Radisson', 4);
INSERT INTO hotels VALUES(DEFAULT, 'Radisson Blue Paris', 4);
INSERT INTO hotels VALUES(DEFAULT, 'Hotel Edgar Paris', 6);
INSERT INTO hotels VALUES(DEFAULT, 'Four Seasons Resort', 5);
INSERT INTO hotels VALUES(DEFAULT, 'Marriott Resort', 4);
INSERT INTO hotels VALUES(DEFAULT, 'Ibis Istanbul', 2);
INSERT INTO hotels VALUES(DEFAULT, 'Ibis Berlin', 2);
INSERT INTO hotels VALUES(DEFAULT, 'Ibis Budget', 2);
INSERT INTO hotels VALUES(DEFAULT, 'Roma Antico', 1);
INSERT INTO hotels VALUES(DEFAULT, 'Mercure Roma Centro Colosseo', 3);


-----------------------------------------------
------insert data into tour_types table--------
-----------------------------------------------
INSERT INTO tour_types VALUES(1, 'holidays');
INSERT INTO tour_types VALUES(2, 'excursion');
INSERT INTO tour_types VALUES(3, 'shopping');


-----------------------------------------
------insert data into tours table-------
-----------------------------------------
INSERT INTO tours VALUES(DEFAULT, 'Amazing Bora Bora', 'Undoubtedly the most celebrated island in the South Pacific, Bora Bora is French Polynesia''s leading lady. Her beauty is unrivaled and her fame, unwavering. Bora Bora is one of the few places on earth that everyone hopes to witness in their lifetime—and once you see it, you are forever enamored.'
                         , '/images/bora-bora.png', 5000, 1, 2, 8, 2, 12, false);
INSERT INTO tours VALUES(DEFAULT, 'Berlin – the city where anything is possible', 'Experience Berlin’s past and present: visit landmarks from the city’s Prussian, Imperial, Nazi, Cold War and modern eras, offering insight into the evolution of this fascinating German capital. Stroll along the iconic Berlin Wall and follow Unter den Linden to the Brandenburg Gate. Then check out the rebuilt Reichstag and the Holocaust Memorial and hear the amazing stories of Cold War Berlin at Checkpoint Charlie.'
                         , '/images/berlin.png', 300, 2, 1, 19, 0, 0, true);



-----------------------------------------------
------insert data into statuses table--------
-----------------------------------------------
INSERT INTO statuses VALUES(1, 'registered');
INSERT INTO statuses VALUES(2, 'paid');
INSERT INTO statuses VALUES(3, 'canceled');