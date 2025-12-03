package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.tarefa.TarefaCreateRequestDTO;
import com.educacidades.core_api.dto.tarefa.TarefaDTO;
import com.educacidades.core_api.dto.tarefa.TarefaStatusUpdateRequestDTO;
import com.educacidades.core_api.dto.tarefa.TarefaUpdateRequestDTO;
import com.educacidades.core_api.services.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid TarefaCreateRequestDTO dto) {
        service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscar(@PathVariable Long id) {
        TarefaDTO resposta = service.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody @Valid TarefaUpdateRequestDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<List<TarefaDTO>> buscarPorProjetoProduto(@PathVariable Long id) {
        List<TarefaDTO> resposta = service.buscarPorProduto(id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody @Valid TarefaStatusUpdateRequestDTO dto) {
        service.atualizarStatus(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/criar-atividade")
    public ResponseEntity<Void> criarAtividade(@PathVariable Long id) {
        service.criarAtividade(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
