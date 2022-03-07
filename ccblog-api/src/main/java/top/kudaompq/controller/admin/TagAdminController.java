package top.kudaompq.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kudaompq.annotation.OperationLogger;
import top.kudaompq.common.PageResult;
import top.kudaompq.common.Result;
import top.kudaompq.entity.Tag;
import top.kudaompq.service.TagService;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/31 21:10
 * @version: v1.0
 */
@RequestMapping("/admin/tag")
@RestController
@Api(description = "后台标签管理模块")
public class TagAdminController {

    @Autowired
    TagService tagService;

    @RequiresAuthentication
    @GetMapping("/list")
    public Result tagList(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        IPage tagList = tagService.getTagListByPage(pageNum, pageSize);
        return Result.success(PageResult.create(tagList));
    }


    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @RequiresAuthentication
    @OperationLogger("添加标签")
    @PostMapping("/save")
    public Result saveTag(@RequestBody Tag tag){
        tagService.saveTag(tag);
        return Result.success("添加标签成功",tag.getId());

    }

    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @RequiresAuthentication
    @OperationLogger("更新标签")
    @PutMapping("/update")
    public Result updateTag(@RequestBody Tag tag){
        tagService.updateTag(tag);
        return Result.success("修改标签成功");

    }

    @RequiresPermissions(value = {"admin"},logical = Logical.OR)
    @RequiresAuthentication
    @OperationLogger("删除标签")
    @DeleteMapping("/delete")
    public Result DeleteTag(@RequestParam Integer id){
        tagService.deleteTag(id);
        return Result.success("删除标签成功");
    }
}
