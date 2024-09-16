package org.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class JasperService {
    private Map<String, Object> params = new HashMap<>();

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public void abrirJrxmlComSubRelatorio(String arqMaster, String arqSub, Connection connection) {
        try {
            JasperReport subReport = compilarJrxml(arqSub);
            this.params.put("SUB_REPORT_PARAM", subReport);
            JasperReport masterReport = compilarJrxml(arqMaster);

            JasperPrint print = JasperFillManager.fillReport(masterReport, this.params, connection);
            JasperViewer viewer = new JasperViewer(print);
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void abrirArquivoJasper(String arquivoJasper, Connection connection) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(arquivoJasper);
            JasperPrint print = JasperFillManager.fillReport(is, this.params, connection);
            JasperViewer viewer = new JasperViewer(print);
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportarParaPDF(String jrxml, Connection connection, String destino) {
        JasperReport report = compilarJrxml(jrxml);
        try {
            OutputStream os = new FileOutputStream(destino);
            JasperPrint print = JasperFillManager.fillReport(report, this.params, connection);
            JasperExportManager.exportReportToPdfStream(print, os);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void abrirJasperViewer(String jrxml, Connection connection) {
        JasperReport report = compilarJrxml(jrxml);
        try {
            JasperPrint print = JasperFillManager.fillReport(report, this.params, connection);
            JasperViewer viewer = new JasperViewer(print);
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    private JasperReport compilarJrxml(String arquivo) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(arquivo);
        try {
            return JasperCompileManager.compileReport(is);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
