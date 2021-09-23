CREATE TABLE roles
(
    id   SERIAL,
    role VARCHAR(10) UNIQUE,
    PRIMARY KEY (id)
);

-- Populating table ROLES
INSERT INTO roles (role)
VALUES ('admin'),
       ('user');










