package com.example.lostandfound.repository;

import com.example.lostandfound.mapper.FoundItemMapper;
import com.example.lostandfound.mapper.LostItemMapper;
import com.example.lostandfound.model.FoundItem;
import com.example.lostandfound.model.LostItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MyBatis实现的物品数据访问
 */
@Repository
@RequiredArgsConstructor
public class MyBatisItemRepository implements ItemRepository {
    
    private final LostItemMapper lostItemMapper;
    private final FoundItemMapper foundItemMapper;
    
    @Override
    public LostItem saveLostItem(LostItem lostItem) {
        if (lostItem == null) {
            throw new IllegalArgumentException("LostItem cannot be null");
        }
        if (lostItem.getId() == null) {
            throw new IllegalArgumentException("LostItem ID cannot be null");
        }
        
        LostItem existing = lostItemMapper.selectById(lostItem.getId());
        if (existing != null) {
            lostItemMapper.update(lostItem);
        } else {
            lostItemMapper.insert(lostItem);
        }
        
        return lostItem;
    }
    
    @Override
    public FoundItem saveFoundItem(FoundItem foundItem) {
        if (foundItem == null) {
            throw new IllegalArgumentException("FoundItem cannot be null");
        }
        if (foundItem.getId() == null) {
            throw new IllegalArgumentException("FoundItem ID cannot be null");
        }
        
        FoundItem existing = foundItemMapper.selectById(foundItem.getId());
        if (existing != null) {
            foundItemMapper.update(foundItem);
        } else {
            foundItemMapper.insert(foundItem);
        }
        
        return foundItem;
    }
    
    @Override
    public List<LostItem> getAllLostItems() {
        return lostItemMapper.selectAll();
    }
    
    @Override
    public List<FoundItem> getAllFoundItems() {
        return foundItemMapper.selectAll();
    }
    
    @Override
    public Optional<LostItem> findLostItemById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(lostItemMapper.selectById(id));
    }
    
    @Override
    public Optional<FoundItem> findFoundItemById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(foundItemMapper.selectById(id));
    }
}
