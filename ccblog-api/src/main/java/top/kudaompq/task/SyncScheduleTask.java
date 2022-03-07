package top.kudaompq.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.CityVisitor;
import top.kudaompq.entity.VisitRecord;
import top.kudaompq.model.dto.VisitLogUUIDTime;
import top.kudaompq.service.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description: 简易的定时任务类
 * 后期会采用quartz来实现，先用着
 * @author: kudaompq
 * @date: 2022/2/11 14:04
 * @version: v1.0
 */

@Component
@EnableScheduling
@Slf4j
public class SyncScheduleTask {

    @Autowired
    RedisService redisService;

    @Autowired
    BlogService blogService;

    @Autowired
    VisitLogService visitLogService;

    @Autowired
    VisitorService visitorService;

    @Autowired
    VisitRecordService visitRecordService;

    @Autowired
    CityVisitorService cityVisitorService;

    /**
     * 每日凌晨一点钟更新数据库中的浏览量
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncBlogViewsToDataBase(){
        String redisKey = RedisKey.BLOG_VIEWS_MAP;
        Map blogViewsMap = redisService.getMapByHash(redisKey);
        Set<Integer> keys = blogViewsMap.keySet();
        for (Integer key : keys){
            Integer views = (Integer) blogViewsMap.get(key);
            blogService.updateViews(key.longValue(),views);
        }
        log.info("执行定时任务：更新数据库中博客浏览量");
    }

    /**
     * 清空当天Redis访客标识
     * 记录当天的PV和UV
     * 更新当天所有访客的PV和最后访问时间
     * 更新城市新增访客UV数
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncVisitInfoToDatabase(){
        // 清空当天Redis的访客标识Set，以便统计每日访问量UV
        redisService.deleteCacheByKey(RedisKey.IDENTIFICATION_SET);
        // 获得昨天所有访问日志
        List<VisitLogUUIDTime> yesterdayLogList = visitLogService.getUUIDAndCreateTimeByYesterday();
        HashSet<String> uuidSet = new HashSet<>();
        HashMap<String, Integer> PVMap = new HashMap<>();
        HashMap<String, Date> lastTimeMap = new HashMap<>();
        yesterdayLogList.forEach(log -> {
            String uuid = log.getUuid();
            Date createTime = log.getCreateTime();
            // 记录当天访客的uuid
            uuidSet.add(uuid);
            // 对应uuid的PV++
            PVMap.merge(uuid,1,Integer::sum);
            // 因为在sql使用了降序，所以第一次出现的时间就是最后访问时间
            lastTimeMap.putIfAbsent(uuid,createTime);
        });
        int pv = yesterdayLogList.size();
        int uv = uuidSet.size();
        // 获得昨天日期
        String date = new SimpleDateFormat("MM-dd").format(DateUtils.addDays(new Date(), -1));
        // 记录当天的PV和UV
        VisitRecord visitRecord = new VisitRecord(pv,uv,date);
        visitRecordService.save(visitRecord);
        // 更新档天所有访客的PV和最后访问时间
        uuidSet.forEach(uuid -> {
            VisitLogUUIDTime visitLogUUIDTime = new VisitLogUUIDTime(uuid, lastTimeMap.get(uuid), PVMap.get(uuid));
            visitorService.updatePVAndLastTimeByUUID(visitLogUUIDTime);
        });
        // 查询新增访客的来源
        List<String> ipSouceList = visitorService.getNewVisitorIpSourceByYesterday();
        HashMap<String, Integer> cityVisitorMap = new HashMap<>();
        ipSouceList.forEach(source -> {
            if (source.startsWith("中国")){
                String[] split = source.split("\\|");
                if (split.length == 4){
                    String city = split[2];
                    cityVisitorMap.merge(city,1,Integer::sum);
                }
            }
        });
        // 更新城市新增访问UV数
        cityVisitorMap.forEach((k,v) -> {
            cityVisitorService.saveCityVisitor(new CityVisitor(k,v));
        });
        log.info("执行定时任务：更新访客记录");

    }
}
