package com.educacidades.core_api.models;

import com.educacidades.core_api.dto.usuario.UsuarioDTO;
import com.educacidades.core_api.dto.usuario.UsuarioResumoDTO;
import com.educacidades.core_api.enums.PerfilAcesso;
import com.educacidades.core_api.enums.StatusUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@Data
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(nullable = false, length = 128)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PerfilAcesso perfil;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dataCriacao = OffsetDateTime.now();

    @ManyToMany(mappedBy = "usuarios")
    @Fetch(FetchMode.SELECT)
    List<Projeto> projetos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nome, String email, String login, String senha, PerfilAcesso perfil) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public boolean isSenhaCorreta(String senhaEnviada, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(senhaEnviada, this.senha);
    }

    public void addProjeto(Projeto projeto) {
        this.projetos.add(projeto);
    }

    public void removeProjeto(Projeto projeto) {
        this.projetos.remove(projeto);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public UsuarioDTO toDto() {
        return new UsuarioDTO(id, nome, email, login, perfil.getDescricao(), status.getDescricao(), dataCriacao);
    }

    public UsuarioResumoDTO toResumoDto() {
        return new UsuarioResumoDTO(id, nome);
    }
}
