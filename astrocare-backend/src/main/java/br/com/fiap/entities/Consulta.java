package br.com.fiap.entities;

public class Consulta {

    private int id;
    private int idPaciente;
    private int idMedico;
    private String dataHora;
    private String status;
    private String descricao;

    public Consulta() {
        super();
    }

    public Consulta(int id, int idPaciente, int idMedico, String dataHora, String status, String descricao) {
        super();
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.dataHora = dataHora;
        this.status = status;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                ", dataHora='" + dataHora + '\'' +
                ", status='" + status + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
