package com.example.lostandfound.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 失物记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LostItem {
    private String id;
    private String description;
    private String lostTime;
    private String location;
    private String contactInfo;
    private LocalDateTime createdAt;
    private ItemStatus status;
    
    public LostItem(String description, String lostTime, String location, String contactInfo) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.lostTime = lostTime;
        this.location = location;
        this.contactInfo = contactInfo;
        this.createdAt = LocalDateTime.now();
        this.status = ItemStatus.PENDING;
    }
}
