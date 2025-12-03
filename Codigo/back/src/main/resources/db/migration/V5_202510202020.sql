CREATE TABLE IF NOT EXISTS configuracao_email
(
    id BIGSERIAL,
    host VARCHAR(200) NOT NULL,
    porta INTEGER NOT NULL,
    usuario VARCHAR(200) NOT NULL,
    senha VARCHAR(500) NOT NULL,
    nome_remetente VARCHAR(100),
    tipo_seguranca SMALLINT NOT NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
)