CREATE TABLE sandbox.hello (
    id SERIAL PRIMARY KEY,
    lang VARCHAR NOT NULL,
    message VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL DEFAULT 'flyway',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR NOT NULL DEFAULT 'flyway',
    UNIQUE (lang, message)
);
