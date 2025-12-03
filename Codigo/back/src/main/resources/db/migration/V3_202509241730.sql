ALTER TABLE clientes
ADD COLUMN usuario_id BIGINT NULL;

ALTER TABLE clientes
ADD CONSTRAINT fk_clientes_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id);