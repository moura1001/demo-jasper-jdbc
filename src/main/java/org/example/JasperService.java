package org.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class JasperService {
    private Map<String, Object> params = new HashMap<>();

    public void addParam(String key, Object value) {
        params.put(key, value);
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
