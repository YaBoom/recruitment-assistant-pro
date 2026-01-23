package com.example.lostandfound.controller;

import com.example.lostandfound.dto.ApiResponse;
import com.example.lostandfound.model.FoundItem;
import com.example.lostandfound.model.MatchResult;
import com.example.lostandfound.service.ItemService;
import com.example.lostandfound.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 拾物记录Controller
 */
@Slf4j
@RestController
@RequestMapping("/found-items")
@RequiredArgsConstructor
public class FoundItemController {
    
    private final ItemService itemService;
    private final MatchingService matchingService;
    
    @PostMapping
    public ApiResponse<FoundItem> reportFoundItem(@Valid @RequestBody FoundItem foundItem) {
        log.info("报告拾物: {}", foundItem);
        FoundItem created = itemService.createFoundItem(
                foundItem.getDescription(),
                foundItem.getFoundTime(),
                foundItem.getLocation(),
                foundItem.getContactInfo()
        );
        return ApiResponse.success("拾物报告成功", created);
    }
    
    @GetMapping
    public ApiResponse<List<FoundItem>> getAllFoundItems() {
        log.info("查询所有拾物记录");
        List<FoundItem> items = itemService.getAllFoundItems();
        return ApiResponse.success(items);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<FoundItem> getFoundItemById(@PathVariable String id) {
        log.info("查询拾物记录: {}", id);
        FoundItem item = itemService.getFoundItemById(id);
        if (item == null) {
            return ApiResponse.error("拾物记录不存在");
        }
        return ApiResponse.success(item);
    }
    
    @GetMapping("/{id}/matches")
    public ApiResponse<List<MatchResult>> findMatches(@PathVariable String id) {
        log.info("为拾物查找匹配: {}", id);
        List<MatchResult> matches = matchingService.findMatchesForFoundItem(id);
        return ApiResponse.success(matches);
    }
}
