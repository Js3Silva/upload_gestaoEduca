package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.token.RenovarTokenRequestDTO;
import com.educacidades.core_api.dto.token.TokenDTO;
import com.educacidades.core_api.models.Usuario;
import com.educacidades.core_api.repositories.UsuarioRepository;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UsuarioRepository usuarioRepository;

    private static final long EXPIRACAO_TOKEN_ACESSO = 604800;
    private static final long EXPIRACAO_TOKEN_RENOVACAO = 7 * 24 * 60 * 60;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UsuarioRepository usuarioRepository) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.usuarioRepository = usuarioRepository;
    }

    private JwtClaimsSet gerarClaims(Usuario usuario, long expiracao) {
        Instant agora = Instant.now();

        return JwtClaimsSet.builder()
                .issuer("core-api-educa-cidades")
                .subject(usuario.getId().toString())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expiracao))
                .claim("roles", List.of(usuario.getPerfil().name()))
                .claim("email", usuario.getEmail())
                .claim("nome", usuario.getNome())
                .build();
    }

    public String gerarTokenAcesso(Usuario usuario) {
        var claims = gerarClaims(usuario, EXPIRACAO_TOKEN_ACESSO);
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String gerarTokenRenovacao(Usuario usuario) {
        var claims = gerarClaims(usuario, EXPIRACAO_TOKEN_RENOVACAO);
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public TokenDTO renovarTokenAcesso(RenovarTokenRequestDTO dto) {
        try {
            var jwt = jwtDecoder.decode(dto.tokenRenovacao());
            Optional<Usuario> usuario = usuarioRepository.findById(Long.parseLong(jwt.getSubject()));

            if (usuario.isPresent())
                throw new RuntimeException("Usuário não encontrado.");

            String novoTokenAcesso = gerarTokenAcesso(usuario.get());
            return new TokenDTO(novoTokenAcesso, EXPIRACAO_TOKEN_ACESSO);
        } catch (JwtException e) {
            throw new RuntimeException("Token de renovação inválido ou expirado.", e);
        }
    }

    public long getExpiracaoTokenAcesso() {
        return EXPIRACAO_TOKEN_ACESSO;
    }

    public long getExpiracaoTokenRenovacao() {
        return EXPIRACAO_TOKEN_RENOVACAO;
    }
}
