package com.educacidades.core_api.controllers;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacidades.core_api.dto.comentario.ComentarioRequestDTO;
import com.educacidades.core_api.dto.comentario.ComentarioResponseDTO;
import com.educacidades.core_api.services.ComentarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comentario")
public class ComentarioController {
    
    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ComentarioResponseDTO>> getComentariosByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(comentarioService.findByUsuario(usuarioId));
    }

    @GetMapping("/tarefa/{tarefaId}")
    public ResponseEntity<List<ComentarioResponseDTO>> getComentariosByTarefa(@PathVariable Long tarefaId) {
        return ResponseEntity.ok(comentarioService.findByTarefa(tarefaId));
    }

    @GetMapping("/projeto/{id}/comentarios")
    public ResponseEntity<List<ComentarioResponseDTO>> listComentariosProjeto(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.findByProjeto(id));
    }

    @GetMapping("/tarefa/{id}/comentarios")
    public ResponseEntity<List<ComentarioResponseDTO>> listComentariosTarefa(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.findByTarefa(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponseDTO> getComentarioById(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> create(@Valid @RequestBody ComentarioRequestDTO dto) {
        ComentarioResponseDTO novo = comentarioService.comentar(dto);
        return ResponseEntity.ok(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ComentarioRequestDTO dto) {
        comentarioService.atualizarComentario(id, dto);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ComentarioResponseDTO> alterarVisibilidade(@PathVariable Long id) {
        ComentarioResponseDTO dto = comentarioService.alterarVisibilidadeComentario(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comentarioService.deletarComentario(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<ComentarioResponseDTO>> getComentariosByProjeto(@PathVariable Long projetoId) {
        return ResponseEntity.ok(comentarioService.findByProjeto(projetoId));
    }

}
