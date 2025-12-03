package com.educacidades.core_api.dto.dashboard;

public record ProjetoEstatisticasMensaisResponse(
        Integer mes,
        String nomeMes,
        Long projetosIniciados,
        Long projetosFinalizados
) {
}

