package com.educacidades.core_api.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record ClienteUpdateRequestDTO(

        @NotBlank(message = "A razão social é obrigatória.")
        @Size(max = 300, message = "A razão social deve ter no máximo 300 caracteres")
        String razaoSocial,

        @NotBlank(message = "O CNPJ é obrigatório.")
        @CNPJ(message = "O CNPJ informado é inválido.")
        String cnpj,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail informado é inválido.")
        String email,

        @NotBlank(message = "A cidade é obrigatória.")
        String cidade,

        @NotBlank(message = "O estado é obrigatório.")
        String estado
) {
}
