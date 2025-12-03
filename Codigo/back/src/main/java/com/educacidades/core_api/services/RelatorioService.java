package com.educacidades.core_api.services;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioService {

    private DataSource dataSource;
    private String diretorio = "relatorios";
    private String relatorio = "ec-analise-final.jasper";

    public RelatorioService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] gerar(Long idProjetoProduto) {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(String.format("/%s/%s", diretorio, relatorio));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("projeto_produto_id", idProjetoProduto);

            JasperPrint print = JasperFillManager.fillReport(stream, parametros, dataSource.getConnection());
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }
    }
}
