package com.educacidades.core_api.dto.login;

import com.educacidades.core_api.dto.token.TokenDTO;

public record LoginResponseDTO(String nome, String perfil, TokenDTO tokenAcesso, TokenDTO tokenRenovacao) {
}
