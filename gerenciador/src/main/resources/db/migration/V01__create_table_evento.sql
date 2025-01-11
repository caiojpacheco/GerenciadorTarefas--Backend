-- Criação da tabela evento
CREATE TABLE evento (
    id_evento SERIAL PRIMARY KEY, -- PRIMARY KEY já implica em UNIQUE e NOT NULL
    nome VARCHAR(200), -- Nome do evento (opcional)
    data DATE NOT NULL, -- Data obrigatória do evento
    localizacao VARCHAR(200) NOT NULL, -- Localização obrigatória
    imagem TEXT, -- Ajustado para TEXT, pois VARCHAR(MAX) não é suportado no padrão SQL
    id_admin BIGINT, -- Referência ao administrador

    evento_dt_criacao TIMESTAMP DEFAULT NOW(), -- Data de criação com valor padrão
    evento_dt_alteracao TIMESTAMP, -- Data de alteração (opcional)

    FOREIGN KEY (id_admin) REFERENCES admin(id_admin) -- Chave estrangeira referenciando a tabela admin
);
