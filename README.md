# AI失物招领助手 (AI Lost and Found Assistant)

## 项目简介

这是一个基于LangChain4j框架的个人应用，系统通过对话式交互，帮助用户报告丢失物品、报告拾到物品，并智能匹配失物和拾物信息。

## 核心技术点

本项目聚焦于以下LLM应用开发的核心技术点：

1. **ChatMemory 状态管理** - 如何管理对话历史和上下文
2. **动态提示词工程** - 如何根据场景动态构建提示词
3. **Tool工具链集成** - 如何让AI调用Java方法执行具体操作
4. **AiService 服务化** - 如何封装AI能力为标准服务接口
5. **企业级开发闭环** - 完整的需求→设计→实现→测试流程

## 技术栈

- **Java 11+** - 使用现代Java特性
- **Spring Boot 2.7.18** - Web应用框架
- **MyBatis 2.3.2** - 持久层框架
- **MySQL 8.0** - 关系型数据库
- **LangChain4j 0.35.0** - Java的LLM应用开发框架
- **Ollama** - 本地LLM服务
- **qwen2.5:4b** - 阿里通义千问本地模型
- **Lombok** - 简化Java代码
- **Maven** - 项目构建和依赖管理

## 环境要求

### 必需环境
- JDK 11 或更高版本
- Maven 3.6+
- **MySQL 8.0+** - 数据库服务
- **Ollama** - 本地LLM服务（无需API密钥）

### 验证环境
```bash
# 检查Java版本
java -version

# 检查Maven版本
mvn -version

# 检查MySQL版本
mysql --version

# 检查Ollama是否安装
ollama --version
```

## 快速开始

### 1. 安装Ollama和模型

#### 安装Ollama
访问 [Ollama官网](https://ollama.ai/) 下载并安装适合你操作系统的版本。

**Windows**: 下载安装包直接安装
**Mac**: `brew install ollama`
**Linux**: `curl -fsSL https://ollama.ai/install.sh | sh`

#### 下载qwen2.5:4b模型
```bash
# 下载模型（首次使用需要下载，约2.3GB）
ollama pull qwen2.5:4b

# 验证模型已下载
ollama list

# 测试模型运行
ollama run qwen2.5:4b "你好"
```

### 2. 启动Ollama服务

Ollama安装后会自动在后台运行，默认监听 `http://localhost:11434`

```bash
# 检查Ollama服务状态
curl http://localhost:11434/api/tags

# 如果服务未运行，手动启动
ollama serve
```

### 3. 配置数据库

#### 创建数据库
```bash
# 登录MySQL
mysql -u root -p

# 创建数据库
CREATE DATABASE ai_lost_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 退出MySQL
exit
```

#### 执行数据库脚本
```bash
# 使用提供的schema.sql创建表结构
mysql -u root -p ai_lost_db < src/main/resources/schema.sql
```

#### 配置数据库连接

配置文件 `src/main/resources/application.yaml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_lost_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 12345678  # 修改为你的MySQL密码
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 4. 配置Ollama连接

配置文件 `src/main/resources/application.yaml` 已默认配置好：

```yaml
ollama:
  base-url: http://localhost:11434
  model-name: qwen2.5:4b
  timeout: 60
```

**无需配置API密钥！** Ollama是本地服务，所有数据在本地处理

### 5. 构建项目
```bash
mvn clean install
```

### 6. 运行应用

#### 方式1：使用Maven运行
```bash
mvn spring-boot:run
```

#### 方式2：运行编译后的jar
```bash
java -jar target/ai-lost-and-found-assistant-1.0-SNAPSHOT.jar
```

应用启动后，可以通过以下方式访问：
- **REST API**: http://localhost:8080
- **API文档**: 查看 `API_DOCUMENTATION.md`

## 项目结构

```
ai-lost-and-found-assistant/
├── src/
│   ├── main/
│   │   ├── java/com/example/lostandfound/
│   │   │   ├── model/          # 数据模型层
│   │   │   │   ├── LostItem.java
│   │   │   │   ├── FoundItem.java
│   │   │   │   ├── MatchResult.java
│   │   │   │   └── ItemStatus.java
│   │   │   ├── mapper/         # MyBatis Mapper接口
│   │   │   │   ├── LostItemMapper.java
│   │   │   │   └── FoundItemMapper.java
│   │   │   ├── repository/     # 数据访问层
│   │   │   │   ├── ItemRepository.java
│   │   │   │   └── MyBatisItemRepository.java
│   │   │   ├── service/        # 业务逻辑层
│   │   │   │   ├── ItemService.java
│   │   │   │   ├── ItemServiceImpl.java
│   │   │   │   ├── MatchingService.java
│   │   │   │   └── MatchingServiceImpl.java
│   │   │   ├── ai/             # AI服务层
│   │   │   │   ├── AssistantService.java
│   │   │   │   └── AssistantServiceFactory.java
│   │   │   ├── tool/           # 工具函数层
│   │   │   │   └── LostAndFoundTools.java
│   │   │   ├── controller/     # REST API控制器
│   │   │   │   ├── LostItemController.java
│   │   │   │   ├── FoundItemController.java
│   │   │   │   └── AiAssistantController.java
│   │   │   ├── dto/            # 数据传输对象
│   │   │   │   └── ApiResponse.java
│   │   │   ├── exception/      # 异常处理
│   │   │   │   ├── BusinessException.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── config/         # 配置管理
│   │   │   │   ├── PromptManager.java
│   │   │   │   └── AiServiceConfig.java
│   │   │   └── LostandfoundApplication.java
│   │   └── resources/
│   │       ├── mapper/         # MyBatis XML映射文件
│   │       │   ├── LostItemMapper.xml
│   │       │   └── FoundItemMapper.xml
│   │       ├── application.yaml
│   │       ├── schema.sql
│   │       └── logback.xml
│   └── test/
├── pom.xml
├── README.md
├── API_DOCUMENTATION.md
└── DATABASE_SETUP.md
```

## 核心技术点指导指南

### 1. ChatMemory - 对话状态管理

**位置**: `AssistantServiceFactory.java`

**学习要点**:
- 使用`MessageWindowChatMemory`保留最近N条消息
- 理解窗口大小对上下文和成本的影响
- 掌握如何让AI记住对话历史

**代码示例**:
```java
ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
```

**配置参数**: `application.properties` 中的 `chatmemory.max-messages`

### 2. 动态提示词工程

**位置**: `PromptManager.java`

**学习要点**:
- 根据不同场景（失物报告、拾物报告、匹配分析）使用不同提示词
- 使用模板和变量替换实现动态内容
- 理解提示词对AI行为的影响

**关键技巧**:
- 清晰定义AI的角色和任务
- 包含引导用户提供信息的指令
- 说明何时调用哪些工具

### 3. Tool工具链集成

**位置**: `LostAndFoundTools.java`

**学习要点**:
- 使用`@Tool`注解让AI能够调用Java方法
- 使用`@P`注解描述参数，帮助AI理解如何传参
- 理解Function Calling机制

**代码示例**:
```java
@Tool("创建失物记录，当用户报告丢失物品时使用")
public String createLostItem(
    @P("物品描述") String description,
    @P("丢失时间") String lostTime,
    @P("丢失地点") String location,
    @P("联系方式") String contactInfo
) {
    // 实现逻辑
}
```

**设计原则**:
- 工具描述要清晰，帮助AI理解使用场景
- 参数描述要详细，说明格式和含义
- 返回值使用String类型，便于AI理解

### 4. AiService 服务化

**位置**: `AssistantService.java` 和 `AssistantServiceFactory.java`

**学习要点**:
- 通过接口定义即可自动生成AI服务实现
- 使用`@SystemMessage`定义系统提示词
- 使用`AiServices.builder()`构建服务实例
- 理解依赖注入和配置管理

**代码示例**:
```java
public interface AssistantService {
    String chat(String userMessage);
}

// 构建实例
AssistantService service = AiServices.builder(AssistantService.class)
    .chatLanguageModel(model)
    .chatMemory(chatMemory)
    .tools(tools)
    .build();
```


## 使用示例

### REST API调用示例

#### 1. 报告失物
```bash
curl -X POST http://localhost:8080/lost-items \
  -H "Content-Type: application/json" \
  -d '{
    "description": "黑色钱包",
    "lostTime": "今天上午10点",
    "location": "图书馆三楼",
    "contactInfo": "13800138000"
  }'
```

#### 2. 报告拾物
```bash
curl -X POST http://localhost:8080/found-items \
  -H "Content-Type: application/json" \
  -d '{
    "description": "蓝色雨伞",
    "foundTime": "今天中午12点",
    "location": "食堂门口",
    "contactInfo": "微信：zhangsan123"
  }'
```

#### 3. 查询所有失物
```bash
curl http://localhost:8080/lost-items
```

#### 4. 查询匹配结果
```bash
curl http://localhost:8080/lost-items/{id}/matches
```

#### 5. AI助手对话
```bash
curl -X POST http://localhost:8080/ai/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "我丢了一个黑色钱包"
  }'
```

更多API详情请查看 `API_DOCUMENTATION.md`

## 配置说明

### application.yaml 配置项

#### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_lost_db
    username: root
    password: 12345678  # 修改为你的密码
```

#### Ollama配置
```yaml
ollama:
  base-url: http://localhost:11434
  model-name: qwen2.5:4b
  timeout: 60
```

#### MyBatis配置
```yaml
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.example.lostandfound.model
```

### 日志配置

日志文件位置: `logs/ai-lost-and-found.log`

调整日志级别: 修改 `src/main/resources/logback.xml`

## 常见问题

### Q1: 如何切换到其他Ollama模型？

**A**: 
1. 先下载其他模型：`ollama pull <model-name>`（如 `ollama pull llama2`）
2. 修改 `application.properties` 中的 `ollama.model.name`
3. 重启应用

**推荐模型**:
- `qwen2.5:4b` - 轻量级，响应快（推荐）
- `qwen2.5:7b` - 更强大，需要更多资源
- `llama2` - Meta的开源模型
- `mistral` - 性能优秀的开源模型

### Q2: 如何提高匹配准确度？

**A**: 
1. 优化 `MatchingServiceImpl` 中的匹配算法
2. 改进提示词，让AI提取更结构化的信息
3. 增加更多匹配维度（如物品类别、颜色、品牌等）
4. 使用更强大的模型（如qwen2.5:7b）

### Q3: Ollama服务连接失败怎么办？

**A**: 
1. 检查Ollama服务是否运行：`curl http://localhost:11434/api/tags`
2. 如果未运行，启动服务：`ollama serve`
3. 检查模型是否已下载：`ollama list`
4. 检查防火墙是否阻止了11434端口

### Q4: 数据库连接失败怎么办？

**A**: 
1. 检查MySQL服务是否运行
2. 确认数据库 `ai_lost_db` 已创建
3. 检查 `application.yaml` 中的用户名和密码是否正确
4. 确认已执行 `schema.sql` 创建表结构

### Q5: 如何处理API调用失败？

**A**: 系统已实现 `GlobalExceptionHandler` 进行错误处理：
- 捕获异常并记录日志
- 向用户返回友好的错误信息
- 保持系统稳定性