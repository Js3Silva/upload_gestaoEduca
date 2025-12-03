package com.educacidades.core_api.repositories;
import com.educacidades.core_api.models.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCodigo(Long codigo);
    Optional<Cliente> findByCnpj(String cnpj);
}
