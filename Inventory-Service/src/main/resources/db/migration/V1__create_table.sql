CREATE TABLE product (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    price DOUBLE,
    quantity_in_stock INT,
    PRIMARY KEY (id)
);