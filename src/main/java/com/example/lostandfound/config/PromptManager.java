package com.example.lostandfound.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 动态提示词管理器
 */
@Component
public class PromptManager {
    
    @Value("${app.matching.score-threshold:0.3}")
    private Double matchThreshold;
    
    @Value("${app.prompt.enabled:true}")
    private Boolean promptEnabled;
    
    @Value("${app.prompt.refresh-interval:300}")
    private Integer refreshInterval;
    
    private final DateTimeFormatter dateTimeFormatter;
    private String cachedSystemPrompt;
    private LocalDateTime lastRefreshTime;
    
    public PromptManager() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lastRefreshTime = LocalDateTime.now();
    }
    
    public String getSystemPrompt() {
        if (cachedSystemPrompt == null || shouldRefresh()) {
            refresh();
        }
        return cachedSystemPrompt;
    }
    
    private boolean shouldRefresh() {
        if (!promptEnabled) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(lastRefreshTime.plusSeconds(refreshInterval));
    }
    
    public void refresh() {
        this.cachedSystemPrompt = buildSystemPrompt();
        this.lastRefreshTime = LocalDateTime.now();
    }
    
    private String buildSystemPrompt() {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("你是失物招领助手。\n\n");
        
        prompt.append("工具：\n");
        prompt.append("- createLostItem(描述,时间,地点,联系方式)\n");
        prompt.append("- createFoundItem(描述,时间,地点,联系方式)\n");
        prompt.append("- queryLostItems(), queryFoundItems()\n");
        prompt.append("- matchItems(物品ID,类型)\n\n");
        
        prompt.append("流程：询问需求→收集信息→调用工具→展示结果\n");
        prompt.append("规则：简洁友好，一次一问，使用中文\n\n");
        
        prompt.append(getDynamicContext());
        
        return prompt.toString();
    }
    
    private String getDynamicContext() {
        StringBuilder context = new StringBuilder();
        
        LocalDateTime now = LocalDateTime.now();
        context.append("- 当前时间：").append(now.format(dateTimeFormatter)).append("\n");
        
        if (matchThreshold != null) {
            context.append("- 匹配分数阈值：").append(matchThreshold).append("\n");
        }
        
        return context.toString();
    }
}
