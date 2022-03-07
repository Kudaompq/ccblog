package top.kudaompq.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.RedisKey;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Friend;
import top.kudaompq.service.FriendService;
import top.kudaompq.service.RedisService;

import java.time.LocalDateTime;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/7 21:06
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/friend")
@Api(description = "后台友链管理模块")
public class FriendAdminController {

    @Autowired
    FriendService friendService;

    @Autowired
    RedisService redisService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(PageResult.create(friendService.getFriendListByPage(pageNum,pageSize)));
    }

    @OperationLogger(value = "修改友链公开状态")
    @PutMapping("/published")
    public Result updatePublished(@RequestParam Long id,
                                  @RequestParam Boolean published){
        Friend friend = new Friend();
        friend.setId(id);
        friend.setPublished(published);
        boolean update = friendService.updateById(friend);
        if (update){
            removeFriendListCache();
            return Result.success("操作成功");
        }else {
            return Result.error("操作失败");
        }
    }

    @OperationLogger(value = "添加友链")
    @PostMapping("/save")
    public Result save(@RequestBody Friend friend){
        friend.setCreateTime(LocalDateTime.now());
        friend.setViews(0);
        boolean save = friendService.save(friend);
        if (save){
            removeFriendListCache();
            return Result.success("添加友链成功");
        }else{
            return Result.error("添加友链失败");
        }

    }

    @OperationLogger(value = "修改友链")
    @PutMapping("/update")
    public Result update(@RequestBody Friend friend){
        boolean update = friendService.updateById(friend);
        if (update){
            removeFriendListCache();
            return Result.success("修改友链成功");
        }else {
            return Result.error("修改友链失败");
        }
    }

    @OperationLogger("删除友链")
    @DeleteMapping("/delete")
    public Result deleteFriend(@RequestParam Long id) {
        boolean delete = friendService.removeById(id);
        if (delete){
            removeFriendListCache();
            return Result.success("删除友链成功");
        }else{
            return Result.error("删除友链失败");
        }
    }

    @GetMapping("/info")
    public Result into(){
        return Result.success(friendService.getFriendInfo());
    }

    @OperationLogger(value = "修改友链评论开启状态")
    @PutMapping("/comment")
    public Result updateFriendInfoCommentEnabled(@RequestParam Boolean commentEnabled){
        friendService.updateFriendInfoCommentEnabled(commentEnabled);
        return Result.success("修改成功");
    }

    @OperationLogger(value = "修改友链页面信息")
    @PutMapping("/content")
    public Result updateFriendInfoContent(@RequestParam String content){
        friendService.updateFriendInfoContent(content);
        return Result.success("修改成功");
    }

    private void removeFriendListCache(){
        redisService.deleteCacheByKey(RedisKey.FRIEND_LIST);
    }
}
