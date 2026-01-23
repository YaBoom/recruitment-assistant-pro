package com.example.lostandfound.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 拾物记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoundItem {
    private String id;
    private String description;
    private String foundTime;
    private String location;
    private String contactInfo;
    private LocalDateTime createdAt;
    private ItemStatus status;
    
    public FoundItem(String description, String foundTime, String location, String contactInfo) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.foundTime = foundTime;
        this.location = location;
        this.contactInfo = contactInfo;
        this.createdAt = LocalDateTime.now();
        this.status = ItemStatus.PENDING;
    }
}
