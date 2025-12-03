package com.educacidades.core_api.models;

import java.time.OffsetDateTime;

import com.educacidades.core_api.dto.comentario.ComentarioResponseDTO;
import com.educacidades.core_api.enums.StatusComentario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comentarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Relacionamentos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefa_id", nullable = true)
    private Tarefa tarefa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", nullable = true)
    private Projeto projeto;

    // 🔹 Atributos principais
    @Column(nullable = false, length = 5000)
    private String texto;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private StatusComentario visibilidade;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime dataCriacao = OffsetDateTime.now();

    @Column(name = "data_atualizacao", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dataAtualizacao = OffsetDateTime.now();

    // 🔹 Construtor auxiliar
    public Comentario(String texto, Usuario usuario, Projeto projeto, Tarefa tarefa) {
        this.texto = texto;
        this.usuario = usuario;
        this.projeto = projeto;
        this.tarefa = tarefa;
        this.visibilidade = StatusComentario.OCULTO;
    }

    // 🔹 Conversão para DTO
    public ComentarioResponseDTO toDto() {
        return new ComentarioResponseDTO(
            this.id,
            this.texto,
            this.usuario != null ? this.usuario.toDto() : null,
            this.projeto != null ? this.projeto.getId() : null,
            this.tarefa != null ? this.tarefa.getId() : null,
            this.visibilidade.toString(),
            this.dataCriacao != null ? this.dataCriacao.toString() : null,
            this.dataAtualizacao != null ? this.dataAtualizacao.toString() : null
        );
    }
}
