@echo off
setlocal

set "HTML_FILE=%~dp0index.html"

if not exist "%HTML_FILE%" (
    echo index.html not found
    pause
    exit /b 1
)

start "" "%LocalAppData%\Google\Chrome\Application\chrome.exe" --allow-file-access-from-files "%HTML_FILE%"