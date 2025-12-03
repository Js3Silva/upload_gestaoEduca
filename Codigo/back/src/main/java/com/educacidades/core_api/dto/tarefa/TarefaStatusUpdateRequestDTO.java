package com.educacidades.core_api.dto.tarefa;

import com.educacidades.core_api.enums.StatusTarefa;
import jakarta.validation.constraints.NotNull;

public record TarefaStatusUpdateRequestDTO(

        @NotNull(message = "O status é obrigatório.")
        StatusTarefa status
) {
}
