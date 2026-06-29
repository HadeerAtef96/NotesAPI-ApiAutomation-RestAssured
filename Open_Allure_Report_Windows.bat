@echo off
setlocal

call mvn allure:report

set "ALLURE_REPORT=%cd%\target\site\allure-maven-plugin\index.html"

if not exist "%ALLURE_REPORT%" (
echo Allure report not found:
echo %ALLURE_REPORT%
pause
exit /b 1
)

set "CHROME=%LocalAppData%\Google\Chrome\Application\chrome.exe"

if not exist "%CHROME%" (
echo Chrome not found:
echo %CHROME%
pause
exit /b 1
)

start "" "%CHROME%" --allow-file-access-from-files "%ALLURE_REPORT%"

exit /b 0
