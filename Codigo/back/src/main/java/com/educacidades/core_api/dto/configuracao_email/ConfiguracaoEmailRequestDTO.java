package com.educacidades.core_api.dto.configuracao_email;

import com.educacidades.core_api.enums.TipoSeguranca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfiguracaoEmailRequestDTO(

        @NotBlank(message = "O host é obrigatório.")
        String host,

        @NotNull(message = "A porta é obrigatória.")
        int porta,

        @NotBlank(message = "O usuário é obrigatório.")
        String usuario,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        String nomeRemetente,

        @NotNull(message = "O tipo de segurança é obrigatório.")
        TipoSeguranca tipoSeguranca
) {
}
