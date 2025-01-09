-- Criação da tabela admin
CREATE TABLE admin (
    id_admin SERIAL PRIMARY KEY UNIQUE NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    nome VARCHAR(200),
    admin_dt_cadastro TIMESTAMP DEFAULT NOW(),
);