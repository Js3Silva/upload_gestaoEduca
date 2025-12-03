package com.educacidades.core_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoResponseDTO;
import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.mapper.ComentarioMapper;
import com.educacidades.core_api.mapper.ProdutoProjetoMapper;
import com.educacidades.core_api.mapper.ProjetoMapper;
import com.educacidades.core_api.mapper.UsuarioMapper;
import com.educacidades.core_api.services.ComentarioService;
import com.educacidades.core_api.services.ProdutoProjetoService;
import com.educacidades.core_api.services.ProjetoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/produto-projeto")
public class ProdutoProjetoController {
    private final ProdutoProjetoMapper produtoProjetoMapper;
    private final ProdutoProjetoService produtoProjetoService;

    public ProdutoProjetoController(ProdutoProjetoMapper produtoProjetoMapper,
                             ProdutoProjetoService produtoProjetoService) {
        this.produtoProjetoMapper = produtoProjetoMapper;
        this.produtoProjetoService = produtoProjetoService;
    }

    @GetMapping("/{projetoId}/produto/{produtoId}")
    public ResponseEntity<ProdutoProjetoResponseDTO> getProjetoById(@PathVariable Long projetoId, @PathVariable Long produtoId) {
        var produtoProjeto = produtoProjetoService.getProdutoProjetoById(projetoId, produtoId);
        
        return ResponseEntity.ok().body(produtoProjetoMapper.produtoProjetoToProdutoProjetoResponseDto(produtoProjeto));
    }
}
