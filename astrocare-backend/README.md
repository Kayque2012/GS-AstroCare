# 🏥 AstroCare Remote Health

**Sistema de Gestão de Telemedicina para Regiões Isoladas**

API Restful desenvolvida com Java + Quarkus + Oracle DB.

---

## 👥 Equipe

| Aluno | RM |
|-------|-----|
| Kayque | RM567980 |
| Eric Maciel | RM567398 |
| Gabriel Correa | RM567903 |

**Turma:** 1TDSPB

---

## 🚀 Tecnologias

- Java 17
- Quarkus 3.34.6
- Oracle Database (FIAP)
- Maven 3.15.0
- Gson 2.13.1
- Jackson (quarkus-rest-jackson)

---

## ⚙️ Como Executar

### Pré-requisitos
- Java JDK 21
- Maven 3.9+
- Acesso ao Oracle FIAP (rede FIAP ou VPN)

### 1. Clonar o repositório
```bash
git clone https://github.com/SEU_USUARIO/astrocare-remote-health.git
cd astrocare-remote-health
```

### 2. Configurar credenciais
Edite `src/main/resources/application.properties`:
```properties
quarkus.datasource.username=RM567980
quarkus.datasource.password=SUA_SENHA (senha no arquivo txt dentro do zip enviado pro prof)
```

### 3. Rodar em modo desenvolvimento
```bash
./mvnw quarkus:dev
```

A API estará disponível em: `http://localhost:8080`

---

## 📋 Endpoints

| Recurso | Método | URI | Descrição |
|---------|--------|-----|-----------|
| Pacientes | GET | /pacientes | Listar todos |
| Pacientes | GET | /pacientes/{id} | Buscar por ID |
| Pacientes | POST | /pacientes | Cadastrar |
| Pacientes | PUT | /pacientes/{id} | Atualizar |
| Pacientes | DELETE | /pacientes/{id} | Remover |
| Médicos | GET | /medicos | Listar todos |
| Médicos | GET | /medicos/{id} | Buscar por ID |
| Médicos | POST | /medicos | Cadastrar |
| Médicos | PUT | /medicos/{id} | Atualizar |
| Médicos | DELETE | /medicos/{id} | Remover |
| Consultas | GET | /consultas | Listar todas |
| Consultas | GET | /consultas/{id} | Buscar por ID |
| Consultas | GET | /consultas/paciente/{id} | Consultas do paciente |
| Consultas | POST | /consultas | Agendar |
| Consultas | PUT | /consultas/{id} | Atualizar |
| Consultas | DELETE | /consultas/{id} | Remover |

---

## 🏗️ Estrutura do Projeto

```
src/main/java/br/com/fiap/
├── CorsFilter.java              # Configuração CORS
├── conexoes/
│   └── ConexaoFactory.java      # Conexão com Oracle
├── excecoes/
│   └── ExcecoesConexao.java     # Tratamento de exceções
├── entities/
│   ├── Paciente.java
│   ├── Medico.java
│   └── Consulta.java
├── dao/
│   ├── PacienteDAO.java         # CRUD Paciente
│   ├── MedicoDAO.java           # CRUD Médico
│   └── ConsultaDAO.java         # CRUD Consulta
├── bo/
│   ├── PacienteBO.java          # Regras de negócio
│   ├── MedicoBO.java
│   └── ConsultaBO.java
└── resource/
    ├── PacienteResource.java    # Endpoints REST
    ├── MedicoResource.java
    └── ConsultaResource.java
```
