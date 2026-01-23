package com.example.lostandfound.config;

import com.example.lostandfound.ai.AssistantService;
import com.example.lostandfound.ai.AssistantServiceFactory;
import com.example.lostandfound.tool.LostAndFoundTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI服务配置类
 */
@Configuration
public class AiServiceConfig {
    
    @Value("${ollama.base-url}")
    private String ollamaBaseUrl;
    
    @Value("${ollama.model-name}")
    private String modelName;
    
    @Value("${ollama.timeout:180}")
    private int ollamaTimeout;
    
    @Value("${chat-memory.max-messages}")
    private int chatMemoryMaxMessages;
    
    @Bean
    public AssistantService assistantService(LostAndFoundTools tools, PromptManager promptManager) {
        return AssistantServiceFactory.createAssistantService(
                ollamaBaseUrl,
                modelName,
                chatMemoryMaxMessages,
                ollamaTimeout,
                tools,
                promptManager
        );
    }
}
