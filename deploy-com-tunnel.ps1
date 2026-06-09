Write-Host "Iniciando tunnel Cloudflare para localhost:8080..." -ForegroundColor Cyan
Write-Host "(Deixe esta janela aberta enquanto fizer a apresentacao)" -ForegroundColor Yellow
Write-Host ""

$logFile = "$env:TEMP\cloudflared-astrocare.log"
Remove-Item $logFile -ErrorAction SilentlyContinue

$proc = Start-Process -FilePath "cloudflared" -ArgumentList "tunnel --url http://localhost:8080 --logfile $logFile" -PassThru -WindowStyle Hidden

Write-Host "Aguardando URL do tunnel..." -ForegroundColor Gray

$tunnelUrl = $null
$tries = 0
while (-not $tunnelUrl -and $tries -lt 30) {
    Start-Sleep -Seconds 2
    $tries++
    if (Test-Path $logFile) {
        $content = Get-Content $logFile -Raw -ErrorAction SilentlyContinue
        if ($content -match 'https://[a-z0-9\-]+\.trycloudflare\.com') {
            $tunnelUrl = $matches[0]
        }
    }
}

if (-not $tunnelUrl) {
    Write-Host "ERRO: Nao foi possivel obter a URL do tunnel." -ForegroundColor Red
    Write-Host "Verifique se o backend esta rodando na porta 8080." -ForegroundColor Red
    $proc | Stop-Process -Force
    exit 1
}

Write-Host ""
Write-Host "URL do tunnel: $tunnelUrl" -ForegroundColor Green
Write-Host "Atualizando variavel no Vercel e fazendo redeploy..." -ForegroundColor Cyan

Set-Location "$PSScriptRoot\astrocare-frontend"

vercel env rm VITE_API_URL production --yes --scope kayquedr2012-2345s-projects 2>$null
echo $tunnelUrl | vercel env add VITE_API_URL production --scope kayquedr2012-2345s-projects
vercel --prod --yes --scope kayquedr2012-2345s-projects

Write-Host ""
Write-Host "Pronto! Acesse o site no Vercel, ele esta conectado ao seu backend local." -ForegroundColor Green
Write-Host "URL do backend: $tunnelUrl" -ForegroundColor Yellow
Write-Host ""
Write-Host "NAO feche esta janela enquanto estiver apresentando!" -ForegroundColor Red

$proc | Wait-Process
