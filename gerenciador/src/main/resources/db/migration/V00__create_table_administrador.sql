-- Criação da tabela admin
CREATE TABLE admin (
    id_admin SERIAL PRIMARY KEY, -- PRIMARY KEY já implica em UNIQUE e NOT NULL
    email TEXT NOT NULL UNIQUE, -- UNIQUE e NOT NULL para garantir emails únicos e obrigatórios
    senha TEXT NOT NULL, -- Campo senha não deve ser nulo
    nome VARCHAR(200), -- Nome opcional
    admin_dt_cadastro TIMESTAMP DEFAULT NOW() -- Data de cadastro com valor padrão como a data/hora atual
);
