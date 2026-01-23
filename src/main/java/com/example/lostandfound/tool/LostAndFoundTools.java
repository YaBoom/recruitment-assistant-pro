package com.example.lostandfound.tool;

import com.example.lostandfound.model.FoundItem;
import com.example.lostandfound.model.LostItem;
import com.example.lostandfound.model.MatchResult;
import com.example.lostandfound.model.ItemStatus;
import com.example.lostandfound.service.ItemService;
import com.example.lostandfound.service.MatchingService;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.P;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 失物招领工具类
 */
@Component
@RequiredArgsConstructor
public class LostAndFoundTools {
    
    private final ItemService itemService;
    private final MatchingService matchingService;
    
    @Tool("创建失物记录。当用户报告丢失物品时使用此工具。需要收集物品描述、丢失时间、丢失地点和联系方式。")
    public String createLostItem(
            @P("物品描述，例如：黑色钱包、蓝色雨伞、iPhone手机等") String description,
            @P("丢失时间，例如：今天上午、昨天下午3点、2024-01-15等") String lostTime,
            @P("丢失地点，例如：图书馆三楼、食堂门口、教学楼A座等") String location,
            @P("联系方式，例如：手机号、微信号、邮箱等") String contactInfo) {
        
        try {
            LostItem lostItem = itemService.createLostItem(description, lostTime, location, contactInfo);
            List<MatchResult> matches = matchingService.findMatchesForLostItem(lostItem.getId());
            
            StringBuilder result = new StringBuilder();
            result.append("✓ 失物记录已成功创建！\n");
            result.append("记录ID：").append(lostItem.getId()).append("\n");
            result.append("物品描述：").append(lostItem.getDescription()).append("\n");
            result.append("丢失时间：").append(lostItem.getLostTime()).append("\n");
            result.append("丢失地点：").append(lostItem.getLocation()).append("\n\n");
            
            if (matches.isEmpty()) {
                result.append("暂时没有找到匹配的拾物记录，我们会持续监控。");
            } else {
                result.append("找到 ").append(matches.size()).append(" 个可能的匹配项：\n");
                for (int i = 0; i < matches.size(); i++) {
                    MatchResult match = matches.get(i);
                    result.append("\n").append(i + 1).append(". ");
                    result.append(match.getMatchReason());
                    result.append("\n   拾物ID：").append(match.getFoundItemId());
                }
            }
            
            return result.toString();
            
        } catch (IllegalArgumentException e) {
            return "❌ 创建失物记录失败：" + e.getMessage() + "\n请检查输入信息是否完整。";
        } catch (Exception e) {
            return "❌ 创建失物记录时发生错误：" + e.getMessage() + "\n请稍后重试或联系管理员。";
        }
    }
    
    @Tool("创建拾物记录。当用户报告拾到物品时使用此工具。需要收集物品描述、拾到时间、拾到地点和联系方式。")
    public String createFoundItem(
            @P("物品描述，例如：红色背包、银色手表、学生证等") String description,
            @P("拾到时间，例如：今天下午、昨天晚上8点、2024-01-15等") String foundTime,
            @P("拾到地点，例如：操场看台、宿舍楼下、校门口等") String location,
            @P("联系方式，例如：手机号、QQ号、微信号等") String contactInfo) {
        
        try {
            FoundItem foundItem = itemService.createFoundItem(description, foundTime, location, contactInfo);
            List<MatchResult> matches = matchingService.findMatchesForFoundItem(foundItem.getId());
            
            StringBuilder result = new StringBuilder();
            result.append("✓ 拾物记录已成功创建！\n");
            result.append("记录ID：").append(foundItem.getId()).append("\n");
            result.append("物品描述：").append(foundItem.getDescription()).append("\n");
            result.append("拾到时间：").append(foundItem.getFoundTime()).append("\n");
            result.append("拾到地点：").append(foundItem.getLocation()).append("\n\n");
            
            if (matches.isEmpty()) {
                result.append("暂时没有找到匹配的失物记录，我们会持续监控。");
            } else {
                result.append("找到 ").append(matches.size()).append(" 个可能的匹配项：\n");
                for (int i = 0; i < matches.size(); i++) {
                    MatchResult match = matches.get(i);
                    result.append("\n").append(i + 1).append(". ");
                    result.append(match.getMatchReason());
                    result.append("\n   失物ID：").append(match.getLostItemId());
                }
            }
            
            return result.toString();
            
        } catch (IllegalArgumentException e) {
            return "❌ 创建拾物记录失败：" + e.getMessage() + "\n请检查输入信息是否完整。";
        } catch (Exception e) {
            return "❌ 创建拾物记录时发生错误：" + e.getMessage() + "\n请稍后重试或联系管理员。";
        }
    }
    
    @Tool("查询所有失物记录。当用户想要查看有哪些失物时使用此工具。")
    public String queryLostItems() {
        try {
            List<LostItem> lostItems = itemService.getAllLostItems();
            
            if (lostItems.isEmpty()) {
                return "目前系统中没有失物记录。";
            }
            
            StringBuilder result = new StringBuilder();
            result.append("共有 ").append(lostItems.size()).append(" 条失物记录：\n\n");
            
            for (int i = 0; i < lostItems.size(); i++) {
                LostItem item = lostItems.get(i);
                result.append(i + 1).append(". ");
                result.append(item.getDescription()).append("\n");
                result.append("   丢失时间：").append(item.getLostTime()).append("\n");
                result.append("   丢失地点：").append(item.getLocation()).append("\n");
                result.append("   联系方式：").append(item.getContactInfo()).append("\n");
                result.append("   状态：").append(formatStatus(item.getStatus())).append("\n");
                result.append("   记录ID：").append(item.getId()).append("\n");
                
                if (i < lostItems.size() - 1) {
                    result.append("\n");
                }
            }
            
            return result.toString();
            
        } catch (Exception e) {
            return "❌ 查询失物记录时发生错误：" + e.getMessage();
        }
    }
    
    @Tool("查询所有拾物记录。当用户想要查看有哪些拾物时使用此工具。")
    public String queryFoundItems() {
        try {
            List<FoundItem> foundItems = itemService.getAllFoundItems();
            
            if (foundItems.isEmpty()) {
                return "目前系统中没有拾物记录。";
            }
            
            StringBuilder result = new StringBuilder();
            result.append("共有 ").append(foundItems.size()).append(" 条拾物记录：\n\n");
            
            for (int i = 0; i < foundItems.size(); i++) {
                FoundItem item = foundItems.get(i);
                result.append(i + 1).append(". ");
                result.append(item.getDescription()).append("\n");
                result.append("   拾到时间：").append(item.getFoundTime()).append("\n");
                result.append("   拾到地点：").append(item.getLocation()).append("\n");
                result.append("   联系方式：").append(item.getContactInfo()).append("\n");
                result.append("   状态：").append(formatStatus(item.getStatus())).append("\n");
                result.append("   记录ID：").append(item.getId()).append("\n");
                
                if (i < foundItems.size() - 1) {
                    result.append("\n");
                }
            }
            
            return result.toString();
            
        } catch (Exception e) {
            return "❌ 查询拾物记录时发生错误：" + e.getMessage();
        }
    }
    
    @Tool("匹配失物和拾物。根据物品ID查找匹配的记录。当用户想要查看某个物品的匹配结果时使用此工具。")
    public String matchItems(
            @P("物品ID，从查询结果中获取") String itemId,
            @P("物品类型，'lost'表示失物，'found'表示拾物") String itemType) {
        
        try {
            if (itemId == null || itemId.trim().isEmpty()) {
                return "❌ 物品ID不能为空";
            }
            
            if (itemType == null || itemType.trim().isEmpty()) {
                return "❌ 物品类型不能为空";
            }
            
            String type = itemType.toLowerCase().trim();
            List<MatchResult> matches;
            
            if ("lost".equals(type)) {
                matches = matchingService.findMatchesForLostItem(itemId);
            } else if ("found".equals(type)) {
                matches = matchingService.findMatchesForFoundItem(itemId);
            } else {
                return "❌ 无效的物品类型：" + itemType + "\n请使用 'lost' 或 'found'";
            }
            
            if (matches.isEmpty()) {
                return "暂时没有找到匹配的记录。";
            }
            
            StringBuilder result = new StringBuilder();
            result.append("找到 ").append(matches.size()).append(" 个匹配项：\n\n");
            
            for (int i = 0; i < matches.size(); i++) {
                MatchResult match = matches.get(i);
                result.append(i + 1).append(". ");
                result.append(match.getMatchReason()).append("\n");
                result.append("   失物ID：").append(match.getLostItemId()).append("\n");
                result.append("   拾物ID：").append(match.getFoundItemId()).append("\n");
                
                if (i < matches.size() - 1) {
                    result.append("\n");
                }
            }
            
            return result.toString();
            
        } catch (Exception e) {
            return "❌ 匹配物品时发生错误：" + e.getMessage();
        }
    }
    
    private String formatStatus(ItemStatus status) {
        switch (status) {
            case PENDING:
                return "待匹配";
            case MATCHED:
                return "已匹配";
            case RESOLVED:
                return "已解决";
            default:
                return status.toString();
        }
    }
}
