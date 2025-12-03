package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.token.RenovarTokenRequestDTO;
import com.educacidades.core_api.dto.token.TokenDTO;
import com.educacidades.core_api.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService service;

    public TokenController(TokenService service) {
        this.service = service;
    }

    @PostMapping("/renovar")
    public ResponseEntity<TokenDTO> renovarToken(@RequestBody RenovarTokenRequestDTO dto) {
        TokenDTO response = service.renovarTokenAcesso(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
