package com.educacidades.core_api.dto.comentario;

import com.educacidades.core_api.dto.usuario.UsuarioDTO;
import com.educacidades.core_api.models.Comentario;

public record ComentarioResponseDTO(Long id,
                                    String texto,
                                    UsuarioDTO usuario,
                                    Long projetoId,
                                    Long tarefaId,
                                    String visibilidade,
                                    String dataCriacao,
                                    String dataAtualizacao) {
    public static ComentarioResponseDTO fromEntity(Comentario comentario) {
        return new ComentarioResponseDTO(
                comentario.getId(),
                comentario.getTexto(),
                comentario.getUsuario().toDto(),
                comentario.getProjeto() != null ? comentario.getProjeto().getId() : null,
                comentario.getTarefa() != null ? comentario.getTarefa().getId() : null,
                comentario.getVisibilidade().toString(),
                comentario.getDataCriacao().toString(),
                comentario.getDataAtualizacao().toString()
        );
    }
}
