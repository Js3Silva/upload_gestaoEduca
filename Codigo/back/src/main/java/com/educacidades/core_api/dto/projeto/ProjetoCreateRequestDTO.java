package com.educacidades.core_api.dto.projeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProjetoCreateRequestDTO(@NotBlank(message = "O nome é obrigatório.")
                                      String nome,
                                      @NotBlank(message = "O nome é obrigatório.")
                                      String objetivo,
                                      @NotNull(message = "A data de início é obrigatória.")
                                      LocalDate inicio,
                                      @NotNull(message = "A data de término é obrigatória.")
                                      LocalDate termino,
                                      @NotNull(message = "O ID do cliente é obrigatório.")
                                      Long idCliente) {}
