package com.educacidades.core_api.dto.produtoProjeto;

import java.time.LocalDate;

import com.educacidades.core_api.dto.produto.ProdutoResponseDTO;
import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.models.IMappable;
import com.educacidades.core_api.models.Produto;
import com.educacidades.core_api.models.ProdutoProjeto;
import org.springframework.format.annotation.DateTimeFormat;

public record ProdutoProjetoResponseDTO(
    Long id,
    ProdutoResponseDTO produto,
    Prioridade prioridade,
    @DateTimeFormat(pattern = "dd-MM-yyy")
    LocalDate previsaoInicio,
    @DateTimeFormat(pattern = "dd-MM-yyy")
    LocalDate previsaoTermino
) implements IMappable<ProdutoProjeto> {
} 
