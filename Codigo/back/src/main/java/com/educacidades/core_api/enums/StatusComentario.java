package com.educacidades.core_api.enums;

public enum StatusComentario {
    VISIVEL("Visível"),
    OCULTO("Oculto");

    private final String descricao;

    StatusComentario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
