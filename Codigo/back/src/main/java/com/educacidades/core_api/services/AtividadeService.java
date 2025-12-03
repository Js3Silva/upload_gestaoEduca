package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.atividade.AtividadeRequestDTO;
import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.models.Atividade;
import com.educacidades.core_api.models.Produto;
import com.educacidades.core_api.repositories.AtividadeRepository;
import com.educacidades.core_api.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final ProdutoRepository produtoRepository;

    public AtividadeService(AtividadeRepository atividadeRepository,
            ProdutoRepository produtoRepository) {
        this.atividadeRepository = atividadeRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Atividade addToProduto(Long produtoId, AtividadeRequestDTO atividadeRequestDTO) {
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        if (produtoOptional.isEmpty())
            throw new RuntimeException("Produto não encotrado");
        Produto produto = produtoOptional.get();

        Atividade atividade = Atividade.builder()
                .nome(atividadeRequestDTO.nome())
                .descricao(atividadeRequestDTO.descricao())
                .prioridade(Prioridade.values()[atividadeRequestDTO.prioridade()])
                .build();
        produto.addAtividade(atividade);
        return atividadeRepository.save(atividade);
    }

    public List<Atividade> findAtividadesByProdutoId(Long produtoId) {
        return atividadeRepository.findAtividadesByProdutoId(produtoId);
    }

    public Optional<Atividade> findById(Long atividadeId) {
        return atividadeRepository.findById(atividadeId);
    }

    @Transactional
    public Atividade update(Long atividadeId, AtividadeRequestDTO atividadeRequest) {
        Optional<Atividade> atividadeOptional = atividadeRepository.findById(atividadeId);
        if (atividadeOptional.isEmpty())
            throw new RuntimeException("Atividade não encontrada");
        Atividade atividade = atividadeOptional.get();

        atividade.setNome(atividadeRequest.nome());
        atividade.setDescricao(atividadeRequest.descricao());
        atividade.setPrioridade(Prioridade.values()[atividadeRequest.prioridade()]);
        return atividadeRepository.save(atividade);
    }

}
