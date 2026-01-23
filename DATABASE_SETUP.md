# 数据库设置说明

## 数据库信息

- **数据库名称**: `ai_lost_db`
- **字符集**: `utf8mb4`
- **排序规则**: `utf8mb4_unicode_ci`

## 表结构

### 1. 失物记录表 (lost_items)

存储用户报告的丢失物品信息

| 字段名 | 类型 | 长度 | 约束 | 说明 |
|--------|------|------|------|------|
| id | VARCHAR | 50 | PRIMARY KEY | 唯一标识（UUID） |
| description | VARCHAR | 500 | NOT NULL | 物品描述 |
| lost_time | VARCHAR | 100 | NOT NULL | 丢失时间（用户提供的时间描述） |
| location | VARCHAR | 200 | NOT NULL | 丢失地点 |
| contact_info | VARCHAR | 100 | NOT NULL | 联系方式 |
| created_at | DATETIME | - | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间（系统记录时间） |
| status | VARCHAR | 20 | NOT NULL, DEFAULT 'PENDING' | 物品状态：PENDING-待处理, MATCHED-已匹配, RETURNED-已归还 |

**索引**:
- `idx_status` - status字段索引
- `idx_created_at` - created_at字段索引
- `idx_location` - location字段索引

### 2. 拾物记录表 (found_items)

存储用户报告的拾到物品信息

| 字段名 | 类型 | 长度 | 约束 | 说明 |
|--------|------|------|------|------|
| id | VARCHAR | 50 | PRIMARY KEY | 唯一标识（UUID） |
| description | VARCHAR | 500 | NOT NULL | 物品描述 |
| found_time | VARCHAR | 100 | NOT NULL | 拾到时间（用户提供的时间描述） |
| location | VARCHAR | 200 | NOT NULL | 拾到地点 |
| contact_info | VARCHAR | 100 | NOT NULL | 联系方式 |
| created_at | DATETIME | - | NOT NULL, DEFAULT CURRENT_TIMESTAMP | 创建时间（系统记录时间） |
| status | VARCHAR | 20 | NOT NULL, DEFAULT 'PENDING' | 物品状态：PENDING-待处理, MATCHED-已匹配, RETURNED-已归还 |

**索引**:
- `idx_status` - status字段索引
- `idx_created_at` - created_at字段索引
- `idx_location` - location字段索引

### SQL脚本命令

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS ai_lost_db 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_unicode_ci;

USE ai_lost_db;

-- 创建失物记录表
CREATE TABLE IF NOT EXISTS lost_items (
    id VARCHAR(50) PRIMARY KEY COMMENT '唯一标识',
    description VARCHAR(500) NOT NULL COMMENT '物品描述',
    lost_time VARCHAR(100) NOT NULL COMMENT '丢失时间',
    location VARCHAR(200) NOT NULL COMMENT '丢失地点',
    contact_info VARCHAR(100) NOT NULL COMMENT '联系方式',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '物品状态',
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建拾物记录表
CREATE TABLE IF NOT EXISTS found_items (
    id VARCHAR(50) PRIMARY KEY COMMENT '唯一标识',
    description VARCHAR(500) NOT NULL COMMENT '物品描述',
    found_time VARCHAR(100) NOT NULL COMMENT '拾到时间',
    location VARCHAR(200) NOT NULL COMMENT '拾到地点',
    contact_info VARCHAR(100) NOT NULL COMMENT '联系方式',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '物品状态',
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    INDEX idx_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## 

## 注意事项

1. **MySQL版本**: 建议使用MySQL 5.7或更高版本
2. **字符集**: 使用utf8mb4支持完整的Unicode字符（包括emoji）
3. **时区**: 配置中使用Asia/Shanghai时区
4. **连接信息**: 
   - 主机: localhost
   - 端口: 3306
   - 用户名: root
   - 密码: 12345678（请根据实际情况修改）
5. **权限**: 确保MySQL用户有创建数据库和表的权限

## 测试数据（可选）

```sql
-- 插入测试失物记录
INSERT INTO lost_items (id, description, lost_time, location, contact_info, created_at, status)
VALUES 
('test-lost-1', '黑色钱包', '今天上午', '图书馆', '13800138000', NOW(), 'PENDING'),
('test-lost-2', '蓝色雨伞', '昨天下午', '食堂', '13900139000', NOW(), 'PENDING');

-- 插入测试拾物记录
INSERT INTO found_items (id, description, found_time, location, contact_info, created_at, status)
VALUES 
('test-found-1', '黑色皮夹', '今天中午', '图书馆门口', '13700137000', NOW(), 'PENDING'),
('test-found-2', '蓝色折叠伞', '昨天傍晚', '食堂二楼', '13600136000', NOW(), 'PENDING');

-- 查询测试数据
SELECT * FROM lost_items;
SELECT * FROM found_items;
```
