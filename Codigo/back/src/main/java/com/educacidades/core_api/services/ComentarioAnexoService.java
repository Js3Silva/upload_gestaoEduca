package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.comentarioAnexo.*;
import com.educacidades.core_api.mapper.ComentarioAnexoMapper;
import com.educacidades.core_api.models.Comentario;
import com.educacidades.core_api.models.ComentarioAnexo;
import com.educacidades.core_api.repositories.ComentarioAnexoRepository;
import com.educacidades.core_api.repositories.ComentarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioAnexoService {

    private final ComentarioAnexoRepository anexoRepository;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioAnexoMapper mapper;

    public ComentarioAnexoService(ComentarioAnexoRepository anexoRepository,
                                  ComentarioRepository comentarioRepository,
                                  ComentarioAnexoMapper mapper) {
        this.anexoRepository = anexoRepository;
        this.comentarioRepository = comentarioRepository;
        this.mapper = mapper;
    }

    public ComentarioAnexoResponseDTO salvarAnexo(ComentarioAnexoRequestDTO dto) {
        Comentario comentario = comentarioRepository.findById(dto.getComentarioId())
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado."));

        // Validação simples de tipo de arquivo
        if (!dto.getTipoArquivo().matches("(?i)image/.*|application/pdf|text/plain")) {
            throw new IllegalArgumentException("Tipo de arquivo inválido. Permitidos: imagens, PDF e TXT.");
        }

        ComentarioAnexo anexo = mapper.toEntity(dto, comentario);
        anexoRepository.save(anexo);
        return mapper.toDto(anexo);
    }

    public List<ComentarioAnexoResponseDTO> listarPorComentario(Long comentarioId) {
        return anexoRepository.findByComentarioId(comentarioId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ComentarioAnexo buscarPorId(Long id) {
        return anexoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anexo não encontrado."));
    }

    public void excluirAnexo(Long id) {
        ComentarioAnexo anexo = buscarPorId(id);
        anexoRepository.delete(anexo);
    }
}
