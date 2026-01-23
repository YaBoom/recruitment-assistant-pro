package com.example.lostandfound.controller;

import com.example.lostandfound.dto.ApiResponse;
import com.example.lostandfound.model.LostItem;
import com.example.lostandfound.model.MatchResult;
import com.example.lostandfound.service.ItemService;
import com.example.lostandfound.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 失物记录Controller
 */
@Slf4j
@RestController
@RequestMapping("/lost-items")
@RequiredArgsConstructor
public class LostItemController {
    
    private final ItemService itemService;
    private final MatchingService matchingService;
    
    @PostMapping
    public ApiResponse<LostItem> reportLostItem(@Valid @RequestBody LostItem lostItem) {
        log.info("报告失物: {}", lostItem);
        LostItem created = itemService.createLostItem(
                lostItem.getDescription(),
                lostItem.getLostTime(),
                lostItem.getLocation(),
                lostItem.getContactInfo()
        );
        return ApiResponse.success("失物报告成功", created);
    }
    
    @GetMapping
    public ApiResponse<List<LostItem>> getAllLostItems() {
        log.info("查询所有失物记录");
        List<LostItem> items = itemService.getAllLostItems();
        return ApiResponse.success(items);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<LostItem> getLostItemById(@PathVariable String id) {
        log.info("查询失物记录: {}", id);
        LostItem item = itemService.getLostItemById(id);
        if (item == null) {
            return ApiResponse.error("失物记录不存在");
        }
        return ApiResponse.success(item);
    }
    
    @GetMapping("/{id}/matches")
    public ApiResponse<List<MatchResult>> findMatches(@PathVariable String id) {
        log.info("为失物查找匹配: {}", id);
        List<MatchResult> matches = matchingService.findMatchesForLostItem(id);
        return ApiResponse.success(matches);
    }
}
