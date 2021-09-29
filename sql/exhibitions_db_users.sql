
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
       ('Василь', '444', 2, 500.00 ),
       ('user1', '111', 2, 50.00 ),
       ('user2', '111', 2, 50.00 ),
       ('user3', '111', 2, 50.00 ),
       ('user4', '111', 2, 50.00 ),
       ('user5', '111', 2, 50.00 ),
       ('user6', '111', 2, 50.00 ),
       ('user7', '111', 2, 50.00 ),
       ('user8', '111', 2, 50.00 ),
       ('user9', '111', 2, 50.00 ),
       ('user10', '111', 2, 50.00 ),
       ('user11', '111', 2, 50.00 ),
       ('user12', '111', 2, 50.00 ),
       ('user13', '111', 2, 50.00 ),
       ('user14', '111', 2, 50.00 ),
       ('user15', '111', 2, 50.00 ),
       ('user16', '111', 2, 50.00 ),
       ('user17', '111', 2, 50.00 ),
       ('user18', '111', 2, 50.00 ),
       ('user19', '111', 2, 50.00 ),
       ('user20', '111', 2, 50.00 ),
       ('user21', '111', 2, 50.00 ),
       ('user22', '111', 2, 50.00 ),
       ('user23', '111', 2, 50.00 ),
       ('user24', '111', 2, 50.00 );


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








