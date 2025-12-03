package com.educacidades.core_api.repositories;

import com.educacidades.core_api.dto.produto.ProdutoLightResponseDTO;
import com.educacidades.core_api.dto.produto.ProdutoResponseDTO;
import com.educacidades.core_api.models.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.educacidades.core_api.models.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
