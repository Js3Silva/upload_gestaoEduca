package com.educacidades.core_api.enums;

public enum PerfilAcesso {
    ADMIN("Administrador"),
    BASICO("Básico"),
    EXTERNO("Externo");

    String descricao;

    PerfilAcesso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
