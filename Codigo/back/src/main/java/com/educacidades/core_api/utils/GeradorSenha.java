package com.educacidades.core_api.utils;

import java.security.SecureRandom;

public final class GeradorSenha {

    private static final String MAIUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITOS = "0123456789";
    private static final String SIMBOLOS = "!@#$%^&*()-_=+";
    private static final String TODOS = MAIUSCULAS + MINUSCULAS + DIGITOS + SIMBOLOS;

    private static final SecureRandom random = new SecureRandom();

    private GeradorSenha() {}

    public static String gerarSenha(int tamanho) {
        StringBuilder sb  = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(TODOS.length());
            sb.append(TODOS.charAt(index));
        }

        return sb.toString();
    }

}
