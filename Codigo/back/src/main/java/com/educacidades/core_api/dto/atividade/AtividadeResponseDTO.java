package com.educacidades.core_api.dto.atividade;

import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.models.Atividade;

public record AtividadeResponseDTO(Long id,
                                   String nome,
                                   String descricao,
                                   Prioridade prioridade) {
    public static AtividadeResponseDTO fromEntity(Atividade atividade) {
        return new AtividadeResponseDTO(atividade.getId(),
                atividade.getNome(),
                atividade.getDescricao(),
                atividade.getPrioridade());
    }
}
