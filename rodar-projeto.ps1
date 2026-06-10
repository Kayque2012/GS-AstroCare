Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy Bypass -Force

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   AstroCare Remote Health" -ForegroundColor Cyan
Write-Host "   Iniciando backend + frontend..." -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$root = $PSScriptRoot

# ── Backend ──────────────────────────────────────────────────────────────────
Write-Host "[1/3] Iniciando backend (Quarkus)..." -ForegroundColor Yellow
$backend = Start-Process -FilePath "powershell.exe" `
    -ArgumentList "-NoExit", "-Command", "cd '$root\astrocare-backend'; mvn quarkus:dev" `
    -PassThru
Write-Host "      Backend iniciado (porta 8080)" -ForegroundColor Green

# ── Frontend ─────────────────────────────────────────────────────────────────
Write-Host "[2/3] Preparando frontend..." -ForegroundColor Yellow

$frontendPath = "$root\astrocare-frontend"

# Criar .env local apontando para localhost
$envContent = "VITE_API_URL=http://localhost:8080"
Set-Content -Path "$frontendPath\.env" -Value $envContent -Encoding UTF8

# Instalar dependencias se necessario
if (-not (Test-Path "$frontendPath\node_modules")) {
    Write-Host "      Instalando dependencias npm (aguarde)..." -ForegroundColor Gray
    Start-Process -FilePath "powershell.exe" `
        -ArgumentList "-NoExit", "-Command", "cd '$frontendPath'; npm install; npm run dev" `
        -PassThru | Out-Null
} else {
    Start-Process -FilePath "powershell.exe" `
        -ArgumentList "-NoExit", "-Command", "cd '$frontendPath'; npm run dev" `
        -PassThru | Out-Null
}

Write-Host "      Frontend iniciado (porta 5173)" -ForegroundColor Green

# ── Aguardar e abrir navegador ────────────────────────────────────────────────
Write-Host "[3/3] Aguardando servicos subirem..." -ForegroundColor Yellow
Start-Sleep -Seconds 6

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Projeto rodando!" -ForegroundColor Green
Write-Host "  Acesse: http://localhost:5173" -ForegroundColor Green
Write-Host "  Backend: http://localhost:8080" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

Start-Process "http://localhost:5173"

Write-Host "Pressione qualquer tecla para encerrar tudo..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

# Encerrar processos
Stop-Process -Id $backend.Id -Force -ErrorAction SilentlyContinue
Write-Host "Encerrado." -ForegroundColor Red
