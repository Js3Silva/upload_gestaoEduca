package com.educacidades.core_api.controllers;

import com.educacidades.core_api.dto.comentario.ComentarioRequestDTO;
import com.educacidades.core_api.dto.comentario.ComentarioResponseDTO;
import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoRequestDTO;
import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoResponseDTO;
import com.educacidades.core_api.dto.projeto.ProjetoCreateRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoProdutoProjetoCreateRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoPutRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.dto.usuario.UsuarioDTO;
import com.educacidades.core_api.dto.usuario.UsuarioResumoDTO;
import com.educacidades.core_api.mapper.ComentarioMapper;
import com.educacidades.core_api.mapper.ProdutoProjetoMapper;
import com.educacidades.core_api.mapper.ProjetoMapper;
import com.educacidades.core_api.mapper.UsuarioMapper;
import com.educacidades.core_api.models.Projeto;
import com.educacidades.core_api.services.ComentarioService;
import com.educacidades.core_api.services.ProdutoProjetoService;
import com.educacidades.core_api.services.ProjetoService;
import com.educacidades.core_api.repositories.UsuarioRepository;
import com.educacidades.core_api.models.Usuario;
import com.educacidades.core_api.enums.PerfilAcesso;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/projetos")
public class ProjetoController {

    private final ProdutoProjetoMapper produtoProjetoMapper;
    private final ProjetoMapper projetoMapper;
    private final ProjetoService projetoService;
    private final UsuarioMapper usuarioMapper;
    private final ProdutoProjetoService produtoProjetoService;
    private final ComentarioService comentarioService;
    private final UsuarioRepository usuarioRepository;

    public ProjetoController(ProdutoProjetoMapper produtoProjetoMapper,
                             ProjetoMapper projetoMapper,
                             ProjetoService projetoService,
                             UsuarioMapper usuarioMapper,
                             ProdutoProjetoService produtoProjetoService,
                             ComentarioService comentarioService,
                             UsuarioRepository usuarioRepository) {
        this.produtoProjetoMapper = produtoProjetoMapper;
        this.projetoMapper = projetoMapper;
        this.projetoService = projetoService;
        this.usuarioMapper = usuarioMapper;
        this.produtoProjetoService = produtoProjetoService;
        this.comentarioService = comentarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> getProjetos() {
        List<Projeto> projetos = projetoService.findAll();
        List<ProjetoResponseDTO> response = projetos.stream()
                .map(projetoMapper::projetoToProjetoResponseDTO)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{projetoId}")
    public ResponseEntity<ProjetoResponseDTO> getProjetoById(@PathVariable Long projetoId) {
        return projetoService.findById(projetoId).map(projeto -> ResponseEntity.ok()
                        .body(projetoMapper
                                .projetoToProjetoResponseDTO(projeto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{projetoId}/produtos")
    public ResponseEntity<List<ProdutoProjetoResponseDTO>> getProdutosProjetoByProjetoId(@PathVariable Long projetoId) {
        return ResponseEntity.ok().body(projetoService
                .findProdutosByProjetoId(projetoId)
                .stream()
                .map(produtoProjetoMapper::produtoProjetoToProdutoProjetoResponseDto)
                .toList());
    }

    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> postProjeto(@RequestBody @Valid ProjetoCreateRequestDTO projetoCreateRequestDTO) {
        Projeto projetoSalvo = projetoService.criar(projetoCreateRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(projetoSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(projetoMapper.projetoToProjetoResponseDTO(projetoSalvo));
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProjetoResponseDTO> postProjetoComProdutoProjeto(@RequestBody @Valid ProjetoProdutoProjetoCreateRequestDTO projetoCreateRequestDTO) {
        Projeto projetoSalvo = projetoService.criarComProdutosProjeto(projetoCreateRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(projetoSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(projetoMapper.projetoToProjetoResponseDTO((projetoSalvo)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> putProjeto(@PathVariable("id") Long projetoId,
                                                         @RequestBody @Valid ProjetoPutRequestDTO projetoPutRequestDTO) {
        return ResponseEntity.ok().body(projetoMapper
                .projetoToProjetoResponseDTO(projetoService.update(projetoId, projetoPutRequestDTO)));
    }

    @PostMapping("/{projetoId}")
    public ResponseEntity<ProdutoProjetoResponseDTO> addProdutoToProjeto(@PathVariable Long projetoId,
                                                                         @RequestBody @Valid ProdutoProjetoRequestDTO produtoProjetoRequestDTO) {
        return ResponseEntity.ok().body(produtoProjetoMapper
                .produtoProjetoToProdutoProjetoResponseDto(produtoProjetoService
                        .addProdutoToProjeto(projetoId, produtoProjetoRequestDTO)));
    }

    @PostMapping("/{projetoId}/responsaveis/{usuarioId}")
    public ResponseEntity<List<UsuarioDTO>> addUsuarioToProjeto(@PathVariable Long projetoId,
                                                                @PathVariable Long usuarioId) {
        return ResponseEntity.ok().body(projetoService
                .addUsuarioToProjeto(projetoId, usuarioId)
                .stream()
                .map(usuarioMapper::usuarioToUsuarioDTO)
                .toList());
    }

    @DeleteMapping("/{projetoId}/responsaveis/{usuarioId}")
    public ResponseEntity<List<UsuarioDTO>> removeUsuarioFromProjeto(@PathVariable Long projetoId,
                                                                     @PathVariable Long usuarioId) {
        return ResponseEntity.ok().body(projetoService
                .removeUsuarioToProjeto(projetoId, usuarioId)
                .stream()
                .map(usuarioMapper::usuarioToUsuarioDTO)
                .toList());
    }

    @DeleteMapping("/{projetoId}/produtos/{produtoId}")
    public ResponseEntity<Void> removeProdutoFromProjeto(@PathVariable Long projetoId, @PathVariable Long produtoId) {
        produtoProjetoService.delete(produtoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Long id) {
        projetoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projetoId}/comentarios")
    public ResponseEntity<ComentarioResponseDTO> putProjetoWithComentario(@PathVariable Long projetoId,
                                                                      @RequestBody @Valid ComentarioRequestDTO dto) {
        return ResponseEntity.ok().body(comentarioService.comentar(dto));
    }

    @GetMapping("/{id}/comentario")
    public ResponseEntity<List<ComentarioResponseDTO>> listAllComentario(@PathVariable Long projetoId){
        return ResponseEntity.ok().body(comentarioService.findByTarefa(projetoId));
    }

    @DeleteMapping("/{projetoId}/comentarios/{comentarioId}")
    public ResponseEntity<Void> deleteComentarioFromProjeto(@PathVariable Long comentarioId) {
        comentarioService.deletarComentario(comentarioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meus-projetos")
    public ResponseEntity<List<ProjetoResponseDTO>> getMeusProjetos(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return ResponseEntity.status(401).build();
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long usuarioId = Long.parseLong(jwt.getSubject());
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getPerfil() != PerfilAcesso.EXTERNO) {
            return ResponseEntity.status(403).build();
        }

        String cnpj = usuario.getLogin();
        List<ProjetoResponseDTO> projetos = projetoService.buscarProjetosPorClienteCnpj(cnpj);
        
        return ResponseEntity.ok().body(projetos);
    }

    @GetMapping("/{id}/usuarios")
    public ResponseEntity<List<UsuarioResumoDTO>> buscarUsuarios(@PathVariable Long id){
        List<UsuarioResumoDTO> resposta = projetoService.buscarUsuarios(id);
        return ResponseEntity.ok().body(resposta);
    }
}
