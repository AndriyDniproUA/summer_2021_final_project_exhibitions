
CREATE TABLE users
(
    id       SERIAL,
    login    VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    role     INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_role FOREIGN KEY (role) REFERENCES roles (id)
        ON DELETE SET NULL
);

--Populating table USERS
INSERT INTO users (login, password, role)
VALUES ('nick', '111', 1),
       ('mike', '222', 2),
       ('john', '333', 3),
       ('Василь', '444', 1);


-- GETTING ALL USERS
SELECT u.id, u.login, u.password, r.role
FROM users u
         JOIN roles r ON u.role = r.id;

-- GETTING A USER BY LOGIN <nick>
SELECT u.id, u.login, u.password, r.role
FROM users u
         JOIN roles r ON u.role = r.id
WHERE u.login = 'nick';

-- ADDING NEW USER <login: Maya, password:666, role: user_auth>
INSERT INTO users (login, password, role)
VALUES ('Maya', '666', 3);

-- Deleting USER <login: Maya>
DELETE
FROM users
WHERE login = 'Maya';

-- Update USER <login: john>
UPDATE users SET login='john2', password='pass', role=1 WHERE login = 'john';








