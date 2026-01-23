package com.example.lostandfound.controller;

import com.example.lostandfound.ai.AssistantService;
import com.example.lostandfound.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AI助手Controller
 */
@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiAssistantController {
    
    private final AssistantService assistantService;
    
    @PostMapping("/chat")
    public ApiResponse<String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ApiResponse.error("消息不能为空");
        }

        log.info("收到用户消息: {}", userMessage);
        
        try {
            long startTime = System.currentTimeMillis();
            String response = assistantService.chat(userMessage);
            long duration = System.currentTimeMillis() - startTime;
            
            log.info("AI响应耗时: {}ms, 响应长度: {} 字符", duration, response.length());
            return ApiResponse.success(response);
        } catch (Exception e) {
            log.error("AI对话失败", e);
            return ApiResponse.error("AI对话失败: " + e.getMessage());
        }
    }
}
