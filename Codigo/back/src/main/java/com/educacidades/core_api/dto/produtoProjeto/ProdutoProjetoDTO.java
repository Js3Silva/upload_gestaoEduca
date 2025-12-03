package com.educacidades.core_api.dto.produtoProjeto;

import java.time.LocalDate;

import com.educacidades.core_api.dto.produto.ProdutoLightResponseDTO;
import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.models.IMappable;
import com.educacidades.core_api.models.ProdutoProjeto;

public record ProdutoProjetoDTO(
        Long id,
        LocalDate previsaoInicio,
        LocalDate previsaoTermino,
        ProjetoResponseDTO projeto,
        ProdutoLightResponseDTO  produto
) implements IMappable<ProdutoProjeto> {
}  
