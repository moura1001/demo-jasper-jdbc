package org.example;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //abrirJrxml("01");
        //abrirJrxml("02");
        //abrirJrxml("09");
        //abrirJrxml("18");
        //exportarParaPDF("18");
        //abrirArquivoJasper("09");
        abrirArquivoJasperComSubRelatorio("10");
    }

    private static void abrirArquivoJasperComSubRelatorio(String numero) {
        Connection connection = JdbcConnection.connection();
        JasperService service = new JasperService();
        service.addParam("SUB_REPORT_DIR", "relatorios/jasper/");
        service.abrirArquivoJasper("relatorios/jasper/funcionarios-"+numero+".jasper", connection);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void abrirArquivoJasper(String numero) {
        Connection connection = JdbcConnection.connection();
        JasperService service = new JasperService();
        if("09".equals(numero)) {
            service.addParam("NIVEL_DESC", "TREINAMENTO");
            service.addParam("UF", "RJ");
        }
        service.abrirArquivoJasper("relatorios/jasper/funcionarios-"+numero+".jasper", connection);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exportarParaPDF(String numero) {
        Connection connection = JdbcConnection.connection();
        JasperService service = new JasperService();
        String nomeArquivo = "jasper-" + numero + "-" + UUID.randomUUID() + ".pdf";
        String resourceUrl = new File("src/main/resources/relatorios/pdf").getAbsolutePath();
        String destino = resourceUrl+"\\"+nomeArquivo;
        service.exportarParaPDF("relatorios/jrxml/funcionarios-"+numero+".jrxml", connection, destino);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void abrirJrxml(String numero) {
        Connection connection = JdbcConnection.connection();
        JasperService service = new JasperService();
        if("09".equals(numero)) {
            service.addParam("NIVEL_DESC", "JUNIOR");
            service.addParam("UF", "SP");
        }
        service.abrirJasperViewer("relatorios/jrxml/funcionarios-"+numero+".jrxml", connection);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}