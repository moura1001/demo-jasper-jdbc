package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //abrirJrxml("01");
        //abrirJrxml("02");
        //abrirJrxml("09");
        abrirJrxml("18");
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