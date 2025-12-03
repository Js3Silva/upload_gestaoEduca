package com.educacidades.core_api.dto.produtoProjeto;

import java.time.LocalDate;

import com.educacidades.core_api.models.IMappable;
import com.educacidades.core_api.models.ProdutoProjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoProjetoRequestDTO(
    @NotNull(message = "O produto é obrigatório")
    Long idProduto,
    @NotNull(message = "A prioridade é obrigatória")
    int prioridadeValue,
    @NotNull(message = "A data de início prevista é obrigatória")
    LocalDate previsaoInicio,
    @NotNull(message = "A data de término prevista é obrigatória")
    LocalDate previsaoTermino
) implements IMappable<ProdutoProjeto> {
} 
