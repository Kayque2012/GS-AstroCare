# AstroCare Remote Health

Sistema de gestão de telemedicina para regiões isoladas.

**Frontend** React + TypeScript + Vite — hospedado no Vercel  
**Backend** Java + Quarkus + Oracle DB — hospedado no Railway

---

## Equipe

| Aluno          | RM       |
| -------------- | -------- |
| Kayque         | RM567980 |
| Eric Maciel    | RM567398 |
| Gabriel Correa | RM567903 |

**Turma:** 1TDSPB

---

## Repositório e Deploy

- GitHub: https://github.com/Kayque2012/GS-AstroCare
- Frontend (Vercel): https://astrocare-frontend.vercel.app
- Backend (Railway): https://gs-astrocare-production.up.railway.app

---

## Estrutura do Projeto

```
astrocare-remote-health/
  astrocare-frontend/   # React + Vite (TypeScript)
  astrocare-backend/    # Quarkus + Java + Oracle
```

---

## Tecnologias

### Frontend

- React 19
- TypeScript
- Vite
- Fetch API

### Backend

- Java 17
- Quarkus 3.34.6
- Oracle Database (FIAP)
- Maven 3.15.0
- Gson 2.13.1
- Jackson (quarkus-rest-jackson)

---

## Como Rodar Localmente

### Pré-requisitos

- Java JDK 17
- Maven 3.9+
- Node.js 18+
- Acesso ao Oracle FIAP (rede FIAP ou VPN)

### 1. Backend (Quarkus)

```bash
cd astrocare-backend
./mvnw quarkus:dev
```

A API fica disponível em `http://localhost:8080`.

### 2. Frontend (React)

```bash
cd astrocare-frontend
npm install
npm run dev
```

O frontend fica disponível em `http://localhost:5173`.

> **Nota:** rodando localmente, o frontend aponta automaticamente para `http://localhost:8080`.
> O backend no Railway é utilizado apenas quando o usuário acessa o site pelo Vercel.
