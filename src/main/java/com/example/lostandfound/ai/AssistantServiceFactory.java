package com.example.lostandfound.ai;

import com.example.lostandfound.config.PromptManager;
import com.example.lostandfound.tool.LostAndFoundTools;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

import java.time.Duration;

/**
 * AI助手服务工厂类
 */
public class AssistantServiceFactory {
    
    public static AssistantService createAssistantService(
            String ollamaBaseUrl,
            String modelName,
            int chatMemoryWindowSize,
            int timeoutSeconds,
            LostAndFoundTools tools,
            PromptManager promptManager) {
        
        if (ollamaBaseUrl == null || ollamaBaseUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Ollama服务地址不能为空");
        }
        if (modelName == null || modelName.trim().isEmpty()) {
            throw new IllegalArgumentException("模型名称不能为空");
        }
        if (chatMemoryWindowSize <= 0) {
            throw new IllegalArgumentException("ChatMemory窗口大小必须大于0");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("超时时间必须大于0");
        }
        if (tools == null) {
            throw new IllegalArgumentException("工具对象不能为null");
        }
        if (promptManager == null) {
            throw new IllegalArgumentException("PromptManager不能为null");
        }
        
        ChatLanguageModel chatLanguageModel = OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .temperature(0.7)
                .build();
        
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(chatMemoryWindowSize);
        
        // 使用动态提示词
        String systemPrompt = promptManager.getSystemPrompt();
        
        return AiServices.builder(AssistantService.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(chatMemory)
                .tools(tools)
                .systemMessageProvider(chatMemoryId -> systemPrompt)
                .build();
    }
}
