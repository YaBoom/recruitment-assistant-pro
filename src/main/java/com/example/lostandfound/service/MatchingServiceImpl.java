package com.example.lostandfound.service;

import com.example.lostandfound.model.FoundItem;
import com.example.lostandfound.model.LostItem;
import com.example.lostandfound.model.MatchResult;
import com.example.lostandfound.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 匹配服务实现类
 */
@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {
    
    private static final double MATCH_THRESHOLD = 0.3;
    private static final double DESCRIPTION_WEIGHT = 0.5;
    private static final double TIME_WEIGHT = 0.25;
    private static final double LOCATION_WEIGHT = 0.25;
    
    private final ItemRepository itemRepository;
    
    @Override
    public List<MatchResult> findMatchesForLostItem(String lostItemId) {
        LostItem lostItem = itemRepository.findLostItemById(lostItemId).orElse(null);
        if (lostItem == null) {
            return new ArrayList<>();
        }
        
        List<FoundItem> allFoundItems = itemRepository.getAllFoundItems();
        List<MatchResult> matches = new ArrayList<>();
        
        for (FoundItem foundItem : allFoundItems) {
            double score = calculateMatchScore(lostItem, foundItem);
            
            if (score >= MATCH_THRESHOLD) {
                String reason = generateMatchReason(lostItem, foundItem, score);
                MatchResult matchResult = new MatchResult(
                    lostItem.getId(),
                    foundItem.getId(),
                    score,
                    reason
                );
                matches.add(matchResult);
            }
        }
        
        matches.sort(Comparator.comparingDouble(MatchResult::getMatchScore).reversed());
        
        return matches;
    }
    
    @Override
    public List<MatchResult> findMatchesForFoundItem(String foundItemId) {
        FoundItem foundItem = itemRepository.findFoundItemById(foundItemId).orElse(null);
        if (foundItem == null) {
            return new ArrayList<>();
        }
        
        List<LostItem> allLostItems = itemRepository.getAllLostItems();
        List<MatchResult> matches = new ArrayList<>();
        
        for (LostItem lostItem : allLostItems) {
            double score = calculateMatchScore(lostItem, foundItem);
            
            if (score >= MATCH_THRESHOLD) {
                String reason = generateMatchReason(lostItem, foundItem, score);
                MatchResult matchResult = new MatchResult(
                    lostItem.getId(),
                    foundItem.getId(),
                    score,
                    reason
                );
                matches.add(matchResult);
            }
        }
        
        matches.sort(Comparator.comparingDouble(MatchResult::getMatchScore).reversed());
        
        return matches;
    }
    
    private double calculateMatchScore(LostItem lostItem, FoundItem foundItem) {
        if (lostItem == null || foundItem == null) {
            return 0.0;
        }
        
        double descriptionSimilarity = calculateDescriptionSimilarity(
            lostItem.getDescription(), 
            foundItem.getDescription()
        );
        
        double timeSimilarity = calculateTimeSimilarity(
            lostItem.getLostTime(), 
            foundItem.getFoundTime()
        );
        
        double locationSimilarity = calculateLocationSimilarity(
            lostItem.getLocation(), 
            foundItem.getLocation()
        );
        
        double totalScore = descriptionSimilarity * DESCRIPTION_WEIGHT +
                           timeSimilarity * TIME_WEIGHT +
                           locationSimilarity * LOCATION_WEIGHT;
        
        return Math.max(0.0, Math.min(1.0, totalScore));
    }
    
    private double calculateDescriptionSimilarity(String description1, String description2) {
        if (description1 == null || description2 == null) {
            return 0.0;
        }
        
        Set<String> keywords1 = extractKeywords(description1);
        Set<String> keywords2 = extractKeywords(description2);
        
        if (keywords1.isEmpty() && keywords2.isEmpty()) {
            return 0.0;
        }
        
        Set<String> intersection = new HashSet<>(keywords1);
        intersection.retainAll(keywords2);
        
        int commonCount = intersection.size();
        int totalCount = keywords1.size() + keywords2.size();
        
        if (totalCount == 0) {
            return 0.0;
        }
        
        return (2.0 * commonCount) / totalCount;
    }
    
    private double calculateTimeSimilarity(String time1, String time2) {
        if (time1 == null || time2 == null) {
            return 0.0;
        }
        
        String t1 = time1.toLowerCase().trim();
        String t2 = time2.toLowerCase().trim();
        
        if (t1.equals(t2)) {
            return 1.0;
        }
        
        Set<String> timeKeywords1 = extractTimeKeywords(t1);
        Set<String> timeKeywords2 = extractTimeKeywords(t2);
        
        if (timeKeywords1.isEmpty() && timeKeywords2.isEmpty()) {
            if (t1.contains(t2) || t2.contains(t1)) {
                return 0.7;
            }
            return 0.0;
        }
        
        Set<String> intersection = new HashSet<>(timeKeywords1);
        intersection.retainAll(timeKeywords2);
        
        int commonCount = intersection.size();
        int totalCount = Math.max(timeKeywords1.size(), timeKeywords2.size());
        
        if (totalCount == 0) {
            return 0.0;
        }
        
        return (double) commonCount / totalCount;
    }
    
    private double calculateLocationSimilarity(String location1, String location2) {
        if (location1 == null || location2 == null) {
            return 0.0;
        }
        
        String loc1 = location1.toLowerCase().trim();
        String loc2 = location2.toLowerCase().trim();
        
        if (loc1.equals(loc2)) {
            return 1.0;
        }
        
        if (loc1.contains(loc2) || loc2.contains(loc1)) {
            return 0.8;
        }
        
        Set<String> locationKeywords1 = extractKeywords(loc1);
        Set<String> locationKeywords2 = extractKeywords(loc2);
        
        if (locationKeywords1.isEmpty() && locationKeywords2.isEmpty()) {
            return 0.0;
        }
        
        Set<String> intersection = new HashSet<>(locationKeywords1);
        intersection.retainAll(locationKeywords2);
        
        int commonCount = intersection.size();
        int totalCount = Math.max(locationKeywords1.size(), locationKeywords2.size());
        
        if (totalCount == 0) {
            return 0.0;
        }
        
        return (double) commonCount / totalCount;
    }
    
    private Set<String> extractKeywords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new HashSet<>();
        }
        
        String[] words = text.toLowerCase().split("[\\s\\p{Punct}]+");
        
        Set<String> keywords = new HashSet<>();
        for (String word : words) {
            if (word.length() >= 2 && !isStopWord(word)) {
                keywords.add(word);
            }
        }
        
        return keywords;
    }
    
    private Set<String> extractTimeKeywords(String timeText) {
        Set<String> keywords = new HashSet<>();
        String text = timeText.toLowerCase();
        
        String[] timeWords = {
            "今天", "昨天", "前天", "明天", "后天",
            "上午", "下午", "晚上", "中午", "早上",
            "周一", "周二", "周三", "周四", "周五", "周六", "周日",
            "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日",
            "上周", "本周", "下周", "上个月", "这个月", "下个月"
        };
        
        for (String word : timeWords) {
            if (text.contains(word)) {
                keywords.add(word);
            }
        }
        
        return keywords;
    }
    
    private boolean isStopWord(String word) {
        Set<String> stopWords = Set.of(
            "的", "了", "在", "是", "我", "有", "和", "就", 
            "不", "人", "都", "一", "个", "上", "也", "很",
            "到", "说", "要", "去", "你", "会", "着", "没",
            "看", "好", "自己", "这", "那", "里", "为"
        );
        
        return stopWords.contains(word);
    }
    
    private String generateMatchReason(LostItem lostItem, FoundItem foundItem, double score) {
        StringBuilder reason = new StringBuilder();
        
        double descSim = calculateDescriptionSimilarity(
            lostItem.getDescription(), 
            foundItem.getDescription()
        );
        double timeSim = calculateTimeSimilarity(
            lostItem.getLostTime(), 
            foundItem.getFoundTime()
        );
        double locSim = calculateLocationSimilarity(
            lostItem.getLocation(), 
            foundItem.getLocation()
        );
        
        reason.append(String.format("匹配度：%.0f%% - ", score * 100));
        
        List<String> reasons = new ArrayList<>();
        
        if (descSim >= 0.5) {
            reasons.add("物品描述高度相似");
        } else if (descSim >= 0.3) {
            reasons.add("物品描述部分匹配");
        }
        
        if (timeSim >= 0.7) {
            reasons.add("时间非常接近");
        } else if (timeSim >= 0.4) {
            reasons.add("时间较为接近");
        }
        
        if (locSim >= 0.8) {
            reasons.add("地点完全匹配");
        } else if (locSim >= 0.5) {
            reasons.add("地点相近");
        }
        
        if (reasons.isEmpty()) {
            reasons.add("存在一定相似性");
        }
        
        reason.append(String.join("，", reasons));
        
        return reason.toString();
    }
}
