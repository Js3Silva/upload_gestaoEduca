package com.educacidades.core_api.enums;

public enum TipoSeguranca {

    STARTTLS("STARTTLS"),
    SSL_TLS("SSL/TLS"),
    NENHUMA("Nenhuma");

    private final String descricao;

    TipoSeguranca(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
