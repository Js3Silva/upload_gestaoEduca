package com.educacidades.core_api.models;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoDTO;
import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.enums.StatusProjeto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projetos")
@AllArgsConstructor
@Data
@Builder
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private long codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "objetivo", nullable = false)
    private String objetivo;

    @Column(name = "inicio", nullable = false)
    private LocalDate inicio;

    @Column(name = "termino", nullable = false)
    private LocalDate termino;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusProjeto status;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "projeto",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<ProdutoProjeto> produtosProjeto;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "projetos_usuarios",
            joinColumns = {@JoinColumn(name = "projeto_id")},
            inverseJoinColumns = {@JoinColumn(name = "usuario_id")})
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();

    public Projeto() {
        this.produtosProjeto = new ArrayList<>();
    }

    public Projeto(String nome, String objetivo, LocalDate inicio, LocalDate termino, Cliente cliente) {
        this.nome = nome;
        this.objetivo = objetivo;
        this.inicio = inicio;
        this.termino = termino;
        this.cliente = cliente;
        this.produtosProjeto = new ArrayList<ProdutoProjeto>();
    }

    @PrePersist
    public void prePersist() {
        this.status = StatusProjeto.NAO_INICIADO;
        this.dataCriacao = OffsetDateTime.now();
    }

    public void addProdutoProjeto(ProdutoProjeto produtoProjeto) {
        this.produtosProjeto.add(produtoProjeto);
        produtoProjeto.setProjeto(this);
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.addProjeto(this);
    }

    public void removeProdutoProjeto(ProdutoProjeto produtoProjeto) {
        this.produtosProjeto.remove(produtoProjeto);
    }

    public void removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.removeProjeto(this);
    }

    public ProjetoResponseDTO toDto() {
        return new ProjetoResponseDTO(
                id,
                nome,
                objetivo,
                inicio,
                termino,
                status.getDescricao(),
                dataCriacao,
                cliente.toDTO()
        );    
    }

}
