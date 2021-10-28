CREATE TABLE IF NOT EXISTS uploaded_document
(
    id              SERIAL PRIMARY KEY,
    title           VARCHAR,
    time_added      TIMESTAMP,
    content         BYTEA
);

