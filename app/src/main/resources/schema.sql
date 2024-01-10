DROP TABLE IF EXISTS urls;
DROP TABLE IF EXISTS url_checks;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    url_id bigint REFERENCES urls (id),
    status_code INTEGER NOT NULL,
    h1 VARCHAR(255),
    title VARCHAR(255),
    description VARCHAR(MAX),
    created_at TIMESTAMP NOT NULL
);