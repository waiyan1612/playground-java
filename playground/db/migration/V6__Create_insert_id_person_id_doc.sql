CREATE SCHEMA IF NOT EXISTS id;
GRANT USAGE ON SCHEMA id TO application;

ALTER DEFAULT PRIVILEGES IN SCHEMA id GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO application;
ALTER DEFAULT PRIVILEGES IN SCHEMA id GRANT USAGE, SELECT ON SEQUENCES TO application;

CREATE TABLE id.person (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

CREATE TABLE id.document (
    id SERIAL PRIMARY KEY,
    document_number VARCHAR NOT NULL,
    document_type VARCHAR NOT NULL,
    person_id INT NOT NULL,
    issued_at DATE NOT NULL,
    expires_at DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

CREATE TABLE id.passport (
    id INT PRIMARY KEY REFERENCES id.document(id) ON DELETE CASCADE,
    issued_by VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

CREATE TABLE id.driving_license (
    id INT PRIMARY KEY REFERENCES id.document(id) ON DELETE CASCADE,
    class_type VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway'
);

INSERT INTO id.person(name) VALUES
('Alice'),
('Bob');

INSERT INTO id.document(document_number, document_type, person_id, issued_at, expires_at) VALUES
('P100A', 'PASSPORT', 1, '2025-01-01', '2029-12-31'),
('D100A', 'DRIVING_LICENSE', 1, '2024-01-01', NULL),
('D200B', 'DRIVING_LICENSE', 2, '2025-01-01', NULL);

INSERT INTO id.passport(id, issued_by) VALUES
(1, 'Port Authority');

INSERT INTO id.driving_license(id, class_type) VALUES
(2, '3A'),
(3, '2');
