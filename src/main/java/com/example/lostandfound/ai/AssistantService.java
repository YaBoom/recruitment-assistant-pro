package com.example.lostandfound.ai;

import dev.langchain4j.service.UserMessage;

/**
 * AI助手服务接口
 */
public interface AssistantService {
    
    @UserMessage("{{it}}")
    String chat(String userMessage);
}
