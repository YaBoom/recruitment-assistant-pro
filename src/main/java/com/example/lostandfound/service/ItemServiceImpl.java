package com.example.lostandfound.service;

import com.example.lostandfound.model.FoundItem;
import com.example.lostandfound.model.ItemStatus;
import com.example.lostandfound.model.LostItem;
import com.example.lostandfound.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * ItemService实现类
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemRepository itemRepository;
    
    @Override
    public LostItem createLostItem(String description, String lostTime, String location, String contactInfo) {
        validateInput(description, "物品描述");
        validateInput(lostTime, "丢失时间");
        validateInput(location, "丢失地点");
        validateInput(contactInfo, "联系方式");
        
        String id = UUID.randomUUID().toString();
        
        LostItem lostItem = new LostItem(
            id,
            description.trim(),
            lostTime.trim(),
            location.trim(),
            contactInfo.trim(),
            LocalDateTime.now(),
            ItemStatus.PENDING
        );
        
        itemRepository.saveLostItem(lostItem);
        
        return lostItem;
    }
    
    @Override
    public FoundItem createFoundItem(String description, String foundTime, String location, String contactInfo) {
        validateInput(description, "物品描述");
        validateInput(foundTime, "拾到时间");
        validateInput(location, "拾到地点");
        validateInput(contactInfo, "联系方式");
        
        String id = UUID.randomUUID().toString();
        
        FoundItem foundItem = new FoundItem(
            id,
            description.trim(),
            foundTime.trim(),
            location.trim(),
            contactInfo.trim(),
            LocalDateTime.now(),
            ItemStatus.PENDING
        );
        
        itemRepository.saveFoundItem(foundItem);
        
        return foundItem;
    }
    
    @Override
    public LostItem getLostItemById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return itemRepository.findLostItemById(id).orElse(null);
    }
    
    @Override
    public FoundItem getFoundItemById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return itemRepository.findFoundItemById(id).orElse(null);
    }
    
    @Override
    public List<LostItem> getAllLostItems() {
        return itemRepository.getAllLostItems();
    }
    
    @Override
    public List<FoundItem> getAllFoundItems() {
        return itemRepository.getAllFoundItems();
    }
    
    private void validateInput(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + "不能为null");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + "不能为空");
        }
    }
}
