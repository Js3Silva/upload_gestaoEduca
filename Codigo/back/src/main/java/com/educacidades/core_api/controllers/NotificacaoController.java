package com.educacidades.core_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoDTO;
import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.dto.tarefa.TarefaDTO;
import com.educacidades.core_api.services.ProdutoProjetoService;
import com.educacidades.core_api.services.ProjetoService;
import com.educacidades.core_api.services.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    private final TarefaService tarefaService;
    private final ProdutoProjetoService produtoService;
    private final ProjetoService projetoService;

    public NotificacaoController(
        TarefaService tarefaService,
        ProdutoProjetoService produtoService,
        ProjetoService projetoService
    ) {
        this.tarefaService = tarefaService;
        this.produtoService = produtoService;
        this.projetoService = projetoService;
    }

    @GetMapping("/tarefas")
    public ResponseEntity<List<TarefaDTO>> listarTarefasVencendo() {
        return ResponseEntity.ok(tarefaService.buscarTarefasComPrazoProximo());
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoProjetoDTO>> listarProdutosVencendo() {
        return ResponseEntity.ok(produtoService.buscarProdutosComPrazoProximo());
    }

    @GetMapping("/projetos")
    public ResponseEntity<List<ProjetoResponseDTO>> listarProjetosVencendo() {
        return ResponseEntity.ok(projetoService.buscarProjetosComPrazoProximo());
    }
}
