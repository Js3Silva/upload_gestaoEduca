package com.educacidades.core_api.dto.usuario;

import com.educacidades.core_api.enums.PerfilAcesso;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email
        String email,

        @NotBlank(message = "O login é obrigatório.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        @NotNull(message = "O perfil é obrigatório.")
        PerfilAcesso perfil
) {
}
