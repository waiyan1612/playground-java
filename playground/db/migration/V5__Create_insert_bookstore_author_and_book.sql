CREATE SCHEMA IF NOT EXISTS bookstore;
GRANT USAGE ON SCHEMA bookstore TO application;

ALTER DEFAULT PRIVILEGES IN SCHEMA bookstore GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO application;
ALTER DEFAULT PRIVILEGES IN SCHEMA bookstore GRANT USAGE, SELECT ON SEQUENCES TO application;

CREATE TABLE bookstore.author (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

CREATE SEQUENCE bookstore.book_id_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE bookstore.book (
    id BIGINT PRIMARY KEY DEFAULT nextval('bookstore.book_id_seq'),
    author_id INT NOT NULL REFERENCES bookstore.author(id),
    title VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

CREATE TABLE bookstore.author_bio (
    id SERIAL PRIMARY KEY,
    author_id INT NOT NULL REFERENCES bookstore.author(id),
    description VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

INSERT INTO bookstore.author (id, name) VALUES
    (1, 'William Shakespeare'),
    (2, 'Charles Dickens'),
    (3, 'Keigo Higashino'),
    (4, 'Margaret Mitchell');

-- Update the sequence after seeding to avoid conflicts with hibernate's GenerationType.IDENTITY
-- This won't be required if we omit the id from DML statements
SELECT setval('bookstore.author_id_seq', (SELECT MAX(id) FROM bookstore.author));

INSERT INTO bookstore.book (id, author_id, title) VALUES
    (1, 2, 'A Tale of Two Cities'),
    (2, 2, 'Great Expectations'),
    (3, 1, 'Hamlet'),
    (4, 1, 'King Lear'),
    (5, 3, 'Journey Under the Midnight Sun');

-- Update the sequence after seeding to avoid conflicts with hibernate's ID GenerationType.SEQUENCE
SELECT setval('bookstore.book_id_seq', (SELECT MAX(id) FROM bookstore.book));


INSERT INTO bookstore.author_bio (author_id, description) VALUES
    (1, 'Born 1564');

-- No need to update the sequence since we are not manually setting the id.
