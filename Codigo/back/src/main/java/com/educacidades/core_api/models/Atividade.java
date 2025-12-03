package com.educacidades.core_api.models;

import com.educacidades.core_api.enums.Prioridade;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "atividades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "prioridade", nullable = false)
    Prioridade prioridade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id")
    @JsonBackReference // impede referência cíclica ao retornar um Produto em uma body response
    Produto produto;

    public Atividade(Tarefa tarefa) {
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.prioridade = tarefa.getPrioridade();
        this.produto = tarefa.getProdutoProjeto().getProduto();
    }
}
