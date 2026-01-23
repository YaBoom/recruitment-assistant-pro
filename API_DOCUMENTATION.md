# API 文档

## 基础信息

- **Base URL**: `http://localhost:8080/api`
- **Content-Type**: `application/json`
- **字符编码**: UTF-8

## 统一响应格式

### 成功响应

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... }
}
```

### 失败响应

```json
{
  "code": 500,
  "message": "错误信息",
  "data": null
}
```

## API 接口

### 1. 失物记录接口

#### 1.1 报告失物

**接口**: `POST /lost-items`

**请求体**:
```json
{
  "description": "黑色钱包",
  "lostTime": "今天上午",
  "location": "图书馆",
  "contactInfo": "13800138000"
}
```

**响应**:
```json
{
  "code": 200,
  "message": "失物报告成功",
  "data": {
    "id": "uuid-string",
    "description": "黑色钱包",
    "lostTime": "今天上午",
    "location": "图书馆",
    "contactInfo": "13800138000",
    "createdAt": "2026-01-23T10:30:00",
    "status": "PENDING"
  }
}
```

#### 1.2 查询所有失物记录

**接口**: `GET /lost-items`

**响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": "uuid-string",
      "description": "黑色钱包",
      "lostTime": "今天上午",
      "location": "图书馆",
      "contactInfo": "13800138000",
      "createdAt": "2026-01-23T10:30:00",
      "status": "PENDING"
    }
  ]
}
```

#### 1.3 根据ID查询失物记录

**接口**: `GET /lost-items/{id}`

**路径参数**:
- `id`: 失物记录ID

**响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": "uuid-string",
    "description": "黑色钱包",
    "lostTime": "今天上午",
    "location": "图书馆",
    "contactInfo": "13800138000",
    "createdAt": "2026-01-23T10:30:00",
    "status": "PENDING"
  }
}
```

#### 1.4 为失物查找匹配

**接口**: `GET /lost-items/{id}/matches`

**路径参数**:
- `id`: 失物记录ID

**响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "foundItem": {
        "id": "uuid-string",
        "description": "黑色皮夹",
        "foundTime": "今天中午",
        "location": "图书馆门口",
        "contactInfo": "13700137000",
        "createdAt": "2026-01-23T11:00:00",
        "status": "PENDING"
      },
      "matchScore": 0.85,
      "matchReason": "描述相似度: 0.90, 时间相似度: 0.80, 地点相似度: 0.85"
    }
  ]
}
```

### 2. 拾物记录接口

#### 2.1 报告拾物

**接口**: `POST /found-items`

**请求体**:
```json
{
  "description": "黑色皮夹",
  "foundTime": "今天中午",
  "location": "图书馆门口",
  "contactInfo": "13700137000"
}
```

**响应**:
```json
{
  "code": 200,
  "message": "拾物报告成功",
  "data": {
    "id": "uuid-string",
    "description": "黑色皮夹",
    "foundTime": "今天中午",
    "location": "图书馆门口",
    "contactInfo": "13700137000",
    "createdAt": "2026-01-23T11:00:00",
    "status": "PENDING"
  }
}


```

#### 2.2 查询所有拾物记录

**接口**: `GET /found-items`

**响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": "uuid-string",
      "description": "黑色皮夹",
      "foundTime": "今天中午",
      "location": "图书馆门口",
      "contactInfo": "13700137000",
      "createdAt": "2026-01-23T11:00:00",
      "status": "PENDING"
    }
  ]
}
```

#### 2.3 根据ID查询拾物记录

**接口**: `GET /found-items/{id}`

**路径参数**:
- `id`: 拾物记录ID

**响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": "uuid-string",
    "description": "黑色皮夹",
    "foundTime": "今天中午",
    "location": "图书馆门口",
    "contactInfo": "13700137000",
    "createdAt": "2026-01-23T11:00:00",
    "status": "PENDING"
  }
}
```

#### 2.4 为拾物查找匹配

**接口**: `GET /found-items/{id}/matches`

**路径参数**:
- `id`: 拾物记录ID

**响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "lostItem": {
        "id": "uuid-string",
        "description": "黑色钱包",
        "lostTime": "今天上午",
        "location": "图书馆",
        "contactInfo": "13800138000",
        "createdAt": "2026-01-23T10:30:00",
        "status": "PENDING"
      },
      "matchScore": 0.85,
      "matchReason": "描述相似度: 0.90, 时间相似度: 0.80, 地点相似度: 0.85"
    }
  ]
}
```

### 3. AI助手接口

#### 3.1 AI对话

**接口**: `POST /api/ai/chat`

**请求体**:
```json
{
  "message": "我丢了一个黑色钱包"
}
```

**响应**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": "很抱歉听到您丢失了钱包。为了帮您更好地找回，我需要了解一些详细信息。请问您的钱包是什么时候丢失的呢？"
}
```

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 测试示例

### 使用curl测试

```bash
# 1. 报告失物
curl -X POST http://localhost:8080/api/lost-items \
  -H "Content-Type: application/json" \
  -d '{
    "description": "黑色钱包",
    "lostTime": "今天上午",
    "location": "图书馆",
    "contactInfo": "13800138000"
  }'

# 2. 查询所有失物
curl http://localhost:8080/api/lost-items

# 3. AI对话
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "我丢了一个黑色钱包"
  }'
```

### 使用Postman测试

1. 导入API集合
2. 设置Base URL: `http://localhost:8080/api`
3. 测试各个接口

## 注意事项

1. 所有时间字段使用ISO 8601格式
2. 联系方式支持手机号和邮箱
3. 匹配分数范围：0.0 - 1.0，阈值为0.3
4. AI对话需要Ollama服务运行
5. 数据库连接信息在application.yaml中配置
