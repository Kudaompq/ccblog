package top.kudaompq.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Comment;
import top.kudaompq.service.BlogService;
import top.kudaompq.service.CommentService;
import top.kudaompq.utils.StringUtils;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/10 16:51
 * @version: v1.0
 */

@RestController
@RequestMapping("/admin/comments")
@Api(description = "后台评论管理模块")
public class CommentAdminController {

    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "") Integer page,
                       @RequestParam(defaultValue = "") Long blogId,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(PageResult.create(commentService.getCommentListByPage(page,blogId,pageNum,pageSize)));
    }

    @GetMapping("/blogIdAndTitle")
    public Result blogIdAndTitle(){
        return Result.success(blogService.getIdAndTitleList());
    }

    @OperationLogger("更新评论公开状态")
    @PutMapping("/published")
    public Result updatePublished(@RequestParam Long id,@RequestParam Boolean published){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setPublished(published);
        boolean update = commentService.updateById(comment);
        if (update){
            return Result.success("操作成功");
        }else{
            return Result.success("操作失败");
        }
    }

    @OperationLogger("更新评论邮件提醒状态")
    @PutMapping("/notice")
    public Result updateNotice(@RequestParam Long id, @RequestParam Boolean notice){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setNotice(notice);
        boolean update = commentService.updateById(comment);
        if (update){
            return Result.success("操作成功");
        }else{
            return Result.success("操作失败");
        }
    }

    @OperationLogger("删除评论")
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Long id){
        commentService.deleteCommentById(id);
        return Result.success("操作成功");
    }

    @OperationLogger("修改评论")
    @PutMapping("/update")
    public Result updateComment(@RequestBody Comment comment) {
        if (StringUtils.isEmpty(comment.getNickname(), comment.getAvatar(), comment.getEmail(), comment.getIp(), comment.getContent())) {
            return Result.error("参数有误");
        }
        commentService.updateById(comment);
        return Result.success("评论修改成功");
    }

}
