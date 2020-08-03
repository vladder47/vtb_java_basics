BEGIN;

DROP TABLE IF EXISTS clients CASCADE;
CREATE TABLE clients
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);
INSERT INTO clients (name)
VALUES ('Vladislav'),
       ('Boris'),
       ('Denis'),
       ('Vasiliy'),
       ('temp_user');;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) UNIQUE,
    current_price INTEGER
);
INSERT INTO products (name, current_price)
VALUES ('phone', 1000),
       ('notebook', 3500),
       ('fridge', 8000),
       ('microwave', 2500),
       ('temp_product', 10000);

DROP TABLE IF EXISTS purchases_details CASCADE;
CREATE TABLE purchases_details
(
    id            BIGSERIAL PRIMARY KEY,
    purchase_cost INTEGER,
    client_id     INTEGER REFERENCES clients (id),
    product_id    INTEGER REFERENCES products (id)
);
INSERT INTO purchases_details (purchase_cost, client_id, product_id)
VALUES (1000, 1, 1),
       (3500, 1, 2),
       (8000, 2, 3),
       (2500, 2, 4),
       (1000, 4, 1),
       (8000, 4, 3);

COMMIT;