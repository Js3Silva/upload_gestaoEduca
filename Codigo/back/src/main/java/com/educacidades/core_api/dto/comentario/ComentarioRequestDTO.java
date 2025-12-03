package com.educacidades.core_api.dto.comentario;

import com.educacidades.core_api.models.Comentario;
import com.educacidades.core_api.models.IMappable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentarioRequestDTO(
    @NotBlank(message = "O texto do comentário é obrigatório") String texto,
    @NotNull(message = "O ID do usuário é obrigatório") Long usuarioId,
    Long tarefaId,
    Long projetoId
) implements IMappable<Comentario> {} 