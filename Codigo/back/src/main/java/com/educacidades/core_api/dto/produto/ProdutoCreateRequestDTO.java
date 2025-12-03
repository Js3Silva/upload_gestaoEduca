package com.educacidades.core_api.dto.produto;

import com.educacidades.core_api.dto.atividade.AtividadeRequestDTO;
import com.educacidades.core_api.models.IMappable;
import com.educacidades.core_api.models.Produto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ProdutoCreateRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "A descricão é obrigatória")
        String descricao,
        List<@Valid AtividadeRequestDTO> atividades
) implements IMappable<Produto> {}

