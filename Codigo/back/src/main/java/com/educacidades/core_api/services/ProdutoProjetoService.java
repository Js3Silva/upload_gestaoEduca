package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoDTO;
import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoRequestDTO;
import com.educacidades.core_api.mapper.ProdutoProjetoMapper;
import com.educacidades.core_api.models.*;
import com.educacidades.core_api.repositories.ProdutoProjetoRepository;
import com.educacidades.core_api.repositories.ProdutoRepository;
import com.educacidades.core_api.repositories.ProjetoRepository;
import com.educacidades.core_api.repositories.TarefaRepository;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProdutoProjetoService {

    private final ProdutoProjetoRepository produtoProjetoRepository;
    private final ProjetoRepository projetoRepository;
    private final ProdutoRepository produtoRepository;
    private final TarefaRepository tarefaRepository;

    private final ProdutoProjetoMapper produtoProjetoMapper;

    public ProdutoProjetoService(ProdutoProjetoRepository produtoProjetoRepository,
                                 ProjetoRepository projetoRepository,
                                 ProdutoRepository produtoRepository,
                                 ProdutoProjetoMapper produtoProjetoMapper,
                                 TarefaRepository tarefaRepository) {
        this.produtoProjetoRepository = produtoProjetoRepository;
        this.projetoRepository = projetoRepository;
        this.produtoRepository = produtoRepository;
        this.produtoProjetoMapper = produtoProjetoMapper;
        this.tarefaRepository = tarefaRepository;
    }

    @Transactional
    public ProdutoProjeto addProdutoToProjeto(Long idProjeto, ProdutoProjetoRequestDTO produtoProjetoRequestDTO) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(idProjeto);
        if (projetoOptional.isEmpty())
            throw new RuntimeException("Projeto não encontrado");
        Projeto projeto = projetoOptional.get();

        Optional<Produto> produtoOptional = produtoRepository.findById(produtoProjetoRequestDTO.idProduto());
        if (produtoOptional.isEmpty())
            throw new RuntimeException("Produto não encontrado");

        ProdutoProjeto produtoProjeto = produtoProjetoMapper
                .produtoProjetoRequestDtoToProdutoProjeto(produtoOptional.get(), produtoProjetoRequestDTO);
        produtoProjeto.setProjeto(projeto);

        produtoProjetoRepository.save(produtoProjeto);

        for (Atividade atividade : produtoOptional.get().getAtividades()) {
            Tarefa tarefa = new Tarefa(produtoProjeto, atividade);
            tarefaRepository.save(tarefa);
        }

        return produtoProjeto;
    }

    @Transactional
    public ProdutoProjeto getProdutoProjetoById(Long projetoId, Long produtoId) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(projetoId);
        if (projetoOptional.isEmpty())
            throw new RuntimeException("Projeto não encontrado");

        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        if (produtoOptional.isEmpty())
            throw new RuntimeException("Produto não encontrado");

        ProdutoProjeto produtoProjeto = produtoProjetoRepository.findOne(projetoId, produtoId);

        return produtoProjeto;
    }

    @Transactional
    public void delete(Long id) {
        Optional<ProdutoProjeto> produtoProjetoOptional = produtoProjetoRepository.findById(id);

        if (!produtoProjetoOptional.isEmpty()) {
            produtoProjetoRepository.delete(produtoProjetoOptional.get());
        }

        /*if (produtoProjetoOptional.isEmpty())
            throw new RuntimeException("Produto não encontrado em projeto");
        ProdutoProjeto produtoProjeto = produtoProjetoOptional.get();

        Projeto projeto = projetoOptional.get();
        projeto.removeProdutoProjeto(produtoProjeto);
        projetoRepository.save(projeto);*/
    }

    public List<ProdutoProjetoDTO> buscarProdutosComPrazoProximo() {
        List<ProdutoProjeto> produto =  produtoProjetoRepository.findProdutosComPrazoProximo(LocalDate.now().plusDays(7));
        return produto.stream()
                .map(ProdutoProjeto::converteDTO)
                .toList();
    }

}