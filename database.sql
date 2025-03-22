-- 删除已存在的数据库并重新创建
DROP DATABASE IF EXISTS smart_apartment;
CREATE DATABASE smart_apartment CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_apartment;

-- 禁用外键检查
SET FOREIGN_KEY_CHECKS = 0;

-- 创建用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建公寓表
CREATE TABLE apartments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    area DECIMAL(10,2) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建设备表
CREATE TABLE devices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    apartment_id BIGINT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (apartment_id) REFERENCES apartments(id)
);

-- 创建租约表
CREATE TABLE leases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    apartment_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    monthly_rent DECIMAL(10,2) NOT NULL,
    deposit DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    terms TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (apartment_id) REFERENCES apartments(id),
    FOREIGN KEY (tenant_id) REFERENCES users(id)
);

-- 启用外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- 插入测试数据
-- 1. 插入用户
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$rTm8zZF.YwBxE3SSzFoNZeGG5hDTEk8VCQP8XrJ8VJQKJHuZ5DDhq', 'ADMIN'), -- 密码: admin123
('tenant1', '$2a$10$rTm8zZF.YwBxE3SSzFoNZeGG5hDTEk8VCQP8XrJ8VJQKJHuZ5DDhq', 'TENANT'), -- 密码: 123456
('tenant2', '$2a$10$rTm8zZF.YwBxE3SSzFoNZeGG5hDTEk8VCQP8XrJ8VJQKJHuZ5DDhq', 'TENANT'), -- 密码: 123456
('tenant3', '$2a$10$rTm8zZF.YwBxE3SSzFoNZeGG5hDTEk8VCQP8XrJ8VJQKJHuZ5DDhq', 'TENANT'); -- 密码: 123456

-- 2. 插入公寓
INSERT INTO apartments (name, address, area, price, status, description) VALUES
('阳光公寓A座101', '北京市朝阳区阳光小区1号楼', 89.5, 6000.00, 'AVAILABLE', '精装修两居室，家具家电齐全'),
('阳光公寓A座102', '北京市朝阳区阳光小区1号楼', 120.5, 8000.00, 'RENTED', '精装修三居室，南北通透'),
('阳光公寓B座201', '北京市朝阳区阳光小区2号楼', 95.0, 6500.00, 'AVAILABLE', '精装修两居室，采光好'),
('阳光公寓B座202', '北京市朝阳区阳光小区2号楼', 135.0, 9000.00, 'MAINTENANCE', '豪华装修三居室，带车位');

-- 3. 插入设备
INSERT INTO devices (name, type, status, apartment_id, description) VALUES
('客厅空调', 'AIR_CONDITIONER', 'ONLINE', 1, '格力智能空调'),
('主卧空调', 'AIR_CONDITIONER', 'ONLINE', 1, '美的智能空调'),
('智能门锁', 'DOOR_LOCK', 'ONLINE', 1, '小米智能门锁'),
('客厅灯', 'LIGHTING', 'ONLINE', 1, '飞利浦智能灯'),
('客厅空调', 'AIR_CONDITIONER', 'ONLINE', 2, '格力智能空调'),
('主卧空调', 'AIR_CONDITIONER', 'OFFLINE', 2, '美的智能空调'),
('智能门锁', 'DOOR_LOCK', 'ONLINE', 2, '小米智能门锁'),
('监控摄像头', 'CAMERA', 'ONLINE', 2, '海康威视摄像头'),
('客厅空调', 'AIR_CONDITIONER', 'MAINTENANCE', 3, '格力智能空调'),
('智能门锁', 'DOOR_LOCK', 'ONLINE', 3, '小米智能门锁'),
('烟雾报警器', 'SMOKE_DETECTOR', 'ONLINE', 3, '霍尼韦尔烟感'),
('温度传感器', 'TEMPERATURE_SENSOR', 'ONLINE', 3, '智能温度监测');

-- 4. 插入租约
INSERT INTO leases (apartment_id, tenant_id, start_date, end_date, monthly_rent, deposit, status, terms) VALUES
(2, 2, '2025-01-01', '2025-12-31', 8000.00, 16000.00, 'ACTIVE', '1. 租金按月支付\n2. 不得转租\n3. 不得改变房屋结构'),
(3, 3, '2025-02-01', '2025-07-31', 6500.00, 13000.00, 'PENDING', '1. 租金按月支付\n2. 不得转租\n3. 不得改变房屋结构'),
(4, 4, '2024-12-01', '2025-02-28', 9000.00, 18000.00, 'EXPIRED', '1. 租金按月支付\n2. 不得转租\n3. 不得改变房屋结构'); 