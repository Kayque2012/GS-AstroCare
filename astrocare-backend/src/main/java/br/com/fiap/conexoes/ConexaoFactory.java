package br.com.fiap.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    // Método de conexão com o banco de dados Oracle FIAP
    public Connection conexao() throws ClassNotFoundException, SQLException {

        // Driver Oracle
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // Retorna a conexão com o banco de dados
        return DriverManager.getConnection(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl",
                "RM567980",
                "210504"
        );
    }
}
