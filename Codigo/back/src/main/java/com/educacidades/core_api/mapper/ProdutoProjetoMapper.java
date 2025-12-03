package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoRequestDTO;
import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoResponseDTO;
import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.models.Produto;
import com.educacidades.core_api.models.ProdutoProjeto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = ProdutoMapper.class)
public abstract class ProdutoProjetoMapper {
    /*@Mapping(target = "idProjeto", source = "produtoProjeto.id.idProjeto")*/
    public abstract ProdutoProjetoResponseDTO produtoProjetoToProdutoProjetoResponseDto(ProdutoProjeto produtoProjeto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "prioridade", source = "produtoProjetoRequestDto.prioridadeValue", qualifiedByName = "prioridadeValueToPrioridade")
    @Mapping(target = "produto", source = "produto")
    public abstract ProdutoProjeto produtoProjetoRequestDtoToProdutoProjeto(Produto produto, ProdutoProjetoRequestDTO produtoProjetoRequestDto);

    @Named("prioridadeValueToPrioridade")
    public static Prioridade prioridadeValueToPrioridade(int prioridadeValue) {
        return Prioridade.values()[prioridadeValue];
    }

    /*@AfterMapping
    protected void initId(@MappingTarget ProdutoProjeto produtoProjeto) {
        if (produtoProjeto.getId() == null)
            produtoProjeto.setId(new ProdutoProjetoId());
    }*/
}
