-- ============================================
-- AI失物招领助手 - 数据库表结构
-- 数据库名称: ai_lost_db
-- ============================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS ai_lost_db 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_unicode_ci;

USE ai_lost_db;

-- ============================================
-- 失物记录表
-- ============================================
CREATE TABLE IF NOT EXISTS lost_items (
    id VARCHAR(50) PRIMARY KEY COMMENT '唯一标识',
    description VARCHAR(500) NOT NULL COMMENT '物品描述',
    lost_time VARCHAR(100) NOT NULL COMMENT '丢失时间（用户提供的时间描述）',
    location VARCHAR(200) NOT NULL COMMENT '丢失地点',
    contact_info VARCHAR(100) NOT NULL COMMENT '联系方式',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（系统记录时间）',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '物品状态：PENDING-待处理, MATCHED-已匹配, RETURNED-已归还',
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='失物记录表';

-- ============================================
-- 拾物记录表
-- ============================================
CREATE TABLE IF NOT EXISTS found_items (
    id VARCHAR(50) PRIMARY KEY COMMENT '唯一标识',
    description VARCHAR(500) NOT NULL COMMENT '物品描述',
    found_time VARCHAR(100) NOT NULL COMMENT '拾到时间（用户提供的时间描述）',
    location VARCHAR(200) NOT NULL COMMENT '拾到地点',
    contact_info VARCHAR(100) NOT NULL COMMENT '联系方式',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（系统记录时间）',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '物品状态：PENDING-待处理, MATCHED-已匹配, RETURNED-已归还',
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='拾物记录表';

-- ============================================
-- 使用说明
-- ============================================
-- 1. 在MySQL中执行此SQL文件创建数据库和表：
--    mysql -u root -p < schema.sql
--
-- 2. 或者在MySQL客户端中直接执行：
--    source /path/to/schema.sql
--
-- 3. 表结构说明：
--    - lost_items: 存储用户报告的丢失物品信息
--    - found_items: 存储用户报告的拾到物品信息
--    - 两个表结构相似，便于统一处理
--    - 使用VARCHAR(50)作为主键，存储UUID
--    - status字段使用枚举值，便于状态管理
--    - 创建了索引以提高查询性能
