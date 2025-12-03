package com.educacidades.core_api.dto.projeto;

import java.time.LocalDate;
import java.util.List;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoRequestDTO;
import com.educacidades.core_api.models.IMappable;
import com.educacidades.core_api.models.Projeto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjetoProdutoProjetoCreateRequestDTO(

    @NotBlank(message = "O nome é obrigatório.")
    String nome,

    @NotBlank(message = "O nome é obrigatório.")
    String objetivo,

    @NotNull(message = "A data de início é obrigatória.")
    LocalDate inicio,

    @NotNull(message = "A data de término é obrigatória.")
    LocalDate termino,

    @NotNull(message = "O ID do cliente é obrigatório.")
    Long idCliente,

    List<@Valid ProdutoProjetoRequestDTO> produtosProjeto
) implements IMappable<Projeto> {}
