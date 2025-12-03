package com.educacidades.core_api.models;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.educacidades.core_api.services.ProjetoService;
import com.educacidades.core_api.services.TarefaService;
import com.educacidades.core_api.services.ProdutoProjetoService;

@Component
public class TarefasAgendadas {

    private final TarefaService tarefaService;
    private final ProdutoProjetoService produtoService;
    private final ProjetoService projetoService;

    public TarefasAgendadas(
        TarefaService tarefaService,
        ProdutoProjetoService produtoService,
        ProjetoService projetoService
    ) {
        this.tarefaService = tarefaService;
        this.produtoService = produtoService;
        this.projetoService = projetoService;
    }
    

    @Scheduled(cron = "0 0 8 * * ?") 
    public void executarVencimentos() {

        tarefaService.buscarTarefasComPrazoProximo();
        produtoService.buscarProdutosComPrazoProximo();
        projetoService.buscarProjetosComPrazoProximo();

        // Deixar caso tenha algum método de log ou notificação no futuro
        System.out.println("Tarefas de verificação de vencimentos executadas às 08:00.");
    }
}

