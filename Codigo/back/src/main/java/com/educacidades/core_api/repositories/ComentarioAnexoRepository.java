package com.educacidades.core_api.repositories;

import com.educacidades.core_api.models.ComentarioAnexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioAnexoRepository extends JpaRepository<ComentarioAnexo, Long> {
    List<ComentarioAnexo> findByComentarioId(Long comentarioId);
}
