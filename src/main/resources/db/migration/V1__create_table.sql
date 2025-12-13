-- Flyway Migration: V1__create_table.sql

-- USERS TABLE

CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(50),
    permissions TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_role ON users (role);


-- PRODUCTS TABLE

CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    owner_id    BIGINT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_resources_owner
        FOREIGN KEY (owner_id)
            REFERENCES users (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);

CREATE INDEX idx_resources_name ON product (name);
CREATE INDEX idx_resources_owner_id ON product (owner_id);


-- TOKENS TABLE

CREATE TABLE tokens
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    token         VARCHAR(1000),
    refresh_token VARCHAR(1000),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_tokens_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE INDEX idx_tokens_user_id ON tokens (user_id);
CREATE INDEX idx_tokens_token ON tokens (token);


-- RENTALS TABLE

CREATE TABLE rentals
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT    NOT NULL,
    renter_id  BIGINT    NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date   TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_rentals_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,

    CONSTRAINT fk_rentals_renter
        FOREIGN KEY (renter_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

    CONSTRAINT chk_rental_dates CHECK (end_date > start_date)
);

CREATE INDEX idx_rentals_product_id ON rentals (product_id);
CREATE INDEX idx_rentals_renter_id ON rentals (renter_id);
CREATE INDEX idx_rentals_dates ON rentals (start_date, end_date);
