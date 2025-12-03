package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.usuario.UsuarioCreateRequestDTO;
import com.educacidades.core_api.dto.usuario.UsuarioDTO;
import com.educacidades.core_api.dto.usuario.UsuarioResumoDTO;
import com.educacidades.core_api.dto.usuario.UsuarioUpdateRequestDTO;
import com.educacidades.core_api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid UsuarioCreateRequestDTO dto) {
        service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateRequestDTO dto) {
        service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/resumo")
    public ResponseEntity<List<UsuarioResumoDTO>> buscarTodosResumo() {
        List<UsuarioResumoDTO> resposta = service.buscarTodosResumo();
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
