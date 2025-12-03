package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.tarefa.TarefaCreateRequestDTO;
import com.educacidades.core_api.dto.tarefa.TarefaDTO;
import com.educacidades.core_api.dto.tarefa.TarefaStatusUpdateRequestDTO;
import com.educacidades.core_api.dto.tarefa.TarefaUpdateRequestDTO;
import com.educacidades.core_api.models.Atividade;
import com.educacidades.core_api.models.ProdutoProjeto;
import com.educacidades.core_api.models.Tarefa;
import com.educacidades.core_api.models.Usuario;
import com.educacidades.core_api.repositories.AtividadeRepository;
import com.educacidades.core_api.repositories.ProdutoProjetoRepository;
import com.educacidades.core_api.repositories.TarefaRepository;
import com.educacidades.core_api.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private TarefaRepository repository;
    private ProdutoProjetoRepository produtoProjetoRepository;
    private UsuarioRepository usuarioRepository;
    private AtividadeRepository atividadeRepository;

    public TarefaService(TarefaRepository repository, ProdutoProjetoRepository produtoProjetoRepository,
            UsuarioRepository usuarioRepository, AtividadeRepository atividadeRepository,
            TarefaRepository tarefaRepository) {
        this.repository = repository;
        this.produtoProjetoRepository = produtoProjetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
        this.tarefaRepository = tarefaRepository;
    }

    private Tarefa buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));
    }

    private Usuario buscarResponsavel(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    @Transactional
    public void criar(TarefaCreateRequestDTO dto) {
        ProdutoProjeto produtoProjeto = produtoProjetoRepository.findById(dto.idProjetoProduto())
                .orElseThrow(() -> new RuntimeException("Produto não está vinculado ao projeto."));
        Usuario responsavel = buscarResponsavel(dto.idResponsavel());

        Tarefa tarefa = new Tarefa(produtoProjeto, dto.nome(), dto.descricao(), dto.url(), dto.prioridade(),
                dto.prazo(), responsavel);
        repository.save(tarefa);
    }

    public TarefaDTO buscarPorId(Long id) {
        return buscar(id).toDto();
    }

    @Transactional
    public void atualizar(Long id, TarefaUpdateRequestDTO dto) {
        Tarefa tarefa = buscar(id);

        tarefa.setNome(dto.nome());
        tarefa.setDescricao(dto.descricao());
        tarefa.setUrl(dto.url());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setPrazo(dto.prazo());
        tarefa.atualizarStatus(dto.status());

        if (!Objects.equals(tarefa.getResponsavel().getId(), dto.idResponsavel())) {
            Usuario responsavel = buscarResponsavel(dto.idResponsavel());
            tarefa.setResponsavel(responsavel);
        }

        repository.save(tarefa);
    }

    @Transactional
    public void excluir(Long id) {
        Tarefa tarefa = buscar(id);
        repository.delete(tarefa);
    }

    public List<TarefaDTO> buscarPorProduto(Long id) {
        List<Tarefa> tarefas = repository.findAllByProdutoProjeto_Id(id);
        return tarefas.stream().map(Tarefa::toDto).toList();
    }

    @Transactional
    public void atualizarStatus(Long id, TarefaStatusUpdateRequestDTO dto) {
        Tarefa tarefa = buscar(id);
        tarefa.atualizarStatus(dto.status());
        repository.save(tarefa);
    }

    @Transactional
    public void criarAtividade(Long id) {
        Tarefa tarefa = buscar(id);
        Atividade atividade = new Atividade(tarefa);

        tarefa.setAtividade(atividade);

        atividadeRepository.save(atividade);
        tarefaRepository.save(tarefa);
    }

    public List<TarefaDTO> buscarTarefasComPrazoProximo() {
        List<Tarefa> tarefas = tarefaRepository.findTarefasComPrazoProximo(LocalDate.now().plusDays(7));
        return tarefas.stream()
                .map(Tarefa::toDto)
                .toList();
    }
}
