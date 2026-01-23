package com.example.lostandfound.mapper;

import com.example.lostandfound.model.LostItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 失物记录Mapper接口
 */
@Mapper
public interface LostItemMapper {
    
    int insert(LostItem lostItem);
    
    int update(LostItem lostItem);
    
    LostItem selectById(@Param("id") String id);
    
    List<LostItem> selectAll();
    
    List<LostItem> selectByStatus(@Param("status") String status);
    
    int deleteById(@Param("id") String id);
}
