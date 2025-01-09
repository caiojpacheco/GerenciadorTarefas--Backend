-- Criação da tabela evento
CREATE TABLE evento (
	id_evento SERIAL PRIMARY KEY UNIQUE NOT NULL,
	nome VARCHAR(200),
	data DATE NOT NULL,
	localizacao VARCHAR(200) NOT NULL,
	imagem VARCHAR(MAX),
	id_admin BIGINT,

	FOREIGN KEY (id_admin) REFERENCES admin(id_admin),

	evento_dt_criacao TIMESTAMP DEFAULT NOW(),
    evento_dt_alteracao TIMESTAMP

);