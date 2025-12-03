package com.educacidades.core_api.dto.produto;

import com.educacidades.core_api.dto.atividade.AtividadeResponseDTO;
import com.educacidades.core_api.models.Produto;

import java.time.LocalDate;
import java.util.List;

public record ProdutoResponseDTO(Long id,
                                 Long codigo,
                                 String nome,
                                 String descricao,
                                 LocalDate dataCriacao,
                                 List<AtividadeResponseDTO> atividades) {
    public static ProdutoResponseDTO fromEntity(Produto produto) {
        return new ProdutoResponseDTO(produto.getId(),
                produto.getCodigo(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getDataCriacao(),
                produto.getAtividades()
                        .stream()
                        .map(AtividadeResponseDTO::fromEntity)
                        .toList());
    }
}
