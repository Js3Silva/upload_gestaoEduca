package com.educacidades.core_api.config;

import com.educacidades.core_api.enums.PerfilAcesso;
import com.educacidades.core_api.models.Cliente;
import com.educacidades.core_api.models.Usuario;
import com.educacidades.core_api.repositories.ClienteRepository;
import com.educacidades.core_api.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Configuration
public class InicializadorDados implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public InicializadorDados(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Optional<Usuario> usuarioAdmin = usuarioRepository.findByLogin("admin");
        usuarioAdmin.ifPresentOrElse(
                admin -> {
                },
                () -> {
                    String senhaCriptografada = passwordEncoder.encode("admin");

                    Usuario admin = new Usuario("Administrador", "", "admin", senhaCriptografada, PerfilAcesso.ADMIN);
                    usuarioRepository.save(admin);
                }
        );

        /*Optional<Usuario> usuarioTeste = usuarioRepository.findByLogin("usuario.teste");
        usuarioTeste.ifPresentOrElse(
                teste -> {
                },
                () -> {
                    String senhaCriptografada = passwordEncoder.encode("senha@123");

                    Usuario teste = new Usuario("Usuário Teste", "usuario.teste@teste.com", "usuario.teste", senhaCriptografada, PerfilAcesso.BASICO);
                    usuarioRepository.save(teste);
                }
        );

        Optional<Usuario> usuarioCliente = usuarioRepository.findByLogin("usuario.cliente");
        usuarioCliente.ifPresentOrElse(
                cliente -> {
                },
                () -> {
                    String senhaCriptografada = passwordEncoder.encode("senha@123");

                    Usuario teste = new Usuario("Usuário Cliente", "usuario.cliente@teste.com", "78307012000150", senhaCriptografada, PerfilAcesso.EXTERNO);
                    usuarioRepository.save(teste);

                    Cliente cliente = new Cliente(1l, "Usuário Cliente", "78307012000150", "usuario.cliente@teste.com", "Belo Horizonte", "MG");
                    cliente.setUsuarioId(teste.getId());
                    clienteRepository.save(cliente);
                }
        );*/
    }
}
