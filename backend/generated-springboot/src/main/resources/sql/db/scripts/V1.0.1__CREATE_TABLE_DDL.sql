-- Create the schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS ocr_test;

-- Create the document table
CREATE TABLE IF NOT EXISTS ocr_test.document (
    ID BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_size BIGINT,
    content TEXT,
    upload_date TIMESTAMP
);

-- Create index on file_name
CREATE INDEX IF NOT EXISTS idx_file_name ON ocr_test.document (file_name);