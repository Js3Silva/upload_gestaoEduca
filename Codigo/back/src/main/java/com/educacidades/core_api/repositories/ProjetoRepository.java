package com.educacidades.core_api.repositories;

import java.time.LocalDate;
import java.util.List;

import com.educacidades.core_api.repositories.projections.ProjetoEstatisticasMensaisProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.educacidades.core_api.models.Projeto;


@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

        @Query("SELECT a FROM Projeto a " +
                "WHERE a.termino BETWEEN CURRENT_DATE AND :dataLimite")
        List<Projeto> findProjetosComPrazoProximo(@Param("dataLimite") LocalDate dataLimite);

    @Query(value = """
                SELECT AVG(termino - inicio)
                FROM projetos   
                WHERE status = 3
            """, nativeQuery = true)
    Double mediaTempoDeConclusaoDeProjetoDias();

    @Query("SELECT COUNT(p) FROM Projeto p WHERE p.status = 3")
    Long countProjetosFinalizados();

    @Query("SELECT COUNT(p) FROM Projeto p WHERE p.status = 0")
    Long countProjetosNaoIniciados();

    @Query("SELECT COUNT(p) FROM Projeto p WHERE p.status = 2")
    Long countProjetosPausados();

    @Query(value = """
                SELECT COUNT(*)
                FROM projetos
                WHERE termino < CURRENT_DATE
                AND status != 3
            """, nativeQuery = true)
    Long countProjetosAtrasados();

    @Query(value = """
                WITH meses AS (
                    SELECT generate_series(1, 12) AS mes
                ),
                projetos_iniciados AS (
                    SELECT
                        EXTRACT(MONTH FROM inicio)::INTEGER AS mes,
                        COUNT(*) AS quantidade
                    FROM projetos
                    WHERE EXTRACT(YEAR FROM inicio) = EXTRACT(YEAR FROM CURRENT_DATE)
                    GROUP BY EXTRACT(MONTH FROM inicio)
                ),
                projetos_finalizados AS (
                    SELECT
                        EXTRACT(MONTH FROM termino)::INTEGER AS mes,
                        COUNT(*) AS quantidade
                    FROM projetos
                    WHERE EXTRACT(YEAR FROM termino) = EXTRACT(YEAR FROM CURRENT_DATE)
                    AND status = 3
                    GROUP BY EXTRACT(MONTH FROM termino)
                )
                SELECT
                    m.mes,
                    COALESCE(pi.quantidade, 0) AS projetosIniciados,
                    COALESCE(pf.quantidade, 0) AS projetosFinalizados
                FROM meses m
                LEFT JOIN projetos_iniciados pi ON m.mes = pi.mes
                LEFT JOIN projetos_finalizados pf ON m.mes = pf.mes
                ORDER BY m.mes
            """, nativeQuery = true)
    List<ProjetoEstatisticasMensaisProjection> findEstatisticasMensaisAnoAtual();

    List<Projeto> findByClienteId(Long clienteId);

}
