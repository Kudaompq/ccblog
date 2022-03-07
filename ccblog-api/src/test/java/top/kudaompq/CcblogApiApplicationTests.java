package top.kudaompq;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.kudaompq.common.RedisKey;
import top.kudaompq.service.RedisService;
import top.kudaompq.task.SyncScheduleTask;
import top.kudaompq.utils.ImageUtils;

import java.util.Map;

@SpringBootTest
class CcblogApiApplicationTests {
    @Autowired
    RedisService redisService;

    @Autowired
    SyncScheduleTask syncScheduleTask;

    @Test
    void contextLoads() {
        String s = ImageUtils.pushGithub(ImageUtils.getImageByRequest("http://images.kudaompq.top/20220210152649.jpeg"));
        System.out.println(s);
    }

    @Test
    void testRedis(){
        // Map blogViewsMap = redisService.getMapByHash(RedisKey.BLOG_VIEWS_MAP);
        // blogViewsMap.forEach((k,v) -> {
        //     System.out.println(k + " " + v);
        // });
        // Object valueByHashKey = redisService.getValueByHashKey(RedisKey.BLOG_VIEWS_MAP, 5l);
        // System.out.println(valueByHashKey);

        redisService.incrementByHashKey(RedisKey.BLOG_VIEWS_MAP,5l,new Integer(1));
    }

    @Test
    void testTask(){
        syncScheduleTask.syncVisitInfoToDatabase();
    }

}
