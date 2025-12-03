package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.produto.ProdutoResponseDTO;
import com.educacidades.core_api.dto.projeto.ProjetoCreateRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoProdutoProjetoCreateRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.models.Produto;
import com.educacidades.core_api.models.Projeto;
import org.mapstruct.*;

import java.util.ArrayList;

@Mapper(componentModel = "spring", uses = {ProdutoProjetoMapper.class, ClienteMapper.class})
public abstract class ProjetoMapper {

    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "produtosProjeto", ignore = true)
    public abstract Projeto projetoProdutoProjetoCreateRequestDTOToProjeto(ProjetoProdutoProjetoCreateRequestDTO projetoCreateRequestDTO);

    public abstract Projeto projetoCreateRequestDTOToProjeto(ProjetoCreateRequestDTO projetoCreateRequestDTO);

    public abstract ProjetoResponseDTO projetoToProjetoResponseDTO(Projeto projeto);

    @AfterMapping
    protected void initList(@MappingTarget Projeto projeto) {
        if (projeto.getProdutosProjeto() == null) {
            projeto.setProdutosProjeto(new ArrayList<>());
        }
    }
}
