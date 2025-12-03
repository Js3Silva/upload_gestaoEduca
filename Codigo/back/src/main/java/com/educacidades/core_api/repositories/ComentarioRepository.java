package com.educacidades.core_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.educacidades.core_api.models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Optional<Comentario> findById(Long id);
    List<Comentario> findByUsuario_Id(Long usuarioId);
    List<Comentario> findByTarefa_Id(Long tarefaId);
    List<Comentario> findByProjeto_Id(Long projetoId);
} 