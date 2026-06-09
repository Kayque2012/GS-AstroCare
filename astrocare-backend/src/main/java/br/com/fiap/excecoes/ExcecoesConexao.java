package br.com.fiap.excecoes;

public class ExcecoesConexao extends Exception {

    public ExcecoesConexao() {
    }

    public ExcecoesConexao(Exception e) {
        super();
        if (e.getClass().toString().equals("class java.lang.ClassNotFoundException")) {
            System.out.println("Erro no driver, sem comunicação com o banco de dados.");
        } else if (e.getClass().toString().equals("class java.sql.SQLException")) {
            System.out.println("Informações de acesso incorretas, acesso negado.");
        } else {
            e.printStackTrace();
        }
    }
}
