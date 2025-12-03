package com.educacidades.core_api.mapper;

import com.educacidades.core_api.dto.comentarioAnexo.*;
import com.educacidades.core_api.models.Comentario;
import com.educacidades.core_api.models.ComentarioAnexo;
import org.springframework.stereotype.Component;

@Component
public class ComentarioAnexoMapper {

    public ComentarioAnexo toEntity(ComentarioAnexoRequestDTO dto, Comentario comentario) {
        return ComentarioAnexo.builder()
                .comentario(comentario)
                .nomeArquivo(dto.getNomeArquivo())
                .tipoArquivo(dto.getTipoArquivo())
                .tamanhoArquivo(dto.getTamanhoArquivo())
                .conteudoBase64(dto.getConteudoBase64())
                .build();
    }

    public ComentarioAnexoResponseDTO toDto(ComentarioAnexo anexo) {
        return ComentarioAnexoResponseDTO.builder()
                .id(anexo.getId())
                .comentarioId(anexo.getComentario().getId())
                .nomeArquivo(anexo.getNomeArquivo())
                .tipoArquivo(anexo.getTipoArquivo())
                .tamanhoArquivo(anexo.getTamanhoArquivo())
                .dataCriacao(anexo.getDataCriacao().toString())
                .build();
    }
}
