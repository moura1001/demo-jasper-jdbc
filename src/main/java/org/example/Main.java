package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        abrirJrxml("02");
    }

    private static void abrirJrxml(String numero) {
        Connection connection = JdbcConnection.connection();
        JasperService service = new JasperService();
        service.abrirJasperViewer("relatorios/jrxml/funcionarios-"+numero+".jrxml", connection);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}