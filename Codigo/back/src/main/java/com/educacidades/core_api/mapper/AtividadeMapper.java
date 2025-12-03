package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.atividade.AtividadeRequestDTO;
import com.educacidades.core_api.dto.atividade.AtividadeResponseDTO;
import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.models.Atividade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AtividadeMapper {
    public AtividadeResponseDTO atividadeToAtividadeResponseDto(Atividade atividade);

    @Mapping(target = "prioridade", source = "prioridade", qualifiedByName = "prioridadeValueToPrioridade")
    public Atividade atividadeRequestDtoToAtividade(AtividadeRequestDTO atividadeRequestDto);

    @Named("prioridadeValueToPrioridade")
    public static Prioridade prioridadeValueToPrioridade(int prioridadeValue) {
        return Prioridade.values()[prioridadeValue];
    }
}
