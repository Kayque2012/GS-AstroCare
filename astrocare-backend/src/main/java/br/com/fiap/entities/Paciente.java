package br.com.fiap.entities;

public class Paciente {

    private int id;
    private String nome;
    private String cpf;
    private String localizacao;
    private int idade;
    private String condicaoMedica;

    public Paciente() {
        super();
    }

    public Paciente(int id, String nome, String cpf, String localizacao, int idade, String condicaoMedica) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.localizacao = localizacao;
        this.idade = idade;
        this.condicaoMedica = condicaoMedica;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getCondicaoMedica() { return condicaoMedica; }
    public void setCondicaoMedica(String condicaoMedica) { this.condicaoMedica = condicaoMedica; }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", idade=" + idade +
                ", condicaoMedica='" + condicaoMedica + '\'' +
                '}';
    }
}
