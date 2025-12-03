package com.educacidades.core_api.dto.projeto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.educacidades.core_api.dto.cliente.ClienteDTO;
import com.educacidades.core_api.models.Projeto;

public record ProjetoResponseDTO (Long id, 
                                  String nome,
                                  String objetivo,
                                  LocalDate inicio,
                                  LocalDate termino,
                                  String status,
                                  OffsetDateTime dataCriacao,
                                  ClienteDTO cliente) { }
