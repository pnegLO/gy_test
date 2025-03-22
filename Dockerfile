# 只使用后端构建和运行
FROM maven:3.8.4-openjdk-8 AS builder

# 复制后端代码
WORKDIR /app
COPY backend/pom.xml /app/
COPY backend/src /app/src/

# 构建后端
RUN mvn clean package -DskipTests

# 最终阶段 - 只使用后端
FROM openjdk:8-jre-slim

# 创建应用目录
WORKDIR /app
RUN mkdir -p /app/data
RUN mkdir -p /app/static

# 复制JAR包
COPY --from=builder /app/target/smart-apartment-0.0.1-SNAPSHOT.jar /app/app.jar

# 添加最小静态文件，避免404错误
RUN echo '<!DOCTYPE html><html><head><meta charset="utf-8"><title>Smart Apartment</title></head><body><h1>Smart Apartment System</h1><p>API is running. This is a placeholder page.</p></body></html>' > /app/static/index.html

# 暴露端口
EXPOSE 8080

# 启动命令
CMD ["java", "-jar", "/app/app.jar"] 