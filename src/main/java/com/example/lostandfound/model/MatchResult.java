package com.example.lostandfound.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 匹配结果
 */
@Data
@AllArgsConstructor
public class MatchResult {
    private String lostItemId;
    private String foundItemId;
    private double matchScore;
    private String matchReason;
    private LocalDateTime matchedAt;
    
    public MatchResult(String lostItemId, String foundItemId, double matchScore, String matchReason) {
        this.lostItemId = lostItemId;
        this.foundItemId = foundItemId;
        this.matchScore = matchScore;
        this.matchReason = matchReason;
        this.matchedAt = LocalDateTime.now();
    }
}
