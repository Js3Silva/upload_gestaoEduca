package com.educacidades.core_api.dto.projeto;

import com.educacidades.core_api.models.IMappable;
import com.educacidades.core_api.models.Projeto;

import jakarta.validation.constraints.NotBlank;

public record ProjetoPutRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    String nome,
    @NotBlank(message = "O objetivo é obrigatório")
    String objetivo
) implements IMappable<Projeto> {}
