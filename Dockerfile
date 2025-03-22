# 构建后端
FROM maven:3.8.4-openjdk-8 AS backend-build
WORKDIR /app/backend
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

# 构建前端
FROM node:14 AS frontend-build
WORKDIR /app/frontend
# 设置淘宝镜像源
RUN npm config set registry https://registry.npm.taobao.org
# 复制package.json
COPY frontend/package.json ./
COPY frontend/package-lock.json ./
# 安装依赖
RUN npm install --legacy-peer-deps
# 复制前端源码 (除了.git目录)
COPY frontend/src ./src
COPY frontend/public ./public
COPY frontend/babel.config.js ./
COPY frontend/jsconfig.json ./
COPY frontend/.eslintrc.js ./
COPY frontend/vue.config.js ./

# 构建前端
RUN npm run build || echo "前端构建失败，继续执行后续步骤"

# 最终镜像
FROM openjdk:8-jre-slim
WORKDIR /app
# 创建数据目录
RUN mkdir -p /app/data
# 复制后端jar
COPY --from=backend-build /app/backend/target/smart-apartment-0.0.1-SNAPSHOT.jar ./app.jar
# 创建静态资源目录
RUN mkdir -p /app/static
# 尝试复制前端构建产物
RUN echo "准备复制前端资源..."
COPY --from=frontend-build /app/frontend/dist /app/static
# 暴露端口
EXPOSE 8080
# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 