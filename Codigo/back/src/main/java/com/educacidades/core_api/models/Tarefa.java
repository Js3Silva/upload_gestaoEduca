package com.educacidades.core_api.models;

import com.educacidades.core_api.dto.tarefa.TarefaDTO;
import com.educacidades.core_api.enums.Prioridade;
import com.educacidades.core_api.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tarefas")
@Getter
@Setter
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_produto_id", nullable = false)
    private ProdutoProjeto produtoProjeto;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 5000)
    private String descricao;

    @Column(length = 1000)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Prioridade prioridade;

    @Column(nullable = false)
    private LocalDate prazo;

    @Column(columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime inicio;

    @Column(columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime termino;

    @Column(name = "duracao")
    private Long duracao;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusTarefa status = StatusTarefa.NAO_INICIADA;

    @Column(nullable = false)
    private boolean planejada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividade_id")
    private Atividade atividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dataCriacao = OffsetDateTime.now();

    public Tarefa() {
    }

    public Tarefa(ProdutoProjeto produtoProjeto, String nome, String descricao, String url, Prioridade prioridade, LocalDate prazo, Usuario responsavel) {
        if (prazo.isBefore(produtoProjeto.getPrevisaoInicio()) || prazo.isAfter(produtoProjeto.getPrevisaoTermino()))
            throw new IllegalArgumentException("Prazo da tarefa fora do intervalo de duração do projeto.");

        this.produtoProjeto = produtoProjeto;
        this.nome = nome;
        this.descricao = descricao;
        this.url = url;
        this.prioridade = prioridade;
        this.prazo = prazo;
        this.planejada = false;
        this.atividade = null;
        this.responsavel = responsavel;
    }

    public Tarefa(ProdutoProjeto produtoProjeto, Atividade atividade) {
        this.produtoProjeto = produtoProjeto;
        this.nome = atividade.getNome();
        this.descricao = atividade.getDescricao();
        this.prioridade = atividade.getPrioridade();
        this.prazo = produtoProjeto.getPrevisaoTermino();
        this.planejada = true;
        this.atividade = atividade;
        this.responsavel = null;
    }

    public void atualizarStatus(StatusTarefa status) {
        this.status = status;

        switch (this.status) {
            case NAO_INICIADA:
                this.inicio = null;
                this.termino = null;
                this.duracao = null;
                break;

            case EM_ANDAMENTO:
                if (this.inicio == null)
                    this.inicio = OffsetDateTime.now();

                this.termino = null;
                this.duracao = null;
                break;

            case PAUSADA:
                this.termino = null;
                this.duracao = null;
                break;

            case CONCLUIDA:
                if (this.inicio == null)
                    this.inicio = OffsetDateTime.now();

                this.termino = OffsetDateTime.now();
                this.duracao = Duration.between(this.inicio, this.termino).getSeconds();
                break;
        }
    }

    public TarefaDTO toDto() {
        return new TarefaDTO(
                id, nome, descricao, url, prioridade, prazo, inicio, termino, status, planejada, responsavel != null ? responsavel.toDto() : null, dataCriacao, produtoProjeto.getProjeto().toDto()
        );
    }
}
