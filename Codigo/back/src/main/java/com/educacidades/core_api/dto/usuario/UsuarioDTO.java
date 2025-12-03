package com.educacidades.core_api.dto.usuario;

import java.time.OffsetDateTime;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        String login,
        String perfil,
        String status,
        OffsetDateTime dataCriacao
) {
}
