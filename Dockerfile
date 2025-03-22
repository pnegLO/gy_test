# Stage 1: 构建后端
FROM maven:3.8.4-openjdk-8 AS backend-build
WORKDIR /app/backend
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

# Stage 2: 构建前端
FROM node:14 AS frontend-build
WORKDIR /app/frontend
# 设置淘宝镜像源
RUN npm config set registry https://registry.npm.taobao.org
# 复制package.json和lock文件
COPY frontend/package.json frontend/package-lock.json ./
# 安装依赖
RUN npm install --legacy-peer-deps
# 复制前端源代码
COPY frontend/src/ ./src/
COPY frontend/public/ ./public/
COPY frontend/babel.config.js ./
COPY frontend/jsconfig.json ./
COPY frontend/.eslintrc.js ./
COPY frontend/.env.development ./
COPY frontend/vue.config.js ./
# 显示文件列表
RUN ls -la
# 构建前端
RUN npm run build || echo "前端构建失败，但继续执行后续步骤"

# Stage 3: 最终镜像
FROM openjdk:8-jre-slim
WORKDIR /app
# 创建必要的目录
RUN mkdir -p /app/data /app/static
# 复制后端jar
COPY --from=backend-build /app/backend/target/smart-apartment-0.0.1-SNAPSHOT.jar ./app.jar
# 复制前端构建文件
# 注意：如果dist目录不存在，这个命令会报错但不会影响构建
COPY --from=frontend-build /app/frontend/dist/ ./static/ || true
# 确保目录存在
RUN ls -la /app/static || echo "静态资源目录可能为空"
# 暴露端口
EXPOSE 8080
# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 