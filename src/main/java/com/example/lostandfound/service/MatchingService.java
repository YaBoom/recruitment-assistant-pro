package com.example.lostandfound.service;

import com.example.lostandfound.model.MatchResult;

import java.util.List;

/**
 * 匹配服务接口
 */
public interface MatchingService {
    
    List<MatchResult> findMatchesForLostItem(String lostItemId);
    
    List<MatchResult> findMatchesForFoundItem(String foundItemId);
}
