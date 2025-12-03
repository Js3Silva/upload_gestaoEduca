package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.produto.ProdutoCreateRequestDTO;
import com.educacidades.core_api.dto.produto.ProdutoPutRequestDTO;
import com.educacidades.core_api.mapper.AtividadeMapper;
import com.educacidades.core_api.models.Atividade;
import com.educacidades.core_api.models.Produto;
import com.educacidades.core_api.repositories.AtividadeRepository;
import com.educacidades.core_api.repositories.CodigoRepository;
import com.educacidades.core_api.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final CodigoRepository codigoRepository;
    private final ProdutoRepository produtoRepository;
    private final AtividadeRepository atividadeRepository;
    private final AtividadeMapper atividadeMapper;

    public ProdutoService(CodigoRepository codigoRepository,
                          ProdutoRepository produtoRepository,
                          AtividadeRepository atividadeRepository,
                          AtividadeMapper atividadeMapper) {
        this.codigoRepository = codigoRepository;
        this.produtoRepository = produtoRepository;
        this.atividadeRepository = atividadeRepository;
        this.atividadeMapper = atividadeMapper;
    }

    @Transactional
    public Produto create(ProdutoCreateRequestDTO produto) {
        Produto produtoEntity = new Produto(
                produto.nome(),
                produto.descricao()
        );

        if (produto.atividades() != null) {
            produto.atividades().forEach(atividadeRequestDTO -> {
                produtoEntity.addAtividade(atividadeMapper.atividadeRequestDtoToAtividade(atividadeRequestDTO));
            });
        }
        produtoEntity.setCodigo(codigoRepository.getCodigo());

        try {
            return produtoRepository.save(produtoEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar produto: " + e);
        }
    }

    @Transactional
    public Produto update(Long produtoToUpdateId, ProdutoPutRequestDTO put) {
        Optional<Produto> produtoToUpdateOptional = produtoRepository.findById(produtoToUpdateId);
        if (produtoToUpdateOptional.isEmpty()) {
            throw new RuntimeException("Produto não encontrado.");
        }
        Produto produtoToUpdate = produtoToUpdateOptional.get();
        produtoToUpdate.setNome(put.nome());
        produtoToUpdate.setDescricao(put.descricao());
        return produtoRepository.save(produtoToUpdate);
    }

    @Transactional
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public void removeAtividade(Long produtoId, Long atividadeId) {
        Optional<Atividade> atividadeOptional = atividadeRepository.findById(atividadeId);
        if (atividadeOptional.isEmpty())
            throw new RuntimeException("Atividade não encontrada.");
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        if (produtoOptional.isEmpty())
            throw new RuntimeException("Produto não encontrado");
        Produto produto = produtoOptional.get();
        Atividade atividade = atividadeOptional.get();
        produto.removeAtividade(atividade);
        produtoRepository.save(produto);
    }

    @Transactional
    public void delete(Long id) {
        Produto produtoFound = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado.", Produto.class));
        produtoRepository.delete(produtoFound);
    }

}
