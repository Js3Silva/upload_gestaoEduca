package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.usuario.*;
import com.educacidades.core_api.enums.PerfilAcesso;
import com.educacidades.core_api.enums.StatusUsuario;
import com.educacidades.core_api.models.Cliente;
import com.educacidades.core_api.models.Usuario;
import com.educacidades.core_api.repositories.UsuarioRepository;
import com.educacidades.core_api.utils.GeradorSenha;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    private Usuario buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    @Transactional
    public void criar(UsuarioCreateRequestDTO dto) {
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        Usuario novo = new Usuario(dto.nome(), dto.email(), dto.login(), senhaCriptografada, dto.perfil());
        repository.save(novo);
    }

    @Transactional
    public Optional<UsuarioClienteDTO> criar(Cliente cliente) {
        Usuario usuario = repository.findByLogin(cliente.getCnpj()).orElse(null);

        if (usuario == null) {
            String senha = GeradorSenha.gerarSenha(8);
            Usuario novo = new Usuario(cliente.getRazaoSocial(), cliente.getEmail(), cliente.getCnpj(), passwordEncoder.encode(senha), PerfilAcesso.EXTERNO);
            repository.save(novo);
            return Optional.of(new UsuarioClienteDTO(novo.getLogin(), senha));
        }

        if (usuario.getStatus().equals(StatusUsuario.INATIVO)) {
            usuario.setStatus(StatusUsuario.ATIVO);
            repository.save(usuario);
        }

        return Optional.empty();
    }

    @PreAuthorize("hasRole('ADMIN') or #id == T(java.lang.Long).valueOf(principal.claims['sub'])")
    @Transactional
    public void atualizar(Long id, UsuarioUpdateRequestDTO dto) {
        Usuario usuario = buscar(id);

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.senha()));
        }
        usuario.setPerfil(dto.perfil());
        usuario.setStatus(dto.status());

        repository.save(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioDTO> buscarTodos() {
        return repository.findAll()
                .stream()
                .map(Usuario::toDto)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN') or #id == T(java.lang.Long).valueOf(principal.claims['sub'])")
    public UsuarioDTO buscarPorId(Long id) {
        return buscar(id).toDto();
    }

    @PreAuthorize("hasRole('ADMIN') or #id == T(java.lang.Long).valueOf(principal.claims['sub'])")
    @Transactional
    public void excluir(Long id) {
        Usuario usuario = buscar(id);

        if (usuario.getLogin().equals("admin"))
            throw new RuntimeException("O usuário admin não pode ser excluído.");

        repository.delete(usuario);
    }

    public List<UsuarioResumoDTO> buscarTodosResumo() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios.stream()
                .filter(x -> x.getPerfil() == PerfilAcesso.BASICO || x.getPerfil() == PerfilAcesso.ADMIN)
                .map(Usuario::toResumoDto).toList();
    }
}
