package com.educacidades.core_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "comentarios_anexos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioAnexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comentario_id", nullable = false)
    private Comentario comentario;

    @Column(name = "nome_arquivo", nullable = false, length = 255)
    private String nomeArquivo;

    @Column(name = "tipo_arquivo", nullable = false, length = 50)
    private String tipoArquivo;

    @Column(name = "tamanho_arquivo", nullable = false)
    private Long tamanhoArquivo;

    @Column(name = "conteudo_base64", nullable = false, columnDefinition = "TEXT")
    private String conteudoBase64;

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime dataCriacao = OffsetDateTime.now();
}
