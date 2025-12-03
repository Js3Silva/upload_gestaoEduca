package com.educacidades.core_api.dto.dashboard;

import java.util.List;

public record DashboardResponse(
        Long totalProjetos,
        Long projetosNaoIniciados,
        Long projetosPausados,
        Long projetosFinalizados,
        Long projetosAtrasados,
        Double mediaTempoConclusaoProjetoDias,
        List<ProjetoEstatisticasMensaisResponse> projetoEstatisticasMensais
) {
}
