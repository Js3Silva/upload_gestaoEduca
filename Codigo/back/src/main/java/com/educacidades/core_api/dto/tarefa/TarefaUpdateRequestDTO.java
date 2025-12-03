package com.educacidades.core_api.dto.tarefa;

import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.enums.StatusTarefa;

import java.time.LocalDate;

public record TarefaUpdateRequestDTO(
        String nome,
        String descricao,
        String url,
        Prioridade prioridade,
        LocalDate prazo,
        StatusTarefa status,
        Long idResponsavel) {
}
