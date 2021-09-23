CREATE TABLE shows_rooms
(
    show_id INTEGER,
    room_id INTEGER,

    PRIMARY KEY (show_id, room_id),

    CONSTRAINT fk_show_id
        FOREIGN KEY (show_id) REFERENCES shows (id) ON DELETE CASCADE,

    CONSTRAINT fk_room_id
        FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE
);

-- Populating table shows_rooms
INSERT INTO shows_rooms
VALUES
       (1, 1),
       (1, 2),
       (2, 1),
       (2, 3),
       (3, 4),
       (3, 5);

-- Retrieving shows and rooms info
SELECT s.id, s.subject, s.date_begins, r.room
FROM shows s
         JOIN shows_rooms s_r ON s.id = s_r.show_id
         JOIN rooms r ON r.id = s_r.room_id
;

--Retrieving room list for shows
select room from(
SELECT s.id, s.subject, r.room
FROM shows s
         JOIN shows_rooms s_r ON s.id = s_r.show_id
         JOIN rooms r ON r.id = s_r.room_id) AS roomsearch
;






-- WHERE u.login = 'nick';






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








