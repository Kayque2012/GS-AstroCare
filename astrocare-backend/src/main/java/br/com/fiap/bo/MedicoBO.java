package br.com.fiap.bo;

import br.com.fiap.dao.MedicoDAO;
import br.com.fiap.entities.Medico;

import java.sql.SQLException;
import java.util.List;

public class MedicoBO {

    // Selecionar todos os médicos
    public List<Medico> selecionarBo() throws ClassNotFoundException, SQLException {
        MedicoDAO dao = new MedicoDAO();
        return dao.selecionar();
    }

    // Buscar médico por ID
    public Medico buscarPorIdBo(int id) throws ClassNotFoundException, SQLException {
        MedicoDAO dao = new MedicoDAO();
        return dao.buscarPorId(id);
    }

    // Inserir médico com validações
    public void inserirBo(Medico medico) throws ClassNotFoundException, SQLException {
        // Regra de negócio: CRM obrigatório
        if (medico.getCrm() == null || medico.getCrm().trim().isEmpty()) {
            throw new IllegalArgumentException("O CRM do médico é obrigatório.");
        }
        // Regra de negócio: nome obrigatório
        if (medico.getNome() == null || medico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do médico é obrigatório.");
        }
        // Regra de negócio: especialidade obrigatória
        if (medico.getEspecialidade() == null || medico.getEspecialidade().trim().isEmpty()) {
            throw new IllegalArgumentException("A especialidade do médico é obrigatória.");
        }
        MedicoDAO dao = new MedicoDAO();
        dao.inserir(medico);
    }

    // Atualizar médico com validações
    public void atualizarBo(Medico medico) throws ClassNotFoundException, SQLException {
        if (medico.getNome() == null || medico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do médico é obrigatório.");
        }
        MedicoDAO dao = new MedicoDAO();
        dao.atualizar(medico);
    }

    // Deletar médico
    public void deletarBo(int id) throws ClassNotFoundException, SQLException {
        MedicoDAO dao = new MedicoDAO();
        dao.deletar(id);
    }
}
