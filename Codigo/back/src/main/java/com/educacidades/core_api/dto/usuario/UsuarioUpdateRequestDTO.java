package com.educacidades.core_api.dto.usuario;

import com.educacidades.core_api.enums.PerfilAcesso;
import com.educacidades.core_api.enums.StatusUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioUpdateRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email
        String email,

        String senha,

        @NotNull(message = "O perfil é obrigatório.")
        PerfilAcesso perfil,

        @NotNull(message = "O status é obrigatório.")
        StatusUsuario status
) {
}
