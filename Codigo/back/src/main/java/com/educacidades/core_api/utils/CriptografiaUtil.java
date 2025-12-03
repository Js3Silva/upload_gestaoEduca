package com.educacidades.core_api.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public final class CriptografiaUtil {

    private static final String ALGORITMO = "AES";
    private static final String CHAVE = "j/0dclP+PhTGDC2Q";

    public static String criptografar(String valor) {
        try {
            SecretKeySpec chave = new SecretKeySpec(CHAVE.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            byte[] valorCriptografado = cipher.doFinal(valor.getBytes());
            return Base64.getEncoder().encodeToString(valorCriptografado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar senha.", e);
        }
    }

    public static String descriptografar(String valorCriptografado) {
        try {
            SecretKeySpec chave = new SecretKeySpec(CHAVE.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, chave);
            byte[] valorDecodificado = Base64.getDecoder().decode(valorCriptografado);
            byte[] valorDescriptografado = cipher.doFinal(valorDecodificado);
            return new String(valorDescriptografado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar senha.", e);
        }
    }
}
