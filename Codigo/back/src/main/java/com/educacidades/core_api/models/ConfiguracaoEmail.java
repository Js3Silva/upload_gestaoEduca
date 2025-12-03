package com.educacidades.core_api.models;

import com.educacidades.core_api.enums.TipoSeguranca;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "configuracao_email")
public class ConfiguracaoEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @Getter
    private String host;

    @Column(nullable = false)
    @Getter
    private int porta;

    @Column(nullable = false, length = 200)
    @Getter
    private String usuario;

    @Column(nullable = false, length = 500)
    @Getter
    private String senha;

    @Column(name = "nome_remetente", nullable = false, length = 100)
    @Getter
    private String nomeRemetente;

    @Column(name = "tipo_seguranca", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @Getter
    private TipoSeguranca tipoSeguranca;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dataCriacao = OffsetDateTime.now();

    public ConfiguracaoEmail() {
    }

    public ConfiguracaoEmail(String host, int porta, String usuario, String senha, String nomeRemetente, TipoSeguranca tipoSeguranca) {
        this.host = host;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
        this.nomeRemetente = nomeRemetente;
        this.tipoSeguranca = tipoSeguranca;
    }
}
