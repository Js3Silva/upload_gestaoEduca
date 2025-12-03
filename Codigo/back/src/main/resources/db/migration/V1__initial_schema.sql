CREATE SEQUENCE codigo_seq START 10000;

CREATE TABLE produtos
(
    id           BIGSERIAL,
    codigo       BIGINT       NOT NULL,
    nome         VARCHAR(100) NOT NULL,
    descricao    VARCHAR(5000) NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_produtos PRIMARY KEY (id),
    CONSTRAINT uq_produtos_codigo UNIQUE (codigo)
);

CREATE TABLE clientes
(
    id           BIGSERIAL,
    codigo       BIGINT       NOT NULL,
    razao_social VARCHAR(300) NOT NULL,
    cnpj         VARCHAR(14)  NOT NULL,
    cidade       VARCHAR(100) NOT NULL,
    estado       CHAR(2)      NOT NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_clientes PRIMARY KEY (id),
    CONSTRAINT uq_clientes_codigo UNIQUE (codigo),
    CONSTRAINT uq_clientes_cnpj UNIQUE (cnpj)
);

CREATE TABLE atividades
(
    id           BIGSERIAL,
    produto_id   BIGINT       NOT NULL,
    nome         VARCHAR(100) NOT NULL,
    descricao    VARCHAR(5000) NULL,
    prioridade   SMALLINT     NOT NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_atividades PRIMARY KEY (id),
    CONSTRAINT fk_atividades_produto_id FOREIGN KEY (produto_id) REFERENCES produtos (id)
);

CREATE TABLE usuarios
(
    id           BIGSERIAL,
    nome         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL,
    login        VARCHAR(50)  NOT NULL,
    senha        VARCHAR(128) NOT NULL,
    tipo_usuario SMALLINT     NOT NULL,
    status       SMALLINT     NOT NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_email UNIQUE (email),
    CONSTRAINT uq_usuarios_login UNIQUE (login)
);

CREATE TABLE projetos
(
    id           BIGSERIAL,
    cliente_id   BIGINT       NOT NULL,
    codigo       BIGINT       NOT NULL,
    nome         VARCHAR(100) NOT NULL,
    objetivo     VARCHAR(5000) NULL,
    inicio       DATE         NOT NULL,
    termino      DATE         NOT NULL,
    status       SMALLINT     NOT NULL,
    data_criacao TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_projetos PRIMARY KEY (id),
    CONSTRAINT fk_projetos_cliente_id FOREIGN KEY (cliente_id) REFERENCES clientes (id)
);

CREATE TABLE projetos_produtos
(
    projeto_id       BIGINT   NOT NULL,
    produto_id       BIGINT   NOT NULL,
    prioridade       SMALLINT NOT NULL,
    previsao_inicio  DATE     NOT NULL,
    previsao_termino DATE     NOT NULL,

    CONSTRAINT pk_projetos_produtos PRIMARY KEY (projeto_id, produto_id),
    CONSTRAINT fk_projetos_produtos_projeto_id FOREIGN KEY (projeto_id) REFERENCES projetos (id),
    CONSTRAINT fk_projetos_produtos_produto_id FOREIGN KEY (produto_id) REFERENCES produtos (id)
);

CREATE TABLE projetos_usuarios
(
    projeto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT pk_projetos_usuarios PRIMARY KEY (projeto_id, usuario_id),
    CONSTRAINT fk_projetos_projeto_id FOREIGN KEY (projeto_id) REFERENCES projetos (id),
    CONSTRAINT fk_projetos_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);

CREATE TABLE tarefas
(
    id             BIGSERIAL,
    projeto_id     BIGINT       NOT NULL,
    produto_id     BIGINT       NOT NULL,
    nome           VARCHAR(100) NOT NULL,
    descricao      VARCHAR(5000),
    prioridade     SMALLINT     NOT NULL,
    prazo          DATE         NOT NULL,
    inicio         TIMESTAMPTZ NULL,
    termino        TIMESTAMPTZ NULL,
    status         SMALLINT     NOT NULL,
    planejada      BOOLEAN      NOT NULL,
    atividade_id   BIGINT NULL,
    responsavel_id BIGINT,
    data_criacao   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_tarefas PRIMARY KEY (id),
    CONSTRAINT fk_tarefas_projeto_id_produto_id FOREIGN KEY (projeto_id, produto_id) REFERENCES projetos_produtos (projeto_id, produto_id),
    CONSTRAINT fk_tarefas_atividade_id FOREIGN KEY (atividade_id) REFERENCES atividades (id),
    CONSTRAINT fk_tarefas_responsavel_id FOREIGN KEY (responsavel_id) REFERENCES usuarios (id)
);

CREATE TABLE comentarios
(
    id               BIGSERIAL,
    projeto_id       BIGINT NULL,
    tarefa_id        BIGINT NULL,
    usuario_id       BIGINT        NOT NULL,
    texto            VARCHAR(5000) NOT NULL,
    visibilidade     SMALLINT      NOT NULL,
    data_criacao     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMPTZ NULL,

    CONSTRAINT pk_comentarios PRIMARY KEY (id),
    CONSTRAINT fk_comentarios_projeto_id FOREIGN KEY (projeto_id) REFERENCES projetos (id),
    CONSTRAINT fk_comentarios_tarefa_id FOREIGN KEY (tarefa_id) REFERENCES tarefas (id),
    CONSTRAINT fk_comentarios_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id),

    CONSTRAINT ck_comentarios_relacionamento CHECK (
        (projeto_id IS NOT NULL AND tarefa_id IS NULL) OR
        (projeto_id IS NULL AND tarefa_id IS NOT NULL)
        )
);

CREATE TABLE comentarios_anexos
(
    id              BIGSERIAL,
    comentario_id   BIGINT       NOT NULL,
    nome_arquivo    VARCHAR(255) NOT NULL,
    caminho_arquivo TEXT         NOT NULL,
    tipo_arquivo    VARCHAR(50)  NOT NULL,
    tamanho_arquivo BIGINT       NOT NULL,
    data_criacao    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_comentarios_anexos PRIMARY KEY (id),
    CONSTRAINT fk_comentarios_anexos_comentario_id FOREIGN KEY (comentario_id) REFERENCES comentarios (id)
);


