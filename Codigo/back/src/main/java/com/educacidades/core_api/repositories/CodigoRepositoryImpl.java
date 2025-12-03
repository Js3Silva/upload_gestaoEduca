package com.educacidades.core_api.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CodigoRepositoryImpl implements CodigoRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getCodigo() {
        return (Long) em.createNativeQuery("SELECT nextval('codigo_seq')").getSingleResult();
    }
}
