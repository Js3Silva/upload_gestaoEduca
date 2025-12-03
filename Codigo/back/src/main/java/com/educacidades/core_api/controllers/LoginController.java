package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.login.LoginRequestDTO;
import com.educacidades.core_api.dto.login.LoginResponseDTO;
import com.educacidades.core_api.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.login(dto));
    }
}
