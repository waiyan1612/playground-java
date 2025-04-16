CREATE SCHEMA IF NOT EXISTS bookstore;
GRANT USAGE ON SCHEMA bookstore TO application;

ALTER DEFAULT PRIVILEGES IN SCHEMA bookstore GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO application;
ALTER DEFAULT PRIVILEGES IN SCHEMA bookstore GRANT USAGE, SELECT ON SEQUENCES TO application;

CREATE TABLE bookstore.author (
    id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

CREATE TABLE bookstore.book (
    id INT PRIMARY KEY,
    author_id INT NOT NULL REFERENCES bookstore.author(id),
    name VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

INSERT INTO bookstore.author (id, name) VALUES
    (1, 'William Shakespeare'),
    (2, 'Charles Dickens'),
    (3, 'Keigo Higashino');
    
INSERT INTO bookstore.book (id, author_id, name) VALUES
    (1, 2, 'A Tale of Two Cities'),
    (2, 2, 'Great Expectations'),
    (3, 1, 'Hamlet'),
    (4, 1, 'King Lear'),
    (5, 3, 'Journey Under the Midnight Sun');
