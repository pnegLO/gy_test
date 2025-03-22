@echo off
title 智能公寓管理系统
echo ======================================
echo  正在启动智能公寓管理系统
echo ======================================
echo.

REM 检查Java是否安装
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [错误] 未检测到Java环境，请安装JDK 8
    echo 请访问 https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html
    pause
    exit /b 1
)

REM 检查Node.js是否安装
node -v >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [错误] 未检测到Node.js环境，请安装Node.js
    echo 请访问 https://nodejs.org/download/release/latest-v14.x/
    pause
    exit /b 1
)

REM 检查Maven是否安装
mvn -v >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [警告] 未检测到Maven，将使用包含的mvnw
    set MVN_CMD=mvnw
) else (
    set MVN_CMD=mvn
)

echo [信息] 正在构建后端服务...
cd backend
call %MVN_CMD% clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo [错误] 后端构建失败
    pause
    exit /b 1
)
echo [成功] 后端构建完成

echo [信息] 启动后端服务...
start cmd /k "java -jar target\smart-apartment-0.0.1-SNAPSHOT.jar"

echo [信息] 等待后端启动...
timeout /t 10 /nobreak >nul

echo [信息] 正在准备前端...
cd ..\frontend
echo [信息] 安装前端依赖...
call npm install
if %ERRORLEVEL% NEQ 0 (
    echo [错误] 前端依赖安装失败
    pause
    exit /b 1
)

echo [信息] 启动前端服务...
start cmd /k "npm run serve"

echo.
echo [成功] 应用已启动!
echo 后端API: http://localhost:8080
echo 前端页面: http://localhost:8081（如果8080被占用，可能会是其他端口，请查看前端启动输出）
echo.
echo 默认用户:
echo   管理员 - 用户名: admin 密码: admin123
echo   租户 - 用户名: tenant1 密码: 123456
echo.
echo 按任意键退出本窗口（应用将继续在后台运行）
pause > nul 