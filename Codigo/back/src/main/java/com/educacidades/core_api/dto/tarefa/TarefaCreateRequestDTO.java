package com.educacidades.core_api.dto.tarefa;

import com.educacidades.core_api.enums.Prioridade;

import java.time.LocalDate;

public record TarefaCreateRequestDTO(
        Long idProjetoProduto,
        String nome,
        String descricao,
        String url,
        Prioridade prioridade,
        LocalDate prazo,
        Long idResponsavel
) {
}
