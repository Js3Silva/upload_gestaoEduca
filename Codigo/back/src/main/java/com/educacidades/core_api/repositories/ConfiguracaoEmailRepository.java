package com.educacidades.core_api.repositories;

import com.educacidades.core_api.models.ConfiguracaoEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoEmailRepository extends JpaRepository<ConfiguracaoEmail, Long> {
}
