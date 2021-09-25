
CREATE TABLE users
(
    id       SERIAL,
    login    VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    role     INT NOT NULL,
    balance  NUMERIC(6,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_role FOREIGN KEY (role) REFERENCES roles (id)
        ON DELETE SET NULL
);

--Populating table USERS
INSERT INTO users (login, password, role, balance)
VALUES ('nick', '111', 1, 500.00),
       ('mike', '222', 2, 500.00),
       ('john', '333', 2, 500.00),
       ('Василь', '444', 2, 500.00 );


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








