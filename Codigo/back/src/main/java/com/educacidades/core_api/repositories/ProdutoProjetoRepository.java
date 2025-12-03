package com.educacidades.core_api.repositories;

import java.time.LocalDate;
import java.util.List;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoDTO;
import com.educacidades.core_api.models.ProdutoProjeto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoProjetoRepository extends JpaRepository<ProdutoProjeto, Long> {
        @Query("SELECT a FROM ProdutoProjeto a " +
            "WHERE a.previsaoTermino BETWEEN CURRENT_DATE AND :dataLimite")
    List<ProdutoProjeto> findProdutosComPrazoProximo(@Param("dataLimite") LocalDate dataLimite);

    @Query("SELECT pp FROM ProdutoProjeto pp " +
            "WHERE pp.projeto.id = :idProjeto AND pp.produto.id = :idProduto")
    ProdutoProjeto findOne(@Param("idProjeto") Long idProjeto,
                            @Param("idProduto") Long idProduto);
}