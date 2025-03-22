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
# 复制前端源代码
COPY frontend/src ./src
COPY frontend/public ./public
COPY frontend/babel.config.js ./
COPY frontend/jsconfig.json ./
COPY frontend/.eslintrc.js ./
COPY frontend/.env.development ./
# 创建vue.config.js文件
RUN echo 'const { defineConfig } = require("@vue/cli-service"); module.exports = defineConfig({ transpileDependencies: true });' > vue.config.js
# 显示文件列表
RUN ls -la
# 构建前端
RUN npm run build || echo "前端构建失败，继续执行"

# 最终镜像
FROM openjdk:8-jre-slim
WORKDIR /app
# 创建目录
RUN mkdir -p /app/data
RUN mkdir -p /app/static
# 复制后端jar
COPY --from=backend-build /app/backend/target/smart-apartment-0.0.1-SNAPSHOT.jar ./app.jar
# 尝试复制前端构建文件
RUN mkdir -p /tmp/dist
COPY --from=frontend-build /app/frontend/dist /tmp/dist || echo "No dist folder found"
RUN if [ -d "/tmp/dist" ] && [ "$(ls -A /tmp/dist)" ]; then cp -r /tmp/dist/* /app/static/; fi
# 显示静态目录内容
RUN ls -la /app/static || echo "Static directory may be empty"
# 暴露端口
EXPOSE 8080
# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 