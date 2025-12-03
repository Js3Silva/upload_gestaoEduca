package com.educacidades.core_api.controllers;

import com.educacidades.core_api.services.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> gerar(@PathVariable Long id) {
        byte[] pdf = service.gerar(id);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=Análise final de produto.pdf")
                .body(pdf);
    }
}
