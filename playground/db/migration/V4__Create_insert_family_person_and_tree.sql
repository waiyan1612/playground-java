CREATE SCHEMA IF NOT EXISTS family;
GRANT USAGE ON SCHEMA family TO application;

ALTER DEFAULT PRIVILEGES IN SCHEMA family GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO application;
ALTER DEFAULT PRIVILEGES IN SCHEMA family GRANT USAGE, SELECT ON SEQUENCES TO application;

CREATE TABLE family.person (
    id INT PRIMARY KEY,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

CREATE TABLE family.tree (
    parent_id INT NOT NULL REFERENCES family.person(id),
    child_id INT NOT NULL REFERENCES family.person(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway',
    PRIMARY KEY (parent_id, child_id)
);

INSERT INTO family.person (id, name) VALUES
    (1, 'me'),
    (2, 'father'),
    (3, 'grandfather'),
    (4, 'daughter'),
    (5, 'uncle'),
    (6, 'sister');

INSERT INTO family.tree (parent_id, child_id) VALUES
    (3, 2),
    (3, 5),
    (2, 1),
    (2, 6),
    (1, 4);
