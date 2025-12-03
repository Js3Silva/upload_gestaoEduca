package com.educacidades.core_api.dto.comentarioAnexo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioAnexoResponseDTO {
    private Long id;
    private Long comentarioId;
    private String nomeArquivo;
    private String tipoArquivo;
    private Long tamanhoArquivo;
    private String dataCriacao;
}
