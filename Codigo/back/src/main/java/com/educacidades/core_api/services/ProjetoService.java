package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.produtoProjeto.ProdutoProjetoRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoCreateRequestDTO;
import com.educacidades.core_api.dto.dashboard.ProjetoEstatisticasMensaisResponse;
import com.educacidades.core_api.dto.projeto.ProjetoProdutoProjetoCreateRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoPutRequestDTO;
import com.educacidades.core_api.dto.projeto.ProjetoResponseDTO;
import com.educacidades.core_api.dto.usuario.UsuarioClienteDTO;
import com.educacidades.core_api.dto.usuario.UsuarioResumoDTO;
import com.educacidades.core_api.mapper.ProdutoProjetoMapper;
import com.educacidades.core_api.mapper.ProjetoMapper;
import com.educacidades.core_api.models.*;
import com.educacidades.core_api.repositories.*;
import com.educacidades.core_api.utils.EmailTemplateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final ObjectMapper objectMapper;
    private final ProjetoMapper projetoMapper;
    private final ProdutoProjetoMapper produtoProjetoMapper;
    private final CodigoRepository codigoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    private final UsuarioService usuarioService;
    private final EmailService emailService;

    public ProjetoService(ProjetoRepository projetoRepository,
            CodigoRepository codigoRepository,
            ProjetoMapper projetoMapper,
            ProdutoProjetoMapper produtoProjetoMapper,
            ClienteRepository clienteRepository,
            UsuarioService usuarioService,
            EmailService emailService,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository) {
        this.projetoRepository = projetoRepository;
        this.objectMapper = new ObjectMapper();
        this.projetoMapper = projetoMapper;
        this.produtoProjetoMapper = produtoProjetoMapper;
        this.codigoRepository = codigoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    @Transactional
    public Projeto criar(ProjetoCreateRequestDTO projetoCreateRequestDTO) {
        Cliente cliente = clienteRepository.findById(projetoCreateRequestDTO.idCliente())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente não encontrado com id: " + projetoCreateRequestDTO.idCliente()));

        Projeto projeto = projetoMapper.projetoCreateRequestDTOToProjeto(projetoCreateRequestDTO);
        projeto.setCodigo(codigoRepository.getCodigo());
        projeto.setCliente(cliente);
        projetoRepository.save(projeto);

        // Envio de email não pode falhar o fluxo de criação de projetos, portanto,
        // grava e depois envia o e-mail com os dados de acesso.
        Optional<UsuarioClienteDTO> usuario = usuarioService.criar(cliente);
        if (usuario.isPresent()) {
            String corpo = EmailTemplateUtil.gerarEmailAcesso(usuario.get().login(), usuario.get().senha());

            try {
                emailService.enviarEmail(cliente.getEmail(), "Acesso à Plataforma Educa Cidades", corpo);
            } catch (Exception e) {
                log.error("Erro ao enviar e-mail para o cliente {}: {}", cliente.getEmail(), e.getMessage(), e);
            }
        }

        return projeto;
    }

    public Projeto criarComProdutosProjeto(ProjetoProdutoProjetoCreateRequestDTO projetoCreateRequestDTO) {

        Cliente cliente = clienteRepository.findById(projetoCreateRequestDTO.idCliente())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente não encontrado com id: " + projetoCreateRequestDTO.idCliente()));

        Projeto projeto = projetoMapper.projetoProdutoProjetoCreateRequestDTOToProjeto(projetoCreateRequestDTO);
        projeto.setCodigo(codigoRepository.getCodigo());
        projeto.setCliente(cliente);
        Projeto projetoSalvo = save(projeto);

        List<ProdutoProjetoRequestDTO> produtosProjetoCreateDTOs = projetoCreateRequestDTO.produtosProjeto();
        for (ProdutoProjetoRequestDTO dto : produtosProjetoCreateDTOs) {
            Optional<Produto> produtoOptional = produtoRepository.findById(dto.idProduto());
            if (produtoOptional.isEmpty())
                throw new RuntimeException("Produto não encontrado com id: " + dto.idProduto());

            ProdutoProjeto produtoProjeto = produtoProjetoMapper
                    .produtoProjetoRequestDtoToProdutoProjeto(produtoOptional.get(), dto);
            projetoSalvo.addProdutoProjeto(produtoProjeto);
        }

        projetoRepository.save(projeto);

        // Envio de email não pode falhar o fluxo de criação de projetos, portanto,
        // grava e depois envia o e-mail com os dados de acesso.
        Optional<UsuarioClienteDTO> usuario = usuarioService.criar(cliente);
        if (usuario.isPresent()) {
            String corpo = EmailTemplateUtil.gerarEmailAcesso(usuario.get().login(), usuario.get().senha());

            try {
                emailService.enviarEmail(cliente.getEmail(), "Acesso à Plataforma Educa Cidades", corpo);
            } catch (Exception e) {
                log.error("Erro ao enviar e-mail para o cliente {}: {}", cliente.getEmail(), e.getMessage(), e);
            }
        }

        return projeto;
    }

    @Transactional
    public Projeto update(Long projetoId, ProjetoPutRequestDTO updatedProjeto) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(projetoId);
        if (projetoOptional.isEmpty())
            throw new RuntimeException("Projeto não encontrado");
        Projeto projeto = projetoOptional.get();
        projeto.setNome(updatedProjeto.nome());
        projeto.setObjetivo(updatedProjeto.objetivo());
        return projetoRepository.save(projeto);
    }

    @Transactional
    public List<Usuario> addUsuarioToProjeto(Long projetoId, Long usuarioId) {
        Projeto projeto = findOrThrow(projetoRepository, projetoId, "Projeto");
        Usuario usuario = findOrThrow(usuarioRepository, usuarioId, "Usuario");

        projeto.addUsuario(usuario);
        return projetoRepository.save(projeto).getUsuarios();
    }

    @Transactional
    public List<Usuario> removeUsuarioToProjeto(Long projetoId, Long usuarioId) {
        Projeto projeto = findOrThrow(projetoRepository, projetoId, "Projeto");
        Usuario usuario = findOrThrow(usuarioRepository, usuarioId, "Usuario");

        projeto.removeUsuario(usuario);
        return projetoRepository.save(projeto).getUsuarios();
    }

    private <T> T findOrThrow(JpaRepository<T, Long> repository, Long id, String nomeEntidade) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(nomeEntidade + " não encontrado(a)"));
    }

    public Projeto save(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Optional<Projeto> findById(Long id) {
        return projetoRepository.findById(id);
    }

    public List<Projeto> findAll() {
        return projetoRepository.findAll();
    }

    public List<ProdutoProjeto> findProdutosByProjetoId(Long projetoId) {
        Optional<Projeto> projetoOptional = projetoRepository.findById(projetoId);
        if (projetoOptional.isEmpty())
            throw new RuntimeException("Projeto não encontrado.");
        return projetoOptional.get().getProdutosProjeto();
    }

    @Transactional
    public void delete(Long id) {
        Projeto projetoFound = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        projetoRepository.delete(projetoFound);
    }

    public List<ProjetoEstatisticasMensaisResponse> getEstatisticasMensaisAnoAtual() {
        return projetoRepository.findEstatisticasMensaisAnoAtual().stream()
                .map(projection -> new ProjetoEstatisticasMensaisResponse(
                        projection.getMes(),
                        getNomeMes(projection.getMes()),
                        projection.getProjetosIniciados(),
                        projection.getProjetosFinalizados()))
                .collect(Collectors.toList());
    }

    private String getNomeMes(Integer mes) {
        if (mes == null || mes < 1 || mes > 12) {
            return "Inválido";
        }
        String[] meses = {
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        return meses[mes - 1];
    }

    public List<ProjetoResponseDTO> buscarProjetosComPrazoProximo() {
        List<Projeto> projetos = projetoRepository.findProjetosComPrazoProximo(LocalDate.now().plusDays(7));
        return projetos.stream()
                .map(Projeto::toDto)
                .toList();
    }

    public List<ProjetoResponseDTO> buscarProjetosPorClienteCnpj(String cnpj) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCnpj(cnpj);
        if (clienteOptional.isEmpty()) {
            return List.of();
        }
        Cliente cliente = clienteOptional.get();
        List<Projeto> projetos = projetoRepository.findByClienteId(cliente.getId());
        return projetos.stream()
                .map(projetoMapper::projetoToProjetoResponseDTO)
                .toList();
    }

    public List<UsuarioResumoDTO> buscarUsuarios(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow(() -> new RuntimeException("Projeto não encontrado."));
        return projeto.getUsuarios().stream().map(Usuario::toResumoDto).toList();
    }

}
