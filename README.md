# 智能公寓管理系统

一个用于管理公寓租赁、设备监控和租户管理的系统，使用Spring Boot（后端）和Vue.js（前端）开发。

## 部署方式

### 方式一：使用GitHub托管的Docker镜像（最简单）

#### 系统要求
- Windows 11/10 或 macOS 或 Linux
- 安装了 [Docker Desktop](https://www.docker.com/products/docker-desktop/)

#### 部署步骤
1. 下载[docker-compose.prod.yml](https://raw.githubusercontent.com/YOUR_GITHUB_USERNAME/smart-apartment/main/docker-compose.prod.yml)文件
2. 修改文件中的`YOUR_GITHUB_USERNAME`为实际的GitHub用户名
3. 运行命令：
   ```bash
   docker-compose -f docker-compose.prod.yml up -d
   ```
4. 打开浏览器访问：http://localhost:8080

这种方式会直接从GitHub Container Registry拉取预构建的镜像，无需本地构建。

### 方式二：Docker一键部署（本地构建）

#### 系统要求
- Windows 11/10 或 macOS 或 Linux
- 安装了 [Docker Desktop](https://www.docker.com/products/docker-desktop/)

#### 部署步骤
1. 克隆GitHub仓库：
   ```bash
   git clone https://github.com/YOUR_GITHUB_USERNAME/smart-apartment.git
   cd smart-apartment
   ```

2. 启动应用：
   - Windows系统：双击运行 `run-windows.bat`
   - macOS/Linux系统：在终端中执行 `./run-linux.sh`

3. 打开浏览器访问：http://localhost:8080

应用程序和所有依赖项都将在Docker容器中运行，无需在本地安装任何环境。

#### 停止应用
- 在命令行中运行：`docker-compose down`

### 方式三：传统部署（需要安装环境）

#### 系统要求
- JDK 8 [下载链接](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
- Node.js (v14+) [下载链接](https://nodejs.org/)
- Maven (可选，项目包含mvnw) [下载链接](https://maven.apache.org/download.cgi)

#### 部署步骤
1. 克隆GitHub仓库：
   ```bash
   git clone https://github.com/YOUR_GITHUB_USERNAME/smart-apartment.git
   cd smart-apartment
   ```

2. 启动应用：
   - Windows系统：双击运行 `start-app.bat`
   - 或手动部署：
     - 后端：
       ```bash
       cd backend
       mvn clean package -DskipTests
       java -jar target/smart-apartment-0.0.1-SNAPSHOT.jar
       ```
     - 前端：
       ```bash
       cd frontend
       npm install
       npm run serve
       ```

## 自行构建与部署

### 在GitHub上构建
本项目使用GitHub Actions自动构建Docker镜像并推送到GitHub Container Registry。

如果你Fork了这个项目：
1. 转到仓库设置，确保启用了"Packages"功能
2. 确保Actions有足够的权限创建和推送包

### 手动构建镜像
```bash
docker build -t smart-apartment .
```

## 特点

- 使用SQLite数据库（无需单独安装数据库）
- 响应式界面设计
- 设备管理与监控
- 租约管理
- 用户权限控制

## 登录信息

- 管理员账户:
  - 用户名: admin
  - 密码: admin123

- 租户账户:
  - 用户名: tenant1
  - 密码: 123456

## 数据持久化

- Docker部署：数据保存在项目目录的`data`文件夹中
- 传统部署：数据保存在应用程序运行目录的`data`文件夹中

## 问题排查

1. **Docker相关问题**
   - 确保Docker Desktop已正确安装并运行
   - 如果启动失败，可以运行`docker-compose logs`查看详细错误信息

2. **端口冲突**
   - 如果8080端口被占用，可以修改`docker-compose.yml`中的端口映射

3. **Java版本问题**（仅适用于传统部署）
   - 确保使用JDK 8 (1.8)，可通过`java -version`命令检查

## 联系方式

如有任何问题，请发送邮件至[您的邮箱] # gy_test
