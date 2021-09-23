CREATE TABLE rooms
(
    id   SERIAL,
    room VARCHAR(10) UNIQUE,
    PRIMARY KEY (id)
);


INSERT INTO rooms (room)
VALUES ('red'),
       ('green'),
       ('blue'),
       ('black'),
       ('white');










