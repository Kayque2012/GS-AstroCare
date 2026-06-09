package br.com.fiap.bo;

import br.com.fiap.dao.PacienteDAO;
import br.com.fiap.entities.Paciente;

import java.sql.SQLException;
import java.util.List;

public class PacienteBO {

    // Selecionar todos os pacientes
    public List<Paciente> selecionarBo() throws ClassNotFoundException, SQLException {
        PacienteDAO dao = new PacienteDAO();
        return dao.selecionar();
    }

    // Buscar paciente por ID
    public Paciente buscarPorIdBo(int id) throws ClassNotFoundException, SQLException {
        PacienteDAO dao = new PacienteDAO();
        return dao.buscarPorId(id);
    }

    // Inserir paciente com validações
    public void inserirBo(Paciente paciente) throws ClassNotFoundException, SQLException {
        // Regra de negócio: nome obrigatório
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do paciente é obrigatório.");
        }
        // Regra de negócio: CPF deve ter 11 dígitos
        if (paciente.getCpf() == null || paciente.getCpf().replaceAll("[^0-9]", "").length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Informe 11 dígitos numéricos.");
        }
        // Regra de negócio: idade deve ser positiva
        if (paciente.getIdade() <= 0) {
            throw new IllegalArgumentException("A idade deve ser um valor positivo.");
        }
        PacienteDAO dao = new PacienteDAO();
        dao.inserir(paciente);
    }

    // Atualizar paciente com validações
    public void atualizarBo(Paciente paciente) throws ClassNotFoundException, SQLException {
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do paciente é obrigatório.");
        }
        if (paciente.getIdade() <= 0) {
            throw new IllegalArgumentException("A idade deve ser um valor positivo.");
        }
        PacienteDAO dao = new PacienteDAO();
        dao.atualizar(paciente);
    }

    // Deletar paciente
    public void deletarBo(int id) throws ClassNotFoundException, SQLException {
        PacienteDAO dao = new PacienteDAO();
        dao.deletar(id);
    }
}
