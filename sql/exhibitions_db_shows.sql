CREATE TABLE shows
(
    id          SERIAL,
    subject     VARCHAR(200)  NOT NULL,
    date_begins TIMESTAMP     NOT NULL,
    date_ends   TIMESTAMP     NOT NULL,
    time_opens  VARCHAR(12)   NOT NULL,
    time_closes VARCHAR(12)   NOT NULL,
    price       NUMERIC(5, 2) NOT NULL,
    PRIMARY KEY (id)
);

-- Populating table SHOWS
INSERT INTO shows
VALUES (DEFAULT, 'Show number 1', '2021-09-20', '2021-09-27', '08:00', '17:00', 100.00),
       (DEFAULT, 'Show number 2', '2021-09-20', '2021-09-27', '08:00', '17:00', 100.00),
       (DEFAULT, 'Show number 3', '2021-09-20', '2021-09-27', '08:00', '17:00', 100.00);


-- Returning shows list
SELECT id, subject, date_begins, date_ends, time_opens, time_closes, price
FROM shows;


-- Retrieving rooms for the show <id=2>
SELECT r.room
FROM shows s
         JOIN shows_rooms s_r ON s.id = s_r.show_id
         JOIN rooms r ON r.id = s_r.room_id
WHERE s.id = 2;


-- Deleting entry with id=1
DELETE
FROM shows
WHERE id = 1;



--        ('Василь', '444', 1);


-- GETTING ALL USERS
-- SELECT u.id, u.login, u.password, r.role
-- FROM users u
--          JOIN roles r ON u.role = r.id;

-- GETTING A USER BY LOGIN <nick>
-- SELECT u.id, u.login, u.password, r.role
-- FROM users u
--          JOIN roles r ON u.role = r.id
-- WHERE u.login = 'nick';

-- ADDING NEW USER <login: Maya, password:666, role: user_auth>
-- INSERT INTO users (login, password, role)
-- VALUES ('Maya', '666', 3);

-- Deleting USER <login: Maya>
-- DELETE
-- FROM users
-- WHERE login = 'Maya';

-- Update USER <login: john>
-- UPDATE users SET login='john2', password='pass', role=1 WHERE login = 'john';








