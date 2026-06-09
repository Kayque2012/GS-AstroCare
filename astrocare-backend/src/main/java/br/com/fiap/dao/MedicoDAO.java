package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // INSERT
    public String inserir(Medico medico) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO T_AC_MEDICO (ID_MEDICO, NOME, CRM, ESPECIALIDADE, DISPONIVEL) " +
                "VALUES (?, ?, ?, ?, ?)")) {

            stmt.setInt(1, medico.getId());
            stmt.setString(2, medico.getNome());
            stmt.setString(3, medico.getCrm());
            stmt.setString(4, medico.getEspecialidade());
            stmt.setString(5, medico.isDisponivel() ? "S" : "N");
            stmt.execute();
        }
        return "Médico cadastrado com sucesso!";
    }

    // DELETE
    public String deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM T_AC_MEDICO WHERE ID_MEDICO = ?")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
        return "Médico removido com sucesso!";
    }

    // UPDATE
    public String atualizar(Medico medico) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE T_AC_MEDICO SET NOME = ?, CRM = ?, ESPECIALIDADE = ?, DISPONIVEL = ? " +
                "WHERE ID_MEDICO = ?")) {

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.isDisponivel() ? "S" : "N");
            stmt.setInt(5, medico.getId());
            stmt.executeUpdate();
        }
        return "Médico atualizado com sucesso!";
    }

    // SELECT ALL
    public List<Medico> selecionar() throws SQLException, ClassNotFoundException {
        List<Medico> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM T_AC_MEDICO ORDER BY ID_MEDICO");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("ID_MEDICO"));
                m.setNome(rs.getString("NOME"));
                m.setCrm(rs.getString("CRM"));
                m.setEspecialidade(rs.getString("ESPECIALIDADE"));
                m.setDisponivel("S".equals(rs.getString("DISPONIVEL")));
                lista.add(m);
            }
        }
        return lista;
    }

    // SELECT BY ID
    public Medico buscarPorId(int id) throws SQLException, ClassNotFoundException {
        Medico medico = null;
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM T_AC_MEDICO WHERE ID_MEDICO = ?")) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico();
                    medico.setId(rs.getInt("ID_MEDICO"));
                    medico.setNome(rs.getString("NOME"));
                    medico.setCrm(rs.getString("CRM"));
                    medico.setEspecialidade(rs.getString("ESPECIALIDADE"));
                    medico.setDisponivel("S".equals(rs.getString("DISPONIVEL")));
                }
            }
        }
        return medico;
    }
}
