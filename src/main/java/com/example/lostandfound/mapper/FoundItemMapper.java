package com.example.lostandfound.mapper;

import com.example.lostandfound.model.FoundItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拾物记录Mapper接口
 */
@Mapper
public interface FoundItemMapper {
    
    int insert(FoundItem foundItem);
    
    int update(FoundItem foundItem);
    
    FoundItem selectById(@Param("id") String id);
    
    List<FoundItem> selectAll();
    
    List<FoundItem> selectByStatus(@Param("status") String status);
    
    int deleteById(@Param("id") String id);
}
