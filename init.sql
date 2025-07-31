-- Initialize Blog Database
CREATE DATABASE IF NOT EXISTS blogdb;
USE blogdb;

-- Create roles table if not exists
CREATE TABLE IF NOT EXISTS role (
    role_id BIGINT PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);

-- Insert default roles if they don't exist
INSERT IGNORE INTO role (role_id, role_name) VALUES 
(502, 'ROLE_USER'),
(503, 'ROLE_ADMIN');

-- Grant permissions
GRANT ALL PRIVILEGES ON blogdb.* TO 'bloguser'@'%';
FLUSH PRIVILEGES;