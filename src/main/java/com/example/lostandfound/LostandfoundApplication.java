package com.example.lostandfound;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AI失物招领助手应用程序入口
 */
@SpringBootApplication
@MapperScan("com.example.lostandfound.mapper")
public class LostandfoundApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LostandfoundApplication.class, args);
    }
}
