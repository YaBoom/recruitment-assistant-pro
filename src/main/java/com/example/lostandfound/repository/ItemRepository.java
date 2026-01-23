package com.example.lostandfound.repository;

import com.example.lostandfound.model.FoundItem;
import com.example.lostandfound.model.LostItem;

import java.util.List;
import java.util.Optional;

/**
 * 物品数据访问接口
 */
public interface ItemRepository {
    
    LostItem saveLostItem(LostItem lostItem);
    
    FoundItem saveFoundItem(FoundItem foundItem);
    
    List<LostItem> getAllLostItems();
    
    List<FoundItem> getAllFoundItems();
    
    Optional<LostItem> findLostItemById(String id);
    
    Optional<FoundItem> findFoundItemById(String id);
}
