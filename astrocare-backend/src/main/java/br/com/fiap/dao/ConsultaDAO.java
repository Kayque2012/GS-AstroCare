package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    // INSERT
    public String inserir(Consulta consulta) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO T_AC_CONSULTA (ID_CONSULTA, ID_PACIENTE, ID_MEDICO, DATA_HORA, STATUS, DESCRICAO) " +
                "VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD HH24:MI'), ?, ?)")) {

            stmt.setInt(1, consulta.getId());
            stmt.setInt(2, consulta.getIdPaciente());
            stmt.setInt(3, consulta.getIdMedico());
            stmt.setString(4, consulta.getDataHora());
            stmt.setString(5, consulta.getStatus());
            stmt.setString(6, consulta.getDescricao());
            stmt.execute();
        }
        return "Consulta agendada com sucesso!";
    }

    // DELETE
    public String deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM T_AC_CONSULTA WHERE ID_CONSULTA = ?")) {

            stmt.setInt(1, id);
            stmt.execute();
        }
        return "Consulta removida com sucesso!";
    }

    // UPDATE
    public String atualizar(Consulta consulta) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE T_AC_CONSULTA SET ID_PACIENTE = ?, ID_MEDICO = ?, " +
                "DATA_HORA = TO_DATE(?, 'YYYY-MM-DD HH24:MI'), STATUS = ?, DESCRICAO = ? " +
                "WHERE ID_CONSULTA = ?")) {

            stmt.setInt(1, consulta.getIdPaciente());
            stmt.setInt(2, consulta.getIdMedico());
            stmt.setString(3, consulta.getDataHora());
            stmt.setString(4, consulta.getStatus());
            stmt.setString(5, consulta.getDescricao());
            stmt.setInt(6, consulta.getId());
            stmt.executeUpdate();
        }
        return "Consulta atualizada com sucesso!";
    }

    // SELECT ALL
    public List<Consulta> selecionar() throws SQLException, ClassNotFoundException {
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, " +
                "TO_CHAR(DATA_HORA, 'YYYY-MM-DD HH24:MI') AS DATA_HORA, STATUS, DESCRICAO " +
                "FROM T_AC_CONSULTA ORDER BY DATA_HORA");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("ID_CONSULTA"));
                c.setIdPaciente(rs.getInt("ID_PACIENTE"));
                c.setIdMedico(rs.getInt("ID_MEDICO"));
                c.setDataHora(rs.getString("DATA_HORA"));
                c.setStatus(rs.getString("STATUS"));
                c.setDescricao(rs.getString("DESCRICAO"));
                lista.add(c);
            }
        }
        return lista;
    }

    // SELECT BY ID
    public Consulta buscarPorId(int id) throws SQLException, ClassNotFoundException {
        Consulta consulta = null;
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, " +
                "TO_CHAR(DATA_HORA, 'YYYY-MM-DD HH24:MI') AS DATA_HORA, STATUS, DESCRICAO " +
                "FROM T_AC_CONSULTA WHERE ID_CONSULTA = ?")) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    consulta = new Consulta();
                    consulta.setId(rs.getInt("ID_CONSULTA"));
                    consulta.setIdPaciente(rs.getInt("ID_PACIENTE"));
                    consulta.setIdMedico(rs.getInt("ID_MEDICO"));
                    consulta.setDataHora(rs.getString("DATA_HORA"));
                    consulta.setStatus(rs.getString("STATUS"));
                    consulta.setDescricao(rs.getString("DESCRICAO"));
                }
            }
        }
        return consulta;
    }

    // SELECT BY PACIENTE
    public List<Consulta> buscarPorPaciente(int idPaciente) throws SQLException, ClassNotFoundException {
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT ID_CONSULTA, ID_PACIENTE, ID_MEDICO, " +
                "TO_CHAR(DATA_HORA, 'YYYY-MM-DD HH24:MI') AS DATA_HORA, STATUS, DESCRICAO " +
                "FROM T_AC_CONSULTA WHERE ID_PACIENTE = ? ORDER BY DATA_HORA")) {

            stmt.setInt(1, idPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    c.setId(rs.getInt("ID_CONSULTA"));
                    c.setIdPaciente(rs.getInt("ID_PACIENTE"));
                    c.setIdMedico(rs.getInt("ID_MEDICO"));
                    c.setDataHora(rs.getString("DATA_HORA"));
                    c.setStatus(rs.getString("STATUS"));
                    c.setDescricao(rs.getString("DESCRICAO"));
                    lista.add(c);
                }
            }
        }
        return lista;
    }
}
