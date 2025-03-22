FROM maven:3.8.4-openjdk-8 AS backend-build

# 复制后端源码
WORKDIR /app/backend
COPY backend/pom.xml .
COPY backend/src ./src

# 构建后端
RUN mvn clean package -DskipTests

FROM node:14 AS frontend-build

# 复制前端源码
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend .
RUN npm run build

FROM openjdk:8-jre-slim

# 创建app目录
WORKDIR /app
RUN mkdir -p /app/data

# 从构建阶段复制构建结果
COPY --from=backend-build /app/backend/target/smart-apartment-0.0.1-SNAPSHOT.jar ./app.jar
COPY --from=frontend-build /app/frontend/dist ./static

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 