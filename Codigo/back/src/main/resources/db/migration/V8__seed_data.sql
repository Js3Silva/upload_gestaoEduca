-- Script de Seed Data para Popular o Banco de Dados
-- Este script popula todas as tabelas com dados de exemplo para testes e desenvolvimento
-- IMPORTANTE: Execute apenas em ambiente de desenvolvimento/teste

-- ============================================
-- 1. USUÁRIOS
-- ============================================
-- Senha padrão para todos: "123456" (hash BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy)
-- Perfis: 0=ADMIN, 1=BASICO, 2=EXTERNO
-- Status: 0=ATIVO, 1=INATIVO
-- NOTA: O admin padrão já existe (criado pelo InicializadorDados), então começamos do ID 2

INSERT INTO usuarios (nome, email, login, senha, perfil, status, data_criacao) VALUES
-- Administradores (se não existir o admin padrão, estes serão criados)
('Maria Santos', 'maria.santos@educacidades.org', 'maria.santos', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 0, 0, CURRENT_TIMESTAMP),

-- Usuários Básicos
('Pedro Oliveira', 'pedro.oliveira@educacidades.org', 'pedro.oliveira', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1, 0, CURRENT_TIMESTAMP),
('Ana Costa', 'ana.costa@educacidades.org', 'ana.costa', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1, 0, CURRENT_TIMESTAMP),
('Carlos Ferreira', 'carlos.ferreira@educacidades.org', 'carlos.ferreira', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1, 0, CURRENT_TIMESTAMP),
('Juliana Alves', 'juliana.alves@educacidades.org', 'juliana.alves', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1, 0, CURRENT_TIMESTAMP),
('Roberto Lima', 'roberto.lima@educacidades.org', 'roberto.lima', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1, 0, CURRENT_TIMESTAMP),

-- Usuários Externos (Clientes)
('Prefeitura Municipal de Vitória', 'contato@vitoria.es.gov.br', '12345678000190', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2, 0, CURRENT_TIMESTAMP),
('Secretaria de Educação de Vila Velha', 'contato@educacao.vilavelha.es.gov.br', '12345678000271', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2, 0, CURRENT_TIMESTAMP),
('Instituto de Ensino Superior de Cariacica', 'contato@iescariacica.edu.br', '12345678000352', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2, 0, CURRENT_TIMESTAMP),
('Fundação Municipal de Educação de Serra', 'contato@fmes.serra.es.gov.br', '12345678000433', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2, 0, CURRENT_TIMESTAMP),
('Prefeitura Municipal de Cachoeiro', 'contato@cachoeiro.es.gov.br', '12345678000514', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2, 0, CURRENT_TIMESTAMP);

-- ============================================
-- 2. CLIENTES
-- ============================================
-- Usando subquery para buscar o ID do usuário pelo login
INSERT INTO clientes (codigo, razao_social, cnpj, email, cidade, estado, usuario_id, data_criacao) VALUES
                                                                                                       (10001, 'Prefeitura Municipal de Vitória', '12345678000190', 'contato@vitoria.es.gov.br', 'Vitória', 'ES', (SELECT id FROM usuarios WHERE login = 'pref.vitoria'), CURRENT_TIMESTAMP),
                                                                                                       (10002, 'Secretaria de Educação de Vila Velha', '12345678000271', 'contato@educacao.vilavelha.es.gov.br', 'Vila Velha', 'ES', (SELECT id FROM usuarios WHERE login = 'educ.vilavelha'), CURRENT_TIMESTAMP),
                                                                                                       (10003, 'Instituto de Ensino Superior de Cariacica', '12345678000352', 'contato@iescariacica.edu.br', 'Cariacica', 'ES', (SELECT id FROM usuarios WHERE login = 'ies.cariacica'), CURRENT_TIMESTAMP),
                                                                                                       (10004, 'Fundação Municipal de Educação de Serra', '12345678000433', 'contato@fmes.serra.es.gov.br', 'Serra', 'ES', (SELECT id FROM usuarios WHERE login = 'fmes.serra'), CURRENT_TIMESTAMP),
                                                                                                       (10005, 'Prefeitura Municipal de Cachoeiro de Itapemirim', '12345678000514', 'contato@cachoeiro.es.gov.br', 'Cachoeiro de Itapemirim', 'ES', (SELECT id FROM usuarios WHERE login = 'pref.cachoeiro'), CURRENT_TIMESTAMP);

-- ============================================
-- 3. PRODUTOS
-- ============================================
INSERT INTO produtos (codigo, nome, descricao, data_criacao) VALUES
                                                                 (10001, 'Sistema de Gestão Escolar', 'Plataforma completa para gestão de escolas, incluindo matrículas, frequência, notas e relatórios', CURRENT_TIMESTAMP),
                                                                 (10002, 'Portal do Aluno', 'Sistema web e mobile para alunos acessarem notas, frequência, materiais e comunicados', CURRENT_TIMESTAMP),
                                                                 (10003, 'Sistema de Avaliação', 'Ferramenta para criação, aplicação e correção de avaliações online', CURRENT_TIMESTAMP),
                                                                 (10004, 'Biblioteca Digital', 'Plataforma de acesso a livros, artigos e materiais educacionais digitais', CURRENT_TIMESTAMP),
                                                                 (10005, 'Plataforma de EAD', 'Ambiente virtual de aprendizagem para cursos online e híbridos', CURRENT_TIMESTAMP);

-- ============================================
-- 4. ATIVIDADES (Templates de Tarefas)
-- ============================================
-- Prioridade: 0=BAIXA, 1=MEDIA, 2=ALTA
INSERT INTO atividades (produto_id, nome, descricao, prioridade, data_criacao) VALUES
                                                                                   (1, 'Análise de Requisitos', 'Levantamento e documentação dos requisitos funcionais e não funcionais', 2, CURRENT_TIMESTAMP),
                                                                                   (1, 'Modelagem de Dados', 'Criação do modelo de dados e estrutura do banco de dados', 2, CURRENT_TIMESTAMP),
                                                                                   (1, 'Desenvolvimento Backend', 'Implementação da API e lógica de negócio', 1, CURRENT_TIMESTAMP),
                                                                                   (1, 'Desenvolvimento Frontend', 'Criação da interface do usuário', 1, CURRENT_TIMESTAMP),
                                                                                   (2, 'Design de Interface', 'Criação do design e protótipos da interface', 2, CURRENT_TIMESTAMP),
                                                                                   (2, 'Desenvolvimento Mobile', 'Implementação da versão mobile do portal', 2, CURRENT_TIMESTAMP),
                                                                                   (3, 'Módulo de Criação de Questões', 'Desenvolvimento do módulo para criação de questões', 2, CURRENT_TIMESTAMP),
                                                                                   (3, 'Módulo de Aplicação', 'Sistema para aplicação de avaliações online', 2, CURRENT_TIMESTAMP);

-- ============================================
-- 5. PROJETOS
-- ============================================
-- Status: 0=NAO_INICIADO, 1=EM_ANDAMENTO, 2=PAUSADO, 3=CONCLUIDO
-- Projetos distribuídos ao longo do ano atual para testar estatísticas mensais
INSERT INTO projetos (cliente_id, codigo, nome, objetivo, inicio, termino, status, data_criacao) VALUES
-- Projetos Finalizados (status 3) - distribuídos ao longo do ano
(1, 10001, 'Implementação Sistema Gestão Escolar - Vitória', 'Implementar sistema completo de gestão escolar na rede municipal de Vitória', '2025-01-15', '2025-03-30', 3, '2025-01-10 10:00:00-03'),
(2, 10002, 'Portal do Aluno - Vila Velha', 'Desenvolver portal do aluno para acesso a notas e frequência', '2025-02-01', '2025-04-15', 3, '2025-01-25 14:00:00-03'),
(3, 10003, 'Sistema de Avaliação - Cariacica', 'Implementar sistema de avaliações online', '2025-03-10', '2025-05-20', 3, '2025-03-05 09:00:00-03'),
(4, 10004, 'Biblioteca Digital - Serra', 'Criar biblioteca digital para acesso a materiais educacionais', '2025-04-01', '2025-06-10', 3, '2025-03-28 11:00:00-03'),

-- Projetos Em Andamento (status 1)
(5, 10005, 'Plataforma EAD - Cachoeiro', 'Implementar plataforma de ensino a distância', '2025-08-01', '2025-11-30', 1, '2025-07-25 14:00:00-03'),
(1, 10006, 'Sistema Gestão Escolar - Vitória Fase 2', 'Expansão do sistema de gestão escolar', '2025-09-15', '2025-12-20', 1, '2025-09-10 09:00:00-03'),
(2, 10007, 'Portal do Aluno - Vila Velha Fase 2', 'Melhorias e novas funcionalidades do portal', '2025-10-01', '2026-01-15', 1, '2025-09-28 11:00:00-03'),

-- Projetos Não Iniciados (status 0)
(3, 10008, 'Sistema de Avaliação - Cariacica Fase 2', 'Expansão do sistema de avaliações', '2026-01-15', '2026-04-30', 0, CURRENT_TIMESTAMP),
(4, 10009, 'Biblioteca Digital - Serra Fase 2', 'Melhorias na biblioteca digital', '2026-02-01', '2026-05-15', 0, CURRENT_TIMESTAMP),

-- Projetos Pausados (status 2)
(5, 10010, 'Plataforma EAD - Cachoeiro Fase 2', 'Expansão da plataforma EAD - Projeto pausado temporariamente', '2025-09-10', '2025-11-20', 2, '2025-09-05 14:00:00-03');

-- ============================================
-- 6. PROJETOS_PRODUTOS (Associação Projeto x Produto)
-- ============================================
-- Prioridade: 0=BAIXA, 1=MEDIA, 2=ALTA
INSERT INTO projetos_produtos (projeto_id, produto_id, prioridade, previsao_inicio, previsao_termino) VALUES
-- Projeto 1 (Finalizado)
(1, 1, 2, '2025-01-15', '2025-03-30'),
(1, 2, 1, '2025-02-01', '2025-03-15'),

-- Projeto 2 (Finalizado)
(2, 2, 2, '2025-02-01', '2025-04-15'),

-- Projeto 3 (Finalizado)
(3, 3, 2, '2025-03-10', '2025-05-20'),

-- Projeto 4 (Finalizado)
(4, 4, 2, '2025-04-01', '2025-06-10'),

-- Projeto 5 (Em Andamento)
(5, 5, 2, '2025-08-01', '2025-11-30'),

-- Projeto 6 (Em Andamento)
(6, 1, 2, '2025-09-15', '2025-12-20'),

-- Projeto 7 (Em Andamento)
(7, 2, 2, '2025-10-01', '2026-01-15'),

-- Projeto 8 (Não Iniciado)
(8, 3, 2, '2026-01-15', '2026-04-30'),

-- Projeto 9 (Não Iniciado)
(9, 4, 2, '2026-02-01', '2026-05-15'),

-- Projeto 10 (Pausado)
(10, 5, 2, '2025-09-10', '2025-11-20');

-- ============================================
-- 7. PROJETOS_USUARIOS (Responsáveis pelos Projetos)
-- ============================================
-- Usando subqueries para buscar IDs pelos logins
INSERT INTO projetos_usuarios (projeto_id, usuario_id) VALUES
-- Projeto 1
(1, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira')),
(1, (SELECT id FROM usuarios WHERE login = 'ana.costa')),

-- Projeto 2
(2, (SELECT id FROM usuarios WHERE login = 'carlos.ferreira')),

-- Projeto 3
(3, (SELECT id FROM usuarios WHERE login = 'juliana.alves')),

-- Projeto 4
(4, (SELECT id FROM usuarios WHERE login = 'roberto.lima')),

-- Projeto 5
(5, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira')),
(5, (SELECT id FROM usuarios WHERE login = 'ana.costa')),

-- Projeto 6
(6, (SELECT id FROM usuarios WHERE login = 'carlos.ferreira')),

-- Projeto 7
(7, (SELECT id FROM usuarios WHERE login = 'juliana.alves')),

-- Projeto 8
(8, (SELECT id FROM usuarios WHERE login = 'roberto.lima')),

-- Projeto 9
(9, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira')),

-- Projeto 10
(10, (SELECT id FROM usuarios WHERE login = 'ana.costa'));

-- ============================================
-- 8. TAREFAS
-- ============================================
-- Status: 0=NAO_INICIADA, 1=EM_ANDAMENTO, 2=PAUSADA, 3=CONCLUIDA
-- Prioridade: 0=BAIXA, 1=MEDIA, 2=ALTA
-- planejada: true se veio de atividade, false se criada manualmente
INSERT INTO tarefas (projeto_produto_id, nome, descricao, url, prioridade, prazo, inicio, termino, status, planejada, atividade_id, responsavel_id, data_criacao) VALUES
-- Tarefas do Projeto 1 (Finalizado) - projeto_produto_id 1
(1, 'Análise de Requisitos', 'Levantamento completo dos requisitos do sistema', NULL, 2, '2025-02-15', '2025-01-20 09:00:00-03', '2025-02-10 17:00:00-03', 3, true, 1, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), '2025-01-15 10:00:00-03'),
(1, 'Modelagem de Dados', 'Criação do modelo de dados', NULL, 2, '2025-02-28', '2025-02-12 08:00:00-03', '2025-02-25 16:00:00-03', 3, true, 2, (SELECT id FROM usuarios WHERE login = 'ana.costa'), '2025-01-20 11:00:00-03'),
(1, 'Desenvolvimento Backend', 'Implementação da API', 'https://github.com/projeto1/backend', 1, '2025-03-15', '2025-02-26 09:00:00-03', '2025-03-12 18:00:00-03', 3, true, 3, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), '2025-02-15 14:00:00-03'),

-- Tarefas do Projeto 2 (Finalizado) - projeto_produto_id 3
(3, 'Design de Interface', 'Criação do design do portal', NULL, 2, '2025-03-01', '2025-02-05 10:00:00-03', '2025-02-25 15:00:00-03', 3, true, 5, (SELECT id FROM usuarios WHERE login = 'carlos.ferreira'), '2025-02-01 09:00:00-03'),
(3, 'Desenvolvimento Mobile', 'Implementação da versão mobile', 'https://github.com/projeto2/mobile', 2, '2025-03-20', '2025-02-26 08:00:00-03', '2025-03-15 17:00:00-03', 3, true, 6, (SELECT id FROM usuarios WHERE login = 'carlos.ferreira'), '2025-02-10 14:00:00-03'),

-- Tarefas do Projeto 5 (Em Andamento) - projeto_produto_id 5
(5, 'Análise de Requisitos EAD', 'Levantamento de requisitos do sistema EAD', NULL, 2, '2025-09-15', '2025-08-05 09:00:00-03', NULL, 1, false, NULL, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), '2025-08-01 10:00:00-03'),
(5, 'Modelagem de Dados EAD', 'Criação do modelo de dados', NULL, 2, '2025-10-01', '2025-09-16 08:00:00-03', NULL, 1, false, NULL, (SELECT id FROM usuarios WHERE login = 'ana.costa'), '2025-09-01 11:00:00-03'),
(5, 'Desenvolvimento Backend EAD', 'API do sistema EAD', 'https://github.com/projeto5/backend', 1, '2025-11-15', NULL, NULL, 0, false, NULL, (SELECT id FROM usuarios WHERE login = 'carlos.ferreira'), '2025-10-01 14:00:00-03'),

-- Tarefas Atrasadas (prazo no passado, não concluídas)
(5, 'Documentação Técnica', 'Elaboração da documentação', NULL, 0, '2025-10-20', NULL, NULL, 0, true, 4, (SELECT id FROM usuarios WHERE login = 'ana.costa'), '2025-10-01 09:00:00-03'),

-- Tarefas Vencendo em 7 dias
(6, 'Análise de Requisitos', 'Levantamento de requisitos do sistema', NULL, 2, (CURRENT_DATE + INTERVAL '3 days')::DATE, NULL, NULL, 0, false, NULL, (SELECT id FROM usuarios WHERE login = 'juliana.alves'), CURRENT_TIMESTAMP),
(7, 'Design da Interface', 'Criação do design do sistema', NULL, 2, (CURRENT_DATE + INTERVAL '5 days')::DATE, NULL, NULL, 0, false, NULL, (SELECT id FROM usuarios WHERE login = 'roberto.lima'), CURRENT_TIMESTAMP),

-- Tarefas Pausadas
(8, 'Módulo de Criação de Questões', 'Desenvolvimento do módulo', NULL, 2, '2025-12-15', '2025-11-20 09:00:00-03', NULL, 2, true, 7, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), '2025-11-15 10:00:00-03');

-- ============================================
-- 9. COMENTÁRIOS
-- ============================================
-- Visibilidade: 0=OCULTO, 1=VISIVEL
INSERT INTO comentarios (projeto_id, tarefa_id, usuario_id, texto, visibilidade, data_criacao, data_atualizacao) VALUES
-- Comentários em Projetos
(1, NULL, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), 'Projeto iniciado com sucesso. Equipe alinhada e pronta para começar.', 1, '2025-01-15 10:00:00-03', NULL),
(1, NULL, (SELECT id FROM usuarios WHERE login = 'ana.costa'), 'Primeira reunião de alinhamento realizada. Todos os requisitos foram validados.', 1, '2025-01-20 14:00:00-03', NULL),
(5, NULL, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), 'Reunião de planejamento realizada. Cronograma definido.', 1, '2025-08-05 11:00:00-03', NULL),
(5, NULL, (SELECT id FROM usuarios WHERE login = 'ana.costa'), 'Aguardando aprovação do cliente para continuar.', 1, '2025-09-15 15:00:00-03', NULL),
(6, NULL, (SELECT id FROM usuarios WHERE login = 'carlos.ferreira'), 'Projeto iniciado. Equipe formada e alinhada.', 1, '2025-09-10 08:00:00-03', NULL),

-- Comentários em Tarefas
(NULL, 1, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), 'Requisitos levantados e documentados. Aguardando validação.', 1, '2025-01-25 11:00:00-03', NULL),
(NULL, 1, (SELECT id FROM usuarios WHERE login = 'ana.costa'), 'Documentação aprovada. Podemos seguir para próxima etapa.', 1, '2025-02-05 15:00:00-03', NULL),
(NULL, 2, (SELECT id FROM usuarios WHERE login = 'ana.costa'), 'Modelo de dados criado e revisado. Pronto para implementação.', 1, '2025-02-20 09:00:00-03', NULL),
(NULL, 5, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), 'Análise de requisitos EAD concluída. Documento disponível no repositório.', 1, '2025-09-10 11:00:00-03', NULL),
(NULL, 6, (SELECT id FROM usuarios WHERE login = 'ana.costa'), 'Modelagem em andamento. Previsão de conclusão na próxima semana.', 1, '2025-09-20 09:00:00-03', NULL),
(NULL, 9, (SELECT id FROM usuarios WHERE login = 'juliana.alves'), 'Tarefa atrasada. Necessário replanejamento.', 1, CURRENT_TIMESTAMP, NULL),
(NULL, 11, (SELECT id FROM usuarios WHERE login = 'pedro.oliveira'), 'Tarefa pausada temporariamente. Aguardando recursos.', 1, '2025-11-25 10:00:00-03', NULL);
