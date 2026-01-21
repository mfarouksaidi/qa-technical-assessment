@echo off
REM QA Automation Framework - Test Execution Script for Windows
REM This script provides easy commands to run different test suites

setlocal enabledelayedexpansion

if "%1"=="" (
    goto :usage
)

if /i "%1"=="all" goto :run_all
if /i "%1"=="api" goto :run_api
if /i "%1"=="ui" goto :run_ui
if /i "%1"=="smoke" goto :run_smoke
if /i "%1"=="clean" goto :clean
if /i "%1"=="install" goto :install
if /i "%1"=="report" goto :open_report
if /i "%1"=="help" goto :usage
if /i "%1"=="-h" goto :usage
if /i "%1"=="--help" goto :usage

echo [ERROR] Invalid option: %1
echo.
goto :usage

:run_all
echo [INFO] Starting All Tests...
if defined HEADLESS (
    mvn clean test -Dtest=TestRunnerAll -Dheadless=true
) else (
    mvn clean test -Dtest=TestRunnerAll
)
if %errorlevel% neq 0 (
    echo [ERROR] Tests failed!
    exit /b 1
)
echo [INFO] All Tests completed successfully!
goto :end

:run_api
echo [INFO] Starting API Tests...
mvn clean test -Dtest=TestRunnerApi
if %errorlevel% neq 0 (
    echo [ERROR] API Tests failed!
    exit /b 1
)
echo [INFO] API Tests completed successfully!
goto :end

:run_ui
echo [INFO] Starting UI Tests...
if defined HEADLESS (
    echo [WARN] Running in headless mode
    mvn clean test -Dtest=TestRunnerUi -Dheadless=true
) else (
    mvn clean test -Dtest=TestRunnerUi
)
if %errorlevel% neq 0 (
    echo [ERROR] UI Tests failed!
    exit /b 1
)
echo [INFO] UI Tests completed successfully!
goto :end

:run_smoke
echo [INFO] Starting Smoke Tests...
if defined HEADLESS (
    mvn clean test -Dtest=TestRunnerSmoke -Dheadless=true
) else (
    mvn clean test -Dtest=TestRunnerSmoke
)
if %errorlevel% neq 0 (
    echo [ERROR] Smoke Tests failed!
    exit /b 1
)
echo [INFO] Smoke Tests completed successfully!
goto :end

:clean
echo [INFO] Cleaning build artifacts...
mvn clean
echo [INFO] Clean completed!
goto :end

:install
echo [INFO] Installing dependencies...
mvn clean install -DskipTests
echo [INFO] Installation completed!
goto :end

:open_report
set "report_file=target\reports\cucumber-reports.html"
if exist "%report_file%" (
    echo [INFO] Opening test report...
    start "" "%report_file%"
) else (
    echo [ERROR] Report not found. Please run tests first.
    exit /b 1
)
goto :end

:usage
echo Usage: %~nx0 [OPTION]
echo.
echo Options:
echo   all         Run all tests (API + UI)
echo   api         Run API tests only
echo   ui          Run UI tests only
echo   smoke       Run smoke tests only
echo   clean       Clean build artifacts
echo   install     Install dependencies
echo   report      Open test report in browser
echo   help        Display this help message
echo.
echo Examples:
echo   %~nx0 smoke              # Run smoke tests
echo   %~nx0 api                # Run API tests
echo   set HEADLESS=true ^& %~nx0 ui   # Run UI tests in headless mode
echo.
goto :end

:end
endlocal
exit /b 0
