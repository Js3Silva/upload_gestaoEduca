package com.educacidades.core_api.repositories;

import com.educacidades.core_api.models.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findAllByProdutoProjeto_Id(Long id);
    
    @Query("SELECT a FROM Tarefa a " +
            "WHERE a.prazo BETWEEN CURRENT_DATE AND :dataLimite")
    List<Tarefa> findTarefasComPrazoProximo(@Param("dataLimite") LocalDate dataLimite);
}
