package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.login.LoginRequestDTO;
import com.educacidades.core_api.dto.login.LoginResponseDTO;
import com.educacidades.core_api.dto.token.TokenDTO;
import com.educacidades.core_api.enums.StatusUsuario;
import com.educacidades.core_api.models.Usuario;
import com.educacidades.core_api.repositories.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginService(UsuarioRepository repository, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Optional<Usuario> usuario = repository.findByLogin(dto.login());

        if (usuario.isEmpty() || !usuario.get().isSenhaCorreta(dto.senha(), passwordEncoder))
            throw new BadCredentialsException("Usuário ou senha incorretos.");

        if (usuario.get().getStatus() == StatusUsuario.INATIVO)
            throw new RuntimeException("Usuário inativo.");

        String tokenAcesso = tokenService.gerarTokenAcesso(usuario.get());
        String tokenRenovacao = tokenService.gerarTokenRenovacao(usuario.get());

        return new LoginResponseDTO(
                usuario.get().getNome(),
                usuario.get().getPerfil().getDescricao(),
                new TokenDTO(tokenAcesso, tokenService.getExpiracaoTokenAcesso()),
                new TokenDTO(tokenRenovacao, tokenService.getExpiracaoTokenRenovacao())
        );
    }
}
