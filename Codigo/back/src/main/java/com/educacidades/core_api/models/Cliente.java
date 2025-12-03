package com.educacidades.core_api.models;

import com.educacidades.core_api.dto.cliente.ClienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private Long codigo;

    @Column(name = "razao_social", nullable = false, length = 300)
    private String razaoSocial;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dataCriacao = OffsetDateTime.now();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Projeto> projetos = new ArrayList<>();

    public Cliente(Long codigo, String razaoSocial, String cnpj, String email, String cidade, String estado) {
        this.codigo = codigo;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
    }

    public ClienteDTO toDTO() {
        return new ClienteDTO(
                id, codigo, razaoSocial, cnpj, email, cidade, estado
        );
    }
}

    