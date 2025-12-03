package com.educacidades.core_api.repositories.projections;

public interface ProjetoEstatisticasMensaisProjection {
    Integer getMes();
    Long getProjetosIniciados();
    Long getProjetosFinalizados();
}

