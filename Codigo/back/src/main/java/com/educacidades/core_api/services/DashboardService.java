package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.dashboard.DashboardResponse;
import com.educacidades.core_api.repositories.ClienteRepository;
import com.educacidades.core_api.repositories.ComentarioRepository;
import com.educacidades.core_api.repositories.ProdutoRepository;
import com.educacidades.core_api.repositories.ProjetoRepository;
import com.educacidades.core_api.repositories.TarefaRepository;
import com.educacidades.core_api.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ProjetoRepository projetoRepository;
    private final ProjetoService projetoService;

    public DashboardService(ProjetoRepository projetoRepository,
                            ProjetoService projetoService) {
        this.projetoRepository = projetoRepository;
        this.projetoService = projetoService;
    }

    public DashboardResponse getDashboard() {
        return new DashboardResponse(
                projetoRepository.count(),
                projetoRepository.countProjetosNaoIniciados(),
                projetoRepository.countProjetosPausados(),
                projetoRepository.countProjetosFinalizados(),
                projetoRepository.countProjetosAtrasados(),
                projetoRepository.mediaTempoDeConclusaoDeProjetoDias(),
                projetoService.getEstatisticasMensaisAnoAtual()
                );
    }
}
