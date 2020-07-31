BEGIN;

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);
INSERT INTO users (name)
VALUES ('Vladislav'),
       ('Boris'),
       ('Denis'),
       ('Vasiliy'),
       ('Sergey'),
       ('Georgiy'),
       ('Maksim'),
       ('Evgeniy');

DROP TABLE IF EXISTS lots CASCADE;
CREATE TABLE lots
(
    id             BIGSERIAL PRIMARY KEY,
    title          VARCHAR(255),
    current_bet    INTEGER,
    last_bet_owner INTEGER REFERENCES users (id),
    version        INTEGER
);
INSERT INTO lots (title, current_bet, version)
VALUES ('ring with diamond', 0, 0),
       ('statue', 0, 0),
       ('canvas', 0, 0),
       ('book', 0, 0);


COMMIT;