package com.educacidades.core_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.educacidades.core_api.dto.comentario.ComentarioRequestDTO;
import com.educacidades.core_api.dto.comentario.ComentarioResponseDTO;
import com.educacidades.core_api.enums.StatusComentario;
import com.educacidades.core_api.models.Comentario;
import com.educacidades.core_api.models.Projeto;
import com.educacidades.core_api.models.Tarefa;
import com.educacidades.core_api.models.Usuario;
import com.educacidades.core_api.repositories.ComentarioRepository;
import com.educacidades.core_api.repositories.ProjetoRepository;
import com.educacidades.core_api.repositories.TarefaRepository;
import com.educacidades.core_api.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository,
                            UsuarioRepository usuarioRepository,
                            ProjetoRepository projetoRepository, 
                            TarefaRepository tarefaRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.tarefaRepository = tarefaRepository;
    }

    @Transactional
    public ComentarioResponseDTO comentar(ComentarioRequestDTO dto){
        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        // Verifica se a tarefa existe
        Tarefa tarefa = null;
        if (dto.tarefaId() != null) {
            tarefa = tarefaRepository.findById(dto.tarefaId()).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        }
        // Verifica se o projeto existe
        Projeto projeto = null;
        if (dto.projetoId() != null) {
            projeto = projetoRepository.findById(dto.projetoId())
                    .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        }
        Comentario comentario = new Comentario(dto.texto(), usuario, projeto, tarefa);
        comentarioRepository.save(comentario);
        return comentario.toDto();
    }

    @Transactional
    public void deletarComentario(Long comentarioId){
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        comentarioRepository.delete(comentario);
    }

    @Transactional
    public ComentarioResponseDTO atualizarComentario(Long comentarioId, ComentarioRequestDTO dto){
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        comentario.setTexto(dto.texto());
        comentario.setDataAtualizacao(java.time.OffsetDateTime.now());
        comentarioRepository.save(comentario);
        return comentario.toDto();
    }

    @Transactional
    public ComentarioResponseDTO alterarVisibilidadeComentario(Long comentarioId){
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        
        if (comentario.getVisibilidade().toString().equals("VISIVEL")){
            comentario.setVisibilidade(StatusComentario.OCULTO);
        } else {
            comentario.setVisibilidade(StatusComentario.VISIVEL);
        }
        comentario.setDataAtualizacao(java.time.OffsetDateTime.now());
        comentarioRepository.save(comentario);
        return comentario.toDto();
    }

    public List<ComentarioResponseDTO> findByUsuario(Long usuarioId){
        List<Comentario> comentarios = comentarioRepository.findByUsuario_Id(usuarioId);
        return comentarios.stream().map(Comentario::toDto).toList();
    }

    public List<ComentarioResponseDTO> findByTarefa(Long tarefaId){
        List<Comentario> comentarios = comentarioRepository.findByTarefa_Id(tarefaId);
        return comentarios.stream().map(Comentario::toDto).toList();
    }

    public List<ComentarioResponseDTO> findByProjeto(Long projetoId){
        List<Comentario> comentarios = comentarioRepository.findByProjeto_Id(projetoId);
        return comentarios.stream().map(Comentario::toDto).toList();
    }

    public ComentarioResponseDTO findById(Long id){
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        return comentario.toDto();
    }
}
