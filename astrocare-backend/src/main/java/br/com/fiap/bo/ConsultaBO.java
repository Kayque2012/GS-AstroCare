package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.entities.Consulta;

import java.sql.SQLException;
import java.util.List;

public class ConsultaBO {

    // Selecionar todas as consultas
    public List<Consulta> selecionarBo() throws ClassNotFoundException, SQLException {
        ConsultaDAO dao = new ConsultaDAO();
        return dao.selecionar();
    }

    // Buscar consulta por ID
    public Consulta buscarPorIdBo(int id) throws ClassNotFoundException, SQLException {
        ConsultaDAO dao = new ConsultaDAO();
        return dao.buscarPorId(id);
    }

    // Buscar consultas de um paciente
    public List<Consulta> buscarPorPacienteBo(int idPaciente) throws ClassNotFoundException, SQLException {
        ConsultaDAO dao = new ConsultaDAO();
        return dao.buscarPorPaciente(idPaciente);
    }

    // Inserir consulta com validações
    public void inserirBo(Consulta consulta) throws ClassNotFoundException, SQLException {
        // Regra de negócio: status deve ser válido
        String status = consulta.getStatus();
        if (status == null || (!status.equals("AGENDADA") && !status.equals("REALIZADA") && !status.equals("CANCELADA"))) {
            throw new IllegalArgumentException("Status inválido. Use: AGENDADA, REALIZADA ou CANCELADA.");
        }
        // Regra de negócio: data/hora obrigatória
        if (consulta.getDataHora() == null || consulta.getDataHora().trim().isEmpty()) {
            throw new IllegalArgumentException("A data e hora da consulta são obrigatórias.");
        }
        // Regra de negócio: paciente e médico devem ser informados
        if (consulta.getIdPaciente() <= 0) {
            throw new IllegalArgumentException("O paciente é obrigatório.");
        }
        if (consulta.getIdMedico() <= 0) {
            throw new IllegalArgumentException("O médico é obrigatório.");
        }
        ConsultaDAO dao = new ConsultaDAO();
        dao.inserir(consulta);
    }

    // Atualizar consulta com validações
    public void atualizarBo(Consulta consulta) throws ClassNotFoundException, SQLException {
        String status = consulta.getStatus();
        if (status == null || (!status.equals("AGENDADA") && !status.equals("REALIZADA") && !status.equals("CANCELADA"))) {
            throw new IllegalArgumentException("Status inválido. Use: AGENDADA, REALIZADA ou CANCELADA.");
        }
        ConsultaDAO dao = new ConsultaDAO();
        dao.atualizar(consulta);
    }

    // Deletar consulta
    public void deletarBo(int id) throws ClassNotFoundException, SQLException {
        ConsultaDAO dao = new ConsultaDAO();
        dao.deletar(id);
    }
}
