#!/bin/bash
echo "======================================"
echo " 正在部署智能公寓管理系统"
echo "======================================"
echo

# 检查Docker是否安装
if ! command -v docker &> /dev/null; then
    echo "[错误] 未检测到Docker环境，请先安装Docker"
    echo "请访问 https://docs.docker.com/engine/install/"
    exit 1
fi

# 检查Docker Compose是否安装
if ! command -v docker-compose &> /dev/null; then
    echo "[错误] 未检测到Docker Compose，请确保已安装"
    echo "请访问 https://docs.docker.com/compose/install/"
    exit 1
fi

echo "[信息] 正在构建并启动应用..."
echo "[信息] 首次构建可能需要较长时间，请耐心等待..."
echo

# 创建data目录（如果不存在）
mkdir -p data

# 启动应用
docker-compose up -d --build

if [ $? -ne 0 ]; then
    echo "[错误] 应用启动失败，请检查错误信息"
    exit 1
fi

echo
echo "[成功] 应用已成功部署!"
echo "请访问: http://localhost:8080"
echo
echo "默认用户:"
echo "  管理员 - 用户名: admin 密码: admin123"
echo "  租户 - 用户名: tenant1 密码: 123456"
echo
echo "[提示] 数据将持久化保存在./data目录中"
echo "[提示] 如需停止应用，请运行: docker-compose down"
echo 