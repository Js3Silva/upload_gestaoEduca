package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.configuracao_email.ConfiguracaoEmailRequestDTO;
import com.educacidades.core_api.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configuracao-email")
public class ConfiguracaoEmailController {

    private final EmailService service;

    public ConfiguracaoEmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid ConfiguracaoEmailRequestDTO dto) {
        service.salvar(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
