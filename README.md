# AstroCare Remote Health

Sistema de gestao de telemedicina para regioes isoladas.

**Frontend** React + TypeScript + Vite — hospedado no Vercel
**Backend** Java + Quarkus + Oracle DB — roda localmente

---

## Equipe

| Aluno | RM |
|---|---|
| Kayque | RM567980 |
| Eric Maciel | RM567398 |
| Gabriel Correa | RM567903 |

**Turma:** 1TDSPB

---

## Repositorio e Deploy

- GitHub: https://github.com/Kayque2012/GS-AstroCare
- Frontend (Vercel): https://astrocare-frontend.vercel.app

---

## Estrutura do Projeto

```
astrocare-remote-health/
  astrocare-frontend/   # React + Vite (TypeScript)
  astrocare-backend/    # Quarkus + Java + Oracle
  deploy-com-tunnel.ps1 # Script para expor backend local no Vercel
```

---

## Como Rodar

### Pre-requisitos

- Java JDK 21
- Maven 3.9+
- Node.js 18+
- Acesso ao Oracle FIAP (rede FIAP ou VPN)
- Cloudflare Tunnel instalado (`winget install Cloudflare.cloudflared`)

---

### 1. Backend (Quarkus)

```powershell
cd astrocare-backend
./mvnw quarkus:dev
```

A API fica disponivel em `http://localhost:8080`. Mantenha este terminal aberto.

---

### 2. Conectar o Vercel ao backend local (tunnel)

O frontend esta hospedado no Vercel (HTTPS). Para ele se comunicar com o backend local
e necessario criar um tunnel HTTPS via Cloudflare — sem conta, sem cadastro.

Abra outro PowerShell na raiz do projeto e rode:

```powershell
.\deploy-com-tunnel.ps1
```

O script vai:
1. Criar um tunnel HTTPS publico apontando para `localhost:8080`
2. Atualizar a variavel `VITE_API_URL` no Vercel com a URL do tunnel
3. Fazer o redeploy do frontend automaticamente

Apos concluir, o site no Vercel estara conectado ao seu backend local.

> **Importante:** o processo `cloudflared` continua rodando em background mesmo apos fechar
> o PowerShell. Para verificar: `Get-Process cloudflared`
> Para encerrar: `Stop-Process -Name cloudflared -Force`

> Ao reiniciar o computador ou encerrar o tunnel, rode o script novamente — a URL muda
> a cada nova sessao do tunnel.

---

### 3. Frontend local (opcional)

Se preferir rodar o frontend localmente sem o Vercel:

```powershell
cd astrocare-frontend
npm install
npm run dev
```

Acesse em `http://localhost:5173`. Neste modo o frontend ja aponta para `localhost:8080`
sem precisar do tunnel.

---

## Variaveis de Ambiente

O frontend usa a variavel `VITE_API_URL` para saber onde esta o backend.

| Variavel | Padrao | Descricao |
|---|---|---|
| `VITE_API_URL` | `http://localhost:8080` | URL base da API |

Copie `.env.example` para `.env` se quiser customizar localmente:

```powershell
cp astrocare-frontend/.env.example astrocare-frontend/.env
```
