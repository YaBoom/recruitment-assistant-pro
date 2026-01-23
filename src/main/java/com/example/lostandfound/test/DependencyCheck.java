package com.example.lostandfound.test;

// 验证所有流式响应需要的依赖是否可用
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.http.MediaType;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;

/**
 * 依赖检查类
 * 如果这个类能编译通过，说明所有流式响应需要的依赖都已就绪
 */
public class DependencyCheck {
    
    public void checkDependencies() {
        System.out.println("✓ SseEmitter 可用 (来自 spring-boot-starter-web)");
        System.out.println("✓ MediaType 可用 (来自 spring-boot-starter-web)");
        System.out.println("✓ TokenStream 可用 (来自 langchain4j)");
        System.out.println("✓ StreamingChatLanguageModel 可用 (来自 langchain4j)");
        System.out.println("✓ OllamaStreamingChatModel 可用 (来自 langchain4j-ollama)");
        System.out.println("\n所有流式响应依赖已就绪！");
    }
    
    public static void main(String[] args) {
        new DependencyCheck().checkDependencies();
    }
}
