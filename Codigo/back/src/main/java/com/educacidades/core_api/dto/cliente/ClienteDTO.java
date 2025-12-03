package com.educacidades.core_api.dto.cliente;

public record ClienteDTO(
        Long id,
        Long codigo,
        String razaoSocial,
        String cnpj,
        String email,
        String cidade,
        String estado
) {
}
