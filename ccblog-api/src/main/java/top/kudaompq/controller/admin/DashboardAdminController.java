package top.kudaompq.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.common.RedisKey;
import top.kudaompq.common.Result;
import top.kudaompq.entity.CityVisitor;
import top.kudaompq.entity.Visitor;
import top.kudaompq.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/14 12:24
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/dashboard")
public class DashboardAdminController {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    RedisService redisService;

    @GetMapping("")
    public Result dashboard(){
        int todayPV = dashboardService.countVisitLogByToday();
        int todayUV = redisService.countBySet(RedisKey.IDENTIFICATION_SET);
        int blogCount = dashboardService.getBlogCount();
        int commentCount = dashboardService.getCommentCount();
        Map<String, List> categoryBlogCountMap = dashboardService.getCategoryBlogCountMap();
        Map<String, List> tagBlogCountMap = dashboardService.getTagBlogCountMap();
        Map<String, List> visitRecordMap = dashboardService.getVisitRecordMap();
        List<CityVisitor> cityVisitorList = dashboardService.getCityVisitorList();

        Map<String, Object> map = new HashMap<>();
        map.put("pv", todayPV);
        map.put("uv", todayUV);
        map.put("blogCount", blogCount);
        map.put("commentCount", commentCount);
        map.put("category", categoryBlogCountMap);
        map.put("tag", tagBlogCountMap);
        map.put("visitRecord", visitRecordMap);
        map.put("cityVisitor", cityVisitorList);
        return Result.success(map);
    }




}
