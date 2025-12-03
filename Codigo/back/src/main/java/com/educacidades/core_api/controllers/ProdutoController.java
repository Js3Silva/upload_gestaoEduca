package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.atividade.AtividadeRequestDTO;
import com.educacidades.core_api.dto.atividade.AtividadeResponseDTO;
import com.educacidades.core_api.dto.produto.ProdutoCreateRequestDTO;
import com.educacidades.core_api.dto.produto.ProdutoLightResponseDTO;
import com.educacidades.core_api.dto.produto.ProdutoPutRequestDTO;
import com.educacidades.core_api.dto.produto.ProdutoResponseDTO;
import com.educacidades.core_api.mapper.AtividadeMapper;
import com.educacidades.core_api.mapper.ProdutoMapper;
import com.educacidades.core_api.models.Atividade;
import com.educacidades.core_api.models.Produto;
import com.educacidades.core_api.services.AtividadeService;
import com.educacidades.core_api.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {

    private final AtividadeMapper atividadeMapper;
    private final ProdutoMapper produtoMapper;
    private final AtividadeService atividadeService;
    private final ProdutoService produtoService;

    public ProdutoController(AtividadeMapper atividadeMapper,
                             ProdutoMapper produtoMapper,
                             AtividadeService atividadeService,
                             ProdutoService produtoService) {
        this.atividadeMapper = atividadeMapper;
        this.produtoMapper = produtoMapper;
        this.atividadeService = atividadeService;
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> postProduto(@RequestBody ProdutoCreateRequestDTO produtoCreateRequestDTO) {
        Produto produtoSalvo = produtoService.create(produtoCreateRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produtoSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(produtoMapper.produtoToProdutoResponseDto(produtoSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> putProduto(@PathVariable Long id,
                                                         @RequestBody @Valid ProdutoPutRequestDTO produtoPutRequestDTO) {
        return ResponseEntity.ok()
                .body(produtoMapper
                        .produtoToProdutoResponseDto(produtoService.update(id, produtoPutRequestDTO)));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoLightResponseDTO>> getProdutos() {
        return ResponseEntity.ok()
                .body(produtoService.findAll()
                        .stream()
                        .map(produtoMapper::produtoToProdutoLightResponseDto)
                        .toList());
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> getProduto(@PathVariable Long produtoId) {
        Optional<Produto> produtoOptional = produtoService.findById(produtoId);
        return produtoOptional.map(
                        produto -> ResponseEntity.ok()
                                .body(produtoMapper
                                        .produtoToProdutoResponseDto(produto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{produtoId}/atividades")
    public ResponseEntity<List<AtividadeResponseDTO>> getAtividades(@PathVariable Long produtoId) {
        return ResponseEntity.ok().body(atividadeService.findAtividadesByProdutoId(produtoId)
                .stream()
                .map(atividadeMapper::atividadeToAtividadeResponseDto)
                .toList());
    }

    @GetMapping("/{produtoId}/atividades/{atividadeId}")
    public ResponseEntity<AtividadeResponseDTO> getAtividade(@PathVariable Long atividadeId) {
        Optional<Atividade> atividadeOptional = atividadeService.findById(atividadeId);
        return atividadeOptional.map(
                        atividade -> ResponseEntity.ok()
                                .body(atividadeMapper.
                                        atividadeToAtividadeResponseDto(atividade)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{produtoId}/atividades")
    public ResponseEntity<AtividadeResponseDTO> postAtividade(@PathVariable Long produtoId,
                                                              @RequestBody @Valid AtividadeRequestDTO atividadeRequestDTO) {

        Atividade atividadeSalva = atividadeService.addToProduto(produtoId, atividadeRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(atividadeSalva.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(atividadeMapper
                        .atividadeToAtividadeResponseDto(atividadeSalva));
    }


    @PutMapping("/{produtoId}/atividades/{atividadeId}")
    public ResponseEntity<AtividadeResponseDTO> putAtividade(@PathVariable Long atividadeId,
                                                             @RequestBody AtividadeRequestDTO atividadeRequestDTO) {
        return ResponseEntity.ok()
                .body(atividadeMapper
                        .atividadeToAtividadeResponseDto(atividadeService.update(atividadeId, atividadeRequestDTO)));
    }

    @DeleteMapping("/{produtoId}/atividades/{atividadeId}")
    public ResponseEntity<Void> deleteAtividade(@PathVariable Long produtoId,
                                                @PathVariable Long atividadeId) {
        produtoService.removeAtividade(produtoId, atividadeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
