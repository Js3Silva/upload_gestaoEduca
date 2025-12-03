package com.educacidades.core_api.enums;

public enum StatusProjeto {

    NAO_INICIADO("Não iniciado"),
    EM_ANDAMENTO("Em andamento"),
    PAUSADO("Pausado"),
    CONCLUIDO("Concluído");

    private final String descricao;

    StatusProjeto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
