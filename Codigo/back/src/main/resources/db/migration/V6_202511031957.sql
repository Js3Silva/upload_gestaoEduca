ALTER TABLE projetos_produtos
    ADD COLUMN id BIGSERIAL;

ALTER TABLE projetos_produtos
    ADD CONSTRAINT uq_projetos_produtos_id UNIQUE (id);

ALTER TABLE tarefas
DROP CONSTRAINT fk_tarefas_projeto_id_produto_id;

ALTER TABLE tarefas
DROP COLUMN projeto_id,
DROP COLUMN produto_id,
ADD COLUMN projeto_produto_id BIGINT NOT NULL,
ADD CONSTRAINT fk_tarefas_projeto_produto_id
    FOREIGN KEY (projeto_produto_id)
    REFERENCES projetos_produtos(id);

ALTER TABLE projetos_produtos
DROP CONSTRAINT pk_projetos_produtos;

ALTER TABLE projetos_produtos
    RENAME CONSTRAINT uq_projetos_produtos_id TO pk_projetos_produtos;