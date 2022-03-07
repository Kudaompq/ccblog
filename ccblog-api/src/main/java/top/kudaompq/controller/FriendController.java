package top.kudaompq.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.kudaompq.annotation.VisitLogger;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Friend;
import top.kudaompq.enums.VisitBehavior;
import top.kudaompq.model.vo.FriendInfo;
import top.kudaompq.model.vo.FriendVO;
import top.kudaompq.service.FriendService;
import top.kudaompq.service.SiteSettingService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/friend")
public class FriendController {


    @Autowired
    FriendService friendService;

    @VisitLogger(VisitBehavior.FRIEND)
    @GetMapping("/list")
    public Result list(){
        FriendInfo friendInfo = friendService.getFriendInfo();
        List<FriendVO> friendList = friendService.getFriendList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("friendList",friendList);
        map.put("friendInfo",friendInfo);
        return Result.success(map);
    }

}
