package com.educacidades.core_api.dto.tarefa;

import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.dto.usuario.UsuarioDTO;
import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.enums.StatusTarefa;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record TarefaDTO(
        Long id,
        String nome,
        String descricao,
        String url,
        Prioridade prioridade,
        LocalDate prazo,
        OffsetDateTime inicio,
        OffsetDateTime termino,
        StatusTarefa status,
        boolean planejada,
        UsuarioDTO responsavel,
        OffsetDateTime dataCriacao,
        ProjetoResponseDTO projeto
) {
}
