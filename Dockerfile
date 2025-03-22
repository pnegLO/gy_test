# 使用官方Maven镜像作为构建环境
FROM maven:3.8.4-openjdk-8 AS builder

# 复制后端代码
WORKDIR /app
COPY backend/pom.xml /app/

# 下载依赖（不使用自定义settings.xml）
RUN mvn dependency:go-offline

# 复制源代码
COPY backend/src /app/src/

# 构建后端（简化构建命令）
RUN mvn package -DskipTests

# 最终阶段 - 只使用后端
FROM openjdk:8-jre-slim

# 创建应用目录
WORKDIR /app
RUN mkdir -p /app/data
RUN mkdir -p /app/static

# 复制JAR包
COPY --from=builder /app/target/*.jar /app/app.jar

# 添加最小静态文件，避免404错误
RUN echo '<!DOCTYPE html><html><head><meta charset="utf-8"><title>Smart Apartment</title></head><body><h1>Smart Apartment System</h1><p>API is running. This is a placeholder page.</p></body></html>' > /app/static/index.html

# 暴露端口
EXPOSE 8080

# 启动命令
CMD ["java", "-jar", "/app/app.jar"] 