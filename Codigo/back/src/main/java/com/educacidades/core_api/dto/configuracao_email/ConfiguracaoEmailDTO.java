package com.educacidades.core_api.dto.configuracao_email;

import java.time.OffsetDateTime;

public record ConfiguracaoEmailDTO(
        Long id,
        String host,
        int porta,
        String usuario,
        String nomeRemetente,
        String tipoSeguranca,
        OffsetDateTime dataCriacao
) {
}
