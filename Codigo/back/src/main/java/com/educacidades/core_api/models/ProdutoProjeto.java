package com.educacidades.core_api.models;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoDTO;
import com.educacidades.core_api.enums.Prioridade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projetos_produtos")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProdutoProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prioridade", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Prioridade prioridade;

    @Column(name = "previsao_inicio", nullable = false)
    private LocalDate previsaoInicio;

    @Column(name = "previsao_termino", nullable = false)
    private LocalDate previsaoTermino;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    /*public void setProduto(Produto produto) {
        this.produto = produto;
        this.id.setIdProduto(produto.getId());
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
        this.id.setIdProjeto(projeto.getId());
    }*/

    public ProdutoProjetoDTO converteDTO(){
        return new ProdutoProjetoDTO(
            id, previsaoInicio, previsaoTermino, projeto.toDto(), produto.toDto()
        );
    }
}