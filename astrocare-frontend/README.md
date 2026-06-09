# AstroCare Frontend

Interface web do sistema AstroCare Remote Health, desenvolvida com React + TypeScript + Vite.

**Deploy:** https://astrocare-frontend.vercel.app

---

## Tecnologias

- React 19
- TypeScript
- Vite
- Fetch API

---

## Estrutura

```
src/
  components/   # Componentes reutilizaveis (Sidebar)
  pages/        # Dashboard, Pacientes, Medicos, Consultas
  services/     # api.ts — chamadas ao backend
  types/        # Tipos TypeScript (Paciente, Medico, Consulta)
```

---

## Como Rodar Localmente

```powershell
npm install
npm run dev
```

Acesse em `http://localhost:5173`. O backend deve estar rodando em `http://localhost:8080`.

---

## Variaveis de Ambiente

| Variavel | Padrao | Descricao |
|---|---|---|
| `VITE_API_URL` | `http://localhost:8080` | URL base da API |

Crie um arquivo `.env` baseado no `.env.example` para customizar.

---

## Conexao com Backend no Vercel

O Vercel serve o frontend via HTTPS. Para conectar ao backend local e necessario
usar um tunnel HTTPS — veja as instrucoes no README principal do projeto.
