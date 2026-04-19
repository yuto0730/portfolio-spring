CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

INSERT INTO categories (name, created_at, updated_at) VALUES
('バックエンド', NOW(), NOW()),
('フロントエンド', NOW(), NOW()),
('インフラ', NOW(), NOW());
