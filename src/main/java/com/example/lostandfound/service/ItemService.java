package com.example.lostandfound.service;

import com.example.lostandfound.model.FoundItem;
import com.example.lostandfound.model.LostItem;

import java.util.List;

/**
 * 物品管理服务接口
 */
public interface ItemService {
    
    LostItem createLostItem(String description, String lostTime, String location, String contactInfo);
    
    FoundItem createFoundItem(String description, String foundTime, String location, String contactInfo);
    
    List<LostItem> getAllLostItems();
    
    List<FoundItem> getAllFoundItems();
    
    LostItem getLostItemById(String id);
    
    FoundItem getFoundItemById(String id);
}
