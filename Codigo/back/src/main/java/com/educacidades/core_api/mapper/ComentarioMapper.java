package com.educacidades.core_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.educacidades.core_api.dto.comentario.ComentarioRequestDTO;
import com.educacidades.core_api.models.Comentario;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {
    public ComentarioRequestDTO comentarioToComentarioRequestDto(Comentario comentario);

    @Mapping(target = "texto", source = "texto")
    public Comentario comentarioRequestDtoToComentario(ComentarioRequestDTO comentarioRequestDto);

    @Named("textoToTexto")
    public static String textoToTexto(String texto) {
        return texto;
    }
}
