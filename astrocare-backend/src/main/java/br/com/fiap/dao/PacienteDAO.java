package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // INSERT
    public String inserir(Paciente paciente) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO T_AC_PACIENTE (ID_PACIENTE, NOME, CPF, LOCALIZACAO, IDADE, CONDICAO_MEDICA) " +
                "VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setInt(1, paciente.getId());
            stmt.setString(2, paciente.getNome());
            stmt.setString(3, paciente.getCpf());
            stmt.setString(4, paciente.getLocalizacao());
            stmt.setInt(5, paciente.getIdade());
            stmt.setString(6, paciente.getCondicaoMedica());
            stmt.execute();
        }
        return "Paciente cadastrado com sucesso!";
    }

    // DELETE
    public String deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM T_AC_PACIENTE WHERE ID_PACIENTE = ?")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
        return "Paciente removido com sucesso!";
    }

    // UPDATE
    public String atualizar(Paciente paciente) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE T_AC_PACIENTE SET NOME = ?, CPF = ?, LOCALIZACAO = ?, IDADE = ?, CONDICAO_MEDICA = ? " +
                "WHERE ID_PACIENTE = ?")) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getLocalizacao());
            stmt.setInt(4, paciente.getIdade());
            stmt.setString(5, paciente.getCondicaoMedica());
            stmt.setInt(6, paciente.getId());
            stmt.executeUpdate();
        }
        return "Paciente atualizado com sucesso!";
    }

    // SELECT ALL
    public List<Paciente> selecionar() throws SQLException, ClassNotFoundException {
        List<Paciente> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM T_AC_PACIENTE ORDER BY ID_PACIENTE");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("ID_PACIENTE"));
                p.setNome(rs.getString("NOME"));
                p.setCpf(rs.getString("CPF"));
                p.setLocalizacao(rs.getString("LOCALIZACAO"));
                p.setIdade(rs.getInt("IDADE"));
                p.setCondicaoMedica(rs.getString("CONDICAO_MEDICA"));
                lista.add(p);
            }
        }
        return lista;
    }

    // SELECT BY ID
    public Paciente buscarPorId(int id) throws SQLException, ClassNotFoundException {
        Paciente paciente = null;
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM T_AC_PACIENTE WHERE ID_PACIENTE = ?")) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    paciente.setId(rs.getInt("ID_PACIENTE"));
                    paciente.setNome(rs.getString("NOME"));
                    paciente.setCpf(rs.getString("CPF"));
                    paciente.setLocalizacao(rs.getString("LOCALIZACAO"));
                    paciente.setIdade(rs.getInt("IDADE"));
                    paciente.setCondicaoMedica(rs.getString("CONDICAO_MEDICA"));
                }
            }
        }
        return paciente;
    }
}
