package com.educacidades.core_api.dto.produto;

import com.educacidades.core_api.dto.atividade.AtividadeResponseDTO;
import com.educacidades.core_api.models.Produto;

import java.time.LocalDate;

public record ProdutoLightResponseDTO(Long id,
                                     Long codigo,
                                     String nome,
                                     String descricao,
                                     LocalDate dataCriacao) {
    public static ProdutoLightResponseDTO fromEntity(Produto produto) {
        return new ProdutoLightResponseDTO(produto.getId(),
                produto.getCodigo(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getDataCriacao());
    }
}
