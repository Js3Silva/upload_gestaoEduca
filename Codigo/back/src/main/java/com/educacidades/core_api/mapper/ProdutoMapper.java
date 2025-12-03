package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.produto.ProdutoLightResponseDTO;
import com.educacidades.core_api.dto.produto.ProdutoResponseDTO;
import com.educacidades.core_api.models.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AtividadeMapper.class)
public interface ProdutoMapper {
    public ProdutoResponseDTO produtoToProdutoResponseDto(Produto produto);
    public ProdutoLightResponseDTO produtoToProdutoLightResponseDto(Produto produto);
}
