CREATE TABLE tickets
(
    order_id SERIAL,
    user_id  INTEGER,
    show_id  INTEGER,
    quantity INTEGER NOT NULL,
    date   TIMESTAMP NOT NULL,

    PRIMARY KEY (order_id),
    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_show_id
        FOREIGN KEY (show_id) REFERENCES shows (id) ON DELETE CASCADE
);

-- Populating table tickets
INSERT INTO tickets (user_id, show_id, quantity)
VALUES (1, 17, 4);

-- -- Retrieving tickets for the user<id=2>
SELECT t.order_id, t.show_id, t.date, s.subject,  t.quantity, s.price*t.quantity AS cost
FROM tickets t
         JOIN users u ON t.user_id = u.id
         JOIN shows s ON t.show_id = s.id
WHERE u.id = 2;


-- --Retrieving room list for shows
-- select room from(
-- SELECT s.id, s.subject, r.room
-- FROM shows s
--          JOIN shows_rooms s_r ON s.id = s_r.show_id
--          JOIN rooms r ON r.id = s_r.room_id) AS roomsearch
-- ;


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








