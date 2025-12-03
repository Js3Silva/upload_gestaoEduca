package com.educacidades.core_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.educacidades.core_api.dto.produto.ProdutoLightResponseDTO;

@Entity
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private Long codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "produto",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Atividade> atividades;

    @OneToMany(mappedBy = "produto")
    private List<ProdutoProjeto> projetosProduto = new ArrayList<>();

    public Produto(String nome,
                   String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.atividades = new ArrayList<>();
    }

    public void addAtividade(Atividade atividade) {
        if (this.atividades == null) {
            this.atividades = new LinkedList<>();
        }
        this.atividades.add(atividade);
        atividade.setProduto(this);
    }

    public void removeAtividade(Atividade atividadeToDelete) {
        this.atividades.removeIf(atividade ->
                Objects.equals(atividadeToDelete.getId(), atividade.getId()));
        atividadeToDelete.setProduto(null);
    }

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDate.now();
    }

    public ProdutoLightResponseDTO toDto() {
        return new ProdutoLightResponseDTO(
                this.id,
                this.codigo,
                this.nome,
                this.descricao,
                this.dataCriacao
        );
    }
}
