package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.comentarioAnexo.*;
import com.educacidades.core_api.services.ComentarioAnexoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios/anexos")
public class ComentarioAnexoController {

    private final ComentarioAnexoService service;

    public ComentarioAnexoController(ComentarioAnexoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ComentarioAnexoResponseDTO> uploadAnexo(@RequestBody ComentarioAnexoRequestDTO dto) {
        ComentarioAnexoResponseDTO response = service.salvarAnexo(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/comentario/{comentarioId}")
    public ResponseEntity<List<ComentarioAnexoResponseDTO>> listarPorComentario(@PathVariable Long comentarioId) {
        return ResponseEntity.ok(service.listarPorComentario(comentarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioAnexoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ComentarioAnexoResponseDTO(
                        id,
                        service.buscarPorId(id).getComentario().getId(),
                        service.buscarPorId(id).getNomeArquivo(),
                        service.buscarPorId(id).getTipoArquivo(),
                        service.buscarPorId(id).getTamanhoArquivo(),
                        service.buscarPorId(id).getDataCriacao().toString()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAnexo(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (service.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.excluirAnexo(id);
        return ResponseEntity.noContent().build();
}}
