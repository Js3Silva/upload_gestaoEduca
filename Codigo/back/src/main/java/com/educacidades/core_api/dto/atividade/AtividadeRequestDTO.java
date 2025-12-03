package com.educacidades.core_api.dto.atividade;

import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.models.Atividade;
import com.educacidades.core_api.models.IMappable;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public record AtividadeRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "A descrição é obrigatório")
        String descricao,
        @Range(min = 0, max = 2, message = "Tipo de prioridade inválido")
        int prioridade
) implements IMappable<Atividade> {
}
