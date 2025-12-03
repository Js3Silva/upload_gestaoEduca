package com.educacidades.core_api.dto.comentarioAnexo;

import lombok.Data;

@Data
public class ComentarioAnexoRequestDTO {
    private Long comentarioId;
    private String nomeArquivo;
    private String tipoArquivo;
    private Long tamanhoArquivo;
    private String conteudoBase64; // Conteúdo em Base64
}
